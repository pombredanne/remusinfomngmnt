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

import groovy.lang.Binding;
import groovy.lang.GroovyShell;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TransferData;

import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.NewElementRules;
import org.remus.infomngmnt.RemusTransferType;
import org.remus.infomngmnt.RuleAction;
import org.remus.infomngmnt.RuleResult;
import org.remus.infomngmnt.common.core.streams.StreamCloser;
import org.remus.infomngmnt.common.core.streams.StreamUtil;
import org.remus.infomngmnt.common.core.util.StringUtils;
import org.remus.infomngmnt.core.CorePlugin;
import org.remus.infomngmnt.core.extension.TransferWrapper;
import org.remus.infomngmnt.core.model.InformationStructureEdit;
import org.remus.infomngmnt.core.services.IRuleExtensionService;
import org.remus.infomngmnt.provider.InfomngmntEditPlugin;
import org.remus.infomngmnt.util.EditingUtil;
import org.remus.infomngmnt.util.StatusCreator;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class RuleProcessor {

	private static final String DESCRIPTION = "description";
	private static final String KEYWORDS = "keywords";
	private static final String LABEL = "label";
	private static final String CATEGORY = "category";
	private static final String DYNAMIC_VALUES = "dynamicValues";
	private static final String FILE_MAP = "fileMap";
	private static final String VALUE_MAP = "valueMap";
	private static final String INPUT = "input";
	private static final String FEATURE_MAP = "featureMap";
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

	private String groovyStub;

	public List<RuleResult> process(final DropTargetEvent event, final NewElementRules rules) {
		List<RuleResult> returnValue = new ArrayList<RuleResult>();
		EList<RemusTransferType> transferTypes = rules.getTransferTypes();
		for (RemusTransferType remusTransferType : transferTypes) {
			TransferWrapper transferTypeById = InfomngmntEditPlugin.getPlugin().getService(
					IRuleExtensionService.class).getTransferTypeById(remusTransferType.getId());
			if (transferTypeById != null
					&& transferTypeById.getTransfer().isSupportedType(event.currentDataType)
					&& remusTransferType.getActions().size() > 0) {
				EList<RuleAction> actions = remusTransferType.getActions();
				RuleResult ruleResult = InfomngmntFactory.eINSTANCE.createRuleResult();
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
			TransferWrapper transferTypeById = InfomngmntEditPlugin.getPlugin().getService(
					IRuleExtensionService.class).getTransferTypeById(remusTransferType.getId());
			if (transferTypeById != null) {
				TransferData[] supportedTypes = transferTypeById.getTransfer().getSupportedTypes();
				for (TransferData transferData : supportedTypes) {
					if (supportsTransferData(listOfAvailableTypes, transferData)
							&& remusTransferType.getActions().size() > 0) {
						EList<RuleAction> actions = remusTransferType.getActions();
						RuleResult ruleResult = InfomngmntFactory.eINSTANCE.createRuleResult();
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
			binding.setVariable(FEATURE_MAP, new HashMap<String, Object>());
			binding.setVariable(VALUE_MAP, new HashMap<String, Object>());
			binding.setVariable(FILE_MAP, new HashMap<String, Object>());
			binding.setVariable(DYNAMIC_VALUES, new HashMap<Object, String>());
			binding.setVariable(CATEGORY, "");
			binding.setVariable(LABEL, "");
			binding.setVariable(KEYWORDS, "");
			binding.setVariable(DESCRIPTION, "");
			GroovyShell gse = new GroovyShell(binding);
			try {
				gse.evaluate(join);
				Map<String, Object> featureMap = (Map<String, Object>) binding
						.getVariable(FEATURE_MAP);
				Map<String, Object> valueMap = (Map<String, Object>) binding.getVariable(VALUE_MAP);
				Map<String, Object> fileMap = (Map<String, Object>) binding.getVariable(FILE_MAP);
				Map<Object, String> dynamicValues = (Map<Object, String>) binding
						.getVariable(DYNAMIC_VALUES);
				RulePostProcessor postProcessing = new RulePostProcessor(binding.getVariable(
						CATEGORY).toString(), binding.getVariable(LABEL).toString(), binding
						.getVariable(KEYWORDS).toString(), binding.getVariable(DESCRIPTION)
						.toString(), featureMap, fileMap, valueMap, dynamicValues, createNewObject);
				postProcessing.process();
				return new PostProcessingResult(binding.getVariable(CATEGORY).toString(),
						createNewObject, fileMap);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return new PostProcessingResult("", createNewObject, Collections.EMPTY_MAP);
	}

	private String getGroovyStub() {
		if (this.groovyStub == null) {
			InputStream resourceAsStream;
			try {
				resourceAsStream = FileLocator.openStream(Platform.getBundle(CorePlugin.PLUGIN_ID),
						new Path("script/groovyStub.groovy"), false);
				this.groovyStub = StreamUtil.convertStreamToString(resourceAsStream);
				StreamCloser.closeStreams(resourceAsStream);
			} catch (IOException e) {
				InfomngmntEditPlugin.getPlugin().getLog().log(
						StatusCreator.newStatus("Error reading groovyScript", e));
			}
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
