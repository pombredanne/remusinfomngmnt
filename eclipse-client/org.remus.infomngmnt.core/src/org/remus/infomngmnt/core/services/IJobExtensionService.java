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

package org.remus.infomngmnt.core.services;

import java.util.Collection;

import org.remus.infomngmnt.core.CorePlugin;
import org.remus.infomngmnt.core.jobs.AbstractJob;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public interface IJobExtensionService {

	public static final String EXTENSION_POINT_ID = CorePlugin.PLUGIN_ID + ".job"; //$NON-NLS-1$

	public static final String JOB_NODE_NAME = "job"; //$NON-NLS-1$

	public static final String ID_ATT = "id"; //$NON-NLS-1$

	public static final String NAME_ATT = "name"; //$NON-NLS-1$

	public static final String INTERVAL_ATT = "interval"; //$NON-NLS-1$

	public static final String CLASS_ATT = "class"; //$NON-NLS-1$

	public Collection<AbstractJob> getAllJobs();

	public AbstractJob getJobById(String id);

}
