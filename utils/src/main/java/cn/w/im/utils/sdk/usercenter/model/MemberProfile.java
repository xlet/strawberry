package cn.w.im.utils.sdk.usercenter.model;

/**
 * Creator: JimmyLin
 * DateTime: 14-6-30 上午9:48
 * Summary: 用户资料
 */
public class MemberProfile {

    private String id;
    private String wid;
    private String nickname;
    private String realName;
    private String sexType;
    private String birthday;
    private String mobile;
    private String telephone;
    private String email;
    private String photoUrl;
    private String create;
    private boolean merchant;
    private long blueDiamondQty;
    private long redDiamondQty;
    private String provinceId;
    private String cityId;
    private String areaId;
    private String streetId;
    private String province;
    private String city;
    private String area;
    private String street;
    private String address;
    private boolean emailValid;
    private boolean mobileValid;
    private boolean realNameValid;
    private boolean companyValid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWid() {
        return wid;
    }

    public void setWid(String wid) {
        this.wid = wid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getSexType() {
        return sexType;
    }

    public void setSexType(String sexType) {
        this.sexType = sexType;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getCreate() {
        return create;
    }

    public void setCreate(String create) {
        this.create = create;
    }

    public boolean isMerchant() {
        return merchant;
    }

    public void setMerchant(boolean merchant) {
        this.merchant = merchant;
    }

    public long getBlueDiamondQty() {
        return blueDiamondQty;
    }

    public void setBlueDiamondQty(long blueDiamondQty) {
        this.blueDiamondQty = blueDiamondQty;
    }

    public long getRedDiamondQty() {
        return redDiamondQty;
    }

    public void setRedDiamondQty(long redDiamondQty) {
        this.redDiamondQty = redDiamondQty;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getStreetId() {
        return streetId;
    }

    public void setStreetId(String streetId) {
        this.streetId = streetId;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isEmailValid() {
        return emailValid;
    }

    public void setEmailValid(boolean emailValid) {
        this.emailValid = emailValid;
    }

    public boolean isMobileValid() {
        return mobileValid;
    }

    public void setMobileValid(boolean mobileValid) {
        this.mobileValid = mobileValid;
    }

    public boolean isRealNameValid() {
        return realNameValid;
    }

    public void setRealNameValid(boolean realNameValid) {
        this.realNameValid = realNameValid;
    }

    public boolean isCompanyValid() {
        return companyValid;
    }

    public void setCompanyValid(boolean companyValid) {
        this.companyValid = companyValid;
    }

    @Override
    public String toString() {
        return "MemberProfile{" +
                "id='" + id + '\'' +
                ", wid='" + wid + '\'' +
                ", nickname='" + nickname + '\'' +
                ", realName='" + realName + '\'' +
                ", sexType='" + sexType + '\'' +
                ", birthday='" + birthday + '\'' +
                ", mobile='" + mobile + '\'' +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", create='" + create + '\'' +
                ", merchant=" + merchant +
                ", blueDiamondQty=" + blueDiamondQty +
                ", redDiamondQty=" + redDiamondQty +
                ", provinceId='" + provinceId + '\'' +
                ", cityId='" + cityId + '\'' +
                ", areaId='" + areaId + '\'' +
                ", streetId='" + streetId + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", area='" + area + '\'' +
                ", street='" + street + '\'' +
                ", address='" + address + '\'' +
                ", emailValid=" + emailValid +
                ", mobileValid=" + mobileValid +
                ", realNameValid=" + realNameValid +
                ", companyValid=" + companyValid +
                '}';
    }
}
