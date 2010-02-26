package org.remus.infomngmnt.connector.twitter.ui;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;

public abstract class ShrinkURLUtils {
	public static String getTinyUrl(final String fullUrl) throws HttpException, IOException {
		HttpClient httpclient = new HttpClient();

		// Prepare a request object
		HttpMethod method = new GetMethod("http://tinyurl.com/api-create.php");
		method.setQueryString(new NameValuePair[] { new NameValuePair("url", fullUrl) });
		httpclient.executeMethod(method);
		String tinyUrl = method.getResponseBodyAsString();
		method.releaseConnection();
		return tinyUrl;
	}

	public static String getBitLyUrl(final String fullUrl) throws HttpException, IOException {
		HttpClient httpclient = new HttpClient();
		// Prepare a request object
		HttpMethod method = new GetMethod("http://to.ly/api.php");
		method.setQueryString(new NameValuePair[] { new NameValuePair("longurl", fullUrl) });
		httpclient.executeMethod(method);
		String bitLyUrl = method.getResponseBodyAsString();
		method.releaseConnection();
		return bitLyUrl;
	}

}