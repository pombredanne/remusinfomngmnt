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

package org.remus.infomngmnt.connector.twitter.desktop;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.EList;
import org.eclipse.remus.InformationUnit;
import org.eclipse.remus.InformationUnitListItem;
import org.eclipse.remus.common.ui.UIUtil;
import org.eclipse.remus.common.ui.image.ResourceManager;
import org.eclipse.remus.core.services.IApplicationModel;
import org.eclipse.remus.resources.util.ResourceUtil;
import org.eclipse.remus.services.RemusServiceTracker;
import org.eclipse.remus.ui.desktop.extension.AbstractTraySection;
import org.eclipse.remus.uimodel.TraySection;
import org.eclipse.remus.util.InformationUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

import org.remus.infomngmnt.connector.twitter.TwitterActivator;
import org.remus.infomngmnt.connector.twitter.infotype.TwitterUtil;
import org.remus.infomngmnt.connector.twitter.ui.MessageComposite;
import org.remus.infomngmnt.connector.twitter.ui.MessageHyperLinkAdapter;
import org.remus.infomngmnt.connector.twitter.ui.actions.TweetAction;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class TwitterTraySection extends AbstractTraySection {

	private int messages = 1;
	private int width = TwitterSectionPreferences.WIDTH_DEFAULT;

	private InformationUnitListItem itemById;

	private Composite parent;
	private final List<MessageComposite> compositeCollection = new ArrayList<MessageComposite>();
	private IResourceChangeListener listener;

	@Override
	public void init(final FormToolkit pToolkit, final TraySection section) {
		super.init(pToolkit, section);
		RemusServiceTracker remusServiceTracker = new RemusServiceTracker(Platform
				.getBundle(TwitterActivator.PLUGIN_ID));
		IApplicationModel service = remusServiceTracker.getService(IApplicationModel.class);

		try {
			this.messages = Integer.valueOf(section.getPreferenceOptions().get(
					TwitterSectionPreferences.PREFERENCE_MESSAGE_COUNT));
		} catch (NumberFormatException e) {
			// do nothing. not a valid value
		}
		try {
			this.width = Integer.valueOf(section.getPreferenceOptions().get(
					TwitterSectionPreferences.PREFERENCE_WIDTH));
		} catch (NumberFormatException e) {
			// do nothing. not a valid value
		}
		String string = section.getPreferenceOptions().get(
				TwitterSectionPreferences.PREFERENCE_INFOUNIT_ID);
		this.itemById = service.getItemById(string, null);
		remusServiceTracker.ungetService(service);
	}

	@Override
	public void createDetailsPart(final Composite parent) {
		this.parent = parent;
		TableWrapLayout layout = new TableWrapLayout();

		parent.setLayout(layout);

		if (this.itemById != null && TwitterUtil.onlineActionsAvailable(this.itemById)) {
			final ToolBar toolBar = new ToolBar(parent, SWT.RIGHT);
			this.toolkit.adapt(toolBar, true, true);
			final TableWrapData twd_toolBar = new TableWrapData(TableWrapData.CENTER,
					TableWrapData.TOP);

			toolBar.setLayoutData(twd_toolBar);
			final TweetAction tweetAction = new TweetAction(this.itemById
					.getSynchronizationMetaData().getRepositoryId());
			ToolItem toolItem = new ToolItem(toolBar, SWT.PUSH);
			toolItem.setText(tweetAction.getText());
			toolItem.setImage(ResourceManager.getPluginImage(TwitterActivator.getDefault(),
					"icons/twitter.png"));
			toolItem.addListener(SWT.Selection, new Listener() {
				public void handleEvent(final Event event) {
					tweetAction.run();
				}
			});
		}
		if (this.itemById != null) {
			this.listener = new IResourceChangeListener() {
				public void resourceChanged(final IResourceChangeEvent event) {
					if (ResourceUtil.isPathInResourceDelta(event.getDelta(), new Path(
							TwitterTraySection.this.itemById.getWorkspacePath()))) {
						UIUtil.getDisplay().asyncExec(new Runnable() {
							public void run() {
								buildMessageList();
							}
						});
					}

				}
			};
			ResourcesPlugin.getWorkspace().addResourceChangeListener(this.listener);
		}
		if (this.itemById != null) {
			buildMessageList();
		} else {
			buildEmptyContents();
		}
		parent.getParent().layout();

	}

	private void buildEmptyContents() {

	}

	private void buildMessageList() {
		for (MessageComposite composite : this.compositeCollection) {
			composite.removeMessageFormHyperlinkListener();
			composite.removeMetaFormHyperlinkListener();
			composite.dispose();
		}
		this.compositeCollection.clear();
		InformationUnit adapter = (InformationUnit) this.itemById.getAdapter(InformationUnit.class);
		InformationUnit childByType = InformationUtil.getChildByType(adapter,
				TwitterActivator.MESSAGES_ID);
		EList<InformationUnit> childValues = childByType.getChildValues();
		for (int i = 0, n = this.messages; i < n; i++) {
			InformationUnit informationUnit = childValues.get(i);
			System.out.println("PAINT");
			MessageComposite messageComposite = new MessageComposite(this.parent, SWT.NONE,
					this.toolkit, false, false, false);
			messageComposite.setValues(childValues.get(i));
			messageComposite.setMessageFormHyperlinkListener(new MessageHyperLinkAdapter(
					informationUnit));
			messageComposite.setMetaFormHyperlinkListener(new MessageHyperLinkAdapter(
					informationUnit));
			this.compositeCollection.add(messageComposite);
			// needed information for the actions.
			String userId = InformationUtil.getChildByType(informationUnit,
					TwitterActivator.MESSAGE_USER_ID_TYPE).getStringValue();
			String message = InformationUtil.getChildByType(informationUnit,
					TwitterActivator.MESSAGE_CONTENT_TYPE).getStringValue();
			Long internalId = InformationUtil.getChildByType(informationUnit,
					TwitterActivator.MESSAGE_INTERNAL_ID).getLongValue();

			this.toolkit.adapt(messageComposite);
			final TableWrapData twd_metaFormText = new TableWrapData(TableWrapData.FILL,
					TableWrapData.TOP);
			twd_metaFormText.grabHorizontal = true;
			twd_metaFormText.maxWidth = this.width;
			messageComposite.setLayoutData(twd_metaFormText);
			// this.compositeCollection.add(messageComposite);

		}
		this.parent.layout(true);

	}

	@Override
	public void dispose() {
		if (this.itemById != null) {
			// InformationUnit adapter = (InformationUnit) this.itemById
			// .getAdapter(InformationUnit.class);
			// if (adapter != null) {
			// adapter.eAdapters().remove(this.refreshAdapter);
			// }
			ResourcesPlugin.getWorkspace().removeResourceChangeListener(this.listener);
		}
		super.dispose();
	}

}
