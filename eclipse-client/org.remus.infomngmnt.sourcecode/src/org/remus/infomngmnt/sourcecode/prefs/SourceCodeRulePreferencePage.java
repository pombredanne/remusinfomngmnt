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

package org.remus.infomngmnt.sourcecode.prefs;

import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.databinding.EMFDataBindingContext;
import org.eclipse.emf.databinding.EMFObservables;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.databinding.swt.ISWTObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.RuleValue;
import org.remus.infomngmnt.sourcecode.SourceCodePlugin;
import org.remus.infomngmnt.ui.extension.AbstractCreationPreferencePage;
import org.remus.infomngmnt.util.InformationUtil;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class SourceCodeRulePreferencePage extends
AbstractCreationPreferencePage {

	public static final String NODENAME_PREDEFINED_SOURCETYPE = "NODENAME_PREDEFINED_SOURCETYPE"; //$NON-NLS-1$

	private final EMFDataBindingContext ctx;
	private InformationUnit preDefCategory;
	private InformationUnit preDefName;
	private InformationUnit preDefType;
	private Combo predefSourceType;

	/**
	 * 
	 */
	public SourceCodeRulePreferencePage() {
		this.ctx = new EMFDataBindingContext();
	}

	@Override
	public void setValues(RuleValue value, EditingDomain domain) {
		this.ctx.dispose();
		super.setValues(value, domain);
		this.preDefCategory = InformationUtil.getChildByType(value,NODENAME_PREDEFINED_CATEGORY);
		if (this.preDefCategory == null) {
			this.preDefCategory = InfomngmntFactory.eINSTANCE.createRuleValue();
			this.preDefCategory.setType(NODENAME_PREDEFINED_CATEGORY);
			value.getChildValues().add(this.preDefCategory);
		}
		this.preDefName = InformationUtil.getChildByType(value,NODENAME_PREDEFINED_NAME);
		if (this.preDefName == null) {
			this.preDefName = InfomngmntFactory.eINSTANCE.createRuleValue();
			this.preDefName.setType(NODENAME_PREDEFINED_NAME);
			value.getChildValues().add(this.preDefName);
		}
		this.preDefType = InformationUtil.getChildByType(value,NODENAME_PREDEFINED_SOURCETYPE);
		if (this.preDefType == null) {
			this.preDefType = InfomngmntFactory.eINSTANCE.createRuleValue();
			this.preDefType.setType(NODENAME_PREDEFINED_SOURCETYPE);
			value.getChildValues().add(this.preDefType);
		}
	}

	@Override
	public void createPreferencePage(Composite parent) {
		Composite composite = createDefaultPreferences(parent);
		Label nameLabel = new Label(composite, SWT.NONE);
		nameLabel.setText("Predefined SourceType");
		this.predefSourceType = new Combo(composite, SWT.READ_ONLY);
		this.predefSourceType.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		setControl(composite);
	}



	@Override
	public void bindValuesToUi() {
		ISWTObservableValue swtCategory = SWTObservables.observeText(this.predefCategoryText, SWT.Modify);
		IObservableValue emfCategory = EMFObservables.observeValue(this.preDefCategory, InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);
		ISWTObservableValue swtName = SWTObservables.observeText(this.predefNameText, SWT.Modify);
		IObservableValue emfName = EMFObservables.observeValue(this.preDefName, InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);
		ISWTObservableValue swtType = SWTObservables.observeText(this.predefSourceType);
		IObservableValue emfType = EMFObservables.observeValue(this.preDefType, InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);
		this.ctx.bindValue(swtCategory, emfCategory, null, null);
		this.ctx.bindValue(swtName, emfName, null, null);
		this.ctx.bindValue(swtType, emfType, null, null);

		ComboViewer viewer = new ComboViewer(this.predefSourceType);
		viewer.setLabelProvider(new LabelProvider());
		viewer.setContentProvider(new ObservableListContentProvider());
		WritableList list = new WritableList();
		list.addAll(SourceCodePlugin.getDefault().getSourceTypes().keySet());
		viewer.setInput(list);
	}


}
