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
import org.remus.infomngmnt.common.ui.extension.AbstractTrayPreferencePage;
import org.remus.infomngmnt.common.ui.extension.IToolbarContribution;
import org.remus.infomngmnt.common.ui.service.CommonServices;
import org.remus.infomngmt.common.ui.uimodel.DesktopToolItem;
import org.remus.infomngmt.common.ui.uimodel.UIModelFactory;
import org.remus.infomngmt.common.ui.uimodel.UIModelPackage;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class TrayActionPreferences extends AbstractTrayPreferencePage {

	private CheckboxTableViewer viewer;

	/**
	 * 
	 */
	public TrayActionPreferences() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.remus.infomngmnt.common.ui.extension.AbstractTrayPreferencePage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createControl(Composite parent) {
		this.viewer = CheckboxTableViewer.newCheckList(parent, SWT.NONE);
		this.viewer.setContentProvider(new ArrayContentProvider());
		this.viewer.setLabelProvider(new LabelProvider() {
			@Override
			public Image getImage(Object element) {
				return ((IToolbarContribution) element).getImage();
			}
			@Override
			public String getText(Object element) {
				return ((IToolbarContribution) element).getName();
			}
		});
		setControl(this.viewer.getControl());
	}

	@Override
	public void bindValuesToUi() {
		this.viewer.setInput(CommonServices.getInstance().getToolbarContributionExtensionService().getAllContributions());
		final EList<DesktopToolItem> items = CommonServices.getInstance().getLocalTrayActionService().getItemCollection().getItems();
		final Map<String, ? extends EObject> buildMap = ModelUtil.buildMap(items, UIModelPackage.Literals.DESKTOP_TOOL_ITEM__ID);
		List<IToolbarContribution> checkedContribs = new ArrayList<IToolbarContribution>();
		for (DesktopToolItem item : items) {
			IToolbarContribution contribution = CommonServices.getInstance().getToolbarContributionExtensionService().getContributionById(item.getId());
			if (contribution != null) {
				checkedContribs.add(contribution);
			}
		}
		this.viewer.setCheckedElements(checkedContribs.toArray());
		this.viewer.addCheckStateListener(new ICheckStateListener() {
			public void checkStateChanged(CheckStateChangedEvent event) {
				IToolbarContribution element = (IToolbarContribution) event.getElement();
				if (event.getChecked()) {
					DesktopToolItem newItem = UIModelFactory.eINSTANCE.createDesktopToolItem();
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
		CommonServices.getInstance().getLocalTrayActionService().save();
	}




}
