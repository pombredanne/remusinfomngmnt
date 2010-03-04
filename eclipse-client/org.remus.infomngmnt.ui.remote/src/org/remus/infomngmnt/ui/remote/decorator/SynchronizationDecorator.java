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

package org.remus.infomngmnt.ui.remote.decorator;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;
import org.eclipse.jface.viewers.LabelProvider;

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.RemoteRepository;
import org.remus.infomngmnt.SynchronizableObject;
import org.remus.infomngmnt.SynchronizationMetadata;
import org.remus.infomngmnt.common.ui.image.CommonImageRegistry;
import org.remus.infomngmnt.common.ui.image.ResourceManager;
import org.remus.infomngmnt.core.remote.services.IRepositoryService;
import org.remus.infomngmnt.ui.UIPlugin;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class SynchronizationDecorator extends LabelProvider implements ILightweightLabelDecorator {

	private final Map<String, ImageDescriptor> scaledImageMap;

	public SynchronizationDecorator() {
		this.scaledImageMap = new HashMap<String, ImageDescriptor>();
	}

	public void decorate(final Object element, final IDecoration decoration) {
		SynchronizationMetadata synchronizationMetaData = null;
		if (element instanceof SynchronizableObject) {
			synchronizationMetaData = ((SynchronizableObject) element).getSynchronizationMetaData();
		}
		if (synchronizationMetaData != null) {
			/**
			 * We have to adapt the SynchronizationMetaData that the adapter is
			 * registered as Notification to the data object. If we don't do
			 * that, the viewer which shows no MetaData as real TreeItems is not
			 * notified about a change in this object, doesn't call a refresh on
			 * the shown object and this decorator will not be refreshed.
			 */
			UIPlugin.getDefault().getEditService().getAdapterFactory().adapt(
					synchronizationMetaData, ITreeItemContentProvider.class);
			RemoteRepository itemById = UIPlugin.getDefault().getService(IRepositoryService.class)
					.getRepositoryById(synchronizationMetaData.getRepositoryId());

			switch (synchronizationMetaData.getSyncState()) {
			case LOCAL_EDITED:
			case NOT_ADDED:
				decoration.addPrefix("> ");
				break;
			default:
				break;
			}
			switch (synchronizationMetaData.getSyncState()) {
			case IN_SYNC:
			case LOCAL_EDITED:
			case TARGET_EDITED:
				decoration.addOverlay(getScaledImage(itemById.getRepositoryTypeId()));
				break;
			case NOT_ADDED:
				decoration.addOverlay(CommonImageRegistry.getInstance().getDescriptor(
						CommonImageRegistry.INFORMATION_DECORATION));
				break;
			case IGNORED:
				decoration.addOverlay(ResourceManager.getPluginImageDescriptor(UIPlugin
						.getDefault(), "icons/iconexperience/decorator/funnel.png"));
				break;
			default:
				break;

			}
			if (element instanceof Category
					&& ((EObject) element).eContainer() != null
					&& ((IAdaptable) ((EObject) element).eContainer())
							.getAdapter(SynchronizationMetadata.class) == null) {
				decoration.addSuffix(String.format(" [%s]", itemById.getName()));
			}
		}

	}

	private ImageDescriptor getScaledImage(final String repositoryId) {
		// if (this.scaledImageMap.get(repositoryId) == null) {
		// ImageDescriptor itemById = UIPlugin.getDefault().getService(
		// IRepositoryExtensionService.class).getImageByRepositoryId(repositoryId);
		// if (itemById != null) {
		// ImageData image = itemById.getImageData();
		// ImageData scaledTo = image.scaledTo(9, 9);
		// this.scaledImageMap
		// .put(repositoryId, ImageDescriptor.createFromImageData(scaledTo));
		// ;
		// } else {
		// return null;
		// }
		// }
		// return this.scaledImageMap.get(repositoryId);
		// FIXME
		return null;
	}

}
