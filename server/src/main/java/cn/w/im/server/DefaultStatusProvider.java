package cn.w.im.server;

import cn.w.im.domains.basic.Member;
import cn.w.im.domains.basic.OnlineMemberStatus;
import cn.w.im.domains.basic.Status;
import cn.w.im.persistent.OnlineMemberStatusDao;
import cn.w.im.utils.spring.SpringContext;

import java.util.List;

/**
 * default implement of {@link cn.w.im.server.StatusProvider}.
 */
public class DefaultStatusProvider implements StatusProvider {

    @Override
    public void render(List<Member> members) {
        OnlineMemberStatusDao memberStatusDao = SpringContext.context().getBean("mongoOnlineMemberStatusDao", OnlineMemberStatusDao.class);
        if (members != null && members.size() > 0) {
            for (Member member : members) {
                OnlineMemberStatus memberStatus = memberStatusDao.get(member.getId());
                if (memberStatus != null) {
                    member.setStatus(memberStatus.getStatus().getValue());
                }
            }
        }
    }

    @Override
    public Status status(String loginId) {
        OnlineMemberStatusDao memberStatusDao = SpringContext.context().getBean("mongoOnlineMemberStatusDao", OnlineMemberStatusDao.class);
        return memberStatusDao.get(loginId).getStatus();
    }
}
