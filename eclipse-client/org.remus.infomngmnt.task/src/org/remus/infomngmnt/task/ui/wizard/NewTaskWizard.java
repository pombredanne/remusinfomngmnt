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

package org.remus.infomngmnt.task.ui.wizard;

import org.remus.infomngmnt.common.ui.image.ResourceManager;
import org.remus.infomngmnt.task.TaskActivator;
import org.remus.infomngmnt.ui.newwizards.NewInfoObjectWizard;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class NewTaskWizard extends NewInfoObjectWizard {

	public NewTaskWizard() {
		super();
		setWindowTitle("New Task");
	}

	@Override
	protected String getInfoTypeId() {
		return TaskActivator.INFO_TYPE_ID;
	}

	@Override
	public void addPages() {
		super.addPages();
		this.page1.setTitle("New Task");
		this.page1.setMessage("This wizard enables you to create new tasks");
		this.page1.setImageDescriptor(ResourceManager.getPluginImageDescriptor(TaskActivator
				.getDefault(), "icons/iconexperience/task_wizard.png"));
	}

}
