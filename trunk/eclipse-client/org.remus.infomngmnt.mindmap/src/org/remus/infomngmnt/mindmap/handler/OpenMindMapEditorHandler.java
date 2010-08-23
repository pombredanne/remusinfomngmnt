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
import org.eclipse.remus.BinaryReference;
import org.eclipse.remus.InformationUnit;
import org.eclipse.remus.common.ui.UIUtil;
import org.eclipse.remus.core.model.InformationStructureRead;
import org.eclipse.remus.ui.handlerutil.InformationHandlerUtil;
import org.eclipse.remus.util.InformationUtil;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;


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
				IPerspectiveDescriptor findPerspectiveWithId = PlatformUI.getWorkbench()
						.getPerspectiveRegistry().findPerspectiveWithId(
								"org.xmind.ui.perspective.mindmapping");
				if (findPerspectiveWithId != null) {
					PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
							.setPerspective(findPerspectiveWithId);
				}
			} catch (PartInitException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

}
