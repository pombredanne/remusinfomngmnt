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

package org.remus.infomngmnt.ui.newwizards;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;

import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.RemoteObject;
import org.remus.infomngmnt.SynchronizationMetadata;
import org.remus.infomngmnt.common.ui.UIUtil;
import org.remus.infomngmnt.core.commands.CommandFactory;
import org.remus.infomngmnt.core.model.EditingUtil;
import org.remus.infomngmnt.core.progress.CancelableRunnable;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public abstract class CheckOutMultipeContainerWizard extends MultipleNewObjectsWizard {

	private Map<InformationUnit, SynchronizationMetadata> convertedObjects;
	
	@Override
	public void init(final IWorkbench workbench, final IStructuredSelection selection) {
		ProgressMonitorDialog pmd = new ProgressMonitorDialog(workbench.getActiveWorkbenchWindow().getShell());
		try {
			pmd.run(true, true, new CancelableRunnable() {

				@Override
				protected IStatus runCancelableRunnable(final IProgressMonitor monitor) {
					if (UIUtil.isSelectionInstanceOf(selection, RemoteObject.class)) {
						List<RemoteObject> list = selection.toList();
//						IRepository itemById = UIPlugin.getDefault().getService(IRepositoryExtensionService.class).getItemById(list.get(0).getRepositoryTypeId());
//						CheckOutMultipeContainerWizard.this.convertedObjects = itemById.convertToLocalObjects(list.toArray(new RemoteObject[list.size()]), monitor);
//						setNewObjects(new BasicEList<InformationUnit>(CheckOutMultipeContainerWizard.this.convertedObjects.keySet()));
					}
					
					return Status.OK_STATUS;
				}
			});
			CheckOutMultipeContainerWizard.this.page1 = new GeneralCheckoutPage();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			
		}
	}
	
	@Override
	public boolean performFinish() {
		Job job = new Job("Creating new items") {
			@Override
			protected IStatus run(final IProgressMonitor monitor) {
				monitor.beginTask("Creating new items", CheckOutMultipeContainerWizard.this.convertedObjects.size());
				for (InformationUnit newElement : CheckOutMultipeContainerWizard.this.convertedObjects.keySet()) {
					EditingUtil.getInstance().getNavigationEditingDomain().getCommandStack()
					.execute(CommandFactory.CREATE_INFOTYPE(newElement, findCategory()));
					
					InformationUnitListItem adapter = (InformationUnitListItem) newElement.getAdapter(InformationUnitListItem.class);
					if (adapter != null) {
						adapter.setSynchronizationMetaData(CheckOutMultipeContainerWizard.this.convertedObjects.get(newElement));
					}
					// we also reveal the created list-item, that can be found in the navigation
//					UIUtil.selectAndReveal(newElement.getAdapter(InformationUnitListItem.class), PlatformUI.getWorkbench().getActiveWorkbenchWindow());
//					UIUtil.selectAndReveal(newElement, PlatformUI.getWorkbench().getActiveWorkbenchWindow());
					monitor.worked(1);
				}
				return Status.OK_STATUS;
			}
		};
		job.schedule();
		return true;
	}

}
