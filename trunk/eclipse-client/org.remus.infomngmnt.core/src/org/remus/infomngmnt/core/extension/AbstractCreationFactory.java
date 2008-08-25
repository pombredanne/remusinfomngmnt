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
package org.remus.infomngmnt.core.extension;

import java.util.Map;

import org.eclipse.core.commands.Category;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.remus.infomngmnt.InformationUnit;

/**
 * @author Tom Seidel <toms@tomosch.de>
 *
 */
public abstract class AbstractCreationFactory {


	protected EditingDomain editingDomain;


	/**
	 * @param parentNode
	 * @param options
	 * @return
	 */
	public abstract InformationUnit createNewObject(InformationUnit parentNode, Map<Object, Object> options);

	public abstract InformationUnit createNewObject(Category parentNode, Map<Object, Object> options);



}
