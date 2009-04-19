/*******************************************************************************
 * Copyright (c) 2009 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/

package org.remus.infomngmnt.mediaplayer.extension;

import java.util.List;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public abstract class AbstractMediaPlayer implements IMediaPlayer {

	private String id;

	private String name;

	private List<String> supportedMediaTypes;

	/**
	 * @return the id
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(final String id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * @return the supportedMediaTypes
	 */
	public List<String> getSupportedMediaTypes() {
		return this.supportedMediaTypes;
	}

	/**
	 * @param supportedMediaTypes
	 *            the supportedMediaTypes to set
	 */
	public void setSupportedMediaTypes(final List<String> supportedMediaTypes) {
		this.supportedMediaTypes = supportedMediaTypes;
	}

	public String buildHeaderScript() {
		return "";
	}

}
