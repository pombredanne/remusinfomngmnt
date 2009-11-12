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

package org.remus.infomngmnt.birtreport.ui;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.birtreport.ReportActivator;
import org.remus.infomngmnt.core.model.InformationStructureRead;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
class ReportTableLabelProvider extends LabelProvider implements ITableLabelProvider {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang
	 * .Object, int)
	 */
	public Image getColumnImage(final Object element, final int columnIndex) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang
	 * .Object, int)
	 */
	public String getColumnText(final Object element, final int columnIndex) {
		InformationStructureRead read = InformationStructureRead.newSession(
				(InformationUnit) element, ReportActivator.INFOTYPE_ID);
		switch (columnIndex) {
		case 0:
			return (String) read.getValueByNodeId(ReportActivator.NODE_NAME_PARAM_NAME);

		case 1:
			return (String) read.getValueByNodeId(ReportActivator.NODE_NAME_PARAM_VALUE);
		default:
			break;
		}
		return null;
	}

}
