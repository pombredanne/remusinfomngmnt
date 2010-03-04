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

package org.remus.infomngmnt.ui.viewer.provider;

import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;

import org.remus.infomngmnt.ui.viewer.ViewerActivator;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class NavigationCellLabelProvider extends LabelProvider {

	private final ILabelProvider delegatingLabelProvider;

	public NavigationCellLabelProvider() {
		this.delegatingLabelProvider = new AdapterFactoryLabelProvider(ViewerActivator.getDefault()
				.getEditService().getAdapterFactory());

	}

}
