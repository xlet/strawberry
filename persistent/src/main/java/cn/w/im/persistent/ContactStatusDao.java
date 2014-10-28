package cn.w.im.persistent;

import cn.w.im.domains.member.BasicMember;
import cn.w.im.domains.status.ContactStatus;

import java.util.List;

/**
 * contact status dao.
 */
public interface ContactStatusDao {

    void save(ContactStatus contactStatus);

    List<ContactStatus> getContactStatus(BasicMember member, int limit);
}
