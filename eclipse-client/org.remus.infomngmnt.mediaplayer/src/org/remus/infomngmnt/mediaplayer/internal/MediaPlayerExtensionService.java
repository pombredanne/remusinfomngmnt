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

package org.remus.infomngmnt.mediaplayer.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;

import org.remus.infomngmnt.core.extension.PluginRegistryDynamic;
import org.remus.infomngmnt.mediaplayer.MediaPlayerActivator;
import org.remus.infomngmnt.mediaplayer.extension.AbstractMediaPlayer;
import org.remus.infomngmnt.mediaplayer.extension.IMediaPlayer;
import org.remus.infomngmnt.mediaplayer.extension.IMediaPlayerExtensionService;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class MediaPlayerExtensionService extends PluginRegistryDynamic implements
		IMediaPlayerExtensionService {

	public static final String EXTENSION_POINT = MediaPlayerActivator.PLUGIN_ID + ".mediaplayer"; //$NON-NLS-1$
	private HashMap<String, IMediaPlayer> items;

	public MediaPlayerExtensionService() {
		super(EXTENSION_POINT);

	}

	@Override
	protected void init() {
		this.items = new HashMap<String, IMediaPlayer>();
		IExtensionPoint extensionPoint = Platform.getExtensionRegistry().getExtensionPoint(
				EXTENSION_POINT);
		IConfigurationElement[] elements = extensionPoint.getConfigurationElements();
		for (IConfigurationElement iConfigurationElement : elements) {
			try {
				AbstractMediaPlayer mediaPlayer = (AbstractMediaPlayer) iConfigurationElement
						.createExecutableExtension(CLASS_ATT);
				mediaPlayer.setId(iConfigurationElement.getAttribute(ID_ATT));
				mediaPlayer.setName(iConfigurationElement.getAttribute(NAME_ATT));
				IConfigurationElement[] children = iConfigurationElement
						.getChildren(SUPPORTEDMEDIA_NODE_NAME);
				List<String> supportedMediaTypes = new ArrayList<String>();
				for (IConfigurationElement mediaTypes : children) {
					supportedMediaTypes.add(mediaTypes.getAttribute(NAME_ATT));
				}
				mediaPlayer.setSupportedMediaTypes(supportedMediaTypes);
				this.items.put(mediaPlayer.getId(), mediaPlayer);
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public IMediaPlayer getPlayerById(final String id) {
		return this.items.get(id);
	}

	public IMediaPlayer getPlayerByType(final String type) {
		Collection<IMediaPlayer> values = this.items.values();
		for (IMediaPlayer player : values) {
			if (player.getSupportedMediaTypes().contains(type)) {
				return player;
			}
		}
		return null;
	}

	public Collection<IMediaPlayer> getAllPlayers() {
		return this.items.values();
	}

}