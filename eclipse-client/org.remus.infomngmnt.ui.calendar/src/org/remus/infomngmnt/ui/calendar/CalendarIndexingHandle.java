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

package org.remus.infomngmnt.ui.calendar;

import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.remus.common.core.util.ResourceUtil;
import org.eclipse.remus.resources.util.IPostProjectHandle;


/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class CalendarIndexingHandle implements IPostProjectHandle {

	/**
	 * 
	 */
	public CalendarIndexingHandle() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.resources.util.IPostProjectHandle#postProjectCreation
	 * (org.eclipse.core.resources.IProjectDescription)
	 */
	public void postProjectCreation(final IProjectDescription project) {
		ResourceUtil.addBuilder(project, CalendarBuilder.BUILDER_ID);
	}

}
