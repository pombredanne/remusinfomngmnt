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

package org.remus.infomngmnt.common.ui.wizards;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class WizardValidatingUtil {

	public static void validateControlsOnModify(final IValidatingWizard wizard,
			final Control... text) {
		Listener listener = new Listener() {
			public void handleEvent(final Event event) {
				wizard.setPageComplete(wizard.validate(true));
			}
		};
		for (Control text2 : text) {
			if (text2 instanceof Text) {
				text2.addListener(SWT.Modify, listener);
			} else if (text2 instanceof Button) {
				text2.addListener(SWT.Selection, listener);
			}
		}
	}

}
