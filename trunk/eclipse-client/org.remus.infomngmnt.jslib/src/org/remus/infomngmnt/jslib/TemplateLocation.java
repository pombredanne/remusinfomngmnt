/*******************************************************************************
 * Copyright (c) 2008 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/

package org.remus.infomngmnt.jslib;

import java.io.File;
import java.io.IOException;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.ui.forms.FormColors;
import org.eclipse.ui.forms.IFormColors;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class TemplateLocation {

	public static final String BUNDLE_ID = "org.remus.infomngmnt.jslib"; //$NON-NLS-1$

	public static String getLoadingUrl() {
		try {
			return FileLocator.toFileURL(FileLocator.find(Platform.getBundle(BUNDLE_ID), new Path("templates/loading.html"), null)).toExternalForm();
		} catch (IOException e) {
			return null;
		}
	}
	public static String getImageFunctionsUrl() {
		try {
			return FileLocator.toFileURL(FileLocator.find(Platform.getBundle(BUNDLE_ID), new Path("templates/images.js"), null)).toExternalForm();
		} catch (IOException e) {
			return null;
		}
	}
	public static String getRoundedCornersFunctionsUrl() {
		try {
			return FileLocator.toFileURL(FileLocator.find(Platform.getBundle(BUNDLE_ID), new Path("js/curvycorners.js"), null)).toExternalForm();
		} catch (IOException e) {
			return null;
		}
	}
	
	public static String getBasePath() {
		try {
			return FileLocator.toFileURL(FileLocator.find(Platform.getBundle(BUNDLE_ID), new Path(""), null)).toExternalForm();
		} catch (IOException e) {
			return null;
		}
	}
	public static String getGradientSectionImageLocation() {
		IPath folderPath = Platform.getStateLocation(Platform.getBundle(BUNDLE_ID)).append("images");
		File imageFolder = folderPath.toFile();
		if (!imageFolder.exists()) {
			imageFolder.mkdirs();
		}
		final File file = folderPath.append("gradientSectionImage.png").toFile();
		if (!file.exists()) {
			StyleProvider.getDisplay().syncExec(new Runnable() {
				public void run() {
					FormColors formColors = new FormColors(StyleProvider.getDisplay());
					Color color1 = formColors.getColor(IFormColors.H_GRADIENT_START);
					Color color2 = formColors.getColor(IFormColors.H_GRADIENT_END);
					//Color color2 = StyleProvider.getDisplay().getSystemColor(SWT.COLOR_BLACK);

					StyleProvider.createGradientImage(color1, color2, SWT.VERTICAL, 1, 20, file.getAbsolutePath());
				}
			});
		}
		return URI.createFileURI(Platform.getStateLocation(Platform.getBundle(BUNDLE_ID)).toFile().getAbsolutePath()).toString();
	}

}
