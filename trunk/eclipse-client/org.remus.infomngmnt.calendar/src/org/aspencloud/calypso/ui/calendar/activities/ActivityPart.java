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

import java.util.Collections;
import java.util.List;

import org.aspencloud.calypso.ui.calendar.factories.FigureFactory;
import org.aspencloud.calypso.ui.calendar.tasks.TaskComponentEditPolicy;
import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.swt.widgets.Display;
import org.remus.infomngmnt.calendar.model.CEvent;
import org.remus.infomngmnt.calendar.model.ModelPackage;
import org.remus.infomngmnt.calendar.model.Task;

public class ActivityPart extends AbstractGraphicalEditPart implements Adapter {

	protected Notifier target = null;

	
	@Override
	protected IFigure createFigure() {
		ActivityFigure fig = (ActivityFigure) FigureFactory.createActivityFigure();
		Task task = getTaskModel();
		fig.setName(task.getName());
//		((ToolTipFigure) fig.getToolTip()).setTitle(task.getName());
//		String str = new SimpleDateFormat("hh:mm a").format(task.getStart().getDate());
//		((ToolTipFigure) fig.getToolTip()).setSectionText(0, str);
		fig.setCleared(false);
		fig.setDue(false);
		return fig;
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new TaskComponentEditPolicy());
	}

	@Override
	public void performRequest(Request request){
	    if (request.getType() == RequestConstants.REQ_OPEN) {
//	    	ModelDialog dlg = new ModelDialog(getViewer().getControl().getShell(), getTaskModel());
//	    	dlg.open();
	    }
	}

	@Override
	public void activate() {
		if(isActive()) return;
		
		super.activate();
		Task task = getTaskModel();
		task.eAdapters().add(this);
		task.getStart().eAdapters().add(this);
		task.getEnd().eAdapters().add(this);
	}
	
	@Override
	public void deactivate() {
		if(!isActive()) return;
		
		super.deactivate();
		Task task = getTaskModel();
		task.eAdapters().remove(this);
		if(task.getStart() != null) task.getStart().eAdapters().remove(this);
		if(task.getEnd() != null) task.getEnd().eAdapters().remove(this);
	}

	private ActivityFigure getActivityFigure() {
		return (ActivityFigure) getFigure();
	}
	
	private Task getTaskModel() {
		return (Task) getModel();
	}
	
	@Override
	protected List getModelChildren() {
		return Collections.EMPTY_LIST;
	}

	
	public void notifyChanged(Notification notification) {
		final Notification n = notification;
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				int type = n.getEventType();
				int featureId;
				switch (type) {
				case Notification.ADD:
					break;
				case Notification.REMOVE:
					break;
				case Notification.SET:
					Object notifier = n.getNotifier();
					if(notifier instanceof Task) {
						featureId = n.getFeatureID(Task.class);
						switch (featureId) {
						case ModelPackage.TASK__NAME:
							getActivityFigure().setName(((Task) notifier).getName());
							break;
						case ModelPackage.TASK__CLEARED:
							getActivityFigure().setCleared(((Task) notifier).getCleared() != null);
							break;
						default:
							break;
						}
					} else if(notifier instanceof CEvent) {
						if(ModelPackage.CEVENT__ALARM_DUE == n.getFeatureID(CEvent.class)) {
							getActivityFigure().setDue(((CEvent) notifier).isAlarmDue());
						}
					}
					break;
				}
			}
		});
	}

	@Override
	protected void refreshVisuals() {
		Task task = getTaskModel();
//		TimeSpan ts = new TimeSpan(task);
//		String str = "";
		
		// ActivityFigure
//		ActivityFigure fig = getActivityFigure();
		getActivityFigure().setName(task.getName());

		// ToolTipFigure
//		ToolTipFigure tip = (ToolTipFigure) fig.getToolTip();
//		tip.setTitle(task.getName());
//		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
//		if(task.hasStart()) {
//			tip.setSectionText(0, sdf.format(task.getStart().getDate()));
//		}
//		if(task.hasEnd()) {
//			tip.setSectionText(1, sdf.format(task.getEnd().getDate()));
//		}
	}

	public Notifier getTarget() {
		return target;
	}

	public boolean isAdapterForType(Object type) {
	    return false;
	}

	public void setTarget(Notifier newTarget) {
		target = newTarget;
	}

	public void unsetTarget(Notifier oldTarget) {
		if(target == oldTarget) {
			setTarget(null);
		}
	}
}