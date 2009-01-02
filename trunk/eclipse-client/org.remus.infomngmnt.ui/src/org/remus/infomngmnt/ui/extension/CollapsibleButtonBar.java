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

package org.remus.infomngmnt.ui.extension;

import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IViewSite;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public abstract class CollapsibleButtonBar {
	
	private String id;
	
	private String title;
	
	private String tooltip;
	
	private Image icon;
	
	private Image bigIcon;
	
	private Control control;
	
	private int order = Integer.MAX_VALUE;

	private IViewSite viewSite;
	
	public void createControl(final Composite parent) {
		// does nothing by default.
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public String getTooltip() {
		return this.tooltip;
	}

	public void setTooltip(final String tooltip) {
		this.tooltip = tooltip;
	}

	public Image getIcon() {
		return this.icon;
	}

	public void setIcon(final Image icon) {
		this.icon = icon;
	}

	public Image getBigIcon() {
		return this.bigIcon;
	}

	public void setBigIcon(final Image bigIcon) {
		this.bigIcon = bigIcon;
	}

	public Control getControl() {
		if (this.control == null) {
			throw new IllegalStateException("Control must be set.");
		}
		return this.control;
	}

	protected void setControl(final Control control) {
		this.control = control;
	}

	public String getId() {
		return this.id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public int getOrder() {
		return this.order;
	}

	public void setOrder(final int order) {
		this.order = order;
	}
	
	public void dispose() {
		// nothing by default
	}
	
	public void setFocus() {
		// does nothing by default.
	}

	public void handleDeselect() {
		// TODO Auto-generated method stub
		
	}

	public void handleSelect() {
		// TODO Auto-generated method stub
		
	}

	

	protected IViewSite getViewSite() {
		return this.viewSite;
	}

	public void saveState(final IMemento child) {
		// TODO Auto-generated method stub
		
	}

	public void init(final IViewSite site, final IMemento memento) {
		this.viewSite = site;
	}

	public void initToolbar(final IToolBarManager toolbarManager) {
		// does nothing by default
		
	}
	
	

}
