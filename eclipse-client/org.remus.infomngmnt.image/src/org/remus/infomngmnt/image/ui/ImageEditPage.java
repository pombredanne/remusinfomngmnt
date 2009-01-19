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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.IValueChangeListener;
import org.eclipse.core.databinding.observable.value.ValueChangeEvent;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.databinding.edit.EMFEditObservables;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.databinding.swt.ISWTObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
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
import org.remus.infomngmnt.common.ui.image.ResourceManager;
import org.remus.infomngmnt.core.model.InformationUtil;
import org.remus.infomngmnt.image.ImagePlugin;
import org.remus.infomngmnt.image.operation.LoadImageRunnable;
import org.remus.infomngmnt.ui.extension.AbstractInformationFormPage;


/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class ImageEditPage extends AbstractInformationFormPage {

	private Text heightText;
	private Text widthText;
	private Text text;
	private boolean keepRatio = true;
	private float ratio = 0;
	private final Action keepRationAction = new Action("",IAction.AS_CHECK_BOX) {
		@Override
		public ImageDescriptor getImageDescriptor() {
			return ImageDescriptor.createFromImage(
					ResourceManager.getPluginImage(ImagePlugin.getDefault(), "icons/iconexperience/fit_to_size.png"));
		};
		@Override
		public String getToolTipText() {
			return "Keep Aspect ratio";
		};
		@Override
		public void run() {
			if (isChecked()) {
				setCurrentRatio();
			}
			ImageEditPage.this.keepRatio = isChecked();
		};
	};
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
		gridLayout.numColumns = 5;
		composite.setLayout(gridLayout);
		toolkit.paintBordersFor(composite);
		generalSection.setClient(composite);

		toolkit.createLabel(composite, "Image-Name", SWT.NONE);

		this.text = toolkit.createText(composite, null, SWT.READ_ONLY);
		this.text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 4, 1));

		
		new Label(composite, SWT.NONE);

		final Hyperlink openImageWithExternalApp = toolkit.createHyperlink(composite, "Open Image with the default external application", SWT.NONE);
		openImageWithExternalApp.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 4, 1));
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
		setLinksInImage.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 4, 1));
		setLinksInImage.setText("Set Links within image");
		new Label(composite, SWT.NONE);

		final ImageHyperlink changeImageHyperlink = toolkit.createImageHyperlink(composite, SWT.NONE);
		changeImageHyperlink.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 4, 1));
		changeImageHyperlink.setText("Change image");
		changeImageHyperlink.addHyperlinkListener(new HyperlinkAdapter() {
			@Override
			public void linkActivated(final HyperlinkEvent e) {
				FileDialog fd = new FileDialog(getSite().getShell());
				fd.setFilterExtensions(new String[] {"*.jpg;*.jpeg;*.png;*.gif;*.bmp"});
				fd.setFilterNames(new String[] {"Supported Images (JPG,PNG,GIF,BMP)"});
				String open = fd.open();
				if (open != null) {
					LoadImageRunnable loadImage = new LoadImageRunnable();
					loadImage.setImagePath(open);
					loadImage.setRawDataNode(InformationUtil.getChildByType(getModelObject(), ImagePlugin.NODE_NAME_RAWDATA));
					loadImage.setHeightImageNode(InformationUtil.getChildByType(getModelObject(), ImagePlugin.HEIGHT));
					loadImage.setWidhtImageNode(InformationUtil.getChildByType(getModelObject(), ImagePlugin.WIDHT));
					loadImage.setDomain(ImageEditPage.this.editingDomain);
					ProgressMonitorDialog pmd = new ProgressMonitorDialog(getSite().getShell());
					try {
						pmd.run(true, false, loadImage);
					} catch (InvocationTargetException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}
			}
		});

		toolkit.createLabel(composite, "Width", SWT.NONE);

		this.widthText = toolkit.createText(composite, null, SWT.NONE);
		final GridData gd_widthText = new GridData();
		gd_widthText.widthHint = 50;
		this.widthText.setLayoutData(gd_widthText);
		this.widthText.addVerifyListener(new VerifyListener() {
			public void verifyText(final VerifyEvent e) {
				 Pattern pattern = Pattern.compile("[0-9]*");
				 Matcher m = pattern.matcher(e.text);
				 // ONLY NUMERICAL VALUES ARE ACCEPTED    .
				 if (!m.matches()) {
					 e.doit = false;
				 }
			}
		});

		toolkit.createLabel(composite, "px", SWT.NONE);
		new Label(composite, SWT.NONE);

		
		ToolBar tb = new ToolBar(composite,SWT.FLAT);
		ToolBarManager tbm = new ToolBarManager(tb);
		this.keepRationAction.setChecked(this.keepRatio);
		tbm.add(this.keepRationAction);
		tbm.update(true);
		tb.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 2));
	
		final Label heightLabel = toolkit.createLabel(composite, "Height", SWT.NONE);
		final GridData gd_heightLabel = new GridData();
		heightLabel.setLayoutData(gd_heightLabel);

		this.heightText = toolkit.createText(composite, null, SWT.NONE);
		final GridData gd_heightText = new GridData();
		gd_heightText.widthHint = 50;
		this.heightText.setLayoutData(gd_heightText);
		this.heightText.addVerifyListener(new VerifyListener() {
			public void verifyText(final VerifyEvent e) {
				 Pattern pattern = Pattern.compile("[0-9]*");
				 Matcher m = pattern.matcher(e.text);
				 // ONLY NUMERICAL VALUES ARE ACCEPTED    .
				 if (!m.matches()) {
					 e.doit = false;
				 }
			}
		});

		toolkit.createLabel(composite, "px", SWT.NONE);
		initDatabinding();
		setCurrentRatio();
		doCreateSemanticSection(body, toolkit);

	}

	
	protected void setCurrentRatio() {
		long width = InformationUtil.getChildByType(getModelObject(), ImagePlugin.WIDHT).getLongValue();
		long height = InformationUtil.getChildByType(getModelObject(), ImagePlugin.HEIGHT).getLongValue();
		this.ratio = (float)width / (float)height;
	}


	private void initDatabinding() {
		ISWTObservableValue swtLink = SWTObservables.observeDelayedValue(500, SWTObservables.observeText(this.text, SWT.Modify));
		IObservableValue emfLink = EMFEditObservables.observeValue(Realm.getDefault(), this.editingDomain, getModelObject(), InfomngmntPackage.Literals.ABSTRACT_INFORMATION_UNIT__LABEL);
		this.dataBindingContext.bindValue(swtLink, emfLink, null, null);
		
		InformationUnit widthNode = InformationUtil.getChildByType(getModelObject(), ImagePlugin.WIDHT);
		ISWTObservableValue swtWidth = SWTObservables.observeDelayedValue(500, SWTObservables.observeText(this.widthText, SWT.Modify));
		swtWidth.addValueChangeListener(new IValueChangeListener() {
			public void handleValueChange(final ValueChangeEvent event) {
				if (ImageEditPage.this.keepRatio) {
					ImageEditPage.this.heightText.setText(String.valueOf(Math.round(Long.parseLong((String) event.getObservableValue().getValue()) / ImageEditPage.this.ratio)));
				}
			}
		});
		IObservableValue emfWidth = EMFEditObservables.observeValue(Realm.getDefault(), this.editingDomain, widthNode, InfomngmntPackage.Literals.INFORMATION_UNIT__LONG_VALUE);
		this.dataBindingContext.bindValue(swtWidth, emfWidth, null, null);
		
		InformationUnit heightNode = InformationUtil.getChildByType(getModelObject(), ImagePlugin.HEIGHT);
		ISWTObservableValue swtHeight = SWTObservables.observeDelayedValue(500, SWTObservables.observeText(this.heightText, SWT.Modify));
		IObservableValue emfHeight = EMFEditObservables.observeValue(Realm.getDefault(), this.editingDomain, heightNode, InfomngmntPackage.Literals.INFORMATION_UNIT__LONG_VALUE);
		this.dataBindingContext.bindValue(swtHeight, emfHeight, null, null);
		swtHeight.addValueChangeListener(new IValueChangeListener() {
			public void handleValueChange(final ValueChangeEvent event) {
				if (ImageEditPage.this.keepRatio) {
					ImageEditPage.this.widthText.setText(String.valueOf(Math.round(Long.parseLong((String) event.getObservableValue().getValue()) * ImageEditPage.this.ratio)));
				}
			}
		});
		
		
		addControl(this.text);
		addControl(this.widthText);
		addControl(this.heightText);
		
	}


	@Override
	protected String getString() {
		// TODO Auto-generated method stub
		return null;
	}

}
