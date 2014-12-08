package cn.w.im.core.status.basicStatus;

import cn.w.im.core.member.BasicMember;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * default implement of {@link cn.w.im.core.status.basicStatus.StatusProvider}.
 */
public class DefaultStatusProvider implements StatusProvider {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    private Map<String, MemberStatus> memberStatusMap;


    public DefaultStatusProvider() {
        this.memberStatusMap = new ConcurrentHashMap<String, MemberStatus>();
    }

    @Override
    public MemberStatus status(BasicMember member) {
        if (this.memberStatusMap.containsKey(member.getId())) {
            return this.memberStatusMap.get(member.getId());
        }
        return new MemberStatus(member, Status.Offline);
    }

    @Override
    public void change(BasicMember member, Status status) {
        if (status.getValue() == Status.Offline.getValue()) {
            this.memberStatusMap.remove(member.getId());
        }
        if (this.memberStatusMap.containsKey(member.getId())) {
            MemberStatus memberStatus = this.status(member);
            memberStatus.setStatus(status);
        } else {
            MemberStatus memberStatus = new MemberStatus(member, status);
            this.memberStatusMap.put(member.getId(), memberStatus);
        }
    }

    @Override
    public boolean online(BasicMember member) {
        MemberStatus status = this.status(member);
        return !status.getStatus().equals(Status.Offline);
    }
}
