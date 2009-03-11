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

import org.eclipse.nebula.widgets.datechooser.DateChooser;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Section;

import org.remus.infomngmnt.ui.extension.CollapsibleButtonBar;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class CalendarSection extends CollapsibleButtonBar {

	private static final int SECTION_STYLE = ExpandableComposite.TITLE_BAR | ExpandableComposite.TWISTIE | ExpandableComposite.EXPANDED;
	/**
	 * 
	 */
	public CalendarSection() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void createControl(final Composite parent) {
		Composite comp = getToolkit().createComposite(parent, SWT.NONE);
		comp.setLayout(new GridLayout(1, false));
		
		final Section typeSection = getToolkit().createSection(comp, SECTION_STYLE);
		typeSection.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		typeSection.setText("Calendar");
		
		Composite calendarComposite = getToolkit().createComposite(typeSection);
		typeSection.setClient(calendarComposite);
		calendarComposite.setLayout(new GridLayout());
		calendarComposite.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		
		
		DateChooser month1 = new DateChooser(calendarComposite, SWT.NONE);
		month1.setLayoutData(new GridData(SWT.CENTER, SWT.BEGINNING, true, false));
		getToolkit().adapt(month1);
		
		final Section today = getToolkit().createSection(comp, SECTION_STYLE);
		today.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		today.setText("Today");
		
		final Section thisWeek = getToolkit().createSection(comp, SECTION_STYLE);
		thisWeek.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		thisWeek.setText("This week");
		
		final Section nextWeek = getToolkit().createSection(comp, ExpandableComposite.TITLE_BAR | ExpandableComposite.TWISTIE);
		nextWeek.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		nextWeek.setText("Next week");
		
		
		
		setControl(comp);
		
	}

}
