/*******************************************************************************
 * Copyright (c) Emil Crumhorn - Hexapixel.com - emil.crumhorn@gmail.com
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    emil.crumhorn@gmail.com - initial API and implementation
 *******************************************************************************/

package org.remus.infomngmnt.common.ui.widgets;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.eclipse.nebula.widgets.calendarcombo.DateHelper;
import org.eclipse.nebula.widgets.calendarcombo.ICalendarListener;
import org.eclipse.nebula.widgets.calendarcombo.IColorManager;
import org.eclipse.nebula.widgets.calendarcombo.ISettings;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Listener;

public class CalendarComposite extends Canvas implements MouseListener, MouseMoveListener {

	private final int ARROW_LEFT = 1;

	private final int ARROW_RIGHT = 2;

	private Button mButtonToday;

	private Button mButtonNone;

	private Rectangle mLeftArrowBounds;

	private Rectangle mRightArrowBounds;

	private Rectangle mMonthNameBounds;

	private Calendar mCalendar;

	private Calendar mToday;

	private int mDatesTopY = 0;

	private final int mDayXs[] = new int[7];

	private final CalDay[] mDays = new CalDay[7 * 6];

	private Calendar mSelectedDay;

	// used for date ranges
	private Calendar mMouseDownDay;

	private Calendar mMouseUpDay;

	private static DateFormatSymbols mDFS;

	private String mMonths[];

	private static String[] mDayTitles = null;

	private boolean mMonthSelectorOpen;

	private ArrayList mListeners;

	private boolean mNoDayClicked;

	// this thread deals with holding down the mouse over the arrows to jump
	// months quickly

	// milliseconds
	private static final int ARROW_SLOW_TIME = 300;

	private static final int ARROW_FAST_TIME = 120;

	// after how many iterations do we switch speed? (after how many months
	// flipped-by)
	private static final int ARROW_SPEED_SWITCH_COUNT = 10;

	// default speed
	private int mArrowSleepTime = ARROW_SLOW_TIME;

	// various variables used to keep track
	private int mArrowIterations = 0;

	private Thread mArrowThread;

	private boolean mArrowRun;

	private boolean mArrowPause;

	private int mArrowThreadDirection = 0;

	private final IColorManager mColorManager;

	private final ISettings mSettings;

	private final Calendar mDisallowBeforeDate;

	private final Calendar mDisallowAfterDate;

	private final boolean mDateRange;

	private boolean mMouseIsDown;

	protected static final boolean OS_CARBON = "carbon".equals(SWT.getPlatform());
	protected static final boolean OS_GTK = "gtk".equals(SWT.getPlatform());
	protected static final boolean OS_WINDOWS = "win32".equals(SWT.getPlatform());

	public CalendarComposite(final Composite parent, final Calendar selectedDay,
			final Calendar disallowBeforeDate, final Calendar disallowAfterDate,
			final IColorManager colorManager, final ISettings settings, final boolean dateRange,
			final Calendar rangeStart, final Calendar rangeEnd) {
		super(parent, SWT.NO_BACKGROUND | SWT.NO_FOCUS | SWT.DOUBLE_BUFFERED);
		this.mSelectedDay = selectedDay;
		this.mCalendar = selectedDay;
		this.mColorManager = colorManager;
		this.mSettings = settings;
		this.mDisallowBeforeDate = disallowBeforeDate;
		this.mDisallowAfterDate = disallowAfterDate;
		this.mDateRange = dateRange;
		if (dateRange) {
			this.mMouseDownDay = rangeStart;
			this.mMouseUpDay = rangeEnd;
		}

		init();

		build();
	}

	private void init() {
		mDFS = new DateFormatSymbols(this.mSettings.getLocale());
		this.mMonths = mDFS.getMonths();
		;

		if (this.mCalendar == null) {
			this.mCalendar = Calendar.getInstance(this.mSettings.getLocale());
		}
		if (this.mToday == null) {
			this.mToday = Calendar.getInstance(this.mSettings.getLocale());
		}

		String[] weekdays = mDFS.getWeekdays();
		mDayTitles = new String[weekdays.length];
		for (int i = 0; i < weekdays.length; i++) {
			String weekday = weekdays[i];
			if (weekday.length() > 0) {
				mDayTitles[i] = weekday.substring(0, 1).toUpperCase();
			}
		}
	}

