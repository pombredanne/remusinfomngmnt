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

package org.remus.infomngmnt.ui.notification;

import org.eclipse.swt.widgets.Composite;

import org.remus.infomngmnt.common.ui.image.ResourceManager;
import org.remus.infomngmnt.ui.widgets.ScalingHyperlink;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class NotificationLink extends ScalingHyperlink {

	public NotificationLink(final Composite parent, final int style) {
		super(parent, style);
		setForeground(ResourceManager.getColor(12, 81, 172));
		addMouseTrackListener(this.MOUSE_TRACK_LISTENER);
	}

}
