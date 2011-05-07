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
package org.remus.infomngmnt.mindmap.extension;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.remus.BinaryReference;
import org.eclipse.remus.InformationUnit;
import org.eclipse.remus.common.core.streams.StreamCloser;
import org.eclipse.remus.common.core.util.IdFactory;
import org.eclipse.remus.common.core.util.ResourceUtil;
import org.eclipse.remus.core.extension.IEmitter;
import org.eclipse.remus.core.model.InformationStructureRead;
import org.eclipse.remus.util.InformationUtil;
import org.eclipse.remus.util.StatusCreator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.widgets.Display;
import org.remus.infomngmnt.emitter.uixml.UiXml;
import org.remus.infomngmnt.mindmap.MindmapActivator;
import org.xmind.core.IWorkbook;
import org.xmind.core.io.IStorage;
import org.xmind.ui.internal.editor.WorkbookRef;
import org.xmind.ui.internal.editor.WorkbookRefManager;
import org.xmind.ui.mindmap.MindMapImageExtractor;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class UiXmlEmitter implements IEmitter {

	private WorkbookRefManager manager;
	private WorkbookRef workbookRef;
	private IStorage storage;

	/**
	 * 
	 */
	public UiXmlEmitter() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.remus.core.extension.IEmitter#preEmitt()
	 */
	public void preEmitt() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.remus.core.extension.IEmitter#emitt(org.eclipse.remus.
	 * InformationUnit)
	 */
	public Object emitt(InformationUnit element) throws CoreException {
		InformationStructureRead read = InformationStructureRead
				.newSession(element);
		List<BinaryReference> binaryReferences = read.getBinaryReferences();
		if (binaryReferences.size() > 0) {
			IFile binaryReferenceToFile = InformationUtil
					.binaryReferenceToFile(binaryReferences.get(0), element);
			manager = WorkbookRefManager.getInstance();
			workbookRef = manager.addReferrer(binaryReferenceToFile, null);
			storage = workbookRef.createStorage();
			try {
				workbookRef.loadWorkbook(storage, null,
						new NullProgressMonitor());
			} catch (Exception e2) {
				throw new CoreException(StatusCreator.newStatus(
						"Error loading mindmap", e2));
			}

			IWorkbook workbook = workbookRef.getWorkbook();
			MindMapImageExtractor mindMapImageExtractor = new MindMapImageExtractor(
					Display.getDefault(), workbook.getPrimarySheet(), workbook
							.getPrimarySheet().getRootTopic());
			Image image = mindMapImageExtractor.getImage();
			ImageLoader loader = new ImageLoader();

			loader.data = new ImageData[] { image.getImageData() };
			Map<String, String> additionals = new HashMap<String, String>();
			String refId = IdFactory.createId();
			additionals
					.put("width", String.valueOf(image.getImageData().width)); //$NON-NLS-1$
			additionals.put("refId", refId); //$NON-NLS-1$
			IFile file = ResourceUtil.createTempFile("png"); //$NON-NLS-1$
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			loader.save(baos, SWT.IMAGE_PNG);
			mindMapImageExtractor.dispose();
			byte[] bytesFromFile = baos.toByteArray();
			try {
				baos.close();
			} catch (IOException e1) {
				// sjipt
			}
			if (!file.exists()) {
				file.create(new ByteArrayInputStream(bytesFromFile), true, null);
			} else {
				file.setContents(new ByteArrayInputStream(bytesFromFile), true,
						false, null);
			}
			ByteArrayOutputStream returnValue = new ByteArrayOutputStream();
			InputStream templateIs = null;
			try {
				templateIs = FileLocator.openStream(
						Platform.getBundle(MindmapActivator.PLUGIN_ID),
						new Path("template/uiserialization.flt"), false); //$NON-NLS-1$
				org.eclipse.remus.js.rendering.FreemarkerRenderer.getInstance()
						.process(MindmapActivator.PLUGIN_ID, templateIs,
								returnValue, additionals,
								read.getContentsAsStrucuturedMap(),
								read.getDynamicContentAsStructuredMap());
			} catch (IOException e) {
				throw new CoreException(StatusCreator.newStatus(
						"Error reading locations", e)); //$NON-NLS-1$
			} finally {
				StreamCloser.closeStreams(templateIs);
			}
			try {
				UiXml uiXml = new UiXml();
				uiXml.setXmlString(new String(returnValue.toByteArray(),
						"UTF-8")); //$NON-NLS-1$
				uiXml.setBinaryReferences(Collections.singletonMap(refId, file));
				return uiXml;
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException();
			}
		}
		return null;
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
