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

import java.util.Collection;
import java.util.Map;

import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.URI;

import org.remus.infomngmnt.AvailableRuleDefinitions;
import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.NewElementRules;
import org.remus.infomngmnt.RemusTransferType;
import org.remus.infomngmnt.RuleAction;
import org.remus.infomngmnt.core.extension.IInfoType;
import org.remus.infomngmnt.core.extension.InformationExtensionManager;
import org.remus.infomngmnt.core.extension.RuleExtensionManager;
import org.remus.infomngmnt.core.extension.TransferWrapper;
import org.remus.infomngmnt.provider.InfomngmntEditPlugin;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class RuleUtil {

	private static RuleUtil INSTANCE;

	public static final String RULE_PATH = "ruleengine/creationrules.xml"; //$NON-NLS-1$

	private AvailableRuleDefinitions rules;

	public static RuleUtil getInstance() {
		if (RuleUtil.INSTANCE == null) {
			synchronized (RuleUtil.class) {
				if (RuleUtil.INSTANCE == null) {
					RuleUtil.INSTANCE = new RuleUtil();
				}
			}
		}
		return RuleUtil.INSTANCE;
	}

	public AvailableRuleDefinitions getElementRules() {
		if (this.rules == null) {
			IPath append = InfomngmntEditPlugin.getPlugin().getStateLocation().append(RULE_PATH);
			boolean createDefaultValues = !append.toFile().exists();
			this.rules = EditingUtil.getInstance().getObjectFromFileUri(
					URI.createFileURI(append.toOSString()), InfomngmntPackage.Literals.AVAILABLE_RULE_DEFINITIONS,false);
			if (createDefaultValues) {
				NewElementRules createNewElementRules = InfomngmntFactory.eINSTANCE.createNewElementRules();
				createNewElementRules.setName("Default Ruleset");
				createNewElementRules.setDeletable(false);
				this.rules.getNewElementRules().add(createNewElementRules);
				Map<String, TransferWrapper> allTransferTypes = RuleExtensionManager.getInstance().getAllTransferTypes();
				for (String transferWrapperIds : allTransferTypes.keySet()) {
					RemusTransferType createRemusTransferType = InfomngmntFactory.eINSTANCE.createRemusTransferType();
					createRemusTransferType.setId(transferWrapperIds);
					createRemusTransferType.setName(allTransferTypes.get(transferWrapperIds).getName());
					// Default behavior: add all valid types
					Collection<IInfoType> types = InformationExtensionManager.getInstance().getTypes();
					for (IInfoType infoType : types) {
						if (infoType.getValidTransferTypeIds().contains(transferWrapperIds)) {
							RuleAction createRuleAction = InfomngmntFactory.eINSTANCE.createRuleAction();
							createRuleAction.setInfoTypeId(infoType.getType());
							createRemusTransferType.getActions().add(createRuleAction);
						}
					}
					createNewElementRules.getTransferTypes().add(createRemusTransferType);
				}

				EditingUtil.getInstance().saveObjectToResource(this.rules);
			}
		}
		return this.rules;
	}

}
