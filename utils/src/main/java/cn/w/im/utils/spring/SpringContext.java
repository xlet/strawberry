package cn.w.im.utils.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-8 下午5:50.
 * Summary: springContext帮助类.
 */
public class SpringContext {

    private static ApplicationContext applicationContext;

    /**
     * 获取spring ApplicationContext.
     * @return spring ApplicationContext.
     */
    public static ApplicationContext context() {
        if (applicationContext == null) {
            applicationContext = new ClassPathXmlApplicationContext("classpath*:/applicationContext.xml");
        }
        return applicationContext;
    }
}
