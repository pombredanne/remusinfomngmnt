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

package org.aspencloud.calypso.ui.calendar.activities;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

public class ActivityFigure extends Figure {

	private static final Rectangle rect = new Rectangle();
	private static final Color borderActive = new Color(Display.getCurrent(), 120,190,255); 
	private static final Color bgActive = new Color(Display.getCurrent(), 211,233,255); 
	private static final Color borderCleared = ColorConstants.buttonDarker;
	private static final Color bgCleared = ColorConstants.buttonLightest;
	private static final Color borderDue = ColorConstants.black;
	private static final Color bgDue = ColorConstants.red;


	private String name = "";
	private boolean isCleared = false;
	private boolean isDue = false;
//	private ToolTipFigure tip = new ToolTipFigure();
	
	private Color txt;
	private Color border;
	private Color bg;

	private int cornerRadius = 3;
	private int cornerDiameter = 2 * cornerRadius;
	private int marginWidth = 2;
	private int marginHeight = 2;
	private int figureAlpha = 255;
	

	public ActivityFigure() {
		setLayoutManager(new XYLayout());
		setOpaque(false);

//		setPreferredSize(1,1);
		
//		tip.setPreferredSize(100, 100);
//		setToolTip(tip);
	}

	
	@Override
	public void paint(Graphics graphics) {
		txt = isCleared ? ColorConstants.gray : ColorConstants.black;
		if(isCleared) {
			border = borderCleared;
			bg = bgCleared;
		} else if(isDue) {
			border = borderDue;
			bg = bgDue;
		} else {
			border = borderActive;
			bg = bgActive;
		}
		graphics.setAntialias(SWT.ON);
		super.paint(graphics);
	}

	private void paintLabels(Graphics graphics) {
		rect.setBounds(getBounds());
		graphics.setForegroundColor(txt);

		graphics.drawString(name, rect.x + marginWidth, rect.y + marginHeight);
	}
	
	@Override
	protected void paintFigure(Graphics graphics) {
		// <figure>
		graphics.setAlpha(figureAlpha);

		rect.setBounds(getBounds());
		graphics.setBackgroundColor(bg);
		graphics.setForegroundColor(border);
		graphics.fillRoundRectangle(rect, cornerDiameter, cornerDiameter);
		rect.width -= 1;
		rect.height -= (1);
		graphics.drawRoundRectangle(rect, cornerDiameter, cornerDiameter);
		// </figure>
		
		paintLabels(graphics);
	}

	public void setCleared(boolean isCleared) {
		if(this.isCleared != isCleared) {
			this.isCleared = isCleared;
			revalidate();
			repaint();
		}
	}
	
	public void setDue(boolean isDue) {
		if(this.isDue != isDue) {
			this.isDue = isDue;
			revalidate();
			repaint();
		}
	}

	public void setName(String name) {
		if(name != null && !name.equals(this.name)) {
			this.name = name;
			getPreferredHeight();
			revalidate();
			repaint();
		}
	}

	public int getPreferredHeight() {
		if(getFont() != null) {
			Dimension d = FigureUtilities.getStringExtents(name, getFont());
			d.width += (2*marginWidth);
			d.height += (2*marginHeight);
			setPreferredSize(d);
		}
		return getPreferredSize().height;
	}

	public int getCornerRadius() {
		return cornerRadius;
	}
}
