/*******************************************************************************
 * Copyright (c) 2009 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/

package org.remus.infomngmnt.image.core;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;

import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.core.extension.AbstractInformationRepresentation;
import org.remus.infomngmnt.core.model.InformationUtil;
import org.remus.infomngmnt.image.ImagePlugin;
import org.remus.infomngmnt.jslib.HtmlSnippets;
import org.remus.infomngmnt.jslib.JavaScriptSnippets;
import org.remus.infomngmnt.jslib.StyleProvider;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class ImageInformationRepresentation extends
		AbstractInformationRepresentation {
	
	private String imageHref;
	
	public static final String IMAGE_SECTION_NAME = "imageSection"; //$NON-NLS-1$

	
	/**
	 * <p>
	 * Before we can extract the general values from the 
	 * information-object we have to extract the raw image data,
	 * and store them within the workspace. After a successful 
	 * creation of a new image file, we have to remember the 
	 * location of the extracted image which is linked within
	 * the generated html content.
	 * </p>
	 * {@inheritDoc}
	 * @see #imageHref
	 * @see #handleHtmlGeneration(IProgressMonitor)
	 */
	@Override
	public void handlePreBuild(final IProgressMonitor monitor) {
		InformationUnit rawDataNode = InformationUtil.getChildByType(getValue(), ImagePlugin.NODE_NAME_RAWDATA);
		if (rawDataNode != null && rawDataNode.getBinaryValue() != null) {
			monitor.setTaskName("Extracting image...");
		}
		InformationUnit origFileName = InformationUtil.getChildByType(getValue(), ImagePlugin.ORIGINAL_FILEPATH);
		if (origFileName != null) {
			String fileExtension = new Path(origFileName.getStringValue()).getFileExtension();
			IFile file = getBuildFolder().getFile(new Path(getValue().getId()).addFileExtension(fileExtension));
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
	

	/* (non-Javadoc)
	 * @see org.remus.infomngmnt.core.extension.AbstractInformationRepresentation#getAdditionalsForIndexing(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public String getAdditionalsForIndexing(final IProgressMonitor monitor)
			throws CoreException {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.remus.infomngmnt.core.extension.AbstractInformationRepresentation#getBodyForIndexing(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public String getBodyForIndexing(final IProgressMonitor monitor)
			throws CoreException {
		return null;
	}
	
	@Override
	public boolean createFolderOnBuild() {
		return true;
	}

	

	/* (non-Javadoc)
	 * @see org.remus.infomngmnt.core.extension.AbstractInformationRepresentation#handleHtmlGeneration(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public String handleHtmlGeneration(final IProgressMonitor monitor)
			throws CoreException {
		StringWriter sw = new StringWriter();
		sw.append(HtmlSnippets.HTML_HEAD)
		.append(StyleProvider.STYLE_DEFINITION_START)
		.append(StyleProvider.getSystemFontDefinition())
		.append(StyleProvider.STYLE_DEFINITION_END)
		.append(JavaScriptSnippets.SCRIPT_SRC_IMAGES_JS)
		.append(JavaScriptSnippets.SCRIPT_SRC_ROUNDED_CORNERS_JS);
			sw.append(JavaScriptSnippets.SECTION_BOX_DEFINITION(IMAGE_SECTION_NAME, 5));
		
		sw.append(HtmlSnippets.HTML_HEAD_END_BODY_START)
		.append("<p style=\"text-align:center;\">\r\n")
		.append("<a href=\"").append(getValue().getStringValue())
		.append("\" target=\"_blank\">")
		.append(getValue().getStringValue())
		.append("</a>")
		.append("</p>\r\n");
			sw.append("<div>");
			sw.append(HtmlSnippets.CREATE_SECTION_BOX("Image", IMAGE_SECTION_NAME));
			sw.append("<p>\r\n")
			.append("<a href=\"javascript:fit2Page(\'")
			.append(getValue().getId())
			.append("\');\">Fit</a><br> <img name=\"")
			.append(getValue().getId())
			.append("\" src=\"")
			.append(URI.createFileURI(this.imageHref).toString())
			.append("\">")
			.append("</div>");
		sw.append(HtmlSnippets.HTML_BODY_END_HTML_END);
		return sw.toString();
	}

}
