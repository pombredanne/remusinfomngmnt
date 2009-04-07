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

import java.io.ByteArrayInputStream;

import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.contact.ContactActivator;
import org.remus.infomngmnt.contact.core.ImageManipulation;
import org.remus.infomngmnt.core.model.InformationUtil;

public class MiscellaneousSection {

	public MiscellaneousSection(Composite body, FormToolkit toolkit, Shell shell, InformationUnit informationUnit, AdapterFactoryEditingDomain editingDomain) {
		final Section section_1 = toolkit.createSection(body, ExpandableComposite.TITLE_BAR
				| ExpandableComposite.TWISTIE);
		section_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		section_1.setText("Miscellaneous");
		
		final Composite compositeGeneral = toolkit.createComposite(section_1, SWT.BORDER);
		final GridLayout gridLayoutAdditional = new GridLayout();
		gridLayoutAdditional.numColumns = 2;
		compositeGeneral.setLayout(gridLayoutAdditional);
		toolkit.paintBordersFor(compositeGeneral);
		section_1.setClient(compositeGeneral);

		createGroupImage("Logo", compositeGeneral, toolkit, shell, informationUnit, editingDomain);
		createGroupGeoDatas(compositeGeneral, toolkit);
		createSeparator(compositeGeneral, true, 2);
		new Label(compositeGeneral, SWT.NONE);
		createGroupButtons(compositeGeneral, toolkit);
	}

	private void createGroupGeoDatas(Composite compositeGeneral,
			FormToolkit toolkit) {
		final Group group_Geo = new Group(compositeGeneral, SWT.NONE);
	    final GridData gd_Geo= new GridData();
	    gd_Geo.grabExcessVerticalSpace = true;
	    gd_Geo.verticalAlignment = GridData.FILL;
	    gd_Geo.grabExcessHorizontalSpace = true;
	    gd_Geo.horizontalAlignment = GridData.FILL;
	    group_Geo.setLayoutData(gd_Geo);
		final GridLayout gl_GeoGroup = new GridLayout();
		gl_GeoGroup.numColumns = 3;
		group_Geo.setLayout(gl_GeoGroup);
		
		new Label(group_Geo, SWT.NONE);
		final Button ck_UseGeoData = toolkit.createButton(group_Geo, "Use Geodata", SWT.CHECK);
		new Label(group_Geo, SWT.NONE);
		new Label(group_Geo, SWT.NONE);
		toolkit.createLabel(group_Geo, "Width");
		final Spinner sp_Width = new Spinner(group_Geo, SWT.BORDER);
		GridData gd_Spinner = new GridData(SWT.FILL, SWT.TOP, true, true);
		gd_Spinner.widthHint = 30;
		sp_Width.setLayoutData(gd_Spinner);
		sp_Width.setMinimum(1);
		new Label(group_Geo, SWT.NONE);
		toolkit.createLabel(group_Geo, "Length");
		final Spinner sp_Length = new Spinner(group_Geo, SWT.BORDER);
		sp_Length.setLayoutData(gd_Spinner);
		sp_Length.setMinimum(1);
		toolkit.createLabel(group_Geo, null);
		final Button bt_EditGeoData = toolkit.createButton(group_Geo, "Edit Geodatas ...", SWT.NONE);
		GridData gd_text = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		gd_text.horizontalSpan = 2;
		bt_EditGeoData.setLayoutData(gd_text);
		
	}

	private void createSeparator(Composite compositeGeneral, boolean isHorizontal, int span) {
		final Label lb_Separator = new Label(compositeGeneral, SWT.SEPARATOR | SWT.HORIZONTAL);
		GridData gd_text = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		if (isHorizontal) {
			gd_text.horizontalSpan = span;
		}else
			gd_text.verticalSpan = span;
			
		lb_Separator.setLayoutData(gd_text);		
	}

	private void createGroupImage(String name, Composite compositeGeneral,
			FormToolkit toolkit, final Shell shell, final InformationUnit informationUnit, final AdapterFactoryEditingDomain editingDomain) {

		final Group group_Images = new Group(compositeGeneral, SWT.NONE);
	    final GridData gd_Images= new GridData();
	    gd_Images.grabExcessVerticalSpace = true;
	    gd_Images.verticalAlignment = GridData.FILL;
	    gd_Images.grabExcessHorizontalSpace = true;
	    gd_Images.horizontalAlignment = GridData.FILL;
	    group_Images.setLayoutData(gd_Images);
		final GridLayout gl_ImageGroup = new GridLayout();
		gl_ImageGroup.numColumns = 2;
		group_Images.setLayout(gl_ImageGroup);
		
		final Label lb_Image = toolkit.createLabel(group_Images, name);
		GridData gd_text = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		gd_text.horizontalSpan = 2;
		gd_text.horizontalAlignment = SWT.CENTER;
		lb_Image.setLayoutData(gd_text);
		
		final Label bt_Image = toolkit.createLabel(group_Images, "double click me ...", SWT.BORDER);
		bt_Image.setSize(100, 300);
		GridData gd_Image = new GridData(SWT.TOP, SWT.FILL, false, true);
		gd_Image.verticalSpan = 5;
		bt_Image.setLayoutData(gd_Image);
		
		bt_Image.addMouseListener(new MouseListener(){

			public void mouseDoubleClick(MouseEvent e) {
				bt_Image.setImage(ImageManipulation.selectImageFromDialog(shell, informationUnit, ContactActivator.NODE_NAME_RAWDATA_LOGO, editingDomain, lb_Image.getSize().x, lb_Image.getSize().y));
			}

			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		InformationUnit rawData = InformationUtil.getChildByType(informationUnit, ContactActivator.NODE_NAME_RAWDATA_LOGO);
		if(rawData != null && rawData.getBinaryValue() != null) {
			ByteArrayInputStream bais = new ByteArrayInputStream(rawData.getBinaryValue());
			ImageData imageData = new ImageData(bais);
			ImageData imageScaled = ImageManipulation.scaleImageToTarget(imageData, bt_Image.getSize().x, bt_Image.getSize().y);
			Image image = new Image(null, imageScaled);
			bt_Image.setImage(image);
		}
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