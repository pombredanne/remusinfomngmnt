/*******************************************************************************
 * Copyright (c) 2010 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/

package org.remus.infomngmnt.common.ui.swt;

import org.eclipse.swt.widgets.Display;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public abstract class PostEvent {

	protected final Display display;

	protected PostEvent(final Display display) {
		this.display = display;
	}

	protected abstract void post();

}
