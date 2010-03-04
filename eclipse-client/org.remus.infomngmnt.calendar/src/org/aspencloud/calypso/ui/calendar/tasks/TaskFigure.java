/****************************************************************************
 * Copyright (c) 2005-2006 Jeremy Dowdall
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Jeremy Dowdall <aspencloud@users.sourceforge.net> - initial API and implementation
 *****************************************************************************/

package org.aspencloud.calypso.ui.calendar.tasks;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.TextLayout;
import org.eclipse.swt.widgets.Display;

public class TaskFigure extends Figure {

	private static final Rectangle rect = new Rectangle();
	private static final Color borderActive = new Color(Display.getCurrent(), 120, 190, 255);
	private static final Color bgActive = new Color(Display.getCurrent(), 211, 233, 255);
	private static final Color fgActive = new Color(Display.getCurrent(), 175, 215, 255);
	private static final Color borderCleared = ColorConstants.buttonDarker;
	private static final Color bgCleared = ColorConstants.buttonLightest;
	private static final Color fgCleared = ColorConstants.button;
	private static final Color borderDue = ColorConstants.black;
	private static final Color bgDue = ColorConstants.red;
	private static final Color fgDue = ColorConstants.red;

	private String contact = "";
	private String name = "";
	private String details = "";
	private boolean isCleared = false;
	private boolean isEvent = false;
	private boolean isDue = false;
	// private ToolTipFigure tip = new ToolTipFigure();

	private Color txt;
	private Color border;
	private Color bg;
	private Color fg;

	private final int cornerRadius = 7;
	private final int cornerDiameter = 2 * this.cornerRadius;
	private final int taskBarWidth = 5;
	private final int marginWidth = 2;
	private final int marginHeight = 2;

	public TaskFigure() {
		setLayoutManager(new XYLayout());
		setOpaque(false);

		// tip.setPreferredSize(100, 100);
		// setToolTip(tip);
	}

	@Override
	public void paint(final Graphics graphics) {
		this.txt = this.isCleared ? ColorConstants.gray : ColorConstants.black;
		if (this.isCleared) {
			this.border = borderCleared;
			this.bg = bgCleared;
			this.fg = fgCleared;
		} else if (this.isDue) {
			this.border = borderDue;
			this.bg = bgDue;
			this.fg = fgDue;
		} else {
			this.border = borderActive;
			this.bg = bgActive;
			this.fg = fgActive;
		}
		graphics.setAntialias(SWT.ON);
		super.paint(graphics);
	}

	private void paintLabels(final Graphics graphics) {
		int marginLeft = Math.max(this.taskBarWidth, this.cornerRadius) + this.marginWidth;
		rect.setBounds(getBounds());
		int figHeight = rect.height;
		int txtHeight;
		graphics.setForegroundColor(this.txt);

		int yOffset = this.isEvent ? -(this.marginHeight + 1) : 0;

		if (this.name.length() > 0) {
			txtHeight = FigureUtilities.getTextExtents(this.name, getFont()).height;
			graphics.drawString(this.name, rect.x + marginLeft, rect.y + yOffset
					+ this.marginHeight);
			yOffset += txtHeight;
		}

		if (this.contact.length() > 0) {
			txtHeight = FigureUtilities.getTextExtents(this.contact, getFont()).height;
			if (yOffset + txtHeight > figHeight)
				return;
			graphics.drawString(this.contact, rect.x + marginLeft, rect.y + yOffset
					+ this.marginHeight);
			yOffset += txtHeight;
		}

		if (this.details.length() > 0) {
			int w = getBounds().width - marginLeft;
			if (w > 0) {
				TextLayout tl = new TextLayout(Display.getCurrent());
				tl.setText(this.details);
				tl.setFont(getFont());
				tl.setWidth(w);
				txtHeight = tl.getBounds().height;
				if (yOffset + txtHeight > figHeight)
					return;
				graphics.drawTextLayout(tl, rect.x + marginLeft, rect.y + yOffset
						+ this.marginHeight);
				tl.dispose();
			}
		}

	}

	private void paintEvent(final Graphics graphics) {
		rect.setBounds(getBounds());
		int r = rect.height / 2 - 1;
		int top = rect.y;
		int middle = rect.y + r;
		int bottom = rect.y + (2 * r);

		graphics.setBackgroundColor(this.bg);
		graphics.setForegroundColor(this.border);
		int[] pts = new int[] { rect.x + r, top, rect.x + rect.width - r - 1, top,
				rect.x + rect.width - 1, middle, rect.x + rect.width - r - 1, bottom, rect.x + r,
				bottom, };
		graphics.fillPolygon(pts);

		rect.x += r;
		rect.width -= (2 * r);
		rect.y = top;
		rect.height = bottom - top;
		graphics.setForegroundColor(this.fg);
		graphics.fillGradient(rect, false);

		rect.setBounds(getBounds());
		graphics.setBackgroundColor(this.border);
		graphics.setForegroundColor(this.border);
		graphics.drawPolygon(pts);
		pts = new int[] { rect.x, middle, rect.x + r, top, rect.x + r, bottom };
		graphics.fillPolygon(pts);
		graphics.drawPolygon(pts);
	}

	@Override
	protected void paintFigure(final Graphics graphics) {
		if (this.isEvent)
			paintEvent(graphics);
		else
			paintTask(graphics);

		paintLabels(graphics);
	}

	private void paintTask(final Graphics graphics) {
		rect.setBounds(getBounds());
		graphics.setBackgroundColor(this.bg);
		graphics.setForegroundColor(this.border);
		graphics.fillRoundRectangle(rect, this.cornerDiameter, this.cornerDiameter);
		rect.width -= 1;
		rect.height -= 1;
		graphics.drawRoundRectangle(rect, this.cornerDiameter, this.cornerDiameter);

		rect.setBounds(getBounds());
		rect.width -= this.cornerRadius;
		rect.y += 1;
		rect.height -= 2;
		graphics.setForegroundColor(this.fg);
		graphics.fillGradient(rect, false);

		rect.setBounds(getBounds());
		rect.width = Math.max(this.taskBarWidth, this.cornerRadius);
		graphics.setAlpha(255);
		graphics.setBackgroundColor(this.border);
		graphics.setForegroundColor(this.border);
		graphics.fillRectangle(rect);
		rect.width -= 1;
		rect.height -= (1);
		graphics.drawRectangle(rect);
	}

	public void setCleared(final boolean isCleared) {
		if (this.isCleared != isCleared) {
			this.isCleared = isCleared;
			revalidate();
			repaint();
		}
	}

	public void setContact(final String contact) {
		if (contact != null && !contact.equals(this.contact)) {
			this.contact = contact;
			revalidate();
			repaint();
		}
	}

	public void setDetails(final String details) {
		if (details != null && !details.equals(this.details)) {
			this.details = details;
			revalidate();
			repaint();
		}
	}

	public void setDue(final boolean isDue) {
		if (this.isDue != isDue) {
			this.isDue = isDue;
			revalidate();
			repaint();
		}
	}

	public void setEvent(final boolean isEvent) {
		if (this.isEvent != isEvent) {
			this.isEvent = isEvent;
			revalidate();
			repaint();
		}
	}

	public void setName(final String name) {
		if (name != null && !name.equals(this.name)) {
			this.name = name;
			revalidate();
			repaint();
		}
	}

}
