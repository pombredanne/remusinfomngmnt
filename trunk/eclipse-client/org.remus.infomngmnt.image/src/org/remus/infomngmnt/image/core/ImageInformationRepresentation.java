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
import org.eclipse.emf.common.util.URI;
import org.eclipse.remus.core.extension.AbstractInformationRepresentation;
import org.eclipse.remus.js.rendering.FreemarkerRenderer;
import org.eclipse.remus.resources.util.ResourceUtil;
import org.eclipse.remus.util.StatusCreator;
import org.remus.infomngmnt.image.ImagePlugin;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class ImageInformationRepresentation extends
		AbstractInformationRepresentation {

	private String imageHref;

	public static final String IMAGE_SECTION_NAME = "imageSection"; //$NON-NLS-1$

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.core.extension.AbstractInformationRepresentation
	 * #handleHtmlGeneration(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public InputStream handleHtmlGeneration(final IProgressMonitor monitor)
			throws CoreException {
		ByteArrayOutputStream returnValue = new ByteArrayOutputStream();
		imageHref = getFile()
				.getProject()
				.getLocation()
				.append(ResourceUtil.BINARY_FOLDER)
				.append(getValue().getBinaryReferences()
						.getProjectRelativePath()).toOSString();
		InputStream templateIs = null;
		InputStream contentsIs = getFile().getContents();
		try {
			templateIs = FileLocator.openStream(Platform
					.getBundle(ImagePlugin.PLUGIN_ID), new Path(
					"$nl$/template/htmlserialization.flt"), true); //$NON-NLS-1$
			FreemarkerRenderer.getInstance().process(
					ImagePlugin.PLUGIN_ID,
					templateIs,
					contentsIs,
					returnValue,
					Collections.<String, String> singletonMap(
							"imageHref", URI.createFileURI( //$NON-NLS-1$
									imageHref).toString()));
		} catch (IOException e) {
			throw new CoreException(StatusCreator.newStatus(
					"Error reading locations", e)); //$NON-NLS-1$
		} finally {
			org.eclipse.remus.common.core.streams.StreamCloser.closeStreams(
					templateIs, contentsIs);
		}
		return new ByteArrayInputStream(returnValue.toByteArray());
	}

}
