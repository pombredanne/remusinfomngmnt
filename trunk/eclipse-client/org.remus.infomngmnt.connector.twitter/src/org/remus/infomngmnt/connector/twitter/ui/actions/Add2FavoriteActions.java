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

package org.remus.infomngmnt.connector.twitter.ui.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.remus.common.ui.image.ResourceManager;

import org.remus.infomngmnt.connector.twitter.Messages;
import org.remus.infomngmnt.connector.twitter.TwitterActivator;
import org.remus.infomngmnt.connector.twitter.jobs.AddFavoriteJob;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class Add2FavoriteActions extends Action {

	private final Long internalId;

	private final String repositoryId;

	public Add2FavoriteActions(final Long internalId, final String repositoryId) {
		super(Messages.Add2FavoriteActions_AddToFavorites);
		setImageDescriptor(ResourceManager.getPluginImageDescriptor(TwitterActivator.getDefault(),
				"icons/iconexperience/star_yellow_add.png")); //$NON-NLS-1$
		this.internalId = internalId;
		this.repositoryId = repositoryId;
	}

	@Override
	public void run() {
		AddFavoriteJob addFavoriteJob = new AddFavoriteJob(this.internalId, this.repositoryId);
		addFavoriteJob.setUser(true);
		addFavoriteJob.schedule();
	}

}
