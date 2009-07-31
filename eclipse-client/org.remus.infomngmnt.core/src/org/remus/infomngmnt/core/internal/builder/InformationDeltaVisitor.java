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

package org.remus.infomngmnt.core.internal.builder;

import java.io.File;
import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.edit.command.SetCommand;

import org.remus.infomngmnt.AbstractInformationUnit;
import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.common.core.streams.StreamCloser;
import org.remus.infomngmnt.common.core.util.StringUtils;
import org.remus.infomngmnt.core.extension.AbstractInformationRepresentation;
import org.remus.infomngmnt.core.extension.IInfoType;
import org.remus.infomngmnt.core.extension.InformationExtensionManager;
import org.remus.infomngmnt.core.services.IReferencedUnitStore;
import org.remus.infomngmnt.core.services.ISaveParticipantExtensionService;
import org.remus.infomngmnt.provider.InfomngmntEditPlugin;
import org.remus.infomngmnt.resources.util.ResourceUtil;
import org.remus.infomngmnt.util.CategoryUtil;
import org.remus.infomngmnt.util.DisposableEditingDomain;
import org.remus.infomngmnt.util.EditingUtil;
import org.remus.infomngmnt.util.StatusCreator;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class InformationDeltaVisitor implements IResourceDeltaVisitor {

	private final ISaveParticipantExtensionService saveParticipantService;

	private IProgressMonitor monitor;

	private final IReferencedUnitStore referenceService;

	public InformationDeltaVisitor() {
		this.referenceService = InfomngmntEditPlugin.getPlugin().getService(
				IReferencedUnitStore.class);
		this.saveParticipantService = InfomngmntEditPlugin.getPlugin().getService(
				ISaveParticipantExtensionService.class);
	}

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
					infoTypeByType = InformationExtensionManager.getInstance().getInfoTypeByType(
							objectFromFile.getType());

				}
				switch (resourceDelta.getKind()) {
				case IResourceDelta.ADDED:
					if (resourceDelta.getResource().exists() && objectFromFile != null
							&& objectFromFile.getId() != null) {
						buildSingleInfoUnit(objectFromFile, infoTypeByType, (IFile) resourceDelta
								.getResource());
						this.saveParticipantService.fireEvent(
								ISaveParticipantExtensionService.CREATED, null, objectFromFile);
					}
					break;
				case IResourceDelta.REPLACED:
				case IResourceDelta.CHANGED:

					if (resourceDelta.getResource().exists() && objectFromFile != null
							&& objectFromFile.getId() != null) {

						buildSingleInfoUnit(objectFromFile, infoTypeByType, (IFile) resourceDelta
								.getResource());
						File previousVersion = null;
						try {
							previousVersion = ResourceUtil.getPreviousVersion((IFile) resourceDelta
									.getResource(), this.monitor);
							if (previousVersion != null) {
								InformationUnit objectFromUri = EditingUtil.getInstance()
										.getObjectFromFileUri(
												URI
														.createFileURI(previousVersion
																.getAbsolutePath()),
												InfomngmntPackage.Literals.INFORMATION_UNIT, null);
								this.saveParticipantService.fireEvent(
										ISaveParticipantExtensionService.SAVED, objectFromUri,
										objectFromFile);
							}
						} catch (CoreException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} finally {
							if (previousVersion != null) {
								previousVersion.delete();
							}
						}

					}
					break;
				case IResourceDelta.REMOVED:
					String lastSegment = resourceDelta.getResource().getFullPath()
							.removeFileExtension().lastSegment();
					this.referenceService.delete(lastSegment);
					this.saveParticipantService.fireEvent(ISaveParticipantExtensionService.DELETED,
							lastSegment, null);
					break;
				}
			}
			visitRecursively(resourceDelta);
		}
	}

	void buildSingleInfoUnit(final InformationUnit objectFromFile, final IInfoType infoTypeByType,
			final IFile resource) {

		try {
			resource.deleteMarkers(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE);

			AbstractInformationRepresentation informationRepresentation = infoTypeByType
					.getInformationRepresentation();
			informationRepresentation.setValue(objectFromFile);
			File previousVersionFile = ResourceUtil.getPreviousVersion(resource, this.monitor);
			informationRepresentation.setPreviousVersion(previousVersionFile);
			if (informationRepresentation.createFolderOnBuild()) {
				String folderName = resource.getProjectRelativePath().removeFileExtension()
						.lastSegment();
				IFolder folder = resource.getParent().getFolder(new Path(folderName));
				if (folder.exists()) {
					folder.delete(true, this.monitor);
				}
				folder.create(true, true, this.monitor);
				informationRepresentation.setBuildFolder(folder);
			}

			informationRepresentation.handlePreBuild(this.monitor);
			if (infoTypeByType.isBuildHtml()) {
				IFile writeContent = null;
				try {
					InputStream handleHtmlGeneration = infoTypeByType
							.getInformationRepresentation().handleHtmlGeneration(this.monitor);
					writeContent = writeContent(resource, handleHtmlGeneration, this.monitor);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				informationRepresentation.handlePostBuild(writeContent, this.monitor);
				if (previousVersionFile != null) {
					previousVersionFile.delete();
				}
			}
		} catch (Exception e) {
			try {
				IMarker createMarker = resource.createMarker(IMarker.PROBLEM);
				createMarker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
				createMarker.setAttribute(IMarker.LOCATION, objectFromFile.getLabel());
				String join = StringUtils.join("Error while saving the inormation unit",
						objectFromFile.getLabel(), "(", e.getMessage(), ")");
				createMarker.setAttribute(IMarker.MESSAGE, join);
				InfomngmntEditPlugin.getPlugin().getLog().log(StatusCreator.newStatus(join, e));

			} catch (CoreException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		Object adapter = objectFromFile.getAdapter(InformationUnitListItem.class);
		if (adapter == null) {
			Category category = CategoryUtil.findCategory(StringUtils.join(resource.getProject()
					.getName(), "/", "Orphan"), true);
			InformationUnitListItem unitListItem = InfomngmntFactory.eINSTANCE
					.createInformationUnitListItem();
			unitListItem.setId(objectFromFile.getId());
			unitListItem.setWorkspacePath(resource.getFullPath().toOSString());
			unitListItem.setLabel(objectFromFile.getLabel());
			unitListItem.setType(objectFromFile.getType());
			category.getInformationUnit().add(unitListItem);
		} else {
			String label = objectFromFile.getLabel();
			String label2 = ((AbstractInformationUnit) adapter).getLabel();
			if (label != null && !label.equals(label2)) {
				DisposableEditingDomain domain = EditingUtil.getInstance().createNewEditingDomain();
				Command create = SetCommand.create(domain, adapter,
						InfomngmntPackage.Literals.ABSTRACT_INFORMATION_UNIT__LABEL, label);
				domain.getCommandStack().execute(create);
				domain.dispose();
			}
		}

		this.referenceService.update(objectFromFile);
	}

	public void setMonitor(final IProgressMonitor monitor) {
		this.monitor = monitor;

	}

	private IFile writeContent(final IFile origFile, final InputStream handleHtmlGeneration,
			final IProgressMonitor monitor) throws Exception {
		SubProgressMonitor progressMonitor = new SubProgressMonitor(monitor,
				IProgressMonitor.UNKNOWN);
		IPath computeBinFilePath = ResourceUtil.computeBinFileFulllPath(origFile);
		IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(computeBinFilePath);
		try {
			if (file.exists()) {
				file.setContents(handleHtmlGeneration, true, true, progressMonitor);
			} else {
				file.create(handleHtmlGeneration, true, progressMonitor);
			}
			file.setDerived(true);
			return file;
		} catch (Exception e) {
			throw e;
		} finally {
			if (handleHtmlGeneration != null) {
				StreamCloser.closeStreams(handleHtmlGeneration);
			}
		}
	}

}
