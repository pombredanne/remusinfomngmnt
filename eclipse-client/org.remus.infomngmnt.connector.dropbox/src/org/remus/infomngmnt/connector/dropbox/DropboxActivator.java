package org.remus.infomngmnt.connector.dropbox;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class DropboxActivator extends AbstractUIPlugin {

	public static final String VERIFIER = "verifier"; //$NON-NLS-1$

	public static final String CONSUMER_SECRET = "consumer_secret"; //$NON-NLS-1$

	public static final String CONSUMER_KEY = "consumer_key"; //$NON-NLS-1$

	public static final String AUTHORIZATION_URL = "authorization_url"; //$NON-NLS-1$

	public static final String ACCESS_TOKEN_URL = "access_token_url"; //$NON-NLS-1$

	public static final String REQUEST_TOKEN_URL = "request_token_url"; //$NON-NLS-1$

	public static final String PORT = "port"; //$NON-NLS-1$

	public static final String CONTENT_SERVER = "content_server"; //$NON-NLS-1$

	public static final String SERVER = "server"; //$NON-NLS-1$

	// The plug-in ID
	public static final String PLUGIN_ID = "org.remus.infomngmnt.connector.dropbox"; //$NON-NLS-1$

	public static Map<String, String> CONNECTION_PROPERTIES;

	static {
		CONNECTION_PROPERTIES = new HashMap<String, String>();
		CONNECTION_PROPERTIES.put(SERVER, "api.getdropbox.com"); //$NON-NLS-1$
		CONNECTION_PROPERTIES.put(CONTENT_SERVER, "api-content.getdropbox.com"); //$NON-NLS-1$
		CONNECTION_PROPERTIES.put(PORT, "80"); //$NON-NLS-1$
		CONNECTION_PROPERTIES.put(REQUEST_TOKEN_URL,
				"http://api.getdropbox.com/0/oauth/request_token"); //$NON-NLS-1$
		CONNECTION_PROPERTIES.put(ACCESS_TOKEN_URL,
				"http://api.getdropbox.com/0/oauth/access_token"); //$NON-NLS-1$
		CONNECTION_PROPERTIES.put(AUTHORIZATION_URL,
				"http://api.getdropbox.com/0/oauth/authorize"); //$NON-NLS-1$
		CONNECTION_PROPERTIES.put(CONSUMER_KEY, "fzhm4b6syruphjf"); //$NON-NLS-1$
		CONNECTION_PROPERTIES.put(CONSUMER_SECRET, "283c1p6u24b69dh"); //$NON-NLS-1$
		CONNECTION_PROPERTIES.put(VERIFIER, ""); //$NON-NLS-1$
	}

	// The shared instance
	private static DropboxActivator plugin;

	/**
	 * The constructor
	 */
	public DropboxActivator() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
	 * )
	 */
	@Override
	public void start(BundleContext context) throws Exception {
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
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static DropboxActivator getDefault() {
		return plugin;
	}

}
