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

import java.util.List;

import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.viewers.IOpenListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.OpenEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.SynchronizationMetadata;
import org.remus.infomngmnt.Tag;
import org.remus.infomngmnt.core.model.ApplicationModelPool;
import org.remus.infomngmnt.core.model.EditingUtil;
import org.remus.infomngmnt.ui.editors.InformationEditor;
import org.remus.infomngmnt.ui.editors.InformationEditorInput;
import org.remus.infomngmnt.ui.extension.CollapsibleButtonBar;
import org.remus.infomngmnt.ui.provider.NavigatorDecoratingLabelProvider;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class TagSection extends CollapsibleButtonBar {

	private TreeViewer viewer;
	private AdapterFactoryContentProvider contentProvider;
	private NavigatorDecoratingLabelProvider labelProvider;

	@Override
	public void createControl(final Composite parent) {
		Tree tree = new Tree(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		//tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		this.viewer = new TreeViewer(tree);
		initProvider();
		initInput();
		initOpen();
		
		setControl(tree);
	}
	
	private void initProvider() {
		this.contentProvider = new AdapterFactoryContentProvider(EditingUtil.getInstance().getAdapterFactory()) {
			@Override
			public boolean hasChildren(final Object object) {
				if (object instanceof InformationUnitListItem) {
					return false;
				} else if (object instanceof Tag) {
					return ((Tag) object).getInfoUnits().size() > 0;
				}
				return super.hasChildren(object);
			}
			@Override
			public Object[] getChildren(final Object object) {
				if (object instanceof Tag) {
					return ((Tag) object).getInfoUnits().toArray();
				}
				return super.getChildren(object);
			}
		};
		
		this.labelProvider = new NavigatorDecoratingLabelProvider(
				new AdapterFactoryLabelProvider(EditingUtil.getInstance().getAdapterFactory()));
				

		this.viewer.setContentProvider(this.contentProvider);
		this.viewer.setLabelProvider(this.labelProvider);
		this.viewer.addFilter(new ViewerFilter() {
			@Override
			public boolean select(final Viewer viewer, final Object parentElement,
					final Object element) {
				return !(element instanceof SynchronizationMetadata);
			}
		});
	}
	
	private void initInput() {
		this.viewer.setInput(ApplicationModelPool.getInstance().getModel().getAvailableTags());

	}
	
	private void initOpen() {
		this.viewer.addOpenListener(new IOpenListener() {
			public void open(final OpenEvent event) {
				List list = ((IStructuredSelection) event.getSelection()).toList();
				for (Object object : list) {
					if (object instanceof InformationUnitListItem) {
						try {
							PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(
									new InformationEditorInput((InformationUnitListItem) object), InformationEditor.ID);
						} catch (PartInitException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}

		});

	}


}
