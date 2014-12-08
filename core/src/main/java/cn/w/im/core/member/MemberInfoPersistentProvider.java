package cn.w.im.core.member;

/**
 * member info persistent provider.
 */
public interface MemberInfoPersistentProvider {

    void save(BasicMember member);

    BasicMember get(String memberId);

    boolean isChanged(BasicMember member);

    boolean exists(BasicMember basicMember);
}
