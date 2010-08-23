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

package org.remus.infomngmnt.mindmap.extension;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.command.Command;
import org.eclipse.remus.InformationUnit;
import org.eclipse.remus.core.create.PostCreationHandler;


/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
@SuppressWarnings("restriction")
public class MindmapPostCreationHandler extends PostCreationHandler {

	@Override
	public Command handlePreSaving(final InformationUnit unit, final IProgressMonitor monitor) {
		return null;
	}

}
