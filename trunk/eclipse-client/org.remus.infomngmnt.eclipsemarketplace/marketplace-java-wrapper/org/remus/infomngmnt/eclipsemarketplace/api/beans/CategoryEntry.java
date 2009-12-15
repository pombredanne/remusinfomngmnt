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

package org.remus.infomngmnt.eclipsemarketplace.api.beans;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class CategoryEntry {

	private String name;

	private int id;

	private int favorited;

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
	public final int getId() {
		return this.id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public final void setId(final int id) {
		this.id = id;
	}

	/**
	 * @return the favorited
	 */
	public final int getFavorited() {
		return this.favorited;
	}

	/**
	 * @param favorited
	 *            the favorited to set
	 */
	public final void setFavorited(final int favorited) {
		this.favorited = favorited;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CategoryEntry [favorited=" + this.favorited + ", id=" + this.id + ", name="
				+ this.name + "]";
	}

}
