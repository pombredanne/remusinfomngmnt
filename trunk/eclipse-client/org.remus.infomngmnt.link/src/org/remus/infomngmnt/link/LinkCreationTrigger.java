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

package org.remus.infomngmnt.link;

import org.remus.infomngmnt.link.ui.NewLinkWizard;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.remus.rules.RuleValue;
import org.eclipse.remus.ui.rules.transfer.TransferWrapper;
import org.eclipse.remus.ui.rules.wizard.NewObjectWizardDelegate;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class LinkCreationTrigger extends NewObjectWizardDelegate {

	public LinkCreationTrigger() {
		super(new NewLinkWizard());

	}

	@Override
	protected void setDefaults(Object value, RuleValue ruleValue,
			TransferWrapper transferType) throws CoreException {
		// TODO Auto-generated method stub
		super.setDefaults(value, ruleValue, transferType);
	}

}
