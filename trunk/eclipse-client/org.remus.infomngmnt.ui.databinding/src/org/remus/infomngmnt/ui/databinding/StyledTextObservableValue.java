package org.remus.infomngmnt.ui.databinding;

import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.jface.databinding.swt.WidgetValueProperty;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;

/**
 * {@link IObservable} implementation that wraps a {@link StyledText} widget. The time
 * at which listeners should be notified about changes to the text is specified
 * on construction.
 * 
 * <dl>
 * <dt>Events:</dt>
 * <dd> If the update event type (specified on construction) is
 * <code>SWT.Modify</code> a value change event will be fired on every key
 * stroke. If the update event type is <code>SWT.FocusOut</code> a value
 * change event will be fired on focus out. When in either mode if the user is
 * entering text and presses [Escape] the value will be reverted back to the
 * last value set using doSetValue(). Regardless of the update event type a
 * value changing event will fire on verify to enable vetoing of changes.</dd>
 * </dl>
 * 
 * @since 1.0
 */
public class StyledTextObservableValue extends WidgetValueProperty {

		
	public StyledTextObservableValue() {
		super(SWT.Modify);
	}

	

	/**
	 * Returns the type of the value from {@link #doGetValue()}, i.e.
	 * String.class
	 * 
	 * @see org.eclipse.core.databinding.observable.value.IObservableValue#getValueType()
	 */
	public Object getValueType() {
		return String.class;
	}

	@Override
	protected Object doGetValue(Object source) {
		return ((StyledText) source).getText();
	}

	@Override
	protected void doSetValue(Object source, Object value) {
		((StyledText) source).setText((String) (value == null? "" : value));
		
	}

	
}
