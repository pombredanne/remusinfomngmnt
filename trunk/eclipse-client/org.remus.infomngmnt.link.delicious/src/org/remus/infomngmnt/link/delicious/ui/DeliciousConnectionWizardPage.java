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
package org.remus.infomngmnt.link.delicious.ui;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.ISWTObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.remus.infomngmnt.RemoteRepository;
import org.remus.infomngmnt.core.remote.IRepository;
import org.remus.infomngmnt.core.security.CredentialProvider;
import org.remus.infomngmnt.link.delicious.operations.ValidateConnectionJob;

public class DeliciousConnectionWizardPage extends WizardPage {

	private Text passwordText;
	private Text userNameText;
	private Text apiUrlText;
	private RemoteRepository repository;
	private IRepository repositoryDefinition;
	
	/**
	 * Create the wizard
	 */
	public DeliciousConnectionWizardPage() {
		super("wizardPage");
		setTitle("Delicious Connector");
		setDescription("Enter your login credentials");
	}

	/**
	 * Create contents of the wizard
	 * @param parent
	 */
	public void createControl(final Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		container.setLayout(new GridLayout());
		//
		

		final Group group = new Group(container, SWT.NONE);
		group.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		group.setLayout(gridLayout);

		final Label apiurlLabel = new Label(group, SWT.NONE);
		apiurlLabel.setText("API-Url:");

		this.apiUrlText = new Text(group, SWT.BORDER);
		final GridData gd_apiUrlText = new GridData(SWT.FILL, SWT.CENTER, true, false);
		this.apiUrlText.setLayoutData(gd_apiUrlText);
		this.apiUrlText.setEditable(false);

		final Group credentialsGroup = new Group(container, SWT.NONE);
		credentialsGroup.setText("Credentials");
		final GridData gd_credentialsGroup = new GridData(SWT.FILL, SWT.CENTER, false, false);
		credentialsGroup.setLayoutData(gd_credentialsGroup);
		final GridLayout gridLayout_1 = new GridLayout();
		gridLayout_1.numColumns = 2;
		credentialsGroup.setLayout(gridLayout_1);

		final Label usernameLabel = new Label(credentialsGroup, SWT.NONE);
		usernameLabel.setText("Username");

		this.userNameText = new Text(credentialsGroup, SWT.BORDER);
		final GridData gd_userNameText = new GridData(SWT.FILL, SWT.CENTER, true, false);
		this.userNameText.setLayoutData(gd_userNameText);

		final Label passwordLabel = new Label(credentialsGroup, SWT.NONE);
		passwordLabel.setText("Password");

		this.passwordText = new Text(credentialsGroup, SWT.BORDER | SWT.PASSWORD);
		final GridData gd_passwordText = new GridData(SWT.FILL, SWT.CENTER, true, false);
		this.passwordText.setLayoutData(gd_passwordText);
		new Label(credentialsGroup, SWT.NONE);

		final Button validateCredentialsButton = new Button(credentialsGroup, SWT.NONE);
		validateCredentialsButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				try {
					getContainer().run(
							true, 
							true, 
							new ValidateConnectionJob(DeliciousConnectionWizardPage.this.repositoryDefinition));
					setErrorMessage(null);
				} catch (InvocationTargetException e) {
					setErrorMessage("Error validating your settings");
				} catch (InterruptedException e) {
					// do nothing
				}

			}
		});
		final GridData gd_validateCredentialsButton = new GridData(SWT.RIGHT, SWT.CENTER, false, false);
		validateCredentialsButton.setLayoutData(gd_validateCredentialsButton);
		validateCredentialsButton.setText("Validate credentials");
		bindValuesToUi();
		setControl(container);
	}

	public void setRemoteObject(final RemoteRepository repository) {
		this.repository = repository;
	}

	public void bindValuesToUi() {
		this.apiUrlText.setText(this.repository.getUrl());
		DataBindingContext ctx = new DataBindingContext();
		try {
			this.repositoryDefinition.getCredentialProvider().setIdentifier(new URL(this.repository.getUrl()).getHost());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ISWTObservableValue swtUserName = SWTObservables.observeText(this.userNameText, SWT.Modify);
		IObservableValue beanUserName = BeansObservables.observeValue(this.repositoryDefinition.getCredentialProvider(), CredentialProvider.USER_NAME);
		ctx.bindValue(swtUserName, beanUserName, null, null);
		ISWTObservableValue swtPassword = SWTObservables.observeText(this.passwordText, SWT.Modify);
		IObservableValue beanPassword = BeansObservables.observeValue(this.repositoryDefinition.getCredentialProvider(), CredentialProvider.PASSWORD);
		ctx.bindValue(swtPassword, beanPassword, null, null);
	}

	public void setDefiningRepository(final IRepository definingRepository) {
		this.repositoryDefinition = definingRepository;

	}

}
