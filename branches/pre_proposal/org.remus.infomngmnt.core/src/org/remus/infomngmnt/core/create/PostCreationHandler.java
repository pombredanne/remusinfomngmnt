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
package org.remus.infomngmnt.core.create;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;

import org.remus.infomngmnt.InformationStructureDefinition;
import org.remus.infomngmnt.InformationUnit;

/**
 * The Creation Factory is a class which describes how to create special
 * information unit objects.
 * 
 * 
 * @author Tom Seidel <toms@tomosch.de>
 * @since 1.0
 */
public abstract class PostCreationHandler {

	/**
	 * The editing domain
	 */
	protected EditingDomain editingDomain;

	protected String infoTypeId;

	protected InformationStructureDefinition strucutureDefinition;

	/**
	 * @param unit
	 * @param monitor
	 * @return
	 */
	public Command handlePreSaving(final InformationUnit unit, final IProgressMonitor monitor) {
		// does nothing by default.
		return null;
	}

	public final void setInfoTypeId(final String infoTypeId) {
		this.infoTypeId = infoTypeId;
	}

	public final void setStrucutureDefinition(
			final InformationStructureDefinition strucutureDefinition) {
		this.strucutureDefinition = strucutureDefinition;
	}

}
