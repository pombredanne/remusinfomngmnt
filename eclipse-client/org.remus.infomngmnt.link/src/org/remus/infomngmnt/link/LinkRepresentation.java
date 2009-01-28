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

package org.remus.infomngmnt.link;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;

import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.core.extension.AbstractInformationRepresentation;
import org.remus.infomngmnt.core.model.InformationUtil;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class LinkRepresentation extends AbstractInformationRepresentation {

	public static final String SCREENSHOT_TYPE = "SCREENSHOT_TYPE"; //$NON-NLS-1$
	public static final String INDEXWEBPAGE_TYPE = "INDEXWEBPAGE_TYPE"; //$NON-NLS-1$
	public static final String INDEXWEBPAGECONTENT_TYPE = "INDEXWEBPAGECONTENT_TYPE"; //$NON-NLS-1$
	public static final String WEBSHOT_SECTION_ID = "webShotSection"; //$NON-NLS-1$
	private String imageHref;

	/**
	 * 
	 */
	public LinkRepresentation() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handlePreBuild(final IProgressMonitor monitor) {
		InformationUnit rawDataNode = InformationUtil.getChildByType(getValue(), LinkActivator.NODE_WEBSHOTIMAGE_DATA);
		this.imageHref = getWebShotFile().getLocation().toOSString();
		ByteArrayInputStream bais = new ByteArrayInputStream(rawDataNode.getBinaryValue());
		try {
			if (getWebShotFile().exists()) {
				getWebShotFile().setContents(bais, true,false, monitor);
			} else {
				getWebShotFile().create(bais, true, monitor);
			}
		} catch (CoreException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			try {
				bais.close();
			} catch (IOException e) {
				// do nothing... we've done our best.
			}
		}
		
	}

	@Override
	public boolean createFolderOnBuild() {
		return true;
	}
	
	private IFile getWebShotFile() {
		IFile file = getBuildFolder().getFile("webshot.png");
		return file;
	}

	/* (non-Javadoc)
	 * @see org.remus.infomngmnt.core.extension.AbstractInformationRepresentation#getAdditionalsForIndexing(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public String getAdditionalsForIndexing(final IProgressMonitor monitor)
	throws CoreException {
		InformationUnit childByType = InformationUtil.getChildByType(getValue(), LinkActivator.NODE_INDEX);
		if (childByType.eIsSet(InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE)) {
			return childByType.getStringValue();
		}
		return null;
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
		//boolean renderWebShot = InformationUtil.getChildByType(getValue(), SCREENSHOT_TYPE).isBoolValue();
		StringWriter sw = new StringWriter();
//		sw.append(HtmlSnippets.HTML_HEAD)
//		.append(StyleProvider.STYLE_DEFINITION_START)
//		.append(StyleProvider.getSystemFontDefinition())
//		.append(StyleProvider.STYLE_DEFINITION_END)
//		.append(JavaScriptSnippets.SCRIPT_SRC_IMAGES_JS)
//		.append(JavaScriptSnippets.SCRIPT_SRC_ROUNDED_CORNERS_JS);
//		if (renderWebShot) {
//			sw.append(JavaScriptSnippets.SECTION_BOX_DEFINITION(WEBSHOT_SECTION_ID, 5));
//		}
		//sw.append(HtmlSnippets.HTML_HEAD_END_BODY_START)
		sw.append("<p style=\"text-align:center;\">\r\n")
		.append("<a href=\"").append(getValue().getStringValue())
		.append("\" target=\"_blank\">")
		.append(getValue().getStringValue())
		.append("</a>")
		.append("</p>\r\n");
		if (true) {
			sw.append("<div>");
			//sw.append(HtmlSnippets.CREATE_SECTION_BOX("Webshot", WEBSHOT_SECTION_ID));
			sw.append("<p>\r\n")
			.append("<a href=\"javascript:fit2Page(\'")
			.append(getValue().getId())
			.append("\');\">Fit</a><br> <img name=\"")
			.append(getValue().getId())
			.append("\" src=\"")
			.append(URI.createFileURI(getWebShotFile().getLocation().toOSString()).toString())
			.append("\">")
			.append("</div>");
		}
		//sw.append(HtmlSnippets.HTML_BODY_END_HTML_END);
		return new ByteArrayInputStream(sw.toString().getBytes());
	}

}
