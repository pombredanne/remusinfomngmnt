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

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import org.remus.infomngmnt.ui.UIPlugin;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class UIPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	private Group buttonBarGroup;
	private Group editingGroup;

	@Override
	protected void createFieldEditors() {
		// new StringFieldEditor()

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

		this.buttonBarGroup = new Group(fieldEditorParent, SWT.NONE);
		this.buttonBarGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		this.buttonBarGroup.setText("Collapsible Button-bar");

		this.editingGroup = new Group(fieldEditorParent, SWT.NONE);
		this.editingGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		this.editingGroup.setText("Download");

		createFieldEditors();
		// adjustGridLayout();
		initialize();
		checkState();
		GridLayout gridLayout = new GridLayout(2, false);
		this.buttonBarGroup.setLayout(gridLayout);
		this.editingGroup.setLayout(new GridLayout(1, false));
		return fieldEditorParent;
	}

	public void init(final IWorkbench workbench) {
		setPreferenceStore(UIPlugin.getDefault().getPreferenceStore());
	}

}
