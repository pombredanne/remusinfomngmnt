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
import org.eclipse.remus.common.core.operation.CancelableJob;
import org.eclipse.remus.util.StatusCreator;

import org.remus.infomngmnt.connector.twitter.Messages;
import org.remus.infomngmnt.connector.twitter.infotype.TwitterUtil;

import twitter4j.Twitter;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class UnFollowUserJob extends CancelableJob {

	private final String repositryId;
	private final String userId;

	public UnFollowUserJob(final String userId, final String repositryId) {
		super(NLS.bind(Messages.UnFollowUserJob_RemovingFollower, userId));
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
			monitor.beginTask(Messages.UnFollowUserJob_RemovingFollower2, IProgressMonitor.UNKNOWN);
			Twitter twitterApi = TwitterUtil.getTwitterApi(this.repositryId);
			twitterApi.disableNotification(this.userId);
		} catch (Exception e) {
			return StatusCreator.newStatus(NLS.bind(Messages.UnFollowUserJob_ErrorRemovingFollower,
					this.userId), e);
		}
		return Status.OK_STATUS;
	}

}
