/*******************************************************************************
 * Copyright (c) 2010 Andreas Deinlein
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Andreas Deinlein
 *******************************************************************************/
package org.remus.infomngmnt.indexcard.extension;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.remus.infomngmnt.common.core.streams.StreamCloser;
import org.remus.infomngmnt.core.extension.AbstractInformationRepresentation;
import org.remus.infomngmnt.core.model.InformationStructureRead;
import org.remus.infomngmnt.jslib.rendering.FreemarkerRenderer;
import org.remus.infomngmnt.indexcard.Activator;
import org.remus.infomngmnt.util.StatusCreator;

public class IndexCardInformationRepresentation extends
		AbstractInformationRepresentation {

	@Override
	public InputStream handleHtmlGeneration(IProgressMonitor monitor)
			throws CoreException {
		InformationStructureRead read = InformationStructureRead.newSession(getValue());
		ByteArrayOutputStream returnValue = new ByteArrayOutputStream();
		InputStream templateIs = null;
		try {
			templateIs = FileLocator.openStream(Platform
					.getBundle(Activator.PLUGIN_ID), new Path("template/htmlserialization.flt"), false);
			FreemarkerRenderer.getInstance().process(Activator.PLUGIN_ID, templateIs,
					returnValue, null, read.getContentsAsStrucuturedMap(),
					read.getDynamicContentAsStructuredMap());
		} catch (IOException e) {
			throw new CoreException(StatusCreator.newStatus("Error reading locations", e));
		} finally {
			StreamCloser.closeStreams(templateIs);
		}
		return new ByteArrayInputStream(returnValue.toByteArray());
	}

}
