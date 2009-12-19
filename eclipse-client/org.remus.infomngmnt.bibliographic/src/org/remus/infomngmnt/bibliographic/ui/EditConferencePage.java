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
 * Edit page for information unit "Conference"
 * 
 * @author Andreas Deinlein <dev@deasw.com>
 *
 */
public class EditConferencePage extends BibliographicAbstractInformationFormPage {
	
	public EditConferencePage() {
		baseTypeId = BibliographicActivator.CONFERENCE_TYPE_ID;
		requiredFields = new ArrayList<String>();
		requiredFields.add(BibliographicActivator.NODE_NAME_AUTHOR);
		requiredFields.add(BibliographicActivator.NODE_NAME_BOOKTITLE);
		requiredFields.add(BibliographicActivator.NODE_NAME_YEAR);
		optionalFields = new ArrayList<String>();
		optionalFields.add(BibliographicActivator.NODE_NAME_EDITOR);
		optionalFields.add(BibliographicActivator.NODE_NAME_PAGES);
		optionalFields.add(BibliographicActivator.NODE_NAME_ORGANIZATION);
		optionalFields.add(BibliographicActivator.NODE_NAME_PUBLISHER);
		optionalFields.add(BibliographicActivator.NODE_NAME_ADDRESS);
		optionalFields.add(BibliographicActivator.NODE_NAME_MONTH);
		optionalFields.add(BibliographicActivator.NODE_NAME_NOTE);
	}

}
