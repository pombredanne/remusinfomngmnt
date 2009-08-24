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

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.bibliographic.BibliographicActivator;
import org.remus.infomngmnt.common.ui.databinding.BindingUtil;
import org.remus.infomngmnt.core.model.InformationStructureRead;
import org.remus.infomngmnt.ui.extension.AbstractInformationFormPage;

/**
 * Edit page for information unit "Article"
 * 
 * @author Andreas Deinlein <dev@deasw.com>
 *
 */
public class ArticleEditPage extends AbstractInformationFormPage {

	private Text title;
	private Text author;
	private Text journal;
	private Text year;
	
	public ArticleEditPage() {
		
	}
	
	@Override
	protected void renderPage(IManagedForm managedForm) {
		FormToolkit toolkit = managedForm.getToolkit();
		ScrolledForm form = managedForm.getForm();
		Composite body = form.getBody();
		body.setLayout(new GridLayout());
		toolkit.paintBordersFor(body);

		final Section generalSection = toolkit.createSection(body, ExpandableComposite.TITLE_BAR
				| ExpandableComposite.EXPANDED | ExpandableComposite.TWISTIE);
		final GridData gd_generalSection = new GridData(SWT.FILL, SWT.FILL, true, true);
		generalSection.setLayoutData(gd_generalSection);
		generalSection.setText("General");

		final Composite client = toolkit.createComposite(generalSection, SWT.NONE);
		client.setLayout(new GridLayout(2, false));
		GridData gridData = new GridData(SWT.FILL, SWT.BEGINNING, true, false);

		client.setLayoutData(gridData);

		generalSection.setClient(client);

		Label titleLabel = toolkit.createLabel(client, "Title");
		titleLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		this.title = toolkit.createText(client, "", SWT.BORDER);
		this.title.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		addControl(this.title);
		
		Label authorLabel = toolkit.createLabel(client, "Author");
		authorLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		this.author = toolkit.createText(client, "", SWT.BORDER);
		this.author.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		addControl(this.author);
		
		Label publisherLabel = toolkit.createLabel(client, "Journal");
		publisherLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		this.journal = toolkit.createText(client, "", SWT.BORDER);
		this.journal.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		addControl(this.journal);
		
		Label yearLabel = toolkit.createLabel(client, "Year");
		yearLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		this.year = toolkit.createText(client, "", SWT.BORDER);
		this.year.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		addControl(this.year);
		
		
		doCreateSemanticSection(body, toolkit);
		
		form.reflow(true);
	}
	
	@Override
	public void bindValuesToUi() {
		super.bindValuesToUi();
		InformationStructureRead read = InformationStructureRead.newSession(getModelObject());
		BindingUtil.createTextAndBind(this.title, getModelObject(),
				InfomngmntPackage.Literals.ABSTRACT_INFORMATION_UNIT__LABEL, this);
		BindingUtil.createTextAndBind(this.author, 
				read.getChildByNodeId(BibliographicActivator.NODE_NAME_AUTHOR), 
				read.getFeatureByNodeId(BibliographicActivator.NODE_NAME_AUTHOR), this);
		BindingUtil.createTextAndBind(this.journal, 
				read.getChildByNodeId(BibliographicActivator.NODE_NAME_JOURNAL), 
				read.getFeatureByNodeId(BibliographicActivator.NODE_NAME_JOURNAL), this);
		BindingUtil.createTextAndBind(this.year, 
				read.getChildByNodeId(BibliographicActivator.NODE_NAME_YEAR), 
				read.getFeatureByNodeId(BibliographicActivator.NODE_NAME_YEAR), this);
	}
	

}
