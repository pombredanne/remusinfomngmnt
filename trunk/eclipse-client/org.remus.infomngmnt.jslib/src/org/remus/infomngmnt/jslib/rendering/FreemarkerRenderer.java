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
import org.xml.sax.InputSource;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateModelException;

import org.remus.infomngmnt.jslib.TemplateLocation;


/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class FreemarkerRenderer {

	private static FreemarkerRenderer INSTANCE;
	private final Configuration cfg;

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
		this.cfg = new Configuration();
		this.cfg.setObjectWrapper(new DefaultObjectWrapper());
		this.cfg.setTemplateLoader(new StreamTemplateLoaders());
		this.cfg.setLocalizedLookup(true);
		this.cfg.setDefaultEncoding("UTF-8");
		try {
			this.cfg.setSharedVariable("jslibDir", TemplateLocation.getBasePath());
			this.cfg.setSharedVariable("jslibstatelocation", TemplateLocation.getGradientSectionImageLocation());
		} catch (TemplateModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void process(
			final String templateName,
			final InputStream template, 
			final InputStream xmlFile,
			final OutputStream out,
			final Map<String, ? extends Object> additionals) throws RenderingException {

		try {
			Template freemarekerTemplate;
			freemarekerTemplate = new Template(templateName,new InputStreamReader(template),this.cfg);
			
			Map<String, Object> root = new HashMap<String, Object>();
			root.put(
					"doc",
					freemarker.ext.dom.NodeModel.parse(new InputSource(xmlFile)));
			root.put("additionals", additionals);
			
			Writer writer = new OutputStreamWriter(out,"UTF-8");
			freemarekerTemplate.process(root, writer);
		} catch (Exception e) {
			Status status = new Status(IStatus.ERROR,TemplateLocation.BUNDLE_ID,300,"Error while serializing object",e);
			throw new RenderingException(status);
		} 
	}
	
	
}
