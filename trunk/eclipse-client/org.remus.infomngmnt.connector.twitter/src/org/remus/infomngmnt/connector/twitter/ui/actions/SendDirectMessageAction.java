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

import org.remus.infomngmnt.common.core.util.StringUtils;
import org.remus.infomngmnt.common.ui.UIUtil;
import org.remus.infomngmnt.common.ui.image.ResourceManager;
import org.remus.infomngmnt.connector.twitter.TwitterActivator;
import org.remus.infomngmnt.connector.twitter.jobs.SendMessageJob;
import org.remus.infomngmnt.connector.twitter.ui.TweetDialog;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class SendDirectMessageAction extends Action {

	private final String id2;
	private final String repositoryId;

	public SendDirectMessageAction(final String userId, final String repositoryId) {
		super("Send direct message");
		this.id2 = userId;
		this.repositoryId = repositoryId;
		setImageDescriptor(ResourceManager.getPluginImageDescriptor(TwitterActivator.getDefault(),
				"icons/iconexperience/user_into.png"));
	}

	@Override
	public void run() {
		TweetDialog dialog = new TweetDialog(UIUtil.getDisplay().getActiveShell(), StringUtils
				.join(this.id2, " "));
		if (dialog.open() == IDialogConstants.OK_ID) {
			SendMessageJob job = new SendMessageJob(dialog.getMessage(), this.repositoryId, null,
					this.id2);
			job.setUser(true);
			job.schedule();
		}
		super.run();
	}

}
