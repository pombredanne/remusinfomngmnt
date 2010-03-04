/*******************************************************************************
 * Copyright (c) 2010 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/

package org.remus.infomngmnt.ui.editors.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.NullProgressMonitor;

import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.ui.editor.EditorActivator;
import org.remus.infomngmnt.ui.editors.EditorUtil;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class BuildSingleInfoUnitHandler extends AbstractHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.
	 * ExecutionEvent)
	 */
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		String parameter = event.getParameter("infoId");
		if (parameter != null) {
			InformationUnitListItem itemById = EditorActivator.getDefault().getApplicationService()
					.getItemById(parameter, new NullProgressMonitor());
			IFile file = (IFile) ((IAdaptable) itemById.getAdapter(InformationUnit.class))
					.getAdapter(IFile.class);
			if (file != null) {
				try {
					IFile binFile = EditorUtil.getBinFile(file);
					if (binFile != null && binFile.exists()) {
						binFile.delete(false, new NullProgressMonitor());
					}
					file.touch(new NullProgressMonitor());
				} catch (CoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}

}
