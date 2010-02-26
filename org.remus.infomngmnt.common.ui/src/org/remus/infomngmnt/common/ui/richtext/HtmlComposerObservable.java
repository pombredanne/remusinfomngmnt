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

package org.remus.infomngmnt.common.ui.richtext;

import java.util.Properties;

import org.eclipse.core.databinding.observable.value.AbstractObservableValue;
import org.eclipse.core.databinding.observable.value.ValueDiff;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import de.spiritlink.richhtml4eclipse.widgets.EventConstants;
import de.spiritlink.richhtml4eclipse.widgets.HtmlComposer;
import de.spiritlink.richhtml4eclipse.widgets.JavaScriptCommands;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class HtmlComposerObservable extends AbstractObservableValue {

	private final HtmlComposer htmlComposer;

	public static final int EVENT_ID_MODIFY = 62;

	private String newValue;
	private String oldValue;

	private final Listener modifyListener = new Listener() {
		public void handleEvent(final Event event) {
			final Properties props = (Properties) event.data;
			if (event.type != EventConstants.ALL) {

				// final String newValue =
				// HtmlComposerObservable.this.htmlComposer.getHtml();
				// At this point the new value is the old.
				// This is necessary because the widget cannot handle
				// its old values out-of-the box.
				// HtmlComposerObservable.this.cachedOldValue = newValue;
				if (props.getProperty("key") != null) {
					Display.getDefault().asyncExec(new Runnable() {
						public void run() {
							HtmlComposerObservable.this.newValue = HtmlComposerObservable.this.htmlComposer
									.getHtml();
							fireValueChange(new ValueDiff() {

								@Override
								public Object getNewValue() {
									return HtmlComposerObservable.this.newValue;
								}

								@Override
								public Object getOldValue() {
									return HtmlComposerObservable.this.oldValue;
								}

							});
							HtmlComposerObservable.this.oldValue = HtmlComposerObservable.this.newValue;
						}

					});
				}
			}
		}

	};

	public HtmlComposerObservable(final HtmlComposer composer) {
		this.htmlComposer = composer;
		this.htmlComposer.addListener(EVENT_ID_MODIFY, this.modifyListener);

	}

	@Override
	protected Object doGetValue() {
		return this.htmlComposer.getHtml();
	}

	@Override
	protected void doSetValue(final Object value) {
		this.htmlComposer.execute(JavaScriptCommands.SET_HTML(value.toString()));
	}

	public Object getValueType() {
		return String.class;
	}

	@Override
	public synchronized void dispose() {
		this.htmlComposer.removeListener(EVENT_ID_MODIFY, this.modifyListener);
		super.dispose();
	}

}
