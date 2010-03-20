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

package org.remus.infomngmnt.pdf.ui;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.databinding.observable.value.IValueChangeListener;
import org.eclipse.core.databinding.observable.value.ValueChangeEvent;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.Path;
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
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.ui.newwizards.GeneralPage;
import org.remus.infomngmnt.ui.operation.LoadFileToTmpFromPathRunnable;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class GeneralPdfPage extends GeneralPage {

	private Text fileNameText;
	private Button browseButton;
	protected String tmpText;
	protected LoadFileToTmpFromPathRunnable loadImageJob;

	public GeneralPdfPage(final Category category) {
		super(category);
		this.loadImageJob = new LoadFileToTmpFromPathRunnable();
		if (this.files == null || this.files.length == 0) {
			this.files = new IFile[1];
		}

	}

	public GeneralPdfPage(final InformationUnitListItem selection) {
		super(selection);
		if (this.files == null || this.files.length == 0) {
			this.files = new IFile[1];
		}
		this.loadImageJob = new LoadFileToTmpFromPathRunnable();
	}

	@Override
	public void createControl(final Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		container.setLayout(new GridLayout());
		setTitle("New PDF Document");
		setMessage("This wizard enables you to create a new pdf from a file.");

		doCreateParentElementGroup(container);
		Group group = new Group(container, SWT.NONE);
		group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		group.setLayout(new GridLayout(3, false));
		group.setText("Name && File");
		doCreateNameElements(group);

		GridData gd_nameText = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gd_nameText.horizontalSpan = 2;
		this.nameText.setLayoutData(gd_nameText);

		if (this.files[0] == null) {
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
					fd.setFilterExtensions(new String[] { "*.pdf" });
					fd.setFilterNames(new String[] { "PDF Files" });
					String open = fd.open();
					if (open != null) {
						GeneralPdfPage.this.fileNameText.setText(open);
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
		if (this.fileNameText != null) {

			ISWTObservableValue swtUrl = SWTObservables.observeDelayedValue(500, SWTObservables
					.observeText(this.fileNameText, SWT.Modify));

			swtUrl.addValueChangeListener(new IValueChangeListener() {
				public void handleValueChange(final ValueChangeEvent event) {
					String newValue = (String) event.getObservableValue().getValue();
					try {
						GeneralPdfPage.this.loadImageJob.setFilePath(newValue);
						getContainer().run(true, true, GeneralPdfPage.this.loadImageJob);
						GeneralPdfPage.this.nameText.setText(new Path(
								GeneralPdfPage.this.fileNameText.getText()).removeFileExtension()
								.lastSegment());
						GeneralPdfPage.this.files[0] = GeneralPdfPage.this.loadImageJob
								.getTmpFile();
					} catch (InvocationTargetException e) {
						setErrorMessage(e.getCause().getMessage());
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			});

		}

	}

	/**
	 * @return the tmpFile
	 */
	public IFile getTmpFile() {
		return this.files[0];
	}

}
