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

package org.remus.infomngmnt.core.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.query.conditions.eobjects.EObjectCondition;
import org.eclipse.emf.query.conditions.eobjects.EObjectTypeRelationCondition;
import org.eclipse.emf.query.statements.FROM;
import org.eclipse.emf.query.statements.IQueryResult;
import org.eclipse.emf.query.statements.SELECT;
import org.eclipse.emf.query.statements.WHERE;

import org.remus.infomngmnt.ApplicationRoot;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnitListItem;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class AvailableInformationCache {

	private Map<String, InformationUnitListItem> cachedItems;

	/**
	 * <p>
	 * Returns all present list-items that are located under a category. The
	 * project the items are located has to be open.
	 * </p>
	 * 
	 * @param monitor
	 * @return
	 */
	public Map<String, InformationUnitListItem> getAllItems(final IProgressMonitor monitor) {
		if (this.cachedItems == null) {
			ApplicationRoot model = ApplicationModelPool.getInstance().getModel();
			EObjectCondition condition = new EObjectTypeRelationCondition(
					InfomngmntPackage.eINSTANCE.getInformationUnitListItem());
			SELECT select = new SELECT(new FROM(model.getRootCategories()), new WHERE(condition));
			IQueryResult execute = select.execute();
			Set<? extends EObject> objects = execute.getEObjects();
			this.cachedItems = new HashMap<String, InformationUnitListItem>(objects.size());
			for (EObject object : objects) {
				this.cachedItems.put(((InformationUnitListItem) object).getId(),
						(InformationUnitListItem) object);
			}
		}
		return this.cachedItems;
	}

	public void clear() {
		this.cachedItems.clear();
		this.cachedItems = null;
	}

	public InformationUnitListItem getItemById(final String id, final IProgressMonitor monitor) {
		return getAllItems(monitor).get(id);
	}

	void addItem(final InformationUnitListItem item) {
		if (this.cachedItems != null) {
			this.cachedItems.put(item.getId(), item);
		}
	}

	void remove(final String id) {
		if (this.cachedItems != null) {
			this.cachedItems.remove(id);
		}
	}

}
