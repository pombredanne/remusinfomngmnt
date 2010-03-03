/*******************************************************************************
 * Copyright (c) 2010 Tom Seidel, Remus Software
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
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Composite;

import org.remus.infomngmnt.ui.collapsiblebutton.CollapsibleButtonBar;
import org.remus.infomngmnt.ui.remote.view.RemoteRepositoryViewer;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class RemoteRepositoryButtonBar extends CollapsibleButtonBar implements IViewerProvider,
		ISelectionProvider {

	private final RemoteRepositoryViewer remoteRepositoryViewer;

	public RemoteRepositoryButtonBar() {
		this.remoteRepositoryViewer = new RemoteRepositoryViewer();
	}

	@Override
	public void createControl(final Composite parent) {
		this.remoteRepositoryViewer.createControl(parent, getViewSite());
		setControl(this.remoteRepositoryViewer.getTopControl());
	}

	public Viewer getViewer() {
		return this.remoteRepositoryViewer.getViewer();
	}

	public void addSelectionChangedListener(final ISelectionChangedListener listener) {
		this.remoteRepositoryViewer.addSelectionChangedListener(listener);

	}

	public ISelection getSelection() {
		return this.remoteRepositoryViewer.getSelection();
	}

	public void removeSelectionChangedListener(final ISelectionChangedListener listener) {
		this.remoteRepositoryViewer.removeSelectionChangedListener(listener);

	}

	public void setSelection(final ISelection selection) {
		this.remoteRepositoryViewer.setSelection(selection);
	}

	@Override
	public boolean setFocus() {
		return this.remoteRepositoryViewer.setFocus();
	}

}
