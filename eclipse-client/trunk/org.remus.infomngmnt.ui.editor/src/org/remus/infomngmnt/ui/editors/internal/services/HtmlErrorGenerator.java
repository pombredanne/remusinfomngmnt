/*******************************************************************************
 * Copyright (c) 2010 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/

package org.remus.infomngmnt.ui.editors.internal.services;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;

import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.core.model.InformationStructureRead;
import org.remus.infomngmnt.core.services.IHtmlGenerationErrorGenerator;
import org.remus.infomngmnt.jslib.rendering.FreemarkerRenderer;
import org.remus.infomngmnt.jslib.rendering.RenderingException;
import org.remus.infomngmnt.ui.editor.EditorActivator;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class HtmlErrorGenerator implements IHtmlGenerationErrorGenerator {

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.remus.infomngmnt.core.services.IHtmlGenerationErrorGenerator#
	 * buildErrorRepresentation(org.remus.infomngmnt.InformationUnit,
	 * java.lang.Exception)
	 */
	public InputStream buildErrorRepresentation(final InformationUnit unit, final Exception e) {
		ByteArrayOutputStream returnValue = new ByteArrayOutputStream();
		try {
			InputStream templateIs = FileLocator.openStream(Platform
					.getBundle(EditorActivator.PLUGIN_ID), new Path(
					"template/htmlserialization.flt"), false);

			Map<String, String> freemarkParameters = new HashMap<String, String>();
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			freemarkParameters.put("stacktrace", sw.toString());
			InformationStructureRead read = InformationStructureRead.newSession(unit);

			FreemarkerRenderer.getInstance().process(EditorActivator.PLUGIN_ID, templateIs,
					returnValue, freemarkParameters, read.getContentsAsStrucuturedMap(),
					read.getDynamicContentAsStructuredMap());
		} catch (RenderingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return new ByteArrayInputStream(returnValue.toByteArray());
	}

}
