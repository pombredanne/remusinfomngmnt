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
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;

import org.remus.infomngmnt.common.core.indexing.HTMLStripReader;
import org.remus.infomngmnt.common.core.streams.StreamCloser;
import org.remus.infomngmnt.core.extension.AbstractInformationRepresentation;
import org.remus.infomngmnt.jslib.rendering.FreemarkerRenderer;
import org.remus.infomngmnt.richtext.RichTextPlugin;
import org.remus.infomngmnt.util.StatusCreator;


/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class RichTextPresentation extends AbstractInformationRepresentation {

	@Override
	public String getBodyForIndexing(final IProgressMonitor monitor)
			throws CoreException {
		StringWriter sw = new StringWriter();
		if (getValue().getStringValue() != null) {
			Reader in = new HTMLStripReader(new StringReader(getValue().getStringValue()));
			int ch;
			try {
				while ( (ch=in.read()) != -1 ) {
					sw.append((char)ch);
				}
			} catch (IOException e) {
				// do nothing.. we've done our best.
			}
		}
		return sw.toString();
	}

	@Override
	public InputStream handleHtmlGeneration(final IProgressMonitor monitor)
			throws CoreException {
		ByteArrayOutputStream returnValue = new ByteArrayOutputStream();
		InputStream templateIs = null;
		InputStream contentsIs = getFile().getContents();
		try {
			templateIs = FileLocator.openStream(
					Platform.getBundle(RichTextPlugin.PLUGIN_ID), 
					new Path("template/htmlserialization.flt"), false);
			FreemarkerRenderer.getInstance().process(
					RichTextPlugin.PLUGIN_ID,
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

}
