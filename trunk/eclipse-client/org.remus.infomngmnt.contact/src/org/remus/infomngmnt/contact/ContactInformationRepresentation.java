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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.common.core.streams.StreamCloser;
import org.remus.infomngmnt.core.extension.AbstractInformationRepresentation;
import org.remus.infomngmnt.core.model.InformationUtil;
import org.remus.infomngmnt.core.model.StatusCreator;
import org.remus.infomngmnt.jslib.rendering.FreemarkerRenderer;

public class ContactInformationRepresentation extends
		AbstractInformationRepresentation {

	public ContactInformationRepresentation() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public void handlePreBuild(final IProgressMonitor monitor) {
		InformationUnit rawDataNode = InformationUtil.getChildByType(getValue(),
				ContactActivator.NODE_NAME_RAWDATA_IMAGE);
		if (rawDataNode != null && rawDataNode.getBinaryValue() != null) {
			monitor.setTaskName("Extracting image...");
		}
		InformationUnit origFileName = InformationUtil.getChildByType(getValue(),
				ContactActivator.ORIGINAL_FILEPATH);
		if (origFileName != null) {
			String fileExtension = "bmp";
			if (origFileName.getStringValue() != null) {
				fileExtension = new Path(origFileName.getStringValue()).getFileExtension();
			}
			
//			// TODO, createabstractbuildfolder überschreiben...
////			createFolderOnBuild
//			IFile file = getBuildFolder().getFile(
//					new Path(getValue().getId()).addFileExtension(fileExtension));
////			this.imageHref = file.getLocation().toOSString();
//			ByteArrayInputStream bais = new ByteArrayInputStream(rawDataNode.getBinaryValue());
//			try {
//				file.create(bais, true, monitor);
//			} catch (CoreException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			} finally {
//				try {
//					bais.close();
//				} catch (IOException e) {
//					// do nothing... we've done our best.
//				}
//			}
		}
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
					Platform.getBundle(ContactActivator.PLUGIN_ID), 
					new Path("template/htmlserialization.flt"), false);
			FreemarkerRenderer.getInstance().process(
					ContactActivator.PLUGIN_ID,
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