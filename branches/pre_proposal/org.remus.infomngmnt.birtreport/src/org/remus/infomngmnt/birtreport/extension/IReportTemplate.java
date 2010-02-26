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

package org.remus.infomngmnt.birtreport.extension;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public interface IReportTemplate {

	String getId();

	String getLabel();

	String getCategory();

	InputStream createStream() throws IOException;

	ITemplateParameter[] getParameter();

}
