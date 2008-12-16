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

package org.remus.infomngmnt.ui.trayaction;

import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.common.service.ITrayService;
import org.remus.infomngmnt.common.ui.UIUtil;
import org.remus.infomngmnt.common.ui.extension.IToolbarItemProvider;
import org.remus.infomngmnt.ui.UIPlugin;
import org.remus.infomngmnt.ui.dialogs.InfoUnitSelectionDialog;
import org.remus.infomngmnt.ui.editors.InformationEditor;
import org.remus.infomngmnt.ui.editors.InformationEditorInput;
import org.remus.infomngmnt.ui.provider.NavigationCellLabelProvider;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class OpenElementToolItem implements IToolbarItemProvider {

	/**
	 * 
	 */
	public OpenElementToolItem() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.remus.infomngmnt.common.ui.extension.IToolbarItemProvider#createToolItem(org.eclipse.swt.widgets.ToolBar)
	 */
	public ToolItem createToolItem(final ToolBar parent) {
		ToolItem toolItem = new ToolItem(parent, SWT.PUSH);
		toolItem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				InfoUnitSelectionDialog diag = new InfoUnitSelectionDialog(parent.getShell(),false);
				diag.setListLabelProvider(new NavigationCellLabelProvider());
				if (diag.open() == IDialogConstants.OK_ID) {
					Object[] result = diag.getResult();
					for (Object object : result) {
						if (object instanceof InformationUnitListItem) {
							try {
								PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(
										new InformationEditorInput((InformationUnitListItem) object), InformationEditor.ID);
								getTrayService().restoreFromTray(UIUtil.getPrimaryWindow().getShell());
							} catch (PartInitException e) {
								ErrorDialog.openError(parent.getShell(), "Error opening element", "Error opening element", e.getStatus());
							}
						}
					}

				}

			}
		});
		return toolItem;
	}

	public ITrayService getTrayService() {
		final BundleContext bundleContext = UIPlugin.getDefault()
		.getBundle().getBundleContext();
		final ServiceReference serviceReference = bundleContext
		.getServiceReference(ITrayService.class.getName());
		if (serviceReference != null) {
			return (ITrayService) bundleContext.getService(serviceReference);
		}
		return null;

	}


}
