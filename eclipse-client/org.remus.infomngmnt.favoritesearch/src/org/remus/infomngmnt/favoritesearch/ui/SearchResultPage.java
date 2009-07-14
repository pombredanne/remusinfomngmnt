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

package org.remus.infomngmnt.favoritesearch.ui;

import java.text.SimpleDateFormat;

import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.SharedHeaderFormEditor;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.events.IHyperlinkListener;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.common.ui.editor.EditorUtil;
import org.remus.infomngmnt.core.extension.InformationExtensionManager;
import org.remus.infomngmnt.favoritesearch.FavoriteSearchActivator;
import org.remus.infomngmnt.favoritesearch.util.SearchDiff;
import org.remus.infomngmnt.favoritesearch.util.SearchSerializer;
import org.remus.infomngmnt.search.Search;
import org.remus.infomngmnt.search.SearchResult;
import org.remus.infomngmnt.ui.extension.AbstractInformationFormPage;
import org.remus.infomngmnt.util.InformationUtil;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class SearchResultPage extends AbstractInformationFormPage {

	public final static SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //$NON-NLS-1$
	private FormText formText;
	private final Adapter searchChangeListener = new AdapterImpl() {
		@Override
		public void notifyChanged(final Notification msg) {
			if (msg.getFeature() == InfomngmntPackage.Literals.INFORMATION_UNIT__BINARY_VALUE) {
				getSite().getShell().getDisplay().asyncExec(new Runnable() {
					public void run() {
						setList(FavoriteSearchActivator.RESULT_NODE, SearchResultPage.this.formText);
					}
				});
			}
		};
	};
	private final Adapter searchNewChangeListener = new AdapterImpl() {
		@Override
		public void notifyChanged(final Notification msg) {
			if (msg.getFeature() == InfomngmntPackage.Literals.INFORMATION_UNIT__BINARY_VALUE) {
				getSite().getShell().getDisplay().asyncExec(new Runnable() {
					public void run() {
						setList(FavoriteSearchActivator.NEW_ELEMENTS_TYPE,
								SearchResultPage.this.formTextNewResults);
					}
				});
			}
		};
	};
	private FormText formTextNewResults;
	private ScrolledForm form;

	/**
	 * 
	 */
	public SearchResultPage() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void renderPage(final IManagedForm managedForm) {
		FormToolkit toolkit = managedForm.getToolkit();
		this.form = managedForm.getForm();
		Composite body = this.form.getBody();
		body.setLayout(new TableWrapLayout());
		toolkit.paintBordersFor(body);

		IHyperlinkListener hyperLinkListener = new HyperlinkAdapter() {
			@Override
			public void linkActivated(final HyperlinkEvent e) {
				String[] split = ((String) e.getHref()).split("\\|"); //$NON-NLS-1$
				EditorUtil.openEditor(new Path(split[1]));
			}
		};

		final Section newResultSection = toolkit.createSection(body, ExpandableComposite.TITLE_BAR
				| ExpandableComposite.TWISTIE);
		newResultSection
				.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB, TableWrapData.TOP));
		newResultSection.setText("New Results since last search");

		final Composite newResultComposite = toolkit.createComposite(newResultSection, SWT.NONE);

		newResultComposite.setLayout(new TableWrapLayout());
		toolkit.paintBordersFor(newResultComposite);
		newResultSection.setClient(newResultComposite);

		this.formTextNewResults = toolkit.createFormText(newResultComposite, true);
		this.formTextNewResults.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB,
				TableWrapData.TOP));
		this.formTextNewResults.addHyperlinkListener(hyperLinkListener);

		final Section resultSection = toolkit.createSection(body, ExpandableComposite.TITLE_BAR
				| ExpandableComposite.TWISTIE | ExpandableComposite.EXPANDED);
		resultSection.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB, TableWrapData.TOP));
		resultSection.setText("Latest Results");

		final Composite composite_2 = toolkit.createComposite(resultSection, SWT.NONE);

		composite_2.setLayout(new TableWrapLayout());
		toolkit.paintBordersFor(composite_2);
		resultSection.setClient(composite_2);

		this.formText = toolkit.createFormText(composite_2, true);
		this.formText.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB, TableWrapData.TOP));
		this.formText.addHyperlinkListener(hyperLinkListener);

		IToolBarManager toolBarManager = ((SharedHeaderFormEditor) getEditor()).getHeaderForm()
				.getForm().getToolBarManager();
		toolBarManager.add(new Action("Refresh") {
			@Override
			public void run() {
				boolean saveEditor = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
						.getActivePage().saveEditor(getEditor(), true);
				if (saveEditor) {
					SearchDiff.applyDiffToFavorite(getModelObject(), getEditor());

				}
			}
		});
		toolBarManager.update(true);

		doCreateSemanticSection(body, toolkit, true);

	}

	@Override
	public void bindValuesToUi() {
		super.bindValuesToUi();
		setList(FavoriteSearchActivator.RESULT_NODE, this.formText);
		setList(FavoriteSearchActivator.NEW_ELEMENTS_TYPE, this.formTextNewResults);
		InformationUtil.getChildByType(getModelObject(), FavoriteSearchActivator.RESULT_NODE)
				.eAdapters().add(this.searchChangeListener);
		InformationUtil.getChildByType(getModelObject(), FavoriteSearchActivator.NEW_ELEMENTS_TYPE)
				.eAdapters().add(this.searchNewChangeListener);
	}

	private void setList(final String node, final FormText text) {
		Search deserialize = SearchSerializer.deserialize(InformationUtil.getChildByType(
				getModelObject(), node).getBinaryValue());
		EList<SearchResult> result2 = deserialize.getResult();
		StringBuilder sb = new StringBuilder();
		sb.append("<form>");
		if (result2.size() == 0) {
			sb.append("<p><b>No results.</b></p>");
		} else {
			for (int i = 0, n = result2.size(); i < n; i++) {
				sb.append(buildSearchResultSting(result2.get(i), i, n));
				text.setImage(result2.get(i).getInfoType(), InformationExtensionManager
						.getInstance().getInfoTypeByType(result2.get(i).getInfoType()).getImage());
			}
		}
		sb.append("</form>");
		text.setText(sb.toString(), true, false);
		this.form.reflow(true);

	}

	@Override
	public void disposeBinding() {
		InformationUtil.getChildByType(getModelObject(), FavoriteSearchActivator.RESULT_NODE)
				.eAdapters().remove(this.searchChangeListener);
		InformationUtil.getChildByType(getModelObject(), FavoriteSearchActivator.NEW_ELEMENTS_TYPE)
				.eAdapters().remove(this.searchNewChangeListener);
	}

	private String buildSearchResultSting(final SearchResult result, final int index,
			final int count) {
		StringBuilder sb = new StringBuilder();
		sb.append("<p>").append(index + 1).append(" of ").append(count).append(" \u00AD ");
		if (result.getDate() != null) {
			sb.append(SDF.format(result.getDate()));
		} else {
			sb.append("n.a.");
		}
		sb.append("</p><p>");
		sb.append("<img href=\"").append(result.getInfoType()).append("\"/>").append(" <a href=\"")
				.append(index).append("|").append(result.getPath().toString()).append("\">")
				.append(result.getTitle()).append("</a>").append("</p>");
		return sb.toString();
	}

}
