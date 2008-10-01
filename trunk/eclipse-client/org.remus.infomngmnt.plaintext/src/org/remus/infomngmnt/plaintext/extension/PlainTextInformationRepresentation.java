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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.remus.infomngmnt.core.extension.AbstractInformationRepresentation;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class PlainTextInformationRepresentation extends
AbstractInformationRepresentation {



	@Override
	public void handlePostBuild(IFile derivedFile, IProgressMonitor monitor)
	throws CoreException {
		// TODO Auto-generated method stub

	}

	@Override
	public String handleHtmlGeneration(IProgressMonitor monitor)
	throws CoreException {
		return getValue().getStringValue();
	}

	@Override
	public String getAdditionalsForIndexing(IProgressMonitor monitor)
	throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getBodyForIndexing(IProgressMonitor monitor)
	throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTitleForIndexing(IProgressMonitor monitor)
	throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

}
