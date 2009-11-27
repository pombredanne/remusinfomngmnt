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

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;
import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.bibliographic.BibliographicActivator;
import org.remus.infomngmnt.bibliographic.extension.BookRepresentation;
import org.remus.infomngmnt.core.model.InformationStructureRead;
import org.remus.infomngmnt.util.CategoryUtil;
import org.remus.infomngmnt.util.EditingUtil;

public class XmlExportWizard extends Wizard implements IExportWizard {

	private XmlExportWizardPage exportPage = null;
	private String sel = null;
	
	public XmlExportWizard() {
		setNeedsProgressMonitor(true);
		setWindowTitle("Export Wizard");
	}
	
	@Override
	public void addPages() {
		exportPage = new XmlExportWizardPage();
		exportPage.setExportElement(sel);
		addPage(exportPage);
	}

	public void init(IWorkbench workbench, IStructuredSelection selection) {
		System.out.println("xmlExportWizard.init()");

		List list = ((IStructuredSelection) selection).toList();
		for (Object object : list) {
			if (object instanceof InformationUnitListItem) {

			} else if (object instanceof Category) {
				sel = ((Category) object).getLabel();
				
				InformationUnitListItem[] allInfoUnitItems = CategoryUtil.getAllInfoUnitItems((Category) object);

				for (InformationUnitListItem informationUnitListItem : allInfoUnitItems) {
					//TODO Log4j verwenden
					System.out.println(informationUnitListItem.getLabel());	
					IFile infUnitFile = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(informationUnitListItem.getWorkspacePath()));
					InformationUnit objectFromFile = EditingUtil.getInstance().getObjectFromUri(
							infUnitFile.getFullPath(), InfomngmntPackage.eINSTANCE.getInformationUnit(), 
							false, null, false);

					if (objectFromFile.getType().toString().equals("BOOK")) {
						System.out.println(BookRepresentation.getXMLExportString(objectFromFile));
					}
				}
			}
		}	
	}

	
	@Override
	public boolean performFinish() {

		
		exportPage.saveSettings();		
		return true;
	}
}
