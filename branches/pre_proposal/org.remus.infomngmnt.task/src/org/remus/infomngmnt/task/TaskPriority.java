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

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public enum TaskPriority {

	NONE, LOW, MEDIUM, HIGH;

	@Override
	public String toString() {
		switch (this) {
		case LOW:
			return "Low";
		case MEDIUM:
			return "Medium";
		case HIGH:
			return "High";
		}
		return "None";
	};

	public String getKey() {
		switch (this) {
		case LOW:
			return "P1";
		case MEDIUM:
			return "P2";
		case HIGH:
			return "P3";
		}
		return null;
	}

	public static TaskPriority fromKey(String string) {
		if (LOW.getKey().equals(string)) {
			return LOW;
		} else if (MEDIUM.getKey().equals(string)) {
			return MEDIUM;
		} else if (HIGH.getKey().equals(string)) {
			return HIGH;
		}
		return NONE;
	}

}
