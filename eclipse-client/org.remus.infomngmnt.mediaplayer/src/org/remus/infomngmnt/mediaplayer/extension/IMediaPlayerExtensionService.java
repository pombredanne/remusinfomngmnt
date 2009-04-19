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

package org.remus.infomngmnt.mediaplayer.extension;

import java.util.Collection;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public interface IMediaPlayerExtensionService {

	public static final String MEDIAPLAYER_NODE_NAME = "mediaplayer"; //$NON-NLS-1$

	public static final String SUPPORTEDMEDIA_NODE_NAME = "supportedmediatype"; //$NON-NLS-1$

	public static final String ID_ATT = "id"; //$NON-NLS-1$

	public static final String NAME_ATT = "name"; //$NON-NLS-1$

	public static final String CLASS_ATT = "CLASS_ATT"; //$NON-NLS-1$

	public IMediaPlayer getPlayerById(final String id);

	public IMediaPlayer getPlayerByType(final String type);

	public Collection<IMediaPlayer> getAllPlayers();

}
