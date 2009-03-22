/****************************************************************************
 * Copyright (c) 2005-2006 Jeremy Dowdall
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Jeremy Dowdall <aspencloud@users.sourceforge.net> - initial API and implementation
 *****************************************************************************/

package org.aspencloud.calypso.ui.workbench.views.calendar.actions;

import java.util.Collections;

import org.aspencloud.calypso.ui.calendar.factories.ModelFactory;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.Tool;
import org.eclipse.gef.tools.AbstractTool;
import org.eclipse.gef.tools.CreationTool;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

public class CreateAction extends Action {
	public static final String ID = "org.aspencloud.calypso.ui.views.Calendar.actions.ShowGridAction";

	private final GraphicalViewer[] viewers;

	public CreateAction(final GraphicalViewer[] viewers) {
		super("Create");
		this.viewers = viewers;
		setToolTipText("Toggle Creation Tool");
		setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(
				ISharedImages.IMG_OBJ_ELEMENT));
		setId(ID);

	}

	@Override
	public void run() {
		Tool tool = null;
		if (true) {
			tool = new CreationTool(new ModelFactory(ModelFactory.TASK_TEMPLATE)) {
				@Override
				protected void performCreation(final int button) {
					super.performCreation(button);
				}
			};
			tool.setProperties(Collections.singletonMap(AbstractTool.PROPERTY_UNLOAD_WHEN_FINISHED,
					Boolean.TRUE));
		}

		for (int i = 0; i < this.viewers.length; i++) {
			if (tool != null) {
				this.viewers[i].getEditDomain().setActiveTool(tool);
			} else {
				this.viewers[i].getEditDomain().loadDefaultTool();
			}
		}
	}
}
