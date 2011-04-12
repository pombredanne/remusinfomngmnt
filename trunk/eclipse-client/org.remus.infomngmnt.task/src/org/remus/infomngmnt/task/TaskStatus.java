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

package org.remus.infomngmnt.task;

import org.remus.infomngmnt.task.messages.Messages;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public enum TaskStatus {

	NONE, IN_PROGRESS, DEFERRED, NOT_STARTED, WAITING;

	@Override
	public String toString() {
		switch (this) {
		case IN_PROGRESS:
			return Messages.TaskStatus_InProgress;
		case DEFERRED:
			return Messages.TaskStatus_Deferred;
		case NOT_STARTED:
			return Messages.TaskStatus_NotStarted;
		case WAITING:
			return Messages.TaskStatus_Waiting;
		default:
			break;
		}
		return Messages.TaskStatus_None;

	};

	public String getKey() {
		switch (this) {
		case IN_PROGRESS:
			return "S1"; //$NON-NLS-1$
		case DEFERRED:
			return "S2"; //$NON-NLS-1$
		case NOT_STARTED:
			return "S3"; //$NON-NLS-1$
		case WAITING:
			return "S4"; //$NON-NLS-1$
		}
		return null;
	}

	public static TaskStatus fromKey(String string) {
		if (IN_PROGRESS.getKey().equals(string)) {
			return IN_PROGRESS;
		} else if (DEFERRED.getKey().equals(string)) {
			return DEFERRED;
		} else if (NOT_STARTED.getKey().equals(string)) {
			return NOT_STARTED;
		} else if (WAITING.getKey().equals(string)) {
			return WAITING;
		}
		return NONE;
	}
}
