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
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import org.remus.infomngmnt.InformationUnit;

/**
 * This edit policy enables the removal of a Shapes instance from its container. 
 * @see ShapeEditPart#createEditPolicies()
 * @see ShapeTreeEditPart#createEditPolicies()
 * @author Elias Volanakis
 */
class CommentComponentEditPolicy extends ComponentEditPolicy {

/* (non-Javadoc)
 * @see org.eclipse.gef.editpolicies.ComponentEditPolicy#createDeleteCommand(org.eclipse.gef.requests.GroupRequest)
 */
@Override
protected Command createDeleteCommand(final GroupRequest deleteRequest) {
	Object parent = getHost().getParent().getModel();
	Object child = getHost().getModel();
	if (parent instanceof InformationUnit && child instanceof InformationUnit) {
		return new LinkDeleteCommand((InformationUnit) parent, (InformationUnit) child);
	}
	return super.createDeleteCommand(deleteRequest);
}
}