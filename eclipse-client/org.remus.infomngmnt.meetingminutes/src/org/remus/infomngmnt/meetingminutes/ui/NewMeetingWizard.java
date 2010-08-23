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

package org.remus.infomngmnt.meetingminutes.ui;

import org.eclipse.remus.common.ui.image.ResourceManager;
import org.eclipse.remus.ui.newwizards.NewInfoObjectWizard;

import org.remus.infomngmnt.meetingminutes.MeetingMinutesActivator;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class NewMeetingWizard extends NewInfoObjectWizard {

	public NewMeetingWizard() {
		super();
		setWindowTitle("New Appointment");
	}

	@Override
	protected String getInfoTypeId() {
		return MeetingMinutesActivator.INFO_TYPE_ID;
	}

	@Override
	public void addPages() {
		super.addPages();
		this.page1.setTitle("New Appointment");
		this.page1.setMessage("This wizard enables you to create a new appointment");
		this.page1.setImageDescriptor(ResourceManager.getPluginImageDescriptor(
				MeetingMinutesActivator.getDefault(), "icons/iconexperience/new_wizard.png"));
	}

}
