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

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;

import org.remus.infomngmnt.common.core.util.StringUtils;
import org.remus.infomngmnt.core.model.EditingUtil;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class RulePostProcessor {

	private class Path2ObjectMapper {

		public static final String ATTRIBUTE_ACCESSOR = "@"; //$NON-NLS-1$

		public static final String REGEXP_CHILD_ATTS = "^(.*)\\[\\@(.*)=\\'(.*)\\'\\]$"; //$NON-NLS-1$

		public static final String REGEXP_CHILD_INDEX_OF = "^(.*)\\((\\d)\\)$"; //$NON-NLS-1$

		String expression;

		Object value;

		EObject object;

		public Path2ObjectMapper(final String expression, final EObject object, final Object value) {
			super();
			this.expression = expression;
			this.object = object;
			this.value = value;
		}

		void manipulate() {
			EObject currentObjectInstance = this.object;
			Path path = new Path(this.expression);
			String[] segments = path.segments();
			for (String string : segments) {
				if (string.startsWith(ATTRIBUTE_ACCESSOR)) {
					EList<EAttribute> eAttributes = currentObjectInstance.eClass()
							.getEAllAttributes();
					for (EAttribute eAttribute : eAttributes) {
						if (string.equals(StringUtils
								.join(ATTRIBUTE_ACCESSOR, eAttribute.getName()))) {
							if (eAttribute.isChangeable()) {
								currentObjectInstance.eSet(eAttribute, this.value);
							}
							return;
						}
					}
				} else {
					Pattern childAttPattern = Pattern.compile(REGEXP_CHILD_ATTS, Pattern.MULTILINE
							| Pattern.DOTALL);
					Matcher matcher = childAttPattern.matcher(string);
					if (matcher.matches()) {
						/*
						 * Searching thorugh the expression. example:
						 * childValues[@type='SRC_TYPE'] the feature childValues
						 * will be searched if there is an object that has an
						 * attribute 'type' and this attribute has the value
						 * SRC_TYPE. If this object cannot be found, it will be
						 * created.
						 */

						String featureName = matcher.group(1);
						String attributeName = matcher.group(2);
						String attributeValue = matcher.group(3);

						EList<EStructuralFeature> eAllStructuralFeatures = currentObjectInstance
								.eClass().getEAllStructuralFeatures();
						/*
						 * We search for the feature of the current object.
						 */
						for (EStructuralFeature eStructuralFeature : eAllStructuralFeatures) {
							/*
							 * Feature was found
							 */
							if (featureName.equals(eStructuralFeature.getName())) {
								/*
								 * If the feature is a many reference we have to
								 * iterate through the list of children
								 */
								if (eStructuralFeature.isMany()) {
									EList children = (EList) currentObjectInstance
											.eGet(eStructuralFeature);
									EObject newChild = null;
									for (Object object : children) {
										if (object instanceof EObject) {
											/*
											 * Searching for the attribte in the
											 * child-list
											 */
											EList<EAttribute> eAllAttributes = ((EObject) object)
													.eClass().getEAllAttributes();
											boolean attributeFound = false;
											for (EAttribute eAttribute : eAllAttributes) {
												if (attributeName.equals(eAttribute.getName())
														&& attributeValue.equals(((EObject) object)
																.eGet(eAttribute))) {
													/*
													 * We have found the object
													 * which matches all
													 * criteria. We can proceed
													 * further
													 */
													attributeFound = true;
													currentObjectInstance = (EObject) object;
													break;
												}
											}
											if (!attributeFound) {
												/*
												 * Attribute was not found. We
												 * have to ask the editing
												 * domain and its itemprovider
												 * for possible children. The
												 */
												IEditingDomainItemProvider adapt = (IEditingDomainItemProvider) EditingUtil
														.getInstance().getAdapterFactory().adapt(
																currentObjectInstance,
																IEditingDomainItemProvider.class);
												if (adapt != null) {
													/*
													 * Getting the
													 * newChild-Descriptors from
													 * the editing domain.
													 */
													Collection<?> newChildDescriptors = adapt
															.getNewChildDescriptors(
																	currentObjectInstance,
																	EditingUtil
																			.getInstance()
																			.createNewEditingDomain(),
																	false);
													/*
													 * Iterating through the
													 * newChildDescriptors to
													 * get the corrent
													 * descriptor (feature must
													 * match)
													 */
													for (Object object2 : newChildDescriptors) {
														if (object2 instanceof CommandParameter
																&& eStructuralFeature == ((CommandParameter) object2)
																		.getEStructuralFeature()) {
															EObject eValue = ((CommandParameter) object2)
																	.getEValue();
															EList<EAttribute> eAllAttributesNew = eValue
																	.eClass().getEAllAttributes();
															for (EAttribute eAttribute : eAllAttributesNew) {
																if (attributeName.equals(eAttribute
																		.getName())
																		&& eAttribute
																				.isChangeable()) {
																	eValue.eSet(eAttribute,
																			attributeValue);
																	newChild = eValue;

																	currentObjectInstance = eValue;
																	break;
																}
															}
														}
														if (newChild != null) {
															break;
														}
													}
												}
											}
										}
									}
									if (newChild != null) {
										children.add(newChild);
									}
								}
								break;
							}
						}

					} else {
						Pattern childPositionPattern = Pattern.compile(REGEXP_CHILD_INDEX_OF,
								Pattern.MULTILINE | Pattern.DOTALL);
						matcher = childPositionPattern.matcher(string);
						if (matcher.matches()) {
							// later.
						}
					}
				}
			}
		}
	}

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
					.manipulate();
		}
	}
}
