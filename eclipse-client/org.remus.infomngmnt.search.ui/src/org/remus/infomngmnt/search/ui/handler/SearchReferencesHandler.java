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

package org.remus.infomngmnt.search.ui.handler;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

import org.remus.infomngmnt.AbstractInformationUnit;
import org.remus.infomngmnt.core.services.IReferencedUnitStore;
import org.remus.infomngmnt.search.Search;
import org.remus.infomngmnt.search.SearchFactory;
import org.remus.infomngmnt.search.service.ILuceneCustomizer;
import org.remus.infomngmnt.search.service.ISearchService;
import org.remus.infomngmnt.search.ui.SearchUIActivator;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class SearchReferencesHandler extends AbstractHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.
	 * ExecutionEvent)
	 */
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		ISelection currentSelection = HandlerUtil.getCurrentSelection(event);
		if (currentSelection instanceof IStructuredSelection) {
			Object firstElement = ((IStructuredSelection) currentSelection).getFirstElement();
			if (firstElement instanceof AbstractInformationUnit) {
				IReferencedUnitStore service = SearchUIActivator.getDefault().getServiceTracker()
						.getService(IReferencedUnitStore.class);
				String[] referencedInfoUnitIds = service
						.getReferencedInfoUnitIds(((AbstractInformationUnit) firstElement).getId());
				Search createSearch = SearchFactory.eINSTANCE.createSearch();
				createSearch.setIdSearch(true);
				createSearch.setSearchString(StringUtils.join(referencedInfoUnitIds,
						ILuceneCustomizer.ID_SEPARATOR));
				ISearchService searchService = SearchUIActivator.getDefault().getServiceTracker()
						.getService(ISearchService.class);
				searchService.search(createSearch, true);
			}
		}
		return null;
	}

}
