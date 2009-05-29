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

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.actions.BaseSelectionListenerAction;

import org.remus.infomngmnt.ui.remote.IRepositoryActionContributor;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class YoutubeActionContributor implements IRepositoryActionContributor {

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.remus.infomngmnt.ui.remote.IRepositoryActionContributor#
	 * contributeCheckOutAsActions(org.eclipse.jface.action.MenuManager,
	 * org.eclipse.jface.viewers.IStructuredSelection)
	 */
	public BaseSelectionListenerAction[] createCheckOutAsActions(
			final IStructuredSelection selection) {
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
