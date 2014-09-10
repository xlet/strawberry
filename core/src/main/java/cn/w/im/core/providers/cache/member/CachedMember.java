package cn.w.im.core.providers.cache.member;

import cn.w.im.domains.basic.Member;
import cn.w.im.domains.client.MessageClientType;

/**
 * cached member.
 */
public class CachedMember extends Member {

    public CachedMember(Member member, MessageClientType memberClient, long expiredTime) {
        this.memberClient = memberClient;
        this.expiredTime = expiredTime;
        this.setId(member.getId());
        this.setStatus(member.getStatus());
        this.setTemp(member.isTemp());
        this.setRealNameValid(member.isRealNameValid());
        this.setAddress(member.getAddress());
        this.setContractor(member.getContractor());
        this.setEmail(member.getEmail());
        this.setMerchant(member.isMerchant());
        this.setMobile(member.getMobile());
        this.setMobileValid(member.isMobileValid());
        this.setNickName(member.getNickName());
        this.setShopName(member.getShopName());
        this.setThumb(member.getThumb());
    }

    private MessageClientType memberClient;
    private long expiredTime;
    private long since = System.currentTimeMillis();

    public MessageClientType getMemberClient() {
        return memberClient;
    }

    public void setMemberClient(MessageClientType memberClient) {
        this.memberClient = memberClient;
    }

    public long getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(long expiredTime) {
        this.expiredTime = expiredTime;
    }

    public boolean isExpired() {
        if ((System.currentTimeMillis() - since >= expiredTime)) {
            return true;
        }
        return false;

    }
}
