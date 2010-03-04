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

package org.remus.infomngmnt.search.ui.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import org.remus.infomngmnt.search.ui.SearchUIActivator;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class NextSearchResultHandler extends AbstractHandler {

	public Object execute(final ExecutionEvent event) throws ExecutionException {

		SearchUIActivator.getDefault().getSearchContext().openNextResult();
		return null;
	}

}
