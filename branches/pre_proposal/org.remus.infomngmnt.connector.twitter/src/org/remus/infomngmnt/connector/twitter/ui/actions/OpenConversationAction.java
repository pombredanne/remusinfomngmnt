/*******************************************************************************
 * Copyright (c) 2010 Tom Seidel, Remus Software
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

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;

import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.common.ui.UIUtil;
import org.remus.infomngmnt.common.ui.image.ResourceManager;
import org.remus.infomngmnt.connector.twitter.TwitterActivator;
import org.remus.infomngmnt.connector.twitter.infotype.TwitterUtil;
import org.remus.infomngmnt.connector.twitter.ui.ConversationPopup;
import org.remus.infomngmnt.core.progress.CancelableRunnable;
import org.remus.infomngmnt.core.sync.SyncUtil;
import org.remus.infomngmnt.util.StatusCreator;

import twitter4j.Status;
import twitter4j.Twitter;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class OpenConversationAction extends Action {
	private final String repositoryId;
	private final String userId;
	private final String replyId;
	private final InformationUnit informationUnit;

	public OpenConversationAction(final String userId, final String replyId,
			final InformationUnit informationUnit) {
		super("Open conversation action");
		this.userId = userId;
		this.replyId = replyId;
		this.informationUnit = informationUnit;
		this.repositoryId = SyncUtil.getRepositoryId(informationUnit);
		setImageDescriptor(ResourceManager.getPluginImageDescriptor(TwitterActivator.getDefault(),
				"icons/iconexperience/user_into.png"));
	}

	@Override
	public void run() {
		final List<InformationUnit> messages = new ArrayList<InformationUnit>();
		CancelableRunnable cancelableRunnable = new CancelableRunnable() {

			@Override
			protected IStatus runCancelableRunnable(final IProgressMonitor monitor) {
				monitor.beginTask("Searching for replies", IProgressMonitor.UNKNOWN);
				Twitter twitterApi = TwitterUtil
						.getTwitterApi(OpenConversationAction.this.repositoryId);
				try {
					Status showStatus = twitterApi.showStatus(Long
							.valueOf(OpenConversationAction.this.replyId));
					messages.add(TwitterUtil.buildMessage(showStatus));
					while (showStatus.getInReplyToScreenName() != null
							&& showStatus.getInReplyToScreenName().length() > 0) {
						showStatus = twitterApi.showStatus(showStatus.getInReplyToStatusId());
						messages.add(TwitterUtil.buildMessage(showStatus));
					}
				} catch (Exception e) {
					return StatusCreator.newStatus("Error loading conversation", e);
				}
				return org.eclipse.core.runtime.Status.OK_STATUS;
			}
		};
		ProgressMonitorDialog progressMonitorDialog = new ProgressMonitorDialog(UIUtil.getDisplay()
				.getActiveShell());
		try {
			progressMonitorDialog.run(true, true, cancelableRunnable);
			ConversationPopup popup = new ConversationPopup(UIUtil.getDisplay().getActiveShell(),
					messages, this.informationUnit);
			popup.open();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
