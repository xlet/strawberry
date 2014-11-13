package cn.w.im.core.providers.status;

import cn.w.im.core.MessageHandlerContext;
import cn.w.im.core.exception.MemberAllNotExisted;
import cn.w.im.core.member.BasicMember;

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

    /**
     * get member all.
     *
     * @param member member.
     * @return member all.
     */
    MemberAll getMember(BasicMember member) throws MemberAllNotExisted;
}
