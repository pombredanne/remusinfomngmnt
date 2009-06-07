/**
 * Copyright (c) 2004-2007, David A. Czarnecki
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 *      this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright notice,
 *      this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 * Neither the name of the "David A. Czarnecki" nor the names of
 * its contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND
 * CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO
 * EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED
 * AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package del.icio.us;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import del.icio.us.beans.Bundle;
import del.icio.us.beans.DeliciousDate;
import del.icio.us.beans.Hash;
import del.icio.us.beans.Post;
import del.icio.us.beans.Subscription;
import del.icio.us.beans.Tag;

/**
 * Delicious is a class for accessing the <a
 * href="http://del.icio.us/doc/api">del.icio.us API</a>.
 * 
 * @author David Czarnecki
 * @version $Id: Delicious.java,v 1.24 2007/01/19 00:14:43 czarneckid Exp $
 * @since 1.0
 */
public class Delicious {

	private final Log logger = LogFactory.getLog(Delicious.class);

	private final HttpClient httpClient;
	private DocumentBuilder documentBuilder;
	private String apiEndpoint;
	private int httpResult;
	private Object resultMetaInformation;

	/**
	 * By convention you have to wait between two requests to the delicious
	 * platform at least 1 second, otherwise you get throttled automatically. So
	 * we have to use this intervall between two requests.
	 */
	private static final long WAIT_INTERVALL = 1500;

	public static long lastApiCall;

	/**
	 * Create an object to interact with del.icio.us. API endpoint defaults to:
	 * http://del.icio.us/api/
	 * 
	 * @param username
	 *            del.icio.us username
	 * @param password
	 *            del.icio.us password
	 */
	public Delicious(final String username, final String password) {
		this(username, password, DeliciousConstants.API_ENDPOINT);
	}

	/**
	 * Create an object to interact with del.icio.us
	 * 
	 * @param username
	 *            del.icio.us username
	 * @param password
	 *            del.icio.us password
	 * @param apiEndpoint
	 *            del.icio.us API endpoint
	 */
	public Delicious(final String username, final String password, final String apiEndpoint) {
		this.apiEndpoint = apiEndpoint;
		this.httpClient = new HttpClient();
		HttpClientParams httpClientParams = new HttpClientParams();
		DefaultHttpMethodRetryHandler defaultHttpMethodRetryHandler = new DefaultHttpMethodRetryHandler(
				0, false);
		httpClientParams.setParameter(HttpMethodParams.USER_AGENT,
				DeliciousConstants.USER_AGENT_VALUE);
		httpClientParams
				.setParameter(HttpClientParams.RETRY_HANDLER, defaultHttpMethodRetryHandler);
		this.httpClient.setParams(httpClientParams);
		this.httpClient.getState().setCredentials(AuthScope.ANY,
				new UsernamePasswordCredentials(username, password));

		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		documentBuilderFactory.setValidating(false);
		documentBuilderFactory.setIgnoringElementContentWhitespace(true);
		documentBuilderFactory.setIgnoringComments(true);
		documentBuilderFactory.setCoalescing(true);
		documentBuilderFactory.setNamespaceAware(false);
		try {
			this.documentBuilder = documentBuilderFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			this.logger.error(e);
		}
	}

	/**
	 * Create an object to interact with del.icio.us
	 * 
	 * @param username
	 *            del.icio.us username
	 * @param password
	 *            del.icio.us password
	 * @param apiEndpoint
	 *            del.icio.us API endpoint
	 * @param proxyHost
	 *            Proxy host
	 * @param proxyPort
	 *            Proxy port
	 * @since 1.1
	 */
	public Delicious(final String username, final String password, final String apiEndpoint,
			final String proxyHost, final int proxyPort) {
		this(username, password, apiEndpoint);

		setProxyConfiguration(proxyHost, proxyPort);
	}

	/**
	 * Sets proxy configuration information. This method must be called before
	 * any calls to the API if you require proxy configuration.
	 * 
	 * @param proxyHost
	 *            Proxy host
	 * @param proxyPort
	 *            Proxy port
	 * @since 1.1
	 */
	public void setProxyConfiguration(final String proxyHost, final int proxyPort) {
		HostConfiguration hostConfiguration = new HostConfiguration();
		hostConfiguration.setProxy(proxyHost, proxyPort);

		this.httpClient.setHostConfiguration(hostConfiguration);
	}

	/**
	 * Sets proxy authentication information. This method must be called before
	 * any calls to the API if you require proxy authentication.
	 * 
	 * @param proxyUsername
	 *            Username to access proxy
	 * @param proxyPassword
	 *            Password to access proxy
	 * @since 1.8
	 */
	public void setProxyAuthenticationConfiguration(final String proxyUsername,
			final String proxyPassword) {
		this.httpClient.getState().setProxyCredentials(AuthScope.ANY,
				new UsernamePasswordCredentials(proxyUsername, proxyPassword));
	}

	/**
	 * Set a new API endpoint which may be different from the default
	 * {@link DeliciousConstants.API_ENDPOINT}
	 * 
	 * @param apiEndpoint
	 *            New API endpoint
	 * @since 2.0
	 */
	public void setApiEndpoint(final String apiEndpoint) {
		this.apiEndpoint = apiEndpoint;
	}

	/**
	 * Return the HTTP status code of the last operation
	 * 
	 * @return HTTP status code
	 */
	public int getHttpResult() {
		return this.httpResult;
	}

	/**
	 * Return list of {@link DeliciousDate} objects
	 * 
	 * @param tag
	 *            Filter by this tag (optional)
	 * @return List of {@link DeliciousDate} objects
	 */
	public List getDatesWithPost(final String tag) {
		clearResultMetaInformation();
		List dates = new ArrayList();
		StringBuffer result = new StringBuffer();

		GetMethod get = new GetMethod(this.apiEndpoint + DeliciousConstants.POSTS_DATES);
		get.setDoAuthentication(true);

		if (!DeliciousUtils.checkNullOrBlank(tag)) {
			NameValuePair tagParam = new NameValuePair(DeliciousConstants.TAG_PARAMETER, tag);
			get.setQueryString(new NameValuePair[] { tagParam });
		}

		try {
			this.httpResult = this.httpClient.executeMethod(get);
			checkNotAuthorized(this.httpResult);
			this.logger.debug("Result: " + this.httpResult);
			if (get.getResponseBodyAsStream() != null) {
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(get
						.getResponseBodyAsStream(), DeliciousUtils.UTF_8));
				String input;
				while ((input = bufferedReader.readLine()) != null) {
					result.append(input).append(DeliciousUtils.LINE_SEPARATOR);
				}

				get.releaseConnection();
				lastApiCall = System.currentTimeMillis();

				Document document = this.documentBuilder.parse(new InputSource(new StringReader(
						result.toString())));
				NodeList dateItems = document.getElementsByTagName(DeliciousConstants.DATE_TAG);
				if (dateItems != null && dateItems.getLength() > 0) {
					for (int i = 0; i < dateItems.getLength(); i++) {
						Node dateItem = dateItems.item(i);
						DeliciousDate date;
						String count = dateItem.getAttributes().getNamedItem(
								DeliciousConstants.COUNT_ATTRIBUTE).getNodeValue();
						String dateAttribute = dateItem.getAttributes().getNamedItem(
								DeliciousConstants.DATE_ATTRIBUTE).getNodeValue();
						date = new DeliciousDate(Integer.parseInt(count), dateAttribute);

						dates.add(date);
					}
				}
			}
		} catch (IOException e) {
			this.logger.error(e);
		} catch (SAXException e) {
			this.logger.error(e);
			throw new DeliciousException("Response parsing error", e);
		}

