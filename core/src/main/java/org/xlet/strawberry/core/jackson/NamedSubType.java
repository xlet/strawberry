package org.xlet.strawberry.core.jackson;

/**
 * for json sub type register config.
 */
public class NamedSubType {

    private Class<?> type;
    private String name;

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
