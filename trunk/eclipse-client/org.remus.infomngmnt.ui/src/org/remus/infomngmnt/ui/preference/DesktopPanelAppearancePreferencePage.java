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

package org.remus.infomngmnt.ui.preference;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.jface.preference.ScaleFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import org.remus.infomngmnt.ui.UIPlugin;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class DesktopPanelAppearancePreferencePage extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {

	/**
	 * Create the preference page
	 */
	public DesktopPanelAppearancePreferencePage() {
		super();
	}

	@Override
	protected IPreferenceStore doGetPreferenceStore() {
		return UIPlugin.getDefault().getPreferenceStore();
	}

	@Override
	protected void createFieldEditors() {
		addField(new IntegerFieldEditor(UIPreferenceInitializer.DESKTOP_PANEL_WIDTH,
				"Width (in px)", getFieldEditorParent()));
		// TODO Auto-generated method stub

		addField(new BooleanFieldEditor(UIPreferenceInitializer.DESKTOP_PANEL_TRANSPARENCY,
				"Transparency", BooleanFieldEditor.DEFAULT, getFieldEditorParent()));
		{
			ScaleFieldEditor scaleFieldEditor = new ScaleFieldEditor(
					UIPreferenceInitializer.DESKTOP_PANEL_TRANSPARENCY_PERCENTAGE,
					"Maximal Transparency (in %)", getFieldEditorParent(), 0, 100, 1, 10);
			scaleFieldEditor.setMaximum(50);
			scaleFieldEditor.setIncrement(10);
			addField(scaleFieldEditor);
		}
		addField(new BooleanFieldEditor(UIPreferenceInitializer.DESKTOP_PANEL_ALWAYS_ON_TOP,
				"Always on top", BooleanFieldEditor.DEFAULT, getFieldEditorParent()));
	}

	public void init(final IWorkbench workbench) {
		// TODO Auto-generated method stub

	}

}
