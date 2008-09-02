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

package org.remus.infomngmnt.core.builder;

import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.runtime.CoreException;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class InformationDeltaVisitor implements IResourceDeltaVisitor {

	public boolean visit(final IResourceDelta delta) throws CoreException {
		final IResourceDelta[] affectedChildren = delta.getAffectedChildren();
		for (final IResourceDelta resourceDelta : affectedChildren) {
			final int kind = resourceDelta.getKind();
			switch (kind) {
			case IResourceDelta.ADDED:
				//				if (ResourceUtil.FILE_EXTENSION.equals(resourceDelta.getResource().getFileExtension())) {
				//					NavigationIndexer.getInstance().addNodeToProject((IFile) resourceDelta.getResource(), null);
				//				}
				break;

			default:
				break;
			}
		}
		return true;
	}

}
