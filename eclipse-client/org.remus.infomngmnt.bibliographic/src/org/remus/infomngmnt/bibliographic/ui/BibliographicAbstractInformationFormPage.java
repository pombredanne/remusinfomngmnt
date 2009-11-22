/*******************************************************************************
 * Copyright (c) 2009 Andreas Deinlein
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Andreas Deinlein - bibliographic extensions
 *******************************************************************************/
package org.remus.infomngmnt.bibliographic.ui;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.DeleteCommand;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IOpenListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.OpenEvent;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.remus.infomngmnt.BinaryReference;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.bibliographic.BibliographicActivator;
import org.remus.infomngmnt.common.ui.databinding.BindingUtil;
import org.remus.infomngmnt.core.commands.DeleteBinaryReferenceCommand;
import org.remus.infomngmnt.core.model.InformationStructureEdit;
import org.remus.infomngmnt.core.model.InformationStructureRead;
import org.remus.infomngmnt.core.operation.LoadFileToTmpFromPathRunnable;
import org.remus.infomngmnt.core.progress.CancelableRunnable;
import org.remus.infomngmnt.ui.extension.AbstractInformationFormPage;
import org.remus.infomngmnt.util.EditingUtil;
import org.remus.infomngmnt.util.InformationUtil;
import org.remus.infomngmnt.util.StatusCreator;

