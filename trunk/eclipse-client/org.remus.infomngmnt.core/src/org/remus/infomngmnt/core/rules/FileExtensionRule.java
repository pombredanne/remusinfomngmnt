/*******************************************************************************
 * Copyright (c) 2008 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/

package org.remus.infomngmnt.core.rules;

import org.remus.infomngmnt.core.extension.AbstractRuleDefinition;
import org.remus.infomngmnt.core.extension.ICondition;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class FileExtensionRule extends AbstractRuleDefinition implements
		ICondition {

	/**
	 * 
	 */
	public FileExtensionRule() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.remus.infomngmnt.core.extension.AbstractRuleDefinition#getStringRepresentation()
	 */
	@Override
	public String getStringRepresentation() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.remus.infomngmnt.core.extension.ICondition#matches()
	 */
	public boolean matches() {
		// TODO Auto-generated method stub
		return false;
	}

}
