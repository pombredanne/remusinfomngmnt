/*******************************************************************************
 * Copyright (c) 2009 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/

package org.remus.infomngmnt.connector.twitter.ui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.connector.twitter.TwitterActivator;
import org.remus.infomngmnt.connector.twitter.infotype.TwitterUtil;
import org.remus.infomngmnt.connector.twitter.ui.actions.Add2FavoriteActions;
import org.remus.infomngmnt.connector.twitter.ui.actions.ReplyAction;
import org.remus.infomngmnt.connector.twitter.ui.actions.RetweetAction;
import org.remus.infomngmnt.core.model.InformationUtil;
import org.remus.infomngmnt.core.sync.SyncUtil;
import org.remus.infomngmnt.ui.extension.AbstractInformationFormPage;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class TwitterEditPage extends AbstractInformationFormPage {

	private final List<MessageComposite> compositeCollection = new ArrayList<MessageComposite>();
	private Composite body;
	private FormToolkit toolkit;

	@Override
	protected void renderPage(final IManagedForm managedForm) {
		this.toolkit = managedForm.getToolkit();
		ScrolledForm form = managedForm.getForm();
		this.body = form.getBody();
		this.body.setLayout(new TableWrapLayout());
		this.toolkit.paintBordersFor(this.body);

	}

	@Override
	public void bindValuesToUi() {
		for (MessageComposite composite : this.compositeCollection) {
			composite.removeMessageFormHyperlinkListener();
			composite.removeMetaFormHyperlinkListener();
			removeControl(composite.getMessageText());
			removeControl(composite.getMetaFormText());
			composite.dispose();
		}
		this.compositeCollection.clear();
		InformationUnit childByType = InformationUtil.getChildByType(getModelObject(),
				TwitterActivator.MESSAGES_ID);
		EList<InformationUnit> childValues = childByType.getChildValues();
		for (InformationUnit informationUnit : childValues) {
			MessageComposite messageComposite = new MessageComposite(this.body, SWT.NONE,
					this.toolkit);
			messageComposite.setValues(informationUnit);
			messageComposite.setMessageFormHyperlinkListener(new MessageHyperLinkAdapter(
					getModelObject()));
			messageComposite.setMetaFormHyperlinkListener(new MessageHyperLinkAdapter(
					getModelObject()));
			addControl(messageComposite.getMessageText());
			addControl(messageComposite.getMetaFormText());

			// needed information for the actions.
			String userId = InformationUtil.getChildByType(informationUnit,
					TwitterActivator.MESSAGE_USER_ID_TYPE).getStringValue();
			String message = InformationUtil.getChildByType(informationUnit,
					TwitterActivator.MESSAGE_CONTENT_TYPE).getStringValue();
			Long internalId = InformationUtil.getChildByType(informationUnit,
					TwitterActivator.MESSAGE_INTERNAL_ID).getLongValue();
			boolean onlineActionsAvailable = TwitterUtil.onlineActionsAvailable(getModelObject());

			// build actions
			ReplyAction action = new ReplyAction(userId,
					SyncUtil.getRepositoryId(getModelObject()), internalId);
			action.setEnabled(onlineActionsAvailable);
			messageComposite.addAction(action);
			RetweetAction retweetAction = new RetweetAction(userId, message, SyncUtil
					.getRepositoryId(getModelObject()));
			retweetAction.setEnabled(onlineActionsAvailable);
			messageComposite.addAction(retweetAction);
			Add2FavoriteActions favAction = new Add2FavoriteActions(internalId, SyncUtil
					.getRepositoryId(getModelObject()));
			favAction.setEnabled(onlineActionsAvailable);
			messageComposite.addAction(favAction);

			this.toolkit.adapt(messageComposite);
			final TableWrapData twd_metaFormText = new TableWrapData(TableWrapData.FILL,
					TableWrapData.TOP);
			twd_metaFormText.grabHorizontal = true;
			messageComposite.setLayoutData(twd_metaFormText);
			this.compositeCollection.add(messageComposite);
		}
		this.body.layout();
	}

}
