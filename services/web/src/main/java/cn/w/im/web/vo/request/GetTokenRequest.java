package cn.w.im.web.vo.request;

/**
 * Created by jackie on 14-6-27.
 * get token request.
 */
public class GetTokenRequest {

    private String fromId;
    private String toId;
    private String callback;

    /**
     * get callback.
     * @return callback.
     */
    public String getCallback() {
        return callback;
    }

    /**
     * set callback.
     * @param callback callback.
     */
    public void setCallback(String callback) {
        this.callback = callback;
    }

    /**
     * get to id.
     * @return to id.
     */
    public String getToId() {
        return toId;
    }

    /**
     * set to id.
     * @param toId to  id.
     */
    public void setToId(String toId) {
        this.toId = toId;
    }

    /**
     * get from id.
     * @return from id.
     */
    public String getFromId() {
        return fromId;
    }

    /**
     * set from id.
     * @param fromId from id.
     */
    public void setFromId(String fromId) {
        this.fromId = fromId;
    }
}
