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

package org.remus.infomngmnt.ui.rules.internal.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;

import org.remus.infomngmnt.common.core.streams.StreamUtil;
import org.remus.infomngmnt.model.service.IResourceLoader;
import org.remus.infomngmnt.ui.rules.service.IRuleService;
import org.remus.rules.AvailableRuleDefinitions;
import org.remus.rules.NewElementRules;
import org.remus.rules.RulesPackage;
import org.remus.rules.provider.RuleEditPlugin;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class RuleUtil implements IRuleService {
	public static final String RULE_PATH = "ruleengine/creationrules.xml"; //$NON-NLS-1$

	private volatile AvailableRuleDefinitions rules;

	private IResourceLoader loaderService;

	public AvailableRuleDefinitions getElementRules() {
		synchronized (this) {
			if (this.rules == null) {
				IPath append = RuleEditPlugin.getPlugin().getStateLocation().append(RULE_PATH);
				boolean createDefaultValues = !append.toFile().exists();
				if (createDefaultValues) {
					try {
						File file = append.toFile();
						file.getParentFile().mkdirs();
						file.createNewFile();
						InputStream openStream = FileLocator.openStream(RuleEditPlugin.getPlugin()
								.getBundle(), new Path("initial/defaultruleset.xml"), false);
						FileOutputStream fos = new FileOutputStream(file);
						StreamUtil.stream(openStream, fos);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				this.rules = this.loaderService.getObjectFromResourceUri(append.toOSString(),
						RulesPackage.Literals.AVAILABLE_RULE_DEFINITIONS, Collections.singletonMap(
								RulesPackage.eNS_URI, RulesPackage.eINSTANCE));
			}
		}
		return this.rules;
	}

	public NewElementRules getRuleByName(final String name) {
		EList<NewElementRules> newElementRules = getElementRules().getNewElementRules();
		for (NewElementRules newElementRules2 : newElementRules) {
			if (newElementRules2.getName().equals(name)) {
				return newElementRules2;
			}
		}
		return null;
	}

	public void setResourceLoader(final IResourceLoader service) {
		this.loaderService = service;

	}

}
