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

package org.remus.infomngmnt.connector.twitter.infotype;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringEscapeUtils;

import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.RemoteRepository;
import org.remus.infomngmnt.SynchronizationState;
import org.remus.infomngmnt.common.core.util.StringUtils;
import org.remus.infomngmnt.connector.twitter.TwitterActivator;
import org.remus.infomngmnt.connector.twitter.TwitterRepository;
import org.remus.infomngmnt.core.model.InformationStructureEdit;
import org.remus.infomngmnt.core.remote.RemoteActivator;
import org.remus.infomngmnt.core.remote.services.IRepositoryService;
import org.remus.infomngmnt.core.remote.sync.SyncUtil;
import org.remus.infomngmnt.core.services.IEditingHandler;
import org.remus.infomngmnt.model.remote.IRepository;

import twitter4j.DirectMessage;
import twitter4j.Status;
import twitter4j.Tweet;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class TwitterUtil {

	public static final String REGEXP_USER = "@\\w+"; //$NON-NLS-1$
	public static final String KEYWORD_REGEXP = "#\\w+"; //$NON-NLS-1$
	public static final String URL_REGEXP = "((mailto\\:|(news|(ht|f)tp(s?))\\://){1}\\S+[^\\(\\)])"; //$NON-NLS-1$

	public static final String HREF_USER_PREFIX = "user"; //$NON-NLS-1$
	public static final String REPLY_USER_PREFIX = "reply"; //$NON-NLS-1$
	public static final String HREF_KEYWORD_PREFIX = "keyword"; //$NON-NLS-1$

	public static InformationUnit buildMessage(final Status status) {

		InformationStructureEdit edit = InformationStructureEdit
				.newSession(TwitterActivator.INFOTYPE_ID);

		InformationUnit message = edit.createSubType(TwitterActivator.MESSAGE_TYPE, null);

		edit.setValue(message, TwitterActivator.MESSAGE_DATE_TYPE, status.getCreatedAt());
		edit.setValue(message, TwitterActivator.MESSAGE_CONTENT_TYPE, status.getText());
		edit.setValue(message, TwitterActivator.MESSAGE_INTERNAL_ID, status.getId());
		edit.setValue(message, TwitterActivator.MESSAGE_SRC_TYPE, status.getSource());
		edit.setValue(message, TwitterActivator.REPLY_ID, status.getInReplyToScreenName());
		edit.setValue(message, TwitterActivator.REPLY_STATUS_ID, status.getInReplyToStatusId());
		edit.setValue(message, TwitterActivator.MESSAGE_USER_TYPE, status.getUser().getName());
		edit.setValue(message, TwitterActivator.MESSAGE_USER_ID_TYPE, status.getUser()
				.getScreenName());
		TwitterActivator.getDefault().getImageCache().checkCache(status.getUser().getScreenName(),
				status.getUser().getProfileImageURL(), null);

		return message;
	}

	public static InformationUnit buildMessage(final DirectMessage status) {
		InformationStructureEdit edit = InformationStructureEdit
				.newSession(TwitterActivator.INFOTYPE_ID);

		InformationUnit message = edit.createSubType(TwitterActivator.MESSAGE_TYPE, null);

		edit.setValue(message, TwitterActivator.MESSAGE_DATE_TYPE, status.getCreatedAt());
		edit.setValue(message, TwitterActivator.MESSAGE_CONTENT_TYPE, status.getText());
		edit.setValue(message, TwitterActivator.MESSAGE_INTERNAL_ID, status.getId());
		edit.setValue(message, TwitterActivator.MESSAGE_USER_TYPE, status.getSender().getName());
		edit.setValue(message, TwitterActivator.MESSAGE_USER_ID_TYPE, status.getSender()
				.getScreenName());
		TwitterActivator.getDefault().getImageCache().checkCache(
				status.getSender().getScreenName(), status.getSender().getProfileImageURL(), null);

		return message;
	}

	public static String parseUsers(final String content) {
		Pattern compile = Pattern.compile(REGEXP_USER);
		Matcher matcher = compile.matcher(content);
		List<String> matchings = new ArrayList<String>();
		List<String> posts = new ArrayList<String>();
		List<String> pres = new ArrayList<String>();
		while (matcher.find()) {
			String group = matcher.group();
			if (!matchings.contains(group)) {
				matchings.add(group);
				pres.add("<a href=\"" + HREF_USER_PREFIX + "." + group.substring(1) + "\">");
				posts.add("</a>");
			}
		}
		String surroundMatchingInString = StringUtils.surroundMatchingInString(content, matchings
				.toArray(new String[matchings.size()]), pres.toArray(new String[pres.size()]),
				posts.toArray(new String[posts.size()]));
		return surroundMatchingInString;
	}

	public static String parseKeywords(final String content) {
		Pattern compile = Pattern.compile(KEYWORD_REGEXP);
		Matcher matcher = compile.matcher(content);
		List<String> matchings = new ArrayList<String>();
		List<String> posts = new ArrayList<String>();
		List<String> pres = new ArrayList<String>();
		while (matcher.find()) {
			String group = matcher.group();
			if (!matchings.contains(group)) {
				matchings.add(group);
				pres.add("<a href=\"" + HREF_KEYWORD_PREFIX + "." + group.substring(1) + "\">");
				posts.add("</a>");
			}
		}
		String surroundMatchingInString = StringUtils.surroundMatchingInString(content, matchings
				.toArray(new String[matchings.size()]), pres.toArray(new String[pres.size()]),
				posts.toArray(new String[posts.size()]));
		return surroundMatchingInString;
	}

	public static String parseUrls(final String content) {
		Pattern compile = Pattern.compile(URL_REGEXP);
		Matcher matcher = compile.matcher(content);
		List<String> matchings = new ArrayList<String>();
		List<String> posts = new ArrayList<String>();
		List<String> pres = new ArrayList<String>();
		while (matcher.find()) {
			String group = matcher.group();
			if (!matchings.contains(group)) {
				matchings.add(group);
				pres.add("<a href=\"" + group + "\">");
				posts.add("</a>");
			}
		}
		String surroundMatchingInString = StringUtils.surroundMatchingInString(content, matchings
				.toArray(new String[matchings.size()]), pres.toArray(new String[pres.size()]),
				posts.toArray(new String[posts.size()]));
		return surroundMatchingInString;
	}

	public static String parseContent(final String content) {
		// String escapeXml = StringEscapeUtils.escapeXml(content);
		String escapeXml = content;
		StringWriter sw = new StringWriter();
		sw.append("<form><p vspace=\"false\">");
		escapeXml = parseUsers(escapeXml);
		escapeXml = parseKeywords(escapeXml);
		escapeXml = parseUrls(escapeXml);
		sw.append(escapeXml);
		sw.append("</p></form>");
		return sw.toString();
	}

	public static Twitter getTwitterApi(final String repositoryId) {
		IRepository repositoryImplementation = SyncUtil
				.getRepositoryImplemenationByRepositoryId(repositoryId);
		if (repositoryImplementation != null
				&& repositoryImplementation instanceof TwitterRepository) {
			return ((TwitterRepository) repositoryImplementation).getApi();
		}
		return null;
	}

	public static InformationUnit buildMessage(final Tweet status, final Twitter api)
			throws TwitterException {

		InformationStructureEdit edit = InformationStructureEdit
				.newSession(TwitterActivator.INFOTYPE_ID);

		InformationUnit message = edit.createSubType(TwitterActivator.MESSAGE_TYPE, null);

		edit.setValue(message, TwitterActivator.MESSAGE_DATE_TYPE, status.getCreatedAt());
		edit.setValue(message, TwitterActivator.MESSAGE_CONTENT_TYPE, status.getText());
		edit.setValue(message, TwitterActivator.MESSAGE_INTERNAL_ID, status.getId());
		edit.setValue(message, TwitterActivator.MESSAGE_SRC_TYPE, StringEscapeUtils
				.unescapeXml(status.getSource()));
		edit.setValue(message, TwitterActivator.REPLY_ID, status.getToUser());
		edit.setValue(message, TwitterActivator.REPLY_STATUS_ID, status.getToUserId());

		Status showStatus = api.showStatus(status.getId());
		User userDetail = showStatus.getUser();
		edit.setValue(message, TwitterActivator.REPLY_STATUS_ID, showStatus.getInReplyToStatusId());
		edit.setValue(message, TwitterActivator.MESSAGE_USER_TYPE, userDetail.getName());
		edit.setValue(message, TwitterActivator.MESSAGE_USER_ID_TYPE, userDetail.getScreenName());

		TwitterActivator.getDefault().getImageCache().checkCache(userDetail.getScreenName(),
				userDetail.getProfileImageURL(), null);
		return message;
	}

	public static boolean onlineActionsAvailable(final InformationUnitListItem adapter) {

		return adapter != null
				&& adapter.getSynchronizationMetaData() != null
				&& adapter.getSynchronizationMetaData().getSyncState() != SynchronizationState.NOT_ADDED
				&& adapter.getSynchronizationMetaData().getSyncState() != SynchronizationState.IGNORED;
	}

	public static boolean onlineActionsAvailable(final InformationUnit unit) {
		InformationUnitListItem adapter = (InformationUnitListItem) unit
				.getAdapter(InformationUnitListItem.class);
		return onlineActionsAvailable(adapter);
	}

	public static boolean canAdd2Repository(final String repositoryId, final String keyWord) {
		RemoteRepository repositoryById = RemoteActivator.getDefault().getServiceTracker()
				.getService(IRepositoryService.class).getRepositoryById(repositoryId);
		String string = repositoryById.getOptions().get(
				TwitterActivator.REPOSITORY_OPTIONS_SEARCH_KEY);
		List<String> asList = Arrays.asList(org.apache.commons.lang.StringUtils.split(string, "|"));
		return !asList.contains(keyWord);
	}

	public static void addKeyWordToRepository(final String repositoryId, final String keyWord) {
		RemoteRepository repositoryById = RemoteActivator.getDefault().getServiceTracker()
				.getService(IRepositoryService.class).getRepositoryById(repositoryId);
		String string = repositoryById.getOptions().get(
				TwitterActivator.REPOSITORY_OPTIONS_SEARCH_KEY);
		List<String> asList = new ArrayList<String>(Arrays
				.asList(org.apache.commons.lang.StringUtils.split(string, "|")));
		asList.add(keyWord);
		repositoryById.getOptions().put(TwitterActivator.REPOSITORY_OPTIONS_SEARCH_KEY,
				org.apache.commons.lang.StringUtils.join(asList, "|"));
		IEditingHandler service = RemoteActivator.getDefault().getServiceTracker().getService(
				IEditingHandler.class);
		service.saveObjectToResource(repositoryById);
		RemoteActivator.getDefault().getServiceTracker().ungetService(service);

	}
}
