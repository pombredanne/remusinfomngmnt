/*******************************************************************************
 * Copyright (c) 2008 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/

package org.remus.infomngmnt.ui.rules.service;

import java.util.List;
import java.util.Map;

import org.remus.infomngmnt.services.IExtensionService;
import org.remus.infomngmnt.ui.rules.transfer.TransferWrapper;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 * @since 1.0
 */
public interface IRuleExtensionService extends IExtensionService {

	/**
	 * @return
	 * @since 1.0
	 */
	Map<String, TransferWrapper> getAllTransferTypes();

	/**
	 * @param transferId
	 * @return
	 * @since 1.0
	 */
	TransferWrapper getTransferTypeById(String transferId);

	ICreationTrigger getCreationTriggerByTypeId(final String typId);

	List<String> getTransferIdsByTypeId(final String typeId);

	Map<String, List<String>> getTransfers();

}
