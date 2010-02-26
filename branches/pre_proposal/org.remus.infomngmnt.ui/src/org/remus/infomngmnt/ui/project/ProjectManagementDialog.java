package org.remus.infomngmnt.ui.project;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.common.core.util.ModelUtil;
import org.remus.infomngmnt.common.ui.UIUtil;
import org.remus.infomngmnt.common.ui.image.ResourceManager;
import org.remus.infomngmnt.core.model.ApplicationModelPool;
import org.remus.infomngmnt.provider.InfomngmntEditPlugin;
import org.remus.infomngmnt.resources.util.ResourceUtil;
import org.remus.infomngmnt.ui.UIPlugin;
import org.remus.infomngmnt.ui.provider.NavigatorDecoratingLabelProvider;
import org.remus.infomngmnt.util.CategoryUtil;

public class ProjectManagementDialog extends TitleAreaDialog {

	private Table table;
	private TableViewer tableViewer;
	private Button exportButton;
	private Button importButton;
	private Button closeButton;
	private Button openButton;
	private Button deleteButton;
	private Button moveUpButton;
	private Button moveDownButton;

	/**
	 * Create the dialog
	 * 
	 * @param parentShell
	 */
	public ProjectManagementDialog(final Shell parentShell) {
		super(parentShell);
		setShellStyle(getShellStyle() | SWT.RESIZE);
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
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		container.setLayout(gridLayout);
		final GridData gd_container = new GridData(GridData.FILL_BOTH);
		gd_container.minimumWidth = 100;
		container.setLayoutData(gd_container);

		this.tableViewer = new TableViewer(container, SWT.BORDER | SWT.MULTI);
		this.table = this.tableViewer.getTable();
		this.table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 7));

		this.exportButton = new Button(container, SWT.NONE);
		final GridData gd_exportButton = new GridData(SWT.FILL, SWT.CENTER, false, false);
		gd_exportButton.widthHint = 100;
		this.exportButton.setLayoutData(gd_exportButton);
		this.exportButton.setText("Export");

		this.importButton = new Button(container, SWT.NONE);
		this.importButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		this.importButton.setText("Import");

		this.closeButton = new Button(container, SWT.NONE);
		this.closeButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		this.closeButton.setText("Close");
		this.closeButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				List list = ((IStructuredSelection) ProjectManagementDialog.this.tableViewer
						.getSelection()).toList();
				for (Object object : list) {
					Category itemByValue = (Category) ModelUtil.getItemByValue(ApplicationModelPool
							.getInstance().getModel().getRootCategories(),
							InfomngmntPackage.Literals.CATEGORY__LABEL, ((IProject) object)
									.getName());
					if (itemByValue != null) {
						InformationUnitListItem[] allInfoUnitItems = CategoryUtil
								.getAllInfoUnitItems(itemByValue);

					}
					try {
						ResourceUtil.closeProject((IProject) object);
					} catch (CoreException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					ProjectManagementDialog.this.tableViewer.refresh();
				}

			}
		});

		this.openButton = new Button(container, SWT.NONE);
		final GridData gd_openButton = new GridData(SWT.FILL, SWT.CENTER, false, false);
		this.openButton.setLayoutData(gd_openButton);
		this.openButton.setText("Open");
		this.openButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				final List list = ((IStructuredSelection) ProjectManagementDialog.this.tableViewer
						.getSelection()).toList();
				ProgressMonitorDialog progressMonitorDialog = new ProgressMonitorDialog(getShell());
				IRunnableWithProgress progress = new IRunnableWithProgress() {

					public void run(final IProgressMonitor monitor)
							throws InvocationTargetException, InterruptedException {
						for (Object object : list) {
							try {
								((IProject) object).open(monitor);
								((IProject) object).build(IncrementalProjectBuilder.FULL_BUILD,
										monitor);

							} catch (CoreException e) {
								throw new InvocationTargetException(e);
							}
						}

					}
				};
				try {
					progressMonitorDialog.run(true, false, progress);
					ProjectManagementDialog.this.tableViewer.refresh();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

		this.deleteButton = new Button(container, SWT.NONE);
		final GridData gd_deleteButton = new GridData(SWT.FILL, SWT.TOP, false, false);
		this.deleteButton.setLayoutData(gd_deleteButton);
		this.deleteButton.setText("Delete");
		this.deleteButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				if (MessageDialog
						.openConfirm(
								getShell(),
								"Deletion of projects",
								"The entire content of the selected project(s) will be deletetd. This action cannot be undone. Continue?")) {
					final List list = ((IStructuredSelection) ProjectManagementDialog.this.tableViewer
							.getSelection()).toList();

					ProgressMonitorDialog pmd = new ProgressMonitorDialog(getShell());
					IRunnableWithProgress progress = new IRunnableWithProgress() {

						public void run(final IProgressMonitor monitor)
								throws InvocationTargetException, InterruptedException {
							for (Object object : list) {
								try {
									ResourceUtil.deleteProject((IProject) object, monitor);
								} catch (CoreException e) {
									throw new InvocationTargetException(e);
								}
							}

						}

					};
					try {
						pmd.run(true, false, progress);
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					ProjectManagementDialog.this.tableViewer.refresh();
				}

			}
		});
		setTitle("Project-Management");
		setMessage("Administer your Information-Projects");
		//
		this.tableViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(final SelectionChangedEvent event) {
				handleSelection((IStructuredSelection) event.getSelection());
			}
		});

		this.moveUpButton = new Button(container, SWT.NONE);
		final GridData gd_moveUpButton = new GridData(SWT.FILL, SWT.CENTER, false, false);
		this.moveUpButton.setLayoutData(gd_moveUpButton);
		this.moveUpButton.setText("Move Up");
		this.moveUpButton.addListener(SWT.Selection, new Listener() {

			public void handleEvent(final Event event) {
				Category c1 = (Category) ModelUtil
						.getItemByValue(
								ApplicationModelPool.getInstance().getModel().getRootCategories(),
								InfomngmntPackage.Literals.CATEGORY__LABEL,
								((IProject) ((IStructuredSelection) ProjectManagementDialog.this.tableViewer
										.getSelection()).getFirstElement()).getName());
				int oldIndex = ApplicationModelPool.getInstance().getModel().getRootCategories()
						.indexOf(c1);
				ApplicationModelPool.getInstance().getModel().getRootCategories().move(
						oldIndex - 1, oldIndex);
				ProjectManagementDialog.this.tableViewer.refresh();
			}

		});

		this.moveDownButton = new Button(container, SWT.NONE);
		final GridData gd_moveDownButton = new GridData(SWT.FILL, SWT.TOP, false, false);
		this.moveDownButton.setLayoutData(gd_moveDownButton);
		this.moveDownButton.setText("Move Down");
		this.moveDownButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				Category c1 = (Category) ModelUtil
						.getItemByValue(
								ApplicationModelPool.getInstance().getModel().getRootCategories(),
								InfomngmntPackage.Literals.CATEGORY__LABEL,
								((IProject) ((IStructuredSelection) ProjectManagementDialog.this.tableViewer
										.getSelection()).getFirstElement()).getName());
				int oldIndex = ApplicationModelPool.getInstance().getModel().getRootCategories()
						.indexOf(c1);
				ApplicationModelPool.getInstance().getModel().getRootCategories().move(
						oldIndex + 1, oldIndex);
				ProjectManagementDialog.this.tableViewer.refresh();
			}

		});
		this.tableViewer.setSelection(StructuredSelection.EMPTY);
		bindValuesToUI();
		return area;
	}

	protected void handleSelection(final IStructuredSelection selection) {
		if (selection.isEmpty()) {
			this.exportButton.setEnabled(false);
			this.closeButton.setEnabled(false);
			this.openButton.setEnabled(false);
			this.deleteButton.setEnabled(false);
			this.moveUpButton.setEnabled(false);
			this.moveDownButton.setEnabled(false);
		} else {
			this.exportButton.setEnabled(checkExportButton(selection));
			this.closeButton.setEnabled(checkCloseButton(selection));
			this.openButton.setEnabled(checkOpenButton(selection));
			this.deleteButton.setEnabled(checkDeleteButton(selection));
			this.moveUpButton.setEnabled(checkMoveUp(selection));
			this.moveDownButton.setEnabled(checkMoveDown(selection));
		}

	}

	private boolean checkMoveUp(final IStructuredSelection selection) {
		int index = ApplicationModelPool.getInstance().getModel().getRootCategories().indexOf(
				ModelUtil.getItemByValue(ApplicationModelPool.getInstance().getModel()
						.getRootCategories(), InfomngmntPackage.Literals.CATEGORY__LABEL,
						((IProject) selection.getFirstElement()).getName()));
		return index != 0 && selection.size() == 1
				&& ((IProject) selection.getFirstElement()).isOpen();
	}

	private boolean checkMoveDown(final IStructuredSelection selection) {
		int index = ApplicationModelPool.getInstance().getModel().getRootCategories().indexOf(
				ModelUtil.getItemByValue(ApplicationModelPool.getInstance().getModel()
						.getRootCategories(), InfomngmntPackage.Literals.CATEGORY__LABEL,
						((IProject) selection.getFirstElement()).getName()));
		return (index + 1) < ApplicationModelPool.getInstance().getModel().getRootCategories()
				.size()
				&& selection.size() == 1 && ((IProject) selection.getFirstElement()).isOpen();
	}

	private boolean checkDeleteButton(final IStructuredSelection selection) {
		List list = selection.toList();
		for (Object object : list) {
			if (!((IProject) object).isOpen()) {
				return false;
			}
		}
		return true;
	}

	private boolean checkOpenButton(final IStructuredSelection selection) {
		List list = selection.toList();
		for (Object object : list) {
			if (((IProject) object).isOpen()
					|| ((IProject) object).getLocationURI().getScheme().startsWith("encrypted")) {
				return false;
			}
		}
		return true;
	}

	private boolean checkCloseButton(final IStructuredSelection selection) {
		List list = selection.toList();
		for (Object object : list) {
			if (!((IProject) object).isOpen()) {
				return false;
			}
		}
		return true;
	}

	private boolean checkExportButton(final IStructuredSelection selection) {
		if (selection.size() == 1 && ((IProject) selection.getFirstElement()).isOpen()) {
			return true;
		}
		return false;
	}

	@Override
	public boolean close() {
		UIUtil.saveDialogSettings(UIPlugin.getDefault(), this);
		return super.close();
	}

	private void bindValuesToUI() {
		this.tableViewer.setContentProvider(UIUtil.getArrayContentProviderInstance());
		this.tableViewer.setInput(ResourceUtil.getRelevantAndClosedProjects());
		this.tableViewer.setLabelProvider(new NavigatorDecoratingLabelProvider(new LabelProvider() {
			@Override
			public String getText(final Object element) {
				return ((IProject) element).getName();
			}

			@Override
			public Image getImage(final Object element) {
				if (((IProject) element).isOpen()) {
					return ResourceManager.getPluginImage(InfomngmntEditPlugin.getPlugin(),
							"icons/iconexperience/folder_green.png");
				} else {
					return ResourceManager.getPluginImage(UIPlugin.getDefault(),
							"icons/iconexperience/16/folder_closed.png");
				}
			}
		}));
		this.tableViewer.setComparator(new ViewerComparator() {
			@Override
			public int compare(final Viewer viewer, final Object e1, final Object e2) {
				IProject p1 = (IProject) e1;
				IProject p2 = (IProject) e2;
				if (p1.isOpen() && p2.isOpen()) {
					Category c1 = (Category) ModelUtil.getItemByValue(ApplicationModelPool
							.getInstance().getModel().getRootCategories(),
							InfomngmntPackage.Literals.CATEGORY__LABEL, p1.getName());
					Category c2 = (Category) ModelUtil.getItemByValue(ApplicationModelPool
							.getInstance().getModel().getRootCategories(),
							InfomngmntPackage.Literals.CATEGORY__LABEL, p2.getName());

					int i1 = ApplicationModelPool.getInstance().getModel().getRootCategories()
							.indexOf(c1);
					int i2 = ApplicationModelPool.getInstance().getModel().getRootCategories()
							.indexOf(c2);
					return Integer.valueOf(i1).compareTo(Integer.valueOf(i2));

				} else if (p1.isOpen() && !p2.isOpen()) {
					return -1;
				} else if (!p1.isOpen() && p2.isOpen()) {
					return 1;
				} else if (!p1.isOpen() && !p2.isOpen()) {
					return p1.getName().compareTo(p2.getName());
				}
				return super.compare(viewer, e1, e2);
			}
		});

	}

	/**
	 * Create contents of the button bar
	 * 
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(final Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.CLOSE_LABEL, true);
	}

	/**
	 * Return the initial size of the dialog
	 */
	@Override
	protected Point getInitialSize() {
		return UIUtil
				.getDialogSettingsInitialSize(UIPlugin.getDefault(), this, new Point(500, 375));
	}

	@Override
	protected Point getInitialLocation(final Point initialSize) {
		return UIUtil.getDialogSettingsInitialLocation(UIPlugin.getDefault(), this, super
				.getInitialLocation(initialSize));
	}

	@Override
	protected void configureShell(final Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Project-Management");
	}

}
