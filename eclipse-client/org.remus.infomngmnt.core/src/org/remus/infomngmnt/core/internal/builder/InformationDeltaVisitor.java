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
import org.remus.infomngmnt.core.edit.DisposableEditingDomain;
import org.remus.infomngmnt.core.extension.AbstractInformationRepresentation;
import org.remus.infomngmnt.core.extension.IInfoType;
import org.remus.infomngmnt.core.services.IApplicationModel;
import org.remus.infomngmnt.core.services.IBinaryReferenceStore;
import org.remus.infomngmnt.core.services.IHtmlGenerationErrorGenerator;
import org.remus.infomngmnt.core.services.IInformationTypeHandler;
import org.remus.infomngmnt.core.services.IReferencedUnitStore;
import org.remus.infomngmnt.core.services.ISaveParticipantExtensionService;
import org.remus.infomngmnt.provider.InfomngmntEditPlugin;
import org.remus.infomngmnt.resources.util.ResourceUtil;
import org.remus.infomngmnt.util.CategoryUtil;
import org.remus.infomngmnt.util.StatusCreator;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class InformationDeltaVisitor implements IResourceDeltaVisitor {

	private final ISaveParticipantExtensionService saveParticipantService;

	private IProgressMonitor monitor;

	private final IReferencedUnitStore referenceService;

	private final IBinaryReferenceStore binaryReferenceService;

	private final IApplicationModel applicationService;

	private final IInformationTypeHandler informationTypeHandler;

	public InformationDeltaVisitor() {
		this.referenceService = InfomngmntEditPlugin.getPlugin().getService(
				IReferencedUnitStore.class);
		this.saveParticipantService = InfomngmntEditPlugin.getPlugin().getService(
				ISaveParticipantExtensionService.class);
		this.binaryReferenceService = InfomngmntEditPlugin.getPlugin().getService(
				IBinaryReferenceStore.class);
		this.applicationService = InfomngmntEditPlugin.getPlugin().getService(
				IApplicationModel.class);
		this.informationTypeHandler = InfomngmntEditPlugin.getPlugin().getService(
				IInformationTypeHandler.class);
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
			if (resourceDelta.getResource().getParent() != null
					&& resourceDelta.getResource().getParent().getName().equals(
							ResourceUtil.BINARY_FOLDER)) {
				// referenced binaries. we have to track the changes
				switch (resourceDelta.getKind()) {
				case IResourceDelta.CHANGED:
					String infoUnit = this.binaryReferenceService.getReferencedInfoUnitIdByPath(
							resourceDelta.getResource().getProject().getName(), resourceDelta
									.getResource().getName());
					if (infoUnit != null) {
						InformationUnitListItem itemById = this.applicationService.getItemById(
								infoUnit, this.monitor);
						if (itemById != null) {
							IInfoType refInfoType = this.informationTypeHandler
									.getInfoTypeByType(itemById.getType());
							InformationUnit adapter = (InformationUnit) itemById
									.getAdapter(InformationUnit.class);
							buildSingleInfoUnit(adapter, refInfoType, (IFile) adapter
									.getAdapter(IFile.class));
						}
					}
				}
				return;
			}

			if (resourceDelta.getResource() != null
					&& resourceDelta.getResource().getType() == IResource.FILE
					&& resourceDelta.getResource().getFileExtension().equals(
							ResourceUtil.FILE_EXTENSION)) {
				if (resourceDelta.getResource().exists()) {
					objectFromFile = InfomngmntEditPlugin.getPlugin().getEditService()
							.getObjectFromFile((IFile) resourceDelta.getResource(),
									InfomngmntPackage.eINSTANCE.getInformationUnit(), null, false);
					infoTypeByType = this.informationTypeHandler.getInfoTypeByType(objectFromFile
							.getType());

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
							previousVersion = org.remus.infomngmnt.common.core.util.ResourceUtil
									.getPreviousVersion((IFile) resourceDelta.getResource(),
											this.monitor);
							if (previousVersion != null) {
								InformationUnit objectFromUri = InfomngmntEditPlugin.getPlugin()
										.getEditService().getObjectFromFileUri(
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
					this.binaryReferenceService.delete(lastSegment);
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
			this.binaryReferenceService.update(objectFromFile);
			AbstractInformationRepresentation informationRepresentation = infoTypeByType
					.getInformationRepresentation();
			informationRepresentation.setValue(objectFromFile);
			File previousVersionFile = org.remus.infomngmnt.common.core.util.ResourceUtil
					.getPreviousVersion(resource, this.monitor);
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
					InputStream handleHtmlGeneration = informationRepresentation
							.handleHtmlGeneration(this.monitor);
					writeContent = writeContent(resource, handleHtmlGeneration, this.monitor);
				} catch (Exception e) {
					if (infoTypeByType.isBuildHtml()) {
						IHtmlGenerationErrorGenerator service = InfomngmntEditPlugin.getPlugin()
								.getService(IHtmlGenerationErrorGenerator.class);
						if (service != null) {
							InputStream buildErrorRepresentation = service
									.buildErrorRepresentation(objectFromFile, e);
							writeContent(resource, buildErrorRepresentation, this.monitor);
						}
					}
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

			} catch (Exception e1) {
				// we skip this
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
				DisposableEditingDomain domain = InfomngmntEditPlugin.getPlugin().getEditService()
						.createNewEditingDomain();
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
