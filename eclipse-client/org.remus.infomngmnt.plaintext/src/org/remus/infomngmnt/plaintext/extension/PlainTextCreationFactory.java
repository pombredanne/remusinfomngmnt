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

package org.remus.infomngmnt.plaintext.extension;

import java.util.Map;

import org.eclipse.core.commands.Category;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.core.extension.AbstractCreationFactory;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class PlainTextCreationFactory extends AbstractCreationFactory {

	/* (non-Javadoc)
	 * @see org.remus.infomngmnt.core.extension.AbstractCreationFactory#createNewObject(org.remus.infomngmnt.InformationUnit, java.util.Map)
	 */
	@Override
	public InformationUnit createNewObject(InformationUnit parentNode,
			Map<Object, Object> options) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.remus.infomngmnt.core.extension.AbstractCreationFactory#createNewObject(org.eclipse.core.commands.Category, java.util.Map)
	 */
	@Override
	public InformationUnit createNewObject(Category parentNode,
			Map<Object, Object> options) {
		// TODO Auto-generated method stub
		return null;
	}

}
