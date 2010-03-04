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

package org.remus.infomngmnt.ui.editors;

import java.net.URI;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IPathEditorInput;
import org.eclipse.ui.IPersistableElement;
import org.eclipse.ui.model.IWorkbenchAdapter;

import org.remus.infomngmnt.AbstractInformationUnit;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.ui.editor.EditorActivator;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class InformationEditorInput implements IPathEditorInput, IPersistableElement {

	private final IFile file;

	/**
	 * Return whether or not file is local. Only {@link IFile}s with a local
	 * value should call {@link IPathEditorInput#getPath()}
	 * 
	 * @param file
	 * @return boolean <code>true</code> if the file has a local implementation.
	 * @since 3.4
	 */
	public static boolean isLocalFile(final IFile file) {

		IPath location = file.getLocation();
		if (location != null)
			return true;
		// this is not a local file, so try to obtain a local file
		try {
			final URI locationURI = file.getLocationURI();
			if (locationURI == null)
				return false;
			IFileStore store = EFS.getStore(locationURI);
			// first try to obtain a local file directly fo1r this store
			java.io.File localFile = store.toLocalFile(EFS.NONE, null);
			// if no local file is available, obtain a cached file
			if (localFile == null)
				localFile = store.toLocalFile(EFS.CACHE, null);
			if (localFile == null)
				return false;
			return true;
		} catch (CoreException e) {
			// this can only happen if the file system is not available for this
			// scheme

			return false;
		}

	}

	private String type;
	private String label;

	public InformationEditorInput(final InformationUnitListItem infoFile) {
		this(ResourcesPlugin.getWorkspace().getRoot()
				.getFile(new Path(infoFile.getWorkspacePath())));
		setLabels(infoFile);
	}

	public InformationEditorInput(final IFile file) {
		this.file = file;
		InformationUnit objectFromFile = EditorActivator.getDefault().getEditService()
				.getObjectFromUri(file.getFullPath(),
						InfomngmntPackage.eINSTANCE.getInformationUnit(), false, null, false);
		setLabels(objectFromFile);
		objectFromFile.eResource().unload();

	}

	private void setLabels(final AbstractInformationUnit unit) {
		this.type = unit.getType();
		this.label = unit.getLabel();
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		// IInfoType infoTypeByType =
		// InformationExtensionManager.getInstance().getInfoTypeByType(
		// this.type);
		// return infoTypeByType.getImageDescriptor();
		// FIXME
		return null;
	}

	@Override
	public String getName() {
		return this.label;
	}

	@Override
	public String getToolTipText() {
		return this.label;
	}

	@Override
	public String getFactoryId() {
		return InformationEditorCreationFactory.FACTORY_ID;
	}

	/*
	 * (non-Javadoc) Method declared on IPersistableElement.
	 */
	public void saveState(final IMemento memento) {
		InformationEditorCreationFactory.saveState(memento, this);
	}

	/*
	 * (non-Javadoc) Method declared on Object.
	 */
	@Override
	public int hashCode() {
		return this.file.hashCode();
	}

	/*
	 * (non-Javadoc) Method declared on Object.
	 * 
	 * The <code>FileEditorInput</code> implementation of this
	 * <code>Object</code> method bases the equality of two
	 * <code>FileEditorInput</code> objects on the equality of their underlying
	 * <code>IFile</code> resources.
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof InformationEditorInput)) {
			return false;
		}
		InformationEditorInput other = (InformationEditorInput) obj;
		return this.file.equals(other.getFile());
	}

	/*
	 * (non-Javadoc) Method declared on IFileEditorInput.
	 */
	public IFile getFile() {
		return this.file;
	}

	/*
	 * Allows for the return of an {@link IWorkbenchAdapter} adapter.
	 * 
	 * @since 3.5
	 * 
	 * @see org.eclipse.core.runtime.PlatformObject#getAdapter(java.lang.Class)
	 */
	public Object getAdapter(final Class adapter) {
		if (IWorkbenchAdapter.class.equals(adapter)) {
			return new IWorkbenchAdapter() {

				public Object[] getChildren(final Object o) {
					return new Object[0];
				}

				public ImageDescriptor getImageDescriptor(final Object object) {
					return InformationEditorInput.this.getImageDescriptor();
				}

				public String getLabel(final Object o) {
					return InformationEditorInput.this.getName();
				}

				public Object getParent(final Object o) {
					return InformationEditorInput.this.getFile().getParent();
				}
			};
		}
		if (IFile.class.equals(adapter)) {
			return this.file;
		}

		return null;
	}

	@Override
	public IPath getPath() {
		IPath location = this.file.getLocation();
		if (location != null)
			return location;
		// this is not a local file, so try to obtain a local file
		try {
			final URI locationURI = this.file.getLocationURI();
			if (locationURI == null)
				throw new IllegalArgumentException();
			IFileStore store = EFS.getStore(locationURI);
			// first try to obtain a local file directly fo1r this store
			java.io.File localFile = store.toLocalFile(EFS.NONE, null);
			// if no local file is available, obtain a cached file
			if (localFile == null)
				localFile = store.toLocalFile(EFS.CACHE, null);
			if (localFile == null)
				throw new IllegalArgumentException();
			return Path.fromOSString(localFile.getAbsolutePath());
		} catch (CoreException e) {
			// this can only happen if the file system is not available for this
			// scheme

			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean exists() {
		return this.file.exists();
	}

	@Override
	public IPersistableElement getPersistable() {
		return this;
	}

}
