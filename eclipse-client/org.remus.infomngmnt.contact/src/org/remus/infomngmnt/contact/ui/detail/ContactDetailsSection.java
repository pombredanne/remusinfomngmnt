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
package org.remus.infomngmnt.contact.ui.detail;
 /**
  * @author Jan Hartwig <jhartwig@feb-radebeul.de>
  * 
  */
import java.util.Calendar;

import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.databinding.EMFDataBindingContext;
import org.eclipse.emf.databinding.edit.EMFEditObservables;
import org.eclipse.jface.databinding.swt.ISWTObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.nebula.widgets.calendarcombo.CalendarCombo;
import org.eclipse.nebula.widgets.calendarcombo.CalendarListenerAdapter;
import org.eclipse.nebula.widgets.calendarcombo.DefaultSettings;
import org.eclipse.nebula.widgets.calendarcombo.ISettings;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.common.ui.databinding.BindingWidgetFactory;
import org.remus.infomngmnt.common.ui.databinding.CalendarComboModelToTarget;
import org.remus.infomngmnt.common.ui.databinding.CalendarComboTargetToModel;
import org.remus.infomngmnt.common.ui.databinding.TextBindingWidget;
import org.remus.infomngmnt.contact.ContactActivator;
import org.remus.infomngmnt.core.model.InformationUtil;
import org.remus.infomngmnt.search.Search;

public class ContactDetailsSection {

	private Text tx_Department;
	private Text tx_NameManager;
	private Text tx_Bureau;
	private Text tx_Assistant;
	private Text tx_Job;
	private Text tx_Title;
	private Text tx_Nickname;
	private CalendarCombo calcb_Birthday;
	private Text tx_NamePartner;
	private CalendarCombo calcb_Jubilee;
	private Text tx_Note;
	
	private final ISettings calendarSettings = new DefaultSettings();
	private EMFDataBindingContext ctx;
	private Search currentSearch;
	private InformationUnit informationUnit;
	private EditDetailPage editDetailPage;
	
	public ContactDetailsSection(Composite body, FormToolkit toolkit, Shell shell, InformationUnit informationUnit, EditDetailPage editDetailPage ) {
		
		this.informationUnit = informationUnit;
		this.editDetailPage = editDetailPage;
		
		final Section section_1 = toolkit.createSection(body, ExpandableComposite.TITLE_BAR
				| ExpandableComposite.TWISTIE | ExpandableComposite.EXPANDED);
		section_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		section_1.setText("Details");
		
		final Composite compositeGeneral = toolkit.createComposite(section_1, SWT.BORDER);
		final GridLayout gridLayoutAdditional = new GridLayout();
		gridLayoutAdditional.numColumns = 1;
		compositeGeneral.setLayout(gridLayoutAdditional);
		toolkit.paintBordersFor(compositeGeneral);
		section_1.setClient(compositeGeneral);

		createGroupGeneral(compositeGeneral, toolkit);
		createSeparator(compositeGeneral, true, 1);
		createGroupPerson(compositeGeneral, toolkit);
		createSeparator(compositeGeneral, true, 1);
		createGroupNote(compositeGeneral, toolkit);

		createTextValueBindingsSectionDetails();
	}

