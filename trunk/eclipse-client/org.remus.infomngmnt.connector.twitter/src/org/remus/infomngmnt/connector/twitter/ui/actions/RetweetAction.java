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
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.remus.common.core.util.StringUtils;
import org.eclipse.remus.common.ui.UIUtil;
import org.eclipse.remus.common.ui.image.ResourceManager;

import org.remus.infomngmnt.connector.twitter.TwitterActivator;
import org.remus.infomngmnt.connector.twitter.jobs.SendMessageJob;
import org.remus.infomngmnt.connector.twitter.ui.TweetDialog;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class RetweetAction extends Action {

	private final String id2;
	private final String message;
	private final String repositoryId;

	public RetweetAction(final String id, final String message, final String repositoryId) {
		this.id2 = id;
		this.message = message;
		this.repositoryId = repositoryId;
		setText("Retweet");
		setImageDescriptor(ResourceManager.getPluginImageDescriptor(TwitterActivator.getDefault(),
				"icons/iconexperience/nav_right_yellow.png"));
	}

	@Override
	public void run() {
		TweetDialog dialog = new TweetDialog(UIUtil.getDisplay().getActiveShell(), StringUtils
				.join("RT @", this.id2, ": ", this.message), this.repositoryId);
		if (dialog.open() == IDialogConstants.OK_ID) {
			SendMessageJob job = new SendMessageJob(dialog.getMessage(), this.repositoryId);
			job.setUser(true);
			job.schedule();
		}
		super.run();
	}
}
