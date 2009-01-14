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

package org.remus.infomngmnt.core.extension;

import org.remus.infomngmnt.InformationUnit;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public interface ISaveParticipant {
	
	public static final int BEFORE_SAVE = 1;
	public static final int SAVED = 2;
	public static final int CREATED = 3;
	public static final int BEFORE_DELETE = 4;
	public static final int DELETED = 5;
	
	void handleEvent(int eventId, InformationUnit unit);

}
