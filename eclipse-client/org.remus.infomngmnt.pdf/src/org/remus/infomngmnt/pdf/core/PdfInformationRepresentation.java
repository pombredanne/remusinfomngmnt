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
import org.eclipse.remus.common.core.streams.StreamCloser;
import org.eclipse.remus.core.extension.AbstractInformationRepresentation;
import org.eclipse.remus.core.model.InformationStructureRead;
import org.eclipse.remus.js.rendering.FreemarkerRenderer;
import org.eclipse.remus.util.InformationUtil;
import org.eclipse.remus.util.StatusCreator;

import org.remus.infomngmnt.pdf.Activator;
import org.remus.infomngmnt.pdf.extension.IPdfImageRenderer;
import org.remus.infomngmnt.pdf.extension.ImageInformation;
import org.remus.infomngmnt.pdf.preferences.PreferenceInitializer;
import org.remus.infomngmnt.pdf.service.IPDF2ImageExtensionService;

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
		String renderer = (String) read.getValueByNodeId(Activator.RENDERER);
		IPdfImageRenderer rendererById = null;
		if (renderer != null) {
			rendererById = service.getRendererById(renderer);
		}
		if (rendererById == null) {
			String string = Activator.getDefault().getPreferenceStore().getString(
					PreferenceInitializer.DEFAULT_RENDERER);
			rendererById = service.getRendererById(string);
		}
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
				// Image image = new Image(UIUtil.getDisplay(),
				// file.getLocation().toOSString());
				double factor2 = (double) string2.getWidth() / (double) width;
				// monitor.setTaskName("Scaling image " + file.getName());
				// ImageData scaledTo =
				// image.getImageData().scaledTo(width.intValue(),
				// ((Double) (string2.getHeight() / factor2)).intValue());
				// monitor.setTaskName("Scaling " + file.getName() +
				// " completed");
				// ImageLoader loader = new ImageLoader();
				//
				// loader.data = new ImageData[] { scaledTo };
				// long currentTimeMillis = System.currentTimeMillis();
				// IFile newFile = getBuildFolder().getFile("scaled_" +
				// file.getName() + ".png");
				//
				// loader.save(newFile.getLocation().toOSString(),
				// SWT.IMAGE_PNG);
				// newFile.refreshLocal(IResource.DEPTH_INFINITE, monitor);
				// System.out.println("Saving image took "
				// + (System.currentTimeMillis() - currentTimeMillis) + " ms");
				string2.setFileName(URI.createFileURI(file.getLocation().toOSString()).toString());

				string2.setWidth(width.intValue());
				string2.setHeight(((Double) (string2.getHeight() / factor2)).intValue());
				// image.dispose();
			}
		}
		HashMap<String, Object> additionals = new HashMap<String, Object>();
		additionals.put("imagesPaths", convert);
		additionals.put("width", width);
		additionals.put("height", !Double.isNaN(factor) ? width * factor : width);
		additionals.put("pdfSrc", URI.createFileURI(binaryReferenceFile.getLocation().toOSString())
				.toString());

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
