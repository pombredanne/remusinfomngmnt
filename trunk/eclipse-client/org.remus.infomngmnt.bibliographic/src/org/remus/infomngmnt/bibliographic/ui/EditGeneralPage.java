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
import org.eclipse.remus.BinaryReference;
import org.eclipse.remus.InformationUnit;
import org.eclipse.remus.core.commands.DeleteBinaryReferenceCommand;
import org.eclipse.remus.core.model.InformationStructureEdit;
import org.eclipse.remus.core.model.InformationStructureRead;
import org.eclipse.remus.core.services.IEditingHandler;
import org.eclipse.remus.ui.databinding.BindingUtil;
import org.eclipse.remus.ui.editors.editpage.AbstractInformationFormPage;
import org.eclipse.remus.ui.operation.LoadFileToTmpFromPathRunnable;
import org.eclipse.remus.ui.progress.CancelableRunnable;
import org.eclipse.remus.util.InformationUtil;
import org.eclipse.remus.util.StatusCreator;
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
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;

import org.remus.infomngmnt.bibliographic.BibliographicActivator;
import org.remus.infomngmnt.bibliographic.Messages;

public class EditGeneralPage extends AbstractInformationFormPage {

	private Text abstractText;
	private Text summaryText;
	private Text contentsText;

	private Text url;
	private TableViewer filesTableViewer;

	/**
	 * LabelProvider for files location table - shows file name stored in
	 * 'fileLabel' - retrieve image data from associated Program
	 */
	private final LabelProvider filesLabelProvider = new LabelProvider() {
		@Override
		public String getText(final Object element) {
			InformationStructureRead read = InformationStructureRead.newSession(getModelObject());
			return (String) read.getValueByNodeId(BibliographicActivator.NODE_NAME_FILES_LABEL);
		}

		@Override
		public Image getImage(final Object element) {
			InformationStructureRead read = InformationStructureRead.newSession(getModelObject());
			List<BinaryReference> binaryReferences = read.getBinaryReferences(getModelObject()
					.getType(), false);
			if (binaryReferences.size() > 0 && binaryReferences.get(0) != null) {
				String projectRelativePath = binaryReferences.get(0).getProjectRelativePath();
				String fileExtension = new Path(projectRelativePath).getFileExtension();
				if (fileExtension == null) {
					fileExtension = ""; //$NON-NLS-1$
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

	@Override
	protected void renderPage(final IManagedForm managedForm) {
		FormToolkit toolkit = managedForm.getToolkit();
		ScrolledForm form = managedForm.getForm();
		Composite body = form.getBody();
		body.setLayout(new GridLayout());
		toolkit.paintBordersFor(body);

		// Abstract Section
		doCreateAbstractSection(body, toolkit, false);

		// Contents Section
		doCreateContentsSection(body, toolkit, false);

		// Summary Section
		doCreateSummarySection(body, toolkit, false);

		// ExternalLinks Section
		doCreateExtLinksSection(body, toolkit, false);

		form.reflow(true);
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
		final Section theSection = toolkit.createSection(parent, ExpandableComposite.TITLE_BAR
				| ExpandableComposite.TWISTIE | ExpandableComposite.EXPANDED);
		if (tableWrapLayout) {
			theSection.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB, TableWrapData.TOP));
		} else {
			final GridData gd_abstractSection = new GridData(SWT.FILL, SWT.CENTER, true, false);
			theSection.setLayoutData(gd_abstractSection);
		}
		theSection.setText(Messages.getString("abstractSection"));
		final Composite innerComposite = toolkit.createComposite(theSection, SWT.NONE);
		final GridLayout compositeGridLayout = new GridLayout();
		compositeGridLayout.numColumns = 2;
		innerComposite.setLayout(compositeGridLayout);
		toolkit.paintBordersFor(innerComposite);
		theSection.setClient(innerComposite);

		toolkit.createLabel(innerComposite, Messages.getString("abstractLabel") + ":", SWT.NONE);
		this.abstractText = toolkit.createText(innerComposite, null, SWT.WRAP | SWT.V_SCROLL
				| SWT.MULTI);
		final GridData gd = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gd.heightHint = 70;
		gd.horizontalAlignment = SWT.FILL;
		this.abstractText.setLayoutData(gd);
		this.abstractText.setToolTipText(Messages.getString("abstractTip"));
		addControl(this.abstractText);
	}

	/**
	 * Create separate section for summary
	 * 
	 * @param parent
	 * @param toolkit
	 * @param tableWrapLayout
	 */
	protected void doCreateSummarySection(final Composite parent, final FormToolkit toolkit,
			final boolean tableWrapLayout) {
		final Section theSection = toolkit.createSection(parent, ExpandableComposite.TITLE_BAR
				| ExpandableComposite.TWISTIE | ExpandableComposite.EXPANDED);
		if (tableWrapLayout) {
			theSection.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB, TableWrapData.TOP));
		} else {
			final GridData gd_abstractSection = new GridData(SWT.FILL, SWT.CENTER, true, false);
			theSection.setLayoutData(gd_abstractSection);
		}
		theSection.setText(Messages.getString("summarySection"));
		final Composite innerComposite = toolkit.createComposite(theSection, SWT.NONE);
		final GridLayout compositeGridLayout = new GridLayout();
		compositeGridLayout.numColumns = 2;
		innerComposite.setLayout(compositeGridLayout);
		toolkit.paintBordersFor(innerComposite);
		theSection.setClient(innerComposite);

		toolkit.createLabel(innerComposite, Messages.getString("summaryLabel") + ":", SWT.NONE);
		this.summaryText = toolkit.createText(innerComposite, null, SWT.WRAP | SWT.V_SCROLL
				| SWT.MULTI);
		final GridData gd = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gd.heightHint = 100;
		gd.horizontalAlignment = SWT.FILL;
		this.summaryText.setLayoutData(gd);
		this.summaryText.setToolTipText(Messages.getString("summaryTip"));
		addControl(this.summaryText);
	}

