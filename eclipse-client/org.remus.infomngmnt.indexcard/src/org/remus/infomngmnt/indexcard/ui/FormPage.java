/*******************************************************************************
 * Copyright (c) 2010 Andreas Deinlein
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Andreas Deinlein
 *******************************************************************************/
package org.remus.infomngmnt.indexcard.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

import org.eclipse.remus.ui.databinding.BindingWidgetFactory;
import org.eclipse.remus.ui.databinding.StyledTextBindingWidget;
import org.eclipse.remus.ui.editors.editpage.AbstractInformationFormPage;
import org.eclipse.remus.common.ui.jface.AnnotatingQuickFixTextBox;
import org.eclipse.remus.core.model.InformationStructureRead;
import org.remus.infomngmnt.indexcard.Activator;


public class FormPage extends AbstractInformationFormPage {

	private AnnotatingQuickFixTextBox frontside;
	private AnnotatingQuickFixTextBox backside;

	@Override
	protected void renderPage(final IManagedForm managedForm) {
		FormToolkit toolkit = managedForm.getToolkit();
		ScrolledForm form = managedForm.getForm();
		Composite body = form.getBody();
		body.setLayout(new GridLayout());
		toolkit.paintBordersFor(body);

		final Section generalSection = toolkit.createSection(body, ExpandableComposite.TITLE_BAR
				| ExpandableComposite.EXPANDED);
		final GridData gd_generalSection = new GridData(SWT.FILL, SWT.FILL, true, true);
		generalSection.setLayoutData(gd_generalSection);
		generalSection.setText("Frontside");

		final Composite client = toolkit.createComposite(generalSection, SWT.NONE);
		client.setLayout(new GridLayout());
		GridData gridData = new GridData(SWT.FILL, SWT.BEGINNING, true, false);

		client.setLayoutData(gridData);

		generalSection.setClient(client);

		this.frontside = new AnnotatingQuickFixTextBox(client, "", "");
		addControl(this.frontside.getFTextField());

		final Section generalSection1 = toolkit.createSection(body, ExpandableComposite.TITLE_BAR
				| ExpandableComposite.EXPANDED);
		final GridData gd_generalSection1 = new GridData(SWT.FILL, SWT.FILL, true, true);
		generalSection1.setLayoutData(gd_generalSection1);
		generalSection1.setText("Backside");

		final Composite client1 = toolkit.createComposite(generalSection1, SWT.NONE);
		client1.setLayout(new GridLayout());
		GridData gridData1 = new GridData(SWT.FILL, SWT.BEGINNING, true, false);

		client1.setLayoutData(gridData1);

		generalSection1.setClient(client1);

		this.backside = new AnnotatingQuickFixTextBox(client1, "", "");
		addControl(this.backside.getFTextField());

		doCreateSemanticSection(body, toolkit);
		form.reflow(true);

	}

	@Override
	public void bindValuesToUi() {
		super.bindValuesToUi();
		InformationStructureRead read = InformationStructureRead.newSession(getModelObject());

		StyledTextBindingWidget textBindingWidget = BindingWidgetFactory.createStyledText(
				this.frontside.getFTextField(), this);
		textBindingWidget.bindModel(read.getChildByNodeId(Activator.NODE_NAME_FRONTSIDE),
				read.getFeatureByNodeId(Activator.NODE_NAME_FRONTSIDE));

		StyledTextBindingWidget textBindingWidget1 = BindingWidgetFactory.createStyledText(
				this.backside.getFTextField(), this);
		textBindingWidget1.bindModel(read.getChildByNodeId(Activator.NODE_NAME_BACKSIDE),
				read.getFeatureByNodeId(Activator.NODE_NAME_BACKSIDE));

	}

}
