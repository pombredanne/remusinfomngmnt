package org.remus.infomngmnt.core.services;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;

import org.remus.infomngmnt.core.edit.DisposableEditingDomain;

public interface IEditingHandler {

	/**
	 * Returns a {@link Resource} object from the given uri
	 * 
	 * @param uri
	 *            the uri
	 * @param cache
	 *            see also bug 10
	 * @return the contents of the file
	 */
	@SuppressWarnings("unchecked")
	<T extends EObject> T getObjectFromFileUri(final URI uri, final EClass objectClas,
			final EditingDomain editingDomain);

	/**
	 * Returns a {@link Resource} object from the given uri
	 * 
	 * @param uri
	 *            the uri
	 * @param cache
	 * @return the contents of the file
	 */
	@SuppressWarnings("unchecked")
	<T extends EObject> T getObjectFromUri(final IPath uri, final EClass objectClas,
			final boolean cache, final EditingDomain domain, final boolean createOnDemand);

	<T extends EObject> T getObjectFromUri(final IPath uri, final EClass objectClas);

	/**
	 * Returns a {@link Resource} object from the given uri
	 * 
	 * @param uri
	 *            the uri
	 * @return the contents of the file
	 */
	<T extends EObject> T getObjectFromFile(final IFile uri, final EClass objectClas);

	/**
	 * Returns a {@link Resource} object from the given uri
	 * 
	 * @param uri
	 *            the uri
	 * @return the contents of the file
	 */
	<T extends EObject> T getObjectFromFile(final IFile uri, final EClass objectClas,
			final boolean cacheInEditingDomain);

	/**
	 * Returns a {@link Resource} object from the given uri
	 * 
	 * @param uri
	 *            the uri
	 * @return the contents of the file
	 */
	<T extends EObject> T getObjectFromFile(final IFile uri, final EClass objectClas,
			final EditingDomain domain, final boolean loadOnDemand);

	/**
	 * Returns a {@link Resource} object from the given uri
	 * 
	 * @param uri
	 *            the uri
	 * @return the contents of the file
	 */
	<T extends EObject> T getObjectFromFile(final IFile uri, final EClass objectClas,
			final EditingDomain domain);

	void saveObjectToResource(final EObject object);

	void saveObjectToResource(final EObject object, final String fileName);

	Resource createRessource(final String fileName);

	byte[] saveObjectToByte(final EObject object);

	void saveObjectToResource(final IFile target, final EObject object);

	EditingDomain getEditingDomain();

	DisposableEditingDomain createNewEditingDomain();

	ComposedAdapterFactory getAdapterFactory();

	AdapterFactoryEditingDomain getNavigationEditingDomain();

	void execute(Command command);

}