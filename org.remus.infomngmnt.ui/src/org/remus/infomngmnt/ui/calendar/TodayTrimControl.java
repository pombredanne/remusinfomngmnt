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

package org.remus.infomngmnt.ui.calendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.aspencloud.calypso.util.TimeSpan;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.ImageHyperlink;

import org.remus.infomngmnt.calendar.model.Task;
import org.remus.infomngmnt.calendar.model.Tasklist;
import org.remus.infomngmnt.ccalendar.service.ICalendarChangeSupport;
import org.remus.infomngmnt.ccalendar.service.IDirtyTimespanListener;
import org.remus.infomngmnt.common.ui.image.ResourceManager;
import org.remus.infomngmnt.ui.UIPlugin;
import org.remus.infomngmnt.ui.service.ICalendarStoreService;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class TodayTrimControl extends Composite implements IDirtyTimespanListener {

	private ImageHyperlink hyperLink;

	public TodayTrimControl(final Composite parent, final int style) {
		super(parent, style);
		init();
	}

	private void init() {
		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		setLayout(layout);
		ICalendarChangeSupport service = UIPlugin.getDefault().getService(
				ICalendarChangeSupport.class);
		service.addTimeSpanListener(this);
		this.hyperLink = new ImageHyperlink(this, SWT.NONE);
		this.hyperLink.setImage(ResourceManager.getPluginImage(UIPlugin.getDefault(),
				"icons/iconexperience/16/calendar.png"));
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.CENTER).grab(true, true).applyTo(
				this.hyperLink);
		this.hyperLink.addMouseTrackListener(new MouseTrackAdapter() {
			@Override
			public void mouseEnter(final MouseEvent e) {
				((ImageHyperlink) e.widget).setUnderlined(true);
			}

			@Override
			public void mouseExit(final MouseEvent e) {
				((ImageHyperlink) e.widget).setUnderlined(false);
			}
		});
		this.hyperLink.addHyperlinkListener(new HyperlinkAdapter() {
			@Override
			public void linkActivated(final HyperlinkEvent e) {
				// do nothhing
			}
		});
		buildHyperLink();

	}

	@Override
	public void setBackground(final Color color) {
		super.setBackground(color);
		this.hyperLink.setBackground(color);
	}

	private void buildHyperLink() {
		if (!this.hyperLink.isDisposed()) {
			ICalendarStoreService storeService = UIPlugin.getDefault().getService(
					ICalendarStoreService.class);
			Tasklist items = storeService.getItems(getTodaytimeSpan());
			List<Task> calendarEntry = new ArrayList<Task>(items.getTasks());
			Collections.sort(calendarEntry, new Comparator<Task>() {
				public int compare(final Task o1, final Task o2) {
					return o1.getStart().getDate().compareTo(o2.getStart().getDate());
				}
			});
			if (calendarEntry.size() == 0) {
				this.hyperLink.setText("Clear");
			} else {
				this.hyperLink.setText(NLS.bind("{0} events today", calendarEntry.size()));
			}
		}

	}

	public void handleDirtyTimeSpan(final TimeSpan timespan) {
		getShell().getDisplay().asyncExec(new Runnable() {
			public void run() {
				buildHyperLink();
			}
		});

	}

	private TimeSpan getTodaytimeSpan() {
		Date now = new Date();
		Calendar startDate = Calendar.getInstance();
		startDate.setTime(now);
		startDate.set(Calendar.HOUR, 0);
		startDate.set(Calendar.MINUTE, 0);
		startDate.set(Calendar.SECOND, 0);
		startDate.set(Calendar.AM_PM, Calendar.AM);

		Calendar endDate = Calendar.getInstance();
		endDate.setTime(now);
		endDate.set(Calendar.HOUR, 11);
		endDate.set(Calendar.MINUTE, 59);
		endDate.set(Calendar.SECOND, 59);
		endDate.set(Calendar.AM_PM, Calendar.PM);
		return new TimeSpan(startDate.getTime(), endDate.getTime());
	}
}
