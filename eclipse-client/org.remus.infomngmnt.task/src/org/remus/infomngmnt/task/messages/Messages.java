package org.remus.infomngmnt.task.messages;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.remus.infomngmnt.task.messages.messages"; //$NON-NLS-1$
	public static String CheckTaskDueJob_TaskNeedsYour;
	public static String CheckTaskDueJob_TaskNotCompleted;
	public static String DueDateSection_NextWeek;
	public static String DueDateSection_ThisWeek;
	public static String DueDateSection_Today;
	public static String EnterDescriptionDialog_EnterText;
	public static String EnterDescriptionDialog_EnterWhatYouReDoing;
	public static String EnterDescriptionDialog_NewWorklogDescription;
	public static String NewTaskWizard_Subtitle;
	public static String NewTaskWizard_Title;
	public static String TaskEditPage_Assignee;
	public static String TaskEditPage_Completed;
	public static String TaskEditPage_Completion;
	public static String TaskEditPage_Days;
	public static String TaskEditPage_Description;
	public static String TaskEditPage_DueDate;
	public static String TaskEditPage_General;
	public static String TaskEditPage_Hours;
	public static String TaskEditPage_Minutes;
	public static String TaskEditPage_Notification;
	public static String TaskEditPage_Priority;
	public static String TaskEditPage_Starts;
	public static String TaskEditPage_Status;
	public static String TaskEditPage_Subject;
	public static String TaskEditPage_WithoutReminder;
	public static String TaskPriority_High;
	public static String TaskPriority_Low;
	public static String TaskPriority_Medium;
	public static String TaskPriority_None;
	public static String TaskStatus_Deferred;
	public static String TaskStatus_InProgress;
	public static String TaskStatus_None;
	public static String TaskStatus_NotStarted;
	public static String TaskStatus_Waiting;
	public static String WorkLoggerTray_14;
	public static String WorkLoggerTray_ClicktoSelectTask;
	public static String WorkLoggerTray_SelectionRequired;
	public static String WorkLoggerTray_SelectTask;
	public static String WorkLoggerTray_SelectTaskDetails;
	public static String WorkLoggerTray_StartTimetracking;
	public static String WorkLoggerTray_StopAndDontSave;
	public static String WorkLoggerTray_StopAndSave;
	public static String WorkLoggerTray_Task;
	public static String WorkLogIndexer_WorkItem;
	public static String WorkLogPage_Add;
	public static String WorkLogPage_Delete;
	public static String WorkLogPage_Description;
	public static String WorkLogPage_End;
	public static String WorkLogPage_NoDescription;
	public static String WorkLogPage_SelectedUnitOfWork;
	public static String WorkLogPage_Start;
	public static String WorkLogPage_WorklogDetails;
	public static String WorkLogPage_WorklogHistory;
	public static String WorkLogPage_WorklogHistoryDetails;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
