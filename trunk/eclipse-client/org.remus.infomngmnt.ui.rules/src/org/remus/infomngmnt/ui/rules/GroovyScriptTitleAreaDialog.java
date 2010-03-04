package org.remus.infomngmnt.ui.rules;

import java.util.List;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.IOpenListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.OpenEvent;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;

import org.remus.infomngmnt.common.ui.UIUtil;
import org.remus.infomngmnt.common.ui.image.ResourceManager;
import org.remus.infomngmnt.ui.UIPlugin;
import org.remus.infomngmnt.ui.rules.extension.IMethodCategory;
import org.remus.infomngmnt.ui.rules.extension.IMethodDefinition;
import org.remus.infomngmnt.ui.rules.service.IGroovyScriptExtensionService;
import org.remus.rules.provider.RuleEditPlugin;

public class GroovyScriptTitleAreaDialog extends TitleAreaDialog {

	private Text text;
	private String script;
	private final IGroovyScriptExtensionService service;

	/**
	 * Create the dialog
	 * 
	 * @param parentShell
	 */
	public GroovyScriptTitleAreaDialog(final Shell parentShell, final String script) {
		super(parentShell);
		setShellStyle(getShellStyle() | SWT.RESIZE | SWT.MAX);
		this.script = script;
		this.service = RuleEditPlugin.getPlugin().getServiceTracker().getService(
				IGroovyScriptExtensionService.class);
	}

	@Override
	protected void configureShell(final Shell newShell) {

		super.configureShell(newShell);

		newShell.setText("Enter a groovy script.");
	}

	/**
	 * Create contents of the dialog
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(final Composite parent) {

		Composite area = (Composite) super.createDialogArea(parent);

		SashForm sashForm_2 = new SashForm(area, SWT.VERTICAL);
		sashForm_2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		SashForm sashForm = new SashForm(sashForm_2, SWT.NONE);

		Composite composite_1 = new Composite(sashForm, SWT.NONE);
		GridLayout gl_composite_1 = new GridLayout(1, false);
		composite_1.setLayout(gl_composite_1);

		Label lblAvailableMethods = new Label(composite_1, SWT.NONE);
		lblAvailableMethods.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblAvailableMethods.setText("Available Methods");

		TreeViewer treeViewer = new TreeViewer(composite_1, SWT.BORDER);
		Tree tree = treeViewer.getTree();
		GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		layoutData.minimumHeight = 100;
		tree.setLayoutData(layoutData);
		treeViewer.setContentProvider(new ITreeContentProvider() {

			public void inputChanged(final Viewer viewer, final Object oldInput,
					final Object newInput) {
				// TODO Auto-generated method stub

			}

			public void dispose() {
				// TODO Auto-generated method stub

			}

			public Object[] getElements(final Object inputElement) {
				return GroovyScriptTitleAreaDialog.this.service.getAllScriptCategories();
			}

			public boolean hasChildren(final Object element) {
				return getChildren(element).length > 0;
			}

			public Object getParent(final Object element) {
				if (element instanceof IMethodDefinition) {
					return ((IMethodDefinition) element).getParentCategory();
				}
				return null;

			}

			public Object[] getChildren(final Object parentElement) {
				if (parentElement instanceof IMethodCategory) {
					List<IMethodDefinition> definitions = ((IMethodCategory) parentElement)
							.getDefinitions();
					return definitions.toArray(new IMethodDefinition[definitions.size()]);
				}
				return new Object[0];
			}
		});
		treeViewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(final Object element) {
				if (element instanceof IMethodCategory) {
					return ((IMethodCategory) element).getName();
				} else if (element instanceof IMethodDefinition) {
					return ((IMethodDefinition) element).getName();
				}
				return super.getText(element);
			}
		});
		treeViewer.setSorter(new ViewerSorter());
		treeViewer.setInput(this.service.getAllScriptCategories());

		Composite composite = new Composite(sashForm, SWT.NONE);
		GridLayout gl_composite = new GridLayout(1, false);
		composite.setLayout(gl_composite);

		Label lblMethodDescription = new Label(composite, SWT.NONE);
		lblMethodDescription.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		lblMethodDescription.setText("Method Description");

		final Browser browser = new Browser(composite, SWT.BORDER);
		browser.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		sashForm.setWeights(new int[] { 1, 1 });
		treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(final SelectionChangedEvent event) {
				Object firstElement = ((IStructuredSelection) event.getSelection())
						.getFirstElement();
				if (firstElement instanceof IMethodDefinition) {
					if (((IMethodDefinition) firstElement).getHtml() != null) {
						browser.setText(((IMethodDefinition) firstElement).getHtml());
					} else {
						browser.setText(NLS.bind(
								"<html><body style=\"margin:0 0 0 0;\">{0}</body></html>",
								"No documentation available."));
					}
				} else {
					browser.setText(NLS.bind(
							"<html><body style=\"margin:0 0 0 0;\">{0}</body></html>",
							"No method selected"));
				}

			}
		});
		treeViewer.addOpenListener(new IOpenListener() {

			public void open(final OpenEvent event) {
				Object firstElement = ((IStructuredSelection) event.getSelection())
						.getFirstElement();
				if (firstElement instanceof IMethodDefinition) {
					GroovyScriptTitleAreaDialog.this.text.insert(((IMethodDefinition) firstElement)
							.getString());
				}
			}
		});
		browser.setText(NLS.bind("<html><body>{0}</body></html>", "No method selected"));

		Composite composite_2 = new Composite(sashForm_2, SWT.NONE);
		composite_2.setLayout(new GridLayout(1, false));
		final Label enterGroovyScriptLabel = new Label(composite_2, SWT.NONE);
		enterGroovyScriptLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		enterGroovyScriptLabel.setText("Enter groovy script to execute...");

		this.text = new Text(composite_2, SWT.V_SCROLL | SWT.MULTI | SWT.H_SCROLL | SWT.BORDER);
		this.text.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		this.text.setFont(ResourceManager.getFont("Courier", 12, SWT.NONE));
		this.text.setText(this.script == null ? "" : this.script);
		this.text.addListener(SWT.Modify, new Listener() {
			public void handleEvent(final Event event) {
				GroovyScriptTitleAreaDialog.this.script = GroovyScriptTitleAreaDialog.this.text
						.getText();

			}

		});
		sashForm_2.setWeights(new int[] { 1, 1 });

		setTitle("Enter a groovy script.");
		setMessage("You can specify if the rule is selectable by the dropped/paste input");
		setTitleImage(ResourceManager.getPluginImage(RuleEditPlugin.getPlugin(),
				"icons/full/wizban/script_edit_wizard.png"));
		return area;
	}

	@Override
	public boolean close() {
		RuleEditPlugin.getPlugin().getServiceTracker().ungetService(this.service);
		UIUtil.saveDialogSettings(UIPlugin.getDefault(), this);
		return super.close();
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

	@Override
	protected Point getInitialLocation(final Point initialSize) {
		return UIUtil.getDialogSettingsInitialLocation(UIPlugin.getDefault(), this, super
				.getInitialLocation(initialSize));
	}

	/**
	 * Return the initial size of the dialog
	 */
	@Override
	protected Point getInitialSize() {
		return UIUtil
				.getDialogSettingsInitialSize(UIPlugin.getDefault(), this, new Point(600, 500));
	}

	public String getScript() {
		return this.script;
	}
}
