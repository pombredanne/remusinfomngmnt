/*******************************************************************************
 * Copyright (c) 2008 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/

package org.remus.infomngmnt.common.core.extension;

import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IRegistryEventListener;
import org.eclipse.core.runtime.Platform;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 * @since 1.0
 */
public abstract class PluginRegistryDynamic implements IRegistryEventListener {

	private final String extensionId;

	public PluginRegistryDynamic(final String extensionId) {
		this.extensionId = extensionId;
		init();
		Platform.getExtensionRegistry().addListener(this);
	}

	public void added(final IExtension[] extensions) {
		// do nothing by default

	}

	public void added(final IExtensionPoint[] extensionPoints) {
		for (IExtensionPoint extensionPoint : extensionPoints) {
			if (extensionPoint.getUniqueIdentifier().equals(this.extensionId)) {
				init();
				break;
			}
		}
	}

	public void removed(final IExtension[] extensions) {
		// do nothing by default

	}

	public void removed(final IExtensionPoint[] extensionPoints) {
		for (IExtensionPoint extensionPoint : extensionPoints) {
			if (extensionPoint.getUniqueIdentifier().equals(this.extensionId)) {
				init();
				break;
			}
		}

	}

	protected abstract void init();
}
