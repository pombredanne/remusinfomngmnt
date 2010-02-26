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

package org.remus.infomngmnt.favoritesearch.ui;

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.common.core.util.StringUtils;
import org.remus.infomngmnt.search.Search;
import org.remus.infomngmnt.ui.newwizards.GeneralPage;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class GeneralFavoriteSearchWizardPage extends GeneralPage {

	private final Search search;

	public GeneralFavoriteSearchWizardPage(final Category category) {
		super(category);
		this.search = null;
	}

	public GeneralFavoriteSearchWizardPage(final Search search) {
		super((Category) null);
		this.search = search;
	}

	@Override
	protected void initDatabinding() {
		if (this.search.getSearchString() != null && this.search.getSearchString().length() > 0) {
			this.unit.setLabel(StringUtils.join("Search for \"", this.search.getSearchString(),
					"\""));
		}
		this.unit.setKeywords("Search");
		super.initDatabinding();
	}

}
