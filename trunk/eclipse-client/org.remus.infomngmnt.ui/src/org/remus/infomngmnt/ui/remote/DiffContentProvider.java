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

import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.compare.diff.metamodel.DiffGroup;
import org.eclipse.emf.compare.diff.metamodel.UpdateAttribute;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;

import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.common.core.util.CollectionFilter;
import org.remus.infomngmnt.common.core.util.CollectionUtils;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class DiffContentProvider extends AdapterFactoryContentProvider {

	public DiffContentProvider(final AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	@Override
	public Object[] getChildren(final Object object) {
		Object[] children = super.getChildren(object);
		List<Object> filter = CollectionUtils.filter(Arrays.asList(children),
				new CollectionFilter<Object>() {
					public boolean select(final Object item) {
						return !(item instanceof UpdateAttribute);
					}

				});
		return filter.toArray();
	}

	@Override
	public boolean hasChildren(final Object object) {
		boolean returnValue = super.hasChildren(object);
		if (object instanceof DiffGroup
				&& ((DiffGroup) object).getLeftParent() instanceof InformationUnitListItem) {
			return false;
		}
		return returnValue;

	}

}
