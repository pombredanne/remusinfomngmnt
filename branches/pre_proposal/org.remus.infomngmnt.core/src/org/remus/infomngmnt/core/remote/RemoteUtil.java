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

package org.remus.infomngmnt.core.remote;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.remus.infomngmnt.Adapter;
import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.RemoteContainer;
import org.remus.infomngmnt.RemoteObject;
import org.remus.infomngmnt.RemoteRepository;
import org.remus.infomngmnt.SynchronizationMetadata;
import org.remus.infomngmnt.common.core.util.CollectionFilter;
import org.remus.infomngmnt.common.core.util.CollectionUtils;
import org.remus.infomngmnt.common.core.util.ModelUtil;
import org.remus.infomngmnt.core.services.IRepositoryService;
import org.remus.infomngmnt.provider.InfomngmntEditPlugin;

/**
 * Utility class for {@link RemoteRepository}, {@link RemoteContainer}
 * and {@link RemoteObject}. While synchronizing with remote Information
 * end-points it's necessary to prepare some objects. This class provides
 * some helper methods which are used in different contexts. 
 * @author Tom Seidel <tom.seidel@remus-software.org>
 * @noextend This class is not intended to be subclassed by clients.
 * @since 1.0
 */
public class RemoteUtil {
	
	/**
	 * Searches in the object hierarchy for the {@link RemoteRepository}
	 * from which the {@link RemoteObject} comes from.
	 * @param object the object
	 * @return the local repository definition if present, else <code>null</code>.
	 * @since 1.0
	 */
	public static RemoteRepository getRemoteRepository(final RemoteObject object) {
		if (object instanceof RemoteRepository) {
			return (RemoteRepository) object;
		} else if (object.eContainer() != null
				&& object.eContainer() instanceof RemoteObject) {
			return getRemoteRepository((RemoteObject) object.eContainer());
		}
		return null;
	}

	
	/**
	 * @param list
	 * @return
	 */
	public static Map<RemoteRepository, List<Adapter>> groupByRemoteRepsoitory(final List<Adapter> list) {
		final Map<RemoteRepository, List<Adapter>> returnValue = new HashMap<RemoteRepository, List<Adapter>>();
		for (Adapter object : list) {
			if (object.getAdapter(SynchronizationMetadata.class) != null) {
				SynchronizationMetadata metadata = (SynchronizationMetadata) object.getAdapter(SynchronizationMetadata.class);
				String repositoryId = metadata.getRepositoryId();
				RemoteRepository localRepository = InfomngmntEditPlugin.getPlugin().getService(IRepositoryService.class).getRepositoryById(repositoryId);
				
				if (!returnValue.containsKey(localRepository)) {
					returnValue.put(localRepository, new ArrayList<Adapter>());	
				} 
				returnValue.get(localRepository).add(object);
			}
		}
		/*
		 * we have to eliminate all child containments  
		 */
		Set<RemoteRepository> keySet = returnValue.keySet();
		for (final RemoteRepository remoteRepository : keySet) {
			List<Adapter> filteredList = CollectionUtils.filter(returnValue.get(remoteRepository), new CollectionFilter<Adapter>() {
				public boolean select(final Adapter item) {
					return !ModelUtil.containsParent(returnValue.get(remoteRepository), item)
						|| item instanceof Category;
				}
			});
			returnValue.get(remoteRepository).clear();
			returnValue.get(remoteRepository).addAll(filteredList);
		}
		return returnValue;
		
	
		
	}

}
