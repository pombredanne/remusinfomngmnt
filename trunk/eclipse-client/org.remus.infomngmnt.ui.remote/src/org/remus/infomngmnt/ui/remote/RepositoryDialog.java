package org.remus.infomngmnt.ui.remote;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.IOpenListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.OpenEvent;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;

import org.remus.infomngmnt.common.ui.UIUtil;
import org.remus.infomngmnt.ui.UIPlugin;
import org.remus.infomngmnt.ui.remote.service.IRepositoryExtensionService;
import org.remus.infomngmnt.ui.remote.service.IRepositoryUI;

public class RepositoryDialog extends TitleAreaDialog {

	private Table table;
	protected IRepositoryUI selectedObject;

	/**
	 * Create the dialog
	 * 
	 * @param parentShell
	 */
	public RepositoryDialog(final Shell parentShell) {
		super(parentShell);
	}

	/**
	 * Create contents of the dialog
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(final Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);

		Composite composite = new Composite(area, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		composite.setLayout(new GridLayout());
		final TableViewer tableViewer = new TableViewer(composite, SWT.BORDER);
		this.table = tableViewer.getTable();
		GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
		this.table.setLayoutData(layoutData);

		setTitle("Repository connectors");
		setMessage("Choose a connector to connect with");

		tableViewer.setContentProvider(UIUtil.getArrayContentProviderInstance());
		tableViewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(final Object element) {
				org.remus.infomngmnt.core.remote.services.IRepositoryExtensionService service = RemoteUiActivator
						.getDefault()
						.getServiceTracker()
						.getService(
								org.remus.infomngmnt.core.remote.services.IRepositoryExtensionService.class);
				return service.getNameByRepositoryId(((IRepositoryUI) element).getRepositoryId());
			}

			@Override
			public Image getImage(final Object element) {
				return ((IRepositoryUI) element).getImage();

			}
		});
		tableViewer.setInput(UIPlugin.getDefault().getService(IRepositoryExtensionService.class)
				.getAllItems());
		tableViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(final SelectionChangedEvent event) {
				if (!event.getSelection().isEmpty()) {
					RepositoryDialog.this.selectedObject = (IRepositoryUI) ((IStructuredSelection) event
							.getSelection()).getFirstElement();

				}

			}
		});
		tableViewer.addOpenListener(new IOpenListener() {
			public void open(final OpenEvent event) {
				if (!event.getSelection().isEmpty()) {
					RepositoryDialog.this.selectedObject = (IRepositoryUI) ((IStructuredSelection) event
							.getSelection()).getFirstElement();
					okPressed();
				}

			}
		});
		return area;
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

	@Override
	protected void configureShell(final Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Connect to a repository");
	}

	public IRepositoryUI getSelectedObject() {
		return this.selectedObject;
	}

}
