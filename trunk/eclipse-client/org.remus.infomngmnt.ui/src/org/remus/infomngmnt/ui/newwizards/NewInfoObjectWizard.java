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
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.RuleValue;
import org.remus.infomngmnt.common.ui.UIUtil;
import org.remus.infomngmnt.core.commands.CommandFactory;
import org.remus.infomngmnt.core.extension.IInfoType;
import org.remus.infomngmnt.core.extension.InformationExtensionManager;
import org.remus.infomngmnt.core.model.CategoryUtil;
import org.remus.infomngmnt.core.model.EditingUtil;
import org.remus.infomngmnt.core.progress.CancelableRunnable;
import org.remus.infomngmnt.ui.editors.InformationEditor;
import org.remus.infomngmnt.ui.editors.InformationEditorInput;
import org.remus.infomngmnt.ui.rules.ICreationTrigger;

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
public abstract class NewInfoObjectWizard extends Wizard implements INewWizard, ICreationTrigger {

	protected GeneralPage page1;

	private Object value;

	private RuleValue ruleValue;

	protected InformationUnit newElement;

	private String categoryString;

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
		IInfoType infoTypeByType = InformationExtensionManager.getInstance().getInfoTypeByType(
				getInfoTypeId());
		if (infoTypeByType != null) {
			return infoTypeByType.getCreationFactory().createNewObject();
		}
		return null;
	}

	/**
	 * Returns the {@link Category} object based on the
	 * {@link GeneralPage#getCategoryString()}
	 * 
	 * @return the category
	 */
	protected Category findCategory() {
		String parentCategoryValue = this.page1.getCategoryString();
		return CategoryUtil.findCategory(parentCategoryValue, true);
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
					createInfotype[0] = CommandFactory.CREATE_INFOTYPE(
							NewInfoObjectWizard.this.newElement, findCategory(), monitor);
					Command additionalCommands = getAdditionalCommands();
					if (additionalCommands != null) {
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
		EditingUtil.getInstance().getNavigationEditingDomain().getCommandStack().execute(
				createInfotype[0]);
		try {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(
					new InformationEditorInput((IFile) this.newElement.getAdapter(IFile.class)),
					InformationEditor.ID);
		} catch (Exception e) {
			// will come soon.
		}
		// we also reveal the created list-item, that can be found in the
		// navigation
		UIUtil.selectAndReveal(this.newElement.getAdapter(InformationUnitListItem.class),
				PlatformUI.getWorkbench().getActiveWorkbenchWindow());
		UIUtil.selectAndReveal(this.newElement, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow());
		return true;
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

	public void handleCreationRequest() {
		init(UIUtil.getPrimaryWindow().getWorkbench(), new StructuredSelection(new Object[0]));
		setDefaults(this.value, this.ruleValue);
		WizardDialog wizard = new WizardDialog(UIUtil.getPrimaryWindow().getShell(), this);
		wizard.open();
	}

	protected void setDefaults(final Object value, final RuleValue ruleValue) {
		// does nothing by default

	}

	public void setNewInformationUnit(final InformationUnit newInformationUnit) {
		this.newElement = newInformationUnit;

	}

	public void setRuleValue(final RuleValue ruleValue) {
		this.ruleValue = ruleValue;

	}

	public void setValue(final Object value) {
		this.value = value;

	}

	/**
	 * @param categoryString
	 *            the categoryString to set
	 */
	public void setCategoryString(final String categoryString) {
		this.categoryString = categoryString;
	}
}