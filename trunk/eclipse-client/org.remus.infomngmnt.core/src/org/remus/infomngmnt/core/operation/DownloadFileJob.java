/*******************************************************************************
 * Copyright (c) 2008 Tom Seidel, Spirit Link GmbH
 * All rights reserved.
 * 
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/
package org.remus.infomngmnt.core.operation;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.ecf.filetransfer.IFileTransferListener;
import org.eclipse.ecf.filetransfer.IIncomingFileTransfer;
import org.eclipse.ecf.filetransfer.IRetrieveFileTransferContainerAdapter;
import org.eclipse.ecf.filetransfer.IncomingFileTransferException;
import org.eclipse.ecf.filetransfer.events.IFileTransferEvent;
import org.eclipse.ecf.filetransfer.events.IIncomingFileTransferReceiveDataEvent;
import org.eclipse.ecf.filetransfer.events.IIncomingFileTransferReceiveDoneEvent;
import org.eclipse.ecf.filetransfer.events.IIncomingFileTransferReceiveStartEvent;
import org.eclipse.ecf.filetransfer.identity.FileCreateException;
import org.eclipse.ecf.filetransfer.identity.FileIDFactory;
import org.eclipse.jface.operation.ModalContext;
import org.eclipse.osgi.util.NLS;

import org.remus.infomngmnt.util.StatusCreator;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class DownloadFileJob extends Job {

	private final URL source;
	private final IFile target;
	private final IRetrieveFileTransferContainerAdapter adapter;

	public DownloadFileJob(final URL source, final IFile target,
			final IRetrieveFileTransferContainerAdapter adapter) {
		super(NLS.bind("Downloading file {0}...", source.getFile()));
		this.source = source;
		this.target = target;
		this.adapter = adapter;

	}

	@Override
	public IStatus run(final IProgressMonitor monitor) {
		final List<IIncomingFileTransfer> transfers = new ArrayList<IIncomingFileTransfer>();
		final IProgressMonitor sub = monitor;// new
		// SubProgressMonitor(monitor,
		// IProgressMonitor.UNKNOWN);
		final PipedOutputStream out = new PipedOutputStream();
		try {
			monitor.subTask("Downloading file " + this.source.getFile());

			final IFileTransferListener listener = new IFileTransferListener() {
				IIncomingFileTransfer incoming = null;
				boolean taskNameSet = false;
				private long bytesOld = 0;

				public void handleTransferEvent(final IFileTransferEvent event) {
					PipedInputStream pipedInputStream = null;
					try {
						if (event instanceof IIncomingFileTransferReceiveStartEvent) {
							final IIncomingFileTransferReceiveStartEvent rse = (IIncomingFileTransferReceiveStartEvent) event;
							try {
								pipedInputStream = new PipedInputStream();
								pipedInputStream.connect(out);
								this.incoming = rse.receive(out);
								DownloadFileJob.this.target.appendContents(pipedInputStream, true,
										false,
										new SubProgressMonitor(sub, IProgressMonitor.UNKNOWN));
							} catch (final IOException e) {
								e.printStackTrace();
								// handle Error
							} catch (final CoreException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						} else if (event instanceof IIncomingFileTransferReceiveDataEvent) {
							// do nothing
							final IIncomingFileTransfer incomingFileTransfer = ((IIncomingFileTransferReceiveDataEvent) event)
									.getSource();
							final long bytesReceived = incomingFileTransfer.getBytesReceived();

							if (bytesReceived < 0 || bytesReceived - this.bytesOld > 16384) {
								this.incoming = incomingFileTransfer;
								if (transfers.isEmpty()) {
									transfers.add(incomingFileTransfer);
								}
								this.bytesOld = bytesReceived;
								ModalContext.checkCanceled(sub);
								ModalContext.checkCanceled(monitor);
							}
						} else if (event instanceof IIncomingFileTransferReceiveDoneEvent) {
							try {
								out.flush();
								out.close();
							} catch (final IOException e) {
								// handleException
							}
							sub.done();
						}
					} catch (final InterruptedException ie) {
						// stop transfer on cancel
						this.incoming.cancel();
						monitor.setCanceled(true);
						sub.setCanceled(true);
					} finally {
						try {
							if (pipedInputStream != null) {
								pipedInputStream.close();
							}
						} catch (final IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				}
			};
			this.adapter.sendRetrieveRequest(FileIDFactory.getDefault().createFileID(
					this.adapter.getRetrieveNamespace(), this.source), listener, null);

		} catch (final IncomingFileTransferException e) {
			return StatusCreator.newStatus("Error while downloading", e);
		} catch (final FileCreateException e) {
			return StatusCreator.newStatus("Error while downloading", e);
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (final IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (!transfers.isEmpty() && transfers.get(0).getException() != null) {
			monitor.setCanceled(true);
			sub.setCanceled(true);
			return Status.CANCEL_STATUS;
		}

		return (monitor.isCanceled() || sub.isCanceled()) ? Status.CANCEL_STATUS : Status.OK_STATUS;
	}
}
