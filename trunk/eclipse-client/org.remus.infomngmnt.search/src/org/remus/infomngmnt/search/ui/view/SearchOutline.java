/*******************************************************************************
 * Copyright (c) 2008 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/

package org.remus.infomngmnt.search.ui.view;

import org.eclipse.ui.views.contentoutline.ContentOutlinePage;

import org.remus.infomngmnt.search.Search;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class SearchOutline extends ContentOutlinePage {

	private final Search model;

	public SearchOutline(Search model) {
		this.model = model;
	}

}
