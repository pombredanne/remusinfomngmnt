/*******************************************************************************
 * Copyright (c) 2010 Tom Seidel, Remus Software
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

import java.awt.Dimension;

import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.databinding.EMFUpdateValueStrategy;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.core.model.InformationStructureRead;
import org.remus.infomngmnt.pdf.Activator;
import org.remus.infomngmnt.pdf.extension.IPdfImageRenderer;
import org.remus.infomngmnt.pdf.preferences.PreferenceInitializer;
import org.remus.infomngmnt.pdf.service.IPDF2ImageExtensionService;
import org.remus.infomngmnt.ui.databinding.BindingWidgetFactory;
import org.remus.infomngmnt.ui.databinding.SpinnerSliderBindingWidget;
import org.remus.infomngmnt.ui.editors.editpage.AbstractInformationFormPage;
import org.remus.infomngmnt.util.InformationUtil;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class PdfMetadaEditPage extends AbstractInformationFormPage {

	private Spinner widthSpinner;

	/**
	 * 
	 */
	public PdfMetadaEditPage() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void renderPage(final IManagedForm managedForm) {
		FormToolkit toolkit = managedForm.getToolkit();
		ScrolledForm form = managedForm.getForm();
		Composite body = form.getBody();
		body.setLayout(new GridLayout());
		toolkit.paintBordersFor(body);

		doCreateGeneralSection(body, toolkit);
		doCreateSemanticSection(body, toolkit);
		form.reflow(true);

	}

	private void doCreateGeneralSection(final Composite body, final FormToolkit toolkit) {
		final Section generalSection = toolkit.createSection(body, ExpandableComposite.TITLE_BAR
				| ExpandableComposite.EXPANDED);
		final GridData gd_generalSection = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		generalSection.setLayoutData(gd_generalSection);
		generalSection.setText("General");

		final Composite client = toolkit.createComposite(generalSection, SWT.NONE);
		client.setLayout(new GridLayout(3, false));
		GridData gridData = new GridData(SWT.FILL, SWT.BEGINNING, true, false);

		client.setLayoutData(gridData);

		generalSection.setClient(client);

		Label subjectLabel = toolkit.createLabel(client, "PDF Slider Width");
		subjectLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));

		this.widthSpinner = new Spinner(client, SWT.BORDER);
		toolkit.adapt(this.widthSpinner);
		this.widthSpinner.setMinimum(100);
		this.widthSpinner.setIncrement(10);
		this.widthSpinner.setMaximum(3000);
		this.widthSpinner.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		Button createButton = toolkit.createButton(client, "Calculate optimal slider width",
				SWT.PUSH);
		createButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				IPDF2ImageExtensionService service = Activator.getDefault().getServiceTracker()
						.getService(IPDF2ImageExtensionService.class);
				String string = Activator.getDefault().getPreferenceStore().getString(
						PreferenceInitializer.DEFAULT_RENDERER);
				IPdfImageRenderer rendererById = service.getRendererById(string);
				if (rendererById == null) {
					rendererById = service.getAllRender()[0];
				}
				IFile binaryReferenceFile = InformationUtil
						.getBinaryReferenceFile(getModelObject());
				Dimension convert = rendererById.getRenderer().firstSlid(binaryReferenceFile);
				InformationStructureRead read = InformationStructureRead
						.newSession(getModelObject());
				InformationUnit childByNodeId = read.getChildByNodeId(Activator.SLIDER_WIDTH);
				Command command = SetCommand.create(getEditingDomain(), childByNodeId, read
						.getFeatureByNodeId(Activator.SLIDER_WIDTH), ((Integer) convert.width)
						.longValue());
				getEditingDomain().getCommandStack().execute(command);
			}
		});

	}

	@Override
	public void bindValuesToUi() {
		UpdateValueStrategy setConverter = new EMFUpdateValueStrategy()
				.setConverter(new IConverter() {

					public Object getToType() {
						// TODO Auto-generated method stub
						return null;
					}

					public Object getFromType() {
						// TODO Auto-generated method stub
						return null;
					}

					public Object convert(final Object fromObject) {
						if (fromObject instanceof Long) {
							return ((Long) fromObject).intValue();
						} else if (fromObject instanceof Integer) {
							return ((Integer) fromObject).longValue();
						}
						return fromObject;
					}
				});
		SpinnerSliderBindingWidget createSpinner = BindingWidgetFactory.createSpinner(
				this.widthSpinner, getDatabindingContext(), getEditingDomain());
		bind(createSpinner, Activator.SLIDER_WIDTH, setConverter, setConverter);
		super.bindValuesToUi();
	}

}
