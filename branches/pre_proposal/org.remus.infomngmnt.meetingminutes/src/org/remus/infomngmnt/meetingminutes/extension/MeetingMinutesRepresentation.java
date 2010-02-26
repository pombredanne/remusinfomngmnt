package org.remus.infomngmnt.meetingminutes.extension;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;

import org.remus.infomngmnt.common.core.streams.StreamCloser;
import org.remus.infomngmnt.core.extension.AbstractInformationRepresentation;
import org.remus.infomngmnt.core.model.InformationStructureRead;
import org.remus.infomngmnt.jslib.rendering.FreemarkerRenderer;
import org.remus.infomngmnt.meetingminutes.MeetingMinutesActivator;
import org.remus.infomngmnt.util.StatusCreator;

public class MeetingMinutesRepresentation extends AbstractInformationRepresentation {

	@Override
	public String getBodyForIndexing(final IProgressMonitor monitor) throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream handleHtmlGeneration(final IProgressMonitor monitor) throws CoreException {
		InformationStructureRead read = InformationStructureRead.newSession(getValue());
		ByteArrayOutputStream returnValue = new ByteArrayOutputStream();
		InputStream templateIs = null;
		try {
			templateIs = FileLocator.openStream(Platform
					.getBundle(MeetingMinutesActivator.PLUGIN_ID), new Path(
					"template/htmlserialization.flt"), false);
			FreemarkerRenderer.getInstance().process(MeetingMinutesActivator.PLUGIN_ID, templateIs,
					returnValue, null, read.getContentsAsStrucuturedMap(),
					read.getDynamicContentAsStructuredMap());
		} catch (IOException e) {
			throw new CoreException(StatusCreator.newStatus("Error reading locations", e));
		} finally {
			StreamCloser.closeStreams(templateIs);
		}
		return new ByteArrayInputStream(returnValue.toByteArray());
	}

}
