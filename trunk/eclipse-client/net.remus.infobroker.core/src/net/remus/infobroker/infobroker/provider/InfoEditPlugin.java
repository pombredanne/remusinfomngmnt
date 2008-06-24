/**
 * Copyright Tom Seidel 2008
 * All rights reserved
 *
 * $Id$
 */
package net.remus.infobroker.infobroker.provider;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

/**
 * This is the central singleton for the Info edit plugin.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated not
 */
public final class InfoEditPlugin extends EMFPlugin {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright Tom Seidel 2008\r\nAll rights reserved";

    /**
     * Keep track of the singleton.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final InfoEditPlugin INSTANCE = new InfoEditPlugin();

    /**
     * Keep track of the singleton.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static Implementation plugin;

    /**
     * Create the instance.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public InfoEditPlugin() {
        super
          (new ResourceLocator [] {
           });
    }

    /**
     * Returns the singleton instance of the Eclipse plugin.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the singleton instance.
     * @generated
     */
    @Override
    public ResourceLocator getPluginResourceLocator() {
        return plugin;
    }

    /**
     * Returns the singleton instance of the Eclipse plugin.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the singleton instance.
     * @generated
     */
    public static Implementation getPlugin() {
        return plugin;
    }

    /**
     * The actual implementation of the Eclipse <b>Plugin</b>.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static class Implementation extends EclipsePlugin {
        
        private AdapterFactoryEditingDomain editingDomain;
        
        private ComposedAdapterFactory adapterFactory;
        
        public static final String DEFAULT_EDITING_DOMAIN_ID = "net.remus.infobroker.core.editingDomain"; //$NON-NLS-1$
        
        private Map<String, EditingDomain> domainMap;
        /**
         * Creates an instance.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        public Implementation() {
            super();

            // Remember the static instance.
            //
            plugin = this;
        }
        
        
        
        private void init() {
            this.adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
            this.adapterFactory.addAdapterFactory(new ResourceItemProviderAdapterFactory());
            this.adapterFactory.addAdapterFactory(new InfobrokerItemProviderAdapterFactory());
            this.adapterFactory.addAdapterFactory(new ReflectiveItemProviderAdapterFactory());
            this.editingDomain = new AdapterFactoryEditingDomain(this.adapterFactory, new BasicCommandStack());
            this.domainMap = new HashMap<String, EditingDomain>();
        }

        public EditingDomain getEditingDomain() {
            if (this.editingDomain == null) {
                init();
            }
            return this.editingDomain;
        }
        
        public EditingDomain getEditingDomainFor(final String key) {
            if (this.domainMap.get(key) == null) {
                this.domainMap.put(key, TransactionalEditingDomain.Registry.INSTANCE.getEditingDomain(DEFAULT_EDITING_DOMAIN_ID));
            }
            return this.domainMap.get(key);
        }

        public ComposedAdapterFactory getAdapterFactory() {
            if (this.adapterFactory == null) {
                init();
            }
            return this.adapterFactory;
        }
    }

}
