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
import java.io.StringWriter;
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
import org.eclipse.emf.common.util.URI;
import org.eclipse.osgi.util.NLS;

import org.remus.infomngmnt.CalendarEntry;
import org.remus.infomngmnt.CalendarEntryType;
import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.common.core.streams.StreamCloser;
import org.remus.infomngmnt.contact.core.ContactUtil;
import org.remus.infomngmnt.contact.preferences.ContactPreferenceInitializer;
import org.remus.infomngmnt.core.extension.AbstractInformationRepresentation;
import org.remus.infomngmnt.core.model.InformationStructureRead;
import org.remus.infomngmnt.core.services.IGeoData;
import org.remus.infomngmnt.jslib.rendering.FreemarkerRenderer;
import org.remus.infomngmnt.ui.UIPlugin;
import org.remus.infomngmnt.util.InformationUtil;
import org.remus.infomngmnt.util.StatusCreator;

public class ContactInformationRepresentation extends AbstractInformationRepresentation {

	@Override
	public String getBodyForIndexing(final IProgressMonitor monitor) throws CoreException {
		StringWriter sw = new StringWriter();
		InformationStructureRead read = InformationStructureRead.newSession(getValue());
		appendToSearch(sw, read.getValueByNodeId(ContactActivator.NODE_NAME_PERS_NAME_TITLE));
		appendToSearch(sw, read.getValueByNodeId(ContactActivator.NODE_NAME_PERS_NAME_FIRST));
		appendToSearch(sw, read.getValueByNodeId(ContactActivator.NODE_NAME_PERS_NAME_ADDITIONAL));
		appendToSearch(sw, read.getValueByNodeId(ContactActivator.NODE_NAME_PERS_NAME_LAST));
		appendToSearch(sw, read.getValueByNodeId(ContactActivator.NODE_NAME_PERS_NAME_TITLE_AFTER));
		appendToSearch(sw, read.getValueByNodeId(ContactActivator.NODE_NAME_PERS_ROLE));
		appendToSearch(sw, read.getValueByNodeId(ContactActivator.NODE_NAME_PERS_ORGANISATION));

		// Workadress
		appendToSearch(sw, read.getValueByPath(ContactActivator.NODE_NAME_WORK_ADRESS,
				ContactActivator.NODE_NAME_ADRESS_STREET));
		appendToSearch(sw, read.getValueByPath(ContactActivator.NODE_NAME_WORK_ADRESS,
				ContactActivator.NODE_NAME_ADRESS_POST_OFFICE_BOX));
		appendToSearch(sw, read.getValueByPath(ContactActivator.NODE_NAME_WORK_ADRESS,
				ContactActivator.NODE_NAME_ADRESS_LOCALITY));
		appendToSearch(sw, read.getValueByPath(ContactActivator.NODE_NAME_WORK_ADRESS,
				ContactActivator.NODE_NAME_ADRESS_REGION));
		appendToSearch(sw, read.getValueByPath(ContactActivator.NODE_NAME_WORK_ADRESS,
				ContactActivator.NODE_NAME_ADRESS_POSTAL));
		appendToSearch(sw, read.getValueByPath(ContactActivator.NODE_NAME_WORK_ADRESS,
				ContactActivator.NODE_NAME_ADRESS_COUNTRY));

		// Homeadress
		appendToSearch(sw, read.getValueByPath(ContactActivator.NODE_NAME_HOME_ADRESS,
				ContactActivator.NODE_NAME_ADRESS_STREET));
		appendToSearch(sw, read.getValueByPath(ContactActivator.NODE_NAME_HOME_ADRESS,
				ContactActivator.NODE_NAME_ADRESS_POST_OFFICE_BOX));
		appendToSearch(sw, read.getValueByPath(ContactActivator.NODE_NAME_HOME_ADRESS,
				ContactActivator.NODE_NAME_ADRESS_LOCALITY));
		appendToSearch(sw, read.getValueByPath(ContactActivator.NODE_NAME_HOME_ADRESS,
				ContactActivator.NODE_NAME_ADRESS_REGION));
		appendToSearch(sw, read.getValueByPath(ContactActivator.NODE_NAME_HOME_ADRESS,
				ContactActivator.NODE_NAME_ADRESS_POSTAL));
		appendToSearch(sw, read.getValueByPath(ContactActivator.NODE_NAME_HOME_ADRESS,
				ContactActivator.NODE_NAME_ADRESS_COUNTRY));

		// internat Adress
		appendToSearch(sw, read.getValueByPath(ContactActivator.NODE_NAME_INTERNAT_ADRESS,
				ContactActivator.NODE_NAME_ADRESS_STREET));
		appendToSearch(sw, read.getValueByPath(ContactActivator.NODE_NAME_INTERNAT_ADRESS,
				ContactActivator.NODE_NAME_ADRESS_POST_OFFICE_BOX));
		appendToSearch(sw, read.getValueByPath(ContactActivator.NODE_NAME_INTERNAT_ADRESS,
				ContactActivator.NODE_NAME_ADRESS_LOCALITY));
		appendToSearch(sw, read.getValueByPath(ContactActivator.NODE_NAME_INTERNAT_ADRESS,
				ContactActivator.NODE_NAME_ADRESS_REGION));
		appendToSearch(sw, read.getValueByPath(ContactActivator.NODE_NAME_INTERNAT_ADRESS,
				ContactActivator.NODE_NAME_ADRESS_POSTAL));
		appendToSearch(sw, read.getValueByPath(ContactActivator.NODE_NAME_INTERNAT_ADRESS,
				ContactActivator.NODE_NAME_ADRESS_COUNTRY));

		// postal adress
		appendToSearch(sw, read.getValueByPath(ContactActivator.NODE_NAME_POSTAL_ADRESS,
				ContactActivator.NODE_NAME_ADRESS_STREET));
		appendToSearch(sw, read.getValueByPath(ContactActivator.NODE_NAME_POSTAL_ADRESS,
				ContactActivator.NODE_NAME_ADRESS_POST_OFFICE_BOX));
		appendToSearch(sw, read.getValueByPath(ContactActivator.NODE_NAME_POSTAL_ADRESS,
				ContactActivator.NODE_NAME_ADRESS_LOCALITY));
		appendToSearch(sw, read.getValueByPath(ContactActivator.NODE_NAME_POSTAL_ADRESS,
				ContactActivator.NODE_NAME_ADRESS_REGION));
		appendToSearch(sw, read.getValueByPath(ContactActivator.NODE_NAME_POSTAL_ADRESS,
				ContactActivator.NODE_NAME_ADRESS_POSTAL));
		appendToSearch(sw, read.getValueByPath(ContactActivator.NODE_NAME_POSTAL_ADRESS,
				ContactActivator.NODE_NAME_ADRESS_COUNTRY));

		// parcel adress
		appendToSearch(sw, read.getValueByPath(ContactActivator.NODE_NAME_PARCEL_ADRESS,
				ContactActivator.NODE_NAME_ADRESS_STREET));
		appendToSearch(sw, read.getValueByPath(ContactActivator.NODE_NAME_PARCEL_ADRESS,
				ContactActivator.NODE_NAME_ADRESS_POST_OFFICE_BOX));
		appendToSearch(sw, read.getValueByPath(ContactActivator.NODE_NAME_PARCEL_ADRESS,
				ContactActivator.NODE_NAME_ADRESS_LOCALITY));
		appendToSearch(sw, read.getValueByPath(ContactActivator.NODE_NAME_PARCEL_ADRESS,
				ContactActivator.NODE_NAME_ADRESS_REGION));
		appendToSearch(sw, read.getValueByPath(ContactActivator.NODE_NAME_PARCEL_ADRESS,
				ContactActivator.NODE_NAME_ADRESS_POSTAL));
		appendToSearch(sw, read.getValueByPath(ContactActivator.NODE_NAME_PARCEL_ADRESS,
				ContactActivator.NODE_NAME_ADRESS_COUNTRY));

		// domestic adress
		appendToSearch(sw, read.getValueByPath(ContactActivator.NODE_NAME_DOMESTIC_ADRESS,
				ContactActivator.NODE_NAME_ADRESS_STREET));
		appendToSearch(sw, read.getValueByPath(ContactActivator.NODE_NAME_DOMESTIC_ADRESS,
				ContactActivator.NODE_NAME_ADRESS_POST_OFFICE_BOX));
		appendToSearch(sw, read.getValueByPath(ContactActivator.NODE_NAME_DOMESTIC_ADRESS,
				ContactActivator.NODE_NAME_ADRESS_LOCALITY));
		appendToSearch(sw, read.getValueByPath(ContactActivator.NODE_NAME_DOMESTIC_ADRESS,
				ContactActivator.NODE_NAME_ADRESS_REGION));
		appendToSearch(sw, read.getValueByPath(ContactActivator.NODE_NAME_DOMESTIC_ADRESS,
				ContactActivator.NODE_NAME_ADRESS_POSTAL));
		appendToSearch(sw, read.getValueByPath(ContactActivator.NODE_NAME_DOMESTIC_ADRESS,
				ContactActivator.NODE_NAME_ADRESS_COUNTRY));

		// other adress
		appendToSearch(sw, read.getValueByPath(ContactActivator.NODE_NAME_OTHER_ADRESS,
				ContactActivator.NODE_NAME_ADRESS_STREET));
		appendToSearch(sw, read.getValueByPath(ContactActivator.NODE_NAME_OTHER_ADRESS,
				ContactActivator.NODE_NAME_ADRESS_POST_OFFICE_BOX));
		appendToSearch(sw, read.getValueByPath(ContactActivator.NODE_NAME_OTHER_ADRESS,
				ContactActivator.NODE_NAME_ADRESS_LOCALITY));
		appendToSearch(sw, read.getValueByPath(ContactActivator.NODE_NAME_OTHER_ADRESS,
				ContactActivator.NODE_NAME_ADRESS_REGION));
		appendToSearch(sw, read.getValueByPath(ContactActivator.NODE_NAME_OTHER_ADRESS,
				ContactActivator.NODE_NAME_ADRESS_POSTAL));
		appendToSearch(sw, read.getValueByPath(ContactActivator.NODE_NAME_OTHER_ADRESS,
				ContactActivator.NODE_NAME_ADRESS_COUNTRY));

		appendToSearch(sw, read.getValueByNodeId(ContactActivator.NODE_MAIL_DEF));
		appendToSearch(sw, read.getValueByNodeId(ContactActivator.NODE_INSTMESS_AIM));
		appendToSearch(sw, read.getValueByNodeId(ContactActivator.NODE_INSTMESS_GADU));
		appendToSearch(sw, read.getValueByNodeId(ContactActivator.NODE_INSTMESS_GROUPWISE));
		appendToSearch(sw, read.getValueByNodeId(ContactActivator.NODE_INSTMESS_ICQ));
		appendToSearch(sw, read.getValueByNodeId(ContactActivator.NODE_INSTMESS_IRC));
		appendToSearch(sw, read.getValueByNodeId(ContactActivator.NODE_INSTMESS_JABBER));
		appendToSearch(sw, read.getValueByNodeId(ContactActivator.NODE_INSTMESS_MSN));
		appendToSearch(sw, read.getValueByNodeId(ContactActivator.NODE_INSTMESS_MEANWHILE));
		appendToSearch(sw, read.getValueByNodeId(ContactActivator.NODE_INSTMESS_SKYPE));
		appendToSearch(sw, read.getValueByNodeId(ContactActivator.NODE_INSTMESS_YAHOO));
		appendToSearch(sw, read.getValueByNodeId(ContactActivator.NODE_BLOG_FEED));
		appendToSearch(sw, read.getValueByNodeId(ContactActivator.NODE_FRONTPAGE));

		appendToSearch(sw, read.getValueByNodeId(ContactActivator.NODE_DETAILS_DEPARTMENT));
		appendToSearch(sw, read.getValueByNodeId(ContactActivator.NODE_DETAILS_BUREAU));
		appendToSearch(sw, read.getValueByNodeId(ContactActivator.NODE_DETAILS_NAME_MANAGER));
		appendToSearch(sw, read.getValueByNodeId(ContactActivator.NODE_DETAILS_NAME_ASSISTANT));
		appendToSearch(sw, read.getValueByNodeId(ContactActivator.NODE_DETAILS_JOB));
		appendToSearch(sw, read.getValueByNodeId(ContactActivator.NODE_DETAILS_TITLE));
		appendToSearch(sw, read.getValueByNodeId(ContactActivator.NODE_DETAILS_NAME_NICK));
		appendToSearch(sw, read.getValueByNodeId(ContactActivator.NODE_DETAILS_NOTES));

		return sw.toString();
	}

