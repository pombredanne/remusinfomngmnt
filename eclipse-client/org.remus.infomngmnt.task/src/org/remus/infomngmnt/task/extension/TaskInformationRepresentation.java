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

package org.remus.infomngmnt.task.extension;

import java.io.InputStream;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;

import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.common.core.util.StringUtils;
import org.remus.infomngmnt.core.extension.AbstractInformationRepresentation;
import org.remus.infomngmnt.task.TaskActivator;
import org.remus.infomngmnt.util.InformationUtil;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class TaskInformationRepresentation extends AbstractInformationRepresentation {

	/**
	 * 
	 */
	public TaskInformationRepresentation() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.core.extension.AbstractInformationRepresentation
	 * #getBodyForIndexing(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public String getBodyForIndexing(IProgressMonitor monitor) throws CoreException {
		String join = StringUtils.join(getValue().getStringValue(), "\n", InformationUtil
				.getChildByType(getValue(), TaskActivator.NODE_NAME_ASIGNEE).getStringValue());
		EList<InformationUnit> childValues = InformationUtil.getChildByType(getValue(),
				TaskActivator.NODE_NAME_WORKED_UNITS).getChildValues();
		for (InformationUnit informationUnit : childValues) {
			join = StringUtils.join(InformationUtil.getChildByType(informationUnit,
					TaskActivator.NODE_NAME_WORKED_UNIT_DESCRIPTION).getStringValue(), "\n");
		}
		return join;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.core.extension.AbstractInformationRepresentation
	 * #handleHtmlGeneration(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public InputStream handleHtmlGeneration(IProgressMonitor monitor) throws CoreException {
		return null;
	}

}
