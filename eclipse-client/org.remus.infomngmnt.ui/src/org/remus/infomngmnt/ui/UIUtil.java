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

package org.remus.infomngmnt.ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;
import org.eclipse.ui.part.ISetSelectionTarget;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class UIUtil {

	public static WorkbenchWindowAdvisor fgPrimaryApplicationWorkbenchWindowAdvisor;


	/**
	 * Attempts to select and reveal the specified resource in all
	 * parts within the supplied workbench window's active page.
	 * <p>
	 * Checks all parts in the active page to see if they implement <code>ISetSelectionTarget</code>,
	 * either directly or as an adapter. If so, tells the part to select and reveal the
	 * specified resource.
	 * </p>
	 *
	 * @param object the resource to be selected and revealed
	 * @param window the workbench window to select and reveal the resource
	 * 
	 * @see ISetSelectionTarget
	 */
	public static void selectAndReveal(Object object,
			IWorkbenchWindow window) {
		// validate the input
		if (window == null || object == null) {
			return;
		}
		IWorkbenchPage page = window.getActivePage();
		if (page == null) {
			return;
		}

		// get all the view and editor parts
		List parts = new ArrayList();
		IWorkbenchPartReference refs[] = page.getViewReferences();
		for (int i = 0; i < refs.length; i++) {
			IWorkbenchPart part = refs[i].getPart(false);
			if (part != null) {
				parts.add(part);
			}
		}
		refs = page.getEditorReferences();
		for (int i = 0; i < refs.length; i++) {
			if (refs[i].getPart(false) != null) {
				parts.add(refs[i].getPart(false));
			}
		}

		final ISelection selection = new StructuredSelection(object);
		Iterator itr = parts.iterator();
		while (itr.hasNext()) {
			IWorkbenchPart part = (IWorkbenchPart) itr.next();

			// get the part's ISetSelectionTarget implementation
			ISetSelectionTarget target = null;
			if (part instanceof ISetSelectionTarget) {
				target = (ISetSelectionTarget) part;
			} else {
				target = (ISetSelectionTarget) part
				.getAdapter(ISetSelectionTarget.class);
			}

			if (target != null) {
				// select and reveal resource
				final ISetSelectionTarget finalTarget = target;
				window.getShell().getDisplay().asyncExec(new Runnable() {
					public void run() {
						finalTarget.selectReveal(selection);
					}
				});
			}
		}
	}

	/**
	 * Attempts to find the primary <code>IWorkbenchWindow</code> from the
	 * PlatformUI facade. Otherwise, returns <code>NULL</code> if none.
	 *
	 * @return the primary <code>IWorkbenchWindow</code> from the PlatformUI
	 * facade or <code>NULL</code> if none.
	 */
	public static IWorkbenchWindow getPrimaryWindow() {

		/* Return the first Window of the Workbench */
		IWorkbenchWindow windows[] = PlatformUI.getWorkbench().getWorkbenchWindows();
		if (windows.length > 0)
			return windows[0];

		return null;
	}

}
