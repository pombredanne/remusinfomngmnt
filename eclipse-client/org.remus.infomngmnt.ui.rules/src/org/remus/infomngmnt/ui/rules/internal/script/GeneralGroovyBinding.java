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

package org.remus.infomngmnt.ui.rules.internal.script;

import groovy.lang.Binding;

import java.util.HashMap;
import java.util.Map;

import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.ui.rules.extension.IGroovyBinding;
import org.remus.infomngmnt.ui.rules.processing.RulePostProcessor;
import org.remus.infomngmnt.ui.rules.script.DefaultScriptConstants;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class GeneralGroovyBinding implements IGroovyBinding {

	private static final String DYNAMIC_VALUES = "dynamicValues";
	private static final String FILE_MAP = "fileMap";
	private static final String VALUE_MAP = "valueMap";
	private static final String FEATURE_MAP = "featureMap";
	private static final String DESCRIPTION = "description";
	private static final String KEYWORDS = "keywords";
	private static final String LABEL = "label";
	private static final String CATEGORY = "category";

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.remus.infomngmnt .ui.rules.extension. IGroovyBinding
	 * #afterEvaluation (groovy.lang.Binding)
	 */

	public Map afterEvaluation(final Binding binding, final InformationUnit createdObject) {
		Map<String, Object> featureMap = (Map<String, Object>) binding.getVariable(FEATURE_MAP);
		Map<String, Object> valueMap = (Map<String, Object>) binding.getVariable(VALUE_MAP);
		Map<String, Object> fileMap = (Map<String, Object>) binding.getVariable(FILE_MAP);
		Map<Object, String> dynamicValues = (Map<Object, String>) binding
				.getVariable(DYNAMIC_VALUES);
		RulePostProcessor postProcessing = new RulePostProcessor(binding.getVariable(CATEGORY)
				.toString(), binding.getVariable(LABEL).toString(), binding.getVariable(KEYWORDS)
				.toString(), binding.getVariable(DESCRIPTION).toString(), featureMap, fileMap,
				valueMap, dynamicValues, createdObject);
		postProcessing.process();

		Map returnValue = new HashMap();
		returnValue.put(DefaultScriptConstants.KEY_CATEGORIES, binding.getVariable(CATEGORY));
		returnValue.put(DefaultScriptConstants.KEY_FILES, binding.getVariable(FILE_MAP));

		return returnValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.ui.rules.extension.IGroovyBinding#beforeEvaluation
	 * (groovy.lang.Binding)
	 */
	public void beforeEvaluation(final Binding binding) {
		binding.setVariable(FEATURE_MAP, new HashMap<String, Object>());
		binding.setVariable(VALUE_MAP, new HashMap<String, Object>());
		binding.setVariable(FILE_MAP, new HashMap<String, Object>());
		binding.setVariable(DYNAMIC_VALUES, new HashMap<Object, String>());
		binding.setVariable(CATEGORY, "");
		binding.setVariable(LABEL, "");
		binding.setVariable(KEYWORDS, "");
		binding.setVariable(DESCRIPTION, "");
	}

}
