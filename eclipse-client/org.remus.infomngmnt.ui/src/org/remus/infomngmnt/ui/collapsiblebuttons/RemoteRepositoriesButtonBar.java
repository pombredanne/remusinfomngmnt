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

import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.jface.viewers.DecoratingLabelProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.PlatformUI;

import org.remus.infomngmnt.RepositoryCollection;
import org.remus.infomngmnt.core.model.EditingUtil;
import org.remus.infomngmnt.core.services.IRepositoryService;
import org.remus.infomngmnt.ui.UIPlugin;
import org.remus.infomngmnt.ui.extension.CollapsibleButtonBar;
import org.remus.infomngmnt.ui.provider.NavigationCellLabelProvider;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class RemoteRepositoriesButtonBar extends CollapsibleButtonBar {

	private TreeViewer viewer;

	@Override
	public void createControl(final Composite parent) {
		Tree tree = new Tree(parent, SWT.MULTI);
		tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		this.viewer = new TreeViewer(tree);
		setControl(tree);
		
		initProvider();
		initInput();
		initOpen();
	}

	private void initOpen() {
		// TODO Auto-generated method stub
		
	}

	private void initInput() {
		RepositoryCollection repositories = UIPlugin.getDefault().getService(IRepositoryService.class).getRepositories();
		this.viewer.setInput(repositories);
	}

	private void initProvider() {
		IStructuredContentProvider contentProvider = new AdapterFactoryContentProvider(EditingUtil.getInstance().getAdapterFactory());
		LabelProvider labelProvider = new DecoratingLabelProvider(
				new NavigationCellLabelProvider(),
				PlatformUI.getWorkbench().getDecoratorManager().getLabelDecorator());
		this.viewer.setContentProvider(contentProvider);
		this.viewer.setLabelProvider(labelProvider);
		getViewSite().setSelectionProvider(this.viewer);
	}
}