public abstract class BibliographicAbstractInformationFormPage extends
		AbstractInformationFormPage {
	
	protected InformationStructureRead read;
	
	protected String baseTypeId;	// Information Id of Subtype: book, article, ... - set in constructor of subtypte
	
	private Text abstractText;
	private Text url;
	private TableViewer filesTableViewer;
	
	/**
	 * LabelProvider for files location table
	 *  - shows file name stored in 'fileLabel'
	 *  - retrieve image data from associated Program
	 */
	private final LabelProvider filesLabelProvider = new LabelProvider() {
		@Override
		public String getText(final Object element) {
			InformationStructureRead read = InformationStructureRead.newSession((InformationUnit) element, baseTypeId);
			return (String) read.getValueByNodeId(BibliographicActivator.NODE_NAME_FILES_LABEL);
		}

		@Override
		public Image getImage(final Object element) {
			InformationUnit element2 = (InformationUnit) element;
			InformationStructureRead read = InformationStructureRead.newSession(element2, baseTypeId);
			List<BinaryReference> binaryReferences = read.getBinaryReferences(element2.getType(), false);
			if (binaryReferences.size() > 0 && binaryReferences.get(0) != null) {
				String projectRelativePath = binaryReferences.get(0).getProjectRelativePath();
				String fileExtension = new Path(projectRelativePath).getFileExtension();
				if (fileExtension == null) {
					fileExtension = "";
				}
				Program findProgram = Program.findProgram(fileExtension);
				if (findProgram != null) {
					ImageData imageData = findProgram.getImageData();
					return ImageDescriptor.createFromImageData(imageData).createImage();
				}
			}
			return super.getImage(element);
		}
	};
	
	/**
	 * create section for external links, like URLs or locally stored files
	 * 
	 * @param parent
	 * @param toolkit
	 * @param tableWrapLayout
	 */
	protected void doCreateExtLinksSection(final Composite parent, final FormToolkit toolkit, final boolean tableWrapLayout) {
		final Section extLinksSection = toolkit.createSection(parent, ExpandableComposite.TITLE_BAR | ExpandableComposite.TWISTIE | ExpandableComposite.EXPANDED);
		if (tableWrapLayout) {
			extLinksSection.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB, TableWrapData.TOP));
		} else {
			final GridData gd_semanticsSection = new GridData(SWT.FILL, SWT.CENTER, false, false);
			extLinksSection.setLayoutData(gd_semanticsSection);
		}
		extLinksSection.setText("External Links");
		final Composite client = toolkit.createComposite(extLinksSection, SWT.NONE);
		final GridLayout clientGridLayout = new GridLayout();
		clientGridLayout.numColumns = 3;
		client.setLayout(clientGridLayout);
		toolkit.paintBordersFor(client);
		extLinksSection.setClient(client);

		toolkit.createLabel(client, "Url:        ", SWT.NONE);
		this.url = toolkit.createText(client, null, SWT.NONE);
		final GridData gd_urlText = new GridData(SWT.FILL, SWT.CENTER, true, false);
		this.url.setLayoutData(gd_urlText);
		addControl(this.url);		
		toolkit.createLabel(client, "", SWT.NONE);
		
		toolkit.createLabel(client, "Files:          ", SWT.NONE);
		Table createTable = toolkit.createTable(client, SWT.V_SCROLL | SWT.MULTI | SWT.BORDER);
		GridDataFactory.fillDefaults().hint(SWT.DEFAULT, 30).grab(true, false).span(1, 3).applyTo(createTable);
		this.filesTableViewer = new TableViewer(createTable);	
		final Composite innerButtonClient = toolkit.createComposite(client, SWT.NONE);
		final GridLayout innerGridLayout = new GridLayout();
		innerGridLayout.numColumns = 1;
		innerButtonClient.setLayout(innerGridLayout);
		Button addbutton = toolkit.createButton(innerButtonClient, "Add", SWT.FLAT);
		addbutton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				FileDialog fileDialog = new FileDialog(getSite().getShell(), SWT.OPEN);
				final String open = fileDialog.open();
				if (open != null) {
					ProgressMonitorDialog pmd = new ProgressMonitorDialog(getSite().getShell());
					try {
						pmd.run(true, true, new CancelableRunnable() {
							@Override
							protected IStatus runCancelableRunnable(final IProgressMonitor monitor) {
								LoadFileToTmpFromPathRunnable loadFile = new LoadFileToTmpFromPathRunnable();
								loadFile.setFilePath(open);
								try {
									loadFile.run(monitor);
								} catch (InvocationTargetException e) {
									return StatusCreator.newStatus("Error uploading file", e);
								} catch (InterruptedException e) {
									return Status.CANCEL_STATUS;
								}
								IFile tmpFile = loadFile.getTmpFile();
								InformationStructureEdit edit = InformationStructureEdit
										.newSession(baseTypeId,
												getEditingDomain());
								InformationUnit createSubType = edit.createSubType(
										BibliographicActivator.NODE_NAME_FILE, null);
								edit.setValue(createSubType,
										BibliographicActivator.NODE_NAME_FILES_LABEL,
										new Path(open).lastSegment());
								edit.addDynamicNode(getModelObject(), createSubType,
										getEditingDomain(), Collections.singletonMap(
												BibliographicActivator.NODE_NAME_FILE,
												tmpFile));
								return Status.OK_STATUS;
							}
						});
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});

		final Button deleteButton = toolkit.createButton(innerButtonClient, "Delete", SWT.FLAT);
		deleteButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				CompoundCommand cc = new CompoundCommand();
				List list = ((IStructuredSelection) BibliographicAbstractInformationFormPage.this.filesTableViewer
						.getSelection()).toList();
				for (Object object : list) {
					InformationStructureRead read = InformationStructureRead.newSession(
							(InformationUnit) object, baseTypeId);
					List<BinaryReference> binaryReferences = read.getBinaryReferences(
							((InformationUnit) object).getType(), false);
					for (BinaryReference binaryReference : binaryReferences) {
						cc.append(new DeleteBinaryReferenceCommand(binaryReference,
								getEditingDomain()));
					}
				}
				Command deleteCmd = DeleteCommand.create(getEditingDomain(),
						((IStructuredSelection) BibliographicAbstractInformationFormPage.this.filesTableViewer
								.getSelection()).toList());
				cc.append(deleteCmd);
				cc.setLabel("Delete files");
				getEditingDomain().getCommandStack().execute(cc);
			}
		});
		
		this.filesTableViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(final SelectionChangedEvent event) {
				deleteButton.setEnabled(!event.getSelection().isEmpty());
			}
		});
		
		this.filesTableViewer.addOpenListener(new IOpenListener() {
			public void open(final OpenEvent event) {
					List list = ((IStructuredSelection) event.getSelection()).toList();
					for (Object object : list) {
						InformationStructureRead read = InformationStructureRead.newSession(
								(InformationUnit) object, baseTypeId);
						InformationUnit childByNodeId = read
								.getChildByNodeId(BibliographicActivator.NODE_NAME_FILE);
						IFile firstBinaryReferenceFile = InformationUtil
								.getBinaryReferenceFile(childByNodeId);
						Program.launch(firstBinaryReferenceFile.getLocation().toOSString());
				}
			}
		});
		
		this.filesTableViewer.setContentProvider(new AdapterFactoryContentProvider(
				EditingUtil.getInstance().getAdapterFactory()));
		this.filesTableViewer.setLabelProvider(this.filesLabelProvider);
		this.filesTableViewer.setSelection(StructuredSelection.EMPTY);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.TOP).hint(50, SWT.DEFAULT).applyTo(addbutton);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.TOP).applyTo(deleteButton);	
	}
	
	/**
	 * Create separate section for an Abstract
	 * 
	 * @param parent
	 * @param toolkit
	 * @param tableWrapLayout
	 */
	protected void doCreateAbstractSection(final Composite parent, final FormToolkit toolkit,
			final boolean tableWrapLayout) {
		final Section abstractSection = toolkit.createSection(parent,
				ExpandableComposite.TITLE_BAR | ExpandableComposite.TWISTIE | ExpandableComposite.EXPANDED);
		if (tableWrapLayout) {
			abstractSection.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB,
					TableWrapData.TOP));
		} else {
			final GridData gd_abstractSection = new GridData(SWT.FILL, SWT.CENTER, false, false);
			abstractSection.setLayoutData(gd_abstractSection);
		}
		abstractSection.setText("Abstract");
		final Composite composite_2 = toolkit.createComposite(abstractSection, SWT.NONE);
		final GridLayout gridLayout_1 = new GridLayout();
		gridLayout_1.numColumns = 2;
		composite_2.setLayout(gridLayout_1);
		toolkit.paintBordersFor(composite_2);
		abstractSection.setClient(composite_2);

		toolkit.createLabel(composite_2, "Abstract:   ", SWT.NONE);
		this.abstractText = toolkit.createText(composite_2, null, SWT.WRAP | SWT.V_SCROLL| SWT.MULTI);
		final GridData gd_abstractText = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gd_abstractText.heightHint = 70;
		this.abstractText.setLayoutData(gd_abstractText);
		addControl(this.abstractText);
	}	
	
	
	/**
	 * bind values
	 */
	public void bindValuesToUi() {
		super.bindValuesToUi();
		read = InformationStructureRead.newSession(getModelObject());
		
		if (this.abstractText != null) {
			BindingUtil.createTextAndBind(this.abstractText, read.getChildByNodeId(BibliographicActivator.NODE_NAME_ABSTRACT),
					read.getFeatureByNodeId(BibliographicActivator.NODE_NAME_ABSTRACT), this);
		}
		
		if (this.url != null) {
			BindingUtil.createTextAndBind(this.url, read.getChildByNodeId(BibliographicActivator.NODE_NAME_URL),
					read.getFeatureByNodeId(BibliographicActivator.NODE_NAME_URL), this);
		}
		
		InformationUnit files = read.getChildByNodeId(BibliographicActivator.NODE_NAME_FILES);
		this.filesTableViewer.setInput(files);
	}
	
	/**
	 * Helper method for binding
	 * @param field
	 * @param name
	 */
	protected void bindBibliographicField(Text field, String name) {
		BindingUtil.createTextAndBind(field, 
				read.getChildByNodeId(name), 
				read.getFeatureByNodeId(name), this);
	}
	
	/**
	 * Helper method for creating a section
	 * @param managedForm
	 * @param name
	 * @return
	 */
	protected Composite createBibliographicSection(IManagedForm managedForm, String name, boolean expanded) {
		FormToolkit toolkit = managedForm.getToolkit();
		ScrolledForm form = managedForm.getForm();
		Composite body = form.getBody();
		final Section generalSection;
		if (expanded) {
			generalSection = toolkit.createSection(body, ExpandableComposite.TITLE_BAR
					| ExpandableComposite.EXPANDED | ExpandableComposite.TWISTIE);
		} else {
			generalSection = toolkit.createSection(body, ExpandableComposite.TITLE_BAR
					| ExpandableComposite.TWISTIE);
		}		
		final GridData gd_generalSection = new GridData(SWT.FILL, SWT.FILL, true, true);
		generalSection.setLayoutData(gd_generalSection);
		generalSection.setText(name);

		Composite client = toolkit.createComposite(generalSection, SWT.NONE);
		client.setLayout(new GridLayout(2, false));
		GridData gridData = new GridData(SWT.FILL, SWT.BEGINNING, true, false);

		client.setLayoutData(gridData);
		generalSection.setClient(client);

		return client;
	}
	
	/**
	 * Helper method that creates a Edit Field 
	 * 
	 * @param managedForm	IManagedForm instance
	 * @param client		client Section 
	 * @param label			String for Label
	 * @return				the generated Edit Field
	 */
	protected Text createBibliographicEditField(IManagedForm managedForm, Composite client, String label) {
		Text field;
		FormToolkit toolkit = managedForm.getToolkit();
		Label tmpLabel = toolkit.createLabel(client, label);
		tmpLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		field = toolkit.createText(client, "", SWT.BORDER);
		field.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		addControl(field);
		return field;
	}

}
