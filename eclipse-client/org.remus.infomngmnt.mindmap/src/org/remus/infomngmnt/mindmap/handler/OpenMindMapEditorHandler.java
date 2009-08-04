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

package org.remus.infomngmnt.mindmap.handler;

import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IDE;

import org.remus.infomngmnt.BinaryReference;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.common.ui.UIUtil;
import org.remus.infomngmnt.core.model.InformationStructureRead;
import org.remus.infomngmnt.ui.handlerutil.InformationHandlerUtil;
import org.remus.infomngmnt.util.InformationUtil;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class OpenMindMapEditorHandler extends AbstractHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.
	 * ExecutionEvent)
	 */
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		InformationUnit unit = InformationHandlerUtil.getInformationUnitFromExecutionEvent(event);
		if (unit != null) {
			InformationStructureRead read = InformationStructureRead.newSession(unit);
			List<BinaryReference> binaryReferences = read.getBinaryReferences();
			openEditor(binaryReferences, unit);
		}
		return null;

	}

	public static void openEditor(final List<BinaryReference> binaryReferences,
			final InformationUnit containingInfoUnit) {
		if (binaryReferences.size() > 0) {
			IFile binaryReferenceToFile = InformationUtil.binaryReferenceToFile(binaryReferences
					.get(0), containingInfoUnit);
			IWorkbenchPage activePage = UIUtil.getPrimaryWindow().getActivePage();
			try {
				IDE.openEditor(activePage, binaryReferenceToFile);
				activePage.showView("org.xmind.ui.StylesView");
				activePage.showView("org.xmind.ui.MarkerView");
				activePage.showView("org.xmind.ui.ThemesView");
			} catch (PartInitException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

}
