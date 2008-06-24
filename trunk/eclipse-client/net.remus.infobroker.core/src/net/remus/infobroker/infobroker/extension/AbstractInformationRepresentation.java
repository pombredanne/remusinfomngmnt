/**
 * Copyright Tom Seidel 2008
 * All rights reserved.
 */
package net.remus.infobroker.infobroker.extension;

import java.io.InputStream;

import net.remus.infobroker.infobroker.Value;

import org.apache.lucene.document.Document;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * @author Tom Seidel <toms@tomosch.de>
 *
 */
public abstract class AbstractInformationRepresentation {
    
    private Value value;
    
    public AbstractInformationRepresentation() {
        super();
    }
    
    /**
     * Executed after the serialization of the Info-Object. Useful 
     * @param derivedFile
     */
    public abstract void handlePostBuild(IFile derivedFile, IProgressMonitor monitor) throws CoreException;
    
    public abstract InputStream handleSerialization(IProgressMonitor monitor) throws CoreException;
    
    public abstract Document handleIndexing(IProgressMonitor monitor) throws CoreException;

    public Value getValue() {
        return this.value;
    }

    public void setValue(final Value value) {
        this.value = value;
    }
    
    

}
