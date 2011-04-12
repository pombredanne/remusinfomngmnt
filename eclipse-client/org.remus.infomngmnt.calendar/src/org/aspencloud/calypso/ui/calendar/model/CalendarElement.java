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

package org.aspencloud.calypso.ui.calendar.model;

import java.util.LinkedList;
import java.util.List;

import org.aspencloud.calypso.util.TimeSpan;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class CalendarElement extends PropertyChangeObject {

	public final static String TIMESPAN = "TIMESPAN"; //$NON-NLS-1$
	public final static String FOREGROUND = "FOREGROUND"; //$NON-NLS-1$
	public final static String BACKGROUND = "BACKGROUND"; //$NON-NLS-1$
	public final static String SUBJECT = "SUBJECT"; //$NON-NLS-1$
	public final static String DESCRIPTION = "DESCRIPTION"; //$NON-NLS-1$
	public final static String TOOLTIPTEXT = "TOOLTIPTEXT"; //$NON-NLS-1$
	public final static String ADD_IMAGE = "ADD_IMAGE"; //$NON-NLS-1$
	public final static String REMOVE_IMAGE = "REMOVE_IMAGE"; //$NON-NLS-1$

	private TimeSpan timeSpan;

	private Color foregroundColor;

	private Color backgroundColor;

	private String subject;

	private String description;

	private String tooltipText;

	private final List<Image> images;

	public List<Image> getImages() {
		return images;
	}

	public TimeSpan getTimeSpan() {
		return timeSpan;
	}

	public CalendarElement() {
		images = new LinkedList<Image>();
	}

	public void setTimeSpan(TimeSpan timeSpan) {
		TimeSpan oldValue = this.timeSpan;
		this.timeSpan = timeSpan;
		firePropertyChange(TIMESPAN, oldValue, timeSpan);

	}

	public Color getForegroundColor() {
		return foregroundColor;
	}

	public void setForegroundColor(Color foregroundColor) {
		Color oldValue = this.foregroundColor;
		this.foregroundColor = foregroundColor;
		firePropertyChange(FOREGROUND, oldValue, foregroundColor);
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		Color oldValue = this.backgroundColor;
		this.backgroundColor = backgroundColor;
		firePropertyChange(BACKGROUND, oldValue, backgroundColor);
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		String oldValue = this.subject;
		this.subject = subject;
		firePropertyChange(SUBJECT, oldValue, subject);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTooltipText() {
		return tooltipText;
	}

	public void setTooltipText(String tooltipText) {
		this.tooltipText = tooltipText;
	}

	public boolean add(Image e) {
		return images.add(e);
	}

	public void add(int index, Image element) {
		images.add(index, element);
	}

	public boolean remove(Object o) {
		return images.remove(o);
	}

}
