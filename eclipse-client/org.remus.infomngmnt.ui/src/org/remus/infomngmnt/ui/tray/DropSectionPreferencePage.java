/*******************************************************************************
 * Copyright (c) 2008 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/

package org.remus.infomngmnt.ui.tray;

import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.common.ui.extension.AbstractTrayPreferencePage;
import org.remus.infomngmnt.core.model.ModelUtil;
import org.remus.infomngmnt.core.model.RuleUtil;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class DropSectionPreferencePage extends AbstractTrayPreferencePage {

	public static final String RULESET_KEY = "RULESET_KEY"; //$NON-NLS-1$
	private Text nameText;
	private ComboViewer comboViewer;

	@Override
	public void createControl(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		comp.setLayout(new GridLayout(2,false));
		Label label = new Label(comp, SWT.NONE);
		label.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false,
				false));
		label.setText("Name");
		this.nameText = new Text(comp, SWT.SINGLE | SWT.LEAD | SWT.BORDER);
		this.nameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		Label ruleSetLabel = new Label(comp, SWT.NONE);
		ruleSetLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false,
				false));
		ruleSetLabel.setText("Ruleset");
		Combo combo = new Combo(comp, SWT.READ_ONLY);
		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		this.comboViewer = new ComboViewer(combo);
		this.comboViewer.setContentProvider(new ArrayContentProvider());
		this.comboViewer.setLabelProvider(new LabelProvider());
		setControl(comp);

	}

	@Override
	public void bindValuesToUi() {

		// binds the name of the section to the text field...
		this.nameText.setText(this.section.getName());
		this.nameText.addListener(SWT.Modify, new Listener() {
			public void handleEvent(Event event) {
				DropSectionPreferencePage.this.section.setName(DropSectionPreferencePage.this.nameText.getText());
			}
		});
		List featureList = ModelUtil.getFeatureList(
				RuleUtil.getInstance().getElementRules().getNewElementRules(),
				InfomngmntPackage.Literals.NEW_ELEMENT_RULES__NAME);
		this.comboViewer.setInput(featureList);
		String string = this.section.getPreferenceOptions().get(RULESET_KEY);
		if (string != null) {
			this.comboViewer.setSelection(new StructuredSelection(string));
		}
		this.comboViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				DropSectionPreferencePage.this.section.getPreferenceOptions().put(RULESET_KEY,
						(String) ((IStructuredSelection) event.getSelection()).getFirstElement());
			}
		});

	}

}
