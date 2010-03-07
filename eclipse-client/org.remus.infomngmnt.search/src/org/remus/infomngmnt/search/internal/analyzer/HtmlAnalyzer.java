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

package org.remus.infomngmnt.search.internal.analyzer;

import java.io.IOException;

import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.common.core.streams.HTMLStripReader;
import org.remus.infomngmnt.core.model.InformationStructureRead;
import org.remus.infomngmnt.search.analyzer.IAnalyzer;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class HtmlAnalyzer implements IAnalyzer {

	/**
	 * 
	 */
	public HtmlAnalyzer() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.search.analyzer.IAnalyzer#analyze(org.remus.infomngmnt
	 * .InformationUnit, java.lang.String)
	 */
	public String analyze(final InformationUnit unit, final String attribute) {
		InformationStructureRead read = InformationStructureRead.newSession(unit);
		Object valueByNodeId = read.getValueByNodeId(attribute);
		if (valueByNodeId != null) {
			try {
				return HTMLStripReader.strip(valueByNodeId.toString());
			} catch (IOException e) {
				// do nothing
			}
		}
		return "";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.remus.infomngmnt.search.analyzer.IAnalyzer#isStandardAnalyzer()
	 */
	public boolean isStandardAnalyzer() {
		return false;
	}

}
