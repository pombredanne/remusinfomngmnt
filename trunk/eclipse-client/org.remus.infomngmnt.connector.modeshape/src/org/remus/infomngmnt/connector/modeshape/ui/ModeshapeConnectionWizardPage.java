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
package org.remus.infomngmnt.connector.modeshape.ui;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.databinding.EMFObservables;
import org.eclipse.jface.databinding.swt.ISWTObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.remus.InfomngmntPackage;
import org.eclipse.remus.RemoteRepository;
import org.eclipse.remus.common.core.util.StringUtils;
import org.eclipse.remus.core.remote.security.CredentialProvider;
import org.eclipse.remus.core.remote.services.IRepositoryExtensionService;
import org.eclipse.remus.model.remote.IRepository;
import org.eclipse.remus.ui.remote.RemoteUiActivator;
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
import org.remus.infomngmnt.connector.modeshape.ModeshapeCredentialProvider;

public class ModeshapeConnectionWizardPage extends WizardPage {

	private Text nameText;

	private Text apiUrlText;
	private RemoteRepository repository;
	private IRepository repositoryDefinition;

	private Button useSeparateWorkspaceButton;
	private Text userNameText;
	private Text passwordText;
	private Text workspaceText;

	/**
	 * Create the wizard
	 */
	public ModeshapeConnectionWizardPage() {
		super("wizardPage");
		setTitle("Modeshape Connector");
		setDescription("Enter a url to your Modeshape Repository");

	}

	/**
	 * Create contents of the wizard
	 * 
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

		final Label nameLabel = new Label(group, SWT.NONE);
		nameLabel.setText("Name:");

		nameText = new Text(group, SWT.BORDER);
		nameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		final Label apiurlLabel = new Label(group, SWT.NONE);
		apiurlLabel.setText("Url");

		apiUrlText = new Text(group, SWT.BORDER);
		apiUrlText
				.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		new Label(group, SWT.NONE);
		useSeparateWorkspaceButton = new Button(group, SWT.CHECK);
		useSeparateWorkspaceButton.setText("Use separate workspace");

		Label lblWorkspace = new Label(group, SWT.NONE);
		lblWorkspace.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblWorkspace.setText("Workspace");

		workspaceText = new Text(group, SWT.BORDER);
		workspaceText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		new Label(group, SWT.NONE);

		Composite composite = new Composite(group, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		GridLayout gridLayout_2 = new GridLayout(1, false);
		gridLayout_2.marginWidth = 0;
		gridLayout_2.marginHeight = 0;
		composite.setLayout(gridLayout_2);

		final Button validateCredentialsButton = new Button(composite, SWT.NONE);
		validateCredentialsButton.setLayoutData(new GridData(SWT.RIGHT,
				SWT.CENTER, false, false, 1, 1));
		validateCredentialsButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				try {
					getContainer().run(true, false,
							new IRunnableWithProgress() {
								public void run(final IProgressMonitor monitor)
										throws InvocationTargetException,
										InterruptedException {
									IStatus validate = repositoryDefinition
											.validate();
									if (!validate.isOK()) {
										throw new InvocationTargetException(
												validate.getException());
									}
								}
							});
					setErrorMessage(null);
				} catch (InvocationTargetException e) {
					setErrorMessage(StringUtils.join(
							"Error validating repository (", e.getCause()
									.getMessage(), ")"));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		validateCredentialsButton.setText("Validate");

		final Group group3 = new Group(container, SWT.NONE);
		group3.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		group3.setText("Authentication");
		GridLayout gridLayout_1 = new GridLayout();
		gridLayout_1.numColumns = 2;
		group3.setLayout(gridLayout_1);
		Label username = new Label(group3, SWT.NONE);
		username.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		username.setText("Username");

		userNameText = new Text(group3, SWT.BORDER);
		userNameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));

		Label lblPassword = new Label(group3, SWT.NONE);
		lblPassword.setText("Password");

		passwordText = new Text(group3, SWT.BORDER | SWT.PASSWORD);
		passwordText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));

		bindValuesToUi();
		setControl(container);
	}

	public void setRemoteObject(final RemoteRepository repository) {
		this.repository = repository;
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

	public void bindValuesToUi() {
		DataBindingContext ctx = new DataBindingContext();
		repositoryDefinition.getCredentialProvider().setIdentifier(
				repository.getId());
		ISWTObservableValue observeText = SWTObservables.observeText(nameText,
				SWT.Modify);
		IObservableValue observeValue = EMFObservables.observeValue(repository,
				InfomngmntPackage.Literals.REMOTE_OBJECT__NAME);
		ctx.bindValue(observeText, observeValue);
		ISWTObservableValue observeText2 = SWTObservables.observeText(
				apiUrlText, SWT.Modify);
		IObservableValue observeValue3 = BeansObservables.observeValue(
				repositoryDefinition.getCredentialProvider(),
				ModeshapeCredentialProvider.URL);
		IObservableValue observeValue2 = EMFObservables.observeValue(
				repository, InfomngmntPackage.Literals.REMOTE_OBJECT__URL);

		ISWTObservableValue authEnabledObservable = SWTObservables
				.observeSelection(useSeparateWorkspaceButton);
		ISWTObservableValue userNameEnabledObservable = SWTObservables
				.observeEnabled(workspaceText);

		ctx.bindValue(authEnabledObservable, userNameEnabledObservable, null,
				new UpdateValueStrategy(UpdateValueStrategy.POLICY_NEVER))
				.updateTargetToModel();

		IObservableValue userNameModel = BeansObservables.observeValue(
				repositoryDefinition.getCredentialProvider(),
				CredentialProvider.USER_NAME);
		IObservableValue passwordModel = BeansObservables.observeValue(
				repositoryDefinition.getCredentialProvider(),
				CredentialProvider.PASSWORD);
		IObservableValue workspaceModel = BeansObservables.observeValue(
				repositoryDefinition.getCredentialProvider(),
				ModeshapeCredentialProvider.WORKSPACE);
		IObservableValue separateWorkspaceModel = BeansObservables
				.observeValue(repositoryDefinition.getCredentialProvider(),
						ModeshapeCredentialProvider.USESEPARATEWORKSPACE);

		ISWTObservableValue userNameTarget = SWTObservables.observeText(
				userNameText, SWT.Modify);
		ISWTObservableValue passwordTarget = SWTObservables.observeText(
				passwordText, SWT.Modify);
		ISWTObservableValue workspaceTarget = SWTObservables.observeText(
				workspaceText, SWT.Modify);
		ISWTObservableValue separateWorkspaceTarget = SWTObservables
				.observeSelection(useSeparateWorkspaceButton);

		ctx.bindValue(observeText2, observeValue3);
		ctx.bindValue(observeValue3, observeValue2);
		ctx.bindValue(userNameTarget, userNameModel);
		ctx.bindValue(passwordTarget, passwordModel);
		ctx.bindValue(workspaceTarget, workspaceModel);
		ctx.bindValue(separateWorkspaceTarget, separateWorkspaceModel);

	}
}
