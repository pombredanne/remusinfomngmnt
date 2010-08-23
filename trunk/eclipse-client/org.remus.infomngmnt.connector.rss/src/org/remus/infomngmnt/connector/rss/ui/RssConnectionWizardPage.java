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
package org.remus.infomngmnt.connector.rss.ui;

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
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

import org.remus.infomngmnt.connector.rss.RssActivator;
import org.remus.infomngmnt.connector.rss.RssConnector;
import org.remus.infomngmnt.connector.rss.RssCredentialProvider;

public class RssConnectionWizardPage extends WizardPage {

	private Text nameText;

	private Text apiUrlText;
	private RemoteRepository repository;
	private IRepository repositoryDefinition;

	private Spinner refreshRateSpinner;

	private Spinner deleteSpinner;

	private Button authentificationButton;
	private Text userNameText;
	private Text passwordText;

	/**
	 * Create the wizard
	 */
	public RssConnectionWizardPage() {
		super("wizardPage");
		setTitle("RSS/Atom Connector");
		setDescription("Enter a feed url");

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

		this.nameText = new Text(group, SWT.BORDER);
		this.nameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		final Label apiurlLabel = new Label(group, SWT.NONE);
		apiurlLabel.setText("Feed-Url:");

		this.apiUrlText = new Text(group, SWT.BORDER);
		this.apiUrlText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		new Label(group, SWT.NONE);

		Composite composite = new Composite(group, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		GridLayout gridLayout_2 = new GridLayout(2, false);
		gridLayout_2.marginWidth = 0;
		gridLayout_2.marginHeight = 0;
		composite.setLayout(gridLayout_2);

		final Button validateCredentialsButton = new Button(composite, SWT.NONE);
		validateCredentialsButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				try {
					getContainer().run(true, false, new IRunnableWithProgress() {
						public void run(final IProgressMonitor monitor)
								throws InvocationTargetException, InterruptedException {
							IStatus validate = RssConnectionWizardPage.this.repositoryDefinition
									.validate();
							if (!validate.isOK()) {
								throw new InvocationTargetException(validate.getException());
							}
						}
					});
					setErrorMessage(null);
				} catch (InvocationTargetException e) {
					setErrorMessage(StringUtils.join("Error validating repository (", e.getCause()
							.getMessage(), ")"));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		validateCredentialsButton.setText("Validate");

		Button btnObtainFeedTitle = new Button(composite, SWT.NONE);
		btnObtainFeedTitle.setBounds(0, 0, 75, 25);
		btnObtainFeedTitle.setText("Obtain Feed Title");
		btnObtainFeedTitle.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				String feedTitle = ((RssConnector) RssConnectionWizardPage.this.repositoryDefinition)
						.getFeedTitle();
				if (feedTitle != null) {
					RssConnectionWizardPage.this.nameText.setText(feedTitle);
				}
			}
		});

		final Group group2 = new Group(container, SWT.NONE);
		group2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		group2.setText("Properties");
		final GridLayout gridLayout2 = new GridLayout();
		gridLayout2.numColumns = 2;
		group2.setLayout(gridLayout2);
		Label refreshLabel = new Label(group2, SWT.NONE);
		refreshLabel.setText("Refresh feed (minutes)");
		this.refreshRateSpinner = new Spinner(group2, SWT.BORDER);
		this.refreshRateSpinner.setMinimum(1);
		this.refreshRateSpinner.setIncrement(1);

		Label deleteLabel = new Label(group2, SWT.NONE);
		deleteLabel.setText("Delete feed-entries older than (days):");
		this.deleteSpinner = new Spinner(group2, SWT.BORDER);
		this.deleteSpinner.setMinimum(1);
		this.deleteSpinner.setIncrement(1);

