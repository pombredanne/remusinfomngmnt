/*******************************************************************************
 * Copyright (c) 2010 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/

package org.remus.infomngmnt.common.ui.swt;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class DisplayKeyPoster {

	public static PostEvent ALT_TAB(final Display display) {
		return new PostEvent(display) {
			@Override
			protected void post() {
				this.display.asyncExec(new Runnable() {
					public void run() {
						Event event2 = new Event();
						event2.type = SWT.KeyDown;
						event2.keyCode = SWT.ALT;
						display.post(event2);
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
						}
						Event event3 = new Event();
						event3.type = SWT.KeyDown;
						event3.character = SWT.TAB;
						display.post(event3);
						event3.type = SWT.KeyUp;
						display.post(event3);
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
						}

						event2.type = SWT.KeyUp;
						display.post(event2);
					}
				});
			}
		};
	}

	public static PostEvent ESCAPE(final Display display) {
		return new PostEvent(display) {
			@Override
			protected void post() {
				this.display.asyncExec(new Runnable() {
					public void run() {
						Event event2 = new Event();
						event2.type = SWT.KeyDown;
						event2.keyCode = SWT.ESC;
						display.post(event2);
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
						}
						event2.type = SWT.KeyUp;
						display.post(event2);
					}
				});
			}
		};
	}

	public static PostEvent TAB(final Display display) {
		return new PostEvent(display) {
			@Override
			protected void post() {
				this.display.asyncExec(new Runnable() {
					public void run() {
						Event event2 = new Event();
						event2.type = SWT.KeyDown;
						event2.keyCode = SWT.TAB;
						display.post(event2);
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
						}
						event2.type = SWT.KeyUp;
						display.post(event2);
					}
				});
			}
		};
	}

	public static PostEvent ENTER(final Display display) {
		return new PostEvent(display) {
			@Override
			protected void post() {
				this.display.asyncExec(new Runnable() {
					public void run() {
						Event event2 = new Event();
						event2.type = SWT.KeyDown;
						event2.keyCode = SWT.CR;
						display.post(event2);
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
						}
						event2.type = SWT.KeyUp;
						display.post(event2);
					}
				});
			}
		};
	}

	public static PostEvent STRING(final Display display, final String string) {
		return new PostEvent(display) {

			@Override
			protected void post() {
				this.display.asyncExec(new Runnable() {
					public void run() {
						for (int i = 0; i < string.length(); i++) {
							char ch = string.charAt(i);
							boolean shift = Character.isUpperCase(ch);
							ch = Character.toLowerCase(ch);
							if (shift) {
								Event event = new Event();
								event.type = SWT.KeyDown;
								event.keyCode = SWT.SHIFT;
								display.post(event);
							}
							Event event = new Event();
							event.type = SWT.KeyDown;
							event.character = ch;
							display.post(event);
							try {
								Thread.sleep(10);
							} catch (InterruptedException e) {
							}
							event.type = SWT.KeyUp;
							display.post(event);
							try {
								Thread.sleep(30);
							} catch (InterruptedException e) {
							}
							if (shift) {
								event = new Event();
								event.type = SWT.KeyUp;
								event.keyCode = SWT.SHIFT;
								display.post(event);
							}
						}
					}
				});

			}
		};
	}

	public static void postEvents(final List<PostEvent> events) {
		for (PostEvent postEvent : events) {
			postEvent.post();
		}
	}
}
