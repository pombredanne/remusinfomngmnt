package org.remus.infomngmnt.bibliographic;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {
	private static final String RESOURCE_BUNDLE = "org.remus.infomngmnt.bibliographic.messages";  //$NON-NLS-1$

    private static ResourceBundle fgResourceBundle = ResourceBundle.getBundle(RESOURCE_BUNDLE);

    private Messages() {
    }

    /**
     * Returns the resource object with the given key in
     * the resource bundle. If there isn't any value under
     * the given key, the key is returned, surrounded by '!'s.
     *
     * @param key the resource name
     * @return the string
     */
    public static String getString(String key) {
        try {
            return fgResourceBundle.getString(key);
        } catch (MissingResourceException e) {
            return "!" + key + "!";   //$NON-NLS-2$ //$NON-NLS-1$
        }
    }
}
