/*******************************************************************************
 * Copyright (c) 2010 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/

package org.eclipse.remus.application.internal;

import org.eclipse.remus.services.IPerspectiveService;
import org.eclipse.remus.ui.perspective.Perspective;


/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class PerspectiveService implements IPerspectiveService {

	public String getInformationPerspectiveId() {
		return Perspective.PERSPECTIVE_ID;
	}

}
