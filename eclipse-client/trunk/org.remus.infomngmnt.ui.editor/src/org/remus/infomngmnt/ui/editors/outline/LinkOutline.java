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
package org.remus.infomngmnt.ui.editors.outline;

import java.util.Collection;

import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.TableWrapLayout;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;

import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.services.RemusServiceTracker;
import org.remus.infomngmnt.ui.editor.EditorActivator;
import org.remus.infomngmnt.ui.editors.services.IEditorExtensionService;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class LinkOutline extends ContentOutlinePage {

	public LinkOutline(final InformationUnit info, final EditingDomain adapterFactoryEditingDomain) {
		this.info = info;
		this.adapterFactoryEditingDomain = adapterFactoryEditingDomain;
		this.serviceTracker = EditorActivator.getDefault().getServiceTracker();
		this.service = this.serviceTracker.getService(IEditorExtensionService.class);

	}

	public static final String ID = "org.remus.infomngmnt.ui.views.Test"; //$NON-NLS-1$

	private InformationUnit info;

	private ScrolledForm sform;

	private EditingDomain adapterFactoryEditingDomain;

	private FormToolkit toolkit;

	private final RemusServiceTracker serviceTracker;

	private final IEditorExtensionService service;

	private Collection<IOutlineSection> outlineSections;

	@Override
	public Control getControl() {
		return this.sform;

	}

	/**
	 * Create contents of the view part
	 * 
	 * @param parent
	 */
	@Override
	public void createControl(final Composite parent) {

		this.toolkit = new FormToolkit(parent.getDisplay());
		this.sform = this.toolkit.createScrolledForm(parent);
		this.sform.setLayoutData(new GridData(GridData.FILL_BOTH));

		TableWrapLayout layout = new TableWrapLayout();
		layout.leftMargin = 10;
		layout.rightMargin = 10;
		this.sform.getBody().setLayout(layout);

		this.outlineSections = this.service.getOutlineSections();
		for (IOutlineSection element : this.outlineSections) {
			element.createControl(this.sform, this.toolkit);
		}

		bindValuesToUi();

	}

	public void bindValuesToUi() {
		for (IOutlineSection element : this.outlineSections) {
			element.bindValuesToUi(this.adapterFactoryEditingDomain, this.info);
		}

	}

	public void disposeModel() {
		for (IOutlineSection element : this.outlineSections) {
			element.disposeModel();
		}
	}

	protected Layout createScrolledFormLayout() {
		final GridLayout layout = new GridLayout();

		layout.marginHeight = 0;
		layout.marginWidth = 0;

		layout.horizontalSpacing = 0;
		layout.verticalSpacing = 0;

		return layout;
	}

	@Override
	public void setFocus() {
		// Set the focus
	}

	/**
	 * @param info
	 *            the info to set
	 */
	public void setInfo(final InformationUnit info) {
		this.info = info;
	}

	/**
	 * @param adapterFactoryEditingDomain
	 *            the adapterFactoryEditingDomain to set
	 */
	public void setAdapterFactoryEditingDomain(final EditingDomain adapterFactoryEditingDomain) {
		this.adapterFactoryEditingDomain = adapterFactoryEditingDomain;
	}

	@Override
	public void dispose() {
		for (IOutlineSection element : this.outlineSections) {
			element.dispose();
		}
		this.serviceTracker.ungetService(this.service);
		super.dispose();
	}

}
