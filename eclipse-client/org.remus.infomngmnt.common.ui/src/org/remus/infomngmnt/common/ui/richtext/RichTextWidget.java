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

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.CoolItem;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.ui.forms.widgets.FormToolkit;

import de.spiritlink.richhtml4eclipse.widgets.HtmlComposer;
import de.spiritlink.richhtml4eclipse.widgets.JavaScriptCommands;

import org.remus.infomngmnt.common.ui.richtext.actions.SelectFormatAction;

/**
 * <p>
 * A Composite which has the ability to edit html. It wraps an instance of the
 * {@link HtmlComposer} and a {@link CoolBar} with the edit-actions.
 * </p>
 * <p>
 * It's always recommended to use this widget due to the ability of Databinding.
 * </p>
 * 
 * @author Tom Seidel <tom.seidel@remus-software.org>
 * @noextend This class is not intended to be subclassed by clients.
 */
public class RichTextWidget extends Composite {

	private HtmlComposer composer;

	/**
	 * @return the composer
	 */
	public HtmlComposer getComposer() {
		return this.composer;
	}

	private CoolBar coolBar;
	private final boolean createComobs;
	private Combo formatCombo;
	private final FormToolkit toolkit;
	private Combo fontCombo;
	private final int style2;
	private Combo fontsizeCombo;

	/**
	 * @param parent
	 * @param style
	 * @param createComobs
	 */
	public RichTextWidget(final Composite parent, final int style, final boolean createComobs,
			final FormToolkit toolkit) {
		super(parent, SWT.NONE);
		this.style2 = style;
		this.createComobs = createComobs;
		this.toolkit = toolkit;

		init();
		if (this.createComobs) {
			initCombos();
		}

	}

	private void init() {
		setLayout(new GridLayout(1, false));
		this.coolBar = new CoolBar(this, SWT.NONE);
		this.toolkit.adapt(this.coolBar);
		this.composer = new HtmlComposer(this, this.style2);
		this.toolkit.adapt(this.composer.getBrowser());
		GridData gd = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		gd.widthHint = 100;
		this.coolBar.setLayoutData(gd);
		this.coolBar.addListener(SWT.Resize, new Listener() {
			public void handleEvent(final Event event) {
				RichTextWidget.this.getShell().layout();
			}
		});
		this.composer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

	}

	private void initCombos() {
		final Composite comboComp = this.toolkit.createComposite(this.coolBar, SWT.NONE);
		comboComp.setLayout(new GridLayout(3, false));

		this.formatCombo = new Combo(comboComp, SWT.SINGLE);

		this.formatCombo
				.setItems(SelectFormatAction.formatMappings.values().toArray(new String[0]));
		this.formatCombo.add("--[Format]--", 0);
		this.formatCombo.select(0);
		this.toolkit.adapt(this.formatCombo);

		Point formatTextSize = this.formatCombo.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		formatTextSize = this.formatCombo.computeSize(formatTextSize.x, formatTextSize.y);

		this.fontCombo = new Combo(comboComp, SWT.SINGLE);

		this.fontCombo.setItems(getFontList());
		this.fontCombo.add("--[Font]--", 0);
		this.fontCombo.select(0);
		this.toolkit.adapt(this.fontCombo);

		Point fontTextSize = this.fontCombo.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		fontTextSize = this.fontCombo.computeSize(fontTextSize.x, fontTextSize.y);

		this.fontsizeCombo = new Combo(comboComp, SWT.SINGLE);
		this.fontsizeCombo.setItems(new String[] { "--[Size]--", "1", "2", "3", "4", "5", "6" });
		this.fontsizeCombo.select(0);
		this.toolkit.adapt(this.fontsizeCombo);

		CoolItem comboItem = new CoolItem(this.coolBar, SWT.NONE);
		comboItem.setControl(comboComp);

		Point fontSizetextSize = this.fontsizeCombo.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		fontSizetextSize = this.fontsizeCombo.computeSize(fontSizetextSize.x, fontSizetextSize.y);
		comboItem.setMinimumSize(formatTextSize.x + fontSizetextSize.x + fontTextSize.x,
				formatTextSize.y + fontSizetextSize.y + fontTextSize.y);
		comboItem.setPreferredSize(formatTextSize.x + fontSizetextSize.x + fontTextSize.x,
				formatTextSize.y + fontSizetextSize.y + fontTextSize.y);
		comboItem.setSize(formatTextSize.x + fontSizetextSize.x + fontTextSize.x, formatTextSize.y
				+ fontSizetextSize.y + fontTextSize.y);

		ActionConfiguration.initFormatCombo(this.composer, this.formatCombo);
		ActionConfiguration.initFontFaceCombo(this.composer, this.fontCombo);
		ActionConfiguration.initFontSizeCombo(this.composer, this.fontsizeCombo);

	}

	/**
	 * Creates a {@link ToolBar} located on the {@link CoolBar} wich is
	 * integrated into this widget.
	 * 
	 * @return the ToolbarManager for the created toolbar.
	 */
	public ToolBarManager crateToolbar() {
		ToolBar menu = new ToolBar(this.coolBar, SWT.HORIZONTAL | SWT.FLAT);
		ToolBarManager toolBarManager = new ToolBarManager(menu);
		CoolItem item = new CoolItem(this.coolBar, SWT.NONE);
		item.setControl(menu);
		this.toolkit.adapt(menu);

		return toolBarManager;
	}

	/**
	 * Set the html content
	 * 
	 * @param html
	 *            the content to set.
	 */
	public void setHtml(final String html) {
		this.composer.execute(JavaScriptCommands.SET_HTML(html));
	}

	/**
	 * Returns a list of all available font families of the underlying operating
	 * system. The font-faces can be rendered by the underlying html control as
	 * long as they are installed in the list of fonts provided by the operating
	 * system.
	 * 
	 * @return all available fonts.
	 */
	private static String[] getFontList() {
		Set s = new HashSet();
		// Add names of all bitmap fonts.
		FontData[] fds = Display.getCurrent().getFontList(null, false);
		for (int i = 0; i < fds.length; ++i) {
			s.add(fds[i].getName());
		}
		// Add names of all scalable fonts.
		fds = Display.getCurrent().getFontList(null, true);
		for (int i = 0; i < fds.length; ++i) {
			s.add(fds[i].getName());
		}
		// Sort the result and print it.
		String[] answer = new String[s.size()];
		s.toArray(answer);
		Arrays.sort(answer);
		return answer;
	}

	/**
	 * Iterates over all created toolitems a refreshes the layout. So can be
	 * ensured that the toolitems are layouted properly.
	 */
	public void adjustBars() {
		// /* Set the sizes after adding all cool items */
		CoolItem[] coolItems = this.coolBar.getItems();
		for (int i = 0; i < coolItems.length; i++) {
			CoolItem coolItem = coolItems[i];
			Control control = coolItem.getControl();
			Point size = control.computeSize(SWT.DEFAULT, SWT.DEFAULT);
			Point coolSize = coolItem.computeSize(size.x, size.y);
			if (control instanceof ToolBar) {
				ToolBar bar = (ToolBar) control;
				for (int j = 0, n = bar.getItemCount(); j < n; j++) {
					size.x += bar.getSize().x;
				}
				this.toolkit.adapt(bar);
			}
			coolItem.setMinimumSize(size);
			coolItem.setPreferredSize(coolSize);
			coolItem.setSize(coolSize);
		}

	}

}
