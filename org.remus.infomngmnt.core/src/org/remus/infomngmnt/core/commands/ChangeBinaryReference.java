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

package org.remus.infomngmnt.core.commands;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import org.remus.infomngmnt.BinaryReference;
import org.remus.infomngmnt.InformationUnit;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class ChangeBinaryReference extends CompoundCommand {

	public ChangeBinaryReference(final BinaryReference oldValue, final IFile tmpFile,
			final EditingDomain editingDomain) {
		InformationUnit infoUnit = (InformationUnit) oldValue.eContainer();
		setLabel("Set new binary data");
		append(new DeleteBinaryReferenceCommand(oldValue, editingDomain));
		append(new CreateBinaryReferenceCommand(tmpFile, infoUnit, editingDomain));
	}
}
