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

import org.eclipse.jface.resource.ImageDescriptor;

import org.remus.infomngmnt.RuleAction;
import org.remus.infomngmnt.common.ui.UIUtil;
import org.remus.infomngmnt.common.ui.quickaccess.QuickAccessElement;
import org.remus.infomngmnt.common.ui.quickaccess.QuickAccessProvider;
import org.remus.infomngmnt.core.extension.IInfoType;
import org.remus.infomngmnt.core.extension.InformationExtensionManager;
import org.remus.infomngmnt.ui.extension.AbstractCreationTrigger;
import org.remus.infomngmnt.ui.extension.UIExtensionManager;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class NewElementQuickAccessElement extends QuickAccessElement {

	private final RuleAction action;
	private final IInfoType infoTypeByType;
	private final Object value;

	public NewElementQuickAccessElement(QuickAccessProvider provider, RuleAction action, Object value) {
		super(provider);
		this.action = action;
		this.value = value;
		this.infoTypeByType = InformationExtensionManager.getInstance()
		.getInfoTypeByType(action.getInfoTypeId());
	}

	/* (non-Javadoc)
	 * @see org.remus.infomngmnt.common.ui.quickaccess.QuickAccessElement#execute()
	 */
	@Override
	public void execute() {
		final AbstractCreationTrigger trigger = UIExtensionManager.getInstance().getCreationTriggerByTypeId(this.infoTypeByType.getType());
		if (trigger != null) {
			trigger.setValue(this.value);
			trigger.setRuleValue(this.action.getRuleValue());
			UIUtil.getDisplay().asyncExec(new Runnable() {
				public void run() {
					trigger.handleCreationRequest();
				}
			});
		}

	}

	/* (non-Javadoc)
	 * @see org.remus.infomngmnt.common.ui.quickaccess.QuickAccessElement#getId()
	 */
	@Override
	public String getId() {
		return this.action.getInfoTypeId();
	}

	/* (non-Javadoc)
	 * @see org.remus.infomngmnt.common.ui.quickaccess.QuickAccessElement#getImageDescriptor()
	 */
	@Override
	public ImageDescriptor getImageDescriptor() {
		return this.infoTypeByType.getImageDescriptor();
	}

	/* (non-Javadoc)
	 * @see org.remus.infomngmnt.common.ui.quickaccess.QuickAccessElement#getLabel()
	 */
	@Override
	public String getLabel() {
		return this.infoTypeByType.getName();
	}

}
