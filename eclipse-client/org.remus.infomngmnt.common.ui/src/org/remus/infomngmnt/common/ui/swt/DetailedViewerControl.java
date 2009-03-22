/*******************************************************************************
 * Copyright (c) 2005, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Tom Seidel      - Generalization
 *******************************************************************************/
package org.remus.infomngmnt.common.ui.swt;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ToolBar;

import org.remus.infomngmnt.common.internal.ui.CompareViewerPane;
import org.remus.infomngmnt.common.internal.ui.Splitter;

/**
 * Control which is capable of displaying refactoring histories.
 * 
 * @since 3.2
 */
public abstract class DetailedViewerControl extends Composite {
	/** The pane */
	protected CompareViewerPane viewerPane = null;

	/** The viewer */
	protected StructuredViewer viewer = null;

	/** The viewers labelProvider */
	protected IBaseLabelProvider labelProvider = null;

	/** The viewers content-provider */
	protected IContentProvider contentProvider = null;

	/** The selection label, or <code>null</code> */
	private Label fSelectionLabel = null;

	/** The splitter control */
	private Splitter fSplitterControl = null;

	private String paneText = null;

	private int[] weight = new int[] { 65, 35 };

	private int orientation = SWT.VERTICAL;

	private ToolBarManager fToolBarManager;

	private Object viewerInput;

	/**
	 * Creates a new refactoring history control.
	 * 
	 * @param parent
	 *            the parent control
	 * @param configuration
	 *            the refactoring history control configuration to use
	 */
	public DetailedViewerControl(final Composite parent) {
		super(parent, SWT.NONE);
	}

	/**
	 * Creates the button bar at the bottom of the component.
	 * 
	 * @param parent
	 *            the parent composite
	 */
	protected void createBottomButtonBar(final Composite parent) {
		// Do nothing
	}

	/**
	 * {@inheritDoc}
	 */
	public void createControl() {
		GridLayout layout = new GridLayout(2, false);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		layout.horizontalSpacing = 0;
		layout.verticalSpacing = 0;
		setLayout(layout);
		this.fSplitterControl = new Splitter(this, SWT.VERTICAL);
		this.fSplitterControl.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		final Composite leftPane = new Composite(this.fSplitterControl, SWT.NONE);
		layout = new GridLayout();
		layout.marginWidth = 0;
		layout.marginHeight = 2;
		leftPane.setLayout(layout);
		this.viewerPane = new CompareViewerPane(leftPane, SWT.BORDER | SWT.FLAT);
		final CLabel label = new CLabel(this.viewerPane, SWT.NONE) {

			@Override
			public final Point computeSize(final int wHint, final int hHint, final boolean changed) {
				return super.computeSize(wHint, Math.max(24, hHint), changed);
			}
		};
		this.viewerPane.setTopLeft(label);

		this.viewerPane.setText(getPaneText());
		this.viewerPane.setLayoutData(new GridData(GridData.FILL_HORIZONTAL
				| GridData.FILL_VERTICAL));
		this.viewer = createViewer(this.viewerPane);
		this.viewer.getControl().setLayoutData(
				new GridData(GridData.FILL_HORIZONTAL | GridData.FILL_VERTICAL));
		this.viewer.setUseHashlookup(true);
		this.viewer.setContentProvider(getContentProvider());
		this.viewer.setLabelProvider(getLabelProvider());
		this.viewer.setInput(this.viewerInput);
		this.viewer.addSelectionChangedListener(new ISelectionChangedListener() {

			public final void selectionChanged(final SelectionChangedEvent event) {
				final ISelection selection = event.getSelection();
				if (selection instanceof IStructuredSelection) {
					handleSelectionChanged((IStructuredSelection) selection);
				}
			}
		});
		this.viewerPane.setContent(this.viewer.getControl());
		createToolBar(getToolBarManager());

		Composite rightButtonBar = new Composite(this, SWT.NONE);
		layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		layout.horizontalSpacing = 0;
		layout.verticalSpacing = 0;
		createRightButtonBar(rightButtonBar);

		Composite detailComposite = new Composite(this.fSplitterControl, SWT.NONE);
		Composite detailLabelComposite = new Composite(detailComposite, SWT.NONE);
		layout = new GridLayout(1, false);
		layout.marginHeight = 2;
		layout.marginWidth = 2;
		detailLabelComposite.setLayout(layout);
		createDetailLabel(detailLabelComposite);
		GridData gd = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		detailLabelComposite.setLayoutData(gd);
		layout = new GridLayout(1, false);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		layout.horizontalSpacing = 0;
		layout.verticalSpacing = 0;
		detailComposite.setLayout(layout);
		detailComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		createDetailPane(detailComposite);

		final MouseAdapter listener = new MouseAdapter() {

			@Override
			public void mouseDoubleClick(final MouseEvent event) {
				final Control content = DetailedViewerControl.this.viewerPane.getContent();
				if (content != null && content.getBounds().contains(event.x, event.y)) {
					return;
				}
				final Control control = DetailedViewerControl.this.viewerPane.getParent()
						.getParent();
				if (control instanceof Splitter) {
					((Splitter) control).setMaximizedControl(DetailedViewerControl.this.viewerPane
							.getParent());
				}
			}
		};
		addMouseListener(listener);
		this.viewerPane.getTopLeft().addMouseListener(listener);
		this.fSplitterControl.setWeights(getWeight());
		createBottomButtonBar(this);
		Dialog.applyDialogFont(this);
	}

