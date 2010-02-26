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

import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.common.core.streams.StreamCloser;
import org.remus.infomngmnt.core.extension.AbstractInformationRepresentation;
import org.remus.infomngmnt.jslib.rendering.FreemarkerRenderer;
import org.remus.infomngmnt.sourcecode.PreferenceInitializer;
import org.remus.infomngmnt.sourcecode.SourceCodePlugin;
import org.remus.infomngmnt.util.InformationUtil;
import org.remus.infomngmnt.util.StatusCreator;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class SourceCodeInformationRepresentation extends
AbstractInformationRepresentation {

	private static final String SRCCODE_SECTION_ID = "srccode";
	private final IPreferenceStore preferenceStore;

	/**
	 * 
	 */
	public SourceCodeInformationRepresentation() {
		this.preferenceStore = SourceCodePlugin.getDefault().getPreferenceStore();
	}

	/* (non-Javadoc)
	 * @see org.remus.infomngmnt.core.extension.AbstractInformationRepresentation#getAdditionalsForIndexing(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public String getAdditionalsForIndexing(final IProgressMonitor monitor)
	throws CoreException {
		return null;
	}

	@Override
	public void handlePreBuild(final IProgressMonitor monitor) {

	}

	/* (non-Javadoc)
	 * @see org.remus.infomngmnt.core.extension.AbstractInformationRepresentation#getBodyForIndexing(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public String getBodyForIndexing(final IProgressMonitor monitor)
	throws CoreException {
		return getValue().getStringValue();
	}

	/* (non-Javadoc)
	 * @see org.remus.infomngmnt.core.extension.AbstractInformationRepresentation#getTitleForIndexing(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public String getTitleForIndexing(final IProgressMonitor monitor)
	throws CoreException {
		return getValue().getLabel();
	}

	/* (non-Javadoc)
	 * @see org.remus.infomngmnt.core.extension.AbstractInformationRepresentation#handleHtmlGeneration(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public InputStream handleHtmlGeneration(final IProgressMonitor monitor)
	throws CoreException {
		Map<String, String> additionals = new HashMap<String, String>();
		additionals.put("formattedSrc", new String(getHtmlSource().toByteArray()));
		ByteArrayOutputStream returnValue = new ByteArrayOutputStream();
		InputStream contentsIs = getFile().getContents();
		InputStream templateIs = null;
		try {
			templateIs = FileLocator.openStream(
					Platform.getBundle(SourceCodePlugin.PLUGIN_ID), 
					new Path("template/htmlserialization.flt"), false);
			FreemarkerRenderer.getInstance().process(
					SourceCodePlugin.PLUGIN_ID,
					templateIs,
					contentsIs,
					returnValue, additionals);
					contentsIs.close();
					templateIs.close();
		} catch (IOException e) {
			throw new CoreException(StatusCreator.newStatus(
					"Error reading locations",e));
		} finally {
			StreamCloser.closeStreams(templateIs, contentsIs);
		}
		return new ByteArrayInputStream(returnValue.toByteArray());
	}

	private ByteArrayOutputStream getHtmlSource() {
		ByteArrayOutputStream targetStream = new ByteArrayOutputStream();
		if (getValue().getStringValue() != null && getValue().getStringValue().length() > 0) {
			InformationUnit childByType = InformationUtil.getChildByType(getValue(), SourceCodePlugin.SRCTYPE_NAME);
			String type = SourceCodePlugin.getDefault().getSourceTypes().get(childByType.getStringValue());
			ReaderLineSource rls = new ReaderLineSource(
					new StringReader(getValue().getStringValue() == null ? "" : getValue().getStringValue()));
			Writer commonWriter = null;
			commonWriter = new OutputStreamWriter(
					targetStream, Charset.forName("UTF-8"));


			ParserFactory pf = ColorerPlugin.getDefaultPF();
			HTMLGenerator hg = new HTMLGenerator(pf, rls, this.preferenceStore.getString(PreferenceInitializer.COLOR_SCHEME));

			try {
				hg.generate(commonWriter, commonWriter,
						"tmp." + type, this.preferenceStore.getBoolean(PreferenceInitializer.LINE_NUMBERS),
						false, false, false);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return targetStream;
	}

}
