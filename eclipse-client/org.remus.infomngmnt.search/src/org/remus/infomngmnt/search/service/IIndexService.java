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
package org.remus.infomngmnt.search.service;

import org.remus.infomngmnt.search.analyzer.IAnalyzer;
import org.remus.infomngmnt.search.analyzer.ISecondaryAnalyzer;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public interface IIndexService {

	IAnalyzer getAnalyzerByTypeAndNodeId(final String typeId, String nodeId);

	ISecondaryAnalyzer[] getSecondaryAnalyerByTypeAndNodeId(final String typeId, String nodeId);

	void setAnalyzerService(ISearchAnalyzerService service);

}
