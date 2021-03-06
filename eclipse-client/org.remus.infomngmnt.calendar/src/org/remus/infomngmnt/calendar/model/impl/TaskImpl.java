/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.remus.infomngmnt.calendar.model.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.remus.infomngmnt.calendar.model.ClearedEvent;
import org.remus.infomngmnt.calendar.model.DueEvent;
import org.remus.infomngmnt.calendar.model.EndEvent;
import org.remus.infomngmnt.calendar.model.ModelPackage;
import org.remus.infomngmnt.calendar.model.StartEvent;
import org.remus.infomngmnt.calendar.model.Task;
import org.remus.infomngmnt.calendar.model.TaskType;
import org.remus.infomngmnt.calendar.model.Tasklist;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Task</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.remus.infomngmnt.calendar.model.impl.TaskImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.remus.infomngmnt.calendar.model.impl.TaskImpl#getPriority <em>Priority</em>}</li>
 *   <li>{@link org.remus.infomngmnt.calendar.model.impl.TaskImpl#getDetails <em>Details</em>}</li>
 *   <li>{@link org.remus.infomngmnt.calendar.model.impl.TaskImpl#getOwner <em>Owner</em>}</li>
 *   <li>{@link org.remus.infomngmnt.calendar.model.impl.TaskImpl#getStart <em>Start</em>}</li>
 *   <li>{@link org.remus.infomngmnt.calendar.model.impl.TaskImpl#getEnd <em>End</em>}</li>
 *   <li>{@link org.remus.infomngmnt.calendar.model.impl.TaskImpl#getDue <em>Due</em>}</li>
 *   <li>{@link org.remus.infomngmnt.calendar.model.impl.TaskImpl#getCleared <em>Cleared</em>}</li>
 *   <li>{@link org.remus.infomngmnt.calendar.model.impl.TaskImpl#getProgress <em>Progress</em>}</li>
 *   <li>{@link org.remus.infomngmnt.calendar.model.impl.TaskImpl#getId <em>Id</em>}</li>
 *   <li>{@link org.remus.infomngmnt.calendar.model.impl.TaskImpl#getType <em>Type</em>}</li>
 *   <li>{@link org.remus.infomngmnt.calendar.model.impl.TaskImpl#getNotification <em>Notification</em>}</li>
 *   <li>{@link org.remus.infomngmnt.calendar.model.impl.TaskImpl#isReadonly <em>Readonly</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TaskImpl extends EObjectImpl implements Task {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getPriority() <em>Priority</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPriority()
	 * @generated
	 * @ordered
	 */
	protected static final int PRIORITY_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getPriority() <em>Priority</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPriority()
	 * @generated
	 * @ordered
	 */
	protected int priority = PRIORITY_EDEFAULT;

	/**
	 * The default value of the '{@link #getDetails() <em>Details</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDetails()
	 * @generated
	 * @ordered
	 */
	protected static final String DETAILS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDetails() <em>Details</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDetails()
	 * @generated
	 * @ordered
	 */
	protected String details = DETAILS_EDEFAULT;

	/**
	 * The cached value of the '{@link #getStart() <em>Start</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStart()
	 * @generated
	 * @ordered
	 */
	protected StartEvent start;

	/**
	 * The cached value of the '{@link #getEnd() <em>End</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEnd()
	 * @generated
	 * @ordered
	 */
	protected EndEvent end;

	/**
	 * The cached value of the '{@link #getDue() <em>Due</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDue()
	 * @generated
	 * @ordered
	 */
	protected DueEvent due;

	/**
	 * The cached value of the '{@link #getCleared() <em>Cleared</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCleared()
	 * @generated
	 * @ordered
	 */
	protected ClearedEvent cleared;

	/**
	 * The default value of the '{@link #getProgress() <em>Progress</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProgress()
	 * @generated
	 * @ordered
	 */
	protected static final double PROGRESS_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getProgress() <em>Progress</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProgress()
	 * @generated
	 * @ordered
	 */
	protected double progress = PROGRESS_EDEFAULT;

	/**
	 * The default value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected static final String ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected String id = ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final TaskType TYPE_EDEFAULT = TaskType.ONE_TIME;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected TaskType type = TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getNotification() <em>Notification</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNotification()
	 * @generated
	 * @ordered
	 */
	protected static final int NOTIFICATION_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getNotification() <em>Notification</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNotification()
	 * @generated
	 * @ordered
	 */
	protected int notification = NOTIFICATION_EDEFAULT;

	/**
	 * The default value of the '{@link #isReadonly() <em>Readonly</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isReadonly()
	 * @generated
	 * @ordered
	 */
	protected static final boolean READONLY_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isReadonly() <em>Readonly</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isReadonly()
	 * @generated
	 * @ordered
	 */
	protected boolean readonly = READONLY_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TaskImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.TASK;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.TASK__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getPriority() {
		return priority;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPriority(int newPriority) {
		int oldPriority = priority;
		priority = newPriority;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.TASK__PRIORITY, oldPriority, priority));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDetails() {
		return details;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDetails(String newDetails) {
		String oldDetails = details;
		details = newDetails;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.TASK__DETAILS, oldDetails, details));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Tasklist getOwner() {
		if (eContainerFeatureID() != ModelPackage.TASK__OWNER) return null;
		return (Tasklist)eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetOwner(Tasklist newOwner, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newOwner, ModelPackage.TASK__OWNER, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOwner(Tasklist newOwner) {
		if (newOwner != eInternalContainer() || (eContainerFeatureID() != ModelPackage.TASK__OWNER && newOwner != null)) {
			if (EcoreUtil.isAncestor(this, newOwner))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newOwner != null)
				msgs = ((InternalEObject)newOwner).eInverseAdd(this, ModelPackage.TASKLIST__TASKS, Tasklist.class, msgs);
			msgs = basicSetOwner(newOwner, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.TASK__OWNER, newOwner, newOwner));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StartEvent getStart() {
		return start;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetStart(StartEvent newStart, NotificationChain msgs) {
		StartEvent oldStart = start;
		start = newStart;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ModelPackage.TASK__START, oldStart, newStart);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStart(StartEvent newStart) {
		if (newStart != start) {
			NotificationChain msgs = null;
			if (start != null)
				msgs = ((InternalEObject)start).eInverseRemove(this, ModelPackage.START_EVENT__TASK, StartEvent.class, msgs);
			if (newStart != null)
				msgs = ((InternalEObject)newStart).eInverseAdd(this, ModelPackage.START_EVENT__TASK, StartEvent.class, msgs);
			msgs = basicSetStart(newStart, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.TASK__START, newStart, newStart));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EndEvent getEnd() {
		return end;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEnd(EndEvent newEnd, NotificationChain msgs) {
		EndEvent oldEnd = end;
		end = newEnd;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ModelPackage.TASK__END, oldEnd, newEnd);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEnd(EndEvent newEnd) {
		if (newEnd != end) {
			NotificationChain msgs = null;
			if (end != null)
				msgs = ((InternalEObject)end).eInverseRemove(this, ModelPackage.END_EVENT__TASK, EndEvent.class, msgs);
			if (newEnd != null)
				msgs = ((InternalEObject)newEnd).eInverseAdd(this, ModelPackage.END_EVENT__TASK, EndEvent.class, msgs);
			msgs = basicSetEnd(newEnd, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.TASK__END, newEnd, newEnd));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DueEvent getDue() {
		return due;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDue(DueEvent newDue, NotificationChain msgs) {
		DueEvent oldDue = due;
		due = newDue;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ModelPackage.TASK__DUE, oldDue, newDue);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDue(DueEvent newDue) {
		if (newDue != due) {
			NotificationChain msgs = null;
			if (due != null)
				msgs = ((InternalEObject)due).eInverseRemove(this, ModelPackage.DUE_EVENT__TASK, DueEvent.class, msgs);
			if (newDue != null)
				msgs = ((InternalEObject)newDue).eInverseAdd(this, ModelPackage.DUE_EVENT__TASK, DueEvent.class, msgs);
			msgs = basicSetDue(newDue, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.TASK__DUE, newDue, newDue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ClearedEvent getCleared() {
		return cleared;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCleared(ClearedEvent newCleared, NotificationChain msgs) {
		ClearedEvent oldCleared = cleared;
		cleared = newCleared;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ModelPackage.TASK__CLEARED, oldCleared, newCleared);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCleared(ClearedEvent newCleared) {
		if (newCleared != cleared) {
			NotificationChain msgs = null;
			if (cleared != null)
				msgs = ((InternalEObject)cleared).eInverseRemove(this, ModelPackage.CLEARED_EVENT__TASK, ClearedEvent.class, msgs);
			if (newCleared != null)
				msgs = ((InternalEObject)newCleared).eInverseAdd(this, ModelPackage.CLEARED_EVENT__TASK, ClearedEvent.class, msgs);
			msgs = basicSetCleared(newCleared, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.TASK__CLEARED, newCleared, newCleared));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getProgress() {
		return progress;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProgress(double newProgress) {
		double oldProgress = progress;
		progress = newProgress;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.TASK__PROGRESS, oldProgress, progress));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getId() {
		return id;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setId(String newId) {
		String oldId = id;
		id = newId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.TASK__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TaskType getType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(TaskType newType) {
		TaskType oldType = type;
		type = newType == null ? TYPE_EDEFAULT : newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.TASK__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getNotification() {
		return notification;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNotification(int newNotification) {
		int oldNotification = notification;
		notification = newNotification;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.TASK__NOTIFICATION, oldNotification, notification));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isReadonly() {
		return readonly;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReadonly(boolean newReadonly) {
		boolean oldReadonly = readonly;
		readonly = newReadonly;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.TASK__READONLY, oldReadonly, readonly));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ModelPackage.TASK__OWNER:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetOwner((Tasklist)otherEnd, msgs);
			case ModelPackage.TASK__START:
				if (start != null)
					msgs = ((InternalEObject)start).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ModelPackage.TASK__START, null, msgs);
				return basicSetStart((StartEvent)otherEnd, msgs);
			case ModelPackage.TASK__END:
				if (end != null)
					msgs = ((InternalEObject)end).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ModelPackage.TASK__END, null, msgs);
				return basicSetEnd((EndEvent)otherEnd, msgs);
			case ModelPackage.TASK__DUE:
				if (due != null)
					msgs = ((InternalEObject)due).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ModelPackage.TASK__DUE, null, msgs);
				return basicSetDue((DueEvent)otherEnd, msgs);
			case ModelPackage.TASK__CLEARED:
				if (cleared != null)
					msgs = ((InternalEObject)cleared).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ModelPackage.TASK__CLEARED, null, msgs);
				return basicSetCleared((ClearedEvent)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ModelPackage.TASK__OWNER:
				return basicSetOwner(null, msgs);
			case ModelPackage.TASK__START:
				return basicSetStart(null, msgs);
			case ModelPackage.TASK__END:
				return basicSetEnd(null, msgs);
			case ModelPackage.TASK__DUE:
				return basicSetDue(null, msgs);
			case ModelPackage.TASK__CLEARED:
				return basicSetCleared(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case ModelPackage.TASK__OWNER:
				return eInternalContainer().eInverseRemove(this, ModelPackage.TASKLIST__TASKS, Tasklist.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ModelPackage.TASK__NAME:
				return getName();
			case ModelPackage.TASK__PRIORITY:
				return getPriority();
			case ModelPackage.TASK__DETAILS:
				return getDetails();
			case ModelPackage.TASK__OWNER:
				return getOwner();
			case ModelPackage.TASK__START:
				return getStart();
			case ModelPackage.TASK__END:
				return getEnd();
			case ModelPackage.TASK__DUE:
				return getDue();
			case ModelPackage.TASK__CLEARED:
				return getCleared();
			case ModelPackage.TASK__PROGRESS:
				return getProgress();
			case ModelPackage.TASK__ID:
				return getId();
			case ModelPackage.TASK__TYPE:
				return getType();
			case ModelPackage.TASK__NOTIFICATION:
				return getNotification();
			case ModelPackage.TASK__READONLY:
				return isReadonly();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ModelPackage.TASK__NAME:
				setName((String)newValue);
				return;
			case ModelPackage.TASK__PRIORITY:
				setPriority((Integer)newValue);
				return;
			case ModelPackage.TASK__DETAILS:
				setDetails((String)newValue);
				return;
			case ModelPackage.TASK__OWNER:
				setOwner((Tasklist)newValue);
				return;
			case ModelPackage.TASK__START:
				setStart((StartEvent)newValue);
				return;
			case ModelPackage.TASK__END:
				setEnd((EndEvent)newValue);
				return;
			case ModelPackage.TASK__DUE:
				setDue((DueEvent)newValue);
				return;
			case ModelPackage.TASK__CLEARED:
				setCleared((ClearedEvent)newValue);
				return;
			case ModelPackage.TASK__PROGRESS:
				setProgress((Double)newValue);
				return;
			case ModelPackage.TASK__ID:
				setId((String)newValue);
				return;
			case ModelPackage.TASK__TYPE:
				setType((TaskType)newValue);
				return;
			case ModelPackage.TASK__NOTIFICATION:
				setNotification((Integer)newValue);
				return;
			case ModelPackage.TASK__READONLY:
				setReadonly((Boolean)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case ModelPackage.TASK__NAME:
				setName(NAME_EDEFAULT);
				return;
			case ModelPackage.TASK__PRIORITY:
				setPriority(PRIORITY_EDEFAULT);
				return;
			case ModelPackage.TASK__DETAILS:
				setDetails(DETAILS_EDEFAULT);
				return;
			case ModelPackage.TASK__OWNER:
				setOwner((Tasklist)null);
				return;
			case ModelPackage.TASK__START:
				setStart((StartEvent)null);
				return;
			case ModelPackage.TASK__END:
				setEnd((EndEvent)null);
				return;
			case ModelPackage.TASK__DUE:
				setDue((DueEvent)null);
				return;
			case ModelPackage.TASK__CLEARED:
				setCleared((ClearedEvent)null);
				return;
			case ModelPackage.TASK__PROGRESS:
				setProgress(PROGRESS_EDEFAULT);
				return;
			case ModelPackage.TASK__ID:
				setId(ID_EDEFAULT);
				return;
			case ModelPackage.TASK__TYPE:
				setType(TYPE_EDEFAULT);
				return;
			case ModelPackage.TASK__NOTIFICATION:
				setNotification(NOTIFICATION_EDEFAULT);
				return;
			case ModelPackage.TASK__READONLY:
				setReadonly(READONLY_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ModelPackage.TASK__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case ModelPackage.TASK__PRIORITY:
				return priority != PRIORITY_EDEFAULT;
			case ModelPackage.TASK__DETAILS:
				return DETAILS_EDEFAULT == null ? details != null : !DETAILS_EDEFAULT.equals(details);
			case ModelPackage.TASK__OWNER:
				return getOwner() != null;
			case ModelPackage.TASK__START:
				return start != null;
			case ModelPackage.TASK__END:
				return end != null;
			case ModelPackage.TASK__DUE:
				return due != null;
			case ModelPackage.TASK__CLEARED:
				return cleared != null;
			case ModelPackage.TASK__PROGRESS:
				return progress != PROGRESS_EDEFAULT;
			case ModelPackage.TASK__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case ModelPackage.TASK__TYPE:
				return type != TYPE_EDEFAULT;
			case ModelPackage.TASK__NOTIFICATION:
				return notification != NOTIFICATION_EDEFAULT;
			case ModelPackage.TASK__READONLY:
				return readonly != READONLY_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (name: ");
		result.append(name);
		result.append(", priority: ");
		result.append(priority);
		result.append(", details: ");
		result.append(details);
		result.append(", progress: ");
		result.append(progress);
		result.append(", id: ");
		result.append(id);
		result.append(", type: ");
		result.append(type);
		result.append(", notification: ");
		result.append(notification);
		result.append(", readonly: ");
		result.append(readonly);
		result.append(')');
		return result.toString();
	}

} //TaskImpl
