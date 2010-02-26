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
import java.util.Locale;

import org.eclipse.nebula.widgets.calendarcombo.ColorCache;
import org.eclipse.nebula.widgets.calendarcombo.ISettings;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

class MonthPick extends Canvas implements MouseListener {
	// months to show in the popup
	private final int mMonthsToShow = 7;

	private final Calendar mStart;

	private Rectangle mBounds;

	private final int mTopDateSpacer = 2;

	private final int mInnerWidth = 99;

	private final DateFormatSymbols mDFS = new DateFormatSymbols(Locale.getDefault());

	private final String[] mMonths = this.mDFS.getMonths();

	private final ArrayList mMonthEntries = new ArrayList();

	private MonthEntry mHoverEntry;

	private final boolean mEnableDoubleBuffering = true;

	private boolean mCreated;

	private Thread mAboveThread = null;

	private Thread mBelowThread = null;

	private boolean mAboveRun = false;

	private boolean mBelowRun = false;

	private CalendarComposite mCalendarComposite = null;

	private Calendar mSelectedMonth = null;

	private final Locale mLocale;

	private final ISettings mSettings;

	private Listener mDisposeListener;

	private final int sleepTime = 200;

	private Listener mCarbonMouseMoveListener;

	private Listener mCarbonMouseUpListener;

	public MonthPick(final Composite parent, final int style, final Calendar start,
			final CalendarComposite cc, final ISettings settings, final Locale locale) {
		super(parent, style | SWT.NO_BACKGROUND | SWT.NO_REDRAW_RESIZE | SWT.ON_TOP);
		this.mStart = start;
		this.mCalendarComposite = cc;
		this.mLocale = locale;
		this.mSettings = settings;

		setSize(101, 112);

		setCapture(true);

		addMouseListener(this);

		// oh carbon, why art thou so different!
		// basically we need to do everything as far as listeners go on Carbon,
		// because everything is fired differently
		if (CalendarComposite.OS_CARBON) {
			this.mCarbonMouseMoveListener = new Listener() {
				public void handleEvent(final Event event) {
					// widget mouse movement reporting is really off on Carbon,
					// check if we're actually reporting a mouse move on us
					if (Display.getDefault().getCursorControl() == MonthPick.this) {
						mouseMove(event.x, event.y);
					}
				}
			};

			this.mCarbonMouseUpListener = new Listener() {
				public void handleEvent(final Event event) {
					MonthPick.this.mBelowRun = false;
					MonthPick.this.mAboveRun = false;

					mouseUp(null);
				}
			};

			Display.getDefault().addFilter(SWT.MouseMove, this.mCarbonMouseMoveListener);
			Display.getDefault().addFilter(SWT.MouseUp, this.mCarbonMouseUpListener);

			addListener(SWT.MouseExit, new Listener() {
				public void handleEvent(final Event event) {
					// if mouse exited east or west, ignore
					if (event.x < 0 || event.x > getSize().x) {
						return;
					}

					// mous exit top or bottom, run thread
					if (event.y < 0) {
						MonthPick.this.mBelowRun = false;
						runAbove();
					} else {
						MonthPick.this.mAboveRun = false;
						runBelow();
					}
				}
			});

			addListener(SWT.MouseEnter, new Listener() {
				public void handleEvent(final Event event) {
					// halt threads
					MonthPick.this.mAboveRun = false;
					MonthPick.this.mBelowRun = false;
				}
			});
		} else {
			addListener(SWT.MouseMove, new Listener() {
				public void handleEvent(final Event event) {
					mouseMove(event.x, event.y);
				}
			});
		}

		addDisposeListener(new DisposeListener() {
			public void widgetDisposed(final DisposeEvent e) {
				setCapture(false);

				if (CalendarComposite.OS_CARBON) {
					Display.getDefault().removeFilter(SWT.MouseMove,
							MonthPick.this.mCarbonMouseMoveListener);
					Display.getDefault().removeFilter(SWT.MouseUp,
							MonthPick.this.mCarbonMouseUpListener);
				}
			}
		});

		addPaintListener(new PaintListener() {
			public void paintControl(final PaintEvent event) {
				paint(event);
			}
		});

		// on carbon the shell doesn't die if it's stuck open and the mouse is
		// clicked out of bounds, so we kill it on mouse down (month has time to
		// be set prior)
		if (CalendarComposite.OS_CARBON) {
			this.mDisposeListener = new Listener() {
				public void handleEvent(final Event e) {
					Display.getDefault().removeFilter(SWT.MouseDown,
							MonthPick.this.mDisposeListener);
					dispose();
				}
			};

			Display.getDefault().addFilter(SWT.MouseDown, this.mDisposeListener);
		}

	}

