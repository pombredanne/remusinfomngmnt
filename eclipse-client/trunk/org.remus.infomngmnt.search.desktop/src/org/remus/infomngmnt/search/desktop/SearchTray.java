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

package org.remus.infomngmnt.search.desktop;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import org.remus.infomngmnt.common.service.ITrayService;
import org.remus.infomngmnt.common.ui.UIUtil;
import org.remus.infomngmnt.search.Search;
import org.remus.infomngmnt.search.SearchFactory;
import org.remus.infomngmnt.search.SearchScope;
import org.remus.infomngmnt.search.service.ISearchCallBack;
import org.remus.infomngmnt.search.service.ISearchService;
import org.remus.infomngmnt.search.ui.SearchUIActivator;
import org.remus.infomngmnt.search.ui.editor.SearchResultEditor;
import org.remus.infomngmnt.ui.desktop.extension.AbstractTraySection;
import org.remus.infomngmnt.ui.widgets.SearchText;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class SearchTray extends AbstractTraySection {

	public static final String DIALOG_SETTINGS_LAST_SEARCHTERM = "DIALOG_SETTINGS_LAST_SEARCHTERM"; //$NON-NLS-1$

	private IJobChangeListener searchJobListener;

	protected boolean restoreScheduled;

	@Override
	public void createDetailsPart(final Composite parent) {
		GridLayout gridLayout = new GridLayout(1, false);
		gridLayout.marginTop = 0;
		gridLayout.marginWidth = 0;
		parent.setLayout(gridLayout);
		SearchText searchText = new SearchText(parent, SWT.NO_BACKGROUND) {
			private Text text;

			@Override
			protected ToolBarManager doCreateButtons() {
				ToolBarManager doCreateButtons = super.doCreateButtons();
				SearchTray.this.toolkit.adapt(doCreateButtons.getControl());
				return doCreateButtons;
			}

			@Override
			protected Text doCreateFilterText(final Composite parent) {
				this.text = super.doCreateFilterText(parent);
				String string = SearchDesktopActivator.getDefault().getDialogSettings().get(
						DIALOG_SETTINGS_LAST_SEARCHTERM);
				if (string != null) {
					this.text.setText(string);
				}
				return this.text;
			}

			@Override
			protected void doAddListenerToTextField() {
				super.doAddListenerToTextField();
				this.text.addListener(SWT.Modify, new Listener() {
					public void handleEvent(final Event event) {
						SearchDesktopActivator.getDefault().getDialogSettings().put(
								DIALOG_SETTINGS_LAST_SEARCHTERM, text.getText());

					}
				});
			}

			@Override
			protected void handleSearchButtonPresses() {
				Search createSearch = SearchFactory.eINSTANCE.createSearch();
				createSearch.setScope(SearchScope.ALL);
				createSearch.setSearchString(this.text.getText());
				getSearchService().search(createSearch, true, new ISearchCallBack() {

					public void beforeSearch(final IProgressMonitor monitor, final Search search) {
						// TODO Auto-generated method stub

					}

					public void afterSearch(final IProgressMonitor monitor, final Search search) {
						Display.getDefault().asyncExec(new Runnable() {
							public void run() {
								try {
									SearchUIActivator.getDefault().getSearchContext().deactivate();
									PlatformUI
											.getWorkbench()
											.getActiveWorkbenchWindow()
											.getActivePage()
											.openEditor(
													new URIEditorInput(search.eResource().getURI()),
													SearchResultEditor.class.getName());
								} catch (PartInitException e) {
									// do nothing
								}
							}
						});

					}
				});
				SearchTray.this.restoreScheduled = true;

			}

		};
		this.toolkit.adapt(searchText);
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		gridData.widthHint = 150;
		gridData.heightHint = SWT.DEFAULT;
		searchText.setLayoutData(gridData);
		final ProgressBar searchBar = new ProgressBar(parent, SWT.INDETERMINATE);
		GridData gridData2 = new GridData(SWT.FILL, SWT.FILL, true, false);
		gridData2.widthHint = SWT.DEFAULT;
		gridData2.heightHint = 10;
		searchBar.setLayoutData(gridData2);

		Job.getJobManager().addJobChangeListener(this.searchJobListener = new JobChangeAdapter() {
			@Override
			public void scheduled(final IJobChangeEvent event) {
				if (!parent.isDisposed() && !parent.getDisplay().isDisposed()) {
					checkSearchBar(searchBar, parent.getDisplay());
				}
			}

			@Override
			public void done(final IJobChangeEvent event) {
				if (!parent.isDisposed() && !parent.getDisplay().isDisposed()) {
					checkSearchBar(searchBar, parent.getDisplay());
				}

			}
		});

		checkSearchBar(searchBar, parent.getDisplay());

	}

	protected void checkSearchBar(final ProgressBar progressBar, final Display display) {

		display.asyncExec(new Runnable() {
			public void run() {
				if (!progressBar.isDisposed()) {

					progressBar
							.setVisible(Job.getJobManager().find(ISearchService.JOB_FAMILY).length > 0);
					if (getTrayService() != null
							&& Job.getJobManager().find(ISearchService.JOB_FAMILY).length == 0
							&& SearchTray.this.restoreScheduled) {
						getTrayService().restoreFromTray(UIUtil.getPrimaryWindow().getShell());
						SearchTray.this.restoreScheduled = false;
					}
				}
			}
		});

	}

	@Override
	public void dispose() {
		if (this.searchJobListener != null) {
			Job.getJobManager().removeJobChangeListener(this.searchJobListener);
		}
	}

	public ITrayService getTrayService() {
		final BundleContext bundleContext = SearchDesktopActivator.getDefault().getBundle()
				.getBundleContext();
		final ServiceReference serviceReference = bundleContext
				.getServiceReference(ITrayService.class.getName());
		if (serviceReference != null) {
			return (ITrayService) bundleContext.getService(serviceReference);
		}
		return null;

	}

	public ISearchService getSearchService() {
		final BundleContext bundleContext = SearchDesktopActivator.getDefault().getBundle()
				.getBundleContext();
		final ServiceReference serviceReference = bundleContext
				.getServiceReference(ISearchService.class.getName());
		if (serviceReference != null) {
			return (ISearchService) bundleContext.getService(serviceReference);
		}
		return null;

	}

}
