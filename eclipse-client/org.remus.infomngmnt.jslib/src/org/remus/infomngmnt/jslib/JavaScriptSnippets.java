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

import org.eclipse.swt.browser.Browser;

import org.apache.commons.lang.StringEscapeUtils;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class JavaScriptSnippets {
	public static final String SCRIPT_SRC_IMAGES_JS = "<script src=\"" + //$NON-NLS-1$
	TemplateLocation.getImageFunctionsUrl() + "\"></script>"; //$NON-NLS-1$


	public static final String SCRIPT_SRC_ROUNDED_CORNERS_JS = "<script src=\"" + //$NON-NLS-1$
	TemplateLocation.getRoundedCornersFunctionsUrl() + "\"></script>"; //$NON-NLS-1$

	public static final String SECTION_BOX_DEFINITION(String sectionBoxId, int radius) {
		return "<script type=\"text/JavaScript\">\r\n" +
		"\r\n" +
		"  window.onload = function()\r\n" +
		"  {\r\n" +
		"          settings = {\r\n" +
		"          tl: { radius: " + radius + " },\r\n" +
		"          tr: { radius: " + radius + " },\r\n" +
		"          bl: { radius: 0 },\r\n" +
		"          br: { radius: 0 },\r\n" +
		"          antiAlias: true,\r\n" +
		"          autoPad: true\r\n" +
		"      }\r\n" +
		"\r\n" +
		"      var myBoxObject = new curvyCorners(settings,\"" + sectionBoxId + "\");\r\n" +
		"      myBoxObject.applyCornersToAll();\r\n" +
		"  }\r\n" +
		"\r\n" +
		"</script>\r\n" +
		"";
	}

	public static void setNewLoadingBarMessage(Browser browser, String message) {
		if (browser.getUrl().endsWith("loading.html") && !browser.isDisposed()) {
			browser.execute("document.getElementById(\"progressMessage\").innerHTML = \"" +StringEscapeUtils.escapeHtml(message) + "\"");
		}
	}
}