	private void paint(final PaintEvent event) {
		GC gc = event.gc;

		// fonts are disposed when calendar is disposed
		Font used = null;
		if (CalendarComposite.OS_CARBON) {
			used = this.mSettings.getCarbonDrawFont();
			if (used != null) {
				gc.setFont(used);
			}
		} else if (CalendarComposite.OS_WINDOWS) {
			used = this.mSettings.getWindowsMonthPopupDrawFont();
			if (used != null) {
				gc.setFont(used);
			}
		}

		// double buffering. this could be triple buffering if the platform does
		// it automatically, windows XP does not seem to however
		// basically, we draw all the updates onto an Image in memory, then we
		// transfer the contents thereof onto the canvas.
		// that way there is 0 flicker, which is the desired effect.
		if (this.mCreated && this.mEnableDoubleBuffering) {
			try {
				Image buffer = new Image(Display.getDefault(), super.getBounds());
				GC gc2 = new GC(buffer);
				drawOntoGC(gc2);

				// transfer the image buffer onto this canvas
				// just drawImage(buffer, w, h) didn't work, so we do the whole
				// source transfer call
				Rectangle b = getBounds();
				gc.drawImage(buffer, 0, 0, b.width, b.height, 0, 0, b.width, b.height);

				// dispose the buffer, very important or we'll run out of
				// address space for buffered images
				buffer.dispose();
				gc2.dispose();
			} catch (IllegalArgumentException iea) {
				// seems to come here for some reason when we switch phases
				// while the gantt chart is being viewed, I'm not sure why
				// but no time to figure it out for the demo.. so instead of
				// buffering, just draw it onto the GC
				drawOntoGC(gc);
			}
		} else {
			drawOntoGC(gc);
			this.mCreated = true;
		}

		// don't dispose font, they are disposed when the CalendarCombo are
		// disposed
	}

	private void drawOntoGC(final GC gc) {
		this.mBounds = super.getBounds();

		drawBG(gc);
		drawDates(gc);
		drawBorder(gc);
	}

	private void drawBG(final GC gc) {
		gc.setForeground(ColorCache.getBlack());
		gc.setBackground(ColorCache.getWhite());
		gc.fillRectangle(this.mBounds.x - 50, this.mBounds.y, this.mBounds.width + 50,
				this.mBounds.height);
	}

	private void drawBorder(final GC gc) {
		Rectangle bounds = getClientArea();
		gc.setForeground(ColorCache.getBlack());
		gc.drawRectangle(bounds.x, bounds.y, bounds.width - 1, bounds.height - 1);
	}

	private void drawDates(final GC gc) {
		int y = this.mTopDateSpacer;
		int spacer = 2;

		Calendar temp = Calendar.getInstance(this.mLocale);
		temp.setTime(this.mStart.getTime());

		temp.add(Calendar.MONTH, -3);

		if (!CalendarComposite.OS_CARBON) {
			gc.setFont(this.mSettings.getWindowsMonthPopupDrawFont());
		} else {
			gc.setFont(this.mSettings.getCarbonDrawFont());
		}

		gc.setForeground(ColorCache.getBlack());

		this.mMonthEntries.clear();

		// gc.setBackground(ColorCache.getWhite());
		// gc.fillRectangle(bounds);

		for (int i = 0; i < this.mMonthsToShow; i++) {
			int mo = temp.get(Calendar.MONTH);
			String toDraw = this.mMonths[mo] + " " + temp.get(Calendar.YEAR);
			Point p = gc.stringExtent(toDraw);

			// center the text in the available space
			int rest = this.mInnerWidth - p.x;
			rest /= 2;

			Rectangle rect = new Rectangle(0, y - 1, this.mBounds.width, p.y + 1);

			if (this.mHoverEntry != null) {
				// draw hover entry
				if (this.mHoverEntry.getRect().equals(rect)) {
					gc.setBackground(ColorCache.getBlack());
					gc.fillRectangle(this.mHoverEntry.getRect());
					gc.setBackground(ColorCache.getWhite());
					gc.setForeground(ColorCache.getWhite());
					// lastHoverRect = hoverEntry.getRect();
					this.mSelectedMonth = this.mHoverEntry.getCalendar();
				}
			} else {
				// draw today
				if (i == 3) {
					gc.setBackground(ColorCache.getBlack());
					gc.fillRectangle(rect);
					gc.setBackground(ColorCache.getWhite());
					gc.setForeground(ColorCache.getWhite());
				}
			}

			gc.drawString(toDraw, rest, y, true);
			gc.setForeground(ColorCache.getBlack());

			MonthEntry me = new MonthEntry(temp);
			me.setRect(rect);
			me.setyPos(y);
			me.setText(toDraw);
			this.mMonthEntries.add(me);

			temp.add(Calendar.MONTH, 1);
			y += p.y + spacer;
		}
	}

