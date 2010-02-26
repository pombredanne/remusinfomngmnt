package org.remus.infomngmnt.ui.link;

import java.util.Collection;
import java.util.List;

import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.ItemProvider;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.osgi.util.NLS;
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
import org.eclipse.swt.widgets.Text;

import org.remus.infomngmnt.AbstractInformationUnit;
import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.Link;
import org.remus.infomngmnt.common.ui.image.ResourceManager;
import org.remus.infomngmnt.core.extension.InformationExtensionManager;
import org.remus.infomngmnt.ui.UIPlugin;
import org.remus.infomngmnt.ui.dialogs.IInputValidator;
import org.remus.infomngmnt.ui.dialogs.InfoUnitSelectionDialog;
import org.remus.infomngmnt.util.EditingUtil;
import org.remus.infomngmnt.util.StatusCreator;

public class NewLinkWizardPage extends TitleAreaDialog {

	private final class IInputValidatorImplementation implements IInputValidator {
		public IStatus validate(final Object object) {
			if (((AbstractInformationUnit) object).getId().equals(
					NewLinkWizardPage.this.infoUnit.getId())) {
				return StatusCreator.newStatus("Cannot link to the same item");
			}
			for (Object link : NewLinkWizardPage.this.input) {
				if (((Link) link).getTarget().getId().equals(
						((AbstractInformationUnit) object).getId())) {
					return StatusCreator.newStatus("Item is already linked.");
				}
			}
			return Status.OK_STATUS;
		}
	}

	private Text text;
	private Table table;
	private InformationUnit infoUnit;
	private Label imageLabel;
	private TableViewer tableViewer;
	private Button deleteButton;

	private Button newButton;
	private final ItemProvider itemProvider;
	private WritableList input;

	/**
	 * Create the wizard
	 */
	public NewLinkWizardPage(final Shell parentShell, final InformationUnit infoUnit,
			final EditingDomain domain) {
		super(parentShell);
		this.infoUnit = infoUnit;
		this.itemProvider = new ItemProvider(infoUnit.getLinks());
		setTitle("Wizard Page title");
	}

	@Override
	protected void configureShell(final Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Edit Links");
	}

	@Override
	protected Control createDialogArea(final Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);

		setTitle("Edit Links");
		setMessage(NLS.bind("Links for unit {0}", this.infoUnit.getLabel()));
		setTitleImage(ResourceManager.getPluginImage(UIPlugin.getDefault(),
				"icons/iconexperience/wizards/edit_link_wizard.png"));

		Composite container = new Composite(area, SWT.NONE);
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		container.setLayout(gridLayout);

		final Label sourceLabel = new Label(container, SWT.NONE);
		sourceLabel.setText("Source");

		this.imageLabel = new Label(container, SWT.NONE);
		this.imageLabel.setLayoutData(new GridData(16, 16));

		this.text = new Text(container, SWT.BORDER);
		this.text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		this.text.setEnabled(false);

		final Group targetsGroup = new Group(container, SWT.NONE);
		targetsGroup.setText("Targets");
		final GridData gd_targetsGroup = new GridData(SWT.FILL, SWT.FILL, false, true, 3, 1);
		targetsGroup.setLayoutData(gd_targetsGroup);
		final GridLayout gridLayout_1 = new GridLayout();
		targetsGroup.setLayout(gridLayout_1);

		this.tableViewer = new TableViewer(targetsGroup, SWT.V_SCROLL | SWT.BORDER | SWT.MULTI);
		this.tableViewer.setContentProvider(new ObservableListContentProvider());
		this.tableViewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(final Object element) {
				return ((Link) element).getTarget().getLabel();
			}

			@Override
			public Image getImage(final Object element) {
				return InformationExtensionManager.getInstance().getInfoTypeByType(
						((Link) element).getTarget().getType()).getImage();
			}
		});
		this.tableViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(final SelectionChangedEvent event) {
				handleSelectionChanged((IStructuredSelection) event.getSelection());

			}
		});

		this.table = this.tableViewer.getTable();
		this.table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		final Composite composite = new Composite(targetsGroup, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
		final GridLayout gridLayout_2 = new GridLayout();
		gridLayout_2.numColumns = 2;
		composite.setLayout(gridLayout_2);
		this.newButton = new Button(composite, SWT.NONE);
		this.newButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		this.newButton.setText("New");
		this.newButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				InfoUnitSelectionDialog diag = InfoUnitSelectionDialog.create(getShell(),
						new IInputValidatorImplementation());

				if (diag.open() == IDialogConstants.OK_ID) {
					InformationUnitListItem newItem = (InformationUnitListItem) diag.getResult()[0];
					Link createLink = InfomngmntFactory.eINSTANCE.createLink();
					Object adapter = newItem.getAdapter(InformationUnit.class);
					if (adapter != null) {
						createLink.setTarget((InformationUnit) adapter);
						NewLinkWizardPage.this.input.add(createLink);
					}
				}
			}
		});

		this.deleteButton = new Button(composite, SWT.NONE);
		this.deleteButton.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
		this.deleteButton.setText("Delete");
		this.deleteButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				List list = ((IStructuredSelection) NewLinkWizardPage.this.tableViewer
						.getSelection()).toList();
				NewLinkWizardPage.this.input.removeAll(list);
			}
		});
		this.deleteButton.setEnabled(false);
		setResult(this.infoUnit, false);
		return area;

	}

	protected void handleSelectionChanged(final IStructuredSelection selection) {
		this.deleteButton.setEnabled(!selection.isEmpty());
	}

	protected void setResult(final AbstractInformationUnit result, final boolean notify) {
		this.text.setText(result.getLabel());
		Object image2 = ((IItemLabelProvider) EditingUtil.getInstance().getAdapterFactory().adapt(
				result, IItemLabelProvider.class)).getImage(result);
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
		this.tableViewer.setInput(this.input = new WritableList(this.itemProvider.getChildren(),
				null));

	}

	@SuppressWarnings("unchecked")
	public Collection getResult() {
		return this.input;
	}

	/**
	 * Return the initial size of the dialog
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(500, 375);
	}

}
