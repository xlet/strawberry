package cn.w.im.core.providers.status;

import cn.w.im.core.MessageHandlerContext;

/**
 * member status provider.
 */
public interface MemberProvider {

    /**
     * handler message.
     *
     * @param context message handler context.
     */
    void handlerMessage(MessageHandlerContext context);
}
