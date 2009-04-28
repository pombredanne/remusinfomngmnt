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

package org.remus.infomngmnt.image.preferences;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.databinding.EMFDataBindingContext;
import org.eclipse.emf.databinding.EMFObservables;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.databinding.swt.ISWTObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.SWT;

import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.RuleValue;
import org.remus.infomngmnt.core.model.InformationUtil;
import org.remus.infomngmnt.ui.extension.AbstractCreationPreferencePage;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class ImageCreationPreferencePage extends AbstractCreationPreferencePage {

	private final EMFDataBindingContext ctx;
	private InformationUnit preDefCategory;
	private InformationUnit preDefName;

	/**
	 * 
	 */
	public ImageCreationPreferencePage() {
		this.ctx = new EMFDataBindingContext();
	}

	@Override
	public void setValues(final RuleValue value, final EditingDomain domain) {
		this.ctx.dispose();
		super.setValues(value, domain);
		this.preDefCategory = InformationUtil.getChildByType(value, NODENAME_PREDEFINED_CATEGORY);
		if (this.preDefCategory == null) {
			this.preDefCategory = InfomngmntFactory.eINSTANCE.createRuleValue();
			this.preDefCategory.setType(NODENAME_PREDEFINED_CATEGORY);
			value.getChildValues().add(this.preDefCategory);
		}
		this.preDefName = InformationUtil.getChildByType(value, NODENAME_PREDEFINED_NAME);
		if (this.preDefName == null) {
			this.preDefName = InfomngmntFactory.eINSTANCE.createRuleValue();
			this.preDefName.setType(NODENAME_PREDEFINED_NAME);
			value.getChildValues().add(this.preDefName);
		}
	}

	@Override
	public void bindValuesToUi() {
		ISWTObservableValue swtCategory = SWTObservables.observeText(this.predefCategoryText,
				SWT.Modify);
		IObservableValue emfCategory = EMFObservables.observeValue(this.preDefCategory,
				InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);
		ISWTObservableValue swtName = SWTObservables.observeText(this.predefNameText, SWT.Modify);
		IObservableValue emfName = EMFObservables.observeValue(this.preDefName,
				InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);
		this.ctx.bindValue(swtCategory, emfCategory, null, null);
		this.ctx.bindValue(swtName, emfName, null, null);
	}
}
