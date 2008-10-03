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

package org.remus.infomngmnt.core.model;

import org.eclipse.core.runtime.IPath;
import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.LinkType;
import org.remus.infomngmnt.LinkTypeCollection;
import org.remus.infomngmnt.provider.InfomngmntEditPlugin;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 * @noextend This class is not intended to be subclassed by clients.
 */
public class LinkUtil {

	public static final String LINK_TYPE_STORE_FILE_NAME = "availableLinkTypes.xml"; //$NON-NLS-1$
	private static LinkUtil INSTANCE;
	private final LinkTypeCollection object;

	public static LinkUtil getInstance() {
		if (LinkUtil.INSTANCE == null) {
			synchronized (LinkUtil.class) {
				if (LinkUtil.INSTANCE == null) {
					LinkUtil.INSTANCE = new LinkUtil();
				}
			}
		}
		return LinkUtil.INSTANCE;
	}

	/**
	 * <p>
	 * The constructor reads from {@link #LINK_TYPE_STORE_FILE_NAME} file
	 * all available link types that are stored within the
	 * plugins state location.
	 * </p>
	 * <p>
	 * It is required to have at least one link type. So if there is no link
	 * type available, a non editable "general" linktype will be created.
	 * </p>
	 */
	private LinkUtil() {
		IPath stateLocation = InfomngmntEditPlugin.getPlugin().getStateLocation();
		this.object = EditingUtil.getInstance().getObjectFromUri(stateLocation.append(LINK_TYPE_STORE_FILE_NAME),InfomngmntPackage.eINSTANCE.getLinkTypeCollection(),false);
		// required is at leas one type. if there is no type we have to create one.
		if (this.object.getAvailableLinkTypes().size() == 0) {
			LinkType createLinkType = InfomngmntFactory.eINSTANCE.createLinkType();
			createLinkType.setDescription("General");
			createLinkType.setId("_general");
			addLinkType(createLinkType);
		}
	}

	public void addLinkType(LinkType createLinkType) {
		this.object.getAvailableLinkTypes().put(createLinkType.getId(), createLinkType);
	}

	public LinkTypeCollection getLinkTypes() {
		return this.object;
	}

}
