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
package org.remus.infomngmnt.core.extension;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.remus.infomngmnt.InformationUnit;


/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public abstract class AbstractInformationRepresentation {

	private InformationUnit value;

	public AbstractInformationRepresentation() {
		super();
	}

	/**
	 * Executed after the serialization of the Info-Object. Useful
	 * @param derivedFile
	 */
	public abstract void handlePostBuild(IFile derivedFile, IProgressMonitor monitor) throws CoreException;

	public abstract String handleHtmlGeneration(IProgressMonitor monitor) throws CoreException;

	public abstract String getTitleForIndexing(IProgressMonitor monitor) throws CoreException;

	public abstract String getBodyForIndexing(IProgressMonitor monitor) throws CoreException;

	public abstract String getAdditionalsForIndexing(IProgressMonitor monitor) throws CoreException;

	public InformationUnit getValue() {
		return this.value;
	}

	public void setValue(final InformationUnit value) {
		this.value = value;
	}



}
