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
import org.eclipse.remus.common.core.operation.CancelableJob;
import org.eclipse.remus.util.StatusCreator;

import org.remus.infomngmnt.connector.twitter.Messages;
import org.remus.infomngmnt.connector.twitter.infotype.TwitterUtil;

import twitter4j.Twitter;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class AddFavoriteJob extends CancelableJob {

	private final Long messageId;
	private final String repositoryId;

	public AddFavoriteJob(final Long messageId, final String repositoryId) {
		super(Messages.AddFavoriteJob_AddToFavorites);
		this.messageId = messageId;
		this.repositoryId = repositoryId;
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
			Twitter twitterApi = TwitterUtil.getTwitterApi(this.repositoryId);
			twitterApi.createFavorite(this.messageId);
		} catch (Exception e) {
			return StatusCreator.newStatus(Messages.AddFavoriteJob_ErrorAddingFavorites, e);
		}
		return Status.OK_STATUS;
	}

}
