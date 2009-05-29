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

package org.remus.infomngmnt.ui.dnd;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.DragAndDropCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.ui.dnd.EditingDomainViewerDropAdapter;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTargetEvent;

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.NewElementRules;
import org.remus.infomngmnt.RuleAction;
import org.remus.infomngmnt.RuleResult;
import org.remus.infomngmnt.RuleValue;
import org.remus.infomngmnt.common.ui.UIUtil;
import org.remus.infomngmnt.common.ui.quickaccess.QuickAccessDialog;
import org.remus.infomngmnt.common.ui.quickaccess.QuickAccessProvider;
import org.remus.infomngmnt.core.model.CategoryUtil;
import org.remus.infomngmnt.core.model.InformationUtil;
import org.remus.infomngmnt.core.rules.RuleProcessor;
import org.remus.infomngmnt.core.services.IRuleService;
import org.remus.infomngmnt.provider.InfomngmntEditPlugin;
import org.remus.infomngmnt.ui.desktop.NewElementQuickAccessProider;
import org.remus.infomngmnt.ui.extension.AbstractCreationPreferencePage;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class NavigationDropAdapter extends EditingDomainViewerDropAdapter {

	private NewElementRules ruleByName;

	public NavigationDropAdapter(final EditingDomain domain, final Viewer viewer) {
		super(domain, viewer);
	}

	@Override
	public void drop(final DropTargetEvent event) {
		Collection<?> extractDragSource = extractDragSource(event.data);
		Object target = extractDropTarget(event.item);
		if (extractDragSource.size() == 0) {
			this.ruleByName = (NewElementRules) EcoreUtil.copy(InfomngmntEditPlugin.getPlugin()
					.getService(IRuleService.class).getRuleByName("Default Ruleset"));
			List<RuleResult> process = RuleProcessor.getInstance().process(event, this.ruleByName);
			if (process.size() == 0) {
				MessageDialog.openInformation(this.viewer.getControl().getShell(), "drop",
						"Cannot drop");
			} else {
				// At first we have to extract the target-category
				Category targetCategory = null;
				if (target instanceof InformationUnitListItem) {
					targetCategory = (Category) ((EObject) target).eContainer();
				} else {
					targetCategory = (Category) target;
				}
				String categoryString = CategoryUtil.categoryToString(targetCategory);
				/*
				 * We have temporarily create some Rule-actions with the
				 * predefined category so that the creation trigger of the new
				 * info-type can access the drop target category.
				 */
				for (RuleResult ruleResult : process) {
					EList<RuleAction> actions = ruleResult.getActions();
					for (RuleAction ruleAction : actions) {
						if (ruleAction.getRuleValue() == null) {
							ruleAction.setRuleValue(InfomngmntFactory.eINSTANCE.createRuleValue());
						}
						RuleValue predefCategory = (RuleValue) InformationUtil.getChildByType(
								ruleAction.getRuleValue(),
								AbstractCreationPreferencePage.NODENAME_PREDEFINED_CATEGORY);
						if (predefCategory == null) {
							predefCategory = InfomngmntFactory.eINSTANCE.createRuleValue();
							predefCategory
									.setType(AbstractCreationPreferencePage.NODENAME_PREDEFINED_CATEGORY);
							ruleAction.getRuleValue().getChildValues().add(predefCategory);
						}
						predefCategory.setStringValue(categoryString);
					}
				}
				showQuickAccess(process);
			}
		} else {
			// A command was created if the source was available early, and the
			// information used to create it was cached...
			//
			if (this.dragAndDropCommandInformation != null) {
				// Recreate the command.
				//
				this.command = this.dragAndDropCommandInformation.createCommand();
			} else {
				// Otherwise, the source should be available now as event.data,
				// and we
				// can create the command.
				//
				this.source = extractDragSource(event.data);
				this.command = DragAndDropCommand.create(this.domain, target, getLocation(event),
						event.operations, this.originalOperation, this.source);
			}

			// If the command can execute...
			//
			if (this.command.canExecute()) {

				/*
				 * Before we execute the command we have to check if src &
				 * target are in the same project. If not, we also have to move
				 * files within the workspace.
				 */
				this.command = NavigationDropHelper.checkProjectRelevance(this.source, target,
						this.command, (this.originalOperation) == DND.DROP_COPY);

				/*
				 * The next step is to check the integration of the
				 * synchronization state.
				 */
				this.command = NavigationDropHelper.checkSyncStates(this.source, target,
						this.command);

				// Execute it.
				//
				this.domain.getCommandStack().execute(this.command);
			} else {
				// Otherwise, let's call the whole thing off.
				//
				event.detail = DND.DROP_NONE;
				this.command.dispose();
			}

			// Clean up the state.
			//
			this.command = null;
			this.commandTarget = null;
			this.source = null;
		}
	}

	void showQuickAccess(final List<RuleResult> process) {
		if (process.size() > 0) {
			QuickAccessProvider[] provider = new NewElementQuickAccessProider[process.size()];
			for (int i = 0, n = process.size(); i < n; i++) {
				provider[i] = new NewElementQuickAccessProider(process.get(i));
			}
			new QuickAccessDialog(UIUtil.getPrimaryWindow(), null, provider).open();
		} else {
			// nothing
		}

	}

	@Override
	public void dragOver(final DropTargetEvent event) {
		Collection<?> dragSource = getDragSource(event);
		Object target = extractDropTarget(event.item);
		if (dragSource != null && dragSource.size() > 0) {
			if (NavigationDropHelper.canDrop(this.source, target)) {
				super.dragOver(event);
			} else {
				event.detail = DND.DROP_NONE;
			}
		}

	}

}
