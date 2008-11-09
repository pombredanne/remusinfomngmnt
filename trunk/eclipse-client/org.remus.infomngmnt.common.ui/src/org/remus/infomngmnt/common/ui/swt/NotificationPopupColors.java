/*******************************************************************************
 * Copyright (c) 2004, 2008 Tasktop Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Benjamin Pasero - initial API and implementation
 *     Tasktop Technologies - improvements
 *******************************************************************************/

package org.remus.infomngmnt.common.ui.swt;

import org.eclipse.jface.resource.DeviceResourceException;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

/**
 * Helper Class to create the colors for the {@link AbstractNotificationPopup}.
 * <p>
 * Note: Copied from FormColors of UI Forms.
 * </p>
 * 
 * @author Benjamin Pasero (initial contribution from RSSOwl, see bug 177974)
 * @author Mik Kersten
 */
public class NotificationPopupColors {

	private final Display display;

	private Color titleText;

	private Color gradientBegin;

	private Color gradientEnd;

	private Color border;

	private final ResourceManager resourceManager;

	public NotificationPopupColors(Display display, ResourceManager resourceManager) {
		this.display = display;
		this.resourceManager = resourceManager;

		createColors();
	}

	private void createColors() {
		createBorderColor();
		createGradientColors();

		this.titleText = getColor(this.resourceManager, getSystemColor(SWT.COLOR_TITLE_INACTIVE_FOREGROUND));
	}

	public Color getGradientBegin() {
		return this.gradientBegin;
	}

	public Color getGradientEnd() {
		return this.gradientEnd;
	}

	public Color getBorder() {
		return this.border;
	}

	public Color getTitleText() {
		return this.titleText;
	}

	private void createBorderColor() {
		RGB tbBorder = getSystemColor(SWT.COLOR_TITLE_BACKGROUND);
		RGB bg = getImpliedBackground().getRGB();

		// Group 1
		// Rule: If at least 2 of the RGB values are equal to or between 180 and
		// 255, then apply specified opacity for Group 1
		// Examples: Vista, XP Silver, Wn High Con #2
		// Keyline = TITLE_BACKGROUND @ 70% Opacity over LIST_BACKGROUND
		if (testTwoPrimaryColors(tbBorder, 179, 256)) {
			tbBorder = blend(tbBorder, bg, 70);
		} else if (testTwoPrimaryColors(tbBorder, 120, 180)) {
			tbBorder = blend(tbBorder, bg, 50);
		} else {
			tbBorder = blend(tbBorder, bg, 30);
		}

		this.border = getColor(this.resourceManager, tbBorder);
	}

	private void createGradientColors() {
		RGB titleBg = getSystemColor(SWT.COLOR_TITLE_BACKGROUND);
		Color bgColor = getImpliedBackground();
		RGB bg = bgColor.getRGB();
		RGB bottom, top;

		// Group 1
		// Rule: If at least 2 of the RGB values are equal to or between 180 and
		// 255, then apply specified opacity for Group 1
		// Examples: Vista, XP Silver, Wn High Con #2
		// Gradient Bottom = TITLE_BACKGROUND @ 30% Opacity over LIST_BACKGROUND
		// Gradient Top = TITLE BACKGROUND @ 0% Opacity over LIST_BACKGROUND
		if (testTwoPrimaryColors(titleBg, 179, 256)) {
			bottom = blend(titleBg, bg, 30);
			top = bg;
		}

		// Group 2
		// Rule: If at least 2 of the RGB values are equal to or between 121 and
		// 179, then apply specified opacity for Group 2
		// Examples: XP Olive, OSX Graphite, Linux GTK, Wn High Con Black
		// Gradient Bottom = TITLE_BACKGROUND @ 20% Opacity over LIST_BACKGROUND
		// Gradient Top = TITLE BACKGROUND @ 0% Opacity over LIST_BACKGROUND
		else if (testTwoPrimaryColors(titleBg, 120, 180)) {
			bottom = blend(titleBg, bg, 20);
			top = bg;
		}

		// Group 3
		// Rule: If at least 2 of the RGB values are equal to or between 0 and
		// 120, then apply specified opacity for Group 3
		// Examples: XP Default, Wn Classic Standard, Wn Marine, Wn Plum, OSX
		// Aqua, Wn High Con White, Wn High Con #1
		// Gradient Bottom = TITLE_BACKGROUND @ 10% Opacity over LIST_BACKGROUND
		// Gradient Top = TITLE BACKGROUND @ 0% Opacity over LIST_BACKGROUND
		else {
			bottom = blend(titleBg, bg, 10);
			top = bg;
		}

		this.gradientBegin = getColor(this.resourceManager, top);
		this.gradientEnd = getColor(this.resourceManager, bottom);
	}

	private RGB blend(RGB c1, RGB c2, int ratio) {
		int r = blend(c1.red, c2.red, ratio);
		int g = blend(c1.green, c2.green, ratio);
		int b = blend(c1.blue, c2.blue, ratio);
		return new RGB(r, g, b);
	}

	private int blend(int v1, int v2, int ratio) {
		int b = (ratio * v1 + (100 - ratio) * v2) / 100;
		return Math.min(255, b);
	}

	private boolean testTwoPrimaryColors(RGB rgb, int from, int to) {
		int total = 0;
		if (testPrimaryColor(rgb.red, from, to)) {
			total++;
		}
		if (testPrimaryColor(rgb.green, from, to)) {
			total++;
		}
		if (testPrimaryColor(rgb.blue, from, to)) {
			total++;
		}
		return total >= 2;
	}

	private boolean testPrimaryColor(int value, int from, int to) {
		return value > from && value < to;
	}

	private RGB getSystemColor(int code) {
		return getDisplay().getSystemColor(code).getRGB();
	}

	private Color getImpliedBackground() {
		return this.display.getSystemColor(SWT.COLOR_LIST_BACKGROUND);
	}

	private Display getDisplay() {
		return this.display;
	}

	private Color getColor(ResourceManager manager, RGB rgb) {
		try {
			return manager.createColor(rgb);
		} catch (DeviceResourceException e) {
			return manager.getDevice().getSystemColor(SWT.COLOR_BLACK);
		}
	}
}