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

package org.remus.infomngmnt.ui.handlerutil;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

import org.remus.infomngmnt.InformationUnit;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class InformationHandlerUtil {

	public static InformationUnit getInformationUnitFromExecutionEvent(final ExecutionEvent event) {
		InformationUnit unit = null;
		InformationUnit adapter = (InformationUnit) HandlerUtil.getActivePart(event).getAdapter(
				InformationUnit.class);
		if (adapter != null) {
			unit = adapter;
		} else {
			ISelection currentSelection = HandlerUtil.getCurrentSelection(event);
			if (currentSelection instanceof IStructuredSelection
					&& ((IStructuredSelection) currentSelection).getFirstElement() instanceof IAdaptable) {
				unit = (InformationUnit) ((IAdaptable) ((IStructuredSelection) currentSelection)
						.getFirstElement()).getAdapter(InformationUnit.class);
			}
		}
		return unit;
	}

	public static List<InformationUnit> getInformationUnitsFromExecutionEvent(
			final ExecutionEvent event) {
		List<InformationUnit> units = new ArrayList<InformationUnit>();
		InformationUnit adapter = (InformationUnit) HandlerUtil.getActivePart(event).getAdapter(
				InformationUnit.class);
		if (adapter != null) {
			units.add(adapter);
		} else {
			ISelection currentSelection = HandlerUtil.getCurrentSelection(event);
			if (currentSelection instanceof IStructuredSelection) {
				List list = ((IStructuredSelection) currentSelection).toList();
				for (Object object : list) {
					if (object instanceof IAdaptable) {
						units.add((InformationUnit) ((IAdaptable) object)
								.getAdapter(InformationUnit.class));
					}
				}
			}
		}
		return units;
	}

}
