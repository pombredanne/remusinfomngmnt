package org.remus.infomngmnt.meetingminutes.extension;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.remus.CalendarEntry;
import org.eclipse.remus.CalendarEntryType;
import org.eclipse.remus.InfomngmntFactory;
import org.eclipse.remus.common.core.streams.StreamCloser;
import org.eclipse.remus.core.extension.AbstractInformationRepresentation;
import org.eclipse.remus.core.model.InformationStructureRead;
import org.eclipse.remus.js.rendering.FreemarkerRenderer;
import org.eclipse.remus.util.StatusCreator;
import org.remus.infomngmnt.meetingminutes.MeetingMinutesActivator;

public class MeetingMinutesRepresentation extends
		AbstractInformationRepresentation {

	@Override
	public InputStream handleHtmlGeneration(final IProgressMonitor monitor)
			throws CoreException {
		InformationStructureRead read = InformationStructureRead
				.newSession(getValue());
		ByteArrayOutputStream returnValue = new ByteArrayOutputStream();
		InputStream templateIs = null;
		try {
			templateIs = FileLocator.openStream(
					Platform.getBundle(MeetingMinutesActivator.PLUGIN_ID),
					new Path("$nl$/template/htmlserialization.flt"), true); //$NON-NLS-1$
			FreemarkerRenderer.getInstance().process(
					MeetingMinutesActivator.PLUGIN_ID, templateIs, returnValue,
					null, read.getContentsAsStrucuturedMap(),
					read.getDynamicContentAsStructuredMap());
		} catch (IOException e) {
			throw new CoreException(StatusCreator.newStatus(
					"Error reading locations", e)); //$NON-NLS-1$
		} finally {
			StreamCloser.closeStreams(templateIs);
		}
		return new ByteArrayInputStream(returnValue.toByteArray());
	}

	@Override
	public CalendarEntry[] getCalendarContributions() {
		InformationStructureRead read = InformationStructureRead
				.newSession(getValue());
		Date start = (Date) read
				.getValueByNodeId(MeetingMinutesActivator.NODE_NAME_DATETIME);
		Date end = (Date) read
				.getValueByNodeId(MeetingMinutesActivator.NODE_NAME_END_DATETIME);
		if (start != null && end != null) {
			CalendarEntry entry = InfomngmntFactory.eINSTANCE
					.createCalendarEntry();
			entry.setStart(start);
			entry.setEnd(end);
			entry.setEntryType(CalendarEntryType.ONE_TIME);
			entry.setTitle(getValue().getLabel());
			return new CalendarEntry[] { entry };
		}
		return super.getCalendarContributions();
	}

}
