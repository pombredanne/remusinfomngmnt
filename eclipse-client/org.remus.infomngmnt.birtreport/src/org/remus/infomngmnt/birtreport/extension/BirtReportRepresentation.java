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

package org.remus.infomngmnt.birtreport.extension;

import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.birt.report.viewer.ViewerPlugin;
import org.eclipse.birt.report.viewer.utilities.WebViewer;
import org.eclipse.birt.report.viewer.utilities.WebappAccessor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;

import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.birtreport.ReportActivator;
import org.remus.infomngmnt.common.core.streams.ReaderInputStream;
import org.remus.infomngmnt.core.extension.AbstractInformationRepresentation;
import org.remus.infomngmnt.core.model.InformationStructureRead;
import org.remus.infomngmnt.resources.util.ResourceUtil;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class BirtReportRepresentation extends AbstractInformationRepresentation {

	/**
	 * 
	 */
	public BirtReportRepresentation() {
		// TODO Auto-generated constructor stub
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
		// reports will not be indexed.
		return null;
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
		InformationStructureRead read = InformationStructureRead.newSession(getValue());
		Map<String, String> params = new HashMap<String, String>();
		EList<InformationUnit> dynamicList = read.getDynamicList(ReportActivator.NODE_NAME_PARAMS);
		for (InformationUnit informationUnit : dynamicList) {
			InformationStructureRead structureRead = InformationStructureRead.newSession(
					informationUnit, ReportActivator.INFOTYPE_ID);
			String name = (String) structureRead
					.getValueByNodeId(ReportActivator.NODE_NAME_PARAM_NAME);
			InformationStructureRead valueRead = InformationStructureRead.newSession(structureRead
					.getChildByNodeId(ReportActivator.NODE_NAME_PARAM_NAME),
					ReportActivator.INFOTYPE_ID);
			String value = (String) valueRead
					.getValueByNodeId(ReportActivator.NODE_NAME_PARAM_VALUE);
			params.put(name, value);
		}

		String reportLocation = getFile().getProject().getLocation().append(
				ResourceUtil.BINARY_FOLDER).append(
				getValue().getBinaryReferences().getProjectRelativePath()).toOSString();

		String createURL = createURL("run", URI.createFileURI(reportLocation).toString(),
				WebViewer.HTML, params);
		StringReader stringReader = new StringReader(
				"<html><head><meta HTTP-EQUIV=\"REFRESH\" content=\"0; url=" + createURL
						+ "\"></head><body></body></html>");
		return new ReaderInputStream(stringReader);
	}

	private static String createURL(final String servletName, final String report,
			final String format, final Map<String, String> params) {
		String encodedReportName = null;
		String encodedDocumentName = null;

		try {
			encodedReportName = URLEncoder.encode(report, "utf-8"); //$NON-NLS-1$
		} catch (UnsupportedEncodingException e) {
			// Do nothing
		}

		String locale = ViewerPlugin.getDefault().getPluginPreferences().getString(
				WebViewer.USER_LOCALE);

		String svgFlag = ViewerPlugin.getDefault().getPluginPreferences().getString(
				WebViewer.SVG_FLAG);
		boolean bSVGFlag = false;
		if ("true".equalsIgnoreCase(svgFlag)) //$NON-NLS-1$
		{
			bSVGFlag = true;
		}

		String masterPageContent = ViewerPlugin.getDefault().getPluginPreferences().getString(
				WebViewer.MASTER_PAGE_CONTENT);
		boolean bMasterPageContent = true;
		if ("false".equalsIgnoreCase(masterPageContent)) //$NON-NLS-1$
		{

			bMasterPageContent = false;
		}

		// So far, only report name is encoded as utf-8 format
		return getBaseURL() + servletName
				+ "?" //$NON-NLS-1$
				+ "__report=" + encodedReportName //$NON-NLS-1$
				+ ("run".equalsIgnoreCase(servletName) ? "" : "&__document=" + encodedDocumentName) //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				+ "&__format=" + format //$NON-NLS-1$
				+ "&__svg=" + String.valueOf(bSVGFlag) //$NON-NLS-1$
				+ (WebViewer.LocaleTable.containsKey(locale) ? "&__locale=" + WebViewer.LocaleTable.get(locale) : "") //$NON-NLS-1$ //$NON-NLS-2$
				+ "&__designer=true" //$NON-NLS-1$
				+ "&__masterpage=" + String.valueOf(bMasterPageContent) + getParameters(params); //$NON-NLS-1$

	}

	private static String getParameters(final Map<String, String> params) {
		StringWriter sw = new StringWriter();
		Set<String> keySet = params.keySet();
		for (String string : keySet) {
			try {
				sw.append("&").append(URLEncoder.encode(string, "UTF-8")).append("=").append(
						URLEncoder.encode(params.get(string != null ? string : ""), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				// do nothing
			}

		}
		return sw.toString();
	}

	private static String getBaseURL() {
		return "http://" + WebappAccessor.getHost() + ":" //$NON-NLS-1$ //$NON-NLS-2$
				+ WebappAccessor.getPort(ViewerPlugin.WEBAPP_CONTEXT) + "/viewer/"; //$NON-NLS-1$
	}

}
