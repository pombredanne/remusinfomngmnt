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

import java.util.Collections;
import java.util.List;

import org.aspencloud.calypso.ui.calendar.factories.FigureFactory;
import org.aspencloud.calypso.util.CTimeSpan;
import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.swt.widgets.Display;
import org.remus.calendartest.ccalendar.CalendarViewer;
import org.remus.infomngmnt.calendar.model.CEvent;
import org.remus.infomngmnt.calendar.model.ModelPackage;
import org.remus.infomngmnt.calendar.model.Task;
import org.remus.infomngmnt.calendar.model.impl.CEventImpl;
import org.remus.infomngmnt.calendar.model.impl.TaskImpl;

public class TaskPart extends AbstractGraphicalEditPart implements Adapter {

	protected Notifier target = null;


	@Override
	protected IFigure createFigure() {
		Task task = getTaskModel(); 
		TaskFigure fig = (TaskFigure) FigureFactory.createTaskFigure();
		fig.setEvent(CTimeSpan.isTaskInstantaneous(task));
		fig.setName(task.getName());
		fig.setDetails(task.getDetails());
		// FIXME
//		fig.setCleared(task.isCleared());
//		fig.setDue(task.isAlarmTriggered());
		return fig;
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new TaskComponentEditPolicy());
	}

	@Override
	public void performRequest(final Request request){
		if (request.getType() == RequestConstants.REQ_OPEN) {
			((CalendarViewer) getViewer()).performOpen(getTaskModel());
		}
	}

	@Override
	public void activate() {
		if(isActive()) {
			return;
		}

		super.activate();
		Task task = getTaskModel();
		task.eAdapters().add(this);
		task.getStart().eAdapters().add(this);
		task.getEnd().eAdapters().add(this);
	}

	@Override
	public void deactivate() {
		if(!isActive()) {
			return;
		}

		super.deactivate();
		Task task = getTaskModel();
		((TaskImpl) task).eAdapters().remove(this);
		if(task.getStart() != null) {
			((CEventImpl) task.getStart()).eAdapters().remove(this);
		}
		if(task.getEnd() != null) {
			((CEventImpl) task.getEnd()).eAdapters().remove(this);
		}
	}

	private TaskFigure getTaskFigure() {
		return (TaskFigure) getFigure();
	}

	private Task getTaskModel() {
		return (Task) getModel();
	}

	@Override
	protected List getModelChildren() {
		return Collections.EMPTY_LIST;
	}


	public void notifyChanged(final Notification notification) {
		final Notification n = notification;
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				int type = n.getEventType();
				switch (type) {
				case Notification.ADD:
					break;
				case Notification.REMOVE:
					break;
				case Notification.SET:
					Object notifier = n.getNotifier();
					if(notifier instanceof Task) {
						int featureId = n.getFeatureID(Task.class);
						switch (featureId) {
						case ModelPackage.TASK__NAME:
							getTaskFigure().setName(((Task) notifier).getName());
							break;
						case ModelPackage.TASK__CLEARED:
							getTaskFigure().setCleared(((Task) notifier).getCleared() != null);
							break;
						default:
							break;
						}
					} else if(notifier instanceof CEvent) {
						if(ModelPackage.CEVENT__ALARM_DUE == n.getFeatureID(CEvent.class)) {
							getTaskFigure().setDue(((CEvent) notifier).isAlarmDue());
						}
					}
					break;
				default:
					break;
				}
			}
		});
	}

	public Notifier getTarget() {
		return this.target;
	}

	public boolean isAdapterForType(final Object type) {
		return false;
	}

	public void setTarget(final Notifier newTarget) {
		this.target = newTarget;
	}

	public void unsetTarget(final Notifier oldTarget) {
		if(this.target == oldTarget) {
			setTarget(null);
		}
	}
}