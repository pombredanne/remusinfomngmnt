package org.remus.infomngmnt.connector.twitter.ui;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import org.remus.infomngmnt.common.ui.UIUtil;
import org.remus.infomngmnt.common.ui.jface.AnnotatingQuickFixTextBox;
import org.remus.infomngmnt.connector.twitter.TwitterActivator;
import org.remus.infomngmnt.core.progress.CancelableRunnable;
import org.remus.infomngmnt.util.StatusCreator;

public class TweetDialog extends TitleAreaDialog {

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
	public TweetDialog(final Shell parentShell, final String initialMessage) {
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
		gridLayout.numColumns = 3;
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
					TweetDialog.this.availableCharacterLabel.setText(String.valueOf(MAX_CHAR
							- TweetDialog.this.styledText.getText().length()));
					TweetDialog.this.message = event.getNewValue().toString();
					if (MAX_CHAR - TweetDialog.this.styledText.getText().length() < 0) {
						setErrorMessage("Message is too long");
					} else {
						setErrorMessage(null);
					}
					checkOkButton();
				}
			}
		});

		this.availableCharacterLabel = new Label(container, SWT.NONE);
		this.availableCharacterLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 3, 1));
		this.availableCharacterLabel.setText(String.valueOf(MAX_CHAR));
		this.styledText.getFTextField().setText(
				this.initialMessage != null ? this.initialMessage : "");

		final Label shortenUrlLabel = new Label(container, SWT.NONE);
		shortenUrlLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1));
		shortenUrlLabel.setText("Shorten URL");

		this.text = new Text(container, SWT.BORDER);
		this.text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		this.combo = new Combo(container, SWT.READ_ONLY);
		this.combo.setLayoutData(new GridData());
		this.combo.add("bit.ly");
		this.combo.add("tinyurl");
		this.combo.select(0);

		final Button shortenButton = new Button(container, SWT.NONE);
		shortenButton.setText("Shorten");
		shortenButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				final String[] tinyUrl = new String[1];
				final String url = TweetDialog.this.text.getText();
				ProgressMonitorDialog pmd = new ProgressMonitorDialog(getShell());
				final int selectionIndex = TweetDialog.this.combo.getSelectionIndex();
				try {
					pmd.run(true, true, new CancelableRunnable() {
						@Override
						protected IStatus runCancelableRunnable(final IProgressMonitor monitor) {
							try {
								if (selectionIndex == 0) {
									tinyUrl[0] = ShrinkURLUtils.getBitLyUrl(url);
								} else {
									tinyUrl[0] = ShrinkURLUtils.getTinyUrl(url);
								}
							} catch (Exception e) {
								return StatusCreator.newStatus("Error getting tinyURL", e);
							}
							return Status.OK_STATUS;
						}

					});
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// do nothing
				}
				TweetDialog.this.styledText.getFTextField().insert(tinyUrl[0]);

			}
		});
		setTitle("New Tweet");
		setMessage("Enter a message (max. 140)");
		//
		return area;
	}

	protected void checkOkButton() {
		if (getButton(OK) != null) {
			getButton(OK)
					.setEnabled(MAX_CHAR - TweetDialog.this.styledText.getText().length() >= 0);
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
		UIUtil.saveDialogSettings(TwitterActivator.getDefault(), this);
		return super.close();
	}

	/**
	 * Return the initial size of the dialog
	 */
	@Override
	protected Point getInitialSize() {
		return UIUtil.getDialogSettingsInitialSize(TwitterActivator.getDefault(), this, new Point(
				500, 375));
	}

	@Override
	protected Point getInitialLocation(final Point initialSize) {
		return UIUtil.getDialogSettingsInitialLocation(TwitterActivator.getDefault(), this, super
				.getInitialLocation(initialSize));
	}

	@Override
	protected void configureShell(final Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("New Tweet");
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return this.message;
	}

}
