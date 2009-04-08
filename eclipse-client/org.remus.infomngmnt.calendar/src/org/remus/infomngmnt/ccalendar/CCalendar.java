/****************************************************************************
 * Copyright (c) 2005-2006 Jeremy Dowdall
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Jeremy Dowdall <aspencloud@users.sourceforge.net> - initial API and implementation
 *****************************************************************************/

package org.remus.infomngmnt.ccalendar;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.aspencloud.calypso.ui.calendar.activities.ActivityPart;
import org.aspencloud.calypso.ui.calendar.activitiesCalendar.ActivitiesCalendarPart;
import org.aspencloud.calypso.ui.calendar.factories.ActivitiesCalendarPartFactory;
import org.aspencloud.calypso.ui.calendar.factories.TasksCalendarPartFactory;
import org.aspencloud.calypso.ui.calendar.tasks.TaskPart;
import org.aspencloud.calypso.ui.calendar.tasksCalendar.TasksCalendarFigure;
import org.aspencloud.calypso.ui.calendar.tasksCalendar.TasksCalendarModel;
import org.aspencloud.calypso.ui.calendar.tasksCalendar.TasksCalendarPart;
import org.aspencloud.calypso.ui.workbench.views.calendar.actions.CreateAction;
import org.aspencloud.calypso.util.CButton;
import org.aspencloud.calypso.util.CalypsoEdit;
import org.aspencloud.calypso.util.TimeSpan;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.RangeModel;
import org.eclipse.draw2d.Viewport;
import org.eclipse.draw2d.geometry.PrecisionDimension;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.gef.EditDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.editparts.ScalableRootEditPart;
import org.eclipse.gef.ui.parts.GraphicalViewerKeyHandler;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import org.remus.infomngmnt.calendar.CalendarPlugin;
import org.remus.infomngmnt.calendar.model.CEvent;
import org.remus.infomngmnt.calendar.model.Task;
import org.remus.infomngmnt.calendar.model.Tasklist;
import org.remus.infomngmnt.ccalendar.service.ICalendarChangeSupport;
import org.remus.infomngmnt.ccalendar.service.ICalendarService;
import org.remus.infomngmnt.ccalendar.service.IDirtyTimespanListener;

