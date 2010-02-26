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

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;

import org.remus.infomngmnt.birtreport.extension.IReportTemplate;
import org.remus.infomngmnt.birtreport.extension.ITemplateParameter;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class ReportTemplate implements IReportTemplate {

	public ReportTemplate(final String id, final String label, final String path,
			final String contributor, final String category) {
		super();
		this.id = id;
		this.label = label;
		this.path = path;
		this.contributor = contributor;
		this.category = category;
	}

	private final String id;

	private final String label;

	private final String path;

	private final String contributor;

	private final String category;

	private ITemplateParameter[] templateParameters;

	/**
	 * @return the category
	 */
	public final String getCategory() {
		return this.category;
	}

	/**
	 * @return the id
	 */
	public final String getId() {
		return this.id;
	}

	/**
	 * @return the label
	 */
	public final String getLabel() {
		return this.label;
	}

	/**
	 * @return the path
	 */
	public final String getPath() {
		return this.path;
	}

	/**
	 * @return the contributor
	 */
	public final String getContributor() {
		return this.contributor;
	}

	public InputStream createStream() throws IOException {
		return FileLocator.openStream(Platform.getBundle(this.contributor),
				new org.eclipse.core.runtime.Path(this.path), false);
	}

	public ITemplateParameter[] getParameter() {
		return this.templateParameters;
	}

	public void setTemplateParameters(final ITemplateParameter[] templateParameters) {
		this.templateParameters = templateParameters;
	}

}
