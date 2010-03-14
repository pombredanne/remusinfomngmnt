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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.common.core.zip.ZipUtil;
import org.remus.infomngmnt.ui.rules.extension.IGroovyBinding;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class FileBinding implements IGroovyBinding {

	public static final String ZIPFILES = "file2zip"; //$NON-NLS-1$

	/**
	 * 
	 */
	public FileBinding() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.ui.rules.extension.IGroovyBinding#afterEvaluation
	 * (groovy.lang.Binding, org.remus.infomngmnt.InformationUnit)
	 */
	public Map afterEvaluation(final Binding binding, final InformationUnit createdObject) {
		Map<String[], String> variable = (Map<String[], String>) binding.getVariable(ZIPFILES);
		Set<String[]> keySet = variable.keySet();
		for (String[] strings : keySet) {
			List<File> files = new ArrayList<File>();
			for (String string : strings) {
				File file = new File(string);
				if (file.exists() && file.canRead()) {
					files.add(file);
				}
			}
			String string = variable.get(strings);
			if (string != null && string.trim().length() > 0) {
				File target = new File(string);
				ZipUtil zipUtil = new ZipUtil(files.toArray(new File[files.size()]), target);
				try {
					zipUtil.createZipFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

		return Collections.EMPTY_MAP;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.ui.rules.extension.IGroovyBinding#beforeEvaluation
	 * (groovy.lang.Binding)
	 */
	public void beforeEvaluation(final Binding binding) {
		Map<String[], String> filesBinding = new HashMap<String[], String>();
		binding.setVariable(ZIPFILES, filesBinding);
	}

}
