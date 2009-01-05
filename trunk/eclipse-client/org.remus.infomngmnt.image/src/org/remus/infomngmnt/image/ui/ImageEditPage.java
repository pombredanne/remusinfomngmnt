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

package org.remus.infomngmnt.image.ui;

import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.databinding.edit.EMFEditObservables;
import org.eclipse.jface.databinding.swt.ISWTObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ImageHyperlink;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.ui.extension.AbstractInformationFormPage;


/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class ImageEditPage extends AbstractInformationFormPage {

	private Text text;
	@Override
	protected void createFormContent(final IManagedForm managedForm) {
		FormToolkit toolkit = managedForm.getToolkit();
		ScrolledForm form = managedForm.getForm();
		Composite body = form.getBody();
		body.setLayout(new GridLayout());
		toolkit.paintBordersFor(body);

		final Section generalSection = toolkit.createSection(body,
				ExpandableComposite.TITLE_BAR | ExpandableComposite.TWISTIE | ExpandableComposite.EXPANDED);
		final GridData gd_generalSection = new GridData(SWT.FILL, SWT.CENTER, true, false);
		generalSection.setLayoutData(gd_generalSection);
		generalSection.setText("General");

		final Composite composite = toolkit.createComposite(generalSection, SWT.NONE);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		composite.setLayout(gridLayout);
		toolkit.paintBordersFor(composite);
		generalSection.setClient(composite);

		toolkit.createLabel(composite, "Image-Name", SWT.NONE);

		this.text = toolkit.createText(composite, null, SWT.READ_ONLY);
		this.text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		new Label(composite, SWT.NONE);

		ISWTObservableValue swtLink = SWTObservables.observeDelayedValue(500, SWTObservables.observeText(this.text, SWT.Modify));
		IObservableValue emfLink = EMFEditObservables.observeValue(Realm.getDefault(), this.editingDomain, getModelObject(), InfomngmntPackage.Literals.ABSTRACT_INFORMATION_UNIT__LABEL);
		this.dataBindingContext.bindValue(swtLink, emfLink, null, null);

		toolkit.createHyperlink(composite, "Open Image with the default external application", SWT.NONE);
		new Label(composite, SWT.NONE);

		final ImageHyperlink refreshWebshotImageHyperlink = toolkit.createImageHyperlink(composite, SWT.NONE);
		refreshWebshotImageHyperlink.setText("Set Links within image");
		new Label(composite, SWT.NONE);

		final ImageHyperlink refreshSearchableContentImageHyperlink = toolkit.createImageHyperlink(composite, SWT.NONE);
		refreshSearchableContentImageHyperlink.setText("Change image");

		doCreateSemanticSection(body, toolkit);

		final Section generalSection_1 = toolkit.createSection(body, Section.TITLE_BAR | Section.EXPANDED | Section.TWISTIE);
		final GridData gd_generalSection_1 = new GridData(SWT.FILL, SWT.CENTER, true, false);
		generalSection_1.setLayoutData(gd_generalSection_1);
		generalSection_1.setText("Preview");

		final Composite composite_1 = toolkit.createComposite(generalSection_1, SWT.NONE);
		final GridLayout gridLayout_1 = new GridLayout();
		composite_1.setLayout(gridLayout_1);
		toolkit.paintBordersFor(composite_1);
		generalSection_1.setClient(composite_1);

	}

	
	@Override
	protected String getString() {
		// TODO Auto-generated method stub
		return null;
	}

}
