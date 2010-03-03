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

package org.remus.infomngmnt.ui.rules.processing;

import java.util.Map;

import org.remus.infomngmnt.InformationUnit;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class PostProcessingResult {

	private final InformationUnit informationUnit;
	private final Map options;

	PostProcessingResult(final InformationUnit informationUnit, final Map options) {
		this.informationUnit = informationUnit;
		this.options = options;

	}

	/**
	 * @return the informationUnit
	 */
	public InformationUnit getInformationUnit() {
		return this.informationUnit;
	}

	public Map getOptions() {
		return this.options;
	}

}
