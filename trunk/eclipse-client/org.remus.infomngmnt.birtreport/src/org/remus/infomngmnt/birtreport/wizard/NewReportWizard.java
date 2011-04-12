/*******************************************************************************
 * Copyright (c) 2009 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/

package org.remus.infomngmnt.birtreport.wizard;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.command.Command;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.remus.Category;
import org.eclipse.remus.InformationUnit;
import org.eclipse.remus.InformationUnitListItem;
import org.eclipse.remus.common.core.streams.StreamCloser;
import org.eclipse.remus.common.core.util.ResourceUtil;
import org.eclipse.remus.core.commands.CommandFactory;
import org.eclipse.remus.core.model.InformationStructureEdit;
import org.eclipse.remus.core.services.IEditingHandler;
import org.eclipse.remus.ui.newwizards.NewInfoObjectWizard;
import org.eclipse.ui.IWorkbench;
import org.remus.infomngmnt.birtreport.ReportActivator;
import org.remus.infomngmnt.birtreport.extension.IReportTemplate;
import org.remus.infomngmnt.birtreport.messages.Messages;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class NewReportWizard extends NewInfoObjectWizard {

	private ParameterSelectionPage page2;
	private boolean templateSelected;
	private IReportTemplate selectedTemplage;
	private IEditingHandler service;

	/**
	 * 
	 */
	public NewReportWizard() {
		setWindowTitle(Messages.NewReportWizard_NewReport);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench,
	 * org.eclipse.jface.viewers.IStructuredSelection)
	 */
	@Override
	public void init(final IWorkbench workbench,
			final IStructuredSelection selection) {
		this.service = ReportActivator.getDefault().getServiceTracker()
				.getService(IEditingHandler.class);
		Object firstElement = selection.getFirstElement();
		if (firstElement instanceof Category) {
			this.page1 = new GeneralReportPage((Category) firstElement);
		} else if (firstElement instanceof InformationUnitListItem) {
			this.page1 = new GeneralReportPage(
					(InformationUnitListItem) firstElement);
		} else {
			this.page1 = new GeneralReportPage((Category) null);
		}

	}

	@Override
	public void addPages() {
		super.addPages();
		addPage(this.page2 = new ParameterSelectionPage());
	}

	@Override
	public IWizardPage getNextPage(final IWizardPage page) {
		if (page == this.page1
				&& ((GeneralReportPage) this.page1).isTemplateSelected()) {
			this.page2
					.setTemplate((IReportTemplate) ((IStructuredSelection) ((GeneralReportPage) this.page1)
							.getTemplateViewer().getSelection())
							.getFirstElement());
			return super.getNextPage(page);
		}
		return null;
	}

	@Override
	public boolean canFinish() {

		return this.page2.isPageComplete();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.ui.newwizards.NewInfoObjectWizard#getInfoTypeId()
	 */
	@Override
	protected String getInfoTypeId() {
		return ReportActivator.INFOTYPE_ID;
	}

	@Override
	public boolean performFinish() {
		this.templateSelected = ((GeneralReportPage) this.page1)
				.isTemplateSelected();
		if (this.templateSelected) {
			this.selectedTemplage = this.page2.getTemplate();
		}
		return super.performFinish();
	}

	@Override
	protected Command getAdditionalCommands() {
		if (this.templateSelected) {
			IFile createTempFile = ResourceUtil.createTempFile("rptdesign"); //$NON-NLS-1$
			InputStream createStream;
			try {
				createStream = this.selectedTemplage.createStream();
				createTempFile.setContents(createStream, true, false,
						new NullProgressMonitor());
				StreamCloser.closeStreams(createStream);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Map<String, String> parameterValues = this.page2
					.getParameterValues();
			Set<String> keySet = parameterValues.keySet();
			InformationStructureEdit edit = InformationStructureEdit
					.newSession(ReportActivator.INFOTYPE_ID);
			for (String string : keySet) {
				InformationUnit params = edit.createSubType(
						ReportActivator.NODE_NAME_PARAM_NAME, string);
				edit.setValue(params, ReportActivator.NODE_NAME_PARAM_VALUE,
						parameterValues.get(string));
				edit.addDynamicNode(this.newElement, params, null);
			}
			return CommandFactory.addFileToInfoUnit(createTempFile,
					this.newElement, this.service.getNavigationEditingDomain());

		}
		return super.getAdditionalCommands();
	}

	@Override
	public void dispose() {
		ReportActivator.getDefault().getServiceTracker()
				.ungetService(this.service);
		super.dispose();
	}

}
