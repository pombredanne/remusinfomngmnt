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

package org.remus.infomngmnt.ui.widgets.richtext;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Combo;

import org.remus.infomngmnt.ui.widgets.richtext.actions.BackColorAction;
import org.remus.infomngmnt.ui.widgets.richtext.actions.BoldAction;
import org.remus.infomngmnt.ui.widgets.richtext.actions.BulletListAction;
import org.remus.infomngmnt.ui.widgets.richtext.actions.CleanupAction;
import org.remus.infomngmnt.ui.widgets.richtext.actions.DeleteColumnAction;
import org.remus.infomngmnt.ui.widgets.richtext.actions.DeleteRowAction;
import org.remus.infomngmnt.ui.widgets.richtext.actions.ForegroundAction;
import org.remus.infomngmnt.ui.widgets.richtext.actions.IndentAction;
import org.remus.infomngmnt.ui.widgets.richtext.actions.InsertColumnAfterAction;
import org.remus.infomngmnt.ui.widgets.richtext.actions.InsertColumnBeforeAction;
import org.remus.infomngmnt.ui.widgets.richtext.actions.InsertEditTableAction;
import org.remus.infomngmnt.ui.widgets.richtext.actions.InsertLayerAction;
import org.remus.infomngmnt.ui.widgets.richtext.actions.InsertNonBreakingWhitespace;
import org.remus.infomngmnt.ui.widgets.richtext.actions.InsertRowAfterAction;
import org.remus.infomngmnt.ui.widgets.richtext.actions.InsertRowBeforeAction;
import org.remus.infomngmnt.ui.widgets.richtext.actions.ItalicAction;
import org.remus.infomngmnt.ui.widgets.richtext.actions.JustifyCenterAction;
import org.remus.infomngmnt.ui.widgets.richtext.actions.JustifyFullAction;
import org.remus.infomngmnt.ui.widgets.richtext.actions.JustifyLeftAction;
import org.remus.infomngmnt.ui.widgets.richtext.actions.JustifyRightAction;
import org.remus.infomngmnt.ui.widgets.richtext.actions.MakeLayerAbsoluteAction;
import org.remus.infomngmnt.ui.widgets.richtext.actions.MoveLayerBackwardAction;
import org.remus.infomngmnt.ui.widgets.richtext.actions.MoveLayerForwardAction;
import org.remus.infomngmnt.ui.widgets.richtext.actions.NumListAction;
import org.remus.infomngmnt.ui.widgets.richtext.actions.OutdentAction;
import org.remus.infomngmnt.ui.widgets.richtext.actions.RemoveFormatAction;
import org.remus.infomngmnt.ui.widgets.richtext.actions.SelectFontAction;
import org.remus.infomngmnt.ui.widgets.richtext.actions.SelectFontSizeAction;
import org.remus.infomngmnt.ui.widgets.richtext.actions.SelectFormatAction;
import org.remus.infomngmnt.ui.widgets.richtext.actions.StrikeThroughAction;
import org.remus.infomngmnt.ui.widgets.richtext.actions.SubAction;
import org.remus.infomngmnt.ui.widgets.richtext.actions.SupAction;
import org.remus.infomngmnt.ui.widgets.richtext.actions.ToggleVisualAidAction;
import org.remus.infomngmnt.ui.widgets.richtext.actions.UnderLineAction;

