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

import java.io.ByteArrayInputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
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
			if (resourceDelta.getResource().getType() == IResource.FOLDER
					&& resourceDelta.getResource().getParent().getType() == IResource.PROJECT
					&& ResourceUtil.SETTINGS_FOLDER.equals(resourceDelta.getResource().getName())) {
				return;
			}

			if (resourceDelta.getResource() != null
					&& resourceDelta.getResource().getType() == IResource.FILE
					&& resourceDelta.getResource().getFileExtension().equals(ResourceUtil.FILE_EXTENSION)) {
				if (resourceDelta.getResource().exists()) {
					objectFromFile = EditingUtil.getInstance().getObjectFromFile((IFile) resourceDelta.getResource(), InfomngmntPackage.eINSTANCE.getInformationUnit(), false);
					infoTypeByType = InformationExtensionManager.getInstance().getInfoTypeByType(objectFromFile.getType());

				}
				switch (resourceDelta.getKind()) {
				case IResourceDelta.REPLACED:
				case IResourceDelta.ADDED:
				case IResourceDelta.CHANGED:
					try {
						AbstractInformationRepresentation informationRepresentation = infoTypeByType.getInformationRepresentation();
						informationRepresentation.setValue(objectFromFile);
						String handleHtmlGeneration = infoTypeByType.getInformationRepresentation().handleHtmlGeneration(this.monitor);
						IFile writeContent = null;
						try {
							writeContent = writeContent((IFile) resourceDelta.getResource(), handleHtmlGeneration, this.monitor);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						informationRepresentation.handlePostBuild(writeContent, this.monitor);

					} catch (CoreException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;

				case IResourceDelta.REMOVED:
					break;
				}
			}
			visitRecursively(resourceDelta);
		}
	}

	public void setMonitor(IProgressMonitor monitor) {
		this.monitor = monitor;

	}

	private IFile writeContent(IFile origFile, String content, IProgressMonitor monitor) throws Exception {
		SubProgressMonitor progressMonitor = new SubProgressMonitor(monitor, IProgressMonitor.UNKNOWN);
		IPath computeBinFilePath = ResourceUtil.computeBinFileFulllPath(origFile);
		IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(computeBinFilePath);
		ByteArrayInputStream byteArrayInputStream = null;
		try {
			byteArrayInputStream = new ByteArrayInputStream(content.getBytes(origFile.getCharset()));
			if (file.exists()) {
				file.setContents(byteArrayInputStream, true,true, progressMonitor);
			} else {
				file.create(byteArrayInputStream, true, progressMonitor);
			}
			file.setDerived(true);
			return file;
		} catch (Exception e) {
			throw e;
		} finally {
			if (byteArrayInputStream != null) {
				try {
					byteArrayInputStream.close();
					byteArrayInputStream = null;
				} catch (Exception e) {
					// do nothing.. we've done our best.
				}
			}
		}
	}




}
