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

import java.io.StringWriter;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.FormColors;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class StyleProvider {

	public static final String STYLE_DEFINITION_START = "<style type=\"text/css\">"; //$NON-NLS-1$
	public static final String STYLE_DEFINITION_END = "</style>"; //$NON-NLS-1$

	public static String getSystemFont() {
		final String[] returnValue = new String[1];
		getDisplay().syncExec(new Runnable() {
			public void run() {
				Font systemFont = StyleProvider.getDisplay().getSystemFont();
				FontData[] fontData = systemFont.getFontData();
				if (fontData.length > 0) {
					returnValue[0] = fontData[0].getName();
				}
			}
		});
		return returnValue[0];
	}

	public static String getSystemFontStyle() {
		final String[] returnValue = new String[1];
		getDisplay().syncExec(new Runnable() {
			public void run() {
				Font systemFont = StyleProvider.getDisplay().getSystemFont();
				FontData[] fontData = systemFont.getFontData();
				if (fontData.length > 0) {
					int style = fontData[0].getStyle();
					if ((style & SWT.BOLD) != 0) {
						returnValue[0] = "bold";
					} else if ((style & SWT.ITALIC) != 0) {
						returnValue[0] = "italic";
					} else {
						returnValue[0] = "normal";
					}
				}
			}
		});
		return returnValue[0];
	}

	public static int getSystemFontSize() {
		final int[] returnValue = new int[1];
		getDisplay().syncExec(new Runnable() {
			public void run() {
				Font systemFont = StyleProvider.getDisplay().getSystemFont();
				FontData[] fontData = systemFont.getFontData();
				if (fontData.length > 0) {
					returnValue[0] = fontData[0].getHeight();
				}
			}
		});
		return returnValue[0];
	}

	public static String getSystemFontDefinition() {
		final StringWriter sw = new StringWriter();
		final String[] returnValue = new String[1];
		getDisplay().syncExec(new Runnable() {
			public void run() {
				Font systemFont = StyleProvider.getDisplay().getSystemFont();
				FontData[] fontData = systemFont.getFontData();
				if (fontData.length > 0) {
					sw.append(".systemfont,body {\n").append(
							"font-family: " + fontData[0].getName() + ";\n").append(
							"font-size: " + fontData[0].getHeight() + "pt;\n");
					int style = fontData[0].getStyle();
					if ((style & SWT.BOLD) != 0) {
						sw.append("font-weight:bold;\n");
					}
					if ((style & SWT.ITALIC) != 0) {
						sw.append("font-stlye:italic;\n");
					}
					sw.append("}\n");
				}
				returnValue[0] = sw.toString();
			}
		});
		return returnValue[0];

	}

	public static String getCssSystemFontDefinition(final FontData[] fontData,
			final String styleName) {
		final StringWriter sw = new StringWriter();
		final String[] returnValue = new String[1];
		getDisplay().syncExec(new Runnable() {
			public void run() {
				if (fontData.length > 0) {
					sw.append(styleName).append(" {\n").append(
							"font-family: " + fontData[0].getName() + ";\n").append(
							"font-size: " + fontData[0].getHeight() + "pt;\n");
					int style = fontData[0].getStyle();
					if ((style & SWT.BOLD) != 0) {
						sw.append("font-weight:bold;\n");
					}
					if ((style & SWT.ITALIC) != 0) {
						sw.append("font-stlye:italic;\n");
					}
					sw.append("}\n");
				}
				returnValue[0] = sw.toString();
			}
		});
		return returnValue[0];

	}

	public static void createGradientImage(final Color color1, final Color color2, final int style,
			final int width, final int height, final String targetLocation) {

		getDisplay().syncExec(new Runnable() {
			public void run() {
				Image returnValue = new Image(getDisplay(), width, height);
				GC gc = new GC(returnValue);
				gc.setForeground(color1);
				gc.setBackground(color2);
				gc.fillGradientRectangle(0, 0, width, height, style == SWT.VERTICAL);
				ImageLoader imageLoader = new ImageLoader();
				imageLoader.data = new ImageData[] { returnValue.getImageData() };
				imageLoader.save(targetLocation, SWT.IMAGE_PNG);
				gc.dispose();
			}
		});
	}

	public static String getFormColorAsHex(final String key) {
		final String[] returnValue = new String[1];
		getDisplay().syncExec(new Runnable() {
			public void run() {
				FormColors formColors = new FormColors(getDisplay());
				Color color = formColors.getColor(key);
				returnValue[0] = convertColor(color.getRed(), color.getGreen(), color.getBlue());
			}

		});
		return returnValue[0];
	}

	static Display getDisplay() {
		if (Display.getCurrent() != null) {
			return Display.getCurrent();
		}
		return Display.getDefault();
	}

	public static String convertColor(final int r, final int g, final int b) {
		java.awt.Color color = new java.awt.Color(r, g, b);
		return Integer.toHexString(color.getRGB() & 0x00ffffff);
	}

}