		final Group group3 = new Group(container, SWT.NONE);
		group3.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		group3.setText("Authentication");
		GridLayout gridLayout_1 = new GridLayout();
		gridLayout_1.numColumns = 2;
		group3.setLayout(gridLayout_1);
		this.authentificationButton = new Button(group3, SWT.CHECK);
		this.authentificationButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false,
				2, 1));
		this.authentificationButton.setText("Use authentication");
		Label username = new Label(group3, SWT.NONE);
		username.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		username.setText("Username");

		this.userNameText = new Text(group3, SWT.BORDER);
		this.userNameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblPassword = new Label(group3, SWT.NONE);
		lblPassword.setText("Password");

		this.passwordText = new Text(group3, SWT.BORDER | SWT.PASSWORD);
		this.passwordText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		bindValuesToUi();
		setControl(container);
	}

	public void setRemoteObject(final RemoteRepository repository) {
		this.repository = repository;
		IRepositoryExtensionService extensionService = RemoteUiActivator.getDefault()
				.getServiceTracker().getService(IRepositoryExtensionService.class);
		try {
			this.repositoryDefinition = extensionService.getItemByRepository(repository);
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void bindValuesToUi() {
		DataBindingContext ctx = new DataBindingContext();
		this.repositoryDefinition.getCredentialProvider().setIdentifier(this.repository.getId());
		ISWTObservableValue observeText = SWTObservables.observeText(this.nameText, SWT.Modify);
		IObservableValue observeValue = EMFObservables.observeValue(this.repository,
				InfomngmntPackage.Literals.REMOTE_OBJECT__NAME);
		ctx.bindValue(observeText, observeValue);
		ISWTObservableValue observeText2 = SWTObservables.observeText(this.apiUrlText, SWT.Modify);
		IObservableValue observeValue3 = BeansObservables.observeValue(this.repositoryDefinition
				.getCredentialProvider(), RssCredentialProvider.URL);
		IObservableValue observeValue2 = EMFObservables.observeValue(this.repository,
				InfomngmntPackage.Literals.REMOTE_OBJECT__URL);

		this.refreshRateSpinner.setSelection(Integer.parseInt(this.repository.getOptions().get(
				RssActivator.REPOSITORY_OPTIONS_REFRESH_INTERVAL)));
		this.refreshRateSpinner.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				RssConnectionWizardPage.this.repository.getOptions().put(
						RssActivator.REPOSITORY_OPTIONS_REFRESH_INTERVAL,
						String.valueOf(RssConnectionWizardPage.this.refreshRateSpinner
								.getSelection()));
			}
		});

		this.deleteSpinner.setSelection(Integer.parseInt(this.repository.getOptions().get(
				RssActivator.REPOSITORY_OPTIONS_DELETE_AFTER_X_DAY)));
		this.deleteSpinner.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				RssConnectionWizardPage.this.repository.getOptions().put(
						RssActivator.REPOSITORY_OPTIONS_DELETE_AFTER_X_DAY,
						String.valueOf(RssConnectionWizardPage.this.deleteSpinner.getSelection()));
			}
		});
		this.authentificationButton.setSelection(Boolean.valueOf(this.repository.getOptions().get(
				RssActivator.REPOSITORY_OPTIONS_BASIC_AUTHENTICATION)));
		ISWTObservableValue authEnabledObservable = SWTObservables
				.observeSelection(this.authentificationButton);
		ISWTObservableValue userNameEnabledObservable = SWTObservables
				.observeEnabled(this.userNameText);
		ISWTObservableValue passWordEnabledObservable = SWTObservables
				.observeEnabled(this.passwordText);
		ctx.bindValue(authEnabledObservable, userNameEnabledObservable, null,
				new UpdateValueStrategy(UpdateValueStrategy.POLICY_NEVER)).updateTargetToModel();
		ctx.bindValue(authEnabledObservable, passWordEnabledObservable, null,
				new UpdateValueStrategy(UpdateValueStrategy.POLICY_NEVER)).updateTargetToModel();
		this.authentificationButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				RssConnectionWizardPage.this.repository.getOptions().put(
						RssActivator.REPOSITORY_OPTIONS_BASIC_AUTHENTICATION,
						String.valueOf(RssConnectionWizardPage.this.authentificationButton
								.getSelection()));
			}
		});

		IObservableValue userNameModel = BeansObservables.observeValue(this.repositoryDefinition
				.getCredentialProvider(), CredentialProvider.USER_NAME);
		IObservableValue passwordModel = BeansObservables.observeValue(this.repositoryDefinition
				.getCredentialProvider(), CredentialProvider.PASSWORD);

		ISWTObservableValue userNameTarget = SWTObservables.observeText(this.userNameText,
				SWT.Modify);
		ISWTObservableValue passwordTarget = SWTObservables.observeText(this.passwordText,
				SWT.Modify);

		ctx.bindValue(observeText2, observeValue3);
		ctx.bindValue(observeValue3, observeValue2);
		ctx.bindValue(userNameTarget, userNameModel);
		ctx.bindValue(passwordTarget, passwordModel);

	}
}
