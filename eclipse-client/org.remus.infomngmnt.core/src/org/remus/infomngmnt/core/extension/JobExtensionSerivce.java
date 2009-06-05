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

package org.remus.infomngmnt.core.extension;

import java.util.Collection;
import java.util.HashMap;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;

import org.remus.infomngmnt.core.jobs.AbstractJob;
import org.remus.infomngmnt.core.services.IJobExtensionService;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class JobExtensionSerivce extends PluginRegistryDynamic implements IJobExtensionService {

	private HashMap<String, AbstractJob> jobs;

	public JobExtensionSerivce() {
		super(EXTENSION_POINT_ID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.remus.infomngmnt.core.extension.PluginRegistryDynamic#init()
	 */
	@Override
	protected void init() {
		this.jobs = new HashMap<String, AbstractJob>();
		final IExtensionPoint extensionPoint = Platform.getExtensionRegistry().getExtensionPoint(
				EXTENSION_POINT_ID);
		final IConfigurationElement[] configurationElements = extensionPoint
				.getConfigurationElements();
		for (final IConfigurationElement configurationElement : configurationElements) {
			if (configurationElement.getName().equals(JOB_NODE_NAME)) {
				try {
					AbstractJob job = (AbstractJob) configurationElement
							.createExecutableExtension(CLASS_ATT);
					job.setName(configurationElement.getAttribute(NAME_ATT));
					this.jobs.put(configurationElement.getAttribute(ID_ATT), job);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	public Collection<AbstractJob> getAllJobs() {
		return this.jobs.values();
	}

	public AbstractJob getJobById(final String id) {
		return this.jobs.get(id);
	}

}
