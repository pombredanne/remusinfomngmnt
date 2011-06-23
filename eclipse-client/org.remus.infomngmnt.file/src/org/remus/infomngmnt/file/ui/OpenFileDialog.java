package org.remus.infomngmnt.file.ui;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.osgi.util.NLS;
import org.eclipse.remus.InformationUnit;
import org.eclipse.remus.common.ui.UIUtil;
import org.eclipse.remus.ui.progress.CancelableRunnable;
import org.eclipse.remus.util.InformationUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.remus.infomngmnt.file.Activator;
import org.remus.infomngmnt.file.Messages;

public class OpenFileDialog extends TrayDialog {

	private final InformationUnit unit;

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */
	public OpenFileDialog(Shell parentShell, InformationUnit unit) {
		super(parentShell);
		setHelpAvailable(false);
		setShellStyle(SWT.DIALOG_TRIM | SWT.MIN | SWT.RESIZE);
		this.unit = unit;
	}

	/**
	 * Create contents of the dialog.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		FillLayout layout = new FillLayout(SWT.HORIZONTAL);
		layout.marginHeight = 10;
		layout.marginWidth = 10;
		container.setLayout(layout);

		Label lblNewLabel = new Label(container, SWT.WRAP);
		lblNewLabel
				.setText(NLS
						.bind(Messages.OpenFileDialog_Message,
								unit.getLabel()));

		return container;
	}

	/**
	 * Create contents of the button bar.
	 * 
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.CLOSE_ID,
				IDialogConstants.CLOSE_LABEL, true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(Messages.OpenFileDialog_Title);
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return UIUtil.getDialogSettingsInitialSize(Activator.getDefault(),
				this, new Point(450, 300));
	}

	@Override
	protected Point getInitialLocation(Point initialSize) {

		return UIUtil.getDialogSettingsInitialLocation(Activator.getDefault(),
				this, super.getInitialLocation(initialSize));

	}

	@Override
	public boolean close() {
		UIUtil.saveDialogSettings(Activator.getDefault(), this);
		return super.close();
	}

	@Override
	protected void buttonPressed(int buttonId) {
		if (buttonId == IDialogConstants.CLOSE_ID) {
			CancelableRunnable cancelableRunnable = new CancelableRunnable() {

				@Override
				protected IStatus runCancelableRunnable(IProgressMonitor monitor) {
					monitor.beginTask(Messages.OpenFileDialog_RefreshFiles,
							IProgressMonitor.UNKNOWN);
					IFile binaryReferenceFile = InformationUtil
							.getBinaryReferenceFile(unit);
					if (binaryReferenceFile != null
							&& binaryReferenceFile.exists()) {
						try {
							binaryReferenceFile.refreshLocal(
									IResource.DEPTH_INFINITE,
									new SubProgressMonitor(monitor, 1));
						} catch (CoreException e) {
							// skip that
						}
					}
					return Status.OK_STATUS;
				}
			};
			try {
				new ProgressMonitorDialog(getParentShell()).run(true, false,
						cancelableRunnable);
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		close();
	}

}
