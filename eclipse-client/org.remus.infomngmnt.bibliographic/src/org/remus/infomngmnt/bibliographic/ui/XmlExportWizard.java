/*******************************************************************************
 * Copyright (c) 2009 Andreas Deinlein
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Andreas Deinlein - bibliographic extensions
 *******************************************************************************/
package org.remus.infomngmnt.bibliographic.ui;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.progress.IProgressService;

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.bibliographic.BibliographicActivator;
import org.remus.infomngmnt.bibliographic.extension.XmlExportOperation;

public class XmlExportWizard extends Wizard implements IExportWizard {

	private XmlExportWizardPage exportPage = null;
	private Category selCategory = null;
	
	private final static String SETTINGS_SECTION = "org.remus.infomngmnt.bibliographic.ui.xmlExportWizard"; //$NON-NLS-1$
	
	public XmlExportWizard() {
		IDialogSettings masterSettings = BibliographicActivator.getDefault().getDialogSettings();
		setDialogSettings(getSettingsSection(masterSettings));
		setNeedsProgressMonitor(true);
		setWindowTitle("BibTeXML export wizard");
	}
	
	/**
	 * Finds or creates a dialog settings section that is used to make the dialog control settings persistent
	 */
	private IDialogSettings getSettingsSection(IDialogSettings master) {
		IDialogSettings settings = master.getSection(SETTINGS_SECTION);
		if (settings == null) {
			settings = master.addNewSection(SETTINGS_SECTION);
		}
		return settings;
	}
	
	@Override
	public void addPages() {
		exportPage = new XmlExportWizardPage();
		exportPage.setExportElement(selCategory);
		addPage(exportPage);
	}

	@SuppressWarnings("unchecked")
	public void init(IWorkbench workbench, IStructuredSelection selection) {

		List list = ((IStructuredSelection) selection).toList();
		for (Object object : list) {
			if (object instanceof Category) {				
				selCategory = (Category) object;
			}
		}	
	}

	
	@Override
	public boolean performFinish() {
		String destFile = exportPage.getDestinationFile();
		selCategory = exportPage.getExportElement();
		XmlExportOperation job = new XmlExportOperation(selCategory, destFile);

		try {
			if (getContainer() != null) {
				getContainer().run(true, true, job);
			} else {
				IProgressService service = PlatformUI.getWorkbench().getProgressService();
				service.run(true, true, job);
			}
		} catch (InvocationTargetException e) {
			Status status = new Status(IStatus.ERROR, BibliographicActivator.PLUGIN_ID, e.getMessage(), e);
			BibliographicActivator.getDefault().getLog().log(status);
		} catch (InterruptedException e) {
			// user canceled
		}
		
		exportPage.saveSettings();		
		return true;
	}
}