import de.spiritlink.richhtml4eclipse.widgets.HtmlComposer;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class ActionConfiguration {

	public static void fillTextFormattingToolbar(final HtmlComposer composer,
			final ToolBarManager tbm) {
		tbm.add(new BoldAction(composer));
		tbm.add(new ItalicAction(composer));
		tbm.add(new UnderLineAction(composer));
		tbm.add(new StrikeThroughAction(composer));
		tbm.add(new Separator());
		tbm.add(new JustifyLeftAction(composer));
		tbm.add(new JustifyCenterAction(composer));
		tbm.add(new JustifyRightAction(composer));
		tbm.add(new JustifyFullAction(composer));
		tbm.add(new Separator());
		tbm.add(new BulletListAction(composer));
		tbm.add(new NumListAction(composer));
		tbm.add(new Separator());
		tbm.add(new OutdentAction(composer));
		tbm.add(new IndentAction(composer));
		tbm.add(new Separator());
		tbm.add(new SubAction(composer));
		tbm.add(new SupAction(composer));
	}

	public static void fillSimpleTextFormattingToolbar(final HtmlComposer composer,
			final ToolBarManager tbm) {
		tbm.add(new BoldAction(composer));
		tbm.add(new ItalicAction(composer));
		tbm.add(new UnderLineAction(composer));
		tbm.add(new StrikeThroughAction(composer));
		tbm.add(new Separator());
		tbm.add(new BulletListAction(composer));
		tbm.add(new NumListAction(composer));
		tbm.add(new Separator());
		tbm.add(new OutdentAction(composer));
		tbm.add(new IndentAction(composer));
		tbm.add(new Separator());
	}

	public static void fillColorFormattingToolbar(final HtmlComposer composer,
			final ToolBarManager tbm) {
		tbm.add(new ForegroundAction(composer));
		tbm.add(new BackColorAction(composer));
		tbm.add(new RemoveFormatAction(composer));

	}

	public static void fillLayerFormattingToolbar(final HtmlComposer composer,
			final ToolBarManager tbm) {
		tbm.add(new InsertLayerAction(composer));
		tbm.add(new MoveLayerBackwardAction(composer));
		tbm.add(new MoveLayerForwardAction(composer));
		tbm.add(new MakeLayerAbsoluteAction(composer));

	}

	public static void fillTableFormattingToolbar(final HtmlComposer composer,
			final ToolBarManager tbm) {
		tbm.add(new InsertEditTableAction(composer));
		tbm.add(new Separator());
		tbm.add(new InsertRowBeforeAction(composer));
		tbm.add(new InsertRowAfterAction(composer));
		tbm.add(new DeleteRowAction(composer));
		tbm.add(new Separator());
		tbm.add(new InsertColumnBeforeAction(composer));
		tbm.add(new InsertColumnAfterAction(composer));
		tbm.add(new DeleteColumnAction(composer));

	}

	public static void fillToolsToolbar(final HtmlComposer composer, final ToolBarManager tbm) {
		tbm.add(new InsertNonBreakingWhitespace(composer));
		tbm.add(new Separator());
		tbm.add(new CleanupAction(composer));
		tbm.add(new RemoveFormatAction(composer));
		tbm.add(new ToggleVisualAidAction(composer));

	}

	public static void initFormatCombo(final HtmlComposer composer, final Combo combo) {
		combo.addSelectionListener(new SelectionAdapter() {
			private final Action action = new SelectFormatAction(composer, combo);

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse
			 * .swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(final SelectionEvent e) {
				this.action.run();
			}
		});
	}

	/**
	 * Initializes the combo for choosing the font face. It's required that the
	 * appropiate {@link HtmlComposer} is already initialized.
	 * 
	 * @param composer
	 *            the Widget for editing html
	 * @param combo
	 *            the combo
	 */
	public static void initFontFaceCombo(final HtmlComposer composer, final Combo combo) {
		combo.addSelectionListener(new SelectionAdapter() {
			private final Action action = new SelectFontAction(composer, combo);

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse
			 * .swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(final SelectionEvent e) {
				this.action.run();
			}
		});

	}

	/**
	 * Initializes the combo to select a font size. {@lin HtmlComposer} must be
	 * initialized.
	 * 
	 * @param composer
	 *            the HTML wdiget
	 * @param combo
	 *            the combo to setup
	 */
	public static void initFontSizeCombo(final HtmlComposer composer, final Combo combo) {
		combo.addSelectionListener(new SelectionAdapter() {
			private final Action action = new SelectFontSizeAction(composer, combo);

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse
			 * .swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(final SelectionEvent e) {
				this.action.run();
			}
		});

	}

}
