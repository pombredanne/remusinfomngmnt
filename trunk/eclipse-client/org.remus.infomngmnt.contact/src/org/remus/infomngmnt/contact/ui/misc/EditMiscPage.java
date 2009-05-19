package org.remus.infomngmnt.contact.ui.misc;

import java.io.ByteArrayInputStream;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.common.ui.UIUtil;
import org.remus.infomngmnt.common.ui.databinding.BindingWidgetFactory;
import org.remus.infomngmnt.common.ui.databinding.TextBindingWidget;
import org.remus.infomngmnt.contact.ContactActivator;
import org.remus.infomngmnt.contact.core.ImageManipulation;
import org.remus.infomngmnt.contact.ui.general.CalendarDateChooser;
import org.remus.infomngmnt.core.model.InformationUtil;
import org.remus.infomngmnt.ui.extension.AbstractInformationFormPage;

public class EditMiscPage extends AbstractInformationFormPage {

	private FormToolkit toolkit;
	private Text tx_OpenGpg;
	private Text tx_Smime;
	private Text tx_Department;
	private Text tx_NameManager;
	private Text tx_Bureau;
	private Text tx_Assistant;
	private Text tx_Job;
	private Text tx_Title;
	private Text tx_Nickname;
	private Text tx_Birthday;
	private Text tx_NamePartner;
	private Text tx_Jubilee;

	public EditMiscPage() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void renderPage(final IManagedForm managedForm) {

		this.toolkit = managedForm.getToolkit();
		ScrolledForm form = managedForm.getForm();
		Composite body = form.getBody();
		body.setLayout(new GridLayout());
		this.toolkit.paintBordersFor(body);

		doCreateDetailSection(body);
		doCreateEncryptionSection(body);
		doCreateMiscSection(body);
	}

	private void doCreateMiscSection(final Composite body) {
		final Section section_1 = this.toolkit.createSection(body, ExpandableComposite.TITLE_BAR
				| ExpandableComposite.EXPANDED);
		section_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		section_1.setText("Miscellaneous");

		final Composite compositeGeneral = this.toolkit.createComposite(section_1, SWT.NONE);
		final GridLayout gridLayoutAdditional = new GridLayout();
		gridLayoutAdditional.numColumns = 2;
		compositeGeneral.setLayout(gridLayoutAdditional);
		this.toolkit.paintBordersFor(compositeGeneral);
		section_1.setClient(compositeGeneral);

		createGroupImage(compositeGeneral);

	}

	private void createGroupImage(final Composite compositeGeneral) {

		final Group group_Images = new Group(compositeGeneral, SWT.NONE);
		final GridData gd_Images = new GridData();
		group_Images.setLayoutData(gd_Images);
		final GridLayout gl_ImageGroup = new GridLayout();
		gl_ImageGroup.numColumns = 1;
		group_Images.setLayout(gl_ImageGroup);

		final Label lb_Image = this.toolkit.createLabel(group_Images, "Logo");
		GridData gd_text = new GridData(SWT.CENTER, SWT.BEGINNING, true, false);
		lb_Image.setLayoutData(gd_text);

		final Label bt_Image = this.toolkit.createLabel(group_Images, "double click me ...",
				SWT.BORDER);
		GridData gd_Image = new GridData(SWT.FILL, SWT.BEGINNING, false, true);
		final int sizeX = 150;
		final int sizeY = 150;
		gd_Image.widthHint = sizeX;
		gd_Image.heightHint = sizeY;
		bt_Image.setLayoutData(gd_Image);

		bt_Image.addMouseListener(new MouseListener() {

			public void mouseDoubleClick(final MouseEvent e) {
				// Image img = ImageManipulation.selectImageFromDialog(shell,
				// informationUnit, ContactActivator.NODE_NAME_RAWDATA_LOGO,
				// (AdapterFactoryEditingDomain)
				// editMiscPage.getEditingDomain(), sizeX, sizeY);
				// if(img != null) bt_Image.setImage(img);;
			}

			public void mouseDown(final MouseEvent e) {
				// TODO Auto-generated method stub
			}

			public void mouseUp(final MouseEvent e) {
				// TODO Auto-generated method stub
			}
		});
		InformationUnit rawData = InformationUtil.getChildByType(getModelObject(),
				ContactActivator.NODE_NAME_RAWDATA_LOGO);
		if (rawData != null && rawData.getBinaryValue() != null) {
			ByteArrayInputStream bais = new ByteArrayInputStream(rawData.getBinaryValue());
			ImageData imageData = new ImageData(bais);
			ImageData imageScaled = ImageManipulation.scaleImageToTarget(imageData, sizeX, sizeY);
			Image image = new Image(null, imageScaled);
			bt_Image.setImage(image);
		}
	}

