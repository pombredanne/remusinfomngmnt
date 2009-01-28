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

package org.remus.infomngmnt.search.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.internal.FastViewBar;
import org.eclipse.ui.internal.Perspective;
import org.eclipse.ui.internal.WorkbenchPage;

import org.remus.infomngmnt.search.ui.view.SearchView;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
@SuppressWarnings("restriction")
public class OpenSearchHandler extends AbstractHandler {

	private final boolean makeFast = false;
	/* (non-Javadoc)
	 * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow activeWorkbenchWindow = HandlerUtil.getActiveWorkbenchWindow(event);
		try {
			openView(SearchView.ID, activeWorkbenchWindow);
		} catch (PartInitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Opens the view with the given identifier.
	 * 
	 * @param viewId
	 *            The view to open; must not be <code>null</code>
	 * @throws PartInitException
	 *             If the part could not be initialized.
	 */
	private final void openView(final String viewId,
			final IWorkbenchWindow activeWorkbenchWindow)
			throws PartInitException {

		final IWorkbenchPage activePage = activeWorkbenchWindow.getActivePage();
		if (activePage == null) {
			return;
		}

        if (this.makeFast) {
            WorkbenchPage wp = (WorkbenchPage) activePage;
        	Perspective persp = wp.getActivePerspective();

            // If we're making a fast view then use the new mechanism directly
            boolean useNewMinMax = Perspective.useNewMinMax(persp);
            if (useNewMinMax) {
            	IViewReference ref = persp.getViewReference(viewId, null);
            	if (ref == null) {
					return;
				}

            	persp.getFastViewManager().addViewReference(FastViewBar.FASTVIEWBAR_ID, -1, ref, true);
        		wp.activate(ref.getPart(true));
        		
        		return;
            }
            
            IViewReference ref = wp.findViewReference(viewId);
            
            if (ref == null) {
                IViewPart part = wp.showView(viewId, null, IWorkbenchPage.VIEW_CREATE);
                ref = (IViewReference)wp.getReference(part); 
            }
            
            if (!wp.isFastView(ref)) {
                wp.addFastView(ref);
            }
            wp.activate(ref.getPart(true));
        } else {
            activePage.showView(viewId);
        }
		
	}

}
