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

import java.util.Date;

import org.remus.infomngmnt.task.TaskStatus;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class TaskDecorationObject {

	public TaskDecorationObject(boolean completed, TaskStatus status,
			Date dueDate, String id, String label) {
		super();
		this.completed = completed;
		this.status = status;
		this.dueDate = dueDate;
		this.id = id;
		this.label = label;
	}

	private final boolean completed;

	private final TaskStatus status;

	private final String id;

	private final String label;

	private final Date dueDate;

	public boolean isCompleted() {
		return this.completed;
	}

	public TaskStatus getStatus() {
		return this.status;
	}

	public Date getDueDate() {
		return this.dueDate;
	}

	public String getId() {
		return this.id;
	}

	public String getLabel() {
		return this.label;
	}

}
