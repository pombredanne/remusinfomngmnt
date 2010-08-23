/*******************************************************************************
 * Copyright (c) 2010 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/

package org.remus.infomngmnt.password.desktop;

import java.util.ArrayList;
import java.util.Set;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.remus.AbstractInformationUnit;
import org.eclipse.remus.InformationUnit;
import org.eclipse.remus.InformationUnitListItem;
import org.eclipse.remus.common.ui.swt.DisplayKeyPoster;
import org.eclipse.remus.common.ui.swt.PostEvent;
import org.eclipse.remus.core.model.InformationStructureRead;
import org.eclipse.remus.core.services.IApplicationModel;
import org.eclipse.remus.services.RemusServiceTracker;
import org.eclipse.remus.ui.desktop.extension.AbstractTraySection;
import org.eclipse.remus.util.InformationUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.forms.widgets.FormToolkit;

import org.remus.infomngmnt.password.PasswordPlugin;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class PasswordTraySection extends AbstractTraySection {

	private static final String BUNDLE_ID = "org.remus.infomngmnt.password.desktop";

	public static final String KEY_SELECTED_VALUE = "DESKTOP_SELECTED_VALUE"; //$NON-NLS-1$

	private ComboViewer combo;

	private RemusServiceTracker remusServiceTracker;

	private ToolItem toolItem1;

	private ToolItem toolItem2;

	private ToolItem toolItem3;

	private ToolItem toolItem4;

	@Override
	public void init(final FormToolkit pToolkit, final org.eclipse.remus.uimodel.TraySection section) {
		this.remusServiceTracker = new RemusServiceTracker(Platform
				.getBundle(PasswordPlugin.PLUGIN_ID));
		super.init(pToolkit, section);
	};

	@Override
	public void createDetailsPart(final Composite parent) {
		GridLayout gridLayout = new GridLayout(1, false);
		gridLayout.marginTop = 0;
		gridLayout.marginWidth = 0;
		parent.setLayout(gridLayout);

		this.combo = new ComboViewer(parent, SWT.READ_ONLY);
		Set<? extends EObject> allItemsByType = InformationUtil
				.getAllItemsByType(PasswordPlugin.PASSWORD_INFO_ID);
		this.combo.setContentProvider(ArrayContentProvider.getInstance());
		this.combo.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(final Object element) {
				return ((InformationUnitListItem) element).getLabel();
			}
		});
		this.combo.setInput(allItemsByType);
		this.combo.getCombo().setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		String string = PasswordPlugin.getDefault().getPreferenceStore().getString(
				KEY_SELECTED_VALUE);
		IApplicationModel service = this.remusServiceTracker.getService(IApplicationModel.class);
		InformationUnitListItem itemById = service.getItemById(string, null);
		if (itemById != null) {
			this.combo.setSelection(new StructuredSelection(itemById));
		}

		ToolBar toolbar = new ToolBar(parent, SWT.FLAT | SWT.RIGHT);
		GridData gridData2 = new GridData(SWT.CENTER, SWT.FILL, true, true);
		gridData2.widthHint = SWT.DEFAULT;
		gridData2.heightHint = SWT.DEFAULT;
		gridData2.minimumWidth = 100;

		toolbar.setLayoutData(gridData2);
		this.toolItem1 = new ToolItem(toolbar, SWT.PUSH);
		this.toolItem1.setToolTipText("Open Url in Browser");
		this.toolItem1.setImage(ResourceManager.getPluginImage(Platform.getBundle(
				PasswordPlugin.PLUGIN_ID).getBundleContext(),
				"icons/iconexperience/window_earth.png"));
		this.toolItem1.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				InformationUnit selection = getSelection();
				if (selection != null) {
					InformationStructureRead read = InformationStructureRead.newSession(selection);
					String valueByNodeId = (String) read.getValueByNodeId(PasswordPlugin.NODE_URL);
					Program.launch(valueByNodeId);
				}
			}
		});
		this.toolItem2 = new ToolItem(toolbar, SWT.PUSH);
		this.toolItem2.setToolTipText("Copy Username to Clipboard");
		this.toolItem2.setImage(ResourceManager.getPluginImage(Platform.getBundle(
				PasswordPlugin.PLUGIN_ID).getBundleContext(),
				"icons/iconexperience/copy_username.png"));
		this.toolItem2.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				InformationUnit selection = getSelection();
				if (selection != null) {
					InformationStructureRead read = InformationStructureRead.newSession(selection);
					String valueByNodeId = (String) read
							.getValueByNodeId(PasswordPlugin.NODE_USERNAME);
					Clipboard clipboard = new Clipboard(parent.getDisplay());
					clipboard.setContents(new String[] { valueByNodeId },
							new Transfer[] { TextTransfer.getInstance() });
				}
			}
		});
		this.toolItem3 = new ToolItem(toolbar, SWT.PUSH);
		this.toolItem3.setToolTipText("Copy Password to Clipboard");
		this.toolItem3.setImage(ResourceManager.getPluginImage(Platform.getBundle(
				PasswordPlugin.PLUGIN_ID).getBundleContext(),
				"icons/iconexperience/copy_password.png"));
		this.toolItem3.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				InformationUnit selection = getSelection();
				if (selection != null) {
					String valueByNodeId = selection.getStringValue();
					Clipboard clipboard = new Clipboard(parent.getDisplay());
					clipboard.setContents(new String[] { valueByNodeId },
							new Transfer[] { TextTransfer.getInstance() });
				}
			}
		});
		this.toolItem4 = new ToolItem(toolbar, SWT.PUSH);
		this.toolItem4.setToolTipText("Fill username und password");
		this.toolItem4.setImage(ResourceManager.getPluginImage(Platform.getBundle(
				PasswordPlugin.PLUGIN_ID).getBundleContext(), "icons/cog.png"));
		this.toolItem4.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				InformationUnit selection = getSelection();
				if (selection != null) {
					InformationStructureRead read = InformationStructureRead.newSession(selection);
					String username = (String) read.getValueByNodeId(PasswordPlugin.NODE_USERNAME);
					String password = selection.getStringValue();
					java.util.List<PostEvent> events = new ArrayList<PostEvent>();
					events.add(DisplayKeyPoster.ESCAPE(parent.getDisplay()));
					events.add(DisplayKeyPoster.STRING(parent.getDisplay(), username));
					events.add(DisplayKeyPoster.TAB(parent.getDisplay()));
					events.add(DisplayKeyPoster.STRING(parent.getDisplay(), password));
					events.add(DisplayKeyPoster.ENTER(parent.getDisplay()));
					DisplayKeyPoster.postEvents(events);
				}
			}
		});
		this.combo.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(final SelectionChangedEvent event) {
				checkEnablement();
			}
		});

		this.toolkit.adapt(this.combo.getCombo());
		toolbar.pack();
		checkEnablement();
		this.toolkit.adapt(toolbar);
		parent.getParent().layout();

	}

	private void checkEnablement() {
		this.toolItem1.setEnabled(!this.combo.getSelection().isEmpty());
		this.toolItem2.setEnabled(!this.combo.getSelection().isEmpty());
		this.toolItem3.setEnabled(!this.combo.getSelection().isEmpty());
		this.toolItem4.setEnabled(!this.combo.getSelection().isEmpty());

	}

	private InformationUnit getSelection() {
		if (!this.combo.getSelection().isEmpty()) {
			return (InformationUnit) ((IAdaptable) ((IStructuredSelection) this.combo
					.getSelection()).getFirstElement()).getAdapter(InformationUnit.class);
		}
		return null;
	}

	@Override
	public void dispose() {
		Object firstElement = ((IStructuredSelection) this.combo.getSelection()).getFirstElement();
		if (firstElement != null) {
			PasswordPlugin.getDefault().getPreferenceStore().setValue(KEY_SELECTED_VALUE,
					((AbstractInformationUnit) firstElement).getId());
		}
		super.dispose();
	}

}
