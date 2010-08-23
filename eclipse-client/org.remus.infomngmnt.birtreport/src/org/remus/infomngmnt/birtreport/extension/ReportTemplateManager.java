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

package org.remus.infomngmnt.birtreport.extension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.remus.common.core.extension.PluginRegistryDynamic;

import org.remus.infomngmnt.birtreport.ReportActivator;
import org.remus.infomngmnt.birtreport.internal.extension.ReportTemplate;
import org.remus.infomngmnt.birtreport.internal.extension.TemplateCategory;
import org.remus.infomngmnt.birtreport.internal.extension.TemplateParameter;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class ReportTemplateManager extends PluginRegistryDynamic {

	public static final String EXTENSION_POINT = ReportActivator.PLUGIN_ID + ".reporttemplate"; //$NON-NLS-1$

	public static final String NODE_NAME_TEMPLATE = "template"; //$NON-NLS-1$

	public static final String ID_ATT = "id"; //$NON-NLS-1$

	public static final String LABEL_ATT = "label"; //$NON-NLS-1$

	public static final String RPTFILE_ATT = "rptfile"; //$NON-NLS-1$

	public static final String NODE_NAME_PARAMETER = "parameter"; //$NON-NLS-1$

	public static final String NAME_ATT = "name"; //$NON-NLS-1$

	public static final String PARAMETERTYPE_ATT = "parametertype"; //$NON-NLS-1$

	public static final String VALUE_ATT = "value"; //$NON-NLS-1$

	public static final String CATEGORY_ATT = "category"; //$NON-NLS-1$

	public static final String NODE_NAME_CATEGORY = "templatecategory"; //$NON-NLS-1$

	private static ReportTemplateManager INSTANCE;

	private List<IReportTemplate> templates;

	private List<ITemplateCategory> categories;

	private ITemplateCategory uncategorized;

	ReportTemplateManager() {
		super(EXTENSION_POINT);
	}

	public static ReportTemplateManager getInstance() {
		if (INSTANCE == null) {
			synchronized (ReportTemplateManager.class) {
				if (INSTANCE == null) {
					INSTANCE = new ReportTemplateManager();
				}
			}
		}
		return INSTANCE;
	}

	@Override
	protected void init() {
		this.templates = new ArrayList<IReportTemplate>();
		this.categories = new ArrayList<ITemplateCategory>();
		this.uncategorized = new TemplateCategory("", "Uncategorized");
		this.categories.add(this.uncategorized);

		final IExtensionPoint extensionPoint = Platform.getExtensionRegistry().getExtensionPoint(
				EXTENSION_POINT);
		final IConfigurationElement[] configurationElements = extensionPoint
				.getConfigurationElements();
		for (IConfigurationElement iConfigurationElement : configurationElements) {
			if (NODE_NAME_CATEGORY.equals(iConfigurationElement.getName())) {
				TemplateCategory category = new TemplateCategory(iConfigurationElement
						.getAttribute(ID_ATT), iConfigurationElement.getAttribute(LABEL_ATT));
				this.categories.add(category);
			}
		}
		for (IConfigurationElement iConfigurationElement : configurationElements) {
			if (NODE_NAME_TEMPLATE.equals(iConfigurationElement.getName())) {
				ReportTemplate reportTemplate = new ReportTemplate(iConfigurationElement
						.getAttribute(ID_ATT), iConfigurationElement.getAttribute(LABEL_ATT),
						iConfigurationElement.getAttribute(RPTFILE_ATT), iConfigurationElement
								.getContributor().getName(), iConfigurationElement
								.getAttribute(CATEGORY_ATT));
				IConfigurationElement[] children = iConfigurationElement.getChildren();
				ArrayList<ITemplateParameter> templateParameter = new ArrayList<ITemplateParameter>();
				for (IConfigurationElement iConfigurationElement2 : children) {
					if (NODE_NAME_PARAMETER.equals(iConfigurationElement2.getName())) {
						TemplateParameter parameter = new TemplateParameter(iConfigurationElement2
								.getAttribute(LABEL_ATT), iConfigurationElement2
								.getAttribute(NAME_ATT), iConfigurationElement2
								.getAttribute(PARAMETERTYPE_ATT));
						IConfigurationElement[] children2 = iConfigurationElement2.getChildren();
						Map<String, String> options = new HashMap<String, String>();
						for (IConfigurationElement iConfigurationElement3 : children2) {
							options.put(iConfigurationElement3.getAttribute(NAME_ATT),
									iConfigurationElement3.getAttribute(VALUE_ATT));
						}
						parameter.setParameterTypeParameters(options);
						templateParameter.add(parameter);
					}

				}
				reportTemplate.setTemplateParameters(templateParameter
						.toArray(new ITemplateParameter[templateParameter.size()]));
				this.templates.add(reportTemplate);
				getCategoryById(reportTemplate.getCategory()).addReport(reportTemplate);
			}
		}

	}

	private ITemplateCategory getCategoryById(final String id) {
		for (ITemplateCategory category : this.categories) {
			if (category.getId().equals(id)) {
				return category;
			}
		}
		return this.uncategorized;
	}

	public List<ITemplateCategory> getCategories() {
		checkForInitialization();
		return this.categories;
	}

	/**
	 * @return the templates
	 */
	public final List<IReportTemplate> getTemplates() {
		checkForInitialization();
		return this.templates;
	}

}
