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

package org.remus.infomngmnt.ui.handler;

import java.util.Collections;
import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.SynchronizableObject;
import org.remus.infomngmnt.SynchronizationState;
import org.remus.infomngmnt.core.commands.CommandFactory;
import org.remus.infomngmnt.util.CategoryUtil;
import org.remus.infomngmnt.util.EditingUtil;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class DisonnectRespositoryHandler extends AbstractRemoteHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.
	 * ExecutionEvent)
	 */
	@Override
	public Object doExecute(final ExecutionEvent event) throws ExecutionException {
		ISelection currentSelectionChecked = HandlerUtil.getCurrentSelectionChecked(event);
		if (currentSelectionChecked instanceof IStructuredSelection) {
			List<Category> list = ((IStructuredSelection) currentSelectionChecked).toList();
			for (Category object : list) {
				EObject[] children = CategoryUtil.getAllChildren(object,
						InfomngmntPackage.Literals.INFORMATION_UNIT_LIST_ITEM);
				for (EObject informationUnitListItem : children) {
					if (((SynchronizableObject) informationUnitListItem)
							.getSynchronizationMetaData().getSyncState() == SynchronizationState.LOCAL_DELETED) {
						CommandFactory
								.DELETE_INFOUNIT_WITHOUT_SYNC_CHECK(
										Collections
												.singletonList((InformationUnitListItem) informationUnitListItem),
										EditingUtil.getInstance().getNavigationEditingDomain())
								.execute();
					} else {
						informationUnitListItem
								.eUnset(InfomngmntPackage.Literals.SYNCHRONIZABLE_OBJECT__SYNCHRONIZATION_META_DATA);
					}
				}
				EObject[] catChildren = CategoryUtil.getAllChildren(object,
						InfomngmntPackage.Literals.CATEGORY);
				for (EObject category : catChildren) {
					if (((SynchronizableObject) category).getSynchronizationMetaData()
							.getSyncState() == SynchronizationState.LOCAL_DELETED) {
						CommandFactory.DELETE_CATEGORY((Category) category,
								EditingUtil.getInstance().getNavigationEditingDomain()).execute();
					}
					category
							.eUnset(InfomngmntPackage.Literals.SYNCHRONIZABLE_OBJECT__SYNCHRONIZATION_META_DATA);
				}
				object
						.eUnset(InfomngmntPackage.Literals.SYNCHRONIZABLE_OBJECT__SYNCHRONIZATION_META_DATA);
			}
		}
		return null;
	}

}
