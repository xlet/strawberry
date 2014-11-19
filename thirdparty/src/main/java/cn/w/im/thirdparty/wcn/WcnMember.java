package cn.w.im.thirdparty.wcn;

import cn.w.im.core.member.MemberSourceType;
import cn.w.im.core.member.BasicMember;

/**
 * member info.
 */
public class WcnMember extends BasicMember {

    private boolean isMerchant;
    private String contractor;
    private String shopName;
    private boolean mobileValid;
    private boolean realNameValid;

    public WcnMember() {
        this.setMemberSource(MemberSourceType.WCN);
        this.setTempMember(false);
        this.setInit(true);
    }

    /**
     * get whether this member is a merchant.
     *
     * @return {@code true} if this member is a merchant.
     */
    public boolean isMerchant() {
        return isMerchant;
    }

    /**
     * set whether this member is a merchant.
     *
     * @param isMerchant {@code true} if this member is a merchant.
     */
    public void setMerchant(boolean isMerchant) {
        this.isMerchant = isMerchant;
    }

    /**
     * get merchant contractor.
     *
     * @return merchant contractor.
     */
    public String getContractor() {
        return contractor;
    }

    /**
     * set merchant contractor.
     *
     * @param contractor merchant contractor.
     */
    public void setContractor(String contractor) {
        this.contractor = contractor;
    }

    /**
     * get merchant shop name.
     *
     * @return merchant shop name.
     */
    public String getShopName() {
        return shopName;
    }

    /**
     * set merchant shop name.
     *
     * @param shopName merchant shop name.
     */
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    /**
     * get whether member mobile has valid.
     *
     * @return {@code true} if member mobile has valid.
     */
    public boolean isMobileValid() {
        return mobileValid;
    }

    /**
     * set whether member mobile has valid.
     *
     * @param mobileValid {@code true} if member mobile has valid.
     */
    public void setMobileValid(boolean mobileValid) {
        this.mobileValid = mobileValid;
    }

    /**
     * get whether member name has valid.
     *
     * @return {@code true} if member name has valid.
     */
    public boolean isRealNameValid() {
        return realNameValid;
    }

    /**
     * set whether member name has valid.
     *
     * @param realNameValid {@code true} if member name has valid.
     */
    public void setRealNameValid(boolean realNameValid) {
        this.realNameValid = realNameValid;
    }
}
