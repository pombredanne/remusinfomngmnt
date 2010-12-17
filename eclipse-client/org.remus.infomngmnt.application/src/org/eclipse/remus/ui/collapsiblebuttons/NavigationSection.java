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

package org.eclipse.remus.ui.collapsiblebuttons;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.ui.viewer.IViewerProvider;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.remus.InformationUnitListItem;
import org.eclipse.remus.common.ui.image.ResourceManager;
import org.eclipse.remus.ui.collapsiblebutton.CollapsibleButtonBar;
import org.eclipse.remus.ui.editors.InformationEditor;
import org.eclipse.remus.ui.editors.InformationEditorInput;
import org.eclipse.remus.ui.viewer.NavigationViewer;
import org.eclipse.remus.ui.viewer.ViewerActivator;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IPathEditorInput;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPreferenceConstants;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.part.ISetSelectionTarget;


/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class NavigationSection extends CollapsibleButtonBar implements ISelectionProvider,
		IEditingDomainProvider, ISetSelectionTarget, IViewerProvider {

	private final NavigationViewer viewer;

	private Action linkEditorAction;
	private boolean linkEditor;
	private IDialogSettings settings;

	IPartListener partListener = new IPartListener() {
		public void partActivated(final IWorkbenchPart part) {
			if (part instanceof EditorPart) {
				IEditorInput input = ((EditorPart) part).getEditorInput();
				if (input instanceof InformationEditorInput) {
					IFile file2 = ((InformationEditorInput) input).getFile();
					Object adapter = Platform.getAdapterManager().getAdapter(file2,
							InformationUnitListItem.class);
					if (adapter != null) {
						setSelection(new StructuredSelection(adapter));
					}
				}
			}
			if (part == getViewSite().getPart()) {
				// NavigationView.this.actionBar.setActiveDomain(NavigationView.this);
			}

		}

		public void partBroughtToTop(final IWorkbenchPart part) {

		}

		public void partClosed(final IWorkbenchPart part) {

		}

		public void partDeactivated(final IWorkbenchPart part) {

		}

		public void partOpened(final IWorkbenchPart part) {

		}
	};

	public NavigationSection() {
		this.viewer = new NavigationViewer() {
			@Override
			protected void handleOpen(final InformationUnitListItem object) {
				try {
					PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
							.openEditor(new InformationEditorInput(object), InformationEditor.ID);
				} catch (PartInitException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
	}

	@Override
	public void createControl(final Composite parent) {
		this.viewer.setContextMenuId(getId());
		this.viewer.createControl(getViewSite(), parent);

		initLinkWithEditor();
		setControl(this.viewer.getViewer().getControl());

	}

	public void addSelectionChangedListener(final ISelectionChangedListener listener) {
		this.viewer.addSelectionChangedListener(listener);

	}

	public ISelection getSelection() {
		return this.viewer.getSelection();

	}

	public void removeSelectionChangedListener(final ISelectionChangedListener listener) {
		this.viewer.removeSelectionChangedListener(listener);

	}

	public void setSelection(final ISelection selection) {
		this.viewer.setSelection(selection);

	}

	public EditingDomain getEditingDomain() {
		return this.viewer.getEditingDomain();
	}

	public void selectReveal(final ISelection selection) {
		this.viewer.selectReveal(selection);

	}

	@Override
	public boolean setFocus() {
		return this.viewer.setFocus();
	}

	@Override
	public void init(final IViewSite site, final IMemento memento) {
		this.settings = ViewerActivator.getDefault().getDialogSettings();
		this.viewer.init(site, memento);
		super.init(site, memento);
	}

	@Override
	public void saveState(final IMemento child) {
		this.viewer.saveState(child);
	}

	@Override
	public void dispose() {
		this.viewer.dispose();
	}

	public Viewer getViewer() {
		return this.viewer.getViewer();
	}

	/**
	 * 
	 */
	private void initLinkWithEditor() {
		if (this.settings == null) {
			return;
		}
		// Try the dialog settings first, which remember the last choice.
		final String setting = this.settings
				.get(IWorkbenchPreferenceConstants.LINK_NAVIGATOR_TO_EDITOR);
		if (setting != null) {
			this.linkEditor = setting.equals("true"); //$NON-NLS-1$
		}
		// Link with editor

		this.linkEditorAction = new Action("Link with editor", IAction.AS_CHECK_BOX) {
			/*
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.jface.action.Action#run()
			 */
			@Override
			public void run() {
				setLinkingEnabled(isChecked());
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.jface.action.Action#getToolTipText()
			 */
			@Override
			public String getToolTipText() {
				return getText();
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.jface.action.Action#getImageDescriptor()
			 */
			@Override
			public ImageDescriptor getImageDescriptor() {
				return ResourceManager.getPluginImageDescriptor(ViewerActivator.getDefault(),
						"icons/synced.gif");

			}

		};
		this.linkEditorAction.setChecked(this.linkEditor);
		// setLinkingEnabled(true);

	}

	public void setLinkingEnabled(final boolean enabled) {
		this.linkEditor = enabled;

		// remember the last settings in the dialog settings
		this.settings.put(IWorkbenchPreferenceConstants.LINK_NAVIGATOR_TO_EDITOR, enabled);

		// if turning linking on, update the selection to correspond to the
		// active editor
		if (enabled) {
			if (PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage() != null) {
				IEditorInput editorInput = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
						.getActivePage().getActiveEditor().getEditorInput();
				if (editorInput instanceof IPathEditorInput) {
					// FIXME
					Object adapter = Platform.getAdapterManager().getAdapter(editorInput,
							InformationUnitListItem.class);
					if (adapter != null) {
						setSelection(new StructuredSelection(adapter));
					}
				}
			}
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().addPartListener(
					this.partListener);
		} else {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
					.removePartListener(this.partListener);
		}
	}

	@Override
	public void initToolbar(final IToolBarManager toolbarManager) {
		toolbarManager.add(this.linkEditorAction);
		toolbarManager.update(true);
	}

}
