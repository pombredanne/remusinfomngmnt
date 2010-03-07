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

package org.remus.infomngmnt.ui.newwizards;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.common.ui.UIUtil;
import org.remus.infomngmnt.core.commands.CommandFactory;
import org.remus.infomngmnt.core.model.InformationStructureEdit;
import org.remus.infomngmnt.ui.UIPlugin;
import org.remus.infomngmnt.ui.progress.CancelableRunnable;
import org.remus.infomngmnt.util.CategoryUtil;
import org.remus.infomngmnt.util.IInfoObjectSetter;

/**
 * <p>
 * This class provides some basic key-features of the wizard which can be used
 * for creating new information units.
 * </p>
 * <p>
 * This class will create a new {@link InformationUnit} object, a
 * {@link GeneralPage} and will try to create a new information unit within the
 * workspace.
 * </p>
 * 
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public abstract class NewInfoObjectWizard extends Wizard implements INewWizard {

	protected GeneralPage page1;

	protected InformationUnit newElement;

	private String categoryString;

	private IFile[] files;

	/**
	 * 
	 */
	public NewInfoObjectWizard() {
		setNeedsProgressMonitor(true);
		this.newElement = createNewInformationUnit();
	}

	@Override
	public void addPages() {
		addPage(this.page1);
	}

	/**
	 * Creates the new information object with the text values created from
	 * 
	 * @return
	 */
	protected InformationUnit createNewInformationUnit() {
		return InformationStructureEdit.newSession(getInfoTypeId()).newInformationUnit();
	}

	/**
	 * Returns the {@link Category} object based on the
	 * {@link GeneralPage#getCategoryString()}
	 * 
	 * @return the category
	 */
	protected Category findCategory() {
		String parentCategoryValue = this.page1.getCategoryString();
		return CategoryUtil.findCategory(parentCategoryValue, false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		final CompoundCommand[] createInfotype = new CompoundCommand[1];

		try {
			getContainer().run(true, true, new CancelableRunnable() {
				@Override
				protected IStatus runCancelableRunnable(final IProgressMonitor monitor) {
					if (findCategory() == null) {
						Command createCATEGORY = CommandFactory
								.CREATE_CATEGORY(
										NewInfoObjectWizard.this.page1.getCategoryString(),
										UIPlugin.getDefault().getEditService()
												.getNavigationEditingDomain());
						if (createCATEGORY != null) {
							UIPlugin.getDefault().getEditService().getNavigationEditingDomain()
									.getCommandStack().execute(createCATEGORY);
						}
					}
					createInfotype[0] = CommandFactory.CREATE_INFOTYPE(
							NewInfoObjectWizard.this.newElement, findCategory(), monitor);
					Command additionalCommands = getAdditionalCommands();
					if (additionalCommands != null && additionalCommands.canExecute()) {
						createInfotype[0].append(additionalCommands);
					}
					return Status.OK_STATUS;
				}
			});
		} catch (InvocationTargetException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		UIPlugin.getDefault().getEditService().getNavigationEditingDomain().getCommandStack()
				.execute(createInfotype[0]);
		performActionAfterCreation();
		// we also reveal the created list-item, that can be found in the
		// navigation
		UIUtil.selectAndReveal(this.newElement.getAdapter(InformationUnitListItem.class),
				PlatformUI.getWorkbench().getActiveWorkbenchWindow());
		UIUtil.selectAndReveal(this.newElement, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow());
		return true;
	}

	protected void performActionAfterCreation() {
		// does nothing by default;
	}

	@Override
	public IWizardPage getStartingPage() {
		IWizardPage startingPage = super.getStartingPage();
		if (startingPage instanceof IInfoObjectSetter) {
			((IInfoObjectSetter) startingPage).setInformationUnit(this.newElement);
		}
		return startingPage;
	}

	@Override
	public IWizardPage getNextPage(final IWizardPage page) {
		IWizardPage nextPage = super.getNextPage(page);
		if (nextPage instanceof IInfoObjectSetter) {
			((IInfoObjectSetter) nextPage).setInformationUnit(this.newElement);
		}
		return nextPage;
	}

	protected Command getAdditionalCommands() {
		// does nothing by default
		return null;
	}

	/**
	 * initializes the wizard with the Workbench selection. At this time the
	 * Wizard pages are created with the selected objects.
	 * 
	 * @param workbench
	 *            the current workbench
	 * @param selection
	 *            the current object selection
	 */
	public void init(final IWorkbench workbench, final IStructuredSelection selection) {
		Object firstElement = selection.getFirstElement();
		if (firstElement instanceof Category) {
			this.page1 = new GeneralPage((Category) firstElement);
		} else if (firstElement instanceof InformationUnitListItem) {
			this.page1 = new GeneralPage((InformationUnitListItem) firstElement);
		} else {
			this.page1 = new GeneralPage((Category) null);
		}
		if (this.categoryString != null) {
			this.page1.setCategoryString(this.categoryString);
		}
		if (this.files != null) {
			this.page1.setFiles(this.files);
		}

	}

	protected void setCategoryToPage() {
		if (this.categoryString != null) {
			this.page1.setCategoryString(this.categoryString);
		}

	}

	protected abstract String getInfoTypeId();

	@Override
	public void createPageControls(final Composite pageContainer) {
		// do nothing
	}

	public void setNewInformationUnit(final InformationUnit newInformationUnit) {
		this.newElement = newInformationUnit;

	}

	public void setFiles(final IFile[] files) {
		this.files = files;
	}

	/**
	 * @param categoryString
	 *            the categoryString to set
	 */
	public void setCategoryString(final String categoryString) {
		this.categoryString = categoryString;
	}

}