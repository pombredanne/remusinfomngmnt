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

package org.remus.infomngmnt.core.services;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;

import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.Tag;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public interface ITagService {
	
	EList<Tag> getAllTags();
	
	void addTag(Tag tag);
	
	void removeTag(Tag tag);
	
	void addItems(Collection<InformationUnitListItem> infoUnits, Tag tag);
	
	void removeItems(Collection<InformationUnitListItem> infoUnits);
	
	InformationUnitListItem[] getItemsForTag(Tag tag);
	
	Tag[] getTagsByInformationUnit(InformationUnitListItem item);

}