		return dates;
	}

	/**
	 * Return list of {@link DeliciousDate} objects
	 * 
	 * @return List of {@link DeliciousDate} objects
	 * @since 2.0
	 */
	public List getDatesWithPost() {
		return getDatesWithPost(null);
	}

	/**
	 * Return a list of {@link Tag} objects
	 * 
	 * @return List of {@link Tag} objects
	 */
	public List getTags() {
		clearResultMetaInformation();
		List tags = new ArrayList();
		StringBuffer result = new StringBuffer();

		GetMethod get = new GetMethod(this.apiEndpoint + DeliciousConstants.TAGS_GET);
		get.setDoAuthentication(true);

		try {
			this.httpResult = this.httpClient.executeMethod(get);
			checkNotAuthorized(this.httpResult);
			this.logger.debug("Result: " + this.httpResult);
			if (get.getResponseBodyAsStream() != null) {
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(get
						.getResponseBodyAsStream(), DeliciousUtils.UTF_8));
				String input;
				while ((input = bufferedReader.readLine()) != null) {
					result.append(input).append(DeliciousUtils.LINE_SEPARATOR);
				}

				get.releaseConnection();
				lastApiCall = System.currentTimeMillis();
				Document document = this.documentBuilder.parse(new InputSource(new StringReader(
						result.toString())));
				NodeList tagItems = document.getElementsByTagName(DeliciousConstants.TAG_TAG);
				if (tagItems != null && tagItems.getLength() > 0) {
					for (int i = 0; i < tagItems.getLength(); i++) {
						Node tagItem = tagItems.item(i);
						Tag tag;
						String count = tagItem.getAttributes().getNamedItem(
								DeliciousConstants.COUNT_ATTRIBUTE).getNodeValue();
						String tagAttribute = tagItem.getAttributes().getNamedItem(
								DeliciousConstants.TAG_ATTRIBUTE).getNodeValue();
						tag = new Tag(Integer.parseInt(count), tagAttribute);
						tags.add(tag);
					}
				}
			}
		} catch (IOException e) {
			this.logger.error(e);
		} catch (SAXException e) {
			this.logger.error(e);
			throw new DeliciousException("Response parsing error", e);
		}

		return tags;
	}

	/**
	 * Return a list of {@link Post} objects
	 * 
	 * @param filterTag
	 *            filter by this tag (optional)
	 * @param date
	 *            Filter by this date
	 * @param url
	 *            URL of post to retrieve (if present, only retrieves a single
	 *            {@link Post} object
	 * @return List of {@link Post} objects
	 * @since 1.8
	 */
	public List getPosts(final String filterTag, final Date date, final String url,
			final String hash) {
		clearResultMetaInformation();
		List posts = new ArrayList();
		StringBuffer result = new StringBuffer();

		GetMethod get = new GetMethod(this.apiEndpoint + DeliciousConstants.POSTS_GET);
		get.setDoAuthentication(true);

		List params = new ArrayList();
		if (!DeliciousUtils.checkNullOrBlank(filterTag)) {
			NameValuePair tag = new NameValuePair(DeliciousConstants.TAG_PARAMETER, filterTag);
			params.add(tag);
		}

		if (date != null) {
			NameValuePair dt = new NameValuePair(DeliciousConstants.DT_PARAMETER, DeliciousUtils
					.getDeliciousDate(date));
			params.add(dt);
		}

		if (!DeliciousUtils.checkNullOrBlank(url)) {
			NameValuePair urlParam = new NameValuePair(DeliciousConstants.URL_PARAMETER, url);
			params.add(urlParam);
		}
		if (!DeliciousUtils.checkNullOrBlank(hash)) {
			NameValuePair urlParam = new NameValuePair(DeliciousConstants.HASHES_PARAMETER, hash);
			params.add(urlParam);
		}

		if (params.size() > 0) {
			get.setQueryString((NameValuePair[]) params.toArray(new NameValuePair[params.size()]));
		}

		try {
			this.httpResult = this.httpClient.executeMethod(get);
			checkNotAuthorized(this.httpResult);
			this.logger.debug("Result: " + this.httpResult);
			if (get.getResponseBodyAsStream() != null) {
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(get
						.getResponseBodyAsStream(), DeliciousUtils.UTF_8));
				String input;
				while ((input = bufferedReader.readLine()) != null) {
					result.append(input).append(DeliciousUtils.LINE_SEPARATOR);
				}

				get.releaseConnection();
				lastApiCall = System.currentTimeMillis();
				Document document = this.documentBuilder.parse(new InputSource(new StringReader(
						result.toString())));
				NodeList postItems = document.getElementsByTagName(DeliciousConstants.POST_TAG);
				if (postItems != null && postItems.getLength() > 0) {
					for (int i = 0; i < postItems.getLength(); i++) {
						Node postItem = postItems.item(i);
						Post post;
						String href = postItem.getAttributes().getNamedItem(
								DeliciousConstants.HREF_ATTRIBUTE).getNodeValue();
						String description = postItem.getAttributes().getNamedItem(
								DeliciousConstants.DESCRIPTION_ATTRIBUTE).getNodeValue();
						String hash2 = postItem.getAttributes().getNamedItem(
								DeliciousConstants.HASH_ATTRIBUTE).getNodeValue();
						String tag = postItem.getAttributes().getNamedItem(
								DeliciousConstants.TAG_ATTRIBUTE).getNodeValue();
						String time = postItem.getAttributes().getNamedItem(
								DeliciousConstants.TIME_ATTRIBUTE).getNodeValue();
						String extended = null;
						if (postItem.getAttributes().getNamedItem(
								DeliciousConstants.EXTENDED_ATTRIBUTE) != null) {
							extended = postItem.getAttributes().getNamedItem(
									DeliciousConstants.EXTENDED_ATTRIBUTE).getNodeValue();
						}
						boolean shared = true;
						if (postItem.getAttributes().getNamedItem(
								DeliciousConstants.SHARED_PARAMETER) != null) {
							shared = Boolean.valueOf(
									postItem.getAttributes().getNamedItem(
											DeliciousConstants.SHARED_PARAMETER).getNodeValue())
									.booleanValue();
						}
						post = new Post(href, description, extended, hash2, tag, time, shared);

						posts.add(post);
					}
				}
			}
		} catch (IOException e) {
			this.logger.error(e);
		} catch (SAXException e) {
			this.logger.error(e);
			throw new DeliciousException("Response parsing error", e);
		}

		return posts;
	}

	/**
	 * Return a list of {@link Post} objects
	 * 
	 * @param filterTag
	 *            filter by this tag (optional)
	 * @param date
	 *            Filter by this date
	 * @return List of {@link Post} objects
	 */
	public List getPostsForDate(final String filterTag, final Date date) {
		return getPosts(filterTag, date, null, null);
	}

	/**
	 * Return a list of {@link Post} objects
	 * 
	 * @return List of {@link Post} objects
	 * @since 2.0
	 */
	public List getPosts() {
		return getPosts(null, null, null, null);
	}

	/**
	 * Return a list of {@link Post} objects for a given URL
	 * 
	 * @param url
	 *            Filter by this URL
	 * @return List of {@link Post} objects for a given URL
	 * @since 1.8
	 */
	public List getPostForURL(final String url) {
		return getPosts(null, null, url, null);
	}

	/**
	 * Return a list of {@link Post} objects for a given tag
	 * 
	 * @param tag
	 *            Filter by this tag
	 * @return List of {@link Post} objects for a given tag
	 * @since 1.8
	 */
	public List getPostsForTag(final String tag) {
		return getPosts(tag, null, null, null);
	}

	/**
	 * Return a list of {@link Post} objects for a given tag
	 * 
	 * @param tag
	 *            Filter by this tag
	 * @return List of {@link Post} objects for a given tag
	 * @since 1.8
	 */
	public List getPostsForHash(final String hash) {
		return getPosts(null, null, null, hash);
	}

	/**
	 * Return a list of {@link Post} objects for a given set of tags. Calls
	 * {@link #getPostsForTag(String)} for each tag.
	 * 
	 * @param tags
	 *            Filter by these tags
	 * @return List of {@link Post} objects for the given set of tags
	 * @since 1.8
	 */
	public List getPostsForTags(final String[] tags) {
		if (tags == null) {
			return new ArrayList();
		}

		List postsForTags = new ArrayList(tags.length);
		List posts;
		for (int i = 0; i < tags.length; i++) {
			String tag = tags[i];
			posts = getPostsForTag(tag);
			for (int j = 0; j < posts.size(); j++) {
				Post post = (Post) posts.get(j);
				postsForTags.add(post);
			}
		}

		return postsForTags;
	}

	/**
	 * Return a list of {@link Post} objects
	 * 
	 * @param filterTag
	 *            filter by this tag (optional)
	 * @param count
	 *            Must be &gt; 0 and &lt; 100
	 * @return List of {@link Post} objects
	 */
	public List getRecentPosts(final String filterTag, int count) {
		clearResultMetaInformation();
		List posts = new ArrayList();
		StringBuffer result = new StringBuffer();

		if (count <= 0) {
			count = DeliciousConstants.DEFAULT_POST_COUNT;
		}

		if (count > DeliciousConstants.MAXIMUM_POST_COUNT) {
			count = DeliciousConstants.MAXIMUM_POST_COUNT;
		}

		GetMethod get = new GetMethod(this.apiEndpoint + DeliciousConstants.POSTS_RECENT);
		get.setDoAuthentication(true);

		List params = new ArrayList();

		NameValuePair countParam = new NameValuePair(DeliciousConstants.COUNT_PARAMETER, Integer
				.toString(count));
		params.add(countParam);

		if (!DeliciousUtils.checkNullOrBlank(filterTag)) {
			NameValuePair tagParam = new NameValuePair(DeliciousConstants.TAG_PARAMETER, filterTag);
			params.add(tagParam);
		}

		if (params.size() > 0) {
			get.setQueryString((NameValuePair[]) params.toArray(new NameValuePair[params.size()]));
		}

		try {
			this.httpResult = this.httpClient.executeMethod(get);
			checkNotAuthorized(this.httpResult);
			this.logger.debug("Result: " + this.httpResult);
			if (get.getResponseBodyAsStream() != null) {
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(get
						.getResponseBodyAsStream(), DeliciousUtils.UTF_8));
				String input;
				while ((input = bufferedReader.readLine()) != null) {
					result.append(input).append(DeliciousUtils.LINE_SEPARATOR);
				}

				get.releaseConnection();
				lastApiCall = System.currentTimeMillis();
				Document document = this.documentBuilder.parse(new InputSource(new StringReader(
						result.toString())));
				NodeList postItems = document.getElementsByTagName(DeliciousConstants.POST_TAG);
				if (postItems != null && postItems.getLength() > 0) {
					for (int i = 0; i < postItems.getLength(); i++) {
						Node postItem = postItems.item(i);
						Post post;
						String href = postItem.getAttributes().getNamedItem(
								DeliciousConstants.HREF_ATTRIBUTE).getNodeValue();
						String description = postItem.getAttributes().getNamedItem(
								DeliciousConstants.DESCRIPTION_ATTRIBUTE).getNodeValue();
						String hash = postItem.getAttributes().getNamedItem(
								DeliciousConstants.HASH_ATTRIBUTE).getNodeValue();
						String tag = postItem.getAttributes().getNamedItem(
								DeliciousConstants.TAG_ATTRIBUTE).getNodeValue();
						String time = postItem.getAttributes().getNamedItem(
								DeliciousConstants.TIME_ATTRIBUTE).getNodeValue();
						String extended = null;
						if (postItem.getAttributes().getNamedItem(
								DeliciousConstants.EXTENDED_ATTRIBUTE) != null) {
							extended = postItem.getAttributes().getNamedItem(
									DeliciousConstants.EXTENDED_ATTRIBUTE).getNodeValue();
						}
						boolean shared = true;
						if (postItem.getAttributes().getNamedItem(
								DeliciousConstants.SHARED_PARAMETER) != null) {
							shared = Boolean.valueOf(
									postItem.getAttributes().getNamedItem(
											DeliciousConstants.SHARED_PARAMETER).getNodeValue())
									.booleanValue();
						}
						post = new Post(href, description, extended, hash, tag, time, shared);

						posts.add(post);
					}
				}
			}
		} catch (IOException e) {
			this.logger.error(e);
		} catch (SAXException e) {
			this.logger.error(e);
			throw new DeliciousException("Response parsing error", e);
		}

		return posts;
	}

	/**
	 * Return a list of {@link Post} objects
	 * 
	 * @param filterTag
	 *            filter by this tag (optional)
	 * @return List of {@link Post} objects
	 */
	public List getRecentPosts(final String filterTag) {
		return getRecentPosts(filterTag, DeliciousConstants.DEFAULT_POST_COUNT);
	}

	/**
	 * Return a list of recent {@link Post} objects; uses default number of
	 * items to retrieve {@link DeliciousConstants#DEFAULT_POST_COUNT}
	 * 
	 * @return List of {@link Post} objects
	 * @since 2.0
	 */
	public List getRecentPosts() {
		return getRecentPosts(null, DeliciousConstants.DEFAULT_POST_COUNT);
	}

	/**
	 * Return a list of all {@link Post} objects. This method populates the
	 * result meta information field with a {@link Date} object containing the
	 * last update time.
	 * 
	 * @param filterTag
	 *            Filter by this tag
	 * @return List of all {@link Post} objects
	 */
	public List getAllPosts(final String filterTag) {
		clearResultMetaInformation();
		List posts = new ArrayList();
		StringBuffer result = new StringBuffer();

		GetMethod get = new GetMethod(this.apiEndpoint + DeliciousConstants.POSTS_ALL);
		if (!DeliciousUtils.checkNullOrBlank(filterTag)) {
			get.setQueryString(new NameValuePair[] { new NameValuePair(
					DeliciousConstants.TAG_PARAMETER, filterTag) });
		}

		get.setDoAuthentication(true);

		try {
			this.httpResult = this.httpClient.executeMethod(get);
			checkNotAuthorized(this.httpResult);
			this.logger.debug("Result: " + this.httpResult);
			if (get.getResponseBodyAsStream() != null) {
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(get
						.getResponseBodyAsStream(), DeliciousUtils.UTF_8));
				String input;
				while ((input = bufferedReader.readLine()) != null) {
					result.append(input).append(DeliciousUtils.LINE_SEPARATOR);
				}

				get.releaseConnection();
				lastApiCall = System.currentTimeMillis();
				Document document = this.documentBuilder.parse(new InputSource(new StringReader(
						result.toString())));

				// Parse the <posts .../> item for meta information (update
				// attribute)
				NodeList postsTag = document.getElementsByTagName(DeliciousConstants.POSTS_TAG);
				if (postsTag != null && postsTag.getLength() > 0) {
					Node postsItem = postsTag.item(0);
					String updateTime = postsItem.getAttributes().getNamedItem(
							DeliciousConstants.UPDATE_ATTRIBUTE).getNodeValue();

					this.resultMetaInformation = DeliciousUtils.getDateFromUTCString(updateTime);
				}

				// Parse the <post .../> items
				NodeList postItems = document.getElementsByTagName(DeliciousConstants.POST_TAG);
				if (postItems != null && postItems.getLength() > 0) {
					for (int i = 0; i < postItems.getLength(); i++) {
						Node postItem = postItems.item(i);
						Post post;
						String href = postItem.getAttributes().getNamedItem(
								DeliciousConstants.HREF_ATTRIBUTE).getNodeValue();
						String description = postItem.getAttributes().getNamedItem(
								DeliciousConstants.DESCRIPTION_ATTRIBUTE).getNodeValue();
						String hash = postItem.getAttributes().getNamedItem(
								DeliciousConstants.HASH_ATTRIBUTE).getNodeValue();
						String tag = postItem.getAttributes().getNamedItem(
								DeliciousConstants.TAG_ATTRIBUTE).getNodeValue();
						String time = postItem.getAttributes().getNamedItem(
								DeliciousConstants.TIME_ATTRIBUTE).getNodeValue();
						String extended = null;
						if (postItem.getAttributes().getNamedItem(
								DeliciousConstants.EXTENDED_ATTRIBUTE) != null) {
							extended = postItem.getAttributes().getNamedItem(
									DeliciousConstants.EXTENDED_ATTRIBUTE).getNodeValue();
						}
						boolean shared = true;
						if (postItem.getAttributes().getNamedItem(
								DeliciousConstants.SHARED_PARAMETER) != null) {
							shared = Boolean.valueOf(
									postItem.getAttributes().getNamedItem(
											DeliciousConstants.SHARED_PARAMETER).getNodeValue())
									.booleanValue();
						}
						post = new Post(href, description, extended, hash, tag, time, shared);

						posts.add(post);
					}
				}
			}
		} catch (IOException e) {
			this.logger.error(e);
		} catch (SAXException e) {
			this.logger.error(e);
			throw new DeliciousException("Response parsing error", e);
		}

		return posts;
	}

	/**
	 * Return a list of all {@link Post} objects. This method populates the
	 * result meta information field with a {@link Date} object containing the
	 * last update time.
	 * 
	 * @return List of all {@link Post} objects
	 */
	public List getAllPosts() {
		return getAllPosts(null);
	}

	public List getAllHahes() {
		clearResultMetaInformation();
		List hashes = new ArrayList();
		StringBuffer result = new StringBuffer();

		GetMethod get = new GetMethod(this.apiEndpoint + DeliciousConstants.POSTS_ALL);

		get.setQueryString(new NameValuePair[] { new NameValuePair(
				DeliciousConstants.HASH_PARAMETER, "") });

		get.setDoAuthentication(true);

		try {
			this.httpResult = this.httpClient.executeMethod(get);
			checkNotAuthorized(this.httpResult);
			this.logger.debug("Result: " + this.httpResult);
			if (get.getResponseBodyAsStream() != null) {
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(get
						.getResponseBodyAsStream(), DeliciousUtils.UTF_8));
				String input;
				while ((input = bufferedReader.readLine()) != null) {
					result.append(input).append(DeliciousUtils.LINE_SEPARATOR);
				}

				get.releaseConnection();
				lastApiCall = System.currentTimeMillis();
				Document document = this.documentBuilder.parse(new InputSource(new StringReader(
						result.toString())));

				// Parse the <posts .../> item for meta information (update
				// attribute)
				NodeList postsTag = document.getElementsByTagName(DeliciousConstants.POSTS_TAG);

				// Parse the <post .../> items
				NodeList postItems = document.getElementsByTagName(DeliciousConstants.POST_TAG);
				if (postItems != null && postItems.getLength() > 0) {
					for (int i = 0; i < postItems.getLength(); i++) {
						Node postItem = postItems.item(i);
						Hash hash;
						String hashValue = postItem.getAttributes().getNamedItem(
								DeliciousConstants.META_ATTRIBUTE).getNodeValue();
						String urlHash = postItem.getAttributes().getNamedItem(
								DeliciousConstants.URL_PARAMETER).getNodeValue();
						hash = new Hash(hashValue, urlHash);
						hashes.add(hash);
					}
				}
			}
		} catch (IOException e) {
			this.logger.error(e);
		} catch (SAXException e) {
			this.logger.error(e);
			throw new DeliciousException("Response parsing error", e);
		}

		return hashes;
	}

	/**
	 * Make a post to del.icio.us. If either the <code>url</code> or
	 * <code>description</code> parameters are null or blank, this method
	 * immediately returns <code>false</code>.
	 * 
	 * @param url
	 *            URL for post
	 * @param description
	 *            Description for post
	 * @param extended
	 *            Extended for post
	 * @param tags
	 *            Space-delimited list of tags
	 * @param date
	 *            Date for post
	 * @param replace
	 *            <code>true</code> if call should replace post
	 * @param shared
	 *            Make the item private
	 * @return <code>true</code> if posted, <code>false</code> otherwise
	 * @since 1.8
	 */
	public boolean addPost(final String url, final String description, final String extended,
			final String tags, final Date date, final boolean replace, final boolean shared) {

		clearResultMetaInformation();
		boolean addPostResult = false;
		StringBuffer result = new StringBuffer();

		PostMethod post = new PostMethod(this.apiEndpoint + DeliciousConstants.POSTS_ADD);
		post.setDoAuthentication(true);

		if (DeliciousUtils.checkNullOrBlank(url)) {
			return false;
		}

		if (DeliciousUtils.checkNullOrBlank(description)) {
			return false;
		}

		NameValuePair urlParam = new NameValuePair(DeliciousConstants.URL_PARAMETER, url);
		post.addParameter(urlParam);

		NameValuePair descriptionParam = new NameValuePair(
				DeliciousConstants.DESCRIPTION_PARAMETER, description);
		post.addParameter(descriptionParam);

		if (!DeliciousUtils.checkNullOrBlank(extended)) {
			NameValuePair extendedParam = new NameValuePair(DeliciousConstants.EXTENDED_PARAMETER,
					extended);
			post.addParameter(extendedParam);
		}

		if (!DeliciousUtils.checkNullOrBlank(tags)) {
			NameValuePair tagsParam = new NameValuePair(DeliciousConstants.TAGS_PARAMETER, tags);
			post.addParameter(tagsParam);
		}

		if (date != null) {
			NameValuePair dtParam = new NameValuePair(DeliciousConstants.DT_PARAMETER,
					DeliciousUtils.getUTCDate(date));
			post.addParameter(dtParam);
		}

		NameValuePair replaceParam = new NameValuePair();
		replaceParam.setName(DeliciousConstants.REPLACE_PARAMETER);
		replaceParam.setValue(DeliciousConstants.NO);
		if (replace) {
			replaceParam.setValue(DeliciousConstants.YES);
			post.addParameter(replaceParam);
		}

		if (!shared) {
			NameValuePair sharedParam = new NameValuePair();
			sharedParam.setName(DeliciousConstants.SHARED_PARAMETER);
			sharedParam.setValue(DeliciousConstants.NO);
			post.addParameter(sharedParam);
		}

		try {
			post.addRequestHeader("Content-Type",
					"application/x-www-form-urlencoded; charset=utf-8");
			this.httpResult = this.httpClient.executeMethod(post);
			checkNotAuthorized(this.httpResult);
			this.logger.debug("Result: " + this.httpResult);
			if (post.getResponseBodyAsStream() != null) {
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(post
						.getResponseBodyAsStream(), DeliciousUtils.UTF_8));
				String input;
				while ((input = bufferedReader.readLine()) != null) {
					result.append(input).append(DeliciousUtils.LINE_SEPARATOR);
				}

				post.releaseConnection();
				lastApiCall = System.currentTimeMillis();
				if (result.indexOf(DeliciousConstants.CODE_DONE) != -1) {
					addPostResult = true;
				}
			}
		} catch (IOException e) {
			this.logger.error(e);
		}

		return addPostResult;
	}

	/**
	 * Make a post to del.icio.us
	 * 
	 * @param url
	 *            URL for post
	 * @param description
	 *            Description for post
	 * @param extended
	 *            Extended for post
	 * @param tags
	 *            Space-delimited list of tags
	 * @param date
	 *            Date for post
	 * @return <code>true</code> if posted, <code>false</code> otherwise
	 */
	public boolean addPost(final String url, final String description, final String extended,
			final String tags, final Date date) {
		return addPost(url, description, extended, tags, date, false, true);
	}

	/**
	 * Make a post to del.icio.us
	 * 
	 * @param url
	 *            URL for post
	 * @param description
	 *            Description for post
	 * @return <code>true</code> if posted, <code>false</code> otherwise
	 * @since 2.0
	 */
	public boolean addPost(final String url, final String description) {
		return addPost(url, description, null, null, null, false, true);
	}

	/**
	 * Deletes a post
	 * 
	 * @param url
	 *            URL for post
	 * @return <code>true</code> if post deleted, <code>false</code> otherwise
	 */
	public boolean deletePost(final String url) {
		clearResultMetaInformation();
		boolean deletePostResult = false;
		StringBuffer result = new StringBuffer();

		GetMethod get = new GetMethod(this.apiEndpoint + DeliciousConstants.POSTS_DELETE);
		get.setDoAuthentication(true);

		NameValuePair urlParam = new NameValuePair(DeliciousConstants.URL_PARAMETER, url);
		get.setQueryString(new NameValuePair[] { urlParam });

		try {
			this.httpResult = this.httpClient.executeMethod(get);
			checkNotAuthorized(this.httpResult);
			this.logger.debug("Result: " + this.httpResult);
			if (get.getResponseBodyAsStream() != null) {
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(get
						.getResponseBodyAsStream(), DeliciousUtils.UTF_8));
				String input;
				while ((input = bufferedReader.readLine()) != null) {
					result.append(input).append(DeliciousUtils.LINE_SEPARATOR);
				}

				get.releaseConnection();
				lastApiCall = System.currentTimeMillis();
				if (result.indexOf(DeliciousConstants.CODE_DONE) != -1) {
					deletePostResult = true;
				}
			}
		} catch (IOException e) {
			this.logger.error(e);
		}

		return deletePostResult;
	}

	/**
	 * Renames a tag
	 * 
	 * @param oldTag
	 *            Old tag
	 * @param newTag
	 *            New tag
	 * @return <code>true</code> if tag renamed, <code>false</code> otherwise
	 */
	public boolean renameTag(final String oldTag, final String newTag) {
		clearResultMetaInformation();
		boolean renameTagResult = false;
		StringBuffer result = new StringBuffer();

		GetMethod get = new GetMethod(this.apiEndpoint + DeliciousConstants.TAGS_RENAME);
		get.setDoAuthentication(true);

		NameValuePair oldParam = new NameValuePair(DeliciousConstants.OLD_PARAMETER, oldTag);
		NameValuePair newParam = new NameValuePair(DeliciousConstants.NEW_PARAMETER, newTag);
		get.setQueryString(new NameValuePair[] { oldParam, newParam });

		try {
			get
					.addRequestHeader("Content-Type",
							"application/x-www-form-urlencoded; charset=utf-8");
			this.httpResult = this.httpClient.executeMethod(get);
			checkNotAuthorized(this.httpResult);
			this.logger.debug("Result: " + this.httpResult);
			if (get.getResponseBodyAsStream() != null) {
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(get
						.getResponseBodyAsStream(), DeliciousUtils.UTF_8));
				String input;
				while ((input = bufferedReader.readLine()) != null) {
					result.append(input).append(DeliciousUtils.LINE_SEPARATOR);
				}

				get.releaseConnection();
				lastApiCall = System.currentTimeMillis();
				if (result.indexOf(DeliciousConstants.CODE_DONE) != -1) {
					renameTagResult = true;
				}
			}
		} catch (IOException e) {
			this.logger.error(e);
		}

		return renameTagResult;
	}

	/**
	 * Return a list of {@link Post} items in your inbox
	 * 
	 * @param date
	 *            Filter by this date
	 * @return List of {@link Post} items in your inbox
	 */
	public List getInboxEntries(final Date date) {
		clearResultMetaInformation();
		List posts = new ArrayList();
		StringBuffer result = new StringBuffer();

		GetMethod get = new GetMethod(this.apiEndpoint + DeliciousConstants.INBOX_GET);
		get.setDoAuthentication(true);

		if (date != null) {
			NameValuePair dateParam = new NameValuePair(DeliciousConstants.DT_PARAMETER,
					DeliciousUtils.getDeliciousDate(date));
			get.setQueryString(new NameValuePair[] { dateParam });
		}

		try {
			this.httpResult = this.httpClient.executeMethod(get);
			checkNotAuthorized(this.httpResult);
			this.logger.debug("Result: " + this.httpResult);
			if (get.getResponseBodyAsStream() != null) {
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(get
						.getResponseBodyAsStream(), DeliciousUtils.UTF_8));
				String input;
				while ((input = bufferedReader.readLine()) != null) {
					result.append(input).append(DeliciousUtils.LINE_SEPARATOR);
				}

				get.releaseConnection();
				lastApiCall = System.currentTimeMillis();
				Document document = this.documentBuilder.parse(new InputSource(new StringReader(
						result.toString())));
				NodeList postItems = document.getElementsByTagName(DeliciousConstants.POST_TAG);
				if (postItems != null && postItems.getLength() > 0) {
					for (int i = 0; i < postItems.getLength(); i++) {
						Node postItem = postItems.item(i);
						Post post;
						String href = postItem.getAttributes().getNamedItem(
								DeliciousConstants.HREF_ATTRIBUTE).getNodeValue();
						String description = postItem.getAttributes().getNamedItem(
								DeliciousConstants.DESCRIPTION_ATTRIBUTE).getNodeValue();
						String hash = postItem.getAttributes().getNamedItem(
								DeliciousConstants.HASH_ATTRIBUTE).getNodeValue();
						String tag = postItem.getAttributes().getNamedItem(
								DeliciousConstants.TAG_ATTRIBUTE).getNodeValue();
						String time = postItem.getAttributes().getNamedItem(
								DeliciousConstants.TIME_ATTRIBUTE).getNodeValue();
						String extended = null;
						if (postItem.getAttributes().getNamedItem(
								DeliciousConstants.EXTENDED_ATTRIBUTE) != null) {
							extended = postItem.getAttributes().getNamedItem(
									DeliciousConstants.EXTENDED_ATTRIBUTE).getNodeValue();
						}
						boolean shared = true;
						if (postItem.getAttributes().getNamedItem(
								DeliciousConstants.SHARED_PARAMETER) != null) {
							shared = Boolean.valueOf(
									postItem.getAttributes().getNamedItem(
											DeliciousConstants.SHARED_PARAMETER).getNodeValue())
									.booleanValue();
						}
						post = new Post(href, description, extended, hash, tag, time, shared);

						posts.add(post);
					}
				}
			}
		} catch (IOException e) {
			this.logger.error(e);
		} catch (SAXException e) {
			this.logger.error(e);
			throw new DeliciousException("Response parse error", e);
		}

		return posts;
	}

	/**
	 * Return a list of {@link DeliciousDate} objects containing inbox entries
	 * 
	 * @return List of {@link DeliciousDate} objects containing inbox entries
	 */
	public List getDatesWithInboxEntries() {
		clearResultMetaInformation();
		List dates = new ArrayList();
		StringBuffer result = new StringBuffer();

		GetMethod get = new GetMethod(this.apiEndpoint + DeliciousConstants.INBOX_DATES);
		get.setDoAuthentication(true);

		try {
			this.httpResult = this.httpClient.executeMethod(get);
			checkNotAuthorized(this.httpResult);
			this.logger.debug("Result: " + this.httpResult);
			if (get.getResponseBodyAsStream() != null) {
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(get
						.getResponseBodyAsStream(), DeliciousUtils.UTF_8));
				String input;
				while ((input = bufferedReader.readLine()) != null) {
					result.append(input).append(DeliciousUtils.LINE_SEPARATOR);
				}

				get.releaseConnection();
				lastApiCall = System.currentTimeMillis();
				Document document = this.documentBuilder.parse(new InputSource(new StringReader(
						result.toString())));
				NodeList dateItems = document.getElementsByTagName(DeliciousConstants.DATE_TAG);
				if (dateItems != null && dateItems.getLength() > 0) {
					for (int i = 0; i < dateItems.getLength(); i++) {
						Node dateItem = dateItems.item(i);
						DeliciousDate date;
						String count = dateItem.getAttributes().getNamedItem(
								DeliciousConstants.COUNT_ATTRIBUTE).getNodeValue();
						String dateAttribute = dateItem.getAttributes().getNamedItem(
								DeliciousConstants.DATE_ATTRIBUTE).getNodeValue();
						date = new DeliciousDate(Integer.parseInt(count), dateAttribute);

						dates.add(date);
					}
				}
			}
		} catch (IOException e) {
			this.logger.error(e);
		} catch (SAXException e) {
			this.logger.error(e);
			throw new DeliciousException("Response parse error", e);
		}

		return dates;
	}

	/**
	 * Return a list of {@link Subscription} objects
	 * 
	 * @return List of {@link Subscription} objects
	 */
	public List getSubscriptions() {
		clearResultMetaInformation();
		List subscriptions = new ArrayList();
		StringBuffer result = new StringBuffer();

		GetMethod get = new GetMethod(this.apiEndpoint + DeliciousConstants.INBOX_SUBS);
		get.setDoAuthentication(true);

		try {
			this.httpResult = this.httpClient.executeMethod(get);
			checkNotAuthorized(this.httpResult);
			this.logger.debug("Result: " + this.httpResult);
			if (get.getResponseBodyAsStream() != null) {
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(get
						.getResponseBodyAsStream(), DeliciousUtils.UTF_8));
				String input;
				while ((input = bufferedReader.readLine()) != null) {
					result.append(input).append(DeliciousUtils.LINE_SEPARATOR);
				}

				get.releaseConnection();
				lastApiCall = System.currentTimeMillis();
				Document document = this.documentBuilder.parse(new InputSource(new StringReader(
						result.toString())));
				NodeList subItems = document.getElementsByTagName(DeliciousConstants.SUB_TAG);
				if (subItems != null && subItems.getLength() > 0) {
					for (int i = 0; i < subItems.getLength(); i++) {
						Node subItem = subItems.item(i);
						Subscription subscription;
						String tag = subItem.getAttributes().getNamedItem(
								DeliciousConstants.TAG_ATTRIBUTE).getNodeValue();
						String user = subItem.getAttributes().getNamedItem(
								DeliciousConstants.USER_ATTRIBUTE).getNodeValue();

						subscription = new Subscription(tag, user);

						subscriptions.add(subscription);
					}
				}
			}
		} catch (IOException e) {
			this.logger.error(e);
		} catch (SAXException e) {
			this.logger.error(e);
			throw new DeliciousException("Response parse error", e);
		}

		return subscriptions;
	}

	/**
	 * Return a list of {@link Bundle} objects
	 * 
	 * @return List of {@link Bundle} objects
	 * @since 1.9
	 */
	public List getBundles() {
		clearResultMetaInformation();
		List bundles = new ArrayList();
		StringBuffer result = new StringBuffer();

		GetMethod get = new GetMethod(this.apiEndpoint + DeliciousConstants.BUNDLES_ALL);
		get.setDoAuthentication(true);

		try {
			this.httpResult = this.httpClient.executeMethod(get);
			checkNotAuthorized(this.httpResult);
			this.logger.debug("Result: " + this.httpResult);
			if (get.getResponseBodyAsStream() != null) {
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(get
						.getResponseBodyAsStream(), DeliciousUtils.UTF_8));
				String input;
				while ((input = bufferedReader.readLine()) != null) {
					result.append(input).append(DeliciousUtils.LINE_SEPARATOR);
				}

				get.releaseConnection();
				lastApiCall = System.currentTimeMillis();
				Document document = this.documentBuilder.parse(new InputSource(new StringReader(
						result.toString())));
				NodeList bundleItems = document.getElementsByTagName(DeliciousConstants.BUNDLE_TAG);
				if (bundleItems != null && bundleItems.getLength() > 0) {
					for (int i = 0; i < bundleItems.getLength(); i++) {
						Node bundleItem = bundleItems.item(i);
						Bundle bundle;
						String name = bundleItem.getAttributes().getNamedItem(
								DeliciousConstants.BUNDLE_NAME_ATTRIBUTE).getNodeValue();
						String tags = bundleItem.getAttributes().getNamedItem(
								DeliciousConstants.BUNDLE_TAGS_ATTRIBUTE).getNodeValue();

						bundle = new Bundle(name, tags);

						bundles.add(bundle);
					}
				}
			}
		} catch (IOException e) {
			this.logger.error(e);
		} catch (SAXException e) {
			this.logger.error(e);
			throw new DeliciousException("Response parse error", e);
		}

		return bundles;
	}

	/**
	 * Add a new tag bundle. If either the bundle name or tags is blank or null,
	 * <code>false</code> is returned immediately
	 * 
	 * @param bundleName
	 *            Bundle name
	 * @param tags
	 *            Space-separated list of tags
	 * @return <code>true</code> if the bundle was created, <code>false</code>
	 *         otherwise
	 * @since 1.9
	 */
	public boolean addBundle(final String bundleName, final String tags) {
		clearResultMetaInformation();
		boolean addBundleResult = false;
		StringBuffer result = new StringBuffer();

		PostMethod post = new PostMethod(this.apiEndpoint + DeliciousConstants.BUNDLES_SET);
		post.setDoAuthentication(true);

		if (DeliciousUtils.checkNullOrBlank(bundleName)) {
			return false;
		}

		if (DeliciousUtils.checkNullOrBlank(tags)) {
			return false;
		}

		NameValuePair bundleNameParam = new NameValuePair(DeliciousConstants.BUNDLE_PARAMETER,
				bundleName);
		NameValuePair tagsParam = new NameValuePair(DeliciousConstants.BUNDLE_TAGS_ATTRIBUTE, tags);

		post.setQueryString(new NameValuePair[] { bundleNameParam, tagsParam });

		try {
			post.addRequestHeader("Content-Type",
					"application/x-www-form-urlencoded; charset=utf-8");
			this.httpResult = this.httpClient.executeMethod(post);
			checkNotAuthorized(this.httpResult);
			this.logger.debug("Result: " + this.httpResult);
			if (post.getResponseBodyAsStream() != null) {
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(post
						.getResponseBodyAsStream(), DeliciousUtils.UTF_8));
				String input;
				while ((input = bufferedReader.readLine()) != null) {
					result.append(input).append(DeliciousUtils.LINE_SEPARATOR);
				}

				post.releaseConnection();

				if (result.indexOf(DeliciousConstants.CODE_OK_STANDALONE) != -1) {
					addBundleResult = true;
				}
			}
		} catch (IOException e) {
			this.logger.error(e);
		}

		return addBundleResult;
	}

	/**
	 * Delete a bundle of the given name
	 * 
	 * @param bundleName
	 *            Bundle to delete
	 * @return <code>true</code> if the bundle was deleted, false otherwise
	 * @since 1.9
	 */
	public boolean deleteBundle(final String bundleName) {
		clearResultMetaInformation();
		boolean deleteBundleResult = false;
		StringBuffer result = new StringBuffer();

		GetMethod get = new GetMethod(this.apiEndpoint + DeliciousConstants.BUNDLES_DELETE);
		get.setDoAuthentication(true);

		NameValuePair bundleNameParam = new NameValuePair(DeliciousConstants.BUNDLE_PARAMETER,
				bundleName);
		get.setQueryString(new NameValuePair[] { bundleNameParam });

		try {
			this.httpResult = this.httpClient.executeMethod(get);
			checkNotAuthorized(this.httpResult);
			this.logger.debug("Result: " + this.httpResult);
			if (get.getResponseBodyAsStream() != null) {
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(get
						.getResponseBodyAsStream(), DeliciousUtils.UTF_8));
				String input;
				while ((input = bufferedReader.readLine()) != null) {
					result.append(input).append(DeliciousUtils.LINE_SEPARATOR);
				}

				get.releaseConnection();
				lastApiCall = System.currentTimeMillis();
				if (result.indexOf(DeliciousConstants.CODE_OK_STANDALONE) != -1) {
					deleteBundleResult = true;
				}
			}
		} catch (IOException e) {
			this.logger.error(e);
		}

		return deleteBundleResult;
	}

	/**
	 * Delete a bundle of the given name
	 * 
	 * @param tagName
	 *            Bundle to delete
	 * @return <code>true</code> if the bundle was deleted, false otherwise
	 * @since 1.9
	 */
	public boolean deleteTag(final String tagName) {
		clearResultMetaInformation();
		boolean deleteTagResult = false;
		StringBuffer result = new StringBuffer();

		GetMethod get = new GetMethod(this.apiEndpoint + DeliciousConstants.TAGS_DELETE);
		get.setDoAuthentication(true);

		NameValuePair bundleNameParam = new NameValuePair(DeliciousConstants.TAG_PARAMETER, tagName);
		get.setQueryString(new NameValuePair[] { bundleNameParam });

		try {
			this.httpResult = this.httpClient.executeMethod(get);
			checkNotAuthorized(this.httpResult);
			this.logger.debug("Result: " + this.httpResult);
			if (get.getResponseBodyAsStream() != null) {
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(get
						.getResponseBodyAsStream(), DeliciousUtils.UTF_8));
				String input;
				while ((input = bufferedReader.readLine()) != null) {
					result.append(input).append(DeliciousUtils.LINE_SEPARATOR);
				}

				get.releaseConnection();
				lastApiCall = System.currentTimeMillis();
				if (result.indexOf(DeliciousConstants.CODE_OK_STANDALONE) != -1) {
					deleteTagResult = true;
				}
			}
		} catch (IOException e) {
			this.logger.error(e);
		}

		return deleteTagResult;
	}

	/**
	 * Add or remove a subscription
	 * 
	 * @param user
	 *            Username
	 * @param tag
	 *            Optional, set to <code>null</code> for all posts
	 * @param unsubscribe
	 *            If you want to unsubscribe
	 * @return <code>true</code> if add/remove subscription successful,
	 *         <code>false</code> otherwise
	 */
	public boolean subs(final String user, final String tag, final boolean unsubscribe) {
		clearResultMetaInformation();
		boolean subscribeResult = false;
		StringBuffer result = new StringBuffer();

		GetMethod get;
		if (!unsubscribe) {
			get = new GetMethod(this.apiEndpoint + DeliciousConstants.INBOX_SUB);
		} else {
			get = new GetMethod(this.apiEndpoint + DeliciousConstants.INBOX_UNSUB);
		}
		get.setDoAuthentication(true);

		List params = new ArrayList();

		NameValuePair userParam = new NameValuePair(DeliciousConstants.USER_PARAMETER, user);
		params.add(userParam);
		if (!DeliciousUtils.checkNullOrBlank(tag)) {
			NameValuePair tagParam = new NameValuePair(DeliciousConstants.TAG_PARAMETER, tag);
			params.add(tagParam);
		}

		if (params.size() > 0) {
			get.setQueryString((NameValuePair[]) params.toArray(new NameValuePair[params.size()]));
		}

		try {
			this.httpResult = this.httpClient.executeMethod(get);
			checkNotAuthorized(this.httpResult);
			this.logger.debug("Result: " + this.httpResult);
			if (get.getResponseBodyAsStream() != null) {
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(get
						.getResponseBodyAsStream(), DeliciousUtils.UTF_8));
				String input;
				while ((input = bufferedReader.readLine()) != null) {
					result.append(input).append(DeliciousUtils.LINE_SEPARATOR);
				}

				get.releaseConnection();
				lastApiCall = System.currentTimeMillis();
				if (result.indexOf(DeliciousConstants.CODE_OK) != -1) {
					subscribeResult = true;
				}
			}
		} catch (IOException e) {
			this.logger.error(e);
		}

		return subscribeResult;
	}

	/**
	 * Get the time of the last update
	 * 
	 * @return {@link Date} object of last update time or <code>null</code> if
	 *         there was an error in the call
	 * @since 1.3
	 */
	public Date getLastUpdate() {
		clearResultMetaInformation();
		StringBuffer result = new StringBuffer();

		GetMethod get = new GetMethod(this.apiEndpoint + DeliciousConstants.POSTS_UPDATE);
		get.setDoAuthentication(true);

		try {
			this.httpResult = this.httpClient.executeMethod(get);
			checkNotAuthorized(this.httpResult);
			this.logger.debug("Result: " + this.httpResult);
			if (get.getResponseBodyAsStream() != null) {
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(get
						.getResponseBodyAsStream(), DeliciousUtils.UTF_8));
				String input;
				while ((input = bufferedReader.readLine()) != null) {
					result.append(input).append(DeliciousUtils.LINE_SEPARATOR);
				}

				get.releaseConnection();
				lastApiCall = System.currentTimeMillis();
				Document document = this.documentBuilder.parse(new InputSource(new StringReader(
						result.toString())));
				NodeList updateItems = document.getElementsByTagName(DeliciousConstants.UPDATE_TAG);
				if (updateItems != null && updateItems.getLength() > 0) {
					Node updateItem = updateItems.item(0);
					String updateTime = updateItem.getAttributes().getNamedItem(
							DeliciousConstants.TIME_ATTRIBUTE).getNodeValue();

					return DeliciousUtils.getDateFromUTCString(updateTime);
				}
			}
		} catch (IOException e) {
			this.logger.error(e);
		} catch (SAXException e) {
			this.logger.error(e);
			throw new DeliciousException("Response parse error", e);
		}

		return null;
	}

	/**
	 * Check to see if the user is not authorized. If result is 401
	 * (NOT_AUTHORIZED) a {@link DeliciousNotAuthorizedException} is thrown
	 * 
	 * @param result
	 *            Result code from executing HTTP method
	 * @since 1.4
	 */
	protected void checkNotAuthorized(final int result) {
		if (result == HttpURLConnection.HTTP_UNAUTHORIZED) {
			throw new DeliciousNotAuthorizedException();
		}
	}

	/**
	 * Clear the result meta information by setting the object to
	 * <code>null</code>.
	 * 
	 * @since 1.3
	 */
	protected void clearResultMetaInformation() {
		while ((System.currentTimeMillis() - lastApiCall) < WAIT_INTERVALL) {
			try {
				System.out.println("WAITING");
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// does nothing.
			}
		}
		this.resultMetaInformation = null;

	}

	/**
	 * Retrieve an object containing meta information about the last call. Only
	 * certain methods actually populate the result meta information such as the
	 * {@link #getAllPosts()} method.
	 * 
	 * @return Object containing meta information about the last call
	 * @since 1.3
	 */
	public Object getResultMetaInformation() {
		return this.resultMetaInformation;
	}
}
