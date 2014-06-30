package cn.w.im.utils.sdk.usercenter.config;

import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Creator: JimmyLin
 * DateTime: 14-6-30 上午9:44
 * Summary: 用户中心SDK配置
 */
public class UcConfig {
    private static Logger logger = Logger.getLogger(UcConfig.class);

    private UcConfig(){}

    private static Properties props = new Properties();

    static{
        try {
            logger.debug("loading user center config ...");
            props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties"));
            logger.debug(props.size()+" user center config items loaded!");
        } catch (FileNotFoundException e) {
            logger.debug(e.getMessage());
        } catch (IOException e) {
            logger.debug(e.getMessage());
        }
    }
    public static String getValue(String key){
        return props.getProperty(key);
    }

    public static void updateProperties(String key,String value) {
        props.setProperty(key, value);
    }
}
