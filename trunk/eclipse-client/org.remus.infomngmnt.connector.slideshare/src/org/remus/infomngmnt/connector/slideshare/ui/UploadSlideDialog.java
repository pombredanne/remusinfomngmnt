package org.remus.infomngmnt.connector.slideshare.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.sf.slidesharejava.Slideshare;
import net.sf.slidesharejava.beans.Format;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.observable.value.IValueChangeListener;
import org.eclipse.core.databinding.observable.value.ValueChangeEvent;
import org.eclipse.core.databinding.observable.value.WritableValue;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.databinding.swt.ISWTObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.remus.RemoteRepository;
import org.eclipse.remus.common.core.operation.CancelableJob;
import org.eclipse.remus.common.core.util.StringUtils;
import org.eclipse.remus.common.ui.UIUtil;
import org.eclipse.remus.common.ui.image.ResourceManager;
import org.eclipse.remus.core.remote.AbstractExtensionRepository;
import org.eclipse.remus.core.remote.RemoteActivator;
import org.eclipse.remus.core.remote.services.IRepositoryExtensionService;
import org.eclipse.remus.core.remote.services.IRepositoryService;
import org.eclipse.remus.util.StatusCreator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import org.remus.infomngmnt.connector.slideshare.Messages;
import org.remus.infomngmnt.connector.slideshare.SlideshareActivator;
import org.remus.infomngmnt.connector.slideshare.SlideshareConnector;

public class UploadSlideDialog extends TitleAreaDialog {

	private Text srcFile;
	private Text descriptionText;
	private Text tagsText;
	private Text titleText;

	private Button browseButton;
	private Button btnMakeSlidePrivate;
	private Button btnGenerateSecretUrl;
	private Button btnAllowEmbed;
	private Button btnShareWithContacts;
	private Button btnAllowDownload;

	private WritableValue srcValue;
	private WritableValue titleValue;
	private WritableValue descriptionValue;
	private WritableValue tagsValue;
	private WritableValue allowDonwloadValue;
	private WritableValue makePrivate;
	private WritableValue generateSecret;
	private WritableValue shareWithContacts;
	private WritableValue allowEmbed;
	private WritableValue errorMessage;
	private final String repositoryId;

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 * @param repositoryId
	 */
	public UploadSlideDialog(final Shell parentShell, final String repositoryId) {
		super(parentShell);
		this.repositoryId = repositoryId;
		setShellStyle(getShellStyle() | SWT.RESIZE);
	}

