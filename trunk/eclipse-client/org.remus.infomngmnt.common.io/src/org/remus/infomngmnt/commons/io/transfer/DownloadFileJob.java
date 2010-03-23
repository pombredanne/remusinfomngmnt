/*******************************************************************************
 * Copyright (c) 2008 Tom Seidel, Spirit Link GmbH
 * All rights reserved.
 * 
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/
package org.remus.infomngmnt.commons.io.transfer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import org.eclipse.osgi.util.NLS;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class DownloadFileJob extends Job {

	private final URL source;
	private IFile target;
	private final IRetrieveFileTransferContainerAdapter adapter;
	private File file;

	public DownloadFileJob(final URL source, final IFile target,
			final IRetrieveFileTransferContainerAdapter adapter) {
		super(NLS.bind("Downloading file {0}...", source.getFile()));
		this.source = source;
		this.target = target;
		this.adapter = adapter;

	}

	public DownloadFileJob(final URL source, final File target,
			final IRetrieveFileTransferContainerAdapter adapter) {
		super(NLS.bind("Downloading file {0}...", source.getFile()));
		this.source = source;
		this.file = target;
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
								if (DownloadFileJob.this.target != null) {
									DownloadFileJob.this.target.appendContents(pipedInputStream,
											true, false, new SubProgressMonitor(sub,
													IProgressMonitor.UNKNOWN));
								} else if (DownloadFileJob.this.file != null) {
									if (!DownloadFileJob.this.file.exists()) {
										DownloadFileJob.this.file.createNewFile();
									}
									FileOutputStream fileOutputStream = new FileOutputStream(
											DownloadFileJob.this.file);
									stream(pipedInputStream, fileOutputStream);
								} else {
									throw new IllegalArgumentException("Target file cannot be null");
								}
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
								checkCanceled(sub);
								checkCanceled(monitor);
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
			// return StatusCreator.newStatus("Error while downloading", e);
		} catch (final FileCreateException e) {
			// return StatusCreator.newStatus("Error while downloading", e);
		} catch (final Exception e) {
			// return StatusCreator.newStatus("Error while downloading", e);
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

	public static long stream(final InputStream inputStream, final OutputStream outputStream)
			throws IOException {

		if (inputStream == null) {
			throw new IllegalArgumentException("input stream cannot be null");
		}

		if (outputStream == null) {
			throw new IllegalArgumentException("output stream cannot be null");
		}

		// BufferedInputStream bufferedInputStream = null;
		// if (inputStream instanceof BufferedInputStream) {
		// bufferedInputStream = (BufferedInputStream) inputStream;
		// } else {
		// bufferedInputStream = new BufferedInputStream(inputStream);
		// }
		//
		// BufferedOutputStream bufferedOutputStream = null;
		// if (outputStream instanceof BufferedOutputStream) {
		// bufferedOutputStream = (BufferedOutputStream) outputStream;
		// } else {
		// bufferedOutputStream = new BufferedOutputStream(outputStream);
		// }

		long counter = 0;
		int flushCounter = 0;

		byte[] buffer = new byte[4096];
		int read;
		while ((read = inputStream.read(buffer)) >= 0) {

			outputStream.write(buffer, 0, read);

			counter += read;
			flushCounter += read;

			// flush output stream every 8kB:
			if (flushCounter >= 8192) {
				outputStream.flush();
				flushCounter = 0;
			}

		}

		outputStream.flush();

		return counter;

	}

	public static void checkCanceled(final IProgressMonitor monitor) throws InterruptedException {
		if (monitor.isCanceled()) {
			throw new InterruptedException();
		}
	}
}
