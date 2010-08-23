package org.remus.infomngmnt.task.tray;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.remus.common.ui.UIUtil;
import org.eclipse.remus.common.ui.image.ResourceManager;
import org.eclipse.remus.common.ui.jface.AnnotatingQuickFixTextBox;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import org.remus.infomngmnt.task.TaskActivator;

public class EnterDescriptionDialog extends TitleAreaDialog {

	private Combo combo;
	private Text text;
	private AnnotatingQuickFixTextBox styledText;
	private Label availableCharacterLabel;
	private final String initialMessage;
	private String message;

	public static final int MAX_CHAR = 140;

	/**
	 * Create the dialog
	 * 
	 * @param parentShell
	 */
	public EnterDescriptionDialog(final Shell parentShell, final String initialMessage) {
		super(parentShell);
		setShellStyle(getShellStyle() | SWT.RESIZE);
		this.initialMessage = initialMessage;
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
		container.setLayout(gridLayout);
		container.setLayoutData(new GridData(GridData.FILL_BOTH));

		final Label enterYourTextLabel = new Label(container, SWT.NONE);
		enterYourTextLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 3, 1));
		enterYourTextLabel.setText("Enter your text");

		Composite composite = new Composite(container, SWT.NONE);
		GridLayout layout = new GridLayout(1, false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1));

		this.styledText = new AnnotatingQuickFixTextBox(composite, "", "");

		this.styledText.addPropertyChangeListener(new IPropertyChangeListener() {
			public void propertyChange(final PropertyChangeEvent event) {
				if (AnnotatingQuickFixTextBox.OK_REQUESTED.equals(event.getProperty())) {
					okPressed();
				} else if (AnnotatingQuickFixTextBox.COMMENT_MODIFIED.equals(event.getProperty())) {
					EnterDescriptionDialog.this.message = event.getNewValue().toString();
					checkOkButton();
				}
			}
		});

		setTitle("New Worklog-Description");
		setMessage("Enter a message what you're doing");
		setTitleImage(ResourceManager.getPluginImage(TaskActivator.getDefault(),
				"icons/iconexperience/worklog_wizard.png"));
		return area;
	}

	protected void checkOkButton() {
		if (getButton(OK) != null) {
			getButton(OK).setEnabled(
					MAX_CHAR - EnterDescriptionDialog.this.styledText.getText().length() >= 0);
		}

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
		checkOkButton();
	}

	@Override
	public boolean close() {
		UIUtil.saveDialogSettings(TaskActivator.getDefault(), this);
		return super.close();
	}

	/**
	 * Return the initial size of the dialog
	 */
	@Override
	protected Point getInitialSize() {
		return UIUtil.getDialogSettingsInitialSize(TaskActivator.getDefault(), this, new Point(500,
				375));
	}

	@Override
	protected Point getInitialLocation(final Point initialSize) {
		return UIUtil.getDialogSettingsInitialLocation(TaskActivator.getDefault(), this, super
				.getInitialLocation(initialSize));
	}

	@Override
	protected void configureShell(final Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("New Worklog-Description");
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return this.message;
	}

}
