/*******************************************************************************
 * Copyright (c) 2011 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/
package org.remus.infomngmnt.connector.dropbox.ui;

import java.io.IOException;

import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.remus.RemoteRepository;
import org.eclipse.remus.core.remote.AbstractExtensionRepository;
import org.eclipse.remus.core.remote.services.IRepositoryExtensionService;
import org.eclipse.remus.ui.remote.NewRepositoryWizard;
import org.eclipse.remus.ui.remote.RemoteUiActivator;
import org.eclipse.remus.ui.util.CancelableRunnable;
import org.eclipse.remus.util.StatusCreator;
import org.eclipse.swt.widgets.Composite;
import org.remus.infomngmnt.connector.dropbox.DropboxActivator;

import com.dropbox.client.Authenticator;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class NewDropboxRepositoryWizard extends NewRepositoryWizard {

	private DropboxConnectionWizardPage page1;
	private Authenticator auth;
	private AbstractExtensionRepository repositoryDefinition;

	/**
	 * 
	 */
	public NewDropboxRepositoryWizard() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void addPages() {
		addPage(page1);
	}

	@Override
	public void init(IStructuredSelection selection) {
		super.init(selection);
		try {
			auth = new Authenticator(DropboxActivator.CONNECTION_PROPERTIES);
			String url = auth.retrieveRequestToken("");
			page1 = new DropboxConnectionWizardPage(url);
			page1.setRemoteObject(getRepository());
		} catch (OAuthCommunicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OAuthException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean performFinish() {
		try {
			getContainer().run(true, true, new CancelableRunnable() {

				@Override
				protected IStatus runCancelableRunnable(
						final IProgressMonitor monitor) {
					try {
						auth.retrieveAccessToken("");
						getRepository().setUrl("/Remus");
						repositoryDefinition.getCredentialProvider()
								.setUserName(auth.getTokenKey());
						repositoryDefinition.getCredentialProvider()
								.setPassword(auth.getTokenSecret());
						return Status.OK_STATUS;
					} catch (Exception e) {
						final IStatus newStatus = StatusCreator.newStatus(
								"Error creating repository", e);
						getShell().getDisplay().syncExec(new Runnable() {

							public void run() {
								ErrorDialog.openError(getShell(), "Error",
										"Error connecting to repository",
										newStatus);

							}
						});
						return newStatus;
					}
				}

			});

		} catch (Exception e) {

			return false;
		}
		return super.performFinish();
	}

	@Override
	public void createPageControls(final Composite pageContainer) {
		// do nothing. no precreation.
	}

	@Override
	protected void configureRepository(final RemoteRepository newRemoteRepositry) {
		IRepositoryExtensionService extensionService = RemoteUiActivator
				.getDefault().getServiceTracker()
				.getService(IRepositoryExtensionService.class);
		try {
			repositoryDefinition = extensionService
					.getItemByRepository(repository);
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
