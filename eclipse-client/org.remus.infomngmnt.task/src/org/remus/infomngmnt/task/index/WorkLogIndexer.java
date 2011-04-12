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

package org.remus.infomngmnt.task.index;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.osgi.util.NLS;
import org.eclipse.remus.InformationUnit;
import org.eclipse.remus.core.model.InformationStructureRead;
import org.eclipse.remus.search.analyzer.ISecondaryAnalyzer;
import org.eclipse.remus.search.analyzer.ISecondaryIndex;
import org.eclipse.remus.search.analyzer.SecondaryIndex;

import org.remus.infomngmnt.task.TaskActivator;
import org.remus.infomngmnt.task.messages.Messages;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class WorkLogIndexer implements ISecondaryAnalyzer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.search.analyzer.ISecondaryAnalyzer#analyze(org.remus
	 * .infomngmnt.InformationUnit, java.lang.String)
	 */
	public ISecondaryIndex[] analyze(InformationUnit unit, String node) {
		InformationStructureRead read = InformationStructureRead.newSession(unit);
		EList<InformationUnit> dynamicList = read
				.getDynamicList(TaskActivator.NODE_NAME_WORKED_UNITS);
		List<ISecondaryIndex> returnValue = new ArrayList<ISecondaryIndex>();
		int i = 0;
		for (InformationUnit informationUnit : dynamicList) {
			InformationStructureRead workLogRead = InformationStructureRead.newSession(
					informationUnit, TaskActivator.INFO_TYPE_ID);
			String description = (String) workLogRead
					.getValueByNodeId(TaskActivator.NODE_NAME_WORKED_UNIT_DESCRIPTION);
			if (description != null && description.length() > 0) {
				returnValue.add(SecondaryIndex.CREATE(NLS.bind(Messages.WorkLogIndexer_WorkItem, i++), description,
						null));
			}
		}
		return returnValue.toArray(new ISecondaryIndex[returnValue.size()]);
	}

}
