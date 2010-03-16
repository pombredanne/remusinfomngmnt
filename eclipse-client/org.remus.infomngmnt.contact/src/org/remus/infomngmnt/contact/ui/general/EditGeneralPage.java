/*******************************************************************************
 * Copyright (c) 2009 Jan Hartwig, FEB Radebeul
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Jan Hartwig - initial API and implementation
 *******************************************************************************/
package org.remus.infomngmnt.contact.ui.general;

/**
 * @author Jan Hartwig <jhartwig@feb-radebeul.de>
 * 
 */
import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.IValueChangeListener;
import org.eclipse.core.databinding.observable.value.ValueChangeEvent;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.databinding.EMFObservables;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.AbstractHyperlink;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.common.core.util.KeyValueObject;
import org.remus.infomngmnt.common.ui.UIUtil;
import org.remus.infomngmnt.common.ui.image.ResourceManager;
import org.remus.infomngmnt.contact.ContactActivator;
import org.remus.infomngmnt.contact.core.ContactUtil;
import org.remus.infomngmnt.contact.core.ImageManipulation;
import org.remus.infomngmnt.contact.ui.ComboAndTextFieldComposite;
import org.remus.infomngmnt.core.model.InformationStructureRead;
import org.remus.infomngmnt.ui.databinding.BindingWidgetFactory;
import org.remus.infomngmnt.ui.databinding.TextBindingWidget;
import org.remus.infomngmnt.ui.editors.editpage.AbstractInformationFormPage;
import org.remus.infomngmnt.util.InformationUtil;

public class EditGeneralPage extends AbstractInformationFormPage {

	private Label lb_Image;
	private Button bt_EditName;
	private Text tx_EditName;
	private Text tx_Role;
	private Text tx_Organisation;
	private Button bt_EditAddress;
	private Text tx_Address;
	private Text tx_BlogFeed;
	private Text tx_Homepage;
	private Button bt_EditEmail;
	private Text tx_Email;
	private Hyperlink hl_Email;
	private AbstractHyperlink tl_Homepage;
	private FormToolkit toolkit;
	private ComboAndTextFieldComposite instantMessagingComposite;
	private ComboViewer adressComboViewer;
	private ComboAndTextFieldComposite firstPhone;
	private ComboAndTextFieldComposite secondPhone;
	private ComboAndTextFieldComposite thirdPhone;

	private InformationUnit currentSelectedAdress;
	private Adapter nameChangeAdapter;
	private final Adapter adressChangeAdapter = new AdapterImpl() {
		@Override
		public void notifyChanged(final Notification msg) {
			setFormattedAdress();
		}
	};;

	public EditGeneralPage() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void renderPage(final IManagedForm managedForm) {
		this.toolkit = managedForm.getToolkit();
		ScrolledForm form = managedForm.getForm();
		Composite body = form.getBody();
		body.setLayout(new GridLayout());

		doCreateGeneralSection(body);
		doCreateSemanticSection(body, this.toolkit);
	}

	private void doCreateGeneralSection(final Composite body) {
		final Section section_1 = this.toolkit.createSection(body, ExpandableComposite.TITLE_BAR
				| ExpandableComposite.TWISTIE | ExpandableComposite.EXPANDED);
		section_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		section_1.setText("General");

		final Composite compositeGeneral = this.toolkit.createComposite(section_1, SWT.NONE);
		final GridLayout gridLayoutGeneral = new GridLayout();
		gridLayoutGeneral.numColumns = 2;
		compositeGeneral.setLayout(gridLayoutGeneral);
		this.toolkit.paintBordersFor(compositeGeneral);
		section_1.setClient(compositeGeneral);

		createGroupPerson(compositeGeneral);
		createGroupPhoneNumbers(compositeGeneral);
		createGroupAddress(compositeGeneral);
		createGroupInternet(compositeGeneral);

	}

