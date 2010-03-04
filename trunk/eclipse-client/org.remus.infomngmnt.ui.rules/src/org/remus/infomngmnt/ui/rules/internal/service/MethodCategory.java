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

package org.remus.infomngmnt.ui.rules.internal.service;

import java.util.ArrayList;
import java.util.List;

import org.remus.infomngmnt.ui.rules.extension.IMethodCategory;
import org.remus.infomngmnt.ui.rules.extension.IMethodDefinition;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class MethodCategory implements IMethodCategory {

	private final List<IMethodDefinition> methods;
	private final String id;
	private final String name;

	public MethodCategory(final String id, final String name) {
		this.id = id;
		this.name = name;
		this.methods = new ArrayList<IMethodDefinition>();
	}

	public List<IMethodDefinition> getDefinitions() {
		return this.methods;
	}

	public String getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

}
