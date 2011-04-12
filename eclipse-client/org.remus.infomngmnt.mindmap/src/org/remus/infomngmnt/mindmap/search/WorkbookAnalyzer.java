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

package org.remus.infomngmnt.mindmap.search;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.remus.BinaryReference;
import org.eclipse.remus.InformationUnit;
import org.eclipse.remus.core.model.InformationStructureRead;
import org.eclipse.remus.search.analyzer.IAnalyzer;
import org.eclipse.remus.util.InformationUtil;
import org.xmind.core.ISheet;
import org.xmind.core.ITopic;
import org.xmind.core.IWorkbook;
import org.xmind.ui.internal.editor.WorkbookRef;
import org.xmind.ui.internal.editor.WorkbookRefManager;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class WorkbookAnalyzer implements IAnalyzer {

	/**
	 * 
	 */
	public WorkbookAnalyzer() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.search.analyzer.IAnalyzer#analyze(org.remus.infomngmnt
	 * .InformationUnit, java.lang.String)
	 */
	public String analyze(final InformationUnit unit, final String attribute) {
		WorkbookRef localworkbookRef = null;
		InformationStructureRead read = InformationStructureRead
				.newSession(unit);
		List<BinaryReference> binaryReferences = read.getBinaryReferences();
		try {
			if (binaryReferences.size() > 0) {
				IFile binaryReferenceToFile = InformationUtil
						.binaryReferenceToFile(binaryReferences.get(0), unit);
				WorkbookRefManager localmanager = WorkbookRefManager
						.getInstance();
				localworkbookRef = localmanager.addReferrer(
						binaryReferenceToFile, null);
				org.xmind.core.io.IStorage localstorage = localworkbookRef
						.createStorage();
				localworkbookRef.loadWorkbook(localstorage, null,
						new NullProgressMonitor());
			}
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (org.xmind.core.CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StringBuffer sb = new StringBuffer();
		if (localworkbookRef != null) {
			IWorkbook workbook = localworkbookRef.getWorkbook();
			List<ISheet> sheets = workbook.getSheets();
			for (ISheet iSheet : sheets) {
				ITopic rootTopic = iSheet.getRootTopic();
				sb.append(appendTopic(rootTopic));
			}
			localworkbookRef.dispose(true);
		}
		return sb.toString();
	}

	private StringBuffer appendTopic(final ITopic rootTopic) {
		StringBuffer returnValue = new StringBuffer();
		returnValue.append(rootTopic.getTitleText()).append(" "); //$NON-NLS-1$
		Set<String> labels = rootTopic.getLabels();
		for (String string : labels) {
			returnValue.append(string).append(" "); //$NON-NLS-1$
		}
		List<ITopic> allChildren = rootTopic.getAllChildren();
		for (ITopic iTopic : allChildren) {
			returnValue.append(appendTopic(iTopic));
		}
		return returnValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.remus.infomngmnt.search.analyzer.IAnalyzer#isStandardAnalyzer()
	 */
	public boolean isStandardAnalyzer() {
		return false;
	}

}
