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
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ISetSelectionTarget;

import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.ui.collapsiblebutton.CollapsibleButtonBar;
import org.remus.infomngmnt.ui.editors.InformationEditor;
import org.remus.infomngmnt.ui.editors.InformationEditorInput;
import org.remus.infomngmnt.ui.viewer.NavigationViewer;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class NavigationSection extends CollapsibleButtonBar implements ISelectionProvider,
		IEditingDomainProvider, ISetSelectionTarget, IViewerProvider {

	private final NavigationViewer viewer;

	public NavigationSection() {
		this.viewer = new NavigationViewer() {
			@Override
			protected void handleOpen(final InformationUnitListItem object) {
				try {
					PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
							.openEditor(new InformationEditorInput(object), InformationEditor.ID);
				} catch (PartInitException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
	}

	@Override
	public void createControl(final Composite parent) {
		this.viewer.setContextMenuId(getId());
		this.viewer.createControl(getViewSite(), parent);
		setControl(this.viewer.getViewer().getControl());

	}

	public void addSelectionChangedListener(final ISelectionChangedListener listener) {
		this.viewer.addSelectionChangedListener(listener);

	}

	public ISelection getSelection() {
		return this.viewer.getSelection();

	}

	public void removeSelectionChangedListener(final ISelectionChangedListener listener) {
		this.viewer.removeSelectionChangedListener(listener);

	}

	public void setSelection(final ISelection selection) {
		this.viewer.setSelection(selection);

	}

	public EditingDomain getEditingDomain() {
		return this.viewer.getEditingDomain();
	}

	public void selectReveal(final ISelection selection) {
		this.viewer.selectReveal(selection);

	}

	@Override
	public boolean setFocus() {
		return this.viewer.setFocus();
	}

	@Override
	public void init(final IViewSite site, final IMemento memento) {
		this.viewer.init(site, memento);
		super.init(site, memento);
	}

	@Override
	public void saveState(final IMemento child) {
		this.viewer.saveState(child);
	}

	@Override
	public void dispose() {
		this.viewer.dispose();
	}

	public Viewer getViewer() {
		return this.viewer.getViewer();
	}

}
