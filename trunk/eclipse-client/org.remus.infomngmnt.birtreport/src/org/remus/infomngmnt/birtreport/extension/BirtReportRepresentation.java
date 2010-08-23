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
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.remus.InformationUnit;
import org.eclipse.remus.core.extension.AbstractInformationRepresentation;
import org.eclipse.remus.core.model.InformationStructureRead;
import org.eclipse.remus.resources.util.ResourceUtil;

import org.remus.infomngmnt.birtreport.ReportActivator;

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
	 * #handleHtmlGeneration(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public InputStream handleHtmlGeneration(final IProgressMonitor monitor) throws CoreException {

		String createURL = createURL("output", WebViewer.HTML, getValue());
		StringReader stringReader = new StringReader(
				"<html><head><meta HTTP-EQUIV=\"REFRESH\" content=\"0; url="
						+ createURL
						+ "\"></head><body><div style=\"width:100%; height:100%; align:center; font-family:Tahoma,Arial; font-size:10px; font-weight:bold;\" id=\"progressMessage\" >Loading Report. This may take a while...</div></body></html>");
		return new ReaderInputStream(stringReader);
	}

	public static String createURL(final String servletName, final String format,
			final InformationUnit infoValue) {

		InformationStructureRead read = InformationStructureRead.newSession(infoValue);
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

		String reportLocation = ((IResource) infoValue.getAdapter(IFile.class)).getProject()
				.getLocation().append(ResourceUtil.BINARY_FOLDER).append(
						infoValue.getBinaryReferences().getProjectRelativePath()).toOSString();
		String encodedReportName = null;
		String encodedDocumentName = null;

		try {
			String report = URI.createFileURI(reportLocation).toString();
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
		return getBaseURL()
				+ servletName
				+ "?" //$NON-NLS-1$
				+ "__report=" + encodedReportName //$NON-NLS-1$
				+ ("output".equalsIgnoreCase(servletName) ? "" : "&__document=" + encodedDocumentName) //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				+ "&__format=" + format //$NON-NLS-1$
				+ "&__svg=" + String.valueOf(bSVGFlag) //$NON-NLS-1$
				+ (WebViewer.LOCALE_TABLE.containsKey(locale) ? "&__locale=" + WebViewer.LOCALE_TABLE.get(locale) : "") //$NON-NLS-1$ //$NON-NLS-2$
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
