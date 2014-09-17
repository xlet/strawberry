package cn.w.im.domains.conf;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-4 下午2:09.
 * Summary: 配置信息.
 */
public class Configuration {

    private String bind;

    private int port;

    private boolean debug = false;

    private String dataStoreType = "mongo";

    /**
     * get bind.
     * @return bind.
     */
    public String getBind(){
        return this.bind;
    }

    /**
     * set bind.
     * @param bind bind.
     */
    public void setBind(String bind){
        this.bind = bind;
    }

    /**
     * get port.
     * @return port.
     */
    public int getPort(){
        return this.port;
    }

    /**
     * set port.
     * @param port port.
     */
    public void setPort(int port){
        this.port = port;
    }

    /**
     * 获取是否已debug模式运行.
     *
     * @return true:debug.
     */
    public boolean isDebug() {
        return debug;
    }

    /**
     * set whether debug mode.
     * @param debug
     */
    public void setDebug(boolean debug){
        this.debug = debug;
    }

    /**
     * get data store type.
     *
     * @return data store type.
     */
    public String getDataStoreType() {
        return this.dataStoreType;
    }

    /**
     * set data store type.
     * @param dataStoreType data store type.
     */
    public void setDataStoreType(String dataStoreType){
        this.dataStoreType = dataStoreType;
    }
}
