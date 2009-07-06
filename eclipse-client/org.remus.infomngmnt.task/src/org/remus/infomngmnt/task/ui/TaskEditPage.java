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

package org.remus.infomngmnt.task.ui;

import java.text.NumberFormat;
import java.util.Arrays;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Slider;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.common.ui.databinding.BindingWidgetFactory;
import org.remus.infomngmnt.common.ui.databinding.CheckBoxBindingWidget;
import org.remus.infomngmnt.common.ui.databinding.ComboBindingWidget;
import org.remus.infomngmnt.common.ui.databinding.DatePickerBindingWidget;
import org.remus.infomngmnt.common.ui.databinding.SpinnerSliderBindingWidget;
import org.remus.infomngmnt.common.ui.databinding.StyledTextBindingWidget;
import org.remus.infomngmnt.common.ui.databinding.TextBindingWidget;
import org.remus.infomngmnt.common.ui.jface.AnnotatingQuickFixTextBox;
import org.remus.infomngmnt.common.ui.swt.DateCombo;
import org.remus.infomngmnt.core.model.InformationUtil;
import org.remus.infomngmnt.task.TaskActivator;
import org.remus.infomngmnt.ui.extension.AbstractInformationFormPage;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class TaskEditPage extends AbstractInformationFormPage {

	private AnnotatingQuickFixTextBox description;
	private Text subjectText;
	private DateCombo dueDate;
	private Combo statusCombo;
	private DateCombo startsDate;
	private Combo prioCombo;
	private Slider slider;
	private Button completedCheckBox;
	private Button notficiaionButton;
	private Combo notificationCombo;
	private Text assigneeText;

	/**
	 * 
	 */
	public TaskEditPage() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void renderPage(IManagedForm managedForm) {
		FormToolkit toolkit = managedForm.getToolkit();
		ScrolledForm form = managedForm.getForm();
		Composite body = form.getBody();
		body.setLayout(new GridLayout());
		toolkit.paintBordersFor(body);

		doCreateGeneralSection(body, toolkit);
		doCreateDescriptionSection(body, toolkit);
		doCreateSemanticSection(body, toolkit);
		form.reflow(true);

	}

	private void doCreateGeneralSection(Composite body, FormToolkit toolkit) {
		final Section generalSection = toolkit.createSection(body, ExpandableComposite.TITLE_BAR
				| ExpandableComposite.EXPANDED);
		final GridData gd_generalSection = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		generalSection.setLayoutData(gd_generalSection);
		generalSection.setText("General");

		final Composite client = toolkit.createComposite(generalSection, SWT.NONE);
		client.setLayout(new GridLayout(6, false));
		GridData gridData = new GridData(SWT.FILL, SWT.BEGINNING, true, false);

		client.setLayoutData(gridData);

		generalSection.setClient(client);

		Label subjectLabel = toolkit.createLabel(client, "Subject");
		subjectLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));

		this.subjectText = toolkit.createText(client, "");
		this.subjectText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 5, 1));

		Label dueLabel = toolkit.createLabel(client, "Due date");
		dueLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));

		Composite dueParent = toolkit.createComposite(client);
		GridLayout layout = new GridLayout(2, false);
		layout.marginHeight = 2;
		layout.marginWidth = 1;
		layout.verticalSpacing = 2;
		layout.horizontalSpacing = 2;
		dueParent.setLayout(layout);
		this.dueDate = new DateCombo(dueParent, SWT.FLAT);
		GridDataFactory.fillDefaults().hint(120, SWT.DEFAULT).grab(true, false).applyTo(
				this.dueDate);
		this.dueDate.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
		toolkit.adapt(this.dueDate, false, false);
		toolkit.paintBordersFor(dueParent);
		GridData dueDateLayoutData = new GridData(SWT.BEGINNING, SWT.CENTER, false, false);
		dueParent.setLayoutData(dueDateLayoutData);
		toolkit.adapt(this.dueDate);

		Label statusLabel = toolkit.createLabel(client, "Status");
		statusLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));

		this.statusCombo = new Combo(client, SWT.DROP_DOWN);
		this.statusCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		toolkit.adapt(this.statusCombo, false, false);

		toolkit.createLabel(client, "");
		this.completedCheckBox = toolkit.createButton(client, "Completed", SWT.CHECK);
		this.completedCheckBox.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 5, 1));

		Label startsLabel = toolkit.createLabel(client, "Starts");
		startsLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));

		Composite startsAtParent = toolkit.createComposite(client);
		startsAtParent.setLayout(layout);
		this.startsDate = new DateCombo(startsAtParent, SWT.FLAT);
		GridDataFactory.fillDefaults().hint(120, SWT.DEFAULT).grab(true, false).applyTo(
				this.startsDate);
		this.startsDate.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
		toolkit.adapt(this.startsDate, false, false);
		toolkit.paintBordersFor(startsAtParent);
		startsAtParent.setLayoutData(dueDateLayoutData);
		toolkit.adapt(this.startsDate);

		Label prioLabel = toolkit.createLabel(client, "Priority");
		prioLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));

		this.prioCombo = new Combo(client, SWT.DROP_DOWN);
		this.prioCombo.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, true, false));

		toolkit.adapt(this.prioCombo, false, false);

		Label completion = toolkit.createLabel(client, "Completion");
		completion.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));

		Composite sliderParent = toolkit.createComposite(client);
		GridLayout sliderLayout = new GridLayout(1, false);
		sliderLayout.marginHeight = 2;
		sliderLayout.marginWidth = 1;
		sliderLayout.verticalSpacing = 2;
		sliderLayout.horizontalSpacing = 2;
		sliderParent.setLayout(sliderLayout);
		this.slider = new Slider(sliderParent, SWT.HORIZONTAL);
		this.slider.setMaximum(100);
		this.slider.setMinimum(0);
		this.slider.setIncrement(5);
		this.slider.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
		GridDataFactory.fillDefaults().hint(120, SWT.DEFAULT).grab(true, false)
				.applyTo(this.slider);
		sliderParent.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		toolkit.adapt(this.slider, false, false);

		Label createSeparator = toolkit.createSeparator(client, SWT.HORIZONTAL);
		createSeparator.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 6, 1));

		toolkit.createLabel(client, "");
		this.notficiaionButton = toolkit.createButton(client, "Notfication", SWT.CHECK);

		this.notificationCombo = new Combo(client, SWT.DROP_DOWN);
		this.notificationCombo
				.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));

		toolkit.createLabel(client, "Assignee").setLayoutData(
				new GridData(SWT.BEGINNING, SWT.CENTER, false, false));

		this.assigneeText = toolkit.createText(client, "");
		this.assigneeText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		new ContactsSmartField(this.assigneeText);
	}

	private void doCreateDescriptionSection(Composite body, FormToolkit toolkit) {
		final Section generalSection = toolkit.createSection(body, ExpandableComposite.TITLE_BAR
				| ExpandableComposite.EXPANDED);
		final GridData gd_generalSection = new GridData(SWT.FILL, SWT.FILL, true, true);
		generalSection.setLayoutData(gd_generalSection);
		generalSection.setText("Description");

		final Composite client = toolkit.createComposite(generalSection, SWT.NONE);
		client.setLayout(new GridLayout());
		GridData gridData = new GridData(SWT.FILL, SWT.BEGINNING, true, false);

		client.setLayoutData(gridData);

		generalSection.setClient(client);

		this.description = new AnnotatingQuickFixTextBox(client, "", "");
		addControl(this.description.getFTextField());

	}

	@Override
	public void bindValuesToUi() {
		StyledTextBindingWidget textBindingWidget = BindingWidgetFactory.createStyledText(
				this.description.getFTextField(), this);
		textBindingWidget.bindModel(getModelObject(),
				InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);

		DatePickerBindingWidget dateComboBinding = BindingWidgetFactory.createDateComboBinding(
				this.dueDate, this);
		dateComboBinding.bindModel(InformationUtil.getChildByType(getModelObject(),
				TaskActivator.NODE_NAME_DUE_DATE),
				InfomngmntPackage.Literals.INFORMATION_UNIT__DATE_VALUE);

		TextBindingWidget subjectBinding = BindingWidgetFactory.createTextBinding(this.subjectText,
				this);
		subjectBinding.bindModel(getModelObject(),
				InfomngmntPackage.Literals.ABSTRACT_INFORMATION_UNIT__LABEL);

		SpinnerSliderBindingWidget sliderBindingWidget = BindingWidgetFactory.createSpinner(
				this.slider, getDatabindingContext(), getEditingDomain());
		sliderBindingWidget.bindModel(InformationUtil.getChildByType(getModelObject(),
				TaskActivator.NODE_NAME_COMPLETED_PERCENTAGE),
				InfomngmntPackage.Literals.INFORMATION_UNIT__LONG_VALUE);

		CheckBoxBindingWidget completedBinding = BindingWidgetFactory.createCheckboxBinding(
				this.completedCheckBox, this);
		completedBinding.bindModel(InformationUtil.getChildByType(getModelObject(),
				TaskActivator.NODE_NAME_COMPLETED),
				InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);

		CheckBoxBindingWidget notificationCheckBinding = BindingWidgetFactory
				.createCheckboxBinding(this.notficiaionButton, this);
		notificationCheckBinding.bindModel(InformationUtil.getChildByType(getModelObject(),
				TaskActivator.NODE_NAME_NOTIFY),
				InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);

		ComboBindingWidget notificationBinding = BindingWidgetFactory.createComboBinding(
				this.notificationCombo, getDatabindingContext(), getEditingDomain());
		notificationBinding.setInput(Arrays.asList(new Long[] { -1L, 0L, 5L, 10L, 15L, 20L, 30L,
				45L, 60L, 90L, 120L, 180L, 240L, 300L, 360L, 420L, 480L, 540L, 600L, 660L, 720L }));
		notificationBinding.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(final Object element) {
				return getReminderString((Long) element);
			}
		});
		notificationBinding.bindModel(InformationUtil.getChildByType(getModelObject(),
				TaskActivator.NODE_NAME_MINUTES_BEFORE_DUE),
				InfomngmntPackage.Literals.INFORMATION_UNIT__LONG_VALUE);

		TextBindingWidget assigneeBinding = BindingWidgetFactory.createTextBinding(
				this.assigneeText, this);
		assigneeBinding.bindModel(InformationUtil.getChildByType(getModelObject(),
				TaskActivator.NODE_NAME_ASIGNEE),
				InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);

		super.bindValuesToUi();
	}

	private static String getReminderString(final long minutes) {
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

}