	private void appendToSearch(final StringWriter sw, final Object object) {
		if (object != null && object.toString().trim().length() > 0) {
			sw.append(object.toString()).append(" ");
		}
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
		parameterMap.put("createMapsImage", ContactActivator.getDefault().getPreferenceStore()
				.getBoolean(ContactPreferenceInitializer.SHOW_MAPS_IMAGE));
		parameterMap.put("mapsWidth", Integer.toString(ContactActivator.getDefault()
				.getPreferenceStore().getInt(ContactPreferenceInitializer.MAPS_IMAGE_WIDTH)));
		parameterMap.put("mapsZoom", Integer.toString(ContactActivator.getDefault()
				.getPreferenceStore().getInt(ContactPreferenceInitializer.MAPS_IMAGE_ZOOMLEVEL)));
		parameterMap.put("mapsHeight", Integer.toString(ContactActivator.getDefault()
				.getPreferenceStore().getInt(ContactPreferenceInitializer.MAPS_IMAGE_HEIGHT)));
		parameterMap.put("formattedHomeAdress", ContactUtil.getFormattedAdress(InformationUtil
				.getChildByType(getValue(), ContactActivator.NODE_NAME_HOME_ADRESS)));
		parameterMap.put("formattedWorkAdress", ContactUtil.getFormattedAdress(InformationUtil
				.getChildByType(getValue(), ContactActivator.NODE_NAME_WORK_ADRESS)));
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
	public boolean createFolderOnBuild() {
		return true;
	}
}