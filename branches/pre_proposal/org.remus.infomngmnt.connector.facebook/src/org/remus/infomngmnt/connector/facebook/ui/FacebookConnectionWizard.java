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

package org.remus.infomngmnt.connector.facebook.ui;

import java.net.URL;
import java.net.URLDecoder;
import java.util.Collections;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Composite;

import org.remus.infomngmnt.RemoteRepository;
import org.remus.infomngmnt.connector.facebook.FacebookActivator;
import org.remus.infomngmnt.connector.facebook.FacebookCredentialProvider;
import org.remus.infomngmnt.connector.facebook.SessionKeyInspector;
import org.remus.infomngmnt.core.progress.CancelableRunnable;
import org.remus.infomngmnt.ui.remote.NewRepositoryWizard;
import org.remus.infomngmnt.util.StatusCreator;

import com.google.code.facebookapi.FacebookJaxbRestClient;
import com.google.code.facebookapi.ProfileField;
import com.google.code.facebookapi.schema.User;
import com.google.code.facebookapi.schema.UsersGetInfoResponse;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class FacebookConnectionWizard extends NewRepositoryWizard {

	private FacebookConnectionWizardPage page1;

	public static final String AUTH_URL = "http://www.facebook.com/login.php?api_key=" + FacebookActivator.API_KEY + "&connect_display=popup&v=1.0&next=http://www.facebook.com/connect/login_success.html&cancel_url=http://www.facebook.com/connect/login_failure.html&fbconnect=true&return_session=true&req_perms=read_stream,publish_stream,offline_access"; //$NON-NLS-1$

	/**
	 * 
	 */
	public FacebookConnectionWizard() {
		super();
		setWindowTitle("Facebook Repository");
	}

	@Override
	public void addPages() {
		addPage(this.page1);
	}

	@Override
	public boolean performFinish() {
		try {
			final String url = this.page1.getBrowser().getUrl();
			getContainer().run(true, true, new CancelableRunnable() {

				@Override
				protected IStatus runCancelableRunnable(final IProgressMonitor monitor) {
					try {
						URL url2 = new URL(url);

						String decodedQuery = URLDecoder.decode(url2.getQuery(), "UTF-8");
						String sessionKey = SessionKeyInspector.getSessionKey(decodedQuery);
						String secret = SessionKeyInspector.getSecret(decodedQuery);

						FacebookCredentialProvider credentialProvider = (FacebookCredentialProvider) getRepository()
								.getRepositoryImplementation().getCredentialProvider();
						credentialProvider.setPassword(secret);
						credentialProvider.setUserName(sessionKey);
						FacebookJaxbRestClient client = credentialProvider.getFacebook();
						long usersGetLoggedInUser = client.users_getLoggedInUser();
						UsersGetInfoResponse response = client.users_getInfo(Collections
								.<Long> singleton(usersGetLoggedInUser), Collections
								.singleton(ProfileField.NAME));
						User user = response.getUser().get(0);
						getRepository().setName(user.getName() + "@facebook");
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

			this.page1 = new FacebookConnectionWizardPage(new URL(AUTH_URL));

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
