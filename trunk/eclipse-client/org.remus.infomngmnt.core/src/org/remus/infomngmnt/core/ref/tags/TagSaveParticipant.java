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
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.EList;

import org.remus.infomngmnt.AvailableTags;
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

	private void handleOldTags(final List<Tag> tagsByInfoUnitCopy, final InformationUnit unit) {
		for (Tag tag : tagsByInfoUnitCopy) {
			tag.getInfoUnits().remove(unit.getId());
			handleEmptyTag(tag);
		}
	}

	private void handleNewTags(final List<String> splitCopy, final InformationUnit unit) {
		for (String newTag : splitCopy) {
			// Look if tag exists
			Tag tagByName = getTagByName(newTag);
			if (tagByName == null) {
				tagByName = InfomngmntFactory.eINSTANCE.createTag();
				tagByName.setName(newTag);
				ApplicationModelPool.getInstance().getModel().getAvailableTags().getTags().add(
						tagByName);
			}
			tagByName.getInfoUnits().add(
					((InformationUnitListItem) unit.getAdapter(InformationUnitListItem.class))
							.getId());
		}

	}

	void handleEmptyTag(final Tag tag) {
		if (tag.getInfoUnits().size() == 0) {
			AvailableTags eContainer = (AvailableTags) tag.eContainer();
			if (eContainer != null) {
				eContainer.getTags().remove(tag);
			}
		}
	}

	private List<Tag> getTagsByInfoUnit(final InformationUnit unit) {
		List<Tag> returnValue = new ArrayList<Tag>();
		InformationUnitListItem adapter = (InformationUnitListItem) unit
				.getAdapter(InformationUnitListItem.class);
		EList<Tag> availableTags = ApplicationModelPool.getInstance().getModel().getAvailableTags()
				.getTags();
		for (Tag tag : availableTags) {
			if (tag.getInfoUnits().contains(adapter)) {
				returnValue.add(tag);
			}
		}
		return returnValue;
	}

	private Tag getTagByName(final String name) {
		EList<Tag> tags = ApplicationModelPool.getInstance().getModel().getAvailableTags()
				.getTags();
		for (Tag tag : tags) {
			if (tag.getName().equals(name)) {
				return tag;
			}
		}
		return null;
	}

	public void handleChanged(final InformationUnit oldValue, final InformationUnit newValue) {
		List<String> splitCopy = Collections.EMPTY_LIST;
		List<String> split = Collections.EMPTY_LIST;
		if (newValue.getKeywords() != null) {
			split = new ArrayList<String>(Arrays.asList(newValue.getKeywords().split("\\W+")));
			splitCopy = new ArrayList<String>(split);
		}
		List<Tag> tagsByInfoUnit = getTagsByInfoUnit(newValue);
		List<Tag> tagsByInfoUnitCopy = new ArrayList<Tag>(tagsByInfoUnit);
		for (Tag tag : tagsByInfoUnit) {
			if (split.contains(tag.getName())) {
				tagsByInfoUnitCopy.remove(tag);
				splitCopy.remove(tag.getName());
			}
		}
		handleNewTags(splitCopy, newValue);
		handleOldTags(tagsByInfoUnitCopy, newValue);
		for (Tag tag : tagsByInfoUnitCopy) {
			handleEmptyTag(tag);
		}

	}

	public void handleCreated(final InformationUnit newValue) {
		if (newValue.getKeywords() != null) {
			handleNewTags(Arrays.asList(newValue.getKeywords().split("\\W+")), newValue);
		}

	}

	public void handleDeleted(final String informationUnitId) {
		EList<Tag> tags = ApplicationModelPool.getInstance().getModel().getAvailableTags()
				.getTags();
		for (Tag tag : tags) {
			EList<String> infoUnits = tag.getInfoUnits();
			for (String string2 : infoUnits) {
				if (string2.equals(informationUnitId)) {
					tag.getInfoUnits().remove(string2);
					handleEmptyTag(tag);
				}
			}

		}
	}

}
