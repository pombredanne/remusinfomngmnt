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

package org.remus.infomngmnt.core.jobs;

import java.util.Date;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;

import org.remus.infomngmnt.Notification;

/**
 * 
 * 
 * @author Tom Seidel <tom.seidel@remus-software.org>
 * @since 1.0
 */
public abstract class AbstractJob {

	private String name;

	private String id;

	private int interval;

	private Date lastExecution;

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

	public void initialize(final IProgressMonitor monitor) {
		// does nothing by default
	}

	public void beforeRun(final IProgressMonitor monitor) {
		// does nothing by default
	}

	public abstract List<Notification> run(IProgressMonitor monitor);

	public void afterRun(final IProgressMonitor monitor) {
		// does nothing by default
	}

	public void dispose(final IProgressMonitor monitor) {
		// does nothing by default
	}

	/**
	 * @return the interval
	 */
	public int getInterval() {
		return this.interval;
	}

	/**
	 * @param interval
	 *            the interval to set
	 */
	public void setInterval(final int interval) {
		this.interval = interval;
	}

	/**
	 * @return the lastExecution
	 */
	public Date getLastExecution() {
		return this.lastExecution;
	}

	/**
	 * @param lastExecution
	 *            the lastExecution to set
	 */
	public void setLastExecution(final Date lastExecution) {
		this.lastExecution = lastExecution;
	}

}
