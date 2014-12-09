package org.xlet.strawberry.persistent.mongo;

import org.mongodb.morphia.Morphia;
import org.springframework.beans.factory.config.AbstractFactoryBean;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-2 下午3:49.
 * Summary: MorphiaFactory.
 */
public class MorphiaFactoryBean extends AbstractFactoryBean<Morphia> {

    /**
     * 要扫描并映射的包.
     */
    private String[] mapPackages;

    /**
     * 要映射的类.
     */
    private String[] mapClasses;

    /**
     * 扫描包时，是否忽略不映射的类.
     * 默认设置为 false.
     */
    private boolean ignoreInvalidClasses = false;

    @Override
    protected Morphia createInstance() throws Exception {
        Morphia morphia = new Morphia();
        if (mapPackages != null) {
            for (String packageName : mapPackages) {
                morphia.mapPackage(packageName, ignoreInvalidClasses);
            }
        }

        if (mapClasses != null) {
            for (String entityClass : mapClasses) {
                morphia.map(Class.forName(entityClass));
            }
        }
        return morphia;
    }

    @Override
    public Class<?> getObjectType() {
        return Morphia.class;
    }

    /**
     * 设置需要扫描的包列表.
     *
     * @param mapPackages 要扫描的包列表.
     */
    public void setMapPackages(String[] mapPackages) {
        this.mapPackages = mapPackages;
    }

    /**
     * 设置要映射的Class列表.
     *
     * @param mapClasses 要映射的Class列表.
     */
    public void setMapClasses(String[] mapClasses) {
        this.mapClasses = mapClasses;
    }

    /**
     * 设置扫描包时,是否忽略不映射的类.
     *
     * @param ignoreInvalidClasses 是否忽略.
     */
    public void setIgnoreInvalidClasses(boolean ignoreInvalidClasses) {
        this.ignoreInvalidClasses = ignoreInvalidClasses;
    }
}
