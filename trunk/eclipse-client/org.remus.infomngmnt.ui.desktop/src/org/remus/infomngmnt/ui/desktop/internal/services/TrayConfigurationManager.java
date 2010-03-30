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

package org.remus.infomngmnt.ui.desktop.internal.services;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import org.remus.infomngmnt.model.service.IResourceLoader;
import org.remus.infomngmnt.ui.desktop.extension.ITraySectionDefinition;
import org.remus.infomngmnt.ui.desktop.extension.TraySectionManager;
import org.remus.infomngmnt.ui.desktop.services.ITrayConfigurationService;
import org.remus.infomngmnt.ui.rules.service.IRuleService;
import org.remus.infomngmnt.ui.tray.DropSectionPreferencePage;
import org.remus.uimodel.TraySection;
import org.remus.uimodel.TraySectionCollection;
import org.remus.uimodel.UimodelFactory;
import org.remus.uimodel.UimodelPackage;
import org.remus.uimodel.provider.UimodelEditPlugin;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class TrayConfigurationManager implements ITrayConfigurationService {

	public static final String SEARCH_BOX_ID = "org.remus.infomngmnt.search.desktop.sectionBox1"; //$NON-NLS-1$
	public static final String DROP_BOX_ID = "org.remus.infomngmnt.ui.dropBox"; //$NON-NLS-1$
	public static final String NOTIFICATION_BOX = "org.remus.infomngmnt.ui.notification.tray.sectionBox"; //$NON-NLS-1$

	public static final String PATH_TO_TRAY_CONFIG_FILE = "tray/trayconfig.xmi"; //$NON-NLS-1$

	private TraySectionCollection traySections;

	private transient boolean initialized;

	public TrayConfigurationManager() {
		this.initialized = false;
	}

	protected synchronized void checkForInitialization() {
		if (!this.initialized) {
			init();
			this.initialized = true;
		}
	}

	private void init() {
		IPath target = UimodelEditPlugin.getPlugin().getStateLocation().append(
				new Path(PATH_TO_TRAY_CONFIG_FILE));
		final org.eclipse.emf.common.util.URI createURI = org.eclipse.emf.common.util.URI
				.createFileURI(target.toString());
		File file = new File(createURI.toFileString());
		if (file.exists()) {
			IResourceLoader service = UimodelEditPlugin.getPlugin().getServiceTracker().getService(
					IResourceLoader.class);
			this.traySections = service.getObjectFromResourceUri(target.toOSString(),
					UimodelPackage.Literals.TRAY_SECTION_COLLECTION, Collections.singletonMap(
							UimodelPackage.eNS_URI, UimodelPackage.eINSTANCE));
			for (TraySection traySection : this.traySections.getSections()) {
				ITraySectionDefinition sectionDefinitionById = TraySectionManager.getInstance()
						.getSectionDefinitionById(traySection.getTemplateId());
				traySection.setImplementation(sectionDefinitionById.getImplementation());
			}
		} else {
			ResourceSet resourceSet = new ResourceSetImpl();
			resourceSet.getPackageRegistry().put(UimodelPackage.eNS_URI, UimodelPackage.eINSTANCE);
			Resource resource = resourceSet.createResource(createURI);
			// FIXME remove later
			this.traySections = buildInitialSectionConfiguration();
			resource.getContents().add(this.traySections);
		}
	}

	/**
	 * 
	 * This has to be removed later. It's built if the user has not setup any
	 * tray-sctions
	 * 
	 * @return
	 */
	private TraySectionCollection buildInitialSectionConfiguration() {
		TraySectionCollection returnValue = UimodelFactory.eINSTANCE.createTraySectionCollection();

		// Search-Box.
		ITraySectionDefinition sectionDefinitionById = TraySectionManager.getInstance()
				.getSectionDefinitionById(SEARCH_BOX_ID);
		TraySection searchTraySection = UimodelFactory.eINSTANCE.createTraySection();
		searchTraySection.setImage(sectionDefinitionById.getImage());
		searchTraySection.setImplementation(sectionDefinitionById.getImplementation());
		searchTraySection.setName(sectionDefinitionById.getLabel());
		searchTraySection.setTemplateId(sectionDefinitionById.getId());
		returnValue.getSections().add(searchTraySection);
		// Drop-Box
		ITraySectionDefinition dropBoxDefinition = TraySectionManager.getInstance()
				.getSectionDefinitionById(DROP_BOX_ID);
		TraySection dropTraySection = UimodelFactory.eINSTANCE.createTraySection();
		dropTraySection.setImage(dropBoxDefinition.getImage());
		dropTraySection.setImplementation(dropBoxDefinition.getImplementation());
		dropTraySection.setName(dropBoxDefinition.getLabel());
		dropTraySection.setTemplateId(dropBoxDefinition.getId());
		dropTraySection.getPreferenceOptions().put(DropSectionPreferencePage.RULESET_KEY,
				IRuleService.DEFAULT_RULENAME);
		returnValue.getSections().add(dropTraySection);

		// Notifications
		ITraySectionDefinition notifcationBox = TraySectionManager.getInstance()
				.getSectionDefinitionById(NOTIFICATION_BOX);
		TraySection notificationTraySection = UimodelFactory.eINSTANCE.createTraySection();
		notificationTraySection.setImage(notifcationBox.getImage());
		notificationTraySection.setImplementation(notifcationBox.getImplementation());
		notificationTraySection.setName(notifcationBox.getLabel());
		notificationTraySection.setTemplateId(notifcationBox.getId());
		returnValue.getSections().add(notificationTraySection);

		return returnValue;
	}

	public TraySectionCollection getTraySections() {
		checkForInitialization();
		return this.traySections;
	}

	public void save() throws IOException {
		checkForInitialization();
		this.traySections.eResource().save(Collections.EMPTY_MAP);
	}

}
