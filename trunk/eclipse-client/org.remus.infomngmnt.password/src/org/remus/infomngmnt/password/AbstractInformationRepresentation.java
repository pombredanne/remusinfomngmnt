package org.remus.infomngmnt.password;

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
import org.remus.infomngmnt.core.model.StatusCreator;
import org.remus.infomngmnt.jslib.rendering.FreemarkerRenderer;

public class AbstractInformationRepresentation extends
		org.remus.infomngmnt.core.extension.AbstractInformationRepresentation {

	public AbstractInformationRepresentation() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getBodyForIndexing(IProgressMonitor monitor)
			throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream handleHtmlGeneration(IProgressMonitor monitor)
			throws CoreException {
		ByteArrayOutputStream returnValue = new ByteArrayOutputStream();
		InputStream templateIs = null;
		InputStream contentsIs = getFile().getContents();
		try {
			templateIs = FileLocator.openStream(
					Platform.getBundle(PasswordPlugin.PLUGIN_ID), 
					new Path("template/htmlserialization.flt"), false);
			FreemarkerRenderer.getInstance().process(
					PasswordPlugin.PLUGIN_ID,
					templateIs,
					contentsIs,
					returnValue, null);
		} catch (IOException e) {
			throw new CoreException(StatusCreator.newStatus(
					"Error reading locations",e));
		} finally {
			StreamCloser.closeStreams(templateIs, contentsIs);
		}
		return new ByteArrayInputStream(returnValue.toByteArray());
	}

}
