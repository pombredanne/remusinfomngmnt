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
package org.remus.infomngmnt.core.extension;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.compare.diff.metamodel.AttributeChange;
import org.eclipse.emf.compare.diff.metamodel.DiffElement;
import org.eclipse.emf.ecore.EAttribute;

import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.core.model.EditingUtil;
import org.remus.infomngmnt.core.model.InformationUtil;

/**
 * <p>
 * This class is the basic implementation for the integration of
 * information-types. This class is normally called by the default builder for
 * information types.
 * </p>
 * <p>
 * The information object is stored as {@link IFile} within the workspace. After
 * a write operation where the the information-object is affected, the specific
 * builder for information units asks
 * </p>
 * <p>
 * In addition the search engine asks the info-type specific implementation of
 * this class for relevant informations, which become indexed.
 * </p>
 * <p>
 * In every case you have to implement a subtype of this class, it is essential
 * for the integration of a new info-type.
 * </p>
 * 
 * @author Tom Seidel <tom.seidel@remus-software.org>
 * @since 1.0
 */
public abstract class AbstractInformationRepresentation {

	/** The information unit to deal with **/
	private InformationUnit value;

	/** The previous version of the file, if present **/
	private File previousVersion;

	/** The calculated differences **/
	private List<DiffElement> differences;

	private IFolder folder;

	public AbstractInformationRepresentation() {
		super();
	}

	/**
	 * <p>
	 * Executed before building the info-object. Useful for executing operations
	 * which are required to serialize information object
	 * </p>
	 * 
	 * @param monitor
	 *            The progressmonitor
	 */
	public void handlePreBuild(final IProgressMonitor monitor) {
		// does nothing by default
	}

	/**
	 * Executed after the serialization of the info-object. Uselful
	 * 
	 * @param derivedFile
	 *            the file
	 * @param monitor
	 *            The progressmonitor
	 * @throws CoreException
	 *             if an exception occurs.
	 */
	public void handlePostBuild(final IFile derivedFile, final IProgressMonitor monitor)
			throws CoreException {
		// does nothing by default.
	}

	/**
	 * @param monitor
	 * @return
	 * @throws CoreException
	 */
	public abstract InputStream handleHtmlGeneration(IProgressMonitor monitor) throws CoreException;

	/**
	 * Returns the string that is used for as title in the search-result. Mostly
	 * it is the same the {@link InformationUnit#getLabel()} would return. It is
	 * the highest ranked field in the search.
	 * 
	 * @param monitor
	 *            the progressmonitor.
	 * @return the String which is inexed
	 * @throws CoreException
	 *             if an exception occurs.
	 */
	public String getTitleForIndexing(final IProgressMonitor monitor) throws CoreException {
		return getValue().getLabel();
	}

	/**
	 * Returns an "indexable representation" of the information object.
	 * 
	 * @param monitor
	 *            the progressmonitor
	 * @return the String which is indexed.
	 * @throws CoreException
	 *             if an exception occurs.
	 */
	public abstract String getBodyForIndexing(IProgressMonitor monitor) throws CoreException;

	/**
	 * Returns an additional String for indexing. Useful for instance if you
	 * 
	 * @param monitor
	 * @return
	 * @throws CoreException
	 */
	public String getAdditionalsForIndexing(final IProgressMonitor monitor) throws CoreException {
		return "";
	}

	public InformationUnit getValue() {
		return this.value;
	}

	public final void setValue(final InformationUnit value) {
		this.value = value;
		unsetDifferences();
	}

	public File getPreviousVersion() {
		return this.previousVersion;
	}

	public final void setPreviousVersion(final File previousVersion) {
		this.previousVersion = previousVersion;
		unsetDifferences();
	}

	private void unsetDifferences() {
		this.differences = null;

	}

	protected IFile getFile() {
		return ResourcesPlugin.getWorkspace().getRoot().getFile(
				new Path(getValue().eResource().getURI().toPlatformString(true)));
	}

	protected List<DiffElement> getDifferences() {
		if (getPreviousVersion() != null && this.differences == null) {
			InformationUnit previousModel = EditingUtil.getInstance().getObjectFromUri(
					new Path(this.previousVersion.getAbsolutePath()),
					InfomngmntPackage.Literals.INFORMATION_UNIT);
			this.differences = InformationUtil.computeDiffs(previousModel, getValue());
		}
		return this.differences;
	}

	protected boolean isChanged(final EAttribute attribute) {
		AttributeChange attributeChange = InformationUtil.getAttributeChange(this.differences,
				attribute);
		return attributeChange != null;
	}

	public boolean createFolderOnBuild() {
		return false;
	}

	public void setBuildFolder(final IFolder folder) {
		this.folder = folder;
	}

	protected IFolder getBuildFolder() {
		if (!createFolderOnBuild()) {
			throw new IllegalStateException(
					"Access on the build folder requires a true returnValue on Method #createFolderOnBuild");
		}
		return this.folder;
	}

}
