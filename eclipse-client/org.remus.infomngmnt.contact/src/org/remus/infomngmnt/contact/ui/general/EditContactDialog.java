package org.remus.infomngmnt.contact.ui.general;

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
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.remus.infomngmnt.InformationUnit;

public class EditContactDialog extends TitleAreaDialog {

	private final InformationUnit contact;
	private final AdapterFactoryEditingDomain editingDomain;
	private Button bt_Ok;
	private Composite area;
	private final Composite body;
	private final FormToolkit toolkit;
	
	public EditContactDialog(Composite body, FormToolkit toolkit, Shell parentShell, InformationUnit contact, AdapterFactoryEditingDomain editingDomain) {
		super(parentShell);
		setShellStyle(getShellStyle() | SWT.RESIZE | SWT.MAX);
		this.contact = contact;
		this.editingDomain = editingDomain;
		this.body = body;
		this.toolkit = toolkit;
	}
	@Override
	protected void createButtonsForButtonBar(final Composite parent) {
		this.bt_Ok = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		this.bt_Ok.setEnabled(false);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}
	
	@Override
	protected Control createDialogArea(final Composite parent) {
		this.area = new Composite((Composite) super.createDialogArea(parent), SWT.NONE);
		this.area.setLayout(new GridLayout());
		this.area.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		final Group group_Properties = new Group(this.area, SWT.NONE);
		group_Properties.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));

		final GridLayout gl_ProportiesGroup = new GridLayout();
		gl_ProportiesGroup.numColumns = 3;
		group_Properties.setLayout(gl_ProportiesGroup);

		GridData gd_text = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		gd_text.horizontalSpan = 2;
		final Label lb_Title = toolkit.createLabel(group_Properties, "Title:");
		final Combo cb_Title = new Combo(group_Properties, SWT.DROP_DOWN | SWT.READ_ONLY);
		String[] titleItems = new String[]{"", "Dr.", "Prof.", "Female", "Miss", "Male"};
		cb_Title.setItems(titleItems);
		cb_Title.setLayoutData(gd_text);

		final Label lb_FirstName = toolkit.createLabel(group_Properties, "First Name");
		final Text tx_FirstName = toolkit.createText(group_Properties, null, SWT.BORDER);
		tx_FirstName.setLayoutData(gd_text);

		final Label lb_AdditionalName = toolkit.createLabel(group_Properties, "Additional Name");
		final Text tx_AdditionalName = toolkit.createText(group_Properties, null, SWT.BORDER);
		tx_AdditionalName.setLayoutData(gd_text);
		
		final Label lb_LastName = toolkit.createLabel(group_Properties, "Last Name");
		final Text tx_LastName = toolkit.createText(group_Properties, null, SWT.BORDER);
		tx_LastName.setLayoutData(gd_text);
		
		final Label lb_TitleAfterName = toolkit.createLabel(group_Properties, "Title After Name");
		final Text tx_TitleAfterName = toolkit.createText(group_Properties, null, SWT.BORDER);
		tx_TitleAfterName.setLayoutData(gd_text);
		
		final Label lb_FormattedName = toolkit.createLabel(group_Properties, "Formatted Name:");
		final Combo cb_FormattedName = new Combo(group_Properties, SWT.DROP_DOWN | SWT.READ_ONLY);
		final Text tx_FormattedName = toolkit.createText(group_Properties, null, SWT.BORDER);
		tx_FormattedName.setEnabled(false);
		tx_FormattedName.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		
		final Button ck_Automation = toolkit.createButton(group_Properties, "namen automatisch auswerten", SWT.CHECK);
		ck_Automation.setSelection(true);
		
		String[] items = new String[]{"User Defined", "Common Name", "Complete Name", "Converse Name, Comma Separated", "Organisation/Firm"};
		cb_FormattedName.setItems(items);
		cb_FormattedName.setText(items[1]);
		
		return this.area;
	}
	public AdapterFactoryEditingDomain getEditingDomain() {
		return editingDomain;
	}
}
