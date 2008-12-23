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

import java.util.Collection;

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
	
	
	Collection<AbstractExtensionRepository> getItems();
	
	AbstractExtensionRepository getItemById(String id);

}
