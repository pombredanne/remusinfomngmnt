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

package org.remus.infomngmnt.connector.twitter;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.osgi.util.NLS;
import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.RemoteContainer;
import org.remus.infomngmnt.RemoteObject;
import org.remus.infomngmnt.RemoteRepository;
import org.remus.infomngmnt.SynchronizableObject;
import org.remus.infomngmnt.common.core.util.StringUtils;
import org.remus.infomngmnt.connector.twitter.infotype.TwitterUtil;
import org.remus.infomngmnt.core.extension.AbstractCreationFactory;
import org.remus.infomngmnt.core.extension.AbstractExtensionRepository;
import org.remus.infomngmnt.core.extension.InformationExtensionManager;
import org.remus.infomngmnt.core.model.InformationUtil;
import org.remus.infomngmnt.core.model.StatusCreator;
import org.remus.infomngmnt.core.remote.ILoginCallBack;
import org.remus.infomngmnt.core.remote.IRepository;
import org.remus.infomngmnt.core.remote.RemoteException;

import twitter4j.DirectMessage;
import twitter4j.Paging;
import twitter4j.Query;
import twitter4j.Tweet;
import twitter4j.Twitter;
import twitter4j.TwitterException;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class TwitterRepository extends AbstractExtensionRepository implements IRepository {

	private Twitter api;

	public static final String ID_FRIENDS = "friends"; //$NON-NLS-1$

	public static final String ID_REPLIES = "replies"; //$NON-NLS-1$

	public static final String ID_FAVORITES = "favorites"; //$NON-NLS-1$

	public static final String ID_DIRECT_MESSAGES = "direct"; //$NON-NLS-1$

	public static final String ID_SEARCH_PREFIX = "search_"; //$NON-NLS-1$

	public static final String KEY_TWITTER_FEED = "twitterfeed"; //$NON-NLS-1$

	/**
	 * 
	 */
	public TwitterRepository() {
		// TODO Auto-generated constructor stub
	}

	private final PropertyChangeListener credentialsMovedListener = new PropertyChangeListener() {
		public void propertyChange(final PropertyChangeEvent evt) {
			reset();
		}
	};

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.core.remote.IRepository#addToRepository(org.remus
	 * .infomngmnt.SynchronizableObject,
	 * org.eclipse.core.runtime.IProgressMonitor)
	 */
	public RemoteObject addToRepository(final SynchronizableObject item,
			final IProgressMonitor monitor) throws RemoteException {
		throw new RemoteException(StatusCreator
				.newStatus("Adding items is not supported by this repository"));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.core.remote.IRepository#commit(org.remus.infomngmnt
	 * .SynchronizableObject, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public RemoteObject commit(final SynchronizableObject item2commit,
			final IProgressMonitor monitor) throws RemoteException {
		throw new RemoteException(StatusCreator
				.newStatus("Committing items is not supported by this repository"));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.core.remote.IRepository#deleteFromRepository(org
	 * .remus.infomngmnt.SynchronizableObject,
	 * org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void deleteFromRepository(final SynchronizableObject item, final IProgressMonitor monitor)
			throws RemoteException {
		throw new RemoteException(StatusCreator
				.newStatus("Deleting items is not supported by this repository"));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.core.remote.IRepository#getChildren(org.eclipse.
	 * core.runtime.IProgressMonitor, org.remus.infomngmnt.RemoteContainer,
	 * boolean)
	 */
	public RemoteObject[] getChildren(final IProgressMonitor monitor,
			final RemoteContainer container, final boolean showOnlyContainers)
			throws RemoteException {
		if (showOnlyContainers) {
			return new RemoteObject[0];
		}
		if (container instanceof RemoteRepository) {
			List<RemoteObject> returnValue = new ArrayList<RemoteObject>();
			returnValue.add(buildAllFriendsMessages(false, null));
			returnValue.add(buildRepliesMessages(false, null));
			returnValue.add(buildDirectMessages(false, null));
			String[] split = org.apache.commons.lang.StringUtils.split(
					((RemoteRepository) container).getOptions().get(
							TwitterActivator.REPOSITORY_OPTIONS_SEARCH_KEY), "|");
			for (String string : split) {
				returnValue.add(buildSearchMessages(string, false, null));
			}
			return returnValue.toArray(new RemoteObject[returnValue.size()]);
		}
		return new RemoteObject[0];
	}

	private RemoteObject buildSearchMessages(final String string, final boolean all, final Long id)
			throws RemoteException {
		try {
			List<Tweet> search;
			Query query = new Query();
			query.setQuery(string);
			if (all) {
				search = getApi().search(query).getTweets();
			} else {
				if (id != null) {
					query.setSinceId(id);
				} else {
					query.setPage(1);
				}
				search = getApi().search(query).getTweets();
			}
			long timeStamp = 0;
			if (search.size() > 0) {
				timeStamp = search.get(0).getCreatedAt().getTime();
			}
			RemoteObject returnValue = InfomngmntFactory.eINSTANCE.createRemoteObject();
			returnValue.setRepositoryTypeObjectId(KEY_TWITTER_FEED);
			returnValue.setHash(String.valueOf(timeStamp));
			returnValue.setId(StringUtils.join(ID_SEARCH_PREFIX, string));
			returnValue.setUrl(StringUtils.join(getRepositoryUrl(), getCredentialProvider()
					.getUserName(), "/", ID_SEARCH_PREFIX, "/", string));
			returnValue.setName(NLS.bind("Search for {0}", string));
			returnValue.setWrappedObject(search);
			return returnValue;
		} catch (TwitterException e) {
			throw new RemoteException(StatusCreator.newStatus("Error populating search feed", e));
		}

	}

	private RemoteObject buildDirectMessages(final boolean all, final Long id)
			throws RemoteException {
		try {
			List<DirectMessage> directMessages;
			if (all) {
				directMessages = getApi().getDirectMessages();
			} else {
				if (id != null) {
					directMessages = getApi().getDirectMessages(new Paging(id));
				} else {
					directMessages = getApi().getDirectMessages(new Paging(1, 1));
				}
			}
			long timeStamp = 0;
			if (directMessages.size() > 0) {
				timeStamp = directMessages.get(0).getCreatedAt().getTime();
			}
			RemoteObject returnValue = InfomngmntFactory.eINSTANCE.createRemoteObject();
			returnValue.setRepositoryTypeObjectId(KEY_TWITTER_FEED);
			returnValue.setHash(String.valueOf(timeStamp));
			returnValue.setId(ID_FRIENDS);
			returnValue.setUrl(StringUtils.join(getRepositoryUrl(), getCredentialProvider()
					.getUserName(), "/", ID_DIRECT_MESSAGES));
			returnValue.setName("Direct Messages");
			returnValue.setWrappedObject(directMessages);
			return returnValue;
		} catch (TwitterException e) {
			throw new RemoteException(StatusCreator.newStatus("Error populating friends feed", e));
		}
	}

	private RemoteObject buildAllFriendsMessages(final boolean all, final Long id)
			throws RemoteException {
		try {
			List<twitter4j.Status> friendsTimeline;
			if (all) {
				friendsTimeline = getApi().getFriendsTimeline();
			} else {
				if (id != null) {
					friendsTimeline = getApi().getFriendsTimeline(new Paging(id));
					if (friendsTimeline.size() == 0) {
						friendsTimeline = getApi().getFriendsTimeline();
					}
				} else {
					friendsTimeline = getApi().getFriendsTimeline(new Paging(1, 1));
				}
			}
			long timeStamp = 0;
			if (friendsTimeline.size() > 0) {
				timeStamp = friendsTimeline.get(0).getCreatedAt().getTime();
			}
			RemoteObject returnValue = InfomngmntFactory.eINSTANCE.createRemoteObject();
			returnValue.setRepositoryTypeObjectId(KEY_TWITTER_FEED);
			returnValue.setHash(String.valueOf(timeStamp));
			returnValue.setId(ID_FRIENDS);
			returnValue.setName("All friends");
			returnValue.setUrl(StringUtils.join(getRepositoryUrl(), getCredentialProvider()
					.getUserName(), "/", ID_FRIENDS));
			returnValue.setWrappedObject(friendsTimeline);
			return returnValue;
		} catch (TwitterException e) {
			throw new RemoteException(StatusCreator.newStatus("Error populating friends feed", e));
		}
	}

	private RemoteObject buildRepliesMessages(final boolean all, final Long id)
			throws RemoteException {
		try {
			List<twitter4j.Status> mentionTimeLine;
			if (all) {
				mentionTimeLine = getApi().getMentions();
			} else {
				if (id != null) {
					mentionTimeLine = getApi().getMentions(new Paging(id));
				} else {
					mentionTimeLine = getApi().getMentions(new Paging(1, 1));
				}
			}
			long timeStamp = 0;
			if (mentionTimeLine.size() > 0) {
				timeStamp = mentionTimeLine.get(0).getCreatedAt().getTime();
			}
			RemoteObject returnValue = InfomngmntFactory.eINSTANCE.createRemoteObject();
			returnValue.setRepositoryTypeObjectId(KEY_TWITTER_FEED);
			returnValue.setHash(String.valueOf(timeStamp));
			returnValue.setId(ID_REPLIES);
			returnValue.setName("Replies");
			returnValue.setUrl(StringUtils.join(getRepositoryUrl(), getCredentialProvider()
					.getUserName(), "/", ID_REPLIES));
			returnValue.setWrappedObject(mentionTimeLine);
			return returnValue;
		} catch (TwitterException e) {
			throw new RemoteException(StatusCreator.newStatus("Error populating friends feed", e));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.core.remote.IRepository#getFullObject(org.remus.
	 * infomngmnt.InformationUnitListItem,
	 * org.eclipse.core.runtime.IProgressMonitor)
	 */
	public InformationUnit getFullObject(final InformationUnitListItem informationUnitListItem,
			final IProgressMonitor monitor) throws RemoteException {
		Object adapter = informationUnitListItem.getAdapter(InformationUnit.class);
		if (adapter != null) {
			InformationUnit childByType = InformationUtil.getChildByType((InformationUnit) adapter,
					TwitterActivator.MESSAGES_ID);
			EList<InformationUnit> childValues = childByType.getChildValues();
			if (childValues.size() > 0) {
				InformationUnit internalTwitterId = InformationUtil.getChildByType(childValues
						.get(0), TwitterActivator.MESSAGE_INTERNAL_ID);
				if (internalTwitterId != null && internalTwitterId.getLongValue() != 0) {
					return createNewObject(informationUnitListItem, internalTwitterId
							.getLongValue(), (InformationUnit) adapter);
				}
			}
		}
		return createNewObject(informationUnitListItem);
	}

	@SuppressWarnings("unchecked")
	private InformationUnit createNewObject(final InformationUnitListItem informationUnitListItem)
			throws RemoteException {
		AbstractCreationFactory creationFactory = InformationExtensionManager.getInstance()
				.getInfoTypeByType(TwitterActivator.INFOTYPE_ID).getCreationFactory();
		InformationUnit createNewObject = creationFactory.createNewObject();
		InformationUnit childByType = InformationUtil.getChildByType(createNewObject,
				TwitterActivator.MESSAGES_ID);
		try {
			URL url = new URL(informationUnitListItem.getSynchronizationMetaData().getUrl());
			Path path = new Path(url.getPath());
			if (path.segmentCount() > 1
					&& ID_SEARCH_PREFIX.equals(path.segment(path.segmentCount() - 2))) {
				RemoteObject buildAllFriendsMessages = buildSearchMessages(path.lastSegment(),
						true, null);
				List<twitter4j.Tweet> wrappedObject = (List<twitter4j.Tweet>) buildAllFriendsMessages
						.getWrappedObject();
				Collections.reverse(wrappedObject);
				for (twitter4j.Tweet status : wrappedObject) {
					try {
						childByType.getChildValues().add(0,
								TwitterUtil.buildMessage(status, getApi()));
					} catch (TwitterException e) {
						throw new RemoteException(StatusCreator.newStatus(
								"Error creating search messages", e));
					}
				}
			} else if (path.lastSegment().equals(ID_FRIENDS)) {
				RemoteObject buildAllFriendsMessages = buildAllFriendsMessages(true, null);
				List<twitter4j.Status> wrappedObject = (List<twitter4j.Status>) buildAllFriendsMessages
						.getWrappedObject();
				for (twitter4j.Status status : wrappedObject) {
					childByType.getChildValues().add(TwitterUtil.buildMessage(status));
				}
			} else if (path.lastSegment().equals(ID_REPLIES)) {
				RemoteObject buildAllFriendsMessages = buildRepliesMessages(true, null);
				List<twitter4j.Status> wrappedObject = (List<twitter4j.Status>) buildAllFriendsMessages
						.getWrappedObject();
				for (twitter4j.Status status : wrappedObject) {
					childByType.getChildValues().add(TwitterUtil.buildMessage(status));
				}
			} else if (path.lastSegment().equals(ID_DIRECT_MESSAGES)) {
				RemoteObject buildAllFriendsMessages = buildDirectMessages(true, null);
				List<DirectMessage> wrappedObject = (List<DirectMessage>) buildAllFriendsMessages
						.getWrappedObject();
				for (DirectMessage status : wrappedObject) {
					childByType.getChildValues().add(TwitterUtil.buildMessage(status));
				}
			}
		} catch (MalformedURLException e) {
			throw new RemoteException(StatusCreator.newStatus(
					"Error creating internal information types", e));
		}
		return createNewObject;
	}

	@SuppressWarnings( { "unchecked" })
	private InformationUnit createNewObject(final InformationUnitListItem informationUnitListItem,
			final Long id, final InformationUnit unit) throws RemoteException {
		try {
			URL url = new URL(informationUnitListItem.getSynchronizationMetaData().getUrl());
			InformationUnit childByType = InformationUtil.getChildByType(unit,
					TwitterActivator.MESSAGES_ID);
			Path path = new Path(url.getPath());
			if (path.segmentCount() > 1
					&& ID_SEARCH_PREFIX.equals(path.segment(path.segmentCount() - 2))) {
				RemoteObject buildAllFriendsMessages = buildSearchMessages(path.lastSegment(),
						false, id);
				List<twitter4j.Tweet> wrappedObject = (List<twitter4j.Tweet>) buildAllFriendsMessages
						.getWrappedObject();
				Collections.reverse(wrappedObject);
				for (twitter4j.Tweet status : wrappedObject) {
					try {
						childByType.getChildValues().add(0,
								TwitterUtil.buildMessage(status, getApi()));
					} catch (TwitterException e) {
						throw new RemoteException(StatusCreator.newStatus(
								"Error creating search messages", e));
					}
				}
			} else if (path.lastSegment().equals(ID_FRIENDS)) {
				RemoteObject buildAllFriendsMessages = buildAllFriendsMessages(false, id);
				List<twitter4j.Status> wrappedObject = (List<twitter4j.Status>) buildAllFriendsMessages
						.getWrappedObject();
				Collections.reverse(wrappedObject);
				for (twitter4j.Status status : wrappedObject) {
					childByType.getChildValues().add(0, TwitterUtil.buildMessage(status));
				}
			} else if (path.lastSegment().equals(ID_REPLIES)) {
				RemoteObject buildAllFriendsMessages = buildRepliesMessages(false, id);
				List<twitter4j.Status> wrappedObject = (List<twitter4j.Status>) buildAllFriendsMessages
						.getWrappedObject();
				Collections.reverse(wrappedObject);
				for (twitter4j.Status status : wrappedObject) {
					childByType.getChildValues().add(0, TwitterUtil.buildMessage(status));
				}
			} else if (path.lastSegment().equals(ID_DIRECT_MESSAGES)) {
				RemoteObject buildAllFriendsMessages = buildDirectMessages(false, id);
				List<DirectMessage> wrappedObject = (List<DirectMessage>) buildAllFriendsMessages
						.getWrappedObject();
				Collections.reverse(wrappedObject);
				for (DirectMessage status : wrappedObject) {
					childByType.getChildValues().add(0, TwitterUtil.buildMessage(status));
				}
			}
		} catch (MalformedURLException e) {
			throw new RemoteException(StatusCreator.newStatus(
					"Error creating internal information types", e));
		}
		return unit;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.remus.infomngmnt.core.remote.IRepository#
	 * getRemoteObjectBySynchronizableObject
	 * (org.remus.infomngmnt.SynchronizableObject,
	 * org.eclipse.core.runtime.IProgressMonitor)
	 */
	public RemoteObject getRemoteObjectBySynchronizableObject(final SynchronizableObject object,
			final IProgressMonitor monitor) throws RemoteException {
		try {
			URL url = new URL(object.getSynchronizationMetaData().getUrl());
			Path path = new Path(url.getPath());
			if (path.lastSegment() == null) {
				return getRepositoryById(object.getSynchronizationMetaData().getRepositoryId());
			}
			if (path.segmentCount() > 1
					&& ID_SEARCH_PREFIX.equals(path.segment(path.segmentCount() - 2))) {
				return buildSearchMessages(path.lastSegment(), false, null);
			} else if (ID_FRIENDS.equals(path.lastSegment())) {
				return buildAllFriendsMessages(false, null);
			} else if (ID_REPLIES.equals(path.lastSegment())) {
				return buildRepliesMessages(false, null);
			} else if (ID_DIRECT_MESSAGES.equals(path.lastSegment())) {
				return buildDirectMessages(false, null);
			}

		} catch (MalformedURLException e) {
			throw new RemoteException(StatusCreator.newStatus("Error getting remote object", e));
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.remus.infomngmnt.core.remote.IRepository#getRepositoryUrl()
	 */
	public String getRepositoryUrl() {
		return "http://twitter.com/";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.core.remote.IRepository#getTypeIdByObject(org.remus
	 * .infomngmnt.RemoteObject)
	 */
	public String getTypeIdByObject(final RemoteObject remoteObject) {
		return TwitterActivator.INFOTYPE_ID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.core.remote.IRepository#login(org.remus.infomngmnt
	 * .core.remote.ILoginCallBack, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void login(final ILoginCallBack callback, final IProgressMonitor monitor) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.remus.infomngmnt.core.remote.IRepository#reset()
	 */
	public void reset() {
		this.api = null;
		getCredentialProvider().removePropertyChangeListener(this.credentialsMovedListener);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.remus.infomngmnt.core.remote.IRepository#validate()
	 */
	public IStatus validate() {
		try {
			getApi().verifyCredentials();
		} catch (TwitterException e) {
			return StatusCreator.newStatus("Error validating credentials", e);
		}
		return Status.OK_STATUS;

	}

	public Twitter getApi() {
		if (this.api == null) {
			getCredentialProvider().setIdentifier(getLocalRepositoryId());
			this.api = new Twitter(getCredentialProvider().getUserName(), getCredentialProvider()
					.getPassword());
			this.api.setUserAgent("Remus");
			this.api.setSource("Remus");

			getCredentialProvider().addPropertyChangeListener(this.credentialsMovedListener);
		}
		return this.api;
	}

}
