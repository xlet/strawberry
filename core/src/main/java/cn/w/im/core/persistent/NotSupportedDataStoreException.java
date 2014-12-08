package cn.w.im.core.persistent;

import cn.w.im.core.exception.ServerInnerException;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-29 下午8:28.
 * Summary: not supported data store exception.
 */
public class NotSupportedDataStoreException extends ServerInnerException {

    private String dataStoreType;

    /**
     * get data store type.
     *
     * @return data store type.
     */
    public String getDataStoreType() {
        return this.dataStoreType;
    }

    /**
     * 构造函数.
     *
     * @param dataStoreType data store type.
     */
    public NotSupportedDataStoreException(String dataStoreType) {
        super("not support data store type[" + dataStoreType + "]");
        this.dataStoreType = dataStoreType;
    }
}
