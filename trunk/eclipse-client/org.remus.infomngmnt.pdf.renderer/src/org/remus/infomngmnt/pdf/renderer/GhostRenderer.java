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
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.program.Program;
import org.remus.infomngmnt.pdf.extension.IPdf2ImageRenderer;
import org.remus.infomngmnt.pdf.extension.ImageInformation;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class GhostRenderer implements IPdf2ImageRenderer {

	/**
	 * 
	 */
	public GhostRenderer() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.pdf.extension.IPdf2ImageRenderer#convert(org.eclipse
	 * .core.resources.IFolder, org.eclipse.core.resources.IFile,
	 * org.eclipse.core.runtime.IProgressMonitor)
	 */
	public List<ImageInformation> convert(final IFolder outputFolder,
			final IFile pdfFile, final IProgressMonitor monitor) {
		List<ImageInformation> returnValue = new ArrayList<ImageInformation>();
		IPreferenceStore preferenceStore = Activator.getDefault()
				.getPreferenceStore();
		String path = preferenceStore.getString(PreferenceInitializer.PATH);
		String options = preferenceStore
				.getString(PreferenceInitializer.OPTIONS);

		options += " -sOutputFile=\"" + outputFolder.getLocation().toOSString() + File.separator //$NON-NLS-1$
				+ "0000%d_pdfimage.png\" "; //$NON-NLS-1$
		options += "\"" + pdfFile.getLocation().toOSString() + "\""; //$NON-NLS-1$ //$NON-NLS-2$
		String fileName = "\"" + path + "\" " + options; //$NON-NLS-1$ //$NON-NLS-2$
		try {
			ProcessBuilder processBuilder = new ProcessBuilder(fileName);
			processBuilder.redirectErrorStream(true);
			Process exec = processBuilder.start();

			BufferedReader output = new BufferedReader(new InputStreamReader(
					exec.getInputStream()));
			String line;
			while ((line = output.readLine()) != null) {
				System.out.println(line);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			outputFolder.refreshLocal(IResource.DEPTH_INFINITE, monitor);
			IResource[] members = outputFolder.members();
			for (IResource iResource : members) {
				if (iResource instanceof IFile) {
					ImageInformation info = new ImageInformation();
					info.setFileName(iResource.getName());
					Image image = new Image(null, outputFolder.getLocation()
							.toFile().getAbsolutePath()
							+ "/" + info.getFileName()); //$NON-NLS-1$
					info.setWidth(image.getImageData().width);
					info.setHeight(image.getImageData().height);
					returnValue.add(info);
					image.dispose();
				}
			}
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Collections.sort(returnValue, new Comparator<ImageInformation>() {
			public int compare(final ImageInformation o1,
					final ImageInformation o2) {
				return o1.getFileName().compareTo(o2.getFileName());
			}
		});
		return returnValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.pdf.extension.IPdf2ImageRenderer#firstSlid(org.eclipse
	 * .core.resources.IFile)
	 */
	public Dimension firstSlid(final IFile pdfFile) {
		IPreferenceStore preferenceStore = Activator.getDefault()
				.getPreferenceStore();
		String path = preferenceStore.getString(PreferenceInitializer.PATH);
		String options = preferenceStore
				.getString(PreferenceInitializer.OPTIONS);
		Dimension returnValue = new Dimension();

		try {
			File createTempFile = File.createTempFile("tst", "png"); //$NON-NLS-1$ //$NON-NLS-2$
			options += "-dFirstPage=1 -dLastPage=1 -sOutputFile=" //$NON-NLS-1$
					+ createTempFile.getAbsolutePath() + " "; //$NON-NLS-1$
			options += "\"" + pdfFile.getLocation().toOSString() + "\""; //$NON-NLS-1$ //$NON-NLS-2$
			Program.launch("\"" + path + "\" " + options); //$NON-NLS-1$ //$NON-NLS-2$
			Image image = new Image(null, createTempFile.getAbsolutePath());
			returnValue = new Dimension(image.getImageData().width,
					image.getImageData().height);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnValue;
	}

}
