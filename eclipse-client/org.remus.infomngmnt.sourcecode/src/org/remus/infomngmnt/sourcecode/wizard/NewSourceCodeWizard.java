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

package org.remus.infomngmnt.sourcecode.wizard;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.RuleValue;
import org.remus.infomngmnt.core.model.InformationUtil;
import org.remus.infomngmnt.sourcecode.SourceCodePlugin;
import org.remus.infomngmnt.sourcecode.prefs.SourceCodeRulePreferencePage;
import org.remus.infomngmnt.ui.extension.AbstractCreationPreferencePage;
import org.remus.infomngmnt.ui.newwizards.NewInfoObjectWizard;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class NewSourceCodeWizard extends NewInfoObjectWizard {

	/**
	 * 
	 */
	public NewSourceCodeWizard() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected InformationUnit createNewInformationUnit() {
		InformationUnit informationUnit = super.createNewInformationUnit();
		informationUnit.setType(SourceCodePlugin.TYPE_ID);

		InformationUnit sourceCodeType = InfomngmntFactory.eINSTANCE.createInformationUnit();
		sourceCodeType.setType(SourceCodePlugin.SRCTYPE_NAME);
		informationUnit.getChildValues().add(sourceCodeType);
		return informationUnit;
	}

	public void setDefaults(Object value, RuleValue ruleValue) {
		this.newElement.setStringValue(String.valueOf(value));
		InformationUnit childByType = InformationUtil.getChildByType(
				ruleValue, AbstractCreationPreferencePage.NODENAME_PREDEFINED_CATEGORY);
		if (childByType != null) {
			this.page1.setCategoryString(childByType.getStringValue());
		}
		childByType = InformationUtil.getChildByType(
				ruleValue, SourceCodeRulePreferencePage.NODENAME_PREDEFINED_SOURCETYPE);
		if (childByType != null) {
			InformationUtil.getChildByType(this.newElement, SourceCodePlugin.SRCTYPE_NAME)
			.setStringValue(childByType.getStringValue());
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench, org.eclipse.jface.viewers.IStructuredSelection)
	 */
	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		Object firstElement = selection.getFirstElement();
		if (firstElement instanceof Category) {
			this.page1 = new GeneralSourcePage((Category) firstElement);
		} else if (firstElement instanceof InformationUnitListItem) {
			this.page1 = new GeneralSourcePage((InformationUnitListItem) firstElement);
		} else {
			this.page1 = new GeneralSourcePage((Category)null);
		}

	}

}
