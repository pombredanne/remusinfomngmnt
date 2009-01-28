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

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;

import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.core.model.InformationUtil;
import org.remus.infomngmnt.image.ImagePlugin;


/**
 * A command to add a Shape to a ShapeDiagram.
 * The command can be undone or redone.
 * @author Elias Volanakis
 */
public class CommentCreateCommand 
extends Command 
{

	/** The new shape. */ 
	private final ShapableInfoDelegate newShape;
	/** ShapeDiagram to add to. */
	private final InformationUnit parent;
	/** The bounds of the new Shape. */
	private final Rectangle bounds;
	private final InformationUnit linkInfo;

	/**
	 * Create a command that will add a new Shape to a ShapesDiagram.
	 * @param newShape the new Shape that is to be added
	 * @param parent the ShapesDiagram that will hold the new element
	 * @param bounds the bounds of the new shape; the size can be (-1, -1) if not known
	 * @throws IllegalArgumentException if any parameter is null, or the request
	 * 						  does not provide a new Shape instance
	 */
	public CommentCreateCommand(final ShapableInfoDelegate newShape, final InformationUnit parent, final Rectangle bounds) {
		this.newShape = newShape;
		this.linkInfo =InformationUtil.getChildByType(parent, ImagePlugin.NODE_NAME_LINKS);
		this.parent = parent;
		this.bounds = bounds;
		setLabel("shape creation");
	}

	/**
	 * Can execute if all the necessary information has been provided. 
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return this.newShape != null && this.parent != null && this.bounds != null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		this.newShape.setLocation(this.bounds.getLocation());
		Dimension size = this.bounds.getSize();
		if (size.width > 0 && size.height > 0) {
			this.newShape.setSize(size);
		}
		redo();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#redo()
	 */
	@Override
	public void redo() {
		this.linkInfo.getChildValues().add(this.newShape.getOrigInfoObject());
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		this.linkInfo.getChildValues().remove(this.newShape.getOrigInfoObject());
	}

}