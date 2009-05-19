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

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.contact.ContactActivator;
import org.remus.infomngmnt.contact.core.ImageManipulation;
import org.remus.infomngmnt.core.model.InformationUtil;

public class MiscellaneousSection {

	public MiscellaneousSection(final Composite body, final FormToolkit toolkit, final Shell shell,
			final InformationUnit informationUnit, final EditMiscPage editMiscPage) {
		final Section section_1 = toolkit.createSection(body, ExpandableComposite.TITLE_BAR
				| ExpandableComposite.TWISTIE | ExpandableComposite.EXPANDED);
		section_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		section_1.setText("Miscellaneous");

		final Composite compositeGeneral = toolkit.createComposite(section_1, SWT.BORDER);
		final GridLayout gridLayoutAdditional = new GridLayout();
		gridLayoutAdditional.numColumns = 2;
		compositeGeneral.setLayout(gridLayoutAdditional);
		toolkit.paintBordersFor(compositeGeneral);
		section_1.setClient(compositeGeneral);

		createGroupImage("Logo", compositeGeneral, toolkit, shell, informationUnit, editMiscPage);
		// createGroupGeoDatas(compositeGeneral, toolkit);
	}

	// private void createGroupGeoDatas(Composite compositeGeneral,
	// FormToolkit toolkit) {
	// final Group group_Geo = new Group(compositeGeneral, SWT.NONE);
	// final GridData gd_Geo= new GridData();
	// gd_Geo.grabExcessVerticalSpace = true;
	// gd_Geo.verticalAlignment = GridData.FILL;
	// gd_Geo.grabExcessHorizontalSpace = true;
	// gd_Geo.horizontalAlignment = GridData.FILL;
	// group_Geo.setLayoutData(gd_Geo);
	// final GridLayout gl_GeoGroup = new GridLayout();
	// gl_GeoGroup.numColumns = 3;
	// group_Geo.setLayout(gl_GeoGroup);
	//		
	// new Label(group_Geo, SWT.NONE);
	// final Button ck_UseGeoData = toolkit.createButton(group_Geo,
	// "Use Geodata", SWT.CHECK);
	// new Label(group_Geo, SWT.NONE);
	// new Label(group_Geo, SWT.NONE);
	// toolkit.createLabel(group_Geo, "Width");
	// final Spinner sp_Width = new Spinner(group_Geo, SWT.BORDER);
	// GridData gd_Spinner = new GridData(SWT.FILL, SWT.TOP, true, true);
	// gd_Spinner.widthHint = 30;
	// sp_Width.setLayoutData(gd_Spinner);
	// sp_Width.setMinimum(1);
	// new Label(group_Geo, SWT.NONE);
	// toolkit.createLabel(group_Geo, "Length");
	// final Spinner sp_Length = new Spinner(group_Geo, SWT.BORDER);
	// sp_Length.setLayoutData(gd_Spinner);
	// sp_Length.setMinimum(1);
	// toolkit.createLabel(group_Geo, null);
	// final Button bt_EditGeoData = toolkit.createButton(group_Geo,
	// "Edit Geodatas ...", SWT.NONE);
	// GridData gd_text = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
	// gd_text.horizontalSpan = 2;
	// bt_EditGeoData.setLayoutData(gd_text);
	//		
	// }

	private void createGroupImage(final String name, final Composite compositeGeneral,
			final FormToolkit toolkit, final Shell shell, final InformationUnit informationUnit,
			final EditMiscPage editMiscPage) {

		final Group group_Images = new Group(compositeGeneral, SWT.NONE);
		final GridData gd_Images = new GridData();
		group_Images.setLayoutData(gd_Images);
		final GridLayout gl_ImageGroup = new GridLayout();
		gl_ImageGroup.numColumns = 1;
		group_Images.setLayout(gl_ImageGroup);

		final Label lb_Image = toolkit.createLabel(group_Images, name);
		GridData gd_text = new GridData(SWT.CENTER, SWT.BEGINNING, true, false);
		lb_Image.setLayoutData(gd_text);

		final Label bt_Image = toolkit.createLabel(group_Images, "double click me ...", SWT.BORDER);
		GridData gd_Image = new GridData(SWT.FILL, SWT.BEGINNING, false, true);
		final int sizeX = 150;
		final int sizeY = 150;
		gd_Image.widthHint = sizeX;
		gd_Image.heightHint = sizeY;
		bt_Image.setLayoutData(gd_Image);

		bt_Image.addMouseListener(new MouseListener() {

			public void mouseDoubleClick(final MouseEvent e) {
				// Image img = ImageManipulation.selectImageFromDialog(shell,
				// informationUnit, ContactActivator.NODE_NAME_RAWDATA_LOGO,
				// (AdapterFactoryEditingDomain)
				// editMiscPage.getEditingDomain(), sizeX, sizeY);
				// if(img != null) bt_Image.setImage(img);;
			}

			public void mouseDown(final MouseEvent e) {
				// TODO Auto-generated method stub
			}

			public void mouseUp(final MouseEvent e) {
				// TODO Auto-generated method stub
			}
		});
		InformationUnit rawData = InformationUtil.getChildByType(informationUnit,
				ContactActivator.NODE_NAME_RAWDATA_LOGO);
		if (rawData != null && rawData.getBinaryValue() != null) {
			ByteArrayInputStream bais = new ByteArrayInputStream(rawData.getBinaryValue());
			ImageData imageData = new ImageData(bais);
			ImageData imageScaled = ImageManipulation.scaleImageToTarget(imageData, sizeX, sizeY);
			Image image = new Image(null, imageScaled);
			bt_Image.setImage(image);
		}
	}
}