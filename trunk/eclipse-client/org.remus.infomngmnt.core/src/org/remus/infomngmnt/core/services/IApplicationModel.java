package org.remus.infomngmnt.core.services;

import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import org.remus.infomngmnt.ApplicationRoot;
import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.InformationUnitListItem;

public interface IApplicationModel {

	void removeFromModel(final IProject project) throws CoreException;

	void addToModel(final IProject project);

	ApplicationRoot getModel();

	void addListenerToCategory(final Category category);

	Map<String, InformationUnitListItem> getAllItems(final IProgressMonitor monitor);

	InformationUnitListItem getItemById(final String id, final IProgressMonitor monitor);

	InformationUnitListItem getItemByIdLocalDeletedIncluded(final String id,
			final IProgressMonitor monitor);

	void clearCache();

	void setEditService(final IEditingHandler service);

}