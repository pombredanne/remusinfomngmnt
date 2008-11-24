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

package org.remus.infomngmnt.core.internal.extension;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;

import org.remus.infomngmnt.core.extension.AbstractRuleDefinition;
import org.remus.infomngmnt.core.extension.IRule;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class Rule implements IRule {




	public Rule(IConfigurationElement configurationElement,
			String createFactoryClass, String id, String name) {
		super();
		this.configurationElement = configurationElement;
		this.createFactoryClass = createFactoryClass;
		this.id = id;
		this.name = name;
	}

	private final IConfigurationElement configurationElement;

	private AbstractRuleDefinition conditionImplementation;

	private final String id;

	private final String name;


	private final String createFactoryClass;

	/**
	 * Returns the creation factory. Every info-type contributes
	 * a separate implementation of how-to create new information
	 * objects
	 * @return the factory which creates new info-objects.
	 */
	public AbstractRuleDefinition getRuleDefinition() {
		if (this.conditionImplementation == null) {
			try {
				this.conditionImplementation = (AbstractRuleDefinition) this.configurationElement.createExecutableExtension(this.createFactoryClass);
			} catch (final CoreException e) {
				//TODO Logging
			}
		}
		return this.conditionImplementation;
	}

	public String getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

}
