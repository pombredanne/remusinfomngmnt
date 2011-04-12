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

import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.remus.InformationUnit;
import org.remus.infomngmnt.image.ImagePlugin;
import org.remus.infomngmnt.image.comments.ShapableInfoDelegate;

/**
 * Factory that maps model elements to edit parts.
 * 
 * @author Elias Volanakis
 */
public class ImageLinkEditPartFactory implements EditPartFactory {

	private final EditingDomain domain;

	public ImageLinkEditPartFactory(final EditingDomain domain) {
		this.domain = domain;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.EditPartFactory#createEditPart(org.eclipse.gef.EditPart,
	 * java.lang.Object)
	 */
	public EditPart createEditPart(final EditPart context,
			final Object modelElement) {
		// get EditPart for model element
		EditPart part = getPartForElement(modelElement);
		if (part instanceof IEditingDomainHolder) {
			((IEditingDomainHolder) part).setEditingDomain(domain);
		}
		// store model element in EditPart
		part.setModel(modelElement);
		return part;
	}

	/**
	 * Maps an object to an EditPart.
	 * 
	 * @throws RuntimeException
	 *             if no match was found (programming error)
	 */
	private EditPart getPartForElement(final Object modelElement) {
		if (modelElement instanceof InformationUnit
				&& ((InformationUnit) modelElement).getType().equals(
						ImagePlugin.TYPE_ID)) {
			return new ImageEditPart();
		}
		if (modelElement instanceof ShapableInfoDelegate) {
			return new CommentEditPart();
		}

		throw new RuntimeException("Can't create part for model element: " //$NON-NLS-1$
				+ ((modelElement != null) ? modelElement.getClass().getName()
						: "null")); //$NON-NLS-1$
	}

}