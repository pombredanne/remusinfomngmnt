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

package org.remus.infomngmnt.ui.rules.internal.service;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.InvalidRegistryObjectException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;

import org.remus.infomngmnt.common.core.streams.StreamCloser;
import org.remus.infomngmnt.common.core.streams.StreamUtil;
import org.remus.infomngmnt.ui.rules.extension.IMethodCategory;
import org.remus.infomngmnt.ui.rules.extension.IMethodDefinition;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class MethodDefinition implements IMethodDefinition {

	private final String id;
	private final IConfigurationElement element;
	private final String stringRepo;
	private String html;
	private final String name;
	private final IMethodCategory parentCategory;

	public MethodDefinition(final String id, final String stringRepo, final String name,
			final IConfigurationElement element, final IMethodCategory parentCategory) {
		this.id = id;
		this.stringRepo = stringRepo;
		this.name = name;
		this.element = element;
		this.parentCategory = parentCategory;

	}

	public String getHtml() {
		if (this.html == null && this.element.getAttribute("htmldoc") != null) {
			try {
				InputStream openStream = FileLocator.openStream(Platform.getBundle(this.element
						.getContributor().getName()),
						new Path(this.element.getAttribute("htmldoc")), false);
				this.html = StreamUtil.convertStreamToString(openStream);
				StreamCloser.closeStreams(openStream);
			} catch (InvalidRegistryObjectException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return this.html;
	}

	public String getId() {
		return this.id;
	}

	public IMethodCategory getParentCategory() {
		return this.parentCategory;
	}

	public String getString() {
		return this.stringRepo;
	}

	public String getName() {
		return this.name;
	}

}
