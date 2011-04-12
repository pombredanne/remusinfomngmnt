package org.remus.infomngmnt.connector.twitter.ui;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.StatusDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;
import org.remus.infomngmnt.connector.twitter.Messages;

public class TwitPicPasswordPrompt extends StatusDialog {

	private Text text;

	private String pwd;

	private Text text1;

	protected String username;

	private boolean save;

	/**
	 * Create the dialog
	 * 
	 * @param parentShell
	 */
	public TwitPicPasswordPrompt(final Shell parentShell, final String userName, final String pwd) {
		super(parentShell);
		this.username = userName;
		this.pwd = pwd;
		this.save = userName != null && userName.trim().length() > 0 && pwd != null
				&& pwd.trim().length() > 0;
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

		pleaseEnterALabel
				.setText(Messages.TwitPicPasswordPrompt_Warning);

		final Label label_1 = new Label(container, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_1.setLayoutData(new TableWrapData(TableWrapData.FILL, TableWrapData.TOP));

		final Label enterYourUsernameLabel = new Label(container, SWT.NONE);
		enterYourUsernameLabel.setText(Messages.TwitPicPasswordPrompt_EnterUsername);
		this.text1 = new Text(container, SWT.BORDER);
		TableWrapData twd_text = new TableWrapData(TableWrapData.LEFT, TableWrapData.TOP);
		twd_text.align = TableWrapData.FILL;
		this.text1.setLayoutData(twd_text);
		this.text1.setText(this.username);
		this.text1.addListener(SWT.Modify, new Listener() {
			public void handleEvent(final Event event) {
				TwitPicPasswordPrompt.this.username = ((Text) event.widget).getText();
			}
		});

		final Label enterYourPasswordLabel = new Label(container, SWT.NONE);
		enterYourPasswordLabel.setText(Messages.TwitPicPasswordPrompt_EnterPassword);

		this.text = new Text(container, SWT.BORDER | SWT.PASSWORD);
		twd_text = new TableWrapData(TableWrapData.LEFT, TableWrapData.TOP);
		twd_text.align = TableWrapData.FILL;
		this.text.setLayoutData(twd_text);
		this.text.setText(this.pwd);

		final Button btnSaveUsernamepassword = new Button(container, SWT.CHECK);
		btnSaveUsernamepassword.setSelection(this.save);
		btnSaveUsernamepassword.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				TwitPicPasswordPrompt.this.save = btnSaveUsernamepassword.getSelection();

			}
		});
		btnSaveUsernamepassword.setText(Messages.TwitPicPasswordPrompt_SaveUsernameAndPassword);
		this.text.addListener(SWT.Modify, new Listener() {
			public void handleEvent(final Event event) {
				TwitPicPasswordPrompt.this.pwd = ((Text) event.widget).getText();
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
	}

	/**
	 * Return the initial size of the dialog
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(300, 315);
	}

	@Override
	protected void configureShell(final Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(Messages.TwitPicPasswordPrompt_DialogTitle);
	}

	/**
	 * @return the pwd
	 */
	public String getPwd() {
		return this.pwd;
	}

	public String getUsername() {
		return this.username;
	}

	public boolean isSave() {
		return this.save;
	}

}
