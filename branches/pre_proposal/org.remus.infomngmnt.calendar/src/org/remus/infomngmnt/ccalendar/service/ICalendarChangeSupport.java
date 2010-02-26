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

package org.remus.infomngmnt.ccalendar.service;

import org.aspencloud.calypso.util.TimeSpan;

/**
 * <p>
 * Interface that defines a component which notifies the calendar component for
 * changed events in given {@link TimeSpan}s.
 * </p>
 * 
 * <p>
 * Clients have to provide an implementation. The calendar component is
 * registering to the given implementation and will listen for events that are
 * fired.
 * </p>
 * 
 * @author Tom Seidel <tom.seidel@remus-software.org>
 * @since 1.0
 * @noextend This interface is not intended to be extended by clients.
 */
public interface ICalendarChangeSupport {

	/**
	 * Adds a listener to the given implementation.
	 * 
	 * @param listener
	 *            the listener to add.
	 */
	void addTimeSpanListener(IDirtyTimespanListener listener);

	/**
	 * Removes a listener from the given implementation
	 * 
	 * @param listener
	 *            the listener to remove.
	 */
	void removeTimeSpanListener(IDirtyTimespanListener listener);

}
