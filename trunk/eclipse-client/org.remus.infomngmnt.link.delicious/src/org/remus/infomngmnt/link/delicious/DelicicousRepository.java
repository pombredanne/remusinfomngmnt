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

package org.remus.infomngmnt.link.delicious;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.RemoteContainer;
import org.remus.infomngmnt.RemoteObject;
import org.remus.infomngmnt.core.extension.AbstractExtensionRepository;
import org.remus.infomngmnt.core.model.StatusCreator;
import org.remus.infomngmnt.core.remote.ILoginCallBack;

import del.icio.us.Delicious;
import del.icio.us.DeliciousConstants;
import del.icio.us.beans.Bundle;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class DelicicousRepository extends AbstractExtensionRepository {
	
	private Delicious api;
	
	private final PropertyChangeListener credentialsMovedListener = new PropertyChangeListener() {
		public void propertyChange(final PropertyChangeEvent evt) {
			reset();
		}
	};

	/* (non-Javadoc)
	 * @see org.remus.infomngmnt.core.remote.IRepository#getChildren()
	 */
	public RemoteObject[] getChildren(final IProgressMonitor monitor) {
		IProgressMonitor sub = new SubProgressMonitor(monitor, IProgressMonitor.UNKNOWN);
		List<Bundle> bundles = getApi().getBundles();
		List<RemoteObject> returnValue = new LinkedList<RemoteObject>();
		for (Bundle bundle : bundles) {
			RemoteContainer remoteContainer = InfomngmntFactory.eINSTANCE.createRemoteContainer();
			//remoteContainer.setId(bundle.getTags());
			//remoteContainer.setTypeId("LINK");
			returnValue.add(remoteContainer);
		}
		return returnValue.toArray(new RemoteObject[returnValue.size()]);
	}

	/* (non-Javadoc)
	 * @see org.remus.infomngmnt.core.remote.IRepository#getRepositoryUrl()
	 */
	public String getRepositoryUrl() {
		return DeliciousConstants.API_ENDPOINT;
	}

	
	
	private Delicious getApi() {
		if (this.api == null) {
			this.api = new Delicious(
					getCredentialProvider().getUserName(),
					getCredentialProvider().getPassword());
			getCredentialProvider().addPropertyChangeListener(this.credentialsMovedListener);
		}
		return this.api;
	}

	public IStatus validate() {
		try {
			getApi().getRecentPosts();
			return Status.OK_STATUS;
		} catch (Exception e) {
			return StatusCreator.newStatus("Error connecting...",e);
		}
	}

	public void login(final ILoginCallBack callback, final IProgressMonitor monitor) {
		// TODO Auto-generated method stub
		
	}

	public void reset() {
		this.api = null;
		getCredentialProvider().removePropertyChangeListener(this.credentialsMovedListener);
	}

}
