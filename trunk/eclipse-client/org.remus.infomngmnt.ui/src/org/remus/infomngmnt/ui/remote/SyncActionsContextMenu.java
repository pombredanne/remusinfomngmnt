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

package org.remus.infomngmnt.ui.remote;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.compare.diff.metamodel.DiffGroup;
import org.eclipse.emf.compare.diff.metamodel.ModelElementChangeLeftTarget;
import org.eclipse.emf.compare.diff.metamodel.ModelElementChangeRightTarget;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.IWorkbenchActionConstants;

import org.remus.infomngmnt.ChangeSetItem;
import org.remus.infomngmnt.SynchronizableObject;
import org.remus.infomngmnt.SynchronizationAction;
import org.remus.infomngmnt.core.sync.SyncUtil;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class SyncActionsContextMenu implements IMenuListener {

	private final Viewer viewer;
	private SyncAction replaceRemoteAction;
	private SyncAction replaceLocalAction;
	private ChangeSetItem changeSet;

	private final Map<EObject, SynchronizationAction> initialValues = new HashMap<EObject, SynchronizationAction>();
	private final SynchronizeChangeSetWizardPage synchronizeChangeSetWizardPage;

	public SyncActionsContextMenu(
			final SynchronizeChangeSetWizardPage synchronizeChangeSetWizardPage) {
		this.synchronizeChangeSetWizardPage = synchronizeChangeSetWizardPage;
		this.viewer = synchronizeChangeSetWizardPage.getViewer();
		this.viewer.addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(final SelectionChangedEvent selectionchangedevent) {
				update(((IStructuredSelection) selectionchangedevent.getSelection())
						.getFirstElement());

			}
		});
		init();
	}

	protected void update(final Object element) {
		EObject selection = null;
		if (element instanceof DiffGroup) {
			final DiffGroup group = (DiffGroup) element;
			selection = group.getRightParent();
		} else if (element instanceof ModelElementChangeRightTarget) {
			selection = ((ModelElementChangeRightTarget) element).getRightElement();

		} else if (element instanceof ModelElementChangeLeftTarget) {
			selection = ((ModelElementChangeLeftTarget) element).getLeftElement();
		}
		if (selection != null) {
			SynchronizationAction action = SyncUtil.getAction(this.changeSet, selection);
			if (action != null) {
				if (this.initialValues.get(selection) == null) {
					this.initialValues.put(selection, action);
				}
				this.replaceLocalAction.setEnabled(true);
				this.replaceLocalAction.setObject(selection);
				this.replaceRemoteAction.setEnabled(true);
				this.replaceRemoteAction.setObject(selection);
				switch (action) {
				case REPLACE_LOCAL:
				case ADD_LOCAL:
				case DELETE_LOCAL:
					this.replaceLocalAction.setChecked(true);
					this.replaceRemoteAction.setChecked(false);
					break;
				case REPLACE_REMOTE:
				case DELETE_REMOTE:
				case ADD_REMOTE:
					this.replaceRemoteAction.setChecked(true);
					this.replaceLocalAction.setChecked(false);
					break;
				case RESOLVE_CONFLICT:
					this.replaceLocalAction.setChecked(false);
					this.replaceRemoteAction.setChecked(false);
				}
				return;
			}
		}
		this.replaceLocalAction.setChecked(false);
		this.replaceRemoteAction.setChecked(false);
		this.replaceLocalAction.setEnabled(false);
		this.replaceRemoteAction.setEnabled(false);

	}

	private void init() {
		this.replaceRemoteAction = new SyncAction("Override remote element",
				IAction.AS_RADIO_BUTTON, SynchronizationAction.REPLACE_REMOTE);
		this.replaceLocalAction = new SyncAction("Override local element", IAction.AS_RADIO_BUTTON,
				SynchronizationAction.REPLACE_LOCAL);
		update(((IStructuredSelection) this.viewer.getSelection()).getFirstElement());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.action.IMenuListener#menuAboutToShow(org.eclipse.jface
	 * .action.IMenuManager)
	 */
	public void menuAboutToShow(final IMenuManager imenumanager) {
		imenumanager.add(new GroupMarker("actions"));
		imenumanager.add(this.replaceLocalAction);
		imenumanager.add(this.replaceRemoteAction);
		imenumanager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		imenumanager.update(true);

	}

	class SyncAction extends Action {

		private final SynchronizationAction action;
		private EObject selection;

		public SyncAction(final String text, final int style, final SynchronizationAction action) {
			super(text, style);
			this.action = action;
		}

		public void setObject(final EObject selection) {
			this.selection = selection;
		}

		@Override
		public void run() {
			if (isChecked()) {
				SyncUtil.changeSynchronizationAction(SyncActionsContextMenu.this.changeSet,
						(SynchronizableObject) this.selection, this.action);
			}
			SyncActionsContextMenu.this.synchronizeChangeSetWizardPage.checkConflicts();
			SyncActionsContextMenu.this.viewer.refresh();
			super.run();
		}

	}

	public void setChangeSet(final ChangeSetItem changeSet2) {
		this.changeSet = changeSet2;
	}

}
