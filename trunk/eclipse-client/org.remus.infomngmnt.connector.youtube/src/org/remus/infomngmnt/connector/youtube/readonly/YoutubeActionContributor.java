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

package org.remus.infomngmnt.connector.youtube.readonly;

import java.util.List;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.actions.BaseSelectionListenerAction;

import org.remus.infomngmnt.RemoteContainer;
import org.remus.infomngmnt.RemoteRepository;
import org.remus.infomngmnt.connector.youtube.actions.CheckoutFeedAction;
import org.remus.infomngmnt.ui.remote.IRepositoryActionContributor;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class YoutubeActionContributor implements IRepositoryActionContributor {

	private CheckoutFeedAction feedAction;

	private BaseSelectionListenerAction getCheckOutFeedAction() {
		if (this.feedAction == null) {
			this.feedAction = new CheckoutFeedAction();
		}
		return this.feedAction;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.remus.infomngmnt.ui.remote.IRepositoryActionContributor#
	 * contributeCheckOutAsActions(org.eclipse.jface.action.MenuManager,
	 * org.eclipse.jface.viewers.IStructuredSelection)
	 */
	public BaseSelectionListenerAction[] createCheckOutAsActions(
			final IStructuredSelection selection) {
		List list = selection.toList();
		for (Object object : list) {
			if (object instanceof RemoteContainer && !(object instanceof RemoteRepository)) {
				/*
				 * if all selected elements are tag-items, we can perform a
				 * checkout for creating new categories with the links wrapped.
				 */
				BaseSelectionListenerAction checkOutFeedAction = getCheckOutFeedAction();
				checkOutFeedAction.selectionChanged(selection);
				return new BaseSelectionListenerAction[] { checkOutFeedAction };
			}

		}
		return new BaseSelectionListenerAction[0];

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.remus.infomngmnt.ui.remote.IRepositoryActionContributor#
	 * contributeShareActions(org.eclipse.jface.action.MenuManager,
	 * org.eclipse.jface.viewers.IStructuredSelection)
	 */
	public void contributeShareActions(final MenuManager menu, final IStructuredSelection selection) {
		// TODO Auto-generated method stub

	}

}
