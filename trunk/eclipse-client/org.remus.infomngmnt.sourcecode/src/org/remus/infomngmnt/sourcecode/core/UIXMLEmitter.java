/*******************************************************************************
 * Copyright (c) 2011 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/
package org.remus.infomngmnt.sourcecode.core;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import net.sf.colorer.ParserFactory;
import net.sf.colorer.eclipse.ColorerPlugin;
import net.sf.colorer.impl.ReaderLineSource;
import net.sf.colorer.viewer.HTMLGenerator;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.remus.InformationUnit;
import org.eclipse.remus.common.core.streams.StreamCloser;
import org.eclipse.remus.core.extension.IEmitter;
import org.eclipse.remus.core.model.InformationStructureRead;
import org.eclipse.remus.js.rendering.FreemarkerRenderer;
import org.eclipse.remus.util.StatusCreator;
import org.remus.infomngmnt.emitter.uixml.UiXml;
import org.remus.infomngmnt.sourcecode.PreferenceInitializer;
import org.remus.infomngmnt.sourcecode.SourceCodePlugin;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class UIXMLEmitter implements IEmitter {

	private IPreferenceStore preferenceStore;

	/**
	 * 
	 */
	public UIXMLEmitter() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.remus.core.extension.IEmitter#preEmitt()
	 */
	public void preEmitt() {
		preferenceStore = SourceCodePlugin.getDefault().getPreferenceStore();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.remus.core.extension.IEmitter#emitt(org.eclipse.remus.
	 * InformationUnit)
	 */
	public Object emitt(InformationUnit element) throws CoreException {
		Map<String, String> additionals = new HashMap<String, String>();
		InformationStructureRead read = InformationStructureRead
				.newSession(element);
		String source = (String) read
				.getValueByNodeId(SourceCodePlugin.TYPE_ID);
		String sourceType = (String) read
				.getValueByNodeId(SourceCodePlugin.SRCTYPE_NAME);
		additionals
				.put("formattedSrc", new String(getHtmlSource(source, sourceType).toByteArray())); //$NON-NLS-1$
		ByteArrayOutputStream returnValue = new ByteArrayOutputStream();
		InputStream templateIs = null;
		try {
			templateIs = FileLocator.openStream(Platform
					.getBundle(SourceCodePlugin.PLUGIN_ID), new Path(
					"template/uiserialization.flt"), false); //$NON-NLS-1$
			FreemarkerRenderer.getInstance().process(
					SourceCodePlugin.PLUGIN_ID, templateIs, returnValue,
					additionals, read.getContentsAsStrucuturedMap(),
					read.getDynamicContentAsStructuredMap());
			templateIs.close();
		} catch (IOException e) {
			throw new CoreException(StatusCreator.newStatus(
					"Error reading locations", e)); //$NON-NLS-1$
		} finally {
			StreamCloser.closeStreams(templateIs);
		}
		try {
			UiXml uixml = new UiXml();
			uixml.setXmlString(new String(returnValue.toByteArray(), "UTF-8"));
			return uixml;
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.remus.core.extension.IEmitter#postEmitt()
	 */
	public void postEmitt() {
		// TODO Auto-generated method stub

	}

	private ByteArrayOutputStream getHtmlSource(String content, String type) {
		ByteArrayOutputStream targetStream = new ByteArrayOutputStream();
		if (content != null && content.length() > 0) {
			ReaderLineSource rls = new ReaderLineSource(new StringReader(
					content == null ? "" : content)); //$NON-NLS-1$
			Writer commonWriter = null;
			commonWriter = new OutputStreamWriter(targetStream,
					Charset.forName("UTF-8")); //$NON-NLS-1$

			ParserFactory pf = ColorerPlugin.getDefaultPF();
			HTMLGenerator hg = new HTMLGenerator(pf, rls,
					preferenceStore
							.getString(PreferenceInitializer.COLOR_SCHEME));

			try {
				hg.generate(
						commonWriter,
						commonWriter,
						"tmp." + type, preferenceStore //$NON-NLS-1$
								.getBoolean(PreferenceInitializer.LINE_NUMBERS),
						false, false, false);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return targetStream;
	}
}
