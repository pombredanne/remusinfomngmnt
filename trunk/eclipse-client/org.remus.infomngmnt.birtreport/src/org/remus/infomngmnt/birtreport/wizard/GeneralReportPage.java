package org.remus.infomngmnt.birtreport.wizard;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.databinding.observable.value.WritableValue;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.birtreport.ReportActivator;
import org.remus.infomngmnt.birtreport.extension.IReportTemplate;
import org.remus.infomngmnt.birtreport.extension.ITemplateCategory;
import org.remus.infomngmnt.birtreport.extension.ReportTemplateManager;
import org.remus.infomngmnt.birtreport.internal.extension.ReportContentProvider;
import org.remus.infomngmnt.common.ui.image.ResourceManager;
import org.remus.infomngmnt.common.ui.wizards.WizardValidatingUtil;
import org.remus.infomngmnt.core.operation.LoadFileToTmpFromPathRunnable;
import org.remus.infomngmnt.ui.newwizards.GeneralPage;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class GeneralReportPage extends GeneralPage {

	private Text fileNameText;
	private Button browseButton;
	protected String tmpText;
	private IFile tmpFile;

	private final WritableValue tmpFileObservable = new WritableValue(null, null);

	private Tree tree;
	private Button btnCreateFromReport;
	private Button btnCreateFromBirt;
	private TreeViewer templateViewer;

	/**
	 * @wbp.parser.constructor
	 */
	public GeneralReportPage(final Category category) {
		super(category);

	}

	public GeneralReportPage(final InformationUnitListItem selection) {
		super(selection);
	}

	@Override
	public void createControl(final Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		container.setLayout(new GridLayout());
		setTitle("New Report");
		setMessage("This wizard enables you to create a new report.");
		setImageDescriptor(ResourceManager.getPluginImageDescriptor(ReportActivator.getDefault(),
				"icons/create_report_wizard.gif"));

		doCreateParentElementGroup(container);
		Group group = new Group(container, SWT.NONE);
		group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		group.setLayout(new GridLayout(3, false));
		group.setText("Name && File");
		doCreateNameElements(group);

		GridData gd_nameText = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gd_nameText.horizontalSpan = 2;
		this.nameText.setLayoutData(gd_nameText);

		this.btnCreateFromReport = new Button(group, SWT.RADIO);
		this.btnCreateFromReport.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 3,
				1));
		this.btnCreateFromReport.setText("Create from Report Template");

		this.templateViewer = new TreeViewer(group, SWT.BORDER | SWT.FULL_SELECTION);
		this.tree = this.templateViewer.getTree();
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1);
		gridData.heightHint = 100;
		this.tree.setLayoutData(gridData);

		this.btnCreateFromBirt = new Button(group, SWT.RADIO);
		this.btnCreateFromBirt
				.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1));
		this.btnCreateFromBirt.setToolTipText("Create from BIRT Report File");
		this.btnCreateFromBirt.setText("Create from BIRT Report File");

		final Label nameLabel = new Label(group, SWT.NONE);
		nameLabel.setText("File");
		this.fileNameText = new Text(group, SWT.BORDER);
		gd_nameText = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gd_nameText.horizontalSpan = 2;
		this.fileNameText.setLayoutData(gd_nameText);
		new Label(group, SWT.NONE);
		new Label(group, SWT.NONE);

		this.browseButton = new Button(group, SWT.PUSH);
		this.browseButton.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		this.browseButton.setText("Browse...");
		this.browseButton.addListener(SWT.Selection, new Listener() {

			public void handleEvent(final Event event) {
				FileDialog fd = new FileDialog(getShell());
				fd.setFilterExtensions(new String[] { "*.rptdesign" });
				fd.setFilterNames(new String[] { "BIRT Reports" });
				String open = fd.open();
				if (open != null) {
					GeneralReportPage.this.fileNameText.setText(open);
					LoadFileToTmpFromPathRunnable runnable = new LoadFileToTmpFromPathRunnable();
					runnable.setFilePath(open);
					try {
						getContainer().run(true, true, runnable);
						GeneralReportPage.this.tmpFile = runnable.getTmpFile();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		});

		doCreatePropertiesGroup(container);
		initDatabinding();
		presetValues();
		initValidation();
		setControl(container);
	}

	@Override
	protected void presetValues() {
		super.presetValues();
		this.btnCreateFromReport.setSelection(true);
	}

	@Override
	protected void initDatabinding() {
		super.initDatabinding();
		this.templateViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(final SelectionChangedEvent event) {
				// TODO Auto-generated method stub

			}
		});
		this.templateViewer.setContentProvider(new ReportContentProvider());
		this.templateViewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(final Object element) {
				if (element instanceof ITemplateCategory) {
					return ((ITemplateCategory) element).getLabel();
				} else if (element instanceof IReportTemplate) {
					return ((IReportTemplate) element).getLabel();
				}
				return super.getText(element);
			}
		});
		this.templateViewer.setSorter(new ViewerSorter());
		this.templateViewer.addFilter(new ViewerFilter() {
			@Override
			public boolean select(final Viewer viewer, final Object parentElement,
					final Object element) {
				if (element instanceof ITemplateCategory
						&& ((ITemplateCategory) element).getTemplates().length == 0) {
					return false;
				}
				return true;
			}
		});
		this.templateViewer.setInput(ReportTemplateManager.getInstance().getCategories());

	}

	/**
	 * @return the templateViewer
	 */
	public final TreeViewer getTemplateViewer() {
		return this.templateViewer;
	}

	@Override
	protected void initValidation() {
		super.initValidation();
		setPageComplete(validate(false));
		WizardValidatingUtil.validateControlsOnModify(this, this.btnCreateFromBirt,
				this.btnCreateFromReport, this.tree, this.fileNameText);
	}

	@Override
	public boolean validate(final boolean showErrorMessage) {
		boolean validate = super.validate(showErrorMessage);
		if (!validate) {
			return validate;
		}
		if (this.btnCreateFromReport.getSelection()
				&& !(((IStructuredSelection) this.templateViewer.getSelection()).getFirstElement() instanceof IReportTemplate)) {
			if (showErrorMessage) {
				setErrorMessage("No Template selected");
				return false;
			}
		}
		if (this.btnCreateFromBirt.getSelection()) {
			if (this.fileNameText.getText().trim().length() == 0) {
				setErrorMessage("No path specified");
				return false;
			}
			File file = new File(this.fileNameText.getText());
			if (!file.isFile() || !file.canRead()) {
				setErrorMessage("No valid report file");
				return false;
			}
		}
		setErrorMessage(null);
		return true;
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	/**
	 * @return the tmpFile
	 */
	public IFile getTmpFile() {
		return this.tmpFile;
	}

	/**
	 * @param tmpFile
	 *            the tmpFile to set
	 */
	public void setTmpFile(final IFile tmpFile) {
		this.tmpFileObservable.setValue(tmpFile);
	}

	public boolean isTemplateSelected() {
		return this.btnCreateFromReport.getSelection();
	}
}
