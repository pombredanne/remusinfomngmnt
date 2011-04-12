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

package org.remus.infomngmnt.task.tray;

import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.StringEscapeUtils;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.remus.InformationUnit;
import org.eclipse.remus.InformationUnitListItem;
import org.eclipse.remus.common.core.util.StringUtils;
import org.eclipse.remus.common.ui.image.ResourceManager;
import org.eclipse.remus.core.model.InformationStructureEdit;
import org.eclipse.remus.core.services.IApplicationModel;
import org.eclipse.remus.core.services.IEditingHandler;
import org.eclipse.remus.services.RemusServiceTracker;
import org.eclipse.remus.ui.desktop.extension.AbstractTraySection;
import org.eclipse.remus.ui.viewer.provider.NavigatorDecoratingLabelProvider;
import org.eclipse.remus.uimodel.TraySection;
import org.eclipse.remus.util.InformationUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

import org.remus.infomngmnt.task.TaskActivator;
import org.remus.infomngmnt.task.messages.Messages;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class WorkLoggerTray extends AbstractTraySection {

	public static final String SELECTED_TASK_ID = "SELECTED_TASK_ID"; //$NON-NLS-1$
	private static final String LAST_START_DATE = "LAST_START_DATE"; //$NON-NLS-1$
	private static final String TRACKING = "TRACKING"; //$NON-NLS-1$
	private static final String LAST_COMMENT = "LAST_COMMENT"; //$NON-NLS-1$
	private InformationUnitListItem itemById;
	private boolean tracking;
	private Date startTime;
	private String description;
	private FormText createHyperlink;
	private ToolBarManager toolBarManager;
	private Action startAction;
	private Action stopAction;
	private Action resetAction;
	private RemusServiceTracker serviceTracker;
	private IEditingHandler editingHandler;
	private IApplicationModel applicationModel;

	@Override
	public void createDetailsPart(Composite parent) {
		TableWrapLayout layout = new TableWrapLayout();

		parent.setLayout(layout);
		buildHyperLink(parent);
		this.serviceTracker = new RemusServiceTracker(Platform.getBundle(TaskActivator.PLUGIN_ID));
		this.editingHandler = this.serviceTracker.getService(IEditingHandler.class);
		this.applicationModel = this.serviceTracker.getService(IApplicationModel.class);
	}

	private void buildHyperLink(final Composite parent) {
		this.createHyperlink = this.toolkit.createFormText(parent, false);
		TableWrapData layoutData = new TableWrapData(TableWrapData.CENTER);
		layoutData.align = TableWrapData.CENTER;
		layoutData.grabHorizontal = true;
		this.createHyperlink.setLayoutData(layoutData);
		refreshHyperLinkLabel();
		this.createHyperlink.addHyperlinkListener(new HyperlinkAdapter() {
			@Override
			public void linkActivated(HyperlinkEvent e) {
				Set<? extends EObject> allItemsByType = InformationUtil
						.getAllItemsByType(TaskActivator.INFO_TYPE_ID);
				NavigatorDecoratingLabelProvider labelProvider = new NavigatorDecoratingLabelProvider(
						new AdapterFactoryLabelProvider(WorkLoggerTray.this.editingHandler
								.getAdapterFactory())) {
					@Override
					public String getText(final Object element) {
						if (element instanceof InformationUnitListItem) {
							return InformationUtil
									.getFullReadablePath((InformationUnitListItem) element);
						}
						return super.getText(element);
					}
				};
				ElementListSelectionDialog dialog = new ElementListSelectionDialog(parent
						.getShell(), labelProvider);
				dialog.setAllowDuplicates(false);
				dialog.setElements(allItemsByType.toArray());
				dialog.setEmptySelectionMessage(Messages.WorkLoggerTray_SelectionRequired);
				dialog.setMultipleSelection(false);
				dialog.setTitle(Messages.WorkLoggerTray_SelectTask);
				dialog.setMessage(Messages.WorkLoggerTray_SelectTaskDetails);
				dialog.setIgnoreCase(true);
				dialog.setBlockOnOpen(true);
				if (dialog.open() == IDialogConstants.OK_ID) {
					setSelectedInfoUnit(((InformationUnitListItem) dialog.getFirstResult()).getId());
				}
			}
		});

		ToolBar tb = new ToolBar(parent, SWT.HORIZONTAL | SWT.FLAT);
		TableWrapData layoutData2 = new TableWrapData(TableWrapData.CENTER);
		layoutData2.align = TableWrapData.CENTER;
		layoutData2.grabHorizontal = true;
		tb.setLayoutData(layoutData2);
		this.toolBarManager = new ToolBarManager(tb);
		this.startAction = new Action(Messages.WorkLoggerTray_StartTimetracking, IAction.AS_CHECK_BOX) {
			@Override
			public ImageDescriptor getImageDescriptor() {
				return ResourceManager.getPluginImageDescriptor(TaskActivator.getDefault(),
						"icons/iconexperience/tray/stopwatch_run.png"); //$NON-NLS-1$
			}

			@Override
			public void run() {
				if (!isChecked()) {
					stopTracking();
				} else {
					EnterDescriptionDialog enterDescriptionDialog = new EnterDescriptionDialog(
							parent.getShell(), WorkLoggerTray.this.description);
					if (enterDescriptionDialog.open() == IDialogConstants.OK_ID) {
						WorkLoggerTray.this.tracking = true;
						WorkLoggerTray.this.startTime = new Date();
						WorkLoggerTray.this.description = enterDescriptionDialog.getMessage();

					} else {
						setChecked(false);
					}
				}
				updateButtonStatus();
			}
		};
		this.stopAction = new Action(Messages.WorkLoggerTray_StopAndSave, IAction.AS_PUSH_BUTTON) {
			@Override
			public ImageDescriptor getImageDescriptor() {
				return ResourceManager.getPluginImageDescriptor(TaskActivator.getDefault(),
						"icons/iconexperience/tray/stopwatch_stop.png"); //$NON-NLS-1$
			}

			@Override
			public void run() {
				stopTracking();
				updateButtonStatus();
			}
		};
		this.resetAction = new Action(Messages.WorkLoggerTray_StopAndDontSave, IAction.AS_PUSH_BUTTON) {
			@Override
			public ImageDescriptor getImageDescriptor() {
				return ResourceManager.getPluginImageDescriptor(TaskActivator.getDefault(),
						"icons/iconexperience/tray/stopwatch_reset.png"); //$NON-NLS-1$
			}

			@Override
			public void run() {
				resetTracking();
				updateButtonStatus();
			}
		};
		this.toolBarManager.add(this.startAction);
		this.toolBarManager.add(this.stopAction);
		this.toolBarManager.add(new Separator());
		this.toolBarManager.add(this.resetAction);
		this.toolBarManager.update(true);
		this.toolkit.adapt(tb);
		updateButtonStatus();

	}

	protected void setSelectedInfoUnit(String id) {
		if (this.tracking) {
			this.tracking = false;
			stopTracking();
		}
		if (id != null) {
			this.itemById = this.applicationModel.getItemById(id, null);
		}
		refreshHyperLinkLabel();
		updateButtonStatus();
	}

	private void saveState() {
		if (this.itemById != null) {
			getSectionDefinition().getPreferenceOptions().put(SELECTED_TASK_ID,
					this.itemById.getId());
		}
		if (this.startTime != null) {
			getSectionDefinition().getPreferenceOptions().put(LAST_START_DATE,
					String.valueOf(this.startTime.getTime()));
		}
		getSectionDefinition().getPreferenceOptions().put(TRACKING, String.valueOf(this.tracking));
		getSectionDefinition().getPreferenceOptions().put(LAST_COMMENT,
				String.valueOf(this.description));

	}

	void refreshHyperLinkLabel() {
		String text = Messages.WorkLoggerTray_ClicktoSelectTask;
		if (this.itemById != null) {
			text = StringEscapeUtils.escapeXml(this.itemById.getLabel());
		}
		this.createHyperlink.setText(StringUtils
				.join(Messages.WorkLoggerTray_Task, text, Messages.WorkLoggerTray_14), true, false);
	}

	@Override
	public void dispose() {
		saveState();
		super.dispose();
	}

	private void stopTracking() {
		InformationUnit adapter = (InformationUnit) this.itemById.getAdapter(InformationUnit.class);
		if (adapter != null) {

			InformationStructureEdit edit = InformationStructureEdit
					.newSession(TaskActivator.INFO_TYPE_ID);
			InformationUnit informationUnit = edit.createSubType(
					TaskActivator.NODE_NAME_WORKED_UNIT, null);
			edit.addDynamicNode(adapter, informationUnit, null);
			edit.setValue(informationUnit, TaskActivator.NODE_NAME_WORKED_UNIT_DESCRIPTION,
					this.description);
			edit.setValue(informationUnit, TaskActivator.NODE_NAME_WORKED_UNIT_STARTED,
					this.startTime);
			edit.setValue(informationUnit, TaskActivator.NODE_NAME_WORKED_UNIT_END, new Date());

			this.editingHandler.saveObjectToResource(adapter);
			this.startTime = null;
		}
		this.tracking = false;

	}

	private void resetTracking() {
		this.startTime = null;

	}

	private void updateButtonStatus() {
		this.startAction.setChecked(this.tracking);
		if (this.tracking) {
			this.startAction.setEnabled(true);
			this.stopAction.setEnabled(true);
			this.resetAction.setEnabled(true);
		} else {
			this.stopAction.setEnabled(false);
			this.resetAction.setEnabled(false);

			this.startAction.setEnabled(this.itemById != null);

		}

	}

	@Override
	public void init(FormToolkit pToolkit, TraySection section) {
		String string = section.getPreferenceOptions().get(SELECTED_TASK_ID);
		if (string != null) {
			this.itemById = this.applicationModel.getItemById(string, null);
		}
		this.description = section.getPreferenceOptions().get(LAST_COMMENT);
		if (this.itemById != null) {
			this.tracking = Boolean.valueOf(section.getPreferenceOptions().get(TRACKING));
			try {
				this.startTime = new Date(Long.parseLong(section.getPreferenceOptions().get(
						LAST_START_DATE)));
			} catch (NumberFormatException e) {
				this.tracking = false;
			}
		}
		super.init(pToolkit, section);
	}

}
