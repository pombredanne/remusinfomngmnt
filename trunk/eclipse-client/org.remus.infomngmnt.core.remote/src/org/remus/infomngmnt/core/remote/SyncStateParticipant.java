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
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.SetCommand;

import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.SynchronizableObject;
import org.remus.infomngmnt.SynchronizationMetadata;
import org.remus.infomngmnt.SynchronizationState;
import org.remus.infomngmnt.core.extension.ISaveParticipant;
import org.remus.infomngmnt.core.model.InformationStructureRead;
import org.remus.infomngmnt.core.remote.services.IRepositoryExtensionService;
import org.remus.infomngmnt.core.remote.sync.SyncStateParticipantNotfier;
import org.remus.infomngmnt.core.services.IEditingHandler;
import org.remus.infomngmnt.core.services.ISynchronizationItemCache;
import org.remus.infomngmnt.model.remote.IChangeSetDefinition;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class SyncStateParticipant implements ISaveParticipant {

	private ISynchronizationItemCache service;

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
			if (SyncStateParticipantNotfier.isCleanAndClear(newValue.getId())) {
				return;
			}

			IRepositoryExtensionService extensionService = RemoteActivator.getDefault()
					.getServiceTracker().getService(IRepositoryExtensionService.class);
			AbstractExtensionRepository itemByRepository = extensionService
					.getItemByLocalObject((SynchronizableObject) adapter);

			if (itemByRepository != null) {
				InformationStructureRead readOld = InformationStructureRead.newSession(oldValue);
				InformationStructureRead readNew = InformationStructureRead.newSession(newValue);
				IChangeSetDefinition changeSetDefinitionForType = itemByRepository
						.getChangeSetDefinitionForType(newValue.getType());
				if (changeSetDefinitionForType != null) {
					List<String> relevantObjectIdValues = changeSetDefinitionForType
							.getRelevantObjectIdValues();
					for (String string2 : relevantObjectIdValues) {
						try {
							Object newFragmentValue = readOld.getValueByNodeId(string2);
							Object oldFragmentValue = readNew.getValueByNodeId(string2);
							boolean equals = checkEqual(oldFragmentValue, newFragmentValue);
							if (!equals) {
								createSetCommandAndExecute(synchronizationMetadata);
								break;

							}
						} catch (RuntimeException e) {
							createSetCommandAndExecute(synchronizationMetadata);
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
								createSetCommandAndExecute(synchronizationMetadata);
								break;
							}
						} catch (RuntimeException e) {
							createSetCommandAndExecute(synchronizationMetadata);
							break;
						}
					}
				} else {
					createSetCommandAndExecute(synchronizationMetadata);
				}
			}
		}

	}

	private void createSetCommandAndExecute(final SynchronizationMetadata synchronizationMetadata) {
		IEditingHandler service = RemoteActivator.getDefault().getServiceTracker().getService(
				IEditingHandler.class);
		Command create = SetCommand.create(service.getNavigationEditingDomain(),
				synchronizationMetadata,
				InfomngmntPackage.Literals.SYNCHRONIZATION_METADATA__SYNC_STATE,
				SynchronizationState.LOCAL_EDITED);
		service.execute(create);
		RemoteActivator.getDefault().getServiceTracker().ungetService(service);
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

	}

	public void handleDeleted(final String informationUnitId) {

	}

	public void handleClean(final IProject project) {

	}

}
