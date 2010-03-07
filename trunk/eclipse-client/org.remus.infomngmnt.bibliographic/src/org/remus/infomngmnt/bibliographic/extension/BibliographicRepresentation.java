/*******************************************************************************
 * Copyright (c) 2009 Andreas Deinlein
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Andreas Deinlein - bibliographic extensions
 *******************************************************************************/
package org.remus.infomngmnt.bibliographic.extension;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;

import org.remus.infomngmnt.bibliographic.BibliographicActivator;
import org.remus.infomngmnt.common.core.streams.StreamCloser;
import org.remus.infomngmnt.core.extension.AbstractInformationRepresentation;
import org.remus.infomngmnt.core.model.InformationStructureRead;
import org.remus.infomngmnt.jslib.rendering.FreemarkerRenderer;
import org.remus.infomngmnt.util.StatusCreator;

/**
 * Generic HTML generation implementation for all bibliographic information
 * items
 * 
 * @author Andreas Deinlein <dev@deasw.com>
 * 
 */
public class BibliographicRepresentation extends AbstractInformationRepresentation {

	final private String transformationFile = "template/bibliographicUnit.flt";

	@Override
	public InputStream handleHtmlGeneration(final IProgressMonitor monitor) throws CoreException {
		InformationStructureRead read = InformationStructureRead.newSession(getValue());
		ByteArrayOutputStream returnValue = new ByteArrayOutputStream();
		InputStream templateIs = null;
		try {
			templateIs = FileLocator.openStream(Platform
					.getBundle(BibliographicActivator.PLUGIN_ID),
					new Path(this.transformationFile), false);
			FreemarkerRenderer.getInstance().process(BibliographicActivator.PLUGIN_ID, templateIs,
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