	/**
	 * Create contents of the dialog.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(final Composite parent) {
		setTitle(Messages.UploadSlideDialog_UploadNewSlides);
		setTitleImage(ResourceManager.getPluginImage(SlideshareActivator.getDefault(),
				"icons/slideshare_dialog.png")); //$NON-NLS-1$
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayout(new GridLayout(3, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		{
			Label lblSource = new Label(container, SWT.NONE);
			lblSource.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
			lblSource.setBounds(0, 0, 55, 15);
			lblSource.setText(Messages.UploadSlideDialog_Source);
		}
		{
			this.srcFile = new Text(container, SWT.BORDER);
			this.srcFile.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		}
		{
			this.browseButton = new Button(container, SWT.NONE);
			this.browseButton.setText(Messages.UploadSlideDialog_Browse);
			this.browseButton.addListener(SWT.Selection, new Listener() {

				public void handleEvent(final Event event) {
					FileDialog fd = new FileDialog(getShell(), SWT.OPEN);
					Format[] values = Format.values();
					List<String> extensions = new ArrayList<String>();
					for (Format format : values) {
						extensions.add(StringUtils.join("*.", format.getValue())); //$NON-NLS-1$
					}
					fd.setFilterExtensions(new String[] { org.apache.commons.lang.StringUtils.join(
							extensions, ";") }); //$NON-NLS-1$
					fd.setFilterNames(new String[] { Messages.UploadSlideDialog_SupportedFormats });
					String open = fd.open();
					if (open != null) {
						UploadSlideDialog.this.srcValue.setValue(open);
						if (UploadSlideDialog.this.titleValue.getValue() == null)
							UploadSlideDialog.this.titleValue
									.setValue(new Path(open).lastSegment());
					}
				}
			});
		}
		{
			Group grpGeneralProperties = new Group(container, SWT.NONE);
			grpGeneralProperties.setText(Messages.UploadSlideDialog_GeneralProperties);
			grpGeneralProperties.setLayout(new GridLayout(2, false));
			{
				GridData gridData = new GridData(SWT.FILL, SWT.CENTER, false, false, 3, 1);
				grpGeneralProperties.setLayoutData(gridData);
			}
			{
				Label lblTitle = new Label(grpGeneralProperties, SWT.NONE);
				lblTitle.setText(Messages.UploadSlideDialog_Title);
				this.titleText = new Text(grpGeneralProperties, SWT.BORDER);
				this.titleText
						.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

				Label lblDescription = new Label(grpGeneralProperties, SWT.NONE);
				lblDescription.setText(Messages.UploadSlideDialog_Description);
			}
			{
				this.descriptionText = new Text(grpGeneralProperties, SWT.BORDER | SWT.MULTI
						| SWT.V_SCROLL | SWT.WRAP);
				{
					GridData gridData = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
					gridData.heightHint = 40;
					this.descriptionText.setLayoutData(gridData);
				}
			}
			{
				Label lblTags = new Label(grpGeneralProperties, SWT.NONE);
				lblTags.setText(Messages.UploadSlideDialog_Tags);
			}
			{
				this.tagsText = new Text(grpGeneralProperties, SWT.BORDER);
				this.tagsText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
			}
			new Label(grpGeneralProperties, SWT.NONE);
			{
				this.btnAllowDownload = new Button(grpGeneralProperties, SWT.CHECK);
				this.btnAllowDownload.setSelection(true);
				this.btnAllowDownload.setText(Messages.UploadSlideDialog_AllowDownload);
			}
		}
		{
			Group grpPrivacy = new Group(container, SWT.NONE);
			grpPrivacy.setText(Messages.UploadSlideDialog_Privacy);
			grpPrivacy.setLayout(new GridLayout(3, false));
			grpPrivacy.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 3, 1));
			{
				this.btnMakeSlidePrivate = new Button(grpPrivacy, SWT.CHECK);
				this.btnMakeSlidePrivate.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false,
						false, 3, 1));
				this.btnMakeSlidePrivate.setSelection(true);
				this.btnMakeSlidePrivate.setText(Messages.UploadSlideDialog_MakeSlidesPrivate);
			}
			{
				Label label = new Label(grpPrivacy, SWT.SEPARATOR | SWT.HORIZONTAL);
				label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 3, 1));
			}
			{
				this.btnGenerateSecretUrl = new Button(grpPrivacy, SWT.CHECK);
				this.btnGenerateSecretUrl.setSelection(true);
				this.btnGenerateSecretUrl.setText(Messages.UploadSlideDialog_GenerateSecretUrl);
			}
			{
				this.btnAllowEmbed = new Button(grpPrivacy, SWT.CHECK);
				this.btnAllowEmbed.setSelection(true);
				this.btnAllowEmbed.setText(Messages.UploadSlideDialog_AllowEmbed);
			}
			{
				this.btnShareWithContacts = new Button(grpPrivacy, SWT.CHECK);
				this.btnShareWithContacts.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
						false, 1, 1));
				this.btnShareWithContacts.setSelection(true);
				this.btnShareWithContacts.setText(Messages.UploadSlideDialog_ShareWithContacts);
			}
		}
		bindValuesToUi();
		return area;
	}

	private void bindValuesToUi() {
		DataBindingContext ctx = new DataBindingContext();

		this.srcValue = new WritableValue(null, String.class);
		ISWTObservableValue srcObserve = SWTObservables.observeText(this.srcFile, SWT.Modify);
		ctx.bindValue(srcObserve, this.srcValue);

		this.descriptionValue = new WritableValue(null, String.class);
		ISWTObservableValue descriptionObserve = SWTObservables.observeText(this.descriptionText,
				SWT.Modify);
		ctx.bindValue(descriptionObserve, this.descriptionValue);

		this.titleValue = new WritableValue(null, String.class);
		ISWTObservableValue titleObserve = SWTObservables.observeText(this.titleText, SWT.Modify);
		ctx.bindValue(titleObserve, this.titleValue);

		this.tagsValue = new WritableValue(null, String.class);
		ISWTObservableValue tagsObserve = SWTObservables.observeText(this.tagsText, SWT.Modify);
		ctx.bindValue(tagsObserve, this.tagsValue);

		this.allowDonwloadValue = new WritableValue(true, Boolean.class);
		ISWTObservableValue allowDlObserve = SWTObservables.observeSelection(this.btnAllowDownload);
		ctx.bindValue(allowDlObserve, this.allowDonwloadValue);

		this.makePrivate = new WritableValue(false, Boolean.class);
		ISWTObservableValue makePrivateObserve = SWTObservables
				.observeSelection(this.btnMakeSlidePrivate);
		ctx.bindValue(makePrivateObserve, this.makePrivate);

		this.generateSecret = new WritableValue(false, Boolean.class);
		ISWTObservableValue generateSecretObserve = SWTObservables
				.observeSelection(this.btnGenerateSecretUrl);
		ctx.bindValue(generateSecretObserve, this.generateSecret);

		this.shareWithContacts = new WritableValue(true, Boolean.class);
		ISWTObservableValue shareWithcontactObserve = SWTObservables
				.observeSelection(this.btnShareWithContacts);
		ctx.bindValue(shareWithcontactObserve, this.shareWithContacts);

		this.allowEmbed = new WritableValue(true, Boolean.class);
		ISWTObservableValue allowEmbedObserve = SWTObservables.observeSelection(this.btnAllowEmbed);
		ctx.bindValue(allowEmbedObserve, this.allowEmbed);

		ISWTObservableValue observeEnabled = SWTObservables.observeEnabled(this.btnAllowEmbed);
		ISWTObservableValue observeEnabled2 = SWTObservables
				.observeEnabled(this.btnGenerateSecretUrl);
		ISWTObservableValue observeEnabled3 = SWTObservables
				.observeEnabled(this.btnShareWithContacts);
		ctx.bindValue(observeEnabled, makePrivateObserve, new UpdateValueStrategy(
				UpdateValueStrategy.POLICY_NEVER), null);
		ctx.bindValue(observeEnabled2, makePrivateObserve, new UpdateValueStrategy(
				UpdateValueStrategy.POLICY_NEVER), null);
		ctx.bindValue(observeEnabled3, makePrivateObserve, new UpdateValueStrategy(
				UpdateValueStrategy.POLICY_NEVER), null);

		this.errorMessage = new WritableValue(null, String.class);
		this.errorMessage.addValueChangeListener(new IValueChangeListener() {

			public void handleValueChange(final ValueChangeEvent event) {
				String value = (String) event.getObservableValue().getValue();
				File file = new File(value);
				if (file.exists() && file.isFile()) {
					setErrorMessage(null);
					getButton(OK).setEnabled(true);
				} else {
					setErrorMessage(Messages.UploadSlideDialog_NoValidFile);
					getButton(OK).setEnabled(false);
				}

			}
		});
		ctx.bindValue(this.errorMessage, this.srcValue);

	}

	@Override
	protected void configureShell(final Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(Messages.UploadSlideDialog_UploadNewSlides);
	}

	/**
	 * Create contents of the button bar.
	 * 
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(final Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
		getButton(OK).setEnabled(false);
	}

	@Override
	public boolean close() {
		UIUtil.saveDialogSettings(SlideshareActivator.getDefault(), this);
		return super.close();
	}

	/**
	 * Return the initial size of the dialog
	 */
	@Override
	protected Point getInitialSize() {
		return UIUtil.getDialogSettingsInitialSize(SlideshareActivator.getDefault(), this,
				new Point(450, 450));
	}

