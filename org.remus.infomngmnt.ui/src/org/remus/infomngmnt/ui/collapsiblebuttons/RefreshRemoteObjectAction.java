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

package org.remus.infomngmnt.ui.collapsiblebuttons;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.ui.actions.BaseSelectionListenerAction;

import org.remus.infomngmnt.RemoteContainer;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class RefreshRemoteObjectAction extends BaseSelectionListenerAction {

	private StructuredViewer viewer;

	public void setViewer(final StructuredViewer viewer) {
		this.viewer = viewer;
	}

	protected RefreshRemoteObjectAction() {
		super("Refresh");
	}
	
	@Override
	public void run() {
		Object[] array = getStructuredSelection().toArray();
		for (Object object : array) {
			if (object instanceof RemoteContainer) {
				((RemoteContainer) object).getChildren().clear();
			}
			if (this.viewer != null) {
				this.viewer.refresh(object);
			}
		}
	}
	
	@Override
	protected boolean updateSelection(final IStructuredSelection selection) {
		return !selection.isEmpty();
	}

}
