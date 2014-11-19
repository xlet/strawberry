package cn.w.im.core.message.persistent;

import cn.w.im.core.member.BasicMember;
import cn.w.im.core.message.client.NormalMessage;
import cn.w.im.core.message.persistent.MessagePersistentProvider;

import java.util.Collection;

/**
 * normal message persistent provider.
 */
public interface NormalMessagePersistentProvider extends MessagePersistentProvider<NormalMessage> {

    Collection<NormalMessage> getOfflineMessages(BasicMember owner);

    int setMessageForwarded(BasicMember owner);
}
