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

package org.remus.infomngmnt.eclipsemarketplace.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import org.remus.infomngmnt.common.core.util.StringUtils;
import org.remus.infomngmnt.eclipsemarketplace.api.beans.Category;
import org.remus.infomngmnt.eclipsemarketplace.api.beans.CategoryEntry;
import org.remus.infomngmnt.eclipsemarketplace.api.beans.Market;
import org.remus.infomngmnt.eclipsemarketplace.api.beans.MarketPlaceElement;
import org.remus.infomngmnt.eclipsemarketplace.api.beans.MarketPlaceRoot;
import org.remus.infomngmnt.eclipsemarketplace.api.beans.Type;

/**
 * Class for accessing <a href="http://marketplace.eclipse.org">Eclipse
 * marketplace</a>.
 * 
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class MarketPlace {

	private final Log logger = LogFactory.getLog(MarketPlace.class);

	private final HttpClient httpClient;

	private final String apiEndpoint;

	private DocumentBuilder documentBuilder;

	public MarketPlace() {
		this(Constants.API_ENDPOINT);
	}

	public MarketPlace(final String apiEndpoint) {
		this.apiEndpoint = apiEndpoint;
		this.httpClient = new HttpClient();
		HttpClientParams httpClientParams = new HttpClientParams();
		DefaultHttpMethodRetryHandler defaultHttpMethodRetryHandler = new DefaultHttpMethodRetryHandler(
				0, false);
		httpClientParams.setParameter(HttpMethodParams.USER_AGENT, Constants.USER_AGENT_VALUE);
		httpClientParams
				.setParameter(HttpClientParams.RETRY_HANDLER, defaultHttpMethodRetryHandler);
		httpClientParams.setCookiePolicy(CookiePolicy.RFC_2109);

		this.httpClient.setParams(httpClientParams);

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

	private void checkResult(final Document document) throws MarketPlaceException {
		// does actually nothing

	}

	public MarketPlaceRoot getRoot() throws MarketPlaceException, IOException {
		StringWriter result = new StringWriter();
		List<NameValuePair> params = getBaseQuery();
		MarketPlaceRoot rootElement = new MarketPlaceRoot();
		PostMethod post = new PostMethod(this.apiEndpoint + Constants.XML);
		post.addParameters(params.toArray(new NameValuePair[params.size()]));
		try {
			int httpResult = this.httpClient.executeMethod(post);
			this.logger.debug("Result:" + httpResult);
			if (post.getResponseBodyAsStream() != null) {
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(post
						.getResponseBodyAsStream(), MarketPlaceUtils.UTF_8));
				String input;
				while ((input = bufferedReader.readLine()) != null) {
					result.append(input).append(MarketPlaceUtils.LINE_SEPARATOR);
				}
				bufferedReader.close();
			}
			post.releaseConnection();
			Document document = this.documentBuilder.parse(new InputSource(new StringReader(result
					.toString())));
			checkResult(document);
			NodeList childNodes = document.getChildNodes();
			for (int i = 0, n = childNodes.getLength(); i < n; i++) {
				Node baseNode = childNodes.item(i);
				if (Constants.MARKETPLACE_NODE.equals(baseNode.getNodeName())) {
					NodeList marketNodes = baseNode.getChildNodes();
					List<Market> markets = new ArrayList<Market>();
					for (int j = 0, m = marketNodes.getLength(); j < m; j++) {
						Node marketNode = marketNodes.item(j);
						if (Constants.MARKET_NODE.equals(marketNode.getNodeName())) {
							Market market = new Market();
							market.setName(marketNode.getAttributes().getNamedItem(
									Constants.NAME_ATTRIBUTE).getNodeValue());
							String marketId = marketNode.getAttributes().getNamedItem(
									Constants.ID_ATTRIBUTE).getNodeValue();
							if (marketId != null) {
								try {
									market.setId(Integer.valueOf(marketId));
								} catch (NumberFormatException e) {
									throw new MarketPlaceException("Market Id no valid integer");
								}
							}
							NodeList categoryNodes = marketNode.getChildNodes();
							List<Category> categories = new ArrayList<Category>();
							for (int k = 0, o = categoryNodes.getLength(); k < o; k++) {
								Node categoryNode = categoryNodes.item(k);
								if (Constants.CATEGORY_NODE.equals(categoryNode.getNodeName())) {
									Category category = new Category();
									String categoryId = categoryNode.getAttributes().getNamedItem(
											Constants.ID_ATTRIBUTE).getNodeValue();
									if (categoryId != null) {
										try {
											category.setId(Integer.valueOf(categoryId));
										} catch (NumberFormatException e) {
											throw new MarketPlaceException(
													"Category Id no valid integer");
										}
									}
									String categoryCount = categoryNode.getAttributes()
											.getNamedItem(Constants.COUNT_ATTRIBUTE).getNodeValue();
									if (categoryCount != null) {
										try {
											category.setCount(Integer.valueOf(categoryCount));
										} catch (NumberFormatException e) {
											throw new MarketPlaceException(
													"Categoriy Count no valid integer");
										}
									}
									category.setName(((Element) categoryNode)
											.getAttribute(Constants.NAME_ATTRIBUTE));
									categories.add(category);
								}
							}
							market.setCategories(categories);
							markets.add(market);
						}
					}
					rootElement.setMarkets(markets);
				}

			}
		} catch (SAXException e) {
			throw new IOException("Error processing result, invalid xml-response.");
		}
		return rootElement;
	}

	public CategoryEntry[] getEntriesForCategory(final int category) throws MarketPlaceException,
			IOException {
		StringWriter result = new StringWriter();
		List<CategoryEntry> returnValue = new ArrayList<CategoryEntry>();
		List<NameValuePair> params = getBaseQuery();
		PostMethod post = new PostMethod(this.apiEndpoint + Constants.TAXONOMY + Constants.TERM
				+ category + "/" + Constants.XML);
		post.addParameters(params.toArray(new NameValuePair[params.size()]));
		try {
			int httpResult = this.httpClient.executeMethod(post);
			this.logger.debug("Result:" + httpResult);
			if (post.getResponseBodyAsStream() != null) {
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(post
						.getResponseBodyAsStream(), MarketPlaceUtils.UTF_8));
				String input;
				while ((input = bufferedReader.readLine()) != null) {
					result.append(input).append(MarketPlaceUtils.LINE_SEPARATOR);
				}
				bufferedReader.close();
			}
			post.releaseConnection();

			// String marketPlaceHack = marketPlaceHack(result);
			Document document = this.documentBuilder.parse(new InputSource(new StringReader(result
					.toString())));
			checkResult(document);
			NodeList childNodes = document.getChildNodes();
			for (int i = 0, n = childNodes.getLength(); i < n; i++) {
				Node baseNode = childNodes.item(i);
				if (Constants.MARKETPLACE_NODE.equals(baseNode.getNodeName())) {
					NodeList entries = baseNode.getChildNodes();
					for (int j = 0, m = entries.getLength(); j < m; j++) {
						Node entryNode = entries.item(j);
						if (Constants.CATEGORY_NODE.equals(entryNode.getNodeName())) {
							NodeList entryDetailNodes = entryNode.getChildNodes();
							for (int k = 0, o = entryDetailNodes.getLength(); k < o; k++) {
								Node entryDetailNode = entryDetailNodes.item(k);
								if (Constants.NODE_NODE.equals(entryDetailNode.getNodeName())) {
									CategoryEntry categoryEntry = new CategoryEntry();
									categoryEntry.setName(((Element) entryDetailNode)
											.getAttribute(Constants.NAME_ATTRIBUTE));
									categoryEntry.setId(Integer.valueOf(((Element) entryDetailNode)
											.getAttribute(Constants.ID_ATTRIBUTE)));

									if (Constants.NID_NODE.equals(entryDetailNode.getNodeName())) {
										String id = entryDetailNode.getFirstChild()
												.getTextContent();
										try {
											categoryEntry.setId(Integer.valueOf(id));
										} catch (NumberFormatException e) {
											throw new MarketPlaceException(
													"Entry Id is not a valid integer");
										}
									}
									if (Constants.FAVORITED_NODE.equals(entryDetailNode
											.getNodeName())) {
										String favorited = entryDetailNode.getFirstChild()
												.getTextContent();
										try {
											categoryEntry.setFavorited(Integer.valueOf(favorited));
										} catch (NumberFormatException e) {
											throw new MarketPlaceException(
													"Favorite count is not a valid integer");
										}
									}
									returnValue.add(categoryEntry);
								}
							}
						}
					}
				}
			}
		} catch (SAXException e) {
			throw new IOException("Error processing result, invalid xml-response.");
		}
		return returnValue.toArray(new CategoryEntry[returnValue.size()]);
	}

	public MarketPlaceElement getMarketPlaceElementById(final int elementId)
			throws MarketPlaceException, IOException {
		StringWriter result = new StringWriter();
		List<NameValuePair> params = getBaseQuery();
		MarketPlaceElement returnValue = new MarketPlaceElement();
		PostMethod post = new PostMethod(this.apiEndpoint + Constants.NODE + elementId + "/"
				+ Constants.XML);
		post.addParameters(params.toArray(new NameValuePair[params.size()]));
		try {
			int httpResult = this.httpClient.executeMethod(post);
			this.logger.debug("Result:" + httpResult);
			if (post.getResponseBodyAsStream() != null) {
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(post
						.getResponseBodyAsStream(), MarketPlaceUtils.UTF_8));
				String input;
				while ((input = bufferedReader.readLine()) != null) {
					result.append(input).append(MarketPlaceUtils.LINE_SEPARATOR);
				}
				bufferedReader.close();
			}
			post.releaseConnection();
			String marketPlaceHack = marketPlaceHack(result);
			Document document = this.documentBuilder.parse(new InputSource(new StringReader(
					marketPlaceHack)));
			checkResult(document);

			NodeList childNodes = document.getChildNodes();
			for (int i = 0, n = childNodes.getLength(); i < n; i++) {
				Node baseNode = childNodes.item(i);
				if (Constants.MARKETPLACE_NODE.equals(baseNode.getNodeName())) {
					NodeList marketNodes = baseNode.getChildNodes();
					for (int j = 0, m = marketNodes.getLength(); j < m; j++) {
						Node childNode = marketNodes.item(j);
						if (Constants.NODE_NODE.equals(childNode.getNodeName())) {
							returnValue.setId(Integer.parseInt(((Element) childNode)
									.getAttribute(Constants.ID_ATTRIBUTE)));
							returnValue.setTitle(((Element) childNode)
									.getAttribute(Constants.NAME_ATTRIBUTE));
							returnValue.setUrl(((Element) childNode)
									.getAttribute(Constants.URL_NODE));
							for (int k = 0, o = childNode.getChildNodes().getLength(); k < o; k++) {
								Node marketNode = childNode.getChildNodes().item(k);
								String firstChild = null;
								if (marketNode.getFirstChild() != null) {
									if (marketNode.getFirstChild() instanceof CDATASection) {
										firstChild = marketNode.getFirstChild().getFirstChild()
												.getTextContent();
									} else {
										firstChild = marketNode.getFirstChild().getTextContent();
									}
								}
								if (firstChild != null) {
									if (Constants.ID_ATTRIBUTE.equals(marketNode.getNodeName())) {
										try {
											returnValue.setId(Integer.valueOf(firstChild));
										} catch (NumberFormatException e) {
											throw new MarketPlaceException(
													"Market Id no valid integer");
										}
									}
									if (Constants.TITLE_NODE.equals(marketNode.getNodeName())) {
										returnValue.setTitle(firstChild);
									}
									if (Constants.TYPE_NODE.equals(marketNode.getNodeName())) {
										returnValue.setType(Type.fromString(firstChild));
									}
									if (Constants.OWNER_NODE.equals(marketNode.getNodeName())) {
										returnValue.setOwner(firstChild);
									}
									if (Constants.FAVORITED_NODE.equals(marketNode.getNodeName())) {
										try {
											returnValue.setFavorited(Integer.valueOf(firstChild));
										} catch (NumberFormatException e) {
											throw new MarketPlaceException(
													"Favorited no valid integer");
										}
									}
									if (Constants.BODY_NODE.equals(marketNode.getNodeName())) {
										returnValue.setBody(firstChild);
									}

									if (Constants.CREATED_NODE.equals(marketNode.getNodeName())) {
										try {
											Long timestamp = Long.valueOf(firstChild);
											returnValue.setCreated(new Date(timestamp * 1000));
										} catch (NumberFormatException e) {
											throw new MarketPlaceException(
													"Creation date no valid long");
										}
									}
									if (Constants.CHANGED_NODE.equals(marketNode.getNodeName())) {
										try {
											Long timestamp = Long.valueOf(firstChild);
											returnValue.setChanged(new Date(timestamp * 1000));
										} catch (NumberFormatException e) {
											throw new MarketPlaceException(
													"Change date no valid long");
										}
									}
									if (Constants.FOUNDATION_NODE.equals(marketNode.getNodeName())) {
										try {
											Integer value = Integer.valueOf(firstChild);
											returnValue.setFoundationMember(value != 0);
										} catch (NumberFormatException e) {
											throw new MarketPlaceException(
													"No valid foundation member flag");
										}
									}

									if (Constants.IMAGE_NODE.equals(marketNode.getNodeName())) {
										returnValue.setImage(firstChild);
									}
									if (Constants.VERSION_NODE.equals(marketNode.getNodeName())) {
										returnValue.setVersion(firstChild);
									}
									if (Constants.LICENSE_NODE.equals(marketNode.getNodeName())) {
										returnValue.setLicense(firstChild);
									}
									if (Constants.COMPANYNAME_NODE.equals(marketNode.getNodeName())) {
										returnValue.setCompany(firstChild);
									}
									if (Constants.STATUS_NODE.equals(marketNode.getNodeName())) {
										returnValue.setStatus(firstChild);
									}
									if (Constants.ECLIPSEVERSION_NODE.equals(marketNode
											.getNodeName())) {
										returnValue.setEclipseversion(firstChild);
									}
									if (Constants.SUPPORTURL_NODE.equals(marketNode.getNodeName())) {
										returnValue.setSupportUrl(firstChild);
									}
									if (Constants.UPDATEURL_NODE.equals(marketNode.getNodeName())) {
										returnValue.setUpdateUrl(firstChild);
									}
								}
							}
						}
					}

				}

			}
		} catch (SAXException e) {
			throw new IOException("Error processing result, invalid xml-response.");
		}
		return returnValue;
	}

	/**
	 * Was necessary due to https://bugs.eclipse.org/bugs/show_bug.cgi?id=297838
	 * 
	 * @param result
	 * @return
	 */
	private String marketPlaceHack(final StringWriter result) {
		String returnValue = result.toString();
		returnValue = escapeTag("title", returnValue);
		return returnValue;

	}

	private String escapeTag(final String string, String returnValue) {
		Matcher matcher = Pattern.compile("<" + string + "[^>/]*>(.*?)</" + string + ">").matcher(
				returnValue);
		String replacement = null;
		if (matcher.find()) {
			replacement = StringEscapeUtils.escapeXml(matcher.group(1));
		}
		if (replacement != null) {
			returnValue = returnValue.replaceAll("<" + string + "[^>/]*>(.*?)</" + string + ">",
					StringUtils.join("<" + string + ">", replacement, "</" + string + ">"));
		}
		return returnValue;
	}

	private List<NameValuePair> getBaseQuery() {
		List<NameValuePair> returnValue = new ArrayList<NameValuePair>();
		// does actually nothing.
		return returnValue;
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
	 * Sets proxy configuration information. This method must be called before
	 * any calls to the API if you require proxy configuration.
	 * 
	 * @param proxyHost
	 *            Proxy host
	 * @param proxyPort
	 *            Proxy port
	 * @since 1.0
	 */
	public void setProxyConfiguration(final String proxyHost, final int proxyPort) {
		HostConfiguration hostConfiguration = new HostConfiguration();
		hostConfiguration.setProxy(proxyHost, proxyPort);

		this.httpClient.setHostConfiguration(hostConfiguration);
	}

}
