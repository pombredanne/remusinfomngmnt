/*******************************************************************************
 * Copyright (c) 2009 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/

package org.remus.infomngmnt.contact.ui.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceNode;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.remus.common.ui.UIUtil;
import org.eclipse.remus.common.ui.jface.MultiLineStringFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Link;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;

import org.remus.infomngmnt.contact.ContactActivator;
import org.remus.infomngmnt.contact.messages.Messages;
import org.remus.infomngmnt.contact.preferences.ContactPreferenceInitializer;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class ContactPreferencePage extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {

	private Group formatGroup;
	private Group editingGroup;
	private Group phoneCalls;

	public ContactPreferencePage() {
		super();
	}

	@Override
	protected void createFieldEditors() {
		MultiLineStringFieldEditor fieldEditor = new MultiLineStringFieldEditor(
				ContactPreferenceInitializer.FORMATTED_ADDRESS_PATTERN, Messages.ContactPreferencePage_AdressFormat,
				this.formatGroup);
		fieldEditor.setHeight(50);

		addField(fieldEditor);
		addField(new StringFieldEditor(ContactPreferenceInitializer.FORMATTED_NAME_PATTERN,
				Messages.ContactPreferencePage_NameFormat, this.formatGroup));

		// Google Preferences
		Link link = new Link(this.editingGroup, SWT.NONE);
		link.setText(Messages.ContactPreferencePage_YouNeedMapsKey);
		link.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false, 2, 1));
		link.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				IPreferenceNode preferenceNode = UIUtil
						.getPreferenceNode("org.remus.infomngmnt.geodata.preferencePage"); //$NON-NLS-1$
				if (preferenceNode != null) {
					((IWorkbenchPreferenceContainer) getContainer()).openPage(preferenceNode
							.getId(), null);
				}
			}
		});
		BooleanFieldEditor editor = new BooleanFieldEditor(
				ContactPreferenceInitializer.SHOW_MAPS_IMAGE, Messages.ContactPreferencePage_RenderMapsImage,
				this.editingGroup);
		editor.fillIntoGrid(this.editingGroup, 2);
		addField(editor);
		addField(new IntegerFieldEditor(ContactPreferenceInitializer.MAPS_IMAGE_WIDTH,
				Messages.ContactPreferencePage_ImageWidth, this.editingGroup));
		addField(new IntegerFieldEditor(ContactPreferenceInitializer.MAPS_IMAGE_HEIGHT,
				Messages.ContactPreferencePage_ImageHeight, this.editingGroup));
		addField(new IntegerFieldEditor(ContactPreferenceInitializer.MAPS_IMAGE_ZOOMLEVEL,
				Messages.ContactPreferencePage_ZoomLevel, this.editingGroup));

		// Phone calls
		BooleanFieldEditor showPhone = new BooleanFieldEditor(
				ContactPreferenceInitializer.SHOW_PHONE_LINKS,
				Messages.ContactPreferencePage_AddIconForPhone, this.phoneCalls);
		showPhone.fillIntoGrid(this.phoneCalls, 2);
		addField(showPhone);
		addField(new StringFieldEditor(ContactPreferenceInitializer.PHONE_CALL_PATTERN, Messages.ContactPreferencePage_Pattern,
				this.phoneCalls));

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

		this.formatGroup = new Group(fieldEditorParent, SWT.NONE);
		this.formatGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		this.formatGroup.setText(Messages.ContactPreferencePage_Formatting);

		this.editingGroup = new Group(fieldEditorParent, SWT.NONE);
		this.editingGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		this.editingGroup.setText(Messages.ContactPreferencePage_GoogleMaps);

		this.phoneCalls = new Group(fieldEditorParent, SWT.NONE);
		this.phoneCalls.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		this.phoneCalls.setText(Messages.ContactPreferencePage_Phone);

		createFieldEditors();
		// adjustGridLayout();
		initialize();
		checkState();
		GridLayout gridLayout = new GridLayout(2, false);
		this.formatGroup.setLayout(gridLayout);
		this.editingGroup.setLayout(new GridLayout(2, false));
		this.phoneCalls.setLayout(new GridLayout(2, false));
		return fieldEditorParent;
	}

	public void init(final IWorkbench workbench) {

	}

	@Override
	protected IPreferenceStore doGetPreferenceStore() {
		return ContactActivator.getDefault().getPreferenceStore();
	}

}
