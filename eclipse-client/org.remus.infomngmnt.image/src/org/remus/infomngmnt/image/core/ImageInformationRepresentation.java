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

package org.remus.infomngmnt.image.core;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;

import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.common.core.streams.StreamCloser;
import org.remus.infomngmnt.core.extension.AbstractInformationRepresentation;
import org.remus.infomngmnt.core.model.InformationUtil;
import org.remus.infomngmnt.core.model.StatusCreator;
import org.remus.infomngmnt.image.ImagePlugin;
import org.remus.infomngmnt.image.gef.ShapableInfoDelegate;
import org.remus.infomngmnt.jslib.rendering.FreemarkerRenderer;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class ImageInformationRepresentation extends
		AbstractInformationRepresentation {
	
	private String imageHref;
	
	public static final String IMAGE_SECTION_NAME = "imageSection"; //$NON-NLS-1$

	
	/**
	 * <p>
	 * Before we can extract the general values from the 
	 * information-object we have to extract the raw image data,
	 * and store them within the workspace. After a successful 
	 * creation of a new image file, we have to remember the 
	 * location of the extracted image which is linked within
	 * the generated html content.
	 * </p>
	 * {@inheritDoc}
	 * @see #imageHref
	 * @see #handleHtmlGeneration(IProgressMonitor)
	 */
	@Override
	public void handlePreBuild(final IProgressMonitor monitor) {
		InformationUnit rawDataNode = InformationUtil.getChildByType(getValue(), ImagePlugin.NODE_NAME_RAWDATA);
		if (rawDataNode != null && rawDataNode.getBinaryValue() != null) {
			monitor.setTaskName("Extracting image...");
		}
		InformationUnit origFileName = InformationUtil.getChildByType(getValue(), ImagePlugin.ORIGINAL_FILEPATH);
		if (origFileName != null) {
			String fileExtension = new Path(origFileName.getStringValue()).getFileExtension();
			IFile file = getBuildFolder().getFile(new Path(getValue().getId()).addFileExtension(fileExtension));
			this.imageHref = file.getLocation().toOSString();
			ByteArrayInputStream bais = new ByteArrayInputStream(rawDataNode.getBinaryValue());
			try {
				file.create(bais, true, monitor);
			} catch (CoreException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} finally {
				try {
					bais.close();
				} catch (IOException e) {
					// do nothing... we've done our best.
				}
			}
		}

	}
	

	/* (non-Javadoc)
	 * @see org.remus.infomngmnt.core.extension.AbstractInformationRepresentation#getAdditionalsForIndexing(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public String getAdditionalsForIndexing(final IProgressMonitor monitor)
			throws CoreException {
		StringBuilder sb = new StringBuilder();
		InformationUnit childByType = InformationUtil.getChildByType(getValue(),ImagePlugin.NODE_NAME_EXIF);
		EList<InformationUnit> exifData = childByType.getChildValues();
		for (InformationUnit informationUnit : exifData) {
			if (informationUnit.getStringValue() != null && informationUnit.getStringValue().length() > 0) {
				sb.append(informationUnit.getStringValue()).append(" ");
			}
		}
		return sb.toString();
	}

	/* (non-Javadoc)
	 * @see org.remus.infomngmnt.core.extension.AbstractInformationRepresentation#getBodyForIndexing(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public String getBodyForIndexing(final IProgressMonitor monitor)
			throws CoreException {
		StringBuilder sb = new StringBuilder();
		InformationUnit childByType = InformationUtil.getChildByType(getValue(),ImagePlugin.NODE_NAME_LINKS);
		EList<InformationUnit> comments = childByType.getChildValues();
		for (InformationUnit informationUnit : comments) {
			InformationUnit commentTextUnit = InformationUtil.getChildByType(informationUnit, ShapableInfoDelegate.TEXT);
			if (commentTextUnit.getStringValue() != null && commentTextUnit.getStringValue().length() > 0 ) {
				sb.append(commentTextUnit.getStringValue()).append(" ");
			}
		}
		return sb.toString();
	}
	
	@Override
	public boolean createFolderOnBuild() {
		return true;
	}

	

	/* (non-Javadoc)
	 * @see org.remus.infomngmnt.core.extension.AbstractInformationRepresentation#handleHtmlGeneration(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public InputStream handleHtmlGeneration(final IProgressMonitor monitor)
			throws CoreException {
		ByteArrayOutputStream returnValue = new ByteArrayOutputStream();
		InputStream templateIs = null;
		InputStream contentsIs = getFile().getContents();
		try {
			templateIs = FileLocator.openStream(
					Platform.getBundle(ImagePlugin.PLUGIN_ID), 
					new Path("template/htmlserialization.flt"), false);
			FreemarkerRenderer.getInstance().process(
					ImagePlugin.PLUGIN_ID,
					templateIs,
					contentsIs,
					returnValue, 
					Collections.<String,String> singletonMap("imageHref", URI.createFileURI(this.imageHref).toString()));
		} catch (IOException e) {
			throw new CoreException(StatusCreator.newStatus(
					"Error reading locations",e));
		} finally {
			StreamCloser.closeStreams(templateIs, contentsIs);
		}
		return new ByteArrayInputStream(returnValue.toByteArray());
	}

}
