package org.xlet.strawberry.core.actionSupport;

import org.xlet.strawberry.core.member.BasicMember;

/**
 * member info action persistent provider.
 */
public interface MemberInfoActionPersistentProvider {

    /**
     * save member info change log.
     *
     * @param changeLog change log.
     */
    void save(MemberInfoChangeLog changeLog);

    /**
     * get last member info change log.
     *
     * @param member member.
     * @return change log.
     */
    MemberInfoChangeLog last(BasicMember member);
}
