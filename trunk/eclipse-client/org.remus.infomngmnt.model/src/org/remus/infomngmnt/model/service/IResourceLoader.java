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

package org.remus.infomngmnt.model.service;

import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public interface IResourceLoader {

	public <T extends EObject> T getObjectFromPlatformUri(final String uri,
			final EClass objectClas, Map<String, ? extends EPackage> packageRegistry);

	public <T extends EObject> T getObjectFromResourceUri(final String uri,
			final EClass objectClas, Map<String, ? extends EPackage> packageRegistry);
}
