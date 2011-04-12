/*******************************************************************************
public boolean isDeletionWizard; * Copyright (c) 2009 Jan Hartwig, FEB Radebeul
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Jan Hartwig - initial API and implementation
 *******************************************************************************/
package org.remus.infomngmnt.geodata.preferences;

/**
 * @author Jan Hartwig <jhartwig@feb-radebeul.de>
 * 
 */
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Link;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import org.remus.infomngmnt.geodata.GeoDataActivator;
import org.remus.infomngmnt.geodata.Messages;

public class GeoDataEditorPreferencePage extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {

	private StringFieldEditor sfe;

	/**
	 * Create the preference page
	 */
	public GeoDataEditorPreferencePage() {
		super(FLAT);
	}

	/**
	 * Create contents of the preference page
	 */
	@Override
	protected void createFieldEditors() {
		{
			this.sfe = new StringFieldEditor(GeoDataPreferenceInitializer.GOOGLE_API_KEY,
					Messages.GeoDataEditorPreferencePage_GetMapsKey, 35, getFieldEditorParent());
			addField(this.sfe);

		}
	}

	@Override
	protected Control createContents(final Composite parent) {
		// TODO Auto-generated method stub
		Composite createContents = (Composite) super.createContents(parent);
		Link link = new Link(createContents, SWT.NONE);
		link.setText(Messages.GeoDataEditorPreferencePage_GetMapsKeyLink);
		link.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				Program.launch(Messages.GeoDataEditorPreferencePage_GetMapsKeyUrl);
			}
		});
		link.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, true, false));
		return createContents;
	}

	/**
	 * Initialize the preference page
	 */
	public void init(final IWorkbench workbench) {

	}

	@Override
	protected IPreferenceStore doGetPreferenceStore() {
		return GeoDataActivator.getDefault().getPreferenceStore();
	}
}
