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

package org.remus.infomngmnt.eclipsemarketplace.internal;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;

import org.remus.infomngmnt.ui.extension.AbstractInformationFormPage;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class EditMetaDataPage extends AbstractInformationFormPage {

	private FormToolkit toolkit;

	@Override
	protected void renderPage(final IManagedForm managedForm) {
		this.toolkit = managedForm.getToolkit();
		ScrolledForm form = managedForm.getForm();
		Composite body = form.getBody();
		body.setLayout(new GridLayout(1, false));
		// body.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		doCreateSemanticSection(body, this.toolkit);
		form.reflow(true);
	}

}
