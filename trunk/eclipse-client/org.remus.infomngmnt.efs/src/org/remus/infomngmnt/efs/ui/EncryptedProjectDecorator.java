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

package org.remus.infomngmnt.efs.ui;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;
import org.eclipse.jface.viewers.LabelProvider;

import org.remus.infomngmnt.Adapter;
import org.remus.infomngmnt.common.ui.image.ResourceManager;
import org.remus.infomngmnt.efs.EFSActivator;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class EncryptedProjectDecorator extends LabelProvider implements ILightweightLabelDecorator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ILightweightLabelDecorator#decorate(java.lang
	 * .Object, org.eclipse.jface.viewers.IDecoration)
	 */
	public void decorate(final Object element, final IDecoration decoration) {
		if (element instanceof Adapter) {
			Object adapter = ((Adapter) element).getAdapter(IProject.class);
			if (adapter != null && adapter instanceof IProject) {
				try {
					if (((IProject) adapter).getDescription().getLocationURI() != null
							&& ((IProject) adapter).getDescription().getLocationURI().getScheme()
									.startsWith("encrypted")) {
						decoration.addOverlay(ResourceManager.getPluginImageDescriptor(EFSActivator
								.getDefault(), "icons/iconexperience/lock.png"));
					}
				} catch (CoreException e) {
					// do nothing.
				}
			}
		}

	}

}
