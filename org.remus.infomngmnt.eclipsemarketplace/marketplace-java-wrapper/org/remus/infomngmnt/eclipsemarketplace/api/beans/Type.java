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

package org.remus.infomngmnt.eclipsemarketplace.api.beans;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public enum Type {

	RESOURCE("resource"), SOLUTION("solution"), TRAINING("training");

	private String string;

	Type(final String str) {
		this.string = str;
	}

	public static Type fromString(final String string) {
		Type[] values = values();
		for (Type type : values) {
			if (string.equals(type.string)) {
				return type;
			}
		}
		return null;
	}

}
