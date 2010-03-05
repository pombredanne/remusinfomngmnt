package org.remus.infomngmnt.audio.wizard;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
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

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.audio.AudioActivator;
import org.remus.infomngmnt.common.ui.image.ResourceManager;
import org.remus.infomngmnt.core.edit.DisposableEditingDomain;
import org.remus.infomngmnt.core.model.InformationStructureRead;
import org.remus.infomngmnt.core.services.IEditingHandler;
import org.remus.infomngmnt.mediaplayer.extension.IMediaPlayer;
import org.remus.infomngmnt.mediaplayer.extension.IMediaPlayerExtensionService;
import org.remus.infomngmnt.services.RemusServiceTracker;
import org.remus.infomngmnt.ui.UIPlugin;
import org.remus.infomngmnt.ui.databinding.BindingWidgetFactory;
import org.remus.infomngmnt.ui.databinding.TextBindingWidget;
import org.remus.infomngmnt.ui.newwizards.GeneralPage;
import org.remus.infomngmnt.ui.operation.LoadFileToTmpFromPathRunnable;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class GeneralAudioPage extends GeneralPage {

	private Text fileNameText;
	private Button browseButton;
	protected String tmpText;
	private IFile tmpFile;

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
		setImageDescriptor(ResourceManager.getPluginImageDescriptor(AudioActivator.getDefault(),
				"icons/iconexperience/loudspeaker_wizard.png"));

		doCreateParentElementGroup(container);
		Group group = new Group(container, SWT.NONE);
		group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		group.setLayout(new GridLayout(3, false));
		group.setText("Name && File");
		doCreateNameElements(group);

		GridData gd_nameText = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gd_nameText.horizontalSpan = 2;
		this.nameText.setLayoutData(gd_nameText);

		final Label mediaTypeLabel = new Label(group, SWT.NONE);
		mediaTypeLabel.setText("Media-Type");

		this.mediaTypeText = new Text(group, SWT.BORDER);
		this.mediaTypeText.setEditable(false);
		gd_nameText = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gd_nameText.horizontalSpan = 2;
		this.mediaTypeText.setLayoutData(gd_nameText);

		if (this.unit.getBinaryReferences() == null) {
			final Label nameLabel = new Label(group, SWT.NONE);
			nameLabel.setText("File");
			this.fileNameText = new Text(group, SWT.BORDER);
			gd_nameText = new GridData(SWT.FILL, SWT.CENTER, true, false);
			gd_nameText.horizontalSpan = 2;
			this.fileNameText.setLayoutData(gd_nameText);

			this.browseButton = new Button(group, SWT.PUSH);
			this.browseButton.setText("Browse...");
			this.browseButton.addListener(SWT.Selection, new Listener() {

				public void handleEvent(final Event event) {
					FileDialog fd = new FileDialog(getShell());
					fd.setFilterExtensions(new String[] { "*.*" });
					fd.setFilterNames(new String[] { "Videos" });
					String open = fd.open();
					if (open != null) {
						GeneralAudioPage.this.fileNameText.setText(open);
						LoadFileToTmpFromPathRunnable runnable = new LoadFileToTmpFromPathRunnable();
						runnable.setFilePath(open);
						try {
							getContainer().run(true, true, runnable);
							GeneralAudioPage.this.tmpFile = runnable.getTmpFile();
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
		final InformationStructureRead read = InformationStructureRead.newSession(this.unit);
		TextBindingWidget mediaBinding = BindingWidgetFactory.createTextBinding(this.mediaTypeText,
				this.ctx, getEditingDomain());
		mediaBinding.bindModel(read.getChildByNodeId(AudioActivator.NODE_NAME_MEDIATYPE), read
				.getFeatureByNodeId(AudioActivator.NODE_NAME_MEDIATYPE));
		this.fileNameText.addListener(SWT.Modify, new Listener() {
			public void handleEvent(final Event event) {
				IMediaPlayerExtensionService playerExtensionService = UIPlugin.getDefault()
						.getService(IMediaPlayerExtensionService.class);
				String type = new Path(GeneralAudioPage.this.fileNameText.getText())
						.getFileExtension().toLowerCase();
				IMediaPlayer playerByType = playerExtensionService.getPlayerByType(type);
				InformationUnit mediaTypeNode = read
						.getChildByNodeId(AudioActivator.NODE_NAME_MEDIATYPE);
				if (playerByType != null) {
					mediaTypeNode.setStringValue(type);
				} else {
					mediaTypeNode.eUnset(InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);
				}
				String name = new Path(GeneralAudioPage.this.fileNameText.getText())
						.removeFileExtension().lastSegment();
				GeneralAudioPage.this.nameText.setText(name);
			}
		});

	}

	private DisposableEditingDomain getEditingDomain() {
		if (this.editingDomain == null) {
			this.remusServiceTracker = new RemusServiceTracker(Platform
					.getBundle(AudioActivator.PLUGIN_ID));
			this.editService = this.remusServiceTracker.getService(IEditingHandler.class);
			this.editingDomain = this.editService.createNewEditingDomain();
		}
		return this.editingDomain;
	}

	@Override
	public void dispose() {
		getEditingDomain().dispose();
		if (this.remusServiceTracker != null && this.editService != null) {
			this.remusServiceTracker.ungetService(this.editService);
		}
		super.dispose();
	}

	/**
	 * @return the tmpFile
	 */
	public IFile getTmpFile() {
		return this.tmpFile;
	}

	/**
	 * @param tmpFile
	 *            the tmpFile to set
	 */
	public void setTmpFile(final IFile tmpFile) {
		this.tmpFile = tmpFile;
	}
}
