package org.remus.infomngmnt.ccalendar;

import org.eclipse.core.runtime.ListenerList;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.jface.viewers.IOpenListener;
import org.eclipse.jface.viewers.OpenEvent;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.remus.infomngmnt.calendar.model.Task;

public class CalendarViewer extends ScrollingGraphicalViewer {

	/**
	 * List of open listeners (element type:
	 * <code>ISelectionActivateListener</code>).
	 * 
	 * @see #fireOpen
	 */
	private final ListenerList openListeners = new ListenerList();
	
	/**
	 * Adds a listener for selection-open in this viewer. Has no effect if an
	 * identical listener is already registered.
	 * 
	 * @param listener
	 *            a double-click listener
	 */
	public void addOpenListener(Listener listener) {
		this.openListeners.add(listener);
	}

	/**
	 * Notifies any open event listeners that a open event has been received.
	 * Only listeners registered at the time this method is called are notified.
	 * 
	 * @param event
	 *            a double-click event
	 * 
	 * @see IOpenListener#open(OpenEvent)
	 */
	protected void fireOpen(final Event event) {
		Object[] listeners = this.openListeners.getListeners();
		for (int i = 0; i < listeners.length; ++i) {
			final Listener l = (Listener) listeners[i];
			SafeRunnable.run(new SafeRunnable() {
				public void run() {
					l.handleEvent(event);
				}
			});
		}
	}
	
	public void performOpen(Task task){
		Event event = new Event();
		event.data = task;
		fireOpen(event);
	}
}
