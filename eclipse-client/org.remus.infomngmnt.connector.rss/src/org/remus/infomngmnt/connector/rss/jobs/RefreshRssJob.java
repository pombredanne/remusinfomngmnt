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

package org.remus.infomngmnt.connector.rss.jobs;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;

import org.remus.infomngmnt.Notification;
import org.remus.infomngmnt.RemoteRepository;
import org.remus.infomngmnt.connector.rss.RssActivator;
import org.remus.infomngmnt.core.jobs.AbstractJob;
import org.remus.infomngmnt.core.services.IRepositoryService;
import org.remus.infomngmnt.ui.UIPlugin;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class RefreshRssJob extends AbstractJob {

	/**
	 * 
	 */
	public RefreshRssJob() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.core.jobs.AbstractJob#run(org.eclipse.core.runtime
	 * .IProgressMonitor)
	 */
	@Override
	public List<Notification> run(final IProgressMonitor monitor) {
		EList<RemoteRepository> repositories = UIPlugin.getDefault().getService(
				IRepositoryService.class).getRepositories().getRepositories();
		for (RemoteRepository remoteRepository : repositories) {
			if (RssActivator.REPOSITORY_ID.equals(remoteRepository.getRepositoryTypeId())) {

			}
		}
		return null;
	}

	@Override
	public int getInterval() {
		return 1;
	}

}
