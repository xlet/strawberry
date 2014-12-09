package org.xlet.strawberry.core.config;

/**
 * system property not set exception.
 */
public class SystemPropertyNotSetException extends Exception {

    private String systemProperty;

    public SystemPropertyNotSetException(String systemProperty) {
        super("the system property[" + systemProperty + "] not set");
        this.systemProperty = systemProperty;
    }

    public String getSystemProperty() {
        return this.systemProperty;
    }
}
