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

package org.remus.infomngmnt.connector.twitter.infotype;

import java.io.InputStream;
import java.io.StringWriter;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;

import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.connector.twitter.TwitterActivator;
import org.remus.infomngmnt.core.extension.AbstractInformationRepresentation;
import org.remus.infomngmnt.util.InformationUtil;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class TwitterInformationRepresentation extends AbstractInformationRepresentation {

	/**
	 * 
	 */
	public TwitterInformationRepresentation() {
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
	public String getBodyForIndexing(final IProgressMonitor monitor) throws CoreException {
		InformationUnit childByType = InformationUtil.getChildByType(getValue(),
				TwitterActivator.MESSAGES_ID);
		EList<InformationUnit> childValues = childByType.getChildValues();
		StringWriter sw = new StringWriter();
		for (InformationUnit informationUnit : childValues) {
			String userName = InformationUtil.getChildByType(informationUnit,
					TwitterActivator.MESSAGE_USER_TYPE).getStringValue();
			String content = InformationUtil.getChildByType(informationUnit,
					TwitterActivator.MESSAGE_CONTENT_TYPE).getStringValue();
			sw.append(userName).append(" - ").append(content).append("\n");
		}
		return sw.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.core.extension.AbstractInformationRepresentation
	 * #handleHtmlGeneration(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public InputStream handleHtmlGeneration(final IProgressMonitor monitor) throws CoreException {
		return null;
	}

}
