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

package org.remus.infomngmnt.core.extension;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;

import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.common.core.extension.PluginRegistryDynamic;
import org.remus.infomngmnt.core.services.ISaveParticipantExtensionService;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 * @noextend This class is not intended to be subclassed by clients.
 * @noinstantiate This class is not intended to be instantiated by clients.
 * @since 1.0
 */
public class SaveParticipantExtensionService extends PluginRegistryDynamic implements
		ISaveParticipantExtensionService {

	private Collection<ISaveParticipant> items;

	public SaveParticipantExtensionService() {
		super(EXTENSION_POINT);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.remus.infomngmnt.core.extension.PluginRegistryDynamic#init()
	 */
	@Override
	public void init() {
		if (this.items == null) {
			this.items = new ArrayList<ISaveParticipant>();
		}
		final IExtensionPoint extensionPoint = Platform.getExtensionRegistry().getExtensionPoint(
				EXTENSION_POINT);
		final IConfigurationElement[] configurationElements = extensionPoint
				.getConfigurationElements();
		for (final IConfigurationElement configurationElement : configurationElements) {
			try {
				this.items.add((ISaveParticipant) configurationElement
						.createExecutableExtension(CLASS_ATT));
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.core.services.ISaveParticipantExtensionService#fireEvent
	 * (int, org.remus.infomngmnt.InformationUnit)
	 */
	public void fireEvent(final int eventId, final Object oldValue, final Object newValue) {
		checkForInitialization();
		for (ISaveParticipant element : this.items) {
			switch (eventId) {
			case CREATED:
				if (newValue instanceof InformationUnit) {
					element.handleCreated((InformationUnit) newValue);
				}
				break;
			case SAVED:
				if (newValue instanceof InformationUnit && oldValue instanceof InformationUnit) {
					element.handleChanged((InformationUnit) oldValue, (InformationUnit) newValue);
				}
				break;
			case DELETED:
				if (oldValue instanceof InformationUnit) {
					element.handleDeleted(oldValue.toString());
				}
				break;
			default:
				break;
			}
		}
	}

	public Collection<ISaveParticipant> getAllItems() {
		checkForInitialization();
		return this.items;
	}

}
