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

package org.remus.infomngmnt.jslib.rendering;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.forms.IFormColors;
import org.xml.sax.InputSource;

import org.remus.infomngmnt.jslib.StyleProvider;
import org.remus.infomngmnt.jslib.TemplateLocation;
import org.remus.infomngmnt.jslib.extension.CheckResourceReferenceJob;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateModelException;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class FreemarkerRenderer {

	private static FreemarkerRenderer INSTANCE;

	public static FreemarkerRenderer getInstance() {
		if (FreemarkerRenderer.INSTANCE == null) {
			synchronized (FreemarkerRenderer.class) {
				if (FreemarkerRenderer.INSTANCE == null) {
					FreemarkerRenderer.INSTANCE = new FreemarkerRenderer();
				}
			}
		}
		return FreemarkerRenderer.INSTANCE;
	}

	private FreemarkerRenderer() {

	}

	private Configuration buildConfig() {
		Configuration cfg = new Configuration();
		cfg.setObjectWrapper(new DefaultObjectWrapper());
		cfg.setTemplateLoader(new StreamTemplateLoaders());
		cfg.setLocalizedLookup(true);
		cfg.setDefaultEncoding("UTF-8");
		try {
			cfg.setSharedVariable("jslibDir", TemplateLocation.getBasePath());
			cfg.setSharedVariable("jslibstatelocation", TemplateLocation
					.getGradientSectionImageLocation());
			cfg.setSharedVariable("defaultFont", StyleProvider.getSystemFont());
			cfg.setSharedVariable("defaultFontStyle", StyleProvider.getSystemFontStyle());
			cfg.setSharedVariable("defaultFontSize", StyleProvider.getSystemFontSize());
			cfg.setSharedVariable("sharedResources", CheckResourceReferenceJob.map);
			cfg.setSharedVariable("sectionTitleColor", StyleProvider
					.getFormColorAsHex(IFormColors.TITLE));
			cfg.setSharedVariable("sectionBgColor", StyleProvider
					.getFormColorAsHex(IFormColors.TB_BORDER));
		} catch (TemplateModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cfg;
	}

	/**
	 * Creates an Html {@link OutputStream} based on the given Template-
	 * {@link InputStream} and data maps.
	 * 
	 * @param templateName
	 *            the name of the template which may be already cached in the
	 *            {@link Configuration}
	 * @param template
	 *            the template as strem
	 * @param out
	 *            the outputstream
	 * @param additionals
	 *            additional map. Values that are used in the template but are
	 *            not directly present in the information unit.
	 * @param datastructure
	 *            the datastructure. All values
	 * @param dynamicStructure
	 *            the dynamic structure. This is a second map which can be used
	 *            for lists.
	 * @throws RenderingException
	 */
	public void process(final String templateName, final InputStream template,
			final OutputStream out, final Map<String, ? extends Object> additionals,
			final Map<String, ? extends Object> datastructure,
			final Map<String, ? extends Object> dynamicStructure) throws RenderingException {
		try {
			Template freemarkerTemplate;
			freemarkerTemplate = new Template(templateName, new InputStreamReader(template),
					buildConfig());

			Map<String, Object> root = new HashMap<String, Object>();

			root.put("additionals", additionals);
			root.put("data", datastructure);
			root.put("dynamic", dynamicStructure);

			Writer writer = new OutputStreamWriter(out, "UTF-8");
			freemarkerTemplate.process(root, writer);
		} catch (Exception e) {
			Status status = new Status(IStatus.ERROR, TemplateLocation.BUNDLE_ID, 300,
					"Error while serializing object", e);
			throw new RenderingException(status);
		}

	}

	/**
	 * Renders a Freemarker Template.
	 * 
	 * @param templateName
	 * @param template
	 * @param xmlFile
	 * @param out
	 * @param additionals
	 * @throws RenderingException
	 * 
	 * @deprecated This method gives the freemarker template a xml node for
	 *             rendering. This is deprecated, use
	 *             {@link #process(String, InputStream, OutputStream, Map, Map, Map)}
	 *             instead.
	 */
	@Deprecated
	public void process(final String templateName, final InputStream template,
			final InputStream xmlFile, final OutputStream out,
			final Map<String, ? extends Object> additionals) throws RenderingException {
		try {
			Template freemarekerTemplate;
			freemarekerTemplate = new Template(templateName, new InputStreamReader(template),
					buildConfig());

			Map<String, Object> root = new HashMap<String, Object>();
			if (xmlFile != null) {
				root.put("doc", freemarker.ext.dom.NodeModel.parse(new InputSource(xmlFile)));
			}
			root.put("additionals", additionals);

			Writer writer = new OutputStreamWriter(out, "UTF-8");
			freemarekerTemplate.process(root, writer);
		} catch (Exception e) {
			Status status = new Status(IStatus.ERROR, TemplateLocation.BUNDLE_ID, 300,
					"Error while serializing object", e);
			throw new RenderingException(status);
		}
	}

}
