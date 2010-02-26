package org.remus.infomngmnt.efs.ui;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;

import org.remus.infomngmnt.common.ui.image.ResourceManager;
import org.remus.infomngmnt.efs.EFSActivator;
import org.remus.infomngmnt.efs.internal.model.SecurityAffectedProject;
import org.remus.infomngmnt.efs.internal.model.SecurityWrapper;

public class InitializeSecurityProviderDialog extends TitleAreaDialog {

	private Table table_1;
	private Table table;
	private final List<SecurityWrapper> model;

	/**
	 * Create the dialog
	 * 
	 * @param parentShell
	 */
	public InitializeSecurityProviderDialog(final Shell parentShell,
			final List<SecurityWrapper> model) {
		super(parentShell);
		this.model = model;
	}

	@Override
	protected void configureShell(final Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Initialization of encrypted projects");
	}

	/**
	 * Create contents of the dialog
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(final Composite parent) {

		setTitleImage(ResourceManager.getPluginImage(EFSActivator.getDefault(),
				"icons/iconexperience/wizards/unlock_project.png"));
		setTitle("Initialization of encrypted projects");
		setMessage("Please enter the required credentials to encrypt the projects.");
		Composite area = (Composite) super.createDialogArea(parent);
		final Composite container = new Composite(area, SWT.NONE);
		container.setLayout(new GridLayout());
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		final Label remusHasFoundLabel = new Label(container, SWT.WRAP);
		remusHasFoundLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.FILL, false, false));
		remusHasFoundLabel
				.setText("RIM has found projects which are encrypted and whose security provider needs input. Before accessing to the content of this projects you have to initialize the appropiated security addins which can decrypt the content.");
		getShell().addListener(SWT.RESIZE, new Listener() {
			public void handleEvent(final Event event) {
				GridData layoutData = (GridData) remusHasFoundLabel.getLayoutData();
				layoutData.widthHint = getShell().getBounds().width - 20;
				container.layout();
			}
		});

		EncryptedProjectViewer viewer = new EncryptedProjectViewer(container, this.model);
		viewer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		final Label label = new Label(area, SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		return area;
	}

	/**
	 * Create contents of the button bar
	 * 
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(final Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}

	@Override
	protected void okPressed() {
		ProgressMonitorDialog progressMonitorDialog = new ProgressMonitorDialog(getShell());
		try {
			progressMonitorDialog.run(true, false, new IRunnableWithProgress() {
				public void run(final IProgressMonitor monitor) throws InvocationTargetException,
						InterruptedException {
					for (SecurityWrapper provider : InitializeSecurityProviderDialog.this.model) {
						if (provider.getProvider().isInitialized()) {
							List<SecurityAffectedProject> affectedProject = provider
									.getAffectedProject();
							for (SecurityAffectedProject securityProvderAffectedProject : affectedProject) {
								IProject project = securityProvderAffectedProject.getProject();
								if (!project.isOpen()) {
									try {
										project.open(monitor);
										project
												.build(IncrementalProjectBuilder.FULL_BUILD,
														monitor);
									} catch (CoreException e) {
										// hm, ok. We've done our best.
									}
								}
							}
						}
					}
				}
			});
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		super.okPressed();
	}

	/**
	 * Return the initial size of the dialog
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(500, 500);
	}

}
