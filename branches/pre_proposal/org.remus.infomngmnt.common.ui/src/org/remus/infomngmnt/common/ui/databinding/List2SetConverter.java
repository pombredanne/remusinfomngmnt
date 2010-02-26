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

import org.eclipse.core.databinding.observable.list.IListChangeListener;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.list.ListChangeEvent;
import org.eclipse.core.databinding.observable.list.ListDiffVisitor;
import org.eclipse.core.databinding.observable.set.IObservableSet;
import org.eclipse.core.databinding.observable.set.ISetChangeListener;
import org.eclipse.core.databinding.observable.set.SetChangeEvent;
import org.eclipse.core.databinding.observable.set.SetDiff;
import org.eclipse.core.databinding.observable.set.WritableSet;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class List2SetConverter {

	public static IObservableSet create(final IObservableList source) {
		final boolean[] flag = new boolean[] { false };
		final IObservableSet set = new WritableSet(source, null);
		source.addListChangeListener(new IListChangeListener() {
			public void handleListChange(final ListChangeEvent event) {
				if (flag[0]) {
					return;
				}
				flag[0] = true;

				event.diff.accept(new ListDiffVisitor() {
					@Override
					public void handleRemove(final int index, final Object element) {
						set.remove(element);
					}

					@Override
					public void handleAdd(final int index, final Object element) {
						set.add(element);
					}
				});
				flag[0] = false;
			}
		});
		set.addSetChangeListener(new ISetChangeListener() {

			public void handleSetChange(final SetChangeEvent event) {
				if (flag[0]) {
					return;
				}

				flag[0] = true;
				SetDiff diff = event.diff;
				source.addAll(diff.getAdditions());
				source.removeAll(diff.getRemovals());
				flag[0] = false;
			}
		});

		return set;
	}

}
