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

package org.remus.infomngmnt.core.rules;

import java.util.Map;

import org.remus.infomngmnt.InformationUnit;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class PostProcessingResult {

	private final String categoryString;

	private final InformationUnit informationUnit;

	private final Map<String, Object> fileMap;

	PostProcessingResult(final String categoryString, final InformationUnit informationUnit,
			final Map<String, Object> fileMap) {
		this.categoryString = categoryString;
		this.informationUnit = informationUnit;
		this.fileMap = fileMap;
	}

	/**
	 * @return the categoryString
	 */
	public String getCategoryString() {
		return this.categoryString;
	}

	/**
	 * @return the informationUnit
	 */
	public InformationUnit getInformationUnit() {
		return this.informationUnit;
	}

	public Map<String, Object> getFileMap() {
		return this.fileMap;
	}

}