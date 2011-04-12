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

package org.remus.infomngmnt.operation;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.osgi.util.NLS;
import org.eclipse.remus.common.core.streams.HTMLStripReader;
import org.eclipse.remus.common.io.proxy.Proxy;
import org.eclipse.remus.common.io.proxy.ProxyUtil;
import org.eclipse.remus.ui.progress.CancelableRunnable;
import org.remus.infomngmnt.link.messsage.Messages;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class IndexWebPageRunnable extends CancelableRunnable {

	private final String url;
	private String content;

	public IndexWebPageRunnable(final String url) {
		this.url = url;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.core.progress.CancelableRunnable#runCancelableRunnable
	 * (org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	protected IStatus runCancelableRunnable(final IProgressMonitor monitor) {
		monitor.beginTask(
				NLS.bind(Messages.IndexWebPageRunnable_GrabbingContent, url.toString()),
				IProgressMonitor.UNKNOWN);
		HttpClient client = new HttpClient();
		Proxy proxyByUrl = ProxyUtil.getProxyByUrl(url.toString());
		if (proxyByUrl != null) {
			HostConfiguration hostConfiguration = new HostConfiguration();
			hostConfiguration.setProxy(proxyByUrl.getAddress().getHostName(),
					proxyByUrl.getAddress().getPort());
			client.setHostConfiguration(hostConfiguration);
			client.getState().setProxyCredentials(
					AuthScope.ANY,
					new UsernamePasswordCredentials(proxyByUrl.getUsername(),
							proxyByUrl.getPassword()));
		}
		// // Create a method instance.
		GetMethod method = new GetMethod(url);
		// Execute the method.

		try {
			int statusCode = client.executeMethod(method);

			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: " + method.getStatusLine()); //$NON-NLS-1$
			}
			InputStream stream = method.getResponseBodyAsStream();
			Reader in = new HTMLStripReader(new InputStreamReader(stream));
			StringWriter sw = new StringWriter();
			int ch;
			while ((ch = in.read()) != -1) {
				sw.append((char) ch);
			}

			String string = sw.toString();
			string = string.replaceAll("\\r\\n", " "); //$NON-NLS-1$ //$NON-NLS-2$
			string = string.replaceAll("\\n", " "); //$NON-NLS-1$ //$NON-NLS-2$
			string = string.replaceAll("\\s+", " "); //$NON-NLS-1$ //$NON-NLS-2$
			content = string;
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Status.OK_STATUS;
	}

	public String getContent() {
		return content;
	}

}
