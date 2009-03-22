package org.remus.infomngmnt.efs.password.ui;

import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.StatusDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

import org.remus.infomngmnt.core.model.StatusCreator;

public class PasswordPrompt extends StatusDialog {

	private Text text;

	private String pwd;

	private final boolean creation;

	/**
	 * Create the dialog
	 * 
	 * @param parentShell
	 */
	public PasswordPrompt(final Shell parentShell, final boolean creation) {
		super(parentShell);
		this.creation = creation;
	}

	/**
	 * Create contents of the dialog
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(final Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(new TableWrapLayout());

		final Label pleaseEnterALabel = new Label(container, SWT.WRAP);
		if (this.creation) {
			pleaseEnterALabel
					.setText("Please enter a password which is used for en- and decryption of project contents. Pleas keep this password in mind, it won't be saved on disc.");
		} else {
			pleaseEnterALabel
					.setText("Please enter the password you have chosen on creating the projects. This is required to access the content of the project.");
		}

		final Label label_1 = new Label(container, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_1.setLayoutData(new TableWrapData(TableWrapData.FILL, TableWrapData.TOP));

		final Label enterYourPasswordLabel = new Label(container, SWT.NONE);
		enterYourPasswordLabel.setText("Enter your password.");

		this.text = new Text(container, SWT.BORDER | SWT.PASSWORD);
		final TableWrapData twd_text = new TableWrapData(TableWrapData.LEFT, TableWrapData.TOP);
		twd_text.align = TableWrapData.FILL;
		this.text.setLayoutData(twd_text);
		this.text.addListener(SWT.Modify, new Listener() {
			public void handleEvent(final Event event) {
				if (((Text) event.widget).getText().trim().length() == 0) {
					updateStatus(StatusCreator.newStatus("No empty password allowed"));
				} else {
					updateStatus(Status.OK_STATUS);
				}
				PasswordPrompt.this.pwd = ((Text) event.widget).getText();
			}
		});

		return container;
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
		updateStatus(StatusCreator.newStatus("Nothing selected"));
	}

	/**
	 * Return the initial size of the dialog
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(300, 205);
	}

	@Override
	protected void configureShell(final Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Enter password");
	}

	/**
	 * @return the pwd
	 */
	public String getPwd() {
		return this.pwd;
	}

}
