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

package org.remus.infomngmnt.ui.collapsiblebutton.service;

import java.util.List;

import org.remus.infomngmnt.ui.collapsiblebutton.CbActivator;
import org.remus.infomngmnt.ui.collapsiblebutton.CollapsibleButtonBar;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 * @since 1.0
 */
public interface ICollapsibleButtonExtensionService {

	public static final String EXTENSION_POINT = CbActivator.PLUGIN_ID + ".collapsibleButtonBar"; //$NON-NLS-1$

	public static final String ID_ATT = "id"; //$NON-NLS-1$

	public static final String CLASS_ATT = "class"; //$NON-NLS-1$

	public static final String NAME_ATT = "name"; //$NON-NLS-1$

	public static final String ICON_ATT = "icon"; //$NON-NLS-1$

	public static final String BIG_IMAGE_ATT = "bigImage"; //$NON-NLS-1$

	public static final String ORDER_ATT = "order"; //$NON-NLS-1$

	public static final String TOOLTIP_ATT = "tooltip"; //$NON-NLS-1$

	public static final String CONTEXT_ID_ATT = "contextId"; //$NON-NLS-1$

	List<CollapsibleButtonBar> getAllItems();

}
