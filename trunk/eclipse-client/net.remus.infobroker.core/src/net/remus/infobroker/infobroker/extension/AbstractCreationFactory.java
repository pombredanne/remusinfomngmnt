/**
 * Copyright Tom Seidel 2008
 * All rights reserved.
 */
package net.remus.infobroker.infobroker.extension;

import java.util.Map;

import net.remus.infobroker.infobroker.Node;

import org.eclipse.emf.edit.domain.EditingDomain;

/**
 * @author Tom Seidel <toms@tomosch.de>
 *
 */
public abstract class AbstractCreationFactory {

    
    protected EditingDomain editingDomain;
    
    
    public abstract Node createNewObject(Node parentNode, Map<Object, Object> options);
    
    
    
}
