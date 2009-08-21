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

package org.remus.infomngmnt.test.remote.connector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.Date;
import java.util.Map.Entry;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.compare.diff.metamodel.DiffModel;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.ui.actions.WorkspaceModifyDelegatingOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.ChangeSet;
import org.remus.infomngmnt.ChangeSetItem;
import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.RemoteContainer;
import org.remus.infomngmnt.RemoteRepository;
import org.remus.infomngmnt.RepositoryCollection;
import org.remus.infomngmnt.SynchronizableObject;
import org.remus.infomngmnt.SynchronizationAction;
import org.remus.infomngmnt.SynchronizationMetadata;
import org.remus.infomngmnt.SynchronizationState;
import org.remus.infomngmnt.common.core.streams.FileUtil;
import org.remus.infomngmnt.common.core.streams.StreamCloser;
import org.remus.infomngmnt.common.core.streams.StreamUtil;
import org.remus.infomngmnt.common.ui.UIUtil;
import org.remus.infomngmnt.core.commands.CommandFactory;
import org.remus.infomngmnt.core.model.ApplicationModelPool;
import org.remus.infomngmnt.core.model.InformationStructureEdit;
import org.remus.infomngmnt.core.model.InformationStructureRead;
import org.remus.infomngmnt.core.services.IRepositoryService;
import org.remus.infomngmnt.core.sync.ChangeSetExecutor;
import org.remus.infomngmnt.core.sync.ChangeSetManager;
import org.remus.infomngmnt.mail.ContentType;
import org.remus.infomngmnt.mail.MailActivator;
import org.remus.infomngmnt.provider.InfomngmntEditPlugin;
import org.remus.infomngmnt.resources.util.ResourceUtil;
import org.remus.infomngmnt.test.Activator;
import org.remus.infomngmnt.test.navigation.InformationStructureReadTest;
import org.remus.infomngmnt.test.util.InfoItemUtils;
import org.remus.infomngmnt.util.CategoryUtil;
import org.remus.infomngmnt.util.EditingUtil;
import org.remus.infomngmnt.util.IdFactory;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class FolderConnectorTest {

	public static final String URL = "C:\\repositoryTest"; //$NON-NLS-1$

	public static final String UPDATED_VALUE = "UPDATED_VALUE"; //$NON-NLS-1$

	@Before
	public void createRepo() throws Exception {
		createRepository(getRepositoryTypeId());
		checkout();
	}

	private void createRepository(final String repositoryTypeId) throws IOException {
		RepositoryCollection repositories = InfomngmntEditPlugin.getPlugin().getService(
				IRepositoryService.class).getRepositories();
		RemoteRepository repository = InfomngmntFactory.eINSTANCE.createRemoteRepository();
		repository.setId(IdFactory.createNewId(null));
		repository.setRepositoryTypeId(getRepositoryTypeId());
		repository.setUrl(URL);
		repository.setName("TEST_" + getRepositoryTypeId());
		repositories.getRepositories().add(repository);

		File file = new File(URL);
		if (file.exists()) {
			FileUtil.delete(file);
		}
		file.mkdirs();

		EditingUtil.getInstance().saveObjectToResource(repositories);
	}

	private void checkout() throws Exception {
		RepositoryCollection repositories = InfomngmntEditPlugin.getPlugin().getService(
				IRepositoryService.class).getRepositories();
		RemoteRepository remoteRepository = repositories.getRepositories().get(0);
		assertNotNull(remoteRepository);

		final ChangeSetManager manager = new ChangeSetManager();
		ChangeSet changeSet = manager.createChangeSet(null, Collections
				.singletonList((RemoteContainer) remoteRepository), remoteRepository,
				ChangeSetManager.MODE_CHECKOUT_REPLACE, new NullProgressMonitor());
		changeSet.setTargetCategory(CategoryUtil.findCategory("Inbox", false));
		ChangeSetExecutor executor = new ChangeSetExecutor();
		executor.checkout(changeSet, new NullProgressMonitor());

		Category findCategory = CategoryUtil.findCategory("Inbox", false);
		assertEquals(1, findCategory.getChildren().size());
	}

	@Test
	public void syncStateAfterCreation() throws Exception {
		InformationUnit createInfoUnit = createInfoUnit(CategoryUtil.findCategory("Inbox", false)
				.getChildren().get(0));
		InformationUnitListItem adapter = (InformationUnitListItem) createInfoUnit
				.getAdapter(InformationUnitListItem.class);
		SynchronizationMetadata synchronizationMetaData = adapter.getSynchronizationMetaData();
		assertNotNull(synchronizationMetaData);
		RepositoryCollection repositories = InfomngmntEditPlugin.getPlugin().getService(
				IRepositoryService.class).getRepositories();
		RemoteRepository remoteRepository = repositories.getRepositories().get(0);
		assertEquals(synchronizationMetaData.getRepositoryId(), remoteRepository.getId());
		assertSame(synchronizationMetaData.getSyncState(), SynchronizationState.NOT_ADDED);

	}

	@Test
	public void syncStateAfterCreationAndEdit() throws Exception {
		InformationUnit createInfoUnit = createInfoUnit(CategoryUtil.findCategory("Inbox", false)
				.getChildren().get(0));
		InformationUnitListItem adapter = (InformationUnitListItem) createInfoUnit
				.getAdapter(InformationUnitListItem.class);
		SynchronizationMetadata synchronizationMetaData = adapter.getSynchronizationMetaData();
		assertNotNull(synchronizationMetaData);
		RepositoryCollection repositories = InfomngmntEditPlugin.getPlugin().getService(
				IRepositoryService.class).getRepositories();
		InformationStructureEdit edit = InformationStructureEdit
				.newSession(MailActivator.INFO_TYPE_ID);
		edit.setValue(createInfoUnit, MailActivator.NODE_NAME_RECEIVED, new Date());
		EditingUtil.getInstance().saveObjectToResource(createInfoUnit);
		RemoteRepository remoteRepository = repositories.getRepositories().get(0);
		assertEquals(synchronizationMetaData.getRepositoryId(), remoteRepository.getId());
		assertSame(synchronizationMetaData.getSyncState(), SynchronizationState.NOT_ADDED);

	}

	@Test
	public void synchronizeAfterCreation() throws Exception {
		Category category = CategoryUtil.findCategory("Inbox", false).getChildren().get(0);
		InformationUnit createInfoUnit = createInfoUnit(category);
		InformationUnitListItem adapter = (InformationUnitListItem) createInfoUnit
				.getAdapter(InformationUnitListItem.class);
		SynchronizationMetadata synchronizationMetaData = adapter.getSynchronizationMetaData();
		assertNotNull(synchronizationMetaData);
		assertSame(synchronizationMetaData.getSyncState(), SynchronizationState.NOT_ADDED);
		final ChangeSetManager manager = new ChangeSetManager();
		ChangeSet changeSet = manager.createChangeSet(category, new NullProgressMonitor());
		ChangeSetItem changeSetItem = changeSet.getChangeSetItems().get(0);
		DiffModel createDiffModel = manager.createDiffModel(changeSetItem, category);
		manager.prepareSyncActions(createDiffModel.getOwnedElements(), changeSetItem, category);
		EMap<Category, SynchronizationAction> syncCategoryActionMap = changeSetItem
				.getSyncCategoryActionMap();
		EMap<SynchronizableObject, SynchronizationAction> syncObjectActionMap = changeSetItem
				.getSyncObjectActionMap();
		assertEquals(0, syncCategoryActionMap.size());
		assertEquals(1, syncObjectActionMap.size());
		Entry<SynchronizableObject, SynchronizationAction> next = syncObjectActionMap.entrySet()
				.iterator().next();
		SynchronizableObject key = next.getKey();
		SynchronizationAction value = next.getValue();
		assertEquals(key.getSynchronizationMetaData().getUrl(), adapter
				.getSynchronizationMetaData().getUrl());
		assertSame(SynchronizationAction.ADD_REMOTE, value);
	}

	@Test
	public void commitAfterCreation() throws Exception {
		Category category = CategoryUtil.findCategory("Inbox", false).getChildren().get(0);
		InformationUnit createInfoUnit = createInfoUnit(category);
		InformationUnitListItem adapter = (InformationUnitListItem) createInfoUnit
				.getAdapter(InformationUnitListItem.class);
		SynchronizationMetadata synchronizationMetaData = adapter.getSynchronizationMetaData();
		assertNotNull(synchronizationMetaData);
		assertSame(synchronizationMetaData.getSyncState(), SynchronizationState.NOT_ADDED);
		final ChangeSetManager manager = new ChangeSetManager();
		ChangeSet changeSet = manager.createChangeSet(category, new NullProgressMonitor());
		ChangeSetItem changeSetItem = changeSet.getChangeSetItems().get(0);
		DiffModel createDiffModel = manager.createDiffModel(changeSetItem, category);
		manager.prepareSyncActions(createDiffModel.getOwnedElements(), changeSetItem, category);
		ChangeSetExecutor executor = new ChangeSetExecutor();
		executor.setChangeSet(changeSet);
		executor.synchronize(createDiffModel.getOwnedElements(), changeSetItem,
				new NullProgressMonitor(), category);
		assertSame(adapter.getSynchronizationMetaData().getSyncState(),
				SynchronizationState.IN_SYNC);
		File file = new File(URL + File.separator + "INFO_" + adapter.getId() + File.separator
				+ adapter.getId() + ResourceUtil.DOT_FILE_EXTENSION);
		assertTrue("Remote copy does not exist", file.exists());
		File file2 = new File(URL + File.separator + "INFO_" + adapter.getId() + File.separator
				+ ".binaries");
		assertTrue("Binary folder does not exisit", file2.exists());
		String[] list = file2.list();
		assertEquals(1, list.length);
	}

	@Test
	public void remoteEdited() throws Exception {
		commitAfterCreation();
		Category category = CategoryUtil.findCategory("Inbox", false).getChildren().get(0);
		InformationUnitListItem syncItem = category.getInformationUnit().get(0);
		InformationUnit adapter = (InformationUnit) syncItem.getAdapter(InformationUnit.class);
		File file = new File(URL + File.separator + "INFO_" + adapter.getId() + File.separator
				+ adapter.getId() + ResourceUtil.DOT_FILE_EXTENSION);
		file.setLastModified(System.currentTimeMillis());

		final ChangeSetManager manager = new ChangeSetManager();
		ChangeSet changeSet = manager.createChangeSet(category, new NullProgressMonitor());
		ChangeSetItem changeSetItem = changeSet.getChangeSetItems().get(0);
		DiffModel createDiffModel = manager.createDiffModel(changeSetItem, category);
		manager.prepareSyncActions(createDiffModel.getOwnedElements(), changeSetItem, category);
		Entry<SynchronizableObject, SynchronizationAction> entry = changeSetItem
				.getSyncObjectActionMap().get(0);
		assertSame(SynchronizationAction.REPLACE_LOCAL, entry.getValue());
	}

	@Test
	public void updateFromRemote() throws Exception {
		remoteEdited();
		Category category = CategoryUtil.findCategory("Inbox", false).getChildren().get(0);
		InformationUnitListItem syncItem = category.getInformationUnit().get(0);
		final ChangeSetManager manager = new ChangeSetManager();
		ChangeSet changeSet = manager.createChangeSet(category, new NullProgressMonitor());
		ChangeSetItem changeSetItem = changeSet.getChangeSetItems().get(0);
		DiffModel createDiffModel = manager.createDiffModel(changeSetItem, category);
		manager.prepareSyncActions(createDiffModel.getOwnedElements(), changeSetItem, category);
		ChangeSetExecutor executor = new ChangeSetExecutor();
		executor.setChangeSet(changeSet);
		executor.synchronize(createDiffModel.getOwnedElements(), changeSetItem,
				new NullProgressMonitor(), category);
		assertSame(SynchronizationState.IN_SYNC, syncItem.getSynchronizationMetaData()
				.getSyncState());
	}

	@Test
	public void updateFromValue() throws Exception {
		commitAfterCreation();
		Category category = CategoryUtil.findCategory("Inbox", false).getChildren().get(0);
		InformationUnitListItem syncItem = category.getInformationUnit().get(0);

		File file = new File(URL + File.separator + "INFO_" + syncItem.getId() + File.separator
				+ syncItem.getId() + ResourceUtil.DOT_FILE_EXTENSION);
		InformationUnit objectFromFileUri = EditingUtil.getInstance().getObjectFromFileUri(
				URI.createFileURI(file.getAbsolutePath()),
				InfomngmntPackage.Literals.INFORMATION_UNIT, null);
		InformationStructureEdit edit = InformationStructureEdit.newSession(objectFromFileUri
				.getType());
		edit.setValue(objectFromFileUri, MailActivator.NODE_NAME_CONTENT, UPDATED_VALUE);
		EditingUtil.getInstance().saveObjectToResource(objectFromFileUri);

		final ChangeSetManager manager = new ChangeSetManager();
		ChangeSet changeSet = manager.createChangeSet(category, new NullProgressMonitor());
		ChangeSetItem changeSetItem = changeSet.getChangeSetItems().get(0);
		DiffModel createDiffModel = manager.createDiffModel(changeSetItem, category);
		manager.prepareSyncActions(createDiffModel.getOwnedElements(), changeSetItem, category);
		ChangeSetExecutor executor = new ChangeSetExecutor();
		executor.setChangeSet(changeSet);
		executor.synchronize(createDiffModel.getOwnedElements(), changeSetItem,
				new NullProgressMonitor(), category);

		InformationUnit adapter = (InformationUnit) syncItem.getAdapter(InformationUnit.class);
		InformationStructureRead read = InformationStructureRead.newSession(adapter);
		Object valueByNodeId = read.getValueByNodeId(MailActivator.NODE_NAME_CONTENT);
		assertEquals(UPDATED_VALUE, valueByNodeId);

	}

	@Test
	public void localDelete() throws Exception {
		commitAfterCreation();
		InformationUnitListItem committedItem = CategoryUtil.findCategory("Inbox", false)
				.getChildren().get(0).getInformationUnit().get(0);
		Command deleteINFOUNIT = CommandFactory.DELETE_INFOUNIT(Collections
				.singletonList(committedItem), EditingUtil.getInstance()
				.getNavigationEditingDomain());
		EditingUtil.getInstance().getNavigationEditingDomain().getCommandStack().execute(
				deleteINFOUNIT);

		InformationUnitListItem deletedItem = ApplicationModelPool.getInstance()
				.getItemByIdLocalDeletedIncluded(committedItem.getId(), new NullProgressMonitor());
		assertNotNull(deletedItem);
		assertSame(deletedItem.getSynchronizationMetaData().getSyncState(),
				SynchronizationState.LOCAL_DELETED);

	}

	@Test
	public void remoteDelete() throws Exception {
		commitAfterCreation();
		Category category = CategoryUtil.findCategory("Inbox", false).getChildren().get(0);
		InformationUnitListItem committedItem = category.getInformationUnit().get(0);
		Command deleteINFOUNIT = CommandFactory.DELETE_INFOUNIT(Collections
				.singletonList(committedItem), EditingUtil.getInstance()
				.getNavigationEditingDomain());
		EditingUtil.getInstance().getNavigationEditingDomain().getCommandStack().execute(
				deleteINFOUNIT);

		InformationUnitListItem deletedItem = ApplicationModelPool.getInstance()
				.getItemByIdLocalDeletedIncluded(committedItem.getId(), new NullProgressMonitor());

		final ChangeSetManager manager = new ChangeSetManager();
		ChangeSet changeSet = manager.createChangeSet(category, new NullProgressMonitor());
		ChangeSetItem changeSetItem = changeSet.getChangeSetItems().get(0);
		DiffModel createDiffModel = manager.createDiffModel(changeSetItem, category);
		manager.prepareSyncActions(createDiffModel.getOwnedElements(), changeSetItem, category);
		ChangeSetExecutor executor = new ChangeSetExecutor();
		executor.setChangeSet(changeSet);
		executor.synchronize(createDiffModel.getOwnedElements(), changeSetItem,
				new NullProgressMonitor(), category);

		File file = new File(URL + File.separator + "INFO_" + deletedItem.getId() + File.separator
				+ deletedItem.getId() + ResourceUtil.DOT_FILE_EXTENSION);

		assertTrue("Remote data was not deleted", !file.exists());

	}

	@Test
	public void localCategoryAdd() throws Exception {
		Category subCat = InfoItemUtils.createNewCategory("sub", "Inbox");
		createInfoUnit(subCat);

	}

	private InformationUnit createInfoUnit(final Category category) throws Exception {
		InformationStructureEdit edit = InformationStructureEdit
				.newSession(MailActivator.INFO_TYPE_ID);
		InformationUnit newInformationUnit = edit.newInformationUnit();
		newInformationUnit.setLabel(InformationStructureReadTest.LABEL);
		InformationUnit cc1 = edit.createSubType(MailActivator.NODE_NAME_CC,
				InformationStructureReadTest.CC1);
		InformationUnit cc2 = edit.createSubType(MailActivator.NODE_NAME_CC,
				InformationStructureReadTest.CC2);
		edit.addDynamicNode(newInformationUnit, cc1, null);
		edit.addDynamicNode(newInformationUnit, cc2, null);
		edit.setValue(newInformationUnit, MailActivator.NODE_NAME_CONTENT_TYPE, ContentType.PLAIN
				.getKey());

		InputStream openStream = FileLocator.openStream(Platform.getBundle(Activator.PLUGIN_ID),
				new Path("dummyfiles/mailcontent/content.txt"), false);
		String content = StreamUtil.convertStreamToString(openStream);
		edit.setValue(newInformationUnit, MailActivator.NODE_NAME_CONTENT, content);

		StreamCloser.closeStreams(openStream);

		CompoundCommand createINFOTYPE = CommandFactory.CREATE_INFOTYPE(newInformationUnit,
				category, new NullProgressMonitor());
		EditingUtil.getInstance().getNavigationEditingDomain().getCommandStack().execute(
				createINFOTYPE);
		IFile file = (IFile) newInformationUnit.getAdapter(IFile.class);

		newInformationUnit = EditingUtil.getInstance().getObjectFromFile(file,
				InfomngmntPackage.Literals.INFORMATION_UNIT);

		InformationUnit createSubType = edit.createSubType(MailActivator.NODE_NAME_EMBEDDED, null);
		IFile createTempFile = ResourceUtil.createTempFile("png");
		InputStream openStream2 = FileLocator.openStream(Platform.getBundle(Activator.PLUGIN_ID),
				new Path("dummyfiles/mailembedded/rim_scaled_perspective.png"), false);
		createTempFile.setContents(openStream2, true, false, new NullProgressMonitor());
		edit.addDynamicNode(newInformationUnit, createSubType, null, Collections.singletonMap(
				MailActivator.NODE_NAME_EMBEDDED, createTempFile));
		EditingUtil.getInstance().saveObjectToResource(file, newInformationUnit);
		return newInformationUnit;
	}

	@After
	public void cleanUp() throws Exception {
		RepositoryCollection repositories = InfomngmntEditPlugin.getPlugin().getService(
				IRepositoryService.class).getRepositories();
		repositories.getRepositories().clear();
		EditingUtil.getInstance().saveObjectToResource(repositories);

		File file = new File(URL);
		if (file.exists()) {
			FileUtil.delete(file);
		}
		final Category category = CategoryUtil.findCategory("Inbox", false).getChildren().get(0);

		WorkspaceModifyDelegatingOperation operation = new WorkspaceModifyDelegatingOperation(
				new IRunnableWithProgress() {
					public void run(final IProgressMonitor monitor)
							throws InvocationTargetException, InterruptedException {
						Command deleteCategoryCommand = CommandFactory
								.DELETE_SYNCHRONIZABLE_CATEGORY(category, EditingUtil.getInstance()
										.getNavigationEditingDomain());
						EditingUtil.getInstance().getNavigationEditingDomain().getCommandStack()
								.execute(deleteCategoryCommand);
					}
				});
		try {
			new ProgressMonitorDialog(UIUtil.getDisplay().getActiveShell()).run(true, true,
					operation);
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(CategoryUtil.findCategory("Inbox", false).getChildren().size());

	}

	protected String getRepositoryTypeId() {
		return "org.remus.infomngmnt.connector.folder.folder";
	}

}
