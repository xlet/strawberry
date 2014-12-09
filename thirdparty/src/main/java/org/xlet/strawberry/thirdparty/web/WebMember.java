package org.xlet.strawberry.thirdparty.web;

import org.xlet.strawberry.core.member.MemberSourceType;
import org.xlet.strawberry.core.member.BasicMember;

/**
 * member info.
 */
public class WebMember extends BasicMember {

    private boolean isMerchant;
    private String contractor;
    private String shopName;
    private boolean mobileValid;
    private boolean realNameValid;

    public WebMember() {
        this.setMemberSource(MemberSourceType.WCN);
        this.setTempMember(false);
        this.setInit(true);
        this.setOuter(true);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append("isMerchant").append(this.isMerchant());
        sb.append("contractor").append(this.getContractor());
        sb.append("shopName").append(this.getShopName());
        sb.append("mobileValid").append(this.isMobileValid());
        sb.append("realNameValid").append(this.isRealNameValid());
        return sb.toString();
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
