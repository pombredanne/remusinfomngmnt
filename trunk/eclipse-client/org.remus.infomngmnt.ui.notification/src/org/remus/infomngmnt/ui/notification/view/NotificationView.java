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

package org.remus.infomngmnt.ui.notification.view;

import java.text.DateFormat;

import org.apache.commons.lang.StringEscapeUtils;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.databinding.EMFDataBindingContext;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.emf.edit.ui.action.CommandActionHandler;
import org.eclipse.emf.edit.ui.action.DeleteAction;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.UnwrappingSelectionProvider;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.forms.DetailsPart;
import org.eclipse.ui.forms.IDetailsPage;
import org.eclipse.ui.forms.IFormPart;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.ManagedForm;
import org.eclipse.ui.forms.MasterDetailsBlock;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapLayout;
import org.eclipse.ui.part.IPage;
import org.eclipse.ui.views.properties.IPropertySheetPage;

import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.Notification;
import org.remus.infomngmnt.NotificationCollection;
import org.remus.infomngmnt.common.ui.image.CommonImageRegistry;
import org.remus.infomngmnt.common.ui.view.AbstractScrolledTitledView;
import org.remus.infomngmnt.core.edit.DisposableEditingDomain;
import org.remus.infomngmnt.core.services.IApplicationModel;
import org.remus.infomngmnt.core.services.IEditingHandler;
import org.remus.infomngmnt.core.services.INotificationManagerManager;
import org.remus.infomngmnt.ui.databinding.BindingWidgetFactory;
import org.remus.infomngmnt.ui.databinding.Date2StringConverter;
import org.remus.infomngmnt.ui.editors.EditorUtil;
import org.remus.infomngmnt.ui.notification.NotificationActivator;
import org.remus.infomngmnt.ui.notification.provider.NotificationLabelProvider;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class NotificationView extends AbstractScrolledTitledView implements IEditingDomainProvider,
		ISelectionProvider {

	public static final String VIEW_ID = "org.remus.infomngmnt.ui.notification.view.NotificationView"; //$NON-NLS-1$
	private NotificationContextMenu actionBar;
	private DisposableEditingDomain domain;
	private NotificationMasterBlock masterDetail;
	private MenuManager contextMenu;

	private final Adapter changeAdapter = new AdapterImpl() {
		@Override
		public void notifyChanged(final org.eclipse.emf.common.notify.Notification msg) {
			getSite().getShell().getDisplay().asyncExec(new Runnable() {

				public void run() {
					if (!NotificationView.this.masterDetail.viewer.getControl().isDisposed())
						NotificationView.this.masterDetail.viewer.refresh();
				}
			});
		};
	};
	private final INotificationManagerManager notificationService;
	private final IEditingHandler editService;
	private final IApplicationModel appService;

	public NotificationView() {
		this.notificationService = NotificationActivator.getDefault().getServiceTracker()
				.getService(INotificationManagerManager.class);
		this.editService = NotificationActivator.getDefault().getServiceTracker().getService(
				IEditingHandler.class);
		this.appService = NotificationActivator.getDefault().getServiceTracker().getService(
				IApplicationModel.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.remus.infomngmnt.common.ui.view.AbstractScrolledTitledView#
	 * createViewContents(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createViewContents(final Composite parent) {
		final ManagedForm managedForm = new ManagedForm(parent);

		this.masterDetail = new NotificationMasterBlock();
		this.masterDetail.createContent(managedForm);
		this.masterDetail.vertical();
		Action haction = new Action("hor", IAction.AS_RADIO_BUTTON) {
			@Override
			public void run() {
				NotificationView.this.masterDetail.horizontal();

			}
		};

		haction.setChecked(true);
		haction.setToolTipText("Horizontal orientation");
		haction.setImageDescriptor(CommonImageRegistry.getInstance().getDescriptor(
				CommonImageRegistry.SECTION_HORIZONTAL));
		Action vaction = new Action("ver", IAction.AS_RADIO_BUTTON) {
			@Override
			public void run() {
				NotificationView.this.masterDetail.vertical();
			}
		};
		vaction.setChecked(false);
		vaction.setToolTipText("Vertical orientation");
		vaction.setImageDescriptor(CommonImageRegistry.getInstance().getDescriptor(
				CommonImageRegistry.SECTION_VERTICAL));
		getToolbarManager().add(haction);
		getToolbarManager().add(vaction);
		getToolbarManager().update(true);
		hookActionBars();
		hookContextMenu();

	}

	private void hookActionBars() {
		this.actionBar = new NotificationContextMenu();
		this.actionBar.init(getViewSite().getActionBars(), this);
		this.actionBar.setActiveDomain(this);
	}

	/**
	 * @param viewer2
	 */
	private void hookContextMenu() {
		this.contextMenu = new MenuManager("#PopUpMenu");
		this.contextMenu.setRemoveAllWhenShown(true);
		this.contextMenu.addMenuListener(this.actionBar);
		final Menu menu = this.contextMenu.createContextMenu(this.masterDetail.viewer.getControl());
		this.masterDetail.viewer.getControl().setMenu(menu);
		getViewSite().registerContextMenu(VIEW_ID, this.contextMenu,
				new UnwrappingSelectionProvider(this));

	}

	@Override
	public void dispose() {
		NotificationCollection allNotifications = this.notificationService.getAllNotifications();
		allNotifications.eAdapters().remove(this.changeAdapter);

		NotificationActivator.getDefault().getServiceTracker().ungetService(
				this.notificationService);
		NotificationActivator.getDefault().getServiceTracker().ungetService(this.editService);
		super.dispose();
	}

	private class NotificationMasterBlock extends MasterDetailsBlock {

		TableViewer viewer;
		private IManagedForm managedForm;

		@Override
		protected void createMasterPart(final IManagedForm pManagedForm, final Composite parent) {
			this.managedForm = pManagedForm;
			FormToolkit toolkit = this.managedForm.getToolkit();

			Section section = toolkit.createSection(parent, Section.DESCRIPTION);
			section.setText("Notifications");
			section.setDescription("This list contains all received notifications.");
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
			final SectionPart spart = new SectionPart(section);

			this.managedForm.addPart(spart);
			this.viewer = new TableViewer(t);

			this.viewer.setContentProvider(new AdapterFactoryContentProvider(NotificationActivator
					.getDefault().getServiceTracker().getService(IEditingHandler.class)
					.getAdapterFactory()));

			this.viewer.setLabelProvider(new NotificationLabelProvider(this.viewer));
			NotificationCollection allNotifications = NotificationActivator.getDefault()
					.getServiceTracker().getService(INotificationManagerManager.class)
					.getAllNotifications();
			allNotifications.eAdapters().add(NotificationView.this.changeAdapter);
			this.viewer.setInput(allNotifications);
			this.viewer.addSelectionChangedListener(new ISelectionChangedListener() {
				public void selectionChanged(final SelectionChangedEvent event) {
					NotificationMasterBlock.this.managedForm.fireSelectionChanged(spart, event
							.getSelection());
				}
			});

		}

		public void horizontal() {
			this.sashForm.setOrientation(SWT.HORIZONTAL);
			this.managedForm.getForm().reflow(true);
		}

		public void vertical() {
			this.sashForm.setOrientation(SWT.VERTICAL);
			this.managedForm.getForm().reflow(true);
		}

		@Override
		protected void registerPages(final DetailsPart pDetailsPart) {
			pDetailsPart.registerPage(org.remus.infomngmnt.impl.NotificationImpl.class,
					new NotificationDetailsPage());
		}

		@Override
		protected void createToolBarActions(final IManagedForm managedForm) {
			// TODO Auto-generated method stub

		}
	}

	private class NotificationDetailsPage implements IDetailsPage {

		private IManagedForm form;
		private FormToolkit toolkit;
		private EMFDataBindingContext ctx;
		private Notification model;
		private Section s1;
		private Label dateLabel;
		private Label titleLabel;
		private Label detailLabel;
		private FormText affectedIULabel;
		private Label severityLabel;
		private Label prioLabel;
		private DisposableEditingDomain domain;
		private long lastSelection;

		public void createContents(final Composite parent) {
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
			this.s1.setText("Notification Details");
			this.s1.setDescription("Details of the selected notification.");
			GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
			gridData.widthHint = SWT.DEFAULT;
			gridData.heightHint = SWT.DEFAULT;
			this.s1.setLayoutData(gridData);

			toolkit.createCompositeSeparator(this.s1);
			Composite composite = toolkit.createComposite(this.s1);

			this.toolkit.paintBordersFor(composite);
			TableWrapLayout wrapLayout = new TableWrapLayout();
			wrapLayout.numColumns = 2;
			composite.setLayout(wrapLayout);
			this.s1.setClient(composite);

			FormText labelDate = this.toolkit.createFormText(composite, false);
			labelDate.setText("<form><p><b>Received:</b></p></form>", true, false);
			this.dateLabel = this.toolkit.createLabel(composite, "", SWT.WRAP);

			FormText labelSearchString = this.toolkit.createFormText(composite, false);
			labelSearchString.setText("<form><p><b>Title:</b></p></form>", true, false);
			this.titleLabel = this.toolkit.createLabel(composite, "", SWT.WRAP);

			FormText labelStartDate = this.toolkit.createFormText(composite, false);
			labelStartDate.setText("<form><p><b>Details:</b></p></form>", true, false);
			this.detailLabel = this.toolkit.createLabel(composite, "", SWT.WRAP);

			FormText labelStartEnd = this.toolkit.createFormText(composite, false);
			labelStartEnd.setText("<form><p><b>Information Unit:</b></p></form>", true, false);
			this.affectedIULabel = this.toolkit.createFormText(composite, false);
			this.affectedIULabel.addHyperlinkListener(new HyperlinkAdapter() {
				@Override
				public void linkActivated(final HyperlinkEvent e) {
					Object href = e.getHref();

					EditorUtil.openInfoUnit(href.toString());
				}
			});

			FormText labelSeverity = this.toolkit.createFormText(composite, false);
			labelSeverity.setText("<form><p><b>Severity:</b></p></form>", true, false);
			this.severityLabel = this.toolkit.createLabel(composite, "", SWT.WRAP);

			FormText labelPrio = this.toolkit.createFormText(composite, false);
			labelPrio.setText("<form><p><b>Priority:</b></p></form>", true, false);
			this.prioLabel = this.toolkit.createLabel(composite, "", SWT.WRAP);

		}

		public void commit(final boolean onSave) {
			// TODO Auto-generated method stub

		}

		public void dispose() {
			this.ctx.dispose();
			this.domain.dispose();
		}

		public void initialize(final IManagedForm form) {
			this.form = form;
			this.toolkit = form.getToolkit();
			this.ctx = new EMFDataBindingContext();
			this.domain = NotificationView.this.editService.createNewEditingDomain();

		}

		public boolean isDirty() {
			// TODO Auto-generated method stub
			return false;
		}

		public boolean isStale() {
			// TODO Auto-generated method stub
			return false;
		}

		public void refresh() {
			// TODO Auto-generated method stub

		}

		public void setFocus() {
			// TODO Auto-generated method stub

		}

		public boolean setFormInput(final Object input) {
			// TODO Auto-generated method stub
			return false;
		}

		public void selectionChanged(final IFormPart part, final ISelection selection) {
			this.ctx.dispose();
			this.lastSelection = System.currentTimeMillis();
			this.model = (Notification) ((IStructuredSelection) selection).getFirstElement();
			getSite().getShell().getDisplay().timerExec(2000, new Runnable() {
				public void run() {
					if (System.currentTimeMillis() - NotificationDetailsPage.this.lastSelection >= 2000) {
						Command command = SetCommand.create(NotificationDetailsPage.this.domain,
								NotificationDetailsPage.this.model,
								InfomngmntPackage.Literals.NOTIFICATION__NOTICED, Boolean.TRUE);
						NotificationDetailsPage.this.domain.getCommandStack().execute(command);

					}

				}
			});

			BindingWidgetFactory.createLabelBinding(this.dateLabel, this.ctx, this.domain)
					.bindModel(
							this.model,
							InfomngmntPackage.Literals.NOTIFICATION__TIME_STAMP,
							null,
							new UpdateValueStrategy().setConverter(new Date2StringConverter(
									DateFormat.SHORT, DateFormat.SHORT)));
			BindingWidgetFactory.createLabelBinding(this.titleLabel, this.ctx, this.domain)
					.bindModel(this.model, InfomngmntPackage.Literals.NOTIFICATION__MESSAGE);
			BindingWidgetFactory.createLabelBinding(this.detailLabel, this.ctx, this.domain)
					.bindModel(this.model, InfomngmntPackage.Literals.NOTIFICATION__DETAILS);
			BindingWidgetFactory.createLabelBinding(this.severityLabel, this.ctx, this.domain)
					.bindModel(this.model, InfomngmntPackage.Literals.NOTIFICATION__SEVERITY);
			BindingWidgetFactory.createLabelBinding(this.prioLabel, this.ctx, this.domain)
					.bindModel(this.model, InfomngmntPackage.Literals.NOTIFICATION__IMPORTANCE);

			EList<String> affectedInfoUnitIds = NotificationDetailsPage.this.model
					.getAffectedInfoUnitIds();
			if (affectedInfoUnitIds.size() > 0) {
				InformationUnitListItem itemById = NotificationView.this.appService.getItemById(
						affectedInfoUnitIds.get(0), null);
				if (itemById != null) {
					NotificationDetailsPage.this.affectedIULabel.setText(
							"<form><p vspace=\"false\"><a href=\"" + itemById.getId() + "\">"
									+ StringEscapeUtils.escapeXml(itemById.getLabel())
									+ "</a></p></form>", true, false);
				}
			} else {
				NotificationDetailsPage.this.affectedIULabel.setText("None", false, false);
			}

			this.form.reflow(true);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
	 */
	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

	@Override
	protected Layout createClientLayout() {
		return new FillLayout();
	}

	private static class NotificationContextMenu implements IMenuListener, IPropertyListener,
			ISelectionChangedListener {
		/**
		 * This keeps track of the current editor part.
		 */
		protected IEditingDomainProvider activeEditor;

		/**
		 * This is the action used to implement delete.
		 */
		protected DeleteAction deleteAction;

		protected CommandActionHandler setReadAction;

		/**
		 * This style bit indicates that the "additions" separator should come
		 * after the "edit" separator.
		 */
		public static final int ADDITIONS_LAST_STYLE = 0x1;

		/**
		 * This is used to encode the style bits.
		 */
		protected int style;

		private IActionBars actionBars;

		/**
		 * This creates an instance of the contributor.
		 */
		public NotificationContextMenu() {
			super();
		}

		public void init(final IActionBars actionBars, final IEditingDomainProvider activeEditor) {
			this.actionBars = actionBars;
			final ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();

			this.deleteAction = new DeleteAction(activeEditor.getEditingDomain(),
					removeAllReferencesOnDelete()) {
				@Override
				public void run() {
					new Job("Delete Notifications") {

						@Override
						protected IStatus run(final IProgressMonitor monitor) {
							runSuper();
							return Status.OK_STATUS;
						}
					}.schedule();

				}

				private void runSuper() {
					super.run();

				}
			};
			this.deleteAction.setImageDescriptor(sharedImages
					.getImageDescriptor(ISharedImages.IMG_TOOL_DELETE));
			this.deleteAction.setActionDefinitionId("org.eclipse.ui.edit.delete");
			this.setReadAction = new CommandActionHandler(activeEditor.getEditingDomain()) {
				@Override
				public Command createCommand(final java.util.Collection<?> selection) {
					CompoundCommand cc = new CompoundCommand();
					for (Object object : selection) {
						if (((EObject) object)
								.eGet(InfomngmntPackage.Literals.NOTIFICATION__NOTICED) != Boolean.TRUE) {
							cc
									.append(SetCommand.create(this.domain, object,
											InfomngmntPackage.Literals.NOTIFICATION__NOTICED,
											Boolean.TRUE));
						}
					}
					return cc;
				};

				@Override
				public String getText() {
					return "Mark as read";
				}
			};
			setGlobalActionHandler();

		}

		public void setGlobalActionHandler() {
			this.actionBars.setGlobalActionHandler(ActionFactory.DELETE.getId(), this.deleteAction);
			this.actionBars.updateActionBars();
		}

		/**
		 * This determines whether or not the delete action should clean up all
		 * references to the deleted objects. It is false by default, to provide
		 * the same beahviour, by default, as in EMF 2.1 and before. You should
		 * probably override this method to return true, in order to get the
		 * new, more useful beahviour.
		 * 
		 * @since 2.2
		 */
		protected boolean removeAllReferencesOnDelete() {
			return false;
		}

		public void shareGlobalActions(final IPage page, final IActionBars actionBars) {
			if (!(page instanceof IPropertySheetPage)) {
				actionBars.setGlobalActionHandler(ActionFactory.DELETE.getId(), this.deleteAction);

			}

		}

		public void setActiveDomain(final IEditingDomainProvider domain) {
			if (domain != this.activeEditor) {
				if (this.activeEditor != null) {
					deactivate();
				}

				if (domain instanceof IEditingDomainProvider) {
					this.activeEditor = domain;
					activate();

				}
			}
		}

		public void deactivate() {
			this.deleteAction.setEditingDomain(null);

			final ISelectionProvider selectionProvider = this.activeEditor instanceof ISelectionProvider ? (ISelectionProvider) this.activeEditor
					: null;

			if (selectionProvider != null) {
				selectionProvider.removeSelectionChangedListener(this.deleteAction);
				selectionProvider.removeSelectionChangedListener(this.setReadAction);

			}
		}

		public void activate() {
			this.deleteAction.setEditingDomain(this.activeEditor.getEditingDomain());
			this.setReadAction.setEditingDomain(this.activeEditor.getEditingDomain());

			final ISelectionProvider selectionProvider = this.activeEditor instanceof ISelectionProvider ? (ISelectionProvider) this.activeEditor
					: null;

			if (selectionProvider != null) {
				selectionProvider.addSelectionChangedListener(this.deleteAction);
				selectionProvider.addSelectionChangedListener(this.setReadAction);
			}

			update();
		}

		public void update() {
			final ISelectionProvider selectionProvider = this.activeEditor instanceof ISelectionProvider ? (ISelectionProvider) this.activeEditor
					: null;

			if (selectionProvider != null) {
				final ISelection selection = selectionProvider.getSelection();
				final IStructuredSelection structuredSelection = selection instanceof IStructuredSelection ? (IStructuredSelection) selection
						: StructuredSelection.EMPTY;

				this.deleteAction.updateSelection(structuredSelection);
				this.setReadAction.updateSelection(structuredSelection);
			}

		}

		/**
		 * This implements {@link org.eclipse.jface.action.IMenuListener} to
		 * help fill the context menus with contributions from the Edit menu.
		 */
		public void menuAboutToShow(final IMenuManager menuManager) {

			// Add our standard marker.
			//
			if ((this.style & ADDITIONS_LAST_STYLE) == 0) {
				menuManager.add(new Separator("additions"));
			}

			menuManager.add(new Separator("edit"));

			// Add the edit menu actions.
			//

			menuManager.add(this.setReadAction);
			menuManager.add(this.deleteAction);

			if ((this.style & ADDITIONS_LAST_STYLE) != 0) {
				menuManager.add(new Separator("additions"));
				menuManager.add(new Separator());
			}
			// Add our other standard marker.
			//
			menuManager.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));

			addGlobalActions(menuManager);
		}

		/**
		 * This inserts global actions before the "additions-end" separator.
		 */
		protected void addGlobalActions(final IMenuManager menuManager) {
			final String key = (this.style & ADDITIONS_LAST_STYLE) == 0 ? "additions-end"
					: "additions";
		}

		public void propertyChanged(final Object source, final int id) {
			update();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(
		 * org.eclipse.jface.viewers.SelectionChangedEvent)
		 */
		public void selectionChanged(final SelectionChangedEvent event) {
			update();

		}
	}

	public EditingDomain getEditingDomain() {
		if (this.domain == null) {
			this.domain = this.editService.createNewEditingDomain();
		}
		return this.domain;
	}

	public void addSelectionChangedListener(final ISelectionChangedListener listener) {
		this.masterDetail.viewer.addSelectionChangedListener(listener);

	}

	public ISelection getSelection() {
		return this.masterDetail.viewer.getSelection();
	}

	public void removeSelectionChangedListener(final ISelectionChangedListener listener) {
		this.masterDetail.viewer.removeSelectionChangedListener(listener);

	}

	public void setSelection(final ISelection selection) {
		this.masterDetail.viewer.setSelection(selection);

	}

}
