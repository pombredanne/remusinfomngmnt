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

package org.remus.infomngmnt.core.builder;

import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.osgi.util.NLS;

import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.core.CorePlugin;
import org.remus.infomngmnt.core.extension.IInfoType;
import org.remus.infomngmnt.core.extension.InformationExtensionManager;
import org.remus.infomngmnt.core.model.EditingUtil;
import org.remus.infomngmnt.provider.InfomngmntEditPlugin;
import org.remus.infomngmnt.resources.util.ResourceUtil;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 * @noextend This class is not intended to be subclassed by clients.
 * @noinstantiate This class is not intended to be instantiated by clients.
 */
public class InformationBuilder extends IncrementalProjectBuilder {

	public static final String BUILDER_ID = CorePlugin.PLUGIN_ID + ".infobuilder"; //$NON-NLS-1$
	private final InformationDeltaVisitor visitor;

	/**
	 * 
	 */
	public InformationBuilder() {
		this.visitor = new InformationDeltaVisitor();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IncrementalProjectBuilder#build(int,
	 * java.util.Map, org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	protected IProject[] build(final int kind, final Map args, final IProgressMonitor monitor)
			throws CoreException {
		switch (kind) {
		case FULL_BUILD:
			IFolder folder = getProject().getFolder(ResourceUtil.BIN_FOLDER);
			if (!folder.exists()) {
				folder.create(true, true, monitor);
			}
			IResource[] members = getProject().members();
			monitor.beginTask(NLS.bind("Refreshing info-object on {0}", getProject().getName()),
					members.length);
			for (IResource resource : members) {
				if (resource.getType() == IResource.FILE
						&& resource.getFileExtension().equals(ResourceUtil.FILE_EXTENSION)) {
					InformationUnit objectFromFile = EditingUtil.getInstance().getObjectFromFile(
							(IFile) resource, InfomngmntPackage.eINSTANCE.getInformationUnit(),
							false);
					if (objectFromFile != null) {
						monitor.setTaskName(NLS.bind("Rebuilding \"{0}\"", objectFromFile
								.getLabel()));
						IInfoType infoTypeByType = InformationExtensionManager.getInstance()
								.getInfoTypeByType(objectFromFile.getType());
						this.visitor.setMonitor(monitor);
						this.visitor.buildSingleInfoUnit(objectFromFile, infoTypeByType,
								(IFile) resource);
						monitor.worked(1);
					}
				}
			}
			break;
		default:
			break;
		}

		if (getDelta(getProject()) != null) {
			proceedDelta(getDelta(getProject()), monitor);
		}
		return null;
	}

	private void proceedDelta(final IResourceDelta delta, final IProgressMonitor monitor) {
		try {
			this.visitor.setMonitor(monitor);
			delta.accept(this.visitor);
		} catch (CoreException e) {
			InfomngmntEditPlugin.INSTANCE.log(e.getStatus());
		}
	}

	@Override
	protected void clean(final IProgressMonitor monitor) throws CoreException {
		IFolder folder = getProject().getFolder("bin");
		if (folder.exists()) {
			folder.delete(true, monitor);
		}
		super.clean(monitor);
	}

}
