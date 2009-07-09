package org.remus.infomngmnt.spellengine;

import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class CommonImageRegistry extends ImageRegistry {

	public static final String SPELLING_ADD = "SPELLING_ADD"; //$NON-NLS-1$
	public static final String SPELLING_CHANGE = "SPELLING_CHANGE"; //$NON-NLS-1$
	public static final String SPELLING_IGNORE = "SPELLING_IGNORE"; //$NON-NLS-1$

	private static CommonImageRegistry INSTANCE;

	public static CommonImageRegistry getInstance() {
		if (CommonImageRegistry.INSTANCE == null) {
			synchronized (CommonImageRegistry.class) {
				if (CommonImageRegistry.INSTANCE == null) {
					CommonImageRegistry.INSTANCE = new CommonImageRegistry();
				}
			}
		}
		return CommonImageRegistry.INSTANCE;
	}

	private CommonImageRegistry() {
		initialize();
	}

	private void initialize() {
		registerImage(SPELLING_ADD, "icons/add_correction.gif"); //$NON-NLS-1$
		registerImage(SPELLING_CHANGE, "icons/correction_rename.gif"); //$NON-NLS-1$
		registerImage(SPELLING_IGNORE, "icons/never_translate.gif"); //$NON-NLS-1$
	}

	private void registerImage(final String key, final String fileName) {
		try {
			IPath path = new Path(fileName);
			URL url = FileLocator.find(SpellActivator.getDefault().getBundle(), path, null);
			if (url != null) {
				ImageDescriptor desc = ImageDescriptor.createFromURL(url);
				put(key, desc);
			}
		} catch (Exception e) {
		}
	}

}
