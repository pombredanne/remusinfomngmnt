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

import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.core.extension.AbstractCreationFactory;
import org.remus.infomngmnt.core.model.InformationUtil;
import org.remus.infomngmnt.task.TaskActivator;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class TaskCreationFactory extends AbstractCreationFactory {

	@Override
	public InformationUnit createNewObject() {
		InformationUnit returnValue = super.createNewObject();
		returnValue.setType(TaskActivator.INFO_TYPE_ID);
		returnValue.getChildValues().add(InformationUtil.createNew(TaskActivator.NODE_NAME_STATUS));
		returnValue.getChildValues()
				.add(InformationUtil.createNew(TaskActivator.NODE_NAME_STARTED));
		returnValue.getChildValues().add(
				InformationUtil.createNew(TaskActivator.NODE_NAME_COMPLETED_PERCENTAGE));
		returnValue.getChildValues()
				.add(InformationUtil.createNew(TaskActivator.NODE_NAME_ASIGNEE));
		returnValue.getChildValues().add(
				InformationUtil.createNew(TaskActivator.NODE_NAME_PRIORITY));
		returnValue.getChildValues().add(
				InformationUtil.createNew(TaskActivator.NODE_NAME_DUE_DATE));
		returnValue.getChildValues().add(InformationUtil.createNew(TaskActivator.NODE_NAME_NOTIFY));
		returnValue.getChildValues().add(
				InformationUtil.createNew(TaskActivator.NODE_NAME_MINUTES_BEFORE_DUE));
		returnValue.getChildValues().add(
				InformationUtil.createNew(TaskActivator.NODE_NAME_ESTIMATED_EFFORTS));
		returnValue.getChildValues().add(
				InformationUtil.createNew(TaskActivator.NODE_NAME_WORKED_UNITS));
		returnValue.getChildValues().add(
				InformationUtil.createNew(TaskActivator.NODE_NAME_COMPLETED));
		return returnValue;
	}

}
