/*******************************************************************************
 * Copyright (c) 2009 Tom Seidel, Remus Software
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

import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;

import org.remus.infomngmnt.core.path.Path2ObjectMapper;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class RulePostProcessor {

	private final EObject object2perform;
	private final Map<String, Object> values;

	public RulePostProcessor(final EObject object2perform, final Map<String, Object> values) {
		this.object2perform = object2perform;
		this.values = values;

	}

	public void process() {
		Set<String> keySet = this.values.keySet();
		for (String string : keySet) {
			new Path2ObjectMapper(string, this.object2perform, this.values.get(string))
					.getObjectForPath(true, true);
		}
	}
}
