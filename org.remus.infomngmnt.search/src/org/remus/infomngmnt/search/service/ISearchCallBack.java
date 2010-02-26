/*******************************************************************************
 * Copyright (c) 2008 Tom Seidel, Remus Software
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

import org.eclipse.core.runtime.IProgressMonitor;

import org.remus.infomngmnt.search.Search;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public interface ISearchCallBack {

	void beforeSearch(IProgressMonitor monitor, Search search);

	void afterSearch(IProgressMonitor monitor, Search search);

}
