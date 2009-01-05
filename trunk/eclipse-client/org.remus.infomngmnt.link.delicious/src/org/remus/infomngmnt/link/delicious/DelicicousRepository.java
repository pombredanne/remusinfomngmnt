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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.core.runtime.jobs.ISchedulingRule;

import del.icio.us.Delicious;
import del.icio.us.DeliciousConstants;
import del.icio.us.beans.Post;
import del.icio.us.beans.Tag;

import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.RemoteContainer;
import org.remus.infomngmnt.RemoteObject;
import org.remus.infomngmnt.RemoteRepository;
import org.remus.infomngmnt.core.extension.AbstractExtensionRepository;
import org.remus.infomngmnt.core.model.StatusCreator;
import org.remus.infomngmnt.core.remote.ILoginCallBack;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class DelicicousRepository extends AbstractExtensionRepository {
	
	private Delicious api;
	
	public static final String KEY_TAG = "KEY_TAG"; //$NON-NLS-1$
	
	public static final String KEY_LINK = "KEY_LINK"; //$NON-NLS-1$
	
	private final PropertyChangeListener credentialsMovedListener = new PropertyChangeListener() {
		public void propertyChange(final PropertyChangeEvent evt) {
			reset();
		}
	};
	
	final ISchedulingRule mutexRule = new ISchedulingRule() {
		public boolean isConflicting(final ISchedulingRule rule) {
			return rule == DelicicousRepository.this.mutexRule;
		}
		public boolean contains(final ISchedulingRule rule) {
			return rule == DelicicousRepository.this.mutexRule;
		}
	};

	

	public RemoteObject[] getChildren(final IProgressMonitor monitor,
			final RemoteContainer container) {
		List<RemoteObject> returnValue = new LinkedList<RemoteObject>();
		if (container instanceof RemoteRepository) {
			IProgressMonitor sub = new SubProgressMonitor(monitor, IProgressMonitor.UNKNOWN);
			List<Tag> tags = getApi().getTags();
			for (Tag tag : tags) {
				RemoteContainer remoteContainer = InfomngmntFactory.eINSTANCE.createRemoteContainer();
				remoteContainer.setId(tag.getTag());
				remoteContainer.setRepositoryTypeObjectId(KEY_TAG);
				remoteContainer.setName(tag.getTag());
				remoteContainer.setUrl(getRepositoryUrl() + tag.getTag());
				returnValue.add(remoteContainer);
			}
			
		} else {
			List<Post> posts = getApi().getAllPosts(container.getName());
			for (Post post : posts) {
				RemoteContainer remoteContainer = InfomngmntFactory.eINSTANCE.createRemoteContainer();
				remoteContainer.setId(post.getHash());
				remoteContainer.setName(post.getDescription());
				remoteContainer.setUrl(getRepositoryUrl() + post.getHash());
				remoteContainer.setRepositoryTypeObjectId(KEY_LINK);
				returnValue.add(remoteContainer);
			}
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
			try {
				getCredentialProvider().setIdentifier(new URL(getRepositoryUrl()).getHost());
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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

	

	public ISchedulingRule getRule() {
		return this.mutexRule;
	}


	

}
