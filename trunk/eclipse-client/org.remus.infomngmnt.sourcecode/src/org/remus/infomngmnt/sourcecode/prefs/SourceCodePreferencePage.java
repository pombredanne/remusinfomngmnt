package org.remus.infomngmnt.sourcecode.prefs;

import java.util.ArrayList;
import java.util.Enumeration;

import net.sf.colorer.eclipse.ColorerPlugin;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.swtdesigner.preference.ComboFieldEditor;

import org.remus.infomngmnt.sourcecode.PreferenceInitializer;
import org.remus.infomngmnt.sourcecode.SourceCodePlugin;

public class SourceCodePreferencePage extends FieldEditorPreferencePage
implements IWorkbenchPreferencePage {

	private ArrayList<String[]> hrdSchemaName;
	private String[][] keyValues;


	/**
	 * Create the preference page
	 */
	public SourceCodePreferencePage() {
		super(FLAT);
	}

	/**
	 * Create contents of the preference page
	 */
	@Override
	protected void createFieldEditors() {
		{
			addField(new BooleanFieldEditor(PreferenceInitializer.LINE_NUMBERS, "Extract line numbers", getFieldEditorParent()));
		}

		{
			addField(new ComboFieldEditor(
					PreferenceInitializer.COLOR_SCHEME, "Color-Sheme", this.keyValues, getFieldEditorParent()));
		}
		// Create the field editors
	}

	/**
	 * Initialize the preference page
	 */
	public void init(IWorkbench workbench) {
		setPreferenceStore(SourceCodePlugin.getDefault().getPreferenceStore());

		final net.sf.colorer.ParserFactory pf = ColorerPlugin.getDefaultPF();
		this.hrdSchemaName = new ArrayList<String[]>();
		for (Enumeration hrds = pf.enumerateHRDInstances("rgb"); hrds
		.hasMoreElements();) {
			final String hrd_name = (String) hrds.nextElement();
			final String hrd_descr = pf.getHRDescription("rgb", hrd_name);
			String[] entryValue = new String[2];
			entryValue[0] = hrd_descr;
			entryValue[1] = hrd_name;
			this.hrdSchemaName.add(entryValue);
		}
		this.keyValues = new String[this.hrdSchemaName.size()][];
		for (int i = 0; i < this.hrdSchemaName.size(); i++) {
			String[] array_element = this.hrdSchemaName.get(i);
			this.keyValues[i] = array_element;
		}
	}

}
