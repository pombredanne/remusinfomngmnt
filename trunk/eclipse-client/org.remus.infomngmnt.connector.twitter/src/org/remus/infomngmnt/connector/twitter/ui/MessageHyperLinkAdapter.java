package org.remus.infomngmnt.connector.twitter.ui;

import org.eclipse.osgi.util.NLS;
import org.eclipse.remus.InformationUnit;
import org.eclipse.remus.core.remote.sync.SyncUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.events.MenuListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.internal.forms.Messages;

import org.remus.infomngmnt.connector.twitter.TwitterActivator;
import org.remus.infomngmnt.connector.twitter.infotype.TwitterUtil;
import org.remus.infomngmnt.connector.twitter.jobs.FollowUserJob;
import org.remus.infomngmnt.connector.twitter.jobs.UnFollowUserJob;
import org.remus.infomngmnt.connector.twitter.preferences.TwitterPreferenceInitializer;
import org.remus.infomngmnt.connector.twitter.ui.actions.OpenConversationAction;
import org.remus.infomngmnt.connector.twitter.ui.actions.SendDirectMessageAction;

public class MessageHyperLinkAdapter extends HyperlinkAdapter {
	private Menu userPopup;
	private Menu keyPopup;
	private Menu originalPopup;
	private final InformationUnit modelObject;

	public MessageHyperLinkAdapter(final InformationUnit unit) {
		this.modelObject = unit;
	}

	@Override
	public void linkActivated(final org.eclipse.ui.forms.events.HyperlinkEvent e) {
		String href = e.getHref().toString();
		if (href.startsWith(TwitterUtil.HREF_USER_PREFIX)
				|| href.startsWith(TwitterUtil.REPLY_USER_PREFIX)) {
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
			buildUserPopup(e, userName, null);
			((Control) e.widget).setMenu(this.userPopup);
		} else if (href.startsWith(TwitterUtil.HREF_KEYWORD_PREFIX)) {
			String keyWord = e.getHref().toString().split("\\.")[1];
			buildKeywordPopup(e, keyWord);
			((Control) e.widget).setMenu(this.keyPopup);
		} else if (href.startsWith(TwitterUtil.REPLY_USER_PREFIX)) {
			String userName = e.getHref().toString().split("\\.")[2];
			String replyId = e.getHref().toString().split("\\.")[1];
			buildUserPopup(e, userName, replyId);
			((Control) e.widget).setMenu(this.userPopup);
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
		item2.setEnabled(TwitterUtil.onlineActionsAvailable(this.modelObject)
				&& TwitterUtil.canAdd2Repository(SyncUtil.getRepositoryId(this.modelObject),
						keyWord));
		item2.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				TwitterUtil.addKeyWordToRepository(SyncUtil
						.getRepositoryId(MessageHyperLinkAdapter.this.modelObject), keyWord);
			}
		});

		buildCopy(this.keyPopup, (Control) e.widget);

	}

	private void buildUserPopup(final HyperlinkEvent e, final String userName, final String replyId) {
		// if (((Control) e.widget).getMenu() != null) {
		// ((Control) e.widget).getMenu().setVisible(true);
		// return;
		// }
		this.userPopup = new Menu((Control) e.widget);

		if (replyId != null) {
			MenuItem item4 = new MenuItem(this.userPopup, SWT.PUSH);
			item4.setText("View conversation");
			item4.setEnabled(TwitterUtil.onlineActionsAvailable(this.modelObject));
			item4.addListener(SWT.Selection, new Listener() {
				public void handleEvent(final Event event) {
					OpenConversationAction action = new OpenConversationAction(userName, replyId,
							MessageHyperLinkAdapter.this.modelObject);
					action.run();
				}
			});
			MenuItem item5 = new MenuItem(this.userPopup, SWT.SEPARATOR);
		}

		MenuItem item = new MenuItem(this.userPopup, SWT.PUSH);
		item.setText(NLS.bind("Follow {0}", userName));
		item.setEnabled(TwitterUtil.onlineActionsAvailable(this.modelObject));
		item.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				new FollowUserJob(userName, SyncUtil
						.getRepositoryId(MessageHyperLinkAdapter.this.modelObject)).schedule();
			}
		});
		MenuItem item2 = new MenuItem(this.userPopup, SWT.PUSH);
		item2.setText(NLS.bind("Unfollow {0}", userName));
		item2.setEnabled(TwitterUtil.onlineActionsAvailable(this.modelObject));
		item2.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				new UnFollowUserJob(userName, SyncUtil
						.getRepositoryId(MessageHyperLinkAdapter.this.modelObject)).schedule();
			}
		});
		MenuItem item3 = new MenuItem(this.userPopup, SWT.PUSH);
		item3.setText(NLS.bind("Send direct message", userName));
		item3.setEnabled(TwitterUtil.onlineActionsAvailable(this.modelObject));
		item3.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				SendDirectMessageAction action = new SendDirectMessageAction(userName, SyncUtil
						.getRepositoryId(MessageHyperLinkAdapter.this.modelObject));
				action.run();
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