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

package org.remus.infomngmnt.ui.service;

import java.util.Collection;

import org.remus.infomngmnt.core.services.IExtensionService;
import org.remus.infomngmnt.ui.UIPlugin;
import org.remus.infomngmnt.ui.extension.IRepositoryUI;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 * @since 1.0
 */
public interface IRepositoryExtensionService extends IExtensionService {

	public static final String EXTENSION_POINT = UIPlugin.PLUGIN_ID + ".repositoryUI"; //$NON-NLS-1$

	public static final String ID_ATT = "id"; //$NON-NLS-1$

	public static final String REPOSITORY_ID = "repository-id"; //$NON-NLS-1$

	public static final String WIZARD_CLASSID = "newWizard"; //$NON-NLS-1$
	
	public static final String ACTIONCONTRIBUTOR_CLASSID = "actioncontributor"; //$NON-NLS-1$

	/**
	 * @return
	 * @since 1.0
	 */
	Collection<IRepositoryUI> getAllItems();

	/**
	 * @param id
	 * @return
	 * @since 1.0
	 */
	IRepositoryUI getItemById(final String id);

}
