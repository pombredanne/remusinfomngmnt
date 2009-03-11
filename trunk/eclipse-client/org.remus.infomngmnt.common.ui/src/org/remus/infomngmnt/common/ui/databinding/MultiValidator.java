/*******************************************************************************
 * Copyright (c) 2009 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/

package org.remus.infomngmnt.common.ui.databinding;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IStatus;

/**
 * A Validator which wrapps a list of other validators. On validation the list
 * is iterated and executes all known iterators. If one validation fails, the
 * validation is canceled and the result of the failing validator is returned.
 * 
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class MultiValidator implements IValidator {

	private final List<IValidator> validators;

	public MultiValidator() {
		this.validators = new LinkedList<IValidator>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.databinding.validation.IValidator#validate(java.lang
	 * .Object)
	 */
	public IStatus validate(final Object value) {
		for (IValidator validator : this.validators) {
			IStatus validate = validator.validate(value);
			if (!validate.isOK()) {
				return validate;
			}
		}
		return ValidationStatus.ok();
	}

	/**
	 * @param element
	 * @return
	 * @see org.eclipse.core.databinding.observable.list.WritableList#add(java.lang.Object)
	 */
	public boolean add(final IValidator element) {
		return this.validators.add(element);
	}

	/**
	 * 
	 * @see org.eclipse.core.databinding.observable.list.WritableList#clear()
	 */
	public void clear() {
		this.validators.clear();
	}

	/**
	 * @param o
	 * @return
	 * @see org.eclipse.core.databinding.observable.list.WritableList#remove(java.lang.Object)
	 */
	public boolean remove(final IValidator o) {
		return this.validators.remove(o);
	}

}
