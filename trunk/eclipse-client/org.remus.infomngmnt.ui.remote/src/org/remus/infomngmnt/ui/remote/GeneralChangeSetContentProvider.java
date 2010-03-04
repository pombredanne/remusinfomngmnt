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

package org.remus.infomngmnt.ui.remote;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.ChangeSetItem;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.ui.UIPlugin;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class GeneralChangeSetContentProvider extends AdapterFactoryContentProvider {

	public GeneralChangeSetContentProvider() {
		super(UIPlugin.getDefault().getEditService().getAdapterFactory());
	}

	@Override
	public Object[] getElements(final Object object) {
		if (object instanceof EList) {
			EList<ChangeSetItem> items = (EList<ChangeSetItem>) object;
			Object[] returnValue = new Object[items.size()];
			for (int i = 0, n = items.size(); i < n; i++) {
				ChangeSetItem currentItem = items.get(i);
				returnValue[i] = currentItem.getRemoteConvertedContainer();
			}
			return returnValue;

		}
		return super.getElements(object);
	}

	@Override
	public boolean hasChildren(final Object object) {
		return !(object instanceof InformationUnitListItem) && super.hasChildren(object);
	}

	@Override
	public Object[] getChildren(final Object object) {
		if (object instanceof InformationUnitListItem) {
			return new Object[0];
		} else if (object instanceof Category) {

		}
		return super.getChildren(object);
	}

}
