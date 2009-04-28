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

package org.remus.infomngmnt.ui.desktop;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.internal.IWorkbenchGraphicConstants;
import org.eclipse.ui.internal.WorkbenchImages;

import org.remus.infomngmnt.RuleAction;
import org.remus.infomngmnt.RuleResult;
import org.remus.infomngmnt.common.ui.quickaccess.QuickAccessElement;
import org.remus.infomngmnt.common.ui.quickaccess.QuickAccessProvider;
import org.remus.infomngmnt.core.model.IdFactory;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class NewElementQuickAccessProider extends QuickAccessProvider {

	public NewElementQuickAccessProider(final RuleResult result) {
		super();
		this.result = result;
	}

	private Map<String, QuickAccessElement> action2idMap;

	public Map<String, QuickAccessElement> getAction2idMap() {
		if (this.action2idMap == null) {
			this.action2idMap = new HashMap<String, QuickAccessElement>();
			EList<RuleAction> actions = this.result.getActions();
			for (RuleAction ruleAction : actions) {
				this.action2idMap.put(IdFactory.createId(), new NewElementQuickAccessElement(this,
						ruleAction, this.result.getValue()));
			}
		}
		return this.action2idMap;
	}

	private final RuleResult result;

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.remus.infomngmnt.common.ui.quickaccess.QuickAccessProvider#
	 * getElementForId(java.lang.String)
	 */
	@Override
	public QuickAccessElement getElementForId(final String id) {
		return getAction2idMap().get(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.common.ui.quickaccess.QuickAccessProvider#getElements
	 * ()
	 */
	@Override
	public QuickAccessElement[] getElements() {
		return getAction2idMap().values().toArray(new QuickAccessElement[0]);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.common.ui.quickaccess.QuickAccessProvider#getId()
	 */
	@Override
	public String getId() {
		return "test";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.remus.infomngmnt.common.ui.quickaccess.QuickAccessProvider#
	 * getImageDescriptor()
	 */
	@Override
	public ImageDescriptor getImageDescriptor() {
		return WorkbenchImages.getImageDescriptor(IWorkbenchGraphicConstants.IMG_OBJ_NODE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.common.ui.quickaccess.QuickAccessProvider#getName()
	 */
	@Override
	public String getName() {
		return this.result.getDescription();
	}

}
