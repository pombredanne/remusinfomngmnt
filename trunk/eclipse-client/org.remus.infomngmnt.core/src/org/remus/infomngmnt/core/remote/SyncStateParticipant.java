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

import org.eclipse.core.resources.IProject;
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
import org.remus.infomngmnt.core.model.InformationStructureRead;
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
			if (synchronizationMetadata.getSyncState() == SynchronizationState.LOCAL_EDITED
					|| synchronizationMetadata.getSyncState() == SynchronizationState.NOT_ADDED
					|| synchronizationMetadata.getSyncState() == SynchronizationState.IGNORED) {
				return;
			}
			if (synchronizationMetadata.isCurrentlySyncing()) {
				return;
			}
			RemoteRepository itemById = InfomngmntEditPlugin.getPlugin().getService(
					IRepositoryService.class).getRepositoryById(
					synchronizationMetadata.getRepositoryId());
			if (itemById != null) {
				InformationStructureRead readOld = InformationStructureRead.newSession(oldValue);
				InformationStructureRead readNew = InformationStructureRead.newSession(newValue);
				try {
					AbstractExtensionRepository itemByRepository = InfomngmntEditPlugin.getPlugin()
							.getService(IRepositoryExtensionService.class).getItemByRepository(
									itemById);
					IChangeSetDefinition changeSetDefinitionForType = itemByRepository
							.getChangeSetDefinitionForType(newValue.getType());
					if (changeSetDefinitionForType != null) {
						List<String> relevantObjectPaths = changeSetDefinitionForType
								.getRelevantObjectPaths();
						for (String string2 : relevantObjectPaths) {
							try {
								EObject newFragmentValue = readOld.getChildByPath(string2
										.split("/"));
								EObject oldFragmentValue = readNew.getChildByPath(string2
										.split("/"));
								boolean equals = checkEqual(oldFragmentValue, newFragmentValue);
								if (!equals) {
									synchronizationMetadata
											.setSyncState(SynchronizationState.LOCAL_EDITED);
									break;
								}
							} catch (RuntimeException e) {
								synchronizationMetadata
										.setSyncState(SynchronizationState.LOCAL_EDITED);
								break;
							}

						}
						List<String> relevantObjectPathValues = changeSetDefinitionForType
								.getRelevantObjectPathValues();
						for (String string2 : relevantObjectPathValues) {
							try {
								Object newFragmentValue = readOld
										.getValueByPath(string2.split("/"));
								Object oldFragmentValue = readNew
										.getValueByPath(string2.split("/"));
								boolean equals = checkEqual(oldFragmentValue, newFragmentValue);
								if (!equals) {
									synchronizationMetadata
											.setSyncState(SynchronizationState.LOCAL_EDITED);
									break;
								}
							} catch (RuntimeException e) {
								synchronizationMetadata
										.setSyncState(SynchronizationState.LOCAL_EDITED);
								break;
							}

						}

						List<String> relevantObjectIdValues = changeSetDefinitionForType
								.getRelevantObjectIdValues();
						for (String string2 : relevantObjectIdValues) {
							try {
								Object newFragmentValue = readOld.getValueByNodeId(string2);
								Object oldFragmentValue = readNew.getValueByNodeId(string2);
								boolean equals = checkEqual(oldFragmentValue, newFragmentValue);
								if (!equals) {
									synchronizationMetadata
											.setSyncState(SynchronizationState.LOCAL_EDITED);
									break;

								}
							} catch (RuntimeException e) {
								synchronizationMetadata
										.setSyncState(SynchronizationState.LOCAL_EDITED);
								break;
							}

						}
						List<String> relevantObjectIds = changeSetDefinitionForType
								.getRelevantObjectIds();
						for (String string2 : relevantObjectIds) {
							try {
								EObject oldFragmentValue = readOld.getChildByNodeId(string2);
								EObject newFragmentValue = readNew.getChildByNodeId(string2);
								boolean equals = checkEqual(oldFragmentValue, newFragmentValue);
								if (!equals) {
									synchronizationMetadata
											.setSyncState(SynchronizationState.LOCAL_EDITED);
									break;
								}
							} catch (RuntimeException e) {
								synchronizationMetadata
										.setSyncState(SynchronizationState.LOCAL_EDITED);
								break;
							}
						}
					} else {
						synchronizationMetadata.setSyncState(SynchronizationState.LOCAL_EDITED);
					}
				} catch (CoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	private boolean checkEqual(final Object oldFragmentValue, final Object newFragmentValue) {
		boolean equals = false;
		if (newFragmentValue instanceof EObject && oldFragmentValue instanceof EObject) {
			equals = EcoreUtil.equals((EObject) newFragmentValue, (EObject) oldFragmentValue);
		} else if (newFragmentValue != null) {
			equals = newFragmentValue.equals(oldFragmentValue);
		} else if (oldFragmentValue != null) {
			equals = oldFragmentValue.equals(newFragmentValue);
		} else if (newFragmentValue == null && oldFragmentValue == null) {
			equals = true;
		}
		return equals;
	}

	public void handleCreated(final InformationUnit newValue) {
		// TODO Auto-generated method stub

	}

	public void handleDeleted(final String informationUnitId) {
		// TODO Auto-generated method stub

	}

	public void handleClean(final IProject project) {
		// TODO Auto-generated method stub

	}

}
