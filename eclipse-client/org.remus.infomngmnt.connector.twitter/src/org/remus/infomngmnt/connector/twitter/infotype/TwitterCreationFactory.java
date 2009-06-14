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

package org.remus.infomngmnt.connector.twitter.infotype;

import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.connector.twitter.TwitterActivator;
import org.remus.infomngmnt.core.extension.AbstractCreationFactory;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class TwitterCreationFactory extends AbstractCreationFactory {

	@Override
	public InformationUnit createNewObject() {
		InformationUnit newObject = super.createNewObject();
		newObject.setType(TwitterActivator.INFOTYPE_ID);
		InformationUnit messages = InfomngmntFactory.eINSTANCE.createInformationUnit();
		messages.setType(TwitterActivator.MESSAGES_ID);
		newObject.getChildValues().add(messages);
		return newObject;
	}
}
