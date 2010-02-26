package org.remus.infomngmnt.connector.twitter.ui;

import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.PopupDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.common.ui.image.CommonImageRegistry;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class ConversationPopup extends PopupDialog {

	private FormToolkit toolkit;

	private ScrolledForm form;

	private final List<InformationUnit> messages;

	private final InformationUnit informationUnit;

	public ConversationPopup(final Shell parent, final List<InformationUnit> messages,
			final InformationUnit informationUnit) {
		super(parent, PopupDialog.INFOPOPUP_SHELLSTYLE | SWT.ON_TOP, false, false, false, false,
				false, null, null);
		this.messages = messages;
		this.informationUnit = informationUnit;

	}

	@Override
	protected Control createContents(final Composite parent) {
		getShell().setBackground(getShell().getDisplay().getSystemColor(SWT.COLOR_DARK_GRAY));
		return createDialogArea(parent);
	}

	@Override
	public int open() {
		int open = super.open();
		// int x = 0;
		// int y = -getShell().getSize().y;
		// Point display = this.parent2.toDisplay(x, y);
		// int highestDisplayWidth = 0;
		// if (display.y < 0) {
		// y = 20;
		// highestDisplayWidth = UIUtil.getHighestDisplayWidth(y);
		// } else {
		// highestDisplayWidth = UIUtil.getHighestDisplayWidth(display.y);
		// }
		// // int highestDisplayHeight = UIUtil.getHighestDisplayHeight(x)
		// if (display.x + getShell().getSize().x > highestDisplayWidth) {
		// x -= display.x + getShell().getSize().x - highestDisplayWidth;
		// }
		// if (display.x < 0) {
		// x -= display.x;
		// }
		// getShell().setLocation(this.parent2.toDisplay(x, y));
		//
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

		return super.close();
	}

	@Override
	protected Control createDialogArea(final Composite parent) {
		this.toolkit = new FormToolkit(parent.getDisplay());
		this.form = this.toolkit.createScrolledForm(parent);
		this.form.setText("Conversation");
		this.form.getToolBarManager().add(new CloseDialogAction());
		this.form.getToolBarManager().update(true);
		this.form.getBody().setLayout(new TableWrapLayout());
		this.form.getBody().setLayout(new TableWrapLayout());
		this.form.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		this.toolkit.decorateFormHeading(this.form.getForm());

		for (InformationUnit message : this.messages) {
			MessageComposite messageComposite = new MessageComposite(this.form.getBody(), SWT.NONE,
					this.toolkit, false, false, false);
			messageComposite.setValues(message);
			messageComposite.setMessageFormHyperlinkListener(new MessageHyperLinkAdapter(
					this.informationUnit));
			messageComposite.setMetaFormHyperlinkListener(new MessageHyperLinkAdapter(
					this.informationUnit));
		}
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
