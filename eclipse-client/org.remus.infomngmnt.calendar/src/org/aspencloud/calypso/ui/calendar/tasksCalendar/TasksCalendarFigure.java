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

package org.aspencloud.calypso.ui.calendar.tasksCalendar;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.FigureListener;
import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ScalableLayeredPane;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;


public class TasksCalendarFigure extends ScalableLayeredPane {

	public static final String PROP_TASK_AREA = "taskArea";
	public static final String PROP_DAY_WIDTH = "dayWidth";

	private boolean drawGrid = true;
	private boolean drawMinorGrid = false;
	private double dayWidth;
	private int lblWidth;
	private int lblWidthHint = 0;
	private int lblHeight;
	private int lblHeightHint = 0;
	private int rowMarginWidth = 5;
	private int borderWidth = 5;
	private int minorGridDivisions = 2;
	private String[] labels = new String[0];
	private int numDays;
	private Color[] dayColors = new Color[0];
	
	private Rectangle taskArea = new Rectangle();
	protected PropertyChangeSupport listeners = new PropertyChangeSupport(this);

	
	public TasksCalendarFigure() {
		setLayoutManager(new XYLayout());
		setOpaque(false);
		
		addFigureListener(new FigureListener() {
			public void figureMoved(IFigure source) {
				setLabelSize();
			}
		});
	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener pcl) {
		listeners.addPropertyChangeListener(pcl);
	}

	@Override
	protected void firePropertyChange(String propName, Object old, Object newValue) {
		listeners.firePropertyChange(propName, old, newValue);
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener pcl) {
		listeners.removePropertyChangeListener(pcl);
	}

	@Override
	protected void paintFigure(Graphics graphics) {
		int yLine;
		int yText;
		int my;
		double rowHeight = (double) (taskArea.height) / (double) (labels.length - 1);
		double minorHeight = rowHeight / minorGridDivisions;
		
		// Day Color Backgrounds
		if(numDays > 0) {
			int xLine = taskArea.x;
			for(int i = 0; i < numDays; i++) {
				xLine = (int) (taskArea.x + (i * dayWidth));
				if((dayColors[i] != null) && !dayColors[i].isDisposed()) {
					graphics.setBackgroundColor(dayColors[i]);
					graphics.fillRectangle(xLine, taskArea.y, (int) dayWidth + 1, taskArea.height);
				}
			}
		}
		
		// horizontal grid
		for(int i = 0; i < labels.length; i++) {
			yLine = (int) ((i * rowHeight) + (lblHeight/2.0)) - 1;

			// horizontal grid
			if(drawGrid) {
				graphics.setForegroundColor(ColorConstants.lightGray);
				graphics.drawLine(taskArea.x, yLine, taskArea.x+taskArea.width, yLine);

				if(drawMinorGrid) {
					if(i < labels.length-1) {
						graphics.setForegroundColor(ColorConstants.lightGray);
						my = (int) (yLine + minorHeight);
						for(int j = 0; j < (minorGridDivisions-1); j++) {
							graphics.drawLine(taskArea.x, my, taskArea.x+taskArea.width, my);
							my = (int) (my + minorHeight);
						}
					}
				}
			}
			
			yText = yLine - (lblHeight / 2);
			graphics.setForegroundColor(ColorConstants.darkGray);
			int d = FigureUtilities.getTextWidth(labels[i], getFont());
			graphics.drawString(labels[i], taskArea.x - d - rowMarginWidth, yText);
		}

		// vertical grid
		if(drawGrid && (numDays > 0)) {
			int xLine = taskArea.x;
			for(int i = 0; i < numDays; i++) {
				xLine = (int) (taskArea.x + (i * dayWidth));
				graphics.setForegroundColor(ColorConstants.lightGray);
				graphics.drawLine(xLine, taskArea.y, xLine, taskArea.y + taskArea.height);
			}
		}
		
		graphics.setForegroundColor(ColorConstants.gray);
		graphics.drawRectangle(taskArea);
		graphics.drawLine(taskArea.x-1, taskArea.y, taskArea.x-1, taskArea.y+taskArea.height);
		if(labels.length == 0)
			graphics.drawLine(taskArea.x, taskArea.y+1, taskArea.x+taskArea.width, taskArea.y+1);
	}

	public double getDayWidth() {
		return dayWidth;
	}

	public void setNumDays(int numDays) {
		if(numDays > 0) {
			this.numDays = numDays;
			clearDayColors();
			dayColors = new Color[numDays];
			Object old = new Double(dayWidth);
			dayWidth = taskArea.width / numDays;
			firePropertyChange(PROP_DAY_WIDTH, old, new Double(dayWidth));
		}
	}
	
	public void clearDayColors() {
		for(int i = 0; i < dayColors.length; i++) {
			if((dayColors[i] != null) && !dayColors[i].isDisposed()) {
				dayColors[i].dispose();
				dayColors[i] = null;
			}
		}
	}

	public void setDayColor(int day, RGB rgb) {
		if((day >= 0) && (day < dayColors.length)) {
			dayColors[day] = new Color(null, rgb);
		}
	}
	
	public int getNumRowLabels() {
		return labels.length;
	}
	
	public String[] getRowLabels() {
		return labels;
	}
	
	public void setRowLabels(String[] labels) {
		this.labels = labels;
	}
	
	public int getRowLabelHeight() {
		return lblHeight;
	}
	
	public int getRowLabelWidth() {
		return lblWidth;
	}
	
	public Rectangle getTaskArea() {
		return taskArea;
	}

	public boolean isGridVisible() {
		return drawGrid;
	}
	
	public boolean isMinorGridVisible() {
		return drawMinorGrid;
	}
	
	public void setGridVisible(boolean visible) {
		if(drawGrid != visible) {
			drawGrid = visible;
			repaint();
		}
	}
	
	public void setMinorGridVisible(boolean visible) {
		if(drawMinorGrid != visible) {
			drawMinorGrid = visible;
			repaint();
		}
	}
	
	public void setLabelSize() {
		if(labels.length > 0) {
			for(int i = 0; i < labels.length; i++) {
				Dimension d = FigureUtilities.getStringExtents(labels[i], getFont());
				lblWidth = Math.max(lblWidthHint, d.width + 2*rowMarginWidth + 2*borderWidth);
				lblHeight = Math.max(lblHeightHint, d.height);
			}
		} else {
			lblWidth = lblWidthHint;
			lblHeight = lblHeightHint;
		}
		
		updateDimensions();
	}
	public void setLabelSize(int widthHint, int heightHint) {
		lblWidthHint = widthHint;
		lblHeightHint = heightHint;
		setLabelSize();
	}
	
	private void updateDimensions() {
		Object old1 = taskArea;
		Object old2 = new Double(dayWidth);
		taskArea = new Rectangle(
				lblWidth,
				lblHeight / 2 - 1,
				getClientArea().width - lblWidth - borderWidth,
				getClientArea().height - lblHeight
				);
		dayWidth = (double) taskArea.width / (double) numDays;
		firePropertyChange(PROP_TASK_AREA, old1, taskArea);
		firePropertyChange(PROP_DAY_WIDTH, old2, new Double(dayWidth));
		repaint();
	}
}