/*******************************************************************************
 * Copyright (c) 2008 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/

package org.remus.infomngmnt.ui.tray;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.ui.action.DeleteAction;
import org.eclipse.emf.edit.ui.dnd.EditingDomainViewerDropAdapter;
import org.eclipse.emf.edit.ui.dnd.LocalTransfer;
import org.eclipse.emf.edit.ui.dnd.ViewerDragAdapter;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import org.remus.infomngmnt.common.ui.UIUtil;
import org.remus.infomngmnt.common.ui.extension.ITraySectionDefinition;
import org.remus.infomngmnt.common.ui.extension.TraySectionManager;
import org.remus.infomngmnt.common.ui.model.UIEditingUtil;
import org.remus.infomngmnt.ui.desktop.TrayConfigurationManager;
import org.remus.infomngmt.common.ui.uimodel.TraySection;
import org.remus.infomngmt.common.ui.uimodel.TraySectionCollection;
import org.remus.infomngmt.common.ui.uimodel.UIModelFactory;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class DesktopPanelPreferencePage extends PreferencePage implements
IWorkbenchPreferencePage {


	private TraySectionCollection sections;
	private TableViewer tableViewer;
	private TableViewer avaialableTemplatesViewer;
	private EditingDomain editingDomain;
	private IDialogSettings dialogSettings;
	private DeleteAction deleteAction;

	public static final String DIALOG_SETTINGS_SECTION = "trayPrefDialogSetting"; //$NON-NLS-1$
	/**
	 * 
	 */
	public DesktopPanelPreferencePage() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param title
	 */
	public DesktopPanelPreferencePage(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param title
	 * @param image
	 */
	public DesktopPanelPreferencePage(String title, ImageDescriptor image) {
		super(title, image);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.preference.PreferencePage#createContents(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createContents(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout());

		final SashForm sashForm = new SashForm(composite, SWT.NONE);
		sashForm.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		final SashForm sashForm_1 = new SashForm(sashForm, SWT.VERTICAL);

		final Group desktopConfigurationGroup = new Group(sashForm_1, SWT.NONE);
		desktopConfigurationGroup.setText("Desktop Configuration");
		desktopConfigurationGroup.setLayout(new FillLayout());

		this.tableViewer = new TableViewer(desktopConfigurationGroup, SWT.BORDER);
		this.tableViewer.setContentProvider(new AdapterFactoryContentProvider(UIEditingUtil.getInstance().getAdapterFactory()));
		this.tableViewer.setLabelProvider(new AdapterFactoryLabelProvider(UIEditingUtil.getInstance().getAdapterFactory()));
		this.tableViewer.setInput(this.sections);


		final Group avaiableTemplatesGroup = new Group(sashForm_1, SWT.NONE);
		avaiableTemplatesGroup.setText("Available Templates");
		avaiableTemplatesGroup.setLayout(new GridLayout());


		this.avaialableTemplatesViewer = new TableViewer(avaiableTemplatesGroup, SWT.BORDER);
		this.avaialableTemplatesViewer.getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		sashForm_1.setWeights(new int[] {7, 3 });
		this.avaialableTemplatesViewer.setContentProvider(new ArrayContentProvider());
		this.avaialableTemplatesViewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				return ((ITraySectionDefinition) element).getLabel();
			}
		});
		this.avaialableTemplatesViewer.setInput(TraySectionManager.getInstance().getAllSections());


		final Group propertiesGroup = new Group(sashForm, SWT.NONE);
		propertiesGroup.setText("Properties");
		sashForm.setWeights(new int[] {1, 1 });
		initDnd();
		hookContextMenu();
		return composite;
	}

	private void hookContextMenu() {
		this.deleteAction = new DeleteAction(this.editingDomain);
		this.tableViewer.addSelectionChangedListener(this.deleteAction);
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				manager.add(DesktopPanelPreferencePage.this.deleteAction);
				manager.update(true);
			}
		});
		Menu menu = menuMgr.createContextMenu(this.tableViewer.getControl());
		this.tableViewer.getControl().setMenu(menu);

	}

	@Override
	protected void performApply() {
		UIEditingUtil.getInstance().saveObjectToResource(this.sections);
		super.performApply();
	}

	@Override
	protected void performDefaults() {
		while (this.editingDomain.getCommandStack().getUndoCommand() != null) {
			this.editingDomain.getCommandStack().undo();
		}
		super.performDefaults();
	}

	private void initDnd() {

		// Drag and Drop of the tray-definition
		int dndOperations = DND.DROP_COPY | DND.DROP_MOVE | DND.DROP_LINK;
		Transfer[] transfers = new Transfer[] { LocalTransfer.getInstance() };
		this.tableViewer.addDragSupport(DND.DROP_MOVE, transfers, new ViewerDragAdapter(this.tableViewer));
		this.tableViewer.addDropSupport(dndOperations, transfers, new EditingDomainViewerDropAdapter(this.editingDomain, this.tableViewer) {
			@Override
			protected Object extractDropTarget(Widget item) {
				/*
				 * For allowing drag'n'drop also on
				 * an empty viewer we have to return
				 * the root, so that it's possible
				 * to drop items on a blank viewer.
				 */
				if (item == null) {
					return this.viewer.getInput();
				}
				return super.extractDropTarget(item);
			}
		});

		// Drag of the available viewer
		this.avaialableTemplatesViewer.addDragSupport(dndOperations, transfers, new DragSourceListener() {
			public void dragFinished(DragSourceEvent event) {
				// do nothing
			}

			/* (non-Javadoc)
			 * @see org.eclipse.swt.dnd.DragSourceListener#dragSetData(org.eclipse.swt.dnd.DragSourceEvent)
			 */
			public void dragSetData(DragSourceEvent event) {
				/*
				 *  by default we're creating a new object, that
				 *  can be dropped as a new TraySection into the list
				 *  of rendered section
				 */
				if (LocalTransfer.getInstance().isSupportedType(event.dataType)) {
					ITraySectionDefinition firstElement = (ITraySectionDefinition) ((IStructuredSelection) DesktopPanelPreferencePage.this.avaialableTemplatesViewer.getSelection()).getFirstElement();
					TraySection newSection = UIModelFactory.eINSTANCE.createTraySection();
					newSection.setImplementation(firstElement.getImplementation());
					newSection.setTemplateId(firstElement.getId());
					newSection.setName(firstElement.getLabel());
					event.data = new StructuredSelection(newSection);
				}
			}

			public void dragStart(DragSourceEvent event) {
				ITraySectionDefinition element = (ITraySectionDefinition) ((IStructuredSelection) DesktopPanelPreferencePage.this.avaialableTemplatesViewer
						.getSelection()).getFirstElement();
				if (!element.isMultiple()) {
					/*
					 * If an element is not allowed to be more than one
					 * time in the desktop panel, the drag will be supressed
					 * if this template is already in use.
					 */
					EList<TraySection> sections2 = DesktopPanelPreferencePage.this.sections.getSections();
					for (TraySection currentSection : sections2) {
						if (currentSection.getTemplateId().equals(element.getId())) {
							event.detail = DND.DROP_NONE;
							event.data = null;
							return;
						}
					}
				}
			}
		});
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
		this.sections = TrayConfigurationManager.getInstance().getTraySections();
		this.editingDomain = UIEditingUtil.getInstance().createNewEditingDomain();
		this.dialogSettings = UIUtil.getDialogSettings(DIALOG_SETTINGS_SECTION);
	}



}
