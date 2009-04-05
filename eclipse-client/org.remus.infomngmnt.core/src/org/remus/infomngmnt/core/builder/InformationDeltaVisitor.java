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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFileState;
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

import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.core.extension.AbstractInformationRepresentation;
import org.remus.infomngmnt.core.extension.IInfoType;
import org.remus.infomngmnt.core.extension.InformationExtensionManager;
import org.remus.infomngmnt.core.model.EditingUtil;
import org.remus.infomngmnt.resources.util.ResourceUtil;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class InformationDeltaVisitor implements IResourceDeltaVisitor {

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
					infoTypeByType = InformationExtensionManager.getInstance().getInfoTypeByType(
							objectFromFile.getType());

				}
				switch (resourceDelta.getKind()) {
				case IResourceDelta.REPLACED:
				case IResourceDelta.ADDED:
				case IResourceDelta.CHANGED:

					if (resourceDelta.getResource().exists() && objectFromFile != null
							&& objectFromFile.getId() != null) {

						buildSingleInfoUnit(objectFromFile, infoTypeByType, (IFile) resourceDelta
								.getResource());
					}

					break;
				case IResourceDelta.REMOVED:
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
			File createTempFile = null;
			AbstractInformationRepresentation informationRepresentation = infoTypeByType
					.getInformationRepresentation();
			informationRepresentation.setValue(objectFromFile);
			IFileState[] history = resource.getHistory(this.monitor);
			if (history.length > 0) {
				InputStream contents;
				FileOutputStream fos = null;
				contents = history[0].getContents();

				try {
					createTempFile = File.createTempFile(objectFromFile.getId(),
							ResourceUtil.FILE_EXTENSION);
					fos = new FileOutputStream(createTempFile);
					byte buf[] = new byte[1024];
					int len;
					while ((len = contents.read(buf)) > 0) {
						fos.write(buf, 0, len);
					}

				} catch (Exception e) {
					informationRepresentation.setPreviousVersion(null);
				} finally {
					if (fos != null) {
						try {
							fos.close();
						} catch (IOException e) {
							// do nothing. we've done our best.
						}
					}
					if (contents != null) {
						try {
							contents.close();
						} catch (IOException e) {
							// do nothing. we've done our best.
						}
					}
				}
				informationRepresentation.setPreviousVersion(createTempFile);
			} else {
				informationRepresentation.setPreviousVersion(null);
			}

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
				InputStream handleHtmlGeneration = infoTypeByType.getInformationRepresentation()
						.handleHtmlGeneration(this.monitor);
				IFile writeContent = null;
				try {
					writeContent = writeContent(resource, handleHtmlGeneration, this.monitor);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				informationRepresentation.handlePostBuild(writeContent, this.monitor);
				if (createTempFile != null) {
					createTempFile.delete();
				}
			}
		} catch (Exception e) {
			try {
				IMarker createMarker = resource.createMarker(IMarker.PROBLEM);
				createMarker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
				createMarker.setAttribute(IMarker.LOCATION, objectFromFile.getLabel());
				createMarker.setAttribute(IMarker.MESSAGE, "Error while saving the inormation "
						+ objectFromFile.getLabel());
			} catch (CoreException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
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
				try {
					handleHtmlGeneration.close();
				} catch (Exception e) {
					// do nothing.. we've done our best.
				}
			}
		}
	}

}
