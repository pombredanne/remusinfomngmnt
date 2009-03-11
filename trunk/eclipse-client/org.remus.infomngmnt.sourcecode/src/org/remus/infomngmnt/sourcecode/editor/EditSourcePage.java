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

package org.remus.infomngmnt.sourcecode.editor;

import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.databinding.edit.EMFEditObservables;
import org.eclipse.jface.databinding.swt.ISWTObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
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

import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.common.ui.databinding.BindingWidgetFactory;
import org.remus.infomngmnt.common.ui.databinding.ComboBindingWidget;
import org.remus.infomngmnt.common.ui.databinding.StyledTextObservableValue;
import org.remus.infomngmnt.core.model.InformationUtil;
import org.remus.infomngmnt.sourcecode.SourceCodePlugin;
import org.remus.infomngmnt.ui.extension.AbstractInformationFormPage;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class EditSourcePage extends AbstractInformationFormPage {

	private Combo combo;
	private StyledText styledText;
	private Text text;

	@Override
	protected void createFormContent(final IManagedForm managedForm) {
		FormToolkit toolkit = managedForm.getToolkit();
		ScrolledForm form = managedForm.getForm();
		Composite body = form.getBody();
		body.setLayout(new GridLayout());
		toolkit.paintBordersFor(body);

		final Section generalSection = toolkit.createSection(body, ExpandableComposite.TITLE_BAR
				| ExpandableComposite.TWISTIE | ExpandableComposite.EXPANDED);
		final GridData gd_generalSection = new GridData(SWT.FILL, SWT.FILL, true, true);
		generalSection.setLayoutData(gd_generalSection);
		generalSection.setText("General");

		final Composite composite = toolkit.createComposite(generalSection, SWT.NONE);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 4;
		composite.setLayout(gridLayout);
		toolkit.paintBordersFor(composite);
		generalSection.setClient(composite);

		toolkit.createLabel(composite, "Name", SWT.NONE);

		this.text = toolkit.createText(composite, null, SWT.NONE);
		this.text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		toolkit.createLabel(composite, "Type", SWT.NONE);

		this.combo = new Combo(composite, SWT.READ_ONLY);

		toolkit.adapt(this.combo, true, true);

		final Label sourcecodeLabel = toolkit.createLabel(composite, "Source-Code", SWT.NONE);
		final GridData gd_sourcecodeLabel = new GridData(SWT.LEFT, SWT.CENTER, false, false, 4, 1);
		sourcecodeLabel.setLayoutData(gd_sourcecodeLabel);

		this.styledText = new StyledText(composite, SWT.FULL_SELECTION | SWT.V_SCROLL
				| SWT.H_SCROLL | SWT.BORDER);
		this.styledText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 4, 1));
		toolkit.adapt(this.styledText, true, true);
		doCreateSemanticSection(body, toolkit);

		addControl(this.text);
		addControl(this.styledText);

		bindValuesToUi();
	}

	@Override
	protected void bindValuesToUi() {
		super.bindValuesToUi();
		ISWTObservableValue swtSource = SWTObservables.observeDelayedValue(500,
				new StyledTextObservableValue(this.styledText, SWT.Modify));
		IObservableValue emfSource = EMFEditObservables.observeValue(Realm.getDefault(),
				this.editingDomain, getModelObject(),
				InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);
		this.dataBindingContext.bindValue(swtSource, emfSource, null, null);

		ComboBindingWidget createComboBinding = BindingWidgetFactory.createComboBinding(this.combo,
				this);
		createComboBinding.setInput(SourceCodePlugin.getDefault().getSourceTypes().keySet());
		InformationUnit srcTypeInfo = InformationUtil.getChildByType(getModelObject(),
				SourceCodePlugin.SRCTYPE_NAME);
		createComboBinding.bindModel(srcTypeInfo,
				InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);

	}

}
