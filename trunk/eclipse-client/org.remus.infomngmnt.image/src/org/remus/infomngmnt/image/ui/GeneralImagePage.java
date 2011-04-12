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

import org.remus.infomngmnt.image.ImagePlugin;
import org.remus.infomngmnt.image.internal.ResourceManager;
import org.remus.infomngmnt.image.messages.Messages;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.IValueChangeListener;
import org.eclipse.core.databinding.observable.value.ValueChangeEvent;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.databinding.EMFObservables;
import org.eclipse.jface.databinding.swt.ISWTObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.remus.Category;
import org.eclipse.remus.InfomngmntPackage;
import org.eclipse.remus.InformationUnit;
import org.eclipse.remus.InformationUnitListItem;
import org.eclipse.remus.ui.newwizards.GeneralPage;
import org.eclipse.remus.ui.operation.LoadFileToTmpFromPathRunnable;
import org.eclipse.remus.util.InformationUtil;
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

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class GeneralImagePage extends GeneralPage {

	private Text fileNameText;
	private Button browseButton;
	protected String tmpText;
	protected LoadFileToTmpFromPathRunnable loadImageJob;

	public GeneralImagePage(final Category category) {
		super(category);
		loadImageJob = new LoadFileToTmpFromPathRunnable();
		if (files == null || files.length == 0) {
			files = new IFile[1];
		}

	}

	public GeneralImagePage(final InformationUnitListItem selection) {
		super(selection);
		loadImageJob = new LoadFileToTmpFromPathRunnable();
		if (files == null || files.length == 0) {
			files = new IFile[1];
		}
	}

	@Override
	public void createControl(final Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		container.setLayout(new GridLayout());
		setTitle(Messages.GeneralImagePage_WizardTitle);
		setMessage(Messages.GeneralImagePage_WizardSubTitle);
		setImageDescriptor(ResourceManager.getPluginImageDescriptor(
				ImagePlugin.getDefault(),
				"icons/iconexperience/photo_wizard_title.png")); //$NON-NLS-1$

		doCreateParentElementGroup(container);
		Group group = new Group(container, SWT.NONE);
		group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		group.setLayout(new GridLayout(3, false));
		group.setText(Messages.GeneralImagePage_NameAndFile);
		doCreateNameElements(group);

		GridData gd_nameText = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gd_nameText.horizontalSpan = 2;
		nameText.setLayoutData(gd_nameText);

		if (files[0] == null) {
			final Label nameLabel = new Label(group, SWT.NONE);
			nameLabel.setText(Messages.GeneralImagePage_File);
			fileNameText = new Text(group, SWT.BORDER);
			gd_nameText = new GridData(SWT.FILL, SWT.CENTER, true, false);
			gd_nameText.horizontalSpan = 2;
			fileNameText.setLayoutData(gd_nameText);

			browseButton = new Button(group, SWT.PUSH);
			browseButton.setText(Messages.GeneralImagePage_Browse);
			browseButton.addListener(SWT.Selection, new Listener() {

				public void handleEvent(final Event event) {
					FileDialog fd = new FileDialog(getShell());
					fd.setFilterExtensions(new String[] { "*.jpg;*.jpeg;*.png;*.gif;*.bmp" }); //$NON-NLS-1$
					fd.setFilterNames(new String[] { Messages.GeneralImagePage_SupportedFormats });
					String open = fd.open();
					if (open != null) {
						fileNameText.setText(open);
					}
				}

			});
		}

		doCreatePropertiesGroup(container);
		initDatabinding();
		presetValues();
		initValidation();
		setControl(container);
	}

	@Override
	protected void initDatabinding() {
		super.initDatabinding();
		if (fileNameText != null) {
			InformationUnit origFilePathNode = InformationUtil.getChildByType(
					unit, ImagePlugin.ORIGINAL_FILEPATH);
			ISWTObservableValue swtUrl = SWTObservables.observeDelayedValue(
					500, SWTObservables.observeText(fileNameText, SWT.Modify));
			IObservableValue emfUrl = EMFObservables.observeValue(
					origFilePathNode,
					InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);
			swtUrl.addValueChangeListener(new IValueChangeListener() {
				public void handleValueChange(final ValueChangeEvent event) {
					String newValue = (String) event.getObservableValue()
							.getValue();
					try {
						loadImageJob.setFilePath(newValue);
						getContainer().run(true, true, loadImageJob);
						GeneralImagePage.this.nameText.setText(new Path(
								fileNameText.getText()).removeFileExtension()
								.lastSegment());
						GeneralImagePage.this.files[0] = loadImageJob
								.getTmpFile();
					} catch (InvocationTargetException e) {
						setErrorMessage(e.getCause().getMessage());
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			});
			ctx.bindValue(swtUrl, emfUrl, null, null);
		}

	}

	/**
	 * @return the tmpFile
	 */
	public IFile getTmpFile() {
		return files[0];
	}

}
