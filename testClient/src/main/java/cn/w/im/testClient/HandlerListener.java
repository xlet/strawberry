package cn.w.im.testClient;

import cn.w.im.domains.HandlerContext;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-16 上午9:49.
 * Summary: 操作完成通知接口.
 */
public interface HandlerListener {

    /**
     * 操作完成.
     * @param context 当前上下文.
     */
    void operationComplete(HandlerContext context);
}
