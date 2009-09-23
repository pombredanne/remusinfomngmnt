/*******************************************************************************
 * Copyright (c) 2009 Andreas Deinlein
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Andreas Deinlein - bibliographic extensions
 *******************************************************************************/
package org.remus.infomngmnt.bibliographic.extension;


/**
 * Concrete representation for article
 * 
 * @author Andreas Deinlein <dev@deasw.com>
 *
 */
public class ArticleRepresentation extends BibliographicRepresentation {

	public ArticleRepresentation() {
		transformationFile = "template/article.flt";
	}

}