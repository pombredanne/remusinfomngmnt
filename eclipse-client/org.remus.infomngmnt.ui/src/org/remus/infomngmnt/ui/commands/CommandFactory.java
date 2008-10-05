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

package org.remus.infomngmnt.ui.commands;

import java.util.Collections;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.command.CreateChildCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.Link;
import org.remus.infomngmnt.core.model.LinkUtil;
import org.remus.infomngmnt.ui.create.CreateInfoTypeCommand;

/**
 * <p>
 * This class generates application specific emf commands. The
 * commands can be executed on a specified editing domain.
 * </p>
 * <p>
 * <b>It is highly recommended to use this class for extending
 * the application with its own features.</b> Working without emf based
 * command can cause inconsistencies.
 * </p>
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class CommandFactory {


	/**
	 * <p>
	 * Creates a new EMF Command for adding a {@link InformationUnit} as
	 * link-target to a source information target. The link is created
	 * inplicitelly.
	 * </p>
	 * @param source the base info type
	 * @param target the link target
	 * @param domain the editing context
	 * @return the command for creating a child.
	 * @deprecated use {@link #CREATE_LINK(InformationUnit, Link, EditingDomain)} instead.
	 */
	@Deprecated
	public static CreateChildCommand CREATE_LINK(InformationUnit source, InformationUnit target, EditingDomain domain) {
		Link createLink = InfomngmntFactory.eINSTANCE.createLink();
		createLink.setLinktype(LinkUtil.getInstance().getLinkTypes().getAvailableLinkTypes().get(0).getValue());
		createLink.setTarget(target);
		return new CreateChildCommand(domain, source,InfomngmntPackage.Literals.INFORMATION_UNIT__LINKS,createLink, Collections.EMPTY_LIST);
	}
	public static CreateChildCommand CREATE_LINK(InformationUnit source, Link link, EditingDomain domain) {
		CreateChildCommand createChildCommand = new CreateChildCommand(domain, source,InfomngmntPackage.Literals.INFORMATION_UNIT__LINKS, link, Collections.EMPTY_LIST);
		createChildCommand.setLabel("Create link");
		return createChildCommand;
	}

	public static Command CREATE_INFOTYPE(InformationUnit newItem, Category parentCategory) {
		return new CreateInfoTypeCommand(newItem,parentCategory);
	}


}
