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

package org.remus.infomngmnt.core.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;

import org.remus.infomngmnt.AvailableRuleDefinitions;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.NewElementRules;
import org.remus.infomngmnt.common.core.streams.StreamUtil;
import org.remus.infomngmnt.core.services.IRuleService;
import org.remus.infomngmnt.provider.InfomngmntEditPlugin;
import org.remus.infomngmnt.util.EditingUtil;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class RuleUtil implements IRuleService {
	public static final String RULE_PATH = "ruleengine/creationrules.xml"; //$NON-NLS-1$

	private AvailableRuleDefinitions rules;

	public AvailableRuleDefinitions getElementRules() {
		if (this.rules == null) {
			IPath append = InfomngmntEditPlugin.getPlugin().getStateLocation().append(RULE_PATH);
			boolean createDefaultValues = !append.toFile().exists();
			if (createDefaultValues) {
				try {
					File file = append.toFile();
					file.getParentFile().mkdirs();
					file.createNewFile();
					InputStream openStream = FileLocator
							.openStream(InfomngmntEditPlugin.getPlugin().getBundle(), new Path(
									"initial/defaultruleset.xml"), false);
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
			this.rules = EditingUtil.getInstance().getObjectFromFileUri(
					URI.createFileURI(append.toOSString()),
					InfomngmntPackage.Literals.AVAILABLE_RULE_DEFINITIONS, null);
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

}
