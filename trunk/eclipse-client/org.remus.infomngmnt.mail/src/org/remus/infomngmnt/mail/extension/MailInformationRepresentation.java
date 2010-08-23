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
import java.util.List;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang.StringEscapeUtils;
import org.cyberneko.html.parsers.DOMParser;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.remus.BinaryReference;
import org.eclipse.remus.common.core.streams.StreamCloser;
import org.eclipse.remus.common.core.util.StringUtils;
import org.eclipse.remus.core.extension.AbstractInformationRepresentation;
import org.eclipse.remus.core.model.InformationStructureRead;
import org.eclipse.remus.js.StyleProvider;
import org.eclipse.remus.js.rendering.FreemarkerRenderer;
import org.eclipse.remus.resources.util.ResourceUtil;
import org.eclipse.remus.util.StatusCreator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import org.remus.infomngmnt.mail.ContentType;
import org.remus.infomngmnt.mail.MailActivator;
import org.remus.infomngmnt.mail.preferences.MailPreferenceInitializer;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class MailInformationRepresentation extends AbstractInformationRepresentation {

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
			IPreferenceStore preferenceStore = MailActivator.getDefault().getPreferenceStore();
			additionals.put("bodycss", StyleProvider
					.getCssSystemFontDefinition(PreferenceConverter.getFontDataArray(
							preferenceStore, MailPreferenceInitializer.BODY_FONT), "body"));
			additionals.put("headercss", StyleProvider.getCssSystemFontDefinition(
					PreferenceConverter.getFontDataArray(preferenceStore,
							MailPreferenceInitializer.HEADER_FONT), "h1"));
			additionals.put("subheadercss", StyleProvider.getCssSystemFontDefinition(
					PreferenceConverter.getFontDataArray(preferenceStore,
							MailPreferenceInitializer.SUB_HEADER_FONT), "h2,h3,h4"));
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
					List<BinaryReference> binaryReferences = read.getBinaryReferences(
							MailActivator.NODE_NAME_EMBEDDEDS, true);
					for (BinaryReference binaryReference : binaryReferences) {
						if (binaryReference.getId().equals(substring)) {
							String osString = getFile().getProject().getLocation().append(
									ResourceUtil.BINARY_FOLDER).append(
									binaryReference.getProjectRelativePath()).toOSString();
							((Element) node).setAttribute("src", URI.createFileURI(osString)
									.toString());
						}
					}
				}
			}
			elementsByTagName = document.getElementsByTagName("a");
			for (int i = 0; i < elementsByTagName.getLength(); i++) {
				final Node node = elementsByTagName.item(i);
				((Element) node).setAttribute("target", "");
				String attribute = ((Element) node).getAttribute("href");
				((Element) node).setAttribute("href", StringUtils.join("javascript:openFile(\'",
						StringEscapeUtils.escapeJavaScript(attribute), "\');"));
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
