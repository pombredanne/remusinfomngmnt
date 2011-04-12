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
package org.remus.infomngmnt.connector.twitter.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
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
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.remus.infomngmnt.connector.twitter.Messages;
import org.remus.infomngmnt.connector.twitter.TwitterActivator;
import org.remus.infomngmnt.connector.twitter.TwitterCredentials;
import org.remus.infomngmnt.connector.twitter.TwitterRepository;

import twitter4j.TwitterException;

public class TwitterConnectionWizardPage extends WizardPage {

	private TableViewer tableViewer;
	private Text nameText;
	private RemoteRepository repository;
	private IRepository repositoryDefinition;
	private boolean manualName;
	private WritableList searchList;
	private Button removeButton;
	private Button btnGrantAccessOn;

	/**
	 * Create the wizard
	 */
	public TwitterConnectionWizardPage() {
		super("wizardPage"); //$NON-NLS-1$
		setTitle(Messages.TwitterConnectionWizardPage_Connector);
		setDescription(Messages.TwitterConnectionWizardPage_EnterCredentials);
		manualName = false;
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
		nameLabel.setText(Messages.TwitterConnectionWizardPage_Name);

		nameText = new Text(group, SWT.BORDER);
		nameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		nameText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(final KeyEvent e) {
				manualName = true;

			}
		});

		nameText.setText(String.format("%s@%s", //$NON-NLS-1$
				((TwitterCredentials) repositoryDefinition
						.getCredentialProvider()).getInternalId(), "twitter")); //$NON-NLS-1$
		new Label(group, SWT.NONE);

		btnGrantAccessOn = new Button(group, SWT.NONE);
		btnGrantAccessOn.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER,
				false, false, 1, 1));
		btnGrantAccessOn
				.setText(Messages.TwitterConnectionWizardPage_GrantAccess);
		btnGrantAccessOn.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				OAuthDialog oAuthDialog = new OAuthDialog(getShell());
				if (oAuthDialog.open() == IDialogConstants.OK_ID) {
					try {
						repositoryDefinition.getCredentialProvider()
								.setUserName(oAuthDialog.getToken());
						repositoryDefinition.getCredentialProvider()
								.setPassword(oAuthDialog.getTokenSecret());
						String userId = StringUtils
								.trim(((TwitterRepository) repositoryDefinition)
										.getApi().verifyCredentials().getName());
						((TwitterCredentials) repositoryDefinition
								.getCredentialProvider()).setInternalId(userId);
						if (!manualName) {
							nameText.setText(String.format(
									"%s@%s", userId, "twitter")); //$NON-NLS-1$ //$NON-NLS-2$
						}
					} catch (TwitterException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});

		final Group group_1 = new Group(container, SWT.NONE);
		group_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		final GridLayout gridLayout_2 = new GridLayout();
		gridLayout_2.numColumns = 2;
		group_1.setLayout(gridLayout_2);
		group_1.setText(Messages.TwitterConnectionWizardPage_AdditionalSearch);

		tableViewer = new TableViewer(group_1, SWT.BORDER);
		Table table = tableViewer.getTable();
		final GridData gd_table = new GridData(SWT.FILL, SWT.FILL, true, true,
				1, 2);
		gd_table.heightHint = 70;
		table.setLayoutData(gd_table);

		final Button addButton = new Button(group_1, SWT.NONE);
		addButton
				.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		addButton.setText(Messages.TwitterConnectionWizardPage_Add);
		addButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				InputDialog inputDialog = new InputDialog(getShell(),
						Messages.TwitterConnectionWizardPage_AddSearchQuery,
						Messages.TwitterConnectionWizardPage_EnterSearchQuery,
						"", new IInputValidator() { //$NON-NLS-1$
							public String isValid(final String newText) {
								if (searchList.contains(newText)) {
									return Messages.TwitterConnectionWizardPage_SearchWordAlreadyInList;
								}
								if (newText.trim().length() == 0) {
									return Messages.TwitterConnectionWizardPage_InputRequired;
								}
								return null;
							}

						});
				if (inputDialog.open() == IDialogConstants.OK_ID) {
					searchList.add(inputDialog.getValue());
				}
			}
		});

		removeButton = new Button(group_1, SWT.NONE);
		removeButton
				.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false));
		removeButton.setText(Messages.TwitterConnectionWizardPage_Remove);
		removeButton.setEnabled(false);
		removeButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				List list = ((IStructuredSelection) tableViewer.getSelection())
						.toList();
				for (Object object : list) {
					searchList.remove(object);
				}
			}
		});
		bindValuesToUi();
		setControl(container);
	}

	public void setRemoteObject(final RemoteRepository repository) {
		this.repository = repository;
		repositoryDefinition = SyncUtil
				.getRepositoryImplemenationByRemoteRepository(repository);
	}

	public void bindValuesToUi() {

		EMFDataBindingContext ctx = new EMFDataBindingContext();
		searchList = new WritableList(
				new ArrayList<String>(
						Arrays.asList(org.apache.commons.lang.StringUtils
								.split((repository)
										.getOptions()
										.get(TwitterActivator.REPOSITORY_OPTIONS_SEARCH_KEY),
										"|"))), String.class); //$NON-NLS-1$
		tableViewer.setContentProvider(new ObservableListContentProvider());
		tableViewer.setLabelProvider(new LabelProvider());
		tableViewer.setInput(searchList);
		tableViewer
				.addSelectionChangedListener(new ISelectionChangedListener() {
					public void selectionChanged(
							final SelectionChangedEvent event) {
						removeButton
								.setEnabled(!event.getSelection().isEmpty());
					}
				});

		repositoryDefinition.getCredentialProvider().setIdentifier(
				repository.getId());

		ISWTObservableValue swtName = SWTObservables.observeText(nameText,
				SWT.Modify);
		IObservableValue emfName = EMFObservables.observeValue(repository,
				InfomngmntPackage.Literals.REMOTE_OBJECT__NAME);
		ctx.bindValue(swtName, emfName, null, null);
	}

	public List getSearchList() {
		return searchList;
	}
}
