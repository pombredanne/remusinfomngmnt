/**
 * 
 */
package org.remus.infomngmnt.connector.twitter.desktop;

import java.util.Collections;
import java.util.Date;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;

import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.Notification;
import org.remus.infomngmnt.NotificationImportance;
import org.remus.infomngmnt.Severity;
import org.remus.infomngmnt.common.ui.UIUtil;
import org.remus.infomngmnt.common.ui.extension.AbstractTraySection;
import org.remus.infomngmnt.common.ui.image.CommonImageRegistry;
import org.remus.infomngmnt.connector.twitter.ui.ShrinkURLUtils;
import org.remus.infomngmnt.core.services.INotificationManagerManager;
import org.remus.infomngmnt.ui.UIPlugin;
import org.remus.infomngmnt.util.StatusCreator;

/**
 * @author tom
 * 
 */
public class URLShorterSection extends AbstractTraySection {

	private IJobChangeListener searchJobListener;
	private Text urlText;

	@Override
	public void createDetailsPart(final Composite parent) {
		GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.marginTop = 0;
		gridLayout.marginWidth = 0;
		parent.setLayout(gridLayout);
		this.urlText = this.toolkit.createText(parent, "", SWT.NO_BACKGROUND);

		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		gridData.widthHint = 130;
		gridData.heightHint = SWT.DEFAULT;
		this.urlText.setLayoutData(gridData);
		this.urlText.addListener(SWT.DefaultSelection, new Listener() {
			public void handleEvent(final Event event) {
				new ShrinkURLJob(URLShorterSection.this.urlText.getText()).schedule();
			}
		});
		doCreateButton(parent);
		final ProgressBar searchBar = new ProgressBar(parent, SWT.INDETERMINATE);
		GridData gridData2 = new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1);
		gridData2.widthHint = SWT.DEFAULT;
		gridData2.heightHint = 10;
		searchBar.setLayoutData(gridData2);

		Job.getJobManager().addJobChangeListener(this.searchJobListener = new JobChangeAdapter() {
			@Override
			public void scheduled(final IJobChangeEvent event) {
				if (!parent.isDisposed() && !parent.getDisplay().isDisposed()) {
					checkProgressBar(searchBar, parent.getDisplay());
				}
			}

			@Override
			public void done(final IJobChangeEvent event) {
				if (!parent.isDisposed() && !parent.getDisplay().isDisposed()) {
					checkProgressBar(searchBar, parent.getDisplay());
				}
				if (event.getJob() instanceof ShrinkURLJob) {
					Notification notification = InfomngmntFactory.eINSTANCE.createNotification();
					if (event.getResult().isOK()) {
						notification.setTimeStamp(new Date());
						notification.setMessage("The shortened URL is now in your clipboard");
						notification.setImportance(NotificationImportance.LOW);
						notification.setSeverity(Severity.OK);
						UIUtil.getDisplay().asyncExec(new Runnable() {

							public void run() {
								Clipboard clipboard = null;
								try {
									clipboard = new Clipboard(UIUtil.getDisplay());
									clipboard.setContents(new Object[] { ((ShrinkURLJob) event
											.getJob()).getShortenURl() },
											new Transfer[] { TextTransfer.getInstance() });
								} finally {
									if (clipboard != null) {
										clipboard.dispose();
									}
								}

							}

						});

					} else {
						notification.setTimeStamp(new Date());
						notification.setMessage("Error while creating shortened URL.");
						notification.setImportance(NotificationImportance.MEDIUM);
						notification.setSeverity(Severity.ERROR);
					}
					UIPlugin.getDefault().getService(INotificationManagerManager.class)
							.addNotification(Collections.singletonList(notification));
				}

			}
		});

		checkProgressBar(searchBar, parent.getDisplay());

	}

	void doCreateButton(final Composite parent) {
		ToolBarManager returnValue = new ToolBarManager(SWT.FLAT | SWT.HORIZONTAL
				| SWT.NO_BACKGROUND);
		ToolBar createControl = returnValue.createControl(parent);

		GridData gridData = new GridData(SWT.END, SWT.FILL, false, false);
		gridData.widthHint = SWT.DEFAULT;
		gridData.heightHint = SWT.DEFAULT;
		createControl.setLayoutData(gridData);

		IAction searchAction = new Action("", IAction.AS_PUSH_BUTTON) {
			@Override
			public void run() {
				new ShrinkURLJob(URLShorterSection.this.urlText.getText()).schedule();
			}
		};
		searchAction.setToolTipText("Search");
		searchAction.setImageDescriptor(CommonImageRegistry.getInstance().getDescriptor(
				CommonImageRegistry.START_TASK));
		returnValue.add(searchAction);
		returnValue.update(true);
		this.toolkit.adapt(createControl);
		createControl.setLayoutData(new GridData(SWT.END, SWT.CENTER, false, false));
	}

	protected void checkProgressBar(final ProgressBar progressBar, final Display display) {

		display.asyncExec(new Runnable() {
			public void run() {
				if (!progressBar.isDisposed()) {
					progressBar
							.setVisible(Job.getJobManager().find(ShrinkURLJob.FAMILY).length > 0);
				}
			}
		});

	}

	@Override
	public void dispose() {
		if (this.searchJobListener != null) {
			Job.getJobManager().removeJobChangeListener(this.searchJobListener);
		}
	}

	private static class ShrinkURLJob extends Job {

		private final String url;
		private String shortenURl;
		static Object FAMILY = new Object();

		public ShrinkURLJob(final String url) {
			super("Shring url");
			this.url = url;
		}

		@Override
		public boolean belongsTo(final Object family) {
			return FAMILY == family;
		}

		@Override
		protected IStatus run(final IProgressMonitor monitor) {
			try {
				this.shortenURl = ShrinkURLUtils.getBitLyUrl(this.url);
			} catch (Exception e) {
				return StatusCreator.newStatus("Error creating shortened url", e);
			}
			return Status.OK_STATUS;

		}

		public String getShortenURl() {
			return this.shortenURl;
		}

	}

}
