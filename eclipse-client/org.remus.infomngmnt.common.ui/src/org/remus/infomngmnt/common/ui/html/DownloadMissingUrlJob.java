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

package org.remus.infomngmnt.common.ui.html;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.osgi.util.NLS;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public abstract class DownloadMissingUrlJob extends Job {

	private final IFile target;

	public DownloadMissingUrlJob(final String url, final IFile target) {
		super(NLS.bind("Download url {0}", url));
		this.target = target;
	}

	/**
	 * @return the target
	 */
	public IFile getTarget() {
		return this.target;
	}

}
