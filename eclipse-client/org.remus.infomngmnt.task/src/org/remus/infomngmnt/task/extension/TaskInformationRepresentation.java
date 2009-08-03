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
import org.eclipse.emf.common.util.EList;

import org.remus.infomngmnt.CalendarEntry;
import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.common.core.util.StringUtils;
import org.remus.infomngmnt.core.extension.AbstractInformationRepresentation;
import org.remus.infomngmnt.core.model.InformationStructureRead;
import org.remus.infomngmnt.task.TaskActivator;
import org.remus.infomngmnt.util.InformationUtil;

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
			createCalendarEntry.setStart(instance.getTime());
			instance.add(Calendar.DAY_OF_YEAR, 1);
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
	 * #getBodyForIndexing(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public String getBodyForIndexing(IProgressMonitor monitor) throws CoreException {
		InformationStructureRead read = InformationStructureRead.newSession(getValue());
		String join = StringUtils.join(getValue().getStringValue(), "\n", (String) read
				.getValueByNodeId(TaskActivator.NODE_NAME_ASIGNEE));
		EList<InformationUnit> childValues = InformationUtil.getChildByType(getValue(),
				TaskActivator.NODE_NAME_WORKED_UNITS).getChildValues();
		for (InformationUnit informationUnit : childValues) {
			InformationStructureRead workUnitRead = InformationStructureRead.newSession(
					informationUnit, TaskActivator.INFO_TYPE_ID);
			join = StringUtils.join((String) workUnitRead
					.getValueByNodeId(TaskActivator.NODE_NAME_WORKED_UNIT_DESCRIPTION), "\n");
		}
		return join;
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
