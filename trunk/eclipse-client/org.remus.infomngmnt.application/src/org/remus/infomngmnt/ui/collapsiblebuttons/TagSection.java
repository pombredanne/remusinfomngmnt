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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.jface.viewers.IOpenListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.OpenEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.remus.AvailableTags;
import org.eclipse.remus.InformationUnitListItem;
import org.eclipse.remus.SynchronizationMetadata;
import org.eclipse.remus.Tag;
import org.eclipse.remus.ui.UIPlugin;
import org.eclipse.remus.ui.collapsiblebutton.CollapsibleButtonBar;
import org.eclipse.remus.ui.editors.InformationEditor;
import org.eclipse.remus.ui.editors.InformationEditorInput;
import org.eclipse.remus.ui.viewer.provider.InformationUnitLabelProvider;
import org.eclipse.remus.ui.viewer.provider.NavigatorDecoratingLabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;


/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class TagSection extends CollapsibleButtonBar {

	private TreeViewer viewer;
	private AdapterFactoryContentProvider contentProvider;
	private NavigatorDecoratingLabelProvider labelProvider;
	private final AdapterImpl refreshAdapter = new AdapterImpl() {
		@Override
		public void notifyChanged(final Notification msg) {
			getViewSite().getShell().getDisplay().asyncExec(new Runnable() {
				public void run() {
					TagSection.this.viewer.refresh();
				}
			});
		}
	};

	@Override
	public void createControl(final Composite parent) {
		Tree tree = new Tree(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		// tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		this.viewer = new TreeViewer(tree);
		initProvider();
		initInput();
		initOpen();

		setControl(tree);
	}

	private void initProvider() {
		this.contentProvider = new AdapterFactoryContentProvider(UIPlugin.getDefault()
				.getEditService().getAdapterFactory()) {
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
					EList<String> infoUnitIds = ((Tag) object).getInfoUnits();
					List<InformationUnitListItem> items = new ArrayList<InformationUnitListItem>(
							infoUnitIds.size());
					for (String id : infoUnitIds) {
						items.add(UIPlugin.getDefault().getApplicationService().getItemById(id,
								new NullProgressMonitor()));
					}
					return items.toArray();
				}
				return super.getChildren(object);
			}
		};

		this.labelProvider = new NavigatorDecoratingLabelProvider(new InformationUnitLabelProvider(
				UIPlugin.getDefault().getEditService().getAdapterFactory()));

		this.viewer.setContentProvider(this.contentProvider);
		this.viewer.setLabelProvider(this.labelProvider);
		this.viewer.addFilter(new ViewerFilter() {
			@Override
			public boolean select(final Viewer viewer, final Object parentElement,
					final Object element) {
				return !(element instanceof SynchronizationMetadata);
			}
		});
		this.viewer.setSorter(new ViewerSorter());
	}

	private void initInput() {
		AvailableTags availableTags = UIPlugin.getDefault().getApplicationService().getModel()
				.getAvailableTags();

		availableTags.eAdapters().add(this.refreshAdapter);
		this.viewer.setInput(availableTags);

	}

	private void initOpen() {
		this.viewer.addOpenListener(new IOpenListener() {
			public void open(final OpenEvent event) {
				List list = ((IStructuredSelection) event.getSelection()).toList();
				for (Object object : list) {
					if (object instanceof InformationUnitListItem) {
						try {
							PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
									.openEditor(
											new InformationEditorInput(
													(InformationUnitListItem) object),
											InformationEditor.ID);
						} catch (PartInitException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else if (object instanceof Tag) {

						if (TagSection.this.viewer.getExpandedState(object)) {
							TagSection.this.viewer.collapseToLevel(object, 1);
						} else {
							TagSection.this.viewer.expandToLevel(object, 1);
						}
					}
				}
			}

		});

	}

	@Override
	public void dispose() {
		UIPlugin.getDefault().getApplicationService().getModel().getAvailableTags().eAdapters()
				.remove(this.refreshAdapter);
		super.dispose();
	}

}
