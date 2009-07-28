package org.remus.infomngmnt.ui.notification;

import java.util.Collection;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.PopupDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

import org.remus.infomngmnt.Notification;
import org.remus.infomngmnt.common.ui.image.CommonImageRegistry;

/**
 * @author Mik Kersten
 * @author Leo Dos Santos
 */
public class NotificationDialog extends PopupDialog {

	private FormToolkit toolkit;

	private ScrolledForm form;

	private NotificationControl content;

	private final Composite parent2;

	private final Collection<Notification> notifications2Show;

	public NotificationDialog(final Shell parent, final Composite parent2,
			final Collection<Notification> collection) {
		super(parent, PopupDialog.INFOPOPUP_SHELLSTYLE | SWT.ON_TOP, false, false, false, false,
				false, null, null);
		this.parent2 = parent2;
		this.notifications2Show = collection;

	}

	@Override
	protected Control createContents(final Composite parent) {
		getShell().setBackground(getShell().getDisplay().getSystemColor(SWT.COLOR_DARK_GRAY));
		return createDialogArea(parent);
	}

	@Override
	public int open() {
		int open = super.open();
		// getShell().setSize(
		// SWT.DEFAULT,
		// Math.max((Math.min(this.notifications2Show.size(),
		// NotificationControl.VISIBLE_NOTIFICATIONS) * 25) + 100, 100));
		Point display = this.parent2.toDisplay(0, -getShell().getSize().y);
		if (display.y < 0) {
			display = this.parent2.toDisplay(0, 20);
		}
		getShell().setLocation(display);

		getShell().setFocus();

		return open;
	}

	@Override
	public boolean close() {
		if (this.form != null && !this.form.isDisposed()) {
			this.form.dispose();
		}

		if (this.toolkit != null) {
			if (this.toolkit.getColors() != null) {
				this.toolkit.dispose();
			}
		}

		if (this.content != null && !this.content.isDisposed()) {
			this.content.dispose();
		}

		return super.close();
	}

	@Override
	protected Control createDialogArea(final Composite parent) {
		this.toolkit = new FormToolkit(parent.getDisplay());
		this.form = this.toolkit.createScrolledForm(parent);
		this.form.setText("Notifications");
		this.form.getToolBarManager().add(new CloseDialogAction());
		this.form.getToolBarManager().update(true);
		this.form.getBody().setLayout(new TableWrapLayout());
		this.form.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		this.toolkit.decorateFormHeading(this.form.getForm());

		this.content = new NotificationControl(this.form.getBody(), this.toolkit,
				this.notifications2Show);
		this.content.setWindow(this);

		return parent;
	}

	private class CloseDialogAction extends Action {

		private CloseDialogAction() {
			setImageDescriptor(CommonImageRegistry.getInstance().getDescriptor(
					CommonImageRegistry.NOTIFICATION_CLOSE));
			setText("Close");
		}

		@Override
		public void run() {
			close();
		}

	}
}
