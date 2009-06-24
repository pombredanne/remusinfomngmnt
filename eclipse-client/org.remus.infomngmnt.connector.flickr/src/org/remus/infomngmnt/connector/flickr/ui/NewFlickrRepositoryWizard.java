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

package org.remus.infomngmnt.connector.flickr.ui;

import java.net.URL;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Composite;

import com.aetrion.flickr.Flickr;
import com.aetrion.flickr.REST;
import com.aetrion.flickr.auth.Auth;
import com.aetrion.flickr.auth.AuthInterface;
import com.aetrion.flickr.auth.Permission;
import com.aetrion.flickr.people.User;

import org.remus.infomngmnt.RemoteRepository;
import org.remus.infomngmnt.connector.flickr.FlickrCredentials;
import org.remus.infomngmnt.connector.flickr.FlickrPlugin;
import org.remus.infomngmnt.core.model.StatusCreator;
import org.remus.infomngmnt.core.progress.CancelableRunnable;
import org.remus.infomngmnt.ui.remote.NewRepositoryWizard;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class NewFlickrRepositoryWizard extends NewRepositoryWizard {

	private FlickConnectionWizardPage page1;
	private Flickr flickr;
	private AuthInterface authInterface;
	private String frob;

	/**
	 * 
	 */
	public NewFlickrRepositoryWizard() {
		super();
		setWindowTitle("Flickr Repository");
	}

	@Override
	public void addPages() {
		addPage(this.page1);
	}

	@Override
	public boolean performFinish() {
		try {
			getContainer().run(true, true, new CancelableRunnable() {

				@Override
				protected IStatus runCancelableRunnable(final IProgressMonitor monitor) {
					try {
						monitor.beginTask("Checking credentials", IProgressMonitor.UNKNOWN);
						Auth auth = NewFlickrRepositoryWizard.this.authInterface
								.getToken(NewFlickrRepositoryWizard.this.frob);
						FlickrCredentials credentialProvider = (FlickrCredentials) getRepository()
								.getRepositoryImplementation().getCredentialProvider();
						User user = auth.getUser();
						credentialProvider.setUserName(user.getUsername());
						credentialProvider.setPassword(auth.getToken());
						credentialProvider.setInternalId(user.getId());
						credentialProvider.setRealName(user.getRealName());
						getRepository().setName(user.getUsername() + "@flickr");
						getRepository().setUrl(
								getRepository().getRepositoryImplementation().getRepositoryUrl());
					} catch (Exception e) {
						return StatusCreator
								.newStatus("Error validating credentials", e.getCause());
					}
					return Status.OK_STATUS;
				}

			});

		} catch (Exception e) {
			ErrorDialog.openError(getShell(), "Error", "Error finishing repository", StatusCreator
					.newStatus("Error initializing connector", e));
			return false;
		}
		return super.performFinish();
	}

	@Override
	public void init(final IStructuredSelection selection) {
		super.init(selection);
		try {
			this.flickr = new Flickr(FlickrPlugin.API_KEY, FlickrPlugin.SHARED_SECRET, new REST());
			this.authInterface = this.flickr.getAuthInterface();
			this.frob = this.authInterface.getFrob();
			URL buildAuthenticationUrl = this.authInterface.buildAuthenticationUrl(
					Permission.DELETE, this.frob);
			this.page1 = new FlickConnectionWizardPage(buildAuthenticationUrl);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void createPageControls(final Composite pageContainer) {
		// do nothing. no precreation.
	}

	@Override
	protected void configureRepository(final RemoteRepository newRemoteRepositry) {
		// TODO Auto-generated method stub

	}

}
