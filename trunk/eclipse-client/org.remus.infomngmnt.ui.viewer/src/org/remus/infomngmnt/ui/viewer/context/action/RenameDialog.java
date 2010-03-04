package org.remus.infomngmnt.ui.viewer.context.action;

import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.StatusDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import org.remus.infomngmnt.util.StatusCreator;

public class RenameDialog extends StatusDialog {

	private Text text;
	private final String oldName;
	private String newName;

	/**
	 * Create the dialog
	 * 
	 * @param parentShell
	 */
	public RenameDialog(final String text) {
		super(Display.getDefault().getActiveShell());
		this.oldName = text;
	}

	/**
	 * Create contents of the dialog
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(final Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		final GridData gd_container = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd_container.verticalIndent = 10;
		container.setLayoutData(gd_container);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		container.setLayout(gridLayout);

		final Label newNameLabel = new Label(container, SWT.NONE);
		newNameLabel.setText("New name:");

		this.text = new Text(container, SWT.BORDER);
		this.text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		this.text.setText(this.oldName);
		this.text.addListener(SWT.Modify, new Listener() {
			public void handleEvent(final Event event) {
				RenameDialog.this.newName = RenameDialog.this.text.getText();
				validate();
			}
		});
		//
		validate();
		return container;
	}

	protected void validate() {
		if (this.newName != null && this.newName.length() == 0) {
			updateStatus(StatusCreator.newStatus("New name must not be null"));
			return;
		} else if (this.newName == null || this.newName.equals(this.oldName)) {
			updateButtonsEnableState(StatusCreator.newStatus(""));
		} else {
			updateStatus(Status.OK_STATUS);
		}

	}

	/**
	 * Return the initial size of the dialog
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(300, 150);
	}

	@Override
	protected void configureShell(final Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Rename...");
	}

	public String getNewName() {
		return this.newName;
	}

}
