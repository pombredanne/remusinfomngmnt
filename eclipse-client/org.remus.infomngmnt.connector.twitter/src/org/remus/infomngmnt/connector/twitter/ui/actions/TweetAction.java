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
import org.eclipse.remus.common.ui.UIUtil;

import org.remus.infomngmnt.connector.twitter.Messages;
import org.remus.infomngmnt.connector.twitter.jobs.SendMessageJob;
import org.remus.infomngmnt.connector.twitter.ui.TweetDialog;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class TweetAction extends Action {

	private final String repositoryId;

	public TweetAction(final String repositoryId) {
		this.repositoryId = repositoryId;
		setText(Messages.TweetAction_TweetNewMessage);
		setToolTipText(Messages.TweetAction_TweetNewMessage);

	}

	@Override
	public void run() {
		TweetDialog dialog = new TweetDialog(UIUtil.getDisplay().getActiveShell(), "", //$NON-NLS-1$
				this.repositoryId);
		if (dialog.open() == IDialogConstants.OK_ID) {
			SendMessageJob job = new SendMessageJob(dialog.getMessage(), this.repositoryId);
			job.setUser(true);
			job.schedule();
		}
		super.run();
	}
}
