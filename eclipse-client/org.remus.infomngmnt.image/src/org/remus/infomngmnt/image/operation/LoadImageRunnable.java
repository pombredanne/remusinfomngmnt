/*******************************************************************************
 * Copyright (c) 2009 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/

package org.remus.infomngmnt.image.operation;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.graphics.ImageData;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifDirectory;

import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.common.core.streams.FileUtil;
import org.remus.infomngmnt.common.ui.UIUtil;
import org.remus.infomngmnt.core.model.EditingUtil;
import org.remus.infomngmnt.core.model.InformationUtil;
import org.remus.infomngmnt.core.model.StatusCreator;
import org.remus.infomngmnt.core.progress.CancelableRunnable;
import org.remus.infomngmnt.image.ImagePlugin;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class LoadImageRunnable extends CancelableRunnable {
	
	private String imagePath;
	private InformationUnit rawImageDataNode;
	private InformationUnit widhtImageNode;
	private InformationUnit heightImageNode;
	private EditingDomain domain;
	public void setDomain(final EditingDomain domain) {
		this.domain = domain;
	}

	private File file;
	private InformationUnit infoUnit;

	public File getFile() {
		return this.file;
	}

	public void setImagePath(final String imagePath) {
		this.imagePath = imagePath;
	}

	@Override
	protected IStatus runCancelableRunnable(final IProgressMonitor monitor) {
		monitor.beginTask("Checking filename...", IProgressMonitor.UNKNOWN);
		this.file = new File(this.imagePath);
		if (this.file.exists() && this.file.isFile()) {
			monitor.beginTask(NLS.bind("Reading file {0}", this.file.getName()), (int) this.file.length());
			try {
				if (this.domain == null) {
					this.domain = EditingUtil.getInstance().createNewEditingDomain();
				}
				
				final CompoundCommand cc = new CompoundCommand();
				cc.append(new SetCommand(
						this.domain,
						this.rawImageDataNode,
						InfomngmntPackage.Literals.INFORMATION_UNIT__BINARY_VALUE,
						FileUtil.getBytesFromFile(this.file, monitor)));
				boolean isJpgeg = Pattern.compile("^.*\\.jpe?g$", Pattern.CASE_INSENSITIVE).matcher(this.imagePath).matches();
				Metadata metadata = null;
				List<InformationUnit> exifData = new ArrayList<InformationUnit>();
				if (isJpgeg) {
					try {
						metadata = JpegMetadataReader.readMetadata(this.file);
						Directory exifDirectory = metadata.getDirectory(ExifDirectory.class);
						Iterator tagIterator = exifDirectory.getTagIterator();
						while (tagIterator.hasNext()) {
							Tag object = (Tag) tagIterator.next();
							try {
								InformationUnit createInformationUnit = InfomngmntFactory.eINSTANCE.createInformationUnit();
								createInformationUnit.setType(object.getTagName());
								createInformationUnit.setStringValue(object.getDescription());
								exifData.add(createInformationUnit);
							} catch (MetadataException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					} catch (JpegProcessingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				InformationUnit childByType = InformationUtil.getChildByType(this.infoUnit, ImagePlugin.NODE_NAME_EXIF);
				if (isJpgeg && metadata != null) {
					if (childByType != null) {
						cc.append(new RemoveCommand(
								this.domain,
								childByType,
								InfomngmntPackage.Literals.INFORMATION_UNIT__CHILD_VALUES,
								childByType.getChildValues()));
					} else {
						childByType = InfomngmntFactory.eINSTANCE.createInformationUnit();
						childByType.setType(ImagePlugin.NODE_NAME_EXIF);
						cc.append(new AddCommand(
								this.domain,
								this.infoUnit.getChildValues(),
								Collections.singleton(childByType)));
					}
					cc.append(new AddCommand(
							this.domain,
							childByType.getChildValues(),
							exifData));
				} else {
					if (childByType != null) {
						cc.append(new RemoveCommand(
								this.domain,
								this.infoUnit,
								InfomngmntPackage.Literals.INFORMATION_UNIT__CHILD_VALUES,
								Collections.singleton(childByType)));
						
					}
				}
				
				InputStream is = new FileInputStream(this.file);
				// Reading src & height of the image:
				ImageData imageData = new ImageData(is);
				cc.append(new SetCommand(
						this.domain,
						this.widhtImageNode,
						InfomngmntPackage.Literals.INFORMATION_UNIT__LONG_VALUE,
						Long.valueOf(imageData.width)));
				
				cc.append(new SetCommand(
						this.domain,
						this.heightImageNode,
						InfomngmntPackage.Literals.INFORMATION_UNIT__LONG_VALUE,
						Long.valueOf(imageData.height)));
				
				cc.append(new SetCommand(
						this.domain,
						InformationUtil.getChildByType(this.infoUnit, ImagePlugin.ORIGINAL_FILEPATH),
						InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE,
						this.imagePath));
//				
				cc.setLabel("Set new image");
				UIUtil.getDisplay().asyncExec(new Runnable() {
					public void run() {
						LoadImageRunnable.this.domain.getCommandStack().execute(cc);
					}
				});
				is.close();
				return Status.OK_STATUS;
			} catch (IOException e) {
				return StatusCreator.newStatus("File not exisits or is not accessible");
			}
		}
		return StatusCreator.newStatus("File not exisits or is not accessible");
	}

	
	
	

	public void setImageNode(final InformationUnit infoUnit) {
		this.infoUnit = infoUnit;
		this.rawImageDataNode = InformationUtil.getChildByType(infoUnit, ImagePlugin.NODE_NAME_RAWDATA);
		this.widhtImageNode = InformationUtil.getChildByType(infoUnit, ImagePlugin.NODE_NAME_WIDTH);
		this.heightImageNode = InformationUtil.getChildByType(infoUnit, ImagePlugin.NODE_NAME_HEIGHT);
	}


	public void setFile(final File file) {
		this.file = file;
	}


}
