/*******************************************************************************
 * Copyright (c) 2008 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/

package org.remus.infomngmnt.ui.desktop;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import org.remus.infomngmnt.common.ui.swt.AbstractNotificationPopup;
import org.remus.infomngmnt.common.ui.swt.SwtFadeUtil;
import org.remus.infomngmnt.common.ui.swt.SwtFadeUtil.FadeJob;
import org.remus.infomngmnt.common.ui.swt.SwtFadeUtil.IFadeListener;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
@SuppressWarnings("restriction")
public class DesktopWindow extends AbstractNotificationPopup {

	private final Display display;

	public DesktopWindow(Display display) {
		super(display);
		this.display = display;
	}

	private final Job closeJob = new Job("set alpha job") {

		@Override
		protected IStatus run(IProgressMonitor monitor) {
			if (!DesktopWindow.this.display.isDisposed()) {
				DesktopWindow.this.display.asyncExec(new Runnable() {
					public void run() {
						Shell shell = getShell();
						if (shell == null || shell.isDisposed()) {
							return;
						}

						if (isMouseOver(shell)) {
							scheduleAutoClose();
							return;
						}

						closeFade();
					}

				});
			}
			if (monitor.isCanceled()) {
				return Status.CANCEL_STATUS;
			}

			return Status.OK_STATUS;
		}
	};


	private FadeJob transparancyJob;
	@Override
	protected void createContentArea(Composite parent) {

		super.createContentArea(parent);
	}

	@Override
	protected void scheduleAutoClose() {
		this.closeJob.schedule(2000);
	}

	@Override
	protected String getPopupShellTitle() {
		return "RIM Desktop Panel";
	}

	private boolean isMouseOver(Shell shell) {
		if (this.display.isDisposed()) {
			return false;
		}
		return shell.getBounds().contains(this.display.getCursorLocation());
	}

	@Override
	public void closeFade() {
		if (this.transparancyJob != null) {
			this.transparancyJob.cancelAndWait(false);
		}
		if (true) {
			this.transparancyJob = SwtFadeUtil.fadeOut(getShell(),120, new IFadeListener() {
				public void faded(Shell shell, int alpha) {
					if (!shell.isDisposed()) {
						if (alpha == 0) {
							shell.close();
						} else if (isMouseOver(shell)) {
							if (DesktopWindow.this.transparancyJob != null) {
								DesktopWindow.this.transparancyJob.cancelAndWait(false);
							}
							DesktopWindow.this.transparancyJob = SwtFadeUtil.fastFadeIn(shell, new IFadeListener() {
								public void faded(Shell shell, int alpha) {
									if (shell.isDisposed()) {
										return;
									}

									if (alpha == 255) {
										scheduleAutoClose();
									}
								}
							});
						}
					}
				}
			});
		} else {
			// do nothing
		}
	}

	@Override
	public int open() {

		return super.open();
	}

	@Override
	public void setBlockOnOpen(boolean shouldBlock) {
		super.setBlockOnOpen(shouldBlock);
	}

}
