/*******************************************************************************
 * Copyright (c) 2010 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/

package org.remus.infomngmnt.ui.rules.wizard;

import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;

import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.common.ui.UIUtil;
import org.remus.infomngmnt.ui.newwizards.NewInfoObjectWizard;
import org.remus.infomngmnt.ui.rules.script.DefaultScriptConstants;
import org.remus.infomngmnt.ui.rules.service.ICreationTrigger;
import org.remus.infomngmnt.ui.rules.transfer.TransferWrapper;
import org.remus.rules.RuleValue;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class NewObjectWizardDelegate implements ICreationTrigger {

	protected final NewInfoObjectWizard wrappingWizard;
	private RuleValue ruleValue;
	private TransferWrapper transferType;
	private Object value;
	protected InformationUnit newInformationUnit;
	protected Map options;

	protected NewObjectWizardDelegate(final NewInfoObjectWizard wrappingWizard) {
		this.wrappingWizard = wrappingWizard;
	}

	protected void setDefaults(final Object value, final RuleValue ruleValue,
			final TransferWrapper transferType) throws CoreException {
		this.wrappingWizard.setCategoryString((String) this.options
				.get(DefaultScriptConstants.KEY_CATEGORIES));
	}

	public void handleCreationRequest() {
		try {
			setDefaults(this.value, this.ruleValue, this.transferType);
			this.wrappingWizard.setNewInformationUnit(this.newInformationUnit);
			this.wrappingWizard.init(UIUtil.getPrimaryWindow().getWorkbench(),
					StructuredSelection.EMPTY);
			WizardDialog wizard = new WizardDialog(UIUtil.getPrimaryWindow().getShell(),
					this.wrappingWizard);
			wizard.open();
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setRuleValue(final RuleValue ruleValue) {
		this.ruleValue = ruleValue;

	}

	/**
	 * @param transferType
	 *            the transferType to set
	 */
	public void setTransferType(final TransferWrapper transferType) {
		this.transferType = transferType;
	}

	public void setNewInformationUnit(final InformationUnit newInformationUnit) {
		this.newInformationUnit = newInformationUnit;

	}

	public void setValue(final Object value) {
		this.value = value;

	}

	public void setOptions(final Map options) {
		this.options = options;
	}

}
