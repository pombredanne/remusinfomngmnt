/*******************************************************************************
 * Copyright (c) 2009 Jan Hartwig, FEB Radebeul
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Jan Hartwig - initial API and implementation
 *******************************************************************************/
package org.remus.infomngmnt.contact.ui.general;
/**
 * @author Jan Hartwig <jhartwig@feb-radebeul.de>
 * 
 */
import java.io.ByteArrayInputStream;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.contact.ContactActivator;
import org.remus.infomngmnt.core.model.InformationUtil;
import org.remus.infomngmnt.ui.extension.AbstractInformationFormPage;

public class EditGeneralPage extends AbstractInformationFormPage {

	public EditGeneralPage() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void createFormContent(final IManagedForm managedForm) {

		FormToolkit toolkit = managedForm.getToolkit();
		ScrolledForm form = managedForm.getForm();
		Composite body = form.getBody();
		body.setLayout(new GridLayout());

		GeneralSection gs = new GeneralSection(body, toolkit, getSite().getShell(), getModelObject(), editingDomain);
		doCreateSemanticSection(body, toolkit);
	}
}
