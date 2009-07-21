package org.remus.infomngmnt.mail.ui;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.StatusDialog;
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
import org.eclipse.swt.widgets.Text;

import org.remus.infomngmnt.util.StatusCreator;

public class MailPersonDialog extends StatusDialog {
	private Text text;

	String content;

	public final String getContent() {
		return this.content;
	}

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */
	public MailPersonDialog(final Shell parentShell, final String initial) {
		super(parentShell);
		setShellStyle(getShellStyle() | SWT.RESIZE);
		this.content = initial;
	}

	@Override
	protected void configureShell(final Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Add/Edit Person");
	}

	/**
	 * Create contents of the dialog.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(final Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(new GridLayout(2, false));

		Label label = new Label(container, SWT.NONE);
		label.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, true, 1, 1));
		label.setText("Enter name:");

		this.text = new Text(container, SWT.BORDER);
		if (this.content != null) {
			this.text.setText(this.content);
		}
		this.text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));
		this.text.addListener(SWT.Modify, new Listener() {
			public void handleEvent(final Event event) {
				MailPersonDialog.this.content = MailPersonDialog.this.text.getText();
				updateStatus(false);
			}
		});
		new ContactsWithEmailSmartField(this.text);

		return container;
	}

	protected void updateStatus(final boolean initial) {
		IStatus status2set = Status.OK_STATUS;
		if (this.text.getText().trim().length() == 0) {
			status2set = StatusCreator.newStatus("Input is mandatory");
		}
		if (initial) {
			updateButtonsEnableState(status2set);
		} else {
			updateStatus(status2set);
		}

	}

	/**
	 * Create contents of the button bar.
	 * 
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(final Composite parent) {
		super.createButtonsForButtonBar(parent);
		updateStatus(false);
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(450, 200);
	}

}
