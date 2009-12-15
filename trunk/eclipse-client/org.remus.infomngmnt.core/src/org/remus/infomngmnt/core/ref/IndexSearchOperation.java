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

package org.remus.infomngmnt.core.ref;

import java.util.concurrent.Callable;

import org.apache.lucene.search.IndexSearcher;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public abstract class IndexSearchOperation<T> implements Callable<T> {

	protected final IndexSearcher reader;

	public IndexSearchOperation(final IndexSearcher searcher) {
		this.reader = searcher;
	}

}