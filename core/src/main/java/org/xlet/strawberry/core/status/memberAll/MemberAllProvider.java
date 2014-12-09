package org.xlet.strawberry.core.status.memberAll;

import org.xlet.strawberry.core.MessageHandlerContext;
import org.xlet.strawberry.core.exception.ServerInnerException;
import org.xlet.strawberry.core.member.BasicMember;

/**
 * member status provider.
 */
public interface MemberAllProvider {

    /**
     * handler message.
     *
     * @param context message handler context.
     */
    void handlerMessage(MessageHandlerContext context) throws ServerInnerException;

    /**
     * get member all.
     *
     * @param member member.
     * @return member all.
     */
    MemberAll getMember(BasicMember member) throws MemberAllNotExisted;
}
