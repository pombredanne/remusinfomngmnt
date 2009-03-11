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

package org.remus.infomngmnt.ui.calendar;

import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;

import org.remus.infomngmnt.ui.editors.InformationFormPage;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class CalendarPage extends InformationFormPage {

	public CalendarPage(final FormEditor editor, final String id, final String title) {
		super(editor, id, title);
	}

	
	@Override
	protected void createFormContent(final IManagedForm managedForm) {
		
		super.createFormContent(managedForm);
	}
	
}
