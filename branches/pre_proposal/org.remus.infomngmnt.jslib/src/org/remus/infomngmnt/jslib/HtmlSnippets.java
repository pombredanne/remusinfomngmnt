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

package org.remus.infomngmnt.jslib;

import java.io.StringWriter;

import org.eclipse.emf.common.util.URI;
import org.eclipse.ui.forms.IFormColors;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class HtmlSnippets {

	public static final String HTML_HEAD
	= "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\r\n"; //$NON-NLS-1$

	public static final String HTML_HEAD_START = "<html>\r\n<head>\r\n"; //$NON-NLS-1$

	public static final String HTML_HEAD_END_BODY_START = "</head>\r\n<body>\r\n"; //$NON-NLS-1$

	public static final String HTML_BODY_END_HTML_END = "</body>\r\n</html>"; //$NON-NLS-1$

	public static String CREATE_SECTION_BOX(String title, String sectionBoxId) {
		String gradientSectionImageLocation = URI.createFileURI(TemplateLocation.getGradientSectionImageLocation()).toString();
		StringWriter sw = new StringWriter();
		sw.append("<div class=\"")
		.append(sectionBoxId)
		.append("\" style=\"margin: 0 auto;width:100%; color: #" + StyleProvider.getFormColorAsHex(IFormColors.TITLE) +";border: 1px solid #" +StyleProvider.getFormColorAsHex(IFormColors.TB_BORDER)+"; padding: 5px;text-align: left;background-image: url(" + gradientSectionImageLocation + "); background-repeat: repeat-x;\">")
		.append("<span class=\"systemfont\" style=\"font-weight:bold;\">")
		.append(title)
		.append("</span></div>");
		return sw.toString();

	}
}
