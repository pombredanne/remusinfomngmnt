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

package org.remus.infomngmnt.ui.views.action;

import java.util.Collection;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.ui.action.CommandActionHandler;

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.util.CategoryUtil;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class MarkAsReadAction extends CommandActionHandler {

	public MarkAsReadAction() {
		super(null);
		setText("Mark as read");
	}

	@Override
	public Command createCommand(final Collection<?> selection) {
		CompoundCommand cc = new CompoundCommand();
		for (Object object : selection) {
			if (object instanceof Category) {
				InformationUnitListItem[] allInfoUnitItems = CategoryUtil
						.getAllInfoUnitItems((Category) object);
				for (InformationUnitListItem informationUnitListItem : allInfoUnitItems) {
					cc.append(createCommand(informationUnitListItem));
				}
			}
			if (object instanceof InformationUnitListItem) {
				cc.append(createCommand((InformationUnitListItem) object));
			}
		}
		cc.setLabel("Mark as read");
		return cc;
	}

	private Command createCommand(final InformationUnitListItem informationUnitListItem) {
		return SetCommand.create(this.domain, informationUnitListItem,
				InfomngmntPackage.Literals.INFORMATION_UNIT_LIST_ITEM__UNREAD, false);
	}

}
