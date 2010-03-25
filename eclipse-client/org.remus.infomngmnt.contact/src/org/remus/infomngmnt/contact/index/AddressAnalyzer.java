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

package org.remus.infomngmnt.contact.index;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;

import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.common.core.util.KeyValueObject;
import org.remus.infomngmnt.contact.ContactActivator;
import org.remus.infomngmnt.contact.core.ContactUtil;
import org.remus.infomngmnt.core.model.InformationStructureRead;
import org.remus.infomngmnt.search.analyzer.ISecondaryAnalyzer;
import org.remus.infomngmnt.search.analyzer.ISecondaryIndex;
import org.remus.infomngmnt.search.analyzer.SecondaryIndex;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class AddressAnalyzer implements ISecondaryAnalyzer {

	private static Map<String, String> addressConverter;

	static {
		addressConverter = new HashMap<String, String>();
		KeyValueObject[] adressCollection = ContactUtil.getAdressCollection();
		for (KeyValueObject keyValueObject : adressCollection) {
			addressConverter.put(keyValueObject.getId(), keyValueObject.getValue());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.search.analyzer.ISecondaryAnalyzer#analyze(org.remus
	 * .infomngmnt.InformationUnit, java.lang.String)
	 */
	public ISecondaryIndex[] analyze(final InformationUnit unit, final String node) {
		InformationStructureRead read = InformationStructureRead.newSession(unit);
		EList<InformationUnit> dynamicList = read
				.getDynamicList(ContactActivator.NODE_NAME_ADDRESSES);
		List<ISecondaryIndex> returnValue = new ArrayList<ISecondaryIndex>();
		for (InformationUnit informationUnit : dynamicList) {
			InformationStructureRead adressRead = InformationStructureRead.newSession(
					informationUnit, ContactActivator.TYPE_ID);
			StringWriter sw = new StringWriter();
			appendToSearch(sw, adressRead
					.getValueByNodeId(ContactActivator.NODE_NAME_ADDRESS_REGION));
			appendToSearch(sw, adressRead
					.getValueByNodeId(ContactActivator.NODE_NAME_ADDRESS_LOCALITY));
			appendToSearch(sw, adressRead
					.getValueByNodeId(ContactActivator.NODE_NAME_ADDRESS_STREET));
			appendToSearch(sw, adressRead
					.getValueByNodeId(ContactActivator.NODE_NAME_ADDRESS_POSTAL));
			appendToSearch(sw, adressRead
					.getValueByNodeId(ContactActivator.NODE_NAME_ADDRESS_POST_OFFICE_BOX));
			returnValue.add(SecondaryIndex.CREATE(addressConverter.get(adressRead
					.getValueByNodeId(ContactActivator.NODE_NAME_ADDRESS)), sw.toString(), null));
		}
		return returnValue.toArray(new ISecondaryIndex[returnValue.size()]);
	}

	private void appendToSearch(final StringWriter sw, final Object object) {
		if (object != null && object.toString().trim().length() > 0) {
			sw.append(object.toString()).append(" ");
		}
	}
}
