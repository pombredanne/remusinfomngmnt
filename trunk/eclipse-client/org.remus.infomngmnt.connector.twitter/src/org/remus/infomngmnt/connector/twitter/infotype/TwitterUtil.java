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
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringEscapeUtils;

import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.common.core.util.StringUtils;
import org.remus.infomngmnt.connector.twitter.TwitterActivator;
import org.remus.infomngmnt.connector.twitter.TwitterRepository;
import org.remus.infomngmnt.core.model.InformationUtil;
import org.remus.infomngmnt.core.remote.IRepository;
import org.remus.infomngmnt.core.services.IRepositoryService;
import org.remus.infomngmnt.provider.InfomngmntEditPlugin;

import twitter4j.DirectMessage;
import twitter4j.Status;
import twitter4j.Twitter;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class TwitterUtil {

	public static final String REGEXP_USER = "@\\w+"; //$NON-NLS-1$
	public static final String KEYWORD_REGEXP = "#\\w+"; //$NON-NLS-1$
	public static final String URL_REGEXP = "((mailto\\:|(news|(ht|f)tp(s?))\\://){1}\\S+)"; //$NON-NLS-1$

	public static final String HREF_USER_PREFIX = "user"; //$NON-NLS-1$
	public static final String HREF_KEYWORD_PREFIX = "keyword"; //$NON-NLS-1$

	public static InformationUnit buildMessage(final Status status) {
		InformationUnit returnValue = InformationUtil.createNew(TwitterActivator.MESSAGE_TYPE);
		returnValue.getChildValues().add(
				InformationUtil
						.createNew(TwitterActivator.MESSAGE_DATE_TYPE, status.getCreatedAt()));
		returnValue.getChildValues().add(
				InformationUtil.createNew(TwitterActivator.MESSAGE_CONTENT_TYPE, status.getText()));
		returnValue.getChildValues().add(
				InformationUtil.createNew(TwitterActivator.MESSAGE_INTERNAL_ID, status.getId()));
		returnValue.getChildValues().add(
				InformationUtil.createNew(TwitterActivator.MESSAGE_SRC_TYPE, status.getSource()));
		returnValue.getChildValues().add(
				InformationUtil.createNew(TwitterActivator.REPLY_ID, status
						.getInReplyToScreenName()));
		returnValue.getChildValues().add(
				InformationUtil.createNew(TwitterActivator.MESSAGE_USER_TYPE, status.getUser()
						.getName()));
		returnValue.getChildValues().add(
				InformationUtil.createNew(TwitterActivator.MESSAGE_USER_ID_TYPE, status.getUser()
						.getScreenName()));

		TwitterActivator.getDefault().getImageCache().checkCache(status.getUser().getScreenName(),
				status.getUser().getProfileImageURL(), null);
		return returnValue;
	}

	public static InformationUnit buildMessage(final DirectMessage status) {
		InformationUnit returnValue = InformationUtil.createNew(TwitterActivator.MESSAGE_TYPE);
		returnValue.getChildValues().add(
				InformationUtil
						.createNew(TwitterActivator.MESSAGE_DATE_TYPE, status.getCreatedAt()));
		returnValue.getChildValues().add(
				InformationUtil.createNew(TwitterActivator.MESSAGE_CONTENT_TYPE, status.getText()));
		returnValue.getChildValues().add(
				InformationUtil.createNew(TwitterActivator.MESSAGE_INTERNAL_ID, status.getId()));
		returnValue.getChildValues().add(
				InformationUtil.createNew(TwitterActivator.MESSAGE_USER_TYPE, status.getSender()
						.getName()));
		returnValue.getChildValues().add(
				InformationUtil.createNew(TwitterActivator.MESSAGE_USER_ID_TYPE, status.getSender()
						.getScreenName()));
		TwitterActivator.getDefault().getImageCache().checkCache(
				status.getSender().getScreenName(), status.getSender().getProfileImageURL(), null);

		return returnValue;
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
		String escapeXml = StringEscapeUtils.escapeXml(content);
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
		IRepository repositoryImplementation = InfomngmntEditPlugin.getPlugin().getService(
				IRepositoryService.class).getRepositoryById(repositoryId)
				.getRepositoryImplementation();
		if (repositoryImplementation != null
				&& repositoryImplementation instanceof TwitterRepository) {
			return ((TwitterRepository) repositoryImplementation).getApi();
		}
		return null;
	}
}
