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

package org.remus.infomngmnt.ui.view;

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
public class MainViewPart extends ViewPart implements ISetSelectionTarget, IEditingDomainProvider,
		ISelectionProvider, IBarSelectionChangeListener {

	private final List<CollapsibleButtonBar> items;

	private FormToolkit toolkit;

	private Form form;

	private final CollapsibleButtonComposite buttonBar;

	public static final String VIEW_ID = "org.remus.infomngmnt.ui.main"; //$NON-NLS-1$

	/**
	 * 
	 */
	public MainViewPart() {
		this.items = UIPlugin.getDefault().getService(ICollapsibleButtonExtensionService.class)
				.getAllItems();
		this.buttonBar = new CollapsibleButtonComposite();
		this.buttonBar.setItems(this.items);

	}

	@Override
	public void createPartControl(final Composite parent) {

		this.toolkit = new FormToolkit(parent.getDisplay());
		getSite().setSelectionProvider(this.buttonBar);
		final Composite comp = this.toolkit.createComposite(parent, SWT.NONE);
		comp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		GridLayout gridLayout = new GridLayout(1, false);
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		parent.setLayout(gridLayout);
		comp.setLayout(gridLayout);

		this.form = this.toolkit.createForm(comp);
		this.toolkit.decorateFormHeading(this.form);
		// this.form.setFont(UIUtil.getStandaloneViewHeaderFont(getSite().getShell().getDisplay()));
		this.form.setText(getTitle());
		if (getDefaultImage() != getTitleImage()) {
			this.form.setImage(getTitleImage());
		}
		this.form.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		this.form.getBody().setLayout(gridLayout);
		this.buttonBar.addBarSelectionChangeListener(this);
		this.buttonBar.createControl(this.form.getBody(), SWT.NONE);

	}

	@Override
	public void dispose() {
		this.buttonBar.removeBarSelectionChangeListener(this);
		this.buttonBar.dispose();
		super.dispose();
	}

	@Override
	public void init(final IViewSite site, final IMemento memento) throws PartInitException {
		UIPlugin.getDefault().getApplicationService().getModel();
		super.init(site, memento);
		this.buttonBar.init(site, memento);
	}

	@Override
	public void saveState(final IMemento memento) {
		this.buttonBar.saveState(memento);
		super.saveState(memento);
	}

	private void setNewImage(final Image icon) {
		this.form.setImage(icon);
		setTitleImage(icon);

	}

	private void setNewTitle(final String title) {
		this.form.setText(title);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
	 */
	@Override
	public void setFocus() {
		if (this.buttonBar != null) {
			this.buttonBar.setFocus();
		}

	}

	public void selectReveal(final ISelection selection) {
		this.buttonBar.selectReveal(selection);

	}

	public EditingDomain getEditingDomain() {
		return UIPlugin.getDefault().getEditService().getNavigationEditingDomain();
	}

	public void addSelectionChangedListener(final ISelectionChangedListener listener) {
		this.buttonBar.addSelectionChangedListener(listener);
	}

	public ISelection getSelection() {
		return this.buttonBar.getSelection();
	}

	public void removeSelectionChangedListener(final ISelectionChangedListener listener) {
		this.buttonBar.removeSelectionChangedListener(listener);

	}

	public void setSelection(final ISelection selection) {
		this.buttonBar.setSelection(selection);

	}

	public Viewer getViewer() {
		return this.buttonBar.getViewer();
	}

	public void handleSelectionChanged(final CollapsibleButtonBar bar) {
		setNewImage(bar.getIcon());
		setNewTitle(bar.getTitle());
		this.form.getToolBarManager().removeAll();
		bar.initToolbar(this.form.getToolBarManager());
		IEvaluationService service = (IEvaluationService) getSite().getService(
				IEvaluationService.class);

	}

}
