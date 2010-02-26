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

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.forms.widgets.FormText;
import org.remus.infomngmnt.connector.twitter.TwitterActivator;
import org.remus.infomngmnt.connector.twitter.preferences.TwitterPreferenceInitializer;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class TwitterPreferencePage2 extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {

	/**
	 * 
	 */
	public TwitterPreferencePage2() {
		super();
	}

	private Group createGroup;

	@Override
	protected IPreferenceStore doGetPreferenceStore() {
		return TwitterActivator.getDefault().getPreferenceStore();
	}

	/**
	 * Initialize the preference page
	 */
	public void init(final IWorkbench workbench) {

	}

	@Override
	protected Control createContents(final Composite parent) {
		Composite fieldEditorParent = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		fieldEditorParent.setLayout(layout);
		fieldEditorParent.setFont(parent.getFont());

		this.createGroup = new Group(fieldEditorParent, SWT.NONE);
		this.createGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		this.createGroup.setText("Update intervalls (in minutes)");

		FormText formText = new FormText(fieldEditorParent, SWT.WRAP);
		formText
				.setText(
						"Setting to high refresh intervalls will bann you from twitter. You can use 100 calls in one hour, but remember that every refresh, every tweet every follow will need at least 1 api call.",
						false, false);
		GridData layoutData = new GridData(SWT.FILL, SWT.FILL, false, false);
		layoutData.widthHint = 150;
		formText.setLayoutData(layoutData);

		createFieldEditors();

		// adjustGridLayout();
		initialize();
		checkState();
		GridLayout gridLayout = new GridLayout(2, false);
		this.createGroup.setLayout(gridLayout);

		return fieldEditorParent;
	}

	@Override
	protected void createFieldEditors() {
		IntegerFieldEditor friendsFeed = new IntegerFieldEditor(
				TwitterPreferenceInitializer.RELOAD_ALL_FRIENDS_FEED, "Friends feed",
				this.createGroup);
		friendsFeed.fillIntoGrid(this.createGroup, 2);
		IntegerFieldEditor repliesFeed = new IntegerFieldEditor(
				TwitterPreferenceInitializer.RELOAD_REPLIES_FEED, "Replies feed", this.createGroup);
		repliesFeed.fillIntoGrid(this.createGroup, 2);
		IntegerFieldEditor directMessagesFeed = new IntegerFieldEditor(
				TwitterPreferenceInitializer.RELOAD_DIRECT_MESSAGES_FEED, "Direct messages feed",
				this.createGroup);
		directMessagesFeed.fillIntoGrid(this.createGroup, 2);
		IntegerFieldEditor searchFeeds = new IntegerFieldEditor(
				TwitterPreferenceInitializer.RELOAD_SEARCH_FEEDS, "Search feeds", this.createGroup);
		searchFeeds.fillIntoGrid(this.createGroup, 2);
		IntegerFieldEditor shownMessage = new IntegerFieldEditor(
				TwitterPreferenceInitializer.SHOWN_MESSAGE, "Visible Messages in Editor",
				this.createGroup);
		shownMessage.fillIntoGrid(this.createGroup, 2);
		IntegerFieldEditor savedMessage = new IntegerFieldEditor(
				TwitterPreferenceInitializer.SAVED_MESSAGES, "Saved Messages in Editor",
				this.createGroup);
		savedMessage.fillIntoGrid(this.createGroup, 2);
		addField(friendsFeed);
		addField(repliesFeed);
		addField(directMessagesFeed);
		addField(searchFeeds);
		addField(shownMessage);
		addField(savedMessage);

	}

}
