/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.remus.infomngmnt.ui.remote.deferred;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.progress.DeferredTreeContentManager;

public class DeferredContentProvider extends AdapterFactoryContentProvider {

	private final IWorkbenchPartSite partSite;

	public DeferredContentProvider(final AdapterFactory adapterFactory, final IWorkbenchPartSite partSite) {
		super(adapterFactory);
		this.partSite = partSite;
	}

	private DeferredTreeContentManager manager;
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	@Override
	public void inputChanged(final Viewer viewer, final Object oldInput, final Object newInput) {
		if (viewer instanceof AbstractTreeViewer) {
			this.manager = new DeferredTreeContentManager((AbstractTreeViewer) viewer, this.partSite);
		}
		super.inputChanged(viewer, oldInput, newInput);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
	 */
	@Override
	public boolean hasChildren(final Object element) {
		// the + box will always appear, but then disappear
		// if not needed after you first click on it.
		if (this.manager != null) {
			if (this.manager.isDeferredAdapter(element)) {
				return this.manager.mayHaveChildren(element);
			}
		}
		return super.hasChildren(element);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.model.WorkbenchContentProvider#getChildren(java.lang.Object)
	 */
	@Override
	public Object[] getChildren(final Object element) {
		if (this.manager != null) {
			Object[] children = this.manager.getChildren(element);
			if(children != null) {
				return children;
			}
		}
		return super.getChildren(element);
	}
}
