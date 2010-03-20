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

package org.remus.infomngmnt.pdf.core;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.common.util.URI;

import org.remus.infomngmnt.common.core.streams.StreamCloser;
import org.remus.infomngmnt.core.extension.AbstractInformationRepresentation;
import org.remus.infomngmnt.core.model.InformationStructureRead;
import org.remus.infomngmnt.jslib.rendering.FreemarkerRenderer;
import org.remus.infomngmnt.pdf.Activator;
import org.remus.infomngmnt.pdf.extension.IPdfImageRenderer;
import org.remus.infomngmnt.pdf.extension.ImageInformation;
import org.remus.infomngmnt.pdf.preferences.PreferenceInitializer;
import org.remus.infomngmnt.pdf.service.IPDF2ImageExtensionService;
import org.remus.infomngmnt.util.InformationUtil;
import org.remus.infomngmnt.util.StatusCreator;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class PdfInformationRepresentation extends AbstractInformationRepresentation {

	/**
	 * 
	 */
	public PdfInformationRepresentation() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean createFolderOnBuild() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.core.extension.AbstractInformationRepresentation
	 * #handleHtmlGeneration(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public InputStream handleHtmlGeneration(final IProgressMonitor monitor) throws CoreException {
		IFile binaryReferenceFile = InformationUtil.getBinaryReferenceFile(getValue());
		InformationStructureRead read = InformationStructureRead.newSession(getValue());
		Long width = (Long) read.getValueByNodeId(Activator.SLIDER_WIDTH);
		width = width == 0 ? 300 : width;
		IPDF2ImageExtensionService service = Activator.getDefault().getServiceTracker().getService(
				IPDF2ImageExtensionService.class);
		String string = Activator.getDefault().getPreferenceStore().getString(
				PreferenceInitializer.DEFAULT_RENDERER);
		IPdfImageRenderer rendererById = service.getRendererById(string);
		if (rendererById == null) {
			rendererById = service.getAllRender()[0];
		}
		List<ImageInformation> convert = rendererById.getRenderer().convert(getBuildFolder(),
				binaryReferenceFile, new SubProgressMonitor(monitor, IProgressMonitor.UNKNOWN));
		getBuildFolder().refreshLocal(IResource.DEPTH_INFINITE, monitor);
		int biggestHeight = 0;
		int biggestWidth = 0;
		for (ImageInformation string2 : convert) {
			if (Math.max(string2.getHeight(), biggestHeight) >= biggestHeight) {
				biggestHeight = string2.getHeight();
				biggestWidth = string2.getWidth();
			}
		}
		double factor = ((double) biggestHeight / (double) biggestWidth);

		for (ImageInformation string2 : convert) {
			IFile file = getBuildFolder().getFile(string2.getFileName());
			if (file.exists()) {
				string2.setFileName(URI.createFileURI(file.getLocation().toOSString()).toString());
				double factor2 = (double) string2.getWidth() / (double) width;
				string2.setWidth(width.intValue());
				string2.setHeight(((Double) (string2.getHeight() / factor2)).intValue());
			}
		}
		HashMap<String, Object> additionals = new HashMap<String, Object>();
		additionals.put("imagesPaths", convert);
		additionals.put("width", width);
		additionals.put("height", width * factor);

		ByteArrayOutputStream returnValue = new ByteArrayOutputStream();
		InputStream templateIs = null;
		try {
			templateIs = FileLocator.openStream(Platform.getBundle(Activator.PLUGIN_ID), new Path(
					"template/htmlserialization.html"), false);
			FreemarkerRenderer.getInstance().process(Activator.PLUGIN_ID, templateIs, returnValue,
					additionals, read.getContentsAsStrucuturedMap(),
					read.getDynamicContentAsStructuredMap());

		} catch (IOException e) {
			throw new CoreException(StatusCreator.newStatus("Error reading locations", e));
		} finally {
			StreamCloser.closeStreams(templateIs);
		}
		return new ByteArrayInputStream(returnValue.toByteArray());
	}
}
