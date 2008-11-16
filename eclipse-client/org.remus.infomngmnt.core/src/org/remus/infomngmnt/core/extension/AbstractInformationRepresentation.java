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
import java.util.List;

import org.eclipse.core.resources.IFile;
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
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public abstract class AbstractInformationRepresentation {

	private InformationUnit value;

	private File previousVersion;

	private List<DiffElement> differences;

	public AbstractInformationRepresentation() {
		super();
	}

	public void handlePreBuild(IProgressMonitor monitor) {
		// does nothing by default
	}

	/**
	 * Executed after the serialization of the Info-Object. Useful
	 * @param derivedFile
	 */
	public void handlePostBuild(IFile derivedFile, IProgressMonitor monitor) throws CoreException {
		// does nothing by default.
	}

	public abstract String handleHtmlGeneration(IProgressMonitor monitor) throws CoreException;

	public abstract String getTitleForIndexing(IProgressMonitor monitor) throws CoreException;

	public abstract String getBodyForIndexing(IProgressMonitor monitor) throws CoreException;

	public abstract String getAdditionalsForIndexing(IProgressMonitor monitor) throws CoreException;

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

	public final void setPreviousVersion(File previousVersion) {
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
					new Path(this.previousVersion.getAbsolutePath()), InfomngmntPackage.Literals.INFORMATION_UNIT, false);
			this.differences = InformationUtil.computeDiffs(previousModel, getValue());
		}
		return this.differences;
	}

	protected boolean isChanged(EAttribute attribute) {
		AttributeChange attributeChange = InformationUtil.getAttributeChange(this.differences, attribute);
		return attributeChange != null;
	}




}
