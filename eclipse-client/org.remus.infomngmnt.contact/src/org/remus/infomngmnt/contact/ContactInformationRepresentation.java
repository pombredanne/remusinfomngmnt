/*******************************************************************************
 * Copyright (c) 2009 Jan Hartwig, FEB Radebeul
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Jan Hartwig - initial API and implementation
 *     Tom Seidel - RIMINFOTYPES-22
 *******************************************************************************/
package org.remus.infomngmnt.contact;

/**
 * @author Jan Hartwig <jhartwig@feb-radebeul.de>
 * 
 */
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.osgi.util.NLS;
import org.eclipse.remus.CalendarEntry;
import org.eclipse.remus.CalendarEntryType;
import org.eclipse.remus.InfomngmntFactory;
import org.eclipse.remus.InformationUnit;
import org.eclipse.remus.common.core.streams.StreamCloser;
import org.eclipse.remus.core.extension.AbstractInformationRepresentation;
import org.eclipse.remus.core.model.InformationStructureRead;
import org.eclipse.remus.core.services.IGeoData;
import org.eclipse.remus.js.rendering.FreemarkerRenderer;
import org.eclipse.remus.ui.UIPlugin;
import org.eclipse.remus.util.InformationUtil;
import org.eclipse.remus.util.StatusCreator;

import org.remus.infomngmnt.contact.core.ContactUtil;
import org.remus.infomngmnt.contact.preferences.ContactPreferenceInitializer;

public class ContactInformationRepresentation extends AbstractInformationRepresentation {

	@Override
	public InputStream handleHtmlGeneration(final IProgressMonitor monitor) throws CoreException {
		String imageHref = null;
		String fileExtension = "png";
		InformationUnit rawDataNode = InformationUtil.getChildByType(getValue(),
				ContactActivator.NODE_NAME_RAWDATA_IMAGE);
		if (rawDataNode != null && rawDataNode.getBinaryValue() != null) {
			monitor.setTaskName("Extracting image...");
			IFile file = getBuildFolder().getFile(
					new Path(getValue().getId()).addFileExtension(fileExtension));
			imageHref = URI.createFileURI(file.getLocation().toOSString()).toString();
			ByteArrayInputStream bais = new ByteArrayInputStream(rawDataNode.getBinaryValue());
			try {
				if (file.exists()) {
					file.setContents(bais, true, false, monitor);
				} else {
					file.create(bais, true, monitor);
				}

			} catch (CoreException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} finally {
				StreamCloser.closeStreams(bais);
			}
		}
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("photo", imageHref);
		try {
			parameterMap.put("mapskey", UIPlugin.getDefault().getService(IGeoData.class)
					.getApiKey());
		} catch (Exception e1) {
			parameterMap.put("mapskey", "");
		}
		InformationStructureRead read = InformationStructureRead.newSession(getValue());
		EList<InformationUnit> dynamicList = read
				.getDynamicList(ContactActivator.NODE_NAME_ADDRESSES);
		for (InformationUnit informationUnit : dynamicList) {
			InformationStructureRead adressRead = InformationStructureRead.newSession(
					informationUnit, ContactActivator.TYPE_ID);
			if (adressRead.getValueByNodeId(ContactActivator.NODE_NAME_ADDRESS).equals(
					ContactActivator.NODE_NAME_HOME_ADDRESS)) {
				parameterMap.put("formattedHomeAdress", ContactUtil
						.getFormattedAdress(informationUnit));
			}
			if (adressRead.getValueByNodeId(ContactActivator.NODE_NAME_ADDRESS).equals(
					ContactActivator.NODE_NAME_WORK_ADDRESS)) {
				parameterMap.put("formattedWorkAdress", ContactUtil
						.getFormattedAdress(informationUnit));
			}
		}
		parameterMap.put("createMapsImage", ContactActivator.getDefault().getPreferenceStore()
				.getBoolean(ContactPreferenceInitializer.SHOW_MAPS_IMAGE));
		parameterMap.put("mapsWidth", Integer.toString(ContactActivator.getDefault()
				.getPreferenceStore().getInt(ContactPreferenceInitializer.MAPS_IMAGE_WIDTH)));
		parameterMap.put("mapsZoom", Integer.toString(ContactActivator.getDefault()
				.getPreferenceStore().getInt(ContactPreferenceInitializer.MAPS_IMAGE_ZOOMLEVEL)));
		parameterMap.put("mapsHeight", Integer.toString(ContactActivator.getDefault()
				.getPreferenceStore().getInt(ContactPreferenceInitializer.MAPS_IMAGE_HEIGHT)));
		parameterMap.put("formattedName", ContactUtil.getFormattedName(getValue()));
		parameterMap.put("renderPhoneLinks", ContactActivator.getDefault().getPreferenceStore()
				.getBoolean(ContactPreferenceInitializer.SHOW_PHONE_LINKS));
		parameterMap.put("phonePattern", ContactActivator.getDefault().getPreferenceStore()
				.getString(ContactPreferenceInitializer.PHONE_CALL_PATTERN));
		ByteArrayOutputStream returnValue = new ByteArrayOutputStream();
		InputStream templateIs = null;
		InputStream contentsIs = getFile().getContents();
		try {
			templateIs = FileLocator.openStream(Platform.getBundle(ContactActivator.PLUGIN_ID),
					new Path("template/htmlserialization.flt"), false);
			FreemarkerRenderer.getInstance().process(ContactActivator.PLUGIN_ID, templateIs,
					contentsIs, returnValue, parameterMap);
		} catch (IOException e) {
			throw new CoreException(StatusCreator.newStatus("Error reading locations", e));
		} finally {
			StreamCloser.closeStreams(templateIs, contentsIs);
		}
		return new ByteArrayInputStream(returnValue.toByteArray());
	}

	@Override
	public CalendarEntry[] getCalendarContributions() {
		InformationStructureRead read = InformationStructureRead.newSession(getValue());
		Date birthday = (Date) read.getValueByNodeId(ContactActivator.NODE_DETAILS_BIRTHDAY);
		if (birthday != null) {
			CalendarEntry createCalendarEntry = InfomngmntFactory.eINSTANCE.createCalendarEntry();
			Calendar instance = Calendar.getInstance();
			instance.setTime(birthday);
			instance.set(Calendar.HOUR, 0);
			instance.set(Calendar.MINUTE, 0);
			instance.set(Calendar.SECOND, 0);
			instance.set(Calendar.AM_PM, Calendar.AM);
			createCalendarEntry.setStart(instance.getTime());
			instance = Calendar.getInstance();
			instance.setTime(birthday);
			instance.add(Calendar.DAY_OF_YEAR, 1);
			instance.set(Calendar.HOUR, 0);
			instance.set(Calendar.MINUTE, 0);
			instance.set(Calendar.SECOND, 0);
			instance.set(Calendar.AM_PM, Calendar.AM);
			createCalendarEntry.setEnd(instance.getTime());
			createCalendarEntry.setEntryType(CalendarEntryType.ANNUAL);
			createCalendarEntry.setTitle(NLS.bind("Birthday of {0}", ContactUtil
					.getFormattedName(getValue())));
			return new CalendarEntry[] { createCalendarEntry };
		}
		return super.getCalendarContributions();
	}

	@Override
	public boolean createFolderOnBuild() {
		return true;
	}
}