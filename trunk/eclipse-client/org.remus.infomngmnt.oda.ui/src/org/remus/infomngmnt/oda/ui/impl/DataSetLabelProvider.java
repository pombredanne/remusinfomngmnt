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

package org.remus.infomngmnt.oda.ui.impl;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import org.remus.infomngmnt.InformationStructure;
import org.remus.infomngmnt.InformationStructureType;
import org.remus.infomngmnt.core.model.InformationStructureRead;
import org.remus.oda.Column;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class DataSetLabelProvider extends LabelProvider implements ITableLabelProvider {

	private String infoType;

	/**
	 * @param infoType
	 *            the infoType to set
	 */
	public final void setInfoType(final String infoType) {
		this.infoType = infoType;
	}

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
		String name = ((Column) element).getName();
		switch (columnIndex) {
		case 0:
			return name;
		case 1:
			if (name.startsWith(InformationStructureRead.ATTRIBUTE_ACCESSOR)) {
				return InformationStructureType.STRING.getLiteral();
			}
			InformationStructure structure = InformationStructureRead.getItemByNodeAndTypeId(name,
					this.infoType);
			if (structure != null) {
				return structure.getType().getLiteral();
			}
			break;
		}
		return null;
	}
}
