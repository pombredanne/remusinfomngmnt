package org.remus.infomngmnt.efs.ui;

import java.io.File;

import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.StatusDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

import org.remus.infomngmnt.core.model.StatusCreator;

public class FolderPrompt extends StatusDialog {

	private Text text;

	private String directory;

	private final boolean creation;

	/**
	 * Create the dialog
	 * 
	 * @param parentShell
	 */
	public FolderPrompt(final Shell parentShell, final boolean creation) {
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
					.setText("Please select a location where the keyfile is saved. During startup the provider tries to load this key file automatically");
		} else {
			pleaseEnterALabel
					.setText("The security provider was unable to load the key file from the provided location. Enter a new location.");
		}

		final Label label_1 = new Label(container, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_1.setLayoutData(new TableWrapData(TableWrapData.FILL, TableWrapData.TOP));

		final Label enterYourPasswordLabel = new Label(container, SWT.NONE);
		enterYourPasswordLabel.setText("Choose a folder");

		this.text = new Text(container, SWT.BORDER | SWT.PASSWORD);
		final TableWrapData twd_text = new TableWrapData(TableWrapData.LEFT, TableWrapData.TOP);
		twd_text.align = TableWrapData.FILL;
		this.text.setLayoutData(twd_text);
		this.text.addListener(SWT.Modify, new Listener() {
			public void handleEvent(final Event event) {
				String dir = ((Text) event.widget).getText();
				File file = new File(dir);
				if (dir.trim().length() == 0 && file.exists() && file.isDirectory()
						&& file.canWrite()) {
					updateStatus(StatusCreator.newStatus("Location is not valid."));
				} else {
					updateStatus(Status.OK_STATUS);
				}
				FolderPrompt.this.directory = ((Text) event.widget).getText();
			}
		});

		final Button browseButton = new Button(container, SWT.NONE);
		final TableWrapData twd_browseButton = new TableWrapData(TableWrapData.RIGHT,
				TableWrapData.TOP);
		browseButton.setLayoutData(twd_browseButton);
		browseButton.setText("Browse...");
		browseButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				DirectoryDialog dd = new DirectoryDialog(getShell());
				String open = dd.open();
				if (open != null) {
					FolderPrompt.this.text.setText(open);
				}
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
		return new Point(300, 250);
	}

	@Override
	protected void configureShell(final Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Enter password");
	}

	/**
	 * @return the pwd
	 */
	public String getDirectory() {
		return this.directory;
	}

}
