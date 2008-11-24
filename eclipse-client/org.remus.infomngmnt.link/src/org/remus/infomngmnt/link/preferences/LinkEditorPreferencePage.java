package org.remus.infomngmnt.link.preferences;

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

import org.remus.infomngmnt.link.LinkActivator;

public class LinkEditorPreferencePage extends FieldEditorPreferencePage implements
IWorkbenchPreferencePage {

	private Group createGroup;
	private Group editingGroup;

	/**
	 * Create the preference page
	 */
	public LinkEditorPreferencePage() {
		super();
	}

	@Override
	protected IPreferenceStore doGetPreferenceStore() {
		return LinkActivator.getDefault().getPreferenceStore();
	}
	/**
	 * Initialize the preference page
	 */
	public void init(IWorkbench workbench) {

	}

	@Override
	protected Control createContents(Composite parent) {
		Composite fieldEditorParent = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		fieldEditorParent.setLayout(layout);
		fieldEditorParent.setFont(parent.getFont());

		this.createGroup = new Group(fieldEditorParent, SWT.NONE);
		this.createGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		this.createGroup.setText("Creation");

		this.editingGroup = new Group(fieldEditorParent, SWT.NONE);
		this.editingGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		this.editingGroup.setText("Edit && View");

		createFieldEditors();
		//adjustGridLayout();
		initialize();
		checkState();
		GridLayout gridLayout = new GridLayout(2, false);
		this.createGroup.setLayout(gridLayout);
		this.editingGroup.setLayout(new GridLayout(1, false));
		return fieldEditorParent;
	}

	@Override
	protected void createFieldEditors() {
		BooleanFieldEditor fieldEditor = new BooleanFieldEditor(LinkPreferenceInitializer.INDEX_DOCUMENT, "Index html webpages by default", this.createGroup);
		fieldEditor.fillIntoGrid(this.createGroup, 2);
		addField(fieldEditor);
		BooleanFieldEditor fieldEditor2 = new BooleanFieldEditor(LinkPreferenceInitializer.MAKE_SCREENSHOT, "Make Webshot by default", this.createGroup);
		fieldEditor2.fillIntoGrid(this.createGroup, 2);
		addField(fieldEditor2);
		addField(new StringFieldEditor(LinkPreferenceInitializer.SCREENSHOT_CMD, "Path to Webshot:", this.createGroup));
		addField(new BooleanFieldEditor("", "Show Webshot", this.editingGroup));
	}

}
