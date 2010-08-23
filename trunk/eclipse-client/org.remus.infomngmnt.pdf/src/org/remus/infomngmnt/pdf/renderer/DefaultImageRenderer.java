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

package org.remus.infomngmnt.pdf.renderer;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.StandardDecryptionMaterial;
import org.apache.pdfbox.util.PDFImageWriter;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.remus.common.core.util.ResourceUtil;
import org.eclipse.remus.util.StatusCreator;
import org.eclipse.swt.graphics.Image;

import org.remus.infomngmnt.pdf.extension.IPdf2ImageRenderer;
import org.remus.infomngmnt.pdf.extension.ImageInformation;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class DefaultImageRenderer implements IPdf2ImageRenderer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.pdf.extension.IPdf2ImageRenderer#convert(org.eclipse
	 * .core.resources.IFolder, org.eclipse.core.resources.IFile,
	 * org.eclipse.core.runtime.IProgressMonitor)
	 */
	public List<ImageInformation> convert(final IFolder outputFolder, final IFile pdfFile,
			final IProgressMonitor monitor) {
		PDDocument document = null;
		List<ImageInformation> returnValue = new ArrayList<ImageInformation>();
		try {
			document = PDDocument.load(pdfFile.getLocationURI().toURL());
			if (document.isEncrypted()) {
				StandardDecryptionMaterial spm = new StandardDecryptionMaterial("*****");
				document.openProtection(spm);
				AccessPermission ap = document.getCurrentAccessPermission();
				if (!ap.canExtractContent())
					throw new CoreException(StatusCreator
							.newStatus("Error: You do not have permission to extract images."));
			}
			int numberOfPages = document.getNumberOfPages();
			PDFImageWriter imageWriter = new PDFImageWriter();
			boolean success = imageWriter.writeImage(document, "png", null, 1, numberOfPages,
					outputFolder.getLocation().toFile().getAbsolutePath() + "/");
			if (success) {
				for (int i = 1; i <= numberOfPages; i++) {
					ImageInformation info = new ImageInformation();
					info.setFileName(i + ".png");
					Image image = new Image(null, outputFolder.getLocation().toFile()
							.getAbsolutePath()
							+ "/" + info.getFileName());
					info.setWidth(image.getImageData().width);
					info.setHeight(image.getImageData().height);
					returnValue.add(info);
					image.dispose();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (document != null) {
				try {
					document.close();
				} catch (Exception e) {
				}
			}
		}
		return returnValue;
	}

	public Dimension firstSlid(final IFile pdfFile) {
		PDDocument document = null;
		List<ImageInformation> returnValue = new ArrayList<ImageInformation>();
		try {
			document = PDDocument.load(pdfFile.getLocationURI().toURL());
			if (document.isEncrypted()) {
				StandardDecryptionMaterial spm = new StandardDecryptionMaterial("*****");
				document.openProtection(spm);
				AccessPermission ap = document.getCurrentAccessPermission();
				if (!ap.canExtractContent())
					throw new CoreException(StatusCreator
							.newStatus("Error: You do not have permission to extract images."));
			}
			IFile createTempFile = ResourceUtil.createTempFile();
			int numberOfPages = document.getNumberOfPages();
			if (numberOfPages == 0) {
				return new Dimension(0, 0);
			}
			PDFImageWriter imageWriter = new PDFImageWriter();
			boolean success = imageWriter.writeImage(document, "png", null, 1, 1, createTempFile
					.getLocation().toFile().getAbsolutePath());
			if (success) {
				String imagePath = createTempFile.getLocation().toFile().getAbsolutePath()
						+ "1.png";
				Image image = new Image(null, imagePath);
				Dimension dimension = new Dimension(image.getImageData().width, image
						.getImageData().height);
				image.dispose();
				return dimension;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (document != null) {
				try {
					document.close();
				} catch (Exception e) {
				}
			}
		}
		return new Dimension(0, 0);
	}
}
