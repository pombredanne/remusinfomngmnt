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

package org.remus.infomngmnt.birtreport.internal.extension;

import java.util.ArrayList;
import java.util.List;

import org.remus.infomngmnt.birtreport.extension.IReportTemplate;
import org.remus.infomngmnt.birtreport.extension.ITemplateCategory;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class TemplateCategory implements ITemplateCategory {

	public TemplateCategory(final String id, final String label) {
		super();
		this.id = id;
		this.label = label;
	}

	private final String id;

	private final String label;

	private final List<IReportTemplate> templates = new ArrayList<IReportTemplate>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.remus.infomngmnt.birtreport.extension.ITemplateCategory#getId()
	 */
	public String getId() {
		return this.id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.birtreport.extension.ITemplateCategory#getLabel()
	 */
	public String getLabel() {
		return this.label;
	}

	public void addReport(final IReportTemplate template) {
		this.templates.add(template);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.birtreport.extension.ITemplateCategory#getTemplates
	 * ()
	 */
	public IReportTemplate[] getTemplates() {
		return this.templates.toArray(new IReportTemplate[this.templates.size()]);
	}

}
