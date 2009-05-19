/*******************************************************************************
 * Copyright (c) 2008 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/

package org.remus.infomngmnt.ui.rules;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.databinding.Binding;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.databinding.EMFDataBindingContext;
import org.eclipse.emf.edit.command.PasteFromClipboardCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.ui.action.CopyAction;
import org.eclipse.emf.edit.ui.action.CutAction;
import org.eclipse.emf.edit.ui.action.DeleteAction;
import org.eclipse.emf.edit.ui.action.PasteAction;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.actions.BaseSelectionListenerAction;

import org.remus.infomngmnt.AvailableRuleDefinitions;
import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.NewElementRules;
import org.remus.infomngmnt.RemusTransferType;
import org.remus.infomngmnt.RuleAction;
import org.remus.infomngmnt.core.extension.IInfoType;
import org.remus.infomngmnt.core.extension.InformationExtensionManager;
import org.remus.infomngmnt.core.model.EditingUtil;
import org.remus.infomngmnt.core.services.IRuleService;
import org.remus.infomngmnt.provider.InfomngmntEditPlugin;
import org.remus.infomngmnt.ui.extension.AbstractCreationPreferencePage;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class RulePreferencePage extends PreferencePage implements IWorkbenchPreferencePage {

	private Text ruleName;
	private Tree tree;
	private Combo combo;
	private AvailableRuleDefinitions model;
	private EMFDataBindingContext ctx;
	private NewElementRules selectedRuleSet;
	private TreeViewer treeViewer;
	private EditingDomain editingDomain;

	// action
	private RuleCutAction cutAction;
	private RuleCopyAction copyAction;
	private PasteRuleAction pasteAction;
	private RuleDeleteAction deleteAction;

	private EditRuleAction editRuleAction;
	private IAction newAction;
	private MenuManager newMenuManager;
	private StackLayout stackLayout;
	private List<AbstractCreationPreferencePage> knownPrefPages;
	private Group availableRulesGroup;
	private ToolItem deleteToolItem;
	private ToolItem newToolItem;

	private Binding nameBinding;
	private RuleAction selectedRuleAction;
	private Button groovyMatcherButton;
	private Button postProcessingActionsButton;

	/**
	 * 
	 */
	public RulePreferencePage() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param title
	 */
	public RulePreferencePage(final String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param title
	 * @param image
	 */
	public RulePreferencePage(final String title, final ImageDescriptor image) {
		super(title, image);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.preference.PreferencePage#createContents(org.eclipse
	 * .swt.widgets.Composite)
	 */
	@Override
	protected Control createContents(final Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		composite.setLayout(new GridLayout(3, false));

		final Label rulesetLabel = new Label(composite, SWT.NONE);
		rulesetLabel.setText("Rule-Set");

		this.combo = new Combo(composite, SWT.READ_ONLY);
		this.combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		EList<NewElementRules> infoTypeRules = this.model.getNewElementRules();
		for (NewElementRules infoTypeElementRule : infoTypeRules) {
			this.combo.add(infoTypeElementRule.getName());
		}
		this.combo.addListener(SWT.Modify, new Listener() {
			public void handleEvent(final Event event) {
				handleRuleSetSelectionChange(event);
			}
		});
		this.editingDomain = EditingUtil.getInstance().createNewEditingDomain();

		final ToolBar toolBar_1 = new ToolBar(composite, SWT.NONE);

		this.newToolItem = new ToolItem(toolBar_1, SWT.PUSH);
		this.newToolItem.setText("New");
		this.newToolItem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				NewRuleDialog diag = new NewRuleDialog(getShell(), RulePreferencePage.this.model);
				if (diag.open() == IDialogConstants.OK_ID) {
					RulePreferencePage.this.model.getNewElementRules().add(diag.getNewElement());
					RulePreferencePage.this.combo.add(diag.getNewElement().getName());
				}
			}
		});

		this.deleteToolItem = new ToolItem(toolBar_1, SWT.PUSH);
		this.deleteToolItem.setText("Delete");
		this.deleteToolItem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				if (MessageDialog.openConfirm(getShell(), "Confirm delete", NLS.bind(
						"Are you sure to delete ruleset {0}?",
						RulePreferencePage.this.selectedRuleSet.getName()))) {
					RulePreferencePage.this.model.getNewElementRules().remove(
							RulePreferencePage.this.selectedRuleSet);
					String itemToRemove = RulePreferencePage.this.selectedRuleSet.getName();
					RulePreferencePage.this.combo.select(0);
					RulePreferencePage.this.combo.remove(itemToRemove);
				}
			}
		});
		new Label(composite, SWT.NONE);

		final Link eclipseorgLink = new Link(composite, SWT.NONE);
		eclipseorgLink.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 2, 1));
		eclipseorgLink.setText("<a>Learn how to create rules...</a>");
		AdapterFactoryContentProvider ocp = new AdapterFactoryContentProvider(EditingUtil
				.getInstance().getAdapterFactory());

		final SashForm sashForm = new SashForm(composite, SWT.VERTICAL);
		sashForm.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1));

		final Group currentRuleConfigurationGroup = new Group(sashForm, SWT.NONE);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		currentRuleConfigurationGroup.setLayout(gridLayout);
		currentRuleConfigurationGroup.setText("Current Rule configuration");

		this.treeViewer = new TreeViewer(currentRuleConfigurationGroup, SWT.BORDER);
		this.tree = this.treeViewer.getTree();
		this.tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		this.tree.setLinesVisible(false);
		this.tree.setHeaderVisible(false);

		this.treeViewer.setContentProvider(ocp);
		this.treeViewer.setLabelProvider(new AdapterFactoryLabelProvider(EditingUtil.getInstance()
				.getAdapterFactory()));
		this.treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(final SelectionChangedEvent event) {
				handleSelectionChanged(event.getSelection());

			}
		});

		final Label ruleNameLabel = new Label(currentRuleConfigurationGroup, SWT.NONE);
		ruleNameLabel.setText("Rule name");

		this.ruleName = new Text(currentRuleConfigurationGroup, SWT.BORDER);
		this.ruleName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		new Label(currentRuleConfigurationGroup, SWT.NONE);

		final Composite composite_1 = new Composite(currentRuleConfigurationGroup, SWT.NONE);
		composite_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		final GridLayout gridLayout_1 = new GridLayout();
		gridLayout_1.numColumns = 2;
		gridLayout_1.marginHeight = 0;
		gridLayout_1.horizontalSpacing = 0;
		composite_1.setLayout(gridLayout_1);

		final Label editTheConditionLabel = new Label(composite_1, SWT.NONE);
		editTheConditionLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		editTheConditionLabel.setText("Condition when this rule will be executed:");

		this.groovyMatcherButton = new Button(composite_1, SWT.NONE);
		this.groovyMatcherButton.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
		this.groovyMatcherButton.setText("Edit");
		this.groovyMatcherButton.addListener(SWT.Selection, new Listener() {

			public void handleEvent(final Event event) {
				GroovyScriptTitleAreaDialog diag = new GroovyScriptTitleAreaDialog(getShell(),
						RulePreferencePage.this.selectedRuleAction.getGroovyMatcher());
				if (diag.open() == IDialogConstants.OK_ID) {
					Command setCommand = SetCommand.create(RulePreferencePage.this.editingDomain,
							RulePreferencePage.this.selectedRuleAction,
							InfomngmntPackage.Literals.RULE_ACTION__GROOVY_MATCHER, diag
									.getScript());
					RulePreferencePage.this.editingDomain.getCommandStack().execute(setCommand);
				}
			}

		});

		final Label postprocessingActionsLabel = new Label(composite_1, SWT.NONE);
		postprocessingActionsLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		postprocessingActionsLabel.setText("Post-processing actions");

		this.postProcessingActionsButton = new Button(composite_1, SWT.NONE);
		this.postProcessingActionsButton.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false));
		this.postProcessingActionsButton.setText("Edit");
		this.postProcessingActionsButton.addListener(SWT.Selection, new Listener() {

			public void handleEvent(final Event event) {
				GroovyScriptTitleAreaDialog diag = new GroovyScriptTitleAreaDialog(getShell(),
						RulePreferencePage.this.selectedRuleAction.getPostProcessingInstructions());
				if (diag.open() == IDialogConstants.OK_ID) {
					Command setCommand = SetCommand.create(RulePreferencePage.this.editingDomain,
							RulePreferencePage.this.selectedRuleAction,
							InfomngmntPackage.Literals.RULE_ACTION__POST_PROCESSING_INSTRUCTIONS,
							diag.getScript());
					RulePreferencePage.this.editingDomain.getCommandStack().execute(setCommand);
				}
			}

		});

		/*
		 * Disalbe
		 */
		// this.availableRulesGroup = new Group(sashForm, SWT.NONE);
		// this.stackLayout = new StackLayout();
		// this.availableRulesGroup.setLayout(this.stackLayout);
		// this.availableRulesGroup.setText("Information unit-specific preferences");
		this.combo.select(0);
		hookContextMenu();
		sashForm.setWeights(new int[] { 1 });
		return composite;
	}

	protected void handleSelectionChanged(final ISelection selection) {
		Object element = ((IStructuredSelection) selection).getFirstElement();
		if (this.nameBinding != null) {
			this.nameBinding.dispose();
		}
		this.ruleName.setEnabled(element instanceof RuleAction);
		this.groovyMatcherButton.setEnabled(element instanceof RuleAction);
		this.postProcessingActionsButton.setEnabled(element instanceof RuleAction);
		/*
		 * This will be disabled
		 */
		// if (element instanceof RuleAction) {
		// this.selectedRuleAction = (RuleAction) element;
		// AbstractCreationPreferencePage preferencePage =
		// UIExtensionManager.getInstance()
		// .getPreferencePageByTransferAndTypeId(
		// ((RemusTransferType) ((RuleAction) element).eContainer()).getId(),
		// ((RuleAction) element).getInfoTypeId());
		// if (preferencePage != null) {
		// RuleValue ruleValue = ((RuleAction) element).getRuleValue();
		// if (ruleValue == null) {
		// ruleValue = InfomngmntFactory.eINSTANCE.createRuleValue();
		// ((RuleAction) element).setRuleValue(ruleValue);
		// }
		// preferencePage.setValues(ruleValue, this.editingDomain);
		// if (!this.knownPrefPages.contains(preferencePage)) {
		// this.knownPrefPages.add(preferencePage);
		// preferencePage.createPreferencePage(this.availableRulesGroup);
		// }
		// preferencePage.bindValuesToUi();
		// this.stackLayout.topControl = preferencePage.getControl();
		// this.availableRulesGroup.layout();
		// }
		// IObservableValue emfName = EMFObservables.observeValue((EObject)
		// element,
		// InfomngmntPackage.Literals.RULE_ACTION__NAME);
		// ISWTObservableValue swtName =
		// SWTObservables.observeText(this.ruleName, SWT.Modify);
		// this.nameBinding = this.ctx.bindValue(swtName, emfName, null, null);
		// }
	}

	protected void handleRuleSetSelectionChange(final Event event) {
		this.selectedRuleSet = this.model.getNewElementRules().get(
				((Combo) event.widget).getSelectionIndex());
		this.ctx.dispose();
		this.treeViewer.setInput(this.selectedRuleSet);
		this.deleteToolItem.setEnabled(this.selectedRuleSet.isDeletable());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(final IWorkbench workbench) {
		this.model = InfomngmntEditPlugin.getPlugin().getService(IRuleService.class)
				.getElementRules();
		this.ctx = new EMFDataBindingContext();
		this.knownPrefPages = new ArrayList<AbstractCreationPreferencePage>();

	}

	private void hookContextMenu() {

		this.cutAction = new RuleCutAction(this.editingDomain);
		this.treeViewer.addSelectionChangedListener(this.cutAction);
		this.copyAction = new RuleCopyAction(this.editingDomain);
		this.treeViewer.addSelectionChangedListener(this.copyAction);
		this.pasteAction = new PasteRuleAction(this.editingDomain);
		this.treeViewer.addSelectionChangedListener(this.pasteAction);
		this.deleteAction = new RuleDeleteAction(this.editingDomain);
		this.treeViewer.addSelectionChangedListener(this.deleteAction);
		this.newMenuManager = new MenuManager("New");
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		this.treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(final SelectionChangedEvent event) {
				RulePreferencePage.this.newMenuManager.removeAll();
				populateNewMenu(event.getSelection());
			}
		});
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(final IMenuManager manager) {
				fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(this.treeViewer.getControl());
		this.treeViewer.getControl().setMenu(menu);
	}

	protected void populateNewMenu(final ISelection selection) {
		if (((IStructuredSelection) selection).getFirstElement() instanceof RemusTransferType) {
			RemusTransferType element = (RemusTransferType) ((IStructuredSelection) selection)
					.getFirstElement();
			Collection<IInfoType> types = InformationExtensionManager.getInstance().getTypes();
			for (IInfoType infoType : types) {
				if (infoType.getValidTransferTypeIds().contains(element.getId())) {
					if (!itemAlreadyPresent(element, infoType)) {
						this.newMenuManager.add(new NewItemAction(infoType, element));
					}
				}
			}
		}

	}

	boolean itemAlreadyPresent(final RemusTransferType type, final IInfoType infoType) {
		// EList<RuleAction> actions = type.getActions();
		// for (RuleAction foundAction : actions) {
		// if (foundAction.getInfoTypeId().equals(infoType.getType())) {
		// return true;
		// }
		// }
		return false;
	}

	protected void fillContextMenu(final IMenuManager manager) {

		manager.add(new Separator());
		manager.add(this.newMenuManager);
		manager.add(this.cutAction);
		manager.add(this.copyAction);
		manager.add(this.pasteAction);
		manager.add(new Separator());
		manager.add(this.deleteAction);

	}

	private static class RuleDeleteAction extends DeleteAction {
		public RuleDeleteAction(final EditingDomain domain) {
			super(domain);
		}

		@Override
		public boolean updateSelection(final IStructuredSelection selection) {
			List list = selection.toList();
			for (Object object : list) {
				if (object instanceof RemusTransferType) {
					return false;
				}
			}
			return super.updateSelection(selection);
		}
	}

	private static class RuleCopyAction extends CopyAction {
		public RuleCopyAction(final EditingDomain domain) {
			super(domain);
		}

		@Override
		public boolean updateSelection(final IStructuredSelection selection) {
			List list = selection.toList();
			for (Object object : list) {
				if (object instanceof RemusTransferType) {
					return false;
				}
			}
			return super.updateSelection(selection);
		}
	}

	private static class RuleCutAction extends CutAction {
		public RuleCutAction(final EditingDomain domain) {
			super(domain);
		}

		@Override
		public boolean updateSelection(final IStructuredSelection selection) {
			List list = selection.toList();
			for (Object object : list) {
				if (object instanceof RemusTransferType) {
					return false;
				}
			}
			return super.updateSelection(selection);
		}
	}

	private static class EditRuleAction extends BaseSelectionListenerAction {

		private final EditingDomain domain;

		public EditRuleAction(final EditingDomain domain) {
			super("Edit");
			this.domain = domain;
		}

		@Override
		public void run() {
			Object firstElement = getStructuredSelection().getFirstElement();

		}

		@Override
		protected boolean updateSelection(final IStructuredSelection selection) {
			return !selection.isEmpty() && selection.size() == 1;
		}

	}

	private class PasteRuleAction extends PasteAction {
		public PasteRuleAction(final EditingDomain editingDomain) {
			super(editingDomain);
		}

		@Override
		public Command createCommand(final Collection<?> selection) {
			Command createCommand = super.createCommand(selection);
			if (createCommand.canExecute()) {
				Collection<Object> clipboard = RulePreferencePage.this.editingDomain.getClipboard();
				for (Object object : clipboard) {
					if (object instanceof RuleAction
							&& ((PasteFromClipboardCommand) createCommand).getOwner() instanceof RemusTransferType) {
						String infoTypeId = ((RuleAction) object).getInfoTypeId();
						IInfoType infoTypeByType = InformationExtensionManager.getInstance()
								.getInfoTypeByType(infoTypeId);
						if (infoTypeByType != null
								&& infoTypeByType
										.getValidTransferTypeIds()
										.contains(
												((RemusTransferType) ((PasteFromClipboardCommand) createCommand)
														.getOwner()).getId())
								&& !itemAlreadyPresent(
										(RemusTransferType) ((PasteFromClipboardCommand) createCommand)
												.getOwner(), infoTypeByType)) {
							// can paste
						} else {
							return UnexecutableCommand.INSTANCE;
						}
					}
				}
			}
			return createCommand;
		}
	}

	@Override
	protected void performDefaults() {
		MessageDialog.openError(getShell(), "Error", "Restoring is currently not supported");
	}

	@Override
	public void performApply() {
		EditingUtil.getInstance().saveObjectToResource(this.model);
	}

	private static class NewItemAction extends Action {

		private final IInfoType infoType;
		private final RemusTransferType element;

		public NewItemAction(final IInfoType infoType, final RemusTransferType element) {
			this.infoType = infoType;
			this.element = element;
			setImageDescriptor(infoType.getImageDescriptor());
			setText(infoType.getType());
		}

		@Override
		public void run() {
			RuleAction createRuleAction = InfomngmntFactory.eINSTANCE.createRuleAction();
			createRuleAction.setInfoTypeId(this.infoType.getType());
			this.element.getActions().add(createRuleAction);
			super.run();
		}

	}

}
