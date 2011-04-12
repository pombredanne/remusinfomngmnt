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
package org.remus.infomngmnt.plaintext.section;

import org.eclipse.core.runtime.Platform;
import org.eclipse.remus.InformationUnit;
import org.eclipse.remus.InformationUnitListItem;
import org.eclipse.remus.common.ui.UIUtil;
import org.eclipse.remus.core.model.InformationStructureRead;
import org.eclipse.remus.core.services.IApplicationModel;
import org.eclipse.remus.services.RemusServiceTracker;
import org.eclipse.remus.ui.desktop.extension.AbstractTraySection;
import org.eclipse.remus.ui.infotypes.service.IInformationTypeImage;
import org.eclipse.remus.uimodel.TraySection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.remus.infomngmnt.plaintext.Activator;
import org.remus.infomngmnt.plaintext.messages.Messages;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class InstantCopySection extends AbstractTraySection {

	private InformationUnitListItem itemById;
	private IInformationTypeImage imageService;

	/**
	 * 
	 */
	public InstantCopySection() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init(FormToolkit pToolkit, TraySection section) {
		super.init(pToolkit, section);
		RemusServiceTracker remusServiceTracker = new RemusServiceTracker(
				Platform.getBundle(Activator.PLUGIN_ID));
		IApplicationModel service = remusServiceTracker
				.getService(IApplicationModel.class);
		imageService = remusServiceTracker
				.getService(IInformationTypeImage.class);

		String string = section.getPreferenceOptions().get(
				InstantSectionPreferences.PREFERENCE_INFOUNIT_ID);
		itemById = service.getItemById(string, null);
		remusServiceTracker.ungetService(service);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.remus.ui.desktop.extension.AbstractTraySection#createDetailsPart
	 * (org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createDetailsPart(Composite parent) {
		GridLayout gridLayout = new GridLayout(1, false);
		gridLayout.marginTop = 0;
		gridLayout.marginWidth = 5;
		parent.setLayout(gridLayout);
		// parent.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));

		GridData gridData2 = new GridData(SWT.CENTER, SWT.FILL, true, true);
		gridData2.widthHint = SWT.DEFAULT;
		gridData2.heightHint = SWT.DEFAULT;
		gridData2.minimumWidth = 100;
		InformationUnit adapter = (InformationUnit) itemById
				.getAdapter(InformationUnit.class);
		if (itemById == null || adapter == null) {
			toolkit.createLabel(parent, Messages.InstantCopySection_InfoUnitNotFound);

		}
		ToolBar tbm = new ToolBar(parent, SWT.VERTICAL | SWT.RIGHT);
		tbm.setLayoutData(gridData2);
		ToolItem toolItem = new ToolItem(tbm, SWT.PUSH);
		toolItem.setText(itemById.getLabel());
		toolItem.setImage(imageService
				.getImageByInfoType(Activator.INFO_TYPE_ID));

		toolItem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				InformationStructureRead read = InformationStructureRead
						.newSession((InformationUnit) itemById
								.getAdapter(InformationUnit.class));
				new Clipboard(UIUtil.getDisplay()).setContents(
						new Object[] { read.getValueByNodeId("contents") }, //$NON-NLS-1$
						new Transfer[] { TextTransfer.getInstance() });
			}
		});
		tbm.pack();

		toolkit.adapt(tbm);
	}

	@Override
	public String getTitle() {
		return null;
	}

}
