package cn.w.im.core.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-8 下午5:50.
 * Summary: springContext帮助类.
 */
public class SpringContext implements ApplicationContextAware {

    private final static Logger LOG = LoggerFactory.getLogger(SpringContext.class);

    private static ApplicationContext appCtx;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        LOG.debug("aware application context.");
        appCtx = applicationContext;
    }

    public static ApplicationContext context() {
        if (appCtx == null) {
            appCtx = new ClassPathXmlApplicationContext("classpath*:applicationContext.xml");
        }
        return appCtx;
    }


}
