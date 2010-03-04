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

package org.remus.infomngmnt.ui.rules.service;

import org.remus.infomngmnt.model.service.IResourceLoader;
import org.remus.rules.AvailableRuleDefinitions;
import org.remus.rules.NewElementRules;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public interface IRuleService {

	AvailableRuleDefinitions getElementRules();

	NewElementRules getRuleByName(final String name);

	void setResourceLoader(IResourceLoader service);

	public static final String DEFAULT_RULENAME = "Default"; //$NON-NLS-1$

}
