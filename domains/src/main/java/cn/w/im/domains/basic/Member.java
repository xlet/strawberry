package cn.w.im.domains.basic;

/**
 * member info.
 */
public class Member {

    private int status;  //todo:replace Status Enum. try.
    private boolean temp;
    private boolean isMerchant;
    private String id;
    private String nickName;
    private String address;
    private String mobile;
    private String contractor;
    private String shopName;
    private boolean mobileValid;
    private boolean realNameValid;
    private String thumb;
    private String email;

    /**
     * get status.
     *
     * @return status.
     */
    public int getStatus() {
        return status;
    }

    /**
     * set status.
     *
     * @param status member status.
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * get whether this member is a temp member.
     *
     * @return {@code true} if this member is a temp member.
     */
    public boolean isTemp() {
        return temp;
    }

    /**
     * set whether this member is a temp member.
     *
     * @param temp {@code true} if this member is a temp member.
     */
    public void setTemp(boolean temp) {
        this.temp = temp;
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
     * get member id.
     *
     * @return member id.
     */
    public String getId() {
        return id;
    }

    /**
     * set member id.
     *
     * @param id member id.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * get member nickname.
     *
     * @return member nickname.
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * set member nickname.
     *
     * @param nickName member nickname.
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * get member address.
     *
     * @return member address.
     */
    public String getAddress() {
        return address;
    }

    /**
     * set member address.
     *
     * @param address member address.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * get member mobile.
     *
     * @return member mobile.
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * set member mobile.
     *
     * @param mobile member mobile.
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    /**
     * get member header picture url.
     *
     * @return member header picture url.
     */
    public String getThumb() {
        return thumb;
    }

    /**
     * set member header picture url.
     *
     * @param thumb member header picture url.
     */
    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    /**
     * get member email address.
     *
     * @return member email address.
     */
    public String getEmail() {
        return email;
    }

    /**
     * set member email address.
     *
     * @param email member email address.
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
