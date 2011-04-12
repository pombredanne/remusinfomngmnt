package org.remus.infomngmnt.video.newwizard;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.Path;
import org.eclipse.remus.Category;
import org.eclipse.remus.InfomngmntPackage;
import org.eclipse.remus.InformationUnit;
import org.eclipse.remus.InformationUnitListItem;
import org.eclipse.remus.common.ui.image.ResourceManager;
import org.eclipse.remus.core.edit.DisposableEditingDomain;
import org.eclipse.remus.core.services.IEditingHandler;
import org.eclipse.remus.ui.UIPlugin;
import org.eclipse.remus.ui.databinding.BindingWidgetFactory;
import org.eclipse.remus.ui.databinding.TextBindingWidget;
import org.eclipse.remus.ui.newwizards.GeneralPage;
import org.eclipse.remus.ui.operation.LoadFileToTmpFromPathRunnable;
import org.eclipse.remus.util.InformationUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.remus.infomngmnt.mediaplayer.extension.IMediaPlayer;
import org.remus.infomngmnt.mediaplayer.extension.IMediaPlayerExtensionService;
import org.remus.infomngmnt.video.VideoActivator;
import org.remus.infomngmnt.video.messages.Messages;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class GeneralVideoPage extends GeneralPage {

	private Text fileNameText;
	private Button browseButton;
	protected String tmpText;

	private final DisposableEditingDomain editingDomain;
	private Text mediaTypeText;

	public GeneralVideoPage(final Category category,
			final IEditingHandler editingHandler) {
		super(category);
		this.editingDomain = editingHandler.createNewEditingDomain();

	}

	public GeneralVideoPage(final InformationUnitListItem selection,
			final IEditingHandler editingHandler) {
		super(selection);
		this.editingDomain = editingHandler.createNewEditingDomain();
	}

	@Override
	public void createControl(final Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		container.setLayout(new GridLayout());
		setTitle(Messages.GeneralVideoPage_Title);
		setMessage(Messages.GeneralVideoPage_Subtitle);
		setImageDescriptor(ResourceManager.getPluginImageDescriptor(
				VideoActivator.getDefault(),
				"icons/iconexperience/wizards/video_wizard_title.png")); //$NON-NLS-1$

		doCreateParentElementGroup(container);
		Group group = new Group(container, SWT.NONE);
		group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		group.setLayout(new GridLayout(3, false));
		group.setText(Messages.GeneralVideoPage_NameFile);
		doCreateNameElements(group);

		GridData gd_nameText = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gd_nameText.horizontalSpan = 2;
		this.nameText.setLayoutData(gd_nameText);

		final Label mediaTypeLabel = new Label(group, SWT.NONE);
		mediaTypeLabel.setText(Messages.GeneralVideoPage_MediaType);

		this.mediaTypeText = new Text(group, SWT.BORDER);
		this.mediaTypeText.setEditable(false);
		gd_nameText = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gd_nameText.horizontalSpan = 2;
		this.mediaTypeText.setLayoutData(gd_nameText);

		if (this.files.length == 0) {
			final Label nameLabel = new Label(group, SWT.NONE);
			nameLabel.setText(Messages.GeneralVideoPage_File);
			this.fileNameText = new Text(group, SWT.BORDER);
			gd_nameText = new GridData(SWT.FILL, SWT.CENTER, true, false);
			gd_nameText.horizontalSpan = 2;
			this.fileNameText.setLayoutData(gd_nameText);

			this.browseButton = new Button(group, SWT.PUSH);
			this.browseButton.setText(Messages.GeneralVideoPage_Browse);
			this.browseButton.addListener(SWT.Selection, new Listener() {

				public void handleEvent(final Event event) {
					FileDialog fd = new FileDialog(getShell());
					fd.setFilterExtensions(new String[] { "*.*" }); //$NON-NLS-1$
					fd.setFilterNames(new String[] { Messages.GeneralVideoPage_Videos });
					String open = fd.open();
					if (open != null) {
						GeneralVideoPage.this.fileNameText.setText(open);
						LoadFileToTmpFromPathRunnable runnable = new LoadFileToTmpFromPathRunnable();
						runnable.setFilePath(open);
						try {
							getContainer().run(true, true, runnable);
							GeneralVideoPage.this.files = new IFile[] { runnable
									.getTmpFile() };
						} catch (InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}

			});
		} else {
			IMediaPlayerExtensionService playerExtensionService = UIPlugin
					.getDefault()
					.getService(IMediaPlayerExtensionService.class);
			String type = this.files[0].getFullPath().getFileExtension()
					.toLowerCase();
			IMediaPlayer playerByType = playerExtensionService
					.getPlayerByType(type);
			InformationUnit mediaTypeNode = InformationUtil.getChildByType(
					GeneralVideoPage.this.unit,
					VideoActivator.NODE_NAME_MEDIATYPE);
			if (playerByType != null) {
				mediaTypeNode.setStringValue(type);
			} else {
				mediaTypeNode
						.eUnset(InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);
			}
		}

		doCreatePropertiesGroup(container);
		initDatabinding();
		presetValues();
		initValidation();
		setControl(container);
	}

	@Override
	protected void initDatabinding() {
		super.initDatabinding();

		TextBindingWidget mediaBinding = BindingWidgetFactory
				.createTextBinding(this.mediaTypeText, this.ctx,
						this.editingDomain);
		mediaBinding.bindModel(InformationUtil.getChildByType(this.unit,
				VideoActivator.NODE_NAME_MEDIATYPE),
				InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);
		if (this.fileNameText != null) {
			this.fileNameText.addListener(SWT.Modify, new Listener() {
				public void handleEvent(final Event event) {
					IMediaPlayerExtensionService playerExtensionService = UIPlugin
							.getDefault().getService(
									IMediaPlayerExtensionService.class);
					String type = new Path(GeneralVideoPage.this.fileNameText
							.getText()).getFileExtension().toLowerCase();
					IMediaPlayer playerByType = playerExtensionService
							.getPlayerByType(type);
					InformationUnit mediaTypeNode = InformationUtil
							.getChildByType(GeneralVideoPage.this.unit,
									VideoActivator.NODE_NAME_MEDIATYPE);
					if (playerByType != null) {
						mediaTypeNode.setStringValue(type);
					} else {
						mediaTypeNode
								.eUnset(InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);
					}
					String name = new Path(GeneralVideoPage.this.fileNameText
							.getText()).removeFileExtension().lastSegment();
					GeneralVideoPage.this.nameText.setText(name);
				}
			});
		}

	}

	@Override
	public void dispose() {
		this.editingDomain.dispose();
		super.dispose();
	}

}
