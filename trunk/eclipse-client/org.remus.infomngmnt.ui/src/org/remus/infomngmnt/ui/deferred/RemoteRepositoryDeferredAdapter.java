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

package org.remus.infomngmnt.ui.deferred;

import java.util.Arrays;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.progress.IDeferredWorkbenchAdapter;
import org.eclipse.ui.progress.IElementCollector;

import org.remus.infomngmnt.RemoteContainer;
import org.remus.infomngmnt.RemoteObject;
import org.remus.infomngmnt.core.extension.AbstractExtensionRepository;
import org.remus.infomngmnt.core.services.IRepositoryExtensionService;
import org.remus.infomngmnt.ui.UIPlugin;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class RemoteRepositoryDeferredAdapter implements
		IDeferredWorkbenchAdapter {
	
	private final RemoteObject remoteObject;
	private final AbstractExtensionRepository itemById;

	public RemoteRepositoryDeferredAdapter(final RemoteObject object) {
		this.remoteObject = object;
		this.itemById = UIPlugin.getDefault().getService(IRepositoryExtensionService.class).getItemById(object.getRepositoryTypeId());
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.progress.IDeferredWorkbenchAdapter#fetchDeferredChildren(java.lang.Object, org.eclipse.ui.progress.IElementCollector, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void fetchDeferredChildren(final Object object,
			final IElementCollector collector, final IProgressMonitor monitor) {
		if (this.remoteObject instanceof RemoteContainer) {
			RemoteObject[] children = this.itemById.getChildren(monitor, (RemoteContainer) this.remoteObject);
			List<RemoteObject> asList = Arrays.asList(children);
			for (RemoteObject remoteObject : asList) {
				remoteObject.setRepositoryTypeId(this.remoteObject.getRepositoryTypeId());
			}
			((RemoteContainer) this.remoteObject).getChildren().addAll(asList);
			collector.add(children,monitor);
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.progress.IDeferredWorkbenchAdapter#getRule(java.lang.Object)
	 */
	public ISchedulingRule getRule(final Object object) {
		return this.itemById.getRule();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.progress.IDeferredWorkbenchAdapter#isContainer()
	 */
	public boolean isContainer() {
		return this.remoteObject instanceof RemoteObject;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.model.IWorkbenchAdapter#getChildren(java.lang.Object)
	 */
	public Object[] getChildren(final Object o) {
		if (isContainer()) {
			return ((RemoteContainer) this.remoteObject).getChildren().toArray();
		}
		return new Object[0];
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.model.IWorkbenchAdapter#getImageDescriptor(java.lang.Object)
	 */
	public ImageDescriptor getImageDescriptor(final Object object) {
		return ImageDescriptor.createFromImage(this.itemById.getImage());
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.model.IWorkbenchAdapter#getLabel(java.lang.Object)
	 */
	public String getLabel(final Object o) {
		return this.remoteObject.getName();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.model.IWorkbenchAdapter#getParent(java.lang.Object)
	 */
	public Object getParent(final Object o) {
		return this.remoteObject.eContainer();
	}

}
