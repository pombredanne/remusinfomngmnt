package org.remus.infomngmnt.sourcecode;

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class SourceCodePlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.remus.infomngmnt.sourcecode"; //$NON-NLS-1$

	public static final String TYPE_ID = "SOURCE"; //$NON-NLS-1$

	public static final String SRCTYPE_NAME = "srcType"; //$NON-NLS-1$

	private final Map<String, String> sourceTypes;

	public Map<String, String> getSourceTypes() {
		return sourceTypes;
	}

	// The shared instance
	private static SourceCodePlugin plugin;

	/**
	 * The constructor
	 */
	public SourceCodePlugin() {
		sourceTypes = new LinkedHashMap<String, String>();
		sourceTypes.put(Messages.SourceCodePlugin_Assembly, "asm"); //$NON-NLS-1$
		sourceTypes.put(Messages.SourceCodePlugin_CPlusPlus, "cpp"); //$NON-NLS-1$
		sourceTypes.put(Messages.SourceCodePlugin_CSS, "css"); //$NON-NLS-1$
		sourceTypes.put(Messages.SourceCodePlugin_HTML, "html"); //$NON-NLS-1$
		sourceTypes.put(Messages.SourceCodePlugin_Java, "java"); //$NON-NLS-1$
		sourceTypes.put(Messages.SourceCodePlugin_JavaScript, "js"); //$NON-NLS-1$
		sourceTypes.put(Messages.SourceCodePlugin_JSP, "jsp"); //$NON-NLS-1$
		sourceTypes.put(Messages.SourceCodePlugin_LISP, "lisp"); //$NON-NLS-1$
		sourceTypes.put(Messages.SourceCodePlugin_Make, "makefile"); //$NON-NLS-1$
		sourceTypes.put(Messages.SourceCodePlugin_TeX, "tex"); //$NON-NLS-1$
		sourceTypes.put(Messages.SourceCodePlugin_Pearl, "pl"); //$NON-NLS-1$
		sourceTypes.put(Messages.SourceCodePlugin_PHP, "php"); //$NON-NLS-1$
		sourceTypes.put(Messages.SourceCodePlugin_WindowsScript, "bat"); //$NON-NLS-1$
		sourceTypes.put(Messages.SourceCodePlugin_XML, "xml"); //$NON-NLS-1$

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
	 * )
	 */
	@Override
	public void start(final BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
	 * )
	 */
	@Override
	public void stop(final BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static SourceCodePlugin getDefault() {
		return plugin;
	}

}
