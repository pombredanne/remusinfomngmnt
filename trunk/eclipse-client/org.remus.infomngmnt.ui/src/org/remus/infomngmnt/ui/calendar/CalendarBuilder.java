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

package org.remus.infomngmnt.ui.calendar;

import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.osgi.util.NLS;

import org.remus.infomngmnt.CalendarEntry;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.core.extension.IInfoType;
import org.remus.infomngmnt.core.extension.InformationExtensionManager;
import org.remus.infomngmnt.core.model.EditingUtil;
import org.remus.infomngmnt.provider.InfomngmntEditPlugin;
import org.remus.infomngmnt.resources.util.ResourceUtil;
import org.remus.infomngmnt.ui.UIPlugin;
import org.remus.infomngmnt.ui.service.ICalendarStoreService;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class CalendarBuilder extends IncrementalProjectBuilder {

	public static final String BUILDER_ID = "org.remus.infomngmnt.ui.calendarEntryBuilder"; //$NON-NLS-1$
	private final CalendarDeltaVisitor visitor;

	/**
	 * 
	 */
	public CalendarBuilder() {
		this.visitor = new CalendarDeltaVisitor();
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
							(IFile) resource, InfomngmntPackage.eINSTANCE.getInformationUnit());
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

	public class CalendarDeltaVisitor implements IResourceDeltaVisitor {

		private final ICalendarStoreService storeService;

		public CalendarDeltaVisitor() {
			this.storeService = UIPlugin.getDefault().getService(ICalendarStoreService.class);
		}

		private IProgressMonitor monitor;

		public boolean visit(final IResourceDelta delta) throws CoreException {
			visitRecursively(delta);
			return true;
		}

		public void visitRecursively(final IResourceDelta delta) {
			IResourceDelta[] affectedChildren = delta.getAffectedChildren();
			for (IResourceDelta resourceDelta : affectedChildren) {
				InformationUnit objectFromFile = null;
				IInfoType infoTypeByType = null;
				if (resourceDelta.getResource().getParent() != null
						&& resourceDelta.getResource().getParent().getName().equals(
								ResourceUtil.SETTINGS_FOLDER)) {
					return;
				}

				if (resourceDelta.getResource() != null
						&& resourceDelta.getResource().getType() == IResource.FILE
						&& resourceDelta.getResource().getFileExtension().equals(
								ResourceUtil.FILE_EXTENSION)) {
					if (resourceDelta.getResource().exists()) {
						objectFromFile = EditingUtil.getInstance().getObjectFromFile(
								(IFile) resourceDelta.getResource(),
								InfomngmntPackage.eINSTANCE.getInformationUnit(), null, false);
						infoTypeByType = InformationExtensionManager.getInstance()
								.getInfoTypeByType(objectFromFile.getType());

					}
					switch (resourceDelta.getKind()) {
					case IResourceDelta.REPLACED:
					case IResourceDelta.ADDED:
					case IResourceDelta.CHANGED:

						if (resourceDelta.getResource().exists() && objectFromFile != null
								&& objectFromFile.getId() != null) {

							buildSingleInfoUnit(objectFromFile, infoTypeByType,
									(IFile) resourceDelta.getResource());
						}

						break;
					case IResourceDelta.REMOVED:
						removeSingleInfoUnit((IFile) resourceDelta.getResource());
						break;
					}
				}
				visitRecursively(resourceDelta);
			}
		}

		private void removeSingleInfoUnit(final IFile resource) {
			this.storeService.removeInfoUnit(resource.getLocation().removeFileExtension()
					.lastSegment());
		}

		public void buildSingleInfoUnit(final InformationUnit objectFromFile,
				final IInfoType infoTypeByType, final IFile resource) {
			EList<CalendarEntry> calendarEntry = objectFromFile.getCalendarEntry();
			this.storeService.update(objectFromFile);
		}

		public void setMonitor(final IProgressMonitor monitor) {
			this.monitor = monitor;
		}

	}

	@Override
	protected void clean(final IProgressMonitor monitor) throws CoreException {
		IResource[] members = getProject().members();
		for (IResource iResource : members) {
			if (iResource.getType() == IResource.FILE
					&& ResourceUtil.FILE_EXTENSION.equals(iResource.getFullPath()
							.getFileExtension())) {
				this.visitor.removeSingleInfoUnit((IFile) iResource);
			}
		}
		super.clean(monitor);
	}

}