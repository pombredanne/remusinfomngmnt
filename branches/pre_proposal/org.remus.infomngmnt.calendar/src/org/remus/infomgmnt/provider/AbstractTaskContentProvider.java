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

package org.remus.infomgmnt.provider;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Display;
import org.remus.infomngmnt.calendar.model.CEvent;
import org.remus.infomngmnt.calendar.model.EndEvent;
import org.remus.infomngmnt.calendar.model.ModelPackage;
import org.remus.infomngmnt.calendar.model.StartEvent;
import org.remus.infomngmnt.calendar.model.Task;
import org.remus.infomngmnt.calendar.model.Tasklist;


public abstract class AbstractTaskContentProvider extends AdapterImpl implements ITreeContentProvider {
	public static final int OWNER_TASKS   = 1 << 0;
	public static final int MEMBER_TASKS  = 1 << 1;
	public static final int SUBJECT_TASKS = 1 << 2;

	private final List eObjects = new ArrayList();
	private int taskTypes = OWNER_TASKS | MEMBER_TASKS | SUBJECT_TASKS;
	
	
	public AbstractTaskContentProvider() {
		super();
	}
	public AbstractTaskContentProvider(int taskTypes) {
		super();
		this.taskTypes = taskTypes;
	}
	

	protected void addAdapter(EObject eObject) {
		if(eObject == null) return;
		if(eObject instanceof Task) {
			Task task = (Task) eObject;
			if(!this.eObjects.contains(task)) {
				task.eAdapters().add(this);
				this.eObjects.add(task);
			}
			if(task.getStart() != null) {
				CEvent event = task.getStart();
				if(!this.eObjects.contains(event)) {
					event.eAdapters().add(this);
					this.eObjects.add(event);
				}
			}
			if(task.getEnd() != null) {
				CEvent event = task.getEnd();
				if(!this.eObjects.contains(event)) {
					event.eAdapters().add(this);
					this.eObjects.add(event);
				}
			}
			if(task.getDue() != null) {
				CEvent event = task.getDue();
				if(!this.eObjects.contains(event)) {
					event.eAdapters().add(this);
					this.eObjects.add(event);
				}
			}
		} else {
			if(!this.eObjects.contains(eObject)) {
				eObject.eAdapters().add(this);
				this.eObjects.add(eObject);
			}
		}
	}

	protected void removeAdapters(EObject[] eObjects) {
		for(int i = 0; i < eObjects.length; i++) {
			removeAdapter(eObjects[i]);
		}
	}
	
	protected void removeAdapter(EObject eObject) {
		if(eObject instanceof Task) {
			Task task = (Task) eObject;
			task.eAdapters().remove(this);
			this.eObjects.remove(task);
			if(task.getStart() != null) {
				CEvent event = task.getStart();
				event.eAdapters().remove(this);
				this.eObjects.remove(event);
			}
			if(task.getEnd() != null) {
				CEvent event = task.getEnd();
				event.eAdapters().remove(this);
				this.eObjects.remove(event);
			}
			if(task.getDue() != null) {
				CEvent event = task.getDue();
				event.eAdapters().remove(this);
				this.eObjects.remove(event);
			}
		} else {
			eObject.eAdapters().remove(this);
			this.eObjects.remove(eObject);
		}
	}

	protected void clearAdapters() {
		EObject[] ea = (EObject[]) this.eObjects.toArray(new EObject[this.eObjects.size()]);
		this.eObjects.clear();
		for(int i = 0; i < ea.length; i++) {
			ea[i].eAdapters().remove(this);
		}
	}

	public void dispose() {
		clearAdapters();
	}

	public Object[] getChildren(Object object) {
		return getElements(object);
	}
	
	public Object[] getElements(Object element) {
		List elements;
		if (element instanceof Tasklist) {
			elements = ((Tasklist) element).getTasks();
		} else {
			return new Object[0];
		}
		
		for(Iterator iter = elements.iterator(); iter.hasNext(); ) {
			addAdapter((Task) iter.next());
		}
		
		return elements.isEmpty() ? new Object[0] : elements.toArray();
	}
	public Object getParent(Object object) {
		return ((Task) object).eContainer();
	}
	
