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

package org.remus.infomngmnt.ui.rules.processing;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TransferData;

import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.common.core.util.StringUtils;
import org.remus.infomngmnt.core.edit.EditingUtil;
import org.remus.infomngmnt.core.model.InformationStructureEdit;
import org.remus.infomngmnt.ui.rules.extension.IGroovyBinding;
import org.remus.infomngmnt.ui.rules.extension.IGroovyScript;
import org.remus.infomngmnt.ui.rules.service.IGroovyScriptExtensionService;
import org.remus.infomngmnt.ui.rules.service.IRuleExtensionService;
import org.remus.infomngmnt.ui.rules.transfer.TransferWrapper;
import org.remus.rules.NewElementRules;
import org.remus.rules.RemusTransferType;
import org.remus.rules.RuleAction;
import org.remus.rules.RuleResult;
import org.remus.rules.RulesFactory;
import org.remus.rules.provider.RuleEditPlugin;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class RuleProcessor {

	private static final String INPUT = "input";

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

	private final IGroovyScriptExtensionService scriptService;

	private RuleProcessor() {
		this.scriptService = RuleEditPlugin.getPlugin().getServiceTracker().getService(
				IGroovyScriptExtensionService.class);
	}

	private volatile String groovyStub;

	public List<RuleResult> process(final DropTargetEvent event, final NewElementRules rules) {
		List<RuleResult> returnValue = new ArrayList<RuleResult>();
		EList<RemusTransferType> transferTypes = rules.getTransferTypes();
		for (RemusTransferType remusTransferType : transferTypes) {
			TransferWrapper transferTypeById = RuleEditPlugin.getPlugin().getServiceTracker()
					.getService(IRuleExtensionService.class).getTransferTypeById(
							remusTransferType.getId());
			if (transferTypeById != null
					&& transferTypeById.getTransfer().isSupportedType(event.currentDataType)
					&& remusTransferType.getActions().size() > 0) {
				EList<RuleAction> actions = remusTransferType.getActions();
				RuleResult ruleResult = RulesFactory.eINSTANCE.createRuleResult();
				ruleResult.setValue(transferTypeById.nativeToJava(event.currentDataType));
				ruleResult.setTransferType(transferTypeById);
				List<RuleAction> performGroovyCheck = performGroovyCheck(ruleResult, actions);
				if (performGroovyCheck.size() > 0) {
					ruleResult.getActions().addAll(performGroovyCheck);
					ruleResult.setDescription(transferTypeById.getName());
					returnValue.add(ruleResult);
				}
			}
		}
		return returnValue;
	}

	private List<RuleAction> performGroovyCheck(final RuleResult ruleResult,
			final EList<RuleAction> actions) {
		List<RuleAction> returnValue = new ArrayList<RuleAction>();
		for (RuleAction ruleAction : actions) {
			try {
				String groovyMatcher = ruleAction.getGroovyMatcher();
				Binding binding = new Binding();
				binding.setVariable(INPUT, ruleResult.getValue());
				GroovyShell gse = new GroovyShell(binding);
				Object evaluate = gse.evaluate(groovyMatcher);
				if (evaluate instanceof Boolean && (Boolean) evaluate) {
					returnValue.add((RuleAction) EcoreUtil.copy(ruleAction));
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return returnValue;

	}

	public List<RuleResult> process(final Clipboard clipboared, final NewElementRules rules) {
		List<RuleResult> returnValue = new ArrayList<RuleResult>();
		EList<RemusTransferType> transferTypes = rules.getTransferTypes();
		List<TransferData> listOfAvailableTypes = Arrays.asList(clipboared.getAvailableTypes());
		for (RemusTransferType remusTransferType : transferTypes) {
			TransferWrapper transferTypeById = RuleEditPlugin.getPlugin().getServiceTracker()
					.getService(IRuleExtensionService.class).getTransferTypeById(
							remusTransferType.getId());
			if (transferTypeById != null) {
				TransferData[] supportedTypes = transferTypeById.getTransfer().getSupportedTypes();
				for (TransferData transferData : supportedTypes) {
					if (supportsTransferData(listOfAvailableTypes, transferData)
							&& remusTransferType.getActions().size() > 0) {
						EList<RuleAction> actions = remusTransferType.getActions();
						RuleResult ruleResult = RulesFactory.eINSTANCE.createRuleResult();
						ruleResult.setValue(clipboared.getContents(transferTypeById.getTransfer()));
						ruleResult.setTransferType(transferTypeById);
						List<RuleAction> performGroovyCheck = performGroovyCheck(ruleResult,
								actions);
						if (performGroovyCheck.size() > 0) {
							ruleResult.getActions().addAll(performGroovyCheck);
							ruleResult.setDescription(transferTypeById.getName());
							returnValue.add(ruleResult);
						}
						break;
					}
				}
			}
		}
		return returnValue;
	}

	@SuppressWarnings("unchecked")
	public PostProcessingResult postProcessing(final Object value, final RuleAction ruleAction) {
		String postProcessingInstructions = ruleAction.getPostProcessingInstructions();
		InformationStructureEdit session = InformationStructureEdit.newSession(ruleAction
				.getInfoTypeId());
		InformationUnit createNewObject = session.newInformationUnit();
		if (postProcessingInstructions != null) {
			String script = getGroovyStub();
			String join = StringUtils.join(script, "\n", postProcessingInstructions);
			Binding binding = new Binding();
			binding.setVariable(INPUT, value);
			IGroovyScript[] allScripts = this.scriptService.getAllScripts();
			for (IGroovyScript iGroovyScript : allScripts) {
				try {
					IGroovyBinding bindingClass = iGroovyScript.getBindingClass();
					bindingClass.beforeEvaluation(binding);
				} catch (CoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			GroovyShell gse = new GroovyShell(binding);
			Map options = new HashMap();
			try {
				gse.evaluate(join);
				for (IGroovyScript iGroovyScript : allScripts) {
					IGroovyBinding bindingClass = iGroovyScript.getBindingClass();
					options.putAll(bindingClass.afterEvaluation(binding, createNewObject));
				}
				return new PostProcessingResult(createNewObject, options);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return new PostProcessingResult(createNewObject, Collections.EMPTY_MAP);
	}

	private String getGroovyStub() {
		if (this.groovyStub == null) {
			StringWriter sw = new StringWriter();
			IGroovyScript[] allScripts = this.scriptService.getAllScripts();
			for (IGroovyScript iGroovyScript : allScripts) {
				try {
					sw.append(iGroovyScript.getScript()).append("\n");
				} catch (CoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			this.groovyStub = sw.toString();
		}
		return this.groovyStub;
	}

	private boolean supportsTransferData(final List<TransferData> availableData,
			final TransferData data) {
		for (TransferData transferData : availableData) {
			if (transferData.type == data.type) {
				return true;
			}
		}
		return false;
	}

}
