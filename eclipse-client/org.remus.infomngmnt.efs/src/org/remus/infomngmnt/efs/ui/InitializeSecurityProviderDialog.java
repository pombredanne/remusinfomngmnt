package org.remus.infomngmnt.efs.ui;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

public class InitializeSecurityProviderDialog extends TitleAreaDialog {

	private Table table_1;
	private Table table;

	/**
	 * Create the dialog
	 * 
	 * @param parentShell
	 */
	public InitializeSecurityProviderDialog(final Shell parentShell) {
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
		Composite container = new Composite(area, SWT.NONE);
		final TableWrapLayout tableWrapLayout = new TableWrapLayout();
		container.setLayout(tableWrapLayout);
		container.setLayoutData(new GridData(GridData.FILL_BOTH));

		final Label remusHasFoundLabel = new Label(container, SWT.WRAP);
		final TableWrapData twd_remusHasFoundLabel = new TableWrapData(TableWrapData.FILL_GRAB,
				TableWrapData.FILL);
		twd_remusHasFoundLabel.grabHorizontal = true;
		remusHasFoundLabel.setLayoutData(twd_remusHasFoundLabel);
		remusHasFoundLabel
				.setText("RIM has found projects which are encrypted. Before accessing to the content of this projects you have to initialize the appropiated security addins which can decrypt the content.");

		final Composite composite_2 = new Composite(container, SWT.NONE);
		composite_2.setLayoutData(new TableWrapData(TableWrapData.FILL, TableWrapData.FILL_GRAB));
		composite_2.setLayout(new FillLayout());

		final SashForm sashForm = new SashForm(composite_2, SWT.NONE);

		final Composite composite = new Composite(sashForm, SWT.NONE);
		composite.setLayout(new GridLayout());

		final Label providersNeedsInitializationLabel = new Label(composite, SWT.NONE);
		providersNeedsInitializationLabel.setText("Providers needs initialization");

		final TableViewer tableViewer = new TableViewer(composite, SWT.BORDER);
		this.table = tableViewer.getTable();
		final GridData gd_table = new GridData(SWT.FILL, SWT.FILL, true, false);
		gd_table.heightHint = 100;
		this.table.setLayoutData(gd_table);

		final Composite composite_1 = new Composite(sashForm, SWT.NONE);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		composite_1.setLayout(gridLayout);

		final Label affectedProjectsLabel = new Label(composite_1, SWT.NONE);
		affectedProjectsLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1));
		affectedProjectsLabel.setText("Affected projects");

		final TableViewer tableViewer_1 = new TableViewer(composite_1, SWT.BORDER);
		this.table_1 = tableViewer_1.getTable();
		final GridData gd_table_1 = new GridData(SWT.FILL, SWT.FILL, true, false, 3, 1);
		gd_table_1.heightHint = 60;
		this.table_1.setLayoutData(gd_table_1);

		final Label label_1 = new Label(composite_1, SWT.NONE);
		label_1.setText("Label");

		final Label label = new Label(composite_1, SWT.NONE);
		label.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false));
		label.setText("Label");

		final Button initializeButton = new Button(composite_1, SWT.NONE);
		final GridData gd_initializeButton = new GridData(SWT.RIGHT, SWT.CENTER, false, false);
		initializeButton.setLayoutData(gd_initializeButton);
		initializeButton.setText("Initialize");
		sashForm.setWeights(new int[] { 1, 1 });
		//
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

}
