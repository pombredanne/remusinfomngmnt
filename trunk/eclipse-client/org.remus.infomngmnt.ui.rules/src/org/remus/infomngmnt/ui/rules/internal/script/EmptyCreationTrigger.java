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

package org.remus.infomngmnt.ui.rules.internal.script;

import java.util.Map;

import org.remus.infomngmnt.ui.rules.extension.AbstractCreationTrigger;
import org.remus.infomngmnt.ui.rules.service.ICreationTrigger;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class EmptyCreationTrigger extends AbstractCreationTrigger implements ICreationTrigger {

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.remus.infomngmnt.ui.rules.extension.AbstractCreationTrigger#
	 * handleCreationRequest()
	 */
	@Override
	public void handleCreationRequest() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.ui.rules.service.ICreationTrigger#setOptions(java
	 * .util.Map)
	 */
	public void setOptions(final Map options) {
		// TODO Auto-generated method stub

	}

}
