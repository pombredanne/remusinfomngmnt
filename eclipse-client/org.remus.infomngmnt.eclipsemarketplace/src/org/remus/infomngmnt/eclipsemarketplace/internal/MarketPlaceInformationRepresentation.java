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

package org.remus.infomngmnt.eclipsemarketplace.internal;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang.StringEscapeUtils;
import org.cyberneko.html.parsers.DOMParser;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.remus.common.core.streams.StreamCloser;
import org.eclipse.remus.common.core.util.StringUtils;
import org.eclipse.remus.core.extension.AbstractInformationRepresentation;
import org.eclipse.remus.core.model.InformationStructureRead;
import org.eclipse.remus.js.rendering.FreemarkerRenderer;
import org.eclipse.remus.util.InformationUtil;
import org.eclipse.remus.util.StatusCreator;
import org.remus.infomngmnt.eclipsemarketplace.MarketPlaceActivator;
import org.remus.infomngmnt.eclipsemarketplace.connector.Messages;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class MarketPlaceInformationRepresentation extends
		AbstractInformationRepresentation {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.core.extension.AbstractInformationRepresentation
	 * #handleHtmlGeneration(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public InputStream handleHtmlGeneration(final IProgressMonitor monitor)
			throws CoreException {
		InformationStructureRead read = InformationStructureRead
				.newSession(getValue());
		IFile binaryReferenceFile = InformationUtil
				.getBinaryReferenceFile(getValue());

		ByteArrayOutputStream returnValue = new ByteArrayOutputStream();
		InputStream templateIs = null;
		try {
			HashMap<String, String> additionals = new HashMap<String, String>();
			additionals.put("embeddedContent", parseContent()); //$NON-NLS-1$
			if (binaryReferenceFile != null) {
				additionals.put(
						"imageHref", //$NON-NLS-1$
						URI.createFileURI(
								binaryReferenceFile.getLocation().toOSString())
								.toString());
			}
			templateIs = FileLocator.openStream(
					Platform.getBundle(MarketPlaceActivator.PLUGIN_ID),
					new Path("$nl$/template/htmlserialization.flt"), true); //$NON-NLS-1$
			FreemarkerRenderer.getInstance().process(
					MarketPlaceActivator.PLUGIN_ID, templateIs, returnValue,
					additionals, read.getContentsAsStrucuturedMap(),
					read.getDynamicContentAsStructuredMap());

		} catch (IOException e) {
			throw new CoreException(StatusCreator.newStatus(
					"Error reading locations", e)); //$NON-NLS-1$
		} finally {
			StreamCloser.closeStreams(templateIs);
		}
		return new ByteArrayInputStream(returnValue.toByteArray());
	}

	private String parseContent() {
		InformationStructureRead read = InformationStructureRead
				.newSession(getValue());
		String content = (String) read
				.getValueByNodeId(MarketPlaceActivator.DESCRIPTION_NODE_ID);

		final DOMParser parser = new DOMParser();
		try {
			parser.parse(new InputSource(new StringReader(content)));
			final Document document = parser.getDocument();
			NodeList elementsByTagName = document.getElementsByTagName("a"); //$NON-NLS-1$
			for (int i = 0; i < elementsByTagName.getLength(); i++) {
				final Node node = elementsByTagName.item(i);
				((Element) node).setAttribute("target", ""); //$NON-NLS-1$ //$NON-NLS-2$
				String attribute = ((Element) node).getAttribute("href"); //$NON-NLS-1$
				((Element) node).setAttribute("href", StringUtils.join( //$NON-NLS-1$
						"javascript:openFile(\'", //$NON-NLS-1$
						StringEscapeUtils.escapeJavaScript(attribute), "\');")); //$NON-NLS-1$
			}
			final Transformer transformer = TransformerFactory.newInstance()
					.newTransformer();
			transformer.setOutputProperty("omit-xml-declaration", "yes"); //$NON-NLS-1$ //$NON-NLS-2$

			final DOMSource source = new DOMSource(document);
			final StringWriter writer = new StringWriter();

			final StreamResult result = new StreamResult(writer);
			transformer.transform(source, result);
			return writer.toString();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return content;
	}

}
