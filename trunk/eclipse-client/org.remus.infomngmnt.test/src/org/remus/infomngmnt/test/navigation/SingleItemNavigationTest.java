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

package org.remus.infomngmnt.test.navigation;

import java.util.Collections;

import junit.framework.Assert;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.junit.Before;
import org.junit.Test;

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.core.commands.CommandFactory;
import org.remus.infomngmnt.core.commands.DeleteCategoryCommand;
import org.remus.infomngmnt.core.model.ApplicationModelPool;
import org.remus.infomngmnt.test.util.InfoItemUtils;
import org.remus.infomngmnt.util.EditingUtil;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class SingleItemNavigationTest {

	private final EditingDomain domain = EditingUtil.getInstance().getNavigationEditingDomain();

	public static final String CAT_SUB_SUB_1 = "CAT_SUB_SUB_1"; //$NON-NLS-1$

	public static final String CAT_SUB_1 = "CAT_SUB_1"; //$NON-NLS-1$

	private Category createCategory;

	private Category subCategory;

	@Before
	public void initialize() throws Exception {
		this.createCategory = InfoItemUtils.createNewCategory(CAT_SUB_1, "Inbox");
		this.subCategory = InfoItemUtils.createNewCategory(CAT_SUB_SUB_1, "Inbox/" + CAT_SUB_1);
	}

	public void cleanUp() {
		DeleteCategoryCommand deleteCategoryCommand = new DeleteCategoryCommand(
				this.createCategory, EditingUtil.getInstance().getNavigationEditingDomain());
		EditingUtil.getInstance().getNavigationEditingDomain().getCommandStack().execute(
				deleteCategoryCommand);
	}

	@Test
	public void createItem() throws Exception {
		InfoItemUtils.createSimpleTextInfoUnit(this.createCategory);
		Assert.assertEquals(1, this.createCategory.getInformationUnit().size());
	}

	@Test
	public void createItemAndCheckCache() throws Exception {
		InformationUnit createSimpleTextInfoUnit = InfoItemUtils
				.createSimpleTextInfoUnit(this.createCategory);
		InformationUnitListItem itemById = ApplicationModelPool.getInstance().getItemById(
				createSimpleTextInfoUnit.getId(), new NullProgressMonitor());
		Assert.assertNotNull(itemById);
	}

	@Test
	public void deleteItem() throws Exception {
		InformationUnit createSimpleTextInfoUnit = InfoItemUtils
				.createSimpleTextInfoUnit(this.createCategory);
		IFile file = (IFile) createSimpleTextInfoUnit.getAdapter(IFile.class);
		Assert.assertTrue(file.exists());
		InformationUnitListItem itemById = ApplicationModelPool.getInstance().getItemById(
				createSimpleTextInfoUnit.getId(), new NullProgressMonitor());
		Command command = CommandFactory.DELETE_INFOUNIT(Collections.singletonList(itemById),
				this.domain);
		this.domain.getCommandStack().execute(command);

		Assert.assertFalse(file.exists());
		itemById = ApplicationModelPool.getInstance().getItemById(createSimpleTextInfoUnit.getId(),
				new NullProgressMonitor());
		Assert.assertNull(itemById);

	}

	@Test
	public void deleteItemAndRestore() throws Exception {
		InformationUnit createSimpleTextInfoUnit = InfoItemUtils
				.createSimpleTextInfoUnit(this.createCategory);
		IFile file = (IFile) createSimpleTextInfoUnit.getAdapter(IFile.class);
		Assert.assertTrue(file.exists());
		InformationUnitListItem itemById = ApplicationModelPool.getInstance().getItemById(
				createSimpleTextInfoUnit.getId(), new NullProgressMonitor());
		Command command = CommandFactory.DELETE_INFOUNIT(Collections.singletonList(itemById),
				this.domain);
		this.domain.getCommandStack().execute(command);

		Assert.assertFalse(file.exists());
		itemById = ApplicationModelPool.getInstance().getItemById(createSimpleTextInfoUnit.getId(),
				new NullProgressMonitor());
		Assert.assertNull(itemById);

		this.domain.getCommandStack().undo();
		Assert.assertTrue(file.exists());
		itemById = ApplicationModelPool.getInstance().getItemById(createSimpleTextInfoUnit.getId(),
				new NullProgressMonitor());
		Assert.assertNotNull(itemById);

	}

}
