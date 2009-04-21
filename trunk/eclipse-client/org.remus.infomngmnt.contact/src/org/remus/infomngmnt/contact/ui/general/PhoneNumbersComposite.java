package org.remus.infomngmnt.contact.ui.general;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.databinding.Binding;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.common.ui.UIUtil;
import org.remus.infomngmnt.common.ui.databinding.BindingWidgetFactory;
import org.remus.infomngmnt.common.ui.databinding.TextBindingWidget;
import org.remus.infomngmnt.contact.ContactActivator;
import org.remus.infomngmnt.core.model.InformationUtil;

public class PhoneNumbersComposite extends Composite {

	private Binding currentBinding;
	private TextBindingWidget createTextBindingWidget;
	public PhoneNumbersComposite(Composite parent, int style, FormToolkit toolkit, int iTextValue, final InformationUnit informationUnit, EditGeneralPage editGeneralPage) {
		super(parent, style);

		final Group group_PhoneNumbers = new Group(parent, style);
		group_PhoneNumbers.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));

		final GridLayout gl_Numbers = new GridLayout();
		gl_Numbers.numColumns = 3;
		group_PhoneNumbers.setLayout(gl_Numbers);

		List<KeyValueObject> values = new ArrayList<KeyValueObject>();
		values.add(new KeyValueObject(ContactActivator.NODE_NAME_PN_HOME, "Home"));
		values.add(new KeyValueObject(ContactActivator.NODE_NAME_PN_WORK, "Work"));
		values.add(new KeyValueObject(ContactActivator.NODE_NAME_PN_MESSANGER, "Messanger"));
		values.add(new KeyValueObject(ContactActivator.NODE_NAME_PN_VOICE, "Voice"));
		values.add(new KeyValueObject(ContactActivator.NODE_NAME_PN_FAX, "Fax"));
		values.add(new KeyValueObject(ContactActivator.NODE_NAME_PN_MOBILE, "Mobile"));
		values.add(new KeyValueObject(ContactActivator.NODE_NAME_PN_VIDEO, "Video"));
		values.add(new KeyValueObject(ContactActivator.NODE_NAME_PN_MAILBOX, "Mailbox"));
		values.add(new KeyValueObject(ContactActivator.NODE_NAME_PN_MODEM, "Modem"));
		values.add(new KeyValueObject(ContactActivator.NODE_NAME_PN_CAR, "Car"));
		values.add(new KeyValueObject(ContactActivator.NODE_NAME_PN_ISDN, "ISDN"));
		values.add(new KeyValueObject(ContactActivator.NODE_NAME_PN_PCS, "PCS"));
		values.add(new KeyValueObject(ContactActivator.NODE_NAME_PN_PAGER, "Pager"));
		values.add(new KeyValueObject(ContactActivator.NODE_NAME_PN_OTHERS, "Others"));
		
		final Combo cb = new Combo(group_PhoneNumbers, SWT.DROP_DOWN | SWT.READ_ONLY);
		ComboViewer comboViewer = new ComboViewer(cb);
		comboViewer.setContentProvider(UIUtil.getArrayContentProviderInstance());
		comboViewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				return ((KeyValueObject) element).getValue();
			}
		});
		comboViewer.setInput(values);
		final Text tx = new Text(group_PhoneNumbers, SWT.BORDER);
		GridData gd_text = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		gd_text.horizontalSpan = 2;
		tx.setLayoutData(gd_text);
		comboViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				if (!event.getSelection().isEmpty()) {
					if (currentBinding != null) {
						currentBinding.dispose();
					}
					createTextBindingWidget.bindModel(InformationUtil.getChildByType(informationUnit,
							((KeyValueObject) ((IStructuredSelection) event.getSelection()).getFirstElement()).getId()),
							InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);
					currentBinding = createTextBindingWidget.getBinding();
				}
			}
		});
		createTextBindingWidget = BindingWidgetFactory.createTextBinding(tx, editGeneralPage.getDatabindingContext(), editGeneralPage.getEditingDomain());
		comboViewer.setSelection(new StructuredSelection(values.get(iTextValue)));
	}
	private static class KeyValueObject {
		public KeyValueObject(String id, String value) {
			super();
			this.id = id;
			this.value = value;
		}

		private String id;

		public String getId() {
			return id;
		}

		public String getValue() {
			return value;
		}

		private String value;
	}
}
