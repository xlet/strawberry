package org.xlet.strawberry.core.message.persistent;

import org.xlet.strawberry.core.member.BasicMember;
import org.xlet.strawberry.core.message.client.NormalMessage;

import java.util.Collection;

/**
 * normal message persistent provider.
 */
public interface NormalMessagePersistentProvider extends MessagePersistentProvider<NormalMessage> {

    Collection<NormalMessage> getOfflineMessages(BasicMember owner);

    int setMessageForwarded(BasicMember owner);
}
