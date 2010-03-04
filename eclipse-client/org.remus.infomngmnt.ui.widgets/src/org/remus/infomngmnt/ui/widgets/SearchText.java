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

package org.remus.infomngmnt.ui.widgets;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;

import org.remus.infomngmnt.ui.internal.widgets.ResourceManager;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class SearchText extends Composite {

	private Text searchText;
	private ToolBarManager filterToolBar;

	public SearchText(final Composite parent, final int style) {
		super(parent, style);
		GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		gridLayout.verticalSpacing = 0;
		gridLayout.horizontalSpacing = 0;
		setLayout(gridLayout);
		init();
	}

	private void init() {
		this.searchText = doCreateFilterText(this);
		doAddListenerToTextField();
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		gridData.widthHint = SWT.DEFAULT;
		gridData.heightHint = SWT.DEFAULT;
		this.searchText.setLayoutData(gridData);
		this.filterToolBar = doCreateButtons();
	}

	/**
	 * Creates the text control for entering the filter text. Subclasses may
	 * override.
	 * 
	 * @param parent
	 *            the parent composite
	 * @return the text widget
	 * 
	 * @since 3.3
	 */
	protected Text doCreateFilterText(final Composite parent) {
		return new Text(parent, SWT.SINGLE | SWT.BORDER | SWT.SEARCH | SWT.CANCEL);
	}

	protected void handleSearchButtonPresses() {
		System.out.println("Search");
		// do nothing by default
	}

	protected void doAddListenerToTextField() {
		this.searchText.addListener(SWT.DefaultSelection, new Listener() {
			public void handleEvent(final Event event) {
				handleSearchButtonPresses();
			}
		});
	}

	protected ToolBarManager doCreateButtons() {
		// only create the button if the text widget doesn't support one
		// natively
		ToolBarManager returnValue = new ToolBarManager(SWT.FLAT | SWT.HORIZONTAL
				| SWT.NO_BACKGROUND);
		ToolBar createControl = returnValue.createControl(this);

		GridData gridData = new GridData(SWT.END, SWT.FILL, false, false);
		gridData.widthHint = SWT.DEFAULT;
		gridData.heightHint = SWT.DEFAULT;
		createControl.setLayoutData(gridData);

		if ((this.searchText.getStyle() & SWT.CANCEL) == 0) {
			IAction clearTextAction = new Action("", IAction.AS_PUSH_BUTTON) {//$NON-NLS-1$
				/*
				 * (non-Javadoc)
				 * 
				 * @see org.eclipse.jface.action.Action#run()
				 */
				@Override
				public void run() {
					clearText();
				}
			};

			clearTextAction.setToolTipText("Clear");
			clearTextAction
					.setImageDescriptor(ResourceManager.getPluginImageDescriptor(Platform
							.getBundle(ResourceManager.PLUGIN_ID).getBundleContext(),
							"icons/clear_co.gif"));

			returnValue.add(clearTextAction);
		}
		IAction searchAction = new Action("", IAction.AS_PUSH_BUTTON) {
			@Override
			public void run() {
				handleSearchButtonPresses();
			}
		};
		searchAction.setToolTipText("Search");
		searchAction.setImageDescriptor(ResourceManager.getPluginImageDescriptor(Platform
				.getBundle(ResourceManager.PLUGIN_ID).getBundleContext(), "icons/start_task.gif"));
		returnValue.add(searchAction);
		returnValue.update(true);
		return returnValue;
	}

	/**
	 * Set the text in the search control.
	 * 
	 * @param string
	 */
	protected void setFilterText(final String string) {
		if (this.searchText != null) {
			this.searchText.setText(string);
			updateToolbar(true);
		}
	}

	protected void clearText() {
		setFilterText(""); //$NON-NLS-1$
	}

	protected void updateToolbar(final boolean visible) {
		if (this.filterToolBar != null) {
			this.filterToolBar.getControl().setVisible(visible);
		}
	}

}
