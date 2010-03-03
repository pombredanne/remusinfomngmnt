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

package org.remus.infomngmnt.ui.rules.internal.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;

import org.remus.infomngmnt.common.core.extension.PluginRegistryDynamic;
import org.remus.infomngmnt.ui.rules.extension.IGroovyScript;
import org.remus.infomngmnt.ui.rules.extension.IMethodCategory;
import org.remus.infomngmnt.ui.rules.service.IGroovyScriptExtensionService;
import org.remus.rules.provider.RuleEditPlugin.Implementation;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class ScriptExtensionManager extends PluginRegistryDynamic implements
		IGroovyScriptExtensionService {

	public ScriptExtensionManager() {
		super(EXTENSION_POINT);
	}

	public static final String NODENAME_CATEGORY = "category"; //$NON-NLS-1$
	public static final String NODENAME_SCRIPT = "groovyScript"; //$NON-NLS-1$
	public static final String NODENAME_METHOD = "method"; //$NON-NLS-1$

	public static final String EXTENSION_POINT = Implementation.PLUGIN_ID + ".script"; //$NON-NLS-1$
	private static final String ID_ATT = "id";
	private static final String NAME_ATT = "name";
	private static final String STRING_ATT = "string";
	private static final String ATT_CATEGORY = "category";

	private Map<String, IMethodCategory> categories;

	private Map<String, IGroovyScript> scripts;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.common.core.extension.PluginRegistryDynamic#init()
	 */
	@Override
	protected void init() {
		this.categories = new HashMap<String, IMethodCategory>();
		this.scripts = new HashMap<String, IGroovyScript>();
		final IExtensionPoint extensionPoint = Platform.getExtensionRegistry().getExtensionPoint(
				EXTENSION_POINT);
		final IConfigurationElement[] configurationElements = extensionPoint
				.getConfigurationElements();
		for (final IConfigurationElement configurationElement : configurationElements) {
			if (configurationElement.getName().equals(NODENAME_CATEGORY)) {
				this.categories.put(configurationElement.getAttribute(ID_ATT), new MethodCategory(
						configurationElement.getAttribute(ID_ATT), configurationElement
								.getAttribute(NAME_ATT)));
			}
		}
		for (final IConfigurationElement configurationElement : configurationElements) {
			if (configurationElement.getName().equals(NODENAME_METHOD)) {
				String attribute = configurationElement.getAttribute(ATT_CATEGORY);
				IMethodCategory iMethodCategory = this.categories.get(attribute);
				if (iMethodCategory == null) {
					iMethodCategory = new MethodCategory(null, "Uncategorized");
					this.categories.put(null, iMethodCategory);
				}
				MethodDefinition methodDefinition = new MethodDefinition(configurationElement
						.getAttribute(ID_ATT), configurationElement.getAttribute(STRING_ATT), configurationElement.getAttribute(NAME_ATT),
						configurationElement, iMethodCategory);
				iMethodCategory.getDefinitions().add(methodDefinition);
			}
		}
		for (final IConfigurationElement configurationElement : configurationElements) {
			if (configurationElement.getName().equals(NODENAME_SCRIPT)) {
				String attribute = configurationElement.getAttribute(ID_ATT);
				this.scripts.put(attribute, new GroovyScript(attribute, configurationElement));
			}
		}

	}

	public IMethodCategory[] getAllScriptCategories() {
		Collection<IMethodCategory> values = this.categories.values();
		return values.toArray(new IMethodCategory[values.size()]);
	}

	public IGroovyScript[] getAllScripts() {
		Collection<IGroovyScript> values = this.scripts.values();
		return values.toArray(new IGroovyScript[values.size()]);
	}

}
