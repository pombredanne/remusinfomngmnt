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

package org.remus.infomngmnt.ui.viewer.dnd;

import java.util.Collection;

import org.eclipse.emf.common.command.IdentityCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.CopyCommand;
import org.eclipse.emf.edit.command.DragAndDropCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class DNDWithIdGenerationOnCopyCommand extends DragAndDropCommand {

	public DNDWithIdGenerationOnCopyCommand(final EditingDomain domain, final Object owner,
			final float location, final int operations, final int operation,
			final Collection<?> collection, final boolean optimize) {
		super(domain, owner, location, operations, operation, collection, optimize);
	}

	public DNDWithIdGenerationOnCopyCommand(final EditingDomain domain, final Object owner,
			final float location, final int operations, final int operation,
			final Collection<?> collection) {
		super(domain, owner, location, operations, operation, collection);
	}

	/**
	 * This attempts to prepare a drop copy on operation.
	 */
	@Override
	protected boolean prepareDropCopyOn() {
		boolean result;

		this.dragCommand = CopyCommand.create(this.domain, this.collection);

		if (this.optimize) {
			result = optimizedCanExecute();
			if (result) {
				this.optimizedDropCommandOwner = this.owner;
			}
		} else {
			if (this.dragCommand.canExecute() && this.dragCommand.canUndo()) {
				this.dragCommand.execute();
				this.isDragCommandExecuted = true;
				this.dropCommand = AddCommand.create(this.domain, this.owner, null,
						this.dragCommand.getResult());
				if (analyzeForNonContainment(this.dropCommand)) {
					this.dropCommand.dispose();
					this.dropCommand = UnexecutableCommand.INSTANCE;

					this.dragCommand.undo();
					this.dragCommand.dispose();
					this.isDragCommandExecuted = false;
					this.dragCommand = IdentityCommand.INSTANCE;
				}
			} else {
				this.dropCommand = UnexecutableCommand.INSTANCE;
			}

			result = this.dragCommand.canExecute() && this.dropCommand.canExecute();
		}
		return result;
	}

}
