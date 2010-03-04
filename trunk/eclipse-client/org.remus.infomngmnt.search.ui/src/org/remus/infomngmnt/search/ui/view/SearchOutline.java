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

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.events.IHyperlinkListener;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;

import org.remus.infomngmnt.search.Search;
import org.remus.infomngmnt.search.ui.editor.SearchResultEditor;
import org.remus.infomngmnt.search.ui.internal.extension.WebsearchExtensions;
import org.remus.infomngmnt.search.ui.websearch.Websearch;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class SearchOutline extends ContentOutlinePage {

	private final Search model;
	private Composite container;
	private FormToolkit toolkit;

	public SearchOutline(final Search model) {
		this.model = model;
	}

	@Override
	public Control getControl() {
		return this.container;
	}

	@Override
	public void createControl(final Composite parent) {
		this.toolkit = new FormToolkit(Display.getCurrent());

		this.container = this.toolkit.createComposite(parent, SWT.NONE);
		this.container.setLayout(new GridLayout(1, false));
		this.toolkit.paintBordersFor(this.container);

		final Section section = this.toolkit.createSection(this.container, Section.TITLE_BAR);
		section.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		section.setText("Search parameters");

		final Composite composite = this.toolkit.createComposite(section, SWT.NONE);
		this.toolkit.paintBordersFor(composite);
		TableWrapLayout wrapLayout = new TableWrapLayout();
		wrapLayout.numColumns = 2;
		composite.setLayout(wrapLayout);
		section.setClient(composite);

		FormText labelDate = this.toolkit.createFormText(composite, false);
		labelDate.setText("<form><p><b>Date of search:</b></p></form>", true, false);
		this.toolkit.createLabel(composite, SearchResultEditor.SDF.format(new Date(Long
				.valueOf(this.model.getId()))), SWT.WRAP);

		FormText labelSearchString = this.toolkit.createFormText(composite, false);
		labelSearchString.setText("<form><p><b>Search string:</b></p></form>", true, false);
		this.toolkit.createLabel(composite, this.model.getSearchString(), SWT.WRAP);

		FormText labelStartDate = this.toolkit.createFormText(composite, false);
		labelStartDate.setText("<form><p><b>From:</b></p></form>", true, false);
		this.toolkit.createLabel(composite,
				this.model.getDateStart() != null ? new SimpleDateFormat("yyyy-MM-dd")
						.format(this.model.getDateStart()) : "n.a.", SWT.WRAP);

		FormText labelStartEnd = this.toolkit.createFormText(composite, false);
		labelStartEnd.setText("<form><p><b>To:</b></p></form>", true, false);
		this.toolkit.createLabel(composite, this.model.getEndDate() != null ? new SimpleDateFormat(
				"yyyy-MM-dd").format(this.model.getEndDate()) : "n.a.", SWT.WRAP);

		FormText labelTypes = this.toolkit.createFormText(composite, false);
		labelTypes.setText("<form><p><b>Types:</b></p></form>", true, false);
		this.toolkit.createLabel(composite, StringUtils.join(this.model.getInfoType(), ", "),
				SWT.WRAP);

		FormText labelScope = this.toolkit.createFormText(composite, false);
		labelScope.setText("<form><p><b>Scope:</b></p></form>", true, false);
		this.toolkit.createLabel(composite, this.model.getScope().getLiteral(), SWT.WRAP);

		// Label createSeparator = this.toolkit.createSeparator(composite,
		// SWT.HORIZONTAL);
		// createSeparator.setLayoutData(twd);
		TableWrapData twoColumWrapData = new TableWrapData(TableWrapData.FILL_GRAB,
				TableWrapData.TOP, 1, 2);

		FormText searchAgainText = this.toolkit.createFormText(composite, false);
		searchAgainText.setText(
				"<form><p><br/><img href=\"searchAgain\"/><a>Search again</a></p></form>", true,
				false);
		searchAgainText.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB, TableWrapData.TOP,
				1, 2));

		FormText addToWatchedSearchesText = this.toolkit.createFormText(composite, false);
		addToWatchedSearchesText
				.setText(
						"<form><p><img href=\"watchSearches\"/><a>Add search to my watched searches</a></p></form>",
						true, false);
		addToWatchedSearchesText.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB,
				TableWrapData.TOP, 1, 2));

		final Section section2 = this.toolkit.createSection(this.container, Section.TITLE_BAR
				| Section.DESCRIPTION);
		section2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		section2.setText("Websearches");
		section2
				.setDescription("Use your search parameters for a online search through the following online-searvices...");

		final Composite composite2 = this.toolkit.createComposite(section2, SWT.NONE);
		composite2.setLayout(new GridLayout(1, false));
		this.toolkit.paintBordersFor(composite);
		section2.setClient(composite2);
		buildWebsearches(composite2);

	}

	@Override
	public void setFocus() {
		this.container.setFocus();
	}

	private void buildWebsearches(final Composite composite2) {
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		gridData.widthHint = SWT.DEFAULT;
		gridData.heightHint = SWT.DEFAULT;
		IHyperlinkListener hyperLinkListener = new HyperlinkAdapter() {
			@Override
			public void linkActivated(final HyperlinkEvent e) {
				Program.launch((String) e.getHref());
			}
		};

		Collection<Websearch> websearch = WebsearchExtensions.getInstance().getSearches();
		for (Websearch websearch2 : websearch) {
			FormText formText = this.toolkit.createFormText(composite2, true);
			formText.setLayoutData(gridData);
			StringBuilder sb = new StringBuilder();
			sb.append("<form><p>");
			sb.append("<img align=\"bottom\" href=\"").append(websearch2.getId()).append("\"/>  ");
			sb.append("<a href=\"").append(
					StringEscapeUtils.escapeXml(NLS.bind(websearch2.getPattern(), this.model
							.getSearchString())));
			sb.append("\">").append(websearch2.getName()).append("</a></p></form>");
			formText.setText(sb.toString(), true, false);
			formText.addHyperlinkListener(hyperLinkListener);
			if (websearch2.getImage() != null) {
				formText.setImage(websearch2.getId(), websearch2.getImage());
			}
		}

	}

}
