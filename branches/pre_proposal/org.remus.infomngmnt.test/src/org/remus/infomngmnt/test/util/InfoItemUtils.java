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

package org.remus.infomngmnt.test.util;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.core.commands.CommandFactory;
import org.remus.infomngmnt.core.model.InformationStructureEdit;
import org.remus.infomngmnt.plaintext.Activator;
import org.remus.infomngmnt.util.CategoryUtil;
import org.remus.infomngmnt.util.EditingUtil;
import org.remus.infomngmnt.util.IdFactory;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class InfoItemUtils {

	public static InformationUnit createSimpleTextInfoUnit(final Category parent) {
		InformationStructureEdit edit = InformationStructureEdit.newSession(Activator.INFO_TYPE_ID);
		InformationUnit newInformationUnit = edit.newInformationUnit();
		newInformationUnit.setLabel("Plain");
		edit.setValue(newInformationUnit, Activator.INFO_TYPE_ID, "TEST1234");
		CompoundCommand createINFOTYPE = CommandFactory.CREATE_INFOTYPE(newInformationUnit, parent,
				new NullProgressMonitor());
		EditingUtil.getInstance().getNavigationEditingDomain().getCommandStack().execute(
				createINFOTYPE);
		return newInformationUnit;
	}

	public static Category createNewCategory(final String name, final String parentPath) {
		Category createCategory = InfomngmntFactory.eINSTANCE.createCategory();
		createCategory.setId(IdFactory.createNewId(null));
		createCategory.setLabel(name);
		Category findCategory = CategoryUtil.findCategory(parentPath, false);
		AdapterFactoryEditingDomain navigationEditingDomain = EditingUtil.getInstance()
				.getNavigationEditingDomain();
		Command createCATEGORY2 = CommandFactory.CREATE_CATEGORY(findCategory, createCategory,
				navigationEditingDomain);
		navigationEditingDomain.getCommandStack().execute(createCATEGORY2);
		return createCategory;
	}

	public static void clearCat(final Category cat) {
		EList<InformationUnitListItem> informationUnit = cat.getInformationUnit();
		Command command1 = CommandFactory.DELETE_INFOUNIT_WITHOUT_SYNC_CHECK(informationUnit,
				EditingUtil.getInstance().getEditingDomain());
		CompoundCommand cc = new CompoundCommand();
		cc.append(command1);
		EObject[] allChildren = CategoryUtil.getAllChildren(cat,
				InfomngmntPackage.Literals.CATEGORY);
		for (EObject eObject : allChildren) {
			cc.append(CommandFactory.DELETE_CATEGORY((Category) eObject, EditingUtil.getInstance()
					.getNavigationEditingDomain()));
		}
		EditingUtil.getInstance().getNavigationEditingDomain().getCommandStack().execute(cc);
	}

}
