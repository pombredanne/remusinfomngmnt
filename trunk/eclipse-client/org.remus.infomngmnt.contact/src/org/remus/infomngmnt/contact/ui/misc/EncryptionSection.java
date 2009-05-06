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
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.common.ui.databinding.BindingWidgetFactory;
import org.remus.infomngmnt.common.ui.databinding.TextBindingWidget;
import org.remus.infomngmnt.contact.ContactActivator;
import org.remus.infomngmnt.core.model.InformationUtil;

public class EncryptionSection {

	private final InformationUnit informationUnit;
	private EditMiscPage editMiscPage;
	private Text tx_OpenGpg;
	private Text tx_Smime;

	public EncryptionSection(Composite body, FormToolkit toolkit, Shell shell, InformationUnit informationUnit, EditMiscPage editMiscPage) {
		
		this.informationUnit = informationUnit;
		this.editMiscPage = editMiscPage;
		
		final Section section_1 = toolkit.createSection(body, ExpandableComposite.TITLE_BAR
				| ExpandableComposite.TWISTIE | ExpandableComposite.EXPANDED);
		section_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		section_1.setText("Encryption");
		
		final Composite compositeEncryption = toolkit.createComposite(section_1, SWT.BORDER);
		final GridLayout gridLayoutEncryption = new GridLayout();
		gridLayoutEncryption.numColumns = 2;
		compositeEncryption.setLayout(gridLayoutEncryption);
		toolkit.paintBordersFor(compositeEncryption);
		section_1.setClient(compositeEncryption);

		createGroupPreferredKeys(compositeEncryption, toolkit);
	}

	private void createGroupPreferredKeys(Composite compositeEncryption,
			FormToolkit toolkit) {
		final Group group_Keys = new Group(compositeEncryption, SWT.NONE);
	    final GridData gd_Keys= new GridData();
	    gd_Keys.grabExcessVerticalSpace = true;
	    gd_Keys.verticalAlignment = GridData.FILL;
	    gd_Keys.grabExcessHorizontalSpace = true;
	    gd_Keys.horizontalAlignment = GridData.FILL;
	    group_Keys.setLayoutData(gd_Keys);
		final GridLayout gl_GroupKeys = new GridLayout();
		gl_GroupKeys.numColumns = 2;
		group_Keys.setLayout(gl_GroupKeys);
		
		GridData gd_text = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		gd_text.horizontalAlignment = SWT.FILL;
		
		toolkit.createLabel(group_Keys, "Preferred OpenPGP Encryption Key:");
		tx_OpenGpg = toolkit.createText(group_Keys, null, SWT.BORDER | SWT.FILL);
		tx_OpenGpg.setLayoutData(gd_text);
		toolkit.createLabel(group_Keys, "Preferred S/MIME Encryption Certificate:");
		tx_Smime = toolkit.createText(group_Keys, null, SWT.BORDER);
		tx_Smime.setLayoutData(gd_text);
		
		createTextValueBindingsMisc();
	}

	private void createTextValueBindingsMisc() {
		
		TextBindingWidget createTextBindingWidget1 = BindingWidgetFactory.createTextBindingWidget(tx_Smime, editMiscPage);
		createTextBindingWidget1.bindModel(InformationUtil.getChildByType(informationUnit, ContactActivator.NODE_MISC_MIME), InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);
		
		TextBindingWidget createTextBindingWidget2 = BindingWidgetFactory.createTextBindingWidget(tx_OpenGpg, editMiscPage);
		createTextBindingWidget2.bindModel(InformationUtil.getChildByType(informationUnit, ContactActivator.NODE_MISC_PUBKEY), InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);
	}
}
