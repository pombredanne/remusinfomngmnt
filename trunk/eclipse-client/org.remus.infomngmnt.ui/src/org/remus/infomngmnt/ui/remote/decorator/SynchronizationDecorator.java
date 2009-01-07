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

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.ImageData;

import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.SynchronizationMetadata;
import org.remus.infomngmnt.core.remote.IRepository;
import org.remus.infomngmnt.core.services.IRepositoryExtensionService;
import org.remus.infomngmnt.ui.UIPlugin;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class SynchronizationDecorator extends LabelProvider implements
ILightweightLabelDecorator {
	
	private final Map<String, ImageDescriptor> scaledImageMap;
	
	public SynchronizationDecorator() {
		this.scaledImageMap = new HashMap<String, ImageDescriptor>();
	}
	
	
	public void decorate(final Object element, final IDecoration decoration) {
		if (element instanceof InformationUnitListItem
				&& ((InformationUnitListItem) element).getSynchronizationMetaData() != null) {
			SynchronizationMetadata synchronizationMetaData = ((InformationUnitListItem) element).getSynchronizationMetaData();
			switch (synchronizationMetaData.getSyncState()) {
			case LOCAL_EDITED:
				decoration.addPrefix("> ");
				break;
			default:
				break;
			}
			decoration.addOverlay(getScaledImage(synchronizationMetaData.getRepositoryId()),IDecoration.BOTTOM_RIGHT);
			IRepository itemById = UIPlugin.getDefault().getService(IRepositoryExtensionService.class).getItemById(synchronizationMetaData.getRepositoryId());
			
			decoration.addSuffix(String.format(" [%s]", itemById.getLabel()));
		}
		
	}


	private ImageDescriptor getScaledImage(final String repositoryId) {
		if (this.scaledImageMap.get(repositoryId) == null) {
			IRepository itemById = UIPlugin.getDefault().getService(IRepositoryExtensionService.class).getItemById(repositoryId);
			ImageData image = itemById.getImage().getImageData();
			ImageData scaledTo = image.scaledTo(7,7);
			this.scaledImageMap.put(repositoryId,ImageDescriptor.createFromImageData(scaledTo));;
		}
		return this.scaledImageMap.get(repositoryId);
	}

}
