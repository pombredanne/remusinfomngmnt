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

import org.eclipse.swt.widgets.Composite;

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.ui.newwizards.GeneralPage;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class GeneralRichTextWizardPage extends GeneralPage {

	public GeneralRichTextWizardPage(final Category category) {
		super(category);

	}

	public GeneralRichTextWizardPage(final InformationUnitListItem selection) {
		super(selection);
	}
	
	@Override
	public void createControl(final Composite parent) {
		// TODO Auto-generated method stub
		super.createControl(parent);
	}

}
