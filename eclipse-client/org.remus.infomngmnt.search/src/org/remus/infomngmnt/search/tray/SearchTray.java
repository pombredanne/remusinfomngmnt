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

package org.remus.infomngmnt.search.tray;

import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
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

import org.remus.infomngmnt.common.ui.Activator;
import org.remus.infomngmnt.common.ui.extension.AbstractTraySection;
import org.remus.infomngmnt.common.ui.swt.SearchText;
import org.remus.infomngmnt.search.Search;
import org.remus.infomngmnt.search.SearchFactory;
import org.remus.infomngmnt.search.SearchScope;
import org.remus.infomngmnt.search.service.LuceneSearchService;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class SearchTray extends AbstractTraySection {

	public static final String DIALOG_SETTINGS_LAST_SEARCHTERM = "DIALOG_SETTINGS_LAST_SEARCHTERM"; //$NON-NLS-1$

	private IJobChangeListener searchJobListener;

	@Override
	public void createDetailsPart(final Composite parent) {
		GridLayout gridLayout = new GridLayout(1,false);
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
			protected Text doCreateFilterText(Composite parent) {
				this.text = super.doCreateFilterText(parent);
				String string = Activator.getDefault().getDialogSettings().get(DIALOG_SETTINGS_LAST_SEARCHTERM);
				if (string != null) {
					this.text.setText(string);
				}
				return this.text;
			}
			@Override
			protected void doAddListenerToTextField() {
				super.doAddListenerToTextField();
				this.text.addListener(SWT.Modify, new Listener() {
					public void handleEvent(Event event) {
						Activator.getDefault().getDialogSettings().put(DIALOG_SETTINGS_LAST_SEARCHTERM, text.getText());

					}
				});
			}
			@Override
			protected void handleSearchButtonPresses() {
				Search createSearch = SearchFactory.eINSTANCE.createSearch();
				createSearch.setScope(SearchScope.ALL);
				createSearch.setSearchString(this.text.getText());
				LuceneSearchService.getInstance().search(
						createSearch, true, true, null);
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
			public void scheduled(IJobChangeEvent event) {
				checkSearchBar(searchBar,parent.getDisplay());
			}
			@Override
			public void done(IJobChangeEvent event) {
				checkSearchBar(searchBar,parent.getDisplay());
			}
		});
		checkSearchBar(searchBar, parent.getDisplay());


	}

	protected void checkSearchBar(final ProgressBar progressBar, Display display) {
		display.asyncExec(new Runnable() {
			public void run() {
				progressBar.setVisible(
						Job.getJobManager().find(LuceneSearchService.JOB_FAMILY).length > 0);
			}
		});

	}

	@Override
	public void dispose() {
		if (this.searchJobListener != null) {
			Job.getJobManager().removeJobChangeListener(this.searchJobListener);
		}
	}

}
