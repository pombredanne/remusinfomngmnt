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

package org.eclipse.remus.welcome;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.cyberneko.html.parsers.DOMParser;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ecf.core.ContainerCreateException;
import org.eclipse.ecf.core.ContainerFactory;
import org.eclipse.ecf.core.IContainer;
import org.eclipse.ecf.filetransfer.IRetrieveFileTransferContainerAdapter;
import org.eclipse.remus.application.messages.IDEWorkbenchMessages;
import org.eclipse.remus.common.core.streams.StreamCloser;
import org.eclipse.remus.common.core.streams.StreamUtil;
import org.eclipse.remus.common.io.transfer.DownloadFileJob;
import org.eclipse.remus.common.ui.html.DownloadMissingUrlJob;
import org.eclipse.remus.resources.util.ResourceUtil;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class DownloadLatestNewsJob extends DownloadMissingUrlJob {

	private IContainer container;

	private IRetrieveFileTransferContainerAdapter fileReceiveAdapter;

	public static final String URL_LATEST_NEWS = IDEWorkbenchMessages.DownloadLatestNewsJob_URLLatestNews;
	public static final String URL_PREFIX = "http://remus-software.org"; //$NON-NLS-1$

	public DownloadLatestNewsJob() {
		super(URL_LATEST_NEWS, ResourceUtil.getInternalFile("latest-news.html", //$NON-NLS-1$
				false));
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.core.runtime.jobs.Job#run(org.eclipse.core.runtime.
	 * IProgressMonitor)
	 */
	@Override
	protected IStatus run(final IProgressMonitor monitor) {
		try {
			File tempFile = org.eclipse.remus.common.core.util.ResourceUtil
					.createTempFileOnFileSystem("html"); //$NON-NLS-1$
			DownloadFileJob downloadJob = new DownloadFileJob(new URL(
					URL_LATEST_NEWS), tempFile, getFileReceiveAdapter());
			IStatus run = downloadJob.run(monitor);
			if (run.isOK()) {
				// appendStyles(tempFile, monitor);
				if (getTarget().exists()) {
					getTarget().refreshLocal(IResource.DEPTH_INFINITE, monitor);
					getTarget().delete(false, monitor);
				}
				FileInputStream inputStream = new FileInputStream(tempFile);
				getTarget().create(inputStream, true, monitor);
				StreamCloser.closeStreams(inputStream);
				tempFile.delete();
				schedule(24 * 60 * 60 * 1000);
			} else {
				schedule(60000);
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Status.OK_STATUS;
	}

	private void appendStyles(final File tempFile,
			final IProgressMonitor monitor) {
		final DOMParser parser = new DOMParser();
		try {
			InputStream contents = new FileInputStream(tempFile);
			parser.parse(new org.apache.xerces.xni.parser.XMLInputSource(null,
					null, null, contents, "UTF-8")); //$NON-NLS-1$
			final Document document = parser.getDocument();
			NodeList elementsByTagName = document.getElementsByTagName("head"); //$NON-NLS-1$
			// for (int i = 0; i < elementsByTagName.getLength(); i++) {
			// final Node node = elementsByTagName.item(i);
			//
			// NodeList childNodes = node.getChildNodes();
			// while (childNodes.getLength() > 0) {
			// node.removeChild(childNodes.item(0));
			// }
			// Element createElement = document.createElement("style");
			// Attr createAttribute = document.createAttribute("type");
			// createAttribute.setNodeValue("text/css");
			// InputStream resourceAsStream = DownloadLatestNewsJob.class
			// .getResourceAsStream("template.css");
			// Text createComment = document.createTextNode(StreamUtil
			// .convertStreamToString(resourceAsStream));
			// createElement.setAttributeNode(createAttribute);
			// createElement.appendChild(createComment);
			// node.appendChild(createElement);
			// break;
			// }
			// elementsByTagName = document.getElementsByTagName("a");
			// for (int i = 0; i < elementsByTagName.getLength(); i++) {
			// final Node node = elementsByTagName.item(i);
			// ((Element) node).setAttribute("target", "_blank");
			// String attribute = ((Element) node).getAttribute("href");
			// // Joomla cuts all internal links...
			// if (attribute.startsWith("/")) {
			// ((Element) node).setAttribute("href", URL_PREFIX
			// + attribute);
			// }
			// }
			// elementsByTagName = document.getElementsByTagName("link");
			// for (int i = 0; i < elementsByTagName.getLength(); i++) {
			// final Node node = elementsByTagName.item(i);
			// String attribute = ((Element) node).getAttribute("href");
			// // Joomla cuts all internal links...
			// if (attribute.startsWith("/")) {
			// ((Element) node).setAttribute("href", URL_PREFIX
			// + attribute);
			// }
			// }
			// elementsByTagName = document.getElementsByTagName("script");
			// for (int i = 0; i < elementsByTagName.getLength(); i++) {
			// final Node node = elementsByTagName.item(i);
			// String attribute = ((Element) node).getAttribute("src");
			// // Joomla cuts all internal links...
			// if (attribute.startsWith("/")) {
			// ((Element) node)
			// .setAttribute("src", URL_PREFIX + attribute);
			// }
			// }
			final Transformer transformer = TransformerFactory.newInstance()
					.newTransformer();
			transformer.setOutputProperty("omit-xml-declaration", "yes"); //$NON-NLS-1$ //$NON-NLS-2$

			final DOMSource source = new DOMSource(document);
			final StringWriter writer = new StringWriter();

			final StreamResult result = new StreamResult(writer);
			transformer.transform(source, result);
			ByteArrayInputStream source2 = new ByteArrayInputStream(writer
					.toString().getBytes("UTF-8")); //$NON-NLS-1$
			FileOutputStream fileOutputStream = new FileOutputStream(tempFile,
					false);
			StreamUtil.stream(contents, fileOutputStream);
			StreamCloser.closeStreams(contents);
			fileOutputStream.flush();
			fileOutputStream.close();
			StreamCloser.closeStreams(contents);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private IRetrieveFileTransferContainerAdapter getFileReceiveAdapter() {
		if (container == null) {
			try {
				container = ContainerFactory.getDefault().createContainer();
			} catch (final ContainerCreateException e) {
				throw new RuntimeException("Error initializing sync-container", //$NON-NLS-1$
						e);
			}
			fileReceiveAdapter = (IRetrieveFileTransferContainerAdapter) container
					.getAdapter(IRetrieveFileTransferContainerAdapter.class);
		}
		return fileReceiveAdapter;
	}

}
