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

import java.util.List;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

import org.remus.infomngmnt.InformationStructureDefinition;
import org.remus.infomngmnt.core.create.PostCreationHandler;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public interface IInfoType {

	PostCreationHandler getPostCreationHandler();

	AbstractInformationRepresentation getInformationRepresentation();

	String getType();

	ImageDescriptor getImageDescriptor();

	Image getImage();

	List<String> getValidTransferTypeIds();

	String getName();

	InformationStructureDefinition getStructureDefinition();

	boolean isBuildHtml();

	boolean isExcludeFromIndex();

}