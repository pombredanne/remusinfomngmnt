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

package org.remus.infomngmnt.mindmap.ui;

import java.util.List;

import org.eclipse.remus.BinaryReference;
import org.eclipse.remus.core.model.InformationStructureRead;
import org.eclipse.remus.ui.editors.editpage.AbstractInformationFormPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

import org.remus.infomngmnt.mindmap.handler.OpenMindMapEditorHandler;
import org.remus.infomngmnt.mindmap.messages.Messages;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class MindMapEditPage extends AbstractInformationFormPage {

	/**
	 * 
	 */
	public MindMapEditPage() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.ui.extension.AbstractInformationFormPage#renderPage
	 * (org.eclipse.ui.forms.IManagedForm)
	 */
	@Override
	protected void renderPage(final IManagedForm managedForm) {
		FormToolkit toolkit = managedForm.getToolkit();
		ScrolledForm form = managedForm.getForm();
		Composite body = form.getBody();
		body.setLayout(new GridLayout());
		toolkit.paintBordersFor(body);

		doCreateContentSection(body, toolkit);
		doCreateSemanticSection(body, toolkit);
		form.reflow(true);

	}

	private void doCreateContentSection(final Composite body, final FormToolkit toolkit) {

		final Section generalSection = toolkit.createSection(body, ExpandableComposite.TITLE_BAR
				| ExpandableComposite.TWISTIE | ExpandableComposite.EXPANDED);
		final GridData gd_generalSection = new GridData(SWT.FILL, SWT.CENTER, true, false);
		generalSection.setLayoutData(gd_generalSection);
		generalSection.setText(Messages.MindMapEditPage_General);

		final Composite composite = toolkit.createComposite(generalSection, SWT.NONE);
		final GridLayout gridLayout = new GridLayout();
		composite.setLayout(gridLayout);
		toolkit.paintBordersFor(composite);
		generalSection.setClient(composite);

		org.eclipse.ui.forms.widgets.Hyperlink createHyperlink = toolkit.createHyperlink(composite,
				Messages.MindMapEditPage_OpenEditor, SWT.NONE);
		createHyperlink.addHyperlinkListener(new HyperlinkAdapter() {
			@Override
			public void linkActivated(final org.eclipse.ui.forms.events.HyperlinkEvent e) {
				InformationStructureRead read = InformationStructureRead
						.newSession(getModelObject());
				List<BinaryReference> binaryReferences = read.getBinaryReferences();
				OpenMindMapEditorHandler.openEditor(binaryReferences, getModelObject());

				super.linkActivated(e);
			}
		});

	}

	@Override
	public void bindValuesToUi() {
		super.bindValuesToUi();
	}

}