	private void doCreateDetailSection(final Composite body) {
		final Section section_1 = this.toolkit.createSection(body, ExpandableComposite.TITLE_BAR
				| ExpandableComposite.EXPANDED);
		section_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		section_1.setText("Details");

		final Composite compositeGeneral = this.toolkit.createComposite(body, SWT.NONE);
		final GridLayout gridLayoutAdditional = new GridLayout();
		gridLayoutAdditional.numColumns = 1;
		compositeGeneral.setLayout(gridLayoutAdditional);
		compositeGeneral.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		this.toolkit.paintBordersFor(compositeGeneral);

		createGroupGeneral(compositeGeneral);
		createSeparator(compositeGeneral, true, 1);
		createGroupPerson(compositeGeneral);

	}

	private void doCreateEncryptionSection(final Composite body) {
		final Section section_1 = this.toolkit.createSection(body, ExpandableComposite.TITLE_BAR
				| ExpandableComposite.EXPANDED);
		section_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		section_1.setText("Encryption");

		final Composite compositeEncryption = this.toolkit.createComposite(section_1, SWT.NONE);
		final GridLayout gridLayoutEncryption = new GridLayout();
		gridLayoutEncryption.numColumns = 2;
		compositeEncryption.setLayout(gridLayoutEncryption);
		this.toolkit.paintBordersFor(compositeEncryption);
		section_1.setClient(compositeEncryption);
		createGroupPreferredKeys(compositeEncryption);

	}

	private void createGroupPreferredKeys(final Composite compositeEncryption) {
		GridData gd_text = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		this.toolkit.createLabel(compositeEncryption, "Preferred OpenPGP Encryption Key:");
		this.tx_OpenGpg = this.toolkit.createText(compositeEncryption, null, SWT.BORDER | SWT.FILL);
		this.tx_OpenGpg.setLayoutData(gd_text);
		this.toolkit.createLabel(compositeEncryption, "Preferred S/MIME Encryption Certificate:");
		this.tx_Smime = this.toolkit.createText(compositeEncryption, null, SWT.BORDER);
		this.tx_Smime.setLayoutData(gd_text);

		addControl(this.tx_OpenGpg);
		addControl(this.tx_Smime);

	}