	@Override
	protected Point getInitialLocation(final Point initialSize) {
		return UIUtil.getDialogSettingsInitialLocation(SlideshareActivator.getDefault(), this,
				super.getInitialLocation(initialSize));
	}

	/**
	 * @return the srcValue
	 */
	public final String getSrc() {
		return (String) this.srcValue.getValue();
	}

	/**
	 * @return the title
	 */
	public final String getTitle() {
		return (String) this.titleValue.getValue();
	}

	/**
	 * @return the descriptionValue
	 */
	public final String getDescription() {
		return (String) this.descriptionValue.getValue();
	}

	/**
	 * @return the tagsValue
	 */
	public final String getTags() {
		return (String) this.tagsValue.getValue();
	}

	/**
	 * @return the allowDonwloadValue
	 */
	public final boolean isAllowDonwload() {
		return (Boolean) this.allowDonwloadValue.getValue();
	}

	/**
	 * @return the makePrivate
	 */
	public final boolean isMakePrivate() {
		return (Boolean) this.makePrivate.getValue();
	}

	/**
	 * @return the generateSecret
	 */
	public final boolean isGenerateSecret() {
		return (Boolean) this.generateSecret.getValue();
	}

	/**
	 * @return the shareWithContacts
	 */
	public final boolean isShareWithContacts() {
		return (Boolean) this.shareWithContacts.getValue();
	}

