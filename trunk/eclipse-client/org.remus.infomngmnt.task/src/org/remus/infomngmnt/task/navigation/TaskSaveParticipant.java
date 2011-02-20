/*******************************************************************************
 * Copyright (c) 2011 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/
package org.remus.infomngmnt.task.navigation;

import org.eclipse.core.resources.IProject;
import org.eclipse.remus.InformationUnit;
import org.eclipse.remus.core.extension.ISaveParticipant;
import org.remus.infomngmnt.task.TaskActivator;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class TaskSaveParticipant implements ISaveParticipant {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.remus.core.extension.ISaveParticipant#handleCreated(org.eclipse
	 * .remus.InformationUnit)
	 */
	public void handleCreated(InformationUnit newValue) {
		if (TaskActivator.INFO_TYPE_ID.equals(newValue.getType())) {
			TaskActivator.getDefault().getStore().update(newValue);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.remus.core.extension.ISaveParticipant#handleChanged(org.eclipse
	 * .remus.InformationUnit, org.eclipse.remus.InformationUnit)
	 */
	public void handleChanged(InformationUnit oldValue, InformationUnit newValue) {
		if (TaskActivator.INFO_TYPE_ID.equals(newValue.getType())) {
			TaskActivator.getDefault().getStore().update(newValue);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.remus.core.extension.ISaveParticipant#handleDeleted(java.
	 * lang.String)
	 */
	public void handleDeleted(String informationUnitId) {
		TaskActivator.getDefault().getStore().delete(informationUnitId);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.remus.core.extension.ISaveParticipant#handleClean(org.eclipse
	 * .core.resources.IProject)
	 */
	public void handleClean(IProject project) {
		// TODO Auto-generated method stub

	}

}
