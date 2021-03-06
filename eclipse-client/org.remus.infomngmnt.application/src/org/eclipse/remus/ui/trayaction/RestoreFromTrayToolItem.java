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

package org.eclipse.remus.ui.trayaction;

import org.eclipse.remus.common.service.ITrayService;
import org.eclipse.remus.common.ui.UIUtil;
import org.eclipse.remus.ui.UIPlugin;
import org.eclipse.remus.ui.desktop.extension.IToolbarItemProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;


/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class RestoreFromTrayToolItem implements IToolbarItemProvider {

	/**
	 * 
	 */
	public RestoreFromTrayToolItem() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.common.ui.extension.IToolbarItemProvider#createToolItem
	 * (org.eclipse.swt.widgets.ToolBar)
	 */
	public ToolItem createToolItem(final ToolBar parent) {
		ToolItem toolItem = new ToolItem(parent, SWT.PUSH);
		toolItem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				getTrayService().restoreFromTray(UIUtil.getPrimaryWindow().getShell());
			}
		});
		return toolItem;
	}

	public ITrayService getTrayService() {
		final BundleContext bundleContext = UIPlugin.getDefault().getBundle().getBundleContext();
		final ServiceReference serviceReference = bundleContext
				.getServiceReference(ITrayService.class.getName());
		if (serviceReference != null) {
			return (ITrayService) bundleContext.getService(serviceReference);
		}
		return null;

	}

}
