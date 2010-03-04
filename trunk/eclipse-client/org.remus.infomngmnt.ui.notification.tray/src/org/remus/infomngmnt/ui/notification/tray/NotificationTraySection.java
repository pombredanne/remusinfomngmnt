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

package org.remus.infomngmnt.ui.notification.tray;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import org.remus.infomngmnt.ui.desktop.extension.AbstractTraySection;
import org.remus.infomngmnt.ui.notification.NotificationTrimControl;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class NotificationTraySection extends AbstractTraySection {

	@Override
	public void createDetailsPart(final Composite parent) {
		parent.setLayout(new GridLayout(1, false));
		NotificationTrimControl notificationTrimControl = new NotificationTrimControl(parent,
				SWT.NONE);
		this.toolkit.adapt(notificationTrimControl);
	}

	@Override
	public String getTitle() {
		return null;
	}

}
