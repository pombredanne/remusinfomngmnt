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
import org.remus.infomngmnt.image.ImagePlugin;
import org.remus.infomngmnt.image.comments.ShapableInfoDelegate;
import org.remus.infomngmnt.jslib.rendering.FreemarkerRenderer;
import org.remus.infomngmnt.resources.util.ResourceUtil;
import org.remus.infomngmnt.util.InformationUtil;
import org.remus.infomngmnt.util.StatusCreator;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class ImageInformationRepresentation extends AbstractInformationRepresentation {

	private String imageHref;

	public static final String IMAGE_SECTION_NAME = "imageSection"; //$NON-NLS-1$

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.core.extension.AbstractInformationRepresentation
	 * #getAdditionalsForIndexing(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public String getAdditionalsForIndexing(final IProgressMonitor monitor) throws CoreException {
		StringBuilder sb = new StringBuilder();
		InformationUnit childByType = InformationUtil.getChildByType(getValue(),
				ImagePlugin.NODE_NAME_EXIF);
		if (childByType != null) {
			EList<InformationUnit> exifData = childByType.getChildValues();
			for (InformationUnit informationUnit : exifData) {
				if (informationUnit.getStringValue() != null
						&& informationUnit.getStringValue().length() > 0) {
					sb.append(informationUnit.getStringValue()).append(" ");
				}
			}
		}
		return sb.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.core.extension.AbstractInformationRepresentation
	 * #getBodyForIndexing(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public String getBodyForIndexing(final IProgressMonitor monitor) throws CoreException {
		StringBuilder sb = new StringBuilder();
		InformationUnit childByType = InformationUtil.getChildByType(getValue(),
				ImagePlugin.NODE_NAME_LINKS);
		EList<InformationUnit> comments = childByType.getChildValues();
		for (InformationUnit informationUnit : comments) {
			InformationUnit commentTextUnit = InformationUtil.getChildByType(informationUnit,
					ShapableInfoDelegate.TEXT);
			if (commentTextUnit.getStringValue() != null
					&& commentTextUnit.getStringValue().length() > 0) {
				sb.append(commentTextUnit.getStringValue()).append(" ");
			}
		}
		return sb.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.core.extension.AbstractInformationRepresentation
	 * #handleHtmlGeneration(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public InputStream handleHtmlGeneration(final IProgressMonitor monitor) throws CoreException {
		ByteArrayOutputStream returnValue = new ByteArrayOutputStream();
		this.imageHref = getFile().getProject().getLocation().append(ResourceUtil.BINARY_FOLDER)
				.append(getValue().getBinaryReferences().getProjectRelativePath()).toOSString();
		InputStream templateIs = null;
		InputStream contentsIs = getFile().getContents();
		try {
			templateIs = FileLocator.openStream(Platform.getBundle(ImagePlugin.PLUGIN_ID),
					new Path("template/htmlserialization.flt"), false);
			FreemarkerRenderer.getInstance().process(
					ImagePlugin.PLUGIN_ID,
					templateIs,
					contentsIs,
					returnValue,
					Collections.<String, String> singletonMap("imageHref", URI.createFileURI(
							this.imageHref).toString()));
		} catch (IOException e) {
			throw new CoreException(StatusCreator.newStatus("Error reading locations", e));
		} finally {
			StreamCloser.closeStreams(templateIs, contentsIs);
		}
		return new ByteArrayInputStream(returnValue.toByteArray());
	}

}
