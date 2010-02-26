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
	public static final String PLUGIN_ID = "org.remus.infomngmnt.sourcecode";

	public static final String TYPE_ID = "SOURCE";

	public static final String SRCTYPE_NAME = "srcType";

	private final Map<String, String> sourceTypes;

	public Map<String, String> getSourceTypes() {
		return this.sourceTypes;
	}

	// The shared instance
	private static SourceCodePlugin plugin;

	/**
	 * The constructor
	 */
	public SourceCodePlugin() {
		this.sourceTypes = new LinkedHashMap<String, String>();
		this.sourceTypes.put("Assembly", "asm");
		this.sourceTypes.put("C/C++", "cpp");
		this.sourceTypes.put("CSS", "css");
		this.sourceTypes.put("HTML", "html");
		this.sourceTypes.put("Java", "java");
		this.sourceTypes.put("Java Script", "js");
		this.sourceTypes.put("JSP", "jsp");
		this.sourceTypes.put("LISP", "lisp");
		this.sourceTypes.put("Make", "makefile");
		this.sourceTypes.put("TeX", "tex");
		this.sourceTypes.put("Pearl", "pl");
		this.sourceTypes.put("PHP", "php");
		this.sourceTypes.put("Windows Script", "bat");
		this.sourceTypes.put("XML", "xml");

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