	public boolean hasChildren(Object object) {
		return false;
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		clearAdapters();
		if(newInput != null) {
			addAdapter((EObject) newInput);
		}
	}

	
	protected abstract void add(Task task);
	protected abstract void add(Task[] tasks);
	protected abstract void clear(Task task);
	protected abstract void refresh();
	protected abstract void refresh(boolean update);
	protected abstract void refresh(Task task);
	protected abstract void refresh(Task[] tasks);
	protected abstract void remove(Task task);
	protected abstract void remove(Task[] tasks);
	protected abstract void update(Task task, String[] properties);
	protected abstract void update(Task[] tasks, String[] properties);
	
	@Override
	public void notifyChanged(Notification notification) {
		if(!notification.isTouch() && !Display.getDefault().isDisposed()) {
			final Notification n = notification;
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					int type = n.getEventType();
					int featureId;
					switch (type) {
					case Notification.ADD:
//					case Notification.ADD_MANY:
						if(n.getNotifier() instanceof Tasklist &&
								n.getNewValue() instanceof Task &&
								!AbstractTaskContentProvider.this.eObjects.contains(n.getNewValue())) {
							
								Task task = (Task) n.getNewValue();
								addAdapter(task);
								add(task);
							}
						break;
					case Notification.MOVE:
						if(n.getNotifier() instanceof Task) {
							update(((Task) n.getNotifier()), 
									new String[] { ModelPackage.eINSTANCE.getTask().getName() } );
						}
						break;
					case Notification.REMOVE:
//					case Notification.REMOVE_MANY:
						if(n.getNotifier() instanceof Tasklist &&
								n.getOldValue() instanceof Task &&
								AbstractTaskContentProvider.this.eObjects.contains(n.getOldValue())) {
								Task task = (Task) n.getOldValue();
								removeAdapter(task);
								remove(task);
							
						}
						break;
					case Notification.SET:
						if(n.getNotifier() instanceof Task) {
							featureId = n.getFeatureID(Task.class);
							if(featureId == ModelPackage.TASK__START || featureId == ModelPackage.TASK__END) {
								update(((Task) n.getNotifier()), 
										new String[] { 
										ModelPackage.eINSTANCE.getTask_Start().getName(),
										ModelPackage.eINSTANCE.getTask_End().getName(),
										} );
							} else if(featureId == ModelPackage.TASK__CLEARED) {
								clear( (Task) n.getNotifier());
							}
						} else if(n.getNotifier() instanceof CEvent) {
							featureId = n.getFeatureID(CEvent.class);
							if(featureId == ModelPackage.CEVENT__ALARM_DUE) {
								Task task = (Task) ((EObject) n.getNotifier()).eContainer();
								if(task != null) {
									update(task, new String[] { ModelPackage.eINSTANCE.getCEvent_AlarmDue().getName() } );
								}
							} else if(featureId == ModelPackage.CEVENT__DATE) {
								if(n.getNotifier() instanceof StartEvent) {
									Task task = ((StartEvent) n.getNotifier()).getTask();
									if(task != null) {
										update(task, new String[] { ModelPackage.eINSTANCE.getTask_Start().getName() } );
										refresh(false);
									}
								} else if(n.getNotifier() instanceof EndEvent) {
									Task task = ((EndEvent) n.getNotifier()).getTask();
									if(task != null) {
										update(task, new String[] { ModelPackage.eINSTANCE.getTask_End().getName() } );
										refresh(false);
									}
								}
							}
						}
						break;
					default:
						if(n.getNotifier() instanceof Task) {
							update( (Task) n.getNotifier(), 
									new String[] { ModelPackage.eINSTANCE.getTask().getName() } );
						}
						break;
					}
				}
			});
		}
	}
}
