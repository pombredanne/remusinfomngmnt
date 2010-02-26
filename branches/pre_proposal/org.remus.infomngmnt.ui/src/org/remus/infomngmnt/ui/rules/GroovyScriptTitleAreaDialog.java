package org.remus.infomngmnt.ui.rules;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import org.remus.infomngmnt.common.ui.image.ResourceManager;
import org.remus.infomngmnt.ui.UIPlugin;

public class GroovyScriptTitleAreaDialog extends TitleAreaDialog {

	private Text text;
	private String script;

	/**
	 * Create the dialog
	 * 
	 * @param parentShell
	 */
	public GroovyScriptTitleAreaDialog(final Shell parentShell, final String script) {
		super(parentShell);
		setShellStyle(getShellStyle() | SWT.RESIZE | SWT.MAX);
		this.script = script;
	}

	@Override
	protected void configureShell(final Shell newShell) {

		super.configureShell(newShell);

		newShell.setText("Enter a groovy script.");
	}

	/**
	 * Create contents of the dialog
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(final Composite parent) {

		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayout(new GridLayout());
		container.setLayoutData(new GridData(GridData.FILL_BOTH));

		final Label enterGroovyScriptLabel = new Label(container, SWT.NONE);
		final GridData gd_enterGroovyScriptLabel = new GridData(SWT.FILL, SWT.CENTER, true, false);
		enterGroovyScriptLabel.setLayoutData(gd_enterGroovyScriptLabel);
		enterGroovyScriptLabel.setText("Enter groovy script to determinate...");

		this.text = new Text(container, SWT.V_SCROLL | SWT.MULTI | SWT.H_SCROLL | SWT.BORDER);
		this.text.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		this.text.setFont(ResourceManager.getFont("Courier", 12, SWT.NONE));
		this.text.setText(this.script == null ? "" : this.script);

		setTitle("Enter a groovy script.");
		setMessage("You can specify if the rule is selectable by the dropped/paste input");
		setTitleImage(ResourceManager.getPluginImage(UIPlugin.getDefault(),
				"icons/iconexperience/wizards/script_edit_wizard.png"));
		this.text.addListener(SWT.Modify, new Listener() {
			public void handleEvent(final Event event) {
				GroovyScriptTitleAreaDialog.this.script = GroovyScriptTitleAreaDialog.this.text
						.getText();

			}

		});
		return area;
	}

	/**
	 * Create contents of the button bar
	 * 
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(final Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(500, 375);
	}

	public String getScript() {
		return this.script;
	}

}
