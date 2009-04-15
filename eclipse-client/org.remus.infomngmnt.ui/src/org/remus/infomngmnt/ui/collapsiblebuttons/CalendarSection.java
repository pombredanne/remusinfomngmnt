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

package org.remus.infomngmnt.ui.collapsiblebuttons;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.aspencloud.calypso.util.TimeSpan;
import org.eclipse.nebula.widgets.calendarcombo.ColorCache;
import org.eclipse.nebula.widgets.calendarcombo.DefaultColorManager;
import org.eclipse.nebula.widgets.calendarcombo.DefaultSettings;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.ManagedForm;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.events.IExpansionListener;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

import org.remus.infomngmnt.calendar.model.Task;
import org.remus.infomngmnt.calendar.model.Tasklist;
import org.remus.infomngmnt.ccalendar.service.ICalendarChangeSupport;
import org.remus.infomngmnt.ccalendar.service.IDirtyTimespanListener;
import org.remus.infomngmnt.common.ui.image.ResourceManager;
import org.remus.infomngmnt.common.ui.widgets.CalendarComposite;
import org.remus.infomngmnt.ui.UIPlugin;
import org.remus.infomngmnt.ui.calendar.CalendarEditor;
import org.remus.infomngmnt.ui.calendar.CalendarEditorInput;
import org.remus.infomngmnt.ui.calendar.CalendarEntryUtil;
import org.remus.infomngmnt.ui.extension.CollapsibleButtonBar;
import org.remus.infomngmnt.ui.service.ICalendarStoreService;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class CalendarSection extends CollapsibleButtonBar implements IDirtyTimespanListener {

	private static final int SECTION_STYLE = ExpandableComposite.TITLE_BAR
			| ExpandableComposite.TWISTIE | ExpandableComposite.EXPANDED;
	private CalendarComposite cc;

	private CalendarSelectionDelegate calendarSelectionDelegate;

	private ScrolledForm sform;

	private FormText todayFormText;

	private FormText thisWeekFormText;

	private FormText nextWeekFormText;

	/**
	 * 
	 */
	public CalendarSection() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createControl(final Composite parent) {
		FormToolkit toolkit = new FormToolkit(parent.getDisplay());
		this.sform = toolkit.createScrolledForm(parent);
		this.sform.setLayoutData(new GridData(GridData.FILL_BOTH));
		ManagedForm managedForm = new ManagedForm(toolkit, this.sform);
		TableWrapLayout layout = new TableWrapLayout();
		layout.leftMargin = 10;
		layout.rightMargin = 10;
		this.sform.getBody().setLayout(layout);

		ICalendarChangeSupport service = UIPlugin.getDefault().getService(
				ICalendarChangeSupport.class);
		service.addTimeSpanListener(this);

		createCalendarSection();
		createTodaySection();
		createThisWeekSection();
		createNextWeekSection();

		setControl(this.sform);

	}

	private void createNextWeekSection() {
		final Section nextWeek = getToolkit().createSection(this.sform.getBody(),
				ExpandableComposite.TITLE_BAR | ExpandableComposite.TWISTIE);
		// nextWeek.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
		// false));
		nextWeek.setText("Next week");
		nextWeek.addExpansionListener(new IExpansionListener() {
			public void expansionStateChanged(final ExpansionEvent e) {
				// comp.layout(true);

			}

			public void expansionStateChanging(final ExpansionEvent e) {
				// TODO Auto-generated method stub

			}
		});
		TableWrapData td = new TableWrapData();
		td.align = TableWrapData.FILL;
		td.grabHorizontal = true;
		nextWeek.setLayoutData(td);

		this.nextWeekFormText = getToolkit().createFormText(nextWeek, false);
		nextWeek.setClient(this.nextWeekFormText);
		buildNextWeekList();

	}

	private void createThisWeekSection() {
		final Section thisWeek = getToolkit().createSection(this.sform.getBody(), SECTION_STYLE);
		// thisWeek.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
		// false));
		thisWeek.setText("This week");

		TableWrapData td = new TableWrapData();
		td.align = TableWrapData.FILL;
		td.grabHorizontal = true;
		thisWeek.setLayoutData(td);

		this.thisWeekFormText = getToolkit().createFormText(thisWeek, false);
		thisWeek.setClient(this.thisWeekFormText);
		buildThisWeekList();

	}

	private void createTodaySection() {
		final Section today = getToolkit().createSection(this.sform.getBody(), SECTION_STYLE);
		// today.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		today.setText("Today");

		TableWrapData td = new TableWrapData();
		td.align = TableWrapData.FILL;
		td.grabHorizontal = true;
		today.setLayoutData(td);

		this.todayFormText = getToolkit().createFormText(today, false);
		today.setClient(this.todayFormText);
		buildTodayList();

	}

	private void createCalendarSection() {

		Composite calendarComposite = getToolkit()
				.createComposite(this.sform.getBody(), SWT.BORDER);
		TableWrapData td = new TableWrapData();
		td.align = TableWrapData.CENTER;
		td.grabHorizontal = true;
		calendarComposite.setLayoutData(td);
		GridLayout gridLayout = new GridLayout();
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		calendarComposite.setLayout(gridLayout);

		DefaultSettings settings = new DefaultSettings();
		DefaultColorManager colorManager = new DefaultColorManager() {
			@Override
			public Color getCalendarBorderColor() {
				return getViewSite().getShell().getDisplay().getSystemColor(SWT.COLOR_WHITE);
			}

			@Override
			public Color getCalendarBackgroundColor() {
				return ColorCache.getWhite();
			}

		};
		this.cc = new CalendarComposite(calendarComposite, null, null, null, colorManager,
				settings, false, null, null);
		GridData gridData = new GridData(SWT.CENTER, SWT.BEGINNING, true, false);
		gridData.widthHint = settings.getCalendarWidth();
		gridData.heightHint = settings.getCalendarHeight();
		this.cc.setLayoutData(gridData);
		this.cc.setVisible(true);
		this.calendarSelectionDelegate = new CalendarSelectionDelegate(this.cc);

		getToolkit().adapt(this.cc);

	}

	@Override
	public void handleSelect() {
		try {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(
					new CalendarEditorInput(), CalendarEditor.EDITOR_ID);
		} catch (PartInitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getViewSite().setSelectionProvider(this.calendarSelectionDelegate);
		super.handleSelect();
	}

	@Override
	public void dispose() {
		this.calendarSelectionDelegate.dispose();
		super.dispose();
	}

	private void buildTodayList() {
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
			this.todayFormText.setText("<form><p>No Events</p></form>", true, false);
			return;
		}
		this.todayFormText.setImage("alarm", ResourceManager.getPluginImage(UIPlugin.getDefault(),
				"icons/iconexperience/16/alarmclock.png"));
		this.todayFormText.setImage("refresh", ResourceManager.getPluginImage(
				UIPlugin.getDefault(), "icons/iconexperience/16/refresh.png"));
		StringBuilder sw = new StringBuilder();
		sw.append("<form>");
		for (Task entry : calendarEntry) {
			sw.append("<p>");
			// sw.append("<li>");
			sw.append(CalendarEntryUtil.setFormTextRepresentation(entry, false));
			// sw.append("</li>");
			sw.append("</p>");
		}
		sw.append("</form>");
		this.todayFormText.setText(sw.toString(), true, false);
		this.sform.reflow(false);
	}

	private void buildThisWeekList() {
		ICalendarStoreService storeService = UIPlugin.getDefault().getService(
				ICalendarStoreService.class);

		Tasklist items = storeService.getItems(getThisWeekTimeSpan());
		List<Task> calendarEntry = new ArrayList<Task>(items.getTasks());
		Collections.sort(calendarEntry, new Comparator<Task>() {
			public int compare(final Task o1, final Task o2) {
				return o1.getStart().getDate().compareTo(o2.getStart().getDate());
			}
		});
		if (calendarEntry.size() == 0) {
			this.thisWeekFormText.setText("<form><p>No Events</p></form>", true, false);
			return;
		}
		this.thisWeekFormText.setImage("alarm", ResourceManager.getPluginImage(UIPlugin
				.getDefault(), "icons/iconexperience/16/alarmclock.png"));
		this.thisWeekFormText.setImage("refresh", ResourceManager.getPluginImage(UIPlugin
				.getDefault(), "icons/iconexperience/16/refresh.png"));
		StringBuilder sw = new StringBuilder();
		sw.append("<form>");
		for (Task entry : calendarEntry) {
			sw.append("<p>");
			// sw.append("<li>");
			sw.append(CalendarEntryUtil.setFormTextRepresentation(entry, false));
			// sw.append("</li>");
			sw.append("</p>");
		}
		sw.append("</form>");
		this.thisWeekFormText.setText(sw.toString(), true, false);
		this.sform.reflow(false);
	}

	private void buildNextWeekList() {
		ICalendarStoreService storeService = UIPlugin.getDefault().getService(
				ICalendarStoreService.class);

		Tasklist items = storeService.getItems(getNextWeekTimeSpan());
		List<Task> calendarEntry = new ArrayList<Task>(items.getTasks());
		Collections.sort(calendarEntry, new Comparator<Task>() {
			public int compare(final Task o1, final Task o2) {
				return o1.getStart().getDate().compareTo(o2.getStart().getDate());
			}
		});
		if (calendarEntry.size() == 0) {
			this.nextWeekFormText.setText("<form><p>No Events</p></form>", true, false);
			return;
		}
		this.nextWeekFormText.setImage("alarm", ResourceManager.getPluginImage(UIPlugin
				.getDefault(), "icons/iconexperience/16/alarmclock.png"));
		this.nextWeekFormText.setImage("refresh", ResourceManager.getPluginImage(UIPlugin
				.getDefault(), "icons/iconexperience/16/refresh.png"));
		StringBuilder sw = new StringBuilder();
		sw.append("<form>");
		for (Task entry : calendarEntry) {
			sw.append("<p>");
			// sw.append("<li>");
			sw.append(CalendarEntryUtil.setFormTextRepresentation(entry, false));
			// sw.append("</li>");
			sw.append("</p>");
		}
		sw.append("</form>");
		this.nextWeekFormText.setText(sw.toString(), true, false);
		this.sform.reflow(false);
	}

	public void handleDirtyTimeSpan(final TimeSpan timespan) {
		getViewSite().getShell().getDisplay().asyncExec(new Runnable() {
			public void run() {
				TimeSpan todaytimeSpan = getTodaytimeSpan();
				if (timespan.contains(todaytimeSpan) || todaytimeSpan.overlaps(timespan)
						|| todaytimeSpan.contains(timespan)) {
					buildTodayList();
				}
				TimeSpan thisWeekTimeSpan = getThisWeekTimeSpan();
				if (timespan.contains(thisWeekTimeSpan) || thisWeekTimeSpan.overlaps(timespan)) {
					buildThisWeekList();
				}
				TimeSpan nextWeekTimeSpan = getNextWeekTimeSpan();
				if (timespan.contains(nextWeekTimeSpan) || nextWeekTimeSpan.overlaps(timespan)) {
					buildNextWeekList();
				}
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

	private TimeSpan getThisWeekTimeSpan() {
		Date now = new Date();
		Calendar startDate = Calendar.getInstance();
		startDate.setTime(now);
		startDate.add(Calendar.DATE, 1);
		startDate.set(Calendar.HOUR, 0);
		startDate.set(Calendar.MINUTE, 0);
		startDate.set(Calendar.SECOND, 0);
		startDate.set(Calendar.AM_PM, Calendar.AM);

		Calendar endDate = Calendar.getInstance();
		endDate.setTime(now);
		// endDate.add(Calendar.WEEK_OF_YEAR, 1);
		endDate.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		endDate.set(Calendar.HOUR, 11);
		endDate.set(Calendar.MINUTE, 59);
		endDate.set(Calendar.SECOND, 59);
		endDate.set(Calendar.AM_PM, Calendar.PM);
		return new TimeSpan(startDate.getTime(), endDate.getTime());
	}

	private TimeSpan getNextWeekTimeSpan() {
		Date now = new Date();
		Calendar startDate = Calendar.getInstance();
		startDate.setTime(now);
		startDate.add(Calendar.WEEK_OF_YEAR, 1);
		startDate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		startDate.set(Calendar.HOUR, 0);
		startDate.set(Calendar.MINUTE, 0);
		startDate.set(Calendar.SECOND, 0);
		startDate.set(Calendar.AM_PM, Calendar.AM);

		Calendar endDate = Calendar.getInstance();
		endDate.setTime(now);
		endDate.add(Calendar.WEEK_OF_YEAR, 1);
		endDate.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		endDate.set(Calendar.HOUR, 11);
		endDate.set(Calendar.MINUTE, 59);
		endDate.set(Calendar.SECOND, 59);
		endDate.set(Calendar.AM_PM, Calendar.PM);
		return new TimeSpan(startDate.getTime(), endDate.getTime());
	}

}
