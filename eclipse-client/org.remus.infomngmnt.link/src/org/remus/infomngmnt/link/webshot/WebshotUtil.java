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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.cyberneko.html.parsers.DOMParser;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.remus.common.core.streams.StreamCloser;
import org.eclipse.remus.common.core.streams.StreamUtil;
import org.eclipse.remus.common.core.util.StringUtils;
import org.remus.infomngmnt.link.LinkActivator;
import org.remus.infomngmnt.link.preferences.LinkPreferenceInitializer;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class WebshotUtil {

	public static final String URL_PLACEHOLDER = "{URL}"; //$NON-NLS-1$
	public static final String OUTFILE_PLACEHOLDER = "{OUT}"; //$NON-NLS-1$

	public static boolean isWebShotToolingEnabled() {
		IPreferenceStore store = LinkActivator.getDefault()
				.getPreferenceStore();
		String string = store
				.getString(LinkPreferenceInitializer.RENDERER_SELECTED);
		String cmd = store.getString(LinkPreferenceInitializer.SCREENSHOT_CMD);

		return Integer.valueOf(string) >= 1 && cmd != null
				&& cmd.trim().length() > 0;
	}

	public static void performWebShot(String url, String out) {
		IPreferenceStore store = LinkActivator.getDefault()
				.getPreferenceStore();
		String string = store
				.getString(LinkPreferenceInitializer.SCREENSHOT_CMD);
		int selectedRenderer = Integer.valueOf(store
				.getString(LinkPreferenceInitializer.RENDERER_SELECTED));
		String[] arguments = store.getString(
				LinkPreferenceInitializer.LIST_RENDERER_ARGUMENTS).split("\\|")[selectedRenderer]
				.split(",");
		for (int i = 0; i < arguments.length; i++) {
			arguments[i] = arguments[i]
					.replaceAll("\\{URL\\}", url)
					.replaceAll("\\{OUT\\}", out.replaceAll("\\\\", "\\\\\\\\"))
					.replaceAll("\\{LOC\\}",
							string.replaceAll("\\\\", "\\\\\\\\"));
		}
		System.out.println("executing: " + StringUtils.join(arguments));
		try {

			Process exec = Runtime.getRuntime().exec(arguments);

			exec.waitFor();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (store.getString(LinkPreferenceInitializer.LIST_RENDERER).split(",")[selectedRenderer]
				.startsWith("Webkit")) {
			try {
				File file = new File(out + "-full.png");
				if (file.exists()) {
					FileInputStream fileInputStream = new FileInputStream(file);
					FileOutputStream fileOutputStream = new FileOutputStream(
							new File(out));
					StreamUtil.stream(fileInputStream, fileOutputStream);
					StreamCloser.closeStreams(fileInputStream);
					fileOutputStream.flush();
					fileOutputStream.close();
					file.delete();
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public static String obtainHtmlTitle(String url) {
		DOMParser parser = new DOMParser();
		try {
			parser.parse(url);
			NodeList elementsByTagName = parser.getDocument()
					.getElementsByTagName("title");
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
