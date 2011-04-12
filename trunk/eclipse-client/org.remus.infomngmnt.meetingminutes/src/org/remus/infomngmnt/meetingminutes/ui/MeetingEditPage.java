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
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.remus.InfomngmntPackage;
import org.eclipse.remus.core.model.InformationStructureRead;
import org.eclipse.remus.ui.databinding.BindingUtil;
import org.eclipse.remus.ui.editors.editpage.AbstractInformationFormPage;
import org.eclipse.remus.ui.widgets.TimeCombo;
import org.eclipse.remus.ui.widgets.databinding.AdditionalBindingWidgetFactory;
import org.eclipse.remus.ui.widgets.databinding.CDateTimeBindingWidget;
import org.eclipse.remus.ui.widgets.databinding.RichTextBindingWidget;
import org.eclipse.remus.ui.widgets.databinding.RichTextWidget;
import org.eclipse.remus.ui.widgets.richtext.ActionConfiguration;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

import org.remus.infomngmnt.contact.shared.ContactsWithEmailSmartField;
import org.remus.infomngmnt.meetingminutes.MeetingMinutesActivator;
import org.remus.infomngmnt.meetingminutes.messages.Messages;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class MeetingEditPage extends AbstractInformationFormPage {

	private RichTextWidget richtext;
	private ToolBarManager textFormatToolbarManager;
	private ToolBarManager colorToolbar;
	private Text subjectText;
	private TimeCombo date;
	private Text placeText;
	private RichTextBindingWidget createRichText;
	private Text moderator;
	private TimeCombo enddate;

	/**
	 * 
	 */
	public MeetingEditPage() {

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

		doCreateHeaderSection(body, toolkit);

		doCreateContentSection(body, toolkit);
		doCreateSemanticSection(body, toolkit);
		form.reflow(true);

	}

	private void doCreateContentSection(final Composite body, final FormToolkit toolkit) {
		final Section generalSection = toolkit.createSection(body, ExpandableComposite.TITLE_BAR
				| ExpandableComposite.EXPANDED);
		final GridData gd_generalSection = new GridData(SWT.FILL, SWT.FILL, true, true);
		generalSection.setLayoutData(gd_generalSection);
		generalSection.setText(Messages.MeetingEditPage_Log);

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

		initActions();

		this.richtext.adjustBars();

	}

	private void initActions() {
		ActionConfiguration.fillSimpleTextFormattingToolbar(this.richtext.getComposer(),
				this.textFormatToolbarManager);
		this.textFormatToolbarManager.update(true);

		ActionConfiguration.fillColorFormattingToolbar(this.richtext.getComposer(),
				this.colorToolbar);
		this.colorToolbar.update(true);

	}

	private void doCreateHeaderSection(final Composite body, final FormToolkit toolkit) {
		final Section generalSection = toolkit.createSection(body, ExpandableComposite.TITLE_BAR
				| ExpandableComposite.EXPANDED | ExpandableComposite.TWISTIE);
		final GridData gd_generalSection = new GridData(SWT.FILL, SWT.FILL, true, true);
		generalSection.setLayoutData(gd_generalSection);
		generalSection.setText(Messages.MeetingEditPage_General);

		final Composite client = toolkit.createComposite(generalSection, SWT.NONE);
		client.setLayout(new GridLayout(4, false));
		GridData gridData = new GridData(SWT.FILL, SWT.BEGINNING, true, false);

		client.setLayoutData(gridData);

		generalSection.setClient(client);

		Label subjectLabel = toolkit.createLabel(client, Messages.MeetingEditPage_Name);
		subjectLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));

		this.subjectText = toolkit.createText(client, "", SWT.BORDER); //$NON-NLS-1$
		this.subjectText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		addControl(this.subjectText);
		Label receivedLabel = toolkit.createLabel(client, Messages.MeetingEditPage_Start);
		receivedLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));

		Composite dueParent = toolkit.createComposite(client);
		GridLayout layout = new GridLayout(2, false);
		layout.marginHeight = 2;
		layout.marginWidth = 1;
		layout.verticalSpacing = 2;
		layout.horizontalSpacing = 2;
		dueParent.setLayout(layout);
		this.date = new TimeCombo(dueParent, SWT.FLAT);
		GridDataFactory.fillDefaults().hint(180, SWT.DEFAULT).grab(true, false).applyTo(this.date);
		this.date.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
		toolkit.adapt(this.date, false, false);
		toolkit.paintBordersFor(dueParent);
		GridData dueDateLayoutData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		dueParent.setLayoutData(dueDateLayoutData);
		toolkit.adapt(this.date);

		Label endLabel = toolkit.createLabel(client, Messages.MeetingEditPage_End);
		endLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));

		Composite endParent = toolkit.createComposite(client);
		endParent.setLayout(layout);
		this.enddate = new TimeCombo(endParent, SWT.FLAT);
		GridDataFactory.fillDefaults().hint(180, SWT.DEFAULT).grab(true, false).applyTo(
				this.enddate);
		this.enddate.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
		toolkit.adapt(this.enddate, false, false);
		toolkit.paintBordersFor(endParent);
		toolkit.adapt(this.enddate);
		GridData endDataLayoutData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		endParent.setLayoutData(endDataLayoutData);

		Label contentTypeLabel = toolkit.createLabel(client, Messages.MeetingEditPage_Location);
		contentTypeLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));

		this.placeText = toolkit.createText(client, "", SWT.BORDER); //$NON-NLS-1$
		this.placeText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		addControl(this.placeText);

		Label moderatorLabel = toolkit.createLabel(client, Messages.MeetingEditPage_Moderator);
		moderatorLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));

		this.moderator = toolkit.createText(client, "", SWT.BORDER); //$NON-NLS-1$
		this.moderator.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		new ContactsWithEmailSmartField(this.moderator);
		addControl(this.moderator);

	}

	@Override
	public void bindValuesToUi() {
		super.bindValuesToUi();
		InformationStructureRead read = InformationStructureRead.newSession(getModelObject());
		BindingUtil.createTextAndBind(this.subjectText, getModelObject(),
				InfomngmntPackage.Literals.ABSTRACT_INFORMATION_UNIT__LABEL, this);
		BindingUtil.createTextAndBind(this.placeText, read
				.getChildByNodeId(MeetingMinutesActivator.NODE_NAME_PLACE), read
				.getFeatureByNodeId(MeetingMinutesActivator.NODE_NAME_PLACE), this);
		BindingUtil.createTextAndBind(this.moderator, read
				.getChildByNodeId(MeetingMinutesActivator.NODE_NAME_MODERATOR), read
				.getFeatureByNodeId(MeetingMinutesActivator.NODE_NAME_MODERATOR), this);
		CDateTimeBindingWidget createCDateTime = AdditionalBindingWidgetFactory.createCDateTime(
				this.date.getDate(), getDatabindingContext(), getEditingDomain());
		createCDateTime.bindModel(
				read.getChildByNodeId(MeetingMinutesActivator.NODE_NAME_DATETIME), read
						.getFeatureByNodeId(MeetingMinutesActivator.NODE_NAME_DATETIME));
		CDateTimeBindingWidget createCDateTime2 = AdditionalBindingWidgetFactory.createCDateTime(
				this.enddate.getDate(), getDatabindingContext(), getEditingDomain());
		createCDateTime2.bindModel(read
				.getChildByNodeId(MeetingMinutesActivator.NODE_NAME_END_DATETIME), read
				.getFeatureByNodeId(MeetingMinutesActivator.NODE_NAME_END_DATETIME));

		this.createRichText = AdditionalBindingWidgetFactory.createRichText(this.richtext, this);
		this.createRichText.bindModel(read.getChildByNodeId(MeetingMinutesActivator.NODE_NAME_LOG),
				read.getFeatureByNodeId(MeetingMinutesActivator.NODE_NAME_LOG));

	}

	@Override
	public void doSave(final IProgressMonitor monitor) {
		if (this.createRichText != null) {
			this.createRichText.getBinding().updateTargetToModel();
		}
		super.doSave(monitor);
	}

}
