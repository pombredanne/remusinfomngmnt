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

package org.remus.infomngmnt.mail.rules;

import org.eclipse.remus.ui.rules.service.ICreationTrigger;
import org.eclipse.remus.ui.rules.wizard.NewObjectWizardDelegate;

import org.remus.infomngmnt.mail.ui.NewMailWizard;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class MailCreationTrigger extends NewObjectWizardDelegate implements ICreationTrigger {

	/**
	 * @param wrappingWizard
	 */
	public MailCreationTrigger() {
		super(new NewMailWizard());
	}

}
