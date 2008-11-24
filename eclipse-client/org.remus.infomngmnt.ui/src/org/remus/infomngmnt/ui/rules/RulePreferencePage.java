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

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.databinding.EMFDataBindingContext;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.ui.action.CopyAction;
import org.eclipse.emf.edit.ui.action.CutAction;
import org.eclipse.emf.edit.ui.action.DeleteAction;
import org.eclipse.emf.edit.ui.action.PasteAction;
import org.eclipse.emf.edit.ui.dnd.LocalTransfer;
import org.eclipse.emf.edit.ui.dnd.ViewerDragAdapter;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.BaseSelectionListenerAction;
import org.eclipse.ui.dialogs.ContainerCheckedTreeViewer;

import org.remus.infomngmnt.AvailableRuleDefinitions;
import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.NewElementRules;
import org.remus.infomngmnt.RemusTransferType;
import org.remus.infomngmnt.Rule;
import org.remus.infomngmnt.core.extension.AbstractRuleDefinition;
import org.remus.infomngmnt.core.extension.IRule;
import org.remus.infomngmnt.core.extension.RuleExtensionManager;
import org.remus.infomngmnt.core.model.EditingUtil;
import org.remus.infomngmnt.core.model.RuleUtil;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class RulePreferencePage extends PreferencePage implements
IWorkbenchPreferencePage {

	private Tree tree;
	private Combo combo;
	private AvailableRuleDefinitions model;
	private EMFDataBindingContext ctx;
	private NewElementRules selectedRuleSet;
	private CheckboxTreeViewer checkboxTableViewer;
	private TableViewer availableRulesTableViewer;
	private EditingDomain editingDomain;

	// action
	private RuleCutAction cutAction;
	private RuleCopyAction copyAction;
	private PasteAction pasteAction;
	private RuleDeleteAction deleteAction;
	private EditRuleAction editRuleAction;
	/**
	 * 
	 */
	public RulePreferencePage() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param title
	 */
	public RulePreferencePage(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param title
	 * @param image
	 */
	public RulePreferencePage(String title, ImageDescriptor image) {
		super(title, image);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.preference.PreferencePage#createContents(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createContents(Composite parent) {
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
			public void handleEvent(Event event) {
				handleRuleSetSelectionChange(event);
			}
		});
		this.editingDomain = EditingUtil.getInstance().createNewEditingDomain();

		final ToolBar toolBar_1 = new ToolBar(composite, SWT.NONE);

		final ToolItem newItemToolItem_2 = new ToolItem(toolBar_1, SWT.PUSH);
		newItemToolItem_2.setText("New item");

		final ToolItem newItemToolItem_3 = new ToolItem(toolBar_1, SWT.PUSH);
		newItemToolItem_3.setText("New item");


		final ToolBar toolBar = new ToolBar(composite, SWT.NONE | SWT.RIGHT);
		toolBar.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));

		final ToolItem newItemToolItem = new ToolItem(toolBar, SWT.PUSH);
		newItemToolItem.setText("New item");
		newItemToolItem.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_DEF_VIEW));

		final ToolItem newItemToolItem_1 = new ToolItem(toolBar, SWT.PUSH);
		newItemToolItem_1.setText("New item");

		final Link eclipseorgLink = new Link(composite, SWT.NONE);
		eclipseorgLink.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
		eclipseorgLink.setText("<a>Learn how to create rules...</a>");
		AdapterFactoryContentProvider ocp = new AdapterFactoryContentProvider(EditingUtil.getInstance().getAdapterFactory());
		int dndOperations = DND.DROP_COPY | DND.DROP_MOVE | DND.DROP_LINK;
		Transfer[] transfers = new Transfer[] { LocalTransfer.getInstance() };

		final SashForm sashForm = new SashForm(composite, SWT.VERTICAL);
		sashForm.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1));

		final Group currentRuleConfigurationGroup = new Group(sashForm, SWT.NONE);
		currentRuleConfigurationGroup.setText("Current Rule configuration");
		currentRuleConfigurationGroup.setLayout(new FillLayout());

		this.checkboxTableViewer = new ContainerCheckedTreeViewer(currentRuleConfigurationGroup, SWT.BORDER);
		this.tree = this.checkboxTableViewer.getTree();
		this.tree.setLinesVisible(false);
		this.tree.setHeaderVisible(false);


		this.checkboxTableViewer.setContentProvider(ocp);
		this.checkboxTableViewer.setLabelProvider(new AdapterFactoryLabelProvider(EditingUtil.getInstance().getAdapterFactory()));
		this.checkboxTableViewer.addDragSupport(dndOperations, transfers, new ViewerDragAdapter(this.checkboxTableViewer));
		this.checkboxTableViewer.addDropSupport(dndOperations, transfers, new RuleDropAdapter(this.editingDomain, this.checkboxTableViewer));

		final Group availableRulesGroup = new Group(sashForm, SWT.NONE);
		availableRulesGroup.setLayout(new FillLayout());
		availableRulesGroup.setText("Available Rule-Templates");


		this.availableRulesTableViewer = new TableViewer(availableRulesGroup, SWT.BORDER);
		this.availableRulesTableViewer.setContentProvider(new ArrayContentProvider());
		this.availableRulesTableViewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				return ((IRule)element).getName();
			}
		});
		this.availableRulesTableViewer.setInput(RuleExtensionManager.getInstance().getAllRules());
		this.availableRulesTableViewer.addDragSupport(dndOperations, transfers, new DragSourceListener() {

			public void dragFinished(DragSourceEvent event) {
				// do nothing
			}

			public void dragSetData(DragSourceEvent event) {
				//List<IRule> list = ((IStructuredSelection) availableRulesTableViewer.getSelection()).toList();
				IRule dragRule = (IRule) ((IStructuredSelection) RulePreferencePage.this.availableRulesTableViewer.getSelection()).getFirstElement();
				Rule rule = InfomngmntFactory.eINSTANCE.createRule();
				rule.setId(dragRule.getId());
				event.data = new StructuredSelection(new Object[] {rule});
			}

			public void dragStart(DragSourceEvent event) {
				// do nothing
			}

		});
		this.combo.select(0);
		hookContextMenu();
		sashForm.setWeights(new int[] {1, 1 });
		return composite;
	}

	protected void handleRuleSetSelectionChange(Event event) {
		this.selectedRuleSet = this.model.getNewElementRules().get(((Combo)event.widget).getSelectionIndex());
		this.ctx.dispose();
		this.checkboxTableViewer.setInput(this.selectedRuleSet);

	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
		this.model = RuleUtil.getInstance().getElementRules();
		this.ctx = new EMFDataBindingContext();

	}

	private void hookContextMenu() {
		this.cutAction = new RuleCutAction(this.editingDomain);
		this.checkboxTableViewer.addSelectionChangedListener(this.cutAction);
		this.copyAction = new RuleCopyAction(this.editingDomain);
		this.checkboxTableViewer.addSelectionChangedListener(this.copyAction);
		this.pasteAction = new PasteAction(this.editingDomain);
		this.checkboxTableViewer.addSelectionChangedListener(this.pasteAction);
		this.deleteAction = new RuleDeleteAction(this.editingDomain);
		this.checkboxTableViewer.addSelectionChangedListener(this.deleteAction);
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(this.checkboxTableViewer.getControl());
		this.checkboxTableViewer.getControl().setMenu(menu);
	}

	protected void fillContextMenu(IMenuManager manager) {
		manager.add(this.cutAction);
		manager.add(this.copyAction);
		manager.add(this.pasteAction);
		manager.add(new Separator());
		manager.add(this.deleteAction);

	}

	private static class RuleDeleteAction extends DeleteAction {
		public RuleDeleteAction(EditingDomain domain) {
			super(domain);
		}

		@Override
		public boolean updateSelection(IStructuredSelection selection) {
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
		public RuleCopyAction(EditingDomain domain) {
			super(domain);
		}

		@Override
		public boolean updateSelection(IStructuredSelection selection) {
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
		public RuleCutAction(EditingDomain domain) {
			super(domain);
		}

		@Override
		public boolean updateSelection(IStructuredSelection selection) {
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
		public EditRuleAction(EditingDomain domain) {
			super("Edit");
			this.domain = domain;
		}
		@Override
		public void run() {
			Object firstElement = getStructuredSelection().getFirstElement();
			if (firstElement instanceof Rule) {
				Rule element = (Rule) firstElement;
				IRule ruleById = RuleExtensionManager.getInstance().getRuleById(element.getId());
				AbstractRuleDefinition ruleDefinition = ruleById.getRuleDefinition();
				ruleDefinition.initialize(element);
			}
		}

		@Override
		protected boolean updateSelection(IStructuredSelection selection) {
			return !selection.isEmpty() && selection.size() == 1;
		}

	}



}
