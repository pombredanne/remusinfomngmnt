/**
 * Copyright Tom Seidel 2008
 * All rights reserved.
 */
package net.remus.infobroker.infobroker.extension;


import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;

/**
 * A object represenation of a registered information type. This class is
 * @author Tom Seidel <toms@tomosch.de>
 * @noextend This class is not intended to be subclassed by clients.
 * @noinstantiate This class is not intended to be instantiated by clients.
 */
public class InfoType {

    private final IConfigurationElement configurationElement;
    private ImageDescriptor img;
    private final String type;
    private AbstractCreationFactory createFactory;
    private AbstractInformationRepresentation informationRepresentation;
    private final String contributor;
    private final String createFactoryClass;
    private final String imageFilePath;

    InfoType(final IConfigurationElement configurationElement, 
            final String contributor, 
            final String type,
            final String createFactoryClass,
            final String imageFilePath) {
        this.configurationElement = configurationElement;
        this.contributor = contributor;
        this.type = type;
        this.createFactoryClass = createFactoryClass;
        this.imageFilePath = imageFilePath;

    }

    public AbstractCreationFactory getCreationFactory() {
        if (this.createFactory == null) {
            try {
                this.createFactory = (AbstractCreationFactory) this.configurationElement.createExecutableExtension(this.createFactoryClass);
            } catch (final CoreException e) {
                //TODO Logging
            }
        }
        return this.createFactory;
    }
    public AbstractInformationRepresentation getInformationRepresentation() {
        if (this.informationRepresentation == null) {
            try {
                this.informationRepresentation = 
                    (AbstractInformationRepresentation) this.configurationElement
                    .createExecutableExtension(InformationExtensionManager.PRESENTATION_ATT);
            } catch (final CoreException e) {
                //TODO Logging
            }
        }
        return this.informationRepresentation;
    }

    public String getType() {
        return this.type;
    }

    public ImageDescriptor getImage() {
        if (this.img == null) {
            this.img = AbstractUIPlugin.imageDescriptorFromPlugin(this.contributor, this.imageFilePath);
        }
        return this.img;

    }




}
