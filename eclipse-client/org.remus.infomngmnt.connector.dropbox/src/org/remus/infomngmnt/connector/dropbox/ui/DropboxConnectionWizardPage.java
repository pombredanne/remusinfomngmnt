package org.remus.infomngmnt.connector.dropbox.ui;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.databinding.EMFDataBindingContext;
import org.eclipse.emf.databinding.EMFObservables;
import org.eclipse.jface.databinding.swt.ISWTObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.remus.InfomngmntPackage;
import org.eclipse.remus.RemoteRepository;
import org.eclipse.remus.core.remote.sync.SyncUtil;
import org.eclipse.remus.model.remote.IRepository;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.remus.infomngmnt.connector.dropbox.Messages;

public class DropboxConnectionWizardPage extends WizardPage {

	private final String connectionUrl;
	private Text text;
	private RemoteRepository repository;
	private IRepository repositoryDefinition;

	/**
	 * Create the wizard.
	 */
	public DropboxConnectionWizardPage(String connectionUrl) {
		super("dropboxconnection"); //$NON-NLS-1$
		this.connectionUrl = connectionUrl;
		setTitle(Messages.DropboxConnectionWizardPage_WizardTitle);
		setDescription(Messages.DropboxConnectionWizardPage_WizardMessage);
	}

	public void setRemoteObject(final RemoteRepository repository) {
		this.repository = repository;
		repositoryDefinition = SyncUtil
				.getRepositoryImplemenationByRemoteRepository(repository);
	}

	/**
	 * Create contents of the wizard.
	 * 
	 * @param parent
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		container.setLayout(new GridLayout(2, false));

		Label lblNewLabel = new Label(container, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblNewLabel.setText(Messages.DropboxConnectionWizardPage_RepoName);

		text = new Text(container, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(container, SWT.NONE);

		final Browser browser = new Browser(container, SWT.BORDER);
		GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, true);

		layoutData.minimumWidth = 900;
		layoutData.minimumHeight = 600;
		layoutData.horizontalSpan = 2;
		browser.setLayoutData(layoutData);
		browser.setUrl(connectionUrl);

		bindValuesToUi();
		setControl(container);

	}

	public void bindValuesToUi() {

		EMFDataBindingContext ctx = new EMFDataBindingContext();

		repositoryDefinition.getCredentialProvider().setIdentifier(
				repository.getId());

		ISWTObservableValue swtName = SWTObservables.observeText(text,
				SWT.Modify);
		IObservableValue emfName = EMFObservables.observeValue(repository,
				InfomngmntPackage.Literals.REMOTE_OBJECT__NAME);
		ctx.bindValue(swtName, emfName, null, null);
	}

}
