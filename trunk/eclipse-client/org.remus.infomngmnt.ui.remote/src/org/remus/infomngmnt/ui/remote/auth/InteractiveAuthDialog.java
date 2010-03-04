package org.remus.infomngmnt.ui.remote.auth;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class InteractiveAuthDialog extends TitleAreaDialog {

	private Text text;

	/**
	 * Create the dialog
	 * 
	 * @param parentShell
	 */
	public InteractiveAuthDialog(final Shell parentShell) {
		super(parentShell);
	}

	/**
	 * Create contents of the dialog
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(final Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 6;
		container.setLayout(gridLayout);
		container.setLayoutData(new GridData(GridData.FILL_BOTH));

		final CTabFolder tabFolder = new CTabFolder(container, SWT.NONE);
		tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 6, 1));

		final Label copyFromLocalLabel = new Label(container, SWT.NONE);
		copyFromLocalLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 6, 1));
		copyFromLocalLabel.setText("Copy from local password-unit");

		this.text = new Text(container, SWT.BORDER);
		this.text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));

		final Button browseButton = new Button(container, SWT.NONE);
		browseButton.setText("Browse...");

		final Button copyUsernameButton = new Button(container, SWT.NONE);
		copyUsernameButton.setText("Copy Username");

		final Button copyPasswordButton = new Button(container, SWT.NONE);
		copyPasswordButton.setText("Copy Password");

		final Label statusLabel = new Label(container, SWT.NONE);
		statusLabel.setText("Status");

		final Label label = new Label(container, SWT.NONE);
		label.setText("Label");

		final Button checkStatusButton = new Button(container, SWT.NONE);
		checkStatusButton.setText("Check Status");
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		//
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

	/**
	 * Return the initial size of the dialog
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(500, 375);
	}

	@Override
	protected void configureShell(final Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Authentication");
	}

}
