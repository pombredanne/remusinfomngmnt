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

package org.remus.infomngmnt.efs.extension;

import org.eclipse.swt.widgets.Shell;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public abstract class AbstractSecurityProvider {

	private boolean initialized;

	private String id;

	private String scheme;

	private String name;

	private String description;

	public final void initProvider(final Shell shell) {
		this.initialized = initialize(shell);
	}

	public final void initProviderForProject(final Shell shell) {
		this.initialized = initializeForProject(shell);
	}

	public abstract boolean initializeForProject(Shell shell);

	public abstract boolean initialize(Shell parentShell);

	/**
	 * @return the initialized
	 */
	public boolean isInitialized() {
		return this.initialized;
	}

	/**
	 * @param initialized
	 *            the initialized to set
	 */
	public void setInitialized(final boolean initialized) {
		this.initialized = initialized;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(final String id) {
		this.id = id;
	}

	/**
	 * @return the fileSystemId
	 */
	public String getScheme() {
		return this.scheme;
	}

	/**
	 * @param fileSystemId
	 *            the fileSystemId to set
	 */
	public void setScheme(final String fileSystemId) {
		this.scheme = fileSystemId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(final String description) {
		this.description = description;
	}

}
