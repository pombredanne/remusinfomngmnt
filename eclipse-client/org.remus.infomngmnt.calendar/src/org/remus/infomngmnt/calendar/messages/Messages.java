package org.remus.infomngmnt.calendar.messages;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.remus.infomngmnt.calendar.messages.messages"; //$NON-NLS-1$
	public static String CCalendar_CurrentWeek;
	public static String CCalendar_Day;
	public static String CCalendar_MoveBackward;
	public static String CCalendar_MoveBackwardDay;
	public static String CCalendar_MoveForward;
	public static String CCalendar_MoveForwardDay;
	public static String CCalendar_Week;
	public static String CCalendar_WeekCalendar;
	public static String CCalendar_WorkWeek;
	public static String CreateAction_Create;
	public static String CreateAction_ToggleCreation;
	public static String CreateTaskCommand_NewTask;
	public static String DeleteTaskCommand_ConfirmDelete;
	public static String DeleteTaskCommand_Delete;
	public static String EditModelAction_Edit;
	public static String MoveToTodayAction_MoveTaskToToday;
	public static String MoveToTodayAction_MoveToToday;
	public static String RemoveAction_Delete;
	public static String RemoveAction_DeleteConfirm;
	public static String ShowGridAction_Grid;
	public static String ShowGridAction_ToggleGrid;
	public static String ZoomInAction_ZoomIn;
	public static String ZoomInAction_ZoomOut;
	public static String ZoomOutAction_ZoomOut;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
