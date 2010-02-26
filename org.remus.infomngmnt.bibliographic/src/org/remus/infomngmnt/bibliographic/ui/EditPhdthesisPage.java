/*******************************************************************************
 * Copyright (c) 2009 Andreas Deinlein
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Andreas Deinlein - bibliographic extensions
 *******************************************************************************/
package org.remus.infomngmnt.bibliographic.ui;

import java.util.ArrayList;

import org.remus.infomngmnt.bibliographic.BibliographicActivator;

/**
 * Edit page for information unit "Phdthesis"
 * 
 * @author Andreas Deinlein <dev@deasw.com>
 *
 */
public class EditPhdthesisPage extends BibliographicAbstractInformationFormPage {
	
	public EditPhdthesisPage() {
		baseTypeId = BibliographicActivator.PHDTHESIS_TYPE_ID;
		requiredFields = new ArrayList<String>();
		requiredFields.add(BibliographicActivator.NODE_NAME_AUTHOR);
		requiredFields.add(BibliographicActivator.NODE_NAME_SCHOOL);
		requiredFields.add(BibliographicActivator.NODE_NAME_YEAR);
		optionalFields = new ArrayList<String>();
		optionalFields.add(BibliographicActivator.NODE_NAME_ADDRESS);
		optionalFields.add(BibliographicActivator.NODE_NAME_MONTH);
		optionalFields.add(BibliographicActivator.NODE_NAME_NOTE);
	}

}