	private void createTextValueBindingsSectionDetails() {
		TextBindingWidget createTextBindingWidget1 = BindingWidgetFactory.createTextBindingWidget(tx_Assistant, editDetailPage);
		createTextBindingWidget1.bindModel(InformationUtil.getChildByType(informationUnit, ContactActivator.NODE_DETAILS_NAME_ASSISTANT), InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);
				
		TextBindingWidget createTextBindingWidget3 = BindingWidgetFactory.createTextBindingWidget(tx_Bureau, editDetailPage);
		createTextBindingWidget3.bindModel(InformationUtil.getChildByType(informationUnit, ContactActivator.NODE_DETAILS_BUREAU), InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);

		TextBindingWidget createTextBindingWidget4 = BindingWidgetFactory.createTextBindingWidget(tx_Department, editDetailPage);
		createTextBindingWidget4.bindModel(InformationUtil.getChildByType(informationUnit, ContactActivator.NODE_DETAILS_DEPARTMENT), InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);
		
		TextBindingWidget createTextBindingWidget6 = BindingWidgetFactory.createTextBindingWidget(tx_NameManager, editDetailPage);
		createTextBindingWidget6.bindModel(InformationUtil.getChildByType(informationUnit, ContactActivator.NODE_DETAILS_NAME_MANAGER), InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);

		TextBindingWidget createTextBindingWidget7 = BindingWidgetFactory.createTextBindingWidget(tx_NamePartner, editDetailPage);
		createTextBindingWidget7.bindModel(InformationUtil.getChildByType(informationUnit, ContactActivator.NODE_DETAILS_NAME_PARTNER), InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);
		
		TextBindingWidget createTextBindingWidget8 = BindingWidgetFactory.createTextBindingWidget(tx_Nickname, editDetailPage);
		createTextBindingWidget8.bindModel(InformationUtil.getChildByType(informationUnit, ContactActivator.NODE_DETAILS_NAME_NICK), InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);
		
		TextBindingWidget createTextBindingWidget9 = BindingWidgetFactory.createTextBindingWidget(tx_Note, editDetailPage);
		createTextBindingWidget9.bindModel(InformationUtil.getChildByType(informationUnit, ContactActivator.NODE_DETAILS_NOTES), InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);

		TextBindingWidget createTextBindingWidget10 = BindingWidgetFactory.createTextBindingWidget(tx_Job, editDetailPage);
		createTextBindingWidget10.bindModel(InformationUtil.getChildByType(informationUnit, ContactActivator.NODE_DETAILS_JOB), InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);
		
		TextBindingWidget createTextBindingWidget11 = BindingWidgetFactory.createTextBindingWidget(tx_Title, editDetailPage);
		createTextBindingWidget11.bindModel(InformationUtil.getChildByType(informationUnit, ContactActivator.NODE_DETAILS_TITLE), InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);
	
		this.ctx = new EMFDataBindingContext();	
		IObservableValue mObsBirthday = EMFEditObservables.observeValue(Realm.getDefault(),	editDetailPage.getEditingDomain(), this.currentSearch, InfomngmntPackage.Literals.INFORMATION_UNIT__DATE_VALUE);
		ISWTObservableValue uObsBirthday = SWTObservables.observeText(calcb_Birthday.getCombo());
		this.ctx.bindValue(uObsBirthday, mObsBirthday, new CalendarComboTargetToModel(calcb_Birthday,
				this.calendarSettings), new CalendarComboModelToTarget(calcb_Birthday, this.calendarSettings));
		
//		IObservableValue mObsJubilee = EMFEditObservables.observeValue(Realm.getDefault(),	editDetailPage.getEditingDomain(), this.currentSearch, InfomngmntPackage.Literals.INFORMATION_UNIT__DATE_VALUE);
//		ISWTObservableValue uObsJubilee = SWTObservables.observeText(calcb_Jubilee.getCombo());
//		this.ctx.bindValue(uObsJubilee, mObsJubilee, new CalendarComboTargetToModel(calcb_Jubilee,
//				this.calendarSettings), new CalendarComboModelToTarget(calcb_Jubilee, this.calendarSettings));

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
		
		tx_Note = toolkit.createText(group_Note, null, SWT.BORDER | SWT.MULTI);
		GridData gd_Address = new GridData();		
		gd_Address.grabExcessVerticalSpace = true;
		gd_Address.verticalAlignment = GridData.FILL;
		gd_Address.grabExcessHorizontalSpace = true;
		gd_Address.horizontalAlignment = GridData.FILL; 
		gd_Address.heightHint = 260;
		tx_Note.setLayoutData(gd_Address);
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
		
		toolkit.createLabel(group_Person, "Nickname:");
		tx_Nickname = toolkit.createText(group_Person, null, SWT.BORDER);
		tx_Nickname.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		
		toolkit.createLabel(group_Person, "Birthday:");
		
		calcb_Birthday = new CalendarCombo(group_Person, SWT.READ_ONLY, calendarSettings, null, true);
//		calcb_Birthday.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
//		calcb_Birthday = new CalendarCombo(group_Person, SWT.READ_ONLY | SWT.FLAT);
//		calcb_Birthday.addCalendarListener(new CalendarListenerAdapter() {
//			public void dateChanged(Calendar date) {
//				if (date != null) {
//					System.err.println(date.getTime().toString());
//				
//					try {
//						String str = calcb_Birthday.getCombo().getText();
//						TextBindingWidget createTextBindingWidget11 = BindingWidgetFactory.createTextBindingWidget(str, editDetailPage);
//						createTextBindingWidget11.bindModel(InformationUtil.getChildByType(informationUnit, ContactActivator.NODE_DETAILS_BIRTHDAY), InfomngmntPackage.Literals.INFORMATION_UNIT__DATE_VALUE);						
//					} catch (Exception e) {
//						// TODO: handle exception
//					}
//				
//				}
//				else {
//					System.err.println("null");
//				}
//			}
//		});
		
		
		toolkit.createLabel(group_Person, "Name Of Partner:");
		tx_NamePartner = toolkit.createText(group_Person, null, SWT.BORDER);
		tx_NamePartner.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		
//		toolkit.createLabel(group_Person, "Jubilee:");
//		calcb_Jubilee = new CalendarCombo(group_Person, SWT.DROP_DOWN, calendarSettings, null, true);
//		calcb_Jubilee.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		
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
	   
		toolkit.createLabel(group_General, "Department:");
		tx_Department = toolkit.createText(group_General, null, SWT.BORDER);
		tx_Department.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		
		toolkit.createLabel(group_General, "Name Of Manager:");
		tx_NameManager = toolkit.createText(group_General, null, SWT.BORDER);
		tx_NameManager.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		
		toolkit.createLabel(group_General, "Bureau:");
		tx_Bureau = toolkit.createText(group_General, null, SWT.BORDER);
		tx_Bureau.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		
		toolkit.createLabel(group_General, "Name of Assistant:");
		tx_Assistant = toolkit.createText(group_General, null, SWT.BORDER);
		tx_Assistant.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		
		toolkit.createLabel(group_General, "Job:");
		tx_Job = toolkit.createText(group_General, null, SWT.BORDER);
		tx_Job.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		
		toolkit.createLabel(group_General, "Title:");
		tx_Title = toolkit.createText(group_General, null, SWT.BORDER);
		tx_Title.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
	}
	public void createSeparator(Composite compositeGeneral, boolean isHorizontal, int span) {
		final Label lb_Separator = new Label(compositeGeneral, SWT.SEPARATOR | SWT.HORIZONTAL);
		GridData gd_text = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		if (isHorizontal) {
			gd_text.horizontalSpan = span;
		}else
			gd_text.verticalSpan = span;
			
		lb_Separator.setLayoutData(gd_text);		
	}
}
