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

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.compare.diff.metamodel.DiffGroup;
import org.eclipse.emf.compare.diff.metamodel.DiffModel;
import org.eclipse.emf.compare.diff.metamodel.DifferenceKind;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class ChangeSetLabelProvider extends AdapterFactoryLabelProvider {

	public ChangeSetLabelProvider(final AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	@Override
	public String getText(final Object object) {
		if (((EObject) object).eContainer() instanceof DiffModel) {
			if (((DiffGroup) object).getKind() == DifferenceKind.ADDITION) {

			}
		}
		return super.getText(object);
	}

}
