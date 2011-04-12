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

package org.remus.infomngmnt.mail;

import org.remus.infomngmnt.mail.messages.Messages;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public enum ContentType {

	PLAIN, HTML;

	@Override
	public String toString() {
		switch (this) {
		case PLAIN:
			return Messages.ContentType_Plain;
		case HTML:
			return Messages.ContentType_Html;
		}
		return Messages.ContentType_None;
	};

	public String getKey() {
		switch (this) {
		case PLAIN:
			return "PLAIN"; //$NON-NLS-1$
		case HTML:
			return "HTML"; //$NON-NLS-1$
		}
		return null;
	}

	public static ContentType fromKey(final String string) {
		if (PLAIN.getKey().equals(string)) {
			return PLAIN;
		} else if (HTML.getKey().equals(string)) {
			return HTML;
		}
		return null;
	}

}
