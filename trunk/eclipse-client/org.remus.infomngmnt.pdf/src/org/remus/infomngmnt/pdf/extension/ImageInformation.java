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

package org.remus.infomngmnt.pdf.extension;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class ImageInformation {

	private String fileName;

	private int width;

	private int height;

	/**
	 * @return the fileName
	 */
	public final String getFileName() {
		return this.fileName;
	}

	/**
	 * @param fileName
	 *            the fileName to set
	 */
	public final void setFileName(final String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the width
	 */
	public final int getWidth() {
		return this.width;
	}

	/**
	 * @param width
	 *            the width to set
	 */
	public final void setWidth(final int width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public final int getHeight() {
		return this.height;
	}

	/**
	 * @param height
	 *            the height to set
	 */
	public final void setHeight(final int height) {
		this.height = height;
	}

}
