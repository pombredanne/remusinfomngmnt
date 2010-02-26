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

package org.remus.infomngmnt.common.core.util;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class RegexpReplacer implements Comparable<RegexpReplacer> {

	public RegexpReplacer(final int start, final int end, final String originalString,
			final String replaceMent) {
		super();
		this.start = start;
		this.end = end;
		this.originalString = originalString;
		this.replaceMent = replaceMent;
	}

	private final int start;

	private final int end;

	private final String originalString;

	private final String replaceMent;

	/**
	 * @return the start
	 */
	public int getStart() {
		return this.start;
	}

	/**
	 * @return the end
	 */
	public int getEnd() {
		return this.end;
	}

	/**
	 * @return the originalString
	 */
	public String getOriginalString() {
		return this.originalString;
	}

	/**
	 * @return the replaceMent
	 */
	public String getReplaceMent() {
		return this.replaceMent;
	}

	public int compareTo(final RegexpReplacer arg0) {
		return Integer.valueOf(this.start).compareTo(arg0.getStart());
	}

}
