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
package org.remus.infomngmnt.connector.googlecalendar.ui;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.IValueChangeListener;
import org.eclipse.core.databinding.observable.value.ValueChangeEvent;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.databinding.EMFDataBindingContext;
import org.eclipse.emf.databinding.EMFObservables;
import org.eclipse.jface.databinding.swt.ISWTObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.remus.InfomngmntPackage;
import org.eclipse.remus.RemoteRepository;
import org.eclipse.remus.core.remote.ValidateConnectionJob;
import org.eclipse.remus.core.remote.security.CredentialProvider;
import org.eclipse.remus.core.remote.sync.SyncUtil;
import org.eclipse.remus.model.remote.IRepository;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
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

import org.remus.infomngmnt.connector.googlecalendar.GoogleCalendarCredentialProvider;
import org.remus.infomngmnt.connector.googlecalendar.Messages;

public class GoogleCalendarConnectionWizardPage extends WizardPage {

	private Text nameText;
	private Text passwordText;
	private Text userNameText;
	private Text apiUrlText;
	private RemoteRepository repository;
	private IRepository repositoryDefinition;
	private boolean manualName;
	private Spinner startSpinner;
	private Spinner endSpinner;

	/**
	 * Create the wizard
	 */
	public GoogleCalendarConnectionWizardPage() {
		super("wizardPage"); //$NON-NLS-1$
		setTitle(Messages.GoogleCalendarConnectionWizardPage_GoogleCalendarConenctor);
		setDescription(Messages.GoogleCalendarConnectionWizardPage_EnterCredentials);
		this.manualName = false;
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
		nameLabel.setText(Messages.GoogleCalendarConnectionWizardPage_Name);

		this.nameText = new Text(group, SWT.BORDER);
		this.nameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		this.nameText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(final KeyEvent e) {
				GoogleCalendarConnectionWizardPage.this.manualName = true;

			}
		});

		final Label apiurlLabel = new Label(group, SWT.NONE);
		apiurlLabel.setText(Messages.GoogleCalendarConnectionWizardPage_APIUrl);

		this.apiUrlText = new Text(group, SWT.BORDER);
		this.apiUrlText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		this.apiUrlText.setEditable(false);

		final Group credentialsGroup = new Group(container, SWT.NONE);
		credentialsGroup.setText(Messages.GoogleCalendarConnectionWizardPage_Credentials);
		credentialsGroup.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		final GridLayout gridLayout_1 = new GridLayout();
		gridLayout_1.numColumns = 2;
		credentialsGroup.setLayout(gridLayout_1);

		final Label usernameLabel = new Label(credentialsGroup, SWT.NONE);
		usernameLabel.setText(Messages.GoogleCalendarConnectionWizardPage_Username);

		this.userNameText = new Text(credentialsGroup, SWT.BORDER);
		this.userNameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		final Label passwordLabel = new Label(credentialsGroup, SWT.NONE);
		passwordLabel.setText(Messages.GoogleCalendarConnectionWizardPage_Password);

		this.passwordText = new Text(credentialsGroup, SWT.BORDER | SWT.PASSWORD);
		this.passwordText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		new Label(credentialsGroup, SWT.NONE);

		final Button validateCredentialsButton = new Button(credentialsGroup, SWT.NONE);
		validateCredentialsButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				try {
					getContainer().run(true, true, new IRunnableWithProgress() {

						public void run(final IProgressMonitor monitor)
								throws InvocationTargetException, InterruptedException {
							IStatus runCancelableRunnable = new ValidateConnectionJob(
									GoogleCalendarConnectionWizardPage.this.repositoryDefinition)
									.runCancelableRunnable(monitor);
							if (!runCancelableRunnable.isOK()) {
								throw new InvocationTargetException(null);
							}

						}
					});
					setErrorMessage(null);
					setPageComplete(true);
				} catch (InvocationTargetException e) {
					setErrorMessage(Messages.GoogleCalendarConnectionWizardPage_ErrorValidatingSettings);
					setPageComplete(false);
				} catch (InterruptedException e) {
					// do nothing
				}

			}
		});
		validateCredentialsButton.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
		validateCredentialsButton.setText(Messages.GoogleCalendarConnectionWizardPage_ValidatingCredentials);

		this.nameText.setText(this.repositoryDefinition.getCredentialProvider().getUserName());

		Group grpSynchronizationTimespan = new Group(container, SWT.NONE);
		grpSynchronizationTimespan.setText(Messages.GoogleCalendarConnectionWizardPage_SyncTimespan);
		grpSynchronizationTimespan.setLayout(new GridLayout(3, false));
		grpSynchronizationTimespan.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false,
				1, 1));

		Label lblEventsInThe = new Label(grpSynchronizationTimespan, SWT.NONE);
		lblEventsInThe.setText(Messages.GoogleCalendarConnectionWizardPage_EventsPast);

		this.startSpinner = new Spinner(grpSynchronizationTimespan, SWT.BORDER);
		this.startSpinner.setPageIncrement(1);
		this.startSpinner.setMinimum(1);
		this.startSpinner.setSelection(6);

		Label lblMonth = new Label(grpSynchronizationTimespan, SWT.NONE);
		lblMonth.setText(Messages.GoogleCalendarConnectionWizardPage_Month);

		Label lblEventsInThe_1 = new Label(grpSynchronizationTimespan, SWT.NONE);
		lblEventsInThe_1.setText(Messages.GoogleCalendarConnectionWizardPage_EventsFuture);

		this.endSpinner = new Spinner(grpSynchronizationTimespan, SWT.BORDER);
		this.endSpinner.setPageIncrement(1);
		this.endSpinner.setMinimum(1);
		this.endSpinner.setSelection(6);

		Label label = new Label(grpSynchronizationTimespan, SWT.NONE);
		label.setText(Messages.GoogleCalendarConnectionWizardPage_Month);
		bindValuesToUi();
		setControl(container);
	}

	public void setRemoteObject(final RemoteRepository repository) {
		this.repository = repository;
		this.repositoryDefinition = SyncUtil
				.getRepositoryImplemenationByRemoteRepository(repository);
	}

	public void bindValuesToUi() {
		this.apiUrlText.setText(this.repository.getUrl());
		DataBindingContext ctx = new DataBindingContext();
		EMFDataBindingContext ectx = new EMFDataBindingContext();
		this.repositoryDefinition.getCredentialProvider().setIdentifier(this.repository.getId());
		IObservableValue beanUserName = BeansObservables.observeValue(this.repositoryDefinition
				.getCredentialProvider(), CredentialProvider.USER_NAME);
		ISWTObservableValue swtUserName = SWTObservables.observeText(this.userNameText, SWT.Modify);
		ctx.bindValue(swtUserName, beanUserName, null, null);
		ISWTObservableValue swtPassword = SWTObservables.observeText(this.passwordText, SWT.Modify);
		IObservableValue beanPassword = BeansObservables.observeValue(this.repositoryDefinition
				.getCredentialProvider(), CredentialProvider.PASSWORD);
		ctx.bindValue(swtPassword, beanPassword, null, null);

		IObservableValue beanStart = BeansObservables.observeValue(this.repositoryDefinition
				.getCredentialProvider(), GoogleCalendarCredentialProvider.START_TIME);
		ISWTObservableValue swtStart = SWTObservables.observeSelection(this.startSpinner);
		ctx.bindValue(swtStart, beanStart);

		IObservableValue beanEnd = BeansObservables.observeValue(this.repositoryDefinition
				.getCredentialProvider(), GoogleCalendarCredentialProvider.END_TIME);
		ISWTObservableValue swtEnd = SWTObservables.observeSelection(this.endSpinner);
		ctx.bindValue(swtEnd, beanEnd);

		ISWTObservableValue swtName = SWTObservables.observeText(this.nameText, SWT.Modify);
		IObservableValue emfName = EMFObservables.observeValue(this.repository,
				InfomngmntPackage.Literals.REMOTE_OBJECT__NAME);
		ectx.bindValue(swtName, emfName, null, null);

		swtUserName.addValueChangeListener(new IValueChangeListener() {
			public void handleValueChange(final ValueChangeEvent event) {
				if (!GoogleCalendarConnectionWizardPage.this.manualName) {
					String userName = (String) event.getObservableValue().getValue();
					GoogleCalendarConnectionWizardPage.this.nameText.setText(userName);
				}
			}
		});
	}
}
