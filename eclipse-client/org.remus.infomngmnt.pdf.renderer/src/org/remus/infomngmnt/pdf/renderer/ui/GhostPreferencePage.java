package org.remus.infomngmnt.pdf.renderer.ui;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import org.remus.infomngmnt.pdf.renderer.PreferenceInitializer;

public class GhostPreferencePage extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {

	/**
	 * Create the preference page.
	 */
	public GhostPreferencePage() {
		super(FLAT);
	}

	/**
	 * Create contents of the preference page.
	 */
	@Override
	protected void createFieldEditors() {
		// Create the field editors

		addField(new StringFieldEditor(PreferenceInitializer.PATH, "Path to Ghostscript", -1,
				StringFieldEditor.VALIDATE_ON_KEY_STROKE, getFieldEditorParent()));
		addField(new StringFieldEditor(PreferenceInitializer.OPTIONS, "Options", -1,
				StringFieldEditor.VALIDATE_ON_KEY_STROKE, getFieldEditorParent()));
	}

	/**
	 * Initialize the preference page.
	 */
	public void init(final IWorkbench workbench) {

	}

	@Override
	protected IPreferenceStore doGetPreferenceStore() {
		return org.remus.infomngmnt.pdf.renderer.Activator.getDefault().getPreferenceStore();
	}

}
