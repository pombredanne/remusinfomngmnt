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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;

import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.common.core.streams.StreamCloser;
import org.remus.infomngmnt.core.extension.AbstractInformationRepresentation;
import org.remus.infomngmnt.jslib.rendering.FreemarkerRenderer;
import org.remus.infomngmnt.resources.util.ResourceUtil;
import org.remus.infomngmnt.util.InformationUtil;
import org.remus.infomngmnt.util.StatusCreator;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class LinkRepresentation extends AbstractInformationRepresentation {

	public static final String SCREENSHOT_TYPE = "SCREENSHOT_TYPE"; //$NON-NLS-1$
	public static final String INDEXWEBPAGE_TYPE = "INDEXWEBPAGE_TYPE"; //$NON-NLS-1$
	public static final String INDEXWEBPAGECONTENT_TYPE = "INDEXWEBPAGECONTENT_TYPE"; //$NON-NLS-1$
	public static final String WEBSHOT_SECTION_ID = "webShotSection"; //$NON-NLS-1$
	private String imageHref;

	/**
	 * 
	 */
	public LinkRepresentation() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handlePreBuild(final IProgressMonitor monitor) {
		if (getValue().getBinaryReferences() != null) {
			this.imageHref = getFile().getProject().getLocation()
					.append(ResourceUtil.BINARY_FOLDER).append(
							getValue().getBinaryReferences().getProjectRelativePath()).toOSString();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.core.extension.AbstractInformationRepresentation
	 * #getAdditionalsForIndexing(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public String getAdditionalsForIndexing(final IProgressMonitor monitor) throws CoreException {
		InformationUnit childByType = InformationUtil.getChildByType(getValue(),
				LinkActivator.NODE_INDEX);
		if (childByType.eIsSet(InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE)) {
			return childByType.getStringValue();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.core.extension.AbstractInformationRepresentation
	 * #getBodyForIndexing(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public String getBodyForIndexing(final IProgressMonitor monitor) throws CoreException {
		return getValue().getStringValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.core.extension.AbstractInformationRepresentation
	 * #handleHtmlGeneration(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public InputStream handleHtmlGeneration(final IProgressMonitor monitor) throws CoreException {
		ByteArrayOutputStream returnValue = new ByteArrayOutputStream();
		InputStream templateIs = null;
		InputStream contentsIs = getFile().getContents();
		try {
			templateIs = FileLocator.openStream(Platform.getBundle(LinkActivator.PLUGIN_ID),
					new Path("template/htmlserialization.flt"), false);

			Map<String, String> map;
			if (this.imageHref != null) {
				map = Collections.<String, String> singletonMap("imageHref", URI.createFileURI(
						this.imageHref).toString());
			} else {
				map = Collections.<String, String> emptyMap();
			}
			FreemarkerRenderer.getInstance().process(LinkActivator.PLUGIN_ID, templateIs,
					contentsIs, returnValue, map);
		} catch (IOException e) {
			throw new CoreException(StatusCreator.newStatus("Error reading locations", e));
		} finally {
			StreamCloser.closeStreams(templateIs, contentsIs);
		}
		return new ByteArrayInputStream(returnValue.toByteArray());
	}
}
