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

import java.util.Date;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.databinding.EMFObservables;
import org.eclipse.emf.edit.command.DeleteCommand;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.nebula.widgets.cdatetime.CDT;
import org.eclipse.nebula.widgets.cdatetime.CDateTime;
import org.eclipse.remus.InfomngmntPackage;
import org.eclipse.remus.InformationUnit;
import org.eclipse.remus.common.ui.image.ResourceManager;
import org.eclipse.remus.common.ui.jface.AnnotatingQuickFixTextBox;
import org.eclipse.remus.core.model.InformationStructureEdit;
import org.eclipse.remus.impl.InformationUnitImpl;
import org.eclipse.remus.ui.databinding.BindingWidgetFactory;
import org.eclipse.remus.ui.databinding.StyledTextBindingWidget;
import org.eclipse.remus.ui.editors.editpage.AbstractInformationFormPage;
import org.eclipse.remus.ui.jface.SubListContentProviderUpdater;
import org.eclipse.remus.ui.widgets.databinding.AdditionalBindingWidgetFactory;
import org.eclipse.remus.ui.widgets.databinding.CDateTimeBindingWidget;
import org.eclipse.remus.util.InformationUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.BaseSelectionListenerAction;
import org.eclipse.ui.forms.DetailsPart;
import org.eclipse.ui.forms.IDetailsPage;
import org.eclipse.ui.forms.IFormPart;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.MasterDetailsBlock;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;
import org.remus.infomngmnt.task.TaskActivator;
import org.remus.infomngmnt.task.messages.Messages;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class WorkLogPage extends AbstractInformationFormPage {

	private WorklogMasterPage masterDetail;

	/**
	 * 
	 */
	public WorkLogPage() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.ui.extension.AbstractInformationFormPage#renderPage
	 * (org.eclipse.ui.forms.IManagedForm)
	 */
	@Override
	protected void renderPage(IManagedForm managedForm) {
		this.masterDetail = new WorklogMasterPage();
		this.masterDetail.createContent(managedForm);
	}

	@Override
	public void bindValuesToUi() {
		super.bindValuesToUi();
		this.masterDetail.bindValuesToUi();

	}

	private class WorklogMasterPage extends MasterDetailsBlock {

		private IManagedForm managedForm;
		private TableViewer viewer;
		private SectionPart spart;

		@Override
		protected void createMasterPart(IManagedForm pManagedForm,
				Composite parent) {
			this.managedForm = pManagedForm;
			FormToolkit toolkit = this.managedForm.getToolkit();

			Section section = toolkit
					.createSection(parent, Section.DESCRIPTION);
			section.setText(Messages.WorkLogPage_WorklogHistory);
			section.setDescription(Messages.WorkLogPage_WorklogHistoryDetails);
			section.marginWidth = 10;
			section.marginHeight = 5;
			section.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
			toolkit.createCompositeSeparator(section);
			Composite client = toolkit.createComposite(section, SWT.WRAP);
			GridLayout layout = new GridLayout();
			layout.numColumns = 1;
			layout.marginWidth = 2;
			layout.marginHeight = 2;
			client.setLayout(layout);
			Table t = toolkit.createTable(client, SWT.MULTI);
			GridData gd = new GridData(GridData.FILL_BOTH);
			gd.heightHint = 20;
			gd.widthHint = 100;
			t.setLayoutData(gd);
			toolkit.paintBordersFor(client);

			section.setClient(client);
			this.spart = new SectionPart(section);

			this.managedForm.addPart(this.spart);
			this.viewer = new TableViewer(t);

			this.viewer
					.addSelectionChangedListener(new ISelectionChangedListener() {
						public void selectionChanged(
								final SelectionChangedEvent event) {
							WorklogMasterPage.this.managedForm
									.fireSelectionChanged(
											WorklogMasterPage.this.spart,
											event.getSelection());
						}
					});

		}

		public void bindValuesToUi() {
			ObservableListContentProvider ocp = new ObservableListContentProvider();

			new SubListContentProviderUpdater(ocp, this.viewer,
					TaskActivator.INFO_TYPE_ID,
					TaskActivator.NODE_NAME_WORKED_UNIT_DESCRIPTION,
					InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);

			IObservableList observeList = EMFObservables.observeList(
					InformationUtil.getChildByType(getModelObject(),
							TaskActivator.NODE_NAME_WORKED_UNITS),
					InfomngmntPackage.Literals.INFORMATION_UNIT__CHILD_VALUES);
			this.viewer.setContentProvider(ocp);
			this.viewer.setLabelProvider(new LabelProvider() {
				@Override
				public String getText(Object element) {
					InformationUnit childByType = InformationUtil
							.getChildByType(
									(InformationUnit) element,
									TaskActivator.NODE_NAME_WORKED_UNIT_DESCRIPTION);
					if (childByType.getStringValue() == null
							|| childByType.getStringValue().trim().length() == 0) {
						return Messages.WorkLogPage_NoDescription;
					}
					return childByType.getStringValue();
				}

				@Override
				public Image getImage(Object element) {
					return ResourceManager.getPluginImage(
							TaskActivator.getDefault(),
							"icons/iconexperience/history2.png"); //$NON-NLS-1$
				}
			});
			this.viewer.setSorter(new ViewerSorter() {
				@Override
				public int compare(Viewer viewer, Object e1, Object e2) {
					Date start1 = InformationUtil.getChildByType(
							(InformationUnit) e1,
							TaskActivator.NODE_NAME_WORKED_UNIT_STARTED)
							.getDateValue();
					Date start2 = InformationUtil.getChildByType(
							(InformationUnit) e2,
							TaskActivator.NODE_NAME_WORKED_UNIT_STARTED)
							.getDateValue();
					if (start1 == null && start2 == null) {
						return 0;
					}
					if (start1 == null) {
						return -1;
					}
					if (start2 == null) {
						return 1;
					}
					return start1.compareTo(start2);

				}
			});
			this.viewer.setInput(observeList);
		}

		@Override
		protected void createToolBarActions(IManagedForm managedForm) {
			Action addAction = new Action(Messages.WorkLogPage_Add) {
				@Override
				public void run() {
					InformationStructureEdit edit = InformationStructureEdit
							.newSession(TaskActivator.INFO_TYPE_ID);
					InformationUnit unit = edit.createSubType(
							TaskActivator.NODE_NAME_WORKED_UNIT, null);
					edit.addDynamicNode(getModelObject(), unit,
							getEditingDomain());
				}

				@Override
				public ImageDescriptor getImageDescriptor() {
					return PlatformUI.getWorkbench().getSharedImages()
							.getImageDescriptor(ISharedImages.IMG_OBJ_ADD);
				}
			};
			BaseSelectionListenerAction removeAction = new BaseSelectionListenerAction(
					Messages.WorkLogPage_Delete) {
				@Override
				protected boolean updateSelection(IStructuredSelection selection) {
					return !selection.isEmpty();
				}

				@Override
				public void run() {
					Command create = DeleteCommand.create(getEditingDomain(),
							getStructuredSelection().toList());
					getEditingDomain().getCommandStack().execute(create);
				}

				@Override
				public ImageDescriptor getImageDescriptor() {
					return PlatformUI.getWorkbench().getSharedImages()
							.getImageDescriptor(ISharedImages.IMG_ETOOL_DELETE);
				}

			};
			removeAction.setEnabled(!this.viewer.getSelection().isEmpty());
			this.viewer.addSelectionChangedListener(removeAction);
			this.spart.getManagedForm().getForm().getToolBarManager()
					.add(addAction);
			this.spart.getManagedForm().getForm().getToolBarManager()
					.add(removeAction);

		}

		@Override
		protected void registerPages(DetailsPart detailsPart) {
			detailsPart.registerPage(InformationUnitImpl.class,
					new WorkLogDetailsPage());
		}

	}

	private class WorkLogDetailsPage implements IDetailsPage {

		private IManagedForm form;
		private FormToolkit toolkit;
		private Section s1;
		private AnnotatingQuickFixTextBox descriptionText;
		private CDateTime startTime;
		private CDateTime endTime;
		private InformationUnit model;
		private StyledTextBindingWidget textBindingWidget;
		private CDateTimeBindingWidget startBinding;
		private CDateTimeBindingWidget endBinding;

		public void createContents(Composite parent) {
			GridLayout gridLayout = new GridLayout(1, false);
			gridLayout.marginWidth = 5;
			gridLayout.marginHeight = 5;
			gridLayout.verticalSpacing = 0;
			gridLayout.horizontalSpacing = 0;
			parent.setLayout(gridLayout);

			// layout.bottomMargin = 2;

			FormToolkit toolkit = this.form.getToolkit();
			this.s1 = toolkit.createSection(parent, Section.DESCRIPTION);
			this.s1.marginWidth = 10;
			this.s1.setText(Messages.WorkLogPage_WorklogDetails);
			this.s1.setDescription(Messages.WorkLogPage_SelectedUnitOfWork);
			GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
			gridData.widthHint = SWT.DEFAULT;
			gridData.heightHint = SWT.DEFAULT;
			this.s1.setLayoutData(gridData);

			toolkit.createCompositeSeparator(this.s1);
			Composite composite = toolkit.createComposite(this.s1);

			this.toolkit.paintBordersFor(composite);
			TableWrapLayout wrapLayout = new TableWrapLayout();
			wrapLayout.numColumns = 1;
			composite.setLayout(wrapLayout);
			this.s1.setClient(composite);

			toolkit.createLabel(composite, Messages.WorkLogPage_Description);
			this.descriptionText = new AnnotatingQuickFixTextBox(composite, "", //$NON-NLS-1$
					""); //$NON-NLS-1$
			TableWrapData data = new TableWrapData(TableWrapData.FILL_GRAB,
					TableWrapData.TOP);
			data.heightHint = 80;
			this.descriptionText.setLayoutData(data);

			toolkit.createLabel(composite, Messages.WorkLogPage_Start);
			this.startTime = new CDateTime(composite, CDT.BORDER
					| CDT.DROP_DOWN | CDT.TIME_MEDIUM | CDT.DATE_MEDIUM);

			this.startTime.setLayoutData(new TableWrapData(
					TableWrapData.FILL_GRAB, TableWrapData.TOP));
			toolkit.adapt(this.startTime, false, false);

			toolkit.createLabel(composite, Messages.WorkLogPage_End);
			this.endTime = new CDateTime(composite, CDT.BORDER | CDT.DROP_DOWN
					| CDT.TIME_MEDIUM | CDT.DATE_MEDIUM);
			this.endTime.setLayoutData(new TableWrapData(
					TableWrapData.FILL_GRAB, TableWrapData.TOP));
			toolkit.adapt(this.endTime, false, false);

		}

		public void commit(boolean onSave) {
			// do nothing

		}

		public void dispose() {
			disposeBinding();

		}

		public void initialize(IManagedForm form) {
			this.form = form;
			this.toolkit = form.getToolkit();

		}

		public boolean isDirty() {
			return false;
		}

		public boolean isStale() {
			return false;
		}

		public void refresh() {
			// TODO Auto-generated method stub

		}

		public void setFocus() {
			this.s1.setFocus();

		}

		public boolean setFormInput(Object input) {
			return false;
		}

		public void selectionChanged(IFormPart part, ISelection selection) {
			disposeBindings();

			this.model = (InformationUnit) ((IStructuredSelection) selection)
					.getFirstElement();
			this.textBindingWidget = BindingWidgetFactory.createStyledText(
					this.descriptionText.getFTextField(), WorkLogPage.this);
			this.textBindingWidget.bindModel(InformationUtil
					.getChildByType(this.model,
							TaskActivator.NODE_NAME_WORKED_UNIT_DESCRIPTION),
					InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);
			this.startBinding = AdditionalBindingWidgetFactory
					.createCDateTime(this.startTime, getDatabindingContext(),
							getEditingDomain());
			this.startBinding.bindModel(InformationUtil.getChildByType(
					this.model, TaskActivator.NODE_NAME_WORKED_UNIT_STARTED),
					InfomngmntPackage.Literals.INFORMATION_UNIT__DATE_VALUE);
			this.endBinding = AdditionalBindingWidgetFactory.createCDateTime(
					this.endTime, getDatabindingContext(), getEditingDomain());
			this.endBinding.bindModel(InformationUtil.getChildByType(
					this.model, TaskActivator.NODE_NAME_WORKED_UNIT_END),
					InfomngmntPackage.Literals.INFORMATION_UNIT__DATE_VALUE);

		}

		private void disposeBindings() {
			if (this.textBindingWidget != null) {
				this.textBindingWidget.getBinding().dispose();
				this.startBinding.getBinding().dispose();
				this.endBinding.getBinding().dispose();
			}
		}

	}

}
