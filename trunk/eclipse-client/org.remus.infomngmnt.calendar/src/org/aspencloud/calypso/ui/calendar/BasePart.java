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

package org.aspencloud.calypso.ui.calendar;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

public abstract class BasePart extends AbstractGraphicalEditPart implements PropertyChangeListener {

	@Override
	public void activate() {
		if(isActive()) return;
		
		super.activate();
		getBaseModel().addPropertyChangeListener(this);
	}
	
	@Override
	public void deactivate() {
		if(!isActive()) return;
		
		super.deactivate();
		getBaseModel().removePropertyChangeListener(this);
	}
	
	protected BaseModel getBaseModel() {
		return (BaseModel) getModel();
	}
	
	@Override
	protected List getModelChildren() {
		return getBaseModel().getChildren();
	}
	
	public void propertyChange(PropertyChangeEvent evt) {
		String prop = evt.getPropertyName();
		if(BaseModel.PROP_TIMESPAN.equals(prop)) {
			refreshVisuals();
		} else if(BaseModel.PROP_CHILDREN.equals(prop)) {
			refreshChildren();
		} else if(BaseModel.PROP_SIZE.equals(prop) || BaseModel.PROP_LOCATION.equals(prop)) {
		    Point loc = getBaseModel().getLocation();
		    Dimension size = getBaseModel().getSize();
		    if((loc != null) && (size != null)) {
			    Rectangle r = new Rectangle(loc ,size);
			    ((GraphicalEditPart) getParent()).setLayoutConstraint(this, getFigure(), r);
		    }
		}
	}
	
//	protected void refreshVisuals() {
//		((BaseFigure) getFigure()).setDate(new Date(getBaseModel().getTimeSpan().getStart()));
//	}
}