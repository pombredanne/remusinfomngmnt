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

package org.remus.infomngmnt.link.webshot;

import java.io.IOException;

import org.cyberneko.html.parsers.DOMParser;
import org.eclipse.jface.preference.IPreferenceStore;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import org.remus.infomngmnt.link.LinkActivator;
import org.remus.infomngmnt.link.preferences.LinkPreferenceInitializer;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class WebshotUtil {

	public static final String URL_PLACEHOLDER = "{URL}"; //$NON-NLS-1$
	public static final String OUTFILE_PLACEHOLDER = "{OUT}"; //$NON-NLS-1$

	public static boolean isWebShotToolingEnabled() {
		IPreferenceStore store = LinkActivator.getDefault().getPreferenceStore();
		String string = store.getString(LinkPreferenceInitializer.SCREENSHOT_CMD);
		return string.indexOf(URL_PLACEHOLDER) != -1 && string.indexOf(OUTFILE_PLACEHOLDER) != -1;
	}

	public static void performWebShot(String url, String out) {
		IPreferenceStore store = LinkActivator.getDefault().getPreferenceStore();
		String string = store.getString(LinkPreferenceInitializer.SCREENSHOT_CMD);
		string = string.replaceAll("\\{URL\\}", url);
		string = string.replaceAll("\\{OUT\\}", out.replaceAll("\\\\", "\\\\\\\\"));
		try {
			Process exec = Runtime.getRuntime().exec(string);
			exec.waitFor();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String obtainHtmlTitle(String url) {
		DOMParser parser = new DOMParser();
		try {
			parser.parse(url);
			NodeList elementsByTagName = parser.getDocument().getElementsByTagName("title");
			if (elementsByTagName.getLength() > 0) {
				NodeList childNodes = elementsByTagName.item(0).getChildNodes();
				if (childNodes.getLength() > 0) {
					Node item = childNodes.item(0);
					if (item instanceof Text) {
						return item.getTextContent();
					}
				}

			}
		} catch (DOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;


	}


}
