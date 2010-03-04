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

package org.remus.infomngmnt.ui.rules.script;

import java.io.InputStream;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import org.remus.infomngmnt.core.extension.AbstractInformationRepresentation;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class InformationRepresentation extends AbstractInformationRepresentation {

	/**
	 * 
	 */
	public InformationRepresentation() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.remus.infomngmnt.core.extension.AbstractInformationRepresentation#getBodyForIndexing(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public String getBodyForIndexing(IProgressMonitor monitor) throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.remus.infomngmnt.core.extension.AbstractInformationRepresentation#handleHtmlGeneration(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public InputStream handleHtmlGeneration(IProgressMonitor monitor) throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

}