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

package org.remus.infomngmnt.core.edit;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;

import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.core.services.IEditingHandler;
import org.remus.infomngmnt.model.service.ResourceConstants;
import org.remus.infomngmnt.provider.InfomngmntItemProviderAdapterFactory;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class EditingUtil implements IEditingHandler {

	private static IEditingHandler INSTANCE;

	private final EditingDomain editingDomain;
	private final ComposedAdapterFactory adapterFactory;
	private final AdapterFactoryEditingDomain navigationEditingDomain;

	public EditingUtil() {
		this.adapterFactory = new ComposedAdapterFactory(
				ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		this.adapterFactory.addAdapterFactory(new ResourceItemProviderAdapterFactory());
		this.adapterFactory.addAdapterFactory(new InfomngmntItemProviderAdapterFactory());
		this.adapterFactory.addAdapterFactory(new ReflectiveItemProviderAdapterFactory());
		this.editingDomain = new AdapterFactoryEditingDomain(this.adapterFactory,
				new BasicCommandStack());
		this.navigationEditingDomain = new RIMEditingDomain(this.adapterFactory,
				new BasicCommandStack());
		this.editingDomain.getResourceSet().getResourceFactoryRegistry().getExtensionToFactoryMap()
				.put(InfomngmntPackage.eNS_URI, InfomngmntPackage.eINSTANCE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.util.IEditingHandler#getObjectFromFileUri(org.eclipse
	 * .emf.common.util.URI, org.eclipse.emf.ecore.EClass,
	 * org.eclipse.emf.edit.domain.EditingDomain)
	 */
	@SuppressWarnings("unchecked")
	public <T extends EObject> T getObjectFromFileUri(final URI uri, final EClass objectClas,
			final EditingDomain editingDomain) {
		Resource resource = null;
		ResourceSet resourceSet = null;
		T returnValue;
		File file = new File(uri.toFileString());
		if (editingDomain != null) {
			resourceSet = editingDomain.getResourceSet();
		} else {
			resourceSet = new ResourceSetImpl();
			resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(
					org.eclipse.emf.ecore.resource.Resource.Factory.Registry.DEFAULT_EXTENSION,
					new XMLResourceFactoryImpl());
			resourceSet.getPackageRegistry().put(InfomngmntPackage.eNS_URI,
					InfomngmntPackage.eINSTANCE);
		}

		if (file.exists()) {
			try {
				resource = resourceSet.getResource(uri, true);
			} catch (final Exception e) {
				resource = resourceSet.getResource(uri, false);
			}
			returnValue = (T) resource.getContents().get(0);
		} else {
			final EObject create = InfomngmntFactory.eINSTANCE.create(objectClas);
			resource = resourceSet.createResource(uri);
			resource.getContents().add(create);
			try {
				resource.save(ResourceConstants.SAVE_OPTIONS);
			} catch (final IOException e) {
				// FIXME What to do here?
			}
			returnValue = (T) create;
		}
		return returnValue;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.util.IEditingHandler#getObjectFromUri(org.eclipse
	 * .core.runtime.IPath, org.eclipse.emf.ecore.EClass, boolean,
	 * org.eclipse.emf.edit.domain.EditingDomain, boolean)
	 */
	@SuppressWarnings("unchecked")
	public <T extends EObject> T getObjectFromUri(final IPath uri, final EClass objectClas,
			final boolean cache, final EditingDomain domain, final boolean createOnDemand) {
		Resource resource = null;
		ResourceSet resourceSet = null;
		T returnValue = null;
		final org.eclipse.emf.common.util.URI createURI = org.eclipse.emf.common.util.URI
				.createPlatformResourceURI(uri.toString(), false);
		final IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(
				new Path(uri.toString()));
		if (cache) {
			resourceSet = domain.getResourceSet();
		} else {
			resourceSet = new ResourceSetImpl();
			resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(
					org.eclipse.emf.ecore.resource.Resource.Factory.Registry.DEFAULT_EXTENSION,
					new XMLResourceFactoryImpl());
			resourceSet.getPackageRegistry().put(InfomngmntPackage.eNS_URI,
					InfomngmntPackage.eINSTANCE);
		}
		if (file.exists()) {
			try {
				resource = resourceSet.getResource(createURI, true);
			} catch (final Exception e) {
				resource = resourceSet.getResource(createURI, false);
			}
			returnValue = (T) resource.getContents().get(0);
		} else {
			if (createOnDemand) {
				final EObject create = InfomngmntFactory.eINSTANCE.create(objectClas);
				resource = resourceSet.createResource(createURI);
				resource.getContents().add(create);
				saveObjectToResource(create);
				returnValue = (T) create;
			}
		}
		return returnValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.util.IEditingHandler#getObjectFromUri(org.eclipse
	 * .core.runtime.IPath, org.eclipse.emf.ecore.EClass)
	 */
	public <T extends EObject> T getObjectFromUri(final IPath uri, final EClass objectClas) {
		return getObjectFromUri(uri, objectClas, false, null, false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.util.IEditingHandler#getObjectFromFile(org.eclipse
	 * .core.resources.IFile, org.eclipse.emf.ecore.EClass)
	 */
	public <T extends EObject> T getObjectFromFile(final IFile uri, final EClass objectClas) {
		return getObjectFromUri(uri.getFullPath(), objectClas, true, this.editingDomain, false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.util.IEditingHandler#getObjectFromFile(org.eclipse
	 * .core.resources.IFile, org.eclipse.emf.ecore.EClass, boolean)
	 */
	public <T extends EObject> T getObjectFromFile(final IFile uri, final EClass objectClas,
			final boolean cacheInEditingDomain) {
		return getObjectFromUri(uri.getFullPath(), objectClas, cacheInEditingDomain,
				this.editingDomain, true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.util.IEditingHandler#getObjectFromFile(org.eclipse
	 * .core.resources.IFile, org.eclipse.emf.ecore.EClass,
	 * org.eclipse.emf.edit.domain.EditingDomain, boolean)
	 */
	public <T extends EObject> T getObjectFromFile(final IFile uri, final EClass objectClas,
			final EditingDomain domain, final boolean loadOnDemand) {
		return getObjectFromUri(uri.getFullPath(), objectClas, domain != null ? true : false,
				this.editingDomain, loadOnDemand);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.util.IEditingHandler#getObjectFromFile(org.eclipse
	 * .core.resources.IFile, org.eclipse.emf.ecore.EClass,
	 * org.eclipse.emf.edit.domain.EditingDomain)
	 */
	public <T extends EObject> T getObjectFromFile(final IFile uri, final EClass objectClas,
			final EditingDomain domain) {
		return getObjectFromUri(uri.getFullPath(), objectClas, true, this.editingDomain, true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.util.IEditingHandler#saveObjectToResource(org.eclipse
	 * .emf.ecore.EObject)
	 */
	public void saveObjectToResource(final EObject object) {
		try {
			String scheme = null;
			if (object.eResource().getURI().isPlatform()) {
				String uriString = object.eResource().getURI().toPlatformString(true);
				scheme = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(uriString))
						.getLocationURI().getScheme();
			} else {
				scheme = object.eResource().getURI().scheme();
			}
			if (!(ResourceConstants.FILEHISTORYKEEPINGSCHEME.equals(scheme))) {
				IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(
						new Path(object.eResource().getURI().toPlatformString(true)));
				saveObjectToResource(file, object);
			} else {
				object.eResource().save(ResourceConstants.SAVE_OPTIONS);
			}
		} catch (final IOException e) {
			// FIXME Error-Handling
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.util.IEditingHandler#saveObjectToResource(org.eclipse
	 * .emf.ecore.EObject, java.lang.String)
	 */
	public void saveObjectToResource(final EObject object, final String fileName) {
		File file = new File(fileName);
		ResourceSetImpl resourceSet = new ResourceSetImpl();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(
				org.eclipse.emf.ecore.resource.Resource.Factory.Registry.DEFAULT_EXTENSION,
				new XMLResourceFactoryImpl());
		resourceSet.getPackageRegistry()
				.put(InfomngmntPackage.eNS_URI, InfomngmntPackage.eINSTANCE);
		Resource resource;
		if (file.exists()) {
			try {
				resource = resourceSet.getResource(URI.createFileURI(fileName), true);
			} catch (Exception e) {
				resource = resourceSet.createResource(URI.createFileURI(fileName));
				e.printStackTrace();
			}
			resource.getContents().clear();
		} else {
			resource = resourceSet.createResource(URI.createFileURI(fileName));
		}
		resource.getContents().add(object);
		try {
			// FileOutputStream fos = new FileOutputStream(file);
			resource.save(ResourceConstants.SAVE_OPTIONS);
			// fos.flush();
			// fos.close();
			// fos = null;
		} catch (final IOException e) {
			e.printStackTrace();
			// FIXME What to do here?
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.util.IEditingHandler#createRessource(java.lang.String
	 * )
	 */
	public Resource createRessource(final String fileName) {
		File file = new File(fileName);
		ResourceSetImpl resourceSet = new ResourceSetImpl();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(
				org.eclipse.emf.ecore.resource.Resource.Factory.Registry.DEFAULT_EXTENSION,
				new XMLResourceFactoryImpl());
		resourceSet.getPackageRegistry()
				.put(InfomngmntPackage.eNS_URI, InfomngmntPackage.eINSTANCE);
		Resource resource = null;
		if (file.exists()) {
			resource = resourceSet.getResource(URI.createFileURI(fileName), true);
			resource.getContents().clear();

		} else {
			resource = resourceSet.createResource(URI.createFileURI(fileName));
			resource.getContents();
		}
		try {
			resource.save(ResourceConstants.SAVE_OPTIONS);
		} catch (final IOException e) {
			// FIXME What to do here?
		}

		return resource;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.util.IEditingHandler#saveObjectToByte(org.eclipse
	 * .emf.ecore.EObject)
	 */
	public byte[] saveObjectToByte(final EObject object) {
		byte[] returnValue = new byte[0];
		Resource resource = new XMLResourceImpl();
		resource.setURI(URI.createURI("serialization"));
		resource.getContents().add(EcoreUtil.copy(object));
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try {
			resource.save(outputStream, ResourceConstants.SAVE_OPTIONS);
			returnValue = outputStream.toByteArray();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				outputStream.flush();
				outputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return returnValue;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.util.IEditingHandler#saveObjectToResource(org.eclipse
	 * .core.resources.IFile, org.eclipse.emf.ecore.EObject)
	 */
	public void saveObjectToResource(final IFile target, final EObject object) {
		try {
			final org.eclipse.emf.common.util.URI createURI = org.eclipse.emf.common.util.URI
					.createPlatformResourceURI(target.getFullPath().toString(), false);
			ResourceSet resourceSet = new ResourceSetImpl();
			resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(
					Resource.Factory.Registry.DEFAULT_EXTENSION, new XMLResourceFactoryImpl());
			resourceSet.getPackageRegistry().put(InfomngmntPackage.eNS_URI,
					InfomngmntPackage.eINSTANCE);
			Resource resource = resourceSet.createResource(createURI);
			resource.getContents().add(object);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			resource.save(baos, ResourceConstants.SAVE_OPTIONS);
			ByteArrayInputStream inputStream = new ByteArrayInputStream(baos.toByteArray());
			baos.flush();
			baos.close();
			String scheme = target.getLocationURI().getScheme();
			boolean keepHistory = ResourceConstants.FILEHISTORYKEEPINGSCHEME.equals(scheme);
			if (target.exists()) {
				target.setContents(inputStream, true, keepHistory, new NullProgressMonitor());
			} else {
				target.create(inputStream, true, new NullProgressMonitor());
			}
			inputStream.close();
			target.refreshLocal(1, new NullProgressMonitor());
		} catch (final IOException e) {
			// FIXME Error-Handling
			e.printStackTrace();
		} catch (CoreException e) {
			// FIXME Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.remus.infomngmnt.util.IEditingHandler#getEditingDomain()
	 */
	public EditingDomain getEditingDomain() {
		return this.editingDomain;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.remus.infomngmnt.util.IEditingHandler#createNewEditingDomain()
	 */
	public DisposableEditingDomain createNewEditingDomain() {
		return new DisposableEditingDomain(this.adapterFactory, new BasicCommandStack());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.remus.infomngmnt.util.IEditingHandler#getAdapterFactory()
	 */
	public ComposedAdapterFactory getAdapterFactory() {
		return this.adapterFactory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.util.IEditingHandler#getNavigationEditingDomain()
	 */
	public AdapterFactoryEditingDomain getNavigationEditingDomain() {
		return this.navigationEditingDomain;
	}

	public void execute(final Command command) {
		getNavigationEditingDomain().getCommandStack().execute(command);

	}
}