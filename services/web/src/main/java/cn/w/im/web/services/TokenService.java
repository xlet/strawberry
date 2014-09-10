package cn.w.im.web.services;

/**
 * @author jackie.
 *         create token string by special algorithm.
 */
public interface TokenService {

    /**
     * create token string.
     *
     * @param fromId   from id.
     * @param toId     to id.
     * @param referrer referrer url.
     * @return token string.
     */
    String create(String fromId, String toId, String referrer);
}
