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

package org.remus.infomngmnt.core.services;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.resource.ImageDescriptor;

import org.remus.infomngmnt.RemoteRepository;
import org.remus.infomngmnt.core.CorePlugin;
import org.remus.infomngmnt.core.extension.AbstractExtensionRepository;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public interface IRepositoryExtensionService extends IExtensionService {

	public static final String EXTENSION_POINT = CorePlugin.PLUGIN_ID + ".remoteRepository"; //$NON-NLS-1$

	public static final String CONNECTOR_NODENAME = "repository-connector"; //$NON-NLS-1$

	public static final String ID_ATT = "id"; //$NON-NLS-1$

	public static final String NAME_ATT = "name"; //$NON-NLS-1$

	public static final String ICON_ATT = "icon"; //$NON-NLS-1$

	public static final String CLASS_ATT = "class"; //$NON-NLS-1$

	public static final String CREDENTIALPROVIDER_ATT = "credentialprovider"; //$NON-NLS-1$

	public static final String CHANGE_DEFINITION_NODE_NAME = "changeDefinition"; //$NON-NLS-1$

	public static final String INFORMATION_TYPE_ATT = "informationType"; //$NON-NLS-1$

	public static final String CHANGE_OBJECT_PATH_NODE_NAME = "changeObjectPath"; //$NON-NLS-1$

	public static final String CHANGE_OBJECT_PATH_VALUE_NODE_NAME = "changeObjectPathValue"; //$NON-NLS-1$

	public static final String CHANGE_OBJECT_ID_NODE_NAME = "changeObjectId"; //$NON-NLS-1$

	public static final String CHANGE_OBJECT_ID_VALUE_NODE_NAME = "changeObjectIdValue"; //$NON-NLS-1$

	public static final String PATH_ATT = "path"; //$NON-NLS-1$

	AbstractExtensionRepository getItemByRepository(RemoteRepository repository)
			throws CoreException;

	ImageDescriptor getImageByRepositoryId(String id);

	String getNameByRepositoryId(String id);

}