	private void createGroupPerson(final Composite compositeGeneral) {

		final Group group_Person = new Group(compositeGeneral, SWT.NONE);
		group_Person.setText("Name");
		final GridData gd_Person = new GridData();
		gd_Person.grabExcessVerticalSpace = true;
		gd_Person.verticalAlignment = GridData.FILL;
		gd_Person.grabExcessHorizontalSpace = true;
		gd_Person.horizontalAlignment = GridData.FILL;
		group_Person.setLayoutData(gd_Person);
		final GridLayout gl_PersonGroup = new GridLayout();
		gl_PersonGroup.numColumns = 3;
		group_Person.setLayout(gl_PersonGroup);

		Composite nameComposite = this.toolkit.createComposite(group_Person);
		nameComposite.setLayout(UIUtil.createMarginLessGridLayout(2));
		nameComposite.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false, 3, 1));

		this.tx_EditName = this.toolkit.createText(nameComposite, null, SWT.BORDER);
		this.tx_EditName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		this.tx_EditName.setEditable(false);
		this.tx_EditName.setEnabled(false);
		this.bt_EditName = this.toolkit.createButton(nameComposite, "Edit Name", SWT.NONE);
		this.bt_EditName.setLayoutData(new GridData(SWT.END, SWT.CENTER, false, false));

		this.lb_Image = this.toolkit.createLabel(group_Person, "", SWT.NONE);
		this.lb_Image.setToolTipText("Add contact photo");
		GridData gd_text = new GridData(SWT.CENTER, SWT.CENTER, false, false);
		gd_text.verticalSpan = 4;
		gd_text.verticalAlignment = GridData.FILL;
		gd_text.widthHint = 64;
		gd_text.heightHint = 64;
		this.lb_Image.setLayoutData(gd_text);

		this.toolkit.createLabel(group_Person, "Role:");
		this.tx_Role = this.toolkit.createText(group_Person, null, SWT.BORDER);
		this.tx_Role.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));

		this.toolkit.createLabel(group_Person, "Organisation:");
		this.tx_Organisation = this.toolkit.createText(group_Person, null, SWT.BORDER);
		this.tx_Organisation.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));

		addControl(this.tx_Role);
		addControl(this.tx_Organisation);

		createListenerGroupPerson();

	}

	private void createTextValueBindingsGroupPerson() {
		TextBindingWidget createTextBindingWidget2 = BindingWidgetFactory.createTextBinding(
				this.tx_Role, this);
		createTextBindingWidget2.bindModel(InformationUtil.getChildByType(getModelObject(),
				ContactActivator.NODE_NAME_PERS_ROLE),
				InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);
		TextBindingWidget createTextBindingWidget3 = BindingWidgetFactory.createTextBinding(
				this.tx_Organisation, this);
		createTextBindingWidget3.bindModel(InformationUtil.getChildByType(getModelObject(),
				ContactActivator.NODE_NAME_PERS_ORGANISATION),
				InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);

	}

	private void createListenerGroupPerson() {
		this.lb_Image.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(final MouseEvent e) {
				ImageManipulation.selectImageFromDialog(getSite().getShell(), getModelObject(),
						ContactActivator.NODE_NAME_RAWDATA_IMAGE,
						(AdapterFactoryEditingDomain) getEditingDomain(), 64, 64);
			}
		});

		this.bt_EditName.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				EditContactPersonDialog ecd = new EditContactPersonDialog(getSite().getShell(),
						getModelObject(), EditGeneralPage.this);
				ecd.open();
			}
		});
	}

	private void createGroupPhoneNumbers(final Composite compositeGeneral) {

		final Group group_PhoneNumbers = new Group(compositeGeneral, SWT.NONE);
		final GridData gd_PhoneNumbers = new GridData();
		gd_PhoneNumbers.grabExcessVerticalSpace = true;
		gd_PhoneNumbers.verticalAlignment = GridData.FILL;
		gd_PhoneNumbers.grabExcessHorizontalSpace = true;
		gd_PhoneNumbers.horizontalAlignment = GridData.FILL;
		group_PhoneNumbers.setLayoutData(gd_PhoneNumbers);
		group_PhoneNumbers.setText("Phone");

		final GridLayout gl_PhoneNumbersGroup = new GridLayout();
		gl_PhoneNumbersGroup.numColumns = 1;
		group_PhoneNumbers.setLayout(gl_PhoneNumbersGroup);

		this.firstPhone = new ComboAndTextFieldComposite(group_PhoneNumbers, SWT.NONE, ContactUtil
				.getPhoneCollection(), InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE,
				this);
		this.firstPhone.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		this.secondPhone = new ComboAndTextFieldComposite(group_PhoneNumbers, SWT.NONE, ContactUtil
				.getPhoneCollection(), InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE,
				this);
		this.secondPhone.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		this.thirdPhone = new ComboAndTextFieldComposite(group_PhoneNumbers, SWT.NONE, ContactUtil
				.getPhoneCollection(), InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE,
				this);
		this.thirdPhone.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		addControl(this.firstPhone.getTx());
		addControl(this.secondPhone.getTx());
		addControl(this.thirdPhone.getTx());

	}

	private void createGroupAddress(final Composite compositeGeneral) {
		final Group group_Address = new Group(compositeGeneral, SWT.NONE);
		group_Address.setText("Adress");
		final GridData gd_GroupAddress = new GridData();
		gd_GroupAddress.grabExcessVerticalSpace = true;
		gd_GroupAddress.verticalAlignment = GridData.FILL;
		gd_GroupAddress.grabExcessHorizontalSpace = true;
		gd_GroupAddress.horizontalAlignment = GridData.FILL;
		group_Address.setLayoutData(gd_GroupAddress);

		final GridLayout gl_Address = new GridLayout(2, false);
		group_Address.setLayout(gl_Address);

		Combo adressCombo = new Combo(group_Address, SWT.DROP_DOWN | SWT.READ_ONLY);
		this.adressComboViewer = new ComboViewer(adressCombo);
		adressCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true));
		this.adressComboViewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(final Object element) {
				return ((KeyValueObject) element).getValue();
			}
		});
		this.adressComboViewer.setContentProvider(UIUtil.getArrayContentProviderInstance());
		this.adressComboViewer.setInput(ContactUtil.getAdressCollection());
		adressCombo.select(0);

		this.bt_EditAddress = this.toolkit.createButton(group_Address, "Edit", SWT.NONE);
		GridData gd_EditAddress = new GridData(SWT.END, SWT.CENTER, false, true);
		this.bt_EditAddress.setLayoutData(gd_EditAddress);

		this.tx_Address = this.toolkit.createText(group_Address, null, SWT.BORDER | SWT.MULTI
				| SWT.V_SCROLL | SWT.H_SCROLL);
		GridData gd_Address = new GridData();
		gd_Address.grabExcessVerticalSpace = true;
		gd_Address.verticalAlignment = GridData.FILL;
		gd_Address.grabExcessHorizontalSpace = true;
		gd_Address.horizontalAlignment = GridData.FILL;
		gd_Address.heightHint = 90;
		gd_Address.horizontalSpan = 2;
		this.tx_Address.setEditable(false);
		this.tx_Address.setLayoutData(gd_Address);
		addControl(this.tx_Address);
		createListenerGroupAddress(compositeGeneral);

	}

	private void createListenerGroupAddress(final Composite compositeGeneral) {

		this.bt_EditAddress.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				EditContactAddressDialog ecd = new EditContactAddressDialog(getSite().getShell(),
						EditGeneralPage.this.currentSelectedAdress, EditGeneralPage.this);
				ecd.open();
			}
		});

		this.adressComboViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(final SelectionChangedEvent event) {
				handleAdressComboChange((KeyValueObject) ((IStructuredSelection) event
						.getSelection()).getFirstElement());
			}
		});
	}

	protected void handleAdressComboChange(final KeyValueObject firstElement) {
		if (this.currentSelectedAdress != null) {
			removeAdressListeners();
		}
		if (firstElement != null) {
			InformationStructureRead read = InformationStructureRead.newSession(getModelObject());
			EList<InformationUnit> dynamicList = read
					.getDynamicList(ContactActivator.NODE_NAME_ADRESSES);
			for (InformationUnit informationUnit : dynamicList) {
				InformationStructureRead adressTypeRead = InformationStructureRead.newSession(
						informationUnit, ContactActivator.TYPE_ID);
				Object valueByNodeId = adressTypeRead
						.getValueByNodeId(ContactActivator.NODE_NAME_ADRESS);
				if (valueByNodeId != null && valueByNodeId.equals(firstElement.getId())) {
					this.currentSelectedAdress = informationUnit;
					break;
				}
			}
		} else {
			this.currentSelectedAdress = null;
		}
		this.bt_EditAddress.setEnabled(this.currentSelectedAdress != null);
		if (this.currentSelectedAdress != null) {
			addAdressListeners();
		}
		setFormattedAdress();
	}

	private void createGroupInternet(final Composite compositeGeneral) {
		final Group group_Internet = new Group(compositeGeneral, SWT.NONE);
		group_Internet.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		group_Internet.setText("Internet");

		final GridData gd_SpanHorizontal2 = new GridData(SWT.END, SWT.CENTER, false, false);
		final GridLayout gl_Internet = new GridLayout();
		gl_Internet.numColumns = 3;
		group_Internet.setLayout(gl_Internet);

		this.hl_Email = this.toolkit.createHyperlink(group_Internet, "E-Mail:", SWT.NONE);

		this.tx_Email = this.toolkit.createText(group_Internet, null, SWT.BORDER);
		this.tx_Email.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		this.tx_Email.setEditable(false);
		this.tx_Email.setEnabled(false);

		this.bt_EditEmail = this.toolkit.createButton(group_Internet, "Edit", SWT.NONE);
		this.bt_EditEmail.setLayoutData(gd_SpanHorizontal2);

		createSeparator(group_Internet, true, 3);

		this.tl_Homepage = this.toolkit.createHyperlink(group_Internet, "Homepage:", SWT.NONE);

		this.tx_Homepage = this.toolkit.createText(group_Internet, null, SWT.BORDER);
		this.tx_Homepage.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false, 2, 1));

		this.toolkit.createLabel(group_Internet, "Blog Feed:");
		this.tx_BlogFeed = this.toolkit.createText(group_Internet, null, SWT.BORDER);
		this.tx_BlogFeed.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false, 2, 1));

		this.toolkit.createLabel(group_Internet, "IM-Address:");

		this.instantMessagingComposite = new ComboAndTextFieldComposite(group_Internet, SWT.NONE,
				ContactUtil.getImObjectCollection(),
				InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE, this);
		this.instantMessagingComposite
				.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		addControl(this.tx_Email);
		addControl(this.tx_Homepage);
		addControl(this.tx_BlogFeed);
		addControl(this.instantMessagingComposite.getTx());

		createListener();
	}

	private void createTextValueBindingsGroupInternet() {
		TextBindingWidget createTextBindingWidget = BindingWidgetFactory.createTextBinding(
				this.tx_Email, this);
		createTextBindingWidget.bindModel(InformationUtil.getChildByType(getModelObject(),
				ContactActivator.NODE_MAIL_DEF),
				InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);
		TextBindingWidget createTextBindingWidget1 = BindingWidgetFactory.createTextBinding(
				this.tx_Homepage, this);
		createTextBindingWidget1.bindModel(InformationUtil.getChildByType(getModelObject(),
				ContactActivator.NODE_FRONTPAGE),
				InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);
		TextBindingWidget createTextBindingWidget2 = BindingWidgetFactory.createTextBinding(
				this.tx_BlogFeed, this);
		createTextBindingWidget2.bindModel(InformationUtil.getChildByType(getModelObject(),
				ContactActivator.NODE_BLOG_FEED),
				InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);
	}

	private void createListener() {
		this.bt_EditEmail.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				EditContactEmailDialog ecd = new EditContactEmailDialog(getSite().getShell(),
						getModelObject(), getEditingDomain());
				ecd.open();
			}
		});
		this.hl_Email.addHyperlinkListener(new HyperlinkAdapter() {
			@Override
			public void linkActivated(final HyperlinkEvent e) {
				try {
					Program.launch("mailto:" + EditGeneralPage.this.tx_Email.getText());
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		});
		this.tl_Homepage.addHyperlinkListener(new HyperlinkAdapter() {
			@Override
			public void linkActivated(final HyperlinkEvent e) {
				try {
					Program.launch(EditGeneralPage.this.tx_Homepage.getText());
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		});
	}

	private void createSeparator(final Composite compositeGeneral, final boolean isHorizontal,
			final int span) {
		final Label lb_Separator = new Label(compositeGeneral, SWT.SEPARATOR | SWT.HORIZONTAL);
		GridData gd_text = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		if (isHorizontal) {
			gd_text.horizontalSpan = span;
		} else {
			gd_text.verticalSpan = span;
		}

		lb_Separator.setLayoutData(gd_text);
	}

	@Override
	public void bindValuesToUi() {
		super.bindValuesToUi();
		createTextValueBindingsGroupPerson();
		createTextValueBindingsGroupInternet();

		setBytesToImage(InformationUtil.getChildByType(getModelObject(),
				ContactActivator.NODE_NAME_RAWDATA_IMAGE).getBinaryValue());
		IObservableValue observeValue = EMFObservables.observeValue(InformationUtil.getChildByType(
				getModelObject(), ContactActivator.NODE_NAME_RAWDATA_IMAGE),
				InfomngmntPackage.Literals.INFORMATION_UNIT__BINARY_VALUE);
		observeValue.addValueChangeListener(new IValueChangeListener() {
			public void handleValueChange(final ValueChangeEvent event) {
				byte[] value = (byte[]) event.getObservableValue().getValue();
				setBytesToImage(value);
			}
		});
		this.tx_EditName.setText(ContactUtil.getFormattedName(getModelObject()));
		this.instantMessagingComposite.setInformationUnit(getModelObject());
		this.instantMessagingComposite.bindValuesToUi();
		this.firstPhone.setInformationUnit(getModelObject());
		this.firstPhone.bindValuesToUi();
		this.firstPhone.select(0);
		this.secondPhone.setInformationUnit(getModelObject());
		this.secondPhone.bindValuesToUi();
		this.secondPhone.select(1);
		this.thirdPhone.setInformationUnit(getModelObject());
		this.thirdPhone.bindValuesToUi();
		this.thirdPhone.select(4);
		handleAdressComboChange((KeyValueObject) ((IStructuredSelection) this.adressComboViewer
				.getSelection()).getFirstElement());
		addNameListeners();
		InformationStructureRead read = InformationStructureRead.newSession(getModelObject());
		Object valueByNodeId = read.getValueByNodeId("adresses/workAdress/street");
		System.out.println(valueByNodeId);

	}

	@Override
	public void disposeBinding() {
		super.disposeBinding();
		removeNameListener();
		handleAdressComboChange(null);
	}

	private void removeNameListener() {
		InformationUtil
				.getChildByType(getModelObject(), ContactActivator.NODE_NAME_PERS_NAME_TITLE)
				.eAdapters().remove(this.nameChangeAdapter);
		InformationUtil
				.getChildByType(getModelObject(), ContactActivator.NODE_NAME_PERS_NAME_FIRST)
				.eAdapters().remove(this.nameChangeAdapter);
		InformationUtil.getChildByType(getModelObject(),
				ContactActivator.NODE_NAME_PERS_NAME_ADDITIONAL).eAdapters().remove(
				this.nameChangeAdapter);
		InformationUtil.getChildByType(getModelObject(), ContactActivator.NODE_NAME_PERS_NAME_LAST)
				.eAdapters().remove(this.nameChangeAdapter);
		InformationUtil.getChildByType(getModelObject(),
				ContactActivator.NODE_NAME_PERS_NAME_TITLE_AFTER).eAdapters().remove(
				this.nameChangeAdapter);
	}

	private void removeAdressListeners() {
		InformationUtil.getChildByType(this.currentSelectedAdress,
				ContactActivator.NODE_NAME_ADRESS_STREET).eAdapters().remove(
				this.adressChangeAdapter);
		InformationUtil.getChildByType(this.currentSelectedAdress,
				ContactActivator.NODE_NAME_ADRESS_LOCALITY).eAdapters().remove(
				this.adressChangeAdapter);
		InformationUtil.getChildByType(this.currentSelectedAdress,
				ContactActivator.NODE_NAME_ADRESS_POST_OFFICE_BOX).eAdapters().remove(
				this.adressChangeAdapter);
		InformationUtil.getChildByType(this.currentSelectedAdress,
				ContactActivator.NODE_NAME_ADRESS_POSTAL).eAdapters().remove(
				this.adressChangeAdapter);
		InformationUtil.getChildByType(this.currentSelectedAdress,
				ContactActivator.NODE_NAME_ADRESS_REGION).eAdapters().remove(
				this.adressChangeAdapter);

	}

	private void addNameListeners() {
		this.nameChangeAdapter = new AdapterImpl() {
			@Override
			public void notifyChanged(final Notification msg) {
				setFormattedName();
			}
		};
		InformationUtil
				.getChildByType(getModelObject(), ContactActivator.NODE_NAME_PERS_NAME_TITLE)
				.eAdapters().add(this.nameChangeAdapter);
		InformationUtil
				.getChildByType(getModelObject(), ContactActivator.NODE_NAME_PERS_NAME_FIRST)
				.eAdapters().add(this.nameChangeAdapter);
		InformationUtil.getChildByType(getModelObject(),
				ContactActivator.NODE_NAME_PERS_NAME_ADDITIONAL).eAdapters().add(
				this.nameChangeAdapter);
		InformationUtil.getChildByType(getModelObject(), ContactActivator.NODE_NAME_PERS_NAME_LAST)
				.eAdapters().add(this.nameChangeAdapter);
		InformationUtil.getChildByType(getModelObject(),
				ContactActivator.NODE_NAME_PERS_NAME_TITLE_AFTER).eAdapters().add(
				this.nameChangeAdapter);

	}

	private void addAdressListeners() {
		InformationUtil.getChildByType(this.currentSelectedAdress,
				ContactActivator.NODE_NAME_ADRESS_STREET).eAdapters().add(this.adressChangeAdapter);
		InformationUtil.getChildByType(this.currentSelectedAdress,
				ContactActivator.NODE_NAME_ADRESS_LOCALITY).eAdapters().add(
				this.adressChangeAdapter);
		InformationUtil.getChildByType(this.currentSelectedAdress,
				ContactActivator.NODE_NAME_ADRESS_POST_OFFICE_BOX).eAdapters().add(
				this.adressChangeAdapter);
		InformationUtil.getChildByType(this.currentSelectedAdress,
				ContactActivator.NODE_NAME_ADRESS_POSTAL).eAdapters().add(this.adressChangeAdapter);
		InformationUtil.getChildByType(this.currentSelectedAdress,
				ContactActivator.NODE_NAME_ADRESS_REGION).eAdapters().add(this.adressChangeAdapter);

	}

	private void setFormattedName() {
		String formattedName = ContactUtil.getFormattedName(getModelObject());
		this.tx_EditName.setText(formattedName);
		getModelObject().setLabel(formattedName);
	}

	private void setFormattedAdress() {
		String formattedAdress = ContactUtil.getFormattedAdress(this.currentSelectedAdress);
		this.tx_Address.setText(formattedAdress);
	}

	protected void setBytesToImage(final byte[] value) {
		if (value == null) {
			this.lb_Image.setImage(ResourceManager.getPluginImage(ContactActivator.getDefault(),
					"icons/iconexperience/photo_portrait.png"));
		} else {
			ByteArrayInputStream bais = new ByteArrayInputStream(value);
			ImageData imageData = new ImageData(bais);
			Image image = new Image(null, imageData);
			this.lb_Image.setImage(image);
			try {
				bais.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}