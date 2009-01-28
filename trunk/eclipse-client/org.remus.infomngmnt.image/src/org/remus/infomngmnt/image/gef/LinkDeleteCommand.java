/*******************************************************************************
 * Copyright (c) 2004, 2005 Elias Volanakis and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Elias Volanakis - initial API and implementation
 *******************************************************************************/
package org.remus.infomngmnt.image.gef;

import org.eclipse.gef.commands.Command;

import org.remus.infomngmnt.InformationUnit;

/**
 * A command to remove a shape from its parent.
 * The command can be undone or redone.
 * @author Elias Volanakis
 */
public class LinkDeleteCommand extends Command {
	/** Shape to remove. */
	private final InformationUnit child;

	/** ShapeDiagram to remove from. */
	private final InformationUnit parent;

	/** True, if child was removed from its parent. */
	private boolean wasRemoved;

	/**
	 * Create a command that will remove the shape from its parent.
	 * @param parent the ShapesDiagram containing the child
	 * @param child    the Shape to remove
	 * @throws IllegalArgumentException if any parameter is null
	 */
	public LinkDeleteCommand(final InformationUnit parent, final InformationUnit child) {
		if (parent == null || child == null) {
			throw new IllegalArgumentException();
		}
		setLabel("shape deletion");
		this.parent = parent;
		this.child = child;
	}



	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#canUndo()
	 */
	@Override
	public boolean canUndo() {
		return this.wasRemoved;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {

		redo();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#redo()
	 */
	@Override
	public void redo() {
		// remove the child and disconnect its connections
		this.wasRemoved = this.parent.getChildValues().remove(this.child);

	}



	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		this.parent.getChildValues().add(this.child);
	}
}