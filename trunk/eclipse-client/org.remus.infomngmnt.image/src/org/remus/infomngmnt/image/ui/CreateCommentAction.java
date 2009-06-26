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

package org.remus.infomngmnt.image.ui;

import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.action.Action;

import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.core.model.InformationUtil;
import org.remus.infomngmnt.image.ImagePlugin;
import org.remus.infomngmnt.image.comments.ShapableInfoDelegate;
import org.remus.infomngmnt.image.gef.IEditingDomainHolder;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class CreateCommentAction extends Action implements IEditingDomainHolder {

	private final InformationUnit image2;

	private EditingDomain editingDomain;

	public CreateCommentAction(final InformationUnit image) {
		this.image2 = image;
		setText("New Comment");
	}

	@Override
	public void run() {
		InformationUnit childByType = InformationUtil.getChildByType(this.image2,
				ImagePlugin.NODE_NAME_LINKS);
		InformationUnit createInformationUnit = InfomngmntFactory.eINSTANCE.createInformationUnit();
		createInformationUnit.setType(ImagePlugin.NODE_NAME_LINK);
		ShapableInfoDelegate shapableInfoDelegate = new ShapableInfoDelegate(createInformationUnit,
				new java.awt.Dimension(300, 300), this.editingDomain);
		shapableInfoDelegate.setLocation(new java.awt.Point(10, 10));
		shapableInfoDelegate.setSize(new java.awt.Dimension(30, 30));
		AddCommand add = new AddCommand(this.editingDomain, childByType,
				InfomngmntPackage.Literals.INFORMATION_UNIT__CHILD_VALUES, shapableInfoDelegate
						.getOrigInfoObject());
		add.setLabel("Add new comment");
		this.editingDomain.getCommandStack().execute(add);
		shapableInfoDelegate.dispose();

	}

	public EditingDomain getEditingDomain() {
		return this.editingDomain;
	}

	public void setEditingDomain(final EditingDomain editingDomain) {
		this.editingDomain = editingDomain;
	}

}
