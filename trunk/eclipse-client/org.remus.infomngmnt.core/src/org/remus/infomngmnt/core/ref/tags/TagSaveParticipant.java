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

package org.remus.infomngmnt.core.ref.tags;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.common.util.EList;

import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.Tag;
import org.remus.infomngmnt.core.extension.ISaveParticipant;
import org.remus.infomngmnt.core.model.ApplicationModelPool;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class TagSaveParticipant implements ISaveParticipant {

	/**
	 * 
	 */
	public TagSaveParticipant() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.remus.infomngmnt.core.extension.ISaveParticipant#handleEvent(int, org.remus.infomngmnt.InformationUnit)
	 */
	public void handleEvent(final int eventId, final InformationUnit unit) {
		
		switch (eventId) {
		case SAVED:
			handleSaved(unit);
			break;
		case BEFORE_DELETE:
			String[] split = unit.getKeywords().split("\\W+");
			List<Tag> tags2Delete = new ArrayList<Tag>();
			for (String string : split) {
				Tag tagByName = getTagByName(string);
				tags2Delete.add(tagByName);
			}
			handleOldTags(tags2Delete, unit);
		case CREATED:
			handleNewTags(Arrays.asList(unit.getKeywords().split("\\W+")), unit);
		default:
			break;
		}
	}
	
	
	private void handleSaved(final InformationUnit unit) {
		List<String> split = new ArrayList<String>(Arrays.asList(unit.getKeywords().split("\\W+")));
		List<String> splitCopy = new ArrayList<String>(split);
		List<Tag> tagsByInfoUnit = getTagsByInfoUnit(unit);
		List<Tag> tagsByInfoUnitCopy = new ArrayList<Tag>(tagsByInfoUnit);
		for (Tag tag : tagsByInfoUnit) {
			if (split.contains(tag.getName())) {
				tagsByInfoUnitCopy.remove(tag);
				splitCopy.remove(tag.getName());
			}
		}
		handleNewTags(splitCopy, unit);
		handleOldTags(tagsByInfoUnitCopy, unit);
	}
	
	private void handleOldTags(final List<Tag> tagsByInfoUnitCopy,
			final InformationUnit unit) {
		for (Tag tag : tagsByInfoUnitCopy) {
			tag.getInfoUnits().remove(unit.getAdapter(InformationUnitListItem.class));
		}
	}

	private void handleNewTags(final List<String> splitCopy, final InformationUnit unit) {
		for (String newTag : splitCopy) {
			// Look if tag exists
			Tag tagByName = getTagByName(newTag);
			if (tagByName == null) {
				tagByName = InfomngmntFactory.eINSTANCE.createTag();
				tagByName.setName(newTag);
				ApplicationModelPool.getInstance().getModel().getAvailableTags().getTags().add(tagByName);
			}
			tagByName.getInfoUnits().add((InformationUnitListItem) unit.getAdapter(InformationUnitListItem.class));
		}
		
	}

	private List<Tag> getTagsByInfoUnit(final InformationUnit unit) {
		List<Tag> returnValue = new ArrayList<Tag>();
		InformationUnitListItem adapter = (InformationUnitListItem) unit.getAdapter(InformationUnitListItem.class);
		EList<Tag> availableTags = ApplicationModelPool.getInstance().getModel().getAvailableTags().getTags();
		for (Tag tag : availableTags) {
			if (tag.getInfoUnits().contains(adapter)) {
				returnValue.add(tag);
			}
		}
		return returnValue;
	}
	
	private Tag getTagByName(final String name) {
		EList<Tag> tags = ApplicationModelPool.getInstance().getModel().getAvailableTags().getTags();
		for (Tag tag : tags) {
			if (tag.getName().equals(name)) {
				return tag;
			}
		}
		return null;
	}


}
