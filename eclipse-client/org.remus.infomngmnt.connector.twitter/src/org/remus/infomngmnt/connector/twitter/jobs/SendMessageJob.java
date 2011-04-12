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
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.remus.util.StatusCreator;

import org.remus.infomngmnt.connector.twitter.Messages;
import org.remus.infomngmnt.connector.twitter.infotype.TwitterUtil;

import twitter4j.Twitter;
import twitter4j.TwitterException;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class SendMessageJob extends Job {

	private final String text;
	private final String repositoryId;
	private final Long replyId;
	private final String userId;

	public SendMessageJob(final String text, final String repositoryId) {
		this(text, repositoryId, null, null);
	}

	public SendMessageJob(final String text, final String repositoryId, final String userId) {
		this(text, repositoryId, null, userId);
	}

	public SendMessageJob(final String text, final String repositoryId, final Long replyId,
			final String userId) {
		super(Messages.SendMessageJob_SendingMessage);
		this.text = text;
		this.repositoryId = repositoryId;
		this.replyId = replyId;
		this.userId = userId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.core.runtime.jobs.Job#run(org.eclipse.core.runtime.
	 * IProgressMonitor)
	 */
	@Override
	protected IStatus run(final IProgressMonitor monitor) {
		monitor.beginTask(Messages.SendMessageJob_SendingMessage, IProgressMonitor.UNKNOWN);
		try {
			Twitter twitterApi = TwitterUtil.getTwitterApi(this.repositoryId);
			if (this.replyId != null) {
				twitterApi.updateStatus(this.text, this.replyId);
			} else if (this.userId != null) {
				twitterApi.sendDirectMessage(this.userId, this.text);
			} else {
				twitterApi.updateStatus(this.text);
			}
		} catch (TwitterException e) {
			return StatusCreator.newStatus(Messages.SendMessageJob_ErrorSending, e);
		}
		return Status.OK_STATUS;
	}

}
