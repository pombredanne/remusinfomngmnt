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

package org.remus.infomngmnt.ui.desktop.trayaction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import org.remus.infomngmnt.common.core.util.ModelUtil;
import org.remus.infomngmnt.ui.desktop.extension.AbstractTrayPreferencePage;
import org.remus.infomngmnt.ui.desktop.extension.IToolbarContribution;
import org.remus.infomngmnt.ui.desktop.services.ILocalTrayActionService;
import org.remus.infomngmnt.ui.desktop.services.IToolbarContributionService;
import org.remus.uimodel.DesktopToolItem;
import org.remus.uimodel.UimodelFactory;
import org.remus.uimodel.UimodelPackage;
import org.remus.uimodel.provider.UimodelEditPlugin;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class TrayActionPreferences extends AbstractTrayPreferencePage {

	private CheckboxTableViewer viewer;
	private final ILocalTrayActionService localTrayService;
	private final IToolbarContributionService toolbarContribService;

	/**
	 * 
	 */
	public TrayActionPreferences() {
		this.localTrayService = UimodelEditPlugin.getPlugin().getServiceTracker().getService(
				ILocalTrayActionService.class);
		this.toolbarContribService = UimodelEditPlugin.getPlugin().getServiceTracker().getService(
				IToolbarContributionService.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.remus.infomngmnt.common.ui.extension.AbstractTrayPreferencePage#
	 * createControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createControl(final Composite parent) {
		this.viewer = CheckboxTableViewer.newCheckList(parent, SWT.NONE);
		this.viewer.setContentProvider(ArrayContentProvider.getInstance());
		this.viewer.setLabelProvider(new LabelProvider() {
			@Override
			public Image getImage(final Object element) {
				return ((IToolbarContribution) element).getImage();
			}

			@Override
			public String getText(final Object element) {
				return ((IToolbarContribution) element).getName();
			}
		});
		setControl(this.viewer.getControl());
	}

	@Override
	public void bindValuesToUi() {
		this.viewer.setInput(this.toolbarContribService.getAllContributions());
		final EList<DesktopToolItem> items = this.localTrayService.getItemCollection().getItems();
		final Map<String, ? extends EObject> buildMap = ModelUtil.buildMap(items,
				UimodelPackage.Literals.DESKTOP_TOOL_ITEM__ID);
		List<IToolbarContribution> checkedContribs = new ArrayList<IToolbarContribution>();
		for (DesktopToolItem item : items) {
			IToolbarContribution contribution = this.toolbarContribService.getContributionById(item
					.getId());
			if (contribution != null) {
				checkedContribs.add(contribution);
			}
		}
		this.viewer.setCheckedElements(checkedContribs.toArray());
		this.viewer.addCheckStateListener(new ICheckStateListener() {
			public void checkStateChanged(final CheckStateChangedEvent event) {
				IToolbarContribution element = (IToolbarContribution) event.getElement();
				if (event.getChecked()) {
					DesktopToolItem newItem = UimodelFactory.eINSTANCE.createDesktopToolItem();
					newItem.setId(element.getId());
					items.add(newItem);
				} else {
					items.remove(buildMap.get(element.getId()));
				}
			}
		});
	}

	@Override
	public void performApply() {
		try {
			this.localTrayService.save();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
