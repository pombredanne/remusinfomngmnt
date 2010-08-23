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

package org.remus.infomngmnt.connector.twitter.tester;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.remus.InformationUnitListItem;
import org.eclipse.remus.SynchronizationState;
import org.eclipse.remus.ui.editors.InformationEditor;
import org.eclipse.ui.part.EditorPart;

import org.remus.infomngmnt.connector.twitter.TwitterActivator;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class CanTweetTester extends PropertyTester {

	public static final String PROPERTY_CAN_TWEET = "canTweet"; //$NON-NLS-1$

	/**
	 * 
	 */
	public CanTweetTester() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.expressions.IPropertyTester#test(java.lang.Object,
	 * java.lang.String, java.lang.Object[], java.lang.Object)
	 */
	public boolean test(final Object receiver, final String property, final Object[] args,
			final Object expectedValue) {
		if (PROPERTY_CAN_TWEET.equals(property)) {
			if (receiver instanceof EditorPart && receiver instanceof InformationEditor) {
				InformationUnitListItem adapter = (InformationUnitListItem) ((InformationEditor) receiver)
						.getPrimaryModel().getAdapter(InformationUnitListItem.class);
				if (adapter.getType().equals(TwitterActivator.INFOTYPE_ID)
						&& adapter.getSynchronizationMetaData() != null
						&& adapter.getSynchronizationMetaData().getSyncState() == SynchronizationState.IN_SYNC) {
					return true;
				}
			}
		}
		return false;
	}

}
