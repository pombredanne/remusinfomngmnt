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

package org.remus.infomngmnt.core.progress;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public abstract class DelayedRunnable implements Runnable {

	private boolean cancel;

	boolean running;

	/**
	 * @return the running
	 */
	public final boolean isRunning() {
		return this.running;
	}

	public DelayedRunnable() {
		this.cancel = false;
		this.running = false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		if (!this.cancel) {
			try {
				this.running = true;
				runDelayed();
			} finally {
				this.running = false;
			}
		}
	}

	protected abstract void runDelayed();

	public void cancel() {
		this.cancel = true;
	}

}
