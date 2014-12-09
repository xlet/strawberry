package org.xlet.strawberry.core.member;

/**
 * member info persistent provider.
 */
public interface MemberInfoPersistentProvider {

    void save(BasicMember member);

    BasicMember get(String memberId);

    boolean isChanged(BasicMember member);

    boolean exists(BasicMember basicMember);

    void update(BasicMember basicMember);
}
