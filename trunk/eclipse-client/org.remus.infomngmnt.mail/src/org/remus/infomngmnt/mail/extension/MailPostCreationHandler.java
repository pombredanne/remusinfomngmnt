/*******************************************************************************
 * Copyright (c) 2011 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/
package org.remus.infomngmnt.mail.extension;

import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.IOUtils;
import org.cyberneko.html.parsers.DOMParser;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.ecf.core.ContainerCreateException;
import org.eclipse.ecf.core.ContainerFactory;
import org.eclipse.ecf.core.IContainer;
import org.eclipse.ecf.filetransfer.IRetrieveFileTransferContainerAdapter;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.remus.InformationUnit;
import org.eclipse.remus.common.core.streams.StreamUtil;
import org.eclipse.remus.common.core.util.ResourceUtil;
import org.eclipse.remus.common.io.transfer.DownloadFileJob;
import org.eclipse.remus.core.commands.CommandFactory;
import org.eclipse.remus.core.commands.CreateBinaryReferenceCommand;
import org.eclipse.remus.core.commands.ICompoundableCreateCommand;
import org.eclipse.remus.core.create.PostCreationHandler;
import org.eclipse.remus.core.model.InformationStructureEdit;
import org.eclipse.remus.core.model.InformationStructureRead;
import org.eclipse.remus.core.services.IEditingHandler;
import org.remus.infomngmnt.mail.ContentType;
import org.remus.infomngmnt.mail.MailActivator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class MailPostCreationHandler extends PostCreationHandler {

	private IContainer container;
	private IRetrieveFileTransferContainerAdapter fileReceiveAdapter;

	@Override
	public Command handlePreSaving(InformationUnit unit,
			IProgressMonitor monitor) {
		InformationStructureRead read = InformationStructureRead
				.newSession(unit);
		String url = (String) read
				.getValueByNodeId(MailActivator.NODE_NAME_MORE_INFO);
		List<Command> parseContent = new ArrayList<Command>();
		if (read.getValueByNodeId(MailActivator.NODE_NAME_CONTENT) == null
				&& url != null) {
			try {
				URL url2Download = new URL(url);
				IFile tempFile = ResourceUtil.createTempFile();
				DownloadFileJob job = new DownloadFileJob(url2Download,
						tempFile, getFileReceiveAdapter());
				IStatus run = job.run(monitor);
				if (run.isOK()) {
					InputStream contents = tempFile.getContents();
					String content = StreamUtil.convertStreamToString(contents);
					InformationStructureEdit edit = InformationStructureEdit
							.newSession(MailActivator.INFO_TYPE_ID);
					edit.setValue(unit, MailActivator.NODE_NAME_CONTENT,
							content);
					edit.setValue(unit, MailActivator.NODE_NAME_RECEIVED,
							new Date());
					edit.setValue(unit, MailActivator.INFO_TYPE_ID,
							unit.getLabel());
					edit.setValue(unit, MailActivator.NODE_NAME_CONTENT_TYPE,
							ContentType.HTML);
					parseContent = parseContent(unit, monitor, url2Download);
				}
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return new MulipleAddCommand(parseContent);
	}

	private List<Command> parseContent(final InformationUnit unit,
			final IProgressMonitor monitor, URL url2Download) {
		List<Command> returnValue = new ArrayList<Command>();
		IEditingHandler service = MailActivator.getDefault()
				.getServiceTracker().getService(IEditingHandler.class);
		InformationStructureRead read = InformationStructureRead
				.newSession(unit);
		AdapterFactoryEditingDomain editingDomain = service
				.getNavigationEditingDomain();
		InformationStructureEdit edit = InformationStructureEdit.newSession(
				MailActivator.INFO_TYPE_ID, editingDomain);
		String content = (String) read
				.getValueByNodeId(MailActivator.NODE_NAME_CONTENT);
		final DOMParser parser = new DOMParser();

		try {
			parser.parse(new InputSource(new StringReader(content)));
			final Document document = parser.getDocument();

			/*
			 * Replace all imgs with
			 */
			NodeList elementsByTagName = document.getElementsByTagName("img"); //$NON-NLS-1$
			for (int i = 0; i < elementsByTagName.getLength(); i++) {
				final Node node = elementsByTagName.item(i);
				String src = ((Element) node).getAttribute("src"); //$NON-NLS-1$
				URL url;
				if (src.startsWith("http")) {
					url = new URL(src);
				} else {
					url = new URL(url2Download, src);
				}

				IFile tmpFile;
				String fileExtension = new Path(src).getFileExtension();
				if (fileExtension == null || fileExtension.indexOf('?') != -1) {
					fileExtension = ""; //$NON-NLS-1$
					tmpFile = ResourceUtil.createTempFile();
				} else {
					tmpFile = ResourceUtil.createTempFile(fileExtension);
				}
				DownloadFileJob downloadFileJob = new DownloadFileJob(url,
						tmpFile, getFileReceiveAdapter());
				IStatus run = downloadFileJob.run(monitor);
				if (run.isOK()) {
					InformationUnit createSubType = edit.createSubType(
							MailActivator.NODE_NAME_EMBEDDED, null);
					edit.addDynamicNode(unit, createSubType, editingDomain);

					CreateBinaryReferenceCommand addFileToInfoUnit = CommandFactory
							.addFileToInfoUnit(tmpFile, createSubType,
									editingDomain);
					returnValue.add(addFileToInfoUnit);
					// IFile targetFile = addFileToInfoUnit.getTargetFile();
					// if (targetFile.exists()) {
					((Element) node).setAttribute("src", //$NON-NLS-1$
							org.eclipse.remus.common.core.util.StringUtils
									.join("cid:", addFileToInfoUnit //$NON-NLS-1$
											.getCreatedId()));
					// changed = true;
					// }
				}

			}
			elementsByTagName = document.getElementsByTagName("link"); //$NON-NLS-1$
			for (int i = 0; i < elementsByTagName.getLength(); i++) {
				final Node node = elementsByTagName.item(i);
				if (((Element) node).getAttribute("rel") != null
						&& "stylesheet".equals(((Element) node)
								.getAttribute("rel"))) {

					String src = ((Element) node).getAttribute("href"); //$NON-NLS-1$
					URL url;
					if (src.startsWith("http")) {
						url = new URL(src);
					} else {
						url = new URL(url2Download, src);
					}

					IFile tmpFile;
					String fileExtension = new Path(src).getFileExtension();
					if (fileExtension != null
							&& fileExtension.indexOf(";") != -1) {
						fileExtension = fileExtension.split(";")[0];
					}
					if (fileExtension == null
							|| fileExtension.indexOf('?') != -1) {
						fileExtension = ""; //$NON-NLS-1$
						tmpFile = ResourceUtil.createTempFile();
					} else {
						tmpFile = ResourceUtil.createTempFile(fileExtension);
					}
					DownloadFileJob downloadFileJob = new DownloadFileJob(url,
							tmpFile, getFileReceiveAdapter());
					IStatus run = downloadFileJob.run(monitor);
					if (run.isOK()) {
						InformationUnit createSubType = edit.createSubType(
								MailActivator.NODE_NAME_EMBEDDED, null);
						edit.addDynamicNode(unit, createSubType, editingDomain);

						CreateBinaryReferenceCommand addFileToInfoUnit = CommandFactory
								.addFileToInfoUnit(tmpFile, createSubType,
										editingDomain);
						returnValue.add(addFileToInfoUnit);
						// IFile targetFile = addFileToInfoUnit.getTargetFile();
						// if (targetFile.exists()) {
						((Element) node).setAttribute("href", //$NON-NLS-1$
								org.eclipse.remus.common.core.util.StringUtils
										.join("cid:", addFileToInfoUnit //$NON-NLS-1$
												.getCreatedId()));
						// changed = true;
						// }
					}
				}

			}
			Calendar instance = Calendar.getInstance();
			instance.setTime(new Date());
			instance.add(Calendar.WEEK_OF_YEAR, 1);
			instance.getTime();
			elementsByTagName = document.getElementsByTagName("a"); //$NON-NLS-1$
			for (int i = 0; i < elementsByTagName.getLength(); i++) {
				final Node node = elementsByTagName.item(i);

				String src = ((Element) node).getAttribute("href"); //$NON-NLS-1$
				URL url;
				if (src.startsWith("http")) {
					url = new URL(src);
				} else {
					try {
						url = new URL(url2Download, src);
						((Element) node).setAttribute("href", url.toString());
					} catch (Exception e) {
						continue;
					}
				}

			}
			final Transformer transformer = TransformerFactory.newInstance()
					.newTransformer();
			transformer.setOutputProperty("omit-xml-declaration", "yes"); //$NON-NLS-1$ //$NON-NLS-2$

			final DOMSource source = new DOMSource(document);
			final StringWriter writer = new StringWriter();

			final StreamResult result = new StreamResult(writer);
			transformer.transform(source, result);

			InformationUnit createSubType = edit.createSubType(
					MailActivator.NODE_NAME_EMBEDDED, null);
			edit.addDynamicNode(unit, createSubType, editingDomain);

			IFile createTempFile = ResourceUtil.createTempFile(".htm");
			InputStream inputStream = IOUtils.toInputStream(writer.toString());
			createTempFile.setContents(inputStream, true, false, monitor);
			CreateBinaryReferenceCommand referenceCommand = new CreateBinaryReferenceCommand(
					createTempFile, createSubType, editingDomain);
			returnValue.add(referenceCommand);

			String iframe = "<iframe src=\"cid:"
					+ referenceCommand.getCreatedId()
					+ "\" width=\"100%\" height=\"100%\"></iframe>";
			edit.setValue(unit, MailActivator.NODE_NAME_CONTENT, iframe);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// editingDomain.dispose();
		}
		MailActivator.getDefault().getServiceTracker().ungetService(service);
		return returnValue;
	}

	protected IRetrieveFileTransferContainerAdapter getFileReceiveAdapter() {
		if (container == null) {
			try {
				container = ContainerFactory.getDefault().createContainer();
			} catch (final ContainerCreateException e) {
				throw new RuntimeException("Error initializing ECF-Container",
						e);
			}
			fileReceiveAdapter = (IRetrieveFileTransferContainerAdapter) container
					.getAdapter(IRetrieveFileTransferContainerAdapter.class);
		}
		return fileReceiveAdapter;
	}

	private class MulipleAddCommand extends CompoundCommand implements
			ICompoundableCreateCommand {

		public MulipleAddCommand(List<Command> commandList) {
			super(commandList);
		}

		public void setTarget(IFile file) {
			List<Command> commandList2 = commandList;
			for (Command command : commandList2) {
				if (command instanceof ICompoundableCreateCommand) {
					((ICompoundableCreateCommand) command).setTarget(file);
				}

			}

		}

	}

}
