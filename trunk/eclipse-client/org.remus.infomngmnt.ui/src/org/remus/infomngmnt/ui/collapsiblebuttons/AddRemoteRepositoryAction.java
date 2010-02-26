package org.remus.infomngmnt.ui.collapsiblebuttons;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.handlers.IHandlerService;

import org.remus.infomngmnt.common.ui.image.ResourceManager;
import org.remus.infomngmnt.ui.UIPlugin;
import org.remus.infomngmnt.util.StatusCreator;

class AddRemoteRepositoryAction extends Action {
	public static final String CMD_ID = "org.remus.infomngmnt.ui.newRepository"; //$NON-NLS-1$
	private IHandlerService service;
	private IViewSite viewSite;

	/**
	 * @return the viewSite
	 */
	private final IViewSite getViewSite() {
		return this.viewSite;
	}

	/**
	 * @param viewSite
	 *            the viewSite to set
	 */
	public final void setViewSite(final IViewSite viewSite) {
		this.viewSite = viewSite;
	}

	AddRemoteRepositoryAction() {
		setToolTipText("Add new repository");
		setText("Add new repository");
		setImageDescriptor(ResourceManager.getPluginImageDescriptor(UIPlugin.getDefault(),
				"icons/iconexperience/16/server_new.png"));
	}

	@Override
	public void run() {
		if (this.service == null) {
			this.service = (IHandlerService) getViewSite().getService(IHandlerService.class);
		}
		try {
			this.service.executeCommand(CMD_ID, null);
		} catch (Exception e) {
			ErrorDialog.openError(getViewSite().getShell(), "Error executing command",
					"Error creating new repository", StatusCreator.newStatus(e.getMessage()));
		}

	}
}