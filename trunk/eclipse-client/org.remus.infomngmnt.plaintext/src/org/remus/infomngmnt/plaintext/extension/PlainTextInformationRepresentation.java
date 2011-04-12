/*******************************************************************************
 * Copyright (c) 2008 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/

package org.remus.infomngmnt.plaintext.extension;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.remus.common.core.streams.StreamCloser;
import org.eclipse.remus.core.model.InformationStructureRead;
import org.eclipse.remus.js.rendering.FreemarkerRenderer;
import org.eclipse.remus.util.StatusCreator;
import org.remus.infomngmnt.plaintext.Activator;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class PlainTextInformationRepresentation extends
		org.eclipse.remus.core.extension.AbstractInformationRepresentation {

	@Override
	public void handlePostBuild(final IFile derivedFile,
			final IProgressMonitor monitor) throws CoreException {
		// do nothing

	}

	@Override
	public InputStream handleHtmlGeneration(final IProgressMonitor monitor)
			throws CoreException {
		ByteArrayOutputStream returnValue = new ByteArrayOutputStream();
		InputStream templateIs = null;
		InputStream contentsIs = getFile().getContents();
		InformationStructureRead read = InformationStructureRead
				.newSession(getValue());
		try {
			templateIs = FileLocator.openStream(Platform
					.getBundle(Activator.PLUGIN_ID), new Path(
					"$nl$/template/htmlserialization.flt"), true); //$NON-NLS-1$
			FreemarkerRenderer.getInstance().process(Activator.PLUGIN_ID,
					templateIs, returnValue, null,
					read.getContentsAsStrucuturedMap(),
					read.getDynamicContentAsStructuredMap());
		} catch (IOException e) {
			throw new CoreException(StatusCreator.newStatus(
					"Error reading locations", e)); //$NON-NLS-1$
		} finally {
			StreamCloser.closeStreams(templateIs, contentsIs);
		}
		return new ByteArrayInputStream(returnValue.toByteArray());
	}

}
