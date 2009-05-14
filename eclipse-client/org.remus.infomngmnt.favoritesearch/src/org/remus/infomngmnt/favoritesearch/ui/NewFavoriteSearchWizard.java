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

package org.remus.infomngmnt.favoritesearch.ui;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;

import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.core.model.InformationUtil;
import org.remus.infomngmnt.favoritesearch.FavoriteSearchActivator;
import org.remus.infomngmnt.favoritesearch.util.SearchSerializer;
import org.remus.infomngmnt.search.Search;
import org.remus.infomngmnt.search.SearchFactory;
import org.remus.infomngmnt.search.service.ISearchCallBack;
import org.remus.infomngmnt.search.service.LuceneSearchService;
import org.remus.infomngmnt.ui.newwizards.NewInfoObjectWizard;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class NewFavoriteSearchWizard extends NewInfoObjectWizard {

	private Search search;

	public NewFavoriteSearchWizard() {
		setWindowTitle("Create new Favorite search");
	}

	@Override
	public boolean performFinish() {
		try {
			getContainer().run(true, false, new IRunnableWithProgress() {
				public void run(final IProgressMonitor monitor) throws InvocationTargetException,
						InterruptedException {
					LuceneSearchService.getInstance().search(NewFavoriteSearchWizard.this.search,
							false, false, new ISearchCallBack() {
								public void afterSearch(final IProgressMonitor monitor,
										final Search search) {
									InformationUnit childByType = InformationUtil.getChildByType(
											NewFavoriteSearchWizard.this.newElement,
											FavoriteSearchActivator.RESULT_NODE);
									childByType.setBinaryValue(SearchSerializer.serialize(search));
									childByType = InformationUtil.getChildByType(
											NewFavoriteSearchWizard.this.newElement,
											FavoriteSearchActivator.NEW_ELEMENTS_TYPE);
									childByType.setBinaryValue(SearchSerializer
											.serialize(SearchFactory.eINSTANCE.createSearch()));
								}

								public void beforeSearch(final IProgressMonitor monitor,
										final Search search) {
									// do nothing.
								}
							});
				}
			});
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return super.performFinish();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.ui.newwizards.NewInfoObjectWizard#getInfoTypeId()
	 */
	@Override
	protected String getInfoTypeId() {
		return FavoriteSearchActivator.TYPE_ID;
	}

	@Override
	public void init(final IWorkbench workbench, final IStructuredSelection selection) {
		this.search = (Search) selection.getFirstElement();
		this.page1 = new GeneralFavoriteSearchWizardPage(this.search);
		setCategoryString("Inbox/Favorite Searches");
		setCategoryToPage();
	}

}
