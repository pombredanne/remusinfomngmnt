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

package org.remus.infomngmnt.richtext.presentation;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.remus.common.core.streams.StreamCloser;
import org.eclipse.remus.core.extension.AbstractInformationRepresentation;
import org.eclipse.remus.js.rendering.FreemarkerRenderer;
import org.eclipse.remus.util.StatusCreator;
import org.remus.infomngmnt.richtext.RichTextPlugin;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class RichTextPresentation extends AbstractInformationRepresentation {

	@Override
	public InputStream handleHtmlGeneration(final IProgressMonitor monitor)
			throws CoreException {
		ByteArrayOutputStream returnValue = new ByteArrayOutputStream();
		InputStream templateIs = null;
		InputStream contentsIs = getFile().getContents();
		try {
			templateIs = FileLocator.openStream(Platform
					.getBundle(RichTextPlugin.PLUGIN_ID), new Path(
					"$nl$/template/htmlserialization.flt"), true); //$NON-NLS-1$
			FreemarkerRenderer.getInstance().process(RichTextPlugin.PLUGIN_ID,
					templateIs, contentsIs, returnValue, null);
		} catch (IOException e) {
			throw new CoreException(StatusCreator.newStatus(
					"Error reading locations", e)); //$NON-NLS-1$
		} finally {
			StreamCloser.closeStreams(templateIs, contentsIs);
		}
		return new ByteArrayInputStream(returnValue.toByteArray());
	}

}
