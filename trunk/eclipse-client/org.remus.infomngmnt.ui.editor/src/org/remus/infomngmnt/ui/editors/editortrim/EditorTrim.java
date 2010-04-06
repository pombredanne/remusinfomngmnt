/*******************************************************************************
 * Copyright (c) 2009 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/

package org.remus.infomngmnt.ui.editors.editortrim;

import org.eclipse.core.runtime.IPath;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPageListener;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IPathEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.menus.WorkbenchWindowControlContribution;

import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.core.services.IBinaryReferenceStore;
import org.remus.infomngmnt.ui.editor.EditorActivator;
import org.remus.infomngmnt.ui.editors.InformationEditor;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class EditorTrim extends WorkbenchWindowControlContribution {

	private IPartListener listener;

	private final IBinaryReferenceStore binaryReferenceService;

	private EditorTrimControl editorTrimControl;

	/**
	 * 
	 */
	public EditorTrim() {
		this.binaryReferenceService = EditorActivator.getDefault().getServiceTracker().getService(
				IBinaryReferenceStore.class);
	}

	/**
	 * @param id
	 */
	public EditorTrim(final String id) {
		super(id);
		this.binaryReferenceService = EditorActivator.getDefault().getServiceTracker().getService(
				IBinaryReferenceStore.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.action.ControlContribution#createControl(org.eclipse
	 * .swt.widgets.Composite)
	 */
	@Override
	protected Control createControl(final Composite parent) {
		this.listener = new IPartListener() {
			public void partOpened(final IWorkbenchPart part) {
				// do nothing
			}

			public void partDeactivated(final IWorkbenchPart part) {
				// do nothing

			}

			public void partClosed(final IWorkbenchPart part) {
				// do nothing

			}

			public void partBroughtToTop(final IWorkbenchPart part) {
				// do nothing

			}

			public void partActivated(final IWorkbenchPart part) {
				handleActivate(part);

			}
		};
		this.editorTrimControl = new EditorTrimControl(parent, SWT.NONE);
		this.editorTrimControl.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true));
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().addPageListener(new IPageListener() {

			public void pageOpened(final IWorkbenchPage page) {
				// TODO Auto-generated method stub

			}

			public void pageClosed(final IWorkbenchPage page) {
				// TODO Auto-generated method stub

			}

			public void pageActivated(final IWorkbenchPage page) {
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
						.addPartListener(EditorTrim.this.listener);
				handleActivate(page.getActivePart());
			}
		});
		if (PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage() != null) {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().addPartListener(
					this.listener);
			handleActivate(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
					.getActivePart());

		}
		return this.editorTrimControl;
	}

	protected void handleActivate(final IWorkbenchPart part) {
		if (part instanceof InformationEditor) {
			handleActivate(((InformationEditor) part).getPrimaryModel().getType());
		} else {
			handleDeactivate(part);
		}
	}

	protected void handleDeactivate(final IWorkbenchPart part) {
		if (part instanceof IEditorPart) {
			IEditorInput editorInput = ((IEditorPart) part).getEditorInput();
			if (editorInput instanceof IPathEditorInput) {
				try {
					IPath path = ((IPathEditorInput) editorInput).getPath();
					String infoUnit = this.binaryReferenceService.getReferencedInfoUnitIdByPath(
							path.segment(path.segmentCount() - 3), path.lastSegment());
					if (infoUnit != null) {
						InformationUnitListItem itemById = EditorActivator.getDefault()
								.getApplicationService().getItemById(infoUnit, null);
						if (itemById != null) {
							handleBackSwitching(itemById);
							return;
						}
					}
				} catch (Exception e) {
					// if any error occurs, we do nothing here.
				}
			}
			handleActivate((String) null);
		}

	}

	private void handleBackSwitching(final InformationUnitListItem itemById) {
		this.editorTrimControl.buildBackButton(itemById.getId());
	}

	protected void handleActivate(final String typeId) {
		this.editorTrimControl.buildHyperLink(typeId);
	}

	@Override
	protected int computeWidth(final Control control) {
		return 200;
	}

	@Override
	public void dispose() {
		if (this.editorTrimControl != null) {
			this.editorTrimControl.dispose();
		}
		super.dispose();
	}

}
