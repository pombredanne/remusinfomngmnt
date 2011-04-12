package org.remus.infomngmnt.meetingminutes.messages;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.remus.infomngmnt.meetingminutes.messages.messages"; //$NON-NLS-1$
	public static String AdditionalLogsPage_Decisions;
	public static String AdditionalLogsPage_TODOs;
	public static String AttachmentsEditPage_Add;
	public static String AttachmentsEditPage_Attachments;
	public static String AttachmentsEditPage_Attendees;
	public static String AttachmentsEditPage_ConfirmOpenAttachments;
	public static String AttachmentsEditPage_ConfirmOpenAttachmentsDetails;
	public static String AttachmentsEditPage_Delete;
	public static String AttachmentsEditPage_DeleteAttachments;
	public static String AttachmentsEditPage_Edit;
	public static String AttachmentsEditPage_ErrorUploadingfile;
	public static String MeetingEditPage_End;
	public static String MeetingEditPage_General;
	public static String MeetingEditPage_Location;
	public static String MeetingEditPage_Log;
	public static String MeetingEditPage_Moderator;
	public static String MeetingEditPage_Name;
	public static String MeetingEditPage_Start;
	public static String NewMeetingWizard_NewAppointment;
	public static String NewMeetingWizard_Subtitle;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
