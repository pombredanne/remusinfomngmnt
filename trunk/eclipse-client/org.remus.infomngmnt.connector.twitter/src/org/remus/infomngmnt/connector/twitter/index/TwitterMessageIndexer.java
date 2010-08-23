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

package org.remus.infomngmnt.connector.twitter.index;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.remus.InformationUnit;
import org.eclipse.remus.core.model.InformationStructureRead;
import org.eclipse.remus.search.analyzer.ISecondaryAnalyzer;
import org.eclipse.remus.search.analyzer.ISecondaryIndex;
import org.eclipse.remus.search.analyzer.SecondaryIndex;

import org.remus.infomngmnt.connector.twitter.TwitterActivator;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class TwitterMessageIndexer implements ISecondaryAnalyzer {

	/**
	 * 
	 */
	public TwitterMessageIndexer() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.search.analyzer.ISecondaryAnalyzer#analyze(org.remus
	 * .infomngmnt.InformationUnit, java.lang.String)
	 */
	public ISecondaryIndex[] analyze(final InformationUnit unit, final String node) {
		InformationStructureRead read = InformationStructureRead.newSession(unit);
		EList<InformationUnit> dynamicList = read.getDynamicList(TwitterActivator.MESSAGES_ID);
		List<ISecondaryIndex> returnValue = new ArrayList<ISecondaryIndex>();
		for (InformationUnit informationUnit : dynamicList) {
			InformationStructureRead messageRead = InformationStructureRead.newSession(
					informationUnit, TwitterActivator.INFOTYPE_ID);
			returnValue.add(SecondaryIndex.CREATE((String) messageRead
					.getValueByNodeId(TwitterActivator.MESSAGE_USER_TYPE), (String) messageRead
					.getValueByNodeId(TwitterActivator.MESSAGE_CONTENT_TYPE), null));
		}
		return returnValue.toArray(new ISecondaryIndex[returnValue.size()]);
	}

}
