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

package org.remus.infomngmnt.core.adapters;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.core.runtime.Path;

import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.SynchronizationMetadata;
import org.remus.infomngmnt.core.model.EditingUtil;
import org.remus.infomngmnt.resources.util.ResourceUtil;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class InformationUnitListItemAdapterFactory implements IAdapterFactory {

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.IAdapterFactory#getAdapter(java.lang.Object, java.lang.Class)
	 */
	public Object getAdapter(final Object adaptableObject, final Class adapterType) {
		if (adapterType.equals(InformationUnit.class)
				&& adaptableObject instanceof InformationUnitListItem) {
			InformationUnitListItem item = (InformationUnitListItem) adaptableObject;
			String id = item.getId();
			IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(
					new Path(item.eResource().getURI().toPlatformString(true)));
			return EditingUtil.getInstance().getObjectFromFile(
					file.getProject().getFile(new Path(id).addFileExtension(ResourceUtil.FILE_EXTENSION)), InfomngmntPackage.eINSTANCE.getInformationUnit(), false);
		}
		if (adapterType.equals(SynchronizationMetadata.class)
				&& adaptableObject instanceof InformationUnitListItem) {
			return ((InformationUnitListItem) adaptableObject).getSynchronizationMetaData();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.IAdapterFactory#getAdapterList()
	 */
	public Class[] getAdapterList() {
		return new Class[] {InformationUnit.class, SynchronizationMetadata.class};
	}

}
