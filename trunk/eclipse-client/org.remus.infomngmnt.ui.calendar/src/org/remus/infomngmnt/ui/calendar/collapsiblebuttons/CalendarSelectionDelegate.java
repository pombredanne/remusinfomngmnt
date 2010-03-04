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

package org.remus.infomngmnt.ui.calendar.collapsiblebuttons;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.nebula.widgets.calendarcombo.CalendarListenerAdapter;

import org.remus.infomngmnt.ui.widgets.CalendarComposite;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class CalendarSelectionDelegate extends CalendarListenerAdapter implements
		ISelectionProvider {

	List<ISelectionChangedListener> listenerList;

	private Date currentDate;

	private final CalendarComposite calendarComposite;

	public CalendarSelectionDelegate(final CalendarComposite calendarComposite) {
		this.calendarComposite = calendarComposite;
		calendarComposite.addCalendarListener(this);
		// this.currentDate = calendarComposite.getSelectedDay().getTime();
		this.listenerList = new ArrayList<ISelectionChangedListener>();
	}

	public void dispose() {
		this.calendarComposite.removeCalendarListener(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.nebula.widgets.calendarcombo.ICalendarListener#dateChanged
	 * (java.util.Calendar)
	 */
	@Override
	public void dateChanged(final Calendar date) {
		this.currentDate = date.getTime();
		SelectionChangedEvent selectionChangedEvent = new SelectionChangedEvent(this,
				new StructuredSelection(this.currentDate));
		for (ISelectionChangedListener listener : this.listenerList) {
			listener.selectionChanged(selectionChangedEvent);
		}
	}

	public void addSelectionChangedListener(final ISelectionChangedListener listener) {
		this.listenerList.add(listener);
	}

	public ISelection getSelection() {
		if (this.currentDate == null) {
			return StructuredSelection.EMPTY;
		}
		return new StructuredSelection(this.currentDate);
	}

	public void removeSelectionChangedListener(final ISelectionChangedListener listener) {
		this.listenerList.remove(listener);
	}

	public void setSelection(final ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			Object firstElement = ((IStructuredSelection) selection).getFirstElement();
			Calendar calendar = null;
			if (firstElement instanceof Calendar) {
				calendar = (Calendar) firstElement;
			}
			if (firstElement instanceof Date) {
				calendar = Calendar.getInstance();
				calendar.setTime((Date) firstElement);
			}
			if (calendar != null) {
				this.calendarComposite.setDate(calendar);

			}
		}
	}

}
