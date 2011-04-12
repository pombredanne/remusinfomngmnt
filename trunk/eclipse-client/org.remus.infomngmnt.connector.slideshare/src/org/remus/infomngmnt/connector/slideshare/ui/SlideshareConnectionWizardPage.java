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
package org.remus.infomngmnt.connector.slideshare.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.IValueChangeListener;
import org.eclipse.core.databinding.observable.value.ValueChangeEvent;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.databinding.EMFDataBindingContext;
import org.eclipse.emf.databinding.EMFObservables;
import org.eclipse.jface.databinding.swt.ISWTObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.remus.InfomngmntPackage;
import org.eclipse.remus.RemoteRepository;
import org.eclipse.remus.core.remote.security.CredentialProvider;
import org.eclipse.remus.core.remote.services.IRepositoryExtensionService;
import org.eclipse.remus.model.remote.IRepository;
import org.eclipse.remus.ui.remote.RemoteUiActivator;
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
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.remus.infomngmnt.connector.slideshare.Messages;
import org.remus.infomngmnt.connector.slideshare.SlideshareActivator;

public class SlideshareConnectionWizardPage extends WizardPage {

	private TableViewer tableViewer;
	private Text nameText;
	private Text passwordText;
	private Text userNameText;
	private RemoteRepository repository;
	private IRepository repositoryDefinition;
	private boolean manualName;
	private WritableList searchList;
	private Button removeButton;

