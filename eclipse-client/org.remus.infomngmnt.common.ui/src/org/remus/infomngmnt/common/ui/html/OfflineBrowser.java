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

package org.remus.infomngmnt.common.ui.html;

import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Display;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class OfflineBrowser {

	public OfflineBrowser(final Browser browser) {
		this.browser = browser;
		browser.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(final DisposeEvent e) {
				dispose();
			}

		});
	}

	private final IResourceChangeListener binFileListener = new IResourceChangeListener() {

		public void resourceChanged(final IResourceChangeEvent event) {
			if (event.getDelta() != null) {
				IResourceDelta[] affectedChildren = event.getDelta().getAffectedChildren();
				visit(affectedChildren);
			}
		}

		private void visit(final IResourceDelta[] affectedChildren) {
			for (IResourceDelta resourceDelta : affectedChildren) {
				if (resourceDelta.getResource().equals(OfflineBrowser.this.downloadJob.getTarget())) {
					Display.getDefault().asyncExec(new Runnable() {
						public void run() {
							if (!OfflineBrowser.this.browser.isDisposed()) {
								if (OfflineBrowser.this.downloadJob.getTarget().exists()) {
									OfflineBrowser.this.browser
											.setUrl(OfflineBrowser.this.downloadJob.getTarget()
													.getLocation().toOSString());
								} else {
									OfflineBrowser.this.browser
											.setUrl(OfflineBrowser.this.loadingUrl);
								}
							}
						}

					});
					return;
				}
				visit(resourceDelta.getAffectedChildren());

			}
		}
	};

	private final Browser browser;
	private String loadingUrl;

	private DownloadMissingUrlJob downloadJob;

	public void init(final String loadingUrl, final DownloadMissingUrlJob downloadJob) {
		this.loadingUrl = loadingUrl;
		this.downloadJob = downloadJob;
		if (this.downloadJob.getTarget().exists()) {
			this.browser.setUrl(this.downloadJob.getTarget().getLocation().toOSString());
		} else {
			this.browser.setUrl(loadingUrl);
		}
		ResourcesPlugin.getWorkspace().addResourceChangeListener(this.binFileListener);
		downloadJob.schedule();
	}

	protected void dispose() {
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(this.binFileListener);
	}

}
