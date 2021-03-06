package org.remus.infomngmnt.connector.youtube.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import org.remus.infomngmnt.connector.youtube.YoutubeActivator;
import org.remus.infomngmnt.connector.youtube.messages.Messages;

public class YoutubePreferencePage extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {

	private Group createGroup;
	private Group editingGroup;

	/**
	 * Create the preference page
	 */
	public YoutubePreferencePage() {
		super();
	}

	@Override
	protected IPreferenceStore doGetPreferenceStore() {
		return YoutubeActivator.getDefault().getPreferenceStore();
	}

	/**
	 * Initialize the preference page
	 */
	public void init(final IWorkbench workbench) {

	}

	@Override
	protected Control createContents(final Composite parent) {
		Composite fieldEditorParent = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		fieldEditorParent.setLayout(layout);
		fieldEditorParent.setFont(parent.getFont());

		this.createGroup = new Group(fieldEditorParent, SWT.NONE);
		this.createGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		this.createGroup.setText(Messages.YoutubePreferencePage_ConnectionUrl);

		this.editingGroup = new Group(fieldEditorParent, SWT.NONE);
		this.editingGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		this.editingGroup.setText(Messages.YoutubePreferencePage_Download);

		createFieldEditors();
		// adjustGridLayout();
		initialize();
		checkState();
		GridLayout gridLayout = new GridLayout(2, false);
		this.createGroup.setLayout(gridLayout);
		this.editingGroup.setLayout(new GridLayout(1, false));
		return fieldEditorParent;
	}

	@Override
	protected void createFieldEditors() {

		addField(new StringFieldEditor(PreferenceInitializer.GDATA_SERVER_URL, Messages.YoutubePreferencePage_YoutubeDataURL,
				this.createGroup));
		addField(new StringFieldEditor(PreferenceInitializer.FAVORITES_URL, Messages.YoutubePreferencePage_FavoritesURL,
				this.createGroup));
		addField(new StringFieldEditor(PreferenceInitializer.PLAYLIST_URL, Messages.YoutubePreferencePage_PlaylistURL,
				this.createGroup));
		addField(new StringFieldEditor(PreferenceInitializer.HIGH_DEFINITION_DOWNLOAD_URL,
				Messages.YoutubePreferencePage_HDDownloadURL, this.createGroup));
		addField(new StringFieldEditor(PreferenceInitializer.HIGH_QUALITY_DOWNLOAD_URL,
				Messages.YoutubePreferencePage_HighQualityURL, this.createGroup));
		addField(new StringFieldEditor(PreferenceInitializer.NORMAL_DOWNLOAD_URL,
				Messages.YoutubePreferencePage_StandardURL, this.createGroup));
		addField(new StringFieldEditor(PreferenceInitializer.VIDEO_HTML_URL, Messages.YoutubePreferencePage_WebsiteVideoURL,
				this.createGroup));

		BooleanFieldEditor fieldEditor = new BooleanFieldEditor(PreferenceInitializer.ALWAYS_HD,
				Messages.YoutubePreferencePage_HDVersion, this.editingGroup);
		addField(fieldEditor);
	}

}
