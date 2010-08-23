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
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.aspencloud.calypso.util.TimeSpan;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.nebula.widgets.calendarcombo.ColorCache;
import org.eclipse.nebula.widgets.calendarcombo.DefaultColorManager;
import org.eclipse.nebula.widgets.calendarcombo.DefaultSettings;
import org.eclipse.remus.common.ui.image.ResourceManager;
import org.eclipse.remus.services.RemusServiceTracker;
import org.eclipse.remus.ui.collapsiblebutton.CollapsibleButtonBar;
import org.eclipse.remus.ui.editors.EditorUtil;
import org.eclipse.remus.ui.widgets.CalendarComposite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.ManagedForm;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
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
import org.remus.infomngmnt.ui.calendar.CalendarActivator;
import org.remus.infomngmnt.ui.calendar.CalendarEditor;
import org.remus.infomngmnt.ui.calendar.CalendarEditorInput;
import org.remus.infomngmnt.ui.calendar.CalendarEntryUtil;
import org.remus.infomngmnt.ui.calendar.service.ICalendarStoreService;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class CalendarSection extends CollapsibleButtonBar implements IDirtyTimespanListener,
		ISelectionProvider {

	private static final int SECTION_STYLE = ExpandableComposite.TITLE_BAR
			| ExpandableComposite.TWISTIE | ExpandableComposite.EXPANDED;
	private CalendarComposite cc;

	private CalendarSelectionDelegate calendarSelectionDelegate;

	private ScrolledForm sform;

	private FormText todayFormText;

	private FormText thisWeekFormText;

	private FormText nextWeekFormText;
	private Section today;

	private final HyperlinkAdapter hyperLinkAdapter = new HyperlinkAdapter() {
		@Override
		public void linkActivated(final HyperlinkEvent e) {
			String href = (String) e.getHref();
			String[] split = StringUtils.split(href, "_");
			EditorUtil.openInfoUnit(split[0]);
		}
	};
	private FormToolkit toolkit;
	private final ICalendarStoreService storeService;
	private final RemusServiceTracker serviceTracker;
	private ICalendarChangeSupport changeSupportService;

	/**
	 * 
	 */
	public CalendarSection() {
		this.storeService = CalendarActivator.getDefault().getCalendarStoreService();
		this.serviceTracker = CalendarActivator.getDefault().getServiceTracker();

	}

	@Override
	public void createControl(final Composite parent) {
		this.toolkit = new FormToolkit(parent.getDisplay());
		this.sform = this.toolkit.createScrolledForm(parent);
		this.sform.setLayoutData(new GridData(GridData.FILL_BOTH));
		ManagedForm managedForm = new ManagedForm(this.toolkit, this.sform);
		TableWrapLayout layout = new TableWrapLayout();
		layout.leftMargin = 10;
		layout.rightMargin = 10;
		this.sform.getBody().setLayout(layout);

		this.changeSupportService = this.serviceTracker.getService(ICalendarChangeSupport.class);
		this.changeSupportService.addTimeSpanListener(this);

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
		this.nextWeekFormText.addHyperlinkListener(this.hyperLinkAdapter);
		nextWeek.setClient(this.nextWeekFormText);
		buildNextWeekList();

	}

	private FormToolkit getToolkit() {
		return this.toolkit;
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
		this.thisWeekFormText.addHyperlinkListener(this.hyperLinkAdapter);
		thisWeek.setClient(this.thisWeekFormText);
		buildThisWeekList();

	}

	private void createTodaySection() {
		this.today = getToolkit().createSection(this.sform.getBody(), SECTION_STYLE);
		// today.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		this.today.setText("Today");

		TableWrapData td = new TableWrapData();
		td.align = TableWrapData.FILL;
		td.grabHorizontal = true;
		this.today.setLayoutData(td);

		this.todayFormText = getToolkit().createFormText(this.today, false);
		this.todayFormText.addHyperlinkListener(this.hyperLinkAdapter);
		this.today.setClient(this.todayFormText);
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
		super.handleSelect();
	}

	@Override
	public void dispose() {
		this.changeSupportService.removeTimeSpanListener(this);
		this.serviceTracker.ungetService(this.changeSupportService);
		this.calendarSelectionDelegate.dispose();
		super.dispose();
	}

	private void buildTodayList() {

		Tasklist items = this.storeService.getItems(getTodaytimeSpan());
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
		this.todayFormText.setImage("alarm", ResourceManager.getPluginImage(CalendarActivator
				.getDefault(), "icons/iconexperience/16/alarmclock.png"));
		this.todayFormText.setImage("refresh", ResourceManager.getPluginImage(CalendarActivator
				.getDefault(), "icons/iconexperience/16/refresh.png"));
		StringBuilder sw = new StringBuilder();
		sw.append("<form>");
		for (Task entry : calendarEntry) {
			sw.append("<p>");
			// sw.append("<li>");
			sw.append(CalendarEntryUtil.setFormTextRepresentation(entry, false, true));
			// sw.append("</li>");
			sw.append("</p>");
		}
		sw.append("</form>");
		this.todayFormText.setText(sw.toString(), true, false);
		this.sform.reflow(false);
	}

	private void buildThisWeekList() {

		Tasklist items = this.storeService.getItems(getThisWeekTimeSpan());
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
		this.thisWeekFormText.setImage("alarm", ResourceManager.getPluginImage(CalendarActivator
				.getDefault(), "icons/iconexperience/16/alarmclock.png"));
		this.thisWeekFormText.setImage("refresh", ResourceManager.getPluginImage(CalendarActivator
				.getDefault(), "icons/iconexperience/16/refresh.png"));
		StringBuilder sw = new StringBuilder();
		sw.append("<form>");
		for (Task entry : calendarEntry) {
			sw.append("<p>");
			// sw.append("<li>");
			sw.append(CalendarEntryUtil.setFormTextRepresentation(entry, false, true));
			// sw.append("</li>");
			sw.append("</p>");
		}
		sw.append("</form>");
		this.thisWeekFormText.setText(sw.toString(), true, false);
		this.sform.reflow(false);
	}

	private void buildNextWeekList() {

		Tasklist items = this.storeService.getItems(getNextWeekTimeSpan());
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
		this.nextWeekFormText.setImage("alarm", ResourceManager.getPluginImage(CalendarActivator
				.getDefault(), "icons/iconexperience/16/alarmclock.png"));
		this.nextWeekFormText.setImage("refresh", ResourceManager.getPluginImage(CalendarActivator
				.getDefault(), "icons/iconexperience/16/refresh.png"));
		StringBuilder sw = new StringBuilder();
		sw.append("<form>");
		for (Task entry : calendarEntry) {
			sw.append("<p>");
			// sw.append("<li>");
			sw.append(CalendarEntryUtil.setFormTextRepresentation(entry, false, true));
			// sw.append("</li>");
			sw.append("</p>");
		}
		sw.append("</form>");
		this.nextWeekFormText.setText(sw.toString(), true, false);
		this.sform.redraw();
	}

	public void handleDirtyTimeSpan(final TimeSpan timespan) {
		getViewSite().getShell().getDisplay().asyncExec(new Runnable() {
			public void run() {
				TimeSpan todaytimeSpan = getTodaytimeSpan();
				clearLists();
				buildTodayList();

				buildThisWeekList();

				buildNextWeekList();

				CalendarSection.this.today.redraw();
			}
		});

	}

	protected void clearLists() {
		this.nextWeekFormText.setText("<form><p>No Events</p></form>", true, false);
		this.todayFormText.setText("<form><p>No Events</p></form>", true, false);
		this.thisWeekFormText.setText("<form><p>No Events</p></form>", true, false);

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
		startDate.set(Calendar.SECOND, 1);
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
		endDate.add(Calendar.WEEK_OF_YEAR, 2);
		endDate.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		endDate.set(Calendar.HOUR, 11);
		endDate.set(Calendar.MINUTE, 59);
		endDate.set(Calendar.SECOND, 59);
		endDate.set(Calendar.AM_PM, Calendar.PM);
		return new TimeSpan(startDate.getTime(), endDate.getTime());
	}

	public void addSelectionChangedListener(final ISelectionChangedListener listener) {
		this.calendarSelectionDelegate.addSelectionChangedListener(listener);

	}

	public ISelection getSelection() {
		return this.calendarSelectionDelegate.getSelection();
	}

	public void removeSelectionChangedListener(final ISelectionChangedListener listener) {
		this.calendarSelectionDelegate.removeSelectionChangedListener(listener);

	}

	public void setSelection(final ISelection selection) {
		this.calendarSelectionDelegate.setSelection(selection);

	}

}
