package cn.w.im.exceptions;

import cn.w.im.domains.server.ServerType;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-16 上午11:20.
 * Summary: 不支持的服务类型异常.
 */
public class NotSupportedServerTypeException extends RuntimeException {

    private ServerType currentType;

    /**
     * 构造函数.
     *
     * @param currentType 当前服务类型.
     */
    public NotSupportedServerTypeException(ServerType currentType) {
        super("不支持的服务类型[" + currentType.toString() + "]");
        this.currentType = currentType;
    }

    /**
     * 获取当前的服务类型.
     *
     * @return 服务类型.
     */
    public ServerType getCurrentType() {
        return currentType;
    }
}
