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

package org.remus.infomngmnt.core.rules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TransferData;

import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.NewElementRules;
import org.remus.infomngmnt.RemusTransferType;
import org.remus.infomngmnt.RuleAction;
import org.remus.infomngmnt.RuleResult;
import org.remus.infomngmnt.core.extension.RuleExtensionManager;
import org.remus.infomngmnt.core.extension.TransferWrapper;
import org.remus.infomngmnt.core.model.EditingUtil;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class RuleProcessor {

	private static RuleProcessor INSTANCE;

	public static RuleProcessor getInstance() {
		if (RuleProcessor.INSTANCE == null) {
			synchronized (EditingUtil.class) {
				if (RuleProcessor.INSTANCE == null) {
					RuleProcessor.INSTANCE = new RuleProcessor();
				}
			}
		}
		return RuleProcessor.INSTANCE;
	}

	public List<RuleResult> process(DropTargetEvent event, NewElementRules rules) {
		List<RuleResult> returnValue = new ArrayList<RuleResult>();
		EList<RemusTransferType> transferTypes = rules.getTransferTypes();
		for (RemusTransferType remusTransferType : transferTypes) {
			TransferWrapper transferTypeById = RuleExtensionManager.getInstance().getTransferTypeById(remusTransferType.getId());
			if (transferTypeById != null
					&& transferTypeById.getTransfer().isSupportedType(event.currentDataType)
					&& remusTransferType.getActions().size() > 0) {
				EList<RuleAction> actions = remusTransferType.getActions();
				RuleResult ruleResult = InfomngmntFactory.eINSTANCE.createRuleResult();
				ruleResult.setValue(transferTypeById.nativeToJava(event.currentDataType));
				ruleResult.getActions().addAll(EcoreUtil.copyAll(actions));
				ruleResult.setDescription(transferTypeById.getName());
				returnValue.add(ruleResult);
			}
		}
		return returnValue;
	}

	public List<RuleResult> process(Clipboard clipboared, NewElementRules rules) {
		List<RuleResult> returnValue = new ArrayList<RuleResult>();
		EList<RemusTransferType> transferTypes = rules.getTransferTypes();
		List<TransferData> listOfAvailableTypes = Arrays.asList(clipboared.getAvailableTypes());
		for (RemusTransferType remusTransferType : transferTypes) {
			TransferWrapper transferTypeById = RuleExtensionManager.getInstance().getTransferTypeById(remusTransferType.getId());
			if (transferTypeById != null) {
				TransferData[] supportedTypes = transferTypeById.getTransfer().getSupportedTypes();
				for (TransferData transferData : supportedTypes) {
					if (supportsTransferData(listOfAvailableTypes,transferData)
							&& remusTransferType.getActions().size() > 0) {
						EList<RuleAction> actions = remusTransferType.getActions();
						RuleResult ruleResult = InfomngmntFactory.eINSTANCE.createRuleResult();
						ruleResult.setValue(clipboared.getContents(transferTypeById.getTransfer()));
						ruleResult.getActions().addAll(EcoreUtil.copyAll(actions));
						ruleResult.setDescription(transferTypeById.getName());
						returnValue.add(ruleResult);
						break;
					}
				}
			}
		}
		return returnValue;
	}

	private boolean supportsTransferData(List<TransferData> availableData, TransferData data) {
		for (TransferData transferData : availableData) {
			if (transferData.type == data.type) {
				return true;
			}
		}
		return false;
	}

}
