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
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.HTMLTransfer;
import org.eclipse.swt.dnd.ImageTransfer;
import org.eclipse.swt.dnd.RTFTransfer;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.dnd.URLTransfer;
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
import org.remus.infomngmnt.common.ui.extension.AbstractTraySection;
import org.remus.infomngmnt.core.extension.RuleExtensionManager;
import org.remus.infomngmnt.core.extension.TransferWrapper;
import org.remus.infomngmnt.core.model.RuleUtil;
import org.remus.infomngmt.common.ui.uimodel.TraySection;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
@SuppressWarnings("restriction")
public class DropSection extends AbstractTraySection {

	private Clipboard clipboard;
	private NewElementRules ruleByName;


	@Override
	public void init(FormToolkit toolkit, TraySection section) {
		super.init(toolkit, section);
		String string = section.getPreferenceOptions().get(DropSectionPreferencePage.RULESET_KEY);
		this.ruleByName = RuleUtil.getInstance().getRuleByName(string);
	}

	@Override
	public void createDetailsPart(Composite parent) {

		GridLayout gridLayout = new GridLayout(1,false);
		gridLayout.marginTop = 0;
		gridLayout.marginWidth = 0;
		parent.setLayout(gridLayout);
		int dropOperations = DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_LINK;
		Composite dropComposite = this.toolkit.createComposite(parent, SWT.BORDER | SWT.BORDER_DOT | SWT.NO_FOCUS);
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData.widthHint = SWT.DEFAULT;
		gridData.heightHint = 40;
		dropComposite.setLayoutData(gridData);

		DropTarget dropTarget = new DropTarget(dropComposite, dropOperations);
		dropTarget.setTransfer(getTransferTypes());
		dropTarget.addDropListener(new DropTargetListener() {

			public void dragEnter(DropTargetEvent event) {
				System.out.println("DRAG ENTER");


			}

			public void dragLeave(DropTargetEvent event) {
				// TODO Auto-generated method stub

			}

			public void dragOperationChanged(DropTargetEvent event) {
				// TODO Auto-generated method stub

			}

			public void dragOver(DropTargetEvent event) {
				System.out.println("DRAG ENTER");

			}

			public void drop(DropTargetEvent event) {
				if (TextTransfer.getInstance().isSupportedType(event.currentDataType)) {
					Object nativeToJava = TextTransfer.getInstance().nativeToJava(event.currentDataType);
					System.out.println("TEXT--> " + nativeToJava);
				}
				if (HTMLTransfer.getInstance().isSupportedType(event.currentDataType)) {
					Object nativeToJava = HTMLTransfer.getInstance().nativeToJava(event.currentDataType);
					System.out.println("HTML--> " + nativeToJava);
				}
				if (FileTransfer.getInstance().isSupportedType(event.currentDataType)) {
					Object nativeToJava = FileTransfer.getInstance().nativeToJava(event.currentDataType);
					System.out.println("FILE--> " + nativeToJava);
				}
				if (RTFTransfer.getInstance().isSupportedType(event.currentDataType)) {
					Object nativeToJava = RTFTransfer.getInstance().nativeToJava(event.currentDataType);
					System.out.println("RTF--> " + nativeToJava);
				}
				if (URLTransfer.getInstance().isSupportedType(event.currentDataType)) {
					Object nativeToJava = URLTransfer.getInstance().nativeToJava(event.currentDataType);
					System.out.println("URL--> " + nativeToJava);
				}
				if (ImageTransfer.getInstance().isSupportedType(event.currentDataType)) {
					Object nativeToJava = ImageTransfer.getInstance().nativeToJava(event.currentDataType);
					System.out.println("IMAGE--> " + nativeToJava);
				}

			}

			public void dropAccept(DropTargetEvent event) {
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
			public void handleEvent(Event event) {
				Object contents = DropSection.this.clipboard.getContents(TextTransfer.getInstance());
				System.out.println(contents);
			}

		});


	}

	private Transfer[] getTransferTypes() {
		List<Transfer> returnValue = new ArrayList<Transfer>();
		Collection<TransferWrapper> values = RuleExtensionManager.getInstance().getAllTransferTypes().values();
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
