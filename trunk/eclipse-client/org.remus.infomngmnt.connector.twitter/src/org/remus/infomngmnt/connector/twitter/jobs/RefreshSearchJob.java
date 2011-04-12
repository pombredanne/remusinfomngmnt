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
package org.remus.infomngmnt.connector.twitter.jobs;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.remus.SynchronizableObject;
import org.eclipse.remus.SynchronizationState;
import org.remus.infomngmnt.connector.twitter.TwitterActivator;
import org.remus.infomngmnt.connector.twitter.TwitterRepository;
import org.remus.infomngmnt.connector.twitter.preferences.TwitterPreferenceInitializer;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class RefreshSearchJob extends RefreshTwitterJob {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.connector.twitter.jobs.RefreshTwitterJob#isSatisfied
	 * (org.eclipse.emf.ecore.EObject)
	 */
	@Override
	protected boolean isTwitterElementSatisfied(EObject eObject) {
		return ((SynchronizableObject) eObject).getSynchronizationMetaData() != null
				&& ((SynchronizableObject) eObject)
						.getSynchronizationMetaData().getSyncState() == SynchronizationState.IN_SYNC
				&& ((SynchronizableObject) eObject)
						.getSynchronizationMetaData().getUrl()
						.endsWith("/" + TwitterRepository.ID_FRIENDS); //$NON-NLS-1$
	}

	@Override
	public int getInterval() {
		return TwitterActivator.getDefault().getPreferenceStore()
				.getInt(TwitterPreferenceInitializer.RELOAD_SEARCH_FEEDS);
	}

}
