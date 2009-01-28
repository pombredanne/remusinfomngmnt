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

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import org.remus.infomngmnt.core.progress.CancelableRunnable;
import org.remus.infomngmnt.link.HTMLStripReader;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class IndexWebPageRunnable extends CancelableRunnable {

	private final String url;
	private String content;

	public IndexWebPageRunnable(final String url) {
		this.url = url;
	}

	/* (non-Javadoc)
	 * @see org.remus.infomngmnt.core.progress.CancelableRunnable#runCancelableRunnable(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	protected IStatus runCancelableRunnable(final IProgressMonitor monitor) {
		HttpClient client = new HttpClient();
		//		// Create a method instance.
		GetMethod method = new GetMethod(this.url);
		// Execute the method.
		
			try {
				int statusCode = client.executeMethod(method);

				if (statusCode != HttpStatus.SC_OK) {
					System.err.println("Method failed: " + method.getStatusLine());
				}
				InputStream stream = method.getResponseBodyAsStream();
				Reader in = new HTMLStripReader(new InputStreamReader(stream));
				StringWriter sw = new StringWriter();
				int ch;
				while ( (ch=in.read()) != -1 ) {
					sw.append((char)ch);
				}
				
				String string = sw.toString();
				string = string.replaceAll("\\r\\n", " ");
				string = string.replaceAll("\\n", " ");
				string = string.replaceAll("\\s+", " ");
				this.content = string;
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
		return this.content;
	}

}
