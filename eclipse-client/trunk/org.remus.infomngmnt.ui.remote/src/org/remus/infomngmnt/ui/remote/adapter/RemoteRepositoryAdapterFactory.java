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

package org.remus.infomngmnt.ui.remote.adapter;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.ui.model.IWorkbenchAdapter;
import org.eclipse.ui.progress.IDeferredWorkbenchAdapter;

import org.remus.infomngmnt.RemoteObject;
import org.remus.infomngmnt.RemoteRepository;
import org.remus.infomngmnt.ui.remote.deferred.RemoteRepositoryDeferredAdapter;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class RemoteRepositoryAdapterFactory implements IAdapterFactory {

	
	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.IAdapterFactory#getAdapter(java.lang.Object, java.lang.Class)
	 */
	public Object getAdapter(final Object object, final Class type) {
		if(object instanceof RemoteObject) {
			if(type == IDeferredWorkbenchAdapter.class || type == IWorkbenchAdapter.class) {
				return new RemoteRepositoryDeferredAdapter((RemoteObject) object);
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.IAdapterFactory#getAdapterList()
	 */
	public Class[] getAdapterList() {
		return new Class[] {
				RemoteRepository.class, IDeferredWorkbenchAdapter.class, IWorkbenchAdapter.class
				};
	}

}
