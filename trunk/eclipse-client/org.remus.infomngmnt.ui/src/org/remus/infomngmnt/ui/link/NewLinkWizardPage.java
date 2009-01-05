package org.remus.infomngmnt.ui.link;


import java.util.ArrayList;

import org.eclipse.emf.common.ui.celleditor.ExtendedComboBoxCellEditor;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

import org.remus.infomngmnt.AbstractInformationUnit;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.Link;
import org.remus.infomngmnt.core.model.EditingUtil;
import org.remus.infomngmnt.core.model.LinkUtil;
import org.remus.infomngmnt.ui.dialogs.InfoUnitSelectionDialog;
import org.remus.infomngmnt.ui.provider.NavigationCellLabelProvider;

public class NewLinkWizardPage extends TitleAreaDialog {

	private Text text;
	private Table table;
	private InformationUnit infoUnit;
	private Label imageLabel;
	private TableViewer tableViewer;
	/**
	 * Create the wizard
	 */
	public NewLinkWizardPage(final Shell parentShell, final InformationUnit infoUnit) {
		super(parentShell);
		this.infoUnit = infoUnit;
		setTitle("Wizard Page title");
	}


	@Override
	protected Control createDialogArea(final Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 4;
		container.setLayout(gridLayout);


		final Label sourceLabel = new Label(container, SWT.NONE);
		sourceLabel.setText("Source");

		this.imageLabel = new Label(container, SWT.NONE);
		this.imageLabel.setLayoutData(new GridData(16, 16));

		this.text = new Text(container, SWT.BORDER);
		this.text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		this.text.setEditable(false);

		final Button browseButton = new Button(container, SWT.NONE);
		browseButton.setText("Browse...");
		browseButton.addListener(SWT.Selection, new Listener() {

			public void handleEvent(final Event event) {
				InfoUnitSelectionDialog diag = new InfoUnitSelectionDialog(getShell(),false);
				diag.setListLabelProvider(new NavigationCellLabelProvider());
				diag.open();
				InformationUnitListItem result = (InformationUnitListItem) diag.getFirstResult();
				setResult(result, true);
			}
		});

		final Group targetsGroup = new Group(container, SWT.NONE);
		targetsGroup.setText("Targets");
		final GridData gd_targetsGroup = new GridData(SWT.FILL, SWT.FILL, false, true, 4, 1);
		targetsGroup.setLayoutData(gd_targetsGroup);
		final GridLayout gridLayout_1 = new GridLayout();
		gridLayout_1.numColumns = 2;
		targetsGroup.setLayout(gridLayout_1);

		this.tableViewer = new TableViewer(targetsGroup, SWT.FULL_SELECTION);
		this.tableViewer.setContentProvider(ArrayContentProvider.getInstance());
		this.tableViewer.setLabelProvider(new ITableLabelProvider() {

			public Image getColumnImage(final Object element, final int columnIndex) {
				// TODO Auto-generated method stub
				return null;
			}

			public String getColumnText(final Object element, final int columnIndex) {
				switch (columnIndex) {
				case 0:
					return ((Link) element).getTarget().getLabel();
				case 1:
					return ((Link) element).getLinktype().getDescription();
				default:
					break;
				}
				return null;
			}

			public void addListener(final ILabelProviderListener listener) {
				// TODO Auto-generated method stub

			}

			public void dispose() {
				// TODO Auto-generated method stub

			}

			public boolean isLabelProperty(final Object element, final String property) {
				// TODO Auto-generated method stub
				return false;
			}

			public void removeListener(final ILabelProviderListener listener) {
				// TODO Auto-generated method stub

			}

		});

		this.table = this.tableViewer.getTable();
		this.table.setLinesVisible(true);
		this.table.setHeaderVisible(true);
		this.table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));

		TableColumn tc0 = new TableColumn(this.table, SWT.LEAD);
		tc0.setText("Name of the target");
		tc0.setWidth(300);

		TableColumn tc1 = new TableColumn(this.table, SWT.LEAD);
		tc1.setText("Link-Type");
		tc1.setWidth(100);
		final Button newButton = new Button(targetsGroup, SWT.NONE);
		final GridData gd_newButton = new GridData(SWT.RIGHT, SWT.CENTER, false, false);
		newButton.setLayoutData(gd_newButton);
		newButton.setText("New");

		final Button deleteButton = new Button(targetsGroup, SWT.NONE);
		final GridData gd_deleteButton = new GridData();
		deleteButton.setLayoutData(gd_deleteButton);
		deleteButton.setText("Delete");
		setResult(this.infoUnit, false);
		return area;

	}
	@Override
	protected void createButtonsForButtonBar(final Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.CLOSE_LABEL,
				true);
	}


	protected void setResult(final AbstractInformationUnit result, final boolean notify) {
		this.text.setText(result.getLabel());
		Object image2 = ((IItemLabelProvider) EditingUtil.getInstance().getAdapterFactory().adapt(result,IItemLabelProvider.class)).getImage(result);
		if (image2 instanceof Image) {
			this.imageLabel.setImage((Image) image2);
		}
		if (result instanceof InformationUnit) {
			this.infoUnit = (InformationUnit) result;
		} else {
			this.infoUnit = (InformationUnit) result.getAdapter(InformationUnit.class);
		}
		populateTreeContent();

	}

	private void populateTreeContent() {
		this.tableViewer.setInput(this.infoUnit.getLinks());
		ExtendedComboBoxCellEditor extendedComboBoxCellEditor = new ExtendedComboBoxCellEditor(
				this.tableViewer.getTable(),
				new ArrayList<Object>(LinkUtil.getInstance().getLinkTypes().getAvailableLinkTypes().values()),
				new AdapterFactoryLabelProvider(EditingUtil.getInstance().getAdapterFactory()));
		this.tableViewer.setCellEditors(new CellEditor[] {null,extendedComboBoxCellEditor});
		this.tableViewer.setColumnProperties(new String[] {"col1","col2"});
		this.tableViewer.setCellModifier(new ICellModifier() {

			public boolean canModify(final Object element, final String property) {
				return property.equals("col2");
			}

			public Object getValue(final Object element, final String property) {
				// TODO Auto-generated method stub
				return null;
			}

			public void modify(final Object element, final String property, final Object value) {
				// TODO Auto-generated method stub

			}

		});


	}


	/**
	 * Return the initial size of the dialog
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(500, 375);
	}

}
