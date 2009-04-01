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
package org.remus.infomngmnt.contact.ui;

 /**
  * @author Jan Hartwig <jhartwig@feb-radebeul.de>
  * 
  */
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

public class CreateDetailsSection {

	public CreateDetailsSection(Composite body, FormToolkit toolkit) {
		final Section section_1 = toolkit.createSection(body, ExpandableComposite.TITLE_BAR
				| ExpandableComposite.TWISTIE | ExpandableComposite.EXPANDED);
		section_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		section_1.setText("Details");
		
		final Composite compositeGeneral = toolkit.createComposite(section_1, SWT.NONE);
		final GridLayout gridLayoutAdditional = new GridLayout();
		gridLayoutAdditional.numColumns = 1;
		compositeGeneral.setLayout(gridLayoutAdditional);
		toolkit.paintBordersFor(compositeGeneral);
		section_1.setClient(compositeGeneral);

		createGroupGeneral(compositeGeneral, toolkit);
		createGroupPerson(compositeGeneral, toolkit);
		createGroupNote(compositeGeneral, toolkit);
	}

	private void createGroupNote(Composite compositeGeneral, FormToolkit toolkit) {
		final Group group_Note = new Group(compositeGeneral, SWT.NONE);
	    final GridData gd_Note= new GridData();
	    gd_Note.grabExcessVerticalSpace = true;
	    gd_Note.verticalAlignment = GridData.FILL;
	    gd_Note.grabExcessHorizontalSpace = true;
	    gd_Note.horizontalAlignment = GridData.FILL;
	    group_Note.setLayoutData(gd_Note);
		final GridLayout gl_General = new GridLayout();
		gl_General.numColumns = 2;
		group_Note.setLayout(gl_General);
		
		final Label lb_Notes = toolkit.createLabel(group_Note, "Notes");
		lb_Notes.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, true));
		
		final StyledText stx_Note = new StyledText(group_Note, SWT.BORDER);
		GridData gd_Address = new GridData();		
		gd_Address.grabExcessVerticalSpace = true;
		gd_Address.verticalAlignment = GridData.FILL;
		gd_Address.grabExcessHorizontalSpace = true;
		gd_Address.horizontalAlignment = GridData.FILL; 
		gd_Address.heightHint = 260;
		stx_Note.setLayoutData(gd_Address);
	}

	private void createGroupPerson(Composite compositeGeneral,
			FormToolkit toolkit) {
		final Group group_Person = new Group(compositeGeneral, SWT.NONE);
	    final GridData gd_Person= new GridData();
	    gd_Person.grabExcessVerticalSpace = true;
	    gd_Person.verticalAlignment = GridData.FILL;
	    gd_Person.grabExcessHorizontalSpace = true;
	    gd_Person.horizontalAlignment = GridData.FILL;
	    group_Person.setLayoutData(gd_Person);
		final GridLayout gl_General = new GridLayout();
		gl_General.numColumns = 4;
		group_Person.setLayout(gl_General);
		
		final Label lb_Nickname = toolkit.createLabel(group_Person, "Nickname:");
		final Text tx_Nickname = toolkit.createText(group_Person, null, SWT.BORDER);
		tx_Nickname.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		
		final Label lb_Birthday = toolkit.createLabel(group_Person, "Birthday:");
		final Text tx_Birthday = toolkit.createText(group_Person, null, SWT.BORDER);
		tx_Birthday.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		
		final Label lb_NamePartner = toolkit.createLabel(group_Person, "Name Of Partner:");
		final Text tx_NamePartner = toolkit.createText(group_Person, null, SWT.BORDER);
		tx_NamePartner.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		
		final Label lb_Jubilee = toolkit.createLabel(group_Person, "Jubilee:");
		final Text tx_Jubilee = toolkit.createText(group_Person, null, SWT.BORDER);
		tx_Jubilee.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		
	}

	private void createGroupGeneral(Composite compositeGeneral,
			FormToolkit toolkit) {
		
		final Group group_General = new Group(compositeGeneral, SWT.NONE);
	    final GridData gd_General= new GridData();
	    gd_General.grabExcessVerticalSpace = true;
	    gd_General.verticalAlignment = GridData.FILL;
	    gd_General.grabExcessHorizontalSpace = true;
	    gd_General.horizontalAlignment = GridData.FILL;
	    group_General.setLayoutData(gd_General);
		final GridLayout gl_General = new GridLayout();
		gl_General.numColumns = 4;
		group_General.setLayout(gl_General);
	   
		final Label lb_Department = toolkit.createLabel(group_General, "Department:");
		final Text tx_Department = toolkit.createText(group_General, null, SWT.BORDER);
		tx_Department.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		
		final Label lb_NameManager = toolkit.createLabel(group_General, "Name Of Manager:");
		final Text tx_NameManager = toolkit.createText(group_General, null, SWT.BORDER);
		tx_NameManager.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		
		final Label lb_Bureau = toolkit.createLabel(group_General, "Bureau:");
		final Text tx_Bureau = toolkit.createText(group_General, null, SWT.BORDER);
		tx_Bureau.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		
		final Label lb_NamesAssistant = toolkit.createLabel(group_General, "Name of Assistant:");
		final Text tx_Assistant = toolkit.createText(group_General, null, SWT.BORDER);
		tx_Assistant.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		
		final Label lb_Job = toolkit.createLabel(group_General, "Job:");
		final Text tx_Job = toolkit.createText(group_General, null, SWT.BORDER);
		tx_Job.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		
		final Label lb_Title = toolkit.createLabel(group_General, "Title:");
		final Text tx_Title = toolkit.createText(group_General, null, SWT.BORDER);
		tx_Title.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
	}

}
