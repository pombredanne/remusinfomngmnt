/*******************************************************************************
 * Copyright (c) 2008 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/
package org.remus.infomngmnt.core.remote;

import java.util.List;

/**
 * <p>
 * An interface that defines the getting of a list of strings with object paths
 * to the sycnhronizing relevant items within an information unit.
 * </p>
 * <p>
 * The problem is the following: The repository doesn't support (mostly) all
 * data that the appendend information unit can handle, e.g. a delicious link
 * doesn't know anything about a link-related event. So this interface describes
 * how to get the list of relevant object paths.
 * </p>
 * 
 * 
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public interface IChangeSetDefinition {

	/**
	 * Returns a list of the relevant object path that are included on
	 * synchronization.
	 * 
	 * @return
	 */
	List<String> getRelevantObjectPaths();

	List<String> getRelevantObjectPathValues();

	List<String> getRelevantObjectIds();

	List<String> getRelevantObjectIdValues();

}
