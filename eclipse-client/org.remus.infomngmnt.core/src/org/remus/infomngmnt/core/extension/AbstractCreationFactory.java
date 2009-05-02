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

import java.util.Date;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;

import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.InformationUnit;

/**
 * The Creation Factory is a class which describes how to create special
 * information unit objects.
 * 
 * 
 * @author Tom Seidel <toms@tomosch.de>
 * @since 1.0
 */
public abstract class AbstractCreationFactory {

	protected EditingDomain editingDomain;

	/**
	 * Creates a new Information unit. At this place you have to create the
	 * structure to create a minimalistic information object which matches the
	 * corresponding information type. Per Default the creation date is set.
	 * 
	 * @return the newly created information object.
	 */
	public InformationUnit createNewObject() {
		InformationUnit newInfoObject = InfomngmntFactory.eINSTANCE.createInformationUnit();
		newInfoObject.setCreationDate(new Date());
		return newInfoObject;
	}

	/**
	 * @param unit
	 * @param monitor
	 * @return
	 */
	public Command handlePreSaving(final InformationUnit unit, final IProgressMonitor monitor) {
		// does nothing by default.
		return null;
	}

}