	private void createGroupPerson(final Composite compositeGeneral) {
		final Composite group_Person = this.toolkit.createComposite(compositeGeneral, SWT.NONE);
		group_Person.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		group_Person.setLayout(UIUtil.createMarginLessGridLayout(5));
		this.toolkit.adapt(group_Person);
		this.toolkit.createLabel(group_Person, "Nickname:");
		this.tx_Nickname = this.toolkit.createText(group_Person, null, SWT.BORDER);
		this.tx_Nickname.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));

		this.toolkit.createLabel(group_Person, "Birthday:");
		this.tx_Birthday = this.toolkit.createText(group_Person, null);
		this.tx_Birthday.setEditable(false);
		this.tx_Birthday.setEnabled(false);

		Button bt_EditBirthday = this.toolkit.createButton(group_Person, "Edit", SWT.NONE);
		bt_EditBirthday.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				CalendarDateChooser cdd = new CalendarDateChooser(getSite().getShell(),
						EditMiscPage.this.tx_Birthday.getText());
				cdd.open();
				if (cdd != null) {
					EditMiscPage.this.tx_Birthday.setText(cdd.getSelectedDate());
				}
			}
		});

		this.toolkit.createLabel(group_Person, "Name Of Partner:");
		this.tx_NamePartner = this.toolkit.createText(group_Person, null, SWT.BORDER);
		this.tx_NamePartner.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));

		this.toolkit.createLabel(group_Person, "Jubilee:");
		this.tx_Jubilee = this.toolkit.createText(group_Person, null);
		this.tx_Jubilee.setEditable(false);
		this.tx_Jubilee.setEnabled(false);

		Button bt_EditJubilee = this.toolkit.createButton(group_Person, "Edit", SWT.NONE);
		bt_EditJubilee.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				CalendarDateChooser cdd = new CalendarDateChooser(getSite().getShell(),
						EditMiscPage.this.tx_Jubilee.getText());
				cdd.open();
				if (cdd != null) {
					EditMiscPage.this.tx_Jubilee.setText(cdd.getSelectedDate());
				}
			}
		});
	}

	private void createGroupGeneral(final Composite compositeGeneral) {

		final Composite group_General = this.toolkit.createComposite(compositeGeneral, SWT.NONE);
		group_General.setLayout(UIUtil.createMarginLessGridLayout(4));
		group_General.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		this.toolkit.adapt(group_General);

		this.toolkit.createLabel(group_General, "Department:");
		this.tx_Department = this.toolkit.createText(group_General, null, SWT.BORDER);
		this.tx_Department.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));

		this.toolkit.createLabel(group_General, "Name Of Manager:");
		this.tx_NameManager = this.toolkit.createText(group_General, null, SWT.BORDER);
		this.tx_NameManager.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));

		this.toolkit.createLabel(group_General, "Bureau:");
		this.tx_Bureau = this.toolkit.createText(group_General, null, SWT.BORDER);
		this.tx_Bureau.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));

		this.toolkit.createLabel(group_General, "Name of Assistant:");
		this.tx_Assistant = this.toolkit.createText(group_General, null, SWT.BORDER);
		this.tx_Assistant.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));

		this.toolkit.createLabel(group_General, "Job:");
		this.tx_Job = this.toolkit.createText(group_General, null, SWT.BORDER);
		this.tx_Job.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));

		this.toolkit.createLabel(group_General, "Title:");
		this.tx_Title = this.toolkit.createText(group_General, null, SWT.BORDER);
		this.tx_Title.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
	}

	public void createSeparator(final Composite compositeGeneral, final boolean isHorizontal,
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
		TextBindingWidget createTextBindingWidget0 = BindingWidgetFactory.createTextBindingWidget(
				this.tx_Smime, this);
		createTextBindingWidget0.bindModel(InformationUtil.getChildByType(getModelObject(),
				ContactActivator.NODE_MISC_MIME),
				InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);

		TextBindingWidget createTextBindingWidget2 = BindingWidgetFactory.createTextBindingWidget(
				this.tx_OpenGpg, this);
		createTextBindingWidget2.bindModel(InformationUtil.getChildByType(getModelObject(),
				ContactActivator.NODE_MISC_PUBKEY),
				InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);

		TextBindingWidget createTextBindingWidget1 = BindingWidgetFactory.createTextBindingWidget(
				this.tx_Assistant, this);
		createTextBindingWidget1.bindModel(InformationUtil.getChildByType(getModelObject(),
				ContactActivator.NODE_DETAILS_NAME_ASSISTANT),
				InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);

		TextBindingWidget createTextBindingWidget3 = BindingWidgetFactory.createTextBindingWidget(
				this.tx_Bureau, this);
		createTextBindingWidget3.bindModel(InformationUtil.getChildByType(getModelObject(),
				ContactActivator.NODE_DETAILS_BUREAU),
				InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);

		TextBindingWidget createTextBindingWidget4 = BindingWidgetFactory.createTextBindingWidget(
				this.tx_Department, this);
		createTextBindingWidget4.bindModel(InformationUtil.getChildByType(getModelObject(),
				ContactActivator.NODE_DETAILS_DEPARTMENT),
				InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);

		TextBindingWidget createTextBindingWidget6 = BindingWidgetFactory.createTextBindingWidget(
				this.tx_NameManager, this);
		createTextBindingWidget6.bindModel(InformationUtil.getChildByType(getModelObject(),
				ContactActivator.NODE_DETAILS_NAME_MANAGER),
				InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);

		TextBindingWidget createTextBindingWidget7 = BindingWidgetFactory.createTextBindingWidget(
				this.tx_NamePartner, this);
		createTextBindingWidget7.bindModel(InformationUtil.getChildByType(getModelObject(),
				ContactActivator.NODE_DETAILS_NAME_PARTNER),
				InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);

		TextBindingWidget createTextBindingWidget8 = BindingWidgetFactory.createTextBindingWidget(
				this.tx_Nickname, this);
		createTextBindingWidget8.bindModel(InformationUtil.getChildByType(getModelObject(),
				ContactActivator.NODE_DETAILS_NAME_NICK),
				InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);

		TextBindingWidget createTextBindingWidget10 = BindingWidgetFactory.createTextBindingWidget(
				this.tx_Job, this);
		createTextBindingWidget10.bindModel(InformationUtil.getChildByType(getModelObject(),
				ContactActivator.NODE_DETAILS_JOB),
				InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);

		TextBindingWidget createTextBindingWidget11 = BindingWidgetFactory.createTextBindingWidget(
				this.tx_Title, this);
		createTextBindingWidget11.bindModel(InformationUtil.getChildByType(getModelObject(),
				ContactActivator.NODE_DETAILS_TITLE),
				InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);

		TextBindingWidget createTextBindingWidget111 = BindingWidgetFactory
				.createTextBindingWidget(this.tx_Birthday, this);
		createTextBindingWidget111.bindModel(InformationUtil.getChildByType(getModelObject(),
				ContactActivator.NODE_DETAILS_BIRTHDAY),
				InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);

		TextBindingWidget createTextBindingWidget112 = BindingWidgetFactory
				.createTextBindingWidget(this.tx_Jubilee, this);
		createTextBindingWidget112.bindModel(InformationUtil.getChildByType(getModelObject(),
				ContactActivator.NODE_DETAILS_JUBILEE),
				InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);
	}
}
