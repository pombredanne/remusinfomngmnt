package org.remus.infomngmnt.common.ui.databinding;

import org.eclipse.jface.databinding.swt.WidgetValueProperty;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class CheckBoxObservableValue extends WidgetValueProperty {

	public CheckBoxObservableValue() {
		super(SWT.Selection);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.internal.databinding.provisional.swt.
	 * AbstractSWTObservableValue#getValueType()
	 */

	public Object getValueType() {
		return String.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.databinding.observable.value.AbstractObservableValue
	 * #doGetValue()
	 */
	@Override
	protected Object doGetValue(Object source) {
		return String.valueOf(((Button) source).getSelection());
	}

	@Override
	protected void doSetValue(final Object source, Object value) {
		((Button) source).setSelection(Boolean.valueOf((String) value));
	}
}
