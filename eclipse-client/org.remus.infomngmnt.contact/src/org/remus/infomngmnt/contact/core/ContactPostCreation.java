/*******************************************************************************
 * Copyright (c) 2010 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/

package org.remus.infomngmnt.contact.core;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.EList;
import org.eclipse.remus.InformationUnit;
import org.eclipse.remus.common.core.util.KeyValueObject;
import org.eclipse.remus.core.create.PostCreationHandler;
import org.eclipse.remus.core.model.InformationStructureEdit;
import org.eclipse.remus.core.model.InformationStructureRead;

import org.remus.infomngmnt.contact.ContactActivator;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class ContactPostCreation extends PostCreationHandler {

	@Override
	public Command handlePreSaving(final InformationUnit unit, final IProgressMonitor monitor) {
		InformationStructureRead read = InformationStructureRead.newSession(unit);
		EList<InformationUnit> dynamicList = read
				.getDynamicList(ContactActivator.NODE_NAME_ADDRESSES);
		List<String> presentAdressTypes = new ArrayList<String>();
		if (dynamicList.size() != 7) {
			for (InformationUnit informationUnit : dynamicList) {
				InformationStructureRead adressTypeRead = InformationStructureRead.newSession(
						informationUnit, ContactActivator.TYPE_ID);
				presentAdressTypes.add((String) adressTypeRead
						.getValueByNodeId(ContactActivator.NODE_NAME_ADDRESS));
			}
		}
		List<String> availableAdressTypes = new ArrayList<String>();
		KeyValueObject[] adressCollection = ContactUtil.getAdressCollection();
		for (KeyValueObject keyValueObject : adressCollection) {
			availableAdressTypes.add(keyValueObject.getId());
		}
		InformationStructureEdit edit = InformationStructureEdit
				.newSession(ContactActivator.TYPE_ID);
		for (String str : availableAdressTypes) {
			if (!presentAdressTypes.contains(str)) {
				InformationUnit newAdressType = edit.createSubType(
						ContactActivator.NODE_NAME_ADDRESS, str);
				edit.addDynamicNode(unit, newAdressType, this.editingDomain);
			}
		}
		return super.handlePreSaving(unit, monitor);
	}

}
