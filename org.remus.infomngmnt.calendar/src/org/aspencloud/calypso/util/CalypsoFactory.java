package org.aspencloud.calypso.util;

import java.util.Date;

import org.remus.infomngmnt.calendar.model.EndEvent;
import org.remus.infomngmnt.calendar.model.ModelFactory;
import org.remus.infomngmnt.calendar.model.ModelPackage;
import org.remus.infomngmnt.calendar.model.StartEvent;
import org.remus.infomngmnt.calendar.model.Task;
import org.remus.infomngmnt.calendar.model.Tasklist;

public class CalypsoFactory {
	
	private static boolean success;
	
	public static boolean alarmEnd;
	private static boolean alarmStart;
	
	private static ModelFactory mfactory = ModelFactory.eINSTANCE;
	private static ModelPackage mpackage = ModelPackage.eINSTANCE;
	
	public static Task createTask(final String name) {
		return createTask(name, null);
	}

	
	
	
	public static Task createTask(final String name, final Date start, final Date end) {
		Task task = createTask(name, null);
		task.getStart().setDate(start);
		task.getEnd().setDate(end);
		return task;
	}
	
	public static Task createTask(final String name, final Tasklist owner) {
		success = false;
		StartEvent start = null;
		EndEvent end = null;
		Task task = null;
		
		try {
			

			// create new objects
			start = mfactory.createStartEvent();
			end = mfactory.createEndEvent();
			task = mfactory.createTask();
			
			// setup new objects
			
		
			
			start.setName("Start");
			start.setDate(new Date(System.currentTimeMillis() + 5*TimeSpan.MINUTE));
			start.setAlarm(alarmStart);
			
			end.setName("End");
			end.setDate(new Date(start.getDate().getTime() + 15*TimeSpan.MINUTE));
			end.setAlarm(alarmEnd);
	
			task.setName(name);
			task.setStart(start);
			task.setEnd(end);
			task.setOwner(owner);
			
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(!success) {
				//CalypsoEdit.delete(new EObject[] { start, end, task, activity } );
				System.out.println("FAILURE: createTask @ " + System.currentTimeMillis());
			}
			//CalypsoManager.getAutoSave().setActive(true);
		}
		
		return task;
	}
	
	
	

}
