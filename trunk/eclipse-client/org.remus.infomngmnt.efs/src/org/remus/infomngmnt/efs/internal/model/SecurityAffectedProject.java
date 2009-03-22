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

package org.remus.infomngmnt.efs.internal.model;

import org.eclipse.core.resources.IProject;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class SecurityAffectedProject {

	private final IProject project;

	private final SecurityWrapper parentProvider;

	public SecurityAffectedProject(final IProject project,
			final SecurityWrapper parentProvider) {
		super();
		this.project = project;
		this.parentProvider = parentProvider;
	}

	/**
	 * @return the project
	 */
	public IProject getProject() {
		return this.project;
	}

	/**
	 * @return the parent provider
	 */
	public SecurityWrapper getParentProvider() {
		return this.parentProvider;
	}

}
