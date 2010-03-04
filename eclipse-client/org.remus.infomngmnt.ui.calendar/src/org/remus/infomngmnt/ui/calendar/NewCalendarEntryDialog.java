/*******************************************************************************
 * Copyright (c) 2009 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/
package org.remus.infomngmnt.ui.calendar;

import java.text.NumberFormat;
import java.util.Arrays;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.databinding.EMFDataBindingContext;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.nebula.widgets.cdatetime.CDT;
import org.eclipse.nebula.widgets.cdatetime.CDateTime;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.ImageHyperlink;

import org.remus.infomngmnt.CalendarEntry;
import org.remus.infomngmnt.CalendarEntryType;
import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.common.ui.image.ResourceManager;
import org.remus.infomngmnt.core.extension.IInfoType;
import org.remus.infomngmnt.core.extension.InformationExtensionManager;
import org.remus.infomngmnt.core.services.IEditingHandler;
import org.remus.infomngmnt.ui.databinding.BindingWidgetFactory;
import org.remus.infomngmnt.ui.databinding.ComboBindingWidget;
import org.remus.infomngmnt.ui.databinding.TextBindingWidget;
import org.remus.infomngmnt.ui.editors.EditorUtil;
import org.remus.infomngmnt.ui.viewer.dialogs.InfoUnitSelectionDialog;
import org.remus.infomngmnt.ui.widgets.databinding.AdditionalBindingWidgetFactory;
import org.remus.infomngmnt.ui.widgets.databinding.CDateTimeBindingWidget;
import org.remus.infomngmnt.util.CategoryUtil;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class NewCalendarEntryDialog extends TitleAreaDialog {

	private Text text_5;
	private Text nameText;
	private Combo typeCombo;
	private Combo notificationCombo;
	private CDateTime startTime;
	private final EditingDomain editingDomain;
	private final EMFDataBindingContext ctx;
	private final CalendarEntry createCalendarEntry;
	private InformationUnitListItem selectedObject;
	private CDateTime endTime;
	private ImageHyperlink hyperlink;
	private final InformationUnit parentElement;
	private final IEditingHandler editService;

	/**
	 * Create the dialog
	 * 
	 * @param parentShell
	 * @param x
	 */
	public NewCalendarEntryDialog(final Shell parentShell, final CalendarEntry entry,
			final EditingDomain editingDomain, final InformationUnit parentElement) {
		super(parentShell);
		this.createCalendarEntry = entry;
		this.editingDomain = editingDomain;
		this.parentElement = parentElement;
		this.editService = CalendarActivator.getDefault().getEditService();
		this.ctx = new EMFDataBindingContext();
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

		final Group dateTimeGroup = new Group(container, SWT.NONE);
		dateTimeGroup.setText("Entry Details");
		final GridData gd_dateTimeGroup = new GridData(SWT.FILL, SWT.CENTER, true, false);
		dateTimeGroup.setLayoutData(gd_dateTimeGroup);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		dateTimeGroup.setLayout(gridLayout);

		final Label nameLabel = new Label(dateTimeGroup, SWT.NONE);
		nameLabel.setText("Name");

		this.nameText = new Text(dateTimeGroup, SWT.BORDER);
		final GridData gd_nameText = new GridData(SWT.FILL, SWT.CENTER, true, false);
		this.nameText.setLayoutData(gd_nameText);

		final Label startdateLabel = new Label(dateTimeGroup, SWT.NONE);
		startdateLabel.setText("Start-Date");

		this.startTime = new CDateTime(dateTimeGroup, CDT.BORDER | CDT.DROP_DOWN);
		this.startTime.setPattern("yyyy-MM-dd '@' hh:mm a");
		this.startTime.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		final Label enddateLabel = new Label(dateTimeGroup, SWT.NONE);
		enddateLabel.setText("End-Date");

		this.endTime = new CDateTime(dateTimeGroup, CDT.BORDER | CDT.DROP_DOWN);
		this.endTime.setPattern("yyyy-MM-dd '@' hh:mm a");
		this.endTime.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		final Label notificationLabel = new Label(dateTimeGroup, SWT.NONE);
		notificationLabel.setText("Notification");

		this.notificationCombo = new Combo(dateTimeGroup, SWT.NONE);
		final GridData gd_notificationCombo = new GridData(SWT.FILL, SWT.CENTER, true, false);
		this.notificationCombo.setLayoutData(gd_notificationCombo);

		final Label typeLabel = new Label(dateTimeGroup, SWT.NONE);
		typeLabel.setText("Type");

		this.typeCombo = new Combo(dateTimeGroup, SWT.NONE);
		final GridData gd_typeCombo = new GridData(SWT.FILL, SWT.CENTER, true, false);
		this.typeCombo.setLayoutData(gd_typeCombo);

		final Group informationunitGroup = new Group(container, SWT.NONE);
		final GridLayout gridLayout_1 = new GridLayout();
		gridLayout_1.numColumns = 2;
		informationunitGroup.setLayout(gridLayout_1);
		informationunitGroup.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		informationunitGroup.setText("Information-Unit");

		if (this.parentElement == null) {
			this.text_5 = new Text(informationunitGroup, SWT.BORDER);
			this.text_5.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
			this.text_5.setEnabled(false);

			final Button browseButton = new Button(informationunitGroup, SWT.NONE);
			browseButton.setText("B&rowse...");
			browseButton.addListener(SWT.Selection, new Listener() {
				public void handleEvent(final Event event) {
					InfoUnitSelectionDialog diag = InfoUnitSelectionDialog.create(getShell(), null);
					if (diag.open() == IDialogConstants.OK_ID) {
						setTargetObject(((InformationUnitListItem) diag.getFirstResult()));
					}
				}
			});
		} else {
			this.hyperlink = new ImageHyperlink(informationunitGroup, SWT.NONE);
			GridData gridData = new GridData(SWT.FILL, SWT.FILL, false, false);
			gridData.horizontalSpan = 2;
			this.hyperlink.setLayoutData(gridData);

			setTargetObject((InformationUnitListItem) this.parentElement
					.getAdapter(InformationUnitListItem.class));
		}
		//

		bindValuesToUi();
		setTitle("Edit calendar entry");
		setMessage("This wizard enables you to edit calendar entry");
		setTitleImage(ResourceManager.getPluginImage(CalendarActivator.getDefault(),
				"icons/iconexperience/wizards/calendar_entry_wizard.png"));
		return area;

	}

	protected void setTargetObject(final InformationUnitListItem informationUnit) {
		this.selectedObject = informationUnit;
		String text = CategoryUtil.categoryToString((Category) informationUnit.eContainer()) + "/"
				+ informationUnit.getLabel();
		if (this.parentElement == null) {
			this.text_5.setText(text);
		} else {
			IInfoType infoTypeByType = InformationExtensionManager.getInstance().getInfoTypeByType(
					this.selectedObject.getType());
			if (infoTypeByType != null) {
				// FIXME
				// this.hyperlink.setImage(infoTypeByType.getImage());
				this.hyperlink.setText(text);
				this.hyperlink.addHyperlinkListener(new HyperlinkAdapter() {
					@Override
					public void linkActivated(final HyperlinkEvent e) {
						EditorUtil.openInfoUnit(NewCalendarEntryDialog.this.selectedObject.getId());
						cancelPressed();
					}
				});
			}
		}

	}

	private void bindValuesToUi() {

		TextBindingWidget createTextBinding = BindingWidgetFactory.createTextBinding(this.nameText,
				this.ctx, this.editingDomain);
		createTextBinding.bindModel(this.createCalendarEntry,
				InfomngmntPackage.Literals.CALENDAR_ENTRY__TITLE);

		CDateTimeBindingWidget dateCombo = AdditionalBindingWidgetFactory.createCDateTime(
				this.startTime, this.ctx, this.editingDomain);
		dateCombo.bindModel(this.createCalendarEntry,
				InfomngmntPackage.Literals.CALENDAR_ENTRY__START);

		CDateTimeBindingWidget dateCombo2 = AdditionalBindingWidgetFactory.createCDateTime(
				this.endTime, this.ctx, this.editingDomain);
		dateCombo2.bindModel(this.createCalendarEntry,
				InfomngmntPackage.Literals.CALENDAR_ENTRY__END);

		ComboBindingWidget createComboBinding = BindingWidgetFactory.createComboBinding(
				this.typeCombo, this.ctx, this.editingDomain);
		createComboBinding.setInput(CalendarEntryType.VALUES);
		createComboBinding.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(final Object element) {
				return CalendarEntryTypeStrings
						.getStringByCalendarEntryType((CalendarEntryType) element);
			}
		});
		createComboBinding.bindModel(this.createCalendarEntry,
				InfomngmntPackage.Literals.CALENDAR_ENTRY__ENTRY_TYPE);

		ComboBindingWidget notificationBinding = BindingWidgetFactory.createComboBinding(
				this.notificationCombo, this.ctx, this.editingDomain);
		notificationBinding.setInput(Arrays.asList(new Integer[] { -1, 0, 5, 10, 15, 20, 30, 45,
				60, 90, 120, 180, 240, 300, 360, 420, 480, 540, 600, 660, 720 }));
		notificationBinding.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(final Object element) {
				return getReminderString((Integer) element);
			}
		});
		notificationBinding.bindModel(this.createCalendarEntry,
				InfomngmntPackage.Literals.CALENDAR_ENTRY__REMINDER);

	}

	@Override
	protected void okPressed() {
		if (this.parentElement == null) {
			InformationUnit adapter = (InformationUnit) this.selectedObject
					.getAdapter(InformationUnit.class);
			if (adapter != null) {
				adapter.getCalendarEntry().add(this.createCalendarEntry);
				this.editService.saveObjectToResource((IFile) adapter.getAdapter(IFile.class),
						adapter);
			}

		}
		super.okPressed();
	}

	public CalendarEntry getNewObject() {
		return this.createCalendarEntry;
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

	private static String getReminderString(final int minutes) {
		NumberFormat instance = NumberFormat.getInstance();
		instance.setMaximumFractionDigits(2);
		if (minutes < 0) {
			return "Without reminder";
		} else if (minutes < 60) {
			return minutes + " Minutes";
		} else if (minutes < 1440) {
			return instance.format(((double) minutes / 60)) + " Hours";
		} else if (minutes < 10800) {
			return instance.format((double) minutes / 1440) + " Days";
		}
		return minutes + " Minutes";
	}

	@Override
	protected void configureShell(final Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Edit Calendar Entry");
	}

}
