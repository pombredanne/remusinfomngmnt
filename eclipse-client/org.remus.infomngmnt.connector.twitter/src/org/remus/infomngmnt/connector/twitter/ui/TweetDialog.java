package org.remus.infomngmnt.connector.twitter.ui;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.remus.common.core.util.ResourceUtil;
import org.eclipse.remus.common.ui.UIUtil;
import org.eclipse.remus.common.ui.jface.AnnotatingQuickFixTextBox;
import org.eclipse.remus.core.remote.security.CredentialProvider;
import org.eclipse.remus.ui.progress.CancelableRunnable;
import org.eclipse.remus.util.StatusCreator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.ImageTransfer;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.remus.infomngmnt.connector.twitter.Messages;
import org.remus.infomngmnt.connector.twitter.TwitterActivator;
import org.remus.infomngmnt.connector.twitter.jobs.UploadImageJob;

public class TweetDialog extends TitleAreaDialog {

	private Combo combo;
	private Text text;
	private AnnotatingQuickFixTextBox styledText;
	private Label availableCharacterLabel;
	private final String initialMessage;
	private String message;

	public static final int MAX_CHAR = 140;
	private final String repositoryId;

	/**
	 * Create the dialog
	 * 
	 * @param parentShell
	 * @param repositoryId
	 */
	public TweetDialog(final Shell parentShell, final String initialMessage,
			final String repositoryId) {
		super(parentShell);
		this.repositoryId = repositoryId;
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
		enterYourTextLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER,
				true, false, 3, 1));
		enterYourTextLabel.setText(Messages.TweetDialog_EnterText);

		Composite composite = new Composite(container, SWT.NONE);
		GridLayout layout = new GridLayout(1, false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3,
				1));

		styledText = new AnnotatingQuickFixTextBox(composite, "", ""); //$NON-NLS-1$ //$NON-NLS-2$

		styledText.addPropertyChangeListener(new IPropertyChangeListener() {
			public void propertyChange(final PropertyChangeEvent event) {
				if (AnnotatingQuickFixTextBox.OK_REQUESTED.equals(event
						.getProperty())) {
					okPressed();
				} else if (AnnotatingQuickFixTextBox.COMMENT_MODIFIED
						.equals(event.getProperty())) {
					availableCharacterLabel.setText(String.valueOf(MAX_CHAR
							- styledText.getText().length()));
					message = event.getNewValue().toString();
					if (MAX_CHAR - styledText.getText().length() < 0) {
						setErrorMessage(Messages.TweetDialog_MessageTooLong);
					} else {
						setErrorMessage(null);
					}
					checkOkButton();
				}
			}
		});

		availableCharacterLabel = new Label(container, SWT.NONE);
		availableCharacterLabel.setLayoutData(new GridData(SWT.RIGHT,
				SWT.CENTER, false, false, 3, 1));
		availableCharacterLabel.setText(String.valueOf(MAX_CHAR));
		styledText.getFTextField().setText(
				initialMessage != null ? initialMessage : ""); //$NON-NLS-1$

		new Label(container, SWT.NONE)
				.setText(Messages.TweetDialog_SpecialCharacter);
		ToolBar tb = new ToolBar(container, SWT.HORIZONTAL | SWT.FLAT);
		tb.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false,
				3, 1));
		addButton(tb, "\u2190", Messages.TweetDialog_ArrowRight); //$NON-NLS-1$
		addButton(tb, "\u2192", Messages.TweetDialog_ArrowLeft); //$NON-NLS-1$
		addButton(tb, "\u2194", Messages.TweetDialog_ArrowLeftRight); //$NON-NLS-1$
		addButton(tb, "\u263A", Messages.TweetDialog_WhiteSmiley); //$NON-NLS-1$
		addButton(tb, "\u2665", Messages.TweetDialog_BlackHeart); //$NON-NLS-1$
		addButton(tb, "\u266B", Messages.TweetDialog_BlackNote); //$NON-NLS-1$
		addButton(tb, "\u263C", Messages.TweetDialog_WhiteSun); //$NON-NLS-1$
		addButton(tb, "\u2020", Messages.TweetDialog_BlackCross); //$NON-NLS-1$

		final Label uploadImageLabel = new Label(container, SWT.NONE);
		uploadImageLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER,
				false, false, 3, 1));
		uploadImageLabel.setText(Messages.TweetDialog_UploadImage);

		Composite buttonComp = new Composite(container, SWT.NONE);
		GridLayout layout2 = new GridLayout(2, false);
		layout2.marginWidth = 0;
		layout2.marginHeight = 0;
		buttonComp.setLayout(layout2);
		buttonComp.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 3, 1));
		Button browseButton = new Button(buttonComp, SWT.PUSH);
		browseButton.setText(Messages.TweetDialog_Browse);
		browseButton.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true,
				false));
		browseButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				FileDialog fd = new FileDialog(getShell(), SWT.OPEN);
				fd.setFilterExtensions(new String[] { "*.jpg", "*.png", "*.gif" }); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				final String open = fd.open();
				if (open != null) {
					CredentialProvider credentialProvider = new CredentialProvider() {

					};
					credentialProvider.setGroup("twitpic"); //$NON-NLS-1$
					credentialProvider.setIdentifier("twitpic"); //$NON-NLS-1$
					TwitPicPasswordPrompt twitPicPasswordPrompt = new TwitPicPasswordPrompt(
							getShell(), credentialProvider.getUserName(),
							credentialProvider.getPassword());
					if (twitPicPasswordPrompt.open() == IDialogConstants.OK_ID) {
						if (twitPicPasswordPrompt.isSave()) {
							credentialProvider
									.setUserName(twitPicPasswordPrompt
											.getUsername());
							credentialProvider
									.setPassword(twitPicPasswordPrompt.getPwd());
						} else {
							credentialProvider.delete();
						}
						ProgressMonitorDialog pmd = new ProgressMonitorDialog(
								getShell());
						UploadImageJob job = new UploadImageJob(open,
								twitPicPasswordPrompt.getUsername(),
								twitPicPasswordPrompt.getPwd());
						try {
							pmd.run(true, true, job);
							styledText.getFTextField().insert(job.getUrl());
						} catch (Exception e) {
							setErrorMessage(Messages.TweetDialog_ErrorUploading);

						}
					}

				}
			}
		});
		Button clibboardButtom = new Button(buttonComp, SWT.PUSH);
		clibboardButtom.setText(Messages.TweetDialog_FromClipboard);
		clibboardButtom.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				Object contents2 = new Clipboard(getShell().getDisplay())
						.getContents(ImageTransfer.getInstance());
				if (contents2 != null) {
					ImageData data = (ImageData) contents2;
					ImageLoader loader = new ImageLoader();
					loader.data = new ImageData[] { data };
					IFile createTempFile = ResourceUtil.createTempFile("png"); //$NON-NLS-1$
					String osString = createTempFile.getLocation().toOSString();
					loader.save(osString, SWT.IMAGE_PNG);
					CredentialProvider credentialProvider = new CredentialProvider() {

					};
					credentialProvider.setGroup("twitpic"); //$NON-NLS-1$
					credentialProvider.setIdentifier("twitpic"); //$NON-NLS-1$
					TwitPicPasswordPrompt twitPicPasswordPrompt = new TwitPicPasswordPrompt(
							getShell(), credentialProvider.getUserName(),
							credentialProvider.getPassword());
					if (twitPicPasswordPrompt.open() == IDialogConstants.OK_ID) {
						if (twitPicPasswordPrompt.isSave()) {
							credentialProvider
									.setUserName(twitPicPasswordPrompt
											.getUsername());
							credentialProvider
									.setPassword(twitPicPasswordPrompt.getPwd());
						} else {
							credentialProvider.delete();
						}
						ProgressMonitorDialog pmd = new ProgressMonitorDialog(
								getShell());
						UploadImageJob job = new UploadImageJob(osString,
								twitPicPasswordPrompt.getUsername(),
								twitPicPasswordPrompt.getPwd());
						try {
							pmd.run(true, true, job);
							styledText.getFTextField().insert(job.getUrl());
						} catch (Exception e) {
							setErrorMessage(Messages.TweetDialog_ErrorUploading);
						}
					}
				} else {
					setErrorMessage(Messages.TweetDialog_ClipboardNoImage);
				}
			}
		});

		new Label(container, SWT.SEPARATOR | SWT.HORIZONTAL)
				.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
						3, 1));

		final Label shortenUrlLabel = new Label(container, SWT.NONE);
		shortenUrlLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false,
				false, 3, 1));
		shortenUrlLabel.setText(Messages.TweetDialog_ShortenUrl);

		text = new Text(container, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		combo = new Combo(container, SWT.READ_ONLY);
		combo.setLayoutData(new GridData());
		combo.add("bit.ly"); //$NON-NLS-1$
		combo.add("tinyurl"); //$NON-NLS-1$
		combo.select(0);

		final Button shortenButton = new Button(container, SWT.NONE);
		shortenButton.setText(Messages.TweetDialog_Shorten);
		shortenButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				final String[] tinyUrl = new String[1];
				final String url = text.getText();
				ProgressMonitorDialog pmd = new ProgressMonitorDialog(
						getShell());
				final int selectionIndex = combo.getSelectionIndex();
				try {
					pmd.run(true, true, new CancelableRunnable() {
						@Override
						protected IStatus runCancelableRunnable(
								final IProgressMonitor monitor) {
							try {
								if (selectionIndex == 0) {
									tinyUrl[0] = ShrinkURLUtils
											.getBitLyUrl(url);
								} else {
									tinyUrl[0] = ShrinkURLUtils.getTinyUrl(url);
								}
							} catch (Exception e) {
								return StatusCreator
										.newStatus(
												Messages.TweetDialog_ErrorGettingShortenedUrl,
												e);
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
				styledText.getFTextField().insert(tinyUrl[0]);

			}
		});
		setTitle(Messages.TweetDialog_NewTweet);
		setMessage(Messages.TweetDialog_EnterMessage);
		//
		return area;
	}

	private void addButton(final ToolBar tb, final String string,
			final String tooltip) {
		final ToolItem item = new ToolItem(tb, SWT.PUSH | SWT.BORDER);
		item.setText(string);
		item.setToolTipText(tooltip);
		item.addListener(SWT.Selection, new Listener() {

			public void handleEvent(final Event event) {
				styledText.getFTextField().insert(item.getText());
			}
		});

	}

	protected void checkOkButton() {
		if (getButton(OK) != null) {
			getButton(OK).setEnabled(
					MAX_CHAR - styledText.getText().length() >= 0);
		}

	}

	/**
	 * Create contents of the button bar
	 * 
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(final Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
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
		return UIUtil.getDialogSettingsInitialSize(
				TwitterActivator.getDefault(), this, new Point(500, 500));
	}

	@Override
	protected Point getInitialLocation(final Point initialSize) {
		return UIUtil.getDialogSettingsInitialLocation(
				TwitterActivator.getDefault(), this,
				super.getInitialLocation(initialSize));
	}

	@Override
	protected void configureShell(final Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(Messages.TweetDialog_NewTweet);
	}

	/**
	 * @return the message
	 */
	@Override
	public String getMessage() {
		return message;
	}

}
