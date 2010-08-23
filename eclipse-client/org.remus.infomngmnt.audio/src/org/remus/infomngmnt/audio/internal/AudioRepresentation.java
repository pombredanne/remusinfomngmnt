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
import org.eclipse.remus.common.core.streams.StreamCloser;
import org.eclipse.remus.core.extension.AbstractInformationRepresentation;
import org.eclipse.remus.core.model.InformationStructureRead;
import org.eclipse.remus.js.rendering.FreemarkerRenderer;
import org.eclipse.remus.services.RemusServiceTracker;
import org.eclipse.remus.util.StatusCreator;

import org.remus.infomngmnt.audio.AudioActivator;
import org.remus.infomngmnt.mediaplayer.extension.IMediaPlayer;
import org.remus.infomngmnt.mediaplayer.extension.IMediaPlayerExtensionService;

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

		RemusServiceTracker remusServiceTracker = new RemusServiceTracker(Platform
				.getBundle(AudioActivator.PLUGIN_ID));

		IMediaPlayerExtensionService service = remusServiceTracker
				.getService(IMediaPlayerExtensionService.class);
		IMediaPlayer mediaPlayer = service.getPlayerByType(playerType);
		remusServiceTracker.ungetService(service);
		this.audioHref = getFile().getProject().getFolder(
				org.eclipse.remus.resources.util.ResourceUtil.BINARY_FOLDER).getFile(
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