public class CCalendar extends Composite implements PropertyChangeListener,
		ISelectionChangedListener, ISelectionProvider {

	public static final String PROP_DATE = "date";
	public static final String PROP_TYPE = "type";
	public static final String PROP_DETAIL = "detail";

	public static final int TYPE_UNDEFINED = -1;
	public static final int TYPE_SINGLETON = 0;
	public static final int TYPE_LIST = 1;
	public static final int TYPE_MATRIX = 2;

	private static final Calendar tmpcal = Calendar.getInstance(Locale.getDefault()); // TODO...

	private TasksCalendarModel acModel;
	private TasksCalendarModel tcModel;
	private Date[] dates = new Date[0];
	private int detailDate = -1;

	private Composite header;
	private CButton[] buttons = new CButton[0];
	private SashForm body;
	private Composite footer;

	private CalendarViewer acViewer;
	private CalendarViewer tcViewer;
	private final TasksCalendarFigure acFigure;
	private final TasksCalendarFigure tcFigure;
	private Label headerLabel;
	private ToolItem dayCal;
	private ToolItem workWeekCal;
	private ToolItem weekCal;
	private ToolItem nextWeek;
	private ToolItem nextDay;
	private ToolItem today;
	private ToolItem prevDay;
	private ToolItem prevWeek;
	private final String buttonLabelFormat = "EE d";
	private int calendarType = TYPE_LIST;
	private int detailType = TYPE_LIST;

	public int marginHeight = 0;
	public int marginWidth = 0;

	private ISelection selection = new StructuredSelection();
	private final List selectionListeners = new ArrayList();
	private final PropertyChangeSupport propertyListeners = new PropertyChangeSupport(this);
	private Image dailyImage;
	private Image workWeekImage;
	private Image weekImage;
	private Image nextWeekImage;
	private Image nextDayImage;
	private Image moveToTodayImage;
	private Image prevWeekImage;
	private Image prevDayImage;
	public static final String PLUGIN_ID = "org.remus.infomngmnt.calendar";

	private final Adapter taskNotification = new EContentAdapter() {
		@Override
		public void notifyChanged(final Notification msg) {
			if (msg.getNotifier() instanceof CEvent && msg.getEventType() == Notification.SET) {
				CCalendar.this.calendarService.updateTask((Task) ((EObject) msg.getNotifier())
						.eContainer());
			}
		};
	};

	private final IDirtyTimespanListener listener = new IDirtyTimespanListener() {

		@Override
		public void handleDirtyTimeSpan(final TimeSpan timespan) {
			Date start = CCalendar.this.dates[0];
			Date end = CCalendar.this.dates[CCalendar.this.dates.length - 1];
			TimeSpan newTimeSpan = new TimeSpan(start, end);
			if (newTimeSpan.contains(timespan.getStartDate())
					|| newTimeSpan.contains(timespan.getEndDate())) {
				Display.getDefault().asyncExec(new Runnable() {
					@Override
					public void run() {
						updateInput();
					}

				});
			}
		}

	};

	private final ICalendarService calendarService;

	private final ICalendarChangeSupport calendarChangeSupport;

	private class CalendarLayout extends Layout {

		private int previousWidth = -1;

		@Override
		protected Point computeSize(final Composite composite, final int wHint, final int hHint,
				final boolean flushCache) {
			Point headerSize = CCalendar.this.header.computeSize(SWT.DEFAULT, SWT.DEFAULT);
			Point bodySize = CCalendar.this.body.computeSize(SWT.DEFAULT, SWT.DEFAULT);
			Point footerSize = CCalendar.this.footer.computeSize(SWT.DEFAULT, SWT.DEFAULT);

			int buttonHeight = 0;
			for (int i = 0; i < CCalendar.this.buttons.length; i++) {
				buttonHeight = Math.max(buttonHeight, CCalendar.this.buttons[i].computeSize(
						SWT.DEFAULT, SWT.DEFAULT).y);
			}

			Point size = new Point(Math.max(headerSize.x, Math.max(bodySize.x, footerSize.x)),
					headerSize.y + buttonHeight + bodySize.y + footerSize.y);

			size.x += CCalendar.this.marginWidth + CCalendar.this.marginWidth;
			size.y += CCalendar.this.marginHeight + CCalendar.this.marginHeight;

			if (wHint != SWT.DEFAULT) {
				size.x = Math.min(size.x, wHint);
			}
			if (hHint != SWT.DEFAULT) {
				size.y = Math.min(size.y, hHint);
			}
			return size;
		}

		@Override
		protected void layout(final Composite composite, final boolean flushCache) {
			Rectangle r = composite.getClientArea();

			Point headerSize = CCalendar.this.header.computeSize(SWT.DEFAULT, SWT.DEFAULT);
			Point bodySize = CCalendar.this.body.computeSize(SWT.DEFAULT, SWT.DEFAULT);
			Point footerSize = CCalendar.this.footer.computeSize(SWT.DEFAULT, SWT.DEFAULT);

			int buttonHeight = 0;
			for (int i = 0; i < CCalendar.this.buttons.length; i++) {
				buttonHeight = Math.max(buttonHeight, CCalendar.this.buttons[i].computeSize(
						SWT.DEFAULT, SWT.DEFAULT).y);
			}

			bodySize.y = r.height
					- (r.y + CCalendar.this.marginHeight + CCalendar.this.marginHeight
							+ headerSize.y + buttonHeight + footerSize.y);

			CCalendar.this.header
					.setBounds(
							r.x + CCalendar.this.marginWidth,
							r.y + CCalendar.this.marginHeight,
							r.width
									- (r.x + CCalendar.this.marginWidth + CCalendar.this.marginWidth),
							headerSize.y);

			CCalendar.this.body.setBounds(r.x + CCalendar.this.marginWidth, r.y
					+ CCalendar.this.marginHeight + headerSize.y + buttonHeight, r.width
					- (r.x + CCalendar.this.marginWidth + CCalendar.this.marginWidth), bodySize.y);

			CCalendar.this.body.layout(true, true);

			CCalendar.this.footer.setBounds(r.x + CCalendar.this.marginWidth, r.y
					+ CCalendar.this.marginHeight + headerSize.y + buttonHeight + bodySize.y,
					r.width - (r.x + CCalendar.this.marginWidth + CCalendar.this.marginWidth),
					footerSize.y);

			// if((r.width != previousWidth) || flushCache) {
			// previousWidth = r.width;

			int lblWidth = CCalendar.this.tcFigure.getRowLabelWidth();
			if ((CCalendar.this.buttons.length > 0) && (CCalendar.this.buttons[0] != null)
					&& !CCalendar.this.buttons[0].isDisposed()) {
				lblWidth = Math.max(lblWidth, CCalendar.this.buttons[0].computeSize(SWT.DEFAULT,
						SWT.DEFAULT).x);
			}

			if ((r.width != this.previousWidth) || flushCache) {
				this.previousWidth = r.width;
				CCalendar.this.acFigure.setLabelSize(lblWidth, SWT.DEFAULT);
				CCalendar.this.tcFigure.setLabelSize(lblWidth, SWT.DEFAULT);
			}

			double dayWidth = (double) CCalendar.this.tcFigure.getTaskArea().width
					/ (double) CCalendar.this.dates.length;

			if (CCalendar.this.buttons.length > 0) {
				CCalendar.this.buttons[0].setBounds(r.x + CCalendar.this.marginWidth, r.y
						+ CCalendar.this.marginHeight + headerSize.y, lblWidth + 1, buttonHeight);

				int buttonX = r.x + CCalendar.this.marginWidth + lblWidth + 1;
				int buttonNextX = r.x + CCalendar.this.marginWidth + lblWidth + ((int) dayWidth)
						+ 2;
				for (int i = 1; i < CCalendar.this.buttons.length; i++) {
					CCalendar.this.buttons[i].setBounds(buttonX, r.y + CCalendar.this.marginHeight
							+ headerSize.y, buttonNextX - buttonX, buttonHeight);
					buttonX = r.x + CCalendar.this.marginWidth + lblWidth + ((int) (dayWidth * i))
							+ 2;
					buttonNextX = r.x + CCalendar.this.marginWidth + lblWidth
							+ ((int) (dayWidth * (i + 1))) + 2;
				}
			}
			// }
		}
	}

	public CCalendar(final Composite parent, final int style) {
		super(parent, style);

		setBackground(getDisplay().getSystemColor(SWT.COLOR_WHITE));

		// GridLayout layout = new GridLayout(1, false);
		// layout.marginHeight = 0;
		// layout.marginWidth = 0;
		// layout.horizontalSpacing = 0;
		// layout.verticalSpacing = 0;
		// setLayout(layout);

		setLayout(new CalendarLayout());
		initImages();
		this.calendarService = CalendarPlugin.getDefault().getService(ICalendarService.class);
		this.calendarChangeSupport = CalendarPlugin.getDefault().getService(
				ICalendarChangeSupport.class);
		this.calendarChangeSupport.addTimeSpanListener(this.listener);
		createContents();

		this.acViewer.addSelectionChangedListener(this);
		this.tcViewer.addSelectionChangedListener(this);

		this.body.setWeights(new int[] { 20, 80 });

		this.acFigure = (TasksCalendarFigure) ((ActivitiesCalendarPart) this.acViewer.getContents())
				.getFigure();
		this.tcFigure = (TasksCalendarFigure) ((TasksCalendarPart) this.tcViewer.getContents())
				.getFigure();

		// addControlListener(new ControlAdapter() {
		// public void controlResized(ControlEvent e) {
		// syncColumnWidths();
		// }
		// });

		((FigureCanvas) this.tcViewer.getControl()).getViewport().getVerticalRangeModel()
				.addPropertyChangeListener(this);

		this.acViewer.setKeyHandler(new GraphicalViewerKeyHandler(this.acViewer) {
			@Override
			public boolean keyPressed(final KeyEvent event) {
				if (event.keyCode == SWT.DEL) {
					List eObjects = new ArrayList();
					for (Iterator i = CCalendar.this.acViewer.getSelectedEditParts().iterator(); i
							.hasNext();) {
						Object model = ((EditPart) i.next()).getModel();
						if (model instanceof EObject) {
							eObjects.add(model);
						}
					}
					// CalypsoEdit.delete(eObjects);
				}
				return super.keyPressed(event);
			}
		});
		this.tcViewer.setKeyHandler(new GraphicalViewerKeyHandler(this.tcViewer) {
			@Override
			public boolean keyPressed(final KeyEvent event) {
				if (event.keyCode == SWT.DEL) {
					List eObjects = new ArrayList();
					for (Iterator i = CCalendar.this.tcViewer.getSelectedEditParts().iterator(); i
							.hasNext();) {
						Object model = ((EditPart) i.next()).getModel();
						if (model instanceof EObject) {
							eObjects.add(model);
						}
					}
					CalypsoEdit.delete(eObjects);
				}
				return super.keyPressed(event);
			}
		});

		updateButtons();
	}

	/**
	 * Creates the images we need for switching through the different views or
	 * to move on the timeline.
	 */
	private void initImages() {

		this.dailyImage = AbstractUIPlugin.imageDescriptorFromPlugin(PLUGIN_ID,
				"icons/iconexperience/16/calendar_1.png").createImage();
		this.workWeekImage = AbstractUIPlugin.imageDescriptorFromPlugin(PLUGIN_ID,
				"icons/iconexperience/16/calendar_5.png").createImage();
		this.weekImage = AbstractUIPlugin.imageDescriptorFromPlugin(PLUGIN_ID,
				"icons/iconexperience/16/calendar_7.png").createImage();

		this.nextWeekImage = AbstractUIPlugin.imageDescriptorFromPlugin(PLUGIN_ID,
				"icons/iconexperience/16/navigate_end.png").createImage();
		this.nextDayImage = AbstractUIPlugin.imageDescriptorFromPlugin(PLUGIN_ID,
				"icons/iconexperience/16/navigate_right.png").createImage();
		this.moveToTodayImage = AbstractUIPlugin.imageDescriptorFromPlugin(PLUGIN_ID,
				"icons/iconexperience/16/bullet_ball_blue.png").createImage();
		this.prevWeekImage = AbstractUIPlugin.imageDescriptorFromPlugin(PLUGIN_ID,
				"icons/iconexperience/16/navigate_beginning.png").createImage();
		this.prevDayImage = AbstractUIPlugin.imageDescriptorFromPlugin(PLUGIN_ID,
				"icons/iconexperience/16/navigate_left.png").createImage();

	}

	public void propertyChange(final PropertyChangeEvent event) {
		if (RangeModel.PROPERTY_EXTENT.equals(event.getPropertyName())) {
			scrollToTime(7, 0, 0, 0);

			((FigureCanvas) this.tcViewer.getControl()).getViewport().getVerticalRangeModel()
					.removePropertyChangeListener(this);
		}
	}

	public Date getTopVisibleDate() {
		org.eclipse.draw2d.geometry.Point point = ((FigureCanvas) this.tcViewer.getControl())
				.getViewport().getViewLocation();
		point.translate(this.tcFigure.getTaskArea().getLocation());
		return ((TasksCalendarPart) this.tcViewer.getContents()).getDateFromPoint(point);
	}

	public Date getMidVisibleDate() {
		Viewport vp = ((FigureCanvas) this.tcViewer.getControl()).getViewport();
		org.eclipse.draw2d.geometry.Point point = vp.getViewLocation();
		point.x += this.tcFigure.getTaskArea().x;
		point.y += (vp.getClientArea().height / 2) + 2;
		return ((TasksCalendarPart) this.tcViewer.getContents()).getDateFromPoint(point);
	}

	public Date getBottomVisibleDate() {
		org.eclipse.draw2d.geometry.Point point = ((FigureCanvas) this.tcViewer.getControl())
				.getViewport().getViewLocation();
		point.translate(((FigureCanvas) this.tcViewer.getControl()).getViewport().getSize());
		point.translate(this.tcFigure.getTaskArea().getLocation());
		return ((TasksCalendarPart) this.tcViewer.getContents()).getDateFromPoint(point);
	}

	public void scrollTimeToTop(final Calendar cal) {
		scrollToTime(cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal
				.get(Calendar.SECOND), cal.get(Calendar.MILLISECOND));
	}

	public void scrollToTime(final int hourOfDay, final int min, final int sec, final int milli) {
		tmpcal.set(Calendar.HOUR_OF_DAY, hourOfDay);
		tmpcal.set(Calendar.MINUTE, min);
		tmpcal.set(Calendar.SECOND, sec);
		tmpcal.set(Calendar.MILLISECOND, milli);
		org.eclipse.draw2d.geometry.Point p = ((TasksCalendarPart) this.tcViewer.getContents())
				.getPointFromDate(tmpcal.getTime());
		if (p != null) {
			int vOffset = p.y;
			vOffset -= this.tcFigure.getTaskArea().y;// ((tcFigure.getRowLabelHeight()
			// / 2));
			((FigureCanvas) this.tcViewer.getControl()).scrollToY(vOffset);
		}
	}

	/**
	 * Adds a listener for selection-open to this CCalendar's viewers. Has no
	 * effect if an identical listener is already registered.
	 * 
	 * @param listener
	 *            a double-click listener
	 */
	public void addOpenListener(final Listener listener) {
		this.acViewer.addOpenListener(listener);
		this.tcViewer.addOpenListener(listener);
	}

	public void addPropertyChangeListener(final PropertyChangeListener pcl) {
		this.propertyListeners.addPropertyChangeListener(pcl);
	}

	private void createContents() {
		this.header = createHeader();
		GridData data = new GridData(SWT.FILL, SWT.FILL, false, false);
		this.header.setLayoutData(data);

		this.body = new SashForm(this, SWT.VERTICAL);
		this.body.setBackground(ColorConstants.lightGray);
		data = new GridData(SWT.FILL, SWT.FILL, true, true);
		this.body.setLayoutData(data);

		createActivities(this.body);
		createTasks(this.body);

		this.footer = createFooter();
		data = new GridData(SWT.FILL, SWT.FILL, false, false);
		this.footer.setLayoutData(data);
		this.footer.addPaintListener(new PaintListener() {
			public void paintControl(final PaintEvent e) {
				Rectangle r = CCalendar.this.footer.getBounds();
				e.gc.setForeground(ColorConstants.lightGray);
				e.gc.drawLine(0, 0, r.width, 0);
			}
		});
	}

	private Composite createHeader() {
		Composite contents = new Composite(this, SWT.NONE);
		contents.setBackground(getDisplay().getSystemColor(SWT.COLOR_WHITE));
		GridLayout layout = new GridLayout(1, false);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		layout.horizontalSpacing = 0;
		layout.verticalSpacing = 0;
		contents.setLayout(layout);

		Composite top = new Composite(contents, SWT.NONE);
		top.setBackground(contents.getBackground());
		layout = new GridLayout(1, false);
		layout.marginBottom = 0;
		layout.marginWidth = 0;
		layout.horizontalSpacing = 0;
		layout.verticalSpacing = 0;
		top.setLayout(layout);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		// data.horizontalSpan = 9;
		top.setLayoutData(data);

		this.headerLabel = new Label(top, SWT.NONE);
		this.headerLabel.setAlignment(SWT.CENTER);
		this.headerLabel.setBackground(top.getBackground());
		data = new GridData(SWT.FILL, SWT.CENTER, true, true);
		this.headerLabel.setLayoutData(data);

		return contents;
	}

	private Composite createActivities(final Composite parent) {
		// create
		this.acViewer = new CalendarViewer();
		this.acViewer.createControl(parent);

		// edit domain
		this.acViewer.setEditDomain(new EditDomain());

		// configure
		ScalableRootEditPart root = new ScalableRootEditPart();
		this.acViewer.setRootEditPart(root);
		this.acViewer.setEditPartFactory(new ActivitiesCalendarPartFactory());
		this.acViewer.getControl().setBackground(ColorConstants.listBackground);
		((FigureCanvas) this.acViewer.getControl())
				.setHorizontalScrollBarVisibility(FigureCanvas.NEVER);
		((FigureCanvas) this.acViewer.getControl())
				.setVerticalScrollBarVisibility(FigureCanvas.ALWAYS);

		// initialize model
		this.acModel = new TasksCalendarModel(false);
		this.acViewer.setContents(this.acModel);

		return (Composite) this.acViewer.getControl();
	}

	private Composite createTasks(final Composite parent) {
		// create
		this.tcViewer = new CalendarViewer();
		this.tcViewer.createControl(parent);

		// edit domain
		this.tcViewer.setEditDomain(new EditDomain());

		// configure
		ScalableRootEditPart root = new ScalableRootEditPart();
		this.tcViewer.setRootEditPart(root);
		this.tcViewer.setEditPartFactory(new TasksCalendarPartFactory());
		this.tcViewer.getControl().setBackground(ColorConstants.listBackground);
		((FigureCanvas) this.tcViewer.getControl())
				.setHorizontalScrollBarVisibility(FigureCanvas.NEVER);
		((FigureCanvas) this.tcViewer.getControl())
				.setVerticalScrollBarVisibility(FigureCanvas.ALWAYS);

		// initialize model
		this.tcModel = new TasksCalendarModel();
		this.tcModel.setNumRowLabels(25);
		this.tcViewer.setContents(this.tcModel);

		return (Composite) this.tcViewer.getControl();
	}

	private Composite createFooter() {
		Composite contents = new Composite(this, SWT.NONE);
		contents.setBackground(getDisplay().getSystemColor(SWT.COLOR_WHITE));
		GridLayout layout = new GridLayout(2, false);
		layout.marginHeight = 0;
		layout.marginTop = 2;
		layout.marginBottom = 1;
		layout.marginWidth = 0;
		layout.horizontalSpacing = 0;
		layout.verticalSpacing = 0;
		contents.setLayout(layout);

		ToolBar tb = new ToolBar(contents, SWT.FLAT);
		tb.setBackground(contents.getBackground());
		GridData data = new GridData(SWT.LEFT, SWT.FILL, true, false);
		tb.setLayoutData(data);

		this.dayCal = new ToolItem(tb, SWT.RADIO);
		this.dayCal.setImage(this.dailyImage);
		this.dayCal.setToolTipText("Day Calendar");
		this.dayCal.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				if (hasDetailDate()) {
					setCalendarToDate(getDetailDate());
				} else if ((CCalendar.this.dates.length > 0) && (CCalendar.this.dates[0] != null)) {
					setCalendarToDate(CCalendar.this.dates[0]);
				} else {
					setCalendarToDate(new Date());
				}
			}
		});

		this.workWeekCal = new ToolItem(tb, SWT.RADIO);
		this.workWeekCal.setImage(this.workWeekImage);
		this.workWeekCal.setToolTipText("Work-Week Calendar");
		this.workWeekCal.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				if (hasDetailDate()) {
					setCalendarToWorkWeekContaining(getDetailDate());
				} else if ((CCalendar.this.dates.length > 0) && (CCalendar.this.dates[0] != null)) {
					setCalendarToWorkWeekContaining(CCalendar.this.dates[0]);
				} else {
					setCalendarToWorkWeekContaining(new Date());
				}
			}
		});

		this.weekCal = new ToolItem(tb, SWT.RADIO);
		this.weekCal.setImage(this.weekImage);
		this.weekCal.setToolTipText("Week Calendar");
		this.weekCal.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				if (hasDetailDate()) {
					setCalendarToWeekContaining(getDetailDate());
				} else if ((CCalendar.this.dates.length > 0) && (CCalendar.this.dates[0] != null)) {
					setCalendarToWeekContaining(CCalendar.this.dates[0]);
				} else {
					setCalendarToWeekContaining(new Date());
				}
			}
		});

		// ti = new ToolItem(tb, SWT.RADIO);
		// ti.setImage(CProperties.getImage(CProperties.IMG_CALENDAR_MONTH));
		// ti.setToolTipText("Month Calendar");

		tb = new ToolBar(contents, SWT.FLAT);
		tb.setBackground(contents.getBackground());
		data = new GridData(SWT.RIGHT, SWT.FILL, false, false);
		data.heightHint = 20;
		tb.setLayoutData(data);

		this.prevWeek = new ToolItem(tb, SWT.PUSH);
		this.prevWeek.setToolTipText("Move Backward One Week");
		this.prevWeek.setImage(this.prevWeekImage);
		this.prevWeek.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				adjustDates(Calendar.DATE, -7);
			}
		});

		this.prevDay = new ToolItem(tb, SWT.PUSH);
		this.prevDay.setToolTipText("Move Backward One Day");
		this.prevDay.setImage(this.prevDayImage);
		this.prevDay.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				adjustDates(Calendar.DATE, -1);
			}
		});

		this.today = new ToolItem(tb, SWT.PUSH);
		this.today.setToolTipText("Current Week");
		this.today.setImage(this.moveToTodayImage);
		this.today.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				setCalendarToWeekContaining(new Date());
			}
		});

		this.nextDay = new ToolItem(tb, SWT.PUSH);
		this.nextDay.setToolTipText("Move Forward One Day");
		this.nextDay.setImage(this.nextDayImage);
		this.nextDay.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				adjustDates(Calendar.DATE, 1);
			}
		});

		this.nextWeek = new ToolItem(tb, SWT.PUSH);
		this.nextWeek.setToolTipText("Move Forward One Week");
		this.nextWeek.setImage(this.nextWeekImage);
		this.nextWeek.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				adjustDates(Calendar.DATE, 7);
			}
		});

		return contents;
	}

	@Override
	public void dispose() {
		this.calendarChangeSupport.removeTimeSpanListener(this.listener);
		this.acModel.dispose();
		this.tcModel.dispose();
		this.dailyImage.dispose();
		this.workWeekImage.dispose();
		this.weekImage.dispose();
		this.moveToTodayImage.dispose();
		this.nextDayImage.dispose();
		this.nextWeekImage.dispose();
		this.prevDayImage.dispose();
		this.prevWeekImage.dispose();
		super.dispose();
	}

	protected void firePropertyChange(final String propName, final Object old, final Object newValue) {
		this.propertyListeners.firePropertyChange(propName, old, newValue);
	}

	public GraphicalViewer getActivitiesViewer() {
		return this.acViewer;
	}

	// public Button[] getButtons() {
	// return buttons;
	// }

	public SashForm getBody() {
		return this.body;
	}

	public GraphicalViewer getTasksViewer() {
		return this.tcViewer;
	}

	private boolean setDetailType(final int detailType) {
		boolean result = false;
		switch (this.calendarType) {
		case TYPE_SINGLETON:
			if (detailType == TYPE_SINGLETON) {
				this.detailType = detailType;
				result = true;
			}
			break;
		case TYPE_LIST:
			if ((detailType == TYPE_SINGLETON) || (detailType == TYPE_LIST)) {
				this.detailType = detailType;
				result = true;
			}
			break;
		case TYPE_MATRIX:
			if ((detailType == TYPE_SINGLETON) || (detailType == TYPE_LIST)
					|| (detailType == TYPE_MATRIX)) {
				this.detailType = detailType;
				result = true;
			}
			break;
		default:
		}

		return result;
	}

	private Date getDetailDate() {
		if ((this.detailDate >= 0) && (this.detailDate < this.dates.length)) {
			return this.dates[this.detailDate];
		}
		return null;
	}

	private boolean hasDetailDate() {
		if ((this.detailDate >= 0) && (this.detailDate < this.dates.length)) {
			return (this.dates[this.detailDate] != null);
		}
		return false;
	}

	// private boolean setDetailDate(Date date) {
	// tmpcal.setTime(date);
	// int d0 = tmpcal.get(Calendar.DAY_OF_YEAR);
	// int d1 = tmpcal.get(Calendar.YEAR);
	// int i = 0;
	// for(; i < dates.length; i++) {
	// int d2 = tmpcal.get(Calendar.DAY_OF_YEAR);
	// int d3 = tmpcal.get(Calendar.YEAR);
	// if((d0 == d2) && (d1 == d3)) break;
	// }
	// return setDetailDate(i);
	// }
	private boolean setDetailDate(final int date) {
		if ((date >= 0) && (date < this.dates.length)) {
			this.detailDate = date;
			return true;
		}
		return false;
	}

	private void handleButton(final Button button) {
		if (button.getSelection()) {
			for (int i = 0; i < this.buttons.length; i++) {
				if (!this.buttons[i].getButton().equals(button)) {
					this.buttons[i].setSelection(false);
				}
			}
		} else {
			this.buttons[0].setSelection(true);
		}

		for (int i = 0; i < this.buttons.length; i++) {
			if (this.buttons[i].getSelection()) {
				if (i == 0) {
					if (setDetailType(TYPE_LIST)) {
						updateInput();
						updateActivitiesCalendar();
						updateTasksCalendar();
						break;
					}
				} else {
					// TODO: will buttons.length always be 1 > dates.length?
					if (setDetailDate(i - 1) && setDetailType(TYPE_SINGLETON)) {
						updateInput();
						updateActivitiesCalendar();
						updateTasksCalendar();
						break;
					}
				}
			}
		}
	}

	public void removePropertyChangeListener(final PropertyChangeListener pcl) {
		this.propertyListeners.removePropertyChangeListener(pcl);
	}

	public void adjustDates(final int field, final int amount) {
		setRedraw(false);

		for (int i = 0; i < this.dates.length; i++) {
			tmpcal.setTime(this.dates[i]);
			tmpcal.add(field, amount);
			this.dates[i] = tmpcal.getTime();
		}
		updateInput();
		updateHeader();
		updateButtons();
		updateActivitiesCalendar();
		updateTasksCalendar();

		// syncColumnWidths();

		setRedraw(true);
	}

	private void updateButtons() {
		// TODO: buttons always 1 > dates? (extra button is "week" button...
		// "zoom" button?)
		if (this.buttons.length != (this.dates.length + 1)) {
			for (int i = 0; i < this.buttons.length; i++) {
				this.buttons[i].dispose();
			}

			this.buttons = new CButton[this.dates.length + 1];

			// first button
			if (this.buttons.length == 2) {
				this.buttons[0] = new CButton(this, SWT.TOGGLE);
			} else {
				this.buttons[0] = new CButton(this, SWT.TOGGLE);
				this.buttons[0].addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(final SelectionEvent e) {
						handleButton((Button) e.widget);
					}
				});
			}

			// the rest of the buttons
			for (int i = 1; i < this.buttons.length; i++) {
				this.buttons[i] = new CButton(this, SWT.TOGGLE);
				this.buttons[i].addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(final SelectionEvent e) {
						handleButton((Button) e.widget);
					}
				});
			}
			if (this.calendarType == TYPE_LIST) {
				this.buttons[0].setSelection(true);
			}
		}

		if (this.calendarType == TYPE_LIST) {
			if (this.dates.length == 7) {
				this.buttons[0].setText("Week");
			} else if (this.dates.length == 5) {
				this.buttons[0].setText("Week");
			}
		}

		SimpleDateFormat sdf = new SimpleDateFormat(this.buttonLabelFormat);
		for (int i = 1; i < this.buttons.length; i++) {
			// TODO: will buttons.length always be 1 > dates.length?
			this.buttons[i].setText(sdf.format(this.dates[i - 1]));
		}

		layout(true, true);
	}

	public void setCalendarToDate(final Date date) {
		if (date != null) {
			setDates(new Date[] { date }, 1, 1);
		}
	}

	public void setCalendarToWorkWeekContaining(final Date date) {
		if (date != null) {
			Date[] dates = new Date[5];

			tmpcal.setTime(date);
			tmpcal.set(Calendar.DAY_OF_WEEK, 2);
			tmpcal.set(Calendar.HOUR_OF_DAY, 0);
			tmpcal.set(Calendar.MINUTE, 0);
			tmpcal.set(Calendar.SECOND, 0);
			tmpcal.set(Calendar.MILLISECOND, 0);
			for (int i = 0; i < dates.length; i++) {
				dates[i] = tmpcal.getTime();
				tmpcal.add(Calendar.DATE, 1);
			}

			setDates(dates, dates.length, 1);
			setDetailType(TYPE_LIST);
		}
	}

	public void setCalendarToWeekContaining(final Date date) {
		if (date != null) {
			Date[] dates = new Date[7];

			tmpcal.setTime(date);
			tmpcal.set(Calendar.DAY_OF_WEEK, 1);
			tmpcal.set(Calendar.HOUR_OF_DAY, 0);
			tmpcal.set(Calendar.MINUTE, 0);
			tmpcal.set(Calendar.SECOND, 0);
			tmpcal.set(Calendar.MILLISECOND, 0);
			for (int i = 0; i < dates.length; i++) {
				dates[i] = tmpcal.getTime();
				tmpcal.add(Calendar.DATE, 1);
			}

			setDates(dates, dates.length, 1);
			setDetailType(TYPE_LIST);
		}
	}

	public void setDate(final Date date) {
		setDates(new Date[] { date }, 1, 1);
	}

	public void setDates(final Date[] dates, final int cols, final int rows) {
		if ((dates.length <= 0) || (dates.length != (cols * rows))) {
			return;
		}

		int tmpType = TYPE_UNDEFINED;
		if (cols > 1) {
			if (rows > 1) {
				tmpType = TYPE_MATRIX;
			} else if (rows == 1) {
				tmpType = TYPE_LIST;
			}
		} else if (cols == 1) {
			if (rows > 1) {
				tmpType = TYPE_LIST;
			} else if (rows == 1) {
				tmpType = TYPE_SINGLETON;
			}
		}
		if (tmpType == TYPE_UNDEFINED) {
			return;
		} else {
			Integer old = new Integer(this.calendarType);
			this.calendarType = tmpType;

			setRedraw(false);

			this.dates = dates;
			if (!setDetailDate(this.detailDate)) {
				setDetailDate(0);
			}

			updateInput();
			updateHeader();
			updateButtons();
			updateActivitiesCalendar();
			updateTasksCalendar();
			updateCalTypeToolbar();

			// syncColumnWidths();

			setRedraw(true);

			firePropertyChange(PROP_TYPE, old, new Integer(this.calendarType));
		}
	}

	private void updateCalTypeToolbar() {
		this.dayCal.setSelection(this.calendarType == TYPE_SINGLETON);
		this.weekCal.setSelection((this.calendarType == TYPE_LIST) && (this.dates.length == 7));
		this.workWeekCal.setSelection((this.calendarType == TYPE_LIST) && (this.dates.length == 5));
	}

	private void updateActivitiesCalendar() {
		int len;
		if (this.detailType == TYPE_SINGLETON) {
			len = 1;
		} else {
			len = this.dates.length;
		}

		if (len != this.acModel.getNumDays()) {
			this.acModel.setNumDays(len);
		}

		this.acFigure.clearDayColors();
		if (this.detailType == TYPE_SINGLETON) {
			tmpcal.setTime(getDetailDate());
			this.acModel.setDay(0, tmpcal.get(Calendar.DAY_OF_YEAR), tmpcal.get(Calendar.YEAR));
			// don't bother shading a single day (they zoomed in on it for a
			// reason didn't they?!?)
		} else {
			for (int i = 0; i < this.dates.length; i++) {
				tmpcal.setTime(this.dates[i]);
				this.acModel.setDay(i, tmpcal.get(Calendar.DAY_OF_YEAR), tmpcal.get(Calendar.YEAR));
				if ((tmpcal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
						|| (tmpcal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)) {
					this.acFigure.setDayColor(i, ColorConstants.button.getRGB());
				}
			}
		}

		this.acFigure.repaint();
	}

	private void updateHeader() {
		String str = "";
		if (hasDetailDate()) {
			str = new SimpleDateFormat("MMMM yyyy").format(getDetailDate());
		}
		this.headerLabel.setText(str);
	}

	private void updateTasksCalendar() {
		int len;
		if (this.detailType == TYPE_SINGLETON) {
			len = 1;
		} else {
			len = this.dates.length;
		}

		if (len != this.tcModel.getNumDays()) {
			this.tcModel.setNumDays(len);
		}

		this.tcFigure.clearDayColors();
		if (this.detailType == TYPE_SINGLETON) {
			tmpcal.setTime(getDetailDate());
			this.tcModel.setDay(0, tmpcal.get(Calendar.DAY_OF_YEAR), tmpcal.get(Calendar.YEAR));
			// don't bother shading a single day (they zoomed in on it for a
			// reason didn't they?!?)
		} else {
			for (int i = 0; i < this.dates.length; i++) {
				tmpcal.setTime(this.dates[i]);
				this.tcModel.setDay(i, tmpcal.get(Calendar.DAY_OF_YEAR), tmpcal.get(Calendar.YEAR));
				if ((tmpcal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
						|| (tmpcal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)) {
					this.tcFigure.setDayColor(i, ColorConstants.button.getRGB());
				}
			}
		}

		this.tcFigure.repaint();
	}

	// private void syncColumnWidths() {
	// setRedraw(false);
	//
	// int lblWidth = tcFigure.getRowLabelWidth();
	// if((buttons.length > 0) && (buttons[0] != null) &&
	// !buttons[0].isDisposed()) {
	// lblWidth = Math.max(lblWidth, buttons[0].getWidth());
	// }
	// int dayWidth = (int) tcFigure.getTaskArea().width / (dates.length);
	//
	// acFigure.setLabelSize(lblWidth, -1);
	// tcFigure.setLabelSize(lblWidth, -1);
	//		
	// spacers[0].setWidth((lblWidth - buttons[0].getWidth()) / 2);
	// spacers[1].setWidth((lblWidth - buttons[0].getWidth()) - ((lblWidth -
	// buttons[0].getWidth()) / 2));
	// for(int i = 1; i < buttons.length; i++) {
	// spacers[2*i].setWidth((dayWidth - buttons[i].getWidth()) / 2);
	// spacers[(2*i)+1].setWidth((dayWidth - buttons[i].getWidth()) - ((dayWidth
	// - buttons[i].getWidth()) / 2));
	// }
	//
	// setRedraw(true);
	// }

	public void setContentProvider(final IStructuredContentProvider provider) {
		this.acModel.setContentProvider(provider);
		this.tcModel.setContentProvider(provider);
	}

	public void setInput(final Tasklist input) {
		this.acModel.setInput(input);
		this.tcModel.setInput(input);
	}

	public TasksCalendarModel getTasksCalendarModel() {
		return this.tcModel;
	}

	public void addSelectionChangedListener(final ISelectionChangedListener listener) {
		if (!this.selectionListeners.contains(listener)) {
			this.selectionListeners.add(listener);
		}
	}

	public ISelection getSelection() {
		return this.selection;
	}

	public Control[] getControlsForContextMenu() {
		return new Control[] { getActivitiesViewer().getControl(), getTasksViewer().getControl() };
	}

	public CreateAction getCreateAction() {
		return new CreateAction(new GraphicalViewer[] { getTasksViewer(), getActivitiesViewer() });

	}

	public void removeSelectionChangedListener(final ISelectionChangedListener listener) {
		this.selectionListeners.remove(listener);
	}

	public void setSelection(final ISelection selection) {
		this.selection = selection;
	}

	private void fireSelectionChanged() {
		SelectionChangedEvent event = new SelectionChangedEvent(this, getSelection());
		Object la[] = this.selectionListeners.toArray();
		for (int i = 0; i < la.length; i++) {
			((ISelectionChangedListener) la[i]).selectionChanged(event);
		}
	}

	public void selectionChanged(final SelectionChangedEvent event) {
		if (event.getSource().equals(this.acViewer)) {
			this.tcViewer.removeSelectionChangedListener(this);
			this.tcViewer.setSelection(event.getSelection());
			this.tcViewer.addSelectionChangedListener(this);
		} else { // assumes event.getSource().equals(tcViewer) for now...
			this.acViewer.removeSelectionChangedListener(this);
			this.acViewer.setSelection(event.getSelection());
			this.acViewer.addSelectionChangedListener(this);
		}
		List list = new ArrayList();
		for (Iterator i = ((StructuredSelection) event.getSelection()).iterator(); i.hasNext();) {
			Object o = i.next();
			if (o instanceof TaskPart) {
				list.add(((TaskPart) o).getModel());
			} else if (o instanceof ActivityPart) {
				list.add(((ActivityPart) o).getModel());
			}
		}
		if (!list.isEmpty() || !this.selection.isEmpty()) {
			this.selection = new StructuredSelection(list);
			fireSelectionChanged();
		}
	}

	public void zoom(final double x, final double y) {
		tmpcal.setTime(getMidVisibleDate());

		PrecisionDimension d1 = new PrecisionDimension(this.tcFigure.getPreferredSize());
		PrecisionDimension d2 = new PrecisionDimension(((FigureCanvas) this.tcViewer.getControl())
				.getViewport().getSize());

		if (d1.width > 0) {
			d1.preciseWidth = Math.max(d1.preciseWidth * x, d2.preciseWidth);
		}
		if (d1.height > 0) {
			d1.preciseHeight = Math.max(d1.preciseHeight * y, d2.preciseHeight);
		}

		setRedraw(false);
		d1.updateInts();
		this.tcFigure.setPreferredSize(d1);
		this.tcFigure.getUpdateManager().performUpdate();

		int top = ((TasksCalendarPart) this.tcViewer.getContents()).getPointFromDate(tmpcal
				.getTime()).y;
		top -= this.tcFigure.getTaskArea().y;
		top -= ((FigureCanvas) this.tcViewer.getControl()).getViewport().getClientArea().height / 2;
		((FigureCanvas) this.tcViewer.getControl()).scrollToY(top);
		setRedraw(true);
	}

	@Override
	public void setRedraw(final boolean redraw) {
		super.setRedraw(redraw);
		this.header.setRedraw(redraw);
		for (int i = 0; i < this.buttons.length; i++) {
			this.buttons[i].setRedraw(redraw);
		}
		this.body.setRedraw(redraw);
		this.acViewer.getControl().setRedraw(redraw);
		this.tcViewer.getControl().setRedraw(redraw);
		this.footer.setRedraw(redraw);
	}

	public void updateInput() {
		Date startDate = this.dates[0];
		Date endDate = this.dates[this.dates.length - 1];
		TimeSpan ts = new TimeSpan(startDate, endDate);

		if (this.calendarService != null) {
			Tasklist input = this.tcModel.getInput();
			if (input != null) {
				EList<Task> tasks = input.getTasks();
				for (Task task : tasks) {
					task.eAdapters().remove(this.taskNotification);
				}
			}
			setInput(this.calendarService.getTasksForTimespan(ts));
			input = this.tcModel.getInput();
			if (input != null) {
				EList<Task> tasks = input.getTasks();
				for (Task task : tasks) {
					task.eAdapters().add(this.taskNotification);
				}
			}
		}
	}
}
