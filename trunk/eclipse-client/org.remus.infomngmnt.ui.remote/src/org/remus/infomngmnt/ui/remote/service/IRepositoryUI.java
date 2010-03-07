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

package org.remus.infomngmnt.ui.remote.service;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

import org.remus.infomngmnt.ui.remote.IRepositoryActionContributor;
import org.remus.infomngmnt.ui.remote.NewRepositoryWizard;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public interface IRepositoryUI {

	String getId();

	String getRepositoryId();

	NewRepositoryWizard getWizardClass();

	IRepositoryActionContributor getActionContributor();

	Image getImage();

	ImageDescriptor getImageDescriptor();

}
