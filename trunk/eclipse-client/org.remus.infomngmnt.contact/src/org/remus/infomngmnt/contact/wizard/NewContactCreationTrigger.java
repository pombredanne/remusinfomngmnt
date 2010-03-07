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

package org.remus.infomngmnt.contact.wizard;

import org.remus.infomngmnt.ui.rules.service.ICreationTrigger;
import org.remus.infomngmnt.ui.rules.wizard.NewObjectWizardDelegate;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class NewContactCreationTrigger extends NewObjectWizardDelegate implements ICreationTrigger {

	/**
	 * @param wrappingWizard
	 */
	public NewContactCreationTrigger() {
		super(new NewContactWizard());
	}

}
