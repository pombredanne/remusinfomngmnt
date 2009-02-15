/*******************************************************************************
 * Copyright (c) 2009 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/

package org.remus.infomngmnt.image.ui;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.IValueChangeListener;
import org.eclipse.core.databinding.observable.value.ValueChangeEvent;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.databinding.EMFObservables;
import org.eclipse.jface.databinding.swt.ISWTObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.core.model.InformationUtil;
import org.remus.infomngmnt.image.ImagePlugin;
import org.remus.infomngmnt.image.operation.LoadImageRunnable;
import org.remus.infomngmnt.ui.newwizards.GeneralPage;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class GeneralImagePage extends GeneralPage {

	private Text fileNameText;
	private Button browseButton;
	protected String tmpText;
	protected LoadImageRunnable loadImageJob;

	public GeneralImagePage(final Category category) {
		super(category);
		this.loadImageJob = new LoadImageRunnable();
		
	}

	public GeneralImagePage(final InformationUnitListItem selection) {
		super(selection);
	}
	
	@Override
	public void createControl(final Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		container.setLayout(new GridLayout());

		doCreateParentElementGroup(container);
		Group group = new Group(container, SWT.NONE);
		group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		group.setLayout(new GridLayout(3, false));
		group.setText("Name && File");
		doCreateNameElements(group);
		
		GridData gd_nameText = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gd_nameText.horizontalSpan = 2;
		this.nameText.setLayoutData(gd_nameText);
		
		/*
		 * we check if we have already set data.
		 */
		InformationUnit rawData = InformationUtil.getChildByType(this.unit, ImagePlugin.NODE_NAME_RAWDATA);
		if (rawData.getBinaryValue() == null) {
			final Label nameLabel = new Label(group, SWT.NONE);
			nameLabel.setText("File");
			this.fileNameText = new Text(group, SWT.BORDER);
			gd_nameText = new GridData(SWT.FILL, SWT.CENTER, true, false);
			gd_nameText.horizontalSpan = 2;
			this.fileNameText.setLayoutData(gd_nameText);
			
			this.browseButton = new Button(group, SWT.PUSH);
			this.browseButton.setText("Browse...");
			this.browseButton.addListener(SWT.Selection, new Listener() {
				
				
				public void handleEvent(final Event event) {
					FileDialog fd = new FileDialog(getShell());
					fd.setFilterExtensions(new String[] {"*.jpg;*.jpeg;*.png;*.gif;*.bmp"});
					fd.setFilterNames(new String[] {"Supported Images (JPG,PNG,GIF,BMP)"});
					String open = fd.open();
					if (open != null) {
						GeneralImagePage.this.fileNameText.setText(open);
					}
				}
				
			});
		}

		

		doCreatePropertiesGroup(container);
		initDatabinding();
		presetValues();
		setPageComplete(false);
		setControl(container);
	}
	
	
	@Override
	protected void initDatabinding() {
		super.initDatabinding();
		if (this.fileNameText != null) {
			InformationUnit origFilePathNode = InformationUtil.getChildByType(this.unit, ImagePlugin.ORIGINAL_FILEPATH);
			ISWTObservableValue swtUrl = SWTObservables.observeDelayedValue(500, SWTObservables.observeText(this.fileNameText, SWT.Modify));
			IObservableValue emfUrl = EMFObservables.observeValue(origFilePathNode, InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);
			swtUrl.addValueChangeListener(new IValueChangeListener() {
				public void handleValueChange(final ValueChangeEvent event) {
					String newValue = (String) event.getObservableValue().getValue();
					try {
						GeneralImagePage.this.loadImageJob.setImagePath(newValue);
						GeneralImagePage.this.loadImageJob.setImageNode(GeneralImagePage.this.unit);
						getContainer().run(true, true, GeneralImagePage.this.loadImageJob);
						GeneralImagePage.this.nameText.setText(new Path(GeneralImagePage.this.loadImageJob.getFile().getAbsolutePath()).removeFileExtension().lastSegment());
					} catch (InvocationTargetException e) {
						setErrorMessage(e.getCause().getMessage());
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
	
			});
			this.ctx.bindValue(swtUrl, emfUrl, null, null);
		}

	}


}