	/**
	 * Create the wizard
	 */
	public SlideshareConnectionWizardPage() {
		super("wizardPage"); //$NON-NLS-1$
		setTitle(Messages.SlideshareConnectionWizardPage_WizardTitle);
		setDescription(Messages.SlideshareConnectionWizardPage_WizardSubtitle);
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
		nameLabel.setText(Messages.SlideshareConnectionWizardPage_Name);

		this.nameText = new Text(group, SWT.BORDER);
		this.nameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false));
		this.nameText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(final KeyEvent e) {
				SlideshareConnectionWizardPage.this.manualName = true;

			}
		});

		final Group credentialsGroup = new Group(container, SWT.NONE);
		credentialsGroup
				.setText(Messages.SlideshareConnectionWizardPage_Credentials);
		final GridData gd_credentialsGroup = new GridData(SWT.FILL, SWT.CENTER,
				false, false);
		credentialsGroup.setLayoutData(gd_credentialsGroup);
		final GridLayout gridLayout_1 = new GridLayout();
		gridLayout_1.numColumns = 2;
		credentialsGroup.setLayout(gridLayout_1);

		final Label usernameLabel = new Label(credentialsGroup, SWT.NONE);
		usernameLabel.setText(Messages.SlideshareConnectionWizardPage_Username);

		this.userNameText = new Text(credentialsGroup, SWT.BORDER);
		final GridData gd_userNameText = new GridData(SWT.FILL, SWT.CENTER,
				true, false);
		this.userNameText.setLayoutData(gd_userNameText);

		final Label passwordLabel = new Label(credentialsGroup, SWT.NONE);
		passwordLabel.setText(Messages.SlideshareConnectionWizardPage_Password);

		this.passwordText = new Text(credentialsGroup, SWT.BORDER
				| SWT.PASSWORD);
		final GridData gd_passwordText = new GridData(SWT.FILL, SWT.CENTER,
				true, false);
		this.passwordText.setLayoutData(gd_passwordText);
		new Label(credentialsGroup, SWT.NONE);

		final Button validateCredentialsButton = new Button(credentialsGroup,
				SWT.NONE);
		validateCredentialsButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				// getContainer().run(
				// true,
				// true,
				// new
				// ValidateConnectionJob(TwitterConnectionWizardPage.this.repositoryDefinition));
				setErrorMessage(null);

			}
		});
		final GridData gd_validateCredentialsButton = new GridData(SWT.RIGHT,
				SWT.CENTER, false, false);
		validateCredentialsButton.setLayoutData(gd_validateCredentialsButton);
		validateCredentialsButton
				.setText(Messages.SlideshareConnectionWizardPage_ValidateCredentials);

		this.nameText.setText(String.format("%s@%s", this.repositoryDefinition //$NON-NLS-1$
				.getCredentialProvider().getUserName(), "slideshare")); //$NON-NLS-1$

		final Group group_1 = new Group(container, SWT.NONE);
		group_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		final GridLayout gridLayout_2 = new GridLayout();
		gridLayout_2.numColumns = 2;
		group_1.setLayout(gridLayout_2);
		group_1.setText(Messages.SlideshareConnectionWizardPage_AdditionalSearchFeeds);

		this.tableViewer = new TableViewer(group_1, SWT.BORDER);
		Table table = this.tableViewer.getTable();
		final GridData gd_table = new GridData(SWT.FILL, SWT.FILL, true, true,
				1, 2);
		gd_table.heightHint = 70;
		table.setLayoutData(gd_table);

		final Button addButton = new Button(group_1, SWT.NONE);
		addButton
				.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		addButton.setText(Messages.SlideshareConnectionWizardPage_Add);
		addButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				InputDialog inputDialog = new InputDialog(
						getShell(),
						Messages.SlideshareConnectionWizardPage_AddSearchQuery,
						Messages.SlideshareConnectionWizardPage_EnterSearchQuery,
						"", new IInputValidator() { //$NON-NLS-1$
							public String isValid(final String newText) {
								if (SlideshareConnectionWizardPage.this.searchList
										.contains(newText)) {
									return Messages.SlideshareConnectionWizardPage_SearchWordAlreadyInList;
								}
								if (newText.trim().length() == 0) {
									return Messages.SlideshareConnectionWizardPage_InputRequired;
								}
								return null;
							}

						});
				if (inputDialog.open() == IDialogConstants.OK_ID) {
					SlideshareConnectionWizardPage.this.searchList
							.add(inputDialog.getValue());
				}
			}
		});

		this.removeButton = new Button(group_1, SWT.NONE);
		this.removeButton.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false,
				false));
		this.removeButton
				.setText(Messages.SlideshareConnectionWizardPage_Remove);
		this.removeButton.setEnabled(false);
		this.removeButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				List list = ((IStructuredSelection) SlideshareConnectionWizardPage.this.tableViewer
						.getSelection()).toList();
				for (Object object : list) {
					SlideshareConnectionWizardPage.this.searchList
							.remove(object);
				}
			}
		});
		bindValuesToUi();
		setControl(container);
	}

	public void setRemoteObject(final RemoteRepository repository) {
		this.repository = repository;
		IRepositoryExtensionService extensionService = RemoteUiActivator
				.getDefault().getServiceTracker()
				.getService(IRepositoryExtensionService.class);
		try {
			this.repositoryDefinition = extensionService
					.getItemByRepository(repository);
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void bindValuesToUi() {
		DataBindingContext ctx = new DataBindingContext();
		EMFDataBindingContext ectx = new EMFDataBindingContext();

		this.searchList = new WritableList(
				new ArrayList<String>(
						Arrays.asList(org.apache.commons.lang.StringUtils
								.split((this.repository)
										.getOptions()
										.get(SlideshareActivator.REPOSITORY_OPTIONS_SEARCH_KEY),
										"|"))), //$NON-NLS-1$
				String.class);
		this.tableViewer
				.setContentProvider(new ObservableListContentProvider());
		this.tableViewer.setLabelProvider(new LabelProvider());
		this.tableViewer.setInput(this.searchList);
		this.tableViewer
				.addSelectionChangedListener(new ISelectionChangedListener() {
					public void selectionChanged(
							final SelectionChangedEvent event) {
						SlideshareConnectionWizardPage.this.removeButton
								.setEnabled(!event.getSelection().isEmpty());
					}
				});

		this.repositoryDefinition.getCredentialProvider().setIdentifier(
				this.repository.getId());
		IObservableValue beanUserName = BeansObservables.observeValue(
				this.repositoryDefinition.getCredentialProvider(),
				CredentialProvider.USER_NAME);
		ISWTObservableValue swtUserName = SWTObservables.observeText(
				this.userNameText, SWT.Modify);
		ctx.bindValue(swtUserName, beanUserName, null, null);
		ISWTObservableValue swtPassword = SWTObservables.observeText(
				this.passwordText, SWT.Modify);
		IObservableValue beanPassword = BeansObservables.observeValue(
				this.repositoryDefinition.getCredentialProvider(),
				CredentialProvider.PASSWORD);
		ctx.bindValue(swtPassword, beanPassword, null, null);

		ISWTObservableValue swtName = SWTObservables.observeText(this.nameText,
				SWT.Modify);
		IObservableValue emfName = EMFObservables.observeValue(this.repository,
				InfomngmntPackage.Literals.REMOTE_OBJECT__NAME);
		ectx.bindValue(swtName, emfName, null, null);

		swtUserName.addValueChangeListener(new IValueChangeListener() {
			public void handleValueChange(final ValueChangeEvent event) {
				if (!SlideshareConnectionWizardPage.this.manualName) {
					String userName = (String) event.getObservableValue()
							.getValue();
					SlideshareConnectionWizardPage.this.nameText.setText(String
							.format("%s@%s", //$NON-NLS-1$
									userName, "slideshare")); //$NON-NLS-1$
				}
			}
		});
	}

	public List getSearchList() {
		return this.searchList;
	}

	/**
	 * @return the repositoryDefinition
	 */
	public final IRepository getRepositoryDefinition() {
		return this.repositoryDefinition;
	}
}
