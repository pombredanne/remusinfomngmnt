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
package org.remus.infomngmnt.contact.ui.misc;

 /**
  * @author Jan Hartwig <jhartwig@feb-radebeul.de>
  * 
  */
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.remus.infomngmnt.InformationUnit;

public class UserDefinedSection {

	public UserDefinedSection(Composite body, FormToolkit toolkit, Shell shell, InformationUnit informationUnit, AdapterFactoryEditingDomain adapterFactoryEditingDomain) {
		final Section section_1 = toolkit.createSection(body, ExpandableComposite.TITLE_BAR
				| ExpandableComposite.TWISTIE);
		section_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		section_1.setText("User Defined Fields");
		
		final Composite compositeGeneral = toolkit.createComposite(section_1, SWT.BORDER);
		final GridLayout gridLayoutAdditional = new GridLayout();
		gridLayoutAdditional.numColumns = 1;
		compositeGeneral.setLayout(gridLayoutAdditional);
		toolkit.paintBordersFor(compositeGeneral);
		section_1.setClient(compositeGeneral);

		createGroupUserDefined(compositeGeneral, toolkit, shell, informationUnit, adapterFactoryEditingDomain);
		createGroupButtons(compositeGeneral, toolkit);
	}
	private void createGroupUserDefined(Composite compositeGeneral, FormToolkit toolkit, final Shell shell, final InformationUnit informationUnit, final AdapterFactoryEditingDomain adapterFactoryEditingDomain) {
		
		
		final Group group_UserDefined = new Group(compositeGeneral, SWT.NONE);
	    final GridData gd_UserDefined= new GridData();
	    gd_UserDefined.grabExcessVerticalSpace = true;
	    gd_UserDefined.verticalAlignment = GridData.FILL;
	    gd_UserDefined.grabExcessHorizontalSpace = true;
	    gd_UserDefined.horizontalAlignment = GridData.FILL;
	    group_UserDefined.setLayoutData(gd_UserDefined);
		final GridLayout gl_UserDefinedGroup = new GridLayout();
		gl_UserDefinedGroup.numColumns = 1;
		group_UserDefined.setLayout(gl_UserDefinedGroup);
		
	    final Label lb_Test = new Label(group_UserDefined, SWT.NONE);
	    lb_Test.setText("dummy");
	    
	    final Group group_Buttons = new Group(group_UserDefined, SWT.NONE);
	    group_Buttons.setLayoutData(gd_UserDefined);
		final GridLayout gl_ButtonGroup = new GridLayout();
		gl_ButtonGroup.numColumns = 2;
		group_Buttons.setLayout(gl_ButtonGroup);
		group_Buttons.setLayoutData(new GridData(SWT.TOP, SWT.END, true, false));
		
		final Button bt_CreateField = toolkit.createButton(group_Buttons, "Create Field ...", SWT.NONE);
		final Button bt_DeleteField = toolkit.createButton(group_Buttons, "Delete Field ...", SWT.NONE);
		
		bt_CreateField.addListener(SWT.Selection, new Listener() {

			public void handleEvent(final Event event) {
				UserDefinedFieldGenerationDialog dialog = new UserDefinedFieldGenerationDialog(shell, informationUnit, adapterFactoryEditingDomain);
				dialog.open();
			}
		});	
		bt_DeleteField.addListener(SWT.Selection, new Listener() {

			public void handleEvent(final Event event) {
				UserDefinedFieldDeletionDialog dialog = new UserDefinedFieldDeletionDialog(shell, informationUnit, adapterFactoryEditingDomain);
				dialog.open();
			}
		});
	}
	private void createGroupButtons(Composite compositeGeneral,
			FormToolkit toolkit) {
		final Composite composite_CreateDetailButtons = toolkit.createComposite(compositeGeneral, SWT.NONE);
		final GridLayout gl_CreateDetailButtons = new GridLayout();
		gl_CreateDetailButtons.numColumns = 5;
		composite_CreateDetailButtons.setLayoutData( new GridData(SWT.FILL, SWT.END, true, false));
		composite_CreateDetailButtons.setLayout(gl_CreateDetailButtons);
		
		final Label lb = new Label(composite_CreateDetailButtons, SWT.NONE);
		GridData gd_text = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		gd_text.horizontalSpan = 2;
		lb.setLayoutData(gd_text);
		final Button bt_Ok = toolkit.createButton(composite_CreateDetailButtons, "OK", SWT.NONE);
		final Button bt_Apply = toolkit.createButton(composite_CreateDetailButtons, "Apply", SWT.NONE);
		final Button bt_Cancel = toolkit.createButton(composite_CreateDetailButtons, "Cancel", SWT.NONE);
	}
}
