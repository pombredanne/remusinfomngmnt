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
import org.remus.infomngmnt.connector.rss.Messages;
import org.remus.infomngmnt.connector.rss.RssActivator;
import org.remus.infomngmnt.connector.rss.RssConnector;

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
		super("wizardPage"); //$NON-NLS-1$
		setTitle(Messages.RssConnectionWizardPage_RSSConnector);
		setDescription(Messages.RssConnectionWizardPage_EnterUrl);

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
		nameLabel.setText(Messages.RssConnectionWizardPage_Name);

		nameText = new Text(group, SWT.BORDER);
		nameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		final Label apiurlLabel = new Label(group, SWT.NONE);
		apiurlLabel.setText(Messages.RssConnectionWizardPage_URL);

		apiUrlText = new Text(group, SWT.BORDER);
		apiUrlText
				.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		new Label(group, SWT.NONE);

		Composite composite = new Composite(group, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		GridLayout gridLayout_2 = new GridLayout(2, false);
		gridLayout_2.marginWidth = 0;
		gridLayout_2.marginHeight = 0;
		composite.setLayout(gridLayout_2);

		final Button validateCredentialsButton = new Button(composite, SWT.NONE);
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
							Messages.RssConnectionWizardPage_ErrorValidating, e
									.getCause().getMessage(), ")")); //$NON-NLS-1$
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		validateCredentialsButton
				.setText(Messages.RssConnectionWizardPage_Validate);

		Button btnObtainFeedTitle = new Button(composite, SWT.NONE);
		btnObtainFeedTitle.setBounds(0, 0, 75, 25);
		btnObtainFeedTitle
				.setText(Messages.RssConnectionWizardPage_ObtainTitle);
		btnObtainFeedTitle.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				String feedTitle = ((RssConnector) repositoryDefinition)
						.getFeedTitle();
				if (feedTitle != null) {
					nameText.setText(feedTitle);
				}
			}
		});

		final Group group2 = new Group(container, SWT.NONE);
		group2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		group2.setText(Messages.RssConnectionWizardPage_Properties);
		final GridLayout gridLayout2 = new GridLayout();
		gridLayout2.numColumns = 2;
		group2.setLayout(gridLayout2);
		Label refreshLabel = new Label(group2, SWT.NONE);
		refreshLabel.setText(Messages.RssConnectionWizardPage_RefreshInterval);
		refreshRateSpinner = new Spinner(group2, SWT.BORDER);
		refreshRateSpinner.setMinimum(1);
		refreshRateSpinner.setIncrement(1);

		Label deleteLabel = new Label(group2, SWT.NONE);
		deleteLabel
				.setText(Messages.RssConnectionWizardPage_DeleteFeedOlderThan);
		deleteSpinner = new Spinner(group2, SWT.BORDER);
		deleteSpinner.setMinimum(1);
		deleteSpinner.setIncrement(1);

		final Group group3 = new Group(container, SWT.NONE);
		group3.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		group3.setText(Messages.RssConnectionWizardPage_Authentication);
		GridLayout gridLayout_1 = new GridLayout();
		gridLayout_1.numColumns = 2;
		group3.setLayout(gridLayout_1);
		authentificationButton = new Button(group3, SWT.CHECK);
		authentificationButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
				false, false, 2, 1));
		authentificationButton
				.setText(Messages.RssConnectionWizardPage_UseAuthentication);
		Label username = new Label(group3, SWT.NONE);
		username.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		username.setText(Messages.RssConnectionWizardPage_Username);

		userNameText = new Text(group3, SWT.BORDER);
		userNameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));

		Label lblPassword = new Label(group3, SWT.NONE);
		lblPassword.setText(Messages.RssConnectionWizardPage_Password);

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
		IObservableValue observeValue2 = EMFObservables.observeValue(
				repository, InfomngmntPackage.Literals.REMOTE_OBJECT__URL);

		refreshRateSpinner.setSelection(Integer.parseInt(repository
				.getOptions().get(
						RssActivator.REPOSITORY_OPTIONS_REFRESH_INTERVAL)));
		refreshRateSpinner.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				repository.getOptions().put(
						RssActivator.REPOSITORY_OPTIONS_REFRESH_INTERVAL,
						String.valueOf(refreshRateSpinner.getSelection()));
			}
		});

		deleteSpinner.setSelection(Integer.parseInt(repository.getOptions()
				.get(RssActivator.REPOSITORY_OPTIONS_DELETE_AFTER_X_DAY)));
		deleteSpinner.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				repository.getOptions().put(
						RssActivator.REPOSITORY_OPTIONS_DELETE_AFTER_X_DAY,
						String.valueOf(deleteSpinner.getSelection()));
			}
		});
		authentificationButton.setSelection(Boolean.valueOf(repository
				.getOptions().get(
						RssActivator.REPOSITORY_OPTIONS_BASIC_AUTHENTICATION)));
		ISWTObservableValue authEnabledObservable = SWTObservables
				.observeSelection(authentificationButton);
		ISWTObservableValue userNameEnabledObservable = SWTObservables
				.observeEnabled(userNameText);
		ISWTObservableValue passWordEnabledObservable = SWTObservables
				.observeEnabled(passwordText);
		ctx.bindValue(authEnabledObservable, userNameEnabledObservable, null,
				new UpdateValueStrategy(UpdateValueStrategy.POLICY_NEVER))
				.updateTargetToModel();
		ctx.bindValue(authEnabledObservable, passWordEnabledObservable, null,
				new UpdateValueStrategy(UpdateValueStrategy.POLICY_NEVER))
				.updateTargetToModel();
		authentificationButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				repository.getOptions().put(
						RssActivator.REPOSITORY_OPTIONS_BASIC_AUTHENTICATION,
						String.valueOf(authentificationButton.getSelection()));
			}
		});

		IObservableValue userNameModel = BeansObservables.observeValue(
				repositoryDefinition.getCredentialProvider(),
				CredentialProvider.USER_NAME);
		IObservableValue passwordModel = BeansObservables.observeValue(
				repositoryDefinition.getCredentialProvider(),
				CredentialProvider.PASSWORD);

		ISWTObservableValue userNameTarget = SWTObservables.observeText(
				userNameText, SWT.Modify);
		ISWTObservableValue passwordTarget = SWTObservables.observeText(
				passwordText, SWT.Modify);

		ctx.bindValue(observeText2, observeValue2);
		ctx.bindValue(userNameTarget, userNameModel);
		ctx.bindValue(passwordTarget, passwordModel);

	}
}
