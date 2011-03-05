package org.remus.infomngmnt.audio.wizard;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.remus.Category;
import org.eclipse.remus.InfomngmntPackage;
import org.eclipse.remus.InformationUnit;
import org.eclipse.remus.InformationUnitListItem;
import org.eclipse.remus.common.ui.image.ResourceManager;
import org.eclipse.remus.core.edit.DisposableEditingDomain;
import org.eclipse.remus.core.model.InformationStructureRead;
import org.eclipse.remus.core.services.IEditingHandler;
import org.eclipse.remus.services.RemusServiceTracker;
import org.eclipse.remus.ui.UIPlugin;
import org.eclipse.remus.ui.databinding.BindingWidgetFactory;
import org.eclipse.remus.ui.databinding.TextBindingWidget;
import org.eclipse.remus.ui.newwizards.GeneralPage;
import org.eclipse.remus.ui.operation.LoadFileToTmpFromPathRunnable;
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
import org.remus.infomngmnt.audio.AudioActivator;
import org.remus.infomngmnt.mediaplayer.extension.IMediaPlayer;
import org.remus.infomngmnt.mediaplayer.extension.IMediaPlayerExtensionService;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class GeneralAudioPage extends GeneralPage {

	private Text fileNameText;
	private Button browseButton;
	protected String tmpText;

	private DisposableEditingDomain editingDomain;
	private Text mediaTypeText;
	private RemusServiceTracker remusServiceTracker;
	private IEditingHandler editService;

	public GeneralAudioPage(final Category category) {
		super(category);
	}

	public GeneralAudioPage(final InformationUnitListItem selection) {
		super(selection);
	}

	@Override
	public void createControl(final Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		container.setLayout(new GridLayout());
		setTitle("New Audio");
		setMessage("This wizard enables you to create a new audio from a file.");
		setImageDescriptor(ResourceManager.getPluginImageDescriptor(
				AudioActivator.getDefault(),
				"icons/iconexperience/loudspeaker_wizard.png"));

		doCreateParentElementGroup(container);
		Group group = new Group(container, SWT.NONE);
		group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		group.setLayout(new GridLayout(3, false));
		group.setText("Name && File");
		doCreateNameElements(group);

		GridData gd_nameText = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gd_nameText.horizontalSpan = 2;
		nameText.setLayoutData(gd_nameText);

		final Label mediaTypeLabel = new Label(group, SWT.NONE);
		mediaTypeLabel.setText("Media-Type");

		mediaTypeText = new Text(group, SWT.BORDER);
		mediaTypeText.setEditable(false);
		gd_nameText = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gd_nameText.horizontalSpan = 2;
		mediaTypeText.setLayoutData(gd_nameText);

		if (files.length == 0) {
			final Label nameLabel = new Label(group, SWT.NONE);
			nameLabel.setText("File");
			fileNameText = new Text(group, SWT.BORDER);
			gd_nameText = new GridData(SWT.FILL, SWT.CENTER, true, false);
			gd_nameText.horizontalSpan = 2;
			fileNameText.setLayoutData(gd_nameText);

			browseButton = new Button(group, SWT.PUSH);
			browseButton.setText("Browse...");
			browseButton.addListener(SWT.Selection, new Listener() {

				public void handleEvent(final Event event) {
					FileDialog fd = new FileDialog(getShell());
					fd.setFilterExtensions(new String[] { "*.*" });
					fd.setFilterNames(new String[] { "Audio files" });
					String open = fd.open();
					if (open != null) {
						fileNameText.setText(open);
						LoadFileToTmpFromPathRunnable runnable = new LoadFileToTmpFromPathRunnable();
						runnable.setFilePath(open);
						try {
							getContainer().run(true, true, runnable);
							addFile(runnable.getTmpFile());
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
			final InformationStructureRead read = InformationStructureRead
					.newSession(unit);
			IMediaPlayerExtensionService playerExtensionService = UIPlugin
					.getDefault()
					.getService(IMediaPlayerExtensionService.class);
			String type = files[0].getFullPath().getFileExtension()
					.toLowerCase();
			IMediaPlayer playerByType = playerExtensionService
					.getPlayerByType(type);
			InformationUnit mediaTypeNode = read
					.getChildByNodeId(AudioActivator.NODE_NAME_MEDIATYPE);
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
		final InformationStructureRead read = InformationStructureRead
				.newSession(unit);
		TextBindingWidget mediaBinding = BindingWidgetFactory
				.createTextBinding(mediaTypeText, ctx, getEditingDomain());
		mediaBinding.bindModel(
				read.getChildByNodeId(AudioActivator.NODE_NAME_MEDIATYPE),
				read.getFeatureByNodeId(AudioActivator.NODE_NAME_MEDIATYPE));
		if (fileNameText != null) {
			fileNameText.addListener(SWT.Modify, new Listener() {
				public void handleEvent(final Event event) {
					IMediaPlayerExtensionService playerExtensionService = UIPlugin
							.getDefault().getService(
									IMediaPlayerExtensionService.class);
					String type = new Path(fileNameText.getText())
							.getFileExtension().toLowerCase();
					IMediaPlayer playerByType = playerExtensionService
							.getPlayerByType(type);
					InformationUnit mediaTypeNode = read
							.getChildByNodeId(AudioActivator.NODE_NAME_MEDIATYPE);
					if (playerByType != null) {
						mediaTypeNode.setStringValue(type);
					} else {
						mediaTypeNode
								.eUnset(InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);
					}
					String name = new Path(fileNameText.getText())
							.removeFileExtension().lastSegment();
					GeneralAudioPage.this.nameText.setText(name);
				}
			});
		}

	}

	private DisposableEditingDomain getEditingDomain() {
		if (editingDomain == null) {
			remusServiceTracker = new RemusServiceTracker(
					Platform.getBundle(AudioActivator.PLUGIN_ID));
			editService = remusServiceTracker.getService(IEditingHandler.class);
			editingDomain = editService.createNewEditingDomain();
		}
		return editingDomain;
	}

	@Override
	public void dispose() {
		getEditingDomain().dispose();
		if (remusServiceTracker != null && editService != null) {
			remusServiceTracker.ungetService(editService);
		}
		super.dispose();
	}

}
