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

package org.remus.infomngmnt.core.remote.services;

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.core.services.INotificationManagerManager;
import org.remus.infomngmnt.model.remote.IChangeHandler;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public interface ISynchronizationManager {

	void scheduleSynchronization(Category category);

	void scheduleSynchronization(InformationUnitListItem singleItem);

	void setNotificationManager(INotificationManagerManager manger);

	void setChangeHandler(IChangeHandler manger);

}
