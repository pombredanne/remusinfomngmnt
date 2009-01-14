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

package org.remus.infomngmnt.link.delicious.ui;

import java.util.List;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.actions.BaseSelectionListenerAction;

import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.RemoteContainer;
import org.remus.infomngmnt.RemoteObject;
import org.remus.infomngmnt.common.core.util.ModelUtil;
import org.remus.infomngmnt.link.delicious.DelicicousRepository;
import org.remus.infomngmnt.link.delicious.actions.CheckoutLinkAction;
import org.remus.infomngmnt.link.delicious.actions.CheckoutTagAction;
import org.remus.infomngmnt.ui.remote.IRepositoryActionContributor;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class DeliciousActionContributor implements IRepositoryActionContributor {


	private CheckoutTagAction tagAction;
	
	private CheckoutLinkAction linkAction;

	private BaseSelectionListenerAction getCheckOutTagAction() {
		if (this.tagAction == null) {
			this.tagAction = new CheckoutTagAction();
		}
		return this.tagAction;
	}
	
	private BaseSelectionListenerAction getCheckOutLinkAction() {
		if (this.linkAction == null) {
			this.linkAction = new CheckoutLinkAction();
		}
		return this.linkAction;
	}

	/* (non-Javadoc)
	 * @see org.remus.infomngmnt.ui.remote.IRepositoryActionContributor#contributeCheckOutAsActions(org.eclipse.jface.action.MenuManager, org.eclipse.jface.viewers.IStructuredSelection)
	 */
	public BaseSelectionListenerAction[] createCheckOutAsActions(final IStructuredSelection selection) {
		List list = selection.toList();
		for (Object object : list) {
			if (object instanceof RemoteContainer) {
				/*
				 * if all selected elements are tag-items, we can perform a checkout
				 * for creating new categories with the links wrapped.
				 */
				if (DelicicousRepository.KEY_TAG.equals(((RemoteObject) object).getRepositoryTypeObjectId())
						&& ModelUtil.hasEqualAttribute(list, InfomngmntPackage.Literals.REMOTE_OBJECT__REPOSITORY_TYPE_OBJECT_ID)) {
					BaseSelectionListenerAction checkOutTagAction = getCheckOutTagAction();
					checkOutTagAction.selectionChanged(selection);
					return new BaseSelectionListenerAction[] {checkOutTagAction};
				} 
				/*
				 * TODO implement the case where the user tries to checkout
				 * the whole repository
				 */	
			}
		}
		return new BaseSelectionListenerAction[0];

	}

	/* (non-Javadoc)
	 * @see org.remus.infomngmnt.ui.remote.IRepositoryActionContributor#contributeShareActions(org.eclipse.jface.action.MenuManager, org.eclipse.jface.viewers.IStructuredSelection)
	 */
	public void contributeShareActions(final MenuManager menu,
			final IStructuredSelection selection) {
		// TODO Auto-generated method stub

	}

	
}
