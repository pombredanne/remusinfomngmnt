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

package org.remus.infomngmnt.task.extension;

import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import org.remus.infomngmnt.CalendarEntry;
import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.core.extension.AbstractInformationRepresentation;
import org.remus.infomngmnt.core.model.InformationStructureRead;
import org.remus.infomngmnt.task.TaskActivator;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class TaskInformationRepresentation extends AbstractInformationRepresentation {

	/**
	 * 
	 */
	public TaskInformationRepresentation() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public CalendarEntry[] getCalendarContributions() {
		InformationStructureRead read = InformationStructureRead.newSession(getValue());
		Date dueDate = (Date) read.getValueByNodeId(TaskActivator.NODE_NAME_DUE_DATE);
		if (dueDate != null) {
			CalendarEntry createCalendarEntry = InfomngmntFactory.eINSTANCE.createCalendarEntry();
			Calendar instance = Calendar.getInstance();
			instance.setTime(dueDate);
			instance.set(Calendar.HOUR, 0);
			instance.set(Calendar.MINUTE, 0);
			instance.set(Calendar.SECOND, 0);
			instance.set(Calendar.AM_PM, Calendar.AM);
			createCalendarEntry.setStart(instance.getTime());
			instance = Calendar.getInstance();
			instance.setTime(dueDate);
			instance.add(Calendar.DAY_OF_YEAR, 1);
			instance.set(Calendar.HOUR, 0);
			instance.set(Calendar.MINUTE, 0);
			instance.set(Calendar.SECOND, 0);
			instance.set(Calendar.AM_PM, Calendar.AM);
			createCalendarEntry.setEnd(instance.getTime());
			return new CalendarEntry[] { createCalendarEntry };
		}
		return super.getCalendarContributions();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.core.extension.AbstractInformationRepresentation
	 * #handleHtmlGeneration(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public InputStream handleHtmlGeneration(IProgressMonitor monitor) throws CoreException {
		return null;
	}

}
