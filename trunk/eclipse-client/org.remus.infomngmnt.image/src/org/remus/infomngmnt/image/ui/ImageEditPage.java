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

import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.databinding.edit.EMFEditObservables;
import org.eclipse.jface.databinding.swt.ISWTObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.ImageHyperlink;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.core.model.InformationUtil;
import org.remus.infomngmnt.image.ImagePlugin;
import org.remus.infomngmnt.ui.extension.AbstractInformationFormPage;


/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class ImageEditPage extends AbstractInformationFormPage {

	private Text heightText;
	private Text widthText;
	private Text text;
	@Override
	protected void createFormContent(final IManagedForm managedForm) {
		FormToolkit toolkit = managedForm.getToolkit();
		ScrolledForm form = managedForm.getForm();
		Composite body = form.getBody();
		body.setLayout(new GridLayout());
		toolkit.paintBordersFor(body);

		final Section generalSection = toolkit.createSection(body,
				ExpandableComposite.TITLE_BAR | ExpandableComposite.TWISTIE | ExpandableComposite.EXPANDED);
		final GridData gd_generalSection = new GridData(SWT.FILL, SWT.CENTER, true, false);
		generalSection.setLayoutData(gd_generalSection);
		generalSection.setText("General");

		final Composite composite = toolkit.createComposite(generalSection, SWT.NONE);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 4;
		composite.setLayout(gridLayout);
		toolkit.paintBordersFor(composite);
		generalSection.setClient(composite);

		toolkit.createLabel(composite, "Image-Name", SWT.NONE);

		this.text = toolkit.createText(composite, null, SWT.READ_ONLY);
		this.text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 3, 1));

		ISWTObservableValue swtLink = SWTObservables.observeDelayedValue(500, SWTObservables.observeText(this.text, SWT.Modify));
		IObservableValue emfLink = EMFEditObservables.observeValue(Realm.getDefault(), this.editingDomain, getModelObject(), InfomngmntPackage.Literals.ABSTRACT_INFORMATION_UNIT__LABEL);
		this.dataBindingContext.bindValue(swtLink, emfLink, null, null);
		new Label(composite, SWT.NONE);

		final Hyperlink openImageWithExternalApp = toolkit.createHyperlink(composite, "Open Image with the default external application", SWT.NONE);
		openImageWithExternalApp.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1));
		openImageWithExternalApp.addHyperlinkListener(new HyperlinkAdapter() {
			@Override
			public void linkActivated(final HyperlinkEvent e) {
				IFile adapter = (IFile) getModelObject().getAdapter(IFile.class);
				IFolder folder = adapter.getParent().getFolder(new Path(getModelObject().getId()));
				if (folder.exists()) {
					InformationUnit origString = InformationUtil.getChildByType(getModelObject(), ImagePlugin.ORIGINAL_FILEPATH);
					IPath path = new Path(origString.getStringValue());
					IFile imageFile = folder.getFile(new Path(getModelObject().getId()).addFileExtension(path.getFileExtension()));
					Program.launch(imageFile.getLocation().toOSString());
				}
			}
		});
		new Label(composite, SWT.NONE);

		final ImageHyperlink setLinksInImage = toolkit.createImageHyperlink(composite, SWT.NONE);
		setLinksInImage.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 3, 1));
		setLinksInImage.setText("Set Links within image");
		new Label(composite, SWT.NONE);

		final ImageHyperlink changeImageHyperlink = toolkit.createImageHyperlink(composite, SWT.NONE);
		changeImageHyperlink.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 3, 1));
		changeImageHyperlink.setText("Change image");

		toolkit.createLabel(composite, "Width", SWT.NONE);

		this.widthText = toolkit.createText(composite, null, SWT.NONE);
		final GridData gd_widthText = new GridData();
		this.widthText.setLayoutData(gd_widthText);

		toolkit.createLabel(composite, "px", SWT.NONE);

		final Button button = toolkit.createButton(composite, "New Forms Button", SWT.NONE);
		button.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 2));

		final Label heightLabel = toolkit.createLabel(composite, "Height", SWT.NONE);
		final GridData gd_heightLabel = new GridData();
		heightLabel.setLayoutData(gd_heightLabel);

		this.heightText = toolkit.createText(composite, null, SWT.NONE);
		final GridData gd_heightText = new GridData();
		this.heightText.setLayoutData(gd_heightText);

		toolkit.createLabel(composite, "px", SWT.NONE);
		new Label(composite, SWT.NONE);

		
		doCreateSemanticSection(body, toolkit);

	}

	
	@Override
	protected String getString() {
		// TODO Auto-generated method stub
		return null;
	}

}
