package cn.w.im.web.vo;

/**
 * @author jackie.
 */
public class LinkmanViewObject {
    private String id,name,thumb;
    private int status;

    /**
     * get id.
     * @return id.
     */
    public String getId() {
        return id;
    }

    /**
     * set id.
     * @param id id.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * get nickname.
     * @return nickname.
     */
    public String getName() {
        return name;
    }

    /**
     * set nickname.
     * @param name nickname.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * get thumb.
     * @return thumb.
     */
    public String getThumb() {
        return thumb;
    }

    /**
     * set thumb.
     * @param thumb thumb.
     */
    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    /**
     * get status.
     * @return status see{@link cn.w.im.web.vo.Status}
     */
    public int getStatus() {
        return status;
    }

    /**
     * set status.
     * @param status status.
     */
    public void setStatus(int status) {
        this.status = status;
    }
}