	private void build() {
		// button height & width and spacers
		int bheight = this.mSettings.getButtonHeight();
		int bwidth = this.mSettings.getButtonWidth();
		int buttonStyle = SWT.PUSH;
		this.mListeners = new ArrayList();

		// Mac buttons need different flag to look normal
		if (OS_CARBON) {
			bheight = this.mSettings.getCarbonButtonHeight();
			bwidth = this.mSettings.getButtonWidthCarbon();
			buttonStyle = SWT.FLAT;
		}

		setLayout(new ButtonSectionLayout());

		addPaintListener(new PaintListener() {
			public void paintControl(final PaintEvent event) {
				paint(event);
			}
		});

		addMouseListener(this);
		addMouseMoveListener(this);
		this.mButtonNone = new Button(this, buttonStyle);
		this.mButtonNone.setText(this.mSettings.getNoneText());
		this.mButtonNone.setLayoutData(new GridData(bwidth, bheight));
		this.mButtonNone.setVisible(false);
		this.mButtonNone.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				clickedNoneButton();
			}
		});

		this.mButtonToday = new Button(this, buttonStyle);
		this.mButtonToday.setText(this.mSettings.getTodayText());
		this.mButtonToday.setLayoutData(new GridData(bwidth, bheight));
		this.mButtonToday.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				clickedTodayButton();
			}
		});

	}

	private void clickedTodayButton() {
		setDate(Calendar.getInstance(this.mSettings.getLocale()));
		this.mSelectedDay = Calendar.getInstance(this.mSettings.getLocale());
		notifyListeners();
	}

	private void clickedNoneButton() {
		notifyListeners(null);
		notifyClose();
	}

	private void paint(final PaintEvent event) {
		GC gc = event.gc;
		drawChartOntoGC(gc);
	}

	private void drawChartOntoGC(final GC gc) {
		Rectangle bounds = super.getBounds();
		gc.setBackground(this.mColorManager.getCalendarBackgroundColor());
		gc.fillRectangle(bounds);

		Font used = null;
		if (OS_CARBON) {
			used = this.mSettings.getCarbonDrawFont();
			if (used != null) {
				gc.setFont(used);
			}
		}

		// header
		drawHeader(gc);
		// day titles
		drawTitleDays(gc);
		// days
		drawDays(gc);
		// 1 pixel border
		drawBorder(gc);

		gc.dispose();
		if (used != null) {
			used.dispose();
		}
	}

	public void setDate(final Calendar date) {
		date.set(Calendar.DAY_OF_MONTH, 1);
		this.mCalendar = date;
		redraw();
	}

	public void nextMonth() {
		this.mSelectedDay = null;
		this.mCalendar.set(Calendar.DAY_OF_MONTH, 1);
		this.mCalendar.add(Calendar.MONTH, 1);
		redraw();
	}

	public void prevMonth() {
		this.mSelectedDay = null;
		this.mCalendar.add(Calendar.MONTH, -1);
		this.mCalendar.set(Calendar.DAY_OF_MONTH, 1);
		redraw();
	}

	public void goToToday() {
		this.mSelectedDay = null;
		this.mCalendar = Calendar.getInstance(this.mSettings.getLocale());
		redraw();
	}

	// draws 1 pixel border around entire calendar
	private void drawBorder(final GC gc) {
		Rectangle bounds = super.getBounds();

		gc.setForeground(this.mColorManager.getCalendarBorderColor());
		gc.drawRectangle(bounds.x, bounds.y, bounds.width - 1, bounds.height - 1);
	}

	private void drawHeader(final GC gc) {
		Rectangle bounds = super.getBounds();
		Rectangle bgRect = new Rectangle(bounds.x + this.mSettings.getHeaderLeftMargin(), bounds.y
				+ this.mSettings.getHeaderTopMargin(), this.mSettings.getCalendarWidth() - 13,
				bounds.y + this.mSettings.getHeaderHeight());
		gc.setBackground(this.mColorManager.getCalendarHeaderColor());
		gc.fillRectangle(bgRect);
		drawArrow(gc, bounds.x + this.mSettings.getHeaderLeftMargin()
				+ this.mSettings.getArrowLeftSpacing() + 1, bounds.y
				+ this.mSettings.getHeaderTopMargin() + this.mSettings.getArrowTopSpacing() + 4,
				this.ARROW_LEFT);
		drawArrow(gc, bounds.x + this.mSettings.getCalendarWidth() - 13
				- this.mSettings.getHeaderRightMargin(), bounds.y
				+ this.mSettings.getHeaderTopMargin() + this.mSettings.getArrowTopSpacing() + 4,
				this.ARROW_RIGHT);

		String toDraw = this.mMonths[this.mCalendar.get(Calendar.MONTH)] + " "
				+ this.mCalendar.get(Calendar.YEAR);
		Point strWidth = gc.stringExtent(toDraw);

		int avail = this.mSettings.getCalendarWidth() - 13 - strWidth.x;
		avail /= 2;

		this.mMonthNameBounds = new Rectangle(bounds.x + this.mSettings.getHeaderLeftMargin()
				+ avail, bounds.y + this.mSettings.getHeaderTopMargin() + 1, strWidth.x, strWidth.y);

		gc.drawString(toDraw, bounds.x + this.mSettings.getHeaderLeftMargin() + avail, bounds.y
				+ this.mSettings.getHeaderTopMargin() + 1, true);
	}

	private void drawTitleDays(final GC gc) {
		Calendar temp = Calendar.getInstance(this.mSettings.getLocale());
		// fetch the first day of the week, and draw starting on that day
		int fdow = temp.getFirstDayOfWeek();

		Rectangle bounds = super.getBounds();
		int xStart = this.mSettings.getDatesLeftMargin() + 5;
		int yStart = bounds.y + this.mSettings.getHeaderTopMargin()
				+ this.mSettings.getHeaderHeight() + 1;

		int spacer = 0;
		int letterHeight = 0;

		for (int i = 0; i < 7; i++) {
			Point strWidth = gc.stringExtent(mDayTitles[fdow]);

			int x = xStart + this.mSettings.getOneDateBoxSize() + spacer - strWidth.x;
			// don't add the string width, as our string width later when
			// drawing days will differ
			this.mDayXs[i] = xStart + this.mSettings.getOneDateBoxSize() + spacer;

			gc.drawString(mDayTitles[fdow], x, yStart, true);

			letterHeight = strWidth.y;
			spacer += this.mSettings.getOneDateBoxSize() + this.mSettings.getBoxSpacer();

			fdow++;
			if (fdow > 7) {
				fdow = 1;
			}
		}

		int lineStart = yStart + 1 + letterHeight;
		gc.setForeground(this.mColorManager.getLineColor());
		gc.drawLine(this.mSettings.getDatesLeftMargin() + 1, lineStart, bounds.width
				- this.mSettings.getDatesRightMargin() - 3, lineStart);

		this.mDatesTopY = lineStart + 3;
	}

	private void drawDays(final GC gc) {
		gc.setBackground(this.mColorManager.getCalendarBackgroundColor());
		gc.setForeground(this.mColorManager.getTextColor());

		Rectangle bounds = super.getBounds();
		int spacer = 0;

		Calendar temp = (Calendar) this.mCalendar.clone();
		temp.set(Calendar.DAY_OF_MONTH, 1);
		temp = clearTime(temp);

		int monthToShow = temp.get(Calendar.MONTH);

		int firstDayOfWeek = temp.getFirstDayOfWeek();
		int firstDay = temp.get(Calendar.DAY_OF_WEEK) - firstDayOfWeek;
		if (firstDay < 0) {
			firstDay += 7;
		}

		temp.add(Calendar.DATE, -firstDay);
		int col = 0;
		int colCount = 0;

		String typicalLetter = "8";
		int strHeight = gc.stringExtent(typicalLetter).y;

		gc.setForeground(this.mColorManager.getLineColor());

		int lastY = 0;

		List betweenDays = null;
		if (this.mDateRange && this.mMouseDownDay != null) {
			Calendar end = this.mMouseUpDay == null ? this.mSelectedDay : this.mMouseUpDay;
			// end can be null when month is switching to next
			if (end != null) {
				betweenDays = getCalendarsBetween(this.mMouseDownDay, end);
			}
		}

		for (int y = 0; y < 42; y++) {
			// new row
			if (y % 7 == 0 && y != 0) {
				spacer += strHeight + 2;
			}

			if (colCount > 6) {
				colCount = 0;
				col = 0;
			}

			int curMonth = temp.get(Calendar.MONTH);

			if (curMonth == monthToShow) {
				gc.setForeground(this.mColorManager.getTextColor());
			} else {
				gc.setForeground(this.mColorManager.getPreviousAndNextMonthForegroundColor());
			}

			boolean disallowedDate = false;
			if (this.mDisallowBeforeDate != null) {
				if (temp.before(this.mDisallowBeforeDate)) {
					disallowedDate = true;
					gc.setForeground(this.mColorManager.getDisabledDayForegroundColor());
				}
			}
			if (this.mDisallowAfterDate != null && !disallowedDate) {
				if (temp.after(this.mDisallowAfterDate)) {
					disallowedDate = true;
					gc.setForeground(this.mColorManager.getDisabledDayForegroundColor());
				}
			}

			String dateStr = "" + temp.get(Calendar.DATE);
			Point width = gc.stringExtent(dateStr);

			if (this.mSelectedDay != null) {
				if (DateHelper.sameDate(this.mSelectedDay, temp)
						|| (this.mDateRange && betweenDays != null && betweenDays.contains(temp))) {
					gc.setBackground(this.mColorManager.getSelectedDayColor());
					gc.fillRectangle(this.mDayXs[col] - this.mSettings.getOneDateBoxSize() - 4,
							this.mDatesTopY + spacer - 1, this.mSettings.getOneDateBoxSize() + 5,
							14);
					gc.setBackground(this.mColorManager.getCalendarBackgroundColor());
				}
			}

			Rectangle dayBounds = new Rectangle(this.mDayXs[col]
					- this.mSettings.getOneDateBoxSize() - 4, this.mDatesTopY + spacer - 1,
					this.mSettings.getOneDateBoxSize() + 5, 14);

			this.mDays[y] = new CalDay(y, temp, dayBounds, disallowedDate);

			gc.drawString(dateStr, this.mDayXs[col] - width.x, this.mDatesTopY + spacer, true);

			if (DateHelper.sameDate(this.mToday, temp)) {
				Color old = gc.getForeground();
				gc.setForeground(this.mColorManager.getSelectedDayBorderColor());
				gc.drawRectangle(this.mDayXs[col] - this.mSettings.getOneDateBoxSize() - 4,
						this.mDatesTopY + spacer - 1, this.mSettings.getOneDateBoxSize() + 5, 14);
				gc.setForeground(old);
			}

			temp.add(Calendar.DATE, 1);
			col++;
			colCount++;
			lastY = this.mDatesTopY + spacer;
		}

		lastY += strHeight + 1;

		gc.setForeground(this.mColorManager.getLineColor());
		gc.drawLine(this.mSettings.getDatesLeftMargin() + 1, lastY, bounds.width
				- this.mSettings.getDatesRightMargin() - 3, lastY);
	}

	private List getCalendarsBetween(final Calendar start, final Calendar end) {
		List ret = new ArrayList();

		// we have to remember that the end can come before the start, so let's
		// figure that out first
		Calendar trueStart = end.before(start) ? end : start;
		Calendar trueEnd = end.after(start) ? end : start;

		// boolean flip = end.before(start);

		int days = (int) DateHelper.daysBetween(trueStart, trueEnd, this.mSettings.getLocale());

		for (int i = 0; i <= days; i++) {
			// we need new objects for each day
			Calendar cal = Calendar.getInstance(this.mSettings.getLocale());
			cal.setTime(trueStart.getTime());

			cal.add(Calendar.DATE, i);
			ret.add(clearTime(cal));
		}

		return ret;
	}

	private Calendar clearTime(final Calendar cal) {
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal;
	}

	private void drawArrow(final GC gc, final int x, final int y, final int style) {
		gc.setForeground(this.mColorManager.getArrowColor());
		switch (style) {
		case ARROW_RIGHT:
			gc.drawLine(x, y - 4, x, y + 4);
			gc.drawLine(x + 1, y - 3, x + 1, y + 3);
			gc.drawLine(x + 2, y - 2, x + 2, y + 2);
			gc.drawLine(x + 3, y - 1, x + 3, y + 1);
			gc.drawLine(x + 4, y, x + 4, y);
			this.mRightArrowBounds = new Rectangle(x - 4, y - 4, x + 8, y);
			break;
		case ARROW_LEFT:
			gc.drawLine(x - 1, y, x - 1, y);
			gc.drawLine(x, y - 1, x, y + 1);
			gc.drawLine(x + 1, y - 2, x + 1, y + 2);
			gc.drawLine(x + 2, y - 3, x + 2, y + 3);
			gc.drawLine(x + 3, y - 4, x + 3, y + 4);
			this.mLeftArrowBounds = new Rectangle(x - 4, y - 4, x + 8, y);
			break;
		}
	}

	private boolean isInside(final int x, final int y, final Rectangle rect) {
		if (rect == null) {
			return false;
		}

		if (x >= rect.x && y >= rect.y && x <= (rect.x + rect.width) && y <= (rect.y + rect.height)) {
			return true;
		}

		return false;
	}

	public void mouseMove(final MouseEvent e) {
		if (this.mMonthSelectorOpen) {
			return;
		}

		// "dragging" the day, just paint it
		if (e.stateMask != 0) {
			doDaySelection(e.x, e.y);
		}

		if (this.mArrowRun && this.mArrowThread != null) {
			if (!isInside(e.x, e.y, this.mLeftArrowBounds)
					&& !isInside(e.x, e.y, this.mRightArrowBounds)) {
				this.mArrowPause = true;
				// also pause the speed
				this.mArrowIterations = 0;
				this.mArrowSleepTime = ARROW_SLOW_TIME;
			} else {
				if (isInside(e.x, e.y, this.mLeftArrowBounds)) {
					this.mArrowThreadDirection = this.ARROW_LEFT;
				} else {
					this.mArrowThreadDirection = this.ARROW_RIGHT;
				}

				this.mArrowPause = false;
			}
		}
	}

	public void mouseDoubleClick(final MouseEvent event) {
	}

	// draw the date selection on mouse down to give that flicker of "response"
	// to the user
	public void mouseDown(final MouseEvent event) {
		this.mMouseIsDown = true;
		if (this.mDateRange) {
			this.mMouseUpDay = null;
		}

		if (isInside(event.x, event.y, this.mLeftArrowBounds)) {
			prevMonth();

			runArrowThread(this.ARROW_LEFT);

			return;
		}

		if (isInside(event.x, event.y, this.mRightArrowBounds)) {
			nextMonth();

			runArrowThread(this.ARROW_RIGHT);

			return;
		}

		if (this.mSettings.showMonthPickerOnMonthNameMousePress()
				&& isInside(event.x, event.y, this.mMonthNameBounds)) {
			MonthPick mp = new MonthPick(this, SWT.NONE, this.mCalendar, this, this.mSettings,
					this.mSettings.getLocale());
			mp.addDisposeListener(new DisposeListener() {
				public void widgetDisposed(final DisposeEvent e) {
					CalendarComposite.this.mMonthSelectorOpen = false;
				}
			});

			// figure out where to put it
			Rectangle bounds = getBounds();
			int xSpacer = (bounds.width - mp.getSize().x) / 2;
			int ySpacer = getLocation().y;
			mp.setLocation(xSpacer, ySpacer);

			mp.setVisible(true);
			this.mMonthSelectorOpen = true;
			return;
		}

		doDaySelection(event.x, event.y);

		if (this.mDateRange) {
			this.mMouseDownDay = this.mSelectedDay;
		}
	}

	private void killArrowThread() {
		if (this.mArrowThread != null) {
			this.mArrowPause = true;
			this.mArrowThreadDirection = 0;
			this.mArrowRun = false;
			this.mArrowThread = null;
		}
	}

	private void runArrowThread(final int direction) {
		this.mArrowThreadDirection = direction;
		this.mArrowIterations = 0;
		this.mArrowSleepTime = ARROW_SLOW_TIME;
		this.mArrowRun = false;
		this.mArrowPause = false;
		this.mArrowThread = new Thread() {
			@Override
			public void run() {
				while (CalendarComposite.this.mArrowRun) {
					try {
						sleep(CalendarComposite.this.mArrowSleepTime);

						if (!CalendarComposite.this.mArrowPause) {
							CalendarComposite.this.mArrowIterations++;
						}

						if (CalendarComposite.this.mArrowIterations > ARROW_SPEED_SWITCH_COUNT
								&& CalendarComposite.this.mArrowSleepTime != ARROW_FAST_TIME) {
							CalendarComposite.this.mArrowSleepTime = ARROW_FAST_TIME;
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (!CalendarComposite.this.mArrowPause) {
						Display.getDefault().syncExec(new Runnable() {
							public void run() {
								if (isDisposed()) {
									CalendarComposite.this.mArrowRun = false;
									CalendarComposite.this.mArrowThread = null;
								} else {
									if (CalendarComposite.this.mArrowThreadDirection == CalendarComposite.this.ARROW_LEFT) {
										prevMonth();
									} else if (CalendarComposite.this.mArrowThreadDirection == CalendarComposite.this.ARROW_RIGHT) {
										nextMonth();
									}
								}
							}
						});
					}
				}
			}
		};
		this.mArrowPause = false;
		this.mArrowRun = true;
		this.mArrowThread.start();
	}

	// selects a day at x, y if there is a day there to select
	private void doDaySelection(final int x, final int y) {
		int curYear = this.mCalendar.get(Calendar.YEAR);
		int curMonth = this.mCalendar.get(Calendar.MONTH);
		this.mNoDayClicked = false;

		for (int i = 0; i < this.mDays.length; i++) {
			if (isInside(x, y, this.mDays[i].getBounds())) {

				// disabled date? ignore click completely
				if (this.mDays[i].isDisabled()) {
					return;
				}

				int dayYear = this.mDays[i].getDate().get(Calendar.YEAR);
				int dayMonth = this.mDays[i].getDate().get(Calendar.MONTH);

				if (dayYear == curYear) {
					if (dayMonth < curMonth) {
						prevMonth();
						return;
					} else if (dayMonth > curMonth) {
						nextMonth();
						return;
					}
				} else {
					if (dayYear < curYear) {
						prevMonth();
						return;
					} else if (dayYear > curYear) {
						nextMonth();
						return;
					}
				}

				this.mSelectedDay = this.mDays[i].getDate();
				redraw();
				return;
			}
		}

		this.mNoDayClicked = true;
	}

	public void mouseUp(final MouseEvent event) {
		this.mMouseIsDown = false;
		killArrowThread();

		if (this.mNoDayClicked) {
			this.mNoDayClicked = false;
			return;
		}

		if (this.mDateRange) {
			// this may seem odd but it's not. First we set the "up" date to the
			// current date
			// then we overwrite it by setting the selected date to when the
			// mouse click was "down".
			// that way the date set on the combo will be the date the user
			// clicked first, and not the date when the user
			// let go of the mouse button, this will be reflected in the
			// listeners as well
			this.mMouseUpDay = this.mSelectedDay;
			this.mSelectedDay = this.mMouseDownDay;
		}

		if (this.mSelectedDay != null) {
			notifyListeners();
			notifyClose();
		}
	}

	private void notifyClose() {

		for (int i = 0; i < this.mListeners.size(); i++) {
			ICalendarListener l = (ICalendarListener) this.mListeners.get(i);
			l.popupClosed();
		}
	}

	private void notifyListeners(final Calendar date) {

		for (int i = 0; i < this.mListeners.size(); i++) {
			ICalendarListener l = (ICalendarListener) this.mListeners.get(i);

			if (this.mDateRange) {
				l.dateRangeChanged(this.mMouseDownDay, this.mMouseUpDay);
			} else {
				l.dateChanged(date);
			}
		}
	}

	private void notifyListeners() {
		notifyListeners(this.mSelectedDay);
	}

	public void addCalendarListener(final ICalendarListener listener) {
		if (!this.mListeners.contains(listener)) {
			this.mListeners.add(listener);
		}
	}

	public void removeCalendarListener(final ICalendarListener listener) {
		this.mListeners.remove(listener);
	}

	public boolean externalClick(Point p) {
		Point loc = this.mButtonToday.getLocation();
		p = toControl(p);
		int width = this.mButtonToday.getSize().x;
		int height = this.mButtonToday.getSize().y;
		Rectangle area = new Rectangle(loc.x, loc.y, width, height);

		if (isInside(p.x, p.y, area)) {
			clickedTodayButton();
			return true;
		}

		// loc = this.mButtonNone.getLocation();
		// width = this.mButtonNone.getSize().x;
		// height = this.mButtonNone.getSize().y;
		// area = new Rectangle(loc.x, loc.y, width, height);
		//
		// if (isInside(p.x, p.y, area)) {
		// clickedNoneButton();
		// }

		return true;
	}

	public boolean isMonthPopupActive() {
		return this.mMonthSelectorOpen;
	}

	class ButtonSectionLayout extends Layout {

		@Override
		protected Point computeSize(final Composite composite, final int wHint, final int hHint,
				final boolean flushCache) {
			return new Point(SWT.DEFAULT, SWT.DEFAULT);
		}

		@Override
		protected void layout(final Composite composite, final boolean flushCache) {
			int bheight = CalendarComposite.this.mSettings.getButtonHeight();
			int bwidth = CalendarComposite.this.mSettings.getButtonWidth();
			int vspacer = CalendarComposite.this.mSettings.getButtonVerticalSpace();
			int bspacer = CalendarComposite.this.mSettings.getButtonsHorizontalSpace();
			if (OS_CARBON) {
				bwidth = CalendarComposite.this.mSettings.getButtonWidthCarbon();
				bheight = CalendarComposite.this.mSettings.getCarbonButtonHeight();
				vspacer = CalendarComposite.this.mSettings.getCarbonButtonVerticalSpace();
				bspacer = CalendarComposite.this.mSettings.getCarbonButtonsHorizontalSpace();
			}

			// see how much space we put on the left and right sides of the
			// buttons
			int width = CalendarComposite.this.mSettings.getCalendarWidth() - (bwidth * 2)
					- bspacer;
			width /= 2;

			int button1Left = width;
			int button2Left = CalendarComposite.this.mSettings.getCalendarWidth() - width - bwidth;

			Control[] children = composite.getChildren();
			for (int i = 0; i < children.length; i++) {
				switch (i) {
				case 0:
					children[i].setBounds(button1Left, vspacer, bwidth, bheight);

					break;
				case 1:
					children[i].setBounds(button2Left, vspacer, bwidth, bheight);
					break;
				}

			}
		}

	}

	class CalDay {
		private final Calendar date;

		private final int number;

		private final Rectangle bounds;

		private final boolean disabled;

		public CalDay(final int number, final Calendar date, final Rectangle bounds,
				final boolean disabled) {
			this.date = (Calendar) date.clone();
			this.bounds = bounds;
			this.number = number;
			this.disabled = disabled;
		}

		public boolean isDisabled() {
			return this.disabled;
		}

		public Calendar getDate() {
			return this.date;
		}

		public int getNumber() {
			return this.number;
		}

		public Rectangle getBounds() {
			return this.bounds;
		}
	}
}
