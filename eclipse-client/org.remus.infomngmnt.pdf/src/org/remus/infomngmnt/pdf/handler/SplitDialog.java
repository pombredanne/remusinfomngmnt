package org.remus.infomngmnt.pdf.handler;

import java.io.FileOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.pdfbox.pdfwriter.COSWriter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.Splitter;
import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.value.AbstractObservableValue;
import org.eclipse.core.databinding.observable.value.IValueChangeListener;
import org.eclipse.core.databinding.observable.value.ValueChangeEvent;
import org.eclipse.core.databinding.observable.value.WritableValue;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.databinding.swt.ISWTObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.osgi.util.NLS;
import org.eclipse.remus.Category;
import org.eclipse.remus.InformationUnit;
import org.eclipse.remus.InformationUnitListItem;
import org.eclipse.remus.common.core.util.ResourceUtil;
import org.eclipse.remus.common.ui.UIUtil;
import org.eclipse.remus.core.commands.CommandFactory;
import org.eclipse.remus.core.model.InformationStructureEdit;
import org.eclipse.remus.core.services.IEditingHandler;
import org.eclipse.remus.ui.category.CategoryBrowser;
import org.eclipse.remus.util.CategoryUtil;
import org.eclipse.remus.util.InformationUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import org.remus.infomngmnt.pdf.Activator;
import org.remus.infomngmnt.pdf.messages.Messages;

public class SplitDialog extends Dialog {
	private Text pdf2Split;
	private CategoryBrowser categoryField;
	private Text text_2;
	private final WritableValue splitValue;
	private Scale scale;
	private final int pageCount2;
	private Label splitLabel;
	private final InformationUnit unit;

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */
	public SplitDialog(final Shell parentShell, final InformationUnit unit, final int pageCount) {
		super(parentShell);
		this.unit = unit;
		this.pageCount2 = pageCount;
		this.splitValue = new WritableValue(-1, Integer.class);
		setShellStyle(getShellStyle() | SWT.RESIZE);

	}

	/**
	 * Create contents of the dialog.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(final Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(new GridLayout(3, false));

		Label lblPdf = new Label(container, SWT.NONE);
		lblPdf.setText(Messages.SplitDialog_PDF);

		this.pdf2Split = new Text(container, SWT.BORDER | SWT.READ_ONLY);
		this.pdf2Split.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		Label label_4 = new Label(container, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_4.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 3, 1));

		Label lblTargetCategory = new Label(container, SWT.NONE);
		lblTargetCategory.setText(Messages.SplitDialog_TargetCat);

		this.categoryField = new CategoryBrowser(container, SWT.BORDER);
		this.categoryField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		Label lblPdfName = new Label(container, SWT.NONE);
		lblPdfName.setText(Messages.SplitDialog_NameScheme);

		this.text_2 = new Text(container, SWT.BORDER);
		this.text_2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		this.scale = new Scale(container, SWT.NONE);
		this.scale.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 3, 1));
		this.scale.setIncrement(1);
		this.scale.setMinimum(1);
		this.scale.setMaximum(this.pageCount2);

		this.splitLabel = new Label(container, SWT.NONE);
		this.splitLabel.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 3, 1));

		bindValuesToUi();

		return container;
	}

	private void bindValuesToUi() {
		DataBindingContext ctx = new DataBindingContext();
		this.pdf2Split.setText(InformationUtil
				.getFullReadablePath((InformationUnitListItem) this.unit
						.getAdapter(InformationUnitListItem.class)));
		this.text_2.setText(this.unit.getLabel() + Messages.SplitDialog_Part);
		ISWTObservableValue observeSelection = SWTObservables.observeSelection(this.scale);
		Binding bindValue = ctx.bindValue(observeSelection, this.splitValue);

		this.splitValue.addValueChangeListener(new IValueChangeListener() {

			public void handleValueChange(final ValueChangeEvent event) {
				Integer value = (Integer) ((AbstractObservableValue) event.getObservable())
						.getValue();
				SplitDialog.this.splitLabel.setText(NLS
						.bind(Messages.SplitDialog_SplitPDFAfterEvery, value));
				SplitDialog.this.splitLabel.getParent().layout();
			}
		});
		bindValue.updateTargetToModel();

	}

	@Override
	protected void okPressed() {
		ProgressMonitorDialog pmd = new ProgressMonitorDialog(getShell());
		final String pdf1 = this.text_2.getText();
		final String text = this.categoryField.getText().getText();
		final Integer value = (Integer) SplitDialog.this.splitValue.getValue();
		try {
			pmd.run(true, false, new IRunnableWithProgress() {

				public void run(final IProgressMonitor monitor) throws InvocationTargetException,
						InterruptedException {
					try {
						Splitter splitter = new Splitter();
						splitter.setSplitAtPage(value);
						IFile binaryReferenceFile = InformationUtil
								.getBinaryReferenceFile(SplitDialog.this.unit);
						PDDocument load = PDDocument.load(binaryReferenceFile.getLocationURI()
								.toURL());
						List documents = splitter.split(load);
						IEditingHandler service = Activator.getDefault().getServiceTracker()
								.getService(IEditingHandler.class);
						EditingDomain domain = service.getNavigationEditingDomain();
						CompoundCommand cc = new CompoundCommand();
						Category cat = CategoryUtil.findCategory(text, false);
						if (cat == null) {
							service.execute(CommandFactory.CREATE_CATEGORY(text, domain));
							cat = CategoryUtil.findCategory(text, false);
						}
						for (int i = 0; i < documents.size(); i++) {
							IFile file1 = ResourceUtil.createTempFile("pdf"); //$NON-NLS-1$
							PDDocument doc = (PDDocument) documents.get(i);
							FileOutputStream output = new FileOutputStream(file1.getLocation()
									.toOSString());
							COSWriter writer = new COSWriter(output);
							writer.write(doc);
							doc.close();
							InformationStructureEdit edit = InformationStructureEdit
									.newSession(Activator.TYPE_ID);
							InformationUnit newInformationUnit = edit.newInformationUnit();

							edit.setValue(newInformationUnit, "@label", NLS.bind(pdf1, i + 1)); //$NON-NLS-1$

							CompoundCommand cc1 = CommandFactory.CREATE_INFOTYPE(
									newInformationUnit, cat, monitor);
							cc1.append(CommandFactory.addFileToInfoUnit(file1, newInformationUnit,
									domain));

							cc.append(cc1);
						}
						cc
								.setLabel(NLS.bind(Messages.SplitDialog_Split, SplitDialog.this.unit
										.getLabel()));
						service.execute(cc);
					} catch (Exception e) {
						throw new InvocationTargetException(e);
					}
				}
			});
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		super.okPressed();
	}

	/**
	 * Create contents of the button bar.
	 * 
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(final Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}

	@Override
	protected Point getInitialLocation(final Point initialSize) {
		return UIUtil.getDialogSettingsInitialLocation(Activator.getDefault(), this, super
				.getInitialLocation(initialSize));
	}

	@Override
	public boolean close() {
		UIUtil.saveDialogSettings(Activator.getDefault(), this);
		return super.close();
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return UIUtil.getDialogSettingsInitialSize(Activator.getDefault(), this,
				new Point(450, 300));
	}

	@Override
	protected void configureShell(final Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(Messages.SplitDialog_SplitPdf);
	}

}
