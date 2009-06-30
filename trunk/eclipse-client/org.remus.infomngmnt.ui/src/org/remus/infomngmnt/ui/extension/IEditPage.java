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

package org.remus.infomngmnt.ui.extension;

import org.eclipse.jface.resource.ImageDescriptor;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public interface IEditPage {


	AbstractInformationFormPage getEditPage();

	String getType();

	ImageDescriptor getImage();

	String getId();

	String getLabel();

}