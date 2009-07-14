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

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.databinding.EMFDataBindingContext;
import org.eclipse.emf.databinding.EMFObservables;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.databinding.swt.ISWTObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.forms.DetailsPart;
import org.eclipse.ui.forms.IDetailsPage;
import org.eclipse.ui.forms.IFormPart;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.ManagedForm;
import org.eclipse.ui.forms.MasterDetailsBlock;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

import org.remus.infomngmnt.common.ui.image.CommonImageRegistry;
import org.remus.infomngmnt.common.ui.image.ResourceManager;
import org.remus.infomngmnt.common.ui.view.AbstractScrolledTitledView;
import org.remus.infomngmnt.search.Search;
import org.remus.infomngmnt.search.SearchPackage;
import org.remus.infomngmnt.search.editor.SearchResultEditor;
import org.remus.infomngmnt.search.impl.SearchImpl;
import org.remus.infomngmnt.search.provider.SearchPlugin;
import org.remus.infomngmnt.search.service.IFavoriteSearchHandler;
import org.remus.infomngmnt.search.service.LuceneSearchService;
import org.remus.infomngmnt.util.EditingUtil;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class SearchHistoryView extends AbstractScrolledTitledView {

	private TableViewer viewer;

	public static final String HREF_SEARCH_AGAIN = "searchAgain"; //$NON-NLS-1$
	public static final String HREF_WATCH_SEARCH = "watchSearch"; //$NON-NLS-1$

	/**
	 * 
	 */
	public SearchHistoryView() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createViewContents(final Composite parent) {
		final ManagedForm managedForm = new ManagedForm(parent);

		final SearchMasterDetailsBlock masterDetail = new SearchMasterDetailsBlock();
		masterDetail.createContent(managedForm);
		Action haction = new Action("hor", IAction.AS_RADIO_BUTTON) {
			@Override
			public void run() {
				masterDetail.horizontal();

			}
		};

		haction.setChecked(true);
		haction.setToolTipText("Horizontal orientation");
		haction.setImageDescriptor(CommonImageRegistry.getInstance().getDescriptor(
				CommonImageRegistry.SECTION_HORIZONTAL));
		Action vaction = new Action("ver", IAction.AS_RADIO_BUTTON) {
			@Override
			public void run() {
				masterDetail.vertical();
			}
		};
		vaction.setChecked(false);
		vaction.setToolTipText("Vertical orientation");
		vaction.setImageDescriptor(CommonImageRegistry.getInstance().getDescriptor(
				CommonImageRegistry.SECTION_VERTICAL));
		getToolbarManager().add(haction);
		getToolbarManager().add(vaction);
		getToolbarManager().update(true);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
	 */
	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

	@Override
	protected Layout createClientLayout() {
		return new FillLayout();
	}

	private class SearchMasterDetailsBlock extends MasterDetailsBlock {

		private TableViewer viewer;
		private IManagedForm managedForm;

		@Override
		protected void createMasterPart(final IManagedForm pManagedForm, final Composite parent) {
			this.managedForm = pManagedForm;
			FormToolkit toolkit = this.managedForm.getToolkit();
			ObservableListContentProvider ocp = new ObservableListContentProvider();
			IObservableList observeList = EMFObservables.observeList(SearchPlugin.getPlugin()
					.getSearchHistory(), SearchPackage.Literals.SEARCH_HISTORY__SEARCH);

			Section section = toolkit.createSection(parent, Section.DESCRIPTION);
			section.setText("Local save history");
			section.setDescription("This list contains all local saved searches.");
			section.marginWidth = 10;
			section.marginHeight = 5;
			section.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
			toolkit.createCompositeSeparator(section);
			Composite client = toolkit.createComposite(section, SWT.WRAP);
			GridLayout layout = new GridLayout();
			layout.numColumns = 1;
			layout.marginWidth = 2;
			layout.marginHeight = 2;
			client.setLayout(layout);
			Table t = toolkit.createTable(client, SWT.MULTI);
			GridData gd = new GridData(GridData.FILL_BOTH);
			gd.heightHint = 20;
			gd.widthHint = 100;
			t.setLayoutData(gd);
			toolkit.paintBordersFor(client);

			section.setClient(client);
			final SectionPart spart = new SectionPart(section);

			this.managedForm.addPart(spart);
			this.viewer = new TableViewer(t);
			this.viewer.setContentProvider(ocp);
			this.viewer.setLabelProvider(new AdapterFactoryLabelProvider(EditingUtil.getInstance()
					.getAdapterFactory()));
			this.viewer.setInput(observeList);
			this.viewer.addSelectionChangedListener(new ISelectionChangedListener() {
				public void selectionChanged(final SelectionChangedEvent event) {
					SearchMasterDetailsBlock.this.managedForm.fireSelectionChanged(spart, event
							.getSelection());
				}
			});

		}

		public void horizontal() {
			this.sashForm.setOrientation(SWT.HORIZONTAL);
			this.managedForm.getForm().reflow(true);
		}

		public void vertical() {
			this.sashForm.setOrientation(SWT.VERTICAL);
			this.managedForm.getForm().reflow(true);
		}

		@Override
		protected void registerPages(final DetailsPart pDetailsPart) {
			pDetailsPart.registerPage(SearchImpl.class, new SearchHistoryDetails());
		}

		@Override
		protected void createToolBarActions(final IManagedForm managedForm) {
			// TODO Auto-generated method stub

		}

	}

	private class SearchHistoryDetails implements IDetailsPage {

		private IManagedForm form;
		private EMFDataBindingContext ctx;
		private Search model;
		private Section s1;
		private FormToolkit toolkit;
		private Label dateLabel;
		private Label searchStringLabel;
		private Label endDateLabel;
		private Label infoTypeLabel;
		private Label scopeLabel;
		private Label startDateLabel;
		private final UpdateValueStrategy dateStrategy = new UpdateValueStrategy() {
			@Override
			public Object convert(final Object value) {
				if (value == null) {
					return "n.a";
				}
				return new SimpleDateFormat("yyyy-MM-dd").format(value);

			}
		};

		public void createContents(final Composite parent) {
			GridLayout gridLayout = new GridLayout(1, false);
			gridLayout.marginWidth = 5;
			gridLayout.marginHeight = 5;
			gridLayout.verticalSpacing = 0;
			gridLayout.horizontalSpacing = 0;
			parent.setLayout(gridLayout);

			// layout.bottomMargin = 2;

			FormToolkit toolkit = this.form.getToolkit();
			this.s1 = toolkit.createSection(parent, Section.DESCRIPTION);
			this.s1.marginWidth = 10;
			this.s1.setText("Type Two Details");
			this.s1.setDescription("Sets the properties of the selected customer.");
			GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
			gridData.widthHint = SWT.DEFAULT;
			gridData.heightHint = SWT.DEFAULT;
			this.s1.setLayoutData(gridData);

			toolkit.createCompositeSeparator(this.s1);
			Composite composite = toolkit.createComposite(this.s1);

			this.toolkit.paintBordersFor(composite);
			TableWrapLayout wrapLayout = new TableWrapLayout();
			wrapLayout.numColumns = 2;
			composite.setLayout(wrapLayout);
			this.s1.setClient(composite);

			FormText labelDate = this.toolkit.createFormText(composite, false);
			labelDate.setText("<form><p><b>Date of search:</b></p></form>", true, false);
			this.dateLabel = this.toolkit.createLabel(composite, "", SWT.WRAP);

			FormText labelSearchString = this.toolkit.createFormText(composite, false);
			labelSearchString.setText("<form><p><b>Search string:</b></p></form>", true, false);
			this.searchStringLabel = this.toolkit.createLabel(composite, "", SWT.WRAP);

			FormText labelStartDate = this.toolkit.createFormText(composite, false);
			labelStartDate.setText("<form><p><b>From:</b></p></form>", true, false);
			this.startDateLabel = this.toolkit.createLabel(composite, "", SWT.WRAP);

			FormText labelStartEnd = this.toolkit.createFormText(composite, false);
			labelStartEnd.setText("<form><p><b>To:</b></p></form>", true, false);
			this.endDateLabel = this.toolkit.createLabel(composite, "", SWT.WRAP);

			FormText labelTypes = this.toolkit.createFormText(composite, false);
			labelTypes.setText("<form><p><b>Types:</b></p></form>", true, false);
			this.infoTypeLabel = this.toolkit.createLabel(composite, "", SWT.WRAP);

			FormText labelScope = this.toolkit.createFormText(composite, false);
			labelScope.setText("<form><p><b>Scope:</b></p></form>", true, false);
			this.scopeLabel = this.toolkit.createLabel(composite, "", SWT.WRAP);

			FormText searchAgainText = this.toolkit.createFormText(composite, false);
			searchAgainText.setImage(HREF_SEARCH_AGAIN, ResourceManager.getPluginImage(SearchPlugin
					.getPlugin(), "icons/iconexperience/nav_refresh_blue.png"));
			searchAgainText.setText(org.remus.infomngmnt.common.core.util.StringUtils.join(
					"<form><p><img href=\"", HREF_SEARCH_AGAIN, "\"/><a href=\"",
					HREF_SEARCH_AGAIN, "\">Search again</a></p></form>"), true, false);
			searchAgainText.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB,
					TableWrapData.TOP, 1, 2));

			FormText addToWatchedSearchesText = this.toolkit.createFormText(composite, false);
			addToWatchedSearchesText.setImage(HREF_WATCH_SEARCH, ResourceManager.getPluginImage(
					SearchPlugin.getPlugin(), "icons/iconexperience/star_blue.png"));
			addToWatchedSearchesText.setText(org.remus.infomngmnt.common.core.util.StringUtils
					.join("<form><p><img href=\"", HREF_WATCH_SEARCH, "\"/><a href=\"",
							HREF_WATCH_SEARCH, "\">Watch search</a></p></form>"), true, false);
			addToWatchedSearchesText.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB,
					TableWrapData.TOP, 1, 2));
			addLinkListener(searchAgainText, addToWatchedSearchesText);

		}

		public void commit(final boolean onSave) {
			// do nothing
		}

		public void dispose() {
			this.ctx.dispose();
		}

		public void initialize(final IManagedForm form) {
			this.form = form;
			this.toolkit = form.getToolkit();
			this.ctx = new EMFDataBindingContext();

		}

		public boolean isDirty() {
			return false;
		}

		public boolean isStale() {
			return false;
		}

		public void refresh() {
			// TODO Auto-generated method stub

		}

		public void setFocus() {
			this.s1.setFocus();
		}

		public boolean setFormInput(final Object input) {
			return false;
		}

		public void selectionChanged(final IFormPart part, final ISelection selection) {
			this.ctx.dispose();
			this.model = (Search) ((IStructuredSelection) selection).getFirstElement();

			ISWTObservableValue swtSearchString = SWTObservables
					.observeText(this.searchStringLabel);
			IObservableValue emfSearchString = EMFObservables.observeValue(this.model,
					SearchPackage.Literals.SEARCH__SEARCH_STRING);
			this.ctx.bindValue(swtSearchString, emfSearchString, null, null);

			ISWTObservableValue swtDate = SWTObservables.observeText(this.dateLabel);
			IObservableValue emfDate = EMFObservables.observeValue(this.model,
					SearchPackage.Literals.SEARCH__ID);
			this.ctx.bindValue(swtDate, emfDate, null, new UpdateValueStrategy() {
				@Override
				public Object convert(final Object value) {
					return SearchResultEditor.SDF.format(new Date(Long.valueOf((String) value)));
				}
			});
			ISWTObservableValue swtDateStart = SWTObservables.observeText(this.startDateLabel);
			IObservableValue emfDateStart = EMFObservables.observeValue(this.model,
					SearchPackage.Literals.SEARCH__DATE_START);
			this.ctx.bindValue(swtDateStart, emfDateStart, null, this.dateStrategy);

			ISWTObservableValue swtDateEnd = SWTObservables.observeText(this.endDateLabel);
			IObservableValue emfDateEnd = EMFObservables.observeValue(this.model,
					SearchPackage.Literals.SEARCH__END_DATE);
			this.ctx.bindValue(swtDateEnd, emfDateEnd, null, this.dateStrategy);

			ISWTObservableValue swtScope = SWTObservables.observeText(this.scopeLabel);
			IObservableValue emfScope = EMFObservables.observeValue(this.model,
					SearchPackage.Literals.SEARCH__SCOPE);
			this.ctx.bindValue(swtScope, emfScope, null, null);

			ISWTObservableValue swtInfoTypes = SWTObservables.observeText(this.infoTypeLabel);
			IObservableValue emfInfoTypes = EMFObservables.observeValue(this.model,
					SearchPackage.Literals.SEARCH__INFO_TYPE);
			this.ctx.bindValue(swtInfoTypes, emfInfoTypes, null, new UpdateValueStrategy() {
				@Override
				public Object convert(final Object value) {
					return StringUtils.join((Collection<String>) value, ", ");
				}
			});

		}

		public void addLinkListener(final FormText... hyperlinks) {
			HyperlinkAdapter adapter = new HyperlinkAdapter() {
				@Override
				public void linkActivated(final HyperlinkEvent e) {
					Object href = e.getHref();
					if (HREF_WATCH_SEARCH.equals(href)) {

						IFavoriteSearchHandler service = SearchPlugin.getPlugin()
								.getFavoriteTrackerService();
						if (service == null) {
							MessageDialog.openWarning(getSite().getShell(), "No handler found",
									"No handler for creating watched searches found.");
						} else {
							service.addToFavorites(SearchHistoryDetails.this.model);
						}

					}
					if (HREF_SEARCH_AGAIN.equals(href)) {
						LuceneSearchService.getInstance().search(SearchHistoryDetails.this.model,
								true);
					}
				};
			};
			for (FormText formText : hyperlinks) {
				formText.addHyperlinkListener(adapter);
			}

		}

	}

	private void initializeToolBar() {
		IToolBarManager toolBarManager = getViewSite().getActionBars().getToolBarManager();
	}

}
