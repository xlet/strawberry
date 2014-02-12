package cn.w.im.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Properties;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-13 上午9:43.
 * Summary: 配置文件帮助类.
 */
public class ConfigHelper {

    /**
     * 获取配置文件内容.
     * @param cls 类，最好是main函数所在的类.
     * @param fileRelativePath 配置文件相对路径.
     * @return 配置文件内容.
     * @throws Exception 未知异常.
     */
    public static Properties getConfig(Class cls, String fileRelativePath) throws Exception {
        URL url = cls.getProtectionDomain().getCodeSource().getLocation();
        String path = URLDecoder.decode(url.getPath(), "UTF-8");
        if (path.endsWith(".jar")) {
            path = path.substring(0, path.lastIndexOf("/") + 1);
        }
        if (path.contains("classes")) {
            path = path.substring(0, path.indexOf("classes"));
        }

        String configPath = path + fileRelativePath;
        File file = new File(configPath);
        if (!file.exists()) {
            throw new FileNotFoundException(configPath);
        }
        FileReader reader = new FileReader(file);
        Properties properties = new Properties();
        properties.load(reader);

        return properties;
    }
}
