package org.remus.infomngmnt.contact.ui.misc;

import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.remus.infomngmnt.InformationUnit;

public class UserDefinedFieldGenerationDialog extends TitleAreaDialog {


	private final InformationUnit contact;
	private final AdapterFactoryEditingDomain editingDomain;
	private Button bt_Ok;
	private Composite area;

	public UserDefinedFieldGenerationDialog(Shell parentShell, InformationUnit contact, AdapterFactoryEditingDomain editingDomain) {
		super(parentShell);
		setShellStyle(getShellStyle() | SWT.RESIZE | SWT.MAX);
		this.contact = contact;
		this.editingDomain = editingDomain;
	}
	@Override
	protected void createButtonsForButtonBar(final Composite parent) {
		this.bt_Ok = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		this.bt_Ok.setEnabled(false);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}

	@Override
	protected void okPressed() {
		// TODO
		super.okPressed();
	}
	
	@Override
	protected Control createDialogArea(final Composite parent) {		
		
		this.area = new Composite((Composite) super.createDialogArea(parent), SWT.NONE);
		this.area.setLayout(new GridLayout());
		this.area.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		final Group group_Properties = new Group(this.area, SWT.NONE);
		group_Properties.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));

		final GridLayout gl_ProportiesGroup = new GridLayout();
		gl_ProportiesGroup.numColumns = 2;
		group_Properties.setLayout(gl_ProportiesGroup);

		final Label lb_Title = new Label(group_Properties, SWT.NONE);
		lb_Title.setText("Title:");
		final Text tx_Title = new Text(group_Properties, SWT.BORDER);
		tx_Title.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		
		final Label lb_Type = new Label(group_Properties, SWT.NONE);
		lb_Type.setText("Type:");
		final Combo co_Type = new Combo(group_Properties, SWT.DROP_DOWN | SWT.READ_ONLY);
		String[] items = new String[]{"Text", "Numerical Value","Logical Value", "Date", "Time", "Date & Time"};
		co_Type.setItems(items );
		co_Type.setText(items[0]);
		co_Type.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		
		final Button cb_SetForAllContact = new Button(group_Properties, SWT.CHECK);
		cb_SetForAllContact.setText("Available for all contacts?");
		final GridData gd_SpanHorizontal2 = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		gd_SpanHorizontal2.horizontalSpan = 2;
		cb_SetForAllContact.setLayoutData(gd_SpanHorizontal2);
		
		return this.area;
	}
}
