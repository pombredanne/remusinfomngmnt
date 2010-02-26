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
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
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
import org.remus.infomngmnt.common.core.streams.StreamCloser;
import org.remus.infomngmnt.common.core.util.StringUtils;
import org.remus.infomngmnt.connector.twitter.infotype.TwitterUtil;
import org.remus.infomngmnt.core.extension.AbstractExtensionRepository;
import org.remus.infomngmnt.core.model.InformationStructureEdit;
import org.remus.infomngmnt.core.model.InformationStructureRead;
import org.remus.infomngmnt.core.remote.ILoginCallBack;
import org.remus.infomngmnt.core.remote.IRepository;
import org.remus.infomngmnt.core.remote.RemoteException;
import org.remus.infomngmnt.resources.util.ResourceUtil;
import org.remus.infomngmnt.util.InformationUtil;
import org.remus.infomngmnt.util.StatusCreator;

import twitter4j.DirectMessage;
import twitter4j.Paging;
import twitter4j.Query;
import twitter4j.Tweet;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class TwitterRepository extends AbstractExtensionRepository implements IRepository {

	private Twitter api;

	public static final String ID_FRIENDS = "friends"; //$NON-NLS-1$

	public static final String ID_REPLIES = "replies"; //$NON-NLS-1$

	public static final String ID_FAVORITES = "favorites"; //$NON-NLS-1$

	public static final String ID_FOLLOWERS = "followers"; //$NON-NLS-1$

	public static final String ID_FOLLOWING = "following"; //$NON-NLS-1$

	public static final String ID_DIRECT_MESSAGES = "direct"; //$NON-NLS-1$

	public static final String ID_MYDETAILS = "mydetails"; //$NON-NLS-1$

	public static final String ID_SEARCH_PREFIX = "search_"; //$NON-NLS-1$

	public static final String KEY_TWITTER_FEED = "twitterfeed"; //$NON-NLS-1$

	public static final String CONSUMER_KEY = "omhaenBOi32GmCWpIZCKQ"; //$NON-NLS-1$

	public static final String CONSUMER_SECRET = "T1ptIaPDBgnM1S2gtjZGZRSU9YhB4Er5fhJkFywsKm0"; //$NON-NLS-1$

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
			returnValue.add(buildFollowers());
			returnValue.add(buildFriends());
			returnValue.add(buildDetail(false, null));
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

	private RemoteObject buildDetail(final boolean all, final Long id) throws RemoteException {
		try {
			List<twitter4j.Status> friendsTimeline;
			if (all) {
				int statusesCount = getApi().verifyCredentials().getStatusesCount();
				friendsTimeline = getApi().getUserTimeline(new Paging(1, statusesCount));

			} else {
				if (id != null) {
					friendsTimeline = getApi().getUserTimeline(new Paging(id));
					if (friendsTimeline.size() == 0) {
						int statusesCount = getApi().verifyCredentials().getStatusesCount();
						friendsTimeline = getApi().getUserTimeline(new Paging(1, statusesCount));
					}
				} else {
					friendsTimeline = getApi().getUserTimeline(new Paging(1, 1));
				}
			}
			long timeStamp = 0;
			if (friendsTimeline.size() > 0) {
				timeStamp = friendsTimeline.get(0).getCreatedAt().getTime();
			}
			RemoteObject returnValue = InfomngmntFactory.eINSTANCE.createRemoteObject();
			returnValue.setHash(String.valueOf(timeStamp));
			returnValue.setId(ID_MYDETAILS);
			returnValue.setName("My twitter");
			returnValue.setUrl(StringUtils.join(getRepositoryUrl(), getCredentialProvider()
					.getUserName(), "/", ID_MYDETAILS));
			returnValue.setWrappedObject(friendsTimeline);
			return returnValue;
		} catch (TwitterException e) {
			throw new RemoteException(StatusCreator
					.newStatus("Error populating my details feed", e));
		}
	}

	private RemoteObject buildFollowers() throws RemoteException {
		try {
			List<User> followersIDs = getApi().getFollowersStatuses();
			RemoteObject returnValue = InfomngmntFactory.eINSTANCE.createRemoteObject();
			returnValue.setHash(String.valueOf(followersIDs.size()));
			returnValue.setId(ID_FOLLOWERS);
			returnValue.setUrl(StringUtils.join(getRepositoryUrl(), getCredentialProvider()
					.getUserName(), "/", ID_FOLLOWERS));
			returnValue.setName("Followers");
			returnValue.setWrappedObject(followersIDs);
			return returnValue;
		} catch (TwitterException e) {
			throw new RemoteException(StatusCreator.newStatus("Error getting followers", e));
		}
	}

	private RemoteObject buildFriends() throws RemoteException {
		try {
			List<User> followersIDs = getApi().getFriendsStatuses();
			RemoteObject returnValue = InfomngmntFactory.eINSTANCE.createRemoteObject();
			returnValue.setHash(String.valueOf(followersIDs.size()));
			returnValue.setId(ID_FOLLOWING);
			returnValue.setUrl(StringUtils.join(getRepositoryUrl(), getCredentialProvider()
					.getUserName(), "/", ID_FOLLOWING));
			returnValue.setName("Friends");
			returnValue.setWrappedObject(followersIDs);
			return returnValue;
		} catch (TwitterException e) {
			throw new RemoteException(StatusCreator.newStatus("Error getting friends", e));
		}
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
		if (informationUnitListItem.getType().equals(TwitterActivator.INFOTYPE_ID)) {
			Object adapter = informationUnitListItem.getAdapter(InformationUnit.class);
			if (adapter != null) {
				InformationUnit childByType = InformationUtil.getChildByType(
						(InformationUnit) adapter, TwitterActivator.MESSAGES_ID);
				EList<InformationUnit> childValues = childByType.getChildValues();
				if (childValues.size() > 0) {
					InformationUnit internalTwitterId = InformationUtil.getChildByType(childValues
							.get(0), TwitterActivator.MESSAGE_INTERNAL_ID);
					if (internalTwitterId != null && internalTwitterId.getLongValue() != 0) {
						return createUpdatedMessageFeed(informationUnitListItem, internalTwitterId
								.getLongValue(), (InformationUnit) adapter, false);
					}
				}
			}
			return createNewMessageFeed(informationUnitListItem, false);
		} else if (informationUnitListItem.getType().equals(TwitterActivator.INFOTYPE_USERS)) {
			Object adapter = informationUnitListItem.getAdapter(InformationUnit.class);
			Path path = new Path(informationUnitListItem.getSynchronizationMetaData().getUrl());
			boolean followers = path.lastSegment().equals(ID_FOLLOWERS);
			if (adapter != null) {
				return createUpdateUserInfoUnit((InformationUnit) adapter, followers);
			} else {
				return createNewUserInfoUnit(informationUnitListItem, followers);
			}
		} else if (informationUnitListItem.getType().equals(TwitterActivator.INFO_TYPE_DETAIL)) {
			Object adapter = informationUnitListItem.getAdapter(InformationUnit.class);
			InformationUnit returnValue = null;
			if (adapter != null) {
				InformationUnit childByType = InformationUtil.getChildByType(
						(InformationUnit) adapter, TwitterActivator.MESSAGES_ID);
				EList<InformationUnit> childValues = childByType.getChildValues();
				if (childValues.size() > 0) {
					InformationUnit internalTwitterId = InformationUtil.getChildByType(childValues
							.get(0), TwitterActivator.MESSAGE_INTERNAL_ID);
					if (internalTwitterId != null && internalTwitterId.getLongValue() != 0) {
						returnValue = createUpdatedMessageFeed(informationUnitListItem,
								internalTwitterId.getLongValue(), (InformationUnit) adapter, true);
					}
				}
			}
			if (returnValue == null) {
				returnValue = createNewMessageFeed(informationUnitListItem, true);
			}
			InformationStructureEdit edit = InformationStructureEdit
					.newSession(TwitterActivator.INFO_TYPE_DETAIL);
			User showUser;
			try {
				showUser = getApi().verifyCredentials();
			} catch (TwitterException e) {
				throw new RemoteException(StatusCreator.newStatus("Error getting user-details", e));
			}
			edit.setValue(returnValue, TwitterActivator.DETAIL_USERNAME_NODE, showUser.getName());
			edit.setValue(returnValue, TwitterActivator.DETAIL_DESCRIPTION_NODE, showUser
					.getDescription());
			edit.setValue(returnValue, TwitterActivator.DETAIL_MEMBERSINCE_NODE, showUser
					.getCreatedAt());
			edit.setValue(returnValue, TwitterActivator.DETAIL_WEBSITE_NODE, showUser.getURL()
					.toString());
			edit.setValue(returnValue, TwitterActivator.DETAIL_LOCATION_NODE, showUser
					.getLocation());
			edit.setValue(returnValue, TwitterActivator.DETAIL_USER_ID, showUser.getScreenName());
			edit.setValue(returnValue, TwitterActivator.DETAIL_STATUS_COUNT, showUser
					.getStatusesCount());
			return returnValue;

		}
		return null;
	}

	private InformationUnit createUpdateUserInfoUnit(final InformationUnit adapter,
			final boolean followers) throws RemoteException {
		List<User> followersIDs;
		try {
			if (followers) {
				followersIDs = getApi().getFollowersStatuses();
			} else {
				followersIDs = getApi().getFriendsStatuses();
			}
		} catch (TwitterException e1) {
			throw new RemoteException(StatusCreator.newStatus(
					"Error loading ids of friends or followers", e1));
		}

		InformationStructureRead read = InformationStructureRead.newSession(adapter);
		EList<InformationUnit> dynamicList = read.getDynamicList(TwitterActivator.USERS_NODE);
		List<String> onlineFollowerIds = new ArrayList<String>();
		for (User i : followersIDs) {
			boolean found = false;
			for (InformationUnit informationUnit : dynamicList) {
				InformationStructureRead subRead = InformationStructureRead.newSession(
						informationUnit, TwitterActivator.INFOTYPE_USERS);
				if (Integer.parseInt((String) subRead.getValueByNodeId(TwitterActivator.USER_NODE)) == i
						.getId()) {
					if ((Boolean) subRead.getValueByNodeId(TwitterActivator.EX_NODE)) {
						InformationStructureEdit edit = InformationStructureEdit
								.newSession(TwitterActivator.INFOTYPE_USERS);
						edit.setValue(informationUnit, TwitterActivator.EX_NODE, Boolean.FALSE);
					}
					found = true;
					break;
				}
			}
			if (!found) {
				InformationStructureEdit edit = InformationStructureEdit
						.newSession(TwitterActivator.INFOTYPE_USERS);

				InformationUnit userNode = edit.createSubType(TwitterActivator.USER_NODE, String
						.valueOf(i.getId()));
				edit.setValue(userNode, TwitterActivator.FOLLOWERS_NODE, i.getFollowersCount());
				edit.setValue(userNode, TwitterActivator.FRIENDS_NODE, i.getFriendsCount());
				edit.setValue(userNode, TwitterActivator.STATUSCOUNT_NODE, i.getStatusesCount());
				edit.setValue(userNode, TwitterActivator.USERNAME_NODE, i.getName());
				edit.setValue(userNode, TwitterActivator.NAME_NODE, i.getScreenName());
				edit.setValue(userNode, TwitterActivator.LOCATION_NODE, i.getLocation());
				edit.setValue(userNode, TwitterActivator.DATE_FOLLOW_UNFOLLOW_NODE, new Date());
				if (i.getURL() != null) {
					edit.setValue(userNode, TwitterActivator.URL_NODE, i.getURL().toString());
				}
				edit.setValue(userNode, TwitterActivator.EX_NODE, false);
				edit.setValue(userNode, TwitterActivator.CREATIONDATE_NODE, i.getCreatedAt());
				edit.addDynamicNode(adapter, userNode, null);
				TwitterActivator.getDefault().getImageCache().checkCache(i.getScreenName(),
						i.getProfileImageURL(), null);
			}
			onlineFollowerIds.add(String.valueOf(i));
		}
		// last one is to search for ex followers
		for (InformationUnit informationUnit : dynamicList) {
			InformationStructureRead subRead = InformationStructureRead.newSession(informationUnit,
					TwitterActivator.INFOTYPE_USERS);
			String userId = (String) subRead.getValueByNodeId(TwitterActivator.USER_NODE);
			if (!onlineFollowerIds.contains(userId)
					&& !(Boolean) subRead.getValueByNodeId(TwitterActivator.EX_NODE)) {
				InformationStructureEdit edit = InformationStructureEdit
						.newSession(TwitterActivator.INFOTYPE_USERS);
				edit.setValue(informationUnit, TwitterActivator.EX_NODE, Boolean.TRUE);
				edit.setValue(informationUnit, TwitterActivator.DATE_FOLLOW_UNFOLLOW_NODE,
						new Date());
			}
		}
		return adapter;
	}

	private InformationUnit createNewUserInfoUnit(
			final InformationUnitListItem informationUnitListItem, final boolean followers)
			throws RemoteException {
		try {
			List<User> followersIDs;
			if (followers) {
				followersIDs = getApi().getFollowersStatuses();
			} else {
				followersIDs = getApi().getFriendsStatuses();
			}
			InformationStructureEdit edit = InformationStructureEdit
					.newSession(TwitterActivator.INFOTYPE_USERS);
			InformationUnit newInformationUnit = edit.newInformationUnit();
			for (User user : followersIDs) {
				InformationUnit userNode = edit.createSubType(TwitterActivator.USER_NODE, String
						.valueOf(user.getId()));
				edit.setValue(userNode, TwitterActivator.FOLLOWERS_NODE, user.getFollowersCount());
				edit.setValue(userNode, TwitterActivator.FRIENDS_NODE, user.getFriendsCount());
				edit.setValue(userNode, TwitterActivator.STATUSCOUNT_NODE, user.getStatusesCount());
				edit.setValue(userNode, TwitterActivator.USERNAME_NODE, user.getName());
				edit.setValue(userNode, TwitterActivator.NAME_NODE, user.getScreenName());
				edit.setValue(userNode, TwitterActivator.LOCATION_NODE, user.getLocation());
				edit.setValue(userNode, TwitterActivator.DATE_FOLLOW_UNFOLLOW_NODE, new Date());
				if (user.getURL() != null) {
					edit.setValue(userNode, TwitterActivator.URL_NODE, user.getURL().toString());
				}
				edit.setValue(userNode, TwitterActivator.EX_NODE, false);
				edit.setValue(userNode, TwitterActivator.CREATIONDATE_NODE, user.getCreatedAt());
				edit.addDynamicNode(newInformationUnit, userNode, null);
				TwitterActivator.getDefault().getImageCache().checkCache(user.getScreenName(),
						user.getProfileImageURL(), null);
			}
			return newInformationUnit;
		} catch (TwitterException e) {
			throw new RemoteException(StatusCreator.newStatus("Error getting followers", e));
		}
	}

	@SuppressWarnings("unchecked")
	private InformationUnit createNewMessageFeed(
			final InformationUnitListItem informationUnitListItem, final boolean detailObject)
			throws RemoteException {

		InformationUnit createNewObject;
		if (detailObject) {
			createNewObject = InformationStructureEdit
					.newSession(TwitterActivator.INFO_TYPE_DETAIL).newInformationUnit();
		} else {
			createNewObject = InformationStructureEdit.newSession(TwitterActivator.INFOTYPE_ID)
					.newInformationUnit();
		}
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
			} else if (path.lastSegment().equals(ID_MYDETAILS)) {
				RemoteObject buildAllFriendsMessages = buildDetail(true, null);
				List<twitter4j.Status> wrappedObject = (List<twitter4j.Status>) buildAllFriendsMessages
						.getWrappedObject();
				for (twitter4j.Status status : wrappedObject) {
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
	private InformationUnit createUpdatedMessageFeed(
			final InformationUnitListItem informationUnitListItem, final Long id,
			final InformationUnit unit, final boolean detailed) throws RemoteException {
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
			} else if (path.lastSegment().equals(ID_MYDETAILS)) {
				RemoteObject buildAllFriendsMessages = buildDetail(false, id);
				List<twitter4j.Status> wrappedObject = (List<twitter4j.Status>) buildAllFriendsMessages
						.getWrappedObject();
				Collections.reverse(wrappedObject);
				for (twitter4j.Status status : wrappedObject) {
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
			} else if (ID_FOLLOWERS.equals(path.lastSegment())) {
				return buildFollowers();
			} else if (ID_FOLLOWING.equals(path.lastSegment())) {
				return buildFriends();
			} else if (ID_MYDETAILS.equals(path.lastSegment())) {
				return buildDetail(false, null);
			}

		} catch (MalformedURLException e) {
			throw new RemoteException(StatusCreator.newStatus("Error getting remote object", e));
		}

		return null;
	}

	@Override
	public boolean hasBinaryReferences() {
		return true;
	}

	@Override
	public IFile getBinaryReferences(final InformationUnitListItem syncObject,
			final InformationUnit localInfoFragment, final IProgressMonitor monitor)
			throws RemoteException {
		if (localInfoFragment.getType().equals(TwitterActivator.INFOTYPE_USERS)) {
			InputStream openStream = null;
			try {
				openStream = FileLocator.openStream(Platform.getBundle(TwitterActivator.PLUGIN_ID),
						new Path("reports/followerreport.rptdesign"), false);
				IFile createTempFile = ResourceUtil.createTempFile("rptdesign");
				createTempFile.setContents(openStream, true, false, monitor);

				return createTempFile;
			} catch (Exception e) {
				throw new RemoteException(StatusCreator
						.newStatus("Error creating twitter user report"));
			} finally {
				if (openStream != null) {
					StreamCloser.closeStreams(openStream);
				}
			}
		}
		if (localInfoFragment.getType().equals(TwitterActivator.INFO_TYPE_DETAIL)) {
			InputStream openStream = null;
			try {
				openStream = FileLocator.openStream(Platform.getBundle(TwitterActivator.PLUGIN_ID),
						new Path("reports/twitterstats.rptdesign"), false);
				IFile createTempFile = ResourceUtil.createTempFile("rptdesign");
				createTempFile.setContents(openStream, true, false, monitor);

				return createTempFile;
			} catch (Exception e) {
				throw new RemoteException(StatusCreator
						.newStatus("Error creating twitter user report"));
			} finally {
				if (openStream != null) {
					StreamCloser.closeStreams(openStream);
				}
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.remus.infomngmnt.core.remote.IRepository#getRepositoryUrl()
	 */
	@Override
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
		Path path = new Path(remoteObject.getUrl());
		if (path.segmentCount() > 1
				&& ID_SEARCH_PREFIX.equals(path.segment(path.segmentCount() - 2))) {
			return TwitterActivator.INFOTYPE_ID;
		} else if (ID_FRIENDS.equals(path.lastSegment())) {
			return TwitterActivator.INFOTYPE_ID;
		} else if (ID_REPLIES.equals(path.lastSegment())) {
			return TwitterActivator.INFOTYPE_ID;
		} else if (ID_DIRECT_MESSAGES.equals(path.lastSegment())) {
			return TwitterActivator.INFOTYPE_ID;
		} else if (ID_FOLLOWERS.equals(path.lastSegment())) {
			return TwitterActivator.INFOTYPE_USERS;
		} else if (ID_FOLLOWING.equals(path.lastSegment())) {
			return TwitterActivator.INFOTYPE_USERS;
		} else if (ID_MYDETAILS.equals(path.lastSegment())) {
			return TwitterActivator.INFO_TYPE_DETAIL;
		}
		return null;
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
	@Override
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
			this.api = new Twitter();
			this.api.setOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
			this.api.setOAuthAccessToken(getCredentialProvider().getUserName(),
					getCredentialProvider().getPassword());
			getCredentialProvider().addPropertyChangeListener(this.credentialsMovedListener);
		}
		return this.api;
	}
}
