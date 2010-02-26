package org.remus.infomngmnt.test.remote.connector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Collections;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.Test;

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.ChangeSet;
import org.remus.infomngmnt.RemoteContainer;
import org.remus.infomngmnt.RemoteObject;
import org.remus.infomngmnt.RemoteRepository;
import org.remus.infomngmnt.RepositoryCollection;
import org.remus.infomngmnt.core.remote.ICredentialProvider;
import org.remus.infomngmnt.core.services.IRepositoryService;
import org.remus.infomngmnt.core.sync.ChangeSetExecutor;
import org.remus.infomngmnt.core.sync.ChangeSetManager;
import org.remus.infomngmnt.provider.InfomngmntEditPlugin;
import org.remus.infomngmnt.test.remote.AbstractRemoteTest;
import org.remus.infomngmnt.util.CategoryUtil;

public class GoogleContactsTest extends AbstractRemoteTest {

	public static final String GOOGLE_CONTACTS_REP_ID = "org.remus.infomngmnt.connector.googlecontacts.repository"; //$NON-NLS-1$

	public static final String USER_NAME = System.getProperty("googlecontacts.user"); //$NON-NLS-1$

	public static final String USER_PASS = System.getProperty("googlecontacts.pass");; //$NON-NLS-1$

	@Test
	public void testBrowse() throws Exception {

		RepositoryCollection repositories = InfomngmntEditPlugin.getPlugin().getService(
				IRepositoryService.class).getRepositories();
		RemoteRepository remoteRepository = repositories.getRepositories().get(0);
		assertNotNull(remoteRepository);

		RemoteObject[] children = remoteRepository.getRepositoryImplementation().getChildren(
				new NullProgressMonitor(), remoteRepository, true);
		assertEquals(5, children.length);
	}

	@Test
	public void testCreateRepository() throws Exception {

		RepositoryCollection repositories = InfomngmntEditPlugin.getPlugin().getService(
				IRepositoryService.class).getRepositories();
		assertEquals(1, repositories.getRepositories().size());

	}

	@Test
	public void testCheckout() throws Exception {

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

	@Override
	protected void configureCredentials(final ICredentialProvider provider) {
		provider.setUserName(USER_NAME);
		provider.setPassword(USER_PASS);

	}

	@Override
	protected String getRepositoryTypeId() {
		return GOOGLE_CONTACTS_REP_ID;
	}
}
