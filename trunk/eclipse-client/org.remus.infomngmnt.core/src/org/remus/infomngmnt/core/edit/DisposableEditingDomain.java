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

package org.remus.infomngmnt.core.edit;

import java.util.Map;

import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.IDisposable;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class DisposableEditingDomain extends AdapterFactoryEditingDomain implements IDisposable {

	public DisposableEditingDomain(final AdapterFactory adapterFactory,
			final CommandStack commandStack, final Map<Resource, Boolean> resourceToReadOnlyMap) {
		super(adapterFactory, commandStack, resourceToReadOnlyMap);

	}

	public DisposableEditingDomain(final AdapterFactory adapterFactory,
			final CommandStack commandStack, final ResourceSet resourceSet) {
		super(adapterFactory, commandStack, resourceSet);

	}

	public DisposableEditingDomain(final AdapterFactory adapterFactory,
			final CommandStack commandStack) {
		super(adapterFactory, commandStack);

	}

	public void dispose() {
		AdapterFactory adapterFactory2 = getAdapterFactory();
		if (adapterFactory2 instanceof IDisposable) {
			try {
				((IDisposable) adapterFactory2).dispose();
			} catch (Exception e) {
				// do nothing.
			}
		}

	}

}
