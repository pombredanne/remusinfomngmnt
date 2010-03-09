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

package org.remus.infomngmnt.mail.ui;

import java.util.Arrays;

import org.eclipse.core.databinding.conversion.Converter;
import org.eclipse.emf.databinding.EMFUpdateValueStrategy;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.nebula.widgets.cdatetime.CDT;
import org.eclipse.nebula.widgets.cdatetime.CDateTime;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

import org.remus.infomngmnt.common.ui.jface.AnnotatingQuickFixTextBox;
import org.remus.infomngmnt.contact.shared.ContactsWithEmailSmartField;
import org.remus.infomngmnt.core.model.InformationStructureRead;
import org.remus.infomngmnt.mail.ContentType;
import org.remus.infomngmnt.mail.MailActivator;
import org.remus.infomngmnt.ui.databinding.BindingUtil;
import org.remus.infomngmnt.ui.databinding.BindingWidgetFactory;
import org.remus.infomngmnt.ui.databinding.ComboBindingWidget;
import org.remus.infomngmnt.ui.databinding.StyledTextBindingWidget;
import org.remus.infomngmnt.ui.editors.editpage.AbstractInformationFormPage;
import org.remus.infomngmnt.ui.widgets.databinding.AdditionalBindingWidgetFactory;
import org.remus.infomngmnt.ui.widgets.databinding.CDateTimeBindingWidget;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class MailEditPage extends AbstractInformationFormPage {

	private Text subjectText;
	private CDateTime receivedDate;
	private Combo contentTypeCombo;
	private Text senderText;
	private AnnotatingQuickFixTextBox description;

	/**
	 * 
	 */
	public MailEditPage() {
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
		generalSection.setText("Content");

		final Composite client = toolkit.createComposite(generalSection, SWT.NONE);
		client.setLayout(new GridLayout());
		GridData gridData = new GridData(SWT.FILL, SWT.BEGINNING, true, false);

		client.setLayoutData(gridData);

		generalSection.setClient(client);

		this.description = new AnnotatingQuickFixTextBox(client, "", "");
		addControl(this.description.getFTextField());
	}

	private void doCreateHeaderSection(final Composite body, final FormToolkit toolkit) {
		final Section generalSection = toolkit.createSection(body, ExpandableComposite.TITLE_BAR
				| ExpandableComposite.EXPANDED);
		final GridData gd_generalSection = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		generalSection.setLayoutData(gd_generalSection);
		generalSection.setText("Header");

		final Composite client = toolkit.createComposite(generalSection, SWT.NONE);
		client.setLayout(new GridLayout(4, false));
		GridData gridData = new GridData(SWT.FILL, SWT.BEGINNING, true, false);

		client.setLayoutData(gridData);

		generalSection.setClient(client);

		Label subjectLabel = toolkit.createLabel(client, "Subject");
		subjectLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));

		this.subjectText = toolkit.createText(client, "", SWT.BORDER);
		this.subjectText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		addControl(this.subjectText);
		Label receivedLabel = toolkit.createLabel(client, "Received");
		receivedLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));

		this.receivedDate = new CDateTime(client, CDT.BORDER | CDT.TIME_MEDIUM | CDT.DATE_MEDIUM);
		GridDataFactory.fillDefaults().hint(180, SWT.DEFAULT).grab(false, false).applyTo(
				this.receivedDate);

		Label contentTypeLabel = toolkit.createLabel(client, "Content-Type");
		contentTypeLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));

		this.contentTypeCombo = new Combo(client, SWT.DROP_DOWN);
		this.contentTypeCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		toolkit.adapt(this.contentTypeCombo, false, false);

		Label senderLabel = toolkit.createLabel(client, "Sender");
		senderLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));

		this.senderText = toolkit.createText(client, "", SWT.BORDER);
		this.senderText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		new ContactsWithEmailSmartField(this.senderText);
		addControl(this.senderText);
	}

	@Override
	public void bindValuesToUi() {
		super.bindValuesToUi();
		InformationStructureRead read = InformationStructureRead.newSession(getModelObject());
		BindingUtil.createTextAndBind(this.subjectText, getModelObject(), read
				.getFeatureByNodeId(MailActivator.INFO_TYPE_ID), this);

		BindingUtil.createTextAndBind(this.senderText, read
				.getChildByNodeId(MailActivator.NODE_NAME_SENDER), read
				.getFeatureByNodeId(MailActivator.NODE_NAME_SENDER), this);

		CDateTimeBindingWidget receivedBinding = AdditionalBindingWidgetFactory.createCDateTime(
				this.receivedDate, getDatabindingContext(), getEditingDomain());
		receivedBinding.bindModel(read.getChildByNodeId(MailActivator.NODE_NAME_RECEIVED), read
				.getFeatureByNodeId(MailActivator.NODE_NAME_RECEIVED));

		ComboBindingWidget contentTypeBinding = BindingWidgetFactory.createComboBinding(
				this.contentTypeCombo, getDatabindingContext(), getEditingDomain());
		contentTypeBinding.setInput(Arrays.asList(ContentType.values()));
		contentTypeBinding.setLabelProvider(new LabelProvider());
		EMFUpdateValueStrategy contentTypeTarget2Model = BindingUtil
				.createUpdateStratyWithConverter(new Converter(ContentType.class, String.class) {
					public Object convert(final Object fromObject) {
						if (fromObject == null) {
							return null;
						}
						return ((ContentType) fromObject).getKey();
					}
				});
		EMFUpdateValueStrategy contentTypeModel2Target = BindingUtil
				.createUpdateStratyWithConverter(new Converter(String.class, ContentType.class) {
					public Object convert(final Object fromObject) {
						if (fromObject == null) {
							return null;
						}
						return ContentType.fromKey(fromObject.toString());
					}
				});
		contentTypeBinding.bindModel(read.getChildByNodeId(MailActivator.NODE_NAME_CONTENT_TYPE),
				read.getFeatureByNodeId(MailActivator.NODE_NAME_CONTENT_TYPE),
				contentTypeTarget2Model, contentTypeModel2Target);

		StyledTextBindingWidget textBindingWidget = BindingWidgetFactory.createStyledText(
				this.description.getFTextField(), this);
		textBindingWidget.bindModel(read.getChildByNodeId(MailActivator.NODE_NAME_CONTENT), read
				.getFeatureByNodeId(MailActivator.NODE_NAME_CONTENT));

	}
}
