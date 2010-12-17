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

package org.eclipse.remus.ui.view;

import java.util.List;

import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.remus.ui.UIPlugin;
import org.eclipse.remus.ui.collapsiblebutton.CollapsibleButtonBar;
import org.eclipse.remus.ui.collapsiblebutton.CollapsibleButtonComposite;
import org.eclipse.remus.ui.collapsiblebutton.IBarSelectionChangeListener;
import org.eclipse.remus.ui.collapsiblebutton.service.ICollapsibleButtonExtensionService;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.part.ISetSelectionTarget;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.services.IEvaluationService;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class MainViewPart extends ViewPart implements ISetSelectionTarget,
		IEditingDomainProvider, ISelectionProvider, IBarSelectionChangeListener {

	private final List<CollapsibleButtonBar> items;

	private FormToolkit toolkit;

	private Form form;

	private final CollapsibleButtonComposite buttonBar;

	public static final String VIEW_ID = "org.eclipse.remus.ui.main"; //$NON-NLS-1$

	/**
	 * 
	 */
	public MainViewPart() {
		items = UIPlugin.getDefault()
				.getService(ICollapsibleButtonExtensionService.class)
				.getAllItems();
		buttonBar = new CollapsibleButtonComposite();
		buttonBar.setItems(items);

	}

	@Override
	public void createPartControl(final Composite parent) {

		toolkit = new FormToolkit(parent.getDisplay());
		getSite().setSelectionProvider(buttonBar);
		final Composite comp = toolkit.createComposite(parent, SWT.NONE);
		comp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		GridLayout gridLayout = new GridLayout(1, false);
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		parent.setLayout(gridLayout);
		comp.setLayout(gridLayout);

		form = toolkit.createForm(comp);
		toolkit.decorateFormHeading(form);
		// this.form.setFont(UIUtil.getStandaloneViewHeaderFont(getSite().getShell().getDisplay()));
		form.setText(getTitle());
		if (getDefaultImage() != getTitleImage()) {
			form.setImage(getTitleImage());
		}
		form.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		form.getBody().setLayout(gridLayout);
		buttonBar.addBarSelectionChangeListener(this);
		buttonBar.createControl(form.getBody(), SWT.NONE);

	}

	@Override
	public void dispose() {
		buttonBar.removeBarSelectionChangeListener(this);
		buttonBar.dispose();
		super.dispose();
	}

	@Override
	public void init(final IViewSite site, final IMemento memento)
			throws PartInitException {
		UIPlugin.getDefault().getApplicationService().getModel();
		super.init(site, memento);
		buttonBar.init(site, memento);
	}

	@Override
	public void saveState(final IMemento memento) {
		buttonBar.saveState(memento);
		super.saveState(memento);
	}

	private void setNewImage(final Image icon) {
		form.setImage(icon);
		setTitleImage(icon);

	}

	private void setNewTitle(final String title) {
		form.setText(title);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
	 */
	@Override
	public void setFocus() {
		if (buttonBar != null) {
			buttonBar.setFocus();
		}

	}

	public void selectReveal(final ISelection selection) {
		buttonBar.selectReveal(selection);

	}

	public EditingDomain getEditingDomain() {
		return UIPlugin.getDefault().getEditService()
				.getNavigationEditingDomain();
	}

	public void addSelectionChangedListener(
			final ISelectionChangedListener listener) {
		buttonBar.addSelectionChangedListener(listener);
	}

	public ISelection getSelection() {
		return buttonBar.getSelection();
	}

	public void removeSelectionChangedListener(
			final ISelectionChangedListener listener) {
		buttonBar.removeSelectionChangedListener(listener);

	}

	public void setSelection(final ISelection selection) {
		buttonBar.setSelection(selection);

	}

	public Viewer getViewer() {
		return buttonBar.getViewer();
	}

	public void handleSelectionChanged(final CollapsibleButtonBar bar) {
		setNewImage(bar.getIcon());
		setNewTitle(bar.getTitle());
		form.getToolBarManager().removeAll();
		bar.initToolbar(form.getToolBarManager());
		IEvaluationService service = (IEvaluationService) getSite().getService(
				IEvaluationService.class);

	}

}
