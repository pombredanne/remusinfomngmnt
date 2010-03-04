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

package org.remus.infomngmnt.ui.rules.processing;

import java.util.Map;
import java.util.Set;

import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.core.model.InformationStructureEdit;
import org.remus.infomngmnt.core.path.Path2ObjectMapper;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class RulePostProcessor {

	private final InformationUnit object2perform;
	private final Map<String, Object> values;
	private final Map<String, Object> fileMap;
	private final Map<Object, String> dynamicValues;
	private final Map<String, Object> valueMap;
	private final String lableString;
	private final String keyWordString;
	private final String descriptionString;

	public RulePostProcessor(final String categoryString, final String lableString,
			final String keyWordString, final String descriptionString,
			final Map<String, Object> featureMap, final Map<String, Object> fileMap,
			final Map<String, Object> valueMap, final Map<Object, String> dynamicValues,
			final InformationUnit createNewObject) {
		this.lableString = lableString;
		this.keyWordString = keyWordString;
		this.descriptionString = descriptionString;
		this.values = featureMap;
		this.fileMap = fileMap;
		this.valueMap = valueMap;
		this.dynamicValues = dynamicValues;
		this.object2perform = createNewObject;

	}

	public void process() {
		Set<String> keySet = this.values.keySet();
		for (String string : keySet) {
			new Path2ObjectMapper(string, this.object2perform, this.values.get(string))
					.getObjectForPath(true, true);
		}
		InformationStructureEdit edit = InformationStructureEdit.newSession(this.object2perform
				.getType());
		Set<String> keySet2 = this.valueMap.keySet();
		for (String string : keySet2) {
			edit.setValue(this.object2perform, string, this.valueMap.get(string));
		}
		Set<Object> keySet3 = this.dynamicValues.keySet();
		for (Object object : keySet3) {
			String string = this.dynamicValues.get(object);
			InformationUnit subType = edit.createSubType(string, object);
			edit.addDynamicNode(this.object2perform, subType, null);
		}
	}
}
