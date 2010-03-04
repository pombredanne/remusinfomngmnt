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

package org.remus.infomngmnt.search.internal;

import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

import org.remus.infomngmnt.search.service.IFavoriteSearchHandler;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class FavoriteHandleTracker extends ServiceTracker {

	public FavoriteHandleTracker(final BundleContext context) {
		super(context, IFavoriteSearchHandler.class.getName(), null);
	}

}
