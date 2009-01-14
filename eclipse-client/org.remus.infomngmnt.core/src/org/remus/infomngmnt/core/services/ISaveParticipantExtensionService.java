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

import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.core.CorePlugin;
import org.remus.infomngmnt.core.extension.ISaveParticipant;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 * @since 1.0
 */
public interface ISaveParticipantExtensionService extends IExtensionService {

	public static final String EXTENSION_POINT = CorePlugin.PLUGIN_ID + ".saveParticipant"; //$NON-NLS-1$

	public static final String CLASS_ATT = "class"; //$NON-NLS-1$
	/**
	 * @return
	 * @since 1.0
	 */
	Collection<ISaveParticipant> getAllItems();

	void fireEvent(int eventId, InformationUnit unit);

}
