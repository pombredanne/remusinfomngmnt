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

package org.remus.infomngmnt.ui.structuredefinition;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import org.remus.infomngmnt.InformationStructureItem;
import org.remus.infomngmnt.InformationStructureType;
import org.remus.infomngmnt.common.ui.image.ResourceManager;
import org.remus.infomngmnt.ui.UIPlugin;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class StructureDefinitionLabelProvider extends LabelProvider {

	@Override
	public String getText(final Object element) {
		return ((InformationStructureItem) element).getId();

	}

	@Override
	public Image getImage(final Object element) {
		if (element instanceof InformationStructureItem) {
			InformationStructureItem item = (InformationStructureItem) element;
			InformationStructureType type = item.getType();
			switch (type) {
			case STRING:
				return ResourceManager.getPluginImage(UIPlugin.getDefault(),
						"icons/datatypes/TextValue.gif");
			case LONG:
				return ResourceManager.getPluginImage(UIPlugin.getDefault(),
						"icons/datatypes/IntegralValue.gif");
			case BOOLEAN:
				return ResourceManager.getPluginImage(UIPlugin.getDefault(),
						"icons/datatypes/BooleanValue.gif");
			case DOUBLE:
				return ResourceManager.getPluginImage(UIPlugin.getDefault(),
						"icons/datatypes/RealValue.gif");
			default:
				return ResourceManager.getPluginImage(UIPlugin.getDefault(),
						"icons/datatypes/GenericValue.gif");
			}
		}
		return super.getImage(element);
	}

}
