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

package org.remus.infomngmnt.search.ui.menu;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.menus.WorkbenchWindowControlContribution;

import org.remus.infomngmnt.search.Search;
import org.remus.infomngmnt.search.SearchFactory;
import org.remus.infomngmnt.search.SearchScope;
import org.remus.infomngmnt.search.service.ISearchCallBack;
import org.remus.infomngmnt.search.service.ISearchService;
import org.remus.infomngmnt.search.ui.SearchUIActivator;
import org.remus.infomngmnt.search.ui.editor.SearchResultEditor;
import org.remus.infomngmnt.ui.widgets.InputPrompter;
import org.remus.infomngmnt.util.StatusCreator;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class SearchFieldContribution extends WorkbenchWindowControlContribution {

	private ISearchService service;

	/**
	 * 
	 */
	public SearchFieldContribution() {
	}

	/**
	 * @param id
	 */
	public SearchFieldContribution(final String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.action.ControlContribution#createControl(org.eclipse
	 * .swt.widgets.Composite)
	 */
	@Override
	protected Control createControl(final Composite parent) {

		this.service = SearchUIActivator.getDefault().getServiceTracker().getService(
				ISearchService.class);
		final Text text = new Text(parent, SWT.SINGLE | SWT.LEAD | SWT.BORDER | SWT.SEARCH);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		text.addListener(SWT.DefaultSelection, new Listener() {

			public void handleEvent(final Event event) {
				Search createSearch = SearchFactory.eINSTANCE.createSearch();
				createSearch.setScope(SearchScope.ALL);
				createSearch.setSearchString(text.getText());
				SearchFieldContribution.this.service.search(createSearch, true,
						new ISearchCallBack() {

							public void beforeSearch(final IProgressMonitor monitor,
									final Search search) {
								// TODO Auto-generated method stub

							}

							public void afterSearch(final IProgressMonitor monitor,
									final Search search) {
								Display.getDefault().asyncExec(new Runnable() {
									public void run() {
										try {
											SearchUIActivator.getDefault().getSearchContext()
													.deactivate();
											PlatformUI.getWorkbench().getActiveWorkbenchWindow()
													.getActivePage().openEditor(
															new URIEditorInput(search.eResource()
																	.getURI()),
															SearchResultEditor.class.getName());
										} catch (PartInitException e) {
											SearchUIActivator.getDefault().getLog().log(
													StatusCreator.newStatus(
															"Error opening search-result", e));
										}
									}
								});
							}
						});

			}

		});
		InputPrompter.addPrompt(text, "Enter your searchterm");
		return text;
	}

	@Override
	protected int computeWidth(final Control control) {
		return 300;
	}

	@Override
	public void dispose() {
		SearchUIActivator.getDefault().getServiceTracker().ungetService(this.service);
		super.dispose();
	}

}
