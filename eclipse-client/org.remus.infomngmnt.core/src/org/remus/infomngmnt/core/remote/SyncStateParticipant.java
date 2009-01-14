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

package org.remus.infomngmnt.core.remote;

import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.SynchronizationState;
import org.remus.infomngmnt.core.extension.ISaveParticipant;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class SyncStateParticipant implements ISaveParticipant {

	public void handleEvent(final int eventId, final InformationUnit unit) {
		if (eventId == SAVED) {
			Object adapter = unit.getAdapter(InformationUnitListItem.class);
			if (adapter != null && ((InformationUnitListItem) adapter).getSynchronizationMetaData() != null) {
				((InformationUnitListItem) adapter).getSynchronizationMetaData()
					.setSyncState(SynchronizationState.LOCAL_EDITED);
			}
		}
		
	}

}
