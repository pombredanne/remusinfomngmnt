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

package org.remus.infomngmnt.audio.internal;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;

import org.remus.infomngmnt.audio.AudioActivator;
import org.remus.infomngmnt.common.core.streams.StreamCloser;
import org.remus.infomngmnt.core.extension.AbstractInformationRepresentation;
import org.remus.infomngmnt.core.model.InformationStructureRead;
import org.remus.infomngmnt.jslib.rendering.FreemarkerRenderer;
import org.remus.infomngmnt.mediaplayer.extension.IMediaPlayer;
import org.remus.infomngmnt.mediaplayer.extension.IMediaPlayerExtensionService;
import org.remus.infomngmnt.provider.InfomngmntEditPlugin;
import org.remus.infomngmnt.resources.util.ResourceUtil;
import org.remus.infomngmnt.util.StatusCreator;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class AudioRepresentation extends AbstractInformationRepresentation {

	private IPath audioHref;

	/**
	 * 
	 */
	public AudioRepresentation() {
		// TODO Auto-generated constructor stub
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
		return "";
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

		InformationStructureRead read = InformationStructureRead.newSession(getValue());
		/*
		 * At first we have to determine the player extension that can provide
		 * the correct html for displaying the video.
		 */
		String playerType = (String) read.getValueByNodeId(AudioActivator.NODE_NAME_MEDIATYPE);

		IMediaPlayerExtensionService service = InfomngmntEditPlugin.getPlugin().getService(
				IMediaPlayerExtensionService.class);
		IMediaPlayer mediaPlayer = service.getPlayerByType(playerType);

		this.audioHref = getFile().getProject().getFolder(ResourceUtil.BINARY_FOLDER).getFile(
				getValue().getBinaryReferences().getProjectRelativePath()).getLocation();
		/*
		 * Next: build the html snippet for displaying the media and put them
		 * into a collection This collection will be passed to freemark. The
		 * renderer will render this media html in the viewer.
		 */
		Map<String, String> freemarkParameters = new HashMap<String, String>();
		if (mediaPlayer != null) {
			freemarkParameters.put("mediaplayerheader", mediaPlayer.buildHeaderScript());
			freemarkParameters.put("mediaplayer", mediaPlayer.buildHtml(this.audioHref, 0, 0,
					Collections.<String, String> emptyMap()));
		}

		ByteArrayOutputStream returnValue = new ByteArrayOutputStream();
		InputStream templateIs = null;
		InputStream contentsIs = getFile().getContents();
		try {
			templateIs = FileLocator.openStream(Platform.getBundle(AudioActivator.PLUGIN_ID),
					new Path("template/htmlserialization.flt"), false);
			/*
			 * We give the html-snippet for creating the mediaplayer
			 */
			FreemarkerRenderer.getInstance().process(AudioActivator.PLUGIN_ID, templateIs,
					returnValue, freemarkParameters, read.getContentsAsStrucuturedMap(),
					read.getDynamicContentAsStructuredMap());

		} catch (IOException e) {
			throw new CoreException(StatusCreator.newStatus("Error reading locations", e));
		} finally {
			StreamCloser.closeStreams(templateIs, contentsIs);
		}
		return new ByteArrayInputStream(returnValue.toByteArray());
	}

}