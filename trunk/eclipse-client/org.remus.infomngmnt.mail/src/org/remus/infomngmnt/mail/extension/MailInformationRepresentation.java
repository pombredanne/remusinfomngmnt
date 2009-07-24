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

package org.remus.infomngmnt.mail.extension;

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

import org.cyberneko.html.parsers.DOMParser;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import org.remus.infomngmnt.common.core.streams.HTMLStripReader;
import org.remus.infomngmnt.common.core.streams.StreamCloser;
import org.remus.infomngmnt.common.core.util.StringUtils;
import org.remus.infomngmnt.core.extension.AbstractInformationRepresentation;
import org.remus.infomngmnt.core.model.InformationStructureRead;
import org.remus.infomngmnt.jslib.rendering.FreemarkerRenderer;
import org.remus.infomngmnt.mail.ContentType;
import org.remus.infomngmnt.mail.MailActivator;
import org.remus.infomngmnt.resources.util.ResourceUtil;
import org.remus.infomngmnt.util.StatusCreator;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class MailInformationRepresentation extends AbstractInformationRepresentation {

	@Override
	public String getBodyForIndexing(final IProgressMonitor monitor) throws CoreException {
		InformationStructureRead read = InformationStructureRead.newSession(getValue());
		String content = (String) read.getValueByNodeId(MailActivator.NODE_NAME_CONTENT);
		try {
			content = HTMLStripReader.strip(content);
		} catch (IOException e) {
			content = "";
		}

		return StringUtils.join(getValue().getStringValue(), "\n", content);
	}

	@Override
	public InputStream handleHtmlGeneration(final IProgressMonitor monitor) throws CoreException {
		InformationStructureRead read = InformationStructureRead.newSession(getValue());
		ByteArrayOutputStream returnValue = new ByteArrayOutputStream();
		InputStream templateIs = null;
		try {
			HashMap<String, String> additionals = new HashMap<String, String>();
			if (read.getValueByNodeId(MailActivator.NODE_NAME_CONTENT_TYPE) != null
					&& read.getValueByNodeId(MailActivator.NODE_NAME_CONTENT_TYPE).equals(
							ContentType.HTML.getKey())) {
				additionals.put("embeddedContent", parseContent());
			}
			templateIs = FileLocator.openStream(Platform.getBundle(MailActivator.PLUGIN_ID),
					new Path("template/htmlserialization.flt"), false);
			FreemarkerRenderer.getInstance().process(MailActivator.PLUGIN_ID, templateIs,
					returnValue, additionals, read.getContentsAsStrucuturedMap(),
					read.getDynamicContentAsStructuredMap());

		} catch (IOException e) {
			throw new CoreException(StatusCreator.newStatus("Error reading locations", e));
		} finally {
			StreamCloser.closeStreams(templateIs);
		}
		return new ByteArrayInputStream(returnValue.toByteArray());
	}

	private String parseContent() {
		InformationStructureRead read = InformationStructureRead.newSession(getValue());
		String content = (String) read.getValueByNodeId(MailActivator.NODE_NAME_CONTENT);

		final DOMParser parser = new DOMParser();
		try {
			parser.parse(new InputSource(new StringReader(content)));
			final Document document = parser.getDocument();

			NodeList elementsByTagName = document.getElementsByTagName("img");
			for (int i = 0; i < elementsByTagName.getLength(); i++) {
				final Node node = elementsByTagName.item(i);
				String src = ((Element) node).getAttribute("src");
				if (src.startsWith("cid:")) {
					String substring = src.substring(4);
					String osString = getFile().getProject().getLocation().append(
							ResourceUtil.BINARY_FOLDER).append(substring).toOSString();
					((Element) node).setAttribute("src", URI.createFileURI(osString).toString());
				}
			}
			final Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty("omit-xml-declaration", "yes");

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
