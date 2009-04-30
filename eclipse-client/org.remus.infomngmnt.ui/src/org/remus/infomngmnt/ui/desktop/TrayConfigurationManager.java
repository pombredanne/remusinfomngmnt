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

package org.remus.infomngmnt.ui.desktop;

import java.io.File;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import org.remus.infomngmnt.common.ui.extension.ITraySectionDefinition;
import org.remus.infomngmnt.common.ui.extension.TraySectionManager;
import org.remus.infomngmnt.core.services.IRuleService;
import org.remus.infomngmnt.ui.UIPlugin;
import org.remus.infomngmnt.ui.tray.DropSectionPreferencePage;
import org.remus.infomngmt.common.ui.uimodel.TraySection;
import org.remus.infomngmt.common.ui.uimodel.TraySectionCollection;
import org.remus.infomngmt.common.ui.uimodel.UIModelFactory;
import org.remus.infomngmt.common.ui.uimodel.UIModelPackage;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class TrayConfigurationManager {

	public static final String SEARCH_BOX_ID = "org.remus.infomngmnt.ui.searchBox"; //$NON-NLS-1$
	public static final String DROP_BOX_ID = "org.remus.infomngmnt.ui.dropBox"; //$NON-NLS-1$

	public static final String PATH_TO_TRAY_CONFIG_FILE = "tray/trayconfig.xml"; //$NON-NLS-1$

	private TraySectionCollection traySections;

	private static TrayConfigurationManager INSTANCE;

	public static TrayConfigurationManager getInstance() {
		if (INSTANCE == null) {
			synchronized (TrayConfigurationManager.class) {
				if (INSTANCE == null) {
					INSTANCE = new TrayConfigurationManager();
				}
			}
		}
		return INSTANCE;
	}

	private TrayConfigurationManager() {
		init();
	}

	private void init() {
		IPath target = UIPlugin.getDefault().getStateLocation().append(
				new Path(PATH_TO_TRAY_CONFIG_FILE));
		final org.eclipse.emf.common.util.URI createURI = org.eclipse.emf.common.util.URI
				.createFileURI(target.toString());
		ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getPackageRegistry().put(UIModelPackage.eNS_URI, UIModelPackage.eINSTANCE);
		File file = new File(createURI.toFileString());
		if (file.exists()) {
			Resource resource = resourceSet.getResource(createURI, true);
			this.traySections = (TraySectionCollection) resource.getContents().get(0);
			for (TraySection traySection : this.traySections.getSections()) {
				ITraySectionDefinition sectionDefinitionById = TraySectionManager.getInstance()
						.getSectionDefinitionById(traySection.getTemplateId());
				traySection.setImplementation(sectionDefinitionById.getImplementation());
			}
		} else {
			Resource resource = resourceSet.createResource(createURI);
			this.traySections = buildInitialSectionConfiguration();
			resource.getContents().add(this.traySections);
		}
	}

	private TraySectionCollection buildInitialSectionConfiguration() {
		TraySectionCollection returnValue = UIModelFactory.eINSTANCE.createTraySectionCollection();

		// Search-Box.
		ITraySectionDefinition sectionDefinitionById = TraySectionManager.getInstance()
				.getSectionDefinitionById(SEARCH_BOX_ID);
		TraySection searchTraySection = UIModelFactory.eINSTANCE.createTraySection();
		searchTraySection.setImage(sectionDefinitionById.getImage());
		searchTraySection.setImplementation(sectionDefinitionById.getImplementation());
		searchTraySection.setName(sectionDefinitionById.getLabel());
		searchTraySection.setTemplateId(sectionDefinitionById.getId());
		returnValue.getSections().add(searchTraySection);
		// Drop-Box
		ITraySectionDefinition dropBoxDefinition = TraySectionManager.getInstance()
				.getSectionDefinitionById(DROP_BOX_ID);
		TraySection dropTraySection = UIModelFactory.eINSTANCE.createTraySection();
		dropTraySection.setImage(dropBoxDefinition.getImage());
		dropTraySection.setImplementation(dropBoxDefinition.getImplementation());
		dropTraySection.setName(dropBoxDefinition.getLabel());
		dropTraySection.setTemplateId(dropBoxDefinition.getId());
		dropTraySection.getPreferenceOptions().put(DropSectionPreferencePage.RULESET_KEY,
				IRuleService.DEFAULT_RULENAME);
		returnValue.getSections().add(dropTraySection);

		return returnValue;
	}

	public TraySectionCollection getTraySections() {
		return this.traySections;
	}

}
