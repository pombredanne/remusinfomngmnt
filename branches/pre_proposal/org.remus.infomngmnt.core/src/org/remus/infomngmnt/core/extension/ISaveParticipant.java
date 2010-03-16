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

package org.remus.infomngmnt.core.extension;

import org.eclipse.core.resources.IProject;

import org.remus.infomngmnt.InformationUnit;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public interface ISaveParticipant {

	void handleCreated(InformationUnit newValue);

	void handleChanged(InformationUnit oldValue, InformationUnit newValue);

	void handleDeleted(String informationUnitId);

	void handleClean(IProject project);

}