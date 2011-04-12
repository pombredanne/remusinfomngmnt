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
package org.remus.infomngmnt.connector.folder.ui;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.IValueChangeListener;
import org.eclipse.core.databinding.observable.value.ValueChangeEvent;
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
import org.eclipse.remus.core.remote.services.IRepositoryExtensionService;
import org.eclipse.remus.model.remote.IRepository;
import org.eclipse.remus.ui.remote.RemoteUiActivator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.remus.infomngmnt.connector.folder.FolderConnector;
import org.remus.infomngmnt.connector.folder.Messages;

public class FolderConnectionWizardPage extends WizardPage {

	private RemoteRepository repository;
	private IRepository repositoryDefinition;
	private Text nameText;
	private Text folderUrl;

	/**
	 * Create the wizard
	 */
	public FolderConnectionWizardPage() {
		super("folderWizardPage"); //$NON-NLS-1$
		setTitle(Messages.FolderConnectionWizardPage_ConnectorName);
		setDescription(Messages.FolderConnectionWizardPage_WizardSubtitle);

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
		gridLayout.numColumns = 3;
		group.setLayout(gridLayout);

		final Label nameLabel = new Label(group, SWT.NONE);
		nameLabel.setText(Messages.FolderConnectionWizardPage_Name);

		nameText = new Text(group, SWT.BORDER);
		nameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				2, 1));

		final Label apiurlLabel = new Label(group, SWT.NONE);
		apiurlLabel.setText(Messages.FolderConnectionWizardPage_Path);

		folderUrl = new Text(group, SWT.BORDER);
		folderUrl
				.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		Button btnBrowse = new Button(group, SWT.NONE);

		GridData gridData = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1,
				1);
		// gridData.widthHint = 80;
		btnBrowse.setLayoutData(gridData);

		btnBrowse.setText(Messages.FolderConnectionWizardPage_Browse);
		btnBrowse.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				DirectoryDialog dd = new DirectoryDialog(getShell());
				dd.setMessage(Messages.FolderConnectionWizardPage_SelectSharedFolder);
				String open = dd.open();
				if (open != null) {
					repository.setUrl(open);
				}
			}
		});

		bindValuesToUi();
		setPageComplete(false);
		setControl(container);

	}

	public void bindValuesToUi() {
		DataBindingContext ctx = new DataBindingContext();
		ISWTObservableValue observeText = SWTObservables.observeText(nameText,
				SWT.Modify);
		IObservableValue observeValue = EMFObservables.observeValue(repository,
				InfomngmntPackage.Literals.REMOTE_OBJECT__NAME);
		ctx.bindValue(observeText, observeValue);
		ISWTObservableValue observeText2 = SWTObservables.observeDelayedValue(
				500, SWTObservables.observeText(folderUrl, SWT.Modify));
		observeText2.addValueChangeListener(new IValueChangeListener() {

			public void handleValueChange(final ValueChangeEvent event) {
				final String newString = (String) event.getObservableValue()
						.getValue();
				try {
					getContainer().run(true, false,
							new IRunnableWithProgress() {
								public void run(final IProgressMonitor monitor)
										throws InvocationTargetException,
										InterruptedException {
									IStatus validate = ((FolderConnector) repositoryDefinition)
											.validate(newString);
									if (!validate.isOK()) {
										throw new InvocationTargetException(
												validate.getException());
									}

								}
							});
					setErrorMessage(null);
					setPageComplete(true);
				} catch (InvocationTargetException e) {
					setErrorMessage(Messages.FolderConnectionWizardPage_NoValidFolder);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		IObservableValue observeValue2 = EMFObservables.observeValue(
				repository, InfomngmntPackage.Literals.REMOTE_OBJECT__URL);
		ctx.bindValue(observeText2, observeValue2);

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

}
