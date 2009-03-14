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

package org.remus.infomngmnt.ui.collapsiblebuttons;

import org.eclipse.nebula.widgets.calendarcombo.ColorCache;
import org.eclipse.nebula.widgets.calendarcombo.DefaultColorManager;
import org.eclipse.nebula.widgets.calendarcombo.DefaultSettings;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.events.IExpansionListener;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Section;

import org.remus.infomngmnt.common.ui.widgets.CalendarComposite;
import org.remus.infomngmnt.ui.extension.CollapsibleButtonBar;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class CalendarSection extends CollapsibleButtonBar {

	private static final int SECTION_STYLE = ExpandableComposite.TITLE_BAR
			| ExpandableComposite.TWISTIE | ExpandableComposite.EXPANDED;

	/**
	 * 
	 */
	public CalendarSection() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createControl(final Composite parent) {
		final Composite comp = getToolkit().createComposite(parent, SWT.NONE);
		comp.setLayout(new GridLayout(1, false));

		final Section typeSection = getToolkit().createSection(comp, SWT.NONE);
		typeSection.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false));

		Composite calendarComposite = getToolkit().createComposite(typeSection, SWT.BORDER);
		typeSection.setClient(calendarComposite);
		GridLayout gridLayout = new GridLayout();
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		calendarComposite.setLayout(gridLayout);
		calendarComposite.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));

		DefaultSettings settings = new DefaultSettings();
		DefaultColorManager colorManager = new DefaultColorManager() {
			@Override
			public Color getCalendarBorderColor() {
				return getViewSite().getShell().getDisplay().getSystemColor(SWT.COLOR_WHITE);
			}

			@Override
			public Color getCalendarBackgroundColor() {
				return ColorCache.getWhite();
			}

		};
		CalendarComposite cc = new CalendarComposite(calendarComposite, null, null, null,
				colorManager, settings, false, null, null);
		GridData gridData = new GridData(SWT.CENTER, SWT.BEGINNING, true, false);
		gridData.widthHint = settings.getCalendarWidth();
		gridData.heightHint = settings.getCalendarHeight();
		cc.setLayoutData(gridData);
		cc.setVisible(true);

		getToolkit().adapt(cc);

		;

		final Section today = getToolkit().createSection(comp, SECTION_STYLE);
		today.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		today.setText("Today");

		final Section thisWeek = getToolkit().createSection(comp, SECTION_STYLE);
		thisWeek.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		thisWeek.setText("This week");

		final Section nextWeek = getToolkit().createSection(comp,
				ExpandableComposite.TITLE_BAR | ExpandableComposite.TWISTIE);
		nextWeek.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		nextWeek.setText("Next week");
		nextWeek.addExpansionListener(new IExpansionListener() {
			public void expansionStateChanged(final ExpansionEvent e) {
				// comp.layout(true);

			}

			public void expansionStateChanging(final ExpansionEvent e) {
				// TODO Auto-generated method stub

			}
		});

		setControl(comp);

	}

}
