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

package org.remus.infomngmnt.favoritesearch;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.command.Command;

import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.core.extension.AbstractCreationFactory;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class FavoriteSearchCreationFactory extends AbstractCreationFactory {

	@Override
	public InformationUnit createNewObject() {
		InformationUnit newObject = super.createNewObject();
		newObject.setType(FavoriteSearchActivator.TYPE_ID);
		InformationUnit latestSearch = InfomngmntFactory.eINSTANCE.createInformationUnit();
		latestSearch.setType(FavoriteSearchActivator.LATEST_SEARCH_TYPE);
		InformationUnit newElementNode = InfomngmntFactory.eINSTANCE.createInformationUnit();
		newElementNode.setType(FavoriteSearchActivator.NEW_ELEMENTS_TYPE);
		InformationUnit searchResultNode = InfomngmntFactory.eINSTANCE.createInformationUnit();
		searchResultNode.setType(FavoriteSearchActivator.RESULT_NODE);

		newObject.getChildValues().add(latestSearch);
		newObject.getChildValues().add(newElementNode);
		newObject.getChildValues().add(searchResultNode);

		return newObject;
	}

	@Override
	public Command handlePreSaving(final InformationUnit unit, final IProgressMonitor monitor) {
		return null;
	}

}
