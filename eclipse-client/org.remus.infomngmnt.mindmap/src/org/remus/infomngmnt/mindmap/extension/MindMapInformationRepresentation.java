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

package org.remus.infomngmnt.mindmap.extension;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.widgets.Display;
import org.xmind.core.ISheet;
import org.xmind.core.ITopic;
import org.xmind.core.IWorkbook;
import org.xmind.core.io.IStorage;
import org.xmind.ui.internal.editor.WorkbookRef;
import org.xmind.ui.internal.editor.WorkbookRefManager;
import org.xmind.ui.mindmap.MindMapImageExtractor;

import org.remus.infomngmnt.BinaryReference;
import org.remus.infomngmnt.common.core.streams.StreamCloser;
import org.remus.infomngmnt.core.extension.AbstractInformationRepresentation;
import org.remus.infomngmnt.core.model.InformationStructureRead;
import org.remus.infomngmnt.jslib.rendering.FreemarkerRenderer;
import org.remus.infomngmnt.mindmap.MindmapActivator;
import org.remus.infomngmnt.util.InformationUtil;
import org.remus.infomngmnt.util.StatusCreator;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class MindMapInformationRepresentation extends AbstractInformationRepresentation {

	private WorkbookRefManager manager;
	private WorkbookRef workbookRef;
	private IStorage storage;
	private String imageHref;

	@Override
	public void handlePreBuild(final IProgressMonitor monitor) {
		InformationStructureRead read = InformationStructureRead.newSession(getValue());
		List<BinaryReference> binaryReferences = read.getBinaryReferences();
		try {
			if (binaryReferences.size() > 0) {
				IFile binaryReferenceToFile = InformationUtil.binaryReferenceToFile(
						binaryReferences.get(0), getValue());
				this.manager = WorkbookRefManager.getInstance();
				this.workbookRef = this.manager.addReferrer(binaryReferenceToFile, null);
				this.storage = this.workbookRef.createStorage();
				this.workbookRef.loadWorkbook(this.storage, null, monitor);
			}
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (org.xmind.core.CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		super.handlePreBuild(monitor);
	}

	@Override
	public void handlePostBuild(final IFile derivedFile, final IProgressMonitor monitor)
			throws CoreException {
		this.workbookRef.dispose(true);
		super.handlePostBuild(derivedFile, monitor);
	}

	/**
	 * 
	 */
	public MindMapInformationRepresentation() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.core.extension.AbstractInformationRepresentation
	 * #getBodyForIndexing(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@SuppressWarnings("restriction")
	@Override
	public String getBodyForIndexing(final IProgressMonitor monitor) throws CoreException {

		WorkbookRef localworkbookRef = null;
		InformationStructureRead read = InformationStructureRead.newSession(getValue());
		List<BinaryReference> binaryReferences = read.getBinaryReferences();
		try {
			if (binaryReferences.size() > 0) {
				IFile binaryReferenceToFile = InformationUtil.binaryReferenceToFile(
						binaryReferences.get(0), getValue());
				WorkbookRefManager localmanager = WorkbookRefManager.getInstance();
				localworkbookRef = localmanager.addReferrer(binaryReferenceToFile, null);
				IStorage localstorage = localworkbookRef.createStorage();
				localworkbookRef.loadWorkbook(localstorage, null, monitor);
			}
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (org.xmind.core.CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StringBuffer sb = new StringBuffer();
		if (localworkbookRef != null) {
			IWorkbook workbook = localworkbookRef.getWorkbook();
			List<ISheet> sheets = workbook.getSheets();
			for (ISheet iSheet : sheets) {
				ITopic rootTopic = iSheet.getRootTopic();
				sb.append(appendTopic(rootTopic));
			}
			localworkbookRef.dispose(true);
		}
		return sb.toString();
	}

	private StringBuffer appendTopic(final ITopic rootTopic) {
		StringBuffer returnValue = new StringBuffer();
		returnValue.append(rootTopic.getTitleText()).append(" ");
		Set<String> labels = rootTopic.getLabels();
		for (String string : labels) {
			returnValue.append(string).append(" ");
		}
		List<ITopic> allChildren = rootTopic.getAllChildren();
		for (ITopic iTopic : allChildren) {
			returnValue.append(appendTopic(iTopic));
		}
		return returnValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.core.extension.AbstractInformationRepresentation
	 * #handleHtmlGeneration(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@SuppressWarnings("restriction")
	@Override
	public InputStream handleHtmlGeneration(final IProgressMonitor monitor) throws CoreException {

		try {
			IWorkbook workbook = this.workbookRef.getWorkbook();
			MindMapImageExtractor mindMapImageExtractor = new MindMapImageExtractor(Display
					.getDefault(), workbook.getPrimarySheet(), workbook.getPrimarySheet()
					.getRootTopic());
			Image image = mindMapImageExtractor.getImage();
			ImageLoader loader = new ImageLoader();

			loader.data = new ImageData[] { image.getImageData() };
			IFile file = getBuildFolder().getFile("out.png");
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			loader.save(baos, SWT.IMAGE_PNG);
			mindMapImageExtractor.dispose();
			this.imageHref = file.getLocation().toOSString();
			byte[] bytesFromFile = baos.toByteArray();
			baos.close();
			if (!file.exists()) {
				file.create(new ByteArrayInputStream(bytesFromFile), true, monitor);
			} else {
				file.setContents(new ByteArrayInputStream(bytesFromFile), true, false, monitor);
			}
			InformationStructureRead read = InformationStructureRead.newSession(getValue());
			ByteArrayOutputStream returnValue = new ByteArrayOutputStream();
			InputStream templateIs = null;
			try {
				templateIs = FileLocator.openStream(Platform.getBundle(MindmapActivator.PLUGIN_ID),
						new Path("template/htmlserialization.flt"), false);
				FreemarkerRenderer.getInstance().process(
						MindmapActivator.PLUGIN_ID,
						templateIs,
						returnValue,
						Collections.singletonMap("imageHref", URI.createFileURI(file.getLocation()
								.toOSString())), read.getContentsAsStrucuturedMap(),
						read.getDynamicContentAsStructuredMap());
			} catch (IOException e) {
				throw new CoreException(StatusCreator.newStatus("Error reading locations", e));
			} finally {
				StreamCloser.closeStreams(templateIs);
			}
			return new ByteArrayInputStream(returnValue.toByteArray());

		} catch (IOException e) {
			throw new CoreException(StatusCreator.newStatus("Error generating html-output", e));
		}

	}

	@Override
	public boolean createFolderOnBuild() {
		return true;
	}

}