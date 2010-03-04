package org.remus.infomngmnt.search.service;

import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.highlight.Highlighter;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;

import org.remus.infomngmnt.core.services.IEditingHandler;
import org.remus.infomngmnt.search.Search;
import org.remus.infomngmnt.search.SearchResult;

public interface ISearchService {

	public static Object JOB_FAMILY = new Object();

	void addToIndex(final List<IFile> filesTodeleteFromIndex, final List<IFile> filesToAddToIndex,
			final IProject project);

	String search(final Search currentSearch);

	String search(final Search currentSearch, final boolean saveAsFile);

	String search(final Search currentSearch, final boolean save, final ISearchCallBack callback);

	SearchResult createSearchResult(final Document doc, final Highlighter highlighter);

	void clean(final IProject project, final IProgressMonitor monitor);

	void setEditHandler(IEditingHandler service);

}