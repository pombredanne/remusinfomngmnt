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

package org.remus.infomngmnt.ui.tray;

import java.util.Date;

import org.eclipse.nebula.widgets.cdatetime.CDT;
import org.eclipse.nebula.widgets.cdatetime.CDateTime;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import org.remus.infomngmnt.common.ui.extension.AbstractTraySection;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class ClockTray extends AbstractTraySection {

	private Thread thread;
	private int i;

	@Override
	public void createDetailsPart(final Composite parent) {
		GridLayout gridLayout = new GridLayout(1, false);
		gridLayout.marginTop = 0;
		gridLayout.marginWidth = 0;
		parent.setLayout(gridLayout);
		int style = CDT.READ_ONLY | CDT.CLOCK_12_HOUR | CDT.SIMPLE | CDT.TIME_MEDIUM | CDT.COMPACT;
		final CDateTime dateTime = new CDateTime(parent, style);
		//		
		this.toolkit.adapt(dateTime);

		//		
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		// gridData.widthHint = 150;
		gridData.heightHint = 130;

		dateTime.setLayoutData(gridData);
		// dateTime.layout();
		this.thread = new Thread() {
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (!parent.isDisposed()) {
						parent.getDisplay().asyncExec(new Runnable() {
							public void run() {
								dateTime.setSelection(new Date());
							}
						});
					}
				}

			};
		};
		this.thread.start();
	}

	@Override
	public void dispose() {
		super.dispose();
	}

}
