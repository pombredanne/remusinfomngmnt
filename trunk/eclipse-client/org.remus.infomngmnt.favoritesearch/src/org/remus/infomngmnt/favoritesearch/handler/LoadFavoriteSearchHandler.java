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

package org.remus.infomngmnt.favoritesearch.handler;

import java.util.Set;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.remus.InformationUnit;
import org.eclipse.remus.InformationUnitListItem;
import org.eclipse.remus.core.model.InformationStructureRead;
import org.eclipse.remus.core.services.IEditingHandler;
import org.eclipse.remus.search.Search;
import org.eclipse.remus.search.ui.view.SearchView;
import org.eclipse.remus.ui.viewer.provider.InformationUnitLabelProvider;
import org.eclipse.remus.ui.viewer.provider.NavigatorDecoratingLabelProvider;
import org.eclipse.remus.util.InformationUtil;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.eclipse.ui.handlers.HandlerUtil;

import org.remus.infomngmnt.favoritesearch.FavoriteSearchActivator;
import org.remus.infomngmnt.favoritesearch.util.SearchSerializer;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class LoadFavoriteSearchHandler extends AbstractHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.
	 * ExecutionEvent)
	 */
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		IEditingHandler service = FavoriteSearchActivator.getDefault().getServiceTracker()
				.getService(IEditingHandler.class);
		Shell activeShell = HandlerUtil.getActiveShell(event);
		Set<? extends EObject> allItemsByType = InformationUtil
				.getAllItemsByType(FavoriteSearchActivator.TYPE_ID);
		NavigatorDecoratingLabelProvider labelProvider = new NavigatorDecoratingLabelProvider(
				new InformationUnitLabelProvider(service.getAdapterFactory()) {
					@Override
					public String getText(final Object element) {
						if (element instanceof InformationUnitListItem) {
							return InformationUtil
									.getFullReadablePath((InformationUnitListItem) element);
						}
						return super.getText(element);
					}
				});
		ElementListSelectionDialog dialog = new ElementListSelectionDialog(activeShell,
				labelProvider);
		dialog.setAllowDuplicates(false);
		dialog.setElements(allItemsByType.toArray());
		dialog.setEmptySelectionMessage("Selection is required");
		dialog.setMultipleSelection(false);
		dialog.setTitle("Select a favorite search feed");
		dialog.setMessage("Please select a favorites search from the list");
		dialog.setIgnoreCase(true);
		if (dialog.open() == IDialogConstants.OK_ID) {
			InformationUnit selSearch = (InformationUnit) (((InformationUnitListItem) dialog
					.getFirstResult()).getAdapter(InformationUnit.class));
			IWorkbenchPart activePart = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
					.getActivePage().findView(SearchView.ID);
			if (activePart != null) {
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().bringToTop(
						activePart);
				InformationStructureRead read = InformationStructureRead.newSession(selSearch);
				byte[] search = (byte[]) read.getValueByNodeId(FavoriteSearchActivator.RESULT_NODE);
				if (search != null) {
					Search deserialize = SearchSerializer.deserialize(search);
					((SearchView) activePart).setCurrentSearch(deserialize);
				}
			}

		}
		FavoriteSearchActivator.getDefault().getServiceTracker().ungetService(service);
		return null;
	}
}
