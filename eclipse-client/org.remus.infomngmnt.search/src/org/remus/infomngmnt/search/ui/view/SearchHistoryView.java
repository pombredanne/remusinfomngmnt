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

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.emf.databinding.EMFObservables;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.forms.DetailsPart;
import org.eclipse.ui.forms.IDetailsPage;
import org.eclipse.ui.forms.IFormPart;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.ManagedForm;
import org.eclipse.ui.forms.MasterDetailsBlock;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.part.ViewPart;

import org.remus.infomngmnt.common.ui.image.CommonImageRegistry;
import org.remus.infomngmnt.core.model.EditingUtil;
import org.remus.infomngmnt.search.SearchPackage;
import org.remus.infomngmnt.search.impl.SearchImpl;
import org.remus.infomngmnt.search.provider.SearchPlugin;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class SearchHistoryView extends ViewPart {

	private TableViewer viewer;

	/**
	 * 
	 */
	public SearchHistoryView() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createPartControl(Composite parent) {
		FormToolkit toolkit = new FormToolkit(Display.getCurrent());
		final ManagedForm managedForm = new ManagedForm(parent);

		initializeToolBar();

		// Databinding
		ObservableListContentProvider ocp = new ObservableListContentProvider();
		IObservableList observeList = EMFObservables.observeList(SearchPlugin.getPlugin().getSearchHistory(), SearchPackage.Literals.SAVED_SEARCHES__SEARCHES);

		managedForm.getForm().setText("Search history...");

		Section section = toolkit.createSection(parent, Section.DESCRIPTION);
		section.setText("Local save history");
		section
		.setDescription("This list contains all local saved searches.");
		section.marginWidth = 10;
		section.marginHeight = 5;
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
		managedForm.addPart(spart);
		this.viewer = new TableViewer(t);
		this.viewer.setContentProvider(ocp);
		this.viewer.setLabelProvider(new AdapterFactoryLabelProvider(EditingUtil.getInstance().getAdapterFactory()));
		this.viewer.setInput(observeList);
		this.viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				managedForm.fireSelectionChanged(spart, event.getSelection());
			}
		});
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
	 */
	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}
	private void initializeToolBar() {
		IToolBarManager toolBarManager = getViewSite().getActionBars().getToolBarManager();
	}

	private class SearchMasterDetailsBlock extends MasterDetailsBlock {

		@Override
		protected void createMasterPart(IManagedForm managedForm, Composite parent) {
			// TODO Auto-generated method stub

		}

		@Override
		protected void createToolBarActions(IManagedForm managedForm) {
			final ScrolledForm form = managedForm.getForm();
			Action haction = new Action("hor", IAction.AS_RADIO_BUTTON) {
				@Override
				public void run() {
					SearchMasterDetailsBlock.this.sashForm.setOrientation(SWT.HORIZONTAL);
					form.reflow(true);
				}
			};
			haction.setChecked(true);
			haction.setToolTipText("Horizontal orientation");
			haction.setImageDescriptor(CommonImageRegistry.getInstance().getDescriptor(CommonImageRegistry.SECTION_HORIZONTAL));
			Action vaction = new Action("ver", IAction.AS_RADIO_BUTTON) {
				@Override
				public void run() {
					SearchMasterDetailsBlock.this.sashForm.setOrientation(SWT.VERTICAL);
					form.reflow(true);
				}
			};
			vaction.setChecked(false);
			vaction.setToolTipText("Vertical orientation");
			vaction.setImageDescriptor(CommonImageRegistry.getInstance().getDescriptor(CommonImageRegistry.SECTION_VERTICAL));
			form.getToolBarManager().add(haction);
			form.getToolBarManager().add(vaction);

		}

		@Override
		protected void registerPages(DetailsPart pDetailsPart) {
			pDetailsPart.registerPage(SearchImpl.class, new SearchHistoryDetails());
		}

	}

	private class SearchHistoryDetails implements IDetailsPage {

		public void createContents(Composite parent) {
			// TODO Auto-generated method stub

		}

		public void commit(boolean onSave) {
			// TODO Auto-generated method stub

		}

		public void dispose() {
			// TODO Auto-generated method stub

		}

		public void initialize(IManagedForm form) {
			// TODO Auto-generated method stub

		}

		public boolean isDirty() {
			// TODO Auto-generated method stub
			return false;
		}

		public boolean isStale() {
			// TODO Auto-generated method stub
			return false;
		}

		public void refresh() {
			// TODO Auto-generated method stub

		}

		public void setFocus() {
			// TODO Auto-generated method stub

		}

		public boolean setFormInput(Object input) {
			// TODO Auto-generated method stub
			return false;
		}

		public void selectionChanged(IFormPart part, ISelection selection) {
			// TODO Auto-generated method stub

		}

	}
}
