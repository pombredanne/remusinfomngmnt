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

package org.remus.infomngmnt.search.ui.websearch;

import org.eclipse.swt.graphics.Image;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class Websearch {

	protected String contributor;

	protected Image image;

	protected String imagePath;

	protected String pattern;

	protected String name;

	protected String id;

	/**
	 * @return the contributor
	 */
	public final String getContributor() {
		return this.contributor;
	}

	/**
	 * @param contributor
	 *            the contributor to set
	 */
	public final void setContributor(final String contributor) {
		this.contributor = contributor;
	}

	/**
	 * @return the image
	 */
	public final Image getImage() {
		return this.image;
	}

	/**
	 * @param image
	 *            the image to set
	 */
	public final void setImage(final Image image) {
		this.image = image;
	}

	/**
	 * @return the imagePath
	 */
	public final String getImagePath() {
		return this.imagePath;
	}

	/**
	 * @param imagePath
	 *            the imagePath to set
	 */
	public final void setImagePath(final String imagePath) {
		this.imagePath = imagePath;
	}

	/**
	 * @return the pattern
	 */
	public final String getPattern() {
		return this.pattern;
	}

	/**
	 * @param pattern
	 *            the pattern to set
	 */
	public final void setPattern(final String pattern) {
		this.pattern = pattern;
	}

	/**
	 * @return the name
	 */
	public final String getName() {
		return this.name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public final void setName(final String name) {
		this.name = name;
	}

	/**
	 * @return the id
	 */
	public final String getId() {
		return this.id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public final void setId(final String id) {
		this.id = id;
	}

}
