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

		this.createGroup.setText("Connection URLs");

		this.editingGroup = new Group(fieldEditorParent, SWT.NONE);
		this.editingGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		this.editingGroup.setText("Download");

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

		addField(new StringFieldEditor(PreferenceInitializer.GDATA_SERVER_URL, "Youtube Data URL",
				this.createGroup));
		addField(new StringFieldEditor(PreferenceInitializer.FAVORITES_URL, "Favorites URL",
				this.createGroup));
		addField(new StringFieldEditor(PreferenceInitializer.PLAYLIST_URL, "Playlist URL",
				this.createGroup));
		addField(new StringFieldEditor(PreferenceInitializer.HIGH_DEFINITION_DOWNLOAD_URL,
				"HD Download URL", this.createGroup));
		addField(new StringFieldEditor(PreferenceInitializer.HIGH_QUALITY_DOWNLOAD_URL,
				"High Quality Download URL", this.createGroup));
		addField(new StringFieldEditor(PreferenceInitializer.NORMAL_DOWNLOAD_URL,
				"Standard Quality Download URL", this.createGroup));
		addField(new StringFieldEditor(PreferenceInitializer.VIDEO_HTML_URL, "Website Video URL",
				this.createGroup));

		BooleanFieldEditor fieldEditor = new BooleanFieldEditor(PreferenceInitializer.ALWAYS_HD,
				"Download HD version if available", this.editingGroup);
		addField(fieldEditor);
	}

}
