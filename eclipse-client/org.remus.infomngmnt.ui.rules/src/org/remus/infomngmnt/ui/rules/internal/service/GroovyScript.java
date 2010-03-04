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

import java.io.InputStream;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;

import org.remus.infomngmnt.common.core.streams.StreamCloser;
import org.remus.infomngmnt.common.core.streams.StreamUtil;
import org.remus.infomngmnt.ui.rules.extension.IGroovyBinding;
import org.remus.infomngmnt.ui.rules.extension.IGroovyScript;
import org.remus.infomngmnt.util.StatusCreator;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class GroovyScript implements IGroovyScript {

	private final String id;
	private final IConfigurationElement element;
	private String script;
	private IGroovyBinding bindingClass;

	public GroovyScript(final String id, final IConfigurationElement element) {
		this.id = id;
		this.element = element;

	}

	public String getScript() throws CoreException {
		if (this.script == null) {
			try {
				InputStream openStream = FileLocator.openStream(Platform.getBundle(this.element
						.getContributor().getName()), new org.eclipse.core.runtime.Path(
						this.element.getAttribute("source")), false);
				this.script = StreamUtil.convertStreamToString(openStream);
				StreamCloser.closeStreams(openStream);
			} catch (Throwable e) {
				throw new CoreException(StatusCreator.newStatus("Error loading script", e));
			}

		}
		return this.script;
	}

	public IGroovyBinding getBindingClass() throws CoreException {
		if (this.bindingClass == null) {
			try {
				this.bindingClass = (IGroovyBinding) this.element
						.createExecutableExtension("bindingclass");
			} catch (Throwable e) {
				throw new CoreException(StatusCreator.newStatus("Error loading binding class", e));
			}
		}
		return this.bindingClass;
	}

	public String getId() {
		return this.id;
	}

}
