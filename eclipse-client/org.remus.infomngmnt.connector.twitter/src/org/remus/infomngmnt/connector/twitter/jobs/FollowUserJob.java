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

package org.remus.infomngmnt.connector.twitter.jobs;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.osgi.util.NLS;

import org.remus.infomngmnt.common.core.operation.CancelableJob;
import org.remus.infomngmnt.connector.twitter.infotype.TwitterUtil;
import org.remus.infomngmnt.util.StatusCreator;

import twitter4j.Twitter;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class FollowUserJob extends CancelableJob {

	private final String repositryId;
	private final String userId;

	public FollowUserJob(final String userId, final String repositryId) {
		super(NLS.bind("Adding \"{0}\" to followers", userId));
		this.userId = userId;
		this.repositryId = repositryId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.core.progress.CancelableJob#runCancelable(org.eclipse
	 * .core.runtime.IProgressMonitor)
	 */
	@Override
	protected IStatus runCancelable(final IProgressMonitor monitor) {
		try {
			monitor.beginTask("Adding follower", IProgressMonitor.UNKNOWN);
			Twitter twitterApi = TwitterUtil.getTwitterApi(this.repositryId);
			twitterApi.enableNotification(this.userId);
		} catch (Exception e) {
			return StatusCreator.newStatus(NLS.bind("Error adding \"{0}\" to followers",
					this.userId), e);
		}
		return Status.OK_STATUS;
	}

}
