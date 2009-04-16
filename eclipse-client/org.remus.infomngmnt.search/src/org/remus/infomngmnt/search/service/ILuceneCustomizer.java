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

import java.text.SimpleDateFormat;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.Directory;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;

import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.search.Search;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public interface ILuceneCustomizer {

	public static final String ID_SEPARATOR = ";"; //$NON-NLS-1$

	Document getLuceneDocument(InformationUnit document, IProject project, IProgressMonitor monitor);

	Directory getIndexDirectory(IProject project);

	Analyzer getAnalyser();

	Query getQueryStringFromSearchQuery(Search search, IProgressMonitor monitor);

	int getMaxResults();

	IProject[] getProjectsToSearch(Search search);

	SimpleDateFormat getDateFormat();

}