	/**
	 * @return the allowEmbed
	 */
	public final boolean isAllowEmbed() {
		return (Boolean) this.allowEmbed.getValue();
	}

	@Override
	protected void okPressed() {
		/*
		 * Need a way to fix that:
		 */
		final String src = getSrc();
		final String title = getTitle();
		final String description = getDescription();
		final String tags = getTags();
		final boolean allowDonwload = isAllowDonwload();
		final boolean makePrivate2 = isMakePrivate();
		final boolean generateSecret2 = isGenerateSecret();
		final boolean shareWithContacts2 = isShareWithContacts();
		final boolean allowEmbed2 = isAllowEmbed();

		CancelableJob job = new CancelableJob(Messages.UploadSlideDialog_UploadingSlides) {
			@Override
			protected IStatus runCancelable(final IProgressMonitor monitor) {
				monitor.beginTask(Messages.UploadSlideDialog_UploadingSelectedSlide, IProgressMonitor.UNKNOWN);
				RemoteRepository repositoryImplementation = RemoteActivator.getDefault()
						.getServiceTracker().getService(IRepositoryService.class)
						.getRepositoryById(UploadSlideDialog.this.repositoryId);
				IRepositoryExtensionService extensionService = RemoteActivator.getDefault()
						.getServiceTracker().getService(IRepositoryExtensionService.class);
				AbstractExtensionRepository repositoryDefinition = null;
				try {
					repositoryDefinition = extensionService
							.getItemByRepository(repositoryImplementation);
				} catch (CoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (repositoryDefinition != null
						&& repositoryDefinition instanceof SlideshareConnector) {
					Slideshare api = ((SlideshareConnector) repositoryImplementation).getApi();
					File file = new File(src);
					if (file.exists() && file.isFile()) {
						try {
							api.uploadSlideShow(title, description, tags, allowDonwload,
									makePrivate2, generateSecret2, allowEmbed2, shareWithContacts2,
									file);
						} catch (Exception e) {
							return StatusCreator.newStatus(Messages.UploadSlideDialog_ErrorUploading, e);
						}
					}
				}
				return Status.OK_STATUS;
			}
		};
		job.setUser(true);
		job.schedule();
		super.okPressed();
	}
}