	public void mouseDoubleClick(final MouseEvent event) {

	}

	public void mouseDown(final MouseEvent event) {

	}

	public void mouseUp(final MouseEvent event) {
		this.mCalendarComposite.mouseUp(event);
		if (this.mSelectedMonth != null) {
			this.mCalendarComposite.setDate(this.mSelectedMonth);
		}

		dispose();
	}

	public void mouseMove(final int x, final int y) {

		for (int i = 0; i < this.mMonthEntries.size(); i++) {
			MonthEntry mEntry = (MonthEntry) this.mMonthEntries.get(i);
			if (isInside(x, y, mEntry.getRect())) {
				this.mHoverEntry = mEntry;
				this.mAboveRun = false;
				this.mBelowRun = false;
				redraw();
				return;
			}
		}
		this.mHoverEntry = null;

		if (!CalendarComposite.OS_CARBON) {
			if (y < getLocation().y) {
				runAbove();
			} else {
				runBelow();
			}
		}
	}

	private void runAbove() {
		if (this.mAboveThread != null && this.mAboveThread.isAlive() && this.mAboveRun) {
			return;
		}

		this.mBelowRun = false;

		this.mAboveThread = new Thread() {
			@Override
			public void run() {
				while (MonthPick.this.mAboveRun) {
					try {
						sleep(MonthPick.this.sleepTime);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					Display.getDefault().asyncExec(new Runnable() {
						public void run() {
							if (isDisposed()) {
								MonthPick.this.mAboveThread = null;
							} else {
								scrollOneMonth(true);
							}
						}
					});
				}
			}
		};
		this.mAboveRun = true;
		this.mAboveThread.start();
	}

	private void runBelow() {
		if (this.mBelowThread != null && this.mBelowThread.isAlive() && this.mBelowRun) {
			return;
		}

		this.mBelowRun = false;

		this.mBelowThread = new Thread() {
			@Override
			public void run() {
				while (MonthPick.this.mBelowRun) {
					try {
						sleep(MonthPick.this.sleepTime);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					Display.getDefault().asyncExec(new Runnable() {
						public void run() {
							if (isDisposed()) {
								MonthPick.this.mBelowThread = null;
							} else {
								scrollOneMonth(false);
							}
						}
					});
				}
			}
		};
		this.mBelowRun = true;
		this.mBelowThread.start();
	}

	private void scrollOneMonth(final boolean up) {
		if (up) {
			this.mStart.add(Calendar.MONTH, -1);
			redraw();
		} else {
			this.mStart.add(Calendar.MONTH, 1);
			redraw();
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

	class MonthEntry {

		private final Calendar mCal;

		private Rectangle mRect;

		private int mYPos;

		private String text;

		public MonthEntry(final Calendar cal) {
			this.mCal = (Calendar) cal.clone();
		}

		public Calendar getCalendar() {
			return this.mCal;
		}

		public Rectangle getRect() {
			return this.mRect;
		}

		public void setRect(final Rectangle rect) {
			this.mRect = rect;
		}

		public int getyPos() {
			return this.mYPos;
		}

		public void setyPos(final int yPos) {
			this.mYPos = yPos;
		}

		public String getText() {
			return this.text;
		}

		public void setText(final String text) {
			this.text = text;
		}
	}
}
