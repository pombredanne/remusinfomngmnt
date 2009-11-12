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

package org.remus.infomngmnt.birtreport.internal.extension;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;

import org.remus.infomngmnt.birtreport.extension.IReportTemplate;
import org.remus.infomngmnt.birtreport.extension.ITemplateCategory;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class ReportContentProvider extends ArrayContentProvider implements ITreeContentProvider {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.
	 * Object)
	 */
	public Object[] getChildren(final Object parentElement) {
		if (parentElement instanceof ITemplateCategory) {
			return ((ITemplateCategory) parentElement).getTemplates();
		}
		return new Object[0];
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object
	 * )
	 */
	public Object getParent(final Object element) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.
	 * Object)
	 */
	public boolean hasChildren(final Object element) {
		return !(element instanceof IReportTemplate);
	}

}
