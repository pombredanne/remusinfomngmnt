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

package org.remus.infomngmnt.core.sync;

import java.util.Iterator;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.compare.diff.metamodel.AddModelElement;
import org.eclipse.emf.compare.diff.metamodel.AttributeChange;
import org.eclipse.emf.compare.diff.metamodel.ConflictingDiffElement;
import org.eclipse.emf.compare.diff.metamodel.DiffElement;
import org.eclipse.emf.compare.diff.metamodel.DiffFactory;
import org.eclipse.emf.compare.diff.metamodel.DiffGroup;
import org.eclipse.emf.compare.diff.metamodel.DiffModel;
import org.eclipse.emf.compare.diff.metamodel.DiffPackage;
import org.eclipse.emf.compare.diff.metamodel.RemoveModelElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.query.conditions.eobjects.EObjectCondition;
import org.eclipse.emf.query.statements.FROM;
import org.eclipse.emf.query.statements.SELECT;
import org.eclipse.emf.query.statements.WHERE;

import org.remus.infomngmnt.ChangeSet;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.SynchronizableObject;
import org.remus.infomngmnt.SynchronizationAction;
import org.remus.infomngmnt.SynchronizationMetadata;
import org.remus.infomngmnt.SynchronizationState;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class PostDiffProcessor {

	public void onSynchronize(final ChangeSet changeSet, final DiffModel diffModel) {
		EList<DiffElement> ownedElements = diffModel.getOwnedElements();
		for (DiffElement diffElement : ownedElements) {
			if (diffElement instanceof RemoveModelElement) {
				RemoveModelElement currentDiffElement = (RemoveModelElement) diffElement;
				/*
				 * Case: Online present Local not present
				 */
				final EObject leftElement = ((RemoveModelElement) diffElement).getLeftElement();
				/*
				 * Case:
				 */
				if (leftElement instanceof SynchronizableObject) {
					/*
					 * We're searching in deleted elements. If found we have to
					 * delete that.
					 */

					EObjectCondition typeRelationCondition = null;

					EObjectCondition valueCondition = new EObjectCondition() {
						@Override
						public boolean isSatisfied(final EObject eObject) {
							return ((SynchronizableObject) eObject).getSynchronizationMetaData() != null
									&& ((SynchronizableObject) leftElement)
											.getSynchronizationMetaData().getUrl().equals(
													((SynchronizableObject) eObject)
															.getSynchronizationMetaData().getUrl());
						}
					};
					SELECT select = new SELECT(new FROM(currentDiffElement.getRightParent()),
							new WHERE(typeRelationCondition.AND(valueCondition)));
					Set<? extends EObject> eObjects = select.execute().getEObjects();
					if (eObjects.size() != 0) {
						Iterator<? extends EObject> iterator = eObjects.iterator();
						while (iterator.hasNext()) {
							EObject eObject = iterator.next();
							if (((SynchronizableObject) eObject).getSynchronizationMetaData() != null
									&& !((SynchronizableObject) leftElement)
											.getSynchronizationMetaData()
											.getHash()
											.equals(
													((SynchronizableObject) eObject)
															.getSynchronizationMetaData().getHash())) {
								// we have a local deleted element and a remote
								// updated element --> Conflict
								ConflictingDiffElement conflictElement = DiffFactory.eINSTANCE
										.createConflictingDiffElement();

							}
						}
						changeSet.getChangeSetItems().get(0).getSyncObjectActionMap().put(
								(SynchronizableObject) leftElement,
								SynchronizationAction.DELETE_REMOTE);
					} else {
						changeSet.getChangeSetItems().get(0).getSyncObjectActionMap()
								.put((SynchronizableObject) leftElement,
										SynchronizationAction.ADD_LOCAL);
					}
				}
			} else if (diffElement instanceof AddModelElement) {
				// changeSet.getChangeSetItems().get(0).getSyncObjectActionMap().put((SynchronizableObject)
				// rightElement, SynchronizationAction.ADD_REMOTE);

			} else if (diffElement instanceof DiffGroup
					&& ((DiffGroup) diffElement).getLeftParent() instanceof InformationUnitListItem) {
				// change of a information-item. check wherether its a remote or
				// local edit
				EObject rightElement = (EObject) diffElement
						.eGet(DiffPackage.Literals.DIFF_GROUP__LEFT_PARENT);
				EList<DiffElement> subDiffElements = diffElement.getSubDiffElements();
				for (DiffElement diffElement2 : subDiffElements) {
					if (diffElement2 instanceof DiffGroup
							&& ((DiffGroup) diffElement).getLeftParent() instanceof SynchronizationMetadata) {
						EList<DiffElement> subDiffElements2 = diffElement2.getSubDiffElements();
						for (DiffElement diffElement3 : subDiffElements2) {
							if (diffElement3 instanceof AttributeChange) {
								if (((AttributeChange) diffElement3).getAttribute() == InfomngmntPackage.Literals.SYNCHRONIZATION_METADATA__HASH) {
									SynchronizationMetadata onlineElement = (SynchronizationMetadata) diffElement3
											.eGet(DiffPackage.Literals.ATTRIBUTE_CHANGE__LEFT_ELEMENT);
									SynchronizationMetadata localElement = (SynchronizationMetadata) diffElement3
											.eGet(DiffPackage.Literals.ATTRIBUTE_CHANGE__RIGHT_ELEMENT);
									boolean remoteChange = !onlineElement.getHash().equals(
											localElement.getHash());
									boolean localChange = localElement.getSyncState() != SynchronizationState.IN_SYNC;
									if (remoteChange && localChange) {

										// we have a conflict
									}
								}
							}
						}
					}
				}
			}
		}
	}

}
