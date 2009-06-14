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

package org.remus.infomngmnt.connector.twitter.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.IDialogConstants;

import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.common.ui.UIUtil;
import org.remus.infomngmnt.connector.twitter.jobs.SendMessageJob;
import org.remus.infomngmnt.connector.twitter.ui.TweetDialog;
import org.remus.infomngmnt.ui.handlerutil.InformationHandlerUtil;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class OpenTweetDialog extends AbstractHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.
	 * ExecutionEvent)
	 */
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		InformationUnit unit = InformationHandlerUtil.getInformationUnitFromExecutionEvent(event);
		InformationUnitListItem adapter = (InformationUnitListItem) unit
				.getAdapter(InformationUnitListItem.class);
		TweetDialog tweetDialog = new TweetDialog(UIUtil.getDisplay().getActiveShell(), null);
		if (tweetDialog.open() == IDialogConstants.OK_ID) {
			Job sendMessageJob = new SendMessageJob(tweetDialog.getMessage(), adapter
					.getSynchronizationMetaData().getRepositoryId());
			sendMessageJob.setUser(true);
			sendMessageJob.schedule();
		}
		return null;
	}
}
