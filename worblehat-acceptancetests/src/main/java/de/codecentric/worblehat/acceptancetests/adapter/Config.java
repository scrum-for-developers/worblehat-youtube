package de.codecentric.worblehat.acceptancetests.adapter;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Reads config parameters from properties file. The properties are by maven during the build process.
 */
public class Config {

    private static final String BUNDLE_NAME = "de.codecentric.worblehat.acceptancetests.adapter.config"; //$NON-NLS-1$

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
        .getBundle(BUNDLE_NAME);

    public Config() {
    }

    public static String getString(String key) {
        try {
            return RESOURCE_BUNDLE.getString(key);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }

    public static String getApplicationURL() {
        return getString("application.url");
    }

    public static String getEnvironment() {
        return getString("environment");
    }

}
