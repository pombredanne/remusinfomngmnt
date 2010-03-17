/*******************************************************************************
 * Copyright (c) 2010 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/

package org.remus.infomngmnt.core.services;

import org.remus.infomngmnt.InformationUnitListItem;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public interface ISynchronizationItemCache {

	void update(InformationUnitListItem unit);

	void delete(String informationId);

	public InformationUnitListItem getItemByUrl(String url);

}
