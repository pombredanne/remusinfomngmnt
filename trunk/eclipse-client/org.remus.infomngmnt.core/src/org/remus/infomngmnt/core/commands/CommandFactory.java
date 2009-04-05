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

package org.remus.infomngmnt.core.commands;

import java.util.ArrayList;
import java.util.Collections;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.CreateChildCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.osgi.util.NLS;

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.Link;
import org.remus.infomngmnt.SynchronizableObject;
import org.remus.infomngmnt.core.model.ApplicationModelPool;

/**
 * <p>
 * This class generates application specific emf commands. The commands can be
 * executed on a specified editing domain.
 * </p>
 * <p>
 * <b>It is highly recommended to use this class for extending the application
 * with its own features.</b> Working without emf based command can cause
 * inconsistencies.
 * </p>
 * 
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class CommandFactory {

	/**
	 * <p>
	 * Creates a new EMF Command for adding a {@link InformationUnit} as
	 * link-target to a source information target. The link is created
	 * inplicitelly.
	 * </p>
	 * 
	 * @param source
	 *            the base info type
	 * @param target
	 *            the link target
	 * @param domain
	 *            the editing context
	 * @return the command for creating a child.
	 * @deprecated use
	 *             {@link #CREATE_LINK(InformationUnit, Link, EditingDomain)}
	 *             instead.
	 */
	@Deprecated
	public static CreateChildCommand CREATE_LINK(final InformationUnit source,
			final InformationUnit target, final EditingDomain domain) {
		Link createLink = InfomngmntFactory.eINSTANCE.createLink();
		createLink.setTarget(target);
		return new CreateChildCommand(domain, source,
				InfomngmntPackage.Literals.INFORMATION_UNIT__LINKS, createLink,
				Collections.EMPTY_LIST);
	}

	public static CreateChildCommand CREATE_LINK(final InformationUnit source, final Link link,
			final EditingDomain domain) {
		CreateChildCommand createChildCommand = new CreateChildCommand(domain, source,
				InfomngmntPackage.Literals.INFORMATION_UNIT__LINKS, link, Collections.EMPTY_LIST);
		createChildCommand.setLabel("Create link");
		return createChildCommand;
	}

	public static Command CREATE_CATEGORY(final Category parentCategory,
			final Category newCategory, final EditingDomain editingDomain) {
		CreateChildCommand createChildCommand = new CreateChildCommand(editingDomain,
				parentCategory, InfomngmntPackage.Literals.CATEGORY__CHILDREN, newCategory,
				Collections.EMPTY_LIST);
		createChildCommand.setLabel(NLS.bind("Create category {0}", newCategory.getLabel()));
		return createChildCommand;
	}

	public static Command CREATE_ROOTCATEGORY(final Category newCategory,
			final EditingDomain editingDomain) {
		CreateChildCommand createChildCommand = new CreateChildCommand(editingDomain,
				ApplicationModelPool.getInstance().getModel(),
				InfomngmntPackage.Literals.APPLICATION_ROOT__ROOT_CATEGORIES, newCategory,
				Collections.EMPTY_LIST);
		CompoundCommand compoundCommand = new CompoundCommand(new ArrayList<Command>(Collections
				.singleton(createChildCommand))) {
			@Override
			public boolean canUndo() {
				return false;
			}
		};
		compoundCommand.setLabel(NLS.bind("Create category {0}", newCategory.getLabel()));
		return compoundCommand;
	}

	public static Command CREATE_INFOTYPE(final InformationUnit newItem,
			final Category parentCategory) {
		return new CreateInfoTypeCommand(newItem, parentCategory);
	}

	public static Command SET_WORKSPACEPATH(final InformationUnitListItem item,
			final String newPath, final EditingDomain editingDomain) {
		return new SetCommand(editingDomain, item,
				InfomngmntPackage.Literals.INFORMATION_UNIT_LIST_ITEM__WORKSPACE_PATH, newPath);
	}

	public static Command MOVE_INFOUNIT_COMMAND(final InformationUnitListItem item,
			final String targetPath) {
		return new MoveInformationUnitCommand(item, targetPath);
	}

	public static Command REMOVE_SYNCDATACOMMAND(final SynchronizableObject synchronizableObject,
			final EditingDomain domain) {
		return new SetCommand(domain, synchronizableObject,
				InfomngmntPackage.Literals.SYNCHRONIZABLE_OBJECT__SYNCHRONIZATION_META_DATA, null);
	}

	public static Command DELETE_CATEGORY(final Category category, final EditingDomain domain) {
		return new DeleteCategoryCommand(category, domain);
	}

}