	/**
	 * Create separate section for contents
	 * 
	 * @param parent
	 * @param toolkit
	 * @param tableWrapLayout
	 */
	protected void doCreateContentsSection(final Composite parent, final FormToolkit toolkit,
			final boolean tableWrapLayout) {
		final Section theSection = toolkit.createSection(parent, ExpandableComposite.TITLE_BAR
				| ExpandableComposite.TWISTIE | ExpandableComposite.EXPANDED);
		if (tableWrapLayout) {
			theSection.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB, TableWrapData.TOP));
		} else {
			final GridData gd_abstractSection = new GridData(SWT.FILL, SWT.CENTER, true, false);
			theSection.setLayoutData(gd_abstractSection);
		}
		theSection.setText(Messages.getString("contentsSection"));
		final Composite innerComposite = toolkit.createComposite(theSection, SWT.NONE);
		final GridLayout compositeGridLayout = new GridLayout();
		compositeGridLayout.numColumns = 2;
		innerComposite.setLayout(compositeGridLayout);
		toolkit.paintBordersFor(innerComposite);
		theSection.setClient(innerComposite);

		toolkit.createLabel(innerComposite, Messages.getString("contentsLabel") + ":", SWT.NONE);
		this.contentsText = toolkit.createText(innerComposite, null, SWT.WRAP | SWT.V_SCROLL
				| SWT.MULTI);
		final GridData gd = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gd.heightHint = 100;
		gd.horizontalAlignment = SWT.FILL;
		this.contentsText.setLayoutData(gd);
		this.contentsText.setToolTipText(Messages.getString("contentsTip"));
		addControl(this.contentsText);
	}

	/**
	 * create section for external links, like URLs or locally stored files
	 * 
	 * @param parent
	 * @param toolkit
	 * @param tableWrapLayout
	 */
	protected void doCreateExtLinksSection(final Composite parent, final FormToolkit toolkit,
			final boolean tableWrapLayout) {
		final Section extLinksSection = toolkit.createSection(parent, ExpandableComposite.TITLE_BAR
				| ExpandableComposite.TWISTIE | ExpandableComposite.EXPANDED);
		if (tableWrapLayout) {
			extLinksSection.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB,
					TableWrapData.TOP));
		} else {
			final GridData gd_semanticsSection = new GridData(SWT.FILL, SWT.CENTER, false, false);
			extLinksSection.setLayoutData(gd_semanticsSection);
		}
		extLinksSection.setText(Messages.getString("externalLinksSection"));
		final Composite client = toolkit.createComposite(extLinksSection, SWT.NONE);
		final GridLayout clientGridLayout = new GridLayout();
		clientGridLayout.numColumns = 3;
		client.setLayout(clientGridLayout);
		toolkit.paintBordersFor(client);
		extLinksSection.setClient(client);

		toolkit.createLabel(client, Messages.getString("urlLabel") + ":", SWT.NONE);
		this.url = toolkit.createText(client, null, SWT.NONE);
		final GridData gd_urlText = new GridData(SWT.FILL, SWT.CENTER, true, false);
		this.url.setLayoutData(gd_urlText);
		addControl(this.url);
		toolkit.createLabel(client, "", SWT.NONE); //$NON-NLS-1$

		toolkit.createLabel(client, Messages.getString("filesLabel") + ":", SWT.NONE);
		Table createTable = toolkit.createTable(client, SWT.V_SCROLL | SWT.MULTI | SWT.BORDER);
		GridDataFactory.fillDefaults().hint(SWT.DEFAULT, 30).grab(true, false).span(1, 3).applyTo(
				createTable);
		this.filesTableViewer = new TableViewer(createTable);
		final Composite innerButtonClient = toolkit.createComposite(client, SWT.NONE);
		final GridLayout innerGridLayout = new GridLayout();
		innerGridLayout.numColumns = 1;
		innerButtonClient.setLayout(innerGridLayout);
		Button addbutton = toolkit.createButton(innerButtonClient, Messages
				.getString("addButtonName"), SWT.FLAT);
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
									return StatusCreator.newStatus(Messages
											.getString("fileLoadError"), e);
								} catch (InterruptedException e) {
									return Status.CANCEL_STATUS;
								}
								IFile tmpFile = loadFile.getTmpFile();
								InformationStructureEdit edit = InformationStructureEdit
										.newSession(getModelObject().getType(), getEditingDomain());
								InformationUnit createSubType = edit.createSubType(
										BibliographicActivator.NODE_NAME_FILE, null);
								edit.setValue(createSubType,
										BibliographicActivator.NODE_NAME_FILES_LABEL,
										new Path(open).lastSegment());
								edit.addDynamicNode(getModelObject(), createSubType,
										getEditingDomain(), Collections.singletonMap(
												BibliographicActivator.NODE_NAME_FILE, tmpFile));
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

		final Button deleteButton = toolkit.createButton(innerButtonClient, Messages
				.getString("deleteButton"), SWT.FLAT);
		deleteButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				CompoundCommand cc = new CompoundCommand();
				List list = ((IStructuredSelection) EditGeneralPage.this.filesTableViewer
						.getSelection()).toList();
				for (Object object : list) {
					InformationStructureRead read = InformationStructureRead
							.newSession(getModelObject());
					List<BinaryReference> binaryReferences = read.getBinaryReferences(
							((InformationUnit) object).getType(), false);
					for (BinaryReference binaryReference : binaryReferences) {
						cc.append(new DeleteBinaryReferenceCommand(binaryReference,
								getEditingDomain()));
					}
				}
				Command deleteCmd = DeleteCommand.create(getEditingDomain(),
						((IStructuredSelection) EditGeneralPage.this.filesTableViewer
								.getSelection()).toList());
				cc.append(deleteCmd);
				cc.setLabel(Messages.getString("deleteCommandLabel"));
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
					InformationStructureRead read = InformationStructureRead
							.newSession(getModelObject());
					InformationUnit childByNodeId = read
							.getChildByNodeId(BibliographicActivator.NODE_NAME_FILE);
					IFile firstBinaryReferenceFile = InformationUtil
							.getBinaryReferenceFile(childByNodeId);
					Program.launch(firstBinaryReferenceFile.getLocation().toOSString());
				}
			}
		});

		IEditingHandler editService = BibliographicActivator.getDefault().getServiceTracker()
				.getService(IEditingHandler.class);
		this.filesTableViewer.setContentProvider(new AdapterFactoryContentProvider(editService
				.getAdapterFactory()));
		this.filesTableViewer.setLabelProvider(this.filesLabelProvider);
		this.filesTableViewer.setSelection(StructuredSelection.EMPTY);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.TOP).hint(50, SWT.DEFAULT).applyTo(
				addbutton);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.TOP).applyTo(deleteButton);
	}

	/**
	 * bind values
	 */
	@Override
	public void bindValuesToUi() {
		super.bindValuesToUi();
		InformationStructureRead read = InformationStructureRead.newSession(getModelObject());

		if (this.abstractText != null) {
			BindingUtil.createTextAndBind(this.abstractText, read
					.getChildByNodeId(BibliographicActivator.NODE_NAME_ABSTRACT), read
					.getFeatureByNodeId(BibliographicActivator.NODE_NAME_ABSTRACT), this);
		}

		if (this.summaryText != null) {
			BindingUtil.createTextAndBind(this.summaryText, read
					.getChildByNodeId(BibliographicActivator.NODE_NAME_SUMMARY), read
					.getFeatureByNodeId(BibliographicActivator.NODE_NAME_SUMMARY), this);
		}

		if (this.contentsText != null) {
			BindingUtil.createTextAndBind(this.contentsText, read
					.getChildByNodeId(BibliographicActivator.NODE_NAME_CONTENTS), read
					.getFeatureByNodeId(BibliographicActivator.NODE_NAME_CONTENTS), this);
		}

		if (this.url != null) {
			BindingUtil.createTextAndBind(this.url, read
					.getChildByNodeId(BibliographicActivator.NODE_NAME_URL), read
					.getFeatureByNodeId(BibliographicActivator.NODE_NAME_URL), this);
		}

		InformationUnit files = read.getChildByNodeId(BibliographicActivator.NODE_NAME_FILES);
		this.filesTableViewer.setInput(files);

	}

}
