/****************************************************************************
 * Copyright (c) 2005-2006 Jeremy Dowdall
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Jeremy Dowdall <aspencloud@users.sourceforge.net> - initial API and implementation
 *****************************************************************************/

package org.aspencloud.calypso.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;

public class CalypsoEdit {
	
	public static final int UP = 0;
	public static final int DOWN = 1;
	public static final int LEFT = 2;
	public static final int RIGHT = 3;
	
	
	public static void delete(final EObject eObject) {
		delete(Collections.singleton(eObject));
	}
	public static void delete(final EObject[] eObjects) {
		delete(Arrays.asList(eObjects));
	}
	public static void delete(final Collection eObjects) {
		delete(eObjects, null);
	}
	static void delete(final Collection eObjects, final ResourceSet rset) {
		boolean stopAutoSave = (rset == null);
		try {
			if(stopAutoSave) {
//				rset = CalypsoManager.getManager().getResourceSet();
//				CalypsoManager.getAutoSave().setActive(false);
			}
	
		// create the list of EVERYTHING to be removed
			List items = new ArrayList();
			// find all explicit items
			for(Iterator iter = eObjects.iterator(); iter.hasNext(); ) {
				Object o = iter.next();
				
			}
			// add all contained items
			List list = new ArrayList();
			for(Iterator iter = eObjects.iterator(); iter.hasNext(); ) {
				Object o = iter.next();
				list.add(o);
				list.addAll(((EObject) o).eContents());
			}
	
		// remove ALL references to ALL objects 
			Map usages = EcoreUtil.UsageCrossReferencer.findAll(list, rset);
			for(Iterator iter = usages.entrySet().iterator(); iter.hasNext(); ) {
				Map.Entry entry = (Map.Entry)iter.next();
				EObject eObject = (EObject)entry.getKey();
				Collection settings = (Collection)entry.getValue();
				for(Iterator j = settings.iterator(); j.hasNext(); ) {
					EStructuralFeature.Setting setting = (EStructuralFeature.Setting) j.next();
					EcoreUtil.remove(setting, eObject);
				}
			}
	
		// at last! remove the objects
			for(Iterator iter = list.iterator(); iter.hasNext(); ) {
				EcoreUtil.remove((EObject) iter.next());
			}
		} finally {
			if(stopAutoSave) {
				//CalypsoManager.getAutoSave().setActive(true);
			}
		}
	}
	
	
//	public static String[] ChannelNames() {
//		ChannelPackage cp = ChannelPackage.eINSTANCE;
//		List l = cp.getEClassifiers();
//		String[] names = new String[l.size() - 3];
//		int j = 0;
//		for(Iterator i = l.iterator(); i.hasNext(); ) {
//			EClass ec = (EClass) i.next();
//			if(ec != cp.getChannel() && ec != cp.getChannelSpace() && ec != cp.getChannelWrapper()) {
//				names[j] = ec.getName();
//				int k = names[j].indexOf("Channel");
//				names[j] = names[j].substring(0, k);
//				j++;
//			}
//		}
//		return new String[0];
//	}
	
	
	

}
