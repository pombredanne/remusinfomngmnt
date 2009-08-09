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

package org.remus.infomngmnt.util;

import java.util.Map;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.command.OverrideableCommand;
import org.eclipse.emf.edit.command.PasteFromClipboardCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;

import org.remus.infomngmnt.core.commands.PasteInformationUnitCommand;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class RIMEditingDomain extends AdapterFactoryEditingDomain {

	public RIMEditingDomain(final AdapterFactory adapterFactory, final CommandStack commandStack,
			final Map<Resource, Boolean> resourceToReadOnlyMap) {
		super(adapterFactory, commandStack, resourceToReadOnlyMap);
	}

	public RIMEditingDomain(final AdapterFactory adapterFactory, final CommandStack commandStack,
			final ResourceSet resourceSet) {
		super(adapterFactory, commandStack, resourceSet);
	}

	public RIMEditingDomain(final AdapterFactory adapterFactory, final CommandStack commandStack) {
		super(adapterFactory, commandStack);
	}

	@Override
	public Command createOverrideCommand(final OverrideableCommand command) {
		if (command.getClass() == PasteFromClipboardCommand.class) {
			PasteFromClipboardCommand originalCommand = (PasteFromClipboardCommand) command;
			return new PasteInformationUnitCommand(this, originalCommand.getOwner(),
					originalCommand.getFeature(), originalCommand.getIndex(), false);
		}
		return super.createOverrideCommand(command);
	}

}
