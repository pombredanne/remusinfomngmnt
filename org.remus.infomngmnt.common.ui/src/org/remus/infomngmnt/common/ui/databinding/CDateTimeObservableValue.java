package org.remus.infomngmnt.common.ui.databinding;

import java.util.Date;

import org.eclipse.jface.databinding.swt.WidgetValueProperty;
import org.eclipse.nebula.widgets.cdatetime.CDateTime;
import org.eclipse.swt.SWT;

/**
 * An implementation of the DataBindings IObservableValue interface for the
 * Nebula CDateTime control.
 * 
 * @author pcentgraf
 * @since Mar 8, 2007
 */
public class CDateTimeObservableValue extends WidgetValueProperty {

	public CDateTimeObservableValue() {
		super(SWT.Modify);

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
	protected Object doGetValue(final Object source) {
		return ((CDateTime) source).getSelection();
	}

	@Override
	protected void doSetValue(final Object source, final Object value) {
		((CDateTime) source).setSelection((Date) value);
	}
}
