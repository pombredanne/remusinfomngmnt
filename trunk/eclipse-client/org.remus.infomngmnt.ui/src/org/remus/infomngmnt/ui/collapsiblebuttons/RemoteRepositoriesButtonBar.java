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

package org.remus.infomngmnt.ui.collapsiblebuttons;

import org.eclipse.emf.common.ui.viewer.IViewerProvider;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.emf.edit.ui.provider.UnwrappingSelectionProvider;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.DecoratingLabelProvider;
import org.eclipse.jface.viewers.IOpenListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.OpenEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.PlatformUI;

import org.remus.infomngmnt.RemoteRepository;
import org.remus.infomngmnt.RepositoryCollection;
import org.remus.infomngmnt.core.model.EditingUtil;
import org.remus.infomngmnt.core.services.IRepositoryService;
import org.remus.infomngmnt.ui.UIPlugin;
import org.remus.infomngmnt.ui.deferred.DeferredContentProvider;
import org.remus.infomngmnt.ui.extension.CollapsibleButtonBar;
import org.remus.infomngmnt.ui.provider.NavigationCellLabelProvider;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class RemoteRepositoriesButtonBar extends CollapsibleButtonBar 
implements ISelectionProvider, IEditingDomainProvider, IViewerProvider {

	private TreeViewer viewer;
	private RemoteRepositoryContextMenu actionBar;
	private final EditingDomain editingDomain;
	
	public RemoteRepositoriesButtonBar() {
		this.editingDomain = EditingUtil.getInstance().createNewEditingDomain();
	}

	@Override
	public void createControl(final Composite parent) {
		Tree tree = new Tree(parent, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
		tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		this.viewer = new TreeViewer(tree);
		setControl(tree);
		
		initProvider();
		initInput();
		initOpen();
		hookContextMenu();
	}

	private void initOpen() {
		this.viewer.addOpenListener(new IOpenListener() {
			public void open(final OpenEvent event) {
				Object object = ((IStructuredSelection) event.getSelection()).getFirstElement();
				if (object instanceof RemoteRepository) {
					
				}
			}

		});
		
	}

	private void initInput() {
		RepositoryCollection repositories = UIPlugin.getDefault().getService(IRepositoryService.class).getRepositories();
		this.viewer.setInput(repositories);
	}

	private void initProvider() {
		IStructuredContentProvider contentProvider = new DeferredContentProvider(EditingUtil.getInstance().getAdapterFactory(),getViewSite());
		LabelProvider labelProvider = new DecoratingLabelProvider(
				new NavigationCellLabelProvider(),
				PlatformUI.getWorkbench().getDecoratorManager().getLabelDecorator());
		this.viewer.setContentProvider(contentProvider);
		this.viewer.setLabelProvider(labelProvider);
		getViewSite().setSelectionProvider(this.viewer);
	}
	
	/**
	 * @param viewer2
	 */
	private void hookContextMenu() {
		final MenuManager contextMenu = new MenuManager("#PopUpMenu");
		contextMenu.setRemoveAllWhenShown(true);
		this.actionBar = new RemoteRepositoryContextMenu();
		this.actionBar.init(getViewSite().getActionBars());
		this.actionBar.setActiveDomain(this);
		this.viewer.addSelectionChangedListener(this.actionBar);
		contextMenu.addMenuListener(this.actionBar);
		final Menu menu = contextMenu.createContextMenu(this.viewer.getControl());
		this.viewer.getControl().setMenu(menu);
		getViewSite().registerContextMenu(getId(), contextMenu, new UnwrappingSelectionProvider(this));

	}

	public void addSelectionChangedListener(final ISelectionChangedListener listener) {
		this.viewer.addSelectionChangedListener(listener);
		
	}

	public ISelection getSelection() {
		return this.viewer.getSelection();
	}

	public void removeSelectionChangedListener(
			final ISelectionChangedListener listener) {
		this.viewer.removeSelectionChangedListener(listener);
		
	}
	
	@Override
	public void dispose() {
		this.viewer.removeSelectionChangedListener(this.actionBar);
		super.dispose();
	}

	public void setSelection(final ISelection selection) {
		this.viewer.setSelection(selection);
		
	}

	public EditingDomain getEditingDomain() {
		return this.editingDomain;
	}

	public Viewer getViewer() {
		return this.viewer;
	}

}
