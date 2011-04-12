/*******************************************************************************
 * Copyright (c) 2011 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/
package org.remus.infomngmnt.task.navigation;

import java.util.Date;

import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.remus.AbstractInformationUnit;
import org.eclipse.remus.InformationUnitListItem;
import org.eclipse.remus.common.ui.image.ResourceManager;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.remus.infomngmnt.task.TaskActivator;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class TaskStatusDecorator extends LabelProvider implements
		ILightweightLabelDecorator {

	private final TaskStateStore store;
	private final Color colorRed;
	private final Color colorGreen;

	public TaskStatusDecorator() {
		this.colorRed = new Color(null, new RGB(255, 0, 0));
		this.colorGreen = new Color(null, new RGB(0, 255, 0));
		this.store = TaskActivator.getDefault().getStore();
	}

	public void decorate(Object element, IDecoration decoration) {
		if (element instanceof InformationUnitListItem
				&& TaskActivator.INFO_TYPE_ID
						.equals(((InformationUnitListItem) element).getType())) {
			TaskDecorationObject decorationObjectByInfoUnit = this.store
					.getDecorationObjectByInfoUnit(((AbstractInformationUnit) element)
							.getId());
			if (decorationObjectByInfoUnit != null) {
				if (!decorationObjectByInfoUnit.isCompleted()
						&& decorationObjectByInfoUnit.getDueDate() != null
						&& decorationObjectByInfoUnit.getDueDate().before(
								new Date())) {
					decoration
							.addOverlay(ResourceManager.getPluginImageDescriptor(
									TaskActivator.getDefault(),
									"icons/iconexperience/decorations/due.png")); //$NON-NLS-1$
				} else if (decorationObjectByInfoUnit.isCompleted()) {
					decoration
							.addOverlay(ResourceManager.getPluginImageDescriptor(
									TaskActivator.getDefault(),
									"icons/iconexperience/decorations/check.png")); //$NON-NLS-1$

				}
			}
		}

	}

	@Override
	public void dispose() {
		this.colorRed.dispose();
		this.colorGreen.dispose();
		super.dispose();
	}

}
