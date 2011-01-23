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
package org.remus.infomngmnt.groovy.internal;

import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.util.GroovyScriptEngine;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IPath;
import org.eclipse.remus.rules.provider.RuleEditPlugin;
import org.eclipse.remus.ui.rules.execution.IGroovyEvaluation;
import org.eclipse.remus.ui.rules.execution.IGroovyEvaluationBinding;
import org.eclipse.remus.ui.rules.extension.IGroovyScript;
import org.eclipse.remus.ui.rules.processing.GroovyClassloader;

import org.apache.commons.io.FileUtils;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class GroovyEvaluation implements IGroovyEvaluation {

	private IGroovyEvaluationBinding binding;
	private ClassLoader classLoader;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.remus.ui.rules.execution.IGroovyEvaluation#setBinding(org
	 * .eclipse.remus.ui.rules.execution.IGroovyEvaluationBinding)
	 */
	public void setBinding(IGroovyEvaluationBinding binding) {
		this.binding = binding;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.remus.ui.rules.execution.IGroovyEvaluation#createBinding()
	 */
	public IGroovyEvaluationBinding createBinding() {
		return new GroovyEvaluationBinding();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.remus.ui.rules.execution.IGroovyEvaluation#evaluate(java.
	 * lang.String)
	 */
	public Object evaluate(String script) throws Exception {

		Map<String, ? extends Object> variables = binding.getVariables();
		Set<String> keySet = variables.keySet();
		IPath cpPath = RuleEditPlugin.getPlugin().getStateLocation()
				.append(IGroovyScript.CPFOLDERNAME);
		Object evaluate = null;
		String scriptName = "S" + System.currentTimeMillis() + ".groovy";
		File[] listFiles = cpPath.toFile().listFiles();
		File scriptFile = cpPath.append(scriptName).toFile();

		FileUtils.writeStringToFile(scriptFile, script);
		URL[] classPathUrls = new URL[listFiles.length + 1];
		for (int i = 0; i < listFiles.length; i++) {
			try {
				classPathUrls[i] = listFiles[i].toURI().toURL();
			} catch (MalformedURLException e) {
			}
		}
		try {
			classPathUrls[listFiles.length] = scriptFile.toURI().toURL();
		} catch (MalformedURLException e1) {

		}
		GroovyScriptEngine engine;
		if (classLoader == null) {
			engine = new GroovyScriptEngine(classPathUrls);
		} else {

			engine = new GroovyScriptEngine(classPathUrls, classLoader);
		}

		Binding binding = new Binding();
		for (String string : keySet) {
			binding.setVariable(string, variables.get(string));
		}
		evaluate = engine.run(scriptFile.toURI().toString(), binding);
		for (String string : keySet) {
			Object variable = binding.getVariable(string);
			this.binding.setVariable(string, variable);
		}
		scriptFile.deleteOnExit();

		return evaluate;
	}

	public void setClassLoader(ClassLoader classloader) {
		if (classloader instanceof org.eclipse.remus.ui.rules.processing.GroovyClassloader) {
			((GroovyClassloader) classloader)
					.addClassLoader(new GroovyClassLoader(Thread
							.currentThread().getContextClassLoader()));

		}
		classLoader = classloader;

	}

}
