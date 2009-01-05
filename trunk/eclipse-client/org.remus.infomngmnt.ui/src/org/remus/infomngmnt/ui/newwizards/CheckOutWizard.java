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
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;

import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.RemoteObject;
import org.remus.infomngmnt.common.ui.UIUtil;
import org.remus.infomngmnt.core.progress.CancelableRunnable;
import org.remus.infomngmnt.core.remote.IRepository;
import org.remus.infomngmnt.core.services.IRepositoryExtensionService;
import org.remus.infomngmnt.ui.UIPlugin;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public abstract class CheckOutWizard extends MultipleNewObjectsWizard {

	
	@Override
	public void init(final IWorkbench workbench, final IStructuredSelection selection) {
		ProgressMonitorDialog pmd = new ProgressMonitorDialog(workbench.getActiveWorkbenchWindow().getShell());
		try {
			pmd.run(true, true, new CancelableRunnable() {
				@Override
				protected IStatus runCancelableRunnable(final IProgressMonitor monitor) {
					if (UIUtil.isSelectionInstanceOf(selection, RemoteObject.class)) {
						List<RemoteObject> list = selection.toList();
						IRepository itemById = UIPlugin.getDefault().getService(IRepositoryExtensionService.class).getItemById(list.get(0).getRepositoryTypeId());
						InformationUnit[] convertToLocalObjects = itemById.convertToLocalObjects(list.toArray(new RemoteObject[list.size()]), monitor);
						setNewObjects(new BasicEList<InformationUnit>(Arrays.asList(convertToLocalObjects)));
					}
					CheckOutWizard.super.init(workbench, selection);
					CheckOutWizard.this.page1 = new GeneralCheckoutPage();
					return Status.OK_STATUS;
				}
			});
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			
		}
		
	}

}
