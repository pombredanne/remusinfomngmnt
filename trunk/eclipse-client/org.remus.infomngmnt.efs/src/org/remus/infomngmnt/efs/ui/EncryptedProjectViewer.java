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

package org.remus.infomngmnt.efs.ui;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;

import org.remus.infomngmnt.common.ui.image.ResourceManager;
import org.remus.infomngmnt.common.ui.swt.DetailedViewerControl;
import org.remus.infomngmnt.efs.EFSActivator;
import org.remus.infomngmnt.efs.extension.AbstractSecurityProvider;
import org.remus.infomngmnt.efs.internal.model.SecurityAffectedProject;
import org.remus.infomngmnt.efs.internal.model.SecurityWrapper;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class EncryptedProjectViewer extends DetailedViewerControl {

	private final List<SecurityWrapper> list;
	private Label label;
	private SecurityWrapper selectedProvider;
	private Button initializeButton;

	public EncryptedProjectViewer(final Composite parent, final List<SecurityWrapper> model) {
		super(parent);
		this.list = model;
		setContentProvider(new EncryptedProjectContentProvider());
		setLabelProvider(new SecurityProviderLableProvider());
		setInput(model);
		setWeight(new int[] { 5, 2 });
		setOrientation(SWT.HORIZONTAL);
		setPaneText("Found security providers and affected projects.");
		createControl();
	}

	@Override
	protected void handleSelectionChanged(final IStructuredSelection selection) {
		if (!selection.isEmpty()) {
			Object firstElement = selection.getFirstElement();
			this.selectedProvider = null;
			if (firstElement instanceof SecurityWrapper) {
				this.selectedProvider = (SecurityWrapper) firstElement;
			} else if (firstElement instanceof SecurityAffectedProject) {
				this.selectedProvider = ((SecurityAffectedProject) firstElement)
						.getParentProvider();

			}
		} else {
			unsetDetails();
		}
		super.handleSelectionChanged(selection);
	}

	private void unsetDetails() {
		this.initializeButton.setEnabled(false);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.common.ui.swt.DetailedViewerControl#createViewer
	 * (org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected StructuredViewer createViewer(final Composite parent) {
		TreeViewer viewer = new TreeViewer(parent, SWT.NONE);
		return viewer;
	}

	@Override
	protected void createDetailLabel(final Composite parent) {
		this.label = new Label(parent, SWT.NONE);
		this.label.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	}

	@Override
	protected Control createDetailPane(final Composite parent) {
		Composite returnValue = (Composite) super.createDetailPane(parent);
		final Label label_1 = new Label(returnValue, SWT.NONE);
		label_1.setText("Label");

		final Label label = new Label(returnValue, SWT.NONE);
		label.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false));
		label.setText("Label");

		this.initializeButton = new Button(returnValue, SWT.NONE);
		final GridData gd_initializeButton = new GridData(SWT.RIGHT, SWT.CENTER, false, false);
		this.initializeButton.setLayoutData(gd_initializeButton);
		this.initializeButton.setText("Initialize");
		this.initializeButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				EncryptedProjectViewer.this.selectedProvider.getProvider().initProviderForProject(
						getShell());
				getViewer().update(EncryptedProjectViewer.this.selectedProvider, null);
			}
		});

		return returnValue;

	}

	private static class EncryptedProjectContentProvider implements ITreeContentProvider {

		public Object[] getChildren(final Object parentElement) {
			if (parentElement instanceof List) {
				return ((List) parentElement).toArray();
			} else if (parentElement instanceof SecurityWrapper) {
				return ((SecurityWrapper) parentElement).getAffectedProject().toArray();
			}
			return new Object[0];
		}

		public Object getParent(final Object element) {
			if (element instanceof SecurityAffectedProject) {
				return ((SecurityAffectedProject) element).getParentProvider();
			}
			return null;
		}

		public boolean hasChildren(final Object element) {
			return getChildren(element).length != 0;
		}

		public Object[] getElements(final Object inputElement) {
			return getChildren(inputElement);
		}

		public void dispose() {
			// TODO Auto-generated method stub

		}

		public void inputChanged(final Viewer viewer, final Object oldInput, final Object newInput) {
			// TODO Auto-generated method stub

		}

	}

	private static class SecurityProviderLableProvider extends LabelProvider {
		@Override
		public String getText(final Object element) {
			if (element instanceof SecurityWrapper) {
				return ((SecurityWrapper) element).getName();
			} else if (element instanceof SecurityAffectedProject) {
				return ((SecurityAffectedProject) element).getProject().getName();
			}
			return null;
		}

		@Override
		public Image getImage(final Object element) {
			if (element instanceof SecurityWrapper) {
				AbstractSecurityProvider provider = ((SecurityWrapper) element).getProvider();
				if (provider.isInitialized()) {
					return ResourceManager.getPluginImage(EFSActivator.getDefault(),
							"icons/iconexperience/ok.png");
				} else {
					return ResourceManager.getPluginImage(EFSActivator.getDefault(),
							"icons/iconexperience/sign_warning.png");
				}
			} else if (element instanceof SecurityAffectedProject) {
				return ResourceManager.getPluginImage(EFSActivator.getDefault(),
						"icons/iconexperience/folder_green.png");
			}
			return super.getImage(element);
		}
	}

}
