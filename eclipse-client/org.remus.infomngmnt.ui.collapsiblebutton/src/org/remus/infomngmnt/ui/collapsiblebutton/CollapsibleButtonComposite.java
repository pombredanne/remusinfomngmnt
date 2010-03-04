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

package org.remus.infomngmnt.ui.collapsiblebutton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.ListenerList;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.contexts.IContextActivation;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.part.ISetSelectionTarget;

import org.remus.infomngmnt.common.core.util.ValueObject;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class CollapsibleButtonComposite implements ISelectionProvider, IViewerProvider {

	private static final String VISIBLE_BUTTONS = "visibleButtons";

	private static final String ACTIVE_BAR = "activeBar";

	private List<CollapsibleButtonBar> items;

	private StackLayout stackLayout;

	private CollapsibleButtonBar activeButtonBar;

	private final List<String> renderedItems;

	private Composite upperComp;

	private CollapsibleButtons cb;

	private String activeBarId;

	private int visibleButtonCount;

	private final MainViewContextSwitcher contextSwitcher;

	private final ListenerList selectionListeners = new ListenerList(ListenerList.IDENTITY);

	private transient final List<IBarSelectionChangeListener> listenerList = new ArrayList<IBarSelectionChangeListener>();

	/**
	 * @author Tom Seidel <tom.seidel@remus-software.org>
	 */
	private class MainViewContextSwitcher {

		private IContextService contextService;

		private final Map<String, IContextActivation> contextActivationMap;

		public MainViewContextSwitcher() {
			this.contextActivationMap = new HashMap<String, IContextActivation>();
		}

		public void activate(final String contextId) {
			deactivate(contextId);
			putContext(contextId, getContextService().activateContext(contextId));
		}

		private void putContext(final String contextId, final IContextActivation activation) {
			this.contextActivationMap.put(contextId, activation);
		}

		private IContextActivation getContext(final String contextId) {
			return this.contextActivationMap.get(contextId);
		}

		public boolean isActivated(final String contextId) {
			return this.contextActivationMap.containsKey(contextId);
		}

		public void deactivate(final String contextId) {
			if (isActivated(contextId)) {
				getContextService().deactivateContext(getContext(contextId));
				this.contextActivationMap.remove(contextId);
			}
		}

		private IContextService getContextService() {
			if (this.contextService == null) {
				this.contextService = (IContextService) PlatformUI.getWorkbench().getService(
						IContextService.class);
			}
			return this.contextService;
		}

	}

	public static final String VIEW_ID = "org.remus.infomngmnt.ui.main"; //$NON-NLS-1$

	/**
	 * 
	 */
	public CollapsibleButtonComposite() {

		this.renderedItems = new ArrayList<String>();
		this.items = new ArrayList<CollapsibleButtonBar>();
		this.visibleButtonCount = 2;
		this.contextSwitcher = new MainViewContextSwitcher();

	}

	/**
	 * @wbp.parser.entryPoint
	 */
	public void createControl(final Composite parent, final int style) {

		this.upperComp = new Composite(parent, SWT.NONE);
		this.upperComp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		this.upperComp.setLayout(this.stackLayout = new StackLayout());

		final ValueObject<Boolean> created = new ValueObject<Boolean>();
		created.setObject(Boolean.FALSE);
		final ValueObject<CollapsibleButtonBar> firstSelection = new ValueObject<CollapsibleButtonBar>();

		this.cb = new CollapsibleButtons(parent, SWT.NONE, IColorManager.SKIN_OFFICE_2007);
		int counter = 1;
		for (final CollapsibleButtonBar element : this.items) {
			CustomButton button = this.cb.addButton(element.getTitle(), element.getTooltip(),
					element.getBigIcon(), element.getIcon());

			// element.createControl(this.upperComp);
			// MainViewPart.this.renderedItems.add(element.getId());

			if (counter++ <= this.visibleButtonCount) {
			} else {
				this.cb.hideButton(button);
			}
			button.setData(element);
			if (element.getId().equals(this.activeBarId)) {
				firstSelection.setObject(element);
			}

		}
		this.cb.addButtonListener(new ButtonListenerAdapter() {
			@Override
			public void buttonClicked(final CustomButton button, final MouseEvent e) {
				handleSelection((CollapsibleButtonBar) button.getData());
			}
		});
		// RIMCORE-83: selectItemAndLoad is only working if the toolbar was
		// rendered.
		this.cb.getToolbarComposite().addPaintListener(new PaintListener() {
			public void paintControl(final PaintEvent e) {
				if (!created.getObject()) {
					CollapsibleButtonComposite.this.cb.layout(true);
					created.setObject(true);
					if (firstSelection.getObject() != null) {
						int indexOf = CollapsibleButtonComposite.this.items.indexOf(firstSelection
								.getObject());
						CustomButton selection = (CustomButton) CollapsibleButtonComposite.this.cb
								.getItems().get(indexOf);
						if (selection != null) {
							try {
								CollapsibleButtonComposite.this.cb.selectItemAndLoad(selection);
							} catch (Exception e1) {
								// do nothing
							}
						}
					} else {
						CollapsibleButtonComposite.this.cb
								.selectItemAndLoad((CustomButton) CollapsibleButtonComposite.this.cb
										.getItems().get(0));
					}
					CollapsibleButtonComposite.this.cb.getToolbarComposite().removePaintListener(
							this);
				}

			}
		});

		// this.cb.setLayoutData(new GridData(GridData.GRAB_VERTICAL |
		// GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_END));
		GridData gridData = new GridData(SWT.FILL, SWT.END, true, false);
		this.cb.setLayoutData(gridData);

		// parent.layout(true);
	}

	public void dispose() {
		for (CollapsibleButtonBar item : this.items) {
			if (this.renderedItems.contains(item.getId())) {
				item.dispose();
			}
		}
		this.renderedItems.clear();

	}

	public void init(final IViewSite site, final IMemento memento) throws PartInitException {
		if (memento != null) {
			for (CollapsibleButtonBar item : this.items) {
				IMemento child = memento.getChild(item.getId());
				if (child == null) {
					child = memento.createChild(item.getId());
				}
				item.init(site, child);
			}
			this.activeBarId = memento.getString(ACTIVE_BAR);
			if (memento.getInteger(VISIBLE_BUTTONS) != null) {
				this.visibleButtonCount = memento.getInteger(VISIBLE_BUTTONS);
			}
		} else {
			for (CollapsibleButtonBar item : this.items) {
				item.init(site, null);
			}
		}
	}

	public void saveState(final IMemento memento) {
		for (CollapsibleButtonBar item : this.items) {
			if (this.renderedItems.contains(item.getId())) {
				IMemento child = memento.getChild(item.getId());
				if (child == null) {
					child = memento.createChild(item.getId());
				}
				item.saveState(child);
			}
		}
		if (this.activeButtonBar != null) {
			memento.putString(ACTIVE_BAR, this.activeButtonBar.getId());
		} else {
			memento.putString(ACTIVE_BAR, this.items.get(0).getId());
		}
		memento.putInteger(VISIBLE_BUTTONS, this.cb.getNumVisibleButtons());
	}

	protected void handleSelection(final CollapsibleButtonBar element) {
		if (element == this.activeButtonBar) {
			return;
		}
		if (this.activeButtonBar != null && this.activeButtonBar.getContextId() != null) {
			this.contextSwitcher.deactivate(this.activeButtonBar.getContextId());
		}
		if (element.getContextId() != null) {
			this.contextSwitcher.activate(element.getContextId());
		}
		if (this.activeButtonBar instanceof ISelectionProvider) {
			for (Object obj : this.selectionListeners.getListeners()) {
				((ISelectionProvider) this.activeButtonBar)
						.removeSelectionChangedListener((ISelectionChangedListener) obj);
			}
		}
		if (!CollapsibleButtonComposite.this.renderedItems.contains(element.getId())) {
			element.createControl(this.upperComp);
			CollapsibleButtonComposite.this.renderedItems.add(element.getId());
		}
		if (this.activeButtonBar != null) {
			CollapsibleButtonComposite.this.activeButtonBar.handleDeselect();
		}

		CollapsibleButtonComposite.this.stackLayout.topControl = element.getControl();
		CollapsibleButtonComposite.this.activeButtonBar = element;
		CollapsibleButtonComposite.this.activeButtonBar.handleSelect();
		this.activeButtonBar.setFocus();
		for (IBarSelectionChangeListener listener : this.listenerList) {
			listener.handleSelectionChanged(this.activeButtonBar);
		}
		if (this.activeButtonBar instanceof ISelectionProvider) {
			for (Object obj : this.selectionListeners.getListeners()) {
				((ISelectionProvider) this.activeButtonBar)
						.addSelectionChangedListener((ISelectionChangedListener) obj);
			}
		}

		fireSelectionChanged(this.selectionListeners, getSelection());
		this.upperComp.layout();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
	 */

	public boolean setFocus() {
		if (this.activeButtonBar != null) {
			return this.activeButtonBar.setFocus();
		}
		return false;

	}

	public void selectReveal(final ISelection selection) {
		if (this.activeButtonBar instanceof ISetSelectionTarget) {
			((ISetSelectionTarget) this.activeButtonBar).selectReveal(selection);
		}

	}

	public void addSelectionChangedListener(final ISelectionChangedListener listener) {

		this.selectionListeners.add(listener);

	}

	public ISelection getSelection() {
		System.out.println("GET SELECTION");
		if (this.activeButtonBar instanceof ISelectionProvider) {
			return ((ISelectionProvider) this.activeButtonBar).getSelection();
		}
		return StructuredSelection.EMPTY;

	}

	public void removeSelectionChangedListener(final ISelectionChangedListener listener) {
		this.listenerList.remove(this.selectionListeners);
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

	/**
	 * @param items
	 *            the items to set
	 */
	public final void setItems(final List<CollapsibleButtonBar> items) {
		this.items = items;
	}

	public void addBarSelectionChangeListener(final IBarSelectionChangeListener listener) {
		this.listenerList.add(listener);

	}

	public void removeBarSelectionChangeListener(final IBarSelectionChangeListener listener) {
		this.listenerList.remove(listener);

	}

	private void fireSelectionChanged(final ListenerList list, final ISelection selection) {
		SelectionChangedEvent event = new SelectionChangedEvent(this, selection);
		Object[] listeners = list.getListeners();
		for (int i = 0; i < listeners.length; i++) {
			ISelectionChangedListener listener = (ISelectionChangedListener) listeners[i];
			listener.selectionChanged(event);
		}
	}

}
