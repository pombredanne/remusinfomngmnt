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

package org.remus.infomngmnt.ui.tray;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.DropTargetListener;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.internal.WorkbenchImages;
import org.remus.infomngmnt.NewElementRules;
import org.remus.infomngmnt.RuleResult;
import org.remus.infomngmnt.common.ui.UIUtil;
import org.remus.infomngmnt.common.ui.extension.AbstractTraySection;
import org.remus.infomngmnt.common.ui.quickaccess.QuickAccessDialog;
import org.remus.infomngmnt.common.ui.quickaccess.QuickAccessProvider;
import org.remus.infomngmnt.core.extension.TransferWrapper;
import org.remus.infomngmnt.core.rules.RuleProcessor;
import org.remus.infomngmnt.core.services.IRuleExtensionService;
import org.remus.infomngmnt.core.services.IRuleService;
import org.remus.infomngmnt.provider.InfomngmntEditPlugin;
import org.remus.infomngmnt.ui.desktop.NewElementQuickAccessProider;
import org.remus.infomngmt.common.ui.uimodel.TraySection;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
@SuppressWarnings("restriction")
public class DropSection extends AbstractTraySection {

	private Clipboard clipboard;
	private NewElementRules ruleByName;


	@Override
	public void init(final FormToolkit pToolkit, final TraySection section) {
		super.init(pToolkit, section);
		String string = section.getPreferenceOptions().get(DropSectionPreferencePage.RULESET_KEY);
		this.ruleByName = InfomngmntEditPlugin.getPlugin().getService(IRuleService.class).getRuleByName(string);
	}

	@Override
	public void createDetailsPart(final Composite parent) {

		GridLayout gridLayout = new GridLayout(1,false);
		gridLayout.marginTop = 0;
		gridLayout.marginWidth = 0;
		parent.setLayout(gridLayout);
		int dropOperations = DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_LINK;
		//int dropOperations = DND.DROP_DEFAULT | DND.DROP_MOVE ;
		Composite dropComposite = this.toolkit.createComposite(parent, SWT.BORDER | SWT.BORDER_DOT | SWT.NO_FOCUS);
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData.widthHint = SWT.DEFAULT;
		gridData.heightHint = 40;
		dropComposite.setLayoutData(gridData);

		DropTarget dropTarget = new DropTarget(dropComposite, dropOperations);
		dropTarget.setTransfer(getTransferTypes());
		dropTarget.addDropListener(new DropTargetListener() {

			public void dragEnter(final DropTargetEvent event) {
				System.out.println("DRAG ENTER");
			}

			public void dragLeave(final DropTargetEvent event) {
				// TODO Auto-generated method stub

			}

			public void dragOperationChanged(final DropTargetEvent event) {
				// TODO Auto-generated method stub

			}

			public void dragOver(final DropTargetEvent event) {
				System.out.println("DRAG ENTER");

			}

			public void drop(final DropTargetEvent event) {
				event.widget.getDisplay().asyncExec(new Runnable() {
					public void run() {
						List<RuleResult> process = RuleProcessor.getInstance().process(
								event, DropSection.this.ruleByName);
						System.out.println(process.size());
						showQuickAccess(process);
					}


				});

			}

			public void dropAccept(final DropTargetEvent event) {
				// TODO Auto-generated method stub

			}

		});
		this.clipboard = new Clipboard(parent.getDisplay());
		ToolBar toolbar = new ToolBar(parent,SWT.RIGHT);
		GridData gridData2 = new GridData(SWT.CENTER, SWT.FILL, true, false);
		gridData2.widthHint = SWT.DEFAULT;
		gridData2.heightHint = SWT.DEFAULT;
		toolbar.setLayoutData(gridData2);
		this.toolkit.adapt(toolbar);
		ToolItem pasteItem = new ToolItem(toolbar, SWT.PUSH);
		pasteItem.setText("Paste from clipboard");
		pasteItem.setImage(WorkbenchImages.getImage(ISharedImages.IMG_TOOL_PASTE));
		pasteItem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				event.widget.getDisplay().asyncExec(new Runnable() {

					public void run() {
						List<RuleResult> process = RuleProcessor.getInstance().process(
								DropSection.this.clipboard, DropSection.this.ruleByName);
						showQuickAccess(process);
					}

				});
			}
		});

	}
	void showQuickAccess(final List<RuleResult> process) {
		if (process.size() > 0) {
			QuickAccessProvider[] provider = new NewElementQuickAccessProider[process.size()];
			for (int i = 0, n = process.size(); i < n; i++) {
				provider[i] = new NewElementQuickAccessProider(process.get(i));
			}
			new QuickAccessDialog(
					UIUtil.getPrimaryWindow(),
					null,provider).open();
		} else {
			// nothing
		}

	}

	private Transfer[] getTransferTypes() {
		List<Transfer> returnValue = new ArrayList<Transfer>();
		Collection<TransferWrapper> values = InfomngmntEditPlugin.getPlugin().getService(IRuleExtensionService.class).getAllTransferTypes().values();
		for (TransferWrapper transferWrapper : values) {
			returnValue.add(transferWrapper.getTransfer());
		}
		return returnValue.toArray(new Transfer[returnValue.size()]);
	}

	@Override
	public void dispose() {
		this.clipboard.dispose();
		super.dispose();
	}

}
