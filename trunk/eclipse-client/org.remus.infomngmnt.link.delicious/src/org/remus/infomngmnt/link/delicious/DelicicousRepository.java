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

package org.remus.infomngmnt.link.delicious;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.emf.common.util.URI;

import del.icio.us.Delicious;
import del.icio.us.DeliciousConstants;
import del.icio.us.beans.Post;
import del.icio.us.beans.Tag;

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.RemoteContainer;
import org.remus.infomngmnt.RemoteObject;
import org.remus.infomngmnt.RemoteRepository;
import org.remus.infomngmnt.SynchronizableObject;
import org.remus.infomngmnt.SynchronizationMetadata;
import org.remus.infomngmnt.common.core.md5.MD5;
import org.remus.infomngmnt.core.extension.AbstractExtensionRepository;
import org.remus.infomngmnt.core.extension.IInfoType;
import org.remus.infomngmnt.core.extension.InformationExtensionManager;
import org.remus.infomngmnt.core.model.StatusCreator;
import org.remus.infomngmnt.core.remote.ILoginCallBack;
import org.remus.infomngmnt.link.LinkActivator;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class DelicicousRepository extends AbstractExtensionRepository {

	private Delicious api;

	public static final String KEY_TAG = "KEY_TAG"; //$NON-NLS-1$

	public static final String KEY_LINK = "KEY_LINK"; //$NON-NLS-1$

	MD5 md5 = new MD5();

	private final PropertyChangeListener credentialsMovedListener = new PropertyChangeListener() {
		public void propertyChange(final PropertyChangeEvent evt) {
			reset();
		}
	};

	final ISchedulingRule mutexRule = new ISchedulingRule() {
		public boolean isConflicting(final ISchedulingRule rule) {
			return rule == DelicicousRepository.this.mutexRule;
		}

		public boolean contains(final ISchedulingRule rule) {
			return rule == DelicicousRepository.this.mutexRule;
		}
	};

	public RemoteObject[] getChildren(final IProgressMonitor monitor,
			final RemoteContainer container, final boolean showOnlyContainer) {
		List<RemoteObject> returnValue = new LinkedList<RemoteObject>();
		if (container instanceof RemoteRepository) {
			IProgressMonitor sub = new SubProgressMonitor(monitor, IProgressMonitor.UNKNOWN);
			List<Tag> tags = getApi().getTags();
			for (Tag tag : tags) {
				RemoteContainer remoteContainer = buildRemoteContainer(tag, container
						.getRepositoryTypeId());
				returnValue.add(remoteContainer);
			}

		} else if (!showOnlyContainer) {
			List<Post> posts = getApi().getAllPosts(container.getName());
			for (Post post : posts) {
				RemoteObject remoteObject = buildRemoteObject(post, container.getRepositoryTypeId());
				returnValue.add(remoteObject);
			}
		}
		return returnValue.toArray(new RemoteObject[returnValue.size()]);
	}

	private RemoteObject buildRemoteObject(final Post post, final String repositoryTypeId) {
		String str = post.getHash() + post.getDescription() + post.getExtended();
		this.md5.Update(str);
		String md5Hash = this.md5.asHex();
		RemoteObject remoteObject = InfomngmntFactory.eINSTANCE.createRemoteObject();
		remoteObject.setId(post.getHash());
		remoteObject.setName(post.getDescription());
		remoteObject.setUrl(getRepositoryUrl() + post.getHash());
		remoteObject.setRepositoryTypeObjectId(KEY_LINK);
		remoteObject.setHash(md5Hash);
		remoteObject.setWrappedObject(post);
		remoteObject.setRepositoryTypeId(repositoryTypeId);
		return remoteObject;
	}

	private RemoteContainer buildRemoteContainer(final Tag tag, final String repositoryTypeId) {
		RemoteContainer remoteContainer = InfomngmntFactory.eINSTANCE.createRemoteContainer();
		remoteContainer.setId(tag.getTag());
		this.md5.Update(getRepositoryUrl() + tag.getTag());
		remoteContainer.setRepositoryTypeObjectId(KEY_TAG);
		remoteContainer.setName(tag.getTag());
		remoteContainer.setUrl(getRepositoryUrl() + tag.getTag());
		remoteContainer.setHash(this.md5.asHex());
		remoteContainer.setWrappedObject(tag);
		remoteContainer.setRepositoryTypeId(repositoryTypeId);
		return remoteContainer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.remus.infomngmnt.core.remote.IRepository#getRepositoryUrl()
	 */
	public String getRepositoryUrl() {
		return DeliciousConstants.API_ENDPOINT;
	}

	private Delicious getApi() {
		System.out.println("CONTACTING API");

		if (this.api == null) {
			getCredentialProvider().setIdentifier(getLocalRepositoryId());
			this.api = new Delicious(getCredentialProvider().getUserName(), getCredentialProvider()
					.getPassword());
			getCredentialProvider().addPropertyChangeListener(this.credentialsMovedListener);
		}
		return this.api;
	}

	public IStatus validate() {
		try {
			getApi().getRecentPosts();
			return Status.OK_STATUS;
		} catch (Exception e) {
			return StatusCreator.newStatus("Error connecting...", e);
		}
	}

	public void login(final ILoginCallBack callback, final IProgressMonitor monitor) {
		// TODO Auto-generated method stub

	}

	public void reset() {
		this.api = null;
		getCredentialProvider().removePropertyChangeListener(this.credentialsMovedListener);
	}

	public ISchedulingRule getRule() {
		return this.mutexRule;
	}

	public String getTypeIdByObject(final RemoteObject remoteObject) {
		return LinkActivator.LINK_INFO_ID;
	}

	public InformationUnit getPrefetchedInformationUnit(final RemoteObject remoteObject) {
		if (KEY_LINK.equals(remoteObject.getRepositoryTypeObjectId())) {
			IInfoType infoTypeByType = InformationExtensionManager.getInstance().getInfoTypeByType(
					LinkActivator.LINK_INFO_ID);
			InformationUnit newObject = infoTypeByType.getCreationFactory().createNewObject();
			Post post = (Post) remoteObject.getWrappedObject();
			newObject.setLabel(remoteObject.getName());
			newObject.setStringValue(post.getHref());
			newObject.setDescription(post.getExtended());
			newObject.setKeywords(post.getTag());
			newObject.setCreationDate(post.getTimeAsDate());
			return newObject;
		}
		return null;

	}

	public InformationUnit getFullObject(final InformationUnitListItem informationUnitListItem,
			final IProgressMonitor monitor) {
		/*
		 * We're getting all objects while browsing through the repository. So
		 * an additional request to the repository is not necessary. See
		 * #getPrefetchedChildren
		 */
		return null;
	}

	/**
	 * <p>
	 * Commits an item to the delicious repository. Committing a
	 * {@link Category} has no effect, because the categories are determinated
	 * by the keywords of the {@link InformationUnit}. This has an important
	 * side-effect. If you're editing the Keywords of the
	 * {@link InformationUnit} and commit the link on the next synchronize it
	 * could be that this link is not anymore in the {@link RemoteContainer} of
	 * the synced {@link Category}.
	 * </p>
	 * <p>
	 * {@inheritDoc}
	 * </p>
	 */
	private void _commit(final SynchronizableObject item2commit, final IProgressMonitor monitor) {
		if (item2commit instanceof InformationUnitListItem) {
			if (LinkActivator.LINK_INFO_ID
					.equals(((InformationUnitListItem) item2commit).getType())) {
				InformationUnit adapter = (InformationUnit) item2commit
						.getAdapter(InformationUnit.class);
				if (adapter != null) {
					/*
					 * This deletion+adding on committing an edited link is
					 * necessary due to the restrictions of the delicious API.
					 * It's not possible to replace an delicious entry which URL
					 * has changed. The hash value, which should be the
					 * identifier cannot be appended to a replace call.
					 */
					SynchronizationMetadata adapter2 = (SynchronizationMetadata) item2commit
							.getAdapter(SynchronizationMetadata.class);
					List<Post> postForUrl = getApi().getPostsForHash(adapter2.getHash());
					boolean replace = postForUrl.size() > 0;
					if (replace) {
						Post post = postForUrl.get(0);
						getApi().deletePost(post.getHref());
					}

					/*
					 * We will manually add before committing the keywords.
					 */
					String keywords = adapter.getKeywords();
					String categoryLabel = ((Category) item2commit.eContainer()).getLabel();
					boolean categoryLabelInKeywords = false;
					if (keywords != null) {
						String[] split = keywords.split(" ");
						for (String string : split) {
							if (string.equals(categoryLabel)) {
								categoryLabelInKeywords = true;
								break;
							}
						}
					}
					if (!categoryLabelInKeywords) {
						if (keywords != null) {
							keywords += " " + categoryLabel;
						} else {
							keywords = categoryLabel;
						}
					}
					boolean commit = getApi().addPost(adapter.getStringValue(), adapter.getLabel(),
							adapter.getDescription(), keywords, adapter.getCreationDate(), false,
							true);

				}
			}
		}
	}

	public String commit(final SynchronizableObject item2commit, final IProgressMonitor monitor) {
		RemoteObject addToRepository = addToRepository(item2commit, monitor);
		return addToRepository.getHash();
	}

	public RemoteObject addToRepository(final SynchronizableObject item,
			final IProgressMonitor monitor) {
		if (item instanceof InformationUnitListItem) {
			_commit(item, monitor);
			List postForUrl = getApi().getPostForURL(
					((InformationUnit) item.getAdapter(InformationUnit.class)).getStringValue());
			if (postForUrl.size() > 0) {
				Post newPost = (Post) postForUrl.get(0);
				RemoteObject returnValue = InfomngmntFactory.eINSTANCE.createRemoteObject();
				returnValue.setId(newPost.getHash());
				returnValue.setName(newPost.getDescription());
				returnValue.setUrl(getRepositoryUrl() + newPost.getHash());
				returnValue.setRepositoryTypeObjectId(KEY_LINK);
				returnValue.setHash(newPost.getHash());
				returnValue.setWrappedObject(newPost);
				returnValue
						.setRepositoryTypeId(item.getSynchronizationMetaData().getRepositoryId());
				return returnValue;
			}

		}
		return null;
	}

	public RemoteObject getRemoteObjectBySynchronizableObject(final SynchronizableObject object) {
		SynchronizationMetadata synchronizationMetaData = object.getSynchronizationMetaData();
		URI uri = URI.createURI(synchronizationMetaData.getUrl());
		if (synchronizationMetaData != null) {
			if (object instanceof Category) {
				List<Tag> tags = getApi().getTags();
				for (Tag tag : tags) {
					if (tag.getTag().equals(uri.lastSegment())) {
						return buildRemoteContainer(tag, synchronizationMetaData.getRepositoryId());
					}
				}
			} else if (object instanceof InformationUnitListItem) {
				List<Post> postsForHash = getApi().getPostsForHash(uri.lastSegment());
				if (postsForHash.size() > 0) {
					Post post = postsForHash.get(0);
					return buildRemoteObject(post, synchronizationMetaData.getRepositoryId());
				}
			}
		}
		return null;
	}

}
