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

package org.remus.infomngmnt.ui.propertytester;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IProject;

import org.remus.infomngmnt.SynchronizableObject;
import org.remus.infomngmnt.SynchronizationMetadata;
import org.remus.infomngmnt.SynchronizationState;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class SynchronizableObjectItemTester extends PropertyTester {

	public static final String PROPERTY_REPLACECOMMIT = "canCommitOrReplace"; //$NON-NLS-1$
	public static final String PROPERTY_LOCALONLY = "canAdd"; //$NON-NLS-1$
	public static final String PROPERTY_IS_UNDER_SYNC = "isUnderSyncControl"; //$NON-NLS-1$
	public static final String PROPERTY_IGNORE = "canIgnore"; //$NON-NLS-1$

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.expressions.IPropertyTester#test(java.lang.Object,
	 * java.lang.String, java.lang.Object[], java.lang.Object)
	 */
	public boolean test(final Object receiver, final String property, final Object[] args,
			final Object expectedValue) {
		if (receiver instanceof SynchronizableObject) {
			// no sync for root categories.
			if (((SynchronizableObject) receiver).getAdapter(IProject.class) != null) {
				return false;
			}
			SynchronizationMetadata metaData = ((SynchronizableObject) receiver)
					.getSynchronizationMetaData();
			if (PROPERTY_REPLACECOMMIT.equals(property)) {
				return metaData != null
						&& metaData.getSyncState() != SynchronizationState.NOT_ADDED
						&& metaData.getSyncState() != SynchronizationState.IGNORED;
			}
			if (PROPERTY_LOCALONLY.equals(property)) {
				return metaData != null
						&& metaData.getSyncState() == SynchronizationState.NOT_ADDED
						&& metaData.getSyncState() != SynchronizationState.IGNORED;
			}
			if (PROPERTY_IS_UNDER_SYNC.equals(property)) {
				return metaData != null;
			}
			if (PROPERTY_IGNORE.equals(property)) {
				return metaData != null
						&& ((SynchronizableObject) receiver).eContainer() != null
						&& ((SynchronizableObject) ((SynchronizableObject) receiver).eContainer())
								.getSynchronizationMetaData() != null
						&& metaData.getSyncState() != SynchronizationState.IGNORED;
			}
		}
		return false;
	}

}