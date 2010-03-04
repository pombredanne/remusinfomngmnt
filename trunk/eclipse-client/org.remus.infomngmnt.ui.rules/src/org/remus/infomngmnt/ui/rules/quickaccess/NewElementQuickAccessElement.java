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

package org.remus.infomngmnt.ui.rules.quickaccess;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.resource.ImageDescriptor;

import org.remus.infomngmnt.common.ui.UIUtil;
import org.remus.infomngmnt.common.ui.quickaccess.QuickAccessElement;
import org.remus.infomngmnt.common.ui.quickaccess.QuickAccessProvider;
import org.remus.infomngmnt.core.extension.IInfoType;
import org.remus.infomngmnt.core.services.IInformationTypeHandler;
import org.remus.infomngmnt.ui.infotypes.service.IInformationTypeImage;
import org.remus.infomngmnt.ui.rules.processing.PostProcessingResult;
import org.remus.infomngmnt.ui.rules.processing.RuleProcessor;
import org.remus.infomngmnt.ui.rules.service.ICreationTrigger;
import org.remus.infomngmnt.ui.rules.service.IRuleExtensionService;
import org.remus.infomngmnt.ui.rules.transfer.TransferWrapper;
import org.remus.rules.RuleAction;
import org.remus.rules.provider.RuleEditPlugin;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class NewElementQuickAccessElement extends QuickAccessElement {

	private final RuleAction action;
	private final IInfoType infoTypeByType;
	private final Object value;
	private final TransferWrapper transferType;
	private final IInformationTypeImage imageService;
	private final IInformationTypeHandler infotypeService;
	private final IRuleExtensionService ruleService;

	public NewElementQuickAccessElement(final QuickAccessProvider provider,
			final RuleAction action, final Object value, final TransferWrapper transferType) {
		super(provider);
		this.action = action;
		this.value = value;
		this.transferType = transferType;
		this.imageService = RuleEditPlugin.getPlugin().getServiceTracker().getService(
				IInformationTypeImage.class);
		this.infotypeService = RuleEditPlugin.getPlugin().getServiceTracker().getService(
				IInformationTypeHandler.class);
		this.ruleService = RuleEditPlugin.getPlugin().getServiceTracker().getService(
				IRuleExtensionService.class);
		this.infoTypeByType = this.infotypeService.getInfoTypeByType(action.getInfoTypeId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.common.ui.quickaccess.QuickAccessElement#execute()
	 */
	@Override
	public void execute() {
		final ICreationTrigger trigger = this.ruleService
				.getCreationTriggerByTypeId(this.infoTypeByType.getType());
		if (trigger != null) {
			Job job = new Job("Executing rule") {

				@Override
				protected IStatus run(final IProgressMonitor monitor) {
					PostProcessingResult postProcessing = RuleProcessor.getInstance()
							.postProcessing(NewElementQuickAccessElement.this.value,
									NewElementQuickAccessElement.this.action);
					trigger.setValue(NewElementQuickAccessElement.this.value);
					trigger.setNewInformationUnit(postProcessing.getInformationUnit());
					trigger.setRuleValue(NewElementQuickAccessElement.this.action.getRuleValue());
					trigger.setTransferType(NewElementQuickAccessElement.this.transferType);
					trigger.setOptions(postProcessing.getOptions());
					UIUtil.getDisplay().asyncExec(new Runnable() {
						public void run() {
							trigger.handleCreationRequest();
						}
					});
					return Status.OK_STATUS;
				}
			};
			job.setUser(true);
			job.schedule();

		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.common.ui.quickaccess.QuickAccessElement#getId()
	 */
	@Override
	public String getId() {
		return this.action.getInfoTypeId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.remus.infomngmnt.common.ui.quickaccess.QuickAccessElement#
	 * getImageDescriptor()
	 */
	@Override
	public ImageDescriptor getImageDescriptor() {
		return this.imageService.getImageDescriptorByInfoType(this.infoTypeByType.getType());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.common.ui.quickaccess.QuickAccessElement#getLabel()
	 */
	@Override
	public String getLabel() {
		String name = this.action.getName();
		if (name == null || name.length() == 0) {
			name = this.infotypeService.getInfoTypeByType(this.action.getInfoTypeId()).getName();
		}
		return name;
	}

}
