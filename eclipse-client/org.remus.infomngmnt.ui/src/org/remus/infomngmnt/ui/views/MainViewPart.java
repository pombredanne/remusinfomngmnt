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

package org.remus.infomngmnt.ui.views;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.ui.viewer.IViewerProvider;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.nebula.widgets.collapsiblebuttons.ButtonListenerAdapter;
import org.eclipse.nebula.widgets.collapsiblebuttons.CollapsibleButtons;
import org.eclipse.nebula.widgets.collapsiblebuttons.CustomButton;
import org.eclipse.nebula.widgets.collapsiblebuttons.IColorManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.ISetSelectionTarget;

import org.remus.infomngmnt.common.ui.view.AbstractScrolledTitledView;
import org.remus.infomngmnt.core.model.EditingUtil;
import org.remus.infomngmnt.ui.UIPlugin;
import org.remus.infomngmnt.ui.extension.CollapsibleButtonBar;
import org.remus.infomngmnt.ui.service.ICollapsibleButtonExtensionService;
import org.remus.infomngmnt.util.ValueObject;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class MainViewPart extends AbstractScrolledTitledView implements ISetSelectionTarget, IEditingDomainProvider, ISelectionProvider, IViewerProvider{

	private static final String VISIBLE_BUTTONS = "visibleButtons";
	private static final String ACTIVE_BAR = "activeBar";
	private final Collection<CollapsibleButtonBar> items;
	private StackLayout stackLayout;
	private CollapsibleButtonBar activeButtonBar;
	private final List<String> renderedItems;
	private Composite upperComp;
	private CollapsibleButtons cb;
	private String activeBarId;
	private int visibleButtonCount;
	
	public static final String VIEW_ID = "org.remus.infomngmnt.ui.main"; //$NON-NLS-1$
	/**
	 * 
	 */
	public MainViewPart() {
		this.renderedItems = new ArrayList<String>();
		this.items = UIPlugin.getDefault().getService(ICollapsibleButtonExtensionService.class).getAllItems();
		this.visibleButtonCount = 2;
	}

	@Override
	public void createViewContents(final Composite parent) {
		
		GridLayout gridLayout = new GridLayout(1,false);
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		parent.setLayout(gridLayout);

		this.upperComp = new Composite(parent, SWT.NONE);
		this.upperComp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		this.upperComp.setLayout(this.stackLayout = new StackLayout());

		final ValueObject<Boolean> created = new ValueObject<Boolean>();
		created.setObject(Boolean.FALSE);
		final ValueObject<CustomButton> firstSelection = new ValueObject<CustomButton>();
		
		this.cb = new CollapsibleButtons(parent, SWT.NONE, IColorManager.SKIN_OFFICE_2007);
		int counter = 1;
		for (final CollapsibleButtonBar element : this.items) {
			CustomButton button = this.cb.addButton(element.getTitle(), element.getTooltip(), element.getBigIcon(), element.getIcon());
			if (counter++ <= this.visibleButtonCount) {
			} else {
				this.cb.hideButton(button);
			}
			button.setData(element);
			if (element.getId().equals(this.activeBarId)) {
				firstSelection.setObject(button);
			}
			
		}
		this.cb.addPaintListener(new PaintListener() {
			public void paintControl(final PaintEvent e) {
				if (!created.getObject()) {
					MainViewPart.this.cb.layout(true);
					created.setObject(true);
					if (firstSelection.getObject() != null) {
						MainViewPart.this.cb.selectItem(firstSelection.getObject());
					}
				}
				
			}
		});
		this.cb.addButtonListener(new ButtonListenerAdapter() {
			@Override
			public void buttonClicked(final CustomButton button, final MouseEvent e) {
				handleSelection((CollapsibleButtonBar) button.getData());
			}
		});
		//this.cb.setLayoutData(new GridData(GridData.GRAB_VERTICAL | GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_END));
		GridData gridData = new GridData(SWT.FILL, SWT.END, true, false);
		this.cb.setLayoutData(gridData);
//		parent.layout(true);
	}
	
	@Override
	public void dispose() {
		for (CollapsibleButtonBar item : this.items) {
			item.dispose();
		}
		this.renderedItems.clear();
		super.dispose();
	}
	
	@Override
	public void init(final IViewSite site, final IMemento memento) throws PartInitException {
		super.init(site, memento);
		if (memento != null) {
			for (CollapsibleButtonBar item : this.items) {
				IMemento child = memento.getChild(item.getId());
				if (child == null) {
					child = memento.createChild(item.getId());
				}
				item.init(site,memento);
			}
			this.activeBarId = memento.getString(ACTIVE_BAR);
			if (memento.getInteger(VISIBLE_BUTTONS) != null) {
				this.visibleButtonCount = memento.getInteger(VISIBLE_BUTTONS);
			}
		}
	}
	
	@Override
	public void saveState(final IMemento memento) {
		for (CollapsibleButtonBar item : this.items) {
			IMemento child = memento.getChild(item.getId());
			if (child == null) {
				child = memento.createChild(item.getId());
			}
			item.saveState(child);
		}
		memento.putString(ACTIVE_BAR, this.activeButtonBar.getId());
		memento.putInteger(VISIBLE_BUTTONS, this.cb.getNumVisibleButtons());
		super.saveState(memento);
	}


	protected void handleSelection(final CollapsibleButtonBar element) {
		if (element == this.activeButtonBar) {
			return;
		}
		if (!MainViewPart.this.renderedItems.contains(element.getId())) {
			element.createControl(this.upperComp);
			MainViewPart.this.renderedItems.add(element.getId());
		}
		if (this.activeButtonBar != null) {
			MainViewPart.this.activeButtonBar.handleDeselect();
		}
		getToolbarManager().removeAll();
		MainViewPart.this.stackLayout.topControl = element.getControl();
		MainViewPart.this.activeButtonBar = element;
		MainViewPart.this.activeButtonBar.handleSelect();
		this.activeButtonBar.initToolbar(getToolbarManager());
		setNewTitle(element.getTitle());
		this.upperComp.layout();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
	 */
	@Override
	public void setFocus() {
		if (this.activeButtonBar != null) {
			this.activeButtonBar.setFocus();

		}

	}
	private void initializeToolBar() {
		IToolBarManager toolBarManager = getViewSite().getActionBars().getToolBarManager();
	}

public void selectReveal(final ISelection selection) {
	if (this.activeButtonBar instanceof ISetSelectionTarget) {
		((ISetSelectionTarget) this.activeButtonBar).selectReveal(selection);
	}

}

	public EditingDomain getEditingDomain() {
		return EditingUtil.getInstance().getNavigationEditingDomain();
	}

	public void addSelectionChangedListener(final ISelectionChangedListener listener) {
		if (this.activeButtonBar instanceof ISelectionProvider) {
			((ISelectionProvider) this.activeButtonBar).addSelectionChangedListener(listener);
		}

	}

	public ISelection getSelection() {
		if (this.activeButtonBar instanceof ISelectionProvider) {
			return ((ISelectionProvider) this.activeButtonBar).getSelection();
		}
		return null;
	}

	public void removeSelectionChangedListener(
			final ISelectionChangedListener listener) {
		if (this.activeButtonBar instanceof ISelectionProvider) {
			((ISelectionProvider) this.activeButtonBar).removeSelectionChangedListener(listener);
		}

	}

	public void setSelection(final ISelection selection) {
		if (this.activeButtonBar instanceof ISelectionProvider) {
			((ISelectionProvider) this.activeButtonBar).setSelection(selection);
		}


	}

	public Viewer getViewer() {
		if (this.activeButtonBar instanceof IViewerProvider) {
			return ((IViewerProvider) this.activeButtonBar).getViewer();
		}
		return null;
	}



}
