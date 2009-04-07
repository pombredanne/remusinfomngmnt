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
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.core.extension.AbstractInformationRepresentation;
import org.remus.infomngmnt.core.model.InformationUtil;

public class ContactInformationRepresentation extends
		AbstractInformationRepresentation {

	private String imageHref;

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
			IFile file = getBuildFolder().getFile(
					new Path(getValue().getId()).addFileExtension(fileExtension));
			this.imageHref = file.getLocation().toOSString();
			ByteArrayInputStream bais = new ByteArrayInputStream(rawDataNode.getBinaryValue());
			try {
				file.create(bais, true, monitor);
			} catch (CoreException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} finally {
				try {
					bais.close();
				} catch (IOException e) {
					// do nothing... we've done our best.
				}
			}
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
		// TODO Auto-generated method stub
		return null;
	}

}