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

package org.remus.infomngmnt.sourcecode.core;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
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
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.remus.InformationUnit;
import org.eclipse.remus.common.core.streams.StreamCloser;
import org.eclipse.remus.core.extension.AbstractInformationRepresentation;
import org.eclipse.remus.js.rendering.FreemarkerRenderer;
import org.eclipse.remus.util.InformationUtil;
import org.eclipse.remus.util.StatusCreator;
import org.remus.infomngmnt.sourcecode.PreferenceInitializer;
import org.remus.infomngmnt.sourcecode.SourceCodePlugin;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class SourceCodeInformationRepresentation extends
		AbstractInformationRepresentation {

	private static final String SRCCODE_SECTION_ID = "srccode"; //$NON-NLS-1$
	private final IPreferenceStore preferenceStore;

	/**
	 * 
	 */
	public SourceCodeInformationRepresentation() {
		preferenceStore = SourceCodePlugin.getDefault().getPreferenceStore();
	}

	@Override
	public void handlePreBuild(final IProgressMonitor monitor) {

	}

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
		Map<String, String> additionals = new HashMap<String, String>();
		additionals.put(
				"formattedSrc", new String(getHtmlSource().toByteArray())); //$NON-NLS-1$
		ByteArrayOutputStream returnValue = new ByteArrayOutputStream();
		InputStream contentsIs = getFile().getContents();
		InputStream templateIs = null;
		try {
			templateIs = FileLocator.openStream(Platform
					.getBundle(SourceCodePlugin.PLUGIN_ID), new Path(
					"$nl$/template/htmlserialization.flt"), true); //$NON-NLS-1$
			FreemarkerRenderer.getInstance().process(
					SourceCodePlugin.PLUGIN_ID, templateIs, contentsIs,
					returnValue, additionals);
			contentsIs.close();
			templateIs.close();
		} catch (IOException e) {
			throw new CoreException(StatusCreator.newStatus(
					"Error reading locations", e)); //$NON-NLS-1$
		} finally {
			StreamCloser.closeStreams(templateIs, contentsIs);
		}
		return new ByteArrayInputStream(returnValue.toByteArray());
	}

	private ByteArrayOutputStream getHtmlSource() {
		ByteArrayOutputStream targetStream = new ByteArrayOutputStream();
		if (getValue().getStringValue() != null
				&& getValue().getStringValue().length() > 0) {
			InformationUnit childByType = InformationUtil.getChildByType(
					getValue(), SourceCodePlugin.SRCTYPE_NAME);
			String type = SourceCodePlugin.getDefault().getSourceTypes()
					.get(childByType.getStringValue());
			ReaderLineSource rls = new ReaderLineSource(
					new StringReader(
							getValue().getStringValue() == null ? "" : getValue().getStringValue())); //$NON-NLS-1$
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
