package org.remus.infomngmnt.common.ui.databinding;

import java.util.Date;

import org.eclipse.core.databinding.observable.Diffs;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.internal.databinding.provisional.swt.AbstractSWTObservableValue;
import org.eclipse.nebula.widgets.cdatetime.CDateTime;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

/**
 * An implementation of the DataBindings IObservableValue interface for the
 * Nebula CDateTime control.
 * 
 * @author pcentgraf
 * @since Mar 8, 2007
 */
public class CDateTimeObservableValue extends AbstractSWTObservableValue implements
		IObservableValue {

	/**
	 * The Control being observed here.
	 */
	protected final CDateTime dateTime;

	/**
	 * Flag to prevent infinite recursion in {@link #doSetValue(Object)}.
	 */
	protected boolean updating = false;

	/**
	 * The "old" selection before a selection event is fired.
	 */
	protected Date currentSelection;

	/**
	 * Observe the selection property of the provided CDateTime control.
	 * 
	 * @param dateTime
	 *            the control to observe
	 */
	public CDateTimeObservableValue(final CDateTime dateTime) {
		super(dateTime);
		this.dateTime = dateTime;
		this.currentSelection = dateTime.getSelection();
		dateTime.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(final SelectionEvent e) {
				update(e);
			}

			public void widgetSelected(final SelectionEvent e) {
				update(e);
			}

			public void update(final SelectionEvent e) {
				if (!CDateTimeObservableValue.this.updating) {
					Date newSelection = CDateTimeObservableValue.this.dateTime.getSelection();
					fireValueChange(Diffs.createValueDiff(
							CDateTimeObservableValue.this.currentSelection, newSelection));
					CDateTimeObservableValue.this.currentSelection = newSelection;
				}
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.jface.internal.databinding.provisional.swt.
	 * AbstractSWTObservableValue#getValueType()
	 */

	public Object getValueType() {
		return Date.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.databinding.observable.value.AbstractObservableValue
	 * #doGetValue()
	 */
	@Override
	protected Object doGetValue() {
		return this.dateTime.getSelection();
	}

	@Override
	protected void doSetValue(final Object value) {
		Date oldValue;
		Date newValue;
		try {
			this.updating = true;
			oldValue = this.dateTime.getSelection();
			newValue = (Date) value;
			this.dateTime.setSelection(newValue);
			this.currentSelection = newValue;
			fireValueChange(Diffs.createValueDiff(oldValue, newValue));
		} finally {
			this.updating = false;
		}
	}
}
