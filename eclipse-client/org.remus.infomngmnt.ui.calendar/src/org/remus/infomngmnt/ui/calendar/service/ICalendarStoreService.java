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

package org.remus.infomngmnt.ui.calendar.service;

import org.aspencloud.calypso.util.TimeSpan;
import org.eclipse.remus.CalendarEntry;
import org.eclipse.remus.InformationUnit;

import org.remus.infomngmnt.calendar.model.Tasklist;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public interface ICalendarStoreService {

	void removeInfoUnit(final String informationUnitId);

	void update(final InformationUnit unit, CalendarEntry[] calendarContributions);

	Tasklist getItems(final TimeSpan timespan);

}
