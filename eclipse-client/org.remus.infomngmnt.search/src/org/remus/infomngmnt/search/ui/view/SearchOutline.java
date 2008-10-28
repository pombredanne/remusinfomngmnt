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

package org.remus.infomngmnt.search.ui.view;

import org.eclipse.emf.common.util.EList;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;

import org.remus.infomngmnt.search.Search;
import org.remus.infomngmnt.search.Websearch;
import org.remus.infomngmnt.search.internal.extension.WebsearchExtensions;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class SearchOutline extends ContentOutlinePage {

	private final Search model;
	private Composite container;
	private FormToolkit toolkit;

	public SearchOutline(Search model) {
		this.model = model;
	}


	@Override
	public Control getControl() {
		return this.container;
	}
	@Override
	public void createControl(Composite parent) {
		this.toolkit = new FormToolkit(Display.getCurrent());

		this.container = this.toolkit.createComposite(parent, SWT.NONE);
		this.container.setLayout(new GridLayout());
		this.toolkit.paintBordersFor(this.container);



		final Section section = this.toolkit.createSection(this.container, Section.TITLE_BAR);
		section.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		section.setText("Search parameters");

		final Composite composite = this.toolkit.createComposite(section, SWT.NONE);
		this.toolkit.paintBordersFor(composite);
		section.setClient(composite);

		final Section section2 = this.toolkit.createSection(this.container, Section.TITLE_BAR);
		section.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		section.setText("Websearches");

		final Composite composite2 = this.toolkit.createComposite(section, SWT.NONE);
		this.toolkit.paintBordersFor(composite);
		section2.setClient(composite);

		buildWebsearches(composite2);

	}


	private void buildWebsearches(Composite composite2) {
		EList<Websearch> websearch = WebsearchExtensions.getInstance().getSearches().getWebsearch();
		for (Websearch websearch2 : websearch) {
			FormText formText = this.toolkit.createFormText(composite2, true);
		}

	}

}
