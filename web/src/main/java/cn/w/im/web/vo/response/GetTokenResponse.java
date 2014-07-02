package cn.w.im.web.vo.response;

import cn.w.im.web.vo.MemberViewObject;

/**
 * Created by jackie on 14-6-27.
 */
public class GetTokenResponse {

    private boolean success;
    private String token;
    private boolean hasCreateTempId;
    private String tempId;
    private String tempName;
    private MemberViewObject to;
    private MemberViewObject from;

    /**
     * get whether request success.
     * @return {@code true} if request success.
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * set whether request success.
     * @param success {@code} if request success.
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * get token.
     * @return token.
     */
    public String getToken() {
        return token;
    }

    /**
     * set token.
     * @param token token.
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * get whether created temp id.
     * @return {@code true} if created temp id.
     */
    public boolean isHasCreateTempId() {
        return hasCreateTempId;
    }

    /**
     * set whether created temp id.
     * @param hasCreateTempId {@code} if created temp id.
     */
    public void setHasCreateTempId(boolean hasCreateTempId) {
        this.hasCreateTempId = hasCreateTempId;
    }

    /**
     * get temp id.
     * @return temp id.
     */
    public String getTempId() {
        return tempId;
    }

    /**
     * set temp id.
     * @param tempId temp id.
     */
    public void setTempId(String tempId) {
        this.tempId = tempId;
    }

    /**
     * get temp name.
     * @return temp name.
     */
    public String getTempName() {
        return tempName;
    }

    /**
     * set temp name.
     * @param tempName temp name.
     */
    public void setTempName(String tempName) {
        this.tempName = tempName;
    }

    /**
     * get to member info.{@link cn.w.im.web.vo.MemberViewObject}.
     * @return to member info {@link cn.w.im.web.vo.MemberViewObject}.
     */
    public MemberViewObject getTo() {
        return to;
    }

    /**
     * set to member info {@link cn.w.im.web.vo.MemberViewObject}.
     * @param to to member info {@link cn.w.im.web.vo.MemberViewObject}.
     */
    public void setTo(MemberViewObject to) {
        this.to = to;
    }

    /**
     * set from member info {@link cn.w.im.web.vo.MemberViewObject}.
     * @return from member info {@link cn.w.im.web.vo.MemberViewObject}.
     */
    public MemberViewObject getFrom() {
        return from;
    }

    /**
     * set from member info {@link cn.w.im.web.vo.MemberViewObject}.
     * @param from from member info {@link cn.w.im.web.vo.MemberViewObject}.
     */
    public void setFrom(MemberViewObject from) {
        this.from = from;
    }
}
