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

package org.remus.infomngmnt.ui.dnd;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.core.commands.CommandFactory;
import org.remus.infomngmnt.core.model.CategoryUtil;
import org.remus.infomngmnt.core.model.EditingUtil;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class NavigationDropHelper {

	public static boolean canDrop(Collection<?> source, Object target) {

		/*
		 * first condition: source & target must not be the same
		 */
		for (Object dragElement : source) {
			if (dragElement == target) {
				return false;
			}
		}
		/*
		 * second condition: target must not be a child of target.
		 * if a drop would be allowed there would be two objects
		 * with the same id. In general I don't understand why this
		 * is not permitted by emfs default dropadapter.
		 */
		for (Object object : source) {
			if (target instanceof EObject && object instanceof Category) {
				boolean isParent = CategoryUtil.isItemParentOfCategory((EObject) target, (Category)object);
				if (isParent) {
					return false;
				}
			}
		}
		/*
		 * third condition the dragged items must not be a direct child
		 * of the target.
		 */
		for (Object object : source) {
			if (object instanceof EObject
					&& ((EObject) object).eContainer() == target) {
				return false;
			}
		}
		return true;
	}

	public static Command checkProjectRelevance(Collection<?> source, Object target, Command originCommand) {
		List<InformationUnitListItem> relevantItems = new LinkedList<InformationUnitListItem>();
		for (Object object : source) {
			if (object instanceof InformationUnitListItem &&
					target instanceof EObject
					&& !((EObject) object).eResource().equals(((EObject) target).eResource())) {
				relevantItems.add((InformationUnitListItem) object);
			}
		}
		if (relevantItems.size() == 0) {
			// we're moving elements within a project. all fine.
			return originCommand;
		}
		CompoundCommand compoundCommand = new CompoundCommand();
		compoundCommand.append(originCommand);
		for (InformationUnitListItem informationUnitListItem : relevantItems) {
			String targetPath = calculateNewWorkspacePath(informationUnitListItem,(EObject) target);
			Command moveResourceCommand = CommandFactory.MOVE_INFOUNIT_COMMAND(informationUnitListItem, targetPath);
			Command setWorkspacePathCommand = CommandFactory.SET_WORKSPACEPATH(
					informationUnitListItem,
					targetPath,
					EditingUtil.getInstance().getNavigationEditingDomain());
			compoundCommand.append(moveResourceCommand);
			compoundCommand.append(setWorkspacePathCommand);
		}
		compoundCommand.setLabel(originCommand.getLabel());
		compoundCommand.setDescription(originCommand.getLabel());
		return compoundCommand;

	}

	private static String calculateNewWorkspacePath(InformationUnitListItem source, EObject target) {
		Path targetPath = new Path(target.eResource().getURI().toPlatformString(true));
		Path sourcePath = new Path(source.getWorkspacePath());
		return new Path(targetPath.segment(0)).append(sourcePath.removeFirstSegments(1)).toOSString();
	}

}
