package org.xlet.strawberry.core.config;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;

import java.io.File;

/**
 * system property PropertyPlaceholderConfigurer.
 * <p/>
 * the property file defined by system property.
 */
public class SystemPropertyPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

    private Logger logger = LoggerFactory.getLogger(SystemPropertyPropertyPlaceholderConfigurer.class);

    public SystemPropertyPropertyPlaceholderConfigurer(String systemProperty) throws SystemPropertyNotSetException {
        if (logger.isInfoEnabled()) {
            logger.info("init system property PropertyPlaceholderConfigurer");
        }

        if (!StringUtils.isEmpty(systemProperty)) {
            if (logger.isDebugEnabled()) {
                logger.debug("get system property:" + systemProperty);
            }
            String path = System.getProperty(systemProperty);
            if (!StringUtils.isEmpty(path)) {
                if (logger.isDebugEnabled()) {
                    logger.debug("get property value:" + path);
                }
                File file = new File(path);
                if (file.exists()) {
                    if (logger.isInfoEnabled()) {
                        logger.info("set property file:" + file.getPath());
                    }
                    this.setLocation(new FileSystemResource(file));
                }
            } else {
                throw new SystemPropertyNotSetException(systemProperty);
            }
        }
    }
}
