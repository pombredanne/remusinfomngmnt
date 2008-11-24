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

package org.remus.infomngmnt.ui.rules;

import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.ui.dnd.EditingDomainViewerDropAdapter;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTargetEvent;

import org.remus.infomngmnt.Rule;
import org.remus.infomngmnt.RuleAction;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class RuleDropAdapter extends EditingDomainViewerDropAdapter{

	public RuleDropAdapter(EditingDomain domain, Viewer viewer) {
		super(domain, viewer);
	}

	@Override
	public void dragEnter(DropTargetEvent event) {
		super.dragEnter(event);
		// this prevents on a ui-level that a rule has child rules and a rule-action
		Object extractDropTarget = extractDropTarget(event.item);
		if (extractDropTarget instanceof Rule || extractDropTarget instanceof RuleAction) {
			if (((Rule) extractDropTarget).getAction() != null) {
				event.feedback = DND.DROP_NONE;
			}
		}

	}

}
