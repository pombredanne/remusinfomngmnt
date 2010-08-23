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

package org.remus.infomngmnt.meetingminutes.ui;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.remus.core.model.InformationStructureRead;
import org.eclipse.remus.ui.editors.editpage.AbstractInformationFormPage;
import org.eclipse.remus.ui.widgets.databinding.AdditionalBindingWidgetFactory;
import org.eclipse.remus.ui.widgets.databinding.RichTextBindingWidget;
import org.eclipse.remus.ui.widgets.databinding.RichTextWidget;
import org.eclipse.remus.ui.widgets.richtext.ActionConfiguration;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

import org.remus.infomngmnt.meetingminutes.MeetingMinutesActivator;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class AdditionalLogsPage extends AbstractInformationFormPage {

	private RichTextWidget richtext;
	private ToolBarManager textFormatToolbarManager;
	private ToolBarManager colorToolbar;
	private RichTextWidget richtext2;
	private ToolBarManager colorToolbar2;
	private ToolBarManager textFormatToolbarManager2;
	private RichTextBindingWidget createRichText;
	private RichTextBindingWidget createRichText2;

	/**
	 * 
	 */
	public AdditionalLogsPage() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.ui.extension.AbstractInformationFormPage#renderPage
	 * (org.eclipse.ui.forms.IManagedForm)
	 */
	@Override
	protected void renderPage(final IManagedForm managedForm) {
		FormToolkit toolkit = managedForm.getToolkit();
		ScrolledForm form = managedForm.getForm();
		Composite body = form.getBody();
		body.setLayout(new GridLayout());
		toolkit.paintBordersFor(body);

		doCreateDecisionSection(body, toolkit);
		doCreateTodoSection(body, toolkit);
		initActions();
		this.richtext2.adjustBars();
		this.richtext.adjustBars();

	}

	private void initActions() {
		ActionConfiguration.fillSimpleTextFormattingToolbar(this.richtext.getComposer(),
				this.textFormatToolbarManager);
		this.textFormatToolbarManager.update(true);

		ActionConfiguration.fillColorFormattingToolbar(this.richtext.getComposer(),
				this.colorToolbar);
		this.colorToolbar.update(true);
		ActionConfiguration.fillSimpleTextFormattingToolbar(this.richtext2.getComposer(),
				this.textFormatToolbarManager2);
		this.textFormatToolbarManager2.update(true);

		ActionConfiguration.fillColorFormattingToolbar(this.richtext2.getComposer(),
				this.colorToolbar2);
		this.colorToolbar2.update(true);

	}

	private void doCreateDecisionSection(final Composite body, final FormToolkit toolkit) {
		final Section generalSection = toolkit.createSection(body, ExpandableComposite.TITLE_BAR
				| ExpandableComposite.EXPANDED | ExpandableComposite.TWISTIE);
		final GridData gd_generalSection = new GridData(SWT.FILL, SWT.FILL, true, true);
		generalSection.setLayoutData(gd_generalSection);
		generalSection.setText("Decisions");

		final Composite client = toolkit.createComposite(generalSection, SWT.NONE);
		client.setLayout(new GridLayout());
		GridData gridData = new GridData(SWT.FILL, SWT.BEGINNING, true, false);

		client.setLayoutData(gridData);

		generalSection.setClient(client);

		this.richtext2 = new RichTextWidget(client, SWT.SIMPLE, false, toolkit);

		//
		this.textFormatToolbarManager2 = this.richtext2.crateToolbar();
		this.colorToolbar2 = this.richtext2.crateToolbar();

		GridData gridData2 = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData2.heightHint = 500;
		this.richtext2.setLayoutData(gridData2);

	}

	private void doCreateTodoSection(final Composite body, final FormToolkit toolkit) {
		final Section generalSection = toolkit.createSection(body, ExpandableComposite.TITLE_BAR
				| ExpandableComposite.EXPANDED | ExpandableComposite.TWISTIE);
		final GridData gd_generalSection = new GridData(SWT.FILL, SWT.FILL, true, true);
		generalSection.setLayoutData(gd_generalSection);
		generalSection.setText("TODOs");

		final Composite client = toolkit.createComposite(generalSection, SWT.NONE);
		client.setLayout(new GridLayout());
		GridData gridData = new GridData(SWT.FILL, SWT.BEGINNING, true, false);

		client.setLayoutData(gridData);

		generalSection.setClient(client);

		this.richtext = new RichTextWidget(client, SWT.SIMPLE, false, toolkit);

		//
		this.textFormatToolbarManager = this.richtext.crateToolbar();
		this.colorToolbar = this.richtext.crateToolbar();

		GridData gridData2 = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData2.heightHint = 500;
		this.richtext.setLayoutData(gridData2);

	}

	@Override
	public void bindValuesToUi() {
		super.bindValuesToUi();
		InformationStructureRead read = InformationStructureRead.newSession(getModelObject());
		this.createRichText2 = AdditionalBindingWidgetFactory.createRichText(this.richtext, this);
		this.createRichText2.bindModel(read
				.getChildByNodeId(MeetingMinutesActivator.NODE_NAME_DECISIONS), read
				.getFeatureByNodeId(MeetingMinutesActivator.NODE_NAME_DECISIONS));
		this.createRichText = AdditionalBindingWidgetFactory.createRichText(this.richtext, this);
		this.createRichText.bindModel(read
				.getChildByNodeId(MeetingMinutesActivator.NODE_NAME_TODOS), read
				.getFeatureByNodeId(MeetingMinutesActivator.NODE_NAME_TODOS));
	}

	@Override
	public void doSave(final IProgressMonitor monitor) {
		if (this.createRichText != null) {
			this.createRichText.getBinding().updateTargetToModel();
			this.createRichText2.getBinding().updateTargetToModel();
		}
		super.doSave(monitor);
	}

}
