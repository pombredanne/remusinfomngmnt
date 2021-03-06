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
package org.remus.infomngmnt.connector.youtube.readonly;

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
import org.eclipse.remus.common.core.util.StringUtils;
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
import org.eclipse.swt.widgets.Text;

import org.remus.infomngmnt.connector.youtube.YoutubeActivator;
import org.remus.infomngmnt.connector.youtube.messages.Messages;
import org.remus.infomngmnt.connector.youtube.preferences.PreferenceInitializer;

public class YoutubeConnectionWizardPage extends WizardPage {

	private Text nameText;
	private Text userNameText;
	private Text apiUrlText;
	private RemoteRepository repository;
	private IRepository repositoryDefinition;
	private boolean manualName;

	/**
	 * Create the wizard
	 */
	public YoutubeConnectionWizardPage() {
		super("wizardPage"); //$NON-NLS-1$
		setTitle(Messages.YoutubeConnectionWizardPage_WizardTitle);
		setDescription(Messages.YoutubeConnectionWizardPage_WizardSubTitle);
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
		nameLabel.setText(Messages.YoutubeConnectionWizardPage_Name);

		this.nameText = new Text(group, SWT.BORDER);
		this.nameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		this.nameText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(final KeyEvent e) {
				YoutubeConnectionWizardPage.this.manualName = true;

			}
		});

		final Label apiurlLabel = new Label(group, SWT.NONE);
		apiurlLabel.setText(Messages.YoutubeConnectionWizardPage_APIUrl);

		this.apiUrlText = new Text(group, SWT.BORDER);
		final GridData gd_apiUrlText = new GridData(SWT.FILL, SWT.CENTER, true, false);
		this.apiUrlText.setLayoutData(gd_apiUrlText);
		this.apiUrlText.setEditable(false);

		final Group credentialsGroup = new Group(container, SWT.NONE);
		credentialsGroup.setText(Messages.YoutubeConnectionWizardPage_UserProfile);
		final GridData gd_credentialsGroup = new GridData(SWT.FILL, SWT.CENTER, false, false);
		credentialsGroup.setLayoutData(gd_credentialsGroup);
		final GridLayout gridLayout_1 = new GridLayout();
		gridLayout_1.numColumns = 2;
		credentialsGroup.setLayout(gridLayout_1);

		final Label usernameLabel = new Label(credentialsGroup, SWT.NONE);
		usernameLabel.setText(Messages.YoutubeConnectionWizardPage_Username);

		this.userNameText = new Text(credentialsGroup, SWT.BORDER);
		final GridData gd_userNameText = new GridData(SWT.FILL, SWT.CENTER, true, false);
		this.userNameText.setLayoutData(gd_userNameText);

		final Button validateCredentialsButton = new Button(credentialsGroup, SWT.NONE);
		validateCredentialsButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				try {
					getContainer().run(true, false, new IRunnableWithProgress() {
						public void run(final IProgressMonitor monitor)
								throws InvocationTargetException, InterruptedException {
							IStatus validate = YoutubeConnectionWizardPage.this.repositoryDefinition
									.validate();
							if (!validate.isOK()) {
								throw new InvocationTargetException(validate.getException());
							}
							setErrorMessage(null);
						}
					});
				} catch (InvocationTargetException e) {
					setErrorMessage(StringUtils.join(Messages.YoutubeConnectionWizardPage_ErrorValidating, e.getCause()
							.getMessage(), ")")); //$NON-NLS-1$
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		final GridData gd_validateCredentialsButton = new GridData(SWT.RIGHT, SWT.CENTER, false,
				false);
		validateCredentialsButton.setLayoutData(gd_validateCredentialsButton);
		validateCredentialsButton.setText(Messages.YoutubeConnectionWizardPage_CheckUserName);
		bindValuesToUi();
		setControl(container);

		this.nameText.setText(String.format("%s@%s", this.repositoryDefinition //$NON-NLS-1$
				.getCredentialProvider().getUserName(), "youtube-readonly")); //$NON-NLS-1$
		this.userNameText.setFocus();
	}

	public void setRemoteObject(final RemoteRepository repository) {
		this.repository = repository;
		this.repositoryDefinition = SyncUtil
				.getRepositoryImplemenationByRemoteRepository(repository);
	}

	public void bindValuesToUi() {
		this.apiUrlText.setText(YoutubeActivator.getDefault().getPreferenceStore().getString(
				PreferenceInitializer.GDATA_SERVER_URL));
		DataBindingContext ctx = new DataBindingContext();
		EMFDataBindingContext ectx = new EMFDataBindingContext();
		this.repositoryDefinition.getCredentialProvider().setIdentifier(this.repository.getId());
		IObservableValue beanUserName = BeansObservables.observeValue(this.repositoryDefinition
				.getCredentialProvider(), CredentialProvider.USER_NAME);
		ISWTObservableValue swtUserName = SWTObservables.observeText(this.userNameText, SWT.Modify);
		ctx.bindValue(swtUserName, beanUserName, null, null);

		ISWTObservableValue swtName = SWTObservables.observeText(this.nameText, SWT.Modify);
		IObservableValue emfName = EMFObservables.observeValue(this.repository,
				InfomngmntPackage.Literals.REMOTE_OBJECT__NAME);
		ectx.bindValue(swtName, emfName, null, null);
		swtName.addValueChangeListener(new IValueChangeListener() {
			public void handleValueChange(final ValueChangeEvent event) {
				String string = event.getObservableValue().getValue().toString();
				YoutubeConnectionWizardPage.this.repository
						.setUrl(YoutubeConnectionWizardPage.this.repositoryDefinition
								.getRepositoryUrl());
			}
		});

		swtUserName.addValueChangeListener(new IValueChangeListener() {
			public void handleValueChange(final ValueChangeEvent event) {
				if (!YoutubeConnectionWizardPage.this.manualName) {
					String userName = (String) event.getObservableValue().getValue();
					YoutubeConnectionWizardPage.this.nameText.setText(String.format("%s@%s", //$NON-NLS-1$
							userName, "youtube-readonly")); //$NON-NLS-1$
				}
			}
		});
	}

}
