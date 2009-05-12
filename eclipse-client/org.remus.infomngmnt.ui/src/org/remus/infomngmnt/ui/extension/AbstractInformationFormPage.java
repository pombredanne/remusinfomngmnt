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

package org.remus.infomngmnt.ui.extension;

import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.databinding.EMFDataBindingContext;
import org.eclipse.emf.databinding.edit.EMFEditObservables;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.databinding.swt.ISWTObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.common.ui.databinding.BindingUtil;
import org.remus.infomngmnt.common.ui.databinding.IEMFEditBindingProvider;
import org.remus.infomngmnt.ui.editors.InformationEditor;
import org.remus.infomngmnt.ui.editors.InformationFormPage;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public abstract class AbstractInformationFormPage extends InformationFormPage implements
		IEMFEditBindingProvider {

	public AbstractInformationFormPage() {
		super(null, null, null);
	}

	private InformationUnit modelObject = null;

	private boolean rendered = false;

	private boolean dirty = false;

	protected AdapterFactoryEditingDomain editingDomain;

	protected EMFDataBindingContext dataBindingContext;

	private Text keyWordText;

	private Text descriptionText;

	@Override
	public void setPartName(final String partName) {
		super.setPartName(partName);
	}

	@Override
	public boolean isDirty() {
		return this.dirty;
	}

	public void setDirty(final boolean dirty) {
		if (dirty != this.dirty) {
			this.dirty = dirty;
			if (dirty) {
				((InformationEditor) getEditor()).setDirty(dirty);
			}
		}
	}

	@Override
	protected final void createFormContent(final IManagedForm managedForm) {
		this.rendered = true;
		renderPage(managedForm);
		bindValuesToUi();
	}

	protected abstract void renderPage(IManagedForm managedForm);

	/**
	 * Binds a text control to a text control. The value to the model object is
	 * set
	 * 
	 * @param control
	 * @param attribute
	 */
	protected void addDirtyOnTextModifyListener(final Control control, final EAttribute attribute) {

		IObservableValue mObs = EMFEditObservables.observeValue(Realm.getDefault(),
				this.editingDomain, getModelObject(), attribute);
		ISWTObservableValue uObs = SWTObservables.observeDelayedValue(100, SWTObservables
				.observeText(control, SWT.Modify));
		this.dataBindingContext.bindValue(uObs, mObs, null, null);
	}

	protected void doCreateSemanticSection(final Composite parent, final FormToolkit toolkit) {
		final Section semanticsSection = toolkit.createSection(parent,
				ExpandableComposite.TITLE_BAR | ExpandableComposite.TWISTIE
						| ExpandableComposite.EXPANDED);
		final GridData gd_semanticsSection = new GridData(SWT.FILL, SWT.CENTER, false, false);
		semanticsSection.setLayoutData(gd_semanticsSection);
		semanticsSection.setText("Semantics");

		final Composite composite_2 = toolkit.createComposite(semanticsSection, SWT.NONE);
		final GridLayout gridLayout_1 = new GridLayout();
		gridLayout_1.numColumns = 2;
		composite_2.setLayout(gridLayout_1);
		toolkit.paintBordersFor(composite_2);
		semanticsSection.setClient(composite_2);

		toolkit.createLabel(composite_2, "Keywords:", SWT.NONE);

		this.keyWordText = toolkit.createText(composite_2, null, SWT.NONE);
		final GridData gd_keyWordText = new GridData(SWT.FILL, SWT.CENTER, true, false);
		this.keyWordText.setLayoutData(gd_keyWordText);

		toolkit.createLabel(composite_2, "Description:", SWT.NONE);

		this.descriptionText = toolkit.createText(composite_2, null, SWT.WRAP | SWT.V_SCROLL
				| SWT.MULTI);
		final GridData gd_descriptionText = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gd_descriptionText.heightHint = 70;
		this.descriptionText.setLayoutData(gd_descriptionText);

		addControl(this.keyWordText);
		addControl(this.descriptionText);

	}

	public void bindValuesToUi() {
		if (this.keyWordText != null) {
			BindingUtil.createTextAndBind(this.keyWordText, getModelObject(),
					InfomngmntPackage.Literals.INFORMATION_UNIT__KEYWORDS, this);
		}
		if (this.descriptionText != null) {
			BindingUtil.createTextAndBind(this.descriptionText, getModelObject(),
					InfomngmntPackage.Literals.INFORMATION_UNIT__DESCRIPTION, this);
		}

	}

	protected InformationUnit getModelObject() {
		return this.modelObject;
	}

	public void setModelObject(final InformationUnit modelObject) {
		this.modelObject = modelObject;
	}

	public EditingDomain getEditingDomain() {
		return this.editingDomain;
	}

	public EMFDataBindingContext getDatabindingContext() {
		return this.dataBindingContext;
	}

	public void setEditingDomain(final AdapterFactoryEditingDomain editingDomain) {
		this.editingDomain = editingDomain;
	}

	public void setBindingContext(final EMFDataBindingContext ctx) {
		this.dataBindingContext = ctx;

	}

	/**
	 * @return the rendered
	 */
	public boolean isRendered() {
		return this.rendered;
	}

}