	/**
	 * Creates the button bar right to the viewer control.
	 * 
	 * @param parent
	 *            the parent container.
	 */
	protected void createRightButtonBar(final Composite parent) {
		GridData gd = new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false);
		gd.widthHint = 0;
		parent.setLayoutData(gd);

	}

	/**
	 * Returns the toolbar manager of this control
	 * 
	 * @return the toolbar manager
	 */
	private ToolBarManager getToolBarManager() {
		if (this.fToolBarManager == null) {
			final ToolBar toolbar = new ToolBar(this.viewerPane, SWT.FLAT);
			this.viewerPane.setTopCenter(toolbar);
			this.fToolBarManager = new ToolBarManager(toolbar);
		}
		return this.fToolBarManager;
	}

	protected void handleSelectionChanged(final IStructuredSelection selection) {
		// TODO Auto-generated method stub

	}

	/**
	 * Creates the detail label of the control
	 * 
	 * @param parent
	 *            the parent composite
	 */
	protected void createDetailLabel(final Composite parent) {
		// do nothing by default
	}

	/**
	 * Creates the detail pane of the control.
	 * 
	 * @param parent
	 *            the parent composite
	 */
	protected Control createDetailPane(final Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(1, false);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		layout.horizontalSpacing = 0;
		layout.verticalSpacing = 0;
		comp.setLayout(layout);
		comp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		return comp;

	}

	/**
	 * Creates the viewer of the control.
	 * 
	 * @param parent
	 *            the parent composite
	 * @return the viewer
	 */
	protected abstract StructuredViewer createViewer(final Composite parent);

	/**
	 * Creates the selection label of the control
	 * 
	 * @param parent
	 *            the parent composite
	 */
	protected void createSelectionLabel(final Composite parent) {
		Assert.isNotNull(parent);
		this.fSelectionLabel = new Label(parent, SWT.HORIZONTAL | SWT.RIGHT | SWT.WRAP);
		final GridData data = new GridData(GridData.HORIZONTAL_ALIGN_END);
		data.horizontalSpan = 1;
		this.fSelectionLabel.setLayoutData(data);
	}

	/**
	 * Creates the toolbar of the control.
	 * 
	 * @param manager
	 *            the parent control
	 */
	protected void createToolBar(final ToolBarManager manager) {
		// Do nothing
	}

	/**
	 * {@inheritDoc}
	 */
	public final Control getControl() {
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setInput(final Object input) {
		this.viewerInput = input;
		if (this.viewer != null) {
			this.viewer.setInput(input);
		}
	}

	public IContentProvider getContentProvider() {
		return this.contentProvider;
	}

	public void setContentProvider(final IContentProvider contentProvider) {
		this.contentProvider = contentProvider;
		if (this.viewer != null) {
			this.viewer.setContentProvider(contentProvider);
		}
	}

	public IBaseLabelProvider getLabelProvider() {
		return this.labelProvider;

	}

	public void setLabelProvider(final IBaseLabelProvider labelProvider) {
		this.labelProvider = labelProvider;
		if (this.viewer != null) {
			this.viewer.setLabelProvider(labelProvider);
		}
	}

	public String getPaneText() {
		return this.paneText;
	}

	public void setPaneText(final String paneText) {
		this.paneText = paneText;
	}

	public int[] getWeight() {
		return this.weight;
	}

	public void setWeight(final int[] weight) {
		this.weight = weight;
	}

	public StructuredViewer getViewer() {
		return this.viewer;
	}

	/**
	 * @return the orientation
	 */
	public int getOrientation() {
		return this.orientation;
	}

	/**
	 * @param orientation
	 *            the orientation to set
	 */
	public void setOrientation(final int orientation) {
		this.orientation = orientation;
	}

}