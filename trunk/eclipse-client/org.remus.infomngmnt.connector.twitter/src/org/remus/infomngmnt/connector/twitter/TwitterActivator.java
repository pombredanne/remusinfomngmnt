package org.remus.infomngmnt.connector.twitter;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import org.remus.infomngmnt.connector.twitter.ui.TwitterImageCache;

/**
 * The activator class controls the plug-in life cycle
 */
public class TwitterActivator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.remus.infomngmnt.connector.twitter";

	// The shared instance
	private static TwitterActivator plugin;

	public static final String REPOSITORY_OPTIONS_SEARCH_KEY = "REPOSITORY_OPTIONS_SEARCH_KEY"; //$NON-NLS-1$
	public static final String REPOSITORY_OPTIONS_DETAILED_KEY = "REPOSITORY_OPTIONS_SEARCH_KEY"; //$NON-NLS-1$
	public static final String REPOSITORY_OPTIONS_WATCH_OWN_PROFILE = "REPOSITORY_OPTIONS_WATCH_OWN_PROFILE";

	// Twitter Feed constancts
	public static final String INFOTYPE_ID = "TWITTER"; //$NON-NLS-1$

	public static final String MESSAGES_ID = "messages"; //$NON-NLS-1$

	public static final String MESSAGE_TYPE = "message"; //$NON-NLS-1$

	public static final String MESSAGE_DATE_TYPE = "date"; //$NON-NLS-1$

	public static final String MESSAGE_USER_TYPE = "user"; //$NON-NLS-1$

	public static final String MESSAGE_CONTENT_TYPE = "content"; //$NON-NLS-1$

	public static final String MESSAGE_SRC_TYPE = "source"; //$NON-NLS-1$

	public static final String MESSAGE_INTERNAL_ID = "twitterid"; //$NON-NLS-1$

	public static final String REPLY_ID = "reply"; //$NON-NLS-1$

	public static final String REPLY_STATUS_ID = "replystatusid"; //$NON-NLS-1$

	public static final String MESSAGE_USER_ID_TYPE = "userid";

	// Twitter user constants
	public static final String INFOTYPE_USERS = "TWITTER-USER"; //$NON-NLS-1$

	public static final String USERS_NODE = "users"; //$NON-NLS-1$

	public static final String USER_NODE = "user"; //$NON-NLS-1$

	public static final String USERNAME_NODE = "username"; //$NON-NLS-1$

	public static final String NAME_NODE = "name"; //$NON-NLS-1$

	public static final String URL_NODE = "url"; //$NON-NLS-1$

	public static final String CREATIONDATE_NODE = "creationDate"; //$NON-NLS-1$

	public static final String FOLLOWERS_NODE = "followerscount"; //$NON-NLS-1$

	public static final String FRIENDS_NODE = "friendscount"; //$NON-NLS-1$

	public static final String LOCATION_NODE = "location"; //$NON-NLS-1$

	public static final String STATUSCOUNT_NODE = "statuscount"; //$NON-NLS-1$

	public static final String EX_NODE = "ex"; //$NON-NLS-1$

	public static final String DATE_FOLLOW_UNFOLLOW_NODE = "datefolloworunfollow"; //$NON-NLS-1$

	// Twitter user detail constants

	public static final String INFO_TYPE_DETAIL = "TWITTER-DETAIL"; //$NON-NLS-1$

	public static final String DETAIL_USERNAME_NODE = "detailusername"; //$NON-NLS-1$
	public static final String DETAIL_MEMBERSINCE_NODE = "detailmembersince"; //$NON-NLS-1$
	public static final String DETAIL_WEBSITE_NODE = "detailwebsite"; //$NON-NLS-1$
	public static final String DETAIL_DESCRIPTION_NODE = "detaildescription"; //$NON-NLS-1$
	public static final String DETAIL_LOCATION_NODE = "detaillocation"; //$NON-NLS-1$
	public static final String DETAIL_USER_ID = "detailuserid"; //$NON-NLS-1$
	public static final String DETAIL_STATUS_COUNT = "detailstatuscount"; //$NON-NLS-1$

	private TwitterImageCache imageCache;

	/**
	 * The constructor
	 */
	public TwitterActivator() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
	 * )
	 */
	@Override
	public void start(final BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
	 * )
	 */
	@Override
	public void stop(final BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static TwitterActivator getDefault() {
		return plugin;
	}

	public TwitterImageCache getImageCache() {
		if (this.imageCache == null) {
			this.imageCache = new TwitterImageCache();
		}
		return this.imageCache;
	}

}
