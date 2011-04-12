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
import org.eclipse.emf.edit.provider.IDisposable;
import org.eclipse.osgi.util.NLS;
import org.eclipse.remus.InfomngmntPackage;
import org.eclipse.remus.InformationUnit;
import org.eclipse.remus.common.ui.UIUtil;
import org.eclipse.remus.core.model.InformationStructureEdit;
import org.eclipse.remus.core.model.InformationStructureRead;
import org.eclipse.remus.core.services.IEditingHandler;
import org.eclipse.remus.ui.progress.CancelableRunnable;
import org.eclipse.remus.util.StatusCreator;
import org.eclipse.swt.graphics.ImageData;

import org.remus.infomngmnt.image.ImagePlugin;
import org.remus.infomngmnt.image.messages.Messages;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifDirectory;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class LoadImageRunnable extends CancelableRunnable {

	private String imagePath;
	private InformationUnit widhtImageNode;
	private InformationUnit heightImageNode;
	private EditingDomain domain;
	private boolean executeOnEditingDomain = true;
	private final boolean setName;

	public LoadImageRunnable(final boolean setName) {
		this.setName = setName;
	}

	public LoadImageRunnable() {
		this(false);
	}

	public void setDomain(final EditingDomain domain) {
		this.domain = domain;
	}

	private File file;
	private InformationUnit infoUnit;

	public void setImagePath(final String imagePath) {
		this.imagePath = imagePath;
	}

	@Override
	protected IStatus runCancelableRunnable(final IProgressMonitor monitor) {
		monitor.beginTask(Messages.LoadImageRunnable_CheckFilename, IProgressMonitor.UNKNOWN);
		this.file = new File(this.imagePath);
		if (this.file.exists() && this.file.isFile()) {
			monitor.beginTask(NLS.bind(Messages.LoadImageRunnable_ReadFile, this.file.getName()), (int) this.file
					.length());
			try {
				if (this.domain == null) {
					IEditingHandler service = ImagePlugin.getDefault().getServiceTracker()
							.getService(IEditingHandler.class);
					this.domain = service.createNewEditingDomain();
					ImagePlugin.getDefault().getServiceTracker().ungetService(service);
				}

				final CompoundCommand cc = new CompoundCommand();
				boolean isJpgeg = Pattern.compile("^.*\\.jpe?g$", Pattern.CASE_INSENSITIVE) //$NON-NLS-1$
						.matcher(this.imagePath).matches();
				Metadata metadata = null;
				List<InformationUnit> exifData = new ArrayList<InformationUnit>();
				InformationStructureEdit edit = InformationStructureEdit
						.newSession(ImagePlugin.TYPE_ID);
				InformationStructureRead read = InformationStructureRead.newSession(this.infoUnit);
				if (isJpgeg) {
					try {
						metadata = JpegMetadataReader.readMetadata(this.file);
						Directory exifDirectory = metadata.getDirectory(ExifDirectory.class);
						Iterator tagIterator = exifDirectory.getTagIterator();
						while (tagIterator.hasNext()) {
							Tag object = (Tag) tagIterator.next();
							try {
								InformationUnit exifItem = edit.createSubType(
										ImagePlugin.NODE_NAME_EXIF_ITEM, null);
								edit.setValue(exifItem, ImagePlugin.NODE_NAME_EXIF_KEY, object
										.getTagName());
								edit.setValue(exifItem, ImagePlugin.NODE_NAME_EXIF_VALUE, object
										.getDescription());
								exifData.add(exifItem);
							} catch (MetadataException e) {
								// do nothing.
							}
						}
					} catch (JpegProcessingException e) {
						// do nothing
					}
				}
				InformationUnit childByType = read.getChildByNodeId(ImagePlugin.NODE_NAME_EXIF);
				if (isJpgeg && metadata != null) {
					if (childByType != null) {
						cc.append(new RemoveCommand(this.domain, childByType,
								InfomngmntPackage.Literals.INFORMATION_UNIT__CHILD_VALUES,
								childByType.getChildValues()));
					} else {
						if (exifData.size() > 0) {
							childByType = edit.createSubType(ImagePlugin.NODE_NAME_EXIF, null);
							cc.append(new AddCommand(this.domain, this.infoUnit.getChildValues(),
									Collections.singleton(childByType)));
						}
					}
					if (exifData.size() > 0) {
						cc.append(new AddCommand(this.domain, childByType.getChildValues(),
								exifData));

					}
				} else {
					if (childByType != null) {
						cc.append(new RemoveCommand(this.domain, this.infoUnit,
								InfomngmntPackage.Literals.INFORMATION_UNIT__CHILD_VALUES,
								Collections.singleton(childByType)));

					}
				}

				InputStream is = new FileInputStream(this.file);
				// Reading src & height of the image:
				ImageData imageData = new ImageData(is);
				cc.append(SetCommand.create(this.domain, this.widhtImageNode,
						InfomngmntPackage.Literals.INFORMATION_UNIT__LONG_VALUE, Long
								.valueOf(imageData.width)));

				cc.append(SetCommand.create(this.domain, this.heightImageNode,
						InfomngmntPackage.Literals.INFORMATION_UNIT__LONG_VALUE, Long
								.valueOf(imageData.height)));

				if (this.setName) {
					cc.append(SetCommand.create(this.domain, read
							.getChildByNodeId(ImagePlugin.ORIGINAL_FILEPATH),
							InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE,
							this.imagePath));
				}
				//				
				cc.setLabel(Messages.LoadImageRunnable_SetNewImage);
				UIUtil.getDisplay().asyncExec(new Runnable() {
					public void run() {
						if (LoadImageRunnable.this.executeOnEditingDomain) {
							LoadImageRunnable.this.domain.getCommandStack().execute(cc);
						} else {
							cc.execute();
						}
						if (LoadImageRunnable.this.domain instanceof IDisposable) {
							((IDisposable) LoadImageRunnable.this.domain).dispose();
						}
					}
				});
				is.close();
				is = null;
				return Status.OK_STATUS;
			} catch (IOException e) {
				return StatusCreator.newStatus(Messages.LoadImageRunnable_FileNotAccessible);
			}
		}
		return StatusCreator.newStatus(Messages.LoadImageRunnable_FileNotAccessible);
	}

	public void setImageNode(final InformationUnit infoUnit) {
		this.infoUnit = infoUnit;
		InformationStructureRead read = InformationStructureRead.newSession(infoUnit);
		this.widhtImageNode = read.getChildByNodeId(ImagePlugin.NODE_NAME_WIDTH);
		this.heightImageNode = read.getChildByNodeId(ImagePlugin.NODE_NAME_HEIGHT);
	}

	public void setFile(final File file) {
		this.file = file;
	}

	/**
	 * @param executeOnEditingDomain
	 *            the executeOnEditingDomain to set
	 */
	public void setExecuteOnEditingDomain(final boolean executeOnEditingDomain) {
		this.executeOnEditingDomain = executeOnEditingDomain;
	}

}
