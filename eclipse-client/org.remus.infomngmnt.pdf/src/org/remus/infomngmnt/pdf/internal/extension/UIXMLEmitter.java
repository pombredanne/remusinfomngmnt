/*******************************************************************************
 * Copyright (c) 2011 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/
package org.remus.infomngmnt.pdf.internal.extension;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.remus.InformationUnit;
import org.eclipse.remus.common.core.streams.StreamCloser;
import org.eclipse.remus.common.core.util.IdFactory;
import org.eclipse.remus.core.extension.IEmitter;
import org.eclipse.remus.core.model.InformationStructureRead;
import org.eclipse.remus.js.rendering.FreemarkerRenderer;
import org.eclipse.remus.util.InformationUtil;
import org.eclipse.remus.util.StatusCreator;
import org.remus.infomngmnt.emitter.uixml.UiXml;
import org.remus.infomngmnt.pdf.Activator;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class UIXMLEmitter implements IEmitter {

	/**
	 * 
	 */
	public UIXMLEmitter() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.remus.core.extension.IEmitter#preEmitt()
	 */
	public void preEmitt() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.remus.core.extension.IEmitter#emitt(org.eclipse.remus.
	 * InformationUnit)
	 */
	public Object emitt(InformationUnit element) throws CoreException {
		Map<String, String> additionals = new HashMap<String, String>();
		IFile binaryReferenceFile = InformationUtil
				.getBinaryReferenceFile(element);
		InformationStructureRead read = InformationStructureRead
				.newSession(element);
		String refId = IdFactory.createId();
		additionals.put("refId", refId); //$NON-NLS-1$
		ByteArrayOutputStream returnValue = new ByteArrayOutputStream();
		InputStream templateIs = null;
		try {
			templateIs = FileLocator.openStream(Platform
					.getBundle(Activator.PLUGIN_ID), new Path(
					"template/uiserialization.flt"), false); //$NON-NLS-1$
			FreemarkerRenderer.getInstance().process(Activator.PLUGIN_ID,
					templateIs, returnValue, additionals,
					read.getContentsAsStrucuturedMap(),
					read.getDynamicContentAsStructuredMap());
			templateIs.close();
		} catch (IOException e) {
			throw new CoreException(StatusCreator.newStatus(
					"Error reading locations", e)); //$NON-NLS-1$
		} finally {
			StreamCloser.closeStreams(templateIs);
		}
		try {
			UiXml uixml = new UiXml();
			uixml.setXmlString(new String(returnValue.toByteArray(), "UTF-8")); //$NON-NLS-1$
			uixml.setBinaryReferences(Collections.singletonMap(refId,
					binaryReferenceFile));
			return uixml;
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.remus.core.extension.IEmitter#postEmitt()
	 */
	public void postEmitt() {
		// TODO Auto-generated method stub

	}

}
