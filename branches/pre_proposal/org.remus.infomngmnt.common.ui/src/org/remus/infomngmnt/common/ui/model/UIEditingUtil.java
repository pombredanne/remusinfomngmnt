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

package org.remus.infomngmnt.common.ui.model;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceFactoryImpl;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;

import org.remus.infomngmt.common.ui.uimodel.UIModelFactory;
import org.remus.infomngmt.common.ui.uimodel.UIModelPackage;
import org.remus.infomngmt.common.ui.uimodel.provider.UIModelItemProviderAdapterFactory;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class UIEditingUtil {

	private static UIEditingUtil INSTANCE;

	private final EditingDomain editingDomain;
	private final ComposedAdapterFactory adapterFactory;

	public static UIEditingUtil getInstance() {
		if (UIEditingUtil.INSTANCE == null) {
			synchronized (UIEditingUtil.class) {
				if (UIEditingUtil.INSTANCE == null) {
					UIEditingUtil.INSTANCE = new UIEditingUtil();
				}
			}
		}
		return UIEditingUtil.INSTANCE;
	}

	private UIEditingUtil() {
		this.adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		this.adapterFactory.addAdapterFactory(new ResourceItemProviderAdapterFactory());
		this.adapterFactory.addAdapterFactory(new UIModelItemProviderAdapterFactory());
		this.adapterFactory.addAdapterFactory(new ReflectiveItemProviderAdapterFactory());
		this.editingDomain = new AdapterFactoryEditingDomain(this.adapterFactory, new BasicCommandStack());
		this.editingDomain.getResourceSet().getResourceFactoryRegistry().getExtensionToFactoryMap().put
		(UIModelPackage.eNS_URI,
				UIModelPackage.eINSTANCE);
	}
	/**
	 * Returns a {@link Resource} object from the given uri
	 * @param uri the uri
	 * @param cache
	 * @return the contents of the file
	 */
	@SuppressWarnings("unchecked")
	public <T extends EObject> T getObjectFromFileUri(final URI uri, final EClass objectClas, final boolean cache) {
		Resource resource = null;
		ResourceSet resourceSet = null;
		T returnValue;
		File file = new File(uri.toFileString());
		resourceSet = new ResourceSetImpl();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put
		(org.eclipse.emf.ecore.resource.Resource.Factory.Registry.DEFAULT_EXTENSION,
				new XMLResourceFactoryImpl());
		resourceSet.getPackageRegistry().put
		(UIModelPackage.eNS_URI,
				UIModelPackage.eINSTANCE);

		if (file.exists()) {
			try {
				resource = resourceSet.getResource(uri, true);
			} catch (final Exception e) {
				resource = resourceSet.getResource(uri, false);
			}
			returnValue = (T) resource.getContents().get(0);
		} else {
			final EObject create = UIModelFactory.eINSTANCE.create(objectClas);
			resource = resourceSet.createResource(uri);
			resource.getContents().add(create);
			try {
				resource.save(Collections.singletonMap(XMLResource.OPTION_ENCODING, "UTF-8"));
			} catch (final IOException e) {
				// FIXME What to do here?
			}
			returnValue = (T) create;
		}
		return returnValue;

	}

	/**
	 * Returns a {@link Resource} object from the given uri
	 * @param uri the uri
	 * @param cache
	 * @return the contents of the file
	 */
	@SuppressWarnings("unchecked")
	public <T extends EObject> T getObjectFromUri(final IPath uri, final EClass objectClas, final boolean cache) {
		Resource resource = null;
		ResourceSet resourceSet = null;
		T returnValue;
		final org.eclipse.emf.common.util.URI createURI = org.eclipse.emf.common.util.URI.createPlatformResourceURI(uri.toString(),false);
		final IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(uri.toString()));
		if (cache) {
			resourceSet = this.editingDomain.getResourceSet();
		} else {
			resourceSet = new ResourceSetImpl();
			resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put
			(org.eclipse.emf.ecore.resource.Resource.Factory.Registry.DEFAULT_EXTENSION,
					new XMLResourceFactoryImpl());
			resourceSet.getPackageRegistry().put
			(UIModelPackage.eNS_URI,
					UIModelPackage.eINSTANCE);
		}
		if (file.exists()) {
			try {
				resource = resourceSet.getResource(createURI, true);
			} catch (final Exception e) {
				resource = resourceSet.getResource(createURI, false);
			}
			returnValue = (T) resource.getContents().get(0);
		} else {
			final EObject create = UIModelFactory.eINSTANCE.create(objectClas);
			resource = resourceSet.createResource(createURI);
			resource.getContents().add(create);
			try {
				resource.save(Collections.singletonMap(XMLResource.OPTION_ENCODING, "UTF-8"));
			} catch (final IOException e) {
				// FIXME What to do here?
			}
			returnValue = (T) create;
		}
		return returnValue;
	}


	/**
	 * Returns a {@link Resource} object from the given uri
	 * @param uri the uri
	 * @return the contents of the file
	 */
	public <T extends EObject> T getObjectFromFile(final IFile uri, final EClass objectClas) {
		return getObjectFromUri(uri.getFullPath(), objectClas, true);
	}
	/**
	 * Returns a {@link Resource} object from the given uri
	 * @param uri the uri
	 * @return the contents of the file
	 */
	public <T extends EObject> T getObjectFromFile(final IFile uri, final EClass objectClas, final boolean cacheInEditingDomain) {
		return getObjectFromUri(uri.getFullPath(), objectClas, cacheInEditingDomain);
	}


	public void saveObjectToResource(final EObject object) {
		try {
			object.eResource().save(Collections.EMPTY_MAP);
		} catch (final IOException e) {
			// FIXME Error-Handling
			e.printStackTrace();
		}
	}
	public void saveObjectToResource(final IFile target, final EObject object) {
		try {
			final org.eclipse.emf.common.util.URI createURI = org.eclipse.emf.common.util.URI.createPlatformResourceURI(target.getFullPath().toString(),false);
			ResourceSet resourceSet = new ResourceSetImpl();
			resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put
			(Resource.Factory.Registry.DEFAULT_EXTENSION,
					new XMLResourceFactoryImpl());
			resourceSet.getPackageRegistry().put
			(UIModelPackage.eNS_URI,
					UIModelPackage.eINSTANCE);
			if (target.exists()) {
				target.delete(true, new NullProgressMonitor());
			}
			Resource resource = resourceSet.createResource(createURI);
			resource.getContents().add(object);
			resource.save(Collections.singletonMap(XMLResource.OPTION_ENCODING, "UTF-8"));
			target.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
		} catch (final IOException e) {
			// FIXME Error-Handling
			e.printStackTrace();
		} catch (CoreException e) {
			// FIXME Auto-generated catch block
			e.printStackTrace();
		}
	}

	public EditingDomain getEditingDomain() {
		return this.editingDomain;
	}

	public EditingDomain createNewEditingDomain() {
		return new AdapterFactoryEditingDomain(this.adapterFactory, new BasicCommandStack());
	}


	public ComposedAdapterFactory getAdapterFactory() {
		return this.adapterFactory;
	}
}