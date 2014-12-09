package org.xlet.strawberry.core.member;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * member basic info.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@type")
public abstract class BasicMember {

    private MemberSourceType memberSource;
    private String id;
    private String nickname;
    private SexType sex;
    private String picUrl;
    private String signature;
    private boolean tempMember;
    private String mobile;
    private String telephone;
    private String address;
    private String email;
    private boolean init;
    private boolean outer;

    /**
     * get member source.
     *
     * @return member source.
     */
    public MemberSourceType getMemberSource() {
        return memberSource;
    }

    /**
     * set member source.
     *
     * @param memberSource member source.
     */
    public void setMemberSource(MemberSourceType memberSource) {
        this.memberSource = memberSource;
    }

    /**
     * get id.
     *
     * @return id.
     */
    public String getId() {
        return id;
    }

    /**
     * set id.
     *
     * @param id id.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * get nickname.
     *
     * @return nickname.
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * set nickname.
     *
     * @param nickname nickname.
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * get sex.
     *
     * @return sex.
     */
    public SexType getSex() {
        return sex;
    }

    /**
     * set sex.
     *
     * @param sex sex.
     */
    public void setSex(SexType sex) {
        this.sex = sex;
    }

    /**
     * get header picture url.
     *
     * @return header picture url.
     */
    public String getPicUrl() {
        return picUrl;
    }

    /**
     * set header picture url.
     *
     * @param picUrl header picture url.
     */
    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    /**
     * get signature.
     *
     * @return signature.
     */
    public String getSignature() {
        return signature;
    }

    /**
     * set signature.
     *
     * @param signature signature.
     */
    public void setSignature(String signature) {
        this.signature = signature;
    }

    /**
     * get whether temp member.
     *
     * @return true:temp member.
     */
    public boolean isTempMember() {
        return tempMember;
    }

    /**
     * set whether temp member.
     *
     * @param tempMember true:temp member.
     */
    public void setTempMember(boolean tempMember) {
        this.tempMember = tempMember;
    }

    /**
     * get mobile.
     *
     * @return mobile.
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * set mobile.
     *
     * @param mobile mobile.
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * get address.
     *
     * @return address.
     */
    public String getAddress() {
        return address;
    }

    /**
     * set address.
     *
     * @param address address.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * get email.
     *
     * @return email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * set email.
     *
     * @param email email.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * get telephone.
     *
     * @return telephone.
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * set telephone.
     *
     * @param telephone telephone.
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * get whether init.
     *
     * @return true:init.
     */
    public boolean isInit() {
        return init;
    }

    /**
     * set whether init.
     *
     * @param init true:init.
     */
    protected void setInit(boolean init) {
        this.init = init;
    }

    /**
     * get is outer system member.
     *
     * @return true:outer.
     */
    public boolean isOuter() {
        return outer;
    }

    /**
     * set is outer system member.
     *
     * @param outer true:outer.
     */
    protected void setOuter(boolean outer) {
        this.outer = outer;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("memberSource").append(this.getMemberSource());
        sb.append("id").append(this.getId());
        sb.append("nickname").append(this.getNickname());
        sb.append("sex").append(this.getSex());
        sb.append("picUrl").append(this.picUrl);
        sb.append("signature").append(this.signature);
        sb.append("tempMember").append(this.isTempMember());
        sb.append("mobile").append(this.getMobile());
        sb.append("telephone").append(this.getTelephone());
        sb.append("address").append(this.getAddress());
        sb.append("email").append(this.getEmail());
        sb.append("init").append(this.isInit());
        sb.append("outer").append(this.isOuter());
        return sb.toString();
    }
}
