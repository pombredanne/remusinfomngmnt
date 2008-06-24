/**
 * Copyright Tom Seidel 2008
 * All rights reserved.
 */
package net.remus.infobroker.infobroker.extension;

import java.util.HashMap;
import java.util.Map;

import net.remus.infobroker.core.CorePlugin;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;

/**
 * @author Tom Seidel <toms@tomosch.de>
 *
 */
public class InformationExtensionManager {
    
    public static final String EXTENSION_POINT = CorePlugin.PLUGIN_ID + ".informationType"; //$NON-NLS-1$
    
    public static final String INFORMATION_NODE_NAME = "information"; //$NON-NLS-1$
    
    public static final String TYPE_ATT = "type"; //$NON-NLS-1$
    
    public static final String ICON_ATT = "icon"; //$NON-NLS-1$
    
    public static final String CREATION_FACTORY_ATT = "creationFactory"; //$NON-NLS-1$
    
    public static final String PRESENTATION_ATT = "presentation"; //$NON-NLS-1$
    
    private static InformationExtensionManager INSTANCE;

    private Map<String,InfoType> items;

    public static InformationExtensionManager getInstance() {
        if (INSTANCE == null) {
            synchronized (InformationExtensionManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new InformationExtensionManager();
                }
            }
        }
        return INSTANCE;
    }
    
    private InformationExtensionManager() {
        init();
    }

    private void init() {
        this.items = new HashMap<String,InfoType>();
        final IExtensionPoint extensionPoint = Platform.getExtensionRegistry().getExtensionPoint(EXTENSION_POINT);
        final IConfigurationElement[] configurationElements = extensionPoint.getConfigurationElements();
        for (final IConfigurationElement configurationElement : configurationElements) {
            final InfoType infoType = new InfoType(
                    configurationElement,
                    configurationElement.getContributor().getName(),
                    configurationElement.getAttribute(TYPE_ATT),
                    CREATION_FACTORY_ATT,
                    configurationElement.getAttribute(ICON_ATT));
            this.items.put(infoType.getType(),infoType);
        }
    }
    
    public InfoType getInfoTypeByType(final String type) {
        return this.items.get(type);
    }

}
