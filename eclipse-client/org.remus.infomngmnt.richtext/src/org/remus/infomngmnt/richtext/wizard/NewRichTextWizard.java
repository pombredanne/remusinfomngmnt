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

package org.remus.infomngmnt.richtext.wizard;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;

import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.RuleValue;
import org.remus.infomngmnt.core.extension.TransferWrapper;
import org.remus.infomngmnt.core.model.InformationUtil;
import org.remus.infomngmnt.richtext.RichTextPlugin;
import org.remus.infomngmnt.ui.extension.AbstractCreationPreferencePage;
import org.remus.infomngmnt.ui.newwizards.NewInfoObjectWizard;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class NewRichTextWizard extends NewInfoObjectWizard {

	/**
	 * 
	 */
	public NewRichTextWizard() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.ui.newwizards.NewInfoObjectWizard#getInfoTypeId()
	 */
	@Override
	protected String getInfoTypeId() {
		return RichTextPlugin.TYPE_ID;
	}

	@Override
	public void setDefaults(final Object value, final RuleValue ruleValue,
			final TransferWrapper transferType) {
		this.newElement.setStringValue(String.valueOf(value));
		InformationUnit childByType = InformationUtil.getChildByType(ruleValue,
				AbstractCreationPreferencePage.NODENAME_PREDEFINED_CATEGORY);
		if (childByType != null && childByType.getStringValue() != null) {
			this.page1.setCategoryString(childByType.getStringValue());
		}
	}

	@Override
	public void init(final IWorkbench workbench, final IStructuredSelection selection) {
		super.init(workbench, selection);
		setCategoryToPage();
	}

}