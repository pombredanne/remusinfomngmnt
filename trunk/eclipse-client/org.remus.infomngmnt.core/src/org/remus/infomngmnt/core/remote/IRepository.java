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

package org.remus.infomngmnt.core.remote;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.swt.graphics.Image;

import org.remus.infomngmnt.RemoteContainer;
import org.remus.infomngmnt.RemoteObject;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public interface IRepository {
	
	/**
	 * Returns an image (typically 16x16) which represents
	 * a specific repository through the whole application.
	 * @return the repository specific image.
	 * @since 1.0
	 */
	Image getImage();
	
	/**
	 * @return
	 */
	String getLabel();
	
	String getId();

	void login(ILoginCallBack callback, IProgressMonitor monitor);

	String getRepositoryUrl();
	
	RemoteObject[] getChildren(IProgressMonitor monitor, RemoteContainer container);
	
	IStatus validate();
	
	void setCredentialProvider(ICredentialProvider provider);
	
	ICredentialProvider getCredentialProvider();
	
	void reset();
	
	ISchedulingRule getRule();
	

}
