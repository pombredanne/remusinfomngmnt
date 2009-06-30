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

package org.remus.infomngmnt.common.ui.trayaction;

import org.eclipse.emf.common.util.EList;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import org.remus.infomngmnt.common.ui.extension.AbstractTraySection;
import org.remus.infomngmnt.common.ui.extension.IToolbarContribution;
import org.remus.infomngmnt.common.ui.service.CommonServices;
import org.remus.infomngmnt.common.ui.service.LocalTrayActionService;
import org.remus.infomngmt.common.ui.uimodel.DesktopToolItem;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class ActionTraySection extends AbstractTraySection {

	@Override
	public void createDetailsPart(final Composite parent) {
		GridLayout gridLayout = new GridLayout(1, false);
		gridLayout.marginTop = 0;
		gridLayout.marginWidth = 5;
		parent.setLayout(gridLayout);
		// parent.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));

		ToolBar tbm = new ToolBar(parent, SWT.VERTICAL | SWT.RIGHT);

		GridData gridData2 = new GridData(SWT.CENTER, SWT.FILL, true, true);
		gridData2.widthHint = SWT.DEFAULT;
		gridData2.heightHint = SWT.DEFAULT;
		gridData2.minimumWidth = 100;
		tbm.setLayoutData(gridData2);
		LocalTrayActionService service = CommonServices.getInstance().getLocalTrayActionService();
		EList<DesktopToolItem> items = service.getItemCollection().getItems();
		for (DesktopToolItem desktopToolItem : items) {
			IToolbarContribution contributionById = CommonServices.getInstance()
					.getToolbarContributionExtensionService().getContributionById(
							desktopToolItem.getId());
			if (contributionById != null) {
				ToolItem item = contributionById.getToolItemProvider().createToolItem(tbm);
				item.setText(contributionById.getName());
				item.setToolTipText(contributionById.getName());
				item.setImage(contributionById.getImage());

				// item.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				// true));
			}
		}
		tbm.pack();

		this.toolkit.adapt(tbm);

	}

}