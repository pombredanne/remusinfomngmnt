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

package org.remus.infomngmnt.core.internal.adapters;

import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.query.conditions.Condition;
import org.eclipse.emf.query.conditions.eobjects.structuralfeatures.EObjectAttributeValueCondition;
import org.eclipse.emf.query.statements.FROM;
import org.eclipse.emf.query.statements.IQueryResult;
import org.eclipse.emf.query.statements.SELECT;
import org.eclipse.emf.query.statements.WHERE;

import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.core.model.ApplicationModelPool;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class FileAdapterFactory implements IAdapterFactory {

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.IAdapterFactory#getAdapter(java.lang.Object, java.lang.Class)
	 */
	public Object getAdapter(final Object adaptableObject, final Class adapterType) {
		if (adaptableObject instanceof IFile) {
			EObjectAttributeValueCondition condition = new EObjectAttributeValueCondition(
					InfomngmntPackage.Literals.INFORMATION_UNIT_LIST_ITEM__WORKSPACE_PATH,new Condition() {
						@Override
						public boolean isSatisfied(final Object object) {
							return ((IFile) adaptableObject).getFullPath().toOSString().equals(object);
						}
					});
			SELECT select = new SELECT(new FROM(ApplicationModelPool.getInstance().getModel().getRootCategories()), new WHERE(condition));
			IQueryResult execute = select.execute();
			if (execute.getEObjects().size() == 1) {
				Set<? extends EObject> objects = execute.getEObjects();
				for (EObject object : objects) {
					return object;
				}
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.IAdapterFactory#getAdapterList()
	 */
	public Class[] getAdapterList() {
		return new Class[] {InformationUnitListItem.class};
	}

}
