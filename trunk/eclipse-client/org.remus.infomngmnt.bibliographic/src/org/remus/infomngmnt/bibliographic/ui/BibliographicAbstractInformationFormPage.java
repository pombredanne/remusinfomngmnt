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
import java.util.HashMap;
import java.util.Map;

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
import org.remus.infomngmnt.bibliographic.Messages;
import org.remus.infomngmnt.core.model.InformationStructureRead;
import org.remus.infomngmnt.ui.databinding.BindingUtil;
import org.remus.infomngmnt.ui.editors.editpage.AbstractInformationFormPage;

public abstract class BibliographicAbstractInformationFormPage extends AbstractInformationFormPage {
	
	protected InformationStructureRead read;
	
	protected String baseTypeId;	// Information Id of Subtype: book, article, ... - set in constructor of subtypte
	
	private Text title;
	private Text bibtexkey;
	protected ArrayList<String>	requiredFields = null;
	protected Map<String, Text> requiredEditFields = null;
	protected ArrayList<String>	optionalFields = null;
	protected Map<String, Text> optionalEditFields = null;
	
	
	@Override
	protected void renderPage(IManagedForm managedForm) {
		FormToolkit toolkit = managedForm.getToolkit();
		ScrolledForm form = managedForm.getForm();
		Composite body = form.getBody();
		body.setLayout(new GridLayout());
		toolkit.paintBordersFor(body);
		
		// Required fields section
		Composite requiredSection = createBibliographicSection(managedForm, Messages.getString("requiredFieldsSection"), true);
		title = createBibliographicEditField(managedForm, requiredSection, Messages.getString("titleLabel"));
		if (requiredFields != null) {
			requiredEditFields = new HashMap<String, Text>();			
			for (String fieldId : requiredFields) {
				Text t = createBibliographicEditField(managedForm, requiredSection, Messages.getString(fieldId + "Label") + ":");	//$NON-NLS-1$
				t.setToolTipText(Messages.getString(fieldId + "Tip"));	//$NON-NLS-1$
				requiredEditFields.put(fieldId, t);  //$NON-NLS-1$
			}			
		}
		bibtexkey = createBibliographicEditField(managedForm, requiredSection, Messages.getString("bibtexkeyLabel"));
		
		// Optional fields section
		Composite optionalSection = createBibliographicSection(managedForm, Messages.getString("optionalFieldsSection"), true);
		if (optionalFields != null) {
			optionalEditFields = new HashMap<String, Text>();
			for (String fieldId : optionalFields) {
				Text t = createBibliographicEditField(managedForm, optionalSection, Messages.getString(fieldId + "Label") + ":"); 	//$NON-NLS-1$
				t.setToolTipText(Messages.getString(fieldId + "Tip"));	//$NON-NLS-1$
				optionalEditFields.put(fieldId, t);  //$NON-NLS-1$
			}
		}			
		
		// Semantic Section
		doCreateSemanticSection(body, toolkit);
		
		form.reflow(true);
	}
		
	
	/**
	 * bind values
	 */
	public void bindValuesToUi() {
		super.bindValuesToUi();
		read = InformationStructureRead.newSession(getModelObject());
		
		BindingUtil.createTextAndBind(this.title, getModelObject(),
				InfomngmntPackage.Literals.ABSTRACT_INFORMATION_UNIT__LABEL, this);
		
		bindBibliographicField(this.bibtexkey, BibliographicActivator.NODE_NAME_BIBTEXKEY);
		
		// Required fields
		if (requiredFields != null) {
			for (String fieldId : requiredFields) {
				bindBibliographicField(requiredEditFields.get(fieldId), fieldId); 
			}
		}
		
		// Optional fields
		if (optionalFields != null) {
			for (String fieldId : optionalFields) {
				bindBibliographicField(optionalEditFields.get(fieldId), fieldId); 
			}
		}				
	}
	
	
	/**
	 * Helper method for binding
	 * @param field
	 * @param name
	 */
	protected void bindBibliographicField(Text field, String name) {
		BindingUtil.createTextAndBind(field, 
				read.getChildByNodeId(name), 
				read.getFeatureByNodeId(name), this);
	}
	
	/**
	 * Helper method for creating a section
	 * @param managedForm
	 * @param name
	 * @return
	 */
	protected Composite createBibliographicSection(IManagedForm managedForm, String name, boolean expanded) {
		FormToolkit toolkit = managedForm.getToolkit();
		ScrolledForm form = managedForm.getForm();
		Composite body = form.getBody();
		final Section generalSection;
		if (expanded) {
			generalSection = toolkit.createSection(body, ExpandableComposite.TITLE_BAR
					| ExpandableComposite.EXPANDED | ExpandableComposite.TWISTIE);
		} else {
			generalSection = toolkit.createSection(body, ExpandableComposite.TITLE_BAR
					| ExpandableComposite.TWISTIE);
		}		
		final GridData gd_generalSection = new GridData(SWT.FILL, SWT.FILL, true, true);
		generalSection.setLayoutData(gd_generalSection);
		generalSection.setText(name);

		Composite client = toolkit.createComposite(generalSection, SWT.NONE);
		client.setLayout(new GridLayout(2, false));
		GridData gridData = new GridData(SWT.FILL, SWT.BEGINNING, true, false);

		client.setLayoutData(gridData);
		generalSection.setClient(client);

		return client;
	}
	
	/**
	 * Helper method that creates a Edit Field 
	 * 
	 * @param managedForm	IManagedForm instance
	 * @param client		client Section 
	 * @param label			String for Label
	 * @return				the generated Edit Field
	 */
	protected Text createBibliographicEditField(IManagedForm managedForm, Composite client, String label) {
		Text field;
		FormToolkit toolkit = managedForm.getToolkit();
		Label tmpLabel = toolkit.createLabel(client, label);
		tmpLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		field = toolkit.createText(client, "", SWT.BORDER); //$NON-NLS-1$
		field.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		addControl(field);
		return field;
	}
	
}
