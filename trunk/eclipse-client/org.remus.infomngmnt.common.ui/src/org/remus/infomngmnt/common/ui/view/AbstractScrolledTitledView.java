/*******************************************************************************
 * Copyright (c) 2008 Tom Seidel, Spirit Link GmbH
 * All rights reserved.
 * 
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/
package org.remus.infomngmnt.common.ui.view;

import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.part.ViewPart;

/**
 * 
 * @author Tom Seidel <tom.seidel@spiritlink.de>
 * 
 */
public abstract class AbstractScrolledTitledView extends ViewPart {
	protected FormToolkit toolkit;
	protected ScrolledForm form;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets
	 * .Composite)
	 */
	@Override
	public void createPartControl(final Composite parent) {
		// shared form colors instance for default colors

		this.toolkit = new FormToolkit(parent.getDisplay());

		final Composite comp = this.toolkit.createComposite(parent, SWT.BORDER);
		comp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		final GridLayout layout = new GridLayout(1, false);
		layout.horizontalSpacing = 0;
		layout.verticalSpacing = 0;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		comp.setLayout(layout);

		createHeaderControl(comp);

		this.form = this.toolkit.createScrolledForm(comp);
		// this.form.setFont(UIUtil.getStandaloneViewHeaderFont(getSite().getShell().getDisplay()));
		this.form.setText(getTitle());
		if (getDefaultImage() != getTitleImage()) {
			this.form.setImage(getTitleImage());
		}
		this.toolkit.decorateFormHeading(this.form.getForm());
		this.form.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		this.form.getBody().setLayout(createScrolledFormLayout());

		final Composite client = this.toolkit.createComposite(this.form.getBody(), SWT.WRAP);
		client.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		client.setLayout(new GridLayout());
		this.toolkit.paintBordersFor(client);
		createViewContents(client);
		this.form.layout(true);
		initializeToolBar();

	}

	/**
	 * initializes the {@link GridLayout} (no margin, no spacing) used by the
	 * scrolled form
	 */
	protected Layout createScrolledFormLayout() {
		final GridLayout layout = new GridLayout();

		layout.marginHeight = 0;
		layout.marginWidth = 0;

		layout.horizontalSpacing = 0;
		layout.verticalSpacing = 0;

		return layout;
	}

	/**
	 * default implementation does nothing, subclasses can add a header control
	 * above the scrolled form by overriding this method
	 * 
	 * @param parent
	 *            the parent control
	 */
	protected void createHeaderControl(final Composite parent) {
		// default
	}

	/**
	 * abstract method for implementor's view content creation
	 * 
	 * @param parent
	 *            a new composite on a {@link ScrolledForm}'s body, initialized
	 *            with a {@link FillLayout} (can be reset to any other layout)
	 */
	public abstract void createViewContents(Composite parent);

	protected IToolBarManager getToolbarManager() {
		return this.form.getToolBarManager();
	}

	protected void setNewTitle(final String title) {
		this.form.setText(title);
	}

	protected void clearTitle() {
		this.form.setText(getTitle());
	}

	protected String getCurrentTitle() {
		return this.form.getText();
	}

	public FormToolkit getToolkit() {
		return this.toolkit;
	}

	private void initializeToolBar() {
		final IToolBarManager toolBarManager = getViewSite().getActionBars().getToolBarManager();
	}

}
