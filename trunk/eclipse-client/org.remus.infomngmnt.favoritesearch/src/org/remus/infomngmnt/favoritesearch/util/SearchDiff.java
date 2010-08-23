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

package org.remus.infomngmnt.favoritesearch.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.remus.InformationUnit;
import org.eclipse.remus.common.ui.UIUtil;
import org.eclipse.remus.search.Search;
import org.eclipse.remus.search.SearchFactory;
import org.eclipse.remus.search.SearchResult;
import org.eclipse.remus.search.service.ISearchCallBack;
import org.eclipse.remus.search.service.ISearchService;
import org.eclipse.remus.util.InformationUtil;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.EditorPart;

import org.remus.infomngmnt.favoritesearch.FavoriteSearchActivator;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class SearchDiff {

	private SearchResult[] newEntries;

	private SearchResult[] oldEntries;

	public static SearchDiff computeDiff(final List<SearchResult> oldSearch,
			final List<SearchResult> newSearch) {
		// very simple
		List<String> oldIds = new ArrayList<String>();
		List<String> newIds = new ArrayList<String>();
		for (SearchResult searchResult : newSearch) {
			newIds.add(searchResult.getInfoId());
		}
		for (SearchResult searchResult : oldSearch) {
			oldIds.add(searchResult.getInfoId());
		}
		List<SearchResult> newEntries = new ArrayList<SearchResult>();
		for (int i = 0, n = newIds.size(); i < n; i++) {
			if (!oldIds.contains(newIds.get(i))) {
				newEntries.add((SearchResult) EcoreUtil.copy(newSearch.get(i)));
			}
		}
		SearchDiff searchDiff = new SearchDiff();
		searchDiff.newEntries = newEntries.toArray(new SearchResult[newEntries.size()]);
		return searchDiff;
	}

	public static void applyDiffToFavorite(final InformationUnit unit, final EditorPart formEditor) {
		final Search deserialize = SearchSerializer.deserialize(InformationUtil.getChildByType(
				unit, FavoriteSearchActivator.RESULT_NODE).getBinaryValue());
		ISearchService service = FavoriteSearchActivator.getDefault().getServiceTracker()
				.getService(ISearchService.class);
		service.search((Search) EcoreUtil.copy(deserialize), false, new ISearchCallBack() {
			public void afterSearch(final IProgressMonitor monitor, final Search search) {
				SearchDiff diff = SearchDiff.computeDiff(deserialize.getResult(), search
						.getResult());
				InformationUnit childByType = InformationUtil.getChildByType(unit,
						FavoriteSearchActivator.NEW_ELEMENTS_TYPE);
				InformationUnit currentResults = InformationUtil.getChildByType(unit,
						FavoriteSearchActivator.RESULT_NODE);
				Search createSearch = SearchFactory.eINSTANCE.createSearch();
				SearchResult[] newEntries2 = diff.getNewEntries();
				for (SearchResult searchResult : newEntries2) {
					createSearch.getResult().add(searchResult);
				}
				childByType.setBinaryValue(SearchSerializer.serialize(createSearch));
				currentResults.setBinaryValue(SearchSerializer.serialize(search));
				if (formEditor != null) {
					UIUtil.getDisplay().asyncExec(new Runnable() {
						public void run() {
							PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
									.saveEditor(formEditor, false);
						}
					});
				}
			}

			public void beforeSearch(final IProgressMonitor monitor, final Search search) {
				// TODO Auto-generated method stub
			}
		});
		FavoriteSearchActivator.getDefault().getServiceTracker().ungetService(service);
	}

	private SearchDiff() {

	}

	/**
	 * @return the newEntries
	 */
	public SearchResult[] getNewEntries() {
		return this.newEntries;
	}

}
