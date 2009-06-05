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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import org.remus.infomngmnt.common.ui.extension.AbstractTraySection;
import org.remus.infomngmnt.common.ui.swt.AbstractDesktopWindow;
import org.remus.infomngmnt.common.ui.swt.SwtFadeUtil;
import org.remus.infomngmnt.common.ui.swt.SwtFadeUtil.FadeJob;
import org.remus.infomngmnt.common.ui.swt.SwtFadeUtil.IFadeListener;
import org.remus.infomngmnt.ui.UIPlugin;
import org.remus.infomngmnt.ui.preference.UIPreferenceInitializer;
import org.remus.infomngmt.common.ui.uimodel.TraySection;
import org.remus.infomngmt.common.ui.uimodel.TraySectionCollection;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
@SuppressWarnings("restriction")
public class DesktopWindow extends AbstractDesktopWindow {

	private final Display display;

	private List<AbstractTraySection> traySections;

	public DesktopWindow(final Display display) {
		super(display);
		this.display = display;
	}

	private final Job closeJob = new Job("set alpha job") {

		@Override
		protected IStatus run(final IProgressMonitor monitor) {
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
	private FormToolkit toolkit;

	@Override
	protected void createContentArea(final Composite parent) {
		this.toolkit = new FormToolkit(parent.getDisplay());
		TraySectionCollection sections = TrayConfigurationManager.getInstance().getTraySections();
		EList<TraySection> sections2 = sections.getSections();
		this.traySections = new ArrayList<AbstractTraySection>();
		for (TraySection traySection : sections2) {
			Section createSection = this.toolkit.createSection(parent, SWT.NO_FOCUS);
			this.toolkit.createCompositeSeparator(createSection);
			createSection.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
			Composite composite = this.toolkit.createComposite(createSection, SWT.NO_FOCUS);
			createSection.setClient(composite);
			AbstractTraySection implementation = traySection.getImplementation();
			implementation.init(this.toolkit, traySection);
			createSection.setText(implementation.getTitle());
			implementation.createDetailsPart(composite);
			this.traySections.add(implementation);
		}
	}

	@Override
	protected void scheduleAutoClose() {
		this.closeJob.schedule(2000);
	}

	@Override
	protected String getPopupShellTitle() {
		return "RIM Desktop Panel";
	}

	private boolean isMouseOver(final Shell shell) {
		if (this.display.isDisposed()) {
			return false;
		}
		return shell.getBounds().contains(this.display.getCursorLocation());
	}

	@Override
	public boolean close() {
		for (AbstractTraySection traySection : this.traySections) {
			traySection.dispose();
		}
		return super.close();
	}

	@Override
	public void closeFade() {
		if (this.transparancyJob != null) {
			this.transparancyJob.cancelAndWait(false);
			this.transparancyJob.done(Status.OK_STATUS);
		}
		this.transparancyJob = SwtFadeUtil.fadeOut(getShell(), 120, new IFadeListener() {
			public void faded(final Shell shell, final int alpha) {
				if (!shell.isDisposed()) {
					if (alpha == 0) {
						shell.close();
					} else if (isMouseOver(shell)) {
						if (DesktopWindow.this.transparancyJob != null) {
							DesktopWindow.this.transparancyJob.cancelAndWait(false);
							DesktopWindow.this.transparancyJob.done(Status.OK_STATUS);
						}
						DesktopWindow.this.transparancyJob = SwtFadeUtil.fastFadeIn(shell,
								new IFadeListener() {
									public void faded(final Shell shell, final int alpha) {
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

	}

	@Override
	public int open() {
		return super.open();

	}

	@Override
	protected void setLocation(final Shell shell2) {
		shell2.setLocation(PreferenceConverter.getPoint(UIPlugin.getDefault().getPreferenceStore(),
				UIPreferenceInitializer.DESKTOP_LOCATION));
	}

	@Override
	public void setBlockOnOpen(final boolean shouldBlock) {
		super.setBlockOnOpen(shouldBlock);
	}

	@Override
	protected void saveLocation(final Shell shell2) {
		PreferenceConverter.setValue(UIPlugin.getDefault().getPreferenceStore(),
				UIPreferenceInitializer.DESKTOP_LOCATION, shell2.getLocation());
	}

}
