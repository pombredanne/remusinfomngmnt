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

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.bibliographic.BibliographicActivator;
import org.remus.infomngmnt.common.ui.databinding.BindingUtil;

/**
 * Edit page for information unit "Book"
 * 
 * @author Andreas Deinlein <dev@deasw.com>
 *
 */
public class BookEditPage extends BibliographicAbstractInformationFormPage {
	// required fields
	private Text title;
	private Text author;
	private Text publisher;
	private Text year;
	private Text bibtexkey;
	// optional fields
	private Text volume;
	private Text series;
	private Text address;
	private Text edition;
	private Text month;
	private Text pages;
	private Text note;
	
	
	public BookEditPage() {
		baseTypeId = BibliographicActivator.BOOK_TYPE_ID;
	}
	
	
	@Override
	protected void renderPage(IManagedForm managedForm) {
		FormToolkit toolkit = managedForm.getToolkit();
		ScrolledForm form = managedForm.getForm();
		Composite body = form.getBody();
		body.setLayout(new GridLayout());
		toolkit.paintBordersFor(body);
		
		// Required fields section
		Composite requiredSection = createBibliographicSection(managedForm, "Required Fields", true);
		title = createBibliographicEditField(managedForm, requiredSection, "Title:"); 
		author = createBibliographicEditField(managedForm, requiredSection, "Author:");
		publisher = createBibliographicEditField(managedForm, requiredSection, "Publisher: ");
		year = createBibliographicEditField(managedForm, requiredSection, "Year:");
		bibtexkey = createBibliographicEditField(managedForm, requiredSection, "Bibtexkey:");
		
		// Optional fields section
		Composite optionalSection = createBibliographicSection(managedForm, "Optional Fields", false);
		volume = createBibliographicEditField(managedForm, optionalSection, "Volume:    ");
		volume.setToolTipText("The volume of a journal or multi-volume book");
		series = createBibliographicEditField(managedForm, optionalSection, "Series:");
		address = createBibliographicEditField(managedForm, optionalSection, "Address:");
		edition = createBibliographicEditField(managedForm, optionalSection, "Edition:");
		month = createBibliographicEditField(managedForm, optionalSection, "Month:");				
		pages = createBibliographicEditField(managedForm, optionalSection, "Pages:");
		note = createBibliographicEditField(managedForm, optionalSection, "Note:");
				
		// Abstract Section
		doCreateAbstractSection(body, toolkit, false);
		
		// ExternalLinks Section
		doCreateExtLinksSection(body, toolkit, false);
		
		// Semantic Section
		doCreateSemanticSection(body, toolkit);
		
		form.reflow(true);
	}
	
	
	@Override
	public void bindValuesToUi() {
		super.bindValuesToUi();
		BindingUtil.createTextAndBind(this.title, getModelObject(),
				InfomngmntPackage.Literals.ABSTRACT_INFORMATION_UNIT__LABEL, this);
		
		bindBibliographicField(this.author, BibliographicActivator.NODE_NAME_AUTHOR);
		bindBibliographicField(this.publisher, BibliographicActivator.NODE_NAME_PUBLISHER);
		bindBibliographicField(this.year, BibliographicActivator.NODE_NAME_YEAR);
		bindBibliographicField(this.bibtexkey, BibliographicActivator.NODE_NAME_BIBTEXKEY);
		
		bindBibliographicField(this.volume, BibliographicActivator.NODE_NAME_VOLUME);
		bindBibliographicField(this.series, BibliographicActivator.NODE_NAME_SERIES);
		bindBibliographicField(this.address, BibliographicActivator.NODE_NAME_ADDRESS);
		bindBibliographicField(this.edition, BibliographicActivator.NODE_NAME_EDITION);
		bindBibliographicField(this.month, BibliographicActivator.NODE_NAME_MONTH);		
		bindBibliographicField(this.pages, BibliographicActivator.NODE_NAME_PAGES);
		bindBibliographicField(this.note, BibliographicActivator.NODE_NAME_NOTE);
	}

}
