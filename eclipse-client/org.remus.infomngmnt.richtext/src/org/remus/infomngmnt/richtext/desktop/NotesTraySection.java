/*******************************************************************************
 * Copyright (c) 2011 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/
package org.remus.infomngmnt.richtext.desktop;

import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.remus.ui.desktop.extension.AbstractTraySection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class NotesTraySection extends AbstractTraySection {

	private Browser htmlComposer;
	private ToolBarManager toolBarManager;

	/**
	 * 
	 */
	public NotesTraySection() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.remus.ui.desktop.extension.AbstractTraySection#createDetailsPart
	 * (org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createDetailsPart(Composite parent) {
		GridLayout gridLayout = new GridLayout(1, false);
		gridLayout.marginTop = 0;
		gridLayout.marginWidth = 0;
		parent.setLayout(gridLayout);

		Composite composite = toolkit.createComposite(parent, SWT.NONE
				| SWT.BORDER);
		composite.setLayout(gridLayout);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		// int dropOperations = DND.DROP_DEFAULT | DND.DROP_MOVE ;

		ToolBar tb = new ToolBar(composite, SWT.HORIZONTAL | SWT.FLAT);
		GridData gridData2 = new GridData(SWT.CENTER, SWT.FILL, true, false);
		gridData2.widthHint = SWT.DEFAULT;
		gridData2.heightHint = SWT.DEFAULT;
		tb.setLayoutData(gridData2);
		toolBarManager = new ToolBarManager(tb);

		htmlComposer = new Browser(composite, SWT.NONE);
		htmlComposer.setUrl("http://www.tinymce.com/tryit/full.php");
		htmlComposer.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				super.focusLost(e);
			}
		});
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData.widthHint = SWT.DEFAULT;
		gridData.heightHint = 500;
		htmlComposer.setLayoutData(gridData);

		// toolBarManager.add(new BoldAction(htmlComposer));
		// toolBarManager.add(new ItalicAction(htmlComposer));
		// toolBarManager.add(new UnderlineAction(htmlComposer));
		// toolBarManager.update(true);
		//
		// Button button = new Button(composite, SWT.PUSH);
		// button.addListener(SWT.Selection, new Listener() {
		//
		// public void handleEvent(Event event) {
		// BoldCommand boldCommand = new BoldCommand();
		// boldCommand.setComposer(htmlComposer);
		// boldCommand.execute();
		//
		// }
		//
		// });
		// button.setLayoutData(gridData2);

	}

}
