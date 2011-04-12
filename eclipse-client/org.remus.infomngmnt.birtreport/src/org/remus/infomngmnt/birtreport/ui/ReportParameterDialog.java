package org.remus.infomngmnt.birtreport.ui;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.StatusDialog;
import org.eclipse.remus.util.StatusCreator;
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
import org.remus.infomngmnt.birtreport.messages.Messages;


public class ReportParameterDialog extends StatusDialog {
	private Text text;

	String name;

	String value;

	private Text text2;

	public final String getName() {
		return this.name;
	}

	public String getValue() {
		return this.value;
	}

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */
	public ReportParameterDialog(final Shell parentShell, final String initialName,
			final String initialValue) {
		super(parentShell);
		this.value = initialValue;
		setShellStyle(getShellStyle() | SWT.RESIZE);
		this.name = initialName;
	}

	@Override
	protected void configureShell(final Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(Messages.ReportParameterDialog_AddEditParameter);
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
		label.setText(Messages.ReportParameterDialog_ParameterName);

		this.text = new Text(container, SWT.BORDER);
		if (this.name != null) {
			this.text.setText(this.name);
		}
		this.text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));
		this.text.addListener(SWT.Modify, new Listener() {
			public void handleEvent(final Event event) {
				ReportParameterDialog.this.name = ReportParameterDialog.this.text.getText();
				updateStatus(false);
			}
		});

		Label label2 = new Label(container, SWT.NONE);
		label2.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, true, 1, 1));
		label2.setText(Messages.ReportParameterDialog_ParameterValue);

		this.text2 = new Text(container, SWT.BORDER);
		if (this.value != null) {
			this.text2.setText(this.value);
		}
		this.text2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));
		this.text2.addListener(SWT.Modify, new Listener() {
			public void handleEvent(final Event event) {
				ReportParameterDialog.this.value = ReportParameterDialog.this.text2.getText();
				updateStatus(false);
			}
		});

		return container;
	}

	protected void updateStatus(final boolean initial) {
		IStatus status2set = Status.OK_STATUS;
		if (this.text.getText().trim().length() == 0) {
			status2set = StatusCreator.newStatus(Messages.ReportParameterDialog_ParameterNameMandatory);
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
