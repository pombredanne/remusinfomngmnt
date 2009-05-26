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

package org.remus.infomngmnt.core.remote;

import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.RemoteRepository;
import org.remus.infomngmnt.SynchronizationMetadata;
import org.remus.infomngmnt.SynchronizationState;
import org.remus.infomngmnt.core.extension.AbstractExtensionRepository;
import org.remus.infomngmnt.core.extension.ISaveParticipant;
import org.remus.infomngmnt.core.path.Path2ObjectMapper;
import org.remus.infomngmnt.core.services.IRepositoryExtensionService;
import org.remus.infomngmnt.core.services.IRepositoryService;
import org.remus.infomngmnt.provider.InfomngmntEditPlugin;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class SyncStateParticipant implements ISaveParticipant {

	public void handleChanged(final InformationUnit oldValue, final InformationUnit newValue) {
		Object adapter = newValue.getAdapter(InformationUnitListItem.class);
		if (adapter != null
				&& ((InformationUnitListItem) adapter).getSynchronizationMetaData() != null) {
			SynchronizationMetadata synchronizationMetadata = ((InformationUnitListItem) adapter)
					.getSynchronizationMetaData();
			RemoteRepository itemById = InfomngmntEditPlugin.getPlugin().getService(
					IRepositoryService.class).getRepositoryById(
					synchronizationMetadata.getRepositoryId());
			if (itemById != null) {
				try {
					AbstractExtensionRepository itemByRepository = InfomngmntEditPlugin.getPlugin()
							.getService(IRepositoryExtensionService.class).getItemByRepository(
									itemById);
					IChangeSetDefinition changeSetDefinitionForType = itemByRepository
							.getChangeSetDefinitionForType(newValue.getType());
					if (changeSetDefinitionForType != null) {
						List<String> relevantObjectPaths = changeSetDefinitionForType
								.getRelevantObjectPaths();
						for (String string : relevantObjectPaths) {
							Path2ObjectMapper newValueMapper = new Path2ObjectMapper(string,
									newValue, null);
							Path2ObjectMapper oldValueMapper = new Path2ObjectMapper(string,
									oldValue, null);
							Object newFragmentValue = newValueMapper.getObjectForPath(false, false);
							Object oldFragmentValue = oldValueMapper.getObjectForPath(false, false);
							boolean equals = false;
							if (newFragmentValue instanceof EObject
									&& oldFragmentValue instanceof EObject) {
								equals = EcoreUtil.equals((EObject) newFragmentValue,
										(EObject) oldFragmentValue);
							} else if (newFragmentValue != null) {
								equals = newFragmentValue.equals(oldFragmentValue);
							} else if (oldFragmentValue != null) {
								equals = oldFragmentValue.equals(newFragmentValue);
							} else if (newFragmentValue == null && oldFragmentValue == null) {
								equals = true;
							}
							if (!equals) {
								if (synchronizationMetadata.getSyncState() != SynchronizationState.NOT_ADDED) {
									synchronizationMetadata
											.setSyncState(SynchronizationState.LOCAL_EDITED);
									break;
								}
							}
						}

					}
				} catch (CoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	public void handleCreated(final InformationUnit newValue) {
		// TODO Auto-generated method stub

	}

	public void handleDeleted(final String informationUnitId) {
		// TODO Auto-generated method stub

	}

}
