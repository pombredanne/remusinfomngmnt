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

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.Charset;

import net.sf.colorer.ParserFactory;
import net.sf.colorer.eclipse.ColorerPlugin;
import net.sf.colorer.impl.ReaderLineSource;
import net.sf.colorer.viewer.HTMLGenerator;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.preference.IPreferenceStore;

import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.common.core.streams.StringOutputStream;
import org.remus.infomngmnt.core.extension.AbstractInformationRepresentation;
import org.remus.infomngmnt.core.model.InformationUtil;
import org.remus.infomngmnt.jslib.HtmlSnippets;
import org.remus.infomngmnt.jslib.JavaScriptSnippets;
import org.remus.infomngmnt.jslib.StyleProvider;
import org.remus.infomngmnt.sourcecode.PreferenceInitializer;
import org.remus.infomngmnt.sourcecode.SourceCodePlugin;

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
	public String getAdditionalsForIndexing(IProgressMonitor monitor)
	throws CoreException {
		return null;
	}

	@Override
	public void handlePreBuild(IProgressMonitor monitor) {

	}

	/* (non-Javadoc)
	 * @see org.remus.infomngmnt.core.extension.AbstractInformationRepresentation#getBodyForIndexing(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public String getBodyForIndexing(IProgressMonitor monitor)
	throws CoreException {
		return getValue().getStringValue();
	}

	/* (non-Javadoc)
	 * @see org.remus.infomngmnt.core.extension.AbstractInformationRepresentation#getTitleForIndexing(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public String getTitleForIndexing(IProgressMonitor monitor)
	throws CoreException {
		return getValue().getLabel();
	}

	/* (non-Javadoc)
	 * @see org.remus.infomngmnt.core.extension.AbstractInformationRepresentation#handleHtmlGeneration(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public String handleHtmlGeneration(IProgressMonitor monitor)
	throws CoreException {
		StringWriter sw = new StringWriter();
		sw.append(HtmlSnippets.HTML_HEAD)
		.append(StyleProvider.STYLE_DEFINITION_START)
		.append(StyleProvider.getSystemFontDefinition())
		.append(StyleProvider.STYLE_DEFINITION_END)
		.append(JavaScriptSnippets.SCRIPT_SRC_IMAGES_JS)
		.append(JavaScriptSnippets.SCRIPT_SRC_ROUNDED_CORNERS_JS)
		.append(JavaScriptSnippets.SECTION_BOX_DEFINITION(SRCCODE_SECTION_ID, 5));

		sw.append(HtmlSnippets.HTML_HEAD_END_BODY_START)
		//		.append("<p style=\"text-align:center;\">\r\n")
		//		.append("<a href=\"").append(getValue().getStringValue())
		//		.append("\" target=\"_blank\">")
		//		.append(getValue().getStringValue())
		//		.append("</a>")
		//		.append("</p>\r\n")
		.append("<div>");
		sw.append(HtmlSnippets.CREATE_SECTION_BOX("Source", SRCCODE_SECTION_ID));
		sw.append("<p><pre>\r\n")
		.append(getHtmlSource())
		.append("</pre></div>");

		sw.append(HtmlSnippets.HTML_BODY_END_HTML_END);
		return sw.toString();

	}

	private String getHtmlSource() {
		if (getValue().getStringValue() != null && getValue().getStringValue().length() > 0) {
			InformationUnit childByType = InformationUtil.getChildByType(getValue(), SourceCodePlugin.SRCTYPE_NAME);
			String type = SourceCodePlugin.getDefault().getSourceTypes().get(childByType.getStringValue());
			ReaderLineSource rls = new ReaderLineSource(
					new StringReader(getValue().getStringValue() == null ? "" : getValue().getStringValue()));
			StringOutputStream targetStream = new StringOutputStream() {
				@Override
				public void close() {
					// no close
				}
			};
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
			return targetStream.toString();
		}
		return "";
	}

}
