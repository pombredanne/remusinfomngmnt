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

package org.remus.infomngmnt.oda.core;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.eclipse.datatools.connectivity.oda.OdaException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl;
import org.eclipse.remus.common.core.streams.StringOutputStream;

import org.remus.oda.Dataset;
import org.remus.oda.OdaFactory;
import org.remus.oda.OdaPackage;
import org.remus.oda.Selection;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class QuerySerializer {

	public static Map<String, Object> SAVE_OPTIONS;

	static {
		SAVE_OPTIONS = new HashMap<String, Object>();
		SAVE_OPTIONS.put(XMLResource.OPTION_ENCODING, "UTF-8");
		SAVE_OPTIONS.put(XMLResource.OPTION_FLUSH_THRESHOLD, 4096);
		SAVE_OPTIONS.put(XMLResource.OPTION_USE_FILE_BUFFER, true);
	}

	public static Dataset load(final String src, final Properties connProperties)
			throws OdaException {
		ResourceSetImpl resourceSet = new ResourceSetImpl();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(
				org.eclipse.emf.ecore.resource.Resource.Factory.Registry.DEFAULT_EXTENSION,
				new XMLResourceFactoryImpl());
		resourceSet.getPackageRegistry().put(OdaPackage.eNS_URI, OdaPackage.eINSTANCE);
		Resource createResource = resourceSet.createResource(URI.createURI("http://my"));
		Dataset dataset = null;
		try {
			createResource.load(new ByteArrayInputStream(src.getBytes("UTF-8")), SAVE_OPTIONS);
			dataset = (Dataset) createResource.getContents().get(0);
		} catch (Exception e) {
			dataset = OdaFactory.eINSTANCE.createDataset();
		} finally {
			Selection createSelection = OdaFactory.eINSTANCE.createSelection();
			createSelection.setElementSelector(connProperties
					.getProperty(Constants.PROPERTY_BASE_SELECTION));
			createSelection.setInfoTypeId(connProperties.getProperty(Constants.PROPERTY_INFO_TYPE));
			createSelection.setPath(connProperties.getProperty(Constants.PROPERY_ROOT_ELEMENT));
			dataset.setSelection(createSelection);
		}
		return dataset;
	}

	public static String serialize(final Dataset dataset) throws OdaException {
		Dataset copy = (Dataset) EcoreUtil.copy(dataset);
		copy.eUnset(OdaPackage.Literals.DATASET__SELECTION);
		Resource resource = new XMLResourceImpl();
		resource.setURI(URI.createURI("serialization"));
		resource.getContents().add(copy);
		OutputStream outputStream = new StringOutputStream();
		try {
			resource.save(outputStream, SAVE_OPTIONS);
			return outputStream.toString();
		} catch (IOException e) {
			throw new OdaException("Error serializing");
		} finally {
			try {
				outputStream.flush();
				outputStream.close();
			} catch (IOException e) {
				// do nothing we've done our best.
			}
		}
	}

}
