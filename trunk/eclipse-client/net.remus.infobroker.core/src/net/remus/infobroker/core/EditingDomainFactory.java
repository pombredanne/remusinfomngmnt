/**
 * Copyright Tom Seidel 2008
 * All rights reserved.
 */
package net.remus.infobroker.core;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.impl.TransactionalEditingDomainImpl;
import org.eclipse.emf.workspace.WorkspaceEditingDomainFactory;


/**
 * @author Tom Seidel <toms@tomosch.de>
 *
 */
public class EditingDomainFactory extends TransactionalEditingDomainImpl.FactoryImpl  {

    @Override
    public TransactionalEditingDomain createEditingDomain() {
        
        return WorkspaceEditingDomainFactory.INSTANCE.createEditingDomain();
    }

    @Override
    public TransactionalEditingDomain createEditingDomain(final ResourceSet rset) {
        return TransactionalEditingDomain.Factory.INSTANCE.createEditingDomain(rset);
    }

    @Override
    public TransactionalEditingDomain getEditingDomain(final ResourceSet rset) {
        return TransactionalEditingDomain.Factory.INSTANCE.getEditingDomain(rset);
    }

}
