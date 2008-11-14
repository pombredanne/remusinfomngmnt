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

package org.remus.infomngmnt.link;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.compare.diff.metamodel.AttributeChange;

import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.core.extension.AbstractInformationRepresentation;
import org.remus.infomngmnt.core.model.EditingUtil;
import org.remus.infomngmnt.core.model.InformationUtil;
import org.remus.infomngmnt.link.webshot.WebshotUtil;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class LinkRepresentation extends AbstractInformationRepresentation {

	public static final String SCREENSHOT_TYPE = "SCREENSHOT_TYPE"; //$NON-NLS-1$
	public static final String INDEXWEBPAGE_TYPE = "INDEXWEBPAGE_TYPE"; //$NON-NLS-1$
	public static final String INDEXWEBPAGECONTENT_TYPE = "INDEXWEBPAGECONTENT_TYPE"; //$NON-NLS-1$

	/**
	 * 
	 */
	public LinkRepresentation() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handlePreBuild(IProgressMonitor monitor) {
		boolean makeNewScreenShot = true;
		boolean indexNew = true;
		if (getPreviousVersion() != null) {
			InformationUnit previousModel = EditingUtil.getInstance().getObjectFromUri(
					getPreviousVersion().getFullPath(), InfomngmntPackage.Literals.INFORMATION_UNIT, false);
			InformationUnit indexWebPagePrevious = InformationUtil.getChildByType(previousModel, INDEXWEBPAGE_TYPE);
			InformationUnit indexWebPageCurrent = InformationUtil.getChildByType(getValue(), INDEXWEBPAGE_TYPE);
			AttributeChange change = InformationUtil.getAttributeChange(indexWebPagePrevious, indexWebPageCurrent, InfomngmntPackage.Literals.INFORMATION_UNIT__BOOL_VALUE);

			InformationUnit screenShotPagePrevious = InformationUtil.getChildByType(previousModel, SCREENSHOT_TYPE);
			InformationUnit screenShotPageCurrent = InformationUtil.getChildByType(getValue(), SCREENSHOT_TYPE);
			AttributeChange makeScreenShotChange = InformationUtil.getAttributeChange(
					screenShotPagePrevious, screenShotPageCurrent, InfomngmntPackage.Literals.INFORMATION_UNIT__BOOL_VALUE);
			makeNewScreenShot = change != null;
			indexNew = makeScreenShotChange != null;
		}

		InformationUnit indexContentInfo = InformationUtil.getChildByType(getValue(), INDEXWEBPAGE_TYPE);
		if (indexContentInfo.isBoolValue() && indexNew) {
			HttpClient client = new HttpClient();
			// Create a method instance.
			GetMethod method = new GetMethod(getValue().getStringValue());
			// Execute the method.
			try {
				int statusCode = client.executeMethod(method);

				if (statusCode != HttpStatus.SC_OK) {
					System.err.println("Method failed: " + method.getStatusLine());
				}
				InputStream stream = method.getResponseBodyAsStream();
				Reader in = new HTMLStripReader(new InputStreamReader(stream));
				StringWriter sw = new StringWriter();
				int ch;
				while ( (ch=in.read()) != -1 ) {
					sw.append((char)ch);
				}
				InformationUnit indexContent = InformationUtil.getChildByType(getValue(), INDEXWEBPAGECONTENT_TYPE);
				String string = sw.toString();
				string = string.replaceAll("\\r\\n", " ");
				string = string.replaceAll("\\n", " ");
				string = string.replaceAll("\\s+", " ");
				indexContent.setStringValue(string);

			} catch (HttpException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			InformationUnit contentInfoUnit = InformationUtil.getChildByType(getValue(), INDEXWEBPAGECONTENT_TYPE);
			contentInfoUnit.eUnset(InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);
		}
		InformationUnit makeScreenshotInfo = InformationUtil.getChildByType(getValue(), SCREENSHOT_TYPE);

		if (makeScreenshotInfo.isBoolValue() && makeNewScreenShot) {
			if (!getWebShotFile().getParent().exists()) {
				try {
					((IFolder)getWebShotFile().getParent()).create(true, true, monitor);
				} catch (CoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			WebshotUtil.performWebShot(getValue().getStringValue(),getWebShotFile().getLocation().toOSString());
		} else {
			try {
				getWebShotFile().delete(true, monitor);
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private IFile getWebShotFile() {
		IFile file = getFile().getParent().getFile(new Path(getValue().getId()).append("webshot.png"));
		return file;
	}

	/* (non-Javadoc)
	 * @see org.remus.infomngmnt.core.extension.AbstractInformationRepresentation#getAdditionalsForIndexing(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public String getAdditionalsForIndexing(IProgressMonitor monitor)
	throws CoreException {
		InformationUnit childByType = InformationUtil.getChildByType(getValue(), INDEXWEBPAGECONTENT_TYPE);
		if (childByType.eIsSet(InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE)) {
			return childByType.getStringValue();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.remus.infomngmnt.core.extension.AbstractInformationRepresentation#getBodyForIndexing(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public String getBodyForIndexing(IProgressMonitor monitor)
	throws CoreException {
		return getValue().getStringValue();
	}

	/* (non-Javadoc)
	 * @see org.remus.infomngmnt.core.extension.AbstractInformationRepresentation#getTitleForIndexing(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public String getTitleForIndexing(IProgressMonitor monitor)
	throws CoreException {
		return getValue().getLabel();
	}

	/* (non-Javadoc)
	 * @see org.remus.infomngmnt.core.extension.AbstractInformationRepresentation#handleHtmlGeneration(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public String handleHtmlGeneration(IProgressMonitor monitor)
	throws CoreException {
		return "<p>" + getValue().getStringValue() + "</p>" +
		"<p><img width=\"100%\" src=\"" + getWebShotFile().getLocation().toOSString() + "\">";
	}

}
