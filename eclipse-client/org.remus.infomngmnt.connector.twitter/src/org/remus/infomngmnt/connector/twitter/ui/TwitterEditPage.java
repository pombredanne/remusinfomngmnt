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
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.action.Action;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.events.MenuListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;
import org.eclipse.ui.internal.forms.Messages;

import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.RemoteRepository;
import org.remus.infomngmnt.SynchronizationState;
import org.remus.infomngmnt.connector.twitter.TwitterActivator;
import org.remus.infomngmnt.connector.twitter.infotype.TwitterUtil;
import org.remus.infomngmnt.connector.twitter.jobs.FollowUserJob;
import org.remus.infomngmnt.connector.twitter.jobs.UnFollowUserJob;
import org.remus.infomngmnt.connector.twitter.preferences.TwitterPreferenceInitializer;
import org.remus.infomngmnt.core.model.InformationUtil;
import org.remus.infomngmnt.core.services.IRepositoryService;
import org.remus.infomngmnt.provider.InfomngmntEditPlugin;
import org.remus.infomngmnt.ui.extension.AbstractInformationFormPage;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class TwitterEditPage extends AbstractInformationFormPage {

	private final List<MessageComposite> compositeCollection = new ArrayList<MessageComposite>();
	private Composite body;
	private FormToolkit toolkit;

	class MessageHyperLinkAdapter extends HyperlinkAdapter {
		private Menu userPopup;
		private Menu keyPopup;
		private Menu originalPopup;

		@Override
		public void linkActivated(final org.eclipse.ui.forms.events.HyperlinkEvent e) {
			String href = e.getHref().toString();
			if (href.startsWith(TwitterUtil.HREF_USER_PREFIX)) {
				this.userPopup.setVisible(true);
			} else if (href.startsWith(TwitterUtil.HREF_KEYWORD_PREFIX)) {
				this.keyPopup.setVisible(true);
			} else {
				Program.launch(href);
			}
		}

		@Override
		public void linkEntered(final HyperlinkEvent e) {
			this.originalPopup = ((Control) e.widget).getMenu();
			String href = e.getHref().toString();
			if (href.startsWith(TwitterUtil.HREF_USER_PREFIX)) {
				String userName = e.getHref().toString().split("\\.")[1];
				buildUserPopup(e, userName);
				((Control) e.widget).setMenu(this.userPopup);
			} else if (href.startsWith(TwitterUtil.HREF_KEYWORD_PREFIX)) {
				String keyWord = e.getHref().toString().split("\\.")[1];
				buildKeywordPopup(e, keyWord);
				((Control) e.widget).setMenu(this.keyPopup);
			}
		};

		@Override
		public void linkExited(final HyperlinkEvent e) {
			Menu menu = new Menu((Control) e.widget);
			buildCopy(menu, (Control) e.widget);
			((Control) e.widget).setMenu(menu);
		};

		private void buildKeywordPopup(final HyperlinkEvent e, final String keyWord) {
			this.keyPopup = new Menu((Control) e.widget);
			MenuItem item = new MenuItem(this.keyPopup, SWT.PUSH);
			item.setText("Open twitter seach");
			item.setEnabled(true);
			item.addListener(SWT.Selection, new Listener() {
				public void handleEvent(final Event event) {
					Program.launch(String.format(TwitterActivator.getDefault().getPreferenceStore()
							.getString(TwitterPreferenceInitializer.SEARCH_URL), keyWord));
				}
			});
			MenuItem item2 = new MenuItem(this.keyPopup, SWT.PUSH);
			item2.setText("Add a search-feed to repository");
			item2.setEnabled(onlineActionsAvailable()
					&& canAdd2Repository(getRepositoryId(), keyWord));
			item2.addListener(SWT.Selection, new Listener() {
				public void handleEvent(final Event event) {
					addKeyWordToRepository(getRepositoryId(), keyWord);
				}
			});

			buildCopy(this.keyPopup, (Control) e.widget);

		}

		private void buildUserPopup(final HyperlinkEvent e, final String userName) {
			// if (((Control) e.widget).getMenu() != null) {
			// ((Control) e.widget).getMenu().setVisible(true);
			// return;
			// }
			this.userPopup = new Menu((Control) e.widget);
			MenuItem item = new MenuItem(this.userPopup, SWT.PUSH);
			item.setText(NLS.bind("Follow {0}", userName));
			item.setEnabled(onlineActionsAvailable());
			item.addListener(SWT.Selection, new Listener() {
				public void handleEvent(final Event event) {
					new FollowUserJob(userName, getRepositoryId()).schedule();
				}
			});
			MenuItem item2 = new MenuItem(this.userPopup, SWT.PUSH);
			item2.setText(NLS.bind("Unfollow {0}", userName));
			item2.setEnabled(onlineActionsAvailable());
			item2.addListener(SWT.Selection, new Listener() {
				public void handleEvent(final Event event) {
					new UnFollowUserJob(userName, getRepositoryId()).schedule();
				}
			});
			MenuItem item3 = new MenuItem(this.userPopup, SWT.PUSH);
			item3.setText(NLS.bind("Send direct message", userName));
			item3.setEnabled(onlineActionsAvailable());
			item3.addListener(SWT.Selection, new Listener() {
				public void handleEvent(final Event event) {
					new UnFollowUserJob(userName, getRepositoryId()).schedule();
				}
			});
			buildCopy(this.userPopup, (Control) e.widget);
		};

		public void buildCopy(final Menu menu, final Control control) {
			if (menu.getItemCount() > 0) {
				new MenuItem(menu, SWT.SEPARATOR);
			}
			final MenuItem copyItem = new MenuItem(menu, SWT.PUSH);
			copyItem.setText(Messages.FormText_copy);

			SelectionListener listener = new SelectionAdapter() {
				@Override
				public void widgetSelected(final SelectionEvent e) {
					if (e.widget == copyItem) {
						((FormText) control).copy();
					}
				}
			};
			copyItem.addSelectionListener(listener);
			menu.addMenuListener(new MenuListener() {
				public void menuShown(final MenuEvent e) {
					copyItem.setEnabled(((FormText) control).canCopy());
				}

				public void menuHidden(final MenuEvent e) {
				}
			});

		}
	};

	@Override
	protected void renderPage(final IManagedForm managedForm) {
		this.toolkit = managedForm.getToolkit();
		ScrolledForm form = managedForm.getForm();
		this.body = form.getBody();
		this.body.setLayout(new TableWrapLayout());
		this.toolkit.paintBordersFor(this.body);

	}

	public boolean canAdd2Repository(final String repositoryId, final String keyWord) {
		RemoteRepository repositoryById = InfomngmntEditPlugin.getPlugin().getService(
				IRepositoryService.class).getRepositoryById(repositoryId);
		String string = repositoryById.getOptions().get(
				TwitterActivator.REPOSITORY_OPTIONS_SEARCH_KEY);
		List<String> asList = Arrays.asList(org.apache.commons.lang.StringUtils.split(string, "|"));
		return !asList.contains(keyWord);
	}

	private void addKeyWordToRepository(final String repositoryId, final String keyWord) {
		RemoteRepository repositoryById = InfomngmntEditPlugin.getPlugin().getService(
				IRepositoryService.class).getRepositoryById(repositoryId);
		String string = repositoryById.getOptions().get(
				TwitterActivator.REPOSITORY_OPTIONS_SEARCH_KEY);
		List<String> asList = new ArrayList<String>(Arrays
				.asList(org.apache.commons.lang.StringUtils.split(string, "|")));
		asList.add(keyWord);
		repositoryById.getOptions().put(TwitterActivator.REPOSITORY_OPTIONS_SEARCH_KEY,
				StringUtils.join(asList, "|"));

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
			messageComposite.setMessageFormHyperlinkListener(new MessageHyperLinkAdapter());
			messageComposite.setMetaFormHyperlinkListener(new MessageHyperLinkAdapter());
			addControl(messageComposite.getMessageText());
			addControl(messageComposite.getMetaFormText());
			messageComposite.addAction(new Action("Retweet") {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					super.run();
				}
			});
			this.toolkit.adapt(messageComposite);
			final TableWrapData twd_metaFormText = new TableWrapData(TableWrapData.FILL,
					TableWrapData.TOP);
			twd_metaFormText.grabHorizontal = true;
			messageComposite.setLayoutData(twd_metaFormText);
			this.compositeCollection.add(messageComposite);
		}
		this.body.layout();
	}

	boolean onlineActionsAvailable() {
		InformationUnitListItem adapter = (InformationUnitListItem) getModelObject().getAdapter(
				InformationUnitListItem.class);
		return adapter != null
				&& adapter.getSynchronizationMetaData() != null
				&& adapter.getSynchronizationMetaData().getSyncState() != SynchronizationState.NOT_ADDED
				&& adapter.getSynchronizationMetaData().getSyncState() != SynchronizationState.IGNORED;
	}

	String getRepositoryId() {
		InformationUnitListItem adapter = (InformationUnitListItem) getModelObject().getAdapter(
				InformationUnitListItem.class);
		return adapter.getSynchronizationMetaData().getRepositoryId();
	}
}
