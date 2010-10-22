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

package org.remus.infomngmnt.ide.integration;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.remus.common.service.ITrayService;
import org.eclipse.remus.common.ui.UIUtil;
import org.eclipse.remus.ui.desktop.panel.DesktopWindow;
import org.eclipse.swt.widgets.Shell;
import org.osgi.framework.ServiceReference;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class TrayHandler extends AbstractHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.
	 * ExecutionEvent)
	 */
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		ServiceReference serviceReference = Platform
				.getBundle(Activator.PLUGIN_ID).getBundleContext()
				.getServiceReference(ITrayService.class.getName());
		if (serviceReference == null
				|| Platform.getBundle(Activator.PLUGIN_ID).getBundleContext()
						.getService(serviceReference) == null) {
			Platform.getBundle(Activator.PLUGIN_ID)
					.getBundleContext()
					.registerService(ITrayService.class.getName(),
							new IDETrayService(), null);
		}
		serviceReference = Platform.getBundle(Activator.PLUGIN_ID)
				.getBundleContext()
				.getServiceReference(ITrayService.class.getName());
		ITrayService trayService = (ITrayService) Platform
				.getBundle(Activator.PLUGIN_ID).getBundleContext()
				.getService(serviceReference);
		if (!trayService.isMinimized()) {
			trayService.moveToTray(UIUtil.getPrimaryWindow().getShell());
		}

		return null;

	}

	private class IDETrayService implements ITrayService {

		private DesktopWindow window;

		public boolean isMinimized() {
			return this.window != null && this.window.getShell() != null
					&& this.window.getShell().isVisible();
		}

		public void moveToTray(final Shell shell) {
			this.window = new DesktopWindow(shell.getDisplay()) {
				@Override
				public boolean close() {
					boolean close = super.close();
					if (getReturnCode() == IDialogConstants.CANCEL_ID) {
						restoreFromTray(UIUtil.getPrimaryWindow().getShell());
					}
					return close;
				}
			};
			this.window.setBlockOnOpen(false);
			this.window.open();

		}

		public void restoreFromTray(final Shell shell) {
			UIUtil.getPrimaryWindow().getShell().setMinimized(false);
			UIUtil.getPrimaryWindow().getShell().setActive();
		}

	}

}
