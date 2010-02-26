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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;

import org.remus.infomngmnt.efs.extension.AbstractSecurityProvider;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class SecurityWrapper {

	private final String name;

	private final AbstractSecurityProvider provider;

	private List<SecurityAffectedProject> affectedProject;

	public SecurityWrapper(final String name, final AbstractSecurityProvider provider) {
		super();
		this.name = name;
		this.provider = provider;
	}

	public String getName() {
		return this.name;
	}

	public AbstractSecurityProvider getProvider() {
		return this.provider;
	}

	public void addProject(final IProject project) {
		if (this.affectedProject == null) {
			this.affectedProject = new ArrayList<SecurityAffectedProject>();
		}
		this.affectedProject.add(new SecurityAffectedProject(project, this));
	}

	/**
	 * @return the affectedProject
	 */
	public List<SecurityAffectedProject> getAffectedProject() {
		return this.affectedProject;
	}

	/**
	 * @param affectedProject
	 *            the affectedProject to set
	 */
	public void setAffectedProject(final List<SecurityAffectedProject> affectedProject) {
		this.affectedProject = affectedProject;
	}

}
