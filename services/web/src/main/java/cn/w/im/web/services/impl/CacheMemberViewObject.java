package cn.w.im.web.services.impl;

import cn.w.im.web.vo.MemberViewObject;

/**
 * cached member view object.
 * <p/>
 * expired time:20 minute.
 */
public class CacheMemberViewObject extends MemberViewObject {

    private long expiredTime;

    /**
     * constructor.
     */
    public CacheMemberViewObject() {
        expiredTime = System.currentTimeMillis() + 20 * 60 * 1000;
    }

    /**
     * constructor.
     *
     * @param memberViewObject member view object.
     */
    public CacheMemberViewObject(MemberViewObject memberViewObject) {
        this();
        this.setId(memberViewObject.getId());
        this.setStatus(memberViewObject.getStatus());
        this.setTemp(memberViewObject.isTemp());
        this.setRealNameValid(memberViewObject.isRealNameValid());
        this.setAddress(memberViewObject.getAddress());
        this.setContractor(memberViewObject.getContractor());
        this.setEmail(memberViewObject.getEmail());
        this.setMerchant(memberViewObject.isMerchant());
        this.setMobile(memberViewObject.getMobile());
        this.setMobileValid(memberViewObject.isMobileValid());
        this.setNickName(memberViewObject.getNickName());
        this.setShopName(memberViewObject.getShopName());
        this.setThumb(memberViewObject.getThumb());
    }

    public long getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(long expiredTime) {
        this.expiredTime = expiredTime;
    }

    public boolean isExpired() {
        return this.expiredTime < System.currentTimeMillis();
    }
}
