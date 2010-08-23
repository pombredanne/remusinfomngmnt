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

package org.remus.infomngmnt.connector.twitter.jobs;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.ByteArrayPartSource;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.remus.common.core.streams.StreamUtil;
import org.eclipse.remus.ui.progress.CancelableRunnable;
import org.eclipse.remus.util.StatusCreator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;


/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class UploadImageJob extends CancelableRunnable {

	private static final String API_UPLOAD_URL = "http://twitpic.com/api/upload";

	private final String filePath;

	private String url;

	private final String username;

	private final String password;

	public final String getUrl() {
		return this.url;
	}

	public UploadImageJob(final String filePath, final String username, final String password) {
		this.filePath = filePath;
		this.username = username;
		this.password = password;

	}

	@Override
	protected IStatus runCancelableRunnable(final IProgressMonitor monitor) {
		FileInputStream fileInputStream = null;
		monitor.beginTask("Uploading image", IProgressMonitor.UNKNOWN);
		try {
			HttpClient client = new HttpClient();
			PostMethod method = new PostMethod(API_UPLOAD_URL);

			ArrayList<Part> partList = new ArrayList<Part>();
			fileInputStream = new FileInputStream(this.filePath);

			partList.add(new StringPart("username", this.username));
			partList.add(new StringPart("password", this.password));
			String fileExtension = new Path(this.filePath).getFileExtension();
			partList.add(new FilePart("media", new ByteArrayPartSource("image." + fileExtension,
					StreamUtil.convertStreamToByte(fileInputStream)), "image/" + fileExtension,
					null));

			MultipartRequestEntity entity = new MultipartRequestEntity(partList
					.toArray(new Part[0]), method.getParams());

			method.setRequestEntity(entity);
			client.executeMethod(method);

			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(method
					.getResponseBodyAsStream(), "UTF-8"));
			String input;
			StringBuffer result = new StringBuffer();

			while ((input = bufferedReader.readLine()) != null) {
				result.append(input).append(System.getProperty("line.separator"));
			}

			method.releaseConnection();

			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			documentBuilderFactory.setValidating(false);
			documentBuilderFactory.setIgnoringElementContentWhitespace(true);
			documentBuilderFactory.setIgnoringComments(true);
			documentBuilderFactory.setCoalescing(true);
			documentBuilderFactory.setNamespaceAware(false);
			DocumentBuilder documentBuilder = null;
			documentBuilder = documentBuilderFactory.newDocumentBuilder();

			Document document = documentBuilder.parse(new InputSource(new StringReader(result
					.toString())));
			NodeList dateItems = document.getElementsByTagName("mediaurl");
			if (dateItems != null && dateItems.getLength() > 0) {
				for (int i = 0; i < dateItems.getLength(); i++) {
					Element url = (Element) dateItems.item(i);
					this.url = url.getTextContent();
					break;
				}
			}

		} catch (Exception e) {
			return StatusCreator.newStatus("Error uploading image", e);
		} finally {
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException e) {
					// do nothing
				}
			}
		}
		return Status.OK_STATUS;

	}

}
