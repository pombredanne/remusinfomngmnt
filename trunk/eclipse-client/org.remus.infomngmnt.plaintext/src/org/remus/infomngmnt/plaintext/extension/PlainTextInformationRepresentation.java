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

import org.remus.infomngmnt.common.core.streams.StreamCloser;
import org.remus.infomngmnt.core.extension.AbstractInformationRepresentation;
import org.remus.infomngmnt.core.model.StatusCreator;
import org.remus.infomngmnt.jslib.rendering.FreemarkerRenderer;
import org.remus.infomngmnt.plaintext.Activator;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class PlainTextInformationRepresentation extends
AbstractInformationRepresentation {



	@Override
	public void handlePostBuild(final IFile derivedFile, final IProgressMonitor monitor)
	throws CoreException {
		// do nothing

	}

	@Override
	public InputStream handleHtmlGeneration(final IProgressMonitor monitor)
	throws CoreException {
		ByteArrayOutputStream returnValue = new ByteArrayOutputStream();
		InputStream templateIs = null;
		InputStream contentsIs = getFile().getContents();
		try {
			templateIs = FileLocator.openStream(
					Platform.getBundle(Activator.PLUGIN_ID), 
					new Path("template/htmlserialization.flt"), false);
			FreemarkerRenderer.getInstance().process(
					Activator.PLUGIN_ID,
					templateIs,
					contentsIs,
					returnValue, null);
		} catch (IOException e) {
			throw new CoreException(StatusCreator.newStatus(
					"Error reading locations",e));
		} finally {
			StreamCloser.closeStreams(templateIs, contentsIs);
		}
		return new ByteArrayInputStream(returnValue.toByteArray());
	}

	@Override
	public String getAdditionalsForIndexing(final IProgressMonitor monitor)
	throws CoreException {
		return null;
	}

	@Override
	public String getBodyForIndexing(final IProgressMonitor monitor)
	throws CoreException {

		return getValue().getStringValue();
	}

	@Override
	public String getTitleForIndexing(final IProgressMonitor monitor)
	throws CoreException {
		return getValue().getLabel();
	}

}
