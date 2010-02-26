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

package org.remus.infomngmnt.favoritesearch.service;

import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.PlatformUI;

import org.remus.infomngmnt.common.ui.UIUtil;
import org.remus.infomngmnt.favoritesearch.ui.NewFavoriteSearchWizard;
import org.remus.infomngmnt.search.Search;
import org.remus.infomngmnt.search.service.IFavoriteSearchHandler;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class FavSearchHandler implements IFavoriteSearchHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.search.service.IFavoriteSearchHandler#addToFavorites
	 * (org.remus.infomngmnt.search.Search)
	 */
	public void addToFavorites(final Search search) {
		NewFavoriteSearchWizard newWizard = new NewFavoriteSearchWizard();

		newWizard.init(PlatformUI.getWorkbench(), new StructuredSelection(search));
		WizardDialog dialog = new WizardDialog(UIUtil.getDisplay().getActiveShell(), newWizard);

		dialog.open();
	}

}
