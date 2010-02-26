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

package org.remus.infomngmnt.ui.rules;

import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.RuleValue;
import org.remus.infomngmnt.core.extension.TransferWrapper;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 * 
 * @since 1.0
 */
public interface ICreationTrigger {

	void setNewInformationUnit(final InformationUnit newInformationUnit);

	void setCategoryString(String categoryString);

	void setRuleValue(final RuleValue ruleValue);

	void setValue(final Object value);

	void setTransferType(TransferWrapper transferWrapper);

	void handleCreationRequest();

}
