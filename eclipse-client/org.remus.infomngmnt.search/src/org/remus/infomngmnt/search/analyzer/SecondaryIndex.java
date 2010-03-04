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

package org.remus.infomngmnt.search.analyzer;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class SecondaryIndex implements ISecondaryIndex {

	private final String label;
	private final String contents;
	private final String query;

	private SecondaryIndex(final String label, final String contents, final String query) {
		this.label = label;
		this.contents = contents;
		this.query = query;

	}

	public static ISecondaryIndex CREATE(final String label, final String contents,
			final String query) {
		return new SecondaryIndex(label, contents, query);

	}

	/**
	 * @return the label
	 */
	public final String getLabel() {
		return this.label;
	}

	/**
	 * @return the contents
	 */
	public final String getContents() {
		return this.contents;
	}

	/**
	 * @return the query
	 */
	public final String getQuery() {
		return this.query;
	}

}
